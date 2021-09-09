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
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.systems.accounts.types.CrackedAccount;

public class AddCrackedAccountScreen
extends WindowScreen {
    public AddCrackedAccountScreen(GuiTheme lllllllllllllllllIlIllIIIllIlIlI) {
        super(lllllllllllllllllIlIllIIIllIlIlI, "Add Cracked Account");
        AddCrackedAccountScreen lllllllllllllllllIlIllIIIllIlIll;
        WTable lllllllllllllllllIlIllIIIllIlIIl = lllllllllllllllllIlIllIIIllIlIll.add(lllllllllllllllllIlIllIIIllIlIlI.table()).widget();
        lllllllllllllllllIlIllIIIllIlIIl.add(lllllllllllllllllIlIllIIIllIlIlI.label("Name: "));
        WTextBox lllllllllllllllllIlIllIIIllIlIII = lllllllllllllllllIlIllIIIllIlIIl.add(lllllllllllllllllIlIllIIIllIlIlI.textBox("")).minWidth(400.0).expandX().widget();
        lllllllllllllllllIlIllIIIllIlIII.setFocused(true);
        lllllllllllllllllIlIllIIIllIlIIl.row();
        WButton lllllllllllllllllIlIllIIIllIIlll = lllllllllllllllllIlIllIIIllIlIIl.add(lllllllllllllllllIlIllIIIllIlIlI.button("Add")).expandX().widget();
        lllllllllllllllllIlIllIIIllIIlll.action = () -> {
            CrackedAccount lllllllllllllllllIlIllIIIlIllIlI = new CrackedAccount(lllllllllllllllllIlIllIIIllIlIII.get());
            if (!lllllllllllllllllIlIllIIIllIlIII.get().trim().isEmpty() && !Accounts.get().exists(lllllllllllllllllIlIllIIIlIllIlI)) {
                AddCrackedAccountScreen lllllllllllllllllIlIllIIIlIllIIl;
                AccountsScreen.addAccount(lllllllllllllllllIlIllIIIllIIlll, lllllllllllllllllIlIllIIIlIllIIl, lllllllllllllllllIlIllIIIlIllIlI);
            }
        };
    }
}

