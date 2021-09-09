/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events;

import meteordevelopment.orbit.ICancellable;

public class Cancellable
implements ICancellable {
    private /* synthetic */ boolean cancelled;

    @Override
    public boolean isCancelled() {
        Cancellable llIIIlllIllIlll;
        return llIIIlllIllIlll.cancelled;
    }

    @Override
    public void setCancelled(boolean llIIIlllIlllIll) {
        llIIIlllIllllII.cancelled = llIIIlllIlllIll;
    }

    public Cancellable() {
        Cancellable llIIIllllIIIIII;
        llIIIllllIIIIII.cancelled = false;
    }
}

