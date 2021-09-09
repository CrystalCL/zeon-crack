/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
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
    private final /* synthetic */ Setting<SettingColor> color;
    private final /* synthetic */ Setting<List<Item>> items;
    private final /* synthetic */ SettingGroup sgGeneral;

    public int getColor(ItemStack lllIlIIlIlIllII) {
        ItemHighlight lllIlIIlIlIllIl;
        if (lllIlIIlIlIllIl.items.get().contains((Object)lllIlIIlIlIllII.getItem()) && lllIlIIlIlIllIl.isActive()) {
            return lllIlIIlIlIllIl.color.get().getPacked();
        }
        return -1;
    }

    public ItemHighlight() {
        super(Categories.Render, "item-highlight", "Highlights selected items when in guis");
        ItemHighlight lllIlIIlIllIIIl;
        lllIlIIlIllIIIl.sgGeneral = lllIlIIlIllIIIl.settings.getDefaultGroup();
        lllIlIIlIllIIIl.items = lllIlIIlIllIIIl.sgGeneral.add(new ItemListSetting.Builder().name("items").description("Items to highlight.").defaultValue(new ArrayList<Item>()).build());
        lllIlIIlIllIIIl.color = lllIlIIlIllIIIl.sgGeneral.add(new ColorSetting.Builder().name("color").description("The color to highlight the items with.").defaultValue(new SettingColor(225, 25, 255, 50)).build());
    }
}

