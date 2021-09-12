/*
 * Decompiled with CFR 0.151.
 */
package meteordevelopment.orbit;

public interface ICancellable {
    public boolean isCancelled();

    default public void cancel() {
        this.setCancelled(true);
    }

    public void setCancelled(boolean var1);
}

