/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.glfw.GLFW
 */
package minegame159.meteorclient.utils.misc;

import org.lwjgl.glfw.GLFW;

public final class CursorStyle
extends Enum<CursorStyle> {
    public static final /* synthetic */ /* enum */ CursorStyle Default;
    private static final /* synthetic */ CursorStyle[] $VALUES;
    public static final /* synthetic */ /* enum */ CursorStyle Type;
    public static final /* synthetic */ /* enum */ CursorStyle Click;
    private /* synthetic */ long cursor;
    private /* synthetic */ boolean created;

    static {
        Default = new CursorStyle();
        Click = new CursorStyle();
        Type = new CursorStyle();
        $VALUES = CursorStyle.$values();
    }

    public static CursorStyle valueOf(String llllllllllllllllIllIllIllllIllIl) {
        return Enum.valueOf(CursorStyle.class, llllllllllllllllIllIllIllllIllIl);
    }

    private static /* synthetic */ CursorStyle[] $values() {
        return new CursorStyle[]{Default, Click, Type};
    }

    public long getGlfwCursor() {
        CursorStyle llllllllllllllllIllIllIllllIIIll;
        if (!llllllllllllllllIllIllIllllIIIll.created) {
            switch (llllllllllllllllIllIllIllllIIIll) {
                case Click: {
                    llllllllllllllllIllIllIllllIIIll.cursor = GLFW.glfwCreateStandardCursor((int)221188);
                    break;
                }
                case Type: {
                    llllllllllllllllIllIllIllllIIIll.cursor = GLFW.glfwCreateStandardCursor((int)221186);
                }
            }
            llllllllllllllllIllIllIllllIIIll.created = true;
        }
        return llllllllllllllllIllIllIllllIIIll.cursor;
    }

    public static CursorStyle[] values() {
        return (CursorStyle[])$VALUES.clone();
    }

    private CursorStyle() {
        CursorStyle llllllllllllllllIllIllIllllIIlll;
    }
}

