/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.RenderEntityEvent;
import net.minecraft.class_1511;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_630;
import net.minecraft.class_892;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_892.class})
public abstract class EndCrystalEntityRendererMixin {
    @Shadow
    @Final
    private class_630 field_21003;
    @Shadow
    @Final
    private class_630 field_21004;
    @Shadow
    @Final
    private class_630 field_21005;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(class_1511 class_15112, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n, CallbackInfo callbackInfo) {
        RenderEntityEvent.Crystal crystal = MeteorClient.EVENT_BUS.post(RenderEntityEvent.Crystal.get(class_15112, f, f2, class_45872, class_45972, n, this.field_21003, this.field_21004, this.field_21005));
        if (crystal.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

