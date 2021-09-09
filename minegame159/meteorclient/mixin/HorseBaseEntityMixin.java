/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.HorseBaseEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
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
    public void setSaddled(boolean saddled) {
        this.setHorseFlag(4, saddled);
    }
}

