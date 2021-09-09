/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.block.BlockState
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.block.BlockState;

public class Nuker
extends Module {
    private final /* synthetic */ Setting<List<Block>> selectedBlocks;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ int timer;
    private /* synthetic */ boolean hasLastBlockPos;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<Boolean> onlySelected;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Mutable lastBlockPos;
    private final /* synthetic */ Setting<Boolean> noParticles;
    private final /* synthetic */ List<Mutable> blocks;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ Pool<Mutable> blockPool;
    private final /* synthetic */ Setting<SortMode> sortMode;

    @EventHandler
    private void onTick(TickEvent.Pre lllllIIlIIlIIlI) {
        Nuker lllllIIlIIlIIll;
        if (lllllIIlIIlIIll.hasLastBlockPos && lllllIIlIIlIIll.mc.world.getBlockState((BlockPos)lllllIIlIIlIIll.lastBlockPos).getBlock() != Blocks.AIR) {
            lllllIIlIIlIIll.mc.interactionManager.updateBlockBreakingProgress((BlockPos)lllllIIlIIlIIll.lastBlockPos, Direction.UP);
            return;
        }
        lllllIIlIIlIIll.hasLastBlockPos = false;
        if (lllllIIlIIlIIll.timer < lllllIIlIIlIIll.delay.get()) {
            ++lllllIIlIIlIIll.timer;
            return;
        }
        lllllIIlIIlIIll.timer = 0;
        double lllllIIlIIlIIIl = lllllIIlIIlIIll.mc.player.getX() - 0.5;
        double lllllIIlIIlIIII = lllllIIlIIlIIll.mc.player.getY();
        double lllllIIlIIIllll = lllllIIlIIlIIll.mc.player.getZ() - 0.5;
        int lllllIIlIIIlllI = (int)Math.floor(lllllIIlIIlIIIl - lllllIIlIIlIIll.range.get());
        int lllllIIlIIIllIl = (int)Math.floor(lllllIIlIIlIIII - lllllIIlIIlIIll.range.get());
        int lllllIIlIIIllII = (int)Math.floor(lllllIIlIIIllll - lllllIIlIIlIIll.range.get());
        int lllllIIlIIIlIll = (int)Math.floor(lllllIIlIIlIIIl + lllllIIlIIlIIll.range.get());
        int lllllIIlIIIlIlI = (int)Math.floor(lllllIIlIIlIIII + lllllIIlIIlIIll.range.get());
        int lllllIIlIIIlIIl = (int)Math.floor(lllllIIlIIIllll + lllllIIlIIlIIll.range.get());
        double lllllIIlIIIlIII = Math.pow(lllllIIlIIlIIll.range.get(), 2.0);
        block2: for (int lllllIIlIIlIllI = lllllIIlIIIllIl; lllllIIlIIlIllI <= lllllIIlIIIlIlI; ++lllllIIlIIlIllI) {
            boolean lllllIIlIIlIlll = false;
            for (int lllllIIlIIllIII = lllllIIlIIIlllI; lllllIIlIIllIII <= lllllIIlIIIlIll; ++lllllIIlIIllIII) {
                for (int lllllIIlIIllIIl = lllllIIlIIIllII; lllllIIlIIllIIl <= lllllIIlIIIlIIl; ++lllllIIlIIllIIl) {
                    if (Utils.squaredDistance(lllllIIlIIlIIIl, lllllIIlIIlIIII, lllllIIlIIIllll, lllllIIlIIllIII, lllllIIlIIlIllI, lllllIIlIIllIIl) > lllllIIlIIIlIII) continue;
                    lllllIIlIIlIIll.blockPos.set(lllllIIlIIllIII, lllllIIlIIlIllI, lllllIIlIIllIIl);
                    BlockState lllllIIlIIllIll = lllllIIlIIlIIll.mc.world.getBlockState((BlockPos)lllllIIlIIlIIll.blockPos);
                    if (lllllIIlIIllIll.getOutlineShape((BlockView)lllllIIlIIlIIll.mc.world, (BlockPos)lllllIIlIIlIIll.blockPos) == VoxelShapes.empty()) continue;
                    if (lllllIIlIIlIIll.mode.get() == Mode.Flatten && (double)lllllIIlIIlIllI < lllllIIlIIlIIll.mc.player.getY()) {
                        lllllIIlIIlIlll = true;
                        break;
                    }
                    if (lllllIIlIIlIIll.mode.get() == Mode.Smash && lllllIIlIIllIll.getHardness((BlockView)lllllIIlIIlIIll.mc.world, (BlockPos)lllllIIlIIlIIll.blockPos) != 0.0f || lllllIIlIIlIIll.onlySelected.get().booleanValue() && !lllllIIlIIlIIll.selectedBlocks.get().contains((Object)lllllIIlIIllIll.getBlock())) continue;
                    Mutable lllllIIlIIllIlI = lllllIIlIIlIIll.blockPool.get();
                    lllllIIlIIllIlI.set(lllllIIlIIllIII, lllllIIlIIlIllI, lllllIIlIIllIIl);
                    lllllIIlIIlIIll.blocks.add(lllllIIlIIllIlI);
                }
                if (lllllIIlIIlIlll) continue block2;
            }
        }
        if (lllllIIlIIlIIll.sortMode.get() != SortMode.None) {
            lllllIIlIIlIIll.blocks.sort(Comparator.comparingDouble(lllllIIIlIIllII -> {
                Nuker lllllIIIlIlIIII;
                return Utils.squaredDistance(lllllIIlIIlIIIl, lllllIIlIIlIIII, lllllIIlIIIllll, lllllIIIlIIllII.getX(), lllllIIIlIIllII.getY(), lllllIIIlIIllII.getZ()) * (double)(lllllIIIlIlIIII.sortMode.get() == SortMode.Closest ? 1 : -1);
            }));
        }
        boolean lllllIIlIIIIlll = false;
        Iterator<Mutable> iterator = lllllIIlIIlIIll.blocks.iterator();
        if (iterator.hasNext()) {
            Mutable lllllIIlIIlIlIl = iterator.next();
            if (!lllllIIlIIlIIll.lastBlockPos.equals((Object)lllllIIlIIlIlIl)) {
                try {
                    if (lllllIIlIIlIIll.rotate.get().booleanValue()) {
                        Rotations.rotate(Rotations.getYaw((BlockPos)lllllIIlIIlIlIl), Rotations.getPitch((BlockPos)lllllIIlIIlIlIl), -50, () -> {
                            Nuker lllllIIIlIlllII;
                            lllllIIIlIlllII.cancelMine((BlockPos)lllllIIlIIlIlIl);
                        });
                    } else {
                        lllllIIlIIlIIll.cancelMine((BlockPos)lllllIIlIIlIlIl);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            lllllIIlIIlIIll.lastBlockPos.set((Vec3i)lllllIIlIIlIlIl);
            if (lllllIIlIIlIIll.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw((BlockPos)lllllIIlIIlIlIl), Rotations.getPitch((BlockPos)lllllIIlIIlIlIl), -50, () -> {
                    Nuker lllllIIIllIIIlI;
                    lllllIIIllIIIlI.mine((BlockPos)lllllIIlIIlIlIl);
                });
            } else {
                lllllIIlIIlIIll.mine((BlockPos)lllllIIlIIlIlIl);
            }
            lllllIIlIIIIlll = true;
            lllllIIlIIlIIll.hasLastBlockPos = true;
        }
        if (!lllllIIlIIIIlll) {
            lllllIIlIIlIIll.mc.interactionManager.cancelBlockBreaking();
        }
        for (Mutable lllllIIlIIlIlII : lllllIIlIIlIIll.blocks) {
            lllllIIlIIlIIll.blockPool.free(lllllIIlIIlIlII);
        }
        lllllIIlIIlIIll.blocks.clear();
    }

    public boolean noParticles() {
        Nuker lllllIIIllIIlll;
        return lllllIIIllIIlll.isActive() && lllllIIIllIIlll.noParticles.get() != false;
    }

    @Override
    public void onDeactivate() {
        Nuker lllllIIllIIIIll;
        lllllIIllIIIIll.mc.interactionManager.cancelBlockBreaking();
        lllllIIllIIIIll.hasLastBlockPos = false;
    }

    private void cancelMine(BlockPos lllllIIIllIllII) {
        Nuker lllllIIIllIllIl;
        lllllIIIllIllIl.mc.interactionManager.cancelBlockBreaking();
        if (lllllIIIllIllII != null) {
            lllllIIIllIllIl.mc.interactionManager.attackBlock(lllllIIIllIllII, Direction.UP);
            lllllIIIllIllIl.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    @Override
    public void onActivate() {
        lllllIIllIIIllI.timer = 0;
    }

    public Nuker() {
        super(Categories.World, "nuker", "Breaks a large amount of specified blocks around you.");
        Nuker lllllIIllIIlIlI;
        lllllIIllIIlIlI.sgGeneral = lllllIIllIIlIlI.settings.getDefaultGroup();
        lllllIIllIIlIlI.mode = lllllIIllIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The way the blocks are broken.").defaultValue(Mode.All).build());
        lllllIIllIIlIlI.delay = lllllIIllIIlIlI.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between mining blocks in ticks.").defaultValue(0).min(0).build());
        lllllIIllIIlIlI.selectedBlocks = lllllIIllIIlIlI.sgGeneral.add(new BlockListSetting.Builder().name("selected-blocks").description("The certain type of blocks you want to mine.").defaultValue(new ArrayList<Block>(0)).build());
        lllllIIllIIlIlI.onlySelected = lllllIIllIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("only-selected").description("Only mines your selected blocks.").defaultValue(false).build());
        lllllIIllIIlIlI.range = lllllIIllIIlIlI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The break range.").defaultValue(5.0).min(0.0).build());
        lllllIIllIIlIlI.sortMode = lllllIIllIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("sort-mode").description("The blocks you want to mine first.").defaultValue(SortMode.Closest).build());
        lllllIIllIIlIlI.noParticles = lllllIIllIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("no-particles").description("Disables all block breaking particles.").defaultValue(false).build());
        lllllIIllIIlIlI.rotate = lllllIIllIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces the blocks being mined.").defaultValue(true).build());
        lllllIIllIIlIlI.blockPool = new Pool<Mutable>(Mutable::new);
        lllllIIllIIlIlI.blocks = new ArrayList<Mutable>();
        lllllIIllIIlIlI.blockPos = new Mutable();
        lllllIIllIIlIlI.lastBlockPos = new Mutable();
    }

    private void mine(BlockPos lllllIIIlllIIII) {
        Nuker lllllIIIlllIIIl;
        if (lllllIIIlllIIIl.mc.interactionManager != null && lllllIIIlllIIIl.mc.player != null) {
            lllllIIIlllIIIl.mc.interactionManager.updateBlockBreakingProgress(lllllIIIlllIIII, Direction.UP);
            lllllIIIlllIIIl.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Smash;
        public static final /* synthetic */ /* enum */ Mode Flatten;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode All;

        static {
            All = new Mode();
            Flatten = new Mode();
            Smash = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llIIIllllllIIll) {
            return Enum.valueOf(Mode.class, llIIIllllllIIll);
        }

        private Mode() {
            Mode llIIIlllllIlllI;
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{All, Flatten, Smash};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

    public static final class SortMode
    extends Enum<SortMode> {
        public static final /* synthetic */ /* enum */ SortMode None;
        public static final /* synthetic */ /* enum */ SortMode Closest;
        public static final /* synthetic */ /* enum */ SortMode Furthest;
        private static final /* synthetic */ SortMode[] $VALUES;

        private static /* synthetic */ SortMode[] $values() {
            return new SortMode[]{None, Closest, Furthest};
        }

        public static SortMode valueOf(String llllllllllllllllllIlIllIlllIIllI) {
            return Enum.valueOf(SortMode.class, llllllllllllllllllIlIllIlllIIllI);
        }

        public static SortMode[] values() {
            return (SortMode[])$VALUES.clone();
        }

        private SortMode() {
            SortMode llllllllllllllllllIlIllIlllIIIIl;
        }

        static {
            None = new SortMode();
            Closest = new SortMode();
            Furthest = new SortMode();
            $VALUES = SortMode.$values();
        }
    }
}

