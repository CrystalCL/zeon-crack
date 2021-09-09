/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.AxeItem
 *  net.minecraft.block.Block
 *  net.minecraft.block.Material
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import java.util.Set;
import net.minecraft.item.AxeItem;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={AxeItem.class})
public interface AxeItemAccessor {
    @Accessor(value="field_23139")
    public static Set<Material> getEffectiveMaterials() {
        return null;
    }

    @Accessor(value="EFFECTIVE_BLOCKS")
    public static Set<Block> getEffectiveBlocks() {
        return null;
    }
}

