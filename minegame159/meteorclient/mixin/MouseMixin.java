/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Mouse
 *  net.minecraft.client.network.ClientPlayerEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.events.meteor.MouseScrollEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.FreeRotate;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.misc.input.Input;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={Mouse.class})
public class MouseMixin {
    @Inject(method={"onMouseButton"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
        Input.setButtonState(button, action != 0);
        if (MeteorClient.EVENT_BUS.post(MouseButtonEvent.get(button, KeyAction.get(action))).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"onMouseScroll"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
        if (MeteorClient.EVENT_BUS.post(MouseScrollEvent.get(vertical)).isCancelled()) {
            info.cancel();
        }
    }

    @Redirect(method={"updateMouse"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void updateMouseChangeLookDirection(ClientPlayerEntity player, double cursorDeltaX, double cursorDeltaY) {
        Freecam freecam = Modules.get().get(Freecam.class);
        FreeRotate freeRotate = Modules.get().get(FreeRotate.class);
        if (freecam.isActive()) {
            freecam.changeLookDirection(cursorDeltaX * 0.15, cursorDeltaY * 0.15);
        } else if (!freeRotate.cameraMode()) {
            player.changeLookDirection(cursorDeltaX, cursorDeltaY);
        }
    }

    @Inject(method={"updateMouse"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/tutorial/TutorialManager;onUpdateMouse(DD)V")}, locals=LocalCapture.CAPTURE_FAILEXCEPTION)
    private void perspectiveUpdatePitchYaw(CallbackInfo info, double adjustedSens, double x, double y, int invert) {
        FreeRotate freeRotate = Modules.get().get(FreeRotate.class);
        if (freeRotate.cameraMode()) {
            freeRotate.cameraYaw = (float)((double)freeRotate.cameraYaw + x / (double)freeRotate.sensitivity.get().floatValue());
            freeRotate.cameraPitch = (float)((double)freeRotate.cameraPitch + y * (double)invert / (double)freeRotate.sensitivity.get().floatValue());
            if (Math.abs(freeRotate.cameraPitch) > 90.0f) {
                freeRotate.cameraPitch = freeRotate.cameraPitch > 0.0f ? 90.0f : -90.0f;
            }
        }
    }
}

