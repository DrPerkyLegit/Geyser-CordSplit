package org.geyser.extension.cordslice.Listeners;

import org.cloudburstmc.math.vector.Vector2i;
import org.cloudburstmc.math.vector.Vector3d;
import org.geyser.extension.cordslice.Events.Event;
import org.geyser.extension.cordslice.Events.EventHandler;
import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geyser.extension.cordslice.Random.PositionSlicer;
import org.geyser.extension.cordslice.Random.PositionTracker;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.entity.type.player.PlayerEntity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.ClientSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundAddEntityPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundTeleportEntityPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundForgetLevelChunkPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLevelChunkWithLightPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSetChunkCacheCenterPacket;

public class ClientboundListener implements EventListener {

    public ClientboundListener(CordSliceExtension extension) {

    }

    //packets here are in normal cords, need to be sliced

    @EventHandler
    public void onClientboundAddEntityPacket(Event event, ClientboundAddEntityPacket packet) {

    }

    @EventHandler
    public void onClientboundPlayerPositionPacket(Event event, ClientboundPlayerPositionPacket packet) {
        PositionSlicer.SlicedPosition slicedPosition = PositionSlicer.getSlicedPosition(packet.getPosition());
        PositionTracker.updateSlice(event.getConnection(), PositionTracker.SliceType.SLICE, slicedPosition.getSlice().toDouble());
        PositionTracker.updateSlice(event.getConnection(), PositionTracker.SliceType.POSITION, packet.getPosition());

        //if (slicedPosition.getPosition().distance(packet.getPosition()) > 100) {
        //    PlayerEntity playerEntity = ((GeyserSession) event.getConnection()).getPlayerEntity();
        //    ClientboundTeleportEntityPacket teleportEntityPacket = new ClientboundTeleportEntityPacket(playerEntity.getEntityId(), slicedPosition.getPosition(), packet.getDeltaMovement(), packet.getYRot(), packet.getXRot(), packet.getRelatives(), playerEntity.isOnGround());
        //
        //    handlePacket(event.getConnection(), teleportEntityPacket);
        //}

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withPosition(slicedPosition.getPosition()));
    }



    @EventHandler
    public void onClientboundSetChunkCacheCenterPacket(Event event, ClientboundSetChunkCacheCenterPacket packet) {
        Vector3d realPosition = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.POSITION);
        PositionSlicer.SlicedPosition slicedPosition = PositionSlicer.getSlicedPosition(realPosition);

        int chunkX = (int) Math.floor(slicedPosition.getPosition().getX() / 16.0);
        int chunkZ = (int) Math.floor(slicedPosition.getPosition().getZ() / 16.0);

        int realchunkX = (int) Math.floor(realPosition.getX() / 16.0);
        int realchunkZ = (int) Math.floor(realPosition.getZ() / 16.0);

        CordSliceExtension.staticLogger.info(String.format(
                "chunk center: Real=(%d,%d) → Fake=(%d,%d)", chunkX, chunkZ, realchunkX, realchunkZ
        ));

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withChunkX(chunkX).withChunkZ(chunkZ));
    }

    @EventHandler
    public void onClientboundLevelChunkWithLightPacket(Event event, ClientboundLevelChunkWithLightPacket packet) {
        Vector3d realPosition = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.POSITION);
        PositionSlicer.SlicedPosition slicedPosition = PositionSlicer.getSlicedPosition(realPosition);

        Vector2i realChunkPos = Vector2i.from(packet.getX(), packet.getZ());

        int realPlayerChunkX = Math.floorDiv((int) realPosition.getX(), 16);
        int realPlayerChunkZ = Math.floorDiv((int) realPosition.getZ(), 16);

        // Calculate offset of this chunk relative to the player's real chunk position
        int dx = realChunkPos.getX() - realPlayerChunkX;
        int dz = realChunkPos.getY() - realPlayerChunkZ;

        // Apply that offset to the fake sliced chunk position
        int fakePlayerChunkX = Math.floorDiv((int) slicedPosition.getPosition().getX(), 16);
        int fakePlayerChunkZ = Math.floorDiv((int) slicedPosition.getPosition().getZ(), 16);

        int fakeChunkX = fakePlayerChunkX + dx;
        int fakeChunkZ = fakePlayerChunkZ + dz;

        CordSliceExtension.staticLogger.info(String.format(
                "Chunk rewrite: Real=(%d,%d) → Fake=(%d,%d) | Offset=(%d,%d)",
                realChunkPos.getX(), realChunkPos.getY(), fakeChunkX, fakeChunkZ, dx, dz
        ));

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withX(fakeChunkX).withZ(fakeChunkZ));
    }

    @EventHandler
    public void onClientboundForgetLevelChunkPacket(Event event, ClientboundForgetLevelChunkPacket packet) {
        Vector3d realPosition = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.POSITION);
        PositionSlicer.SlicedPosition slicedPosition = PositionSlicer.getSlicedPosition(realPosition);

        Vector2i realChunkPos = Vector2i.from(packet.getX(), packet.getZ());

        int realPlayerChunkX = Math.floorDiv((int) realPosition.getX(), 16);
        int realPlayerChunkZ = Math.floorDiv((int) realPosition.getZ(), 16);

        // Calculate offset of this chunk relative to the player's real chunk position
        int dx = realChunkPos.getX() - realPlayerChunkX;
        int dz = realChunkPos.getY() - realPlayerChunkZ;

        // Apply that offset to the fake sliced chunk position
        int fakePlayerChunkX = Math.floorDiv((int) slicedPosition.getPosition().getX(), 16);
        int fakePlayerChunkZ = Math.floorDiv((int) slicedPosition.getPosition().getZ(), 16);

        int fakeChunkX = fakePlayerChunkX + dx;
        int fakeChunkZ = fakePlayerChunkZ + dz;

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withX(fakeChunkX).withZ(fakeChunkZ));
    }


    public static void handlePacket(GeyserConnection connection, Packet packet) {
        ClientSession networkSession = ((GeyserSession) connection).getDownstream().getSession();
        networkSession.getPacketHandlerExecutor().execute(() -> networkSession.callPacketReceived(packet));
    }

}
