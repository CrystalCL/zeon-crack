/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={EnchantingTableBlockEntityRenderer.class})
public class EnchantingTableBlockEntityRendererMixin {
    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/BookModel;renderBook(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void onRenderBookModelRenderProxy(BookModel BookModel2, MatrixStack MatrixStack2, VertexConsumer VertexConsumer2, int n, int n2, float f, float f2, float f3, float f4) {
        if (!Modules.get().get(NoRender.class).noEnchTableBook()) {
            BookModel2.renderBook(MatrixStack2, VertexConsumer2, n, n2, f, f2, f3, f4);
        }
    }
}

