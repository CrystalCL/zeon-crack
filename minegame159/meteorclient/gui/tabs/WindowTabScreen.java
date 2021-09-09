/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.tabs;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WWindow;

public class WindowTabScreen
extends TabScreen {
    private final /* synthetic */ WWindow window;

    @Override
    public void clear() {
        WindowTabScreen llllllllllllllllIllllllIIllIlIll;
        llllllllllllllllIllllllIIllIlIll.window.clear();
    }

    @Override
    public <W extends WWidget> Cell<W> add(W llllllllllllllllIllllllIIllIllll) {
        WindowTabScreen llllllllllllllllIllllllIIllIlllI;
        return llllllllllllllllIllllllIIllIlllI.window.add(llllllllllllllllIllllllIIllIllll);
    }

    public WindowTabScreen(GuiTheme llllllllllllllllIllllllIIlllIlII, Tab llllllllllllllllIllllllIIlllIIll) {
        super(llllllllllllllllIllllllIIlllIlII, llllllllllllllllIllllllIIlllIIll);
        WindowTabScreen llllllllllllllllIllllllIIlllIlIl;
        llllllllllllllllIllllllIIlllIlIl.window = super.add(llllllllllllllllIllllllIIlllIlII.window(llllllllllllllllIllllllIIlllIIll.name)).center().widget();
    }
}

