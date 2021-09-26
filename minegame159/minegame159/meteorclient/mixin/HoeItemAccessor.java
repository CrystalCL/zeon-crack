/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.Set;
import net.minecraft.item.HoeItem;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={HoeItem.class})
public interface HoeItemAccessor {
    @Accessor(value="EFFECTIVE_BLOCKS")
    public static Set<Block> getEffectiveBlocks() {
        return null;
    }
}

