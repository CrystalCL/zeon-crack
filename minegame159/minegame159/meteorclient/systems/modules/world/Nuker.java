/*
 * Decompiled with CFR 0.151.
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
    private final Setting<List<Block>> selectedBlocks;
    private final Setting<Boolean> noParticles;
    private final List<Mutable> blocks;
    private final SettingGroup sgGeneral;
    private boolean hasLastBlockPos;
    private final Mutable blockPos;
    private final Setting<Integer> delay;
    private int timer;
    private final Mutable lastBlockPos;
    private final Setting<Double> range;
    private final Setting<Boolean> rotate;
    private final Setting<SortMode> sortMode;
    private final Pool<Mutable> blockPool;
    private final Setting<Mode> mode;
    private final Setting<Boolean> onlySelected;

    private void cancelMine(BlockPos BlockPos2) {
        this.mc.interactionManager.cancelBlockBreaking();
        if (BlockPos2 != null) {
            this.mc.interactionManager.attackBlock(BlockPos2, Direction.UP);
            this.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n;
        if (this.hasLastBlockPos && this.mc.world.getBlockState((BlockPos)this.lastBlockPos).getBlock() != Blocks.AIR) {
            this.mc.interactionManager.updateBlockBreakingProgress((BlockPos)this.lastBlockPos, Direction.UP);
            return;
        }
        this.hasLastBlockPos = false;
        if (this.timer < this.delay.get()) {
            ++this.timer;
            return;
        }
        this.timer = 0;
        double d = this.mc.player.getX() - 0.5;
        double d2 = this.mc.player.getY();
        double d3 = this.mc.player.getZ() - 0.5;
        int n2 = (int)Math.floor(d - this.range.get());
        int n3 = (int)Math.floor(d2 - this.range.get());
        int n4 = (int)Math.floor(d3 - this.range.get());
        int n5 = (int)Math.floor(d + this.range.get());
        int n6 = (int)Math.floor(d2 + this.range.get());
        int n7 = (int)Math.floor(d3 + this.range.get());
        double d4 = Math.pow(this.range.get(), 2.0);
        block2: for (n = n3; n <= n6; ++n) {
            boolean bl = false;
            for (int i = n2; i <= n5; ++i) {
                for (int j = n4; j <= n7; ++j) {
                    if (Utils.squaredDistance(d, d2, d3, i, n, j) > d4) continue;
                    this.blockPos.set(i, n, j);
                    BlockState BlockState2 = this.mc.world.getBlockState((BlockPos)this.blockPos);
                    if (BlockState2.getOutlineShape((BlockView)this.mc.world, (BlockPos)this.blockPos) == VoxelShapes.empty()) continue;
                    if (this.mode.get() == Mode.Flatten && (double)n < this.mc.player.getY()) {
                        bl = true;
                        break;
                    }
                    if (this.mode.get() == Mode.Smash && BlockState2.getHardness((BlockView)this.mc.world, (BlockPos)this.blockPos) != 0.0f || this.onlySelected.get().booleanValue() && !this.selectedBlocks.get().contains(BlockState2.getBlock())) continue;
                    Mutable class_23392 = this.blockPool.get();
                    class_23392.set(i, n, j);
                    this.blocks.add(class_23392);
                    if (null == null) continue;
                    return;
                }
                if (bl) continue block2;
                if (3 > -1) continue;
                return;
            }
            if (3 > -1) continue;
            return;
        }
        if (this.sortMode.get() != SortMode.None) {
            this.blocks.sort(Comparator.comparingDouble(arg_0 -> this.lambda$onTick$0(d, d2, d3, arg_0)));
        }
        n = 0;
        Iterator<Mutable> iterator = this.blocks.iterator();
        if (iterator.hasNext()) {
            Mutable class_23393 = iterator.next();
            if (!this.lastBlockPos.equals((Object)class_23393)) {
                try {
                    if (this.rotate.get().booleanValue()) {
                        Rotations.rotate(Rotations.getYaw((BlockPos)class_23393), Rotations.getPitch((BlockPos)class_23393), -50, () -> this.lambda$onTick$1(class_23393));
                    } else {
                        this.cancelMine((BlockPos)class_23393);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.lastBlockPos.set((Vec3i)class_23393);
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw((BlockPos)class_23393), Rotations.getPitch((BlockPos)class_23393), -50, () -> this.lambda$onTick$2(class_23393));
            } else {
                this.mine((BlockPos)class_23393);
            }
            n = 1;
            this.hasLastBlockPos = true;
        }
        if (n == 0) {
            this.mc.interactionManager.cancelBlockBreaking();
        }
        for (Mutable class_23394 : this.blocks) {
            this.blockPool.free(class_23394);
        }
        this.blocks.clear();
    }

    private void mine(BlockPos BlockPos2) {
        if (this.mc.interactionManager != null && this.mc.player != null) {
            this.mc.interactionManager.updateBlockBreakingProgress(BlockPos2, Direction.UP);
            this.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private void lambda$onTick$2(Mutable class_23392) {
        this.mine((BlockPos)class_23392);
    }

    @Override
    public void onDeactivate() {
        this.mc.interactionManager.cancelBlockBreaking();
        this.hasLastBlockPos = false;
    }

    public boolean noParticles() {
        return this.isActive() && this.noParticles.get() != false;
    }

    private void lambda$onTick$1(Mutable class_23392) {
        this.cancelMine((BlockPos)class_23392);
    }

    public Nuker() {
        super(Categories.World, "nuker", "Breaks a large amount of specified blocks around you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The way the blocks are broken.").defaultValue(Mode.All).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between mining blocks in ticks.").defaultValue(0).min(0).build());
        this.selectedBlocks = this.sgGeneral.add(new BlockListSetting.Builder().name("selected-blocks").description("The certain type of blocks you want to mine.").defaultValue(new ArrayList<Block>(0)).build());
        this.onlySelected = this.sgGeneral.add(new BoolSetting.Builder().name("only-selected").description("Only mines your selected blocks.").defaultValue(false).build());
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The break range.").defaultValue(5.0).min(0.0).build());
        this.sortMode = this.sgGeneral.add(new EnumSetting.Builder().name("sort-mode").description("The blocks you want to mine first.").defaultValue(SortMode.Closest).build());
        this.noParticles = this.sgGeneral.add(new BoolSetting.Builder().name("no-particles").description("Disables all block breaking particles.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces the blocks being mined.").defaultValue(true).build());
        this.blockPool = new Pool<Mutable>(Mutable::new);
        this.blocks = new ArrayList<Mutable>();
        this.blockPos = new Mutable();
        this.lastBlockPos = new Mutable();
    }

    private double lambda$onTick$0(double d, double d2, double d3, Mutable class_23392) {
        return Utils.squaredDistance(d, d2, d3, class_23392.getX(), class_23392.getY(), class_23392.getZ()) * (double)(this.sortMode.get() == SortMode.Closest ? 1 : -1);
    }

    @Override
    public void onActivate() {
        this.timer = 0;
    }

    public static final class SortMode
    extends Enum<SortMode> {
        public static final /* enum */ SortMode Closest;
        public static final /* enum */ SortMode Furthest;
        public static final /* enum */ SortMode None;
        private static final SortMode[] $VALUES;

        static {
            None = new SortMode();
            Closest = new SortMode();
            Furthest = new SortMode();
            $VALUES = SortMode.$values();
        }

        private static SortMode[] $values() {
            return new SortMode[]{None, Closest, Furthest};
        }

        public static SortMode valueOf(String string) {
            return Enum.valueOf(SortMode.class, string);
        }

        public static SortMode[] values() {
            return (SortMode[])$VALUES.clone();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode All = new Mode();
        public static final /* enum */ Mode Flatten = new Mode();
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Smash;

        private static Mode[] $values() {
            return new Mode[]{All, Flatten, Smash};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Smash = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }
}

