/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.function.Predicate;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class ItemListSettingScreen
extends LeftRightListSettingScreen<Item> {
    @Override
    protected String getValueName(Object object) {
        return this.getValueName((Item)object);
    }

    @Override
    protected String getValueName(Item Item2) {
        return Names.get(Item2);
    }

    public ItemListSettingScreen(GuiTheme guiTheme, ItemListSetting itemListSetting) {
        super(guiTheme, "Select items", itemListSetting, Registry.ITEM);
    }

    @Override
    protected boolean includeValue(Object object) {
        return this.includeValue((Item)object);
    }

    @Override
    protected WWidget getValueWidget(Item Item2) {
        return this.theme.itemWithLabel(Item2.getDefaultStack());
    }

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((Item)object);
    }

    @Override
    protected boolean includeValue(Item Item2) {
        Predicate<Item> predicate = ((ItemListSetting)this.setting).filter;
        if (predicate != null && !predicate.test(Item2)) {
            return false;
        }
        return Item2 != Items.AIR;
    }
}

