/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.text.TextColor
 */
package minegame159.meteorclient.utils.misc.text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import minegame159.meteorclient.utils.misc.text.ColoredText;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class TextUtils {
    public static Color getMostPopularColor(Text llllllllllllllllIlIllllIIlIlIIll) {
        Comparator llllllllllllllllIlIllllIIlIlIlIl = Comparator.naturalOrder();
        Optional llllllllllllllllIlIllllIIlIlIlII = TextUtils.getColoredCharacterCount(TextUtils.toColoredTextList(llllllllllllllllIlIllllIIlIlIIll)).entrySet().stream().max((llllllllllllllllIlIllllIIIlIIlIl, llllllllllllllllIlIllllIIIlIIlII) -> llllllllllllllllIlIllllIIlIlIlIl.compare((Integer)llllllllllllllllIlIllllIIIlIIlIl.getValue(), (Integer)llllllllllllllllIlIllllIIIlIIlII.getValue()));
        return llllllllllllllllIlIllllIIlIlIlII.map(Map.Entry::getKey).orElse(new Color(255, 255, 255));
    }

    public static Map<Color, Integer> getColoredCharacterCount(List<ColoredText> llllllllllllllllIlIllllIIlIIlIIl) {
        HashMap<Color, Integer> llllllllllllllllIlIllllIIlIIlIlI = new HashMap<Color, Integer>();
        for (ColoredText llllllllllllllllIlIllllIIlIIllII : llllllllllllllllIlIllllIIlIIlIIl) {
            if (llllllllllllllllIlIllllIIlIIlIlI.containsKey(llllllllllllllllIlIllllIIlIIllII.getColor())) {
                llllllllllllllllIlIllllIIlIIlIlI.put(llllllllllllllllIlIllllIIlIIllII.getColor(), (Integer)llllllllllllllllIlIllllIIlIIlIlI.get(llllllllllllllllIlIllllIIlIIllII.getColor()) + llllllllllllllllIlIllllIIlIIllII.getText().length());
                continue;
            }
            llllllllllllllllIlIllllIIlIIlIlI.put(llllllllllllllllIlIllllIIlIIllII.getColor(), llllllllllllllllIlIllllIIlIIllII.getText().length());
        }
        return llllllllllllllllIlIllllIIlIIlIlI;
    }

    public static List<ColoredText> toColoredTextList(Text llllllllllllllllIlIllllIIlIlllll) {
        Stack<ColoredText> llllllllllllllllIlIllllIIlIllllI = new Stack<ColoredText>();
        ArrayList<ColoredText> llllllllllllllllIlIllllIIlIlllIl = new ArrayList<ColoredText>();
        TextUtils.preOrderTraverse(llllllllllllllllIlIllllIIlIlllll, llllllllllllllllIlIllllIIlIllllI, llllllllllllllllIlIllllIIlIlllIl);
        llllllllllllllllIlIllllIIlIlllIl.removeIf(llllllllllllllllIlIllllIIIIlIlII -> llllllllllllllllIlIllllIIIIlIlII.getText().equals(""));
        return llllllllllllllllIlIllllIIlIlllIl;
    }

    public TextUtils() {
        TextUtils llllllllllllllllIlIllllIIllIIIll;
    }

    private static void preOrderTraverse(Text llllllllllllllllIlIllllIIIllIIlI, Stack<ColoredText> llllllllllllllllIlIllllIIIlllIII, List<ColoredText> llllllllllllllllIlIllllIIIllIIII) {
        Color llllllllllllllllIlIllllIIIllIlII;
        if (llllllllllllllllIlIllllIIIllIIlI == null) {
            return;
        }
        String llllllllllllllllIlIllllIIIllIllI = llllllllllllllllIlIllllIIIllIIlI.asString();
        TextColor llllllllllllllllIlIllllIIIllIlIl = llllllllllllllllIlIllllIIIllIIlI.getStyle().getColor();
        if (llllllllllllllllIlIllllIIIllIlIl == null) {
            if (llllllllllllllllIlIllllIIIlllIII.empty()) {
                Color llllllllllllllllIlIllllIIIllllII = new Color(255, 255, 255);
            } else {
                Color llllllllllllllllIlIllllIIIlllIll = llllllllllllllllIlIllllIIIlllIII.peek().getColor();
            }
        } else {
            llllllllllllllllIlIllllIIIllIlII = new Color(llllllllllllllllIlIllllIIIllIIlI.getStyle().getColor().getRgb() | 0xFF000000);
        }
        ColoredText llllllllllllllllIlIllllIIIllIIll = new ColoredText(llllllllllllllllIlIllllIIIllIllI, llllllllllllllllIlIllllIIIllIlII);
        llllllllllllllllIlIllllIIIllIIII.add(llllllllllllllllIlIllllIIIllIIll);
        llllllllllllllllIlIllllIIIlllIII.push(llllllllllllllllIlIllllIIIllIIll);
        for (Text llllllllllllllllIlIllllIIIlllIlI : llllllllllllllllIlIllllIIIllIIlI.getSiblings()) {
            TextUtils.preOrderTraverse(llllllllllllllllIlIllllIIIlllIlI, llllllllllllllllIlIllllIIIlllIII, llllllllllllllllIlIllllIIIllIIII);
        }
        llllllllllllllllIlIllllIIIlllIII.pop();
    }
}

