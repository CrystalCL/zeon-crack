/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.render;

public class Render2DEvent {
    public /* synthetic */ float tickDelta;
    private static final /* synthetic */ Render2DEvent INSTANCE;
    public /* synthetic */ int screenHeight;
    public /* synthetic */ int screenWidth;

    public Render2DEvent() {
        Render2DEvent lllllllllllllllllIllIlIIlIllllIl;
    }

    public static Render2DEvent get(int lllllllllllllllllIllIlIIlIlllIIl, int lllllllllllllllllIllIlIIlIlllIII, float lllllllllllllllllIllIlIIlIllIlII) {
        Render2DEvent.INSTANCE.screenWidth = lllllllllllllllllIllIlIIlIlllIIl;
        Render2DEvent.INSTANCE.screenHeight = lllllllllllllllllIllIlIIlIlllIII;
        Render2DEvent.INSTANCE.tickDelta = lllllllllllllllllIllIlIIlIllIlII;
        return INSTANCE;
    }

    static {
        INSTANCE = new Render2DEvent();
    }
}

