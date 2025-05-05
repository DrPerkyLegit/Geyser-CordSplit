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
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.ClientSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundAddEntityPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket;
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

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withPosition(slicedPosition.getPosition()));
    }

    @EventHandler
    public void onClientboundSetChunkCacheCenterPacket(Event event, ClientboundSetChunkCacheCenterPacket packet) {
        Vector3d realPosition = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.POSITION);

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withChunkX((int)realPosition.getX() >> 4).withChunkZ((int)realPosition.getZ() >> 4));
    }

    @EventHandler
    public void onClientboundLevelChunkWithLightPacket(Event event, ClientboundLevelChunkWithLightPacket packet) {
        Vector3d realPosition = PositionTracker.getSlice(event.getConnection(), PositionTracker.SliceType.POSITION);
        PositionSlicer.SlicedPosition slicedPosition = PositionSlicer.getSlicedPosition(realPosition);

        Vector2i realChunkPos = Vector2i.from(packet.getX(), packet.getZ());

        int realPlayerChunkX = (int) realPosition.getX() >> 4;
        int realPlayerChunkZ = (int) realPosition.getZ() >> 4;

        // Calculate offset of this chunk relative to the player's real chunk position
        int dx = realChunkPos.getX() - realPlayerChunkX;
        int dz = realChunkPos.getY() - realPlayerChunkZ;

        // Apply that offset to the fake sliced chunk position
        int fakePlayerChunkX = (int) slicedPosition.getPosition().getX() >> 4;
        int fakePlayerChunkZ = (int) slicedPosition.getPosition().getZ() >> 4;

        int fakeChunkX = fakePlayerChunkX + dx;
        int fakeChunkZ = fakePlayerChunkZ + dz;

        //CordSliceExtension.staticLogger.info(String.format("Sending chunk at: Real=(%d,%d) → Fake=(%d,%d)%n", realChunkPos.getX(), realChunkPos.getY(), fakeChunkX, fakeChunkZ));

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withX(fakeChunkX).withZ(fakeChunkZ));

        /*ChunkSlicer.SlicedChunk slicedChunk = ChunkSlicer.getSlicedPosition(Vector2i.from(packet.getX(), packet.getZ()));
        PositionTracker.updateSlice(event.getConnection(), PositionTracker.SliceType.CHUNK, slicedChunk.getSlice().toVector3());

        event.setCanceled(true);
        handlePacket(event.getConnection(), packet.withX(slicedChunk.getChunkIndex().getX()).withZ(slicedChunk.getChunkIndex().getY()));*/
    }

    public static void handlePacket(GeyserConnection connection, Packet packet) {
        ClientSession networkSession = ((GeyserSession) connection).getDownstream().getSession();
        networkSession.getPacketHandlerExecutor().execute(() -> networkSession.callPacketReceived(packet));
    }

}
