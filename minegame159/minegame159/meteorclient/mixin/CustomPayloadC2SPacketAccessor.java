/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CustomPayloadC2SPacket.class})
public interface CustomPayloadC2SPacketAccessor {
    @Accessor(value="channel")
    public Identifier getChannel();

    @Accessor(value="data")
    public PacketByteBuf getData();

    @Accessor(value="data")
    public void setData(PacketByteBuf var1);
}

