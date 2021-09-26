/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHighlight
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> color;
    private final Setting<List<Item>> items;

    public ItemHighlight() {
        super(Categories.Render, "item-highlight", "Highlights selected items when in guis");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.items = this.sgGeneral.add(new ItemListSetting.Builder().name("items").description("Items to highlight.").defaultValue(new ArrayList<Item>()).build());
        this.color = this.sgGeneral.add(new ColorSetting.Builder().name("color").description("The color to highlight the items with.").defaultValue(new SettingColor(225, 25, 255, 50)).build());
    }

    public int getColor(ItemStack ItemStack2) {
        if (this.items.get().contains(ItemStack2.getItem()) && this.isActive()) {
            return this.color.get().getPacked();
        }
        return -1;
    }
}

