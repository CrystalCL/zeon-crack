/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IBox;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Box.class})
public class BoxMixin
implements IBox {
    @Shadow
    @Final
    @Mutable
    public double minX;
    @Shadow
    @Final
    @Mutable
    public double minY;
    @Shadow
    @Final
    @Mutable
    public double minZ;
    @Shadow
    @Final
    @Mutable
    public double maxX;
    @Shadow
    @Final
    @Mutable
    public double maxY;
    @Shadow
    @Final
    @Mutable
    public double maxZ;

    @Override
    public void expand(double d) {
        this.minX -= d;
        this.minY -= d;
        this.minZ -= d;
        this.maxX += d;
        this.maxY += d;
        this.maxZ += d;
    }
}

