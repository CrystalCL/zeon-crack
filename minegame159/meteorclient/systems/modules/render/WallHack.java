/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.ChunkOcclusionEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.block.Block;

public class WallHack
extends Module {
    public final /* synthetic */ Setting<Boolean> occludeChunks;
    private final /* synthetic */ SettingGroup sgGeneral;
    public final /* synthetic */ Setting<Integer> opacity;
    public final /* synthetic */ Setting<List<Block>> blocks;

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent llllllllllllllllIllIllllllllllII) {
        WallHack llllllllllllllllIllIllllllllllll;
        if (!llllllllllllllllIllIllllllllllll.occludeChunks.get().booleanValue()) {
            llllllllllllllllIllIllllllllllII.cancel();
        }
    }

    @Override
    public void onActivate() {
        WallHack llllllllllllllllIlllIIIIIIIIIlIl;
        llllllllllllllllIlllIIIIIIIIIlIl.mc.worldRenderer.reload();
    }

    @Override
    public void onDeactivate() {
        WallHack llllllllllllllllIlllIIIIIIIIIIll;
        llllllllllllllllIlllIIIIIIIIIIll.mc.worldRenderer.reload();
    }

    public WallHack() {
        super(Categories.Render, "wall-hack", "Makes blocks translucent.");
        WallHack llllllllllllllllIlllIIIIIIIIlIIl;
        llllllllllllllllIlllIIIIIIIIlIIl.sgGeneral = llllllllllllllllIlllIIIIIIIIlIIl.settings.getDefaultGroup();
        llllllllllllllllIlllIIIIIIIIlIIl.opacity = llllllllllllllllIlllIIIIIIIIlIIl.sgGeneral.add(new IntSetting.Builder().name("opacity").description("The opacity for rendered blocks.").defaultValue(1).min(1).max(255).sliderMax(255).onChanged(llllllllllllllllIllIllllllllIlIl -> {
            WallHack llllllllllllllllIllIllllllllIlII;
            if (llllllllllllllllIllIllllllllIlII.isActive()) {
                llllllllllllllllIllIllllllllIlII.mc.worldRenderer.reload();
            }
        }).build());
        llllllllllllllllIlllIIIIIIIIlIIl.blocks = llllllllllllllllIlllIIIIIIIIlIIl.sgGeneral.add(new BlockListSetting.Builder().name("blocks").description("What blocks should be targeted for Wall Hack.").defaultValue(new ArrayList<Block>()).onChanged(llllllllllllllllIllIlllllllllIIl -> {
            WallHack llllllllllllllllIllIlllllllllIlI;
            if (llllllllllllllllIllIlllllllllIlI.isActive()) {
                llllllllllllllllIllIlllllllllIlI.mc.worldRenderer.reload();
            }
        }).build());
        llllllllllllllllIlllIIIIIIIIlIIl.occludeChunks = llllllllllllllllIlllIIIIIIIIlIIl.sgGeneral.add(new BoolSetting.Builder().name("occlude-chunks").description("Whether caves should occlude underground (may look wonky when on).").defaultValue(false).build());
    }
}

