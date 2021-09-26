/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.BetterTooltips;
import net.minecraft.item.ItemStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.world.BlockView;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ShulkerBoxBlock.class})
public class ShulkerBoxBlockMixin {
    @Inject(method={"appendTooltip"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAppendTooltip(ItemStack ItemStack2, BlockView BlockView2, List<Text> list, TooltipContext TooltipContext2, CallbackInfo callbackInfo) {
        if (Modules.get() == null) {
            return;
        }
        BetterTooltips betterTooltips = Modules.get().get(BetterTooltips.class);
        if (betterTooltips.isActive() && betterTooltips.previewShulkers()) {
            callbackInfo.cancel();
        }
    }
}

