/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.renderer.operations;

import minegame159.meteorclient.gui.renderer.GuiRenderOperation;
import minegame159.meteorclient.rendering.text.TextRenderer;

public class TextOperation
extends GuiRenderOperation<TextOperation> {
    private /* synthetic */ String text;
    public /* synthetic */ boolean title;
    private /* synthetic */ TextRenderer renderer;

    public TextOperation() {
        TextOperation llllllllllllllllllIllIIIIIlIllII;
    }

    public TextOperation set(String llllllllllllllllllIllIIIIIlIIIIl, TextRenderer llllllllllllllllllIllIIIIIlIIlII, boolean llllllllllllllllllIllIIIIIIlllll) {
        TextOperation llllllllllllllllllIllIIIIIlIIllI;
        llllllllllllllllllIllIIIIIlIIllI.text = llllllllllllllllllIllIIIIIlIIIIl;
        llllllllllllllllllIllIIIIIlIIllI.renderer = llllllllllllllllllIllIIIIIlIIlII;
        llllllllllllllllllIllIIIIIlIIllI.title = llllllllllllllllllIllIIIIIIlllll;
        return llllllllllllllllllIllIIIIIlIIllI;
    }

    @Override
    protected void onRun() {
        TextOperation llllllllllllllllllIllIIIIIIlllIl;
        llllllllllllllllllIllIIIIIIlllIl.renderer.render(llllllllllllllllllIllIIIIIIlllIl.text, llllllllllllllllllIllIIIIIIlllIl.x, llllllllllllllllllIllIIIIIIlllIl.y, llllllllllllllllllIllIIIIIIlllIl.color);
    }
}

