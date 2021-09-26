/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Hand;
import net.minecraft.item.Item;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.client.MinecraftClient;

public class VeinMiner
extends Module {
    private final Setting<ShapeMode> shapeMode;
    private final Pool<MyBlock> blockPool;
    private final SettingGroup sgRender;
    private final Set<Vec3i> blockNeighbours;
    private final SettingGroup sgGeneral;
    private final List<BlockPos> foundBlockPositions;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> rotate;
    private final Setting<SettingColor> lineColor;
    private final Setting<Integer> depth;
    private final List<MyBlock> blocks;

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        if (this.mc.world.getBlockState(startBreakingBlockEvent.blockPos).getHardness((BlockView)this.mc.world, startBreakingBlockEvent.blockPos) < 0.0f) {
            return;
        }
        this.foundBlockPositions.clear();
        if (!this.isMiningBlock(startBreakingBlockEvent.blockPos)) {
            MyBlock myBlock = this.blockPool.get();
            myBlock.set(startBreakingBlockEvent);
            this.blocks.add(myBlock);
            this.mineNearbyBlocks(myBlock.originalBlock.asItem(), startBreakingBlockEvent.blockPos, startBreakingBlockEvent.direction, this.depth.get());
        }
    }

    static MinecraftClient access$800(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$000(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$1000(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$600(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$1400(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$500(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$400(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    private boolean isMiningBlock(BlockPos BlockPos2) {
        for (MyBlock myBlock : this.blocks) {
            if (!myBlock.blockPos.equals((Object)BlockPos2)) continue;
            return true;
        }
        return false;
    }

    static MinecraftClient access$1100(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$1500(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$200(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$100(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.render.get().booleanValue()) {
            for (MyBlock myBlock : this.blocks) {
                myBlock.render();
            }
        }
    }

    private void mineNearbyBlocks(Item Item2, BlockPos BlockPos2, Direction Direction2, int n) {
        if (n <= 0) {
            return;
        }
        if (this.foundBlockPositions.contains(BlockPos2)) {
            return;
        }
        this.foundBlockPositions.add(BlockPos2);
        if (Utils.distance(this.mc.player.getX() - 0.5, this.mc.player.getY() + (double)this.mc.player.getEyeHeight(this.mc.player.getPose()), this.mc.player.getZ() - 0.5, BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ()) > (double)this.mc.interactionManager.getReachDistance()) {
            return;
        }
        for (Vec3i Vec3i2 : this.blockNeighbours) {
            BlockPos BlockPos3 = BlockPos2.add(Vec3i2);
            if (this.mc.world.getBlockState(BlockPos3).getBlock().asItem() != Item2) continue;
            MyBlock myBlock = this.blockPool.get();
            myBlock.set(BlockPos3, Direction2);
            this.blocks.add(myBlock);
            this.mineNearbyBlocks(Item2, BlockPos3, Direction2, n - 1);
        }
    }

    static MinecraftClient access$1300(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static MinecraftClient access$900(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static Setting access$1600(VeinMiner veinMiner) {
        return veinMiner.sideColor;
    }

    @Override
    public void onDeactivate() {
        for (MyBlock myBlock : this.blocks) {
            this.blockPool.free(myBlock);
        }
        this.blocks.clear();
        this.foundBlockPositions.clear();
    }

    static MinecraftClient access$300(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.blocks.removeIf(MyBlock::shouldRemove);
        if (!this.blocks.isEmpty()) {
            this.blocks.get(0).mine();
        }
    }

    static Setting access$1200(VeinMiner veinMiner) {
        return veinMiner.rotate;
    }

    private MyBlock lambda$new$0() {
        return new MyBlock(this, null);
    }

    public VeinMiner() {
        super(Categories.World, "vein-miner", "Mines all nearby blocks with this type");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.blockNeighbours = Sets.newHashSet((Object[])new Vec3i[]{new Vec3i(1, -1, 1), new Vec3i(0, -1, 1), new Vec3i(-1, -1, 1), new Vec3i(1, -1, 0), new Vec3i(0, -1, 0), new Vec3i(-1, -1, 0), new Vec3i(1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(-1, -1, -1), new Vec3i(1, 0, 1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(1, 0, -1), new Vec3i(0, 0, -1), new Vec3i(-1, 0, -1), new Vec3i(1, 1, 1), new Vec3i(0, 1, 1), new Vec3i(-1, 1, 1), new Vec3i(1, 1, 0), new Vec3i(0, 1, 0), new Vec3i(-1, 1, 0), new Vec3i(1, 1, -1), new Vec3i(0, 1, -1), new Vec3i(-1, 1, -1)});
        this.depth = this.sgGeneral.add(new IntSetting.Builder().name("depth").description("Amount of iterations used to scan for similar blocks").defaultValue(3).min(1).sliderMax(15).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when mining.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the block being mined.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.blockPool = new Pool<MyBlock>(this::lambda$new$0);
        this.blocks = new ArrayList<MyBlock>();
        this.foundBlockPositions = new ArrayList<BlockPos>();
    }

    static MinecraftClient access$700(VeinMiner veinMiner) {
        return veinMiner.mc;
    }

    static Setting access$1700(VeinMiner veinMiner) {
        return veinMiner.lineColor;
    }

    static Setting access$1800(VeinMiner veinMiner) {
        return veinMiner.shapeMode;
    }

    private class MyBlock {
        public boolean mining;
        public Direction direction;
        public Block originalBlock;
        final VeinMiner this$0;
        public BlockPos blockPos;

        public void render() {
            VoxelShape VoxelShape2 = VeinMiner.access$1500((VeinMiner)this.this$0).world.getBlockState(this.blockPos).getOutlineShape((BlockView)VeinMiner.access$1400((VeinMiner)this.this$0).world, this.blockPos);
            double d = this.blockPos.getX();
            double d2 = this.blockPos.getY();
            double d3 = this.blockPos.getZ();
            double d4 = this.blockPos.getX() + 1;
            double d5 = this.blockPos.getY() + 1;
            double d6 = this.blockPos.getZ() + 1;
            if (!VoxelShape2.isEmpty()) {
                d = (double)this.blockPos.getX() + VoxelShape2.getMin(Direction.class_2351.X);
                d2 = (double)this.blockPos.getY() + VoxelShape2.getMin(Direction.class_2351.Y);
                d3 = (double)this.blockPos.getZ() + VoxelShape2.getMin(Direction.class_2351.Z);
                d4 = (double)this.blockPos.getX() + VoxelShape2.getMax(Direction.class_2351.X);
                d5 = (double)this.blockPos.getY() + VoxelShape2.getMax(Direction.class_2351.Y);
                d6 = (double)this.blockPos.getZ() + VoxelShape2.getMax(Direction.class_2351.Z);
            }
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d, d2, d3, d4, d5, d6, (Color)VeinMiner.access$1600(this.this$0).get(), (Color)VeinMiner.access$1700(this.this$0).get(), (ShapeMode)((Object)VeinMiner.access$1800(this.this$0).get()), 0);
        }

        private void updateBlockBreakingProgress() {
            VeinMiner.access$1300((VeinMiner)this.this$0).interactionManager.updateBlockBreakingProgress(this.blockPos, this.direction);
        }

        public void set(BlockPos BlockPos2, Direction Direction2) {
            this.blockPos = BlockPos2;
            this.direction = Direction2;
            this.originalBlock = VeinMiner.access$100((VeinMiner)this.this$0).world.getBlockState(BlockPos2).getBlock();
            this.mining = false;
        }

        private MyBlock(VeinMiner veinMiner) {
            this.this$0 = veinMiner;
        }

        MyBlock(VeinMiner veinMiner, 1 var2_2) {
            this(veinMiner);
        }

        public void mine() {
            if (!this.mining) {
                VeinMiner.access$1100((VeinMiner)this.this$0).player.swingHand(Hand.MAIN_HAND);
                this.mining = true;
            }
            if (((Boolean)VeinMiner.access$1200(this.this$0).get()).booleanValue()) {
                Rotations.rotate(Rotations.getYaw(this.blockPos), Rotations.getPitch(this.blockPos), 50, this::updateBlockBreakingProgress);
            } else {
                this.updateBlockBreakingProgress();
            }
        }

        public void set(StartBreakingBlockEvent startBreakingBlockEvent) {
            this.blockPos = startBreakingBlockEvent.blockPos;
            this.direction = startBreakingBlockEvent.direction;
            this.originalBlock = VeinMiner.access$000((VeinMiner)this.this$0).world.getBlockState(this.blockPos).getBlock();
            this.mining = false;
        }

        public boolean shouldRemove() {
            boolean bl;
            boolean bl2 = bl = VeinMiner.access$200((VeinMiner)this.this$0).world.getBlockState(this.blockPos).getBlock() != this.originalBlock || Utils.distance(VeinMiner.access$300((VeinMiner)this.this$0).player.getX() - 0.5, VeinMiner.access$400((VeinMiner)this.this$0).player.getY() + (double)VeinMiner.access$600((VeinMiner)this.this$0).player.getEyeHeight(VeinMiner.access$500((VeinMiner)this.this$0).player.getPose()), VeinMiner.access$700((VeinMiner)this.this$0).player.getZ() - 0.5, this.blockPos.getX() + this.direction.getOffsetX(), this.blockPos.getY() + this.direction.getOffsetY(), this.blockPos.getZ() + this.direction.getOffsetZ()) > (double)VeinMiner.access$800((VeinMiner)this.this$0).interactionManager.getReachDistance();
            if (bl) {
                VeinMiner.access$900((VeinMiner)this.this$0).interactionManager.cancelBlockBreaking();
                VeinMiner.access$1000((VeinMiner)this.this$0).player.swingHand(Hand.MAIN_HAND);
            }
            return bl;
        }
    }
}

