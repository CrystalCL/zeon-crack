/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.world;

public class AmbientOcclusionEvent {
    private static final /* synthetic */ AmbientOcclusionEvent INSTANCE;
    public /* synthetic */ float lightLevel;

    public AmbientOcclusionEvent() {
        AmbientOcclusionEvent llIIIIIlIIlIll;
        llIIIIIlIIlIll.lightLevel = -1.0f;
    }

    public static AmbientOcclusionEvent get() {
        AmbientOcclusionEvent.INSTANCE.lightLevel = -1.0f;
        return INSTANCE;
    }

    static {
        INSTANCE = new AmbientOcclusionEvent();
    }
}

