/*
 * Decompiled with CFR 0.150.
 */
package meteordevelopment.orbit;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.ICancellable;
import meteordevelopment.orbit.IEventBus;
import meteordevelopment.orbit.listeners.IListener;
import meteordevelopment.orbit.listeners.LambdaListener;

public class EventBus
implements IEventBus {
    private final /* synthetic */ Map<Class<?>, List<IListener>> staticListenerCache;
    private final /* synthetic */ Map<Object, List<IListener>> listenerCache;
    private final /* synthetic */ Map<Class<?>, List<IListener>> listenerMap;

    @Override
    public void subscribe(Class<?> lllllllllllllllllIlllIlIIlIllIlI) {
        EventBus lllllllllllllllllIlllIlIIlIllIll;
        lllllllllllllllllIlllIlIIlIllIll.subscribe(lllllllllllllllllIlllIlIIlIllIll.getListeners(lllllllllllllllllIlllIlIIlIllIlI, null), true);
    }

    private void unsubscribe(IListener lllllllllllllllllIlllIlIIIIIlIll, boolean lllllllllllllllllIlllIlIIIIIIllI) {
        EventBus lllllllllllllllllIlllIlIIIIIlIII;
        List<IListener> lllllllllllllllllIlllIlIIIIIlIIl = lllllllllllllllllIlllIlIIIIIlIII.listenerMap.get(lllllllllllllllllIlllIlIIIIIlIll.getTarget());
        if (lllllllllllllllllIlllIlIIIIIlIIl != null) {
            if (lllllllllllllllllIlllIlIIIIIIllI) {
                if (lllllllllllllllllIlllIlIIIIIlIll.isStatic()) {
                    lllllllllllllllllIlllIlIIIIIlIIl.remove(lllllllllllllllllIlllIlIIIIIlIll);
                }
            } else {
                lllllllllllllllllIlllIlIIIIIlIIl.remove(lllllllllllllllllIlllIlIIIIIlIll);
            }
        }
    }

    private void unsubscribe(List<IListener> lllllllllllllllllIlllIlIIIIlIlll, boolean lllllllllllllllllIlllIlIIIIlIIll) {
        for (IListener lllllllllllllllllIlllIlIIIIllIIl : lllllllllllllllllIlllIlIIIIlIlll) {
            EventBus lllllllllllllllllIlllIlIIIIlIlIl;
            lllllllllllllllllIlllIlIIIIlIlIl.unsubscribe(lllllllllllllllllIlllIlIIIIllIIl, lllllllllllllllllIlllIlIIIIlIIll);
        }
    }

    @Override
    public void subscribe(Object lllllllllllllllllIlllIlIIllIIIII) {
        EventBus lllllllllllllllllIlllIlIIlIlllll;
        lllllllllllllllllIlllIlIIlIlllll.subscribe(lllllllllllllllllIlllIlIIlIlllll.getListeners(lllllllllllllllllIlllIlIIllIIIII.getClass(), lllllllllllllllllIlllIlIIllIIIII), false);
    }

    private void insert(List<IListener> lllllllllllllllllIlllIlIIIllIllI, IListener lllllllllllllllllIlllIlIIIllIIlI) {
        int lllllllllllllllllIlllIlIIIllIlII;
        for (lllllllllllllllllIlllIlIIIllIlII = 0; lllllllllllllllllIlllIlIIIllIlII < lllllllllllllllllIlllIlIIIllIllI.size() && lllllllllllllllllIlllIlIIIllIIlI.getPriority() <= lllllllllllllllllIlllIlIIIllIllI.get(lllllllllllllllllIlllIlIIIllIlII).getPriority(); ++lllllllllllllllllIlllIlIIIllIlII) {
        }
        lllllllllllllllllIlllIlIIIllIllI.add(lllllllllllllllllIlllIlIIIllIlII, lllllllllllllllllIlllIlIIIllIIlI);
    }

    @Override
    public <T> T post(T lllllllllllllllllIlllIlIIllllIII) {
        EventBus lllllllllllllllllIlllIlIIlllIllI;
        List<IListener> lllllllllllllllllIlllIlIIlllIlll = lllllllllllllllllIlllIlIIlllIllI.listenerMap.get(lllllllllllllllllIlllIlIIllllIII.getClass());
        if (lllllllllllllllllIlllIlIIlllIlll != null) {
            for (IListener lllllllllllllllllIlllIlIIllllIlI : lllllllllllllllllIlllIlIIlllIlll) {
                lllllllllllllllllIlllIlIIllllIlI.call(lllllllllllllllllIlllIlIIllllIII);
            }
        }
        return lllllllllllllllllIlllIlIIllllIII;
    }

    @Override
    public void unsubscribe(IListener lllllllllllllllllIlllIlIIIIlllll) {
        EventBus lllllllllllllllllIlllIlIIIlIIIII;
        lllllllllllllllllIlllIlIIIlIIIII.unsubscribe(lllllllllllllllllIlllIlIIIIlllll, false);
    }

    private void subscribe(List<IListener> lllllllllllllllllIlllIlIIlIIIlll, boolean lllllllllllllllllIlllIlIIlIIIllI) {
        for (IListener lllllllllllllllllIlllIlIIlIIllII : lllllllllllllllllIlllIlIIlIIIlll) {
            EventBus lllllllllllllllllIlllIlIIlIIlIll;
            lllllllllllllllllIlllIlIIlIIlIll.subscribe(lllllllllllllllllIlllIlIIlIIllII, lllllllllllllllllIlllIlIIlIIIllI);
        }
    }

    @Override
    public void unsubscribe(Class<?> lllllllllllllllllIlllIlIIIlIIlIl) {
        EventBus lllllllllllllllllIlllIlIIIlIlIII;
        lllllllllllllllllIlllIlIIIlIlIII.unsubscribe(lllllllllllllllllIlllIlIIIlIlIII.getListeners(lllllllllllllllllIlllIlIIIlIIlIl, null), true);
    }

    private List<IListener> getListeners(Class<?> lllllllllllllllllIlllIIlllllIlll, Object lllllllllllllllllIlllIIllllllIll) {
        EventBus lllllllllllllllllIlllIIllllllIII;
        Function<Object, List> lllllllllllllllllIlllIIllllllIlI = lllllllllllllllllIlllIIlllIIlIII -> {
            EventBus lllllllllllllllllIlllIIlllIIlIll;
            CopyOnWriteArrayList<IListener> lllllllllllllllllIlllIIlllIIIlll = new CopyOnWriteArrayList<IListener>();
            lllllllllllllllllIlllIIlllIIlIll.getListeners(lllllllllllllllllIlllIIlllIIIlll, lllllllllllllllllIlllIIlllllIlll, lllllllllllllllllIlllIIllllllIll);
            return lllllllllllllllllIlllIIlllIIIlll;
        };
        if (lllllllllllllllllIlllIIllllllIll == null) {
            return lllllllllllllllllIlllIIllllllIII.staticListenerCache.computeIfAbsent(lllllllllllllllllIlllIIlllllIlll, lllllllllllllllllIlllIIllllllIlI);
        }
        for (Object lllllllllllllllllIlllIIllllllllI : lllllllllllllllllIlllIIllllllIII.listenerCache.keySet()) {
            if (lllllllllllllllllIlllIIllllllllI != lllllllllllllllllIlllIIllllllIll) continue;
            return lllllllllllllllllIlllIIllllllIII.listenerCache.get(lllllllllllllllllIlllIIllllllIll);
        }
        List lllllllllllllllllIlllIIllllllIIl = lllllllllllllllllIlllIIllllllIlI.apply(lllllllllllllllllIlllIIllllllIll);
        lllllllllllllllllIlllIIllllllIII.listenerCache.put(lllllllllllllllllIlllIIllllllIll, lllllllllllllllllIlllIIllllllIIl);
        return lllllllllllllllllIlllIIllllllIIl;
    }

    @Override
    public void unsubscribe(Object lllllllllllllllllIlllIlIIIlIlIll) {
        EventBus lllllllllllllllllIlllIlIIIlIllII;
        lllllllllllllllllIlllIlIIIlIllII.unsubscribe(lllllllllllllllllIlllIlIIIlIllII.getListeners(lllllllllllllllllIlllIlIIIlIlIll.getClass(), lllllllllllllllllIlllIlIIIlIlIll), false);
    }

    private boolean isValid(Method lllllllllllllllllIlllIIlllIlIIll) {
        if (!lllllllllllllllllIlllIIlllIlIIll.isAnnotationPresent(EventHandler.class)) {
            return false;
        }
        if (lllllllllllllllllIlllIIlllIlIIll.getReturnType() != Void.TYPE) {
            return false;
        }
        if (lllllllllllllllllIlllIIlllIlIIll.getParameterCount() != 1) {
            return false;
        }
        return !lllllllllllllllllIlllIIlllIlIIll.getParameters()[0].getType().isPrimitive();
    }

    @Override
    public <T extends ICancellable> T post(T lllllllllllllllllIlllIlIIllIIlll) {
        EventBus lllllllllllllllllIlllIlIIllIlIll;
        List<IListener> lllllllllllllllllIlllIlIIllIlIIl = lllllllllllllllllIlllIlIIllIlIll.listenerMap.get(lllllllllllllllllIlllIlIIllIIlll.getClass());
        if (lllllllllllllllllIlllIlIIllIlIIl != null) {
            lllllllllllllllllIlllIlIIllIIlll.setCancelled(false);
            for (IListener lllllllllllllllllIlllIlIIllIllII : lllllllllllllllllIlllIlIIllIlIIl) {
                lllllllllllllllllIlllIlIIllIllII.call(lllllllllllllllllIlllIlIIllIIlll);
                if (!lllllllllllllllllIlllIlIIllIIlll.isCancelled()) continue;
                break;
            }
        }
        return lllllllllllllllllIlllIlIIllIIlll;
    }

    public EventBus() {
        EventBus lllllllllllllllllIlllIlIlIIIIIIl;
        lllllllllllllllllIlllIlIlIIIIIIl.listenerCache = new ConcurrentHashMap<Object, List<IListener>>();
        lllllllllllllllllIlllIlIlIIIIIIl.staticListenerCache = new ConcurrentHashMap();
        lllllllllllllllllIlllIlIlIIIIIIl.listenerMap = new ConcurrentHashMap();
    }

    @Override
    public void subscribe(IListener lllllllllllllllllIlllIlIIlIlIlII) {
        EventBus lllllllllllllllllIlllIlIIlIlIlIl;
        lllllllllllllllllIlllIlIIlIlIlIl.subscribe(lllllllllllllllllIlllIlIIlIlIlII, false);
    }

    private void subscribe(IListener lllllllllllllllllIlllIlIIIllllll, boolean lllllllllllllllllIlllIlIIIlllllI) {
        EventBus lllllllllllllllllIlllIlIIlIIIIII;
        if (lllllllllllllllllIlllIlIIIlllllI) {
            if (lllllllllllllllllIlllIlIIIllllll.isStatic()) {
                lllllllllllllllllIlllIlIIlIIIIII.insert(lllllllllllllllllIlllIlIIlIIIIII.listenerMap.computeIfAbsent(lllllllllllllllllIlllIlIIIllllll.getTarget(), lllllllllllllllllIlllIIllIlllllI -> new CopyOnWriteArrayList()), lllllllllllllllllIlllIlIIIllllll);
            }
        } else {
            lllllllllllllllllIlllIlIIlIIIIII.insert(lllllllllllllllllIlllIlIIlIIIIII.listenerMap.computeIfAbsent(lllllllllllllllllIlllIlIIIllllll.getTarget(), lllllllllllllllllIlllIIlllIIIIIl -> new CopyOnWriteArrayList()), lllllllllllllllllIlllIlIIIllllll);
        }
    }

    private void getListeners(List<IListener> lllllllllllllllllIlllIIllllIlIII, Class<?> lllllllllllllllllIlllIIllllIIlll, Object lllllllllllllllllIlllIIllllIIllI) {
        EventBus lllllllllllllllllIlllIIllllIIlIl;
        for (Method lllllllllllllllllIlllIIllllIlIlI : lllllllllllllllllIlllIIllllIIlll.getDeclaredMethods()) {
            if (!lllllllllllllllllIlllIIllllIIlIl.isValid(lllllllllllllllllIlllIIllllIlIlI)) continue;
            lllllllllllllllllIlllIIllllIlIII.add(new LambdaListener(lllllllllllllllllIlllIIllllIIlll, lllllllllllllllllIlllIIllllIIllI, lllllllllllllllllIlllIIllllIlIlI));
        }
        if (lllllllllllllllllIlllIIllllIIlll.getSuperclass() != null) {
            lllllllllllllllllIlllIIllllIIlIl.getListeners(lllllllllllllllllIlllIIllllIlIII, lllllllllllllllllIlllIIllllIIlll.getSuperclass(), lllllllllllllllllIlllIIllllIIllI);
        }
    }
}

