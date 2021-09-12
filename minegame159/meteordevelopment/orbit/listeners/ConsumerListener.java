/*
 * Decompiled with CFR 0.151.
 */
package meteordevelopment.orbit.listeners;

import java.util.function.Consumer;
import meteordevelopment.orbit.listeners.IListener;

public class ConsumerListener<T>
implements IListener {
    private final int priority;
    private final Consumer<T> executor;
    private final Class<?> target;

    @Override
    public Class<?> getTarget() {
        return this.target;
    }

    public ConsumerListener(Class<?> clazz, int n, Consumer<T> consumer) {
        this.target = clazz;
        this.priority = n;
        this.executor = consumer;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void call(Object object) {
        this.executor.accept(object);
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public ConsumerListener(Class<?> clazz, Consumer<T> consumer) {
        this(clazz, 0, consumer);
    }
}

