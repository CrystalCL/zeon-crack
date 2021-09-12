/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc.input;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_304;
import net.minecraft.class_3675;

public class KeyBinds {
    private static final String CATEGORY = "Meteor Client";
    public static class_304 OPEN_CLICK_GUI = new class_304("key.meteor-client.open-click-gui", class_3675.class_307.field_1668, 344, "Meteor Client");

    public static void Register() {
        KeyBindingHelper.registerKeyBinding((class_304)OPEN_CLICK_GUI);
    }
}

