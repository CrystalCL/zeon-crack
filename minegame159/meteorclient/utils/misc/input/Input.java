/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.client.option.KeyBinding
 *  net.minecraft.client.MinecraftClient
 *  org.lwjgl.glfw.GLFW
 */
package minegame159.meteorclient.utils.misc.input;

import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.utils.misc.CursorStyle;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class Input {
    private static final /* synthetic */ boolean[] keys;
    private static final /* synthetic */ boolean[] buttons;
    private static /* synthetic */ CursorStyle lastCursorStyle;

    public static void setKeyState(int llllllllllllllllIlIlIllllllIIlII, boolean llllllllllllllllIlIlIllllllIIlIl) {
        if (llllllllllllllllIlIlIllllllIIlII >= 0 && llllllllllllllllIlIlIllllllIIlII < keys.length) {
            Input.keys[llllllllllllllllIlIlIllllllIIlII] = llllllllllllllllIlIlIllllllIIlIl;
        }
    }

    public static boolean isPressed(KeyBinding llllllllllllllllIlIlIlllllIlIIlI) {
        int llllllllllllllllIlIlIlllllIlIIll = KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIlllllIlIIlI).getCode();
        return Input.isKeyPressed(llllllllllllllllIlIlIlllllIlIIll);
    }

    public static void setCursorStyle(CursorStyle llllllllllllllllIlIlIlllllIIlIII) {
        if (lastCursorStyle != llllllllllllllllIlIlIlllllIIlIII) {
            GLFW.glfwSetCursor((long)MinecraftClient.getInstance().getWindow().getHandle(), (long)llllllllllllllllIlIlIlllllIIlIII.getGlfwCursor());
            lastCursorStyle = llllllllllllllllIlIlIlllllIIlIII;
        }
    }

    static {
        keys = new boolean[512];
        buttons = new boolean[16];
        lastCursorStyle = CursorStyle.Default;
    }

    public Input() {
        Input llllllllllllllllIlIlIllllllIlIlI;
    }

    public static boolean isButtonPressed(int llllllllllllllllIlIlIlllllIIllII) {
        if (llllllllllllllllIlIlIlllllIIllII == -1) {
            return false;
        }
        return llllllllllllllllIlIlIlllllIIllII < buttons.length && buttons[llllllllllllllllIlIlIlllllIIllII];
    }

    public static void setButtonState(int llllllllllllllllIlIlIlllllIllllI, boolean llllllllllllllllIlIlIlllllIlllll) {
        if (llllllllllllllllIlIlIlllllIllllI >= 0 && llllllllllllllllIlIlIlllllIllllI < buttons.length) {
            Input.buttons[llllllllllllllllIlIlIlllllIllllI] = llllllllllllllllIlIlIlllllIlllll;
        }
    }

    public static void setKeyState(KeyBinding llllllllllllllllIlIlIlllllIllIII, boolean llllllllllllllllIlIlIlllllIllIIl) {
        Input.setKeyState(KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIlllllIllIII).getCode(), llllllllllllllllIlIlIlllllIllIIl);
    }

    public static boolean isKeyPressed(int llllllllllllllllIlIlIlllllIIlllI) {
        if (!GuiKeyEvents.canUseKeys()) {
            return false;
        }
        if (llllllllllllllllIlIlIlllllIIlllI == -1) {
            return false;
        }
        return llllllllllllllllIlIlIlllllIIlllI < keys.length && keys[llllllllllllllllIlIlIlllllIIlllI];
    }
}

