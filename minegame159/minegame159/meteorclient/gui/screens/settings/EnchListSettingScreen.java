/*
 * Decompiled with CFR 0.151.
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
    public EnchListSettingScreen(GuiTheme guiTheme, Setting<List<Enchantment>> setting) {
        super(guiTheme, "Select items", setting, Registry.ENCHANTMENT);
    }

    @Override
    protected String getValueName(Enchantment Enchantment2) {
        return Names.get(Enchantment2);
    }

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((Enchantment)object);
    }

    @Override
    protected WWidget getValueWidget(Enchantment Enchantment2) {
        return this.theme.label(this.getValueName(Enchantment2));
    }

    @Override
    protected String getValueName(Object object) {
        return this.getValueName((Enchantment)object);
    }
}

