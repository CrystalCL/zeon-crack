/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.screen.Screen
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.gui;

import java.util.HashMap;
import java.util.Map;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.screens.settings.BlockListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.BlockSettingScreen;
import minegame159.meteorclient.gui.screens.settings.ColorSettingScreen;
import minegame159.meteorclient.gui.screens.settings.EnchListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.EntityTypeListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.ItemListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.ModuleListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.PacketBoolSettingScreen;
import minegame159.meteorclient.gui.screens.settings.ParticleTypeListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.SoundEventListSettingScreen;
import minegame159.meteorclient.gui.screens.settings.StatusEffectSettingScreen;
import minegame159.meteorclient.gui.screens.settings.StorageBlockListSettingScreen;
import minegame159.meteorclient.gui.utils.SettingsWidgetFactory;
import minegame159.meteorclient.gui.widgets.WItem;
import minegame159.meteorclient.gui.widgets.WKeybind;
import minegame159.meteorclient.gui.widgets.WQuad;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.input.WDoubleEdit;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WIntEdit;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BlockSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.KeybindSetting;
import minegame159.meteorclient.settings.ModuleListSetting;
import minegame159.meteorclient.settings.PacketBoolSetting;
import minegame159.meteorclient.settings.ParticleTypeListSetting;
import minegame159.meteorclient.settings.PotionSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.settings.SoundEventListSetting;
import minegame159.meteorclient.settings.StatusEffectSetting;
import minegame159.meteorclient.settings.StorageBlockListSetting;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.StringUtils;

