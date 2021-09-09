/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
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

