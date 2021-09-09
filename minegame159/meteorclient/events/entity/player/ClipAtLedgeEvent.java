/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.entity.player;

public class ClipAtLedgeEvent {
    private static final /* synthetic */ ClipAtLedgeEvent INSTANCE;
    private /* synthetic */ boolean clip;
    private /* synthetic */ boolean set;

    public boolean isSet() {
        ClipAtLedgeEvent llllllllllllllllllIllIIlIIlIlllI;
        return llllllllllllllllllIllIIlIIlIlllI.set;
    }

    public void setClip(boolean llllllllllllllllllIllIIlIIllIIIl) {
        llllllllllllllllllIllIIlIIllIIlI.set = true;
        llllllllllllllllllIllIIlIIllIIlI.clip = llllllllllllllllllIllIIlIIllIIIl;
    }

    public boolean isClip() {
        ClipAtLedgeEvent llllllllllllllllllIllIIlIIlIllII;
        return llllllllllllllllllIllIIlIIlIllII.clip;
    }

    public static ClipAtLedgeEvent get() {
        INSTANCE.reset();
        return INSTANCE;
    }

    public void reset() {
        llllllllllllllllllIllIIlIIllIlll.set = false;
    }

    static {
        INSTANCE = new ClipAtLedgeEvent();
    }

    public ClipAtLedgeEvent() {
        ClipAtLedgeEvent llllllllllllllllllIllIIlIIlllIlI;
    }
}

