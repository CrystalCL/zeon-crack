/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import java.util.function.Predicate;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.SlotUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;

public class InvUtils {
    private static final Action ACTION = new Action(null);
    private static final FindItemResult findItemResult = new FindItemResult();

    public static Action quickMove() {
        Action.access$102(ACTION, class_1713.field_7794);
        return ACTION;
    }

    public static int findItemInMain(class_1792 class_17922) {
        return InvUtils.findItemInHotbar(class_17922, InvUtils::lambda$findItemInMain$2);
    }

    public static int findItemInMain(class_1792 class_17922, Predicate<class_1799> predicate) {
        return InvUtils.findItem(class_17922, predicate, Utils.mc.field_1724.field_7514.field_7547.size());
    }

    public static int findItemInAll(class_1792 class_17922) {
        return InvUtils.findItemInHotbar(class_17922, InvUtils::lambda$findItemInAll$0);
    }

    public static class_1268 getHand(Predicate<class_1799> predicate) {
        class_1268 class_12682 = null;
        if (predicate.test(Utils.mc.field_1724.method_6047())) {
            class_12682 = class_1268.field_5808;
        } else if (predicate.test(Utils.mc.field_1724.method_6079())) {
            class_12682 = class_1268.field_5810;
        }
        return class_12682;
    }

    public static int findItemInHotbar(class_1792 class_17922, Predicate<class_1799> predicate) {
        return InvUtils.findItem(class_17922, predicate, 9);
    }

    public static int invIndexToSlotId(int n) {
        if (n < 9) {
            return 44 - (8 - n);
        }
        return n;
    }

    private static boolean lambda$findItemInAll$0(class_1799 class_17992) {
        return true;
    }

    public static class_1268 getHand(class_1792 class_17922) {
        class_1268 class_12682 = class_1268.field_5808;
        if (Utils.mc.field_1724.method_6079().method_7909() == class_17922) {
            class_12682 = class_1268.field_5810;
        }
        return class_12682;
    }

    public static Action click() {
        Action.access$102(ACTION, class_1713.field_7790);
        return ACTION;
    }

    public static int findItemInHotbar(class_1792 class_17922) {
        return InvUtils.findItemInHotbar(class_17922, InvUtils::lambda$findItemInHotbar$1);
    }

    public static int findItemInAll(class_1792 class_17922, Predicate<class_1799> predicate) {
        return InvUtils.findItem(class_17922, predicate, Utils.mc.field_1724.field_7514.method_5439());
    }

    public static Action move() {
        Action.access$102(ACTION, class_1713.field_7790);
        Action.access$202(ACTION, true);
        return ACTION;
    }

    public static int findItemInAll(Predicate<class_1799> predicate) {
        return InvUtils.findItem(null, predicate, Utils.mc.field_1724.field_7514.method_5439());
    }

    private static int findItem(class_1792 class_17922, Predicate<class_1799> predicate, int n) {
        for (int i = 0; i < n; ++i) {
            class_1799 class_17992 = Utils.mc.field_1724.field_7514.method_5438(i);
            if (class_17922 != null && class_17992.method_7909() != class_17922 || !predicate.test(class_17992)) continue;
            return i;
        }
        return -1;
    }

    public static int findItemInMain(Predicate<class_1799> predicate) {
        return InvUtils.findItem(null, predicate, Utils.mc.field_1724.field_7514.field_7547.size());
    }

    public static void clickSlot(int n, int n2, class_1713 class_17132) {
        Utils.mc.field_1761.method_2906(Utils.mc.field_1724.field_7512.field_7763, n, n2, class_17132, (class_1657)Utils.mc.field_1724);
    }

    public static Action drop() {
        Action.access$102(ACTION, class_1713.field_7795);
        Action.access$302(ACTION, 1);
        return ACTION;
    }

    private static boolean lambda$findItemInMain$2(class_1799 class_17992) {
        return true;
    }

    public static FindItemResult findItemWithCount(class_1792 class_17922) {
        InvUtils.findItemResult.slot = -1;
        InvUtils.findItemResult.count = 0;
        for (int i = 0; i < Utils.mc.field_1724.field_7514.method_5439(); ++i) {
            class_1799 class_17992 = Utils.mc.field_1724.field_7514.method_5438(i);
            if (class_17992.method_7909() != class_17922) continue;
            if (!findItemResult.found()) {
                InvUtils.findItemResult.slot = i;
            }
            InvUtils.findItemResult.count += class_17992.method_7947();
            if (!false) continue;
            return null;
        }
        return findItemResult;
    }

    private static boolean lambda$findItemInHotbar$1(class_1799 class_17992) {
        return true;
    }

    public static int findItemInHotbar(Predicate<class_1799> predicate) {
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
        private class_1713 type = null;
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
            boolean bl = Utils.mc.field_1724.field_7514.method_7399().method_7960();
            if (this.type != null && this.from != -1 && this.to != -1) {
                this.click(this.from);
                if (this.two) {
                    this.click(this.to);
                }
            }
            class_1713 class_17132 = this.type;
            boolean bl2 = this.two;
            int n = this.from;
            int n2 = this.to;
            this.type = null;
            this.two = false;
            this.from = -1;
            this.to = -1;
            this.data = 0;
            if (!this.isRecursive && bl && class_17132 == class_1713.field_7790 && bl2 && n != -1 && n2 != -1 && !Utils.mc.field_1724.field_7514.method_7399().method_7960()) {
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

        static class_1713 access$102(Action action, class_1713 class_17132) {
            action.type = class_17132;
            return class_17132;
        }

        private void click(int n) {
            Utils.mc.field_1761.method_2906(Utils.mc.field_1724.field_7512.field_7763, n, this.data, this.type, (class_1657)Utils.mc.field_1724);
        }

        public Action fromHotbar(int n) {
            return this.from(0 + n);
        }
    }
}

