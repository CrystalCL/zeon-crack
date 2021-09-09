/*
 * Decompiled with CFR 0.150.
 */
package meteordevelopment.orbit.listeners;

import java.util.function.Consumer;
import meteordevelopment.orbit.listeners.IListener;

public class ConsumerListener<T>
implements IListener {
    private final /* synthetic */ Consumer<T> executor;
    private final /* synthetic */ Class<?> target;
    private final /* synthetic */ int priority;

    public ConsumerListener(Class<?> lllIllllIIIIlII, int lllIllllIIIIIll, Consumer<T> lllIllllIIIIIlI) {
        ConsumerListener lllIllllIIIIlIl;
        lllIllllIIIIlIl.target = lllIllllIIIIlII;
        lllIllllIIIIlIl.priority = lllIllllIIIIIll;
        lllIllllIIIIlIl.executor = lllIllllIIIIIlI;
    }

    @Override
    public Class<?> getTarget() {
        ConsumerListener lllIlllIlllIIIl;
        return lllIlllIlllIIIl.target;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    public ConsumerListener(Class<?> lllIlllIlllllIl, Consumer<T> lllIlllIlllllII) {
        lllIlllIllllllI(lllIlllIlllllIl, 0, lllIlllIlllllII);
        ConsumerListener<T> lllIlllIllllllI;
    }

    @Override
    public void call(Object lllIlllIlllIIll) {
        ConsumerListener lllIlllIlllIlII;
        lllIlllIlllIlII.executor.accept(lllIlllIlllIIll);
    }

    @Override
    public int getPriority() {
        ConsumerListener lllIlllIllIllIl;
        return lllIlllIllIllIl.priority;
    }
}

