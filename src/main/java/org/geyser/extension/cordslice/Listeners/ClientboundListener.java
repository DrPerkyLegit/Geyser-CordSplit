package org.geyser.extension.cordslice.Listeners;

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.geyser.extension.cordslice.Events.EventHandler;
import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.Events.JavaPacketEvent;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.session.GeyserSession;

public class ClientboundListener implements EventListener {

    public ClientboundListener(CordSliceExtension extension) {

    }

    @EventHandler
    public void onJavaPacketEvent(JavaPacketEvent event) {



    }

    public void sendPacket(GeyserConnection connection, BedrockPacket packet) {
        ((GeyserSession) connection).getUpstream().getSession().getPacketHandler().handlePacket(packet);
    }

}
