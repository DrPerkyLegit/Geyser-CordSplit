package org.geyser.extension.cordslice;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.geyser.extension.cordslice.Events.EventManager;
import org.geyser.extension.cordslice.Events.JavaPacketEvent;
import org.geyser.extension.cordslice.Listeners.ServerboundListener;
import org.geyser.extension.cordslice.Listeners.ClientboundListener;
import org.geyser.extension.cordslice.Random.SliceTracker;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent;
import org.geysermc.geyser.api.event.bedrock.SessionJoinEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.extension.ExtensionLogger;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;

/**
 * The main class of your extension - must implement extension, and be in the extension.yml file.
 * See {@link Extension} for available methods - for example to get the path to the configuration folder.
 */
public class CordSliceExtension implements Extension {
    public static final AttributeKey<GeyserSession> SESSION_KEY = AttributeKey.valueOf("CordSlice-SessionKey");

    public static ExtensionLogger staticLogger;

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        staticLogger = this.logger();
        staticLogger.info("Loading %s...".formatted(this.description().name()));

        EventManager.register(new ClientboundListener(this));
        EventManager.register(new ServerboundListener(this));
    }

    @Subscribe
    public void onPlayerDisconnect(SessionDisconnectEvent event) {
        SliceTracker.remove(event.connection());
    }

    @Subscribe
    public void onPlayerJoin(SessionJoinEvent event) {//we dont need to handle packets before the join state
        Channel channel = ((GeyserSession) event.connection()).getDownstream().getSession().getChannel();
        channel.attr(SESSION_KEY).set((GeyserSession) event.connection());

        //all channels share the same pipeline (i think) so dont create more than 1 netty handler, im new to using direct netty for stuff
        if (channel.pipeline().get("CordSlide-PacketHandler") == null) {
            channel.pipeline().addBefore("manager", "CordSlide-PacketHandler", new ChannelDuplexHandler() {
                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    if (msg instanceof Packet packet) {
                        GeyserSession session = ctx.channel().attr(CordSliceExtension.SESSION_KEY).get();

                        JavaPacketEvent packetEvent = new JavaPacketEvent(packet, session);
                        EventManager.call(packetEvent);

                        if (packetEvent.isCanceled()) return;
                    }
                    super.channelRead(ctx, msg);
                }

                //clientbound packets are here, need to add event lister setup to it like the "channelRead" function
                @Override
                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                    super.write(ctx, msg, promise);
                }
            });
        }


    }
}
