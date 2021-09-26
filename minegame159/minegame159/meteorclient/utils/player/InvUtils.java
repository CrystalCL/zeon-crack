/*
 * Decompiled with CFR 0.151.
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
    private static final Action ACTION = new Action(null);
    private static final FindItemResult findItemResult = new FindItemResult();

    public static Action quickMove() {
        Action.access$102(ACTION, SlotActionType.QUICK_MOVE);
        return ACTION;
    }

    public static int findItemInMain(Item Item2) {
        return InvUtils.findItemInHotbar(Item2, InvUtils::lambda$findItemInMain$2);
    }

    public static int findItemInMain(Item Item2, Predicate<ItemStack> predicate) {
        return InvUtils.findItem(Item2, predicate, Utils.mc.player.inventory.main.size());
    }

    public static int findItemInAll(Item Item2) {
        return InvUtils.findItemInHotbar(Item2, InvUtils::lambda$findItemInAll$0);
    }

    public static Hand getHand(Predicate<ItemStack> predicate) {
        Hand Hand2 = null;
        if (predicate.test(Utils.mc.player.getMainHandStack())) {
            Hand2 = Hand.MAIN_HAND;
        } else if (predicate.test(Utils.mc.player.getOffHandStack())) {
            Hand2 = Hand.OFF_HAND;
        }
        return Hand2;
    }

    public static int findItemInHotbar(Item Item2, Predicate<ItemStack> predicate) {
        return InvUtils.findItem(Item2, predicate, 9);
    }

    public static int invIndexToSlotId(int n) {
        if (n < 9) {
            return 44 - (8 - n);
        }
        return n;
    }

    private static boolean lambda$findItemInAll$0(ItemStack ItemStack2) {
        return true;
    }

    public static Hand getHand(Item Item2) {
        Hand Hand2 = Hand.MAIN_HAND;
        if (Utils.mc.player.getOffHandStack().getItem() == Item2) {
            Hand2 = Hand.OFF_HAND;
        }
        return Hand2;
    }

    public static Action click() {
        Action.access$102(ACTION, SlotActionType.PICKUP);
        return ACTION;
    }

    public static int findItemInHotbar(Item Item2) {
        return InvUtils.findItemInHotbar(Item2, InvUtils::lambda$findItemInHotbar$1);
    }

    public static int findItemInAll(Item Item2, Predicate<ItemStack> predicate) {
        return InvUtils.findItem(Item2, predicate, Utils.mc.player.inventory.size());
    }

    public static Action move() {
        Action.access$102(ACTION, SlotActionType.PICKUP);
        Action.access$202(ACTION, true);
        return ACTION;
    }

    public static int findItemInAll(Predicate<ItemStack> predicate) {
        return InvUtils.findItem(null, predicate, Utils.mc.player.inventory.size());
    }

    private static int findItem(Item Item2, Predicate<ItemStack> predicate, int n) {
        for (int i = 0; i < n; ++i) {
            ItemStack ItemStack2 = Utils.mc.player.inventory.getStack(i);
            if (Item2 != null && ItemStack2.getItem() != Item2 || !predicate.test(ItemStack2)) continue;
            return i;
        }
        return -1;
    }

    public static int findItemInMain(Predicate<ItemStack> predicate) {
        return InvUtils.findItem(null, predicate, Utils.mc.player.inventory.main.size());
    }

    public static void clickSlot(int n, int n2, SlotActionType SlotActionType2) {
        Utils.mc.interactionManager.clickSlot(Utils.mc.player.currentScreenHandler.syncId, n, n2, SlotActionType2, (PlayerEntity)Utils.mc.player);
    }

    public static Action drop() {
        Action.access$102(ACTION, SlotActionType.THROW);
        Action.access$302(ACTION, 1);
        return ACTION;
    }

    private static boolean lambda$findItemInMain$2(ItemStack ItemStack2) {
        return true;
    }

    public static FindItemResult findItemWithCount(Item Item2) {
        InvUtils.findItemResult.slot = -1;
        InvUtils.findItemResult.count = 0;
        for (int i = 0; i < Utils.mc.player.inventory.size(); ++i) {
            ItemStack ItemStack2 = Utils.mc.player.inventory.getStack(i);
            if (ItemStack2.getItem() != Item2) continue;
            if (!findItemResult.found()) {
                InvUtils.findItemResult.slot = i;
            }
            InvUtils.findItemResult.count += ItemStack2.getCount();
            if (!false) continue;
            return null;
        }
        return findItemResult;
    }

    private static boolean lambda$findItemInHotbar$1(ItemStack ItemStack2) {
        return true;
    }

    public static int findItemInHotbar(Predicate<ItemStack> predicate) {
        return InvUtils.findItem(null, predicate, 9);
    }

    public static class FindItemResult {
        public int slot;
        public int count;

        public boolean found() {
            return this.slot != -1;
        }
    }

    public static class Action {
        private SlotActionType type = null;
        private int data = 0;
        private boolean two = false;
        private int from = -1;
        private boolean isRecursive = false;
        private int to = -1;

        public Action fromId(int n) {
            this.from = n;
            return this;
        }

        public void toMain(int n) {
            this.to(9 + n);
        }

        public void toHotbar(int n) {
            this.to(0 + n);
        }

        Action(1 var1_1) {
            this();
        }

        public void slotOffhand() {
            this.slot(45);
        }

        public void to(int n) {
            this.toId(SlotUtils.indexToId(n));
        }

        public void slotMain(int n) {
            this.slot(9 + n);
        }

        public void slot(int n) {
            this.slotId(SlotUtils.indexToId(n));
        }

        private Action() {
        }

        public void toArmor(int n) {
            this.to(36 + (3 - n));
        }

        public void slotArmor(int n) {
            this.slot(36 + (3 - n));
        }

        static boolean access$202(Action action, boolean bl) {
            action.two = bl;
            return bl;
        }

        public void toOffhand() {
            this.to(45);
        }

        static int access$302(Action action, int n) {
            action.data = n;
            return n;
        }

        public Action fromArmor(int n) {
            return this.from(36 + (3 - n));
        }

        public Action fromOffhand() {
            return this.from(45);
        }

        private void run() {
            boolean bl = Utils.mc.player.inventory.getCursorStack().isEmpty();
            if (this.type != null && this.from != -1 && this.to != -1) {
                this.click(this.from);
                if (this.two) {
                    this.click(this.to);
                }
            }
            SlotActionType SlotActionType2 = this.type;
            boolean bl2 = this.two;
            int n = this.from;
            int n2 = this.to;
            this.type = null;
            this.two = false;
            this.from = -1;
            this.to = -1;
            this.data = 0;
            if (!this.isRecursive && bl && SlotActionType2 == SlotActionType.PICKUP && bl2 && n != -1 && n2 != -1 && !Utils.mc.player.inventory.getCursorStack().isEmpty()) {
                this.isRecursive = true;
                InvUtils.click().slotId(n);
                this.isRecursive = false;
            }
        }

        public Action fromMain(int n) {
            return this.from(9 + n);
        }

        public void slotId(int n) {
            this.to = n;
            this.from = n;
            this.run();
        }

        public void toId(int n) {
            this.to = n;
            this.run();
        }

        public void slotHotbar(int n) {
            this.slot(0 + n);
        }

        public Action from(int n) {
            return this.fromId(SlotUtils.indexToId(n));
        }

        static SlotActionType access$102(Action action, SlotActionType SlotActionType2) {
            action.type = SlotActionType2;
            return SlotActionType2;
        }

        private void click(int n) {
            Utils.mc.interactionManager.clickSlot(Utils.mc.player.currentScreenHandler.syncId, n, this.data, this.type, (PlayerEntity)Utils.mc.player);
        }

        public Action fromHotbar(int n) {
            return this.from(0 + n);
        }
    }
}

