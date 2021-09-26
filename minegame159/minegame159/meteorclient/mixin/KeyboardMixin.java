/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.meteor.CharTypedEvent;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.Input;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Keyboard.class})
public abstract class KeyboardMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method={"onKey"}, at={@At(value="HEAD")}, cancellable=true)
    public void onKey(long l, int n, int n2, int n3, int n4, CallbackInfo callbackInfo) {
        if (n != -1) {
            if (this.client.currentScreen instanceof WidgetScreen && n3 == 2) {
                ((WidgetScreen)this.client.currentScreen).keyRepeated(n, n4);
            }
            if (GuiKeyEvents.canUseKeys()) {
                Input.setKeyState(n, n3 != 0);
                KeyEvent keyEvent = MeteorClient.EVENT_BUS.post(KeyEvent.get(n, KeyAction.get(n3)));
                if (keyEvent.isCancelled()) {
                    callbackInfo.cancel();
                }
            }
        }
    }

    @Inject(method={"onChar"}, at={@At(value="HEAD")}, cancellable=true)
    private void onChar(long l, int n, int n2, CallbackInfo callbackInfo) {
        CharTypedEvent charTypedEvent;
        if (Utils.canUpdate() && !this.client.isPaused() && (this.client.currentScreen == null || this.client.currentScreen instanceof WidgetScreen) && (charTypedEvent = MeteorClient.EVENT_BUS.post(CharTypedEvent.get((char)n))).isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

