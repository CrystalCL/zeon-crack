/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
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
    private void initWidgets() {
        AccountsScreen lllllllllllllllllIIIIlIllIIIlIlI;
        for (Account<?> lllllllllllllllllIIIIlIllIIIllIl : Accounts.get()) {
            WAccount lllllllllllllllllIIIIlIllIIIlllI = lllllllllllllllllIIIIlIllIIIlIlI.add(new WMeteorAccount(lllllllllllllllllIIIIlIllIIIlIlI, lllllllllllllllllIIIIlIllIIIllIl)).expandX().widget();
            lllllllllllllllllIIIIlIllIIIlllI.refreshScreenAction = () -> {
                AccountsScreen lllllllllllllllllIIIIlIlIlIllIll;
                lllllllllllllllllIIIIlIlIlIllIll.clear();
                lllllllllllllllllIIIIlIlIlIllIll.initWidgets();
            };
        }
        WHorizontalList lllllllllllllllllIIIIlIllIIIlIll = lllllllllllllllllIIIIlIllIIIlIlI.add(lllllllllllllllllIIIIlIllIIIlIlI.theme.horizontalList()).expandX().widget();
        lllllllllllllllllIIIIlIllIIIlIlI.addButton(lllllllllllllllllIIIIlIllIIIlIll, "Cracked", () -> {
            AccountsScreen lllllllllllllllllIIIIlIlIlIlllIl;
            Utils.mc.openScreen((Screen)new AddCrackedAccountScreen(lllllllllllllllllIIIIlIlIlIlllIl.theme));
        });
        lllllllllllllllllIIIIlIllIIIlIlI.addButton(lllllllllllllllllIIIIlIllIIIlIll, "Premium", () -> {
            AccountsScreen lllllllllllllllllIIIIlIlIllIIIIl;
            Utils.mc.openScreen((Screen)new AddPremiumAccountScreen(lllllllllllllllllIIIIlIlIllIIIIl.theme));
        });
        lllllllllllllllllIIIIlIllIIIlIlI.addButton(lllllllllllllllllIIIIlIllIIIlIll, "The Altening", () -> {
            AccountsScreen lllllllllllllllllIIIIlIlIllIIIll;
            Utils.mc.openScreen((Screen)new AddAlteningAccountScreen(lllllllllllllllllIIIIlIlIllIIIll.theme));
        });
    }

    public static void addAccount(WButton lllllllllllllllllIIIIlIlIlllIIIl, WidgetScreen lllllllllllllllllIIIIlIlIlllIIll, Account<?> lllllllllllllllllIIIIlIlIllIllll) {
        lllllllllllllllllIIIIlIlIlllIIIl.set("...");
        lllllllllllllllllIIIIlIlIlllIIll.locked = true;
        MeteorExecutor.execute(() -> {
            if (lllllllllllllllllIIIIlIlIllIllll.fetchInfo() && lllllllllllllllllIIIIlIlIllIllll.fetchHead()) {
                Accounts.get().add(lllllllllllllllllIIIIlIlIllIllll);
                lllllllllllllllllIIIIlIlIllIlIlI.locked = false;
                lllllllllllllllllIIIIlIlIlllIIll.onClose();
            }
            lllllllllllllllllIIIIlIlIlllIIIl.set("Add");
            lllllllllllllllllIIIIlIlIllIlIlI.locked = false;
        });
    }

    public AccountsScreen(GuiTheme lllllllllllllllllIIIIlIllIIlIllI) {
        super(lllllllllllllllllIIIIlIllIIlIllI, "Accounts");
        AccountsScreen lllllllllllllllllIIIIlIllIIlIlll;
    }

    private void addButton(WContainer lllllllllllllllllIIIIlIllIIIIIII, String lllllllllllllllllIIIIlIlIlllllll, Runnable lllllllllllllllllIIIIlIlIllllIIl) {
        AccountsScreen lllllllllllllllllIIIIlIllIIIIIIl;
        WButton lllllllllllllllllIIIIlIlIlllllIl = lllllllllllllllllIIIIlIllIIIIIII.add(lllllllllllllllllIIIIlIllIIIIIIl.theme.button(lllllllllllllllllIIIIlIlIlllllll)).expandX().widget();
        lllllllllllllllllIIIIlIlIlllllIl.action = lllllllllllllllllIIIIlIlIllllIIl;
    }

    @Override
    protected void init() {
        AccountsScreen lllllllllllllllllIIIIlIllIIlIlII;
        super.init();
        lllllllllllllllllIIIIlIllIIlIlII.clear();
        lllllllllllllllllIIIIlIllIIlIlII.initWidgets();
    }
}

