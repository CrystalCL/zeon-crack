/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={AbstractFurnaceScreenHandler.class})
public interface AbstractFurnaceScreenHandlerAccessor {
    @Invoker(value="isSmeltable")
    public boolean isSmeltable(ItemStack var1);

    @Invoker(value="isFuel")
    public boolean isFuel(ItemStack var1);
}

