/*
 * Decompiled with CFR 0.151.
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

