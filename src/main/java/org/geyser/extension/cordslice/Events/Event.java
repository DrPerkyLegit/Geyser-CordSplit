package org.geyser.extension.cordslice.Events;

import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.mcprotocollib.network.packet.Packet;

public class Event {
    private final Packet packet;
    private final GeyserConnection connection;
    private boolean isCanceled = false;

    public Event(Packet packet, GeyserConnection connection) {
        this.packet = packet;
        this.connection = connection;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public GeyserConnection getConnection() {
        return this.connection;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void call() { EventManager.call(this); }
}
