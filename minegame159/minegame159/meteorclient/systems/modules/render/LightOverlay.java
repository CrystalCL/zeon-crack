/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexFormats;

public class LightOverlay
extends Module {
    private final Setting<SettingColor> color;
    private final Pool<Cross> crossPool;
    private final MeshBuilder mb;
    private final Setting<Boolean> seeThroughBlocks;
    private final SettingGroup sgColors;
    private final List<Cross> crosses;
    private final Mutable bp;
    private final Setting<SettingColor> potentialColor;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> verticalRange;
    private final Setting<Integer> horizontalRange;

    static Setting access$000(LightOverlay lightOverlay) {
        return lightOverlay.potentialColor;
    }

    private void lambda$onTick$1(BlockPos BlockPos2, BlockState BlockState2) {
        if (!BlockState2.getCollisionShape((BlockView)this.mc.world, BlockPos2).isEmpty()) {
            return;
        }
        this.bp.set((Vec3i)BlockPos2).move(0, -1, 0);
        if (this.mc.world.getBlockState((BlockPos)this.bp).getCollisionShape((BlockView)this.mc.world, (BlockPos)this.bp) != VoxelShapes.fullCube()) {
            return;
        }
        if (this.mc.world.getLightLevel(BlockPos2, 0) <= 7) {
            this.crosses.add(this.crossPool.get().set(BlockPos2, false));
        } else if (this.mc.world.getLightLevel(LightType.BLOCK, BlockPos2) <= 7) {
            this.crosses.add(this.crossPool.get().set(BlockPos2, true));
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.crosses.isEmpty()) {
            return;
        }
        this.mb.depthTest = this.seeThroughBlocks.get() == false;
        this.mb.begin(renderEvent, DrawMode.Lines, VertexFormats.POSITION_COLOR);
        for (Cross cross : this.crosses) {
            cross.render();
        }
        this.mb.end();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        for (Cross cross : this.crosses) {
            this.crossPool.free(cross);
        }
        this.crosses.clear();
        BlockIterator.register(this.horizontalRange.get(), this.verticalRange.get(), this::lambda$onTick$1);
    }

    static MeshBuilder access$200(LightOverlay lightOverlay) {
        return lightOverlay.mb;
    }

    private Cross lambda$new$0() {
        return new Cross(this, null);
    }

    static Setting access$100(LightOverlay lightOverlay) {
        return lightOverlay.color;
    }

    public LightOverlay() {
        super(Categories.Render, "light-overlay", "Shows blocks where mobs can spawn.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgColors = this.settings.createGroup("Colors");
        this.horizontalRange = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-range").description("Horizontal range in blocks.").defaultValue(8).min(0).build());
        this.verticalRange = this.sgGeneral.add(new IntSetting.Builder().name("vertical-range").description("Vertical range in blocks.").defaultValue(4).min(0).build());
        this.seeThroughBlocks = this.sgGeneral.add(new BoolSetting.Builder().name("see-through-blocks").description("Allows you to see the lines through blocks.").defaultValue(false).build());
        this.color = this.sgColors.add(new ColorSetting.Builder().name("color").description("Color of places where mobs can currently spawn.").defaultValue(new SettingColor(225, 25, 25)).build());
        this.potentialColor = this.sgColors.add(new ColorSetting.Builder().name("potential-color").description("Color of places where mobs can potentially spawn (eg at night).").defaultValue(new SettingColor(225, 225, 25)).build());
        this.crossPool = new Pool<Cross>(this::lambda$new$0);
        this.crosses = new ArrayList<Cross>();
        this.bp = new Mutable();
        this.mb = new MeshBuilder();
    }

    private class Cross {
        private double y;
        private boolean potential;
        final LightOverlay this$0;
        private double z;
        private double x;

        public Cross set(BlockPos BlockPos2, boolean bl) {
            this.x = BlockPos2.getX();
            this.y = (double)BlockPos2.getY() + 0.0075;
            this.z = BlockPos2.getZ();
            this.potential = bl;
            return this;
        }

        private Cross(LightOverlay lightOverlay) {
            this.this$0 = lightOverlay;
        }

        public void render() {
            Color color = this.potential ? (Color)LightOverlay.access$000(this.this$0).get() : (Color)LightOverlay.access$100(this.this$0).get();
            LightOverlay.access$200(this.this$0).line(this.x, this.y, this.z, this.x + 1.0, this.y, this.z + 1.0, color);
            LightOverlay.access$200(this.this$0).line(this.x + 1.0, this.y, this.z, this.x, this.y, this.z + 1.0, color);
        }

        Cross(LightOverlay lightOverlay, 1 var2_2) {
            this(lightOverlay);
        }
    }
}

