/*
 * Decompiled with CFR 0.150.
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
    private /* synthetic */ Consumer<Object> executor;
    private static /* synthetic */ Constructor<MethodHandles.Lookup> lookupConstructor;
    private final /* synthetic */ int priority;
    private final /* synthetic */ Class<?> target;
    private final /* synthetic */ boolean isStatic;

    public LambdaListener(Class<?> llIlIlllllllIII, Object llIlIlllllllIll, Method llIlIllllllIllI) {
        LambdaListener llIlIllllllllIl;
        llIlIllllllllIl.target = llIlIllllllIllI.getParameters()[0].getType();
        llIlIllllllllIl.isStatic = Modifier.isStatic(llIlIllllllIllI.getModifiers());
        llIlIllllllllIl.priority = llIlIllllllIllI.getAnnotation(EventHandler.class).priority();
        try {
            MethodType llIllIIIIIIIIII;
            MethodHandle llIllIIIIIIIIIl;
            String llIllIIIIIIIlIl = llIlIllllllIllI.getName();
            boolean llIllIIIIIIIlII = lookupConstructor.isAccessible();
            lookupConstructor.setAccessible(true);
            MethodHandles.Lookup llIllIIIIIIIIll = lookupConstructor.newInstance(llIlIlllllllIII);
            lookupConstructor.setAccessible(llIllIIIIIIIlII);
            MethodType llIllIIIIIIIIlI = MethodType.methodType(Void.TYPE, llIlIllllllIllI.getParameters()[0].getType());
            if (llIlIllllllllIl.isStatic) {
                MethodHandle llIllIIIIIIIlll = llIllIIIIIIIIll.findStatic(llIlIlllllllIII, llIllIIIIIIIlIl, llIllIIIIIIIIlI);
                MethodType llIllIIIIIIIllI = MethodType.methodType(Consumer.class);
            } else {
                llIllIIIIIIIIIl = llIllIIIIIIIIll.findVirtual(llIlIlllllllIII, llIllIIIIIIIlIl, llIllIIIIIIIIlI);
                llIllIIIIIIIIII = MethodType.methodType(Consumer.class, llIlIlllllllIII);
            }
            MethodHandle llIlIllllllllll = LambdaMetafactory.metafactory(llIllIIIIIIIIll, "accept", llIllIIIIIIIIII, MethodType.methodType(Void.TYPE, Object.class), llIllIIIIIIIIIl, llIllIIIIIIIIlI).getTarget();
            llIlIllllllllIl.executor = llIlIllllllllIl.isStatic ? llIlIllllllllll.invoke() : llIlIllllllllll.invoke(llIlIlllllllIll);
        }
        catch (Throwable llIlIlllllllllI) {
            llIlIlllllllllI.printStackTrace();
        }
    }

    @Override
    public boolean isStatic() {
        LambdaListener llIlIlllllIIIIl;
        return llIlIlllllIIIIl.isStatic;
    }

    @Override
    public void call(Object llIlIlllllIlIIl) {
        LambdaListener llIlIlllllIlIlI;
        llIlIlllllIlIlI.executor.accept(llIlIlllllIlIIl);
    }

    @Override
    public int getPriority() {
        LambdaListener llIlIlllllIIIll;
        return llIlIlllllIIIll.priority;
    }

    static {
        try {
            lookupConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
        }
        catch (NoSuchMethodException llIlIllllIllllI) {
            llIlIllllIllllI.printStackTrace();
        }
    }

    @Override
    public Class<?> getTarget() {
        LambdaListener llIlIlllllIIllI;
        return llIlIlllllIIllI.target;
    }
}

