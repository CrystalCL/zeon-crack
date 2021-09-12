/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_310;

public class RotationUtils {
    public static class_310 mc = class_310.method_1551();

    public static void packetRotate(float f, float f2) {
        RotationUtils.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(f, f2, RotationUtils.mc.field_1724.method_24828()));
        Rotations.setCamRotation(f, f2);
    }
}

