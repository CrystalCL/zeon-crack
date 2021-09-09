/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.block.ShapeContext
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
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private /* synthetic */ boolean placed;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> center;
    private final /* synthetic */ Setting<TopMode> topPlacement;
    private /* synthetic */ int delay;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Integer> delaySetting;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ List<BlockPos> placePositions;
    private final /* synthetic */ Setting<Boolean> turnOff;
    private final /* synthetic */ Setting<BottomMode> bottomPlacement;
    private final /* synthetic */ Setting<SettingColor> lineColor;

    public SelfTrap() {
        super(Categories.Combat, "self-trap", "Places obsidian above your head.");
        SelfTrap lllllllllllllllllllIllIllIIIlIlI;
        lllllllllllllllllllIllIllIIIlIlI.sgGeneral = lllllllllllllllllllIllIllIIIlIlI.settings.getDefaultGroup();
        lllllllllllllllllllIllIllIIIlIlI.sgRender = lllllllllllllllllllIllIllIIIlIlI.settings.createGroup("Render");
        lllllllllllllllllllIllIllIIIlIlI.topPlacement = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("top-mode").description("Which positions to place on your top half.").defaultValue(TopMode.AntiFacePlace).build());
        lllllllllllllllllllIllIllIIIlIlI.bottomPlacement = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new EnumSetting.Builder().name("bottom-mode").description("Which positions to place on your bottom half.").defaultValue(BottomMode.None).build());
        lllllllllllllllllllIllIllIIIlIlI.delaySetting = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
        lllllllllllllllllllIllIllIIIlIlI.center = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("center").description("Centers you on the block you are standing on before placing.").defaultValue(true).build());
        lllllllllllllllllllIllIllIIIlIlI.rotate = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(true).build());
        lllllllllllllllllllIllIllIIIlIlI.turnOff = lllllllllllllllllllIllIllIIIlIlI.sgGeneral.add(new BoolSetting.Builder().name("turn-off").description("Turns off after placing.").defaultValue(true).build());
        lllllllllllllllllllIllIllIIIlIlI.render = lllllllllllllllllllIllIllIIIlIlI.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lllllllllllllllllllIllIllIIIlIlI.shapeMode = lllllllllllllllllllIllIllIIIlIlI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllllIllIllIIIlIlI.sideColor = lllllllllllllllllllIllIllIIIlIlI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lllllllllllllllllllIllIllIIIlIlI.lineColor = lllllllllllllllllllIllIllIIIlIlI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lllllllllllllllllllIllIllIIIlIlI.placePositions = new ArrayList<BlockPos>();
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllllIllIlIlllIlll) {
        SelfTrap lllllllllllllllllllIllIlIlllIllI;
        if (!lllllllllllllllllllIllIlIlllIllI.render.get().booleanValue() || lllllllllllllllllllIllIlIlllIllI.placePositions.isEmpty()) {
            return;
        }
        for (BlockPos lllllllllllllllllllIllIlIllllIIl : lllllllllllllllllllIllIlIlllIllI.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllllIllIlIllllIIl, lllllllllllllllllllIllIlIlllIllI.sideColor.get(), lllllllllllllllllllIllIlIlllIllI.lineColor.get(), lllllllllllllllllllIllIlIlllIllI.shapeMode.get(), 0);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIllIllIIIIIIl) {
        SelfTrap lllllllllllllllllllIllIlIlllllll;
        int lllllllllllllllllllIllIllIIIIIII = InvUtils.findItemInHotbar(Blocks.OBSIDIAN.asItem());
        if (lllllllllllllllllllIllIlIlllllll.turnOff.get().booleanValue() && (lllllllllllllllllllIllIlIlllllll.placed && lllllllllllllllllllIllIlIlllllll.placePositions.isEmpty() || lllllllllllllllllllIllIllIIIIIII == -1)) {
            lllllllllllllllllllIllIlIlllllll.sendToggledMsg();
            lllllllllllllllllllIllIlIlllllll.toggle();
            return;
        }
        if (lllllllllllllllllllIllIllIIIIIII == -1) {
            lllllllllllllllllllIllIlIlllllll.placePositions.clear();
            return;
        }
        lllllllllllllllllllIllIlIlllllll.findPlacePos();
        if (lllllllllllllllllllIllIlIlllllll.delay >= lllllllllllllllllllIllIlIlllllll.delaySetting.get() && lllllllllllllllllllIllIlIlllllll.placePositions.size() > 0) {
            BlockPos lllllllllllllllllllIllIllIIIIIll = lllllllllllllllllllIllIlIlllllll.placePositions.get(lllllllllllllllllllIllIlIlllllll.placePositions.size() - 1);
            if (BlockUtils.place(lllllllllllllllllllIllIllIIIIIll, Hand.MAIN_HAND, lllllllllllllllllllIllIllIIIIIII, lllllllllllllllllllIllIlIlllllll.rotate.get(), 50, true)) {
                lllllllllllllllllllIllIlIlllllll.placePositions.remove((Object)lllllllllllllllllllIllIllIIIIIll);
                lllllllllllllllllllIllIlIlllllll.placed = true;
            }
            lllllllllllllllllllIllIlIlllllll.delay = 0;
        } else {
            ++lllllllllllllllllllIllIlIlllllll.delay;
        }
    }

    private void findPlacePos() {
        SelfTrap lllllllllllllllllllIllIlIllIllll;
        lllllllllllllllllllIllIlIllIllll.placePositions.clear();
        BlockPos lllllllllllllllllllIllIlIlllIIII = lllllllllllllllllllIllIlIllIllll.mc.player.getBlockPos();
        switch (lllllllllllllllllllIllIlIllIllll.topPlacement.get()) {
            case Full: {
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 2, 0));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(1, 1, 0));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(-1, 1, 0));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 1, 1));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 1, -1));
                break;
            }
            case Top: {
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 2, 0));
                break;
            }
            case AntiFacePlace: {
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(1, 1, 0));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(-1, 1, 0));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 1, 1));
                lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, 1, -1));
            }
        }
        if (lllllllllllllllllllIllIlIllIllll.bottomPlacement.get() == BottomMode.Single) {
            lllllllllllllllllllIllIlIllIllll.add(lllllllllllllllllllIllIlIlllIIII.add(0, -1, 0));
        }
    }

    @Override
    public void onActivate() {
        SelfTrap lllllllllllllllllllIllIllIIIIlll;
        if (!lllllllllllllllllllIllIllIIIIlll.placePositions.isEmpty()) {
            lllllllllllllllllllIllIllIIIIlll.placePositions.clear();
        }
        lllllllllllllllllllIllIllIIIIlll.delay = 0;
        lllllllllllllllllllIllIllIIIIlll.placed = false;
        if (lllllllllllllllllllIllIllIIIIlll.center.get().booleanValue()) {
            PlayerUtils.centerPlayer();
        }
    }

    private void add(BlockPos lllllllllllllllllllIllIlIllIlIlI) {
        SelfTrap lllllllllllllllllllIllIlIllIlIIl;
        if (!lllllllllllllllllllIllIlIllIlIIl.placePositions.contains((Object)lllllllllllllllllllIllIlIllIlIlI) && lllllllllllllllllllIllIlIllIlIIl.mc.world.getBlockState(lllllllllllllllllllIllIlIllIlIlI).getMaterial().isReplaceable() && lllllllllllllllllllIllIlIllIlIIl.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), lllllllllllllllllllIllIlIllIlIlI, ShapeContext.absent())) {
            lllllllllllllllllllIllIlIllIlIIl.placePositions.add(lllllllllllllllllllIllIlIllIlIlI);
        }
    }

    public static final class BottomMode
    extends Enum<BottomMode> {
        private static final /* synthetic */ BottomMode[] $VALUES;
        public static final /* synthetic */ /* enum */ BottomMode Single;
        public static final /* synthetic */ /* enum */ BottomMode None;

        public static BottomMode valueOf(String llllllllllllllllllIIllIlllIIlllI) {
            return Enum.valueOf(BottomMode.class, llllllllllllllllllIIllIlllIIlllI);
        }

        public static BottomMode[] values() {
            return (BottomMode[])$VALUES.clone();
        }

        static {
            Single = new BottomMode();
            None = new BottomMode();
            $VALUES = BottomMode.$values();
        }

        private BottomMode() {
            BottomMode llllllllllllllllllIIllIlllIIlIlI;
        }

        private static /* synthetic */ BottomMode[] $values() {
            return new BottomMode[]{Single, None};
        }
    }

    public static final class TopMode
    extends Enum<TopMode> {
        private static final /* synthetic */ TopMode[] $VALUES;
        public static final /* synthetic */ /* enum */ TopMode AntiFacePlace;
        public static final /* synthetic */ /* enum */ TopMode Full;
        public static final /* synthetic */ /* enum */ TopMode Top;
        public static final /* synthetic */ /* enum */ TopMode None;

        public static TopMode valueOf(String lllIIIIIIIlII) {
            return Enum.valueOf(TopMode.class, lllIIIIIIIlII);
        }

        private TopMode() {
            TopMode llIlllllllllI;
        }

        private static /* synthetic */ TopMode[] $values() {
            return new TopMode[]{AntiFacePlace, Full, Top, None};
        }

        static {
            AntiFacePlace = new TopMode();
            Full = new TopMode();
            Top = new TopMode();
            None = new TopMode();
            $VALUES = TopMode.$values();
        }

        public static TopMode[] values() {
            return (TopMode[])$VALUES.clone();
        }
    }
}

