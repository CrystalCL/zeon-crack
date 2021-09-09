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
import minegame159.meteorclient.systems.accounts.types.PremiumAccount;

public class AddPremiumAccountScreen
extends WindowScreen {
    public AddPremiumAccountScreen(GuiTheme llIIlIlIIIlIII) {
        super(llIIlIlIIIlIII, "Add Premium Account");
        AddPremiumAccountScreen llIIlIlIIIlIIl;
        WTable llIIlIlIIIllIl = llIIlIlIIIlIIl.add(llIIlIlIIIlIII.table()).widget();
        llIIlIlIIIllIl.add(llIIlIlIIIlIII.label("Email: "));
        WTextBox llIIlIlIIIllII = llIIlIlIIIllIl.add(llIIlIlIIIlIII.textBox("")).minWidth(400.0).expandX().widget();
        llIIlIlIIIllII.setFocused(true);
        llIIlIlIIIllIl.row();
        llIIlIlIIIllIl.add(llIIlIlIIIlIII.label("Password: "));
        WTextBox llIIlIlIIIlIll = llIIlIlIIIllIl.add(llIIlIlIIIlIII.textBox("")).minWidth(400.0).expandX().widget();
        llIIlIlIIIllIl.row();
        WButton llIIlIlIIIlIlI = llIIlIlIIIllIl.add(llIIlIlIIIlIII.button("Add")).expandX().widget();
        llIIlIlIIIlIlI.action = () -> {
            PremiumAccount llIIlIIllllIlI = new PremiumAccount(llIIlIlIIIllII.get(), llIIlIlIIIlIll.get());
            if (!llIIlIlIIIllII.get().isEmpty() && !llIIlIlIIIlIll.get().isEmpty() && llIIlIlIIIllII.get().contains("@") && !Accounts.get().exists(llIIlIIllllIlI)) {
                AddPremiumAccountScreen llIIlIIllllllI;
                AccountsScreen.addAccount(llIIlIlIIIlIlI, llIIlIIllllllI, llIIlIIllllIlI);
            }
        };
    }
}

