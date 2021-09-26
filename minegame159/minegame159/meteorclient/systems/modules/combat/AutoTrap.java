/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
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
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.ShapeContext;

public class AutoTrap
extends Module {
    private int delay;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> render;
    private boolean placed;
    private PlayerEntity target;
    private final Setting<Boolean> turnOff;
    private final SettingGroup sgGeneral;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<BottomMode> bottomPlacement;
    private final Setting<TopMode> topPlacement;
    private final List<BlockPos> placePositions;
    private final Setting<SettingColor> lineColor;
    private final Setting<Integer> range;
    private final Setting<SettingColor> sideColor;
    private final SettingGroup sgRender;
    private final Setting<Integer> delaySetting;

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue() || this.placePositions.isEmpty()) {
            return;
        }
        for (BlockPos BlockPos2 : this.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, BlockPos2, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = InvUtils.findItemInHotbar(Blocks.OBSIDIAN.asItem());
        if (this.turnOff.get().booleanValue() && (this.placed && this.placePositions.isEmpty() || n == -1)) {
            this.sendToggledMsg();
            this.toggle();
            return;
        }
        if (n == -1) {
            this.placePositions.clear();
            return;
        }
        if (this.target == null || this.mc.player.distanceTo((Entity)this.target) > (float)this.range.get().intValue()) {
            this.placePositions.clear();
            this.target = this.findTarget();
            this.placed = false;
            return;
        }
        this.findPlacePos(this.target);
        if (this.delay >= this.delaySetting.get() && this.placePositions.size() > 0) {
            BlockPos BlockPos2 = this.placePositions.get(this.placePositions.size() - 1);
            if (BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 50, true)) {
                this.placePositions.remove(BlockPos2);
                this.placed = true;
            }
            this.delay = 0;
        } else {
            ++this.delay;
        }
    }

    private void add(BlockPos BlockPos2) {
        if (!this.placePositions.contains(BlockPos2) && this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && this.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), BlockPos2, ShapeContext.absent())) {
            this.placePositions.add(BlockPos2);
        }
    }

    public AutoTrap() {
        super(Categories.Combat, "auto-trap", "Traps people in an obsidian box to prevent them from moving.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.topPlacement = this.sgGeneral.add(new EnumSetting.Builder().name("top-mode").description("Which blocks to place on the top half of the target.").defaultValue(TopMode.Full).build());
        this.bottomPlacement = this.sgGeneral.add(new EnumSetting.Builder().name("bottom-mode").description("Which blocks to place on the bottom half of the target.").defaultValue(BottomMode.Platform).build());
        this.range = this.sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        this.delaySetting = this.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
        this.turnOff = this.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Turns off after placing.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.placePositions = new ArrayList<BlockPos>();
    }

    private void findPlacePos(PlayerEntity PlayerEntity2) {
        this.placePositions.clear();
        BlockPos BlockPos2 = PlayerEntity2.getBlockPos();
        switch (this.topPlacement.get()) {
            case Full: {
                this.add(BlockPos2.add(0, 2, 0));
                this.add(BlockPos2.add(1, 1, 0));
                this.add(BlockPos2.add(-1, 1, 0));
                this.add(BlockPos2.add(0, 1, 1));
                this.add(BlockPos2.add(0, 1, -1));
                break;
            }
            case Face: {
                this.add(BlockPos2.add(1, 1, 0));
                this.add(BlockPos2.add(-1, 1, 0));
                this.add(BlockPos2.add(0, 1, 1));
                this.add(BlockPos2.add(0, 1, -1));
                break;
            }
            case Top: {
                this.add(BlockPos2.add(0, 2, 0));
            }
        }
        switch (this.bottomPlacement.get()) {
            case Platform: {
                this.add(BlockPos2.add(0, -1, 0));
                this.add(BlockPos2.add(1, -1, 0));
                this.add(BlockPos2.add(0, -1, 0));
                this.add(BlockPos2.add(0, -1, 1));
                this.add(BlockPos2.add(0, -1, -1));
                break;
            }
            case Full: {
                this.add(BlockPos2.add(1, 0, 0));
                this.add(BlockPos2.add(-1, 0, 0));
                this.add(BlockPos2.add(0, 0, -1));
                this.add(BlockPos2.add(0, 0, 1));
                break;
            }
            case Single: {
                this.add(BlockPos2.add(0, -1, 0));
            }
        }
    }

    private PlayerEntity findTarget() {
        for (PlayerEntity object : this.mc.world.getPlayers()) {
            if (object == this.mc.player || !Friends.get().attack(object) || !object.isAlive()) continue;
            if (this.target == null) {
                this.target = object;
                continue;
            }
            if (!(this.mc.player.distanceTo((Entity)object) < this.mc.player.distanceTo((Entity)this.target))) continue;
            this.target = object;
        }
        for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
            if (!Friends.get().attack((PlayerEntity)fakePlayerEntity) || !fakePlayerEntity.isAlive()) continue;
            if (this.target == null) {
                this.target = fakePlayerEntity;
                continue;
            }
            if (!(this.mc.player.distanceTo((Entity)fakePlayerEntity) < this.mc.player.distanceTo((Entity)this.target))) continue;
            this.target = fakePlayerEntity;
        }
        return this.target;
    }

    @Override
    public void onActivate() {
        this.target = null;
        if (!this.placePositions.isEmpty()) {
            this.placePositions.clear();
        }
        this.delay = 0;
        this.placed = false;
    }

    public static final class TopMode
    extends Enum<TopMode> {
        public static final /* enum */ TopMode Full = new TopMode();
        public static final /* enum */ TopMode Top = new TopMode();
        public static final /* enum */ TopMode Face = new TopMode();
        public static final /* enum */ TopMode None = new TopMode();
        private static final TopMode[] $VALUES = TopMode.$values();

        public static TopMode valueOf(String string) {
            return Enum.valueOf(TopMode.class, string);
        }

        private static TopMode[] $values() {
            return new TopMode[]{Full, Top, Face, None};
        }

        public static TopMode[] values() {
            return (TopMode[])$VALUES.clone();
        }
    }

    public static final class BottomMode
    extends Enum<BottomMode> {
        private static final BottomMode[] $VALUES;
        public static final /* enum */ BottomMode None;
        public static final /* enum */ BottomMode Platform;
        public static final /* enum */ BottomMode Single;
        public static final /* enum */ BottomMode Full;

        private static BottomMode[] $values() {
            return new BottomMode[]{Single, Platform, Full, None};
        }

        static {
            Single = new BottomMode();
            Platform = new BottomMode();
            Full = new BottomMode();
            None = new BottomMode();
            $VALUES = BottomMode.$values();
        }

        public static BottomMode valueOf(String string) {
            return Enum.valueOf(BottomMode.class, string);
        }

        public static BottomMode[] values() {
            return (BottomMode[])$VALUES.clone();
        }
    }
}

