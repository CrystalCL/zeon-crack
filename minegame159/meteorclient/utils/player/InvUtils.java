/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.screen.slot.SlotActionType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.utils.player;

import java.util.function.Predicate;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.SlotUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvUtils {
    private static final /* synthetic */ FindItemResult findItemResult;
    private static final /* synthetic */ Action ACTION;

    public static int findItemInHotbar(Item lllllllllllllllllllIlllIlIllllll, Predicate<ItemStack> lllllllllllllllllllIlllIlIllllII) {
        return InvUtils.findItem(lllllllllllllllllllIlllIlIllllll, lllllllllllllllllllIlllIlIllllII, 9);
    }

    public static void clickSlot(int lllllllllllllllllllIlllIlIlIIllI, int lllllllllllllllllllIlllIlIlIIIlI, SlotActionType lllllllllllllllllllIlllIlIlIIlII) {
        Utils.mc.interactionManager.clickSlot(Utils.mc.player.currentScreenHandler.syncId, lllllllllllllllllllIlllIlIlIIllI, lllllllllllllllllllIlllIlIlIIIlI, lllllllllllllllllllIlllIlIlIIlII, (PlayerEntity)Utils.mc.player);
    }

    private static int findItem(Item lllllllllllllllllllIlllIlIIllIIl, Predicate<ItemStack> lllllllllllllllllllIlllIlIIlIlIl, int lllllllllllllllllllIlllIlIIlIlll) {
        for (int lllllllllllllllllllIlllIlIIllIlI = 0; lllllllllllllllllllIlllIlIIllIlI < lllllllllllllllllllIlllIlIIlIlll; ++lllllllllllllllllllIlllIlIIllIlI) {
            ItemStack lllllllllllllllllllIlllIlIIllIll = Utils.mc.player.inventory.getStack(lllllllllllllllllllIlllIlIIllIlI);
            if (lllllllllllllllllllIlllIlIIllIIl != null && lllllllllllllllllllIlllIlIIllIll.getItem() != lllllllllllllllllllIlllIlIIllIIl || !lllllllllllllllllllIlllIlIIlIlIl.test(lllllllllllllllllllIlllIlIIllIll)) continue;
            return lllllllllllllllllllIlllIlIIllIlI;
        }
        return -1;
    }

    public static int findItemInMain(Predicate<ItemStack> lllllllllllllllllllIlllIlIlIlIlI) {
        return InvUtils.findItem(null, lllllllllllllllllllIlllIlIlIlIlI, Utils.mc.player.inventory.main.size());
    }

    public static Action drop() {
        ACTION.type = SlotActionType.THROW;
        ACTION.data = 1;
        return ACTION;
    }

    public static int findItemInHotbar(Item lllllllllllllllllllIlllIllIIIIll) {
        return InvUtils.findItemInHotbar(lllllllllllllllllllIlllIllIIIIll, lllllllllllllllllllIlllIlIIlIIII -> true);
    }

    public static Action quickMove() {
        ACTION.type = SlotActionType.QUICK_MOVE;
        return ACTION;
    }

    public InvUtils() {
        InvUtils lllllllllllllllllllIlllIlllIIllI;
    }

    public static Hand getHand(Item lllllllllllllllllllIlllIlllIIIll) {
        Hand lllllllllllllllllllIlllIlllIIIlI = Hand.MAIN_HAND;
        if (Utils.mc.player.getOffHandStack().getItem() == lllllllllllllllllllIlllIlllIIIll) {
            lllllllllllllllllllIlllIlllIIIlI = Hand.OFF_HAND;
        }
        return lllllllllllllllllllIlllIlllIIIlI;
    }

    public static int findItemInMain(Item lllllllllllllllllllIlllIlIllIlll) {
        return InvUtils.findItemInHotbar(lllllllllllllllllllIlllIlIllIlll, lllllllllllllllllllIlllIlIIlIIIl -> true);
    }

    public static Action move() {
        ACTION.type = SlotActionType.PICKUP;
        ACTION.two = true;
        return ACTION;
    }

    public static int findItemInAll(Item lllllllllllllllllllIlllIllIIllll) {
        return InvUtils.findItemInHotbar(lllllllllllllllllllIlllIllIIllll, lllllllllllllllllllIlllIlIIIllll -> true);
    }

    static {
        ACTION = new Action();
        findItemResult = new FindItemResult();
    }

    public static Hand getHand(Predicate<ItemStack> lllllllllllllllllllIlllIllIllIll) {
        Hand lllllllllllllllllllIlllIllIlllII = null;
        if (lllllllllllllllllllIlllIllIllIll.test(Utils.mc.player.getMainHandStack())) {
            lllllllllllllllllllIlllIllIlllII = Hand.MAIN_HAND;
        } else if (lllllllllllllllllllIlllIllIllIll.test(Utils.mc.player.getOffHandStack())) {
            lllllllllllllllllllIlllIllIlllII = Hand.OFF_HAND;
        }
        return lllllllllllllllllllIlllIllIlllII;
    }

    public static int invIndexToSlotId(int lllllllllllllllllllIlllIlIllIIll) {
        if (lllllllllllllllllllIlllIlIllIIll < 9) {
            return 44 - (8 - lllllllllllllllllllIlllIlIllIIll);
        }
        return lllllllllllllllllllIlllIlIllIIll;
    }

    public static Action click() {
        ACTION.type = SlotActionType.PICKUP;
        return ACTION;
    }

    public static FindItemResult findItemWithCount(Item lllllllllllllllllllIlllIllIlIlII) {
        InvUtils.findItemResult.slot = -1;
        InvUtils.findItemResult.count = 0;
        for (int lllllllllllllllllllIlllIllIlIlIl = 0; lllllllllllllllllllIlllIllIlIlIl < Utils.mc.player.inventory.size(); ++lllllllllllllllllllIlllIllIlIlIl) {
            ItemStack lllllllllllllllllllIlllIllIlIllI = Utils.mc.player.inventory.getStack(lllllllllllllllllllIlllIllIlIlIl);
            if (lllllllllllllllllllIlllIllIlIllI.getItem() != lllllllllllllllllllIlllIllIlIlII) continue;
            if (!findItemResult.found()) {
                InvUtils.findItemResult.slot = lllllllllllllllllllIlllIllIlIlIl;
            }
            InvUtils.findItemResult.count += lllllllllllllllllllIlllIllIlIllI.getCount();
        }
        return findItemResult;
    }

    public static int findItemInAll(Item lllllllllllllllllllIlllIllIIlIll, Predicate<ItemStack> lllllllllllllllllllIlllIllIIlIII) {
        return InvUtils.findItem(lllllllllllllllllllIlllIllIIlIll, lllllllllllllllllllIlllIllIIlIII, Utils.mc.player.inventory.size());
    }

    public static int findItemInMain(Item lllllllllllllllllllIlllIlIlIlllI, Predicate<ItemStack> lllllllllllllllllllIlllIlIlIllIl) {
        return InvUtils.findItem(lllllllllllllllllllIlllIlIlIlllI, lllllllllllllllllllIlllIlIlIllIl, Utils.mc.player.inventory.main.size());
    }

    public static int findItemInHotbar(Predicate<ItemStack> lllllllllllllllllllIlllIlIlllIlI) {
        return InvUtils.findItem(null, lllllllllllllllllllIlllIlIlllIlI, 9);
    }

    public static int findItemInAll(Predicate<ItemStack> lllllllllllllllllllIlllIllIIIllI) {
        return InvUtils.findItem(null, lllllllllllllllllllIlllIllIIIllI, Utils.mc.player.inventory.size());
    }

    public static class Action {
        private /* synthetic */ boolean isRecursive;
        private /* synthetic */ int from;
        private /* synthetic */ SlotActionType type;
        private /* synthetic */ int to;
        private /* synthetic */ boolean two;
        private /* synthetic */ int data;

        public Action fromMain(int lllIIlIlIlIIllI) {
            Action lllIIlIlIlIIlIl;
            return lllIIlIlIlIIlIl.from(9 + lllIIlIlIlIIllI);
        }

        public void slotOffhand() {
            Action lllIIlIIllIlIIl;
            lllIIlIIllIlIIl.slot(45);
        }

        public void slotMain(int lllIIlIIllIIlII) {
            Action lllIIlIIllIIlIl;
            lllIIlIIllIIlIl.slot(9 + lllIIlIIllIIlII);
        }

        private void run() {
            Action lllIIlIIlIlIlIl;
            boolean lllIIlIIlIlIlII = Utils.mc.player.inventory.getCursorStack().isEmpty();
            if (lllIIlIIlIlIlIl.type != null && lllIIlIIlIlIlIl.from != -1 && lllIIlIIlIlIlIl.to != -1) {
                lllIIlIIlIlIlIl.click(lllIIlIIlIlIlIl.from);
                if (lllIIlIIlIlIlIl.two) {
                    lllIIlIIlIlIlIl.click(lllIIlIIlIlIlIl.to);
                }
            }
            SlotActionType lllIIlIIlIlIIll = lllIIlIIlIlIlIl.type;
            boolean lllIIlIIlIlIIlI = lllIIlIIlIlIlIl.two;
            int lllIIlIIlIlIIIl = lllIIlIIlIlIlIl.from;
            int lllIIlIIlIlIIII = lllIIlIIlIlIlIl.to;
            lllIIlIIlIlIlIl.type = null;
            lllIIlIIlIlIlIl.two = false;
            lllIIlIIlIlIlIl.from = -1;
            lllIIlIIlIlIlIl.to = -1;
            lllIIlIIlIlIlIl.data = 0;
            if (!lllIIlIIlIlIlIl.isRecursive && lllIIlIIlIlIlII && lllIIlIIlIlIIll == SlotActionType.PICKUP && lllIIlIIlIlIIlI && lllIIlIIlIlIIIl != -1 && lllIIlIIlIlIIII != -1 && !Utils.mc.player.inventory.getCursorStack().isEmpty()) {
                lllIIlIIlIlIlIl.isRecursive = true;
                InvUtils.click().slotId(lllIIlIIlIlIIIl);
                lllIIlIIlIlIlIl.isRecursive = false;
            }
        }

        public void toId(int lllIIlIlIIllIII) {
            Action lllIIlIlIIllIIl;
            lllIIlIlIIllIIl.to = lllIIlIlIIllIII;
            lllIIlIlIIllIIl.run();
        }

        public void to(int lllIIlIlIIlIlII) {
            Action lllIIlIlIIlIlIl;
            lllIIlIlIIlIlIl.toId(SlotUtils.indexToId(lllIIlIlIIlIlII));
        }

        public void slotArmor(int lllIIlIIlIlllII) {
            Action lllIIlIIlIlllIl;
            lllIIlIIlIlllIl.slot(36 + (3 - lllIIlIIlIlllII));
        }

        public void slotHotbar(int lllIIlIIllIllIl) {
            Action lllIIlIIllIlllI;
            lllIIlIIllIlllI.slot(0 + lllIIlIIllIllIl);
        }

        public Action from(int lllIIlIlIllIIll) {
            Action lllIIlIlIllIllI;
            return lllIIlIlIllIllI.fromId(SlotUtils.indexToId(lllIIlIlIllIIll));
        }

        public void toOffhand() {
            Action lllIIlIlIIIlIIl;
            lllIIlIlIIIlIIl.to(45);
        }

        public Action fromArmor(int lllIIlIlIlIIIII) {
            Action lllIIlIlIIlllll;
            return lllIIlIlIIlllll.from(36 + (3 - lllIIlIlIlIIIII));
        }

        public void toHotbar(int lllIIlIlIIIllII) {
            Action lllIIlIlIIIllIl;
            lllIIlIlIIIllIl.to(0 + lllIIlIlIIIllII);
        }

        public Action fromHotbar(int lllIIlIlIlIllIl) {
            Action lllIIlIlIlIlllI;
            return lllIIlIlIlIlllI.from(0 + lllIIlIlIlIllIl);
        }

        public void slotId(int lllIIlIIllllIIl) {
            Action lllIIlIIllllIlI;
            lllIIlIIllllIlI.from = lllIIlIIllllIlI.to = lllIIlIIllllIIl;
            lllIIlIIllllIlI.run();
        }

        public Action fromOffhand() {
            Action lllIIlIlIlIlIlI;
            return lllIIlIlIlIlIlI.from(45);
        }

        private void click(int lllIIlIIlIIIllI) {
            Action lllIIlIIlIIIlll;
            Utils.mc.interactionManager.clickSlot(Utils.mc.player.currentScreenHandler.syncId, lllIIlIIlIIIllI, lllIIlIIlIIIlll.data, lllIIlIIlIIIlll.type, (PlayerEntity)Utils.mc.player);
        }

        public void toMain(int lllIIlIlIIIIlIl) {
            Action lllIIlIlIIIIllI;
            lllIIlIlIIIIllI.to(9 + lllIIlIlIIIIlIl);
        }

        private Action() {
            Action lllIIlIlIllllll;
            lllIIlIlIllllll.type = null;
            lllIIlIlIllllll.two = false;
            lllIIlIlIllllll.from = -1;
            lllIIlIlIllllll.to = -1;
            lllIIlIlIllllll.data = 0;
            lllIIlIlIllllll.isRecursive = false;
        }

        public Action fromId(int lllIIlIlIlllIIl) {
            Action lllIIlIlIlllIlI;
            lllIIlIlIlllIlI.from = lllIIlIlIlllIIl;
            return lllIIlIlIlllIlI;
        }

        public void slot(int lllIIlIIlllIIIl) {
            Action lllIIlIIlllIlII;
            lllIIlIIlllIlII.slotId(SlotUtils.indexToId(lllIIlIIlllIIIl));
        }

        public void toArmor(int lllIIlIIlllllIl) {
            Action lllIIlIlIIIIIII;
            lllIIlIlIIIIIII.to(36 + (3 - lllIIlIIlllllIl));
        }
    }

    public static class FindItemResult {
        public /* synthetic */ int count;
        public /* synthetic */ int slot;

        public FindItemResult() {
            FindItemResult llllllllllllllllllIlIllIlIIIlIIl;
        }

        public boolean found() {
            FindItemResult llllllllllllllllllIlIllIlIIIIlll;
            return llllllllllllllllllIlIllIlIIIIlll.slot != -1;
        }
    }
}

