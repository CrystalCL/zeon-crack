/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets;

import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.network.OnlinePlayers;
import minegame159.meteorclient.utils.render.color.Color;

public abstract class WAccount
extends WHorizontalList {
    public /* synthetic */ Runnable refreshScreenAction;
    private final /* synthetic */ Account<?> account;
    private final /* synthetic */ WidgetScreen screen;

    protected abstract Color accountTypeColor();

    protected abstract Color loggedInColor();

    @Override
    public void init() {
        WAccount llIllllIlIIlIl;
        llIllllIlIIlIl.add(llIllllIlIIlIl.theme.texture(32.0, 32.0, 90.0, llIllllIlIIlIl.account.getCache().getHeadTexture()));
        WLabel llIllllIlIIlII = llIllllIlIIlIl.add(llIllllIlIIlIl.theme.label(llIllllIlIIlIl.account.getUsername())).widget();
        if (Utils.mc.getSession().getUsername().equalsIgnoreCase(llIllllIlIIlIl.account.getUsername())) {
            llIllllIlIIlII.color = llIllllIlIIlIl.loggedInColor();
        }
        WLabel llIllllIlIIIll = llIllllIlIIlIl.add(llIllllIlIIlIl.theme.label(String.valueOf(new StringBuilder().append("(").append((Object)llIllllIlIIlIl.account.getType()).append(")")))).expandCellX().right().widget();
        llIllllIlIIIll.color = llIllllIlIIlIl.accountTypeColor();
        WButton llIllllIlIIIlI = llIllllIlIIlIl.add(llIllllIlIIlIl.theme.button("Login")).widget();
        llIllllIlIIIlI.action = () -> {
            WAccount llIllllIIlIlIl;
            llIllllIIlIlII.minWidth = llIllllIIlIlII.width;
            llIllllIlIIIlI.set("...");
            llIllllIIlIlIl.screen.locked = true;
            MeteorExecutor.execute(() -> {
                WAccount llIllllIIIllII;
                if (llIllllIIIllII.account.login()) {
                    llIllllIlIIlII.set(llIllllIIIllII.account.getUsername());
                    Accounts.get().save();
                    OnlinePlayers.forcePing();
                    llIllllIIIllII.screen.taskAfterRender = llIllllIIIllII.refreshScreenAction;
                }
                llIllllIIIIlll.minWidth = 0.0;
                llIllllIlIIIlI.set("Login");
                llIllllIIIllII.screen.locked = false;
            });
        };
        WMinus llIllllIlIIIIl = llIllllIlIIlIl.add(llIllllIlIIlIl.theme.minus()).widget();
        llIllllIlIIIIl.action = () -> {
            WAccount llIllllIIllIIl;
            Accounts.get().remove(llIllllIIllIIl.account);
            if (llIllllIIllIIl.refreshScreenAction != null) {
                llIllllIIllIIl.refreshScreenAction.run();
            }
        };
    }

    public WAccount(WidgetScreen llIllllIlIllII, Account<?> llIllllIlIlllI) {
        WAccount llIllllIllIIII;
        llIllllIllIIII.screen = llIllllIlIllII;
        llIllllIllIIII.account = llIllllIlIlllI;
    }
}

