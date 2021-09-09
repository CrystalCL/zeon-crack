/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.gui.tabs.builtin;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.macros.Macros;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.profiles.Profile;
import minegame159.meteorclient.systems.profiles.Profiles;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.StringUtils;

public class ProfilesTab
extends Tab {
    public ProfilesTab() {
        super("Profiles");
        ProfilesTab lllllllllllllllllllIlIIIllllIIlI;
    }

    @Override
    public boolean isScreen(Screen lllllllllllllllllllIlIIIlllIlIIl) {
        return lllllllllllllllllllIlIIIlllIlIIl instanceof ProfilesScreen;
    }

    @Override
    protected TabScreen createScreen(GuiTheme lllllllllllllllllllIlIIIlllIllII) {
        ProfilesTab lllllllllllllllllllIlIIIlllIllIl;
        return new ProfilesScreen(lllllllllllllllllllIlIIIlllIllII, lllllllllllllllllllIlIIIlllIllIl);
    }

    private static class EditProfileScreen
    extends WindowScreen {
        private final /* synthetic */ Profile profile;
        private final /* synthetic */ boolean newProfile;
        private final /* synthetic */ Runnable action;

        private void fillTable(WTable llllIIIIlIlIlI) {
            EditProfileScreen llllIIIIlIlIIl;
            if (llllIIIIlIlIIl.profile.loadOnJoinIps.isEmpty()) {
                llllIIIIlIlIIl.profile.loadOnJoinIps.add("");
            }
            for (int llllIIIIlIllII = 0; llllIIIIlIllII < llllIIIIlIlIIl.profile.loadOnJoinIps.size(); ++llllIIIIlIllII) {
                int llllIIIIlIlllI = llllIIIIlIllII;
                WTextBox llllIIIIlIllIl = llllIIIIlIlIlI.add(llllIIIIlIlIIl.theme.textBox(llllIIIIlIlIIl.profile.loadOnJoinIps.get(llllIIIIlIlllI))).minWidth(400.0).expandX().widget();
                llllIIIIlIllIl.action = () -> {
                    EditProfileScreen llllIIIIIlIIll;
                    String llllIIIIIlIIII = llllIIIIlIllIl.get().trim();
                    if (StringUtils.containsWhitespace((CharSequence)llllIIIIIlIIII) || !llllIIIIIlIIII.contains(".")) {
                        return;
                    }
                    llllIIIIIlIIll.profile.loadOnJoinIps.set(llllIIIIlIlllI, llllIIIIIlIIII);
                };
                if (llllIIIIlIlllI != llllIIIIlIlIIl.profile.loadOnJoinIps.size() - 1) {
                    WMinus llllIIIIllIIII = llllIIIIlIlIlI.add(llllIIIIlIlIIl.theme.minus()).widget();
                    llllIIIIllIIII.action = () -> {
                        EditProfileScreen llllIIIIIllIIl;
                        llllIIIIIllIIl.profile.loadOnJoinIps.remove(llllIIIIlIlllI);
                        llllIIIIIllIIl.clear();
                        llllIIIIIllIIl.initWidgets();
                    };
                } else {
                    WPlus llllIIIIlIllll = llllIIIIlIlIlI.add(llllIIIIlIlIIl.theme.plus()).widget();
                    llllIIIIlIllll.action = () -> {
                        EditProfileScreen llllIIIIIllllI;
                        llllIIIIIllllI.profile.loadOnJoinIps.add("");
                        llllIIIIIllllI.clear();
                        llllIIIIIllllI.initWidgets();
                    };
                }
                llllIIIIlIlIlI.row();
            }
        }

        public EditProfileScreen(GuiTheme llllIIIlIllIlI, Profile llllIIIlIlllIl, Runnable llllIIIlIlllII) {
            super(llllIIIlIllIlI, llllIIIlIlllIl == null ? "New Profile" : "Edit Profile");
            EditProfileScreen llllIIIlIlllll;
            llllIIIlIlllll.newProfile = llllIIIlIlllIl == null;
            llllIIIlIlllll.profile = llllIIIlIlllll.newProfile ? new Profile() : llllIIIlIlllIl;
            llllIIIlIlllll.action = llllIIIlIlllII;
            llllIIIlIlllll.initWidgets();
        }

        @Override
        protected void onClosed() {
            EditProfileScreen llllIIIIlIIIIl;
            if (llllIIIIlIIIIl.action != null) {
                llllIIIIlIIIIl.action.run();
            }
        }

        public void initWidgets() {
            EditProfileScreen llllIIIlIIIIIl;
            WTable llllIIIlIIlIll = llllIIIlIIIIIl.add(llllIIIlIIIIIl.theme.table()).expandX().widget();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Name:"));
            WTextBox llllIIIlIIlIlI = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.textBox(llllIIIlIIIIIl.newProfile ? "" : llllIIIlIIIIIl.profile.name)).minWidth(400.0).expandX().widget();
            llllIIIlIIlIlI.action = () -> {
                lllIllllIlIlll.profile.name = llllIIIlIIlIlI.get().trim();
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.horizontalSeparator()).expandX();
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Load on Launch:"));
            WCheckbox llllIIIlIIlIIl = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.onLaunch)).widget();
            llllIIIlIIlIIl.action = () -> {
                lllIllllIllIll.profile.onLaunch = lllIllllIllIlI.checked;
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Load when Joining:"));
            WTable llllIIIlIIlIII = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.table()).widget();
            llllIIIlIIIIIl.fillTable(llllIIIlIIlIII);
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.horizontalSeparator()).expandX();
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Accounts:"));
            WCheckbox llllIIIlIIIlll = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.accounts)).widget();
            llllIIIlIIIlll.action = () -> {
                EditProfileScreen lllIlllllIIIIl;
                lllIlllllIIIIl.profile.accounts = lllIlllllIIIII.checked;
                if (lllIlllllIIIIl.newProfile) {
                    return;
                }
                if (lllIlllllIIIIl.profile.accounts) {
                    lllIlllllIIIIl.profile.save(Accounts.get());
                } else {
                    lllIlllllIIIIl.profile.delete(Accounts.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Config:"));
            WCheckbox llllIIIlIIIllI = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.config)).widget();
            llllIIIlIIIllI.action = () -> {
                EditProfileScreen lllIlllllIIlll;
                lllIlllllIIlll.profile.config = lllIlllllIIllI.checked;
                if (lllIlllllIIlll.newProfile) {
                    return;
                }
                if (lllIlllllIIlll.profile.config) {
                    lllIlllllIIlll.profile.save(Config.get());
                } else {
                    lllIlllllIIlll.profile.delete(Config.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Friends:"));
            WCheckbox llllIIIlIIIlIl = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.friends)).widget();
            llllIIIlIIIlIl.action = () -> {
                EditProfileScreen lllIlllllIllIl;
                lllIlllllIllIl.profile.friends = lllIlllllIllII.checked;
                if (lllIlllllIllIl.newProfile) {
                    return;
                }
                if (lllIlllllIllIl.profile.friends) {
                    lllIlllllIllIl.profile.save(Friends.get());
                } else {
                    lllIlllllIllIl.profile.delete(Friends.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Macros:"));
            WCheckbox llllIIIlIIIlII = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.macros)).widget();
            llllIIIlIIIlII.action = () -> {
                EditProfileScreen lllIllllllIlIl;
                lllIllllllIlIl.profile.macros = lllIllllllIlII.checked;
                if (lllIllllllIlIl.newProfile) {
                    return;
                }
                if (lllIllllllIlIl.profile.macros) {
                    lllIllllllIlIl.profile.save(Macros.get());
                } else {
                    lllIllllllIlIl.profile.delete(Macros.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Modules:"));
            WCheckbox llllIIIlIIIIll = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.modules)).widget();
            llllIIIlIIIIll.action = () -> {
                EditProfileScreen lllIlllllllIIl;
                lllIlllllllIIl.profile.modules = lllIlllllllIlI.checked;
                if (lllIlllllllIIl.newProfile) {
                    return;
                }
                if (lllIlllllllIIl.profile.modules) {
                    lllIlllllllIIl.profile.save(Modules.get());
                } else {
                    lllIlllllllIIl.profile.delete(Modules.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.label("Waypoints:"));
            WCheckbox llllIIIlIIIIlI = llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.checkbox(llllIIIlIIIIIl.profile.waypoints)).widget();
            llllIIIlIIIIlI.action = () -> {
                EditProfileScreen llllIIIIIIIIIl;
                llllIIIIIIIIIl.profile.waypoints = llllIIIIIIIIII.checked;
                if (llllIIIIIIIIIl.newProfile) {
                    return;
                }
                if (llllIIIIIIIIIl.profile.waypoints) {
                    llllIIIIIIIIIl.profile.save(Waypoints.get());
                } else {
                    llllIIIIIIIIIl.profile.delete(Waypoints.get());
                }
            };
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.horizontalSeparator()).expandX();
            llllIIIlIIlIll.row();
            llllIIIlIIlIll.add(llllIIIlIIIIIl.theme.button((String)"Save")).expandX().widget().action = () -> {
                EditProfileScreen llllIIIIIIIlll;
                if (llllIIIIIIIlll.profile.name == null || llllIIIIIIIlll.profile.name.isEmpty()) {
                    return;
                }
                for (Profile llllIIIIIIlIII : Profiles.get()) {
                    if (llllIIIIIIIlll.profile == llllIIIIIIlIII || !llllIIIIIIIlll.profile.name.equalsIgnoreCase(llllIIIIIIlIII.name)) continue;
                    return;
                }
                if (llllIIIIIIIlll.newProfile) {
                    Profiles.get().add(llllIIIIIIIlll.profile);
                } else {
                    Profiles.get().save();
                }
                llllIIIIIIIlll.onClose();
            };
        }
    }

    private static class ProfilesScreen
    extends WindowTabScreen {
        @Override
        protected void init() {
            ProfilesScreen lllllllllllllllllIIllIlllIllIIIl;
            super.init();
            lllllllllllllllllIIllIlllIllIIIl.initWidget();
        }

        public ProfilesScreen(GuiTheme lllllllllllllllllIIllIlllIllIlIl, Tab lllllllllllllllllIIllIlllIllIlII) {
            super(lllllllllllllllllIIllIlllIllIlIl, lllllllllllllllllIIllIlllIllIlII);
            ProfilesScreen lllllllllllllllllIIllIlllIllIllI;
        }

        private void initWidget() {
            ProfilesScreen lllllllllllllllllIIllIlllIlIIIII;
            lllllllllllllllllIIllIlllIlIIIII.clear();
            WTable lllllllllllllllllIIllIlllIlIIIlI = lllllllllllllllllIIllIlllIlIIIII.add(lllllllllllllllllIIllIlllIlIIIII.theme.table()).expandX().minWidth(300.0).widget();
            for (Profile lllllllllllllllllIIllIlllIlIIlII : Profiles.get()) {
                lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.label(lllllllllllllllllIIllIlllIlIIlII.name)).expandCellX();
                WButton lllllllllllllllllIIllIlllIlIlIII = lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.button("Save")).widget();
                lllllllllllllllllIIllIlllIlIlIII.action = lllllllllllllllllIIllIlllIlIIlII::save;
                WButton lllllllllllllllllIIllIlllIlIIlll = lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.button("Load")).widget();
                lllllllllllllllllIIllIlllIlIIlll.action = lllllllllllllllllIIllIlllIlIIlII::load;
                WButton lllllllllllllllllIIllIlllIlIIllI = lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.button(GuiRenderer.EDIT)).widget();
                lllllllllllllllllIIllIlllIlIIllI.action = () -> {
                    ProfilesScreen lllllllllllllllllIIllIlllIIIlIll;
                    Utils.mc.openScreen((Screen)new EditProfileScreen(lllllllllllllllllIIllIlllIIIlIll.theme, lllllllllllllllllIIllIlllIlIIlII, lllllllllllllllllIIllIlllIIIlIll::initWidget));
                };
                WMinus lllllllllllllllllIIllIlllIlIIlIl = lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.minus()).widget();
                lllllllllllllllllIIllIlllIlIIlIl.action = () -> {
                    ProfilesScreen lllllllllllllllllIIllIlllIIlIIIl;
                    Profiles.get().remove(lllllllllllllllllIIllIlllIlIIlII);
                    lllllllllllllllllIIllIlllIIlIIIl.initWidget();
                };
                lllllllllllllllllIIllIlllIlIIIlI.row();
            }
            lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.horizontalSeparator()).expandX();
            lllllllllllllllllIIllIlllIlIIIlI.row();
            WButton lllllllllllllllllIIllIlllIlIIIIl = lllllllllllllllllIIllIlllIlIIIlI.add(lllllllllllllllllIIllIlllIlIIIII.theme.button("Create")).expandX().widget();
            lllllllllllllllllIIllIlllIlIIIIl.action = () -> {
                ProfilesScreen lllllllllllllllllIIllIlllIIlIlll;
                Utils.mc.openScreen((Screen)new EditProfileScreen(lllllllllllllllllIIllIlllIIlIlll.theme, null, lllllllllllllllllIIllIlllIIlIlll::initWidget));
            };
        }
    }
}

