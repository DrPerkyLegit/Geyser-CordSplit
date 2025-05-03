package org.geyser.extension.cordslice.Random;

import org.cloudburstmc.math.vector.Vector3d;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

public class PositionSlicer {
    protected static final double SLICE_SIZE = 16384.0;

    public static class SlicedPosition {
        Vector3d position;
        Vector3i slice;

        public SlicedPosition(double x, double y, double z, int sx, int sy, int sz) {
            position = Vector3d.from(x, y, z);
            slice = Vector3i.from(sx, sy, sz);
        }

        public Vector3d getPosition() { return this.position; }
        public Vector3i getSlice() { return this.slice; }

        public Vector3d getRealPosition() {
            Vector3i slice = getSlice();
            Vector3d position = getPosition();

            return Vector3d.from(slice.getX() * SLICE_SIZE + position.getX(), slice.getY() * SLICE_SIZE + position.getY(), slice.getZ() * SLICE_SIZE + position.getZ());
        }

        public static Vector3d getRealPosition(Vector3d position, Vector3i slice) {
            return Vector3d.from(slice.getX() * SLICE_SIZE + position.getX(), slice.getY() * SLICE_SIZE + position.getY(), slice.getZ() * SLICE_SIZE + position.getZ());
        }
    }

    public static Vector3i getSlice(Vector3f pos) {
        int sliceX = (int) Math.floor(pos.getX() / SLICE_SIZE);
        int sliceY = (int) Math.floor(pos.getY() / SLICE_SIZE);
        int sliceZ = (int) Math.floor(pos.getZ() / SLICE_SIZE);

        return Vector3i.from(sliceX, sliceY, sliceZ);
    }

    public static SlicedPosition getSlicedPositionFromSlice(Vector3f pos, Vector3i sliced) {
        double localX = pos.getX() - (sliced.getX() * SLICE_SIZE);
        double localY = pos.getY() - (sliced.getY() * SLICE_SIZE);
        double localZ = pos.getZ() - (sliced.getZ() * SLICE_SIZE);

        return new SlicedPosition(localX, localY, localZ, sliced.getX(), sliced.getY(), sliced.getZ());
    }

    public static SlicedPosition getSlicedPosition(Vector3f pos) {
        Vector3i sliced = getSlice(pos);

        return getSlicedPositionFromSlice(pos, sliced);
    }


}

