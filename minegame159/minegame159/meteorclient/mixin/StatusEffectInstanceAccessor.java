/*
 * Decompiled with CFR 0.151.
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

