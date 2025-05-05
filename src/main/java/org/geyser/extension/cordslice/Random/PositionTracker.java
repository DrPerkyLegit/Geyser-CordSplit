package org.geyser.extension.cordslice.Random;

import org.cloudburstmc.math.vector.Vector3d;
import org.cloudburstmc.math.vector.Vector3i;
import org.geysermc.geyser.api.connection.GeyserConnection;

import java.util.HashMap;
import java.util.Map;

public class PositionTracker {
    public enum SliceType {
        SLICE,
        POSITION
    }
    private static final Map<GeyserConnection, Map<SliceType, Vector3d>> sliceMap = new HashMap<>();

    public static void remove(GeyserConnection connection) {
        sliceMap.remove(connection);
    }

    public static void updateSlice(GeyserConnection connection, SliceType type, Vector3d slice) {
        Map<SliceType, Vector3d> allSlices;
        if (sliceMap.containsKey(connection)) allSlices = sliceMap.get(connection);
        else allSlices = new HashMap<>();

        allSlices.put(type, slice);
        sliceMap.put(connection, allSlices);
    }

    public static Vector3d getSlice(GeyserConnection connection, SliceType type) {
        if (sliceMap.containsKey(connection))  {
            Map<SliceType, Vector3d> allSlices = sliceMap.get(connection);

            if (allSlices.containsKey(type)) return allSlices.get(type);
        }

        return Vector3d.from(0, 0, 0);
    }


}
