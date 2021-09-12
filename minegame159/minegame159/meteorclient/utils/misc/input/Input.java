/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc.input;

import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.utils.misc.CursorStyle;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_304;
import net.minecraft.class_310;
import org.lwjgl.glfw.GLFW;

public class Input {
    private static CursorStyle lastCursorStyle;
    private static final boolean[] buttons;
    private static final boolean[] keys;

    static {
        keys = new boolean[512];
        buttons = new boolean[16];
        lastCursorStyle = CursorStyle.Default;
    }

    public static void setKeyState(class_304 class_3042, boolean bl) {
        Input.setKeyState(KeyBindingHelper.getBoundKeyOf((class_304)class_3042).method_1444(), bl);
    }

    public static boolean isKeyPressed(int n) {
        if (!GuiKeyEvents.canUseKeys()) {
            return false;
        }
        if (n == -1) {
            return false;
        }
        return n < keys.length && keys[n];
    }

    public static boolean isPressed(class_304 class_3042) {
        int n = KeyBindingHelper.getBoundKeyOf((class_304)class_3042).method_1444();
        return Input.isKeyPressed(n);
    }

    public static void setButtonState(int n, boolean bl) {
        if (n >= 0 && n < buttons.length) {
            Input.buttons[n] = bl;
        }
    }

    public static boolean isButtonPressed(int n) {
        if (n == -1) {
            return false;
        }
        return n < buttons.length && buttons[n];
    }

    public static void setCursorStyle(CursorStyle cursorStyle) {
        if (lastCursorStyle != cursorStyle) {
            GLFW.glfwSetCursor((long)class_310.method_1551().method_22683().method_4490(), (long)cursorStyle.getGlfwCursor());
            lastCursorStyle = cursorStyle;
        }
    }

    public static void setKeyState(int n, boolean bl) {
        if (n >= 0 && n < keys.length) {
            Input.keys[n] = bl;
        }
    }
}

