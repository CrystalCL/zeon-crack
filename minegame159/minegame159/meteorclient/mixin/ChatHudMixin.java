/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BetterChat;
import net.minecraft.class_2561;
import net.minecraft.class_303;
import net.minecraft.class_338;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_338.class})
public abstract class ChatHudMixin {
    @Shadow
    @Final
    private List<class_303<class_5481>> field_2064;
    @Shadow
    @Final
    private List<class_303<class_2561>> field_2061;

    @Inject(at={@At(value="HEAD")}, method={"addMessage(Lnet/minecraft/text/Text;IIZ)V"}, cancellable=true)
    private void onAddMessage(class_2561 class_25612, int n, int n2, boolean bl, CallbackInfo callbackInfo) {
        if (Modules.get().get(BetterChat.class).onMsg(class_25612.getString(), n, n2, this.field_2061, this.field_2064)) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"addMessage(Lnet/minecraft/text/Text;IIZ)V"}, at=@At(value="INVOKE", target="Ljava/util/List;size()I"))
    private int addMessageListSizeProxy(List<class_303> list) {
        BetterChat betterChat = Modules.get().get(BetterChat.class);
        return betterChat.isLongerChat() && betterChat.getChatLength() > 100 ? 1 : list.size();
    }
}

