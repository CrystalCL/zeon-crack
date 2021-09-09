/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.network.ClientConnection
 *  net.minecraft.network.listener.PacketListener
 *  net.minecraft.network.Packet
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        PacketEvent.Receive event = MeteorClient.EVENT_BUS.post(PacketEvent.Receive.get(packet));
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"connect"}, at={@At(value="HEAD")})
    private static void onConnect(InetAddress address, int port, boolean shouldUseNativeTransport, CallbackInfoReturnable<ClientConnection> info) {
        MeteorClient.EVENT_BUS.post(ConnectToServerEvent.get());
    }

    @Inject(method={"exceptionCaught"}, at={@At(value="HEAD")}, cancellable=true)
    private void exceptionCaught(ChannelHandlerContext context, Throwable throwable, CallbackInfo ci) {
        if (throwable instanceof IOException && Modules.get().isActive(AntiPacketKick.class)) {
            ci.cancel();
        }
    }

    @Inject(at={@At(value="HEAD")}, method={"send(Lnet/minecraft/network/Packet;)V"}, cancellable=true)
    private void onSendPacketHead(Packet<?> packet, CallbackInfo info) {
        PacketEvent.Send event = MeteorClient.EVENT_BUS.post(PacketEvent.Send.get(packet));
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"send(Lnet/minecraft/network/Packet;)V"}, at={@At(value="TAIL")})
    private void onSendPacketTail(Packet<?> packet, CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(PacketEvent.Sent.get(packet));
    }
}

