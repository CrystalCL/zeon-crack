/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ItemStack.class})
public interface ItemStackAccessor {
    @Accessor(value="item")
    public void setItem(Item var1);

    @Accessor(value="empty")
    public void setEmpty(boolean var1);
}

