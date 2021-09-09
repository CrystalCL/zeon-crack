/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.misc.text;

import java.util.Objects;
import minegame159.meteorclient.utils.render.color.Color;

public class ColoredText {
    private final /* synthetic */ String text;
    private final /* synthetic */ Color color;

    public boolean equals(Object llllllllllllllllllIlIlIIIllIllII) {
        ColoredText llllllllllllllllllIlIlIIIllIllIl;
        if (llllllllllllllllllIlIlIIIllIllIl == llllllllllllllllllIlIlIIIllIllII) {
            return true;
        }
        if (llllllllllllllllllIlIlIIIllIllII == null || llllllllllllllllllIlIlIIIllIllIl.getClass() != llllllllllllllllllIlIlIIIllIllII.getClass()) {
            return false;
        }
        ColoredText llllllllllllllllllIlIlIIIllIlIll = (ColoredText)llllllllllllllllllIlIlIIIllIllII;
        return llllllllllllllllllIlIlIIIllIllIl.text.equals(llllllllllllllllllIlIlIIIllIlIll.text) && llllllllllllllllllIlIlIIIllIllIl.color.equals(llllllllllllllllllIlIlIIIllIlIll.color);
    }

    public String getText() {
        ColoredText llllllllllllllllllIlIlIIIlllIlII;
        return llllllllllllllllllIlIlIIIlllIlII.text;
    }

    public int hashCode() {
        ColoredText llllllllllllllllllIlIlIIIllIIllI;
        return Objects.hash(llllllllllllllllllIlIlIIIllIIllI.text, llllllllllllllllllIlIlIIIllIIllI.color);
    }

    public Color getColor() {
        ColoredText llllllllllllllllllIlIlIIIlllIIIl;
        return llllllllllllllllllIlIlIIIlllIIIl.color;
    }

    public ColoredText(String llllllllllllllllllIlIlIIIllllIll, Color llllllllllllllllllIlIlIIIllllIlI) {
        ColoredText llllllllllllllllllIlIlIIIlllllII;
        llllllllllllllllllIlIlIIIlllllII.text = llllllllllllllllllIlIlIIIllllIll;
        llllllllllllllllllIlIlIIIlllllII.color = llllllllllllllllllIlIlIIIllllIlI;
    }
}

