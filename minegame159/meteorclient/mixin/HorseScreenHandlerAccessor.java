/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.HorseBaseEntity
 *  net.minecraft.screen.HorseScreenHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.screen.HorseScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={HorseScreenHandler.class})
public interface HorseScreenHandlerAccessor {
    @Accessor(value="entity")
    public HorseBaseEntity getEntity();
}

