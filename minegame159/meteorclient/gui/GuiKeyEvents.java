/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui;

public class GuiKeyEvents {
    public static /* synthetic */ int postKeyEvents;

    public GuiKeyEvents() {
        GuiKeyEvents lllIlIIIIllIlIl;
    }

    public static boolean canUseKeys() {
        return postKeyEvents <= 0;
    }

    static {
        postKeyEvents = 0;
    }

    public static void resetPostKeyEvents() {
        postKeyEvents = 0;
    }

    public static void setPostKeyEvents(boolean lllIlIIIIllIIlI) {
        postKeyEvents += lllIlIIIIllIIlI ? 1 : -1;
    }
}

