/*
 * Decompiled with CFR 0.151.
 */
package meteordevelopment.orbit.listeners;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.listeners.IListener;

public class LambdaListener
implements IListener {
    private static Constructor<MethodHandles.Lookup> lookupConstructor;
    private final Class<?> target;
    private final boolean isStatic;
    private final int priority;
    private Consumer<Object> executor;

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    static {
        try {
            lookupConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public void call(Object object) {
        this.executor.accept(object);
    }

    public LambdaListener(Class<?> clazz, Object object, Method method) {
        this.target = method.getParameters()[0].getType();
        this.isStatic = Modifier.isStatic(method.getModifiers());
        this.priority = method.getAnnotation(EventHandler.class).priority();
        try {
            MethodType methodType;
            MethodHandle methodHandle;
            String string = method.getName();
            boolean bl = lookupConstructor.isAccessible();
            lookupConstructor.setAccessible(true);
            MethodHandles.Lookup lookup = lookupConstructor.newInstance(clazz);
            lookupConstructor.setAccessible(bl);
            MethodType methodType2 = MethodType.methodType(Void.TYPE, method.getParameters()[0].getType());
            if (this.isStatic) {
                methodHandle = lookup.findStatic(clazz, string, methodType2);
                methodType = MethodType.methodType(Consumer.class);
            } else {
                methodHandle = lookup.findVirtual(clazz, string, methodType2);
                methodType = MethodType.methodType(Consumer.class, clazz);
            }
            MethodHandle methodHandle2 = LambdaMetafactory.metafactory(lookup, "accept", methodType, MethodType.methodType(Void.TYPE, Object.class), methodHandle, methodType2).getTarget();
            this.executor = this.isStatic ? methodHandle2.invoke() : methodHandle2.invoke(object);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Class<?> getTarget() {
        return this.target;
    }
}

