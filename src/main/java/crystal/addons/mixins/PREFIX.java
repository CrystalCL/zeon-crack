package crystal.addons.mixins;

import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {ChatUtils.class}, remap = false)
public class PREFIX {
    @Inject(method = "getPrefix", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getPrefix(CallbackInfoReturnable<Text> info) {
        BaseText text = new LiteralText(Formatting.GRAY + "[" + Formatting.BLUE + "Crystal" + Formatting.RED + "CL+" + Formatting.GRAY + "] ");
        info.setReturnValue(text);
    }
}
