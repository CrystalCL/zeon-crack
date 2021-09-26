/*
 * Decompiled with CFR 0.151.
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

