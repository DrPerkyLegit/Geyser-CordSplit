package org.geyser.extension.cordslice.Random;

import org.cloudburstmc.math.vector.Vector3d;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

//big issue with negative numbers and slice scaling for some reason

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

    private static class NegativeResults {
        double value;
        boolean wasNegative;

        NegativeResults(double value, boolean wasNegative) {
            this.value = value;
            this.wasNegative = wasNegative;
        }
    }
    private static NegativeResults handleNegative(double value) {
        boolean isNegative = value < 0;
        double finalValue = value;
        if (isNegative) finalValue *= -1;

        return new NegativeResults(finalValue, isNegative);
    }

    public static Vector3i getSlice(Vector3d pos) {
        NegativeResults outputX = handleNegative(pos.getX());
        NegativeResults outputY = handleNegative(pos.getY());
        NegativeResults outputZ = handleNegative(pos.getZ());

        int sliceX = (int) Math.floor(outputX.value / SLICE_SIZE);
        int sliceY = (int) Math.floor(outputY.value / SLICE_SIZE);
        int sliceZ = (int) Math.floor(outputZ.value / SLICE_SIZE);

        if (outputX.wasNegative) sliceX *= -1;
        if (outputY.wasNegative) sliceY *= -1;
        if (outputZ.wasNegative) sliceZ *= -1;

        return Vector3i.from(sliceX, sliceY, sliceZ);
    }

    public static SlicedPosition getSlicedPositionFromSlice(Vector3d pos, Vector3i sliced) {
        double sliceOffsetX = pos.getX() - sliced.getX() * SLICE_SIZE;
        double sliceOffsetY = pos.getY() - sliced.getY() * SLICE_SIZE;
        double sliceOffsetZ = pos.getZ() - sliced.getZ() * SLICE_SIZE;

        return new SlicedPosition(sliceOffsetX, sliceOffsetY, sliceOffsetZ, sliced.getX(), sliced.getY(), sliced.getZ());
    }

    public static SlicedPosition getSlicedPosition(Vector3d pos) {
        Vector3i sliced = getSlice(pos);

        return getSlicedPositionFromSlice(pos, sliced);
    }


}

