/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.BlockView
 *  net.minecraft.world.LightType
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.client.render.VertexFormats
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockIterator;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.client.render.VertexFormats;

public class LightOverlay
extends Module {
    private final /* synthetic */ MeshBuilder mb;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Pool<Cross> crossPool;
    private final /* synthetic */ SettingGroup sgColors;
    private final /* synthetic */ Setting<Integer> horizontalRange;
    private final /* synthetic */ Setting<Boolean> seeThroughBlocks;
    private final /* synthetic */ Setting<SettingColor> color;
    private final /* synthetic */ Setting<SettingColor> potentialColor;
    private final /* synthetic */ Setting<Integer> verticalRange;
    private final /* synthetic */ List<Cross> crosses;
    private final /* synthetic */ Mutable bp;

    public LightOverlay() {
        super(Categories.Render, "light-overlay", "Shows blocks where mobs can spawn.");
        LightOverlay lllllllllllllllllIIllIlIIIIlIIII;
        lllllllllllllllllIIllIlIIIIlIIII.sgGeneral = lllllllllllllllllIIllIlIIIIlIIII.settings.getDefaultGroup();
        lllllllllllllllllIIllIlIIIIlIIII.sgColors = lllllllllllllllllIIllIlIIIIlIIII.settings.createGroup("Colors");
        lllllllllllllllllIIllIlIIIIlIIII.horizontalRange = lllllllllllllllllIIllIlIIIIlIIII.sgGeneral.add(new IntSetting.Builder().name("horizontal-range").description("Horizontal range in blocks.").defaultValue(8).min(0).build());
        lllllllllllllllllIIllIlIIIIlIIII.verticalRange = lllllllllllllllllIIllIlIIIIlIIII.sgGeneral.add(new IntSetting.Builder().name("vertical-range").description("Vertical range in blocks.").defaultValue(4).min(0).build());
        lllllllllllllllllIIllIlIIIIlIIII.seeThroughBlocks = lllllllllllllllllIIllIlIIIIlIIII.sgGeneral.add(new BoolSetting.Builder().name("see-through-blocks").description("Allows you to see the lines through blocks.").defaultValue(false).build());
        lllllllllllllllllIIllIlIIIIlIIII.color = lllllllllllllllllIIllIlIIIIlIIII.sgColors.add(new ColorSetting.Builder().name("color").description("Color of places where mobs can currently spawn.").defaultValue(new SettingColor(225, 25, 25)).build());
        lllllllllllllllllIIllIlIIIIlIIII.potentialColor = lllllllllllllllllIIllIlIIIIlIIII.sgColors.add(new ColorSetting.Builder().name("potential-color").description("Color of places where mobs can potentially spawn (eg at night).").defaultValue(new SettingColor(225, 225, 25)).build());
        lllllllllllllllllIIllIlIIIIlIIII.crossPool = new Pool<Cross>(() -> {
            LightOverlay lllllllllllllllllIIllIIllllIllll;
            return lllllllllllllllllIIllIIllllIllll.new Cross();
        });
        lllllllllllllllllIIllIlIIIIlIIII.crosses = new ArrayList<Cross>();
        lllllllllllllllllIIllIlIIIIlIIII.bp = new Mutable();
        lllllllllllllllllIIllIlIIIIlIIII.mb = new MeshBuilder();
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIIllIIlllllllll) {
        LightOverlay lllllllllllllllllIIllIIllllllllI;
        if (lllllllllllllllllIIllIIllllllllI.crosses.isEmpty()) {
            return;
        }
        lllllllllllllllllIIllIIllllllllI.mb.depthTest = lllllllllllllllllIIllIIllllllllI.seeThroughBlocks.get() == false;
        lllllllllllllllllIIllIIllllllllI.mb.begin(lllllllllllllllllIIllIIlllllllll, DrawMode.Lines, VertexFormats.POSITION_COLOR);
        for (Cross lllllllllllllllllIIllIlIIIIIIIIl : lllllllllllllllllIIllIIllllllllI.crosses) {
            lllllllllllllllllIIllIlIIIIIIIIl.render();
        }
        lllllllllllllllllIIllIIllllllllI.mb.end();
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIIllIlIIIIIlIIl) {
        LightOverlay lllllllllllllllllIIllIlIIIIIlIII;
        for (Cross lllllllllllllllllIIllIlIIIIIlIll : lllllllllllllllllIIllIlIIIIIlIII.crosses) {
            lllllllllllllllllIIllIlIIIIIlIII.crossPool.free(lllllllllllllllllIIllIlIIIIIlIll);
        }
        lllllllllllllllllIIllIlIIIIIlIII.crosses.clear();
        BlockIterator.register(lllllllllllllllllIIllIlIIIIIlIII.horizontalRange.get(), lllllllllllllllllIIllIlIIIIIlIII.verticalRange.get(), (lllllllllllllllllIIllIIlllllIllI, lllllllllllllllllIIllIIlllllIlIl) -> {
            LightOverlay lllllllllllllllllIIllIIlllllIlII;
            if (!lllllllllllllllllIIllIIlllllIlIl.getCollisionShape((BlockView)lllllllllllllllllIIllIIlllllIlII.mc.world, lllllllllllllllllIIllIIlllllIllI).isEmpty()) {
                return;
            }
            lllllllllllllllllIIllIIlllllIlII.bp.set((Vec3i)lllllllllllllllllIIllIIlllllIllI).move(0, -1, 0);
            if (lllllllllllllllllIIllIIlllllIlII.mc.world.getBlockState((BlockPos)lllllllllllllllllIIllIIlllllIlII.bp).getCollisionShape((BlockView)lllllllllllllllllIIllIIlllllIlII.mc.world, (BlockPos)lllllllllllllllllIIllIIlllllIlII.bp) != VoxelShapes.fullCube()) {
                return;
            }
            if (lllllllllllllllllIIllIIlllllIlII.mc.world.getLightLevel(lllllllllllllllllIIllIIlllllIllI, 0) <= 7) {
                lllllllllllllllllIIllIIlllllIlII.crosses.add(lllllllllllllllllIIllIIlllllIlII.crossPool.get().set((BlockPos)lllllllllllllllllIIllIIlllllIllI, false));
            } else if (lllllllllllllllllIIllIIlllllIlII.mc.world.getLightLevel(LightType.BLOCK, lllllllllllllllllIIllIIlllllIllI) <= 7) {
                lllllllllllllllllIIllIIlllllIlII.crosses.add(lllllllllllllllllIIllIIlllllIlII.crossPool.get().set((BlockPos)lllllllllllllllllIIllIIlllllIllI, true));
            }
        });
    }

    private class Cross {
        private /* synthetic */ boolean potential;
        private /* synthetic */ double y;
        private /* synthetic */ double x;
        private /* synthetic */ double z;

        private Cross() {
            Cross llllllllllllllllIlIllIIllIllllII;
        }

        public Cross set(BlockPos llllllllllllllllIlIllIIllIllIlIl, boolean llllllllllllllllIlIllIIllIllIlII) {
            Cross llllllllllllllllIlIllIIllIllIIll;
            llllllllllllllllIlIllIIllIllIIll.x = llllllllllllllllIlIllIIllIllIlIl.getX();
            llllllllllllllllIlIllIIllIllIIll.y = (double)llllllllllllllllIlIllIIllIllIlIl.getY() + 0.0075;
            llllllllllllllllIlIllIIllIllIIll.z = llllllllllllllllIlIllIIllIllIlIl.getZ();
            llllllllllllllllIlIllIIllIllIIll.potential = llllllllllllllllIlIllIIllIllIlII;
            return llllllllllllllllIlIllIIllIllIIll;
        }

        public void render() {
            Cross llllllllllllllllIlIllIIllIlIlllI;
            Color llllllllllllllllIlIllIIllIlIllIl = llllllllllllllllIlIllIIllIlIlllI.potential ? (Color)llllllllllllllllIlIllIIllIlIlllI.LightOverlay.this.potentialColor.get() : (Color)llllllllllllllllIlIllIIllIlIlllI.LightOverlay.this.color.get();
            llllllllllllllllIlIllIIllIlIlllI.LightOverlay.this.mb.line(llllllllllllllllIlIllIIllIlIlllI.x, llllllllllllllllIlIllIIllIlIlllI.y, llllllllllllllllIlIllIIllIlIlllI.z, llllllllllllllllIlIllIIllIlIlllI.x + 1.0, llllllllllllllllIlIllIIllIlIlllI.y, llllllllllllllllIlIllIIllIlIlllI.z + 1.0, llllllllllllllllIlIllIIllIlIllIl);
            llllllllllllllllIlIllIIllIlIlllI.LightOverlay.this.mb.line(llllllllllllllllIlIllIIllIlIlllI.x + 1.0, llllllllllllllllIlIllIIllIlIlllI.y, llllllllllllllllIlIllIIllIlIlllI.z, llllllllllllllllIlIllIIllIlIlllI.x, llllllllllllllllIlIllIIllIlIlllI.y, llllllllllllllllIlIllIIllIlIlllI.z + 1.0, llllllllllllllllIlIllIIllIlIllIl);
        }
    }
}

