/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import org.lwjgl.glfw.GLFW;

public final class CursorStyle
extends Enum<CursorStyle> {
    public static final /* enum */ CursorStyle Type;
    private boolean created;
    private static final CursorStyle[] $VALUES;
    public static final /* enum */ CursorStyle Default;
    private long cursor;
    public static final /* enum */ CursorStyle Click;

    static {
        Default = new CursorStyle();
        Click = new CursorStyle();
        Type = new CursorStyle();
        $VALUES = CursorStyle.$values();
    }

    public static CursorStyle valueOf(String string) {
        return Enum.valueOf(CursorStyle.class, string);
    }

    public static CursorStyle[] values() {
        return (CursorStyle[])$VALUES.clone();
    }

    private static CursorStyle[] $values() {
        return new CursorStyle[]{Default, Click, Type};
    }

    public long getGlfwCursor() {
        if (!this.created) {
            switch (1.$SwitchMap$minegame159$meteorclient$utils$misc$CursorStyle[this.ordinal()]) {
                case 1: {
                    this.cursor = GLFW.glfwCreateStandardCursor((int)221188);
                    break;
                }
                case 2: {
                    this.cursor = GLFW.glfwCreateStandardCursor((int)221186);
                }
            }
            this.created = true;
        }
        return this.cursor;
    }
}

