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
import net.minecraft.item.Item;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class AutoDrop
extends Module {
    private final Setting<List<Item>> items;
    private final Setting<Boolean> excludeHotbar;
    private final SettingGroup sgGeneral;

    public AutoDrop() {
        super(Categories.Player, "auto-drop", "Automatically drops specified items.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.items = this.sgGeneral.add(new ItemListSetting.Builder().name("items").description("Items to drop.").defaultValue(new ArrayList<Item>(0)).build());
        this.excludeHotbar = this.sgGeneral.add(new BoolSetting.Builder().name("exclude-hotbar").description("Whether or not to drop items from your hotbar.").defaultValue(false).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        int n;
        if (this.mc.currentScreen instanceof HandledScreen) {
            return;
        }
        int n2 = n = this.excludeHotbar.get() != false ? 9 : 0;
        while (n < this.mc.player.inventory.size()) {
            if (this.items.get().contains(this.mc.player.inventory.getStack(n).getItem())) {
                InvUtils.drop().slot(n);
            }
            ++n;
            if (3 < 4) continue;
            return;
        }
    }
}

