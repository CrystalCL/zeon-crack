/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.tabs;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;

public abstract class Tab {
    public final String name;

    public abstract boolean isScreen(Screen var1);

    protected abstract TabScreen createScreen(GuiTheme var1);

    public void openScreen(GuiTheme guiTheme) {
        TabScreen tabScreen = this.createScreen(guiTheme);
        tabScreen.addDirect(guiTheme.topBar()).top().centerX();
        Utils.mc.openScreen((Screen)tabScreen);
    }

    public Tab(String string) {
        this.name = string;
    }
}