public class DefaultSettingsWidgetFactory
implements SettingsWidgetFactory {
    private final /* synthetic */ Map<Class<?>, Factory> factories;
    private final /* synthetic */ GuiTheme theme;

    private <T extends Enum<?>> void enumW(WTable lllllllllllllllllIIIIIllIllllllI, EnumSetting<T> lllllllllllllllllIIIIIllIlllllIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIlllllll;
        WDropdown<Enum> lllllllllllllllllIIIIIlllIIIIIII = lllllllllllllllllIIIIIllIllllllI.add(lllllllllllllllllIIIIIllIlllllll.theme.dropdown((Enum)lllllllllllllllllIIIIIllIlllllIl.get())).expandCellX().widget();
        lllllllllllllllllIIIIIlllIIIIIII.action = () -> lllllllllllllllllIIIIIllIlllllIl.set((Enum)lllllllllllllllllIIIIIlllIIIIIII.get());
        lllllllllllllllllIIIIIllIlllllll.reset(lllllllllllllllllIIIIIllIllllllI, lllllllllllllllllIIIIIllIlllllIl, () -> lllllllllllllllllIIIIIlllIIIIIII.set((Enum)lllllllllllllllllIIIIIllIlllllIl.get()));
    }

    private void boolW(WTable lllllllllllllllllIIIIIlllIlIIlIl, BoolSetting lllllllllllllllllIIIIIlllIlIlIII) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlllIlIlIlI;
        WCheckbox lllllllllllllllllIIIIIlllIlIIlll = lllllllllllllllllIIIIIlllIlIIlIl.add(lllllllllllllllllIIIIIlllIlIlIlI.theme.checkbox((Boolean)lllllllllllllllllIIIIIlllIlIlIII.get())).expandCellX().widget();
        lllllllllllllllllIIIIIlllIlIIlll.action = () -> lllllllllllllllllIIIIIlllIlIlIII.set(lllllllllllllllllIIIIIlIIIllIIlI.checked);
        lllllllllllllllllIIIIIlllIlIlIlI.reset(lllllllllllllllllIIIIIlllIlIIlIl, lllllllllllllllllIIIIIlllIlIlIII, () -> {
            lllllllllllllllllIIIIIlIIIlllIIl.checked = (Boolean)lllllllllllllllllIIIIIlllIlIlIII.get();
        });
    }

    private void blockW(WTable lllllllllllllllllIIIIIllIlIlIllI, BlockSetting lllllllllllllllllIIIIIllIlIlIlIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIlIlIlll;
        WHorizontalList lllllllllllllllllIIIIIllIlIlIlII = lllllllllllllllllIIIIIllIlIlIllI.add(lllllllllllllllllIIIIIllIlIlIlll.theme.horizontalList()).expandX().widget();
        WItem lllllllllllllllllIIIIIllIlIlIIll = lllllllllllllllllIIIIIllIlIlIlII.add(lllllllllllllllllIIIIIllIlIlIlll.theme.item(((Block)lllllllllllllllllIIIIIllIlIlIlIl.get()).asItem().getDefaultStack())).widget();
        WButton lllllllllllllllllIIIIIllIlIlIIlI = lllllllllllllllllIIIIIllIlIlIlII.add(lllllllllllllllllIIIIIllIlIlIlll.theme.button("Select")).widget();
        lllllllllllllllllIIIIIllIlIlIIlI.action = () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIlllllIl;
            Utils.mc.openScreen((Screen)new BlockSettingScreen(lllllllllllllllllIIIIIlIIlllllIl.theme, lllllllllllllllllIIIIIllIlIlIlIl));
        };
        lllllllllllllllllIIIIIllIlIlIlll.reset(lllllllllllllllllIIIIIllIlIlIllI, lllllllllllllllllIIIIIllIlIlIlIl, () -> lllllllllllllllllIIIIIllIlIlIIll.set(((Block)lllllllllllllllllIIIIIllIlIlIlIl.get()).asItem().getDefaultStack()));
    }

    private void entityTypeListW(WTable lllllllllllllllllIIIIIllIIlIlIIl, EntityTypeListSetting lllllllllllllllllIIIIIllIIlIIlIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIlIIlll;
        lllllllllllllllllIIIIIllIIlIIlll.selectW(lllllllllllllllllIIIIIllIIlIlIIl, lllllllllllllllllIIIIIllIIlIIlIl, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIIlIlIl;
            Utils.mc.openScreen((Screen)new EntityTypeListSettingScreen(lllllllllllllllllIIIIIlIlIIlIlIl.theme, lllllllllllllllllIIIIIllIIlIIlIl));
        });
    }

    private void keybindW(WTable lllllllllllllllllIIIIIllIlIIIllI, KeybindSetting lllllllllllllllllIIIIIllIlIIIlIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIlIIIIll;
        WKeybind lllllllllllllllllIIIIIllIlIIIlII = lllllllllllllllllIIIIIllIlIIIllI.add(lllllllllllllllllIIIIIllIlIIIIll.theme.keybind((Keybind)lllllllllllllllllIIIIIllIlIIIlIl.get(), true)).expandCellX().widget();
        lllllllllllllllllIIIIIllIlIIIlII.action = lllllllllllllllllIIIIIllIlIIIlIl::changed;
        lllllllllllllllllIIIIIllIlIIIlIl.widget = lllllllllllllllllIIIIIllIlIIIlII;
        lllllllllllllllllIIIIIllIlIIIIll.reset(lllllllllllllllllIIIIIllIlIIIllI, lllllllllllllllllIIIIIllIlIIIlIl, lllllllllllllllllIIIIIllIlIIIlII::reset);
    }

    private void storageBlockListW(WTable lllllllllllllllllIIIIIlIlllIlIlI, StorageBlockListSetting lllllllllllllllllIIIIIlIlllIlIIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlllIlIII;
        lllllllllllllllllIIIIIlIlllIlIII.selectW(lllllllllllllllllIIIIIlIlllIlIlI, lllllllllllllllllIIIIIlIlllIlIIl, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIllllIl;
            Utils.mc.openScreen((Screen)new StorageBlockListSettingScreen(lllllllllllllllllIIIIIlIlIllllIl.theme, lllllllllllllllllIIIIIlIlllIlIIl));
        });
    }

    private void doubleW(WTable lllllllllllllllllIIIIIlllIIIlIll, DoubleSetting lllllllllllllllllIIIIIlllIIIlIlI) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlllIIIllII;
        WDoubleEdit lllllllllllllllllIIIIIlllIIIlllI = lllllllllllllllllIIIIIlllIIIllII.theme.doubleEdit((Double)lllllllllllllllllIIIIIlllIIIlIlI.get(), lllllllllllllllllIIIIIlllIIIlIlI.getSliderMin(), lllllllllllllllllIIIIIlllIIIlIlI.getSliderMax());
        lllllllllllllllllIIIIIlllIIIlllI.min = lllllllllllllllllIIIIIlllIIIlIlI.min;
        lllllllllllllllllIIIIIlllIIIlllI.max = lllllllllllllllllIIIIIlllIIIlIlI.max;
        lllllllllllllllllIIIIIlllIIIlllI.decimalPlaces = lllllllllllllllllIIIIIlllIIIlIlI.decimalPlaces;
        lllllllllllllllllIIIIIlllIIIlIll.add(lllllllllllllllllIIIIIlllIIIlllI).expandX();
        Runnable lllllllllllllllllIIIIIlllIIIllIl = () -> {
            if (!lllllllllllllllllIIIIIlllIIIlIlI.set(lllllllllllllllllIIIIIlllIIIlllI.get())) {
                lllllllllllllllllIIIIIlllIIIlllI.set((Double)lllllllllllllllllIIIIIlllIIIlIlI.get());
            }
        };
        if (lllllllllllllllllIIIIIlllIIIlIlI.onSliderRelease) {
            lllllllllllllllllIIIIIlllIIIlllI.actionOnRelease = lllllllllllllllllIIIIIlllIIIllIl;
        } else {
            lllllllllllllllllIIIIIlllIIIlllI.action = lllllllllllllllllIIIIIlllIIIllIl;
        }
        lllllllllllllllllIIIIIlllIIIllII.reset(lllllllllllllllllIIIIIlllIIIlIll, lllllllllllllllllIIIIIlllIIIlIlI, () -> lllllllllllllllllIIIIIlllIIIlllI.set((Double)lllllllllllllllllIIIIIlllIIIlIlI.get()));
    }

    private void enchListW(WTable lllllllllllllllllIIIIIllIIIlllIl, EnchListSetting lllllllllllllllllIIIIIllIIIlllll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIIllllI;
        lllllllllllllllllIIIIIllIIIllllI.selectW(lllllllllllllllllIIIIIllIIIlllIl, lllllllllllllllllIIIIIllIIIlllll, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIIllIll;
            Utils.mc.openScreen((Screen)new EnchListSettingScreen(lllllllllllllllllIIIIIlIlIIllIll.theme, lllllllllllllllllIIIIIllIIIlllll));
        });
    }

    private void intW(WTable lllllllllllllllllIIIIIlllIIlllIl, IntSetting lllllllllllllllllIIIIIlllIIlllII) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlllIIllIlI;
        WIntEdit lllllllllllllllllIIIIIlllIIllIll = lllllllllllllllllIIIIIlllIIlllIl.add(lllllllllllllllllIIIIIlllIIllIlI.theme.intEdit((Integer)lllllllllllllllllIIIIIlllIIlllII.get(), lllllllllllllllllIIIIIlllIIlllII.getSliderMin(), lllllllllllllllllIIIIIlllIIlllII.getSliderMax())).expandX().widget();
        lllllllllllllllllIIIIIlllIIllIll.min = lllllllllllllllllIIIIIlllIIlllII.min;
        lllllllllllllllllIIIIIlllIIllIll.max = lllllllllllllllllIIIIIlllIIlllII.max;
        lllllllllllllllllIIIIIlllIIllIll.actionOnRelease = () -> {
            if (!lllllllllllllllllIIIIIlllIIlllII.set(lllllllllllllllllIIIIIlllIIllIll.get())) {
                lllllllllllllllllIIIIIlllIIllIll.set((Integer)lllllllllllllllllIIIIIlllIIlllII.get());
            }
        };
        lllllllllllllllllIIIIIlllIIllIlI.reset(lllllllllllllllllIIIIIlllIIlllIl, lllllllllllllllllIIIIIlllIIlllII, () -> lllllllllllllllllIIIIIlllIIllIll.set((Integer)lllllllllllllllllIIIIIlllIIlllII.get()));
    }

    private void reset(WTable lllllllllllllllllIIIIIlIllIlIIII, Setting<?> lllllllllllllllllIIIIIlIllIIlIlI, Runnable lllllllllllllllllIIIIIlIllIIlIIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIllIlIIIl;
        WButton lllllllllllllllllIIIIIlIllIIllIl = lllllllllllllllllIIIIIlIllIlIIII.add(lllllllllllllllllIIIIIlIllIlIIIl.theme.button(GuiRenderer.RESET)).widget();
        lllllllllllllllllIIIIIlIllIIllIl.action = () -> {
            lllllllllllllllllIIIIIlIllIIlIlI.reset();
            if (lllllllllllllllllIIIIIlIllIIlIIl != null) {
                lllllllllllllllllIIIIIlIllIIlIIl.run();
            }
        };
    }

    @Override
    public WWidget create(GuiTheme lllllllllllllllllIIIIIllllIIlllI, Settings lllllllllllllllllIIIIIllllIIllIl, String lllllllllllllllllIIIIIllllIlIIIl) {
        WVerticalList lllllllllllllllllIIIIIllllIlIIII = lllllllllllllllllIIIIIllllIIlllI.verticalList();
        for (SettingGroup lllllllllllllllllIIIIIllllIlIlIl : lllllllllllllllllIIIIIllllIIllIl.groups) {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllllIIllll;
            lllllllllllllllllIIIIIllllIIllll.group(lllllllllllllllllIIIIIllllIlIIII, lllllllllllllllllIIIIIllllIlIlIl, lllllllllllllllllIIIIIllllIlIIIl);
        }
        return lllllllllllllllllIIIIIllllIlIIII;
    }

    private void selectW(WTable lllllllllllllllllIIIIIlIllIlllll, Setting<?> lllllllllllllllllIIIIIlIllIllllI, Runnable lllllllllllllllllIIIIIlIllIlllIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlllIIIII;
        WButton lllllllllllllllllIIIIIlIllIlllII = lllllllllllllllllIIIIIlIllIlllll.add(lllllllllllllllllIIIIIlIlllIIIII.theme.button("Select")).expandCellX().widget();
        lllllllllllllllllIIIIIlIllIlllII.action = lllllllllllllllllIIIIIlIllIlllIl;
        lllllllllllllllllIIIIIlIlllIIIII.reset(lllllllllllllllllIIIIIlIllIlllll, lllllllllllllllllIIIIIlIllIllllI, null);
    }

    private void soundEventListW(WTable lllllllllllllllllIIIIIlIlllllIIl, SoundEventListSetting lllllllllllllllllIIIIIlIlllllIII) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlllllIlI;
        lllllllllllllllllIIIIIlIlllllIlI.selectW(lllllllllllllllllIIIIIlIlllllIIl, lllllllllllllllllIIIIIlIlllllIII, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIllIIll;
            Utils.mc.openScreen((Screen)new SoundEventListSettingScreen(lllllllllllllllllIIIIIlIlIllIIll.theme, lllllllllllllllllIIIIIlIlllllIII));
        });
    }

    private void blockListW(WTable lllllllllllllllllIIIIIllIIlllIll, BlockListSetting lllllllllllllllllIIIIIllIIllIlll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIllllII;
        lllllllllllllllllIIIIIllIIllllII.selectW(lllllllllllllllllIIIIIllIIlllIll, lllllllllllllllllIIIIIllIIllIlll, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIIIIlll;
            Utils.mc.openScreen((Screen)new BlockListSettingScreen(lllllllllllllllllIIIIIlIlIIIIlll.theme, lllllllllllllllllIIIIIllIIllIlll));
        });
    }

    public DefaultSettingsWidgetFactory(GuiTheme lllllllllllllllllIIIIIllllIlllll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlllllIIIII;
        lllllllllllllllllIIIIIlllllIIIII.factories = new HashMap();
        lllllllllllllllllIIIIIlllllIIIII.theme = lllllllllllllllllIIIIIllllIlllll;
        lllllllllllllllllIIIIIlllllIIIII.factories.put(BoolSetting.class, (lllllllllllllllllIIIIIIllIIIIlIl, lllllllllllllllllIIIIIIllIIIIlII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIIIIllI;
            lllllllllllllllllIIIIIIllIIIIllI.boolW(lllllllllllllllllIIIIIIllIIIIlIl, (BoolSetting)lllllllllllllllllIIIIIIllIIIIlII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(IntSetting.class, (lllllllllllllllllIIIIIIllIIIlllI, lllllllllllllllllIIIIIIllIIIllIl) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIIIllll;
            lllllllllllllllllIIIIIIllIIIllll.intW(lllllllllllllllllIIIIIIllIIIlllI, (IntSetting)lllllllllllllllllIIIIIIllIIIllIl);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(DoubleSetting.class, (lllllllllllllllllIIIIIIllIIlIlll, lllllllllllllllllIIIIIIllIIlIllI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIIlIlIl;
            lllllllllllllllllIIIIIIllIIlIlIl.doubleW(lllllllllllllllllIIIIIIllIIlIlll, (DoubleSetting)lllllllllllllllllIIIIIIllIIlIllI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(EnumSetting.class, (lllllllllllllllllIIIIIIllIIlllIl, lllllllllllllllllIIIIIIllIIlllll) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIlIIIIl;
            lllllllllllllllllIIIIIIllIlIIIIl.enumW(lllllllllllllllllIIIIIIllIIlllIl, (EnumSetting)lllllllllllllllllIIIIIIllIIlllll);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(PotionSetting.class, (lllllllllllllllllIIIIIIllIlIlIIl, lllllllllllllllllIIIIIIllIlIIlIl) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIlIlIlI;
            lllllllllllllllllIIIIIIllIlIlIlI.enumW(lllllllllllllllllIIIIIIllIlIlIIl, (EnumSetting)lllllllllllllllllIIIIIIllIlIIlIl);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(ColorSetting.class, (lllllllllllllllllIIIIIIllIlIllll, lllllllllllllllllIIIIIIllIlIlllI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIllIIII;
            lllllllllllllllllIIIIIIllIllIIII.colorW(lllllllllllllllllIIIIIIllIlIllll, (ColorSetting)lllllllllllllllllIIIIIIllIlIlllI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(StringSetting.class, (lllllllllllllllllIIIIIIllIlllIll, lllllllllllllllllIIIIIIllIllIlll) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllIllllII;
            lllllllllllllllllIIIIIIllIllllII.stringW(lllllllllllllllllIIIIIIllIlllIll, (StringSetting)lllllllllllllllllIIIIIIllIllIlll);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(BlockSetting.class, (lllllllllllllllllIIIIIIlllIIIIIl, lllllllllllllllllIIIIIIlllIIIIII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIlllIIIIlI;
            lllllllllllllllllIIIIIIlllIIIIlI.blockW(lllllllllllllllllIIIIIIlllIIIIIl, (BlockSetting)lllllllllllllllllIIIIIIlllIIIIII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(KeybindSetting.class, (lllllllllllllllllIIIIIIlllIIllIl, lllllllllllllllllIIIIIIlllIIlIIl) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIlllIIlIll;
            lllllllllllllllllIIIIIIlllIIlIll.keybindW(lllllllllllllllllIIIIIIlllIIllIl, (KeybindSetting)lllllllllllllllllIIIIIIlllIIlIIl);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(BlockListSetting.class, (lllllllllllllllllIIIIIIlllIlIIll, lllllllllllllllllIIIIIIlllIlIIlI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIlllIlIlII;
            lllllllllllllllllIIIIIIlllIlIlII.blockListW(lllllllllllllllllIIIIIIlllIlIIll, (BlockListSetting)lllllllllllllllllIIIIIIlllIlIIlI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(ItemListSetting.class, (lllllllllllllllllIIIIIIlllIlllII, lllllllllllllllllIIIIIIlllIllllI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIlllIlllIl;
            lllllllllllllllllIIIIIIlllIlllIl.itemListW(lllllllllllllllllIIIIIIlllIlllII, (ItemListSetting)lllllllllllllllllIIIIIIlllIllllI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(EntityTypeListSetting.class, (lllllllllllllllllIIIIIIllllIIlIl, lllllllllllllllllIIIIIIllllIIlII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllllIlIIl;
            lllllllllllllllllIIIIIIllllIlIIl.entityTypeListW(lllllllllllllllllIIIIIIllllIIlIl, (EntityTypeListSetting)lllllllllllllllllIIIIIIllllIIlII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(EnchListSetting.class, (lllllllllllllllllIIIIIIllllIlllI, lllllllllllllllllIIIIIIlllllIIII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIlllllIIlI;
            lllllllllllllllllIIIIIIlllllIIlI.enchListW(lllllllllllllllllIIIIIIllllIlllI, (EnchListSetting)lllllllllllllllllIIIIIIlllllIIII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(ModuleListSetting.class, (lllllllllllllllllIIIIIIllllllIlI, lllllllllllllllllIIIIIIlllllIllI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIIllllllIII;
            lllllllllllllllllIIIIIIllllllIII.moduleListW(lllllllllllllllllIIIIIIllllllIlI, (ModuleListSetting)lllllllllllllllllIIIIIIlllllIllI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(PacketBoolSetting.class, (lllllllllllllllllIIIIIlIIIIIIIll, lllllllllllllllllIIIIIlIIIIIIIlI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIIIIIIIl;
            lllllllllllllllllIIIIIlIIIIIIIIl.packetBoolW(lllllllllllllllllIIIIIlIIIIIIIll, (PacketBoolSetting)lllllllllllllllllIIIIIlIIIIIIIlI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(ParticleTypeListSetting.class, (lllllllllllllllllIIIIIlIIIIIlIIl, lllllllllllllllllIIIIIlIIIIIlIII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIIIIllIl;
            lllllllllllllllllIIIIIlIIIIIllIl.particleEffectListW(lllllllllllllllllIIIIIlIIIIIlIIl, (ParticleTypeListSetting)lllllllllllllllllIIIIIlIIIIIlIII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(SoundEventListSetting.class, (lllllllllllllllllIIIIIlIIIIlIIlI, lllllllllllllllllIIIIIlIIIIlIlII) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIIIlIIll;
            lllllllllllllllllIIIIIlIIIIlIIll.soundEventListW(lllllllllllllllllIIIIIlIIIIlIIlI, (SoundEventListSetting)lllllllllllllllllIIIIIlIIIIlIlII);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(StatusEffectSetting.class, (lllllllllllllllllIIIIIlIIIIllllI, lllllllllllllllllIIIIIlIIIIllIlI) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIIIlllll;
            lllllllllllllllllIIIIIlIIIIlllll.statusEffectW(lllllllllllllllllIIIIIlIIIIllllI, (StatusEffectSetting)lllllllllllllllllIIIIIlIIIIllIlI);
        });
        lllllllllllllllllIIIIIlllllIIIII.factories.put(StorageBlockListSetting.class, (lllllllllllllllllIIIIIlIIIlIIlII, lllllllllllllllllIIIIIlIIIlIIIll) -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIIlIIlIl;
            lllllllllllllllllIIIIIlIIIlIIlIl.storageBlockListW(lllllllllllllllllIIIIIlIIIlIIlII, (StorageBlockListSetting)lllllllllllllllllIIIIIlIIIlIIIll);
        });
    }

    private void packetBoolW(WTable lllllllllllllllllIIIIIllIIIIlIll, PacketBoolSetting lllllllllllllllllIIIIIllIIIIllIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIIIllll;
        lllllllllllllllllIIIIIllIIIIllll.selectW(lllllllllllllllllIIIIIllIIIIlIll, lllllllllllllllllIIIIIllIIIIllIl, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIlIIlll;
            Utils.mc.openScreen((Screen)new PacketBoolSettingScreen(lllllllllllllllllIIIIIlIlIlIIlll.theme, lllllllllllllllllIIIIIllIIIIllIl));
        });
    }

    private void particleEffectListW(WTable lllllllllllllllllIIIIIllIIIIIIlI, ParticleTypeListSetting lllllllllllllllllIIIIIllIIIIIlII) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIIIIIll;
        lllllllllllllllllIIIIIllIIIIIIll.selectW(lllllllllllllllllIIIIIllIIIIIIlI, lllllllllllllllllIIIIIllIIIIIlII, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIlIlIll;
            Utils.mc.openScreen((Screen)new ParticleTypeListSettingScreen(lllllllllllllllllIIIIIlIlIlIlIll.theme, lllllllllllllllllIIIIIllIIIIIlII));
        });
    }

    private void stringW(WTable lllllllllllllllllIIIIIllIllIIIII, StringSetting lllllllllllllllllIIIIIllIllIIIll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIllIIlIl;
        WTextBox lllllllllllllllllIIIIIllIllIIIlI = lllllllllllllllllIIIIIllIllIIIII.add(lllllllllllllllllIIIIIllIllIIlIl.theme.textBox((String)lllllllllllllllllIIIIIllIllIIIll.get())).expandX().widget();
        lllllllllllllllllIIIIIllIllIIIlI.action = () -> lllllllllllllllllIIIIIllIllIIIll.set(lllllllllllllllllIIIIIllIllIIIlI.get());
        lllllllllllllllllIIIIIllIllIIlIl.reset(lllllllllllllllllIIIIIllIllIIIII, lllllllllllllllllIIIIIllIllIIIll, () -> lllllllllllllllllIIIIIllIllIIIlI.set((String)lllllllllllllllllIIIIIllIllIIIll.get()));
    }

    private void colorW(WTable lllllllllllllllllIIIIIllIlllIlII, ColorSetting lllllllllllllllllIIIIIllIlllIIll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIllIllll;
        WHorizontalList lllllllllllllllllIIIIIllIlllIIlI = lllllllllllllllllIIIIIllIlllIlII.add(lllllllllllllllllIIIIIllIllIllll.theme.horizontalList()).expandX().widget();
        WQuad lllllllllllllllllIIIIIllIlllIIIl = lllllllllllllllllIIIIIllIlllIIlI.add(lllllllllllllllllIIIIIllIllIllll.theme.quad((Color)lllllllllllllllllIIIIIllIlllIIll.get())).widget();
        WButton lllllllllllllllllIIIIIllIlllIIII = lllllllllllllllllIIIIIllIlllIIlI.add(lllllllllllllllllIIIIIllIllIllll.theme.button(GuiRenderer.EDIT)).widget();
        lllllllllllllllllIIIIIllIlllIIII.action = () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIIllIIlIl;
            Utils.mc.openScreen((Screen)new ColorSettingScreen(lllllllllllllllllIIIIIlIIllIIlIl.theme, lllllllllllllllllIIIIIllIlllIIll));
        };
        lllllllllllllllllIIIIIllIllIllll.reset(lllllllllllllllllIIIIIllIlllIlII, lllllllllllllllllIIIIIllIlllIIll, () -> {
            lllllllllllllllllIIIIIlIIllIlIll.color = (Color)lllllllllllllllllIIIIIllIlllIIll.get();
        });
    }

    private void moduleListW(WTable lllllllllllllllllIIIIIllIIIlIlll, ModuleListSetting lllllllllllllllllIIIIIllIIIlIIll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIIlIlIl;
        lllllllllllllllllIIIIIllIIIlIlIl.selectW(lllllllllllllllllIIIIIllIIIlIlll, lllllllllllllllllIIIIIllIIIlIIll, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIIlllll;
            Utils.mc.openScreen((Screen)new ModuleListSettingScreen(lllllllllllllllllIIIIIlIlIIlllll.theme, lllllllllllllllllIIIIIllIIIlIIll));
        });
    }

    private void statusEffectW(WTable lllllllllllllllllIIIIIlIllllIIII, StatusEffectSetting lllllllllllllllllIIIIIlIlllIllll) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIllllIIIl;
        lllllllllllllllllIIIIIlIllllIIIl.selectW(lllllllllllllllllIIIIIlIllllIIII, lllllllllllllllllIIIIIlIlllIllll, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIllIlll;
            Utils.mc.openScreen((Screen)new StatusEffectSettingScreen(lllllllllllllllllIIIIIlIlIllIlll.theme, lllllllllllllllllIIIIIlIlllIllll));
        });
    }

    private void group(WVerticalList lllllllllllllllllIIIIIlllIllllII, SettingGroup lllllllllllllllllIIIIIlllIlllIll, String lllllllllllllllllIIIIIlllIllIlII) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlllIllllIl;
        WSection lllllllllllllllllIIIIIlllIlllIIl = lllllllllllllllllIIIIIlllIllllII.add(lllllllllllllllllIIIIIlllIllllIl.theme.section(lllllllllllllllllIIIIIlllIlllIll.name, lllllllllllllllllIIIIIlllIlllIll.sectionExpanded)).expandX().widget();
        lllllllllllllllllIIIIIlllIlllIIl.action = () -> {
            lllllllllllllllllIIIIIlIIIlIllll.sectionExpanded = lllllllllllllllllIIIIIlllIlllIIl.isExpanded();
        };
        WTable lllllllllllllllllIIIIIlllIlllIII = lllllllllllllllllIIIIIlllIlllIIl.add(lllllllllllllllllIIIIIlllIllllIl.theme.table()).expandX().widget();
        for (Setting<?> lllllllllllllllllIIIIIlllIlllllI : lllllllllllllllllIIIIIlllIlllIll) {
            if (!StringUtils.containsIgnoreCase((CharSequence)lllllllllllllllllIIIIIlllIlllllI.title, (CharSequence)lllllllllllllllllIIIIIlllIllIlII)) continue;
            lllllllllllllllllIIIIIlllIlllIII.add(lllllllllllllllllIIIIIlllIllllIl.theme.label((String)lllllllllllllllllIIIIIlllIlllllI.title)).widget().tooltip = lllllllllllllllllIIIIIlllIlllllI.description;
            Factory lllllllllllllllllIIIIIlllIllllll = lllllllllllllllllIIIIIlllIllllIl.factories.get(lllllllllllllllllIIIIIlllIlllllI.getClass());
            if (lllllllllllllllllIIIIIlllIllllll != null) {
                lllllllllllllllllIIIIIlllIllllll.create(lllllllllllllllllIIIIIlllIlllIII, lllllllllllllllllIIIIIlllIlllllI);
            }
            lllllllllllllllllIIIIIlllIlllIII.row();
        }
        if (lllllllllllllllllIIIIIlllIlllIII.cells.isEmpty()) {
            lllllllllllllllllIIIIIlllIllllII.cells.remove(lllllllllllllllllIIIIIlllIllllII.cells.size() - 1);
        }
    }

    private void itemListW(WTable lllllllllllllllllIIIIIllIIllIIlI, ItemListSetting lllllllllllllllllIIIIIllIIllIIIl) {
        DefaultSettingsWidgetFactory lllllllllllllllllIIIIIllIIllIIll;
        lllllllllllllllllIIIIIllIIllIIll.selectW(lllllllllllllllllIIIIIllIIllIIlI, lllllllllllllllllIIIIIllIIllIIIl, () -> {
            DefaultSettingsWidgetFactory lllllllllllllllllIIIIIlIlIIIllIl;
            Utils.mc.openScreen((Screen)new ItemListSettingScreen(lllllllllllllllllIIIIIlIlIIIllIl.theme, lllllllllllllllllIIIIIllIIllIIIl));
        });
    }

    protected static interface Factory {
        public void create(WTable var1, Setting<?> var2);
    }
}

