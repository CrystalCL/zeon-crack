/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

public class Vec3 {
    public double z;
    public double y;
    public double x;

    public void set(Vec3d Vec3d2) {
        this.x = Vec3d2.x;
        this.y = Vec3d2.y;
        this.z = Vec3d2.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public void set(Entity Entity2, double d) {
        this.x = MathHelper.lerp((double)d, (double)Entity2.lastRenderX, (double)Entity2.getX());
        this.y = MathHelper.lerp((double)d, (double)Entity2.lastRenderY, (double)Entity2.getY());
        this.z = MathHelper.lerp((double)d, (double)Entity2.lastRenderZ, (double)Entity2.getZ());
    }

    public double distanceTo(Vec3 vec3) {
        double d = vec3.x - this.x;
        double d2 = vec3.y - this.y;
        double d3 = vec3.z - this.z;
        return Math.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public void add(double d, double d2, double d3) {
        this.x += d;
        this.y += d2;
        this.z += d3;
    }

    public void set(Vec3 vec3) {
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
    }
}

