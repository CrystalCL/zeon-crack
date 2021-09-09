/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.render;

public class GetFovEvent {
    private static final /* synthetic */ GetFovEvent INSTANCE;
    public /* synthetic */ double fov;

    static {
        INSTANCE = new GetFovEvent();
    }

    public GetFovEvent() {
        GetFovEvent llllllllllllllllllllIIIlllllllIl;
    }

    public static GetFovEvent get(double llllllllllllllllllllIIIllllllIlI) {
        GetFovEvent.INSTANCE.fov = llllllllllllllllllllIIIllllllIlI;
        return INSTANCE;
    }
}

