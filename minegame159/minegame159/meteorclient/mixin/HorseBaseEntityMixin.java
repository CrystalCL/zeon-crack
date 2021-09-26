/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IHorseBaseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={HorseBaseEntity.class})
public abstract class HorseBaseEntityMixin
implements IHorseBaseEntity {
    @Shadow
    protected abstract void setHorseFlag(int var1, boolean var2);

    @Override
    public void setSaddled(boolean bl) {
        this.setHorseFlag(4, bl);
    }
}

