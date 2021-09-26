/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.function.Function;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import net.minecraft.text.Text;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={SignBlockEntityRenderer.class})
public class SignBlockEntityRendererMixin {
    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/entity/SignBlockEntity;getTextBeingEditedOnRow(ILjava/util/function/Function;)Lnet/minecraft/text/OrderedText;"))
    private OrderedText onRenderSignBlockEntityGetTextBeingEditedOnRowProxy(SignBlockEntity SignBlockEntity2, int n, Function<Text, OrderedText> function) {
        if (Modules.get().get(NoRender.class).noSignText()) {
            return null;
        }
        return SignBlockEntity2.getTextBeingEditedOnRow(n, function);
    }
}

