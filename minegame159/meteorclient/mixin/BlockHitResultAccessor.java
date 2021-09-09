/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.hit.BlockHitResult
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BlockHitResult.class})
public interface BlockHitResultAccessor {
    @Accessor(value="side")
    public void setSide(Direction var1);
}

