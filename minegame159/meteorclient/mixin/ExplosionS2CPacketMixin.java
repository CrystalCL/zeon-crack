/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.ExplosionS2CPacket
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
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
    public void setVelocityX(float velocity) {
        this.playerVelocityX = velocity;
    }

    @Override
    public void setVelocityY(float velocity) {
        this.playerVelocityY = velocity;
    }

    @Override
    public void setVelocityZ(float velocity) {
        this.playerVelocityZ = velocity;
    }
}

