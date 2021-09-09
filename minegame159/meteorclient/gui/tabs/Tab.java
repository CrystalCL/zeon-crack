/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.tabs;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;

public abstract class Tab {
    public final /* synthetic */ String name;

    public Tab(String llllllllllllllllllIlIllIllIIlIll) {
        Tab llllllllllllllllllIlIllIllIIlllI;
        llllllllllllllllllIlIllIllIIlllI.name = llllllllllllllllllIlIllIllIIlIll;
    }

    public void openScreen(GuiTheme llllllllllllllllllIlIllIllIIIIll) {
        Tab llllllllllllllllllIlIllIllIIIlll;
        TabScreen llllllllllllllllllIlIllIllIIIlIl = llllllllllllllllllIlIllIllIIIlll.createScreen(llllllllllllllllllIlIllIllIIIIll);
        llllllllllllllllllIlIllIllIIIlIl.addDirect(llllllllllllllllllIlIllIllIIIIll.topBar()).top().centerX();
        Utils.mc.openScreen((Screen)llllllllllllllllllIlIllIllIIIlIl);
    }

    public abstract boolean isScreen(Screen var1);

    protected abstract TabScreen createScreen(GuiTheme var1);
}

