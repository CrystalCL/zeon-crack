/*
 * Decompiled with CFR 0.151.
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
    private void onDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo callbackInfo) throws DataFormatException {
        block3: {
            int n;
            PacketByteBuf PacketByteBuf2;
            block4: {
                block2: {
                    if (!Modules.get().isActive(AntiPacketKick.class)) break block2;
                    callbackInfo.cancel();
                    if (byteBuf.readableBytes() == 0) break block3;
                    PacketByteBuf2 = new PacketByteBuf(byteBuf);
                    n = PacketByteBuf2.readVarInt();
                    if (n != 0) break block4;
                    list.add(PacketByteBuf2.readBytes(PacketByteBuf2.readableBytes()));
                    break block3;
                }
                return;
            }
            if (n < this.compressionThreshold) {
                throw new DecoderException("Badly compressed packet - size of " + n + " is below server threshold of " + this.compressionThreshold);
            }
            if (n > 0x200000) {
                throw new DecoderException("Badly compressed packet - size of " + n + " is larger than protocol maximum of " + 0x200000);
            }
            byte[] byArray = new byte[PacketByteBuf2.readableBytes()];
            PacketByteBuf2.readBytes(byArray);
            this.inflater.setInput(byArray);
            byte[] byArray2 = new byte[n];
            this.inflater.inflate(byArray2);
            list.add(Unpooled.wrappedBuffer((byte[])byArray2));
            this.inflater.reset();
        }
    }
}

