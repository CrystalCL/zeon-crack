/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3i
 */
package minegame159.meteorclient.mixininterface;

import minegame159.meteorclient.utils.misc.Vec3;
import net.minecraft.util.math.Vec3i;

public interface IVec3d {
    public void set(double var1, double var3, double var5);

    default public void set(Vec3i llllllllllllllllllllIllIlIlllIIl) {
        IVec3d llllllllllllllllllllIllIlIllllII;
        llllllllllllllllllllIllIlIllllII.set(llllllllllllllllllllIllIlIlllIIl.getX(), llllllllllllllllllllIllIlIlllIIl.getY(), llllllllllllllllllllIllIlIlllIIl.getZ());
    }

    public void setY(double var1);

    default public void set(Vec3 llllllllllllllllllllIllIlIllIlIl) {
        IVec3d llllllllllllllllllllIllIlIllIlII;
        llllllllllllllllllllIllIlIllIlII.set(llllllllllllllllllllIllIlIllIlIl.x, llllllllllllllllllllIllIlIllIlIl.y, llllllllllllllllllllIllIlIllIlIl.z);
    }

    public void setXZ(double var1, double var3);
}

