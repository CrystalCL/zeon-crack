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
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.ShapeContext;

public class SelfTrap
extends Module {
    private final Setting<BottomMode> bottomPlacement;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;
    private final Setting<TopMode> topPlacement;
    private final List<BlockPos> placePositions;
    private final SettingGroup sgRender;
    private final Setting<Boolean> center;
    private final Setting<SettingColor> lineColor;
    private boolean placed;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;
    private final Setting<Boolean> turnOff;
    private int delay;
    private final Setting<Integer> delaySetting;

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
        this.findPlacePos();
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

    @Override
    public void onActivate() {
        if (!this.placePositions.isEmpty()) {
            this.placePositions.clear();
        }
        this.delay = 0;
        this.placed = false;
        if (this.center.get().booleanValue()) {
            PlayerUtils.centerPlayer();
        }
    }

    private void add(BlockPos BlockPos2) {
        if (!this.placePositions.contains(BlockPos2) && this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && this.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), BlockPos2, ShapeContext.absent())) {
            this.placePositions.add(BlockPos2);
        }
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

    private void findPlacePos() {
        this.placePositions.clear();
        BlockPos BlockPos2 = this.mc.player.getBlockPos();
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$SelfTrap$TopMode[this.topPlacement.get().ordinal()]) {
            case 1: {
                this.add(BlockPos2.add(0, 2, 0));
                this.add(BlockPos2.add(1, 1, 0));
                this.add(BlockPos2.add(-1, 1, 0));
                this.add(BlockPos2.add(0, 1, 1));
                this.add(BlockPos2.add(0, 1, -1));
                break;
            }
            case 2: {
                this.add(BlockPos2.add(0, 2, 0));
                break;
            }
            case 3: {
                this.add(BlockPos2.add(1, 1, 0));
                this.add(BlockPos2.add(-1, 1, 0));
                this.add(BlockPos2.add(0, 1, 1));
                this.add(BlockPos2.add(0, 1, -1));
            }
        }
        if (this.bottomPlacement.get() == BottomMode.Single) {
            this.add(BlockPos2.add(0, -1, 0));
        }
    }

    public SelfTrap() {
        super(Categories.Combat, "self-trap", "Places obsidian above your head.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.topPlacement = this.sgGeneral.add(new EnumSetting.Builder().name("top-mode").description("Which positions to place on your top half.").defaultValue(TopMode.AntiFacePlace).build());
        this.bottomPlacement = this.sgGeneral.add(new EnumSetting.Builder().name("bottom-mode").description("Which positions to place on your bottom half.").defaultValue(BottomMode.None).build());
        this.delaySetting = this.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
        this.center = this.sgGeneral.add(new BoolSetting.Builder().name("center").description("Centers you on the block you are standing on before placing.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(true).build());
        this.turnOff = this.sgGeneral.add(new BoolSetting.Builder().name("turn-off").description("Turns off after placing.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.placePositions = new ArrayList<BlockPos>();
    }

    public static final class BottomMode
    extends Enum<BottomMode> {
        public static final /* enum */ BottomMode Single = new BottomMode();
        private static final BottomMode[] $VALUES;
        public static final /* enum */ BottomMode None;

        public static BottomMode valueOf(String string) {
            return Enum.valueOf(BottomMode.class, string);
        }

        private static BottomMode[] $values() {
            return new BottomMode[]{Single, None};
        }

        public static BottomMode[] values() {
            return (BottomMode[])$VALUES.clone();
        }

        static {
            None = new BottomMode();
            $VALUES = BottomMode.$values();
        }
    }

    public static final class TopMode
    extends Enum<TopMode> {
        public static final /* enum */ TopMode AntiFacePlace = new TopMode();
        public static final /* enum */ TopMode None;
        private static final TopMode[] $VALUES;
        public static final /* enum */ TopMode Full;
        public static final /* enum */ TopMode Top;

        static {
            Full = new TopMode();
            Top = new TopMode();
            None = new TopMode();
            $VALUES = TopMode.$values();
        }

        public static TopMode valueOf(String string) {
            return Enum.valueOf(TopMode.class, string);
        }

        private static TopMode[] $values() {
            return new TopMode[]{AntiFacePlace, Full, Top, None};
        }

        public static TopMode[] values() {
            return (TopMode[])$VALUES.clone();
        }
    }
}

