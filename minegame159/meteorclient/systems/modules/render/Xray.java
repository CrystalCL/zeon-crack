/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.block.BlockState
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
    private final /* synthetic */ Setting<List<Block>> blocks;
    private final /* synthetic */ SettingGroup sgGeneral;

    public boolean isBlocked(Block lllIlIlIIlIllI) {
        Xray lllIlIlIIllIIl;
        return !lllIlIlIIllIIl.blocks.get().contains((Object)lllIlIlIIlIllI);
    }

    @EventHandler
    private void onRenderBlockEntity(RenderBlockEntityEvent lllIlIllllllII) {
        Xray lllIlIllllllIl;
        if (lllIlIllllllIl.isBlocked(lllIlIllllllII.blockEntity.getCachedState().getBlock())) {
            lllIlIllllllII.cancel();
        }
    }

    @EventHandler
    private void onAmbientOcclusion(AmbientOcclusionEvent lllIlIllllIIll) {
        lllIlIllllIIll.lightLevel = 1.0f;
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent lllIlIllllIllI) {
        lllIlIllllIllI.cancel();
    }

    public boolean modifyDrawSide(BlockState lllIlIllIllIII, BlockView lllIlIllIlIlll, BlockPos lllIlIllIlIIII, Direction lllIlIllIlIlIl, boolean lllIlIllIlIlII) {
        Xray lllIlIllIlIIll;
        if (lllIlIllIlIlII) {
            if (lllIlIllIlIIll.isBlocked(lllIlIllIllIII.getBlock())) {
                return false;
            }
        } else if (!lllIlIllIlIIll.isBlocked(lllIlIllIllIII.getBlock())) {
            BlockPos lllIlIllIlllIl = lllIlIllIlIIII.offset(lllIlIllIlIlIl);
            BlockState lllIlIllIllIll = lllIlIllIlIlll.getBlockState(lllIlIllIlllIl);
            return lllIlIllIllIll.getCullingFace(lllIlIllIlIlll, lllIlIllIlllIl, lllIlIllIlIlIl.getOpposite()) != VoxelShapes.fullCube() || lllIlIllIllIll.getBlock() != lllIlIllIllIII.getBlock();
        }
        return lllIlIllIlIlII;
    }

    @Override
    public void onDeactivate() {
        Xray lllIllIIIIIIII;
        Fullbright.disable();
        lllIllIIIIIIII.mc.worldRenderer.reload();
    }

    public Xray() {
        super(Categories.Render, "xray", "Only renders specified blocks. Good for mining.");
        Xray lllIllIIIIlIIl;
        lllIllIIIIlIIl.sgGeneral = lllIllIIIIlIIl.settings.getDefaultGroup();
        lllIllIIIIlIIl.blocks = lllIllIIIIlIIl.sgGeneral.add(new BlockListSetting.Builder().name("blocks").description("Blocks.").defaultValue(Arrays.asList(new Block[]{Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE, Blocks.ANCIENT_DEBRIS})).onChanged(lllIlIlIIlIIll -> {
            Xray lllIlIlIIlIIlI;
            if (lllIlIlIIlIIlI.isActive()) {
                lllIlIlIIlIIlI.mc.worldRenderer.reload();
            }
        }).build());
    }

    @Override
    public void onActivate() {
        Xray lllIllIIIIIllI;
        Fullbright.enable();
        lllIllIIIIIllI.mc.worldRenderer.reload();
    }
}

