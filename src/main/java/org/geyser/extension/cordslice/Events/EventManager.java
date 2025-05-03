package org.geyser.extension.cordslice.Events;

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
            for (ListenerMethod lm : new ArrayList<>(list)) { // avoid concurrent modification
                try {
                    Class<?> paramType = lm.method.getParameterTypes()[0];

                    if (paramType.equals(event.getClass())) {
                        lm.method.invoke(lm.target, event);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private record ListenerMethod(Object target, Method method) {}
}

