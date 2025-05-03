package org.geyser.extension.cordslice.Random;

import org.cloudburstmc.math.vector.*;

public class ChunkSlicer {
    private static final int CHUNK_SLICE_SIZE = (int) (PositionSlicer.SLICE_SIZE / 16);

    public static class SlicedChunk {
        Vector2i chunkIndex;
        Vector2i slice;

        public SlicedChunk(int x, int y, int sx, int sy) {
            chunkIndex = Vector2i.from(x, y);
            slice = Vector2i.from(sx, sy);
        }

        public Vector2i getChunkIndex() { return this.chunkIndex; }
        public Vector2i getSlice() { return this.slice; }

        public Vector2i getRealChunkIndex() {
            return Vector2i.from(slice.getX() * CHUNK_SLICE_SIZE + chunkIndex.getX(), slice.getY() * CHUNK_SLICE_SIZE + chunkIndex.getY());
        }

        public static Vector2i getRealPosition(Vector2i chunkIndex, Vector2i slice) {
            return Vector2i.from(slice.getX() * CHUNK_SLICE_SIZE + chunkIndex.getX(), slice.getY() * CHUNK_SLICE_SIZE + chunkIndex.getY());
        }
    }

    public static Vector2i getSlice(Vector2i pos) {
        int sliceX = (int) Math.floor((double) pos.getX() / CHUNK_SLICE_SIZE);
        int sliceY = (int) Math.floor((double) pos.getY() / CHUNK_SLICE_SIZE);

        return Vector2i.from(sliceX, sliceY);
    }

    public static SlicedChunk getSlicedPositionFromSlice(Vector2i pos, Vector2i sliced) {
        int localX = (pos.getX() - sliced.getX() * CHUNK_SLICE_SIZE);
        int localY = (pos.getY() - sliced.getY() * CHUNK_SLICE_SIZE);

        return new SlicedChunk(localX, localY, sliced.getX(), sliced.getY());
    }

    public static SlicedChunk getSlicedPosition(Vector2i pos) {
        Vector2i sliced = getSlice(pos);

        return getSlicedPositionFromSlice(pos, sliced);
    }


}
