/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.NoSlow;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.SlimeBlock;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SlimeBlock.class})
public class SlimeBlockMixin {
    @Inject(method={"onSteppedOn"}, at={@At(value="HEAD")}, cancellable=true)
    private void onSteppedOn(World World2, BlockPos BlockPos2, Entity Entity2, CallbackInfo callbackInfo) {
        if (Modules.get().get(NoSlow.class).slimeBlock() && Entity2 == MinecraftClient.getInstance().player) {
            callbackInfo.cancel();
        }
    }
}

