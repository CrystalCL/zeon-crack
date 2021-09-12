/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.class_1268;
import net.minecraft.class_1937;
import net.minecraft.class_2199;
import net.minecraft.class_2231;
import net.minecraft.class_2237;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2269;
import net.minecraft.class_2304;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_2428;
import net.minecraft.class_243;
import net.minecraft.class_2533;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_2879;
import net.minecraft.class_310;
import net.minecraft.class_3726;
import net.minecraft.class_3965;

public class BlockUtils {
    private static final class_310 mc = class_310.method_1551();
    private static final class_243 hitPos = new class_243(0.0, 0.0, 0.0);

    public static boolean place(class_2338 class_23382, class_1268 class_12682, int n, boolean bl, int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        class_2338 class_23383;
        class_243 class_2432;
        if (n == -1 || !BlockUtils.canPlace(class_23382, bl3)) {
            return false;
        }
        class_2350 class_23502 = BlockUtils.getPlaceSide(class_23382);
        class_243 class_2433 = class_2432 = bl ? new class_243(0.0, 0.0, 0.0) : hitPos;
        if (class_23502 == null) {
            class_23502 = class_2350.field_11036;
            class_23383 = class_23382;
            ((IVec3d)class_2432).set((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 0.5, (double)class_23382.method_10260() + 0.5);
        } else {
            class_23383 = class_23382.method_10093(class_23502.method_10153());
            ((IVec3d)class_2432).set((double)class_23383.method_10263() + 0.5 + (double)class_23502.method_10148() * 0.5, (double)class_23383.method_10264() + 0.6 + (double)class_23502.method_10164() * 0.5, (double)class_23383.method_10260() + 0.5 + (double)class_23502.method_10165() * 0.5);
        }
        if (bl) {
            class_2350 class_23503 = class_23502;
            Rotations.rotate(Rotations.getYaw(class_2432), Rotations.getPitch(class_2432), n2, () -> BlockUtils.lambda$place$0(n, class_2432, class_12682, class_23503, class_23383, bl2, bl4, bl5));
        } else {
            BlockUtils.place(n, class_2432, class_12682, class_23502, class_23383, bl2, bl4, bl5);
        }
        return true;
    }

    public static boolean place(class_2338 class_23382, class_1268 class_12682, int n, boolean bl, int n2, boolean bl2) {
        return BlockUtils.place(class_23382, class_12682, n, bl, n2, true, bl2, true, true);
    }

    public static boolean canPlace(class_2338 class_23382, boolean bl) {
        if (class_23382 == null) {
            return false;
        }
        if (class_1937.method_8518((class_2338)class_23382)) {
            return false;
        }
        if (!BlockUtils.mc.field_1687.method_8320(class_23382).method_26207().method_15800()) {
            return false;
        }
        return !bl || BlockUtils.mc.field_1687.method_8628(class_2246.field_10340.method_9564(), class_23382, class_3726.method_16194());
    }

    public static boolean isClickable(class_2248 class_22482) {
        boolean bl = class_22482 instanceof class_2304 || class_22482 instanceof class_2199 || class_22482 instanceof class_2269 || class_22482 instanceof class_2231 || class_22482 instanceof class_2237 || class_22482 instanceof class_2349 || class_22482 instanceof class_2323 || class_22482 instanceof class_2428 || class_22482 instanceof class_2533;
        return bl;
    }

    private static void lambda$place$0(int n, class_243 class_2432, class_1268 class_12682, class_2350 class_23502, class_2338 class_23382, boolean bl, boolean bl2, boolean bl3) {
        BlockUtils.place(n, class_2432, class_12682, class_23502, class_23382, bl, bl2, bl3);
    }

    public static boolean canPlace(class_2338 class_23382) {
        return BlockUtils.canPlace(class_23382, true);
    }

    private static void place(int n, class_243 class_2432, class_1268 class_12682, class_2350 class_23502, class_2338 class_23382, boolean bl, boolean bl2, boolean bl3) {
        int n2 = BlockUtils.mc.field_1724.field_7514.field_7545;
        if (bl2) {
            BlockUtils.mc.field_1724.field_7514.field_7545 = n;
        }
        boolean bl4 = BlockUtils.mc.field_1724.field_3913.field_3903;
        BlockUtils.mc.field_1724.field_3913.field_3903 = false;
        BlockUtils.mc.field_1761.method_2896(BlockUtils.mc.field_1724, BlockUtils.mc.field_1687, class_12682, new class_3965(class_2432, class_23502, class_23382, false));
        if (bl) {
            BlockUtils.mc.field_1724.method_6104(class_12682);
        } else {
            mc.method_1562().method_2883((class_2596)new class_2879(class_12682));
        }
        BlockUtils.mc.field_1724.field_3913.field_3903 = bl4;
        if (bl3) {
            BlockUtils.mc.field_1724.field_7514.field_7545 = n2;
        }
    }

    private static class_2350 getPlaceSide(class_2338 class_23382) {
        for (class_2350 class_23502 : class_2350.values()) {
            class_2338 class_23383 = class_23382.method_10093(class_23502);
            class_2350 class_23503 = class_23502.method_10153();
            class_2680 class_26802 = BlockUtils.mc.field_1687.method_8320(class_23383);
            if (class_26802.method_26215() || BlockUtils.isClickable(class_26802.method_26204()) || !class_26802.method_26227().method_15769()) continue;
            return class_23503;
        }
        return null;
    }
}

