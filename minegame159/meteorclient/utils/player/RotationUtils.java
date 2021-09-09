/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookOnly
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.MinecraftClient;

public class RotationUtils {
    public static /* synthetic */ MinecraftClient mc;

    public static void packetRotate(float llllllllllllllllIlIlllIlllIlIllI, float llllllllllllllllIlIlllIlllIlIlIl) {
        RotationUtils.mc.player.networkHandler.sendPacket((Packet)new LookOnly(llllllllllllllllIlIlllIlllIlIllI, llllllllllllllllIlIlllIlllIlIlIl, RotationUtils.mc.player.isOnGround()));
        Rotations.setCamRotation(llllllllllllllllIlIlllIlllIlIllI, llllllllllllllllIlIlllIlllIlIlIl);
    }

    public RotationUtils() {
        RotationUtils llllllllllllllllIlIlllIlllIlllII;
    }

    static {
        mc = MinecraftClient.getInstance();
    }
}

