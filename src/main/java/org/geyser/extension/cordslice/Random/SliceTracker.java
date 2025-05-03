package org.geyser.extension.cordslice.Random;

import org.cloudburstmc.math.vector.Vector3i;
import org.geysermc.geyser.api.connection.GeyserConnection;

import java.util.HashMap;
import java.util.Map;

public class SliceTracker {
    private static final Map<GeyserConnection, Vector3i> sliceMap = new HashMap<>();

    public static void remove(GeyserConnection connection) {
        sliceMap.remove(connection);
    }

    public static void updateSlice(GeyserConnection connection, Vector3i slice) {
        sliceMap.put(connection, slice);
    }

    public static Vector3i getSlice(GeyserConnection connection) {
        if (sliceMap.containsKey(connection)) return sliceMap.get(connection);

        return Vector3i.ONE;
    }


}
