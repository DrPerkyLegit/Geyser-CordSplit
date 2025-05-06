package org.geyser.extension.cordslice;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.cloudburstmc.math.vector.Vector3d;
import org.geyser.extension.cordslice.Events.Event;
import org.geyser.extension.cordslice.Events.EventManager;
import org.geyser.extension.cordslice.Listeners.ServerboundListener;
import org.geyser.extension.cordslice.Listeners.ClientboundListener;
import org.geyser.extension.cordslice.Random.PositionSlicer;
import org.geyser.extension.cordslice.Random.PositionTracker;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.extension.ExtensionLogger;
import org.geysermc.geyser.entity.type.player.PlayerEntity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;

import java.util.concurrent.CompletableFuture;

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

        EventManager.setDebugMode(true);
        EventManager.register(new ClientboundListener(this));
        EventManager.register(new ServerboundListener(this));
    }

    @Subscribe
    public void onPlayerDisconnect(SessionDisconnectEvent event) {
        PositionTracker.remove(event.connection());
    }

    @Subscribe
    public void onPlayerJoin(SessionLoginEvent event) {
        logger().info("SessionLoginEvent: Run");
        CompletableFuture.runAsync(() -> {
            try {
                // Slight delay to let Geyser finish initializing the session
                Thread.sleep(100); // 1 tick (adjust if needed)
            } catch (InterruptedException ignored) {}

            if (event.isCancelled()) {
                CordSliceExtension.staticLogger.info("SessionLoginEvent: Cancelled");
                return;
            }

            if (((GeyserSession) event.connection()).getDownstream() == null) return;
            if (((GeyserSession) event.connection()).getDownstream().getSession() == null) return;

            Channel channel = ((GeyserSession) event.connection()).getDownstream().getSession().getChannel();
            channel.attr(SESSION_KEY).set((GeyserSession) event.connection());

            PlayerEntity playerEntity = ((GeyserSession) event.connection()).getPlayerEntity();

            Vector3d playerPosition = playerEntity.getPosition().toDouble();

            PositionTracker.updateSlice(event.connection(), PositionTracker.SliceType.SLICE, PositionSlicer.getSlice(playerPosition).toDouble());
            PositionTracker.updateSlice(event.connection(), PositionTracker.SliceType.POSITION, playerPosition);

            //ClientboundListener.handlePacket(event.connection(), new ClientboundPlayerPositionPacket(992882, playerPosition, playerEntity.getMotion().toDouble(), playerEntity.getYaw(), playerEntity.getPitch(), new ArrayList<>()));

            //all channels share the same pipeline (i think) so dont create more than 1 netty handler, im new to using direct netty for stuff
            if (channel.pipeline().get("CordSlide-PacketHandler") == null) {
                CordSliceExtension.staticLogger.info("SessionLoginEvent: Created Handler");
                channel.pipeline().addBefore("manager", "CordSlide-PacketHandler", new ChannelDuplexHandler() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        if (msg instanceof Packet packet) {
                            GeyserSession session = ctx.channel().attr(CordSliceExtension.SESSION_KEY).get();

                            Event packetEvent = new Event(packet, session);
                            packetEvent.call();

                            if (packetEvent.isCanceled()) return;
                        }
                        super.channelRead(ctx, msg);
                    }

                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        if (msg instanceof Packet packet) {
                            if (ServerboundListener.outboundPackets.contains(packet)) {
                                ServerboundListener.outboundPackets.remove(packet);
                            } else {
                                GeyserSession session = ctx.channel().attr(CordSliceExtension.SESSION_KEY).get();

                                Event packetEvent = new Event(packet, session);
                                packetEvent.call();

                                if (packetEvent.isCanceled()) {
                                    promise.setSuccess();
                                    return;
                                }
                            }
                        }

                        super.write(ctx, msg, promise);
                    }
                });
            }
        });
    }
}
