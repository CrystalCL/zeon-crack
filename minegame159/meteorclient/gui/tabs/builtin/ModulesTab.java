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
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab
extends Tab {
    public ModulesTab() {
        super("Modules");
        ModulesTab llllllllllllllllIlIlllllIIllIlll;
    }

    @Override
    public boolean isScreen(Screen llllllllllllllllIlIlllllIIlIllll) {
        return GuiThemes.get().isModulesScreen(llllllllllllllllIlIlllllIIlIllll);
    }

    @Override
    public TabScreen createScreen(GuiTheme llllllllllllllllIlIlllllIIllIIlI) {
        return llllllllllllllllIlIlllllIIllIIlI.modulesScreen();
    }
}

