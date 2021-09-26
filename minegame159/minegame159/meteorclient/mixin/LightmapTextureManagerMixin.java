/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Fullbright;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={LightmapTextureManager.class})
public class LightmapTextureManagerMixin {
    @Redirect(method={"update"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal=0))
    private boolean updateHasStatusEffectProxy(ClientPlayerEntity ClientPlayerEntity2, StatusEffect StatusEffect2) {
        return Modules.get().isActive(Fullbright.class) || ClientPlayerEntity2.hasStatusEffect(StatusEffect2);
    }

    @Redirect(method={"update"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/GameRenderer;getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F"))
    private float updateGetNightVisionStrengthProxy(LivingEntity LivingEntity2, float f) {
        return Modules.get().isActive(Fullbright.class) ? 1.0f : GameRenderer.getNightVisionStrength((LivingEntity)LivingEntity2, (float)f);
    }
}

