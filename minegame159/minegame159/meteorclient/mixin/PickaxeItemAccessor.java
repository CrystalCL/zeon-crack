/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.Set;
import net.minecraft.item.PickaxeItem;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={PickaxeItem.class})
public interface PickaxeItemAccessor {
    @Accessor(value="EFFECTIVE_BLOCKS")
    public static Set<Block> getEffectiveBlocks() {
        return null;
    }
}

