/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientPlayerInteractionManager.class})
public interface ClientPlayerInteractionManagerAccessor {
    @Accessor(value="currentBreakingProgress")
    public float getBreakingProgress();

    @Accessor(value="currentBreakingPos")
    public BlockPos getCurrentBreakingBlockPos();
}

