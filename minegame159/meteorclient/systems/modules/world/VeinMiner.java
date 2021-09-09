/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Item
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Direction$class_2351
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.shape.VoxelShape
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

public class VeinMiner
extends Module {
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ List<BlockPos> foundBlockPositions;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Pool<MyBlock> blockPool;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Set<Vec3i> blockNeighbours;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ List<MyBlock> blocks;
    private final /* synthetic */ Setting<Integer> depth;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> rotate;

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIlllllIIlIllIII) {
        VeinMiner lllllllllllllllllIlllllIIlIllIIl;
        if (lllllllllllllllllIlllllIIlIllIIl.render.get().booleanValue()) {
            for (MyBlock lllllllllllllllllIlllllIIlIllIlI : lllllllllllllllllIlllllIIlIllIIl.blocks) {
                lllllllllllllllllIlllllIIlIllIlI.render();
            }
        }
    }

    public VeinMiner() {
        super(Categories.World, "vein-miner", "Mines all nearby blocks with this type");
        VeinMiner lllllllllllllllllIlllllIIllllllI;
        lllllllllllllllllIlllllIIllllllI.sgGeneral = lllllllllllllllllIlllllIIllllllI.settings.getDefaultGroup();
        lllllllllllllllllIlllllIIllllllI.sgRender = lllllllllllllllllIlllllIIllllllI.settings.createGroup("Render");
        lllllllllllllllllIlllllIIllllllI.blockNeighbours = Sets.newHashSet((Object[])new Vec3i[]{new Vec3i(1, -1, 1), new Vec3i(0, -1, 1), new Vec3i(-1, -1, 1), new Vec3i(1, -1, 0), new Vec3i(0, -1, 0), new Vec3i(-1, -1, 0), new Vec3i(1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(-1, -1, -1), new Vec3i(1, 0, 1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(1, 0, -1), new Vec3i(0, 0, -1), new Vec3i(-1, 0, -1), new Vec3i(1, 1, 1), new Vec3i(0, 1, 1), new Vec3i(-1, 1, 1), new Vec3i(1, 1, 0), new Vec3i(0, 1, 0), new Vec3i(-1, 1, 0), new Vec3i(1, 1, -1), new Vec3i(0, 1, -1), new Vec3i(-1, 1, -1)});
        lllllllllllllllllIlllllIIllllllI.depth = lllllllllllllllllIlllllIIllllllI.sgGeneral.add(new IntSetting.Builder().name("depth").description("Amount of iterations used to scan for similar blocks").defaultValue(3).min(1).sliderMax(15).build());
        lllllllllllllllllIlllllIIllllllI.rotate = lllllllllllllllllIlllllIIllllllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when mining.").defaultValue(true).build());
        lllllllllllllllllIlllllIIllllllI.render = lllllllllllllllllIlllllIIllllllI.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the block being mined.").defaultValue(true).build());
        lllllllllllllllllIlllllIIllllllI.shapeMode = lllllllllllllllllIlllllIIllllllI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIlllllIIllllllI.sideColor = lllllllllllllllllIlllllIIllllllI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lllllllllllllllllIlllllIIllllllI.lineColor = lllllllllllllllllIlllllIIllllllI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lllllllllllllllllIlllllIIllllllI.blockPool = new Pool<MyBlock>(() -> {
            VeinMiner lllllllllllllllllIlllllIIIlllIIl;
            return lllllllllllllllllIlllllIIIlllIIl.new MyBlock();
        });
        lllllllllllllllllIlllllIIllllllI.blocks = new ArrayList<MyBlock>();
        lllllllllllllllllIlllllIIllllllI.foundBlockPositions = new ArrayList<BlockPos>();
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent lllllllllllllllllIlllllIIllIIIll) {
        VeinMiner lllllllllllllllllIlllllIIllIIlII;
        if (lllllllllllllllllIlllllIIllIIlII.mc.world.getBlockState(lllllllllllllllllIlllllIIllIIIll.blockPos).getHardness((BlockView)lllllllllllllllllIlllllIIllIIlII.mc.world, lllllllllllllllllIlllllIIllIIIll.blockPos) < 0.0f) {
            return;
        }
        lllllllllllllllllIlllllIIllIIlII.foundBlockPositions.clear();
        if (!lllllllllllllllllIlllllIIllIIlII.isMiningBlock(lllllllllllllllllIlllllIIllIIIll.blockPos)) {
            MyBlock lllllllllllllllllIlllllIIllIIlll = lllllllllllllllllIlllllIIllIIlII.blockPool.get();
            lllllllllllllllllIlllllIIllIIlll.set(lllllllllllllllllIlllllIIllIIIll);
            lllllllllllllllllIlllllIIllIIlII.blocks.add(lllllllllllllllllIlllllIIllIIlll);
            lllllllllllllllllIlllllIIllIIlII.mineNearbyBlocks(lllllllllllllllllIlllllIIllIIlll.originalBlock.asItem(), lllllllllllllllllIlllllIIllIIIll.blockPos, lllllllllllllllllIlllllIIllIIIll.direction, lllllllllllllllllIlllllIIllIIlII.depth.get());
        }
    }

    private void mineNearbyBlocks(Item lllllllllllllllllIlllllIIlIIIIlI, BlockPos lllllllllllllllllIlllllIIlIIIllI, Direction lllllllllllllllllIlllllIIlIIIlIl, int lllllllllllllllllIlllllIIIllllll) {
        VeinMiner lllllllllllllllllIlllllIIlIIlIII;
        if (lllllllllllllllllIlllllIIIllllll <= 0) {
            return;
        }
        if (lllllllllllllllllIlllllIIlIIlIII.foundBlockPositions.contains((Object)lllllllllllllllllIlllllIIlIIIllI)) {
            return;
        }
        lllllllllllllllllIlllllIIlIIlIII.foundBlockPositions.add(lllllllllllllllllIlllllIIlIIIllI);
        if (Utils.distance(lllllllllllllllllIlllllIIlIIlIII.mc.player.getX() - 0.5, lllllllllllllllllIlllllIIlIIlIII.mc.player.getY() + (double)lllllllllllllllllIlllllIIlIIlIII.mc.player.getEyeHeight(lllllllllllllllllIlllllIIlIIlIII.mc.player.getPose()), lllllllllllllllllIlllllIIlIIlIII.mc.player.getZ() - 0.5, lllllllllllllllllIlllllIIlIIIllI.getX(), lllllllllllllllllIlllllIIlIIIllI.getY(), lllllllllllllllllIlllllIIlIIIllI.getZ()) > (double)lllllllllllllllllIlllllIIlIIlIII.mc.interactionManager.getReachDistance()) {
            return;
        }
        for (Vec3i lllllllllllllllllIlllllIIlIIlIIl : lllllllllllllllllIlllllIIlIIlIII.blockNeighbours) {
            BlockPos lllllllllllllllllIlllllIIlIIlIlI = lllllllllllllllllIlllllIIlIIIllI.add(lllllllllllllllllIlllllIIlIIlIIl);
            if (lllllllllllllllllIlllllIIlIIlIII.mc.world.getBlockState(lllllllllllllllllIlllllIIlIIlIlI).getBlock().asItem() != lllllllllllllllllIlllllIIlIIIIlI) continue;
            MyBlock lllllllllllllllllIlllllIIlIIlIll = lllllllllllllllllIlllllIIlIIlIII.blockPool.get();
            lllllllllllllllllIlllllIIlIIlIll.set(lllllllllllllllllIlllllIIlIIlIlI, lllllllllllllllllIlllllIIlIIIlIl);
            lllllllllllllllllIlllllIIlIIlIII.blocks.add(lllllllllllllllllIlllllIIlIIlIll);
            lllllllllllllllllIlllllIIlIIlIII.mineNearbyBlocks(lllllllllllllllllIlllllIIlIIIIlI, lllllllllllllllllIlllllIIlIIlIlI, lllllllllllllllllIlllllIIlIIIlIl, lllllllllllllllllIlllllIIIllllll - 1);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlllllIIlIlllll) {
        VeinMiner lllllllllllllllllIlllllIIllIIIII;
        lllllllllllllllllIlllllIIllIIIII.blocks.removeIf(MyBlock::shouldRemove);
        if (!lllllllllllllllllIlllllIIllIIIII.blocks.isEmpty()) {
            lllllllllllllllllIlllllIIllIIIII.blocks.get(0).mine();
        }
    }

    private boolean isMiningBlock(BlockPos lllllllllllllllllIlllllIIllIllll) {
        VeinMiner lllllllllllllllllIlllllIIllIlllI;
        for (MyBlock lllllllllllllllllIlllllIIlllIIIl : lllllllllllllllllIlllllIIllIlllI.blocks) {
            if (!lllllllllllllllllIlllllIIlllIIIl.blockPos.equals((Object)lllllllllllllllllIlllllIIllIllll)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onDeactivate() {
        VeinMiner lllllllllllllllllIlllllIIllllIII;
        for (MyBlock lllllllllllllllllIlllllIIllllIlI : lllllllllllllllllIlllllIIllllIII.blocks) {
            lllllllllllllllllIlllllIIllllIII.blockPool.free(lllllllllllllllllIlllllIIllllIlI);
        }
        lllllllllllllllllIlllllIIllllIII.blocks.clear();
        lllllllllllllllllIlllllIIllllIII.foundBlockPositions.clear();
    }

    private class MyBlock {
        public /* synthetic */ Direction direction;
        public /* synthetic */ boolean mining;
        public /* synthetic */ Block originalBlock;
        public /* synthetic */ BlockPos blockPos;

        public void mine() {
            MyBlock llllllllllllllllllllIIIlllIlllII;
            if (!llllllllllllllllllllIIIlllIlllII.mining) {
                ((VeinMiner)llllllllllllllllllllIIIlllIlllII.VeinMiner.this).mc.player.swingHand(Hand.MAIN_HAND);
                llllllllllllllllllllIIIlllIlllII.mining = true;
            }
            if (((Boolean)llllllllllllllllllllIIIlllIlllII.VeinMiner.this.rotate.get()).booleanValue()) {
                Rotations.rotate(Rotations.getYaw(llllllllllllllllllllIIIlllIlllII.blockPos), Rotations.getPitch(llllllllllllllllllllIIIlllIlllII.blockPos), 50, llllllllllllllllllllIIIlllIlllII::updateBlockBreakingProgress);
            } else {
                llllllllllllllllllllIIIlllIlllII.updateBlockBreakingProgress();
            }
        }

        public void render() {
            MyBlock llllllllllllllllllllIIIlllIIlIII;
            VoxelShape llllllllllllllllllllIIIlllIIllll = ((VeinMiner)llllllllllllllllllllIIIlllIIlIII.VeinMiner.this).mc.world.getBlockState(llllllllllllllllllllIIIlllIIlIII.blockPos).getOutlineShape((BlockView)((VeinMiner)llllllllllllllllllllIIIlllIIlIII.VeinMiner.this).mc.world, llllllllllllllllllllIIIlllIIlIII.blockPos);
            double llllllllllllllllllllIIIlllIIlllI = llllllllllllllllllllIIIlllIIlIII.blockPos.getX();
            double llllllllllllllllllllIIIlllIIllIl = llllllllllllllllllllIIIlllIIlIII.blockPos.getY();
            double llllllllllllllllllllIIIlllIIllII = llllllllllllllllllllIIIlllIIlIII.blockPos.getZ();
            double llllllllllllllllllllIIIlllIIlIll = llllllllllllllllllllIIIlllIIlIII.blockPos.getX() + 1;
            double llllllllllllllllllllIIIlllIIlIlI = llllllllllllllllllllIIIlllIIlIII.blockPos.getY() + 1;
            double llllllllllllllllllllIIIlllIIlIIl = llllllllllllllllllllIIIlllIIlIII.blockPos.getZ() + 1;
            if (!llllllllllllllllllllIIIlllIIllll.isEmpty()) {
                llllllllllllllllllllIIIlllIIlllI = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getX() + llllllllllllllllllllIIIlllIIllll.getMin(Direction.class_2351.X);
                llllllllllllllllllllIIIlllIIllIl = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getY() + llllllllllllllllllllIIIlllIIllll.getMin(Direction.class_2351.Y);
                llllllllllllllllllllIIIlllIIllII = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getZ() + llllllllllllllllllllIIIlllIIllll.getMin(Direction.class_2351.Z);
                llllllllllllllllllllIIIlllIIlIll = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getX() + llllllllllllllllllllIIIlllIIllll.getMax(Direction.class_2351.X);
                llllllllllllllllllllIIIlllIIlIlI = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getY() + llllllllllllllllllllIIIlllIIllll.getMax(Direction.class_2351.Y);
                llllllllllllllllllllIIIlllIIlIIl = (double)llllllllllllllllllllIIIlllIIlIII.blockPos.getZ() + llllllllllllllllllllIIIlllIIllll.getMax(Direction.class_2351.Z);
            }
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllllllIIIlllIIlllI, llllllllllllllllllllIIIlllIIllIl, llllllllllllllllllllIIIlllIIllII, llllllllllllllllllllIIIlllIIlIll, llllllllllllllllllllIIIlllIIlIlI, llllllllllllllllllllIIIlllIIlIIl, (Color)llllllllllllllllllllIIIlllIIlIII.VeinMiner.this.sideColor.get(), (Color)llllllllllllllllllllIIIlllIIlIII.VeinMiner.this.lineColor.get(), (ShapeMode)((Object)llllllllllllllllllllIIIlllIIlIII.VeinMiner.this.shapeMode.get()), 0);
        }

        private MyBlock() {
            MyBlock llllllllllllllllllllIIIlllllIlIl;
        }

        public void set(BlockPos llllllllllllllllllllIIIllllIIllI, Direction llllllllllllllllllllIIIllllIIlIl) {
            MyBlock llllllllllllllllllllIIIllllIIlll;
            llllllllllllllllllllIIIllllIIlll.blockPos = llllllllllllllllllllIIIllllIIllI;
            llllllllllllllllllllIIIllllIIlll.direction = llllllllllllllllllllIIIllllIIlIl;
            llllllllllllllllllllIIIllllIIlll.originalBlock = ((VeinMiner)llllllllllllllllllllIIIllllIIlll.VeinMiner.this).mc.world.getBlockState(llllllllllllllllllllIIIllllIIllI).getBlock();
            llllllllllllllllllllIIIllllIIlll.mining = false;
        }

        private void updateBlockBreakingProgress() {
            MyBlock llllllllllllllllllllIIIlllIllIIl;
            ((VeinMiner)llllllllllllllllllllIIIlllIllIIl.VeinMiner.this).mc.interactionManager.updateBlockBreakingProgress(llllllllllllllllllllIIIlllIllIIl.blockPos, llllllllllllllllllllIIIlllIllIIl.direction);
        }

        public boolean shouldRemove() {
            MyBlock llllllllllllllllllllIIIllllIIIlI;
            boolean llllllllllllllllllllIIIllllIIIIl;
            boolean bl = llllllllllllllllllllIIIllllIIIIl = ((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.world.getBlockState(llllllllllllllllllllIIIllllIIIlI.blockPos).getBlock() != llllllllllllllllllllIIIllllIIIlI.originalBlock || Utils.distance(((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.getX() - 0.5, ((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.getY() + (double)((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.getEyeHeight(((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.getPose()), ((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.getZ() - 0.5, llllllllllllllllllllIIIllllIIIlI.blockPos.getX() + llllllllllllllllllllIIIllllIIIlI.direction.getOffsetX(), llllllllllllllllllllIIIllllIIIlI.blockPos.getY() + llllllllllllllllllllIIIllllIIIlI.direction.getOffsetY(), llllllllllllllllllllIIIllllIIIlI.blockPos.getZ() + llllllllllllllllllllIIIllllIIIlI.direction.getOffsetZ()) > (double)((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.interactionManager.getReachDistance();
            if (llllllllllllllllllllIIIllllIIIIl) {
                ((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.interactionManager.cancelBlockBreaking();
                ((VeinMiner)llllllllllllllllllllIIIllllIIIlI.VeinMiner.this).mc.player.swingHand(Hand.MAIN_HAND);
            }
            return llllllllllllllllllllIIIllllIIIIl;
        }

        public void set(StartBreakingBlockEvent llllllllllllllllllllIIIlllllIIII) {
            MyBlock llllllllllllllllllllIIIllllIllll;
            llllllllllllllllllllIIIllllIllll.blockPos = llllllllllllllllllllIIIlllllIIII.blockPos;
            llllllllllllllllllllIIIllllIllll.direction = llllllllllllllllllllIIIlllllIIII.direction;
            llllllllllllllllllllIIIllllIllll.originalBlock = ((VeinMiner)llllllllllllllllllllIIIllllIllll.VeinMiner.this).mc.world.getBlockState(llllllllllllllllllllIIIllllIllll.blockPos).getBlock();
            llllllllllllllllllllIIIllllIllll.mining = false;
        }
    }
}

