package org.geyser.extension.cordslice.Events;

import org.geyser.extension.cordslice.CordSliceExtension;
import org.geysermc.mcprotocollib.network.packet.Packet;

import java.lang.reflect.Method;
import java.util.*;

public class EventManager {
    private static final Map<Class<?>, List<ListenerMethod>> listeners = new HashMap<>();
    private static boolean debugMode = false;

    public static void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    public static void register(Object listener) {
        if (debugMode) CordSliceExtension.staticLogger.info("Registered Event Listener: " + listener.getClass().getSimpleName());
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class<?>[] params = method.getParameterTypes();

                if (params.length == 1 && Event.class.isAssignableFrom(params[0])) {
                    if (debugMode) CordSliceExtension.staticLogger.info("Registered Basic Handler: " + method.getName());
                    listeners.computeIfAbsent(params[0], k -> new ArrayList<>())
                            .add(new ListenerMethod(listener, method));

                } else if (params.length == 2 &&
                        Event.class.isAssignableFrom(params[0]) &&
                        Packet.class.isAssignableFrom(params[1]) &&
                        !params[1].equals(Packet.class)) {
                    if (debugMode) CordSliceExtension.staticLogger.info("Registered Advance Handler: " + method.getName());
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
                        if (debugMode) CordSliceExtension.staticLogger.info("Invoked Basic Handler: " + lm.method.getName());
                        lm.method.invoke(lm.target, event);

                    } else if (paramTypes.length == 2 && paramTypes[0].equals(event.getClass())) {
                        Packet packet = event.getPacket();

                        if (packet != null &&
                                paramTypes[1].isAssignableFrom(packet.getClass()) &&
                                !paramTypes[1].equals(Packet.class)) {
                            if (debugMode) CordSliceExtension.staticLogger.info("Invoked Advance Handler: " + lm.method.getName());
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



