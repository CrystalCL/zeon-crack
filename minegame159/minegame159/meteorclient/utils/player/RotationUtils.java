/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.MinecraftClient;

public class RotationUtils {
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static void packetRotate(float f, float f2) {
        RotationUtils.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(f, f2, RotationUtils.mc.player.isOnGround()));
        Rotations.setCamRotation(f, f2);
    }
}

