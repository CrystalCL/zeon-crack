/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.widgets.pressable;

import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;

public abstract class WButton
extends WPressable {
    protected /* synthetic */ GuiTexture texture;
    protected /* synthetic */ String text;
    protected /* synthetic */ double textWidth;

    public void set(String llllllllllllllllllIlIIlIIlIlIlII) {
        WButton llllllllllllllllllIlIIlIIlIlIIll;
        if (llllllllllllllllllIlIIlIIlIlIIll.text == null || (double)Math.round(llllllllllllllllllIlIIlIIlIlIIll.theme.textWidth(llllllllllllllllllIlIIlIIlIlIlII)) != llllllllllllllllllIlIIlIIlIlIIll.textWidth) {
            llllllllllllllllllIlIIlIIlIlIIll.invalidate();
        }
        llllllllllllllllllIlIIlIIlIlIIll.text = llllllllllllllllllIlIIlIIlIlIlII;
    }

    @Override
    protected void onCalculateSize() {
        WButton llllllllllllllllllIlIIlIIlIllIlI;
        double llllllllllllllllllIlIIlIIlIllIll = llllllllllllllllllIlIIlIIlIllIlI.pad();
        if (llllllllllllllllllIlIIlIIlIllIlI.text != null) {
            llllllllllllllllllIlIIlIIlIllIlI.textWidth = llllllllllllllllllIlIIlIIlIllIlI.theme.textWidth(llllllllllllllllllIlIIlIIlIllIlI.text);
            llllllllllllllllllIlIIlIIlIllIlI.width = llllllllllllllllllIlIIlIIlIllIll + llllllllllllllllllIlIIlIIlIllIlI.textWidth + llllllllllllllllllIlIIlIIlIllIll;
            llllllllllllllllllIlIIlIIlIllIlI.height = llllllllllllllllllIlIIlIIlIllIll + llllllllllllllllllIlIIlIIlIllIlI.theme.textHeight() + llllllllllllllllllIlIIlIIlIllIll;
        } else {
            double llllllllllllllllllIlIIlIIlIlllIl = llllllllllllllllllIlIIlIIlIllIlI.theme.textHeight();
            llllllllllllllllllIlIIlIIlIllIlI.width = llllllllllllllllllIlIIlIIlIllIll + llllllllllllllllllIlIIlIIlIlllIl + llllllllllllllllllIlIIlIIlIllIll;
            llllllllllllllllllIlIIlIIlIllIlI.height = llllllllllllllllllIlIIlIIlIllIll + llllllllllllllllllIlIIlIIlIlllIl + llllllllllllllllllIlIIlIIlIllIll;
        }
    }

    public WButton(String llllllllllllllllllIlIIlIIllIIIlI, GuiTexture llllllllllllllllllIlIIlIIllIIlII) {
        WButton llllllllllllllllllIlIIlIIllIIllI;
        llllllllllllllllllIlIIlIIllIIllI.text = llllllllllllllllllIlIIlIIllIIIlI;
        llllllllllllllllllIlIIlIIllIIllI.texture = llllllllllllllllllIlIIlIIllIIlII;
    }
}

