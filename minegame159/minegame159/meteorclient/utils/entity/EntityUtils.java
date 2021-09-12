/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.misc.text.TextUtils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1934;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3611;
import net.minecraft.class_3612;
import net.minecraft.class_640;

public class EntityUtils {
    private static final List<class_1297> entities = new ArrayList<class_1297>();
    public static class_310 mc;

    public static boolean isAttackable(class_1299<?> class_12992) {
        return class_12992 != class_1299.field_6083 && class_12992 != class_1299.field_6122 && class_12992 != class_1299.field_6089 && class_12992 != class_1299.field_6133 && class_12992 != class_1299.field_6052 && class_12992 != class_1299.field_6124 && class_12992 != class_1299.field_6135 && class_12992 != class_1299.field_6082 && class_12992 != class_1299.field_6064 && class_12992 != class_1299.field_6045 && class_12992 != class_1299.field_6127 && class_12992 != class_1299.field_6112 && class_12992 != class_1299.field_6103 && class_12992 != class_1299.field_6044 && class_12992 != class_1299.field_6144;
    }

    private static boolean lambda$getPlayerTarget$1(double d, boolean bl, class_1297 class_12972) {
        if (!(class_12972 instanceof class_1657) || class_12972 == EntityUtils.mc.field_1724) {
            return false;
        }
        if (((class_1657)class_12972).method_29504() || ((class_1657)class_12972).method_6032() <= 0.0f) {
            return false;
        }
        if ((double)EntityUtils.mc.field_1724.method_5739(class_12972) > d) {
            return false;
        }
        if (!Friends.get().attack((class_1657)class_12972) && !bl) {
            return false;
        }
        return EntityUtils.getGameMode((class_1657)class_12972) == class_1934.field_9215 || class_12972 instanceof FakePlayerEntity;
    }

    public static int getPing(class_1657 class_16572) {
        if (mc.method_1562() == null) {
            return 0;
        }
        class_640 class_6402 = mc.method_1562().method_2871(class_16572.method_5667());
        if (class_6402 == null) {
            return 0;
        }
        return class_6402.method_2959();
    }

    public static Color getEntityColor(class_1297 class_12972, Color color, Color color2, Color color3, Color color4, Color color5, Color color6, boolean bl) {
        if (class_12972 instanceof class_1657) {
            Color color7 = Friends.get().getFriendColor((class_1657)class_12972);
            if (color7 != null) {
                return new Color(color7.r, color7.g, color7.b, color.a);
            }
            if (bl) {
                return TextUtils.getMostPopularColor(class_12972.method_5476());
            }
            return color;
        }
        switch (class_12972.method_5864().method_5891()) {
            case field_6294: {
                return color2;
            }
            case field_6300: {
                return color3;
            }
            case field_6302: {
                return color4;
            }
            case field_6303: {
                return color5;
            }
            case field_17715: {
                return color6;
            }
        }
        return Utils.WHITE;
    }

    public static float getTotalHealth(class_1657 class_16572) {
        return class_16572.method_6032() + class_16572.method_6067();
    }

    public static void getList(Predicate<class_1297> predicate, SortPriority sortPriority, List<class_1297> list) {
        for (class_1297 class_12972 : EntityUtils.mc.field_1687.method_18112()) {
            if (!predicate.test(class_12972)) continue;
            list.add(class_12972);
        }
        for (class_1297 class_12972 : FakePlayerUtils.getPlayers().keySet()) {
            if (!predicate.test(class_12972)) continue;
            list.add(class_12972);
        }
        list.sort((arg_0, arg_1) -> EntityUtils.lambda$getList$0(sortPriority, arg_0, arg_1));
    }

