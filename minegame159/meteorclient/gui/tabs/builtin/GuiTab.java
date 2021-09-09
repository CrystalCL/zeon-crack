/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.tabs.builtin;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;

public class GuiTab
extends Tab {
    @Override
    public boolean isScreen(Screen lllIllIIIllI) {
        return lllIllIIIllI instanceof GuiScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme lllIllIIllII) {
        GuiTab lllIllIIllIl;
        return new GuiScreen(lllIllIIllII, lllIllIIllIl);
    }

    public GuiTab() {
        super("GUI");
        GuiTab lllIllIlIIII;
    }

    private static class GuiScreen
    extends WindowTabScreen {
        public GuiScreen(GuiTheme lIIllllllllIlI, Tab lIIllllllllIIl) {
            super(lIIllllllllIlI, lIIllllllllIIl);
            GuiScreen lIIllllllllIll;
            WTable lIIllllllllIII = lIIllllllllIll.add(lIIllllllllIlI.table()).expandX().widget();
            lIIllllllllIII.add(lIIllllllllIlI.label("Theme:"));
            WDropdown<String> lIIlllllllIlll = lIIllllllllIII.add(lIIllllllllIlI.dropdown(GuiThemes.getNames(), GuiThemes.get().name)).widget();
            lIIlllllllIlll.action = () -> {
                GuiThemes.select((String)lIIlllllllIlll.get());
                Utils.mc.openScreen(null);
                lIIllllllllIIl.openScreen(GuiThemes.get());
            };
            lIIllllllllIlI.settings.onActivated();
            lIIllllllllIll.add(lIIllllllllIlI.settings(lIIllllllllIlI.settings)).expandX();
        }
    }
}

