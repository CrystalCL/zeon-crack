/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CloseHandledScreenC2SPacket.class})
public interface CloseHandledScreenC2SPacketAccessor {
    @Accessor(value="syncId")
    public int getSyncId();
}

