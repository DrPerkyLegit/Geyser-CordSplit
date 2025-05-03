package org.geyser.extension.cordslice.Events;

public abstract class Cancelable {
    private boolean isCanceled = false;

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }
}
