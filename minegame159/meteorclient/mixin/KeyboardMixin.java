/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Keyboard
 *  net.minecraft.client.MinecraftClient
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo info) {
        if (key != -1) {
            if (this.client.currentScreen instanceof WidgetScreen && i == 2) {
                ((WidgetScreen)this.client.currentScreen).keyRepeated(key, j);
            }
            if (GuiKeyEvents.canUseKeys()) {
                Input.setKeyState(key, i != 0);
                KeyEvent event = MeteorClient.EVENT_BUS.post(KeyEvent.get(key, KeyAction.get(i)));
                if (event.isCancelled()) {
                    info.cancel();
                }
            }
        }
    }

    @Inject(method={"onChar"}, at={@At(value="HEAD")}, cancellable=true)
    private void onChar(long window, int i, int j, CallbackInfo info) {
        CharTypedEvent event;
        if (Utils.canUpdate() && !this.client.isPaused() && (this.client.currentScreen == null || this.client.currentScreen instanceof WidgetScreen) && (event = MeteorClient.EVENT_BUS.post(CharTypedEvent.get((char)i))).isCancelled()) {
            info.cancel();
        }
    }
}

