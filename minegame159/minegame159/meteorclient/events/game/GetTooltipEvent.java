/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.game;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;

public class GetTooltipEvent {
    public ItemStack itemStack;
    public List<Text> list;

    public static class Append
    extends GetTooltipEvent {
        private static final Append INSTANCE = new Append();

        public static Append get(ItemStack ItemStack2, List<Text> list) {
            Append.INSTANCE.itemStack = ItemStack2;
            Append.INSTANCE.list = list;
            return INSTANCE;
        }
    }

    public static class Modify
    extends GetTooltipEvent {
        public int y;
        public int x;
        private static final Modify INSTANCE = new Modify();
        public MatrixStack matrixStack;

        public static Modify get(ItemStack ItemStack2, List<Text> list, MatrixStack MatrixStack2, int n, int n2) {
            Modify.INSTANCE.itemStack = ItemStack2;
            Modify.INSTANCE.list = list;
            Modify.INSTANCE.matrixStack = MatrixStack2;
            Modify.INSTANCE.x = n;
            Modify.INSTANCE.y = n2;
            return INSTANCE;
        }
    }
}

