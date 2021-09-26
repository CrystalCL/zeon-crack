/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import io.netty.channel.ChannelHandlerContext;
import java.io.IOException;
import java.net.InetAddress;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.ConnectToServerEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.AntiPacketKick;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientConnection.class})
public class ClientConnectionMixin {
    @Inject(method={"handlePacket"}, at={@At(value="HEAD")}, cancellable=true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> Packet2, PacketListener PacketListener2, CallbackInfo callbackInfo) {
        PacketEvent.Receive receive = MeteorClient.EVENT_BUS.post(PacketEvent.Receive.get(Packet2));
        if (receive.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"connect"}, at={@At(value="HEAD")})
    private static void onConnect(InetAddress inetAddress, int n, boolean bl, CallbackInfoReturnable<ClientConnection> callbackInfoReturnable) {
        MeteorClient.EVENT_BUS.post(ConnectToServerEvent.get());
    }

    @Inject(method={"exceptionCaught"}, at={@At(value="HEAD")}, cancellable=true)
    private void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo callbackInfo) {
        if (throwable instanceof IOException && Modules.get().isActive(AntiPacketKick.class)) {
            callbackInfo.cancel();
        }
    }

    @Inject(at={@At(value="HEAD")}, method={"send(Lnet/minecraft/network/Packet;)V"}, cancellable=true)
    private void onSendPacketHead(Packet<?> Packet2, CallbackInfo callbackInfo) {
        PacketEvent.Send send = MeteorClient.EVENT_BUS.post(PacketEvent.Send.get(Packet2));
        if (send.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"send(Lnet/minecraft/network/Packet;)V"}, at={@At(value="TAIL")})
    private void onSendPacketTail(Packet<?> Packet2, CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(PacketEvent.Sent.get(Packet2));
    }
}

