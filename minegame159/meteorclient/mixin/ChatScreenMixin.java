/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.widget.TextFieldWidget
 *  net.minecraft.client.gui.screen.ChatScreen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BetterChat;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatScreen.class})
public class ChatScreenMixin {
    @Shadow
    protected TextFieldWidget chatField;

    @Inject(method={"init"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/widget/TextFieldWidget;setMaxLength(I)V", shift=At.Shift.AFTER)})
    private void onInit(CallbackInfo info) {
        if (Modules.get().get(BetterChat.class).isInfiniteChatBox()) {
            this.chatField.setMaxLength(Integer.MAX_VALUE);
        }
    }
}

