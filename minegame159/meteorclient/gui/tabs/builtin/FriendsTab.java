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
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.entity.FriendType;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;

public class FriendsTab
extends Tab {
    @Override
    public boolean isScreen(Screen llllllllllllllllllllIIIlIIllIIll) {
        return llllllllllllllllllllIIIlIIllIIll instanceof FriendsScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme llllllllllllllllllllIIIlIIllIlll) {
        FriendsTab llllllllllllllllllllIIIlIIlllIlI;
        return new FriendsScreen(llllllllllllllllllllIIIlIIllIlll, llllllllllllllllllllIIIlIIlllIlI);
    }

    public FriendsTab() {
        super("Friends");
        FriendsTab llllllllllllllllllllIIIlIIlllllI;
    }

    private static class FriendsScreen
    extends WindowTabScreen {
        public FriendsScreen(GuiTheme llllllllllllllllIlllIlIIllIIIIII, Tab llllllllllllllllIlllIlIIlIllllll) {
            super(llllllllllllllllIlllIlIIllIIIIII, llllllllllllllllIlllIlIIlIllllll);
            FriendsScreen llllllllllllllllIlllIlIIllIIllIl;
            Settings llllllllllllllllIlllIlIIllIIlIlI = new Settings();
            SettingGroup llllllllllllllllIlllIlIIllIIlIIl = llllllllllllllllIlllIlIIllIIlIlI.createGroup("Enemies");
            SettingGroup llllllllllllllllIlllIlIIllIIlIII = llllllllllllllllIlllIlIIllIIlIlI.createGroup("Neutral");
            SettingGroup llllllllllllllllIlllIlIIllIIIlll = llllllllllllllllIlllIlIIllIIlIlI.createGroup("Trusted");
            llllllllllllllllIlllIlIIllIIlIIl.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show enemies in tracers.").defaultValue(true).onChanged(llllllllllllllllIlllIlIIIllIlIlI -> {
                Friends.get().showEnemies = llllllllllllllllIlllIlIIIllIlIlI;
            }).onModuleActivated(llllllllllllllllIlllIlIIIllIllII -> llllllllllllllllIlllIlIIIllIllII.set(Friends.get().showEnemies)).build());
            llllllllllllllllIlllIlIIllIIlIIl.add(new ColorSetting.Builder().name("color").description("The color used to show enemies in ESP and Tracers.").defaultValue(new SettingColor(204, 0, 0)).onChanged(Friends.get().enemyColor::set).onModuleActivated(llllllllllllllllIlllIlIIIlllIIII -> llllllllllllllllIlllIlIIIlllIIII.set(Friends.get().enemyColor)).build());
            llllllllllllllllIlllIlIIllIIlIII.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show neutrals in tracers.").defaultValue(true).onChanged(llllllllllllllllIlllIlIIIlllIIlI -> {
                Friends.get().showNeutral = llllllllllllllllIlllIlIIIlllIIlI;
            }).onModuleActivated(llllllllllllllllIlllIlIIIlllIlIl -> llllllllllllllllIlllIlIIIlllIlIl.set(Friends.get().showNeutral)).build());
            llllllllllllllllIlllIlIIllIIlIII.add(new ColorSetting.Builder().name("color").description("The color used to show neutrals in ESP and Tracers.").defaultValue(new SettingColor(60, 240, 240)).onChanged(Friends.get().neutralColor::set).onModuleActivated(llllllllllllllllIlllIlIIIllllIIl -> llllllllllllllllIlllIlIIIllllIIl.set(Friends.get().neutralColor)).build());
            llllllllllllllllIlllIlIIllIIlIII.add(new BoolSetting.Builder().name("attack").description("Whether to attack neutrals.").defaultValue(false).onChanged(llllllllllllllllIlllIlIIIllllIll -> {
                Friends.get().attackNeutral = llllllllllllllllIlllIlIIIllllIll;
            }).onModuleActivated(llllllllllllllllIlllIlIIIlllllll -> llllllllllllllllIlllIlIIIlllllll.set(Friends.get().attackNeutral)).build());
            llllllllllllllllIlllIlIIllIIIlll.add(new BoolSetting.Builder().name("show-in-tracers").description("Whether to show trusted in tracers.").defaultValue(true).onChanged(llllllllllllllllIlllIlIIlIIIIIlI -> {
                Friends.get().showTrusted = llllllllllllllllIlllIlIIlIIIIIlI;
            }).onModuleActivated(llllllllllllllllIlllIlIIlIIIIlIl -> llllllllllllllllIlllIlIIlIIIIlIl.set(Friends.get().showTrusted)).build());
            llllllllllllllllIlllIlIIllIIIlll.add(new ColorSetting.Builder().name("color").description("The color used to show trusted in ESP and Tracers.").defaultValue(new SettingColor(57, 247, 47)).onChanged(Friends.get().trustedColor::set).onModuleActivated(llllllllllllllllIlllIlIIlIIIIlll -> llllllllllllllllIlllIlIIlIIIIlll.set(Friends.get().trustedColor)).build());
            llllllllllllllllIlllIlIIllIIlIlI.onActivated();
            llllllllllllllllIlllIlIIllIIllIl.add(llllllllllllllllIlllIlIIllIIIIII.settings(llllllllllllllllIlllIlIIllIIlIlI)).expandX();
            WSection llllllllllllllllIlllIlIIllIIIllI = llllllllllllllllIlllIlIIllIIllIl.add(llllllllllllllllIlllIlIIllIIIIII.section("Friends")).expandX().widget();
            WTable llllllllllllllllIlllIlIIllIIIlIl = llllllllllllllllIlllIlIIllIIIllI.add(llllllllllllllllIlllIlIIllIIIIII.table()).expandX().widget();
            llllllllllllllllIlllIlIIllIIllIl.fillTable(llllllllllllllllIlllIlIIllIIIlIl);
            WHorizontalList llllllllllllllllIlllIlIIllIIIlII = llllllllllllllllIlllIlIIllIIIllI.add(llllllllllllllllIlllIlIIllIIIIII.horizontalList()).expandX().widget();
            WTextBox llllllllllllllllIlllIlIIllIIIIll = llllllllllllllllIlllIlIIllIIIlII.add(llllllllllllllllIlllIlIIllIIIIII.textBox("")).minWidth(400.0).expandX().widget();
            llllllllllllllllIlllIlIIllIIIIll.setFocused(true);
            WPlus llllllllllllllllIlllIlIIllIIIIlI = llllllllllllllllIlllIlIIllIIIlII.add(llllllllllllllllIlllIlIIllIIIIII.plus()).widget();
            llllllllllllllllIlllIlIIllIIIIlI.action = () -> {
                String llllllllllllllllIlllIlIIlIIIlllI = llllllllllllllllIlllIlIIllIIIIll.get().trim();
                if (Friends.get().add(new Friend(llllllllllllllllIlllIlIIlIIIlllI))) {
                    FriendsScreen llllllllllllllllIlllIlIIlIIlIIIl;
                    llllllllllllllllIlllIlIIllIIIIll.set("");
                    llllllllllllllllIlllIlIIllIIIlIl.clear();
                    llllllllllllllllIlllIlIIlIIlIIIl.fillTable(llllllllllllllllIlllIlIIllIIIlIl);
                }
            };
        }

        private void fillTable(WTable llllllllllllllllIlllIlIIlIlIlIll) {
            for (Friend llllllllllllllllIlllIlIIlIlIllIl : Friends.get()) {
                FriendsScreen llllllllllllllllIlllIlIIlIlIlIlI;
                llllllllllllllllIlllIlIIlIlIlIll.add(llllllllllllllllIlllIlIIlIlIlIlI.theme.label(llllllllllllllllIlllIlIIlIlIllIl.name));
                WDropdown<FriendType> llllllllllllllllIlllIlIIlIlIllll = llllllllllllllllIlllIlIIlIlIlIll.add(llllllllllllllllIlllIlIIlIlIlIlI.theme.dropdown(llllllllllllllllIlllIlIIlIlIllIl.type)).widget();
                llllllllllllllllIlllIlIIlIlIllll.action = () -> {
                    llllllllllllllllIlllIlIIlIIllIIl.type = (FriendType)((Object)((Object)llllllllllllllllIlllIlIIlIlIllll.get()));
                };
                WMinus llllllllllllllllIlllIlIIlIlIlllI = llllllllllllllllIlllIlIIlIlIlIll.add(llllllllllllllllIlllIlIIlIlIlIlI.theme.minus()).expandCellX().right().widget();
                llllllllllllllllIlllIlIIlIlIlllI.action = () -> {
                    FriendsScreen llllllllllllllllIlllIlIIlIlIIIIl;
                    Friends.get().remove(llllllllllllllllIlllIlIIlIlIllIl);
                    llllllllllllllllIlllIlIIlIlIlIll.clear();
                    llllllllllllllllIlllIlIIlIlIIIIl.fillTable(llllllllllllllllIlllIlIIlIlIlIll);
                };
                llllllllllllllllIlllIlIIlIlIlIll.row();
            }
        }
    }
}

