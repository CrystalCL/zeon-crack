/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui;

public class GuiKeyEvents {
    public static int postKeyEvents = 0;

    public static void resetPostKeyEvents() {
        postKeyEvents = 0;
    }

    public static boolean canUseKeys() {
        return postKeyEvents <= 0;
    }

    public static void setPostKeyEvents(boolean bl) {
        postKeyEvents += bl ? 1 : -1;
    }
}

