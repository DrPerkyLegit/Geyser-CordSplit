package org.geyser.extension.cordslice.Events;

import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.mcprotocollib.network.packet.Packet;

public class JavaPacketEvent extends Cancelable implements Event {
    private final Packet packet;
    private final GeyserConnection connection;

    public JavaPacketEvent(Packet packet, GeyserConnection connection) {
        this.packet = packet;
        this.connection = connection;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public GeyserConnection getConnection() {
        return this.connection;
    }
}
