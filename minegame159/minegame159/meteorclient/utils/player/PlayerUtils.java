/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import baritone.api.BaritoneAPI;
import baritone.api.utils.Rotation;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.utils.misc.BaritoneUtils;
import minegame159.meteorclient.utils.misc.Vector2;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1812;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public class PlayerUtils {
    private static final double diagonal;
    private static final class_310 mc;
    private static final class_243 horizontalVelocity;
    private static final class_243 hitPos;

    public static boolean placeBlock(class_2338 class_23382, int n, class_1268 class_12682) {
        if (n == -1) {
            return false;
        }
        int n2 = PlayerUtils.mc.field_1724.field_7514.field_7545;
        PlayerUtils.mc.field_1724.field_7514.field_7545 = n;
        boolean bl = PlayerUtils.placeBlock(class_23382, class_12682, true);
        PlayerUtils.mc.field_1724.field_7514.field_7545 = n2;
        return bl;
    }

    public static boolean shouldPause(boolean bl, boolean bl2, boolean bl3) {
        if (bl && PlayerUtils.mc.field_1761.method_2923()) {
            return true;
        }
        if (bl2 && PlayerUtils.mc.field_1724.method_6115() && (PlayerUtils.mc.field_1724.method_6047().method_7909().method_19263() || PlayerUtils.mc.field_1724.method_6079().method_7909().method_19263())) {
            return true;
        }
        return bl3 && PlayerUtils.mc.field_1724.method_6115() && (PlayerUtils.mc.field_1724.method_6047().method_7909() instanceof class_1812 || PlayerUtils.mc.field_1724.method_6079().method_7909() instanceof class_1812);
    }

    static {
        mc = class_310.method_1551();
        hitPos = new class_243(0.0, 0.0, 0.0);
        diagonal = 1.0 / Math.sqrt(2.0);
        horizontalVelocity = new class_243(0.0, 0.0, 0.0);
    }

    public static boolean placeBlock(class_2338 class_23382, class_1268 class_12682, boolean bl) {
        if (!BlockUtils.canPlace(class_23382)) {
            return false;
        }
        for (class_2350 class_23502 : class_2350.values()) {
            class_2338 class_23383 = class_23382.method_10093(class_23502);
            class_2350 class_23503 = class_23502.method_10153();
            if (PlayerUtils.mc.field_1687.method_8320(class_23383).method_26215() || BlockUtils.isClickable(PlayerUtils.mc.field_1687.method_8320(class_23383).method_26204())) continue;
            ((IVec3d)hitPos).set((double)class_23383.method_10263() + 0.5 + (double)class_23503.method_10163().method_10263() * 0.5, (double)class_23383.method_10264() + 0.5 + (double)class_23503.method_10163().method_10264() * 0.5, (double)class_23383.method_10260() + 0.5 + (double)class_23503.method_10163().method_10260() * 0.5);
            boolean bl2 = PlayerUtils.mc.field_1724.field_3913.field_3903;
            PlayerUtils.mc.field_1724.field_3913.field_3903 = false;
            PlayerUtils.mc.field_1761.method_2896(PlayerUtils.mc.field_1724, PlayerUtils.mc.field_1687, class_12682, new class_3965(hitPos, class_23503, class_23383, false));
            if (bl) {
                PlayerUtils.mc.field_1724.method_6104(class_12682);
            }
            PlayerUtils.mc.field_1724.field_3913.field_3903 = bl2;
            return true;
        }
        ((IVec3d)hitPos).set((class_2382)class_23382);
        PlayerUtils.mc.field_1761.method_2896(PlayerUtils.mc.field_1724, PlayerUtils.mc.field_1687, class_12682, new class_3965(hitPos, class_2350.field_11036, class_23382, false));
        if (bl) {
            PlayerUtils.mc.field_1724.method_6104(class_12682);
        }
        return true;
    }

    public static void centerPlayer() {
        double d = (double)class_3532.method_15357((double)PlayerUtils.mc.field_1724.method_23317()) + 0.5;
        double d2 = (double)class_3532.method_15357((double)PlayerUtils.mc.field_1724.method_23321()) + 0.5;
        PlayerUtils.mc.field_1724.method_5814(d, PlayerUtils.mc.field_1724.method_23318(), d2);
        PlayerUtils.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(PlayerUtils.mc.field_1724.method_23317(), PlayerUtils.mc.field_1724.method_23318(), PlayerUtils.mc.field_1724.method_23321(), PlayerUtils.mc.field_1724.method_24828()));
    }

    public static boolean isInHole() {
        return !PlayerUtils.mc.field_1687.method_8320(PlayerUtils.mc.field_1724.method_24515().method_10069(1, 0, 0)).method_26215() && !PlayerUtils.mc.field_1687.method_8320(PlayerUtils.mc.field_1724.method_24515().method_10069(-1, 0, 0)).method_26215() && !PlayerUtils.mc.field_1687.method_8320(PlayerUtils.mc.field_1724.method_24515().method_10069(0, 0, 1)).method_26215() && !PlayerUtils.mc.field_1687.method_8320(PlayerUtils.mc.field_1724.method_24515().method_10069(0, 0, -1)).method_26215() && !PlayerUtils.mc.field_1687.method_8320(PlayerUtils.mc.field_1724.method_24515().method_10069(0, -1, 0)).method_26215();
    }

    public static float[] calculateAngle(class_243 class_2432) {
        class_243 class_2433 = new class_243(PlayerUtils.mc.field_1724.method_23317(), PlayerUtils.mc.field_1724.method_23318() + (double)PlayerUtils.mc.field_1724.method_18381(PlayerUtils.mc.field_1724.method_18376()), PlayerUtils.mc.field_1724.method_23321());
        double d = class_2432.field_1352 - class_2433.field_1352;
        double d2 = (class_2432.field_1351 - class_2433.field_1351) * -1.0;
        double d3 = class_2432.field_1350 - class_2433.field_1350;
        double d4 = class_3532.method_15368((double)(d * d + d3 * d3));
        return new float[]{(float)class_3532.method_15338((double)(Math.toDegrees(Math.atan2(d3, d)) - 90.0)), (float)class_3532.method_15338((double)Math.toDegrees(Math.atan2(d2, d4)))};
    }

    public static boolean isSprinting() {
        return PlayerUtils.mc.field_1724.method_5624() && (PlayerUtils.mc.field_1724.field_6250 != 0.0f || PlayerUtils.mc.field_1724.field_6212 != 0.0f);
    }

    public static boolean placeBlock(class_2338 class_23382, class_1268 class_12682) {
        return PlayerUtils.placeBlock(class_23382, class_12682, true);
    }

    public static boolean canSeeEntity(class_1297 class_12972) {
        class_243 class_2432 = new class_243(0.0, 0.0, 0.0);
        class_243 class_2433 = new class_243(0.0, 0.0, 0.0);
        ((IVec3d)class_2432).set(PlayerUtils.mc.field_1724.method_23317(), PlayerUtils.mc.field_1724.method_23318() + (double)PlayerUtils.mc.field_1724.method_5751(), PlayerUtils.mc.field_1724.method_23321());
        ((IVec3d)class_2433).set(class_12972.method_23317(), class_12972.method_23318(), class_12972.method_23321());
        boolean bl = PlayerUtils.mc.field_1687.method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)PlayerUtils.mc.field_1724)).method_17783() == class_239.class_240.field_1333;
        ((IVec3d)class_2433).set(class_12972.method_23317(), class_12972.method_23318() + (double)class_12972.method_5751(), class_12972.method_23321());
        boolean bl2 = PlayerUtils.mc.field_1687.method_17742(new class_3959(class_2432, class_2433, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)PlayerUtils.mc.field_1724)).method_17783() == class_239.class_240.field_1333;
        return bl || bl2;
    }

    public static boolean isMoving() {
        return PlayerUtils.mc.field_1724.field_6250 != 0.0f || PlayerUtils.mc.field_1724.field_6212 != 0.0f;
    }

    public static Vector2 transformStrafe(double d) {
        float f = PlayerUtils.mc.field_1724.field_3913.field_3905;
        float f2 = PlayerUtils.mc.field_1724.field_3913.field_3907;
        float f3 = PlayerUtils.mc.field_1724.field_5982 + (PlayerUtils.mc.field_1724.field_6031 - PlayerUtils.mc.field_1724.field_5982) * mc.method_1488();
        if (f == 0.0f && f2 == 0.0f) {
            return new Vector2(0.0, 0.0);
        }
        if (f != 0.0f) {
            if (f2 >= 1.0f) {
                f3 += (float)(f > 0.0f ? -45 : 45);
                f2 = 0.0f;
            } else if (f2 <= -1.0f) {
                f3 += (float)(f > 0.0f ? 45 : -45);
                f2 = 0.0f;
            }
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        double d2 = Math.cos(Math.toRadians(f3 + 90.0f));
        double d3 = Math.sin(Math.toRadians(f3 + 90.0f));
        double d4 = (double)f * d * d2 + (double)f2 * d * d3;
        double d5 = (double)f * d * d3 - (double)f2 * d * d2;
        return new Vector2(d4, d5);
    }

    public static class_243 getHorizontalVelocity(double d) {
        Rotation rotation;
        float f = PlayerUtils.mc.field_1724.field_6031;
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && (rotation = BaritoneUtils.getTarget()) != null) {
            f = rotation.getYaw();
        }
        rotation = class_243.method_1030((float)0.0f, (float)f);
        class_243 class_2432 = class_243.method_1030((float)0.0f, (float)(f + 90.0f));
        double d2 = 0.0;
        double d3 = 0.0;
        boolean bl = false;
        if (PlayerUtils.mc.field_1724.field_3913.field_3910) {
            d2 += rotation.field_1352 / 20.0 * d;
            d3 += rotation.field_1350 / 20.0 * d;
            bl = true;
        }
        if (PlayerUtils.mc.field_1724.field_3913.field_3909) {
            d2 -= rotation.field_1352 / 20.0 * d;
            d3 -= rotation.field_1350 / 20.0 * d;
            bl = true;
        }
        boolean bl2 = false;
        if (PlayerUtils.mc.field_1724.field_3913.field_3906) {
            d2 += class_2432.field_1352 / 20.0 * d;
            d3 += class_2432.field_1350 / 20.0 * d;
            bl2 = true;
        }
        if (PlayerUtils.mc.field_1724.field_3913.field_3908) {
            d2 -= class_2432.field_1352 / 20.0 * d;
            d3 -= class_2432.field_1350 / 20.0 * d;
            bl2 = true;
        }
        if (bl && bl2) {
            d2 *= diagonal;
            d3 *= diagonal;
        }
        ((IVec3d)horizontalVelocity).setXZ(d2, d3);
        return horizontalVelocity;
    }
}

