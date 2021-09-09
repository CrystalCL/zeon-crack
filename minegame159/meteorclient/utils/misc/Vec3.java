/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.MathHelper
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

public class Vec3 {
    public /* synthetic */ double y;
    public /* synthetic */ double z;
    public /* synthetic */ double x;

    public void add(double llllllllllllllllllllIIlIlIIllIlI, double llllllllllllllllllllIIlIlIIlIlIl, double llllllllllllllllllllIIlIlIIlIlII) {
        llllllllllllllllllllIIlIlIIllIll.x += llllllllllllllllllllIIlIlIIllIlI;
        llllllllllllllllllllIIlIlIIllIll.y += llllllllllllllllllllIIlIlIIlIlIl;
        llllllllllllllllllllIIlIlIIllIll.z += llllllllllllllllllllIIlIlIIlIlII;
    }

    public void set(Vec3d llllllllllllllllllllIIlIlIlIlIll) {
        llllllllllllllllllllIIlIlIlIlIlI.x = llllllllllllllllllllIIlIlIlIlIll.x;
        llllllllllllllllllllIIlIlIlIlIlI.y = llllllllllllllllllllIIlIlIlIlIll.y;
        llllllllllllllllllllIIlIlIlIlIlI.z = llllllllllllllllllllIIlIlIlIlIll.z;
    }

    public void set(Entity llllllllllllllllllllIIlIlIlIIlII, double llllllllllllllllllllIIlIlIlIIIll) {
        llllllllllllllllllllIIlIlIlIIlIl.x = MathHelper.lerp((double)llllllllllllllllllllIIlIlIlIIIll, (double)llllllllllllllllllllIIlIlIlIIlII.lastRenderX, (double)llllllllllllllllllllIIlIlIlIIlII.getX());
        llllllllllllllllllllIIlIlIlIIlIl.y = MathHelper.lerp((double)llllllllllllllllllllIIlIlIlIIIll, (double)llllllllllllllllllllIIlIlIlIIlII.lastRenderY, (double)llllllllllllllllllllIIlIlIlIIlII.getY());
        llllllllllllllllllllIIlIlIlIIlIl.z = MathHelper.lerp((double)llllllllllllllllllllIIlIlIlIIIll, (double)llllllllllllllllllllIIlIlIlIIlII.lastRenderZ, (double)llllllllllllllllllllIIlIlIlIIlII.getZ());
    }

    public Vec3() {
        Vec3 llllllllllllllllllllIIlIllIIIIlI;
    }

    public void set(double llllllllllllllllllllIIlIlIllIlll, double llllllllllllllllllllIIlIlIllIllI, double llllllllllllllllllllIIlIlIlllIIl) {
        llllllllllllllllllllIIlIlIllllII.x = llllllllllllllllllllIIlIlIllIlll;
        llllllllllllllllllllIIlIlIllllII.y = llllllllllllllllllllIIlIlIllIllI;
        llllllllllllllllllllIIlIlIllllII.z = llllllllllllllllllllIIlIlIlllIIl;
    }

    public void negate() {
        Vec3 llllllllllllllllllllIIlIlIIlIIIl;
        llllllllllllllllllllIIlIlIIlIIIl.x = -llllllllllllllllllllIIlIlIIlIIIl.x;
        llllllllllllllllllllIIlIlIIlIIIl.y = -llllllllllllllllllllIIlIlIIlIIIl.y;
        llllllllllllllllllllIIlIlIIlIIIl.z = -llllllllllllllllllllIIlIlIIlIIIl.z;
    }

    public void set(Vec3 llllllllllllllllllllIIlIlIllIIIl) {
        llllllllllllllllllllIIlIlIllIIlI.x = llllllllllllllllllllIIlIlIllIIIl.x;
        llllllllllllllllllllIIlIlIllIIlI.y = llllllllllllllllllllIIlIlIllIIIl.y;
        llllllllllllllllllllIIlIlIllIIlI.z = llllllllllllllllllllIIlIlIllIIIl.z;
    }

    public double distanceTo(Vec3 llllllllllllllllllllIIlIlIIIlIlI) {
        Vec3 llllllllllllllllllllIIlIlIIIlIll;
        double llllllllllllllllllllIIlIlIIIlIIl = llllllllllllllllllllIIlIlIIIlIlI.x - llllllllllllllllllllIIlIlIIIlIll.x;
        double llllllllllllllllllllIIlIlIIIlIII = llllllllllllllllllllIIlIlIIIlIlI.y - llllllllllllllllllllIIlIlIIIlIll.y;
        double llllllllllllllllllllIIlIlIIIIlll = llllllllllllllllllllIIlIlIIIlIlI.z - llllllllllllllllllllIIlIlIIIlIll.z;
        return Math.sqrt(llllllllllllllllllllIIlIlIIIlIIl * llllllllllllllllllllIIlIlIIIlIIl + llllllllllllllllllllIIlIlIIIlIII * llllllllllllllllllllIIlIlIIIlIII + llllllllllllllllllllIIlIlIIIIlll * llllllllllllllllllllIIlIlIIIIlll);
    }
}

