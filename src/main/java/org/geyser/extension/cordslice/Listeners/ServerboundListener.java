package org.geyser.extension.cordslice.Listeners;

import org.geyser.extension.cordslice.Events.EventListener;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.session.DownstreamSession;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.data.ProtocolState;
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundCustomQueryAnswerPacket;

public class ServerboundListener implements EventListener {
    public ServerboundListener(CordSliceExtension extension) {

    }




    public void sendPacket(GeyserConnection connection, Packet packet) {
        DownstreamSession downstreamSession = ((GeyserSession) connection).getDownstream();

        ProtocolState state = downstreamSession.getSession().getPacketProtocol().getOutboundState();
        if (state == ProtocolState.GAME || state == ProtocolState.CONFIGURATION || packet.getClass() == ServerboundCustomQueryAnswerPacket.class) {
            downstreamSession.sendPacket(packet);
            CordSliceExtension.staticLogger.info("attempting to send packet");
        }
    }

}
