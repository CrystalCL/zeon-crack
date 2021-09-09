/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.MathHelper
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.AbstractBlockAccessor;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Anchor
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    public /* synthetic */ double deltaX;
    private /* synthetic */ int holeX;
    private final /* synthetic */ Setting<Integer> maxHeight;
    public /* synthetic */ boolean cancelJump;
    private /* synthetic */ int holeZ;
    public /* synthetic */ double deltaZ;
    private final /* synthetic */ Setting<Boolean> cancelMove;
    private /* synthetic */ boolean foundHole;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ boolean wasInHole;
    private final /* synthetic */ Setting<Boolean> pull;
    private final /* synthetic */ Setting<Double> pullSpeed;
    public /* synthetic */ boolean controlMovement;
    private final /* synthetic */ Setting<Integer> minPitch;

    @Override
    public void onActivate() {
        lllllllllllllllllIlIlIllIllIlIlI.wasInHole = false;
        lllllllllllllllllIlIlIllIllIlIlI.holeZ = 0;
        lllllllllllllllllIlIlIllIllIlIlI.holeX = 0;
    }

    private boolean isAir(int lllllllllllllllllIlIlIllIIlIllll, int lllllllllllllllllIlIlIllIIlIlIlI, int lllllllllllllllllIlIlIllIIlIllIl) {
        Anchor lllllllllllllllllIlIlIllIIlIllII;
        lllllllllllllllllIlIlIllIIlIllII.blockPos.set(lllllllllllllllllIlIlIllIIlIllll, lllllllllllllllllIlIlIllIIlIlIlI, lllllllllllllllllIlIlIllIIlIllIl);
        return !((AbstractBlockAccessor)lllllllllllllllllIlIlIllIIlIllII.mc.world.getBlockState((BlockPos)lllllllllllllllllIlIlIllIIlIllII.blockPos).getBlock()).isCollidable();
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre lllllllllllllllllIlIlIllIllIIlll) {
        Anchor lllllllllllllllllIlIlIllIllIlIII;
        lllllllllllllllllIlIlIllIllIlIII.cancelJump = lllllllllllllllllIlIlIllIllIlIII.foundHole && lllllllllllllllllIlIlIllIllIlIII.cancelMove.get() != false && lllllllllllllllllIlIlIllIllIlIII.mc.player.pitch >= (float)lllllllllllllllllIlIlIllIllIlIII.minPitch.get().intValue();
    }

    private boolean isHole(int lllllllllllllllllIlIlIllIlIIlIlI, int lllllllllllllllllIlIlIllIlIIIlIl, int lllllllllllllllllIlIlIllIlIIlIII) {
        Anchor lllllllllllllllllIlIlIllIlIIlIll;
        return lllllllllllllllllIlIlIllIlIIlIll.isHoleBlock(lllllllllllllllllIlIlIllIlIIlIlI, lllllllllllllllllIlIlIllIlIIIlIl - 1, lllllllllllllllllIlIlIllIlIIlIII) && lllllllllllllllllIlIlIllIlIIlIll.isHoleBlock(lllllllllllllllllIlIlIllIlIIlIlI + 1, lllllllllllllllllIlIlIllIlIIIlIl, lllllllllllllllllIlIlIllIlIIlIII) && lllllllllllllllllIlIlIllIlIIlIll.isHoleBlock(lllllllllllllllllIlIlIllIlIIlIlI - 1, lllllllllllllllllIlIlIllIlIIIlIl, lllllllllllllllllIlIlIllIlIIlIII) && lllllllllllllllllIlIlIllIlIIlIll.isHoleBlock(lllllllllllllllllIlIlIllIlIIlIlI, lllllllllllllllllIlIlIllIlIIIlIl, lllllllllllllllllIlIlIllIlIIlIII + 1) && lllllllllllllllllIlIlIllIlIIlIll.isHoleBlock(lllllllllllllllllIlIlIllIlIIlIlI, lllllllllllllllllIlIlIllIlIIIlIl, lllllllllllllllllIlIlIllIlIIlIII - 1);
    }

    private boolean isHoleBlock(int lllllllllllllllllIlIlIllIIllllIl, int lllllllllllllllllIlIlIllIIllIlll, int lllllllllllllllllIlIlIllIIlllIll) {
        Anchor lllllllllllllllllIlIlIllIIlllllI;
        lllllllllllllllllIlIlIllIIlllllI.blockPos.set(lllllllllllllllllIlIlIllIIllllIl, lllllllllllllllllIlIlIllIIllIlll, lllllllllllllllllIlIlIllIIlllIll);
        Block lllllllllllllllllIlIlIllIIlllIlI = lllllllllllllllllIlIlIllIIlllllI.mc.world.getBlockState((BlockPos)lllllllllllllllllIlIlIllIIlllllI.blockPos).getBlock();
        return lllllllllllllllllIlIlIllIIlllIlI == Blocks.BEDROCK || lllllllllllllllllIlIlIllIIlllIlI == Blocks.OBSIDIAN || lllllllllllllllllIlIlIllIIlllIlI == Blocks.CRYING_OBSIDIAN;
    }

    @EventHandler
    private void onPostTick(TickEvent.Post lllllllllllllllllIlIlIllIlIlllII) {
        int lllllllllllllllllIlIlIllIlIllIIl;
        int lllllllllllllllllIlIlIllIlIllIlI;
        Anchor lllllllllllllllllIlIlIllIlIlllIl;
        lllllllllllllllllIlIlIllIlIlllIl.controlMovement = false;
        int lllllllllllllllllIlIlIllIlIllIll = MathHelper.floor((double)lllllllllllllllllIlIlIllIlIlllIl.mc.player.getX());
        if (lllllllllllllllllIlIlIllIlIlllIl.isHole(lllllllllllllllllIlIlIllIlIllIll, lllllllllllllllllIlIlIllIlIllIlI = MathHelper.floor((double)lllllllllllllllllIlIlIllIlIlllIl.mc.player.getY()), lllllllllllllllllIlIlIllIlIllIIl = MathHelper.floor((double)lllllllllllllllllIlIlIllIlIlllIl.mc.player.getZ()))) {
            lllllllllllllllllIlIlIllIlIlllIl.wasInHole = true;
            lllllllllllllllllIlIlIllIlIlllIl.holeX = lllllllllllllllllIlIlIllIlIllIll;
            lllllllllllllllllIlIlIllIlIlllIl.holeZ = lllllllllllllllllIlIlIllIlIllIIl;
            return;
        }
        if (lllllllllllllllllIlIlIllIlIlllIl.wasInHole && lllllllllllllllllIlIlIllIlIlllIl.holeX == lllllllllllllllllIlIlIllIlIllIll && lllllllllllllllllIlIlIllIlIlllIl.holeZ == lllllllllllllllllIlIlIllIlIllIIl) {
            return;
        }
        if (lllllllllllllllllIlIlIllIlIlllIl.wasInHole) {
            lllllllllllllllllIlIlIllIlIlllIl.wasInHole = false;
        }
        if (lllllllllllllllllIlIlIllIlIlllIl.mc.player.pitch < (float)lllllllllllllllllIlIlIllIlIlllIl.minPitch.get().intValue()) {
            return;
        }
        lllllllllllllllllIlIlIllIlIlllIl.foundHole = false;
        double lllllllllllllllllIlIlIllIlIllIII = 0.0;
        double lllllllllllllllllIlIlIllIlIlIlll = 0.0;
        for (int lllllllllllllllllIlIlIllIlIllllI = 0; lllllllllllllllllIlIlIllIlIllllI < lllllllllllllllllIlIlIllIlIlllIl.maxHeight.get() && --lllllllllllllllllIlIlIllIlIllIlI > 0 && lllllllllllllllllIlIlIllIlIlllIl.isAir(lllllllllllllllllIlIlIllIlIllIll, lllllllllllllllllIlIlIllIlIllIlI, lllllllllllllllllIlIlIllIlIllIIl); ++lllllllllllllllllIlIlIllIlIllllI) {
            if (!lllllllllllllllllIlIlIllIlIlllIl.isHole(lllllllllllllllllIlIlIllIlIllIll, lllllllllllllllllIlIlIllIlIllIlI, lllllllllllllllllIlIlIllIlIllIIl)) continue;
            lllllllllllllllllIlIlIllIlIlllIl.foundHole = true;
            lllllllllllllllllIlIlIllIlIllIII = (double)lllllllllllllllllIlIlIllIlIllIll + 0.5;
            lllllllllllllllllIlIlIllIlIlIlll = (double)lllllllllllllllllIlIlIllIlIllIIl + 0.5;
            break;
        }
        if (lllllllllllllllllIlIlIllIlIlllIl.foundHole) {
            lllllllllllllllllIlIlIllIlIlllIl.controlMovement = true;
            lllllllllllllllllIlIlIllIlIlllIl.deltaX = Utils.clamp(lllllllllllllllllIlIlIllIlIllIII - lllllllllllllllllIlIlIllIlIlllIl.mc.player.getX(), -0.05, 0.05);
            lllllllllllllllllIlIlIllIlIlllIl.deltaZ = Utils.clamp(lllllllllllllllllIlIlIllIlIlIlll - lllllllllllllllllIlIlIllIlIlllIl.mc.player.getZ(), -0.05, 0.05);
            ((IVec3d)lllllllllllllllllIlIlIllIlIlllIl.mc.player.getVelocity()).set(lllllllllllllllllIlIlIllIlIlllIl.deltaX, lllllllllllllllllIlIlIllIlIlllIl.mc.player.getVelocity().y - (lllllllllllllllllIlIlIllIlIlllIl.pull.get() != false ? lllllllllllllllllIlIlIllIlIlllIl.pullSpeed.get() : 0.0), lllllllllllllllllIlIlIllIlIlllIl.deltaZ);
        }
    }

    public Anchor() {
        super(Categories.Movement, "anchor", "Helps you get into holes by stopping your movement completely over a hole.");
        Anchor lllllllllllllllllIlIlIllIllIlllI;
        lllllllllllllllllIlIlIllIllIlllI.sgGeneral = lllllllllllllllllIlIlIllIllIlllI.settings.getDefaultGroup();
        lllllllllllllllllIlIlIllIllIlllI.maxHeight = lllllllllllllllllIlIlIllIllIlllI.sgGeneral.add(new IntSetting.Builder().name("max-height").description("The maximum height Anchor will work at.").defaultValue(10).min(0).max(255).sliderMax(20).build());
        lllllllllllllllllIlIlIllIllIlllI.minPitch = lllllllllllllllllIlIlIllIllIlllI.sgGeneral.add(new IntSetting.Builder().name("min-pitch").description("The minimum pitch at which anchor will work.").defaultValue(-90).min(-90).max(90).sliderMin(-90).sliderMax(90).build());
        lllllllllllllllllIlIlIllIllIlllI.cancelMove = lllllllllllllllllIlIlIllIllIlllI.sgGeneral.add(new BoolSetting.Builder().name("cancel-jump-in-hole").description("Prevents you from jumping when Anchor is active and Min Pitch is met.").defaultValue(false).build());
        lllllllllllllllllIlIlIllIllIlllI.pull = lllllllllllllllllIlIlIllIllIlllI.sgGeneral.add(new BoolSetting.Builder().name("pull").description("The pull strength of Anchor.").defaultValue(false).build());
        lllllllllllllllllIlIlIllIllIlllI.pullSpeed = lllllllllllllllllIlIlIllIllIlllI.sgGeneral.add(new DoubleSetting.Builder().name("pull-speed").description("How fast to pull towards the hole in blocks per second.").defaultValue(0.3).min(0.0).sliderMax(5.0).build());
        lllllllllllllllllIlIlIllIllIlllI.blockPos = new Mutable();
    }
}

