/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.utils.SettingsUtil
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.MathHelper
 */
package minegame159.meteorclient.utils.world;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.SettingsUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

public class GoalDirection
implements Goal {
    private /* synthetic */ int z;
    private static final /* synthetic */ double SQRT_2;
    private /* synthetic */ int x;
    private final /* synthetic */ float yaw;

    public double heuristic(int lIIlIIIlIIlIIl, int lIIlIIIlIIlllI, int lIIlIIIlIIlIII) {
        GoalDirection lIIlIIIlIIlIlI;
        int lIIlIIIlIIllII = lIIlIIIlIIlIIl - lIIlIIIlIIlIlI.x;
        int lIIlIIIlIIlIll = lIIlIIIlIIlIII - lIIlIIIlIIlIlI.z;
        return GoalDirection.calculate(lIIlIIIlIIllII, lIIlIIIlIIlIll);
    }

    public boolean isInGoal(int lIIlIIIlIllIll, int lIIlIIIlIllIlI, int lIIlIIIlIlIllI) {
        GoalDirection lIIlIIIlIlllII;
        return lIIlIIIlIllIll == lIIlIIIlIlllII.x && lIIlIIIlIlIllI == lIIlIIIlIlllII.z;
    }

    public GoalDirection(Vec3d lIIlIIIllllllI, float lIIlIIlIIIIIII) {
        GoalDirection lIIlIIlIIIIIlI;
        lIIlIIlIIIIIlI.yaw = lIIlIIlIIIIIII;
        lIIlIIlIIIIIlI.recalculate(lIIlIIIllllllI);
    }

    public String toString() {
        GoalDirection lIIlIIIlIIIIll;
        return String.format("GoalXZ{x=%s,z=%s}", SettingsUtil.maybeCensor((int)lIIlIIIlIIIIll.x), SettingsUtil.maybeCensor((int)lIIlIIIlIIIIll.z));
    }

    public int getX() {
        GoalDirection lIIlIIIlIIIIII;
        return lIIlIIIlIIIIII.x;
    }

    static {
        SQRT_2 = Math.sqrt(2.0);
    }

    public int getZ() {
        GoalDirection lIIlIIIIlllllI;
        return lIIlIIIIlllllI.z;
    }

    public static double calculate(double lIIlIIIlllIlII, double lIIlIIIlllIIll) {
        double lIIlIIIlllIIII;
        double lIIlIIIlllIIIl;
        double lIIlIIIlllIIlI = Math.abs(lIIlIIIlllIlII);
        if (lIIlIIIlllIIlI < (lIIlIIIlllIIIl = Math.abs(lIIlIIIlllIIll))) {
            double lIIlIIIlllIllI = lIIlIIIlllIIIl - lIIlIIIlllIIlI;
            double lIIlIIIlllIlIl = lIIlIIIlllIIlI;
        } else {
            lIIlIIIlllIIII = lIIlIIIlllIIlI - lIIlIIIlllIIIl;
            double lIIlIIIllIllll = lIIlIIIlllIIIl;
        }
        return ((lIIlIIIllIllll *= SQRT_2) + lIIlIIIlllIIII) * (Double)BaritoneAPI.getSettings().costHeuristic.value;
    }

    public void recalculate(Vec3d lIIlIIIllIIlII) {
        GoalDirection lIIlIIIllIIlIl;
        float lIIlIIIllIIIll = (float)Math.toRadians(lIIlIIIllIIlIl.yaw);
        lIIlIIIllIIlIl.x = (int)Math.floor(lIIlIIIllIIlII.x - (double)MathHelper.sin((float)lIIlIIIllIIIll) * 100.0);
        lIIlIIIllIIlIl.z = (int)Math.floor(lIIlIIIllIIlII.z + (double)MathHelper.cos((float)lIIlIIIllIIIll) * 100.0);
    }
}

