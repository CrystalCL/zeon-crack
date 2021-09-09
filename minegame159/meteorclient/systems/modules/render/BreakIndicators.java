/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.render.BlockBreakingInfo
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.mixin.ClientPlayerInteractionManagerAccessor;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BlockBreakingInfo;

public class BreakIndicators
extends Module {
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Color cSides;
    private final /* synthetic */ Color cLines;
    private final /* synthetic */ Setting<SettingColor> gradientColor1Lines;
    public final /* synthetic */ Setting<Boolean> multiple;
    private final /* synthetic */ Setting<SettingColor> gradientColor1Sides;
    private final /* synthetic */ Setting<SettingColor> gradientColor2Sides;
    private final /* synthetic */ Setting<SettingColor> gradientColor2Lines;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    public final /* synthetic */ Map<Integer, BlockBreakingInfo> blocks;
    public final /* synthetic */ Setting<Boolean> hideVanillaIndicators;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> smoothAnim;

    public BreakIndicators() {
        super(Categories.Render, "break-indicators", "Renders the progress of a block being broken.");
        BreakIndicators llllllllllllllllllIlIIIlllIlIIII;
        llllllllllllllllllIlIIIlllIlIIII.sgGeneral = llllllllllllllllllIlIIIlllIlIIII.settings.getDefaultGroup();
        llllllllllllllllllIlIIIlllIlIIII.sgRender = llllllllllllllllllIlIIIlllIlIIII.settings.createGroup("Render");
        llllllllllllllllllIlIIIlllIlIIII.multiple = llllllllllllllllllIlIIIlllIlIIII.sgGeneral.add(new BoolSetting.Builder().name("multiple").description("Renders block breaking from other players.").defaultValue(true).build());
        llllllllllllllllllIlIIIlllIlIIII.hideVanillaIndicators = llllllllllllllllllIlIIIlllIlIIII.sgGeneral.add(new BoolSetting.Builder().name("hide-vanilla-indicators").description("Hides the vanilla (or resource pack) break indicators.").defaultValue(true).build());
        llllllllllllllllllIlIIIlllIlIIII.smoothAnim = llllllllllllllllllIlIIIlllIlIIII.sgGeneral.add(new BoolSetting.Builder().name("smooth-animation").description("Renders a smooth animation at block you break.").defaultValue(true).build());
        llllllllllllllllllIlIIIlllIlIIII.shapeMode = llllllllllllllllllIlIIIlllIlIIII.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        llllllllllllllllllIlIIIlllIlIIII.gradientColor1Sides = llllllllllllllllllIlIIIlllIlIIII.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-sides").description("The side color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 25)).build());
        llllllllllllllllllIlIIIlllIlIIII.gradientColor1Lines = llllllllllllllllllIlIIIlllIlIIII.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-lines").description("The line color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 100)).build());
        llllllllllllllllllIlIIIlllIlIIII.gradientColor2Sides = llllllllllllllllllIlIIIlllIlIIII.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-sides").description("The side color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        llllllllllllllllllIlIIIlllIlIIII.gradientColor2Lines = llllllllllllllllllIlIIIlllIlIIII.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-lines").description("The line color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        llllllllllllllllllIlIIIlllIlIIII.blocks = new HashMap<Integer, BlockBreakingInfo>();
        llllllllllllllllllIlIIIlllIlIIII.cSides = new Color();
        llllllllllllllllllIlIIIlllIlIIII.cLines = new Color();
    }

    @EventHandler
    private void onRender(RenderEvent llllllllllllllllllIlIIIlllIIIlII) {
        boolean llllllllllllllllllIlIIIlllIIIIlI;
        ClientPlayerInteractionManagerAccessor llllllllllllllllllIlIIIlllIIIIll;
        BreakIndicators llllllllllllllllllIlIIIlllIIIIIl;
        if (llllllllllllllllllIlIIIlllIIIIIl.smoothAnim.get().booleanValue()) {
            boolean llllllllllllllllllIlIIIlllIIIllI;
            ClientPlayerInteractionManagerAccessor llllllllllllllllllIlIIIlllIIIlll = (ClientPlayerInteractionManagerAccessor)llllllllllllllllllIlIIIlllIIIIIl.mc.interactionManager;
            BlockPos llllllllllllllllllIlIIIlllIIlIII = llllllllllllllllllIlIIIlllIIIlll.getCurrentBreakingBlockPos();
            boolean bl = llllllllllllllllllIlIIIlllIIIllI = llllllllllllllllllIlIIIlllIIlIII != null && llllllllllllllllllIlIIIlllIIIlll.getBreakingProgress() > 0.0f;
            if (llllllllllllllllllIlIIIlllIIIllI && llllllllllllllllllIlIIIlllIIIIIl.blocks.values().stream().noneMatch(llllllllllllllllllIlIIIlIllIlllI -> llllllllllllllllllIlIIIlIllIlllI.getPos().equals((Object)llllllllllllllllllIlIIIlllIIlIII))) {
                llllllllllllllllllIlIIIlllIIIIIl.blocks.put(llllllllllllllllllIlIIIlllIIIIIl.mc.player.getEntityId(), new BlockBreakingInfo(llllllllllllllllllIlIIIlllIIIIIl.mc.player.getEntityId(), llllllllllllllllllIlIIIlllIIlIII));
            }
        } else {
            llllllllllllllllllIlIIIlllIIIIll = null;
            llllllllllllllllllIlIIIlllIIIIlI = false;
        }
        llllllllllllllllllIlIIIlllIIIIIl.blocks.values().forEach(llllllllllllllllllIlIIIllIIIIlll -> {
            double llllllllllllllllllIlIIIllIIllIIl;
            Box llllllllllllllllllIlIIIllIIllIll;
            BreakIndicators llllllllllllllllllIlIIIllIIIlIlI;
            BlockPos llllllllllllllllllIlIIIllIIlllll = llllllllllllllllllIlIIIllIIIIlll.getPos();
            int llllllllllllllllllIlIIIllIIllllI = llllllllllllllllllIlIIIllIIIIlll.getStage();
            BlockState llllllllllllllllllIlIIIllIIlllIl = llllllllllllllllllIlIIIllIIIlIlI.mc.world.getBlockState(llllllllllllllllllIlIIIllIIlllll);
            VoxelShape llllllllllllllllllIlIIIllIIlllII = llllllllllllllllllIlIIIllIIlllIl.getOutlineShape((BlockView)llllllllllllllllllIlIIIllIIIlIlI.mc.world, llllllllllllllllllIlIIIllIIlllll);
            if (llllllllllllllllllIlIIIllIIlllII.isEmpty()) {
                return;
            }
            Box llllllllllllllllllIlIIIllIIllIlI = llllllllllllllllllIlIIIllIIllIll = llllllllllllllllllIlIIIllIIlllII.getBoundingBox();
            if (llllllllllllllllllIlIIIlllIIIIlI && llllllllllllllllllIlIIIlllIIIIll.getCurrentBreakingBlockPos().equals((Object)llllllllllllllllllIlIIIllIIlllll)) {
                double llllllllllllllllllIlIIIllIlIIlII = 1.0 - (double)llllllllllllllllllIlIIIlllIIIIll.getBreakingProgress();
            } else {
                llllllllllllllllllIlIIIllIIllIIl = (double)(9 - (llllllllllllllllllIlIIIllIIllllI + 1)) / 9.0;
            }
            double llllllllllllllllllIlIIIllIIllIII = 1.0 - llllllllllllllllllIlIIIllIIllIIl;
            llllllllllllllllllIlIIIllIIllIlI = llllllllllllllllllIlIIIllIIllIlI.shrink(llllllllllllllllllIlIIIllIIllIlI.getXLength() * llllllllllllllllllIlIIIllIIllIIl, llllllllllllllllllIlIIIllIIllIlI.getYLength() * llllllllllllllllllIlIIIllIIllIIl, llllllllllllllllllIlIIIllIIllIlI.getZLength() * llllllllllllllllllIlIIIllIIllIIl);
            double llllllllllllllllllIlIIIllIIlIlll = llllllllllllllllllIlIIIllIIllIll.getXLength() * llllllllllllllllllIlIIIllIIllIIl / 2.0;
            double llllllllllllllllllIlIIIllIIlIllI = llllllllllllllllllIlIIIllIIllIll.getYLength() * llllllllllllllllllIlIIIllIIllIIl / 2.0;
            double llllllllllllllllllIlIIIllIIlIlIl = llllllllllllllllllIlIIIllIIllIll.getZLength() * llllllllllllllllllIlIIIllIIllIIl / 2.0;
            double llllllllllllllllllIlIIIllIIlIlII = (double)llllllllllllllllllIlIIIllIIlllll.getX() + llllllllllllllllllIlIIIllIIllIlI.minX + llllllllllllllllllIlIIIllIIlIlll;
            double llllllllllllllllllIlIIIllIIlIIll = (double)llllllllllllllllllIlIIIllIIlllll.getY() + llllllllllllllllllIlIIIllIIllIlI.minY + llllllllllllllllllIlIIIllIIlIllI;
            double llllllllllllllllllIlIIIllIIlIIlI = (double)llllllllllllllllllIlIIIllIIlllll.getZ() + llllllllllllllllllIlIIIllIIllIlI.minZ + llllllllllllllllllIlIIIllIIlIlIl;
            double llllllllllllllllllIlIIIllIIlIIIl = (double)llllllllllllllllllIlIIIllIIlllll.getX() + llllllllllllllllllIlIIIllIIllIlI.maxX + llllllllllllllllllIlIIIllIIlIlll;
            double llllllllllllllllllIlIIIllIIlIIII = (double)llllllllllllllllllIlIIIllIIlllll.getY() + llllllllllllllllllIlIIIllIIllIlI.maxY + llllllllllllllllllIlIIIllIIlIllI;
            double llllllllllllllllllIlIIIllIIIllll = (double)llllllllllllllllllIlIIIllIIlllll.getZ() + llllllllllllllllllIlIIIllIIllIlI.maxZ + llllllllllllllllllIlIIIllIIlIlIl;
            Color llllllllllllllllllIlIIIllIIIlllI = llllllllllllllllllIlIIIllIIIlIlI.gradientColor1Sides.get();
            Color llllllllllllllllllIlIIIllIIIllIl = llllllllllllllllllIlIIIllIIIlIlI.gradientColor2Sides.get();
            llllllllllllllllllIlIIIllIIIlIlI.cSides.set((int)Math.round((double)llllllllllllllllllIlIIIllIIIlllI.r + (double)(llllllllllllllllllIlIIIllIIIllIl.r - llllllllllllllllllIlIIIllIIIlllI.r) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIlllI.g + (double)(llllllllllllllllllIlIIIllIIIllIl.g - llllllllllllllllllIlIIIllIIIlllI.g) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIlllI.b + (double)(llllllllllllllllllIlIIIllIIIllIl.b - llllllllllllllllllIlIIIllIIIlllI.b) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIlllI.a + (double)(llllllllllllllllllIlIIIllIIIllIl.a - llllllllllllllllllIlIIIllIIIlllI.a) * llllllllllllllllllIlIIIllIIllIII));
            Color llllllllllllllllllIlIIIllIIIllII = llllllllllllllllllIlIIIllIIIlIlI.gradientColor1Lines.get();
            Color llllllllllllllllllIlIIIllIIIlIll = llllllllllllllllllIlIIIllIIIlIlI.gradientColor2Lines.get();
            llllllllllllllllllIlIIIllIIIlIlI.cLines.set((int)Math.round((double)llllllllllllllllllIlIIIllIIIllII.r + (double)(llllllllllllllllllIlIIIllIIIlIll.r - llllllllllllllllllIlIIIllIIIllII.r) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIllII.g + (double)(llllllllllllllllllIlIIIllIIIlIll.g - llllllllllllllllllIlIIIllIIIllII.g) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIllII.b + (double)(llllllllllllllllllIlIIIllIIIlIll.b - llllllllllllllllllIlIIIllIIIllII.b) * llllllllllllllllllIlIIIllIIllIII), (int)Math.round((double)llllllllllllllllllIlIIIllIIIllII.a + (double)(llllllllllllllllllIlIIIllIIIlIll.a - llllllllllllllllllIlIIIllIIIllII.a) * llllllllllllllllllIlIIIllIIllIII));
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllllIlIIIllIIlIlII, llllllllllllllllllIlIIIllIIlIIll, llllllllllllllllllIlIIIllIIlIIlI, llllllllllllllllllIlIIIllIIlIIIl, llllllllllllllllllIlIIIllIIlIIII, llllllllllllllllllIlIIIllIIIllll, llllllllllllllllllIlIIIllIIIlIlI.cSides, llllllllllllllllllIlIIIllIIIlIlI.cLines, llllllllllllllllllIlIIIllIIIlIlI.shapeMode.get(), 0);
        });
    }

    @Override
    public void onDeactivate() {
        BreakIndicators llllllllllllllllllIlIIIlllIIlllI;
        llllllllllllllllllIlIIIlllIIlllI.blocks.clear();
    }
}

