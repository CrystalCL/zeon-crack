/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.SettingsUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

public class GoalDirection
implements Goal {
    private int x;
    private static final double SQRT_2 = Math.sqrt(2.0);
    private final float yaw;
    private int z;

    public GoalDirection(Vec3d Vec3d2, float f) {
        this.yaw = f;
        this.recalculate(Vec3d2);
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public double heuristic(int n, int n2, int n3) {
        int n4 = n - this.x;
        int n5 = n3 - this.z;
        return GoalDirection.calculate(n4, n5);
    }

    public void recalculate(Vec3d Vec3d2) {
        float f = (float)Math.toRadians(this.yaw);
        this.x = (int)Math.floor(Vec3d2.x - (double)MathHelper.sin((float)f) * 100.0);
        this.z = (int)Math.floor(Vec3d2.z + (double)MathHelper.cos((float)f) * 100.0);
    }

    public String toString() {
        return String.format("GoalXZ{x=%s,z=%s}", SettingsUtil.maybeCensor((int)this.x), SettingsUtil.maybeCensor((int)this.z));
    }

    public static double calculate(double d, double d2) {
        double d3;
        double d4;
        double d5;
        double d6 = Math.abs(d);
        if (d6 < (d5 = Math.abs(d2))) {
            d4 = d5 - d6;
            d3 = d6;
        } else {
            d4 = d6 - d5;
            d3 = d5;
        }
        return ((d3 *= SQRT_2) + d4) * (Double)BaritoneAPI.getSettings().costHeuristic.value;
    }

    public boolean isInGoal(int n, int n2, int n3) {
        return n == this.x && n3 == this.z;
    }
}

