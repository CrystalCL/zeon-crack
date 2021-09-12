/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.tabs.builtin;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.network.OnlinePlayers;
import net.minecraft.class_437;

public class ConfigTab
extends Tab {
    @Override
    public TabScreen createScreen(GuiTheme guiTheme) {
        return new ConfigScreen(guiTheme, this);
    }

    public ConfigTab() {
        super("Config");
    }

    @Override
    public boolean isScreen(class_437 class_4372) {
        return class_4372 instanceof ConfigScreen;
    }

    private static class ConfigScreen
    extends WindowTabScreen {
        private static void lambda$new$3(Boolean bl) {
            Config.get().chatCommandsInfo = bl;
        }

        private static void lambda$new$6(Setting setting) {
            setting.set(Config.get().deleteChatCommandsInfo);
        }

        public ConfigScreen(GuiTheme guiTheme, Tab tab) {
            super(guiTheme, tab);
            Settings settings = new Settings();
            SettingGroup settingGroup = settings.getDefaultGroup();
            settingGroup.add(new StringSetting.Builder().name("prefix").description("Prefix.").defaultValue(".").onChanged(Config.get()::setPrefix).onModuleActivated(ConfigScreen::lambda$new$0).build());
            settingGroup.add(new BoolSetting.Builder().name("custom-font").description("Use a custom font.").defaultValue(true).onChanged(this::lambda$new$1).onModuleActivated(ConfigScreen::lambda$new$2).build());
            settingGroup.add(new BoolSetting.Builder().name("chat-commands-info").description("Sends a chat message when you use chat comamnds (eg toggling module, changing a setting, etc).").defaultValue(true).onChanged(ConfigScreen::lambda$new$3).onModuleActivated(ConfigScreen::lambda$new$4).build());
            settingGroup.add(new BoolSetting.Builder().name("delete-chat-commands-info").description("Delete previous chat messages.").defaultValue(true).onChanged(ConfigScreen::lambda$new$5).onModuleActivated(ConfigScreen::lambda$new$6).build());
            settingGroup.add(new BoolSetting.Builder().name("send-data-to-api").description("If checked, your UUID will be send to Meteor's servers.").defaultValue(true).onChanged(ConfigScreen::lambda$new$7).onModuleActivated(ConfigScreen::lambda$new$8).build());
            settingGroup.add(new IntSetting.Builder().name("rotation-hold-ticks").description("Hold long to hold server side rotation when not sending any packets.").defaultValue(9).onChanged(ConfigScreen::lambda$new$9).onModuleActivated(ConfigScreen::lambda$new$10).build());
            settingGroup.add(new BoolSetting.Builder().name("title-screen-credits").description("Show Meteor credits on title screen").defaultValue(true).onChanged(ConfigScreen::lambda$new$11).onModuleActivated(ConfigScreen::lambda$new$12).build());
            settingGroup.add(new BoolSetting.Builder().name("window-title").description("Show Meteor in the window title.").defaultValue(false).onChanged(ConfigScreen::lambda$new$13).onModuleActivated(ConfigScreen::lambda$new$14).build());
            settings.onActivated();
            this.add(guiTheme.settings(settings)).expandX();
        }

        private static void lambda$new$9(Integer n) {
            Config.get().rotationHoldTicks = n;
        }

        private static void lambda$new$13(Boolean bl) {
            Config.get().windowTitle = bl;
        }

        private static void lambda$new$4(Setting setting) {
            setting.set(Config.get().chatCommandsInfo);
        }

        private static void lambda$new$2(Setting setting) {
            setting.set(Config.get().customFont);
        }

        private static void lambda$new$11(Boolean bl) {
            Config.get().titleScreenCredits = bl;
        }

        private static void lambda$new$7(Boolean bl) {
            Config.get().sendDataToApi = bl;
            OnlinePlayers.forcePing();
        }

        private static void lambda$new$8(Setting setting) {
            setting.set(Config.get().sendDataToApi);
        }

        private static void lambda$new$0(Setting setting) {
            setting.set(Config.get().getPrefix());
        }

        private static void lambda$new$5(Boolean bl) {
            Config.get().deleteChatCommandsInfo = bl;
        }

        private static void lambda$new$14(Setting setting) {
            setting.set(Config.get().windowTitle);
        }

        private static void lambda$new$10(Setting setting) {
            setting.set(Config.get().rotationHoldTicks);
        }

        private static void lambda$new$12(Setting setting) {
            setting.set(Config.get().titleScreenCredits);
        }

        private void lambda$new$1(Boolean bl) {
            Config.get().customFont = bl;
            this.invalidate();
        }
    }
}

