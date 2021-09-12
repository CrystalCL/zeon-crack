/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3965;

public class Ezz {
    private static final class_310 mc = class_310.method_1551();

    public static boolean BlockPlace(int n, int n2, int n3, int n4, boolean bl) {
        return Ezz.BlockPlace(new class_2338(n, n2, n3), n4, bl);
    }

    public static void attackEntity(class_1297 class_12972) {
        Ezz.mc.field_1761.method_2918((class_1657)Ezz.mc.field_1724, class_12972);
    }

    public static void clickSlot(int n, int n2, class_1713 class_17132) {
        Ezz.mc.field_1761.method_2906(Ezz.mc.field_1724.field_7512.field_7763, n, n2, class_17132, (class_1657)Ezz.mc.field_1724);
    }

    public static void interact(class_2338 class_23382, int n, class_2350 class_23502) {
        int n2 = Ezz.mc.field_1724.field_7514.field_7545;
        Ezz.swap(n);
        Ezz.mc.field_1761.method_2896(Ezz.mc.field_1724, Ezz.mc.field_1687, class_1268.field_5808, new class_3965(Ezz.mc.field_1724.method_19538(), class_23502, class_23382, true));
        Ezz.swap(n2);
    }

    public static boolean equalsBlockPos(class_2338 class_23382, class_2338 class_23383) {
        if (!(class_23382 instanceof class_2382) || !(class_23382 instanceof class_2382)) {
            return false;
        }
        if (class_23382 == null || class_23383 == null) {
            return false;
        }
        if (class_23382.method_10263() != class_23383.method_10263()) {
            return false;
        }
        if (class_23382.method_10264() != class_23383.method_10264()) {
            return false;
        }
        return class_23382.method_10260() == class_23383.method_10260();
    }

    public static boolean isFriend(class_1657 class_16572) {
        if (class_16572 == null) {
            return false;
        }
        return Ezz.isFriend(class_16572.method_5477().method_10851());
    }

    public static double DistanceTo(int n, double d, double d2) {
        double d3 = n;
        double d4 = d;
        double d5 = d2;
        d3 = d3 >= 0.0 ? (d3 += 0.5) : (d3 -= 0.5);
        d4 = d4 >= 0.0 ? (d4 += 0.5) : (d4 -= 0.5);
        d5 = d5 >= 0.0 ? (d5 += 0.5) : (d5 -= 0.5);
        double d6 = Ezz.mc.field_1724.method_23317() - d3;
        double d7 = Ezz.mc.field_1724.method_23318() - d4;
        double d8 = Ezz.mc.field_1724.method_23321() - d5;
        return Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
    }

    public static boolean BlockPlace(class_2338 class_23382, int n, boolean bl) {
        if (n == -1) {
            return false;
        }
        if (!BlockUtils.canPlace(class_23382, true)) {
            return false;
        }
        int n2 = Ezz.mc.field_1724.field_7514.field_7545;
        Ezz.swap(n);
        if (bl) {
            class_243 class_2432 = new class_243(0.0, 0.0, 0.0);
            ((IVec3d)class_2432).set((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 0.5, (double)class_23382.method_10260() + 0.5);
            Rotations.rotate(Rotations.getYaw(class_2432), Rotations.getPitch(class_2432));
        }
        Ezz.mc.field_1761.method_2896(Ezz.mc.field_1724, Ezz.mc.field_1687, class_1268.field_5808, new class_3965(Ezz.mc.field_1724.method_19538(), class_2350.field_11033, class_23382, true));
        Ezz.swap(n2);
        return true;
    }

    public static double distanceToBlockAnge(class_2338 class_23382) {
        double d = Ezz.mc.field_1724.method_23317();
        double d2 = Ezz.mc.field_1724.method_23318() + 1.0;
        double d3 = Ezz.mc.field_1724.method_23321();
        double d4 = class_23382.method_10263();
        double d5 = class_23382.method_10264();
        double d6 = class_23382.method_10260();
        if (d5 == Ezz.floor(d2)) {
            d5 = d2;
        }
        if (d4 > 0.0 && d4 == Ezz.floor(d)) {
            d4 = d;
        }
        if (d4 < 0.0 && d4 + 1.0 == Ezz.floor(d)) {
            d4 = d;
        }
        if (d6 > 0.0 && d6 == Ezz.floor(d3)) {
            d6 = d3;
        }
        if (d6 < 0.0 && d6 + 1.0 == Ezz.floor(d3)) {
            d6 = d3;
        }
        if (d4 < d) {
            d4 += 1.0;
        }
        if (d5 < d2) {
            d5 += 1.0;
        }
        if (d6 < d3) {
            d6 += 1.0;
        }
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        return Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    public static class_2338 SetRelative(int n, int n2, int n3) {
        return new class_2338(Ezz.mc.field_1724.method_23317() + (double)n, Ezz.mc.field_1724.method_23318() + (double)n2, Ezz.mc.field_1724.method_23321() + (double)n3);
    }

    public static boolean isFriend(String string) {
        return Friends.get().getAll().contains(new Friend(string));
    }

    public static void swap(int n) {
        if (n != Ezz.mc.field_1724.field_7514.field_7545 && n >= 0 && n < 9) {
            Ezz.mc.field_1724.field_7514.field_7545 = n;
        }
    }

    public static double DistanceTo(class_2338 class_23382) {
        return Ezz.DistanceTo(class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260());
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    public static double floor(double d) {
        return (long)d;
    }

    public static int invIndexToSlotId(int n) {
        if (n < 9 && n != -1) {
            return 44 - (8 - n);
        }
        return n;
    }
}

