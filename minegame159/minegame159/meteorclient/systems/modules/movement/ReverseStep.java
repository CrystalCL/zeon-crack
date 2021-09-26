/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;

public class ReverseStep
extends Module {
    private final Setting<Double> fallDistance;
    private final SettingGroup sgGeneral;
    private final Setting<Double> fallSpeed;

    private boolean isOnBed() {
        Mutable class_23392 = this.mc.player.getBlockPos().mutableCopy();
        if (this.check(class_23392, 0, 0)) {
            return true;
        }
        double d = this.mc.player.getX() - (double)class_23392.getX();
        double d2 = this.mc.player.getZ() - (double)class_23392.getZ();
        if (d >= 0.0 && d <= 0.3 && this.check(class_23392, -1, 0)) {
            return true;
        }
        if (d >= 0.7 && this.check(class_23392, 1, 0)) {
            return true;
        }
        if (d2 >= 0.0 && d2 <= 0.3 && this.check(class_23392, 0, -1)) {
            return true;
        }
        if (d2 >= 0.7 && this.check(class_23392, 0, 1)) {
            return true;
        }
        if (d >= 0.0 && d <= 0.3 && d2 >= 0.0 && d2 <= 0.3 && this.check(class_23392, -1, -1)) {
            return true;
        }
        if (d >= 0.0 && d <= 0.3 && d2 >= 0.7 && this.check(class_23392, -1, 1)) {
            return true;
        }
        if (d >= 0.7 && d2 >= 0.0 && d2 <= 0.3 && this.check(class_23392, 1, -1)) {
            return true;
        }
        return d >= 0.7 && d2 >= 0.7 && this.check(class_23392, 1, 1);
    }

    public ReverseStep() {
        super(Categories.Movement, "reverse-step", "Allows you to fall down blocks at a greater speed.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.fallSpeed = this.sgGeneral.add(new DoubleSetting.Builder().name("fall-speed").description("How fast to fall in blocks per second.").defaultValue(3.0).min(0.0).sliderMax(10.0).build());
        this.fallDistance = this.sgGeneral.add(new DoubleSetting.Builder().name("fall-distance").description("The maximum fall distance this setting will activate at.").defaultValue(3.0).min(0.0).sliderMax(10.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.mc.player.isOnGround() || this.mc.player.isHoldingOntoLadder() || this.mc.player.isSubmergedInWater() || this.mc.player.isInLava() || this.mc.options.keyJump.isPressed() || this.mc.player.noClip || this.mc.player.forwardSpeed == 0.0f && this.mc.player.sidewaysSpeed == 0.0f) {
            return;
        }
        if (!this.isOnBed() && !this.mc.world.isSpaceEmpty(this.mc.player.getBoundingBox().offset(0.0, (double)((float)(-(this.fallDistance.get() + 0.01))), 0.0))) {
            ((IVec3d)this.mc.player.getVelocity()).setY(-this.fallSpeed.get().doubleValue());
        }
    }

    private boolean check(Mutable class_23392, int n, int n2) {
        class_23392.move(n, 0, n2);
        boolean bl = this.mc.world.getBlockState((BlockPos)class_23392).getBlock() instanceof BedBlock;
        class_23392.move(-n, 0, -n2);
        return bl;
    }
}

