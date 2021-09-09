/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.CobwebBlock
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.MinecraftClient
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.NoSlow;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CobwebBlock.class})
public class CobwebBlockMixin {
    @Inject(method={"onEntityCollision"}, at={@At(value="HEAD")}, cancellable=true)
    private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo info) {
        if (Modules.get().get(NoSlow.class).web() && entity == MinecraftClient.getInstance().player) {
            info.cancel();
        }
    }
}

