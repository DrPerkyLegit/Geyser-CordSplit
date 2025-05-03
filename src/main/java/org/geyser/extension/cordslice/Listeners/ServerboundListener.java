package org.geyser.extension.cordslice.Listeners;

import org.cloudburstmc.math.vector.Vector3d;
import org.geyser.extension.cordslice.Events.EventHandler;
import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.Events.JavaPacketEvent;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geyser.extension.cordslice.Random.PositionSlicer;
import org.geyser.extension.cordslice.Random.SliceTracker;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundPlayerChatPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosRotPacket;

import javax.swing.text.Position;

public class ServerboundListener implements EventListener {
    public ServerboundListener(CordSliceExtension extension) {

    }

    @EventHandler
    public void onJavaPacketEvent(JavaPacketEvent event) {

        if (event.getPacket() instanceof ServerboundMovePlayerPosPacket movePlayerPosPacket) {
            event.setCanceled(true);
            CordSliceExtension.staticLogger.info("Java Packet");


            Vector3d playerLocation = Vector3d.from(movePlayerPosPacket.getX(), movePlayerPosPacket.getY(), movePlayerPosPacket.getZ());
            Vector3d slicedPosition = PositionSlicer.SlicedPosition.getRealPosition(playerLocation, SliceTracker.getSlice(event.getConnection()));


            ServerboundMovePlayerPosPacket newPacket = new ServerboundMovePlayerPosPacket(movePlayerPosPacket.isOnGround(), movePlayerPosPacket.isHorizontalCollision(), slicedPosition.getX(), slicedPosition.getY(), slicedPosition.getZ());

            sendPacket(event.getConnection(), newPacket);

        } else if (event.getPacket() instanceof ServerboundMovePlayerPosRotPacket movePlayerPosRotPacket) {
            event.setCanceled(true);
            CordSliceExtension.staticLogger.info("Java Packet");


            Vector3d playerLocation = Vector3d.from(movePlayerPosRotPacket.getX(), movePlayerPosRotPacket.getY(), movePlayerPosRotPacket.getZ());
            Vector3d slicedPosition = PositionSlicer.SlicedPosition.getRealPosition(playerLocation, SliceTracker.getSlice(event.getConnection()));


            ServerboundMovePlayerPosRotPacket newPacket = new ServerboundMovePlayerPosRotPacket(movePlayerPosRotPacket.isOnGround(), movePlayerPosRotPacket.isHorizontalCollision(), slicedPosition.getX(), slicedPosition.getY(), slicedPosition.getZ(), movePlayerPosRotPacket.getYaw(), movePlayerPosRotPacket.getPitch());

            sendPacket(event.getConnection(), newPacket);
        }
    }


    public void sendPacket(GeyserConnection connection, Packet packet) {
        ((GeyserSession) connection).getDownstream().sendPacket(packet);
    }

}
