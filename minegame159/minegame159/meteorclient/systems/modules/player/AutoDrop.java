/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1792;
import net.minecraft.class_465;

public class AutoDrop
extends Module {
    private final Setting<List<class_1792>> items;
    private final Setting<Boolean> excludeHotbar;
    private final SettingGroup sgGeneral;

    public AutoDrop() {
        super(Categories.Player, "auto-drop", "Automatically drops specified items.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.items = this.sgGeneral.add(new ItemListSetting.Builder().name("items").description("Items to drop.").defaultValue(new ArrayList<class_1792>(0)).build());
        this.excludeHotbar = this.sgGeneral.add(new BoolSetting.Builder().name("exclude-hotbar").description("Whether or not to drop items from your hotbar.").defaultValue(false).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        int n;
        if (this.mc.field_1755 instanceof class_465) {
            return;
        }
        int n2 = n = this.excludeHotbar.get() != false ? 9 : 0;
        while (n < this.mc.field_1724.field_7514.method_5439()) {
            if (this.items.get().contains(this.mc.field_1724.field_7514.method_5438(n).method_7909())) {
                InvUtils.drop().slot(n);
            }
            ++n;
            if (3 < 4) continue;
            return;
        }
    }
}

