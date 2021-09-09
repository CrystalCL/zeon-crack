/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.widgets.WAccount;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.utils.render.color.Color;

public class WMeteorAccount
extends WAccount
implements MeteorWidget {
    @Override
    protected Color accountTypeColor() {
        WMeteorAccount lIIllIlIllllllI;
        return lIIllIlIllllllI.theme().accountTypeColor.get();
    }

    @Override
    protected Color loggedInColor() {
        WMeteorAccount lIIllIllIIIIIII;
        return lIIllIllIIIIIII.theme().loggedInColor.get();
    }

    public WMeteorAccount(WidgetScreen lIIllIllIIIIlII, Account<?> lIIllIllIIIIllI) {
        super(lIIllIllIIIIlII, lIIllIllIIIIllI);
        WMeteorAccount lIIllIllIIIlIII;
    }
}

