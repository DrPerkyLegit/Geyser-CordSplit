package org.geyser.extension.cordslice.Listeners;

import org.geyser.extension.cordslice.Events.EventHandler;
import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.Events.JavaPacketEvent;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.ClientSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundAddEntityPacket;

public class ClientboundListener implements EventListener {

    public ClientboundListener(CordSliceExtension extension) {

    }

    @EventHandler
    public void setClientboundAddEntityPacket(JavaPacketEvent event, ClientboundAddEntityPacket packet) {
        CordSliceExtension.staticLogger.info("AddEntityPacket: " + packet.getClass().getName());
    }


    public void handlePacket(GeyserConnection connection, Packet packet) {
        ClientSession networkSession = ((GeyserSession) connection).getDownstream().getSession();
        if (packet.shouldRunOnGameThread()) {
            networkSession.getPacketHandlerExecutor().execute(() -> networkSession.callPacketReceived(packet));
        } else {
            networkSession.callPacketReceived(packet);
        }
    }

}
