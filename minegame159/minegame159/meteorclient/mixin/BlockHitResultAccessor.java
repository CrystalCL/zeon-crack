/*
 * Decompiled with CFR 0.151.
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

