/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public class Vec3 {
    public double z;
    public double y;
    public double x;

    public void set(class_243 class_2432) {
        this.x = class_2432.field_1352;
        this.y = class_2432.field_1351;
        this.z = class_2432.field_1350;
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

    public void set(class_1297 class_12972, double d) {
        this.x = class_3532.method_16436((double)d, (double)class_12972.field_6038, (double)class_12972.method_23317());
        this.y = class_3532.method_16436((double)d, (double)class_12972.field_5971, (double)class_12972.method_23318());
        this.z = class_3532.method_16436((double)d, (double)class_12972.field_5989, (double)class_12972.method_23321());
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

