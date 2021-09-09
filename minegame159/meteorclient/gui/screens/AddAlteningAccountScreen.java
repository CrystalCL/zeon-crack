/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.screens;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.screens.AccountsScreen;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.systems.accounts.types.TheAlteningAccount;

public class AddAlteningAccountScreen
extends WindowScreen {
    public AddAlteningAccountScreen(GuiTheme lIllIlIlIlIlll) {
        super(lIllIlIlIlIlll, "Add The Altening Account");
        AddAlteningAccountScreen lIllIlIlIllIII;
        WTable lIllIlIlIllIll = lIllIlIlIllIII.add(lIllIlIlIlIlll.table()).widget();
        lIllIlIlIllIll.add(lIllIlIlIlIlll.label("Token: "));
        WTextBox lIllIlIlIllIlI = lIllIlIlIllIll.add(lIllIlIlIlIlll.textBox("")).minWidth(400.0).expandX().widget();
        lIllIlIlIllIlI.setFocused(true);
        lIllIlIlIllIll.row();
        WButton lIllIlIlIllIIl = lIllIlIlIllIll.add(lIllIlIlIlIlll.button("Add")).expandX().widget();
        lIllIlIlIllIIl.action = () -> {
            if (!lIllIlIlIllIlI.get().isEmpty()) {
                AddAlteningAccountScreen lIllIlIlIlIIII;
                AccountsScreen.addAccount(lIllIlIlIllIIl, lIllIlIlIlIIII, new TheAlteningAccount(lIllIlIlIllIlI.get()));
            }
        };
    }
}

