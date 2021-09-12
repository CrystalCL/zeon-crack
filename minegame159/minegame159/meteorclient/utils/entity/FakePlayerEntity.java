/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2945;
import net.minecraft.class_310;
import net.minecraft.class_638;
import net.minecraft.class_745;
import net.minecraft.class_746;

public class FakePlayerEntity
extends class_745 {
    private final class_638 world;
    private final class_746 player;
    private static final class_310 mc = class_310.method_1551();

    public void despawn() {
        this.field_5988 = true;
    }

    private void spawn() {
        this.world.method_2942(this.method_5628(), (class_1297)this);
    }

    private void copyPlayerModel(class_1297 class_12972, class_1297 class_12973) {
        class_2945 class_29452 = class_12972.method_5841();
        class_2945 class_29453 = class_12973.method_5841();
        Byte by = (Byte)class_29452.method_12789(class_1657.field_7518);
        class_29453.method_12778(class_1657.field_7518, (Object)by);
    }

    private void copyRotation() {
        this.field_6241 = this.player.field_6241;
        this.field_6283 = this.player.field_6283;
    }

    private void copyAttributes() {
        this.method_6127().method_26846(this.player.method_6127());
    }

    public FakePlayerEntity(String string, boolean bl, boolean bl2, float f) {
        super(FakePlayerEntity.mc.field_1687, new GameProfile(FakePlayerEntity.mc.field_1724.method_5667(), string));
        this.player = FakePlayerEntity.mc.field_1724;
        this.world = FakePlayerEntity.mc.field_1687;
        this.method_5719((class_1297)this.player);
        this.copyPlayerModel((class_1297)this.player, (class_1297)this);
        this.copyRotation();
        this.copyAttributes();
        this.resetCapeMovement();
        this.method_6033(f);
        if (bl) {
            this.field_7514.method_7377(FakePlayerEntity.mc.field_1724.field_7514);
        }
        if (bl2) {
            this.method_5834(true);
        }
        this.spawn();
    }

    private void resetCapeMovement() {
        this.field_7500 = this.method_23317();
        this.field_7521 = this.method_23318();
        this.field_7499 = this.method_23321();
    }
}

