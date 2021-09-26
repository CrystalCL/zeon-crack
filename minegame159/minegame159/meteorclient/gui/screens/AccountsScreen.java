/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.screens.AddAlteningAccountScreen;
import minegame159.meteorclient.gui.screens.AddCrackedAccountScreen;
import minegame159.meteorclient.gui.screens.AddPremiumAccountScreen;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorAccount;
import minegame159.meteorclient.gui.widgets.WAccount;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import net.minecraft.client.gui.screen.Screen;

public class AccountsScreen
extends WindowScreen {
    private void lambda$initWidgets$1() {
        Utils.mc.openScreen((Screen)new AddCrackedAccountScreen(this.theme));
    }

    private void initWidgets() {
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = this.add(new WMeteorAccount(this, account)).expandX().widget();
            wAccount.refreshScreenAction = this::lambda$initWidgets$0;
        }
        WHorizontalList wHorizontalList = this.add(this.theme.horizontalList()).expandX().widget();
        this.addButton(wHorizontalList, "Cracked", this::lambda$initWidgets$1);
        this.addButton(wHorizontalList, "Premium", this::lambda$initWidgets$2);
        this.addButton(wHorizontalList, "The Altening", this::lambda$initWidgets$3);
    }

    @Override
    protected void init() {
        super.init();
        this.clear();
        this.initWidgets();
    }

    private void lambda$initWidgets$3() {
        Utils.mc.openScreen((Screen)new AddAlteningAccountScreen(this.theme));
    }

    public AccountsScreen(GuiTheme guiTheme) {
        super(guiTheme, "Accounts");
    }

    public static void addAccount(WButton wButton, WidgetScreen widgetScreen, Account<?> account) {
        wButton.set("...");
        widgetScreen.locked = true;
        MeteorExecutor.execute(() -> AccountsScreen.lambda$addAccount$4(account, widgetScreen, wButton));
    }

    private void lambda$initWidgets$2() {
        Utils.mc.openScreen((Screen)new AddPremiumAccountScreen(this.theme));
    }

    private void addButton(WContainer wContainer, String string, Runnable runnable) {
        WButton wButton = wContainer.add(this.theme.button(string)).expandX().widget();
        wButton.action = runnable;
    }

    private void lambda$initWidgets$0() {
        this.clear();
        this.initWidgets();
    }

    private static void lambda$addAccount$4(Account account, WidgetScreen widgetScreen, WButton wButton) {
        if (account.fetchInfo() && account.fetchHead()) {
            Accounts.get().add(account);
            widgetScreen.locked = false;
            widgetScreen.onClose();
        }
        wButton.set("Add");
        widgetScreen.locked = false;
    }
}

