/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BetterChat;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatHud.class})
public abstract class ChatHudMixin {
    @Shadow
    @Final
    private List<ChatHudLine<OrderedText>> visibleMessages;
    @Shadow
    @Final
    private List<ChatHudLine<Text>> messages;

    @Inject(at={@At(value="HEAD")}, method={"addMessage(Lnet/minecraft/text/Text;IIZ)V"}, cancellable=true)
    private void onAddMessage(Text Text2, int n, int n2, boolean bl, CallbackInfo callbackInfo) {
        if (Modules.get().get(BetterChat.class).onMsg(Text2.getString(), n, n2, this.messages, this.visibleMessages)) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"addMessage(Lnet/minecraft/text/Text;IIZ)V"}, at=@At(value="INVOKE", target="Ljava/util/List;size()I"))
    private int addMessageListSizeProxy(List<ChatHudLine> list) {
        BetterChat betterChat = Modules.get().get(BetterChat.class);
        return betterChat.isLongerChat() && betterChat.getChatLength() > 100 ? 1 : list.size();
    }
}

