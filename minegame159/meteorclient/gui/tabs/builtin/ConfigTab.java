/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.tabs.builtin;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.network.OnlinePlayers;
import net.minecraft.client.gui.screen.Screen;

public class ConfigTab
extends Tab {
    @Override
    public boolean isScreen(Screen llllIllIIlIIIlI) {
        return llllIllIIlIIIlI instanceof ConfigScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme llllIllIIlIIllI) {
        ConfigTab llllIllIIlIIlll;
        return new ConfigScreen(llllIllIIlIIllI, llllIllIIlIIlll);
    }

    public ConfigTab() {
        super("Config");
        ConfigTab llllIllIIlIllIl;
    }

    private static class ConfigScreen
    extends WindowTabScreen {
        public ConfigScreen(GuiTheme llllllllllllllllIlIllIllIIllllIl, Tab llllllllllllllllIlIllIllIIllllII) {
            super(llllllllllllllllIlIllIllIIllllIl, llllllllllllllllIlIllIllIIllllII);
            ConfigScreen llllllllllllllllIlIllIllIIlllIIl;
            Settings llllllllllllllllIlIllIllIIlllIll = new Settings();
            SettingGroup llllllllllllllllIlIllIllIIlllIlI = llllllllllllllllIlIllIllIIlllIll.getDefaultGroup();
            llllllllllllllllIlIllIllIIlllIlI.add(new StringSetting.Builder().name("prefix").description("Prefix.").defaultValue(".").onChanged(Config.get()::setPrefix).onModuleActivated(llllllllllllllllIlIllIllIIIIIllI -> llllllllllllllllIlIllIllIIIIIllI.set(Config.get().getPrefix())).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("custom-font").description("Use a custom font.").defaultValue(true).onChanged(llllllllllllllllIlIllIllIIIIlIlI -> {
                ConfigScreen llllllllllllllllIlIllIllIIIIlIIl;
                Config.get().customFont = llllllllllllllllIlIllIllIIIIlIlI;
                llllllllllllllllIlIllIllIIIIlIIl.invalidate();
            }).onModuleActivated(llllllllllllllllIlIllIllIIIIlllI -> llllllllllllllllIlIllIllIIIIlllI.set(Config.get().customFont)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("chat-commands-info").description("Sends a chat message when you use chat comamnds (eg toggling module, changing a setting, etc).").defaultValue(true).onChanged(llllllllllllllllIlIllIllIIIlIIIl -> {
                Config.get().chatCommandsInfo = llllllllllllllllIlIllIllIIIlIIIl;
            }).onModuleActivated(llllllllllllllllIlIllIllIIIlIlII -> llllllllllllllllIlIllIllIIIlIlII.set(Config.get().chatCommandsInfo)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("delete-chat-commands-info").description("Delete previous chat messages.").defaultValue(true).onChanged(llllllllllllllllIlIllIllIIIllIII -> {
                Config.get().deleteChatCommandsInfo = llllllllllllllllIlIllIllIIIllIII;
            }).onModuleActivated(llllllllllllllllIlIllIllIIIllIlI -> llllllllllllllllIlIllIllIIIllIlI.set(Config.get().deleteChatCommandsInfo)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("send-data-to-api").description("If checked, your UUID will be send to Meteor's servers.").defaultValue(true).onChanged(llllllllllllllllIlIllIllIIIllllI -> {
                Config.get().sendDataToApi = llllllllllllllllIlIllIllIIIllllI;
                OnlinePlayers.forcePing();
            }).onModuleActivated(llllllllllllllllIlIllIllIIlIIIIl -> llllllllllllllllIlIllIllIIlIIIIl.set(Config.get().sendDataToApi)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new IntSetting.Builder().name("rotation-hold-ticks").description("Hold long to hold server side rotation when not sending any packets.").defaultValue(9).onChanged(llllllllllllllllIlIllIllIIlIIlII -> {
                Config.get().rotationHoldTicks = llllllllllllllllIlIllIllIIlIIlII;
            }).onModuleActivated(llllllllllllllllIlIllIllIIlIIlll -> llllllllllllllllIlIllIllIIlIIlll.set(Config.get().rotationHoldTicks)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("title-screen-credits").description("Show Meteor credits on title screen").defaultValue(true).onChanged(llllllllllllllllIlIllIllIIlIlIlI -> {
                Config.get().titleScreenCredits = llllllllllllllllIlIllIllIIlIlIlI;
            }).onModuleActivated(llllllllllllllllIlIllIllIIlIllIl -> llllllllllllllllIlIllIllIIlIllIl.set(Config.get().titleScreenCredits)).build());
            llllllllllllllllIlIllIllIIlllIlI.add(new BoolSetting.Builder().name("window-title").description("Show Meteor in the window title.").defaultValue(false).onChanged(llllllllllllllllIlIllIllIIlIllll -> {
                Config.get().windowTitle = llllllllllllllllIlIllIllIIlIllll;
            }).onModuleActivated(llllllllllllllllIlIllIllIIllIIlI -> llllllllllllllllIlIllIllIIllIIlI.set(Config.get().windowTitle)).build());
            llllllllllllllllIlIllIllIIlllIll.onActivated();
            llllllllllllllllIlIllIllIIlllIIl.add(llllllllllllllllIlIllIllIIllllIl.settings(llllllllllllllllIlIllIllIIlllIll)).expandX();
        }
    }
}

