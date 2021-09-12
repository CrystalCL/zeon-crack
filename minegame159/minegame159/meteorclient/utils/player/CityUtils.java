/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import java.util.ArrayList;
import minegame159.meteorclient.mixin.AbstractBlockAccessor;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class CityUtils {
    private static final class_310 mc;
    static final boolean $assertionsDisabled;
    private static final class_2338[] surround;

    public static class_1657 getPlayerTarget(double d) {
        if (CityUtils.mc.field_1724.method_29504()) {
            return null;
        }
        Object object = null;
        for (class_1657 object2 : CityUtils.mc.field_1687.method_18456()) {
            if (object2 == CityUtils.mc.field_1724 || object2.method_29504() || !Friends.get().attack(object2) || (double)CityUtils.mc.field_1724.method_5739((class_1297)object2) > d) continue;
            if (object == null) {
                object = object2;
                continue;
            }
            if (!(CityUtils.mc.field_1724.method_5739((class_1297)object2) < CityUtils.mc.field_1724.method_5739((class_1297)object))) continue;
            object = object2;
        }
        if (object == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (fakePlayerEntity.method_29504() || !Friends.get().attack((class_1657)fakePlayerEntity) || (double)CityUtils.mc.field_1724.method_5739((class_1297)fakePlayerEntity) > d) continue;
                if (object == null) {
                    object = fakePlayerEntity;
                    continue;
                }
                if (!(CityUtils.mc.field_1724.method_5739((class_1297)fakePlayerEntity) < CityUtils.mc.field_1724.method_5739((class_1297)object))) continue;
                object = fakePlayerEntity;
            }
        }
        return object;
    }

    public static class_2338 getTargetBlock(class_1657 class_16572) {
        class_2338 class_23382 = null;
        ArrayList<class_2338> arrayList = CityUtils.getTargetSurround(class_16572);
        ArrayList<class_2338> arrayList2 = CityUtils.getTargetSurround((class_1657)CityUtils.mc.field_1724);
        if (arrayList == null) {
            return null;
        }
        for (class_2338 class_23383 : arrayList) {
            if (arrayList2 != null && !arrayList2.isEmpty() && arrayList2.contains(class_23383)) continue;
            if (class_23382 == null) {
                class_23382 = class_23383;
                continue;
            }
            if (!(CityUtils.mc.field_1724.method_5707(Utils.vec3d(class_23383)) < CityUtils.mc.field_1724.method_5707(Utils.vec3d(class_23382)))) continue;
            class_23382 = class_23383;
        }
        return class_23382;
    }

    private static ArrayList<class_2338> getTargetSurround(class_1657 class_16572) {
        ArrayList<class_2338> arrayList = new ArrayList<class_2338>();
        boolean bl = false;
        for (int i = 0; i < 4; ++i) {
            class_2338 class_23382;
            if (class_16572 == null || (class_23382 = CityUtils.getSurround((class_1297)class_16572, surround[i])) == null) continue;
            if (!$assertionsDisabled && CityUtils.mc.field_1687 == null) {
                throw new AssertionError();
            }
            if (CityUtils.mc.field_1687.method_8320(class_23382) == null) continue;
            if (!((AbstractBlockAccessor)CityUtils.mc.field_1687.method_8320(class_23382).method_26204()).isCollidable()) {
                bl = true;
            }
            if (CityUtils.mc.field_1687.method_8320(class_23382).method_26204() != class_2246.field_10540) continue;
            arrayList.add(class_23382);
            if (3 <= 3) continue;
            return null;
        }
        if (bl) {
            return null;
        }
        return arrayList;
    }

    static {
        $assertionsDisabled = !CityUtils.class.desiredAssertionStatus();
        mc = class_310.method_1551();
        surround = new class_2338[]{new class_2338(0, 0, -1), new class_2338(1, 0, 0), new class_2338(0, 0, 1), new class_2338(-1, 0, 0)};
    }

    public static class_2338 getSurround(class_1297 class_12972, class_2338 class_23382) {
        class_243 class_2432 = class_12972.method_19538();
        if (class_23382 == null) {
            return new class_2338(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        }
        return new class_2338(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350).method_10081((class_2382)class_23382);
    }
}

