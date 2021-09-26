/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixininterface;

import minegame159.meteorclient.utils.misc.Vec3;
import net.minecraft.util.math.Vec3i;

public interface IVec3d {
    public void setY(double var1);

    public void set(double var1, double var3, double var5);

    default public void set(Vec3i Vec3i2) {
        this.set(Vec3i2.getX(), Vec3i2.getY(), Vec3i2.getZ());
    }

    default public void set(Vec3 vec3) {
        this.set(vec3.x, vec3.y, vec3.z);
    }

    public void setXZ(double var1, double var3);
}

