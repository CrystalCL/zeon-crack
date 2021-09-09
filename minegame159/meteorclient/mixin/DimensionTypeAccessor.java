/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.dimension.DimensionType
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={DimensionType.class})
public interface DimensionTypeAccessor {
    @Accessor(value="OVERWORLD")
    public static DimensionType getOverworld() {
        return null;
    }
}