    public static boolean isInRenderDistance(class_2338 class_23382) {
        if (class_23382 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(class_23382.method_10263(), class_23382.method_10260());
    }

    public static class_1297 get(Predicate<class_1297> predicate, SortPriority sortPriority) {
        entities.clear();
        EntityUtils.getList(predicate, sortPriority, entities);
        if (!entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }

    private static int invertSort(int n) {
        if (n == 0) {
            return 0;
        }
        return n > 0 ? -1 : 1;
    }

    private static int sort(class_1297 class_12972, class_1297 class_12973, SortPriority sortPriority) {
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$SortPriority[sortPriority.ordinal()]) {
            case 1: {
                return Double.compare(class_12972.method_5739((class_1297)EntityUtils.mc.field_1724), class_12973.method_5739((class_1297)EntityUtils.mc.field_1724));
            }
            case 2: {
                return EntityUtils.invertSort(Double.compare(class_12972.method_5739((class_1297)EntityUtils.mc.field_1724), class_12973.method_5739((class_1297)EntityUtils.mc.field_1724)));
            }
            case 3: {
                return EntityUtils.sortHealth(class_12972, class_12973);
            }
            case 4: {
                return EntityUtils.invertSort(EntityUtils.sortHealth(class_12972, class_12973));
            }
        }
        return 0;
    }

    private static int lambda$getList$0(SortPriority sortPriority, class_1297 class_12972, class_1297 class_12973) {
        return EntityUtils.sort(class_12972, class_12973, sortPriority);
    }

    public static boolean isAboveWater(class_1297 class_12972) {
        class_2680 class_26802;
        class_2338.class_2339 class_23392 = class_12972.method_24515().method_25503();
        for (int i = 0; i < 64 && !(class_26802 = EntityUtils.mc.field_1687.method_8320((class_2338)class_23392)).method_26207().method_15801(); ++i) {
            class_3611 class_36112 = class_26802.method_26227().method_15772();
            if (class_36112 == class_3612.field_15910 || class_36112 == class_3612.field_15909) {
                return true;
            }
            class_23392.method_10100(0, -1, 0);
            if (-5 < 0) continue;
            return false;
        }
        return false;
    }

    public static boolean isInvalid(class_1657 class_16572, double d) {
        if (class_16572 == null) {
            return true;
        }
        return (double)EntityUtils.mc.field_1724.method_5739((class_1297)class_16572) > d || !class_16572.method_5805() || class_16572.method_29504() || class_16572.method_6032() <= 0.0f;
    }

    public static class_1934 getGameMode(class_1657 class_16572) {
        class_640 class_6402 = mc.method_1562().method_2871(class_16572.method_5667());
        if (class_6402 == null) {
            return null;
        }
        return class_6402.method_2958();
    }

    public static class_1657 getPlayerTarget(double d, SortPriority sortPriority, boolean bl) {
        if (!Utils.canUpdate()) {
            return null;
        }
        return (class_1657)EntityUtils.get(arg_0 -> EntityUtils.lambda$getPlayerTarget$1(d, bl, arg_0), sortPriority);
    }

    public static boolean isInRenderDistance(double d, double d2) {
        double d3 = Math.abs(EntityUtils.mc.field_1773.method_19418().method_19326().field_1352 - d);
        double d4 = Math.abs(EntityUtils.mc.field_1773.method_19418().method_19326().field_1350 - d2);
        double d5 = (EntityUtils.mc.field_1690.field_1870 + 1) * 16;
        return d3 < d5 && d4 < d5;
    }

    public static boolean isInRenderDistance(class_2586 class_25862) {
        if (class_25862 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(class_25862.method_11016().method_10263(), class_25862.method_11016().method_10260());
    }

    public static boolean isInRenderDistance(class_1297 class_12972) {
        if (class_12972 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(class_12972.method_23317(), class_12972.method_23321());
    }

    private static int sortHealth(class_1297 class_12972, class_1297 class_12973) {
        boolean bl = class_12972 instanceof class_1309;
        boolean bl2 = class_12973 instanceof class_1309;
        if (!bl && !bl2) {
            return 0;
        }
        if (bl && !bl2) {
            return 1;
        }
        if (!bl) {
            return -1;
        }
        return Float.compare(((class_1309)class_12972).method_6032(), ((class_1309)class_12973).method_6032());
    }
}

