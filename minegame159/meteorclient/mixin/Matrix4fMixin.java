/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IMatrix4f;
import minegame159.meteorclient.utils.misc.Vec4;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Matrix4f.class})
public class Matrix4fMixin
implements IMatrix4f {
    @Shadow
    protected float a00;
    @Shadow
    protected float a10;
    @Shadow
    protected float a20;
    @Shadow
    protected float a30;
    @Shadow
    protected float a01;
    @Shadow
    protected float a11;
    @Shadow
    protected float a21;
    @Shadow
    protected float a31;
    @Shadow
    protected float a02;
    @Shadow
    protected float a12;
    @Shadow
    protected float a22;
    @Shadow
    protected float a32;
    @Shadow
    protected float a03;
    @Shadow
    protected float a13;
    @Shadow
    protected float a23;
    @Shadow
    protected float a33;

    @Override
    public void multiplyMatrix(Vec4 v, Vec4 out) {
        out.set((double)this.a00 * v.x + (double)this.a01 * v.y + (double)this.a02 * v.z + (double)this.a03 * v.w, (double)this.a10 * v.x + (double)this.a11 * v.y + (double)this.a12 * v.z + (double)this.a13 * v.w, (double)this.a20 * v.x + (double)this.a21 * v.y + (double)this.a22 * v.z + (double)this.a23 * v.w, (double)this.a30 * v.x + (double)this.a31 * v.y + (double)this.a32 * v.z + (double)this.a33 * v.w);
    }
}

