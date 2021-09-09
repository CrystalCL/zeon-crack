/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.util.registry.Registry
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class EnchListSettingScreen
extends LeftRightListSettingScreen<Enchantment> {
    @Override
    protected WWidget getValueWidget(Enchantment lllllllllllllllllIIIllIlIlIllllI) {
        EnchListSettingScreen lllllllllllllllllIIIllIlIllIIIIl;
        return lllllllllllllllllIIIllIlIllIIIIl.theme.label(lllllllllllllllllIIIllIlIllIIIIl.getValueName(lllllllllllllllllIIIllIlIlIllllI));
    }

    public EnchListSettingScreen(GuiTheme lllllllllllllllllIIIllIlIllIlIII, Setting<List<Enchantment>> lllllllllllllllllIIIllIlIllIIlll) {
        super(lllllllllllllllllIIIllIlIllIlIII, "Select items", lllllllllllllllllIIIllIlIllIIlll, Registry.ENCHANTMENT);
        EnchListSettingScreen lllllllllllllllllIIIllIlIllIlIIl;
    }

    @Override
    protected String getValueName(Enchantment lllllllllllllllllIIIllIlIlIllIlI) {
        return Names.get(lllllllllllllllllIIIllIlIlIllIlI);
    }
}

