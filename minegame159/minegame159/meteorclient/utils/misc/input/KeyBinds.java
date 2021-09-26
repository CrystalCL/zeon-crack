/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc.input;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyBinds {
    private static final String CATEGORY = "Meteor Client";
    public static KeyBinding OPEN_CLICK_GUI = new KeyBinding("key.meteor-client.open-click-gui", InputUtil.class_307.KEYSYM, 344, "Meteor Client");

    public static void Register() {
        KeyBindingHelper.registerKeyBinding((KeyBinding)OPEN_CLICK_GUI);
    }
}

