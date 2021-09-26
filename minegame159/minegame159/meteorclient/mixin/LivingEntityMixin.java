/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.DamageEvent;
import minegame159.meteorclient.events.entity.TookDamageEvent;
import minegame159.meteorclient.events.entity.player.CanWalkOnFluidEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.OffhandCrash;
import minegame159.meteorclient.systems.modules.movement.AntiLevitation;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LivingEntity.class})
public abstract class LivingEntityMixin
extends Entity {
    public LivingEntityMixin(EntityType<?> EntityType2, World World2) {
        super(EntityType2, World2);
    }

    @Inject(method={"damage"}, at={@At(value="HEAD")})
    private void onDamageHead(DamageSource DamageSource2, float f, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Utils.canUpdate()) {
            MeteorClient.EVENT_BUS.post(DamageEvent.get((LivingEntity)this, DamageSource2));
        }
    }

    @Inject(method={"damage"}, at={@At(value="TAIL")})
    private void onDamageTail(DamageSource DamageSource2, float f, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Utils.canUpdate()) {
            MeteorClient.EVENT_BUS.post(TookDamageEvent.get((LivingEntity)this, DamageSource2));
        }
    }

    @Inject(method={"canWalkOnFluid"}, at={@At(value="HEAD")}, cancellable=true)
    private void onCanWalkOnFluid(Fluid Fluid2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        CanWalkOnFluidEvent canWalkOnFluidEvent = MeteorClient.EVENT_BUS.post(CanWalkOnFluidEvent.get((LivingEntity)this, Fluid2));
        if (canWalkOnFluidEvent.walkOnFluid) {
            callbackInfoReturnable.setReturnValue((Object)true);
        }
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean travelHasStatusEffectProxy(LivingEntity LivingEntity2, StatusEffect StatusEffect2) {
        if (StatusEffect2 == StatusEffects.LEVITATION && Modules.get().isActive(AntiLevitation.class)) {
            return false;
        }
        return LivingEntity2.hasStatusEffect(StatusEffect2);
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z"))
    private boolean travelHasNoGravityProxy(LivingEntity LivingEntity2) {
        if (LivingEntity2.hasStatusEffect(StatusEffects.LEVITATION) && Modules.get().isActive(AntiLevitation.class)) {
            return !Modules.get().get(AntiLevitation.class).isApplyGravity();
        }
        return LivingEntity2.hasNoGravity();
    }

    @Inject(method={"spawnItemParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private void spawnItemParticles(ItemStack ItemStack2, int n, CallbackInfo callbackInfo) {
        NoRender noRender = Modules.get().get(NoRender.class);
        if (noRender.noEatParticles() && ItemStack2.isFood()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"onEquipStack"}, at={@At(value="HEAD")}, cancellable=true)
    private void onEquipStack(ItemStack ItemStack2, CallbackInfo callbackInfo) {
        if (this == MinecraftClient.getInstance().player && Modules.get().get(OffhandCrash.class).isAntiCrash()) {
            callbackInfo.cancel();
        }
    }
}

