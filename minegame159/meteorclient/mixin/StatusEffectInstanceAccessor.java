/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={StatusEffectInstance.class})
public interface StatusEffectInstanceAccessor {
    @Accessor(value="duration")
    public void setDuration(int var1);

    @Accessor(value="amplifier")
    public void setAmplifier(int var1);
}

