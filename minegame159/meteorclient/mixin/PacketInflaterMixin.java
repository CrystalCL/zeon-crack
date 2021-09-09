/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.DecoderException
 *  net.minecraft.network.PacketInflater
 *  net.minecraft.network.PacketByteBuf
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.AntiPacketKick;
import net.minecraft.network.PacketInflater;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PacketInflater.class})
public class PacketInflaterMixin {
    @Shadow
    private int compressionThreshold;
    @Shadow
    @Final
    private Inflater inflater;

    @Inject(method={"decode"}, at={@At(value="HEAD")}, cancellable=true)
    private void onDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo info) throws DataFormatException {
        if (!Modules.get().isActive(AntiPacketKick.class)) {
            return;
        }
        info.cancel();
        if (byteBuf.readableBytes() != 0) {
            PacketByteBuf packetByteBuf = new PacketByteBuf(byteBuf);
            int i = packetByteBuf.readVarInt();
            if (i == 0) {
                list.add((Object)packetByteBuf.readBytes(packetByteBuf.readableBytes()));
            } else {
                if (i < this.compressionThreshold) {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.compressionThreshold);
                }
                if (i > 0x200000) {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 0x200000);
                }
                byte[] bs = new byte[packetByteBuf.readableBytes()];
                packetByteBuf.readBytes(bs);
                this.inflater.setInput(bs);
                byte[] cs = new byte[i];
                this.inflater.inflate(cs);
                list.add((Object)Unpooled.wrappedBuffer((byte[])cs));
                this.inflater.reset();
            }
        }
    }
}

