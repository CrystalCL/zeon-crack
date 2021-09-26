/*
 * Decompiled with CFR 0.151.
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
    public GuiTab() {
        super("GUI");
    }

    @Override
    public boolean isScreen(Screen Screen2) {
        return Screen2 instanceof GuiScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme guiTheme) {
        return new GuiScreen(guiTheme, this);
    }

    private static class GuiScreen
    extends WindowTabScreen {
        public GuiScreen(GuiTheme guiTheme, Tab tab) {
            super(guiTheme, tab);
            WTable wTable = this.add(guiTheme.table()).expandX().widget();
            wTable.add(guiTheme.label("Theme:"));
            WDropdown<String> wDropdown = wTable.add(guiTheme.dropdown(GuiThemes.getNames(), GuiThemes.get().name)).widget();
            wDropdown.action = () -> GuiScreen.lambda$new$0(wDropdown, tab);
            guiTheme.settings.onActivated();
            this.add(guiTheme.settings(guiTheme.settings)).expandX();
        }

        private static void lambda$new$0(WDropdown wDropdown, Tab tab) {
            GuiThemes.select((String)wDropdown.get());
            Utils.mc.openScreen(null);
            tab.openScreen(GuiThemes.get());
        }
    }
}

