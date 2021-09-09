/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.text.Text
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.events.game;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;

public class GetTooltipEvent {
    public /* synthetic */ ItemStack itemStack;
    public /* synthetic */ List<Text> list;

    public GetTooltipEvent() {
        GetTooltipEvent lllllllllllllllllIIIIllIIllIIlll;
    }

    public static class Append
    extends GetTooltipEvent {
        private static final /* synthetic */ Append INSTANCE;

        public static Append get(ItemStack llIIIIIIlllIllI, List<Text> llIIIIIIlllIlll) {
            Append.INSTANCE.itemStack = llIIIIIIlllIllI;
            Append.INSTANCE.list = llIIIIIIlllIlll;
            return INSTANCE;
        }

        static {
            INSTANCE = new Append();
        }

        public Append() {
            Append llIIIIIIllllIll;
        }
    }

    public static class Modify
    extends GetTooltipEvent {
        public /* synthetic */ MatrixStack matrixStack;
        public /* synthetic */ int y;
        public /* synthetic */ int x;
        private static final /* synthetic */ Modify INSTANCE;

        static {
            INSTANCE = new Modify();
        }

        public Modify() {
            Modify llllllllllllllllllIllIIIllIllIII;
        }

        public static Modify get(ItemStack llllllllllllllllllIllIIIllIIllIl, List<Text> llllllllllllllllllIllIIIllIlIIIl, MatrixStack llllllllllllllllllIllIIIllIIlIll, int llllllllllllllllllIllIIIllIIllll, int llllllllllllllllllIllIIIllIIlIIl) {
            Modify.INSTANCE.itemStack = llllllllllllllllllIllIIIllIIllIl;
            Modify.INSTANCE.list = llllllllllllllllllIllIIIllIlIIIl;
            Modify.INSTANCE.matrixStack = llllllllllllllllllIllIIIllIIlIll;
            Modify.INSTANCE.x = llllllllllllllllllIllIIIllIIllll;
            Modify.INSTANCE.y = llllllllllllllllllIllIIIllIIlIIl;
            return INSTANCE;
        }
    }
}

