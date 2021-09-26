/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ExplosionS2CPacket.class})
public class ExplosionS2CPacketMixin
implements IExplosionS2CPacket {
    @Shadow
    private float playerVelocityX;
    @Shadow
    private float playerVelocityY;
    @Shadow
    private float playerVelocityZ;

    @Override
    public void setVelocityX(float f) {
        this.playerVelocityX = f;
    }

    @Override
    public void setVelocityY(float f) {
        this.playerVelocityY = f;
    }

    @Override
    public void setVelocityZ(float f) {
        this.playerVelocityZ = f;
    }
}

