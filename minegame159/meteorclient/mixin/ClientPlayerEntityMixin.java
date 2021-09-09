/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.network.ClientPlayNetworkHandler
 *  net.minecraft.client.network.ClientPlayerEntity
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin {
    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;
    private boolean ignoreChatMessage;

    @Shadow
    public abstract void sendChatMessage(String var1);

    @Inject(at={@At(value="HEAD")}, method={"sendChatMessage"}, cancellable=true)
    private void onSendChatMessage(String msg, CallbackInfo info) {
        if (this.ignoreChatMessage) {
            return;
        }
        if (!(msg.startsWith(Config.get().getPrefix()) || msg.startsWith("/") || msg.startsWith((String)BaritoneAPI.getSettings().prefix.value))) {
            SendMessageEvent event = MeteorClient.EVENT_BUS.post(SendMessageEvent.get(msg));
            if (!event.isCancelled()) {
                this.ignoreChatMessage = true;
                this.sendChatMessage(event.msg);
                this.ignoreChatMessage = false;
            }
            info.cancel();
            return;
        }
        if (msg.startsWith(Config.get().getPrefix())) {
            try {
                Commands.get().dispatch(msg.substring(Config.get().getPrefix().length()));
            }
            catch (CommandSyntaxException e) {
                ChatUtils.error(e.getMessage(), new Object[0]);
            }
            info.cancel();
        }
    }

    @Redirect(method={"updateNausea"}, at=@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;"))
    private Screen updateNauseaGetCurrentScreenProxy(MinecraftClient client) {
        if (Modules.get().isActive(Portals.class)) {
            return null;
        }
        return client.currentScreen;
    }

    @Redirect(method={"tickMovement"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean proxy_tickMovement_isUsingItem(ClientPlayerEntity player) {
        if (Modules.get().get(NoSlow.class).items()) {
            return false;
        }
        return player.isUsingItem();
    }

    @Inject(method={"isSneaking"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> info) {
        if (Modules.get().isActive(Scaffold.class)) {
            info.setReturnValue((Object)false);
        }
    }

    @Inject(method={"sendMovementPackets"}, at={@At(value="HEAD")})
    private void onSendMovementPacketsHead(CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=0)})
    private void onTickHasVehicleBeforeSendPackets(CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method={"sendMovementPackets"}, at={@At(value="TAIL")})
    private void onSendMovementPacketsTail(CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=1, shift=At.Shift.AFTER)})
    private void onTickHasVehicleAfterSendPackets(CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }
}

