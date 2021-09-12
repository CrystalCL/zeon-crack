/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.tabs.builtin;

import java.util.Objects;
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
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.macros.Macros;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.profiles.Profile;
import minegame159.meteorclient.systems.profiles.Profiles;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.class_437;
import org.apache.commons.lang3.StringUtils;

public class ProfilesTab
extends Tab {
    @Override
    public boolean isScreen(class_437 class_4372) {
        return class_4372 instanceof ProfilesScreen;
    }

    @Override
    protected TabScreen createScreen(GuiTheme guiTheme) {
        return new ProfilesScreen(guiTheme, this);
    }

    public ProfilesTab() {
        super("Profiles");
    }

    private static class ProfilesScreen
    extends WindowTabScreen {
        private void initWidget() {
            this.clear();
            WTable wTable = this.add(this.theme.table()).expandX().minWidth(300.0).widget();
            for (Profile profile : Profiles.get()) {
                wTable.add(this.theme.label(profile.name)).expandCellX();
                WButton wButton = wTable.add(this.theme.button("Save")).widget();
                Objects.requireNonNull(profile);
                wButton.action = profile::save;
                WButton wButton2 = wTable.add(this.theme.button("Load")).widget();
                Objects.requireNonNull(profile);
                wButton2.action = profile::load;
                WButton wButton3 = wTable.add(this.theme.button(GuiRenderer.EDIT)).widget();
                wButton3.action = () -> this.lambda$initWidget$0(profile);
                WMinus wMinus = wTable.add(this.theme.minus()).widget();
                wMinus.action = () -> this.lambda$initWidget$1(profile);
                wTable.row();
            }
            wTable.add(this.theme.horizontalSeparator()).expandX();
            wTable.row();
            WButton wButton = wTable.add(this.theme.button("Create")).expandX().widget();
            wButton.action = this::lambda$initWidget$2;
        }

        @Override
        protected void method_25426() {
            super.method_25426();
            this.initWidget();
        }

        public ProfilesScreen(GuiTheme guiTheme, Tab tab) {
            super(guiTheme, tab);
        }

        private void lambda$initWidget$1(Profile profile) {
            Profiles.get().remove(profile);
            this.initWidget();
        }

        private void lambda$initWidget$2() {
            Utils.mc.method_1507((class_437)new EditProfileScreen(this.theme, null, this::initWidget));
        }

        private void lambda$initWidget$0(Profile profile) {
            Utils.mc.method_1507((class_437)new EditProfileScreen(this.theme, profile, this::initWidget));
        }
    }

    private static class EditProfileScreen
    extends WindowScreen {
        private final Runnable action;
        private final Profile profile;
        private final boolean newProfile;

        private void lambda$initWidgets$4(WCheckbox wCheckbox) {
            this.profile.friends = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.friends) {
                this.profile.save(Friends.get());
            } else {
                this.profile.delete(Friends.get());
            }
        }

        private void lambda$initWidgets$5(WCheckbox wCheckbox) {
            this.profile.macros = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.macros) {
                this.profile.save(Macros.get());
            } else {
                this.profile.delete(Macros.get());
            }
        }

        private void lambda$fillTable$10(int n) {
            this.profile.loadOnJoinIps.remove(n);
            this.clear();
            this.initWidgets();
        }

        private void lambda$fillTable$9(WTextBox wTextBox, int n) {
            String string = wTextBox.get().trim();
            if (StringUtils.containsWhitespace((CharSequence)string) || !string.contains(".")) {
                return;
            }
            this.profile.loadOnJoinIps.set(n, string);
        }

        private void lambda$initWidgets$1(WCheckbox wCheckbox) {
            this.profile.onLaunch = wCheckbox.checked;
        }

        public void initWidgets() {
            WTable wTable = this.add(this.theme.table()).expandX().widget();
            wTable.add(this.theme.label("Name:"));
            WTextBox wTextBox = wTable.add(this.theme.textBox(this.newProfile ? "" : this.profile.name)).minWidth(400.0).expandX().widget();
            wTextBox.action = () -> this.lambda$initWidgets$0(wTextBox);
            wTable.row();
            wTable.add(this.theme.horizontalSeparator()).expandX();
            wTable.row();
            wTable.add(this.theme.label("Load on Launch:"));
            WCheckbox wCheckbox = wTable.add(this.theme.checkbox(this.profile.onLaunch)).widget();
            wCheckbox.action = () -> this.lambda$initWidgets$1(wCheckbox);
            wTable.row();
            wTable.add(this.theme.label("Load when Joining:"));
            WTable wTable2 = wTable.add(this.theme.table()).widget();
            this.fillTable(wTable2);
            wTable.row();
            wTable.add(this.theme.horizontalSeparator()).expandX();
            wTable.row();
            wTable.add(this.theme.label("Accounts:"));
            WCheckbox wCheckbox2 = wTable.add(this.theme.checkbox(this.profile.accounts)).widget();
            wCheckbox2.action = () -> this.lambda$initWidgets$2(wCheckbox2);
            wTable.row();
            wTable.add(this.theme.label("Config:"));
            WCheckbox wCheckbox3 = wTable.add(this.theme.checkbox(this.profile.config)).widget();
            wCheckbox3.action = () -> this.lambda$initWidgets$3(wCheckbox3);
            wTable.row();
            wTable.add(this.theme.label("Friends:"));
            WCheckbox wCheckbox4 = wTable.add(this.theme.checkbox(this.profile.friends)).widget();
            wCheckbox4.action = () -> this.lambda$initWidgets$4(wCheckbox4);
            wTable.row();
            wTable.add(this.theme.label("Macros:"));
            WCheckbox wCheckbox5 = wTable.add(this.theme.checkbox(this.profile.macros)).widget();
            wCheckbox5.action = () -> this.lambda$initWidgets$5(wCheckbox5);
            wTable.row();
            wTable.add(this.theme.label("Modules:"));
            WCheckbox wCheckbox6 = wTable.add(this.theme.checkbox(this.profile.modules)).widget();
            wCheckbox6.action = () -> this.lambda$initWidgets$6(wCheckbox6);
            wTable.row();
            wTable.add(this.theme.label("Waypoints:"));
            WCheckbox wCheckbox7 = wTable.add(this.theme.checkbox(this.profile.waypoints)).widget();
            wCheckbox7.action = () -> this.lambda$initWidgets$7(wCheckbox7);
            wTable.row();
            wTable.add(this.theme.horizontalSeparator()).expandX();
            wTable.row();
            wTable.add(this.theme.button((String)"Save")).expandX().widget().action = this::lambda$initWidgets$8;
        }

        private void lambda$initWidgets$3(WCheckbox wCheckbox) {
            this.profile.config = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.config) {
                this.profile.save(Config.get());
            } else {
                this.profile.delete(Config.get());
            }
        }

        private void lambda$initWidgets$8() {
            if (this.profile.name == null || this.profile.name.isEmpty()) {
                return;
            }
            for (Profile profile : Profiles.get()) {
                if (this.profile == profile || !this.profile.name.equalsIgnoreCase(profile.name)) continue;
                return;
            }
            if (this.newProfile) {
                Profiles.get().add(this.profile);
            } else {
                Profiles.get().save();
            }
            this.method_25419();
        }

        public EditProfileScreen(GuiTheme guiTheme, Profile profile, Runnable runnable) {
            super(guiTheme, profile == null ? "New Profile" : "Edit Profile");
            this.newProfile = profile == null;
            this.profile = this.newProfile ? new Profile() : profile;
            this.action = runnable;
            this.initWidgets();
        }

        @Override
        protected void onClosed() {
            if (this.action != null) {
                this.action.run();
            }
        }

        private void lambda$initWidgets$6(WCheckbox wCheckbox) {
            this.profile.modules = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.modules) {
                this.profile.save(Modules.get());
            } else {
                this.profile.delete(Modules.get());
            }
        }

        private void lambda$initWidgets$7(WCheckbox wCheckbox) {
            this.profile.waypoints = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.waypoints) {
                this.profile.save(Waypoints.get());
            } else {
                this.profile.delete(Waypoints.get());
            }
        }

        private void lambda$fillTable$11() {
            this.profile.loadOnJoinIps.add("");
            this.clear();
            this.initWidgets();
        }

        private void fillTable(WTable wTable) {
            if (this.profile.loadOnJoinIps.isEmpty()) {
                this.profile.loadOnJoinIps.add("");
            }
            for (int i = 0; i < this.profile.loadOnJoinIps.size(); ++i) {
                WPressable wPressable;
                int n = i;
                WTextBox wTextBox = wTable.add(this.theme.textBox(this.profile.loadOnJoinIps.get(n))).minWidth(400.0).expandX().widget();
                wTextBox.action = () -> this.lambda$fillTable$9(wTextBox, n);
                if (n != this.profile.loadOnJoinIps.size() - 1) {
                    wPressable = wTable.add(this.theme.minus()).widget();
                    wPressable.action = () -> this.lambda$fillTable$10(n);
                } else {
                    wPressable = wTable.add(this.theme.plus()).widget();
                    ((WPlus)wPressable).action = this::lambda$fillTable$11;
                }
                wTable.row();
                if (false <= true) continue;
                return;
            }
        }

        private void lambda$initWidgets$2(WCheckbox wCheckbox) {
            this.profile.accounts = wCheckbox.checked;
            if (this.newProfile) {
                return;
            }
            if (this.profile.accounts) {
                this.profile.save(Accounts.get());
            } else {
                this.profile.delete(Accounts.get());
            }
        }

        private void lambda$initWidgets$0(WTextBox wTextBox) {
            this.profile.name = wTextBox.get().trim();
        }
    }
}

