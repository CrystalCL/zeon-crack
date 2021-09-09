/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.client.option.KeyBinding
 *  net.minecraft.client.util.InputUtil.Type
 */
package minegame159.meteorclient.utils.misc.input;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyBinds {
    private static final /* synthetic */ String CATEGORY;
    public static /* synthetic */ KeyBinding OPEN_CLICK_GUI;

    public static void Register() {
        KeyBindingHelper.registerKeyBinding((KeyBinding)OPEN_CLICK_GUI);
    }

    public KeyBinds() {
        KeyBinds llllllllllllllllIlIllIlIIIIIllll;
    }

    static {
        CATEGORY = "Meteor Client";
        OPEN_CLICK_GUI = new KeyBinding("key.meteor-client.open-click-gui", Type.KEYSYM, 344, "Meteor Client");
    }
}

