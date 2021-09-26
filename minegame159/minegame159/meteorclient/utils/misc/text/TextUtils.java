/*
 * Decompiled with CFR 0.151.
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
    private static void preOrderTraverse(Text Text2, Stack<ColoredText> stack, List<ColoredText> list) {
        if (Text2 == null) {
            return;
        }
        String string = Text2.asString();
        TextColor TextColor2 = Text2.getStyle().getColor();
        Color color = TextColor2 == null ? (stack.empty() ? new Color(255, 255, 255) : stack.peek().getColor()) : new Color(Text2.getStyle().getColor().getRgb() | 0xFF000000);
        ColoredText coloredText = new ColoredText(string, color);
        list.add(coloredText);
        stack.push(coloredText);
        for (Text Text3 : Text2.getSiblings()) {
            TextUtils.preOrderTraverse(Text3, stack, list);
        }
        stack.pop();
    }

    public static List<ColoredText> toColoredTextList(Text Text2) {
        Stack<ColoredText> stack = new Stack<ColoredText>();
        ArrayList<ColoredText> arrayList = new ArrayList<ColoredText>();
        TextUtils.preOrderTraverse(Text2, stack, arrayList);
        arrayList.removeIf(TextUtils::lambda$toColoredTextList$0);
        return arrayList;
    }

    public static Map<Color, Integer> getColoredCharacterCount(List<ColoredText> list) {
        HashMap<Color, Integer> hashMap = new HashMap<Color, Integer>();
        for (ColoredText coloredText : list) {
            if (hashMap.containsKey(coloredText.getColor())) {
                hashMap.put(coloredText.getColor(), (Integer)hashMap.get(coloredText.getColor()) + coloredText.getText().length());
                continue;
            }
            hashMap.put(coloredText.getColor(), coloredText.getText().length());
        }
        return hashMap;
    }

    public static Color getMostPopularColor(Text Text2) {
        Comparator comparator = Comparator.naturalOrder();
        Optional optional = TextUtils.getColoredCharacterCount(TextUtils.toColoredTextList(Text2)).entrySet().stream().max((arg_0, arg_1) -> TextUtils.lambda$getMostPopularColor$1(comparator, arg_0, arg_1));
        return optional.map(Map.Entry::getKey).orElse(new Color(255, 255, 255));
    }

    private static boolean lambda$toColoredTextList$0(ColoredText coloredText) {
        return coloredText.getText().equals("");
    }

    private static int lambda$getMostPopularColor$1(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare((Integer)entry.getValue(), (Integer)entry2.getValue());
    }
}

