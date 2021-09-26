/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GetTooltipEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={Screen.class})
public abstract class ScreenMixin {
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Inject(method={"renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRenderBackground(CallbackInfo callbackInfo) {
        if (Utils.canUpdate() && Modules.get().get(NoRender.class).noGuiBackground()) {
            callbackInfo.cancel();
        }
    }

    @Shadow
    protected <T extends ClickableWidget> T addButton(T t) {
        return null;
    }

    @Shadow
    public void tick() {
    }

    @ModifyArgs(method={"renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/Screen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V"))
    private void getList(Args args, MatrixStack MatrixStack2, ItemStack ItemStack2, int n, int n2) {
        GetTooltipEvent.Modify modify = MeteorClient.EVENT_BUS.post(GetTooltipEvent.Modify.get(ItemStack2, (List)args.get(1), MatrixStack2, n, n2));
        args.set(0, (Object)modify.matrixStack);
        args.set(1, (Object)modify.list);
        args.set(2, (Object)modify.x);
        args.set(3, (Object)modify.y);
    }
}

