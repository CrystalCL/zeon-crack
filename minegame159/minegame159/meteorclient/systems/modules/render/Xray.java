/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.Arrays;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderBlockEntityEvent;
import minegame159.meteorclient.events.world.AmbientOcclusionEvent;
import minegame159.meteorclient.events.world.ChunkOcclusionEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.render.Fullbright;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.block.BlockState;

public class Xray
extends Module {
    private final Setting<List<Block>> blocks;
    private final SettingGroup sgGeneral;

    public Xray() {
        super(Categories.Render, "xray", "Only renders specified blocks. Good for mining.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.blocks = this.sgGeneral.add(new BlockListSetting.Builder().name("blocks").description("Blocks.").defaultValue(Arrays.asList(Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE, Blocks.ANCIENT_DEBRIS)).onChanged(this::lambda$new$0).build());
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent chunkOcclusionEvent) {
        chunkOcclusionEvent.cancel();
    }

    @EventHandler
    private void onAmbientOcclusion(AmbientOcclusionEvent ambientOcclusionEvent) {
        ambientOcclusionEvent.lightLevel = 1.0f;
    }

    public boolean modifyDrawSide(BlockState BlockState2, BlockView BlockView2, BlockPos BlockPos2, Direction Direction2, boolean bl) {
        if (bl) {
            if (this.isBlocked(BlockState2.getBlock())) {
                return false;
            }
        } else if (!this.isBlocked(BlockState2.getBlock())) {
            BlockPos BlockPos3 = BlockPos2.offset(Direction2);
            BlockState BlockState3 = BlockView2.getBlockState(BlockPos3);
            return BlockState3.getCullingFace(BlockView2, BlockPos3, Direction2.getOpposite()) != VoxelShapes.fullCube() || BlockState3.getBlock() != BlockState2.getBlock();
        }
        return bl;
    }

    private void lambda$new$0(List list) {
        if (this.isActive()) {
            this.mc.worldRenderer.reload();
        }
    }

    @Override
    public void onActivate() {
        Fullbright.enable();
        this.mc.worldRenderer.reload();
    }

    @EventHandler
    private void onRenderBlockEntity(RenderBlockEntityEvent renderBlockEntityEvent) {
        if (this.isBlocked(renderBlockEntityEvent.blockEntity.getCachedState().getBlock())) {
            renderBlockEntityEvent.cancel();
        }
    }

    public boolean isBlocked(Block Block2) {
        return !this.blocks.get().contains(Block2);
    }

    @Override
    public void onDeactivate() {
        Fullbright.disable();
        this.mc.worldRenderer.reload();
    }
}

