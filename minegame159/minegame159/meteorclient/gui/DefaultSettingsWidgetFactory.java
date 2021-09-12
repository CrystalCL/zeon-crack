/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import net.minecraft.class_2248;
import net.minecraft.class_437;
import org.apache.commons.lang3.StringUtils;

public class DefaultSettingsWidgetFactory
implements SettingsWidgetFactory {
    private final Map<Class<?>, Factory> factories = new HashMap();
    private final GuiTheme theme;

    private void lambda$storageBlockListW$43(StorageBlockListSetting storageBlockListSetting) {
        Utils.mc.method_1507((class_437)new StorageBlockListSettingScreen(this.theme, storageBlockListSetting));
    }

    private static void lambda$doubleW$25(WDoubleEdit wDoubleEdit, DoubleSetting doubleSetting) {
        wDoubleEdit.set((Double)doubleSetting.get());
    }

    private void lambda$enchListW$37(EnchListSetting enchListSetting) {
        Utils.mc.method_1507((class_437)new EnchListSettingScreen(this.theme, enchListSetting));
    }

    private void lambda$colorW$28(ColorSetting colorSetting) {
        Utils.mc.method_1507((class_437)new ColorSettingScreen(this.theme, colorSetting));
    }

    private static void lambda$stringW$31(WTextBox wTextBox, StringSetting stringSetting) {
        wTextBox.set((String)stringSetting.get());
    }

    private static void lambda$boolW$21(WCheckbox wCheckbox, BoolSetting boolSetting) {
        wCheckbox.checked = (Boolean)boolSetting.get();
    }

    private static void lambda$intW$22(IntSetting intSetting, WIntEdit wIntEdit) {
        if (!intSetting.set(wIntEdit.get())) {
            wIntEdit.set((Integer)intSetting.get());
        }
    }

    private static void lambda$boolW$20(BoolSetting boolSetting, WCheckbox wCheckbox) {
        boolSetting.set(wCheckbox.checked);
    }

    private static void lambda$enumW$26(EnumSetting enumSetting, WDropdown wDropdown) {
        enumSetting.set((Enum)wDropdown.get());
    }

    private void lambda$new$1(WTable wTable, Setting setting) {
        this.intW(wTable, (IntSetting)setting);
    }

    private void blockW(WTable wTable, BlockSetting blockSetting) {
        WHorizontalList wHorizontalList = wTable.add(this.theme.horizontalList()).expandX().widget();
        WItem wItem = wHorizontalList.add(this.theme.item(((class_2248)blockSetting.get()).method_8389().method_7854())).widget();
        WButton wButton = wHorizontalList.add(this.theme.button("Select")).widget();
        wButton.action = () -> this.lambda$blockW$32(blockSetting);
        this.reset(wTable, blockSetting, () -> DefaultSettingsWidgetFactory.lambda$blockW$33(wItem, blockSetting));
    }

    private void keybindW(WTable wTable, KeybindSetting keybindSetting) {
        WKeybind wKeybind = wTable.add(this.theme.keybind((Keybind)keybindSetting.get(), true)).expandCellX().widget();
        Objects.requireNonNull(keybindSetting);
        wKeybind.action = keybindSetting::changed;
        keybindSetting.widget = wKeybind;
        Objects.requireNonNull(wKeybind);
        this.reset(wTable, keybindSetting, wKeybind::reset);
    }

    private void lambda$new$8(WTable wTable, Setting setting) {
        this.keybindW(wTable, (KeybindSetting)setting);
    }

    private void lambda$new$7(WTable wTable, Setting setting) {
        this.blockW(wTable, (BlockSetting)setting);
    }

    private static void lambda$colorW$29(WQuad wQuad, ColorSetting colorSetting) {
        wQuad.color = (Color)colorSetting.get();
    }

    private void lambda$itemListW$35(ItemListSetting itemListSetting) {
        Utils.mc.method_1507((class_437)new ItemListSettingScreen(this.theme, itemListSetting));
    }

    private void lambda$soundEventListW$41(SoundEventListSetting soundEventListSetting) {
        Utils.mc.method_1507((class_437)new SoundEventListSettingScreen(this.theme, soundEventListSetting));
    }

    private void lambda$new$13(WTable wTable, Setting setting) {
        this.moduleListW(wTable, (ModuleListSetting)setting);
    }

    private void blockListW(WTable wTable, BlockListSetting blockListSetting) {
        this.selectW(wTable, blockListSetting, () -> this.lambda$blockListW$34(blockListSetting));
    }

    private static void lambda$stringW$30(StringSetting stringSetting, WTextBox wTextBox) {
        stringSetting.set(wTextBox.get());
    }

    private void selectW(WTable wTable, Setting<?> setting, Runnable runnable) {
        WButton wButton = wTable.add(this.theme.button("Select")).expandCellX().widget();
        wButton.action = runnable;
        this.reset(wTable, setting, null);
    }

    private static void lambda$enumW$27(WDropdown wDropdown, EnumSetting enumSetting) {
        wDropdown.set((Enum)enumSetting.get());
    }

    private void statusEffectW(WTable wTable, StatusEffectSetting statusEffectSetting) {
        this.selectW(wTable, statusEffectSetting, () -> this.lambda$statusEffectW$42(statusEffectSetting));
    }

    private void lambda$new$6(WTable wTable, Setting setting) {
        this.stringW(wTable, (StringSetting)setting);
    }

    private static void lambda$blockW$33(WItem wItem, BlockSetting blockSetting) {
        wItem.set(((class_2248)blockSetting.get()).method_8389().method_7854());
    }

    private void packetBoolW(WTable wTable, PacketBoolSetting packetBoolSetting) {
        this.selectW(wTable, packetBoolSetting, () -> this.lambda$packetBoolW$39(packetBoolSetting));
    }

    private static void lambda$group$19(SettingGroup settingGroup, WSection wSection) {
        settingGroup.sectionExpanded = wSection.isExpanded();
    }

    private void colorW(WTable wTable, ColorSetting colorSetting) {
        WHorizontalList wHorizontalList = wTable.add(this.theme.horizontalList()).expandX().widget();
        WQuad wQuad = wHorizontalList.add(this.theme.quad((Color)colorSetting.get())).widget();
        WButton wButton = wHorizontalList.add(this.theme.button(GuiRenderer.EDIT)).widget();
        wButton.action = () -> this.lambda$colorW$28(colorSetting);
        this.reset(wTable, colorSetting, () -> DefaultSettingsWidgetFactory.lambda$colorW$29(wQuad, colorSetting));
    }

    private void lambda$packetBoolW$39(PacketBoolSetting packetBoolSetting) {
        Utils.mc.method_1507((class_437)new PacketBoolSettingScreen(this.theme, packetBoolSetting));
    }

    private void enchListW(WTable wTable, EnchListSetting enchListSetting) {
        this.selectW(wTable, enchListSetting, () -> this.lambda$enchListW$37(enchListSetting));
    }

    private void group(WVerticalList wVerticalList, SettingGroup settingGroup, String string) {
        WSection wSection = wVerticalList.add(this.theme.section(settingGroup.name, settingGroup.sectionExpanded)).expandX().widget();
        wSection.action = () -> DefaultSettingsWidgetFactory.lambda$group$19(settingGroup, wSection);
        WTable wTable = wSection.add(this.theme.table()).expandX().widget();
        for (Setting<?> setting : settingGroup) {
            if (!StringUtils.containsIgnoreCase((CharSequence)setting.title, (CharSequence)string)) continue;
            wTable.add(this.theme.label((String)setting.title)).widget().tooltip = setting.description;
            Factory factory = this.factories.get(setting.getClass());
            if (factory != null) {
                factory.create(wTable, setting);
            }
            wTable.row();
        }
        if (wTable.cells.isEmpty()) {
            wVerticalList.cells.remove(wVerticalList.cells.size() - 1);
        }
    }

    private void lambda$new$10(WTable wTable, Setting setting) {
        this.itemListW(wTable, (ItemListSetting)setting);
    }

    private void boolW(WTable wTable, BoolSetting boolSetting) {
        WCheckbox wCheckbox = wTable.add(this.theme.checkbox((Boolean)boolSetting.get())).expandCellX().widget();
        wCheckbox.action = () -> DefaultSettingsWidgetFactory.lambda$boolW$20(boolSetting, wCheckbox);
        this.reset(wTable, boolSetting, () -> DefaultSettingsWidgetFactory.lambda$boolW$21(wCheckbox, boolSetting));
    }

    private static void lambda$doubleW$24(DoubleSetting doubleSetting, WDoubleEdit wDoubleEdit) {
        if (!doubleSetting.set(wDoubleEdit.get())) {
            wDoubleEdit.set((Double)doubleSetting.get());
        }
    }

    private <T extends Enum<?>> void enumW(WTable wTable, EnumSetting<T> enumSetting) {
        WDropdown<Enum> wDropdown = wTable.add(this.theme.dropdown((Enum)enumSetting.get())).expandCellX().widget();
        wDropdown.action = () -> DefaultSettingsWidgetFactory.lambda$enumW$26(enumSetting, wDropdown);
        this.reset(wTable, enumSetting, () -> DefaultSettingsWidgetFactory.lambda$enumW$27(wDropdown, enumSetting));
    }

    private void lambda$new$11(WTable wTable, Setting setting) {
        this.entityTypeListW(wTable, (EntityTypeListSetting)setting);
    }

    private void reset(WTable wTable, Setting<?> setting, Runnable runnable) {
        WButton wButton = wTable.add(this.theme.button(GuiRenderer.RESET)).widget();
        wButton.action = () -> DefaultSettingsWidgetFactory.lambda$reset$44(setting, runnable);
    }

    @Override
    public WWidget create(GuiTheme guiTheme, Settings settings, String string) {
        WVerticalList wVerticalList = guiTheme.verticalList();
        for (SettingGroup settingGroup : settings.groups) {
            this.group(wVerticalList, settingGroup, string);
        }
        return wVerticalList;
    }

    private void lambda$new$9(WTable wTable, Setting setting) {
        this.blockListW(wTable, (BlockListSetting)setting);
    }

    private void intW(WTable wTable, IntSetting intSetting) {
        WIntEdit wIntEdit = wTable.add(this.theme.intEdit((Integer)intSetting.get(), intSetting.getSliderMin(), intSetting.getSliderMax())).expandX().widget();
        wIntEdit.min = intSetting.min;
        wIntEdit.max = intSetting.max;
        wIntEdit.actionOnRelease = () -> DefaultSettingsWidgetFactory.lambda$intW$22(intSetting, wIntEdit);
        this.reset(wTable, intSetting, () -> DefaultSettingsWidgetFactory.lambda$intW$23(wIntEdit, intSetting));
    }

    private void lambda$new$5(WTable wTable, Setting setting) {
        this.colorW(wTable, (ColorSetting)setting);
    }

    private void lambda$new$17(WTable wTable, Setting setting) {
        this.statusEffectW(wTable, (StatusEffectSetting)setting);
    }

    private void soundEventListW(WTable wTable, SoundEventListSetting soundEventListSetting) {
        this.selectW(wTable, soundEventListSetting, () -> this.lambda$soundEventListW$41(soundEventListSetting));
    }

    private static void lambda$intW$23(WIntEdit wIntEdit, IntSetting intSetting) {
        wIntEdit.set((Integer)intSetting.get());
    }

    private void lambda$new$2(WTable wTable, Setting setting) {
        this.doubleW(wTable, (DoubleSetting)setting);
    }

    private void lambda$moduleListW$38(ModuleListSetting moduleListSetting) {
        Utils.mc.method_1507((class_437)new ModuleListSettingScreen(this.theme, moduleListSetting));
    }

    private void lambda$new$0(WTable wTable, Setting setting) {
        this.boolW(wTable, (BoolSetting)setting);
    }

    private static void lambda$reset$44(Setting setting, Runnable runnable) {
        setting.reset();
        if (runnable != null) {
            runnable.run();
        }
    }

    private void doubleW(WTable wTable, DoubleSetting doubleSetting) {
        WDoubleEdit wDoubleEdit = this.theme.doubleEdit((Double)doubleSetting.get(), doubleSetting.getSliderMin(), doubleSetting.getSliderMax());
        wDoubleEdit.min = doubleSetting.min;
        wDoubleEdit.max = doubleSetting.max;
        wDoubleEdit.decimalPlaces = doubleSetting.decimalPlaces;
        wTable.add(wDoubleEdit).expandX();
        Runnable runnable = () -> DefaultSettingsWidgetFactory.lambda$doubleW$24(doubleSetting, wDoubleEdit);
        if (doubleSetting.onSliderRelease) {
            wDoubleEdit.actionOnRelease = runnable;
        } else {
            wDoubleEdit.action = runnable;
        }
        this.reset(wTable, doubleSetting, () -> DefaultSettingsWidgetFactory.lambda$doubleW$25(wDoubleEdit, doubleSetting));
    }

    private void entityTypeListW(WTable wTable, EntityTypeListSetting entityTypeListSetting) {
        this.selectW(wTable, entityTypeListSetting, () -> this.lambda$entityTypeListW$36(entityTypeListSetting));
    }

    private void lambda$new$15(WTable wTable, Setting setting) {
        this.particleEffectListW(wTable, (ParticleTypeListSetting)setting);
    }

    private void moduleListW(WTable wTable, ModuleListSetting moduleListSetting) {
        this.selectW(wTable, moduleListSetting, () -> this.lambda$moduleListW$38(moduleListSetting));
    }

    private void lambda$new$18(WTable wTable, Setting setting) {
        this.storageBlockListW(wTable, (StorageBlockListSetting)setting);
    }

    private void lambda$particleEffectListW$40(ParticleTypeListSetting particleTypeListSetting) {
        Utils.mc.method_1507((class_437)new ParticleTypeListSettingScreen(this.theme, particleTypeListSetting));
    }

    private void storageBlockListW(WTable wTable, StorageBlockListSetting storageBlockListSetting) {
        this.selectW(wTable, storageBlockListSetting, () -> this.lambda$storageBlockListW$43(storageBlockListSetting));
    }

    private void lambda$new$12(WTable wTable, Setting setting) {
        this.enchListW(wTable, (EnchListSetting)setting);
    }

    private void lambda$blockW$32(BlockSetting blockSetting) {
        Utils.mc.method_1507((class_437)new BlockSettingScreen(this.theme, blockSetting));
    }

    private void lambda$new$3(WTable wTable, Setting setting) {
        this.enumW(wTable, (EnumSetting)setting);
    }

    private void lambda$statusEffectW$42(StatusEffectSetting statusEffectSetting) {
        Utils.mc.method_1507((class_437)new StatusEffectSettingScreen(this.theme, statusEffectSetting));
    }

    private void lambda$new$16(WTable wTable, Setting setting) {
        this.soundEventListW(wTable, (SoundEventListSetting)setting);
    }

    private void lambda$new$14(WTable wTable, Setting setting) {
        this.packetBoolW(wTable, (PacketBoolSetting)setting);
    }

    private void lambda$new$4(WTable wTable, Setting setting) {
        this.enumW(wTable, (EnumSetting)setting);
    }

    private void itemListW(WTable wTable, ItemListSetting itemListSetting) {
        this.selectW(wTable, itemListSetting, () -> this.lambda$itemListW$35(itemListSetting));
    }

    private void lambda$blockListW$34(BlockListSetting blockListSetting) {
        Utils.mc.method_1507((class_437)new BlockListSettingScreen(this.theme, blockListSetting));
    }

    private void stringW(WTable wTable, StringSetting stringSetting) {
        WTextBox wTextBox = wTable.add(this.theme.textBox((String)stringSetting.get())).expandX().widget();
        wTextBox.action = () -> DefaultSettingsWidgetFactory.lambda$stringW$30(stringSetting, wTextBox);
        this.reset(wTable, stringSetting, () -> DefaultSettingsWidgetFactory.lambda$stringW$31(wTextBox, stringSetting));
    }

    public DefaultSettingsWidgetFactory(GuiTheme guiTheme) {
        this.theme = guiTheme;
        this.factories.put(BoolSetting.class, this::lambda$new$0);
        this.factories.put(IntSetting.class, this::lambda$new$1);
        this.factories.put(DoubleSetting.class, this::lambda$new$2);
        this.factories.put(EnumSetting.class, this::lambda$new$3);
        this.factories.put(PotionSetting.class, this::lambda$new$4);
        this.factories.put(ColorSetting.class, this::lambda$new$5);
        this.factories.put(StringSetting.class, this::lambda$new$6);
        this.factories.put(BlockSetting.class, this::lambda$new$7);
        this.factories.put(KeybindSetting.class, this::lambda$new$8);
        this.factories.put(BlockListSetting.class, this::lambda$new$9);
        this.factories.put(ItemListSetting.class, this::lambda$new$10);
        this.factories.put(EntityTypeListSetting.class, this::lambda$new$11);
        this.factories.put(EnchListSetting.class, this::lambda$new$12);
        this.factories.put(ModuleListSetting.class, this::lambda$new$13);
        this.factories.put(PacketBoolSetting.class, this::lambda$new$14);
        this.factories.put(ParticleTypeListSetting.class, this::lambda$new$15);
        this.factories.put(SoundEventListSetting.class, this::lambda$new$16);
        this.factories.put(StatusEffectSetting.class, this::lambda$new$17);
        this.factories.put(StorageBlockListSetting.class, this::lambda$new$18);
    }

    private void lambda$entityTypeListW$36(EntityTypeListSetting entityTypeListSetting) {
        Utils.mc.method_1507((class_437)new EntityTypeListSettingScreen(this.theme, entityTypeListSetting));
    }

    private void particleEffectListW(WTable wTable, ParticleTypeListSetting particleTypeListSetting) {
        this.selectW(wTable, particleTypeListSetting, () -> this.lambda$particleEffectListW$40(particleTypeListSetting));
    }

    protected static interface Factory {
        public void create(WTable var1, Setting<?> var2);
    }
}

