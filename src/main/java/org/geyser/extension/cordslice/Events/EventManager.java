package org.geyser.extension.cordslice.Events;

import org.geysermc.mcprotocollib.network.packet.Packet;

import java.lang.reflect.Method;
import java.util.*;

public class EventManager {
    private static final Map<Class<?>, List<ListenerMethod>> listeners = new HashMap<>();

    public static void register(Object listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class<?>[] params = method.getParameterTypes();

                if (params.length == 1 && Event.class.isAssignableFrom(params[0])) {
                    listeners.computeIfAbsent(params[0], k -> new ArrayList<>())
                            .add(new ListenerMethod(listener, method));

                } else if (params.length == 2 &&
                        Event.class.isAssignableFrom(params[0]) &&
                        Packet.class.isAssignableFrom(params[1]) &&
                        !params[1].equals(Packet.class)) {
                    listeners.computeIfAbsent(params[0], k -> new ArrayList<>())
                            .add(new ListenerMethod(listener, method));
                }
            }
        }
    }

    public static void unregister(Object listener) {
        for (List<ListenerMethod> list : listeners.values()) {
            list.removeIf(lm -> lm.target == listener);
        }
    }

    public static void call(Event event) {
        List<ListenerMethod> list = listeners.get(event.getClass());
        if (list != null) {
            for (ListenerMethod lm : new ArrayList<>(list)) {
                try {
                    Class<?>[] paramTypes = lm.method.getParameterTypes();

                    if (paramTypes.length == 1 && paramTypes[0].equals(event.getClass())) {
                        lm.method.invoke(lm.target, event);

                    } else if (paramTypes.length == 2 && paramTypes[0].equals(event.getClass())) {
                        Packet packet = event instanceof JavaPacketEvent pe ? pe.getPacket() : null;

                        if (packet != null &&
                                paramTypes[1].isAssignableFrom(packet.getClass()) &&
                                !paramTypes[1].equals(Packet.class)) {
                            lm.method.invoke(lm.target, event, packet);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private record ListenerMethod(Object target, Method method) {}
}



