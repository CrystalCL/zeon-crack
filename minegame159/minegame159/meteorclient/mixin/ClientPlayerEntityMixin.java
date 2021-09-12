/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.SendMessageEvent;
import minegame159.meteorclient.events.entity.player.SendMovementPacketsEvent;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.NoSlow;
import minegame159.meteorclient.systems.modules.movement.Scaffold;
import minegame159.meteorclient.systems.modules.player.Portals;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_634;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_746.class})
public abstract class ClientPlayerEntityMixin {
    @Shadow
    @Final
    public class_634 field_3944;
    private boolean ignoreChatMessage;

    @Shadow
    public abstract void method_3142(String var1);

    @Inject(at={@At(value="HEAD")}, method={"sendChatMessage"}, cancellable=true)
    private void onSendChatMessage(String string, CallbackInfo callbackInfo) {
        if (this.ignoreChatMessage) {
            return;
        }
        if (!(string.startsWith(Config.get().getPrefix()) || string.startsWith("/") || string.startsWith((String)BaritoneAPI.getSettings().prefix.value))) {
            SendMessageEvent sendMessageEvent = MeteorClient.EVENT_BUS.post(SendMessageEvent.get(string));
            if (!sendMessageEvent.isCancelled()) {
                this.ignoreChatMessage = true;
                this.method_3142(sendMessageEvent.msg);
                this.ignoreChatMessage = false;
            }
            callbackInfo.cancel();
            return;
        }
        if (string.startsWith(Config.get().getPrefix())) {
            try {
                Commands.get().dispatch(string.substring(Config.get().getPrefix().length()));
            }
            catch (CommandSyntaxException commandSyntaxException) {
                ChatUtils.error(commandSyntaxException.getMessage(), new Object[0]);
            }
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"updateNausea"}, at=@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;"))
    private class_437 updateNauseaGetCurrentScreenProxy(class_310 class_3102) {
        if (Modules.get().isActive(Portals.class)) {
            return null;
        }
        return class_3102.field_1755;
    }

    @Redirect(method={"tickMovement"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean proxy_tickMovement_isUsingItem(class_746 class_7462) {
        if (Modules.get().get(NoSlow.class).items()) {
            return false;
        }
        return class_7462.method_6115();
    }

    @Inject(method={"isSneaking"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Modules.get().isActive(Scaffold.class)) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }

    @Inject(method={"sendMovementPackets"}, at={@At(value="HEAD")})
    private void onSendMovementPacketsHead(CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=0)})
    private void onTickHasVehicleBeforeSendPackets(CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method={"sendMovementPackets"}, at={@At(value="TAIL")})
    private void onSendMovementPacketsTail(CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=1, shift=At.Shift.AFTER)})
    private void onTickHasVehicleAfterSendPackets(CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }
}

