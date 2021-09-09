/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WWindow;

public abstract class WindowScreen
extends WidgetScreen {
    private final /* synthetic */ WWindow window;

    @Override
    public <W extends WWidget> Cell<W> add(W llllllllllllllllllllIIlIIIIIIIlI) {
        WindowScreen llllllllllllllllllllIIlIIIIIIlIl;
        return llllllllllllllllllllIIlIIIIIIlIl.window.add(llllllllllllllllllllIIlIIIIIIIlI);
    }

    public WindowScreen(GuiTheme llllllllllllllllllllIIlIIIIIlIIl, String llllllllllllllllllllIIlIIIIIlIII) {
        super(llllllllllllllllllllIIlIIIIIlIIl, llllllllllllllllllllIIlIIIIIlIII);
        WindowScreen llllllllllllllllllllIIlIIIIIllIl;
        llllllllllllllllllllIIlIIIIIllIl.window = super.add(llllllllllllllllllllIIlIIIIIlIIl.window(llllllllllllllllllllIIlIIIIIlIII)).center().widget();
    }

    @Override
    public void clear() {
        WindowScreen llllllllllllllllllllIIlIIIIIIIII;
        llllllllllllllllllllIIlIIIIIIIII.window.clear();
    }
}

