/*
 * Decompiled with CFR 0.151.
 */
package meteordevelopment.orbit.listeners;

public interface IListener {
    public boolean isStatic();

    public int getPriority();

    public Class<?> getTarget();

    public void call(Object var1);
}

