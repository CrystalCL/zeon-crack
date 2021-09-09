/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BedBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
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
    private final /* synthetic */ Setting<Double> fallDistance;
    private final /* synthetic */ Setting<Double> fallSpeed;
    private final /* synthetic */ SettingGroup sgGeneral;

    private boolean isOnBed() {
        ReverseStep llIllIIIllIIIll;
        Mutable llIllIIIllIIllI = llIllIIIllIIIll.mc.player.getBlockPos().mutableCopy();
        if (llIllIIIllIIIll.check(llIllIIIllIIllI, 0, 0)) {
            return true;
        }
        double llIllIIIllIIlIl = llIllIIIllIIIll.mc.player.getX() - (double)llIllIIIllIIllI.getX();
        double llIllIIIllIIlII = llIllIIIllIIIll.mc.player.getZ() - (double)llIllIIIllIIllI.getZ();
        if (llIllIIIllIIlIl >= 0.0 && llIllIIIllIIlIl <= 0.3 && llIllIIIllIIIll.check(llIllIIIllIIllI, -1, 0)) {
            return true;
        }
        if (llIllIIIllIIlIl >= 0.7 && llIllIIIllIIIll.check(llIllIIIllIIllI, 1, 0)) {
            return true;
        }
        if (llIllIIIllIIlII >= 0.0 && llIllIIIllIIlII <= 0.3 && llIllIIIllIIIll.check(llIllIIIllIIllI, 0, -1)) {
            return true;
        }
        if (llIllIIIllIIlII >= 0.7 && llIllIIIllIIIll.check(llIllIIIllIIllI, 0, 1)) {
            return true;
        }
        if (llIllIIIllIIlIl >= 0.0 && llIllIIIllIIlIl <= 0.3 && llIllIIIllIIlII >= 0.0 && llIllIIIllIIlII <= 0.3 && llIllIIIllIIIll.check(llIllIIIllIIllI, -1, -1)) {
            return true;
        }
        if (llIllIIIllIIlIl >= 0.0 && llIllIIIllIIlIl <= 0.3 && llIllIIIllIIlII >= 0.7 && llIllIIIllIIIll.check(llIllIIIllIIllI, -1, 1)) {
            return true;
        }
        if (llIllIIIllIIlIl >= 0.7 && llIllIIIllIIlII >= 0.0 && llIllIIIllIIlII <= 0.3 && llIllIIIllIIIll.check(llIllIIIllIIllI, 1, -1)) {
            return true;
        }
        return llIllIIIllIIlIl >= 0.7 && llIllIIIllIIlII >= 0.7 && llIllIIIllIIIll.check(llIllIIIllIIllI, 1, 1);
    }

    private boolean check(Mutable llIllIIIlIlIlII, int llIllIIIlIllIII, int llIllIIIlIlIlll) {
        ReverseStep llIllIIIlIllIlI;
        llIllIIIlIlIlII.move(llIllIIIlIllIII, 0, llIllIIIlIlIlll);
        boolean llIllIIIlIlIllI = llIllIIIlIllIlI.mc.world.getBlockState((BlockPos)llIllIIIlIlIlII).getBlock() instanceof BedBlock;
        llIllIIIlIlIlII.move(-llIllIIIlIllIII, 0, -llIllIIIlIlIlll);
        return llIllIIIlIlIllI;
    }

    @EventHandler
    private void onTick(TickEvent.Post llIllIIIllIllIl) {
        ReverseStep llIllIIIllIllII;
        if (!llIllIIIllIllII.mc.player.isOnGround() || llIllIIIllIllII.mc.player.isHoldingOntoLadder() || llIllIIIllIllII.mc.player.isSubmergedInWater() || llIllIIIllIllII.mc.player.isInLava() || llIllIIIllIllII.mc.options.keyJump.isPressed() || llIllIIIllIllII.mc.player.noClip || llIllIIIllIllII.mc.player.forwardSpeed == 0.0f && llIllIIIllIllII.mc.player.sidewaysSpeed == 0.0f) {
            return;
        }
        if (!llIllIIIllIllII.isOnBed() && !llIllIIIllIllII.mc.world.isSpaceEmpty(llIllIIIllIllII.mc.player.getBoundingBox().offset(0.0, (double)((float)(-(llIllIIIllIllII.fallDistance.get() + 0.01))), 0.0))) {
            ((IVec3d)llIllIIIllIllII.mc.player.getVelocity()).setY(-llIllIIIllIllII.fallSpeed.get().doubleValue());
        }
    }

    public ReverseStep() {
        super(Categories.Movement, "reverse-step", "Allows you to fall down blocks at a greater speed.");
        ReverseStep llIllIIIlllIIII;
        llIllIIIlllIIII.sgGeneral = llIllIIIlllIIII.settings.getDefaultGroup();
        llIllIIIlllIIII.fallSpeed = llIllIIIlllIIII.sgGeneral.add(new DoubleSetting.Builder().name("fall-speed").description("How fast to fall in blocks per second.").defaultValue(3.0).min(0.0).sliderMax(10.0).build());
        llIllIIIlllIIII.fallDistance = llIllIIIlllIIII.sgGeneral.add(new DoubleSetting.Builder().name("fall-distance").description("The maximum fall distance this setting will activate at.").defaultValue(3.0).min(0.0).sliderMax(10.0).build());
    }
}

