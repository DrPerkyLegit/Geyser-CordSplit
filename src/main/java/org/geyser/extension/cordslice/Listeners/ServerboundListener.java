package org.geyser.extension.cordslice.Listeners;

import org.cloudburstmc.math.vector.Vector3d;
import org.cloudburstmc.math.vector.Vector3i;
import org.geyser.extension.cordslice.Events.Event;
import org.geyser.extension.cordslice.Events.EventHandler;
import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geyser.extension.cordslice.Random.PositionSlicer;
import org.geyser.extension.cordslice.Random.PositionTracker;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.entity.type.player.PlayerEntity;
import org.geysermc.geyser.session.DownstreamSession;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.data.ProtocolState;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundTeleportEntityPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSetChunkCacheCenterPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosRotPacket;
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundCustomQueryAnswerPacket;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerboundListener implements EventListener {
    public ServerboundListener(CordSliceExtension extension) { }

    public static Set<Packet> outboundPackets = ConcurrentHashMap.newKeySet(); //maybe find a better way to prevent reading outbound packets

    //packets here are in sliced cords, need to be normalized

    @EventHandler
    public void onServerboundMovePlayerPosPacket(Event event, ServerboundMovePlayerPosPacket packet) {
        Vector3i positionSlice = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.SLICE).toInt();
        PositionSlicer.SlicedPosition slicedPosition = new PositionSlicer.SlicedPosition(
                packet.getX(), packet.getY(), packet.getZ(),
                positionSlice.getX(), positionSlice.getY(), positionSlice.getZ()
        );

        Vector3d realPosition = slicedPosition.getRealPosition();

        CordSliceExtension.staticLogger.info("[Slice] Packet position: " + packet.getX() + ", " + packet.getY() + ", " + packet.getZ());
        CordSliceExtension.staticLogger.info("[Slice] Sliced position: " + slicedPosition.getPosition().getX() + ", " + slicedPosition.getPosition().getY() + ", " + slicedPosition.getPosition().getZ());
        CordSliceExtension.staticLogger.info("[Slice] Slice offset: " + positionSlice);

        event.setCanceled(true);

        if (slicedPosition.getPosition().distance(packet.getX(), packet.getY(), packet.getZ()) > 100) {//idk if this actually works
            PlayerEntity playerEntity = ((GeyserSession) event.getConnection()).getPlayerEntity();
            ClientboundTeleportEntityPacket teleportEntityPacket = new ClientboundTeleportEntityPacket(
                    playerEntity.getEntityId(),
                    slicedPosition.getPosition(),
                    playerEntity.getMotion().toDouble(),
                    playerEntity.getYaw(),
                    playerEntity.getPitch(),
                    new ArrayList<>(),
                    playerEntity.isOnGround()
            );

            ClientboundListener.handlePacket(event.getConnection(), teleportEntityPacket);
            CordSliceExtension.staticLogger.info("[Slice] Attempting Self Teleport: " + realPosition);
        }
            ServerboundMovePlayerPosPacket toSend = new ServerboundMovePlayerPosPacket(
                    packet.isOnGround(),
                    packet.isHorizontalCollision(),
                    realPosition.getX(),
                    realPosition.getY(),
                    realPosition.getZ()
            );


            sendPacket(event.getConnection(), toSend);
            CordSliceExtension.staticLogger.info("[Slice] Real position to sent to server: " + realPosition);
    }


    @EventHandler
    public void onServerboundMovePlayerPosRotPacket(Event event, ServerboundMovePlayerPosRotPacket packet) {
        Vector3i positionSlice = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.SLICE).toInt();
        PositionSlicer.SlicedPosition slicedPosition = new PositionSlicer.SlicedPosition(packet.getX(), packet.getY(), packet.getZ(), positionSlice.getX(), positionSlice.getY(), positionSlice.getZ());

        Vector3d realPosition = slicedPosition.getRealPosition();

        ServerboundMovePlayerPosRotPacket toSend = new ServerboundMovePlayerPosRotPacket(packet.isOnGround(), packet.isHorizontalCollision(), realPosition.getX(), realPosition.getY(), realPosition.getZ(), packet.getYaw(), packet.getPitch());

        event.setCanceled(true);
        sendPacket(event.getConnection(), toSend);

        //work around for now, please find better fix

        ClientboundSetChunkCacheCenterPacket setCenterPacket = new ClientboundSetChunkCacheCenterPacket((int) Math.floor(packet.getX() / 16.0), (int) Math.floor(packet.getX() / 16.0));
        ClientboundListener.handlePacket(event.getConnection(), setCenterPacket);
    }




    public void sendPacket(GeyserConnection connection, Packet packet) {
        DownstreamSession downstreamSession = ((GeyserSession) connection).getDownstream();
        outboundPackets.add(packet);

        ProtocolState state = downstreamSession.getSession().getPacketProtocol().getOutboundState();
        if (state == ProtocolState.GAME || state == ProtocolState.CONFIGURATION || packet.getClass() == ServerboundCustomQueryAnswerPacket.class) {
            downstreamSession.sendPacket(packet);
        }
    }

}
