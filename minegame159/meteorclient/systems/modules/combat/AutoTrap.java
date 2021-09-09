/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> turnOff;
    private final /* synthetic */ Setting<BottomMode> bottomPlacement;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ int delay;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private /* synthetic */ boolean placed;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ List<BlockPos> placePositions;
    private final /* synthetic */ Setting<TopMode> topPlacement;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Integer> delaySetting;

    @Override
    public String getInfoString() {
        AutoTrap lIlllIllIIlllII;
        if (lIlllIllIIlllII.target != null) {
            return lIlllIllIIlllII.target.getEntityName();
        }
        return null;
    }

    @Override
    public void onActivate() {
        AutoTrap lIlllIlllIIlIIl;
        lIlllIlllIIlIIl.target = null;
        if (!lIlllIlllIIlIIl.placePositions.isEmpty()) {
            lIlllIlllIIlIIl.placePositions.clear();
        }
        lIlllIlllIIlIIl.delay = 0;
        lIlllIlllIIlIIl.placed = false;
    }

    private PlayerEntity findTarget() {
        AutoTrap lIlllIllIlIIIIl;
        for (PlayerEntity lIlllIllIlIIIll : lIlllIllIlIIIIl.mc.world.getPlayers()) {
            if (lIlllIllIlIIIll == lIlllIllIlIIIIl.mc.player || !Friends.get().attack(lIlllIllIlIIIll) || !lIlllIllIlIIIll.isAlive()) continue;
            if (lIlllIllIlIIIIl.target == null) {
                lIlllIllIlIIIIl.target = lIlllIllIlIIIll;
                continue;
            }
            if (!(lIlllIllIlIIIIl.mc.player.distanceTo((Entity)lIlllIllIlIIIll) < lIlllIllIlIIIIl.mc.player.distanceTo((Entity)lIlllIllIlIIIIl.target))) continue;
            lIlllIllIlIIIIl.target = lIlllIllIlIIIll;
        }
        for (FakePlayerEntity lIlllIllIlIIIlI : FakePlayerUtils.getPlayers().keySet()) {
            if (!Friends.get().attack((PlayerEntity)lIlllIllIlIIIlI) || !lIlllIllIlIIIlI.isAlive()) continue;
            if (lIlllIllIlIIIIl.target == null) {
                lIlllIllIlIIIIl.target = lIlllIllIlIIIlI;
                continue;
            }
            if (!(lIlllIllIlIIIIl.mc.player.distanceTo((Entity)lIlllIllIlIIIlI) < lIlllIllIlIIIIl.mc.player.distanceTo((Entity)lIlllIllIlIIIIl.target))) continue;
            lIlllIllIlIIIIl.target = lIlllIllIlIIIlI;
        }
        return lIlllIllIlIIIIl.target;
    }

    private void add(BlockPos lIlllIllIlIlIIl) {
        AutoTrap lIlllIllIlIlIlI;
        if (!lIlllIllIlIlIlI.placePositions.contains((Object)lIlllIllIlIlIIl) && lIlllIllIlIlIlI.mc.world.getBlockState(lIlllIllIlIlIIl).getMaterial().isReplaceable() && lIlllIllIlIlIlI.mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), lIlllIllIlIlIIl, ShapeContext.absent())) {
            lIlllIllIlIlIlI.placePositions.add(lIlllIllIlIlIIl);
        }
    }

    @EventHandler
    private void onRender(RenderEvent lIlllIllIlllIIl) {
        AutoTrap lIlllIllIlllIII;
        if (!lIlllIllIlllIII.render.get().booleanValue() || lIlllIllIlllIII.placePositions.isEmpty()) {
            return;
        }
        for (BlockPos lIlllIllIlllIll : lIlllIllIlllIII.placePositions) {
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIlllIllIlllIll, lIlllIllIlllIII.sideColor.get(), lIlllIllIlllIII.lineColor.get(), lIlllIllIlllIII.shapeMode.get(), 0);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIlllIlllIIIIll) {
        AutoTrap lIlllIlllIIIIIl;
        int lIlllIlllIIIIlI = InvUtils.findItemInHotbar(Blocks.OBSIDIAN.asItem());
        if (lIlllIlllIIIIIl.turnOff.get().booleanValue() && (lIlllIlllIIIIIl.placed && lIlllIlllIIIIIl.placePositions.isEmpty() || lIlllIlllIIIIlI == -1)) {
            lIlllIlllIIIIIl.sendToggledMsg();
            lIlllIlllIIIIIl.toggle();
            return;
        }
        if (lIlllIlllIIIIlI == -1) {
            lIlllIlllIIIIIl.placePositions.clear();
            return;
        }
        if (lIlllIlllIIIIIl.target == null || lIlllIlllIIIIIl.mc.player.distanceTo((Entity)lIlllIlllIIIIIl.target) > (float)lIlllIlllIIIIIl.range.get().intValue()) {
            lIlllIlllIIIIIl.placePositions.clear();
            lIlllIlllIIIIIl.target = lIlllIlllIIIIIl.findTarget();
            lIlllIlllIIIIIl.placed = false;
            return;
        }
        lIlllIlllIIIIIl.findPlacePos(lIlllIlllIIIIIl.target);
        if (lIlllIlllIIIIIl.delay >= lIlllIlllIIIIIl.delaySetting.get() && lIlllIlllIIIIIl.placePositions.size() > 0) {
            BlockPos lIlllIlllIIIlIl = lIlllIlllIIIIIl.placePositions.get(lIlllIlllIIIIIl.placePositions.size() - 1);
            if (BlockUtils.place(lIlllIlllIIIlIl, Hand.MAIN_HAND, lIlllIlllIIIIlI, lIlllIlllIIIIIl.rotate.get(), 50, true)) {
                lIlllIlllIIIIIl.placePositions.remove((Object)lIlllIlllIIIlIl);
                lIlllIlllIIIIIl.placed = true;
            }
            lIlllIlllIIIIIl.delay = 0;
        } else {
            ++lIlllIlllIIIIIl.delay;
        }
    }

    private void findPlacePos(PlayerEntity lIlllIllIllIIIl) {
        AutoTrap lIlllIllIllIIlI;
        lIlllIllIllIIlI.placePositions.clear();
        BlockPos lIlllIllIllIIII = lIlllIllIllIIIl.getBlockPos();
        switch (lIlllIllIllIIlI.topPlacement.get()) {
            case Full: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 2, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(1, 1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(-1, 1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 1, 1));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 1, -1));
                break;
            }
            case Face: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(1, 1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(-1, 1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 1, 1));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 1, -1));
                break;
            }
            case Top: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 2, 0));
            }
        }
        switch (lIlllIllIllIIlI.bottomPlacement.get()) {
            case Platform: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, -1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(1, -1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, -1, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, -1, 1));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, -1, -1));
                break;
            }
            case Full: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(1, 0, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(-1, 0, 0));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 0, -1));
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, 0, 1));
                break;
            }
            case Single: {
                lIlllIllIllIIlI.add(lIlllIllIllIIII.add(0, -1, 0));
            }
        }
    }

    public AutoTrap() {
        super(Categories.Combat, "auto-trap", "Traps people in an obsidian box to prevent them from moving.");
        AutoTrap lIlllIlllIIllIl;
        lIlllIlllIIllIl.sgGeneral = lIlllIlllIIllIl.settings.getDefaultGroup();
        lIlllIlllIIllIl.sgRender = lIlllIlllIIllIl.settings.createGroup("Render");
        lIlllIlllIIllIl.topPlacement = lIlllIlllIIllIl.sgGeneral.add(new EnumSetting.Builder().name("top-mode").description("Which blocks to place on the top half of the target.").defaultValue(TopMode.Full).build());
        lIlllIlllIIllIl.bottomPlacement = lIlllIlllIIllIl.sgGeneral.add(new EnumSetting.Builder().name("bottom-mode").description("Which blocks to place on the bottom half of the target.").defaultValue(BottomMode.Platform).build());
        lIlllIlllIIllIl.range = lIlllIlllIIllIl.sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
        lIlllIlllIIllIl.delaySetting = lIlllIlllIIllIl.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
        lIlllIlllIIllIl.turnOff = lIlllIlllIIllIl.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Turns off after placing.").defaultValue(true).build());
        lIlllIlllIIllIl.rotate = lIlllIlllIIllIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(true).build());
        lIlllIlllIIllIl.render = lIlllIlllIIllIl.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lIlllIlllIIllIl.shapeMode = lIlllIlllIIllIl.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lIlllIlllIIllIl.sideColor = lIlllIlllIIllIl.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lIlllIlllIIllIl.lineColor = lIlllIlllIIllIl.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lIlllIlllIIllIl.placePositions = new ArrayList<BlockPos>();
    }

    public static final class BottomMode
    extends Enum<BottomMode> {
        public static final /* synthetic */ /* enum */ BottomMode None;
        private static final /* synthetic */ BottomMode[] $VALUES;
        public static final /* synthetic */ /* enum */ BottomMode Full;
        public static final /* synthetic */ /* enum */ BottomMode Platform;
        public static final /* synthetic */ /* enum */ BottomMode Single;

        private BottomMode() {
            BottomMode lllllllllllllllllllllIlIIlllIIIl;
        }

        public static BottomMode[] values() {
            return (BottomMode[])$VALUES.clone();
        }

        public static BottomMode valueOf(String lllllllllllllllllllllIlIIlllIllI) {
            return Enum.valueOf(BottomMode.class, lllllllllllllllllllllIlIIlllIllI);
        }

        static {
            Single = new BottomMode();
            Platform = new BottomMode();
            Full = new BottomMode();
            None = new BottomMode();
            $VALUES = BottomMode.$values();
        }

        private static /* synthetic */ BottomMode[] $values() {
            return new BottomMode[]{Single, Platform, Full, None};
        }
    }

    public static final class TopMode
    extends Enum<TopMode> {
        public static final /* synthetic */ /* enum */ TopMode Face;
        public static final /* synthetic */ /* enum */ TopMode None;
        public static final /* synthetic */ /* enum */ TopMode Top;
        private static final /* synthetic */ TopMode[] $VALUES;
        public static final /* synthetic */ /* enum */ TopMode Full;

        public static TopMode valueOf(String lIIlIlllIIl) {
            return Enum.valueOf(TopMode.class, lIIlIlllIIl);
        }

        private TopMode() {
            TopMode lIIlIllIIll;
        }

        private static /* synthetic */ TopMode[] $values() {
            return new TopMode[]{Full, Top, Face, None};
        }

        public static TopMode[] values() {
            return (TopMode[])$VALUES.clone();
        }

        static {
            Full = new TopMode();
            Top = new TopMode();
            Face = new TopMode();
            None = new TopMode();
            $VALUES = TopMode.$values();
        }
    }
}

