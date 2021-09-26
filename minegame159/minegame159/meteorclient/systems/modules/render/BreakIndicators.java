/*
 * Decompiled with CFR 0.151.
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
    private final Setting<SettingColor> gradientColor2Sides;
    private final Setting<SettingColor> gradientColor1Sides;
    private final Setting<ShapeMode> shapeMode;
    public final Map<Integer, BlockBreakingInfo> blocks;
    private final Setting<SettingColor> gradientColor2Lines;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> hideVanillaIndicators;
    private final Color cLines;
    private final SettingGroup sgRender;
    private final Setting<Boolean> smoothAnim;
    public final Setting<Boolean> multiple;
    private final Color cSides;
    private final Setting<SettingColor> gradientColor1Lines;

    public BreakIndicators() {
        super(Categories.Render, "break-indicators", "Renders the progress of a block being broken.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.multiple = this.sgGeneral.add(new BoolSetting.Builder().name("multiple").description("Renders block breaking from other players.").defaultValue(true).build());
        this.hideVanillaIndicators = this.sgGeneral.add(new BoolSetting.Builder().name("hide-vanilla-indicators").description("Hides the vanilla (or resource pack) break indicators.").defaultValue(true).build());
        this.smoothAnim = this.sgGeneral.add(new BoolSetting.Builder().name("smooth-animation").description("Renders a smooth animation at block you break.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.gradientColor1Sides = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-sides").description("The side color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 25)).build());
        this.gradientColor1Lines = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-lines").description("The line color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 100)).build());
        this.gradientColor2Sides = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-sides").description("The side color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        this.gradientColor2Lines = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-lines").description("The line color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        this.blocks = new HashMap<Integer, BlockBreakingInfo>();
        this.cSides = new Color();
        this.cLines = new Color();
    }

    private static boolean lambda$onRender$0(BlockPos BlockPos2, BlockBreakingInfo BlockBreakingInfo2) {
        return BlockBreakingInfo2.getPos().equals((Object)BlockPos2);
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        boolean bl;
        ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor;
        if (this.smoothAnim.get().booleanValue()) {
            clientPlayerInteractionManagerAccessor = (ClientPlayerInteractionManagerAccessor)this.mc.interactionManager;
            BlockPos BlockPos2 = clientPlayerInteractionManagerAccessor.getCurrentBreakingBlockPos();
            boolean bl2 = bl = BlockPos2 != null && clientPlayerInteractionManagerAccessor.getBreakingProgress() > 0.0f;
            if (bl && this.blocks.values().stream().noneMatch(arg_0 -> BreakIndicators.lambda$onRender$0(BlockPos2, arg_0))) {
                this.blocks.put(this.mc.player.getEntityId(), new BlockBreakingInfo(this.mc.player.getEntityId(), BlockPos2));
            }
        } else {
            clientPlayerInteractionManagerAccessor = null;
            bl = false;
        }
        this.blocks.values().forEach(arg_0 -> this.lambda$onRender$1(bl, clientPlayerInteractionManagerAccessor, arg_0));
    }

    private void lambda$onRender$1(boolean bl, ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor, BlockBreakingInfo BlockBreakingInfo2) {
        Box Box3;
        BlockPos BlockPos2 = BlockBreakingInfo2.getPos();
        int n = BlockBreakingInfo2.getStage();
        BlockState BlockState2 = this.mc.world.getBlockState(BlockPos2);
        VoxelShape VoxelShape2 = BlockState2.getOutlineShape((BlockView)this.mc.world, BlockPos2);
        if (VoxelShape2.isEmpty()) {
            return;
        }
        Box Box4 = Box3 = VoxelShape2.getBoundingBox();
        double d = bl && clientPlayerInteractionManagerAccessor.getCurrentBreakingBlockPos().equals((Object)BlockPos2) ? 1.0 - (double)clientPlayerInteractionManagerAccessor.getBreakingProgress() : (double)(9 - (n + 1)) / 9.0;
        double d2 = 1.0 - d;
        Box4 = Box4.shrink(Box4.getXLength() * d, Box4.getYLength() * d, Box4.getZLength() * d);
        double d3 = Box3.getXLength() * d / 2.0;
        double d4 = Box3.getYLength() * d / 2.0;
        double d5 = Box3.getZLength() * d / 2.0;
        double d6 = (double)BlockPos2.getX() + Box4.minX + d3;
        double d7 = (double)BlockPos2.getY() + Box4.minY + d4;
        double d8 = (double)BlockPos2.getZ() + Box4.minZ + d5;
        double d9 = (double)BlockPos2.getX() + Box4.maxX + d3;
        double d10 = (double)BlockPos2.getY() + Box4.maxY + d4;
        double d11 = (double)BlockPos2.getZ() + Box4.maxZ + d5;
        Color color = this.gradientColor1Sides.get();
        Color color2 = this.gradientColor2Sides.get();
        this.cSides.set((int)Math.round((double)color.r + (double)(color2.r - color.r) * d2), (int)Math.round((double)color.g + (double)(color2.g - color.g) * d2), (int)Math.round((double)color.b + (double)(color2.b - color.b) * d2), (int)Math.round((double)color.a + (double)(color2.a - color.a) * d2));
        Color color3 = this.gradientColor1Lines.get();
        Color color4 = this.gradientColor2Lines.get();
        this.cLines.set((int)Math.round((double)color3.r + (double)(color4.r - color3.r) * d2), (int)Math.round((double)color3.g + (double)(color4.g - color3.g) * d2), (int)Math.round((double)color3.b + (double)(color4.b - color3.b) * d2), (int)Math.round((double)color3.a + (double)(color4.a - color3.a) * d2));
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d6, d7, d8, d9, d10, d11, this.cSides, this.cLines, this.shapeMode.get(), 0);
    }

    @Override
    public void onDeactivate() {
        this.blocks.clear();
    }
}

