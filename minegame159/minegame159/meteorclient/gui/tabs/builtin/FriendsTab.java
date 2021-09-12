/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.tabs.builtin;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.entity.FriendType;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_437;

public class FriendsTab
extends Tab {
    @Override
    public boolean isScreen(class_437 class_4372) {
        return class_4372 instanceof FriendsScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme guiTheme) {
        return new FriendsScreen(guiTheme, this);
    }

    public FriendsTab() {
        super("Friends");
    }

    private static class FriendsScreen
    extends WindowTabScreen {
        private static void lambda$fillTable$12(Friend friend, WDropdown wDropdown) {
            friend.type = (FriendType)((Object)wDropdown.get());
        }

        private void lambda$new$11(WTextBox wTextBox, WTable wTable) {
            String string = wTextBox.get().trim();
            if (Friends.get().add(new Friend(string))) {
                wTextBox.set("");
                wTable.clear();
                this.fillTable(wTable);
            }
        }

        private void lambda$fillTable$13(Friend friend, WTable wTable) {
            Friends.get().remove(friend);
            wTable.clear();
            this.fillTable(wTable);
        }

        private static void lambda$new$9(Setting setting) {
            setting.set(Friends.get().showTrusted);
        }

        private static void lambda$new$0(Boolean bl) {
            Friends.get().showEnemies = bl;
        }

        private static void lambda$new$3(Boolean bl) {
            Friends.get().showNeutral = bl;
        }

        public FriendsScreen(GuiTheme guiTheme, Tab tab) {
            super(guiTheme, tab);
            Settings settings = new Settings();
            SettingGroup settingGroup = settings.createGroup("Enemies");
            SettingGroup settingGroup2 = settings.createGroup("Neutral");
            SettingGroup settingGroup3 = settings.createGroup("Trusted");
            settingGroup.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show enemies in tracers.").defaultValue(true).onChanged(FriendsScreen::lambda$new$0).onModuleActivated(FriendsScreen::lambda$new$1).build());
            settingGroup.add(new ColorSetting.Builder().name("color").description("The color used to show enemies in ESP and Tracers.").defaultValue(new SettingColor(204, 0, 0)).onChanged(Friends.get().enemyColor::set).onModuleActivated(FriendsScreen::lambda$new$2).build());
            settingGroup2.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show neutrals in tracers.").defaultValue(true).onChanged(FriendsScreen::lambda$new$3).onModuleActivated(FriendsScreen::lambda$new$4).build());
            settingGroup2.add(new ColorSetting.Builder().name("color").description("The color used to show neutrals in ESP and Tracers.").defaultValue(new SettingColor(60, 240, 240)).onChanged(Friends.get().neutralColor::set).onModuleActivated(FriendsScreen::lambda$new$5).build());
            settingGroup2.add(new BoolSetting.Builder().name("attack").description("Whether to attack neutrals.").defaultValue(false).onChanged(FriendsScreen::lambda$new$6).onModuleActivated(FriendsScreen::lambda$new$7).build());
            settingGroup3.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show trusted in tracers.").defaultValue(true).onChanged(FriendsScreen::lambda$new$8).onModuleActivated(FriendsScreen::lambda$new$9).build());
            settingGroup3.add(new ColorSetting.Builder().name("color").description("The color used to show trusted in ESP and Tracers.").defaultValue(new SettingColor(57, 247, 47)).onChanged(Friends.get().trustedColor::set).onModuleActivated(FriendsScreen::lambda$new$10).build());
            settings.onActivated();
            this.add(guiTheme.settings(settings)).expandX();
            WSection wSection = this.add(guiTheme.section("Friends")).expandX().widget();
            WTable wTable = wSection.add(guiTheme.table()).expandX().widget();
            this.fillTable(wTable);
            WHorizontalList wHorizontalList = wSection.add(guiTheme.horizontalList()).expandX().widget();
            WTextBox wTextBox = wHorizontalList.add(guiTheme.textBox("")).minWidth(400.0).expandX().widget();
            wTextBox.setFocused(true);
            WPlus wPlus = wHorizontalList.add(guiTheme.plus()).widget();
            wPlus.action = () -> this.lambda$new$11(wTextBox, wTable);
        }

        private static void lambda$new$10(Setting setting) {
            setting.set(Friends.get().trustedColor);
        }

        private static void lambda$new$1(Setting setting) {
            setting.set(Friends.get().showEnemies);
        }

        private void fillTable(WTable wTable) {
            for (Friend friend : Friends.get()) {
                wTable.add(this.theme.label(friend.name));
                WDropdown<FriendType> wDropdown = wTable.add(this.theme.dropdown(friend.type)).widget();
                wDropdown.action = () -> FriendsScreen.lambda$fillTable$12(friend, wDropdown);
                WMinus wMinus = wTable.add(this.theme.minus()).expandCellX().right().widget();
                wMinus.action = () -> this.lambda$fillTable$13(friend, wTable);
                wTable.row();
            }
        }

        private static void lambda$new$4(Setting setting) {
            setting.set(Friends.get().showNeutral);
        }

        private static void lambda$new$7(Setting setting) {
            setting.set(Friends.get().attackNeutral);
        }

        private static void lambda$new$6(Boolean bl) {
            Friends.get().attackNeutral = bl;
        }

        private static void lambda$new$8(Boolean bl) {
            Friends.get().showTrusted = bl;
        }

        private static void lambda$new$2(Setting setting) {
            setting.set(Friends.get().enemyColor);
        }

        private static void lambda$new$5(Setting setting) {
            setting.set(Friends.get().neutralColor);
        }
    }
}

