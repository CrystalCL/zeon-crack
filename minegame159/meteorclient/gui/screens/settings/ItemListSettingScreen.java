/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.util.registry.Registry
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
    protected boolean includeValue(Item lllllllllllllllllIlllIlllllIIIII) {
        ItemListSettingScreen lllllllllllllllllIlllIlllllIIIIl;
        Predicate<Item> lllllllllllllllllIlllIlllllIIIlI = ((ItemListSetting)lllllllllllllllllIlllIlllllIIIIl.setting).filter;
        if (lllllllllllllllllIlllIlllllIIIlI != null && !lllllllllllllllllIlllIlllllIIIlI.test(lllllllllllllllllIlllIlllllIIIII)) {
            return false;
        }
        return lllllllllllllllllIlllIlllllIIIII != Items.AIR;
    }

    @Override
    protected String getValueName(Item lllllllllllllllllIlllIllllIlIllI) {
        return Names.get(lllllllllllllllllIlllIllllIlIllI);
    }

    public ItemListSettingScreen(GuiTheme lllllllllllllllllIlllIlllllIlIIl, ItemListSetting lllllllllllllllllIlllIlllllIlIII) {
        super(lllllllllllllllllIlllIlllllIlIIl, "Select items", lllllllllllllllllIlllIlllllIlIII, Registry.ITEM);
        ItemListSettingScreen lllllllllllllllllIlllIlllllIllIl;
    }

    @Override
    protected WWidget getValueWidget(Item lllllllllllllllllIlllIllllIllIll) {
        ItemListSettingScreen lllllllllllllllllIlllIllllIlllII;
        return lllllllllllllllllIlllIllllIlllII.theme.itemWithLabel(lllllllllllllllllIlllIllllIllIll.getDefaultStack());
    }
}

