/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IEntityRenderer;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Nametags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public abstract class EntityRendererMixin<T extends Entity>
implements IEntityRenderer {
    @Shadow
    public abstract Identifier getTexture(Entity var1);

    @Inject(method={"renderLabelIfPresent"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderLabel(T t, Text Text2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, CallbackInfo callbackInfo) {
        if (!(t instanceof PlayerEntity)) {
            return;
        }
        if (Modules.get().isActive(Nametags.class)) {
            callbackInfo.cancel();
        }
    }

    @Override
    public Identifier getTextureInterface(Entity Entity2) {
        return this.getTexture(Entity2);
    }
}

