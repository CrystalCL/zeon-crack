/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.block.BlockState
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.AbstractBlockAccessor;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockIterator;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.BlockState;

public class HoleESP
extends Module {
    private final /* synthetic */ Setting<SettingColor> singleObsidian;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Boolean> drawOpposite;
    private final /* synthetic */ Setting<Integer> verticalRadius;
    private final /* synthetic */ Color transparent;
    private final /* synthetic */ Setting<Integer> horizontalRadius;
    private final /* synthetic */ Setting<Boolean> webs;
    private final /* synthetic */ List<Hole> holes;
    private final /* synthetic */ Setting<SettingColor> doubleBedrock;
    private final /* synthetic */ Setting<SettingColor> doubleObsidian;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<Boolean> doubles;
    private final /* synthetic */ Setting<SettingColor> doubleMixed;
    private final /* synthetic */ Setting<Boolean> ignoreOwn;
    private final /* synthetic */ SettingGroup sgGlow;
    private final /* synthetic */ Setting<Integer> holeHeight;
    private final /* synthetic */ Setting<Double> glowHeight;
    private final /* synthetic */ SettingGroup sgColors;
    private final /* synthetic */ Setting<Mode> renderMode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> singleMixed;
    private final /* synthetic */ Pool<Hole> holePool;
    private final /* synthetic */ Setting<SettingColor> singleBedrock;

    public HoleESP() {
        super(Categories.Render, "hole-esp", "Displays Safe holes that you will take less damage in.");
        HoleESP llIlllIIIIlIlll;
        llIlllIIIIlIlll.sgGeneral = llIlllIIIIlIlll.settings.getDefaultGroup();
        llIlllIIIIlIlll.sgGlow = llIlllIIIIlIlll.settings.createGroup("Glow");
        llIlllIIIIlIlll.sgColors = llIlllIIIIlIlll.settings.createGroup("Colors");
        llIlllIIIIlIlll.renderMode = llIlllIIIIlIlll.sgGeneral.add(new EnumSetting.Builder().name("render-mode").description("The rendering mode.").defaultValue(Mode.Glow).build());
        llIlllIIIIlIlll.shapeMode = llIlllIIIIlIlll.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
        llIlllIIIIlIlll.horizontalRadius = llIlllIIIIlIlll.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(10).min(0).sliderMax(32).build());
        llIlllIIIIlIlll.verticalRadius = llIlllIIIIlIlll.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for holes.").defaultValue(10).min(0).sliderMax(32).build());
        llIlllIIIIlIlll.holeHeight = llIlllIIIIlIlll.sgGeneral.add(new IntSetting.Builder().name("hole-height").description("Minimum hole height required to be rendered.").defaultValue(3).min(1).build());
        llIlllIIIIlIlll.doubles = llIlllIIIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("doubles").description("Highlights double holes that can be stood across.").defaultValue(false).build());
        llIlllIIIIlIlll.ignoreOwn = llIlllIIIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("ignore-own").description("Ignores rendering the hole you are currently standing in.").defaultValue(true).build());
        llIlllIIIIlIlll.webs = llIlllIIIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("webs").description("Whether to show holes that have webs inside of them.").defaultValue(false).build());
        llIlllIIIIlIlll.glowHeight = llIlllIIIIlIlll.sgGlow.add(new DoubleSetting.Builder().name("glow-height").description("The height of the glow when Glow mode is active").defaultValue(1.0).min(0.0).build());
        llIlllIIIIlIlll.drawOpposite = llIlllIIIIlIlll.sgGlow.add(new BoolSetting.Builder().name("draw-opposite").description("Draws a quad at the opposite end of the glow.").defaultValue(false).build());
        llIlllIIIIlIlll.singleBedrock = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("single-bedrock").description("The color for single holes that are completely bedrock.").defaultValue(new SettingColor(25, 225, 25, 100)).build());
        llIlllIIIIlIlll.singleObsidian = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("single-obsidian").description("The color for single holes that are completely obsidian.").defaultValue(new SettingColor(225, 25, 25, 100)).build());
        llIlllIIIIlIlll.singleMixed = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("single-mixed").description("The color for single holes that have mixed bedrock and obsidian.").defaultValue(new SettingColor(225, 145, 25, 100)).build());
        llIlllIIIIlIlll.doubleBedrock = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("double-bedrock").description("The color for double holes that are completely bedrock.").defaultValue(new SettingColor(25, 225, 25, 100)).build());
        llIlllIIIIlIlll.doubleObsidian = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("double-obsidian").description("The color for double holes that are completely obsidian.").defaultValue(new SettingColor(225, 25, 25, 100)).build());
        llIlllIIIIlIlll.doubleMixed = llIlllIIIIlIlll.sgColors.add(new ColorSetting.Builder().name("double-mixed").description("The color for double holes that have mixed bedrock and obsidian.").defaultValue(new SettingColor(225, 145, 25, 100)).build());
        llIlllIIIIlIlll.holePool = new Pool<Hole>(() -> new Hole());
        llIlllIIIIlIlll.blockPos = new Mutable();
        llIlllIIIIlIlll.holes = new ArrayList<Hole>();
        llIlllIIIIlIlll.transparent = new Color(0, 0, 0, 0);
    }

    private int[] checkArround(BlockPos llIllIlllllIlll, Direction llIllIlllllIllI) {
        Block llIllIllllllIll;
        HoleESP llIllIllllllIII;
        int llIllIlllllllll = 0;
        int llIllIllllllllI = 0;
        if (llIllIllllllIII.isBlocked(llIllIlllllIlll)) {
            return new int[]{llIllIlllllllll, llIllIllllllllI};
        }
        Block llIllIlllllllIl = llIllIllllllIII.mc.world.getBlockState(llIllIlllllIlll.down()).getBlock();
        if (llIllIlllllllIl == Blocks.BEDROCK) {
            ++llIllIlllllllll;
        } else if (llIllIlllllllIl == Blocks.OBSIDIAN) {
            ++llIllIllllllllI;
        }
        Block llIllIlllllllII = llIllIllllllIII.mc.world.getBlockState(llIllIlllllIlll.north()).getBlock();
        if (llIllIlllllIllI != Direction.NORTH) {
            if (llIllIlllllllII == Blocks.BEDROCK) {
                ++llIllIlllllllll;
            } else if (llIllIlllllllII == Blocks.OBSIDIAN) {
                ++llIllIllllllllI;
            }
        }
        if ((llIllIllllllIll = llIllIllllllIII.mc.world.getBlockState(llIllIlllllIlll.south()).getBlock()) == Blocks.BEDROCK) {
            ++llIllIlllllllll;
        } else if (llIllIllllllIll == Blocks.OBSIDIAN) {
            ++llIllIllllllllI;
        }
        Block llIllIllllllIlI = llIllIllllllIII.mc.world.getBlockState(llIllIlllllIlll.east()).getBlock();
        if (llIllIllllllIlI == Blocks.BEDROCK) {
            ++llIllIlllllllll;
        } else if (llIllIllllllIlI == Blocks.OBSIDIAN) {
            ++llIllIllllllllI;
        }
        Block llIllIllllllIIl = llIllIllllllIII.mc.world.getBlockState(llIllIlllllIlll.west()).getBlock();
        if (llIllIllllllIIl == Blocks.BEDROCK || llIllIllllllIIl == Blocks.OBSIDIAN) {
            // empty if block
        }
        return new int[]{++llIllIlllllllll, ++llIllIllllllllI};
    }

    @EventHandler
    private void onRender(RenderEvent llIllIllllIIIII) {
        HoleESP llIllIlllIlllll;
        for (Hole llIllIllllIIIlI : llIllIlllIlllll.holes) {
            switch (llIllIlllIlllll.renderMode.get()) {
                case Flat: {
                    llIllIlllIlllll.drawFlat(llIllIllllIIIlI);
                    break;
                }
                case Box: {
                    llIllIlllIlllll.drawBox(llIllIllllIIIlI, false);
                    break;
                }
                case BoxBelow: {
                    llIllIlllIlllll.drawBox(llIllIllllIIIlI, true);
                    break;
                }
                case ReverseGlow: 
                case Glow: {
                    llIllIlllIlllll.drawBoxGlowDirection(llIllIllllIIIlI, llIllIlllIlllll.renderMode.get() == Mode.ReverseGlow);
                }
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIlllIIIIlIIII) {
        HoleESP llIlllIIIIIllll;
        for (Hole llIlllIIIIlIIlI : llIlllIIIIIllll.holes) {
            llIlllIIIIIllll.holePool.free(llIlllIIIIlIIlI);
        }
        llIlllIIIIIllll.holes.clear();
        BlockIterator.register(llIlllIIIIIllll.horizontalRadius.get(), llIlllIIIIIllll.verticalRadius.get(), (llIllIlIlllIlII, llIllIlIlllllll) -> {
            HoleESP llIllIllIIIIIIl;
            llIllIllIIIIIIl.blockPos.set((Vec3i)llIllIlIlllIlII);
            if (llIllIllIIIIIIl.ignoreOwn.get().booleanValue() && llIllIllIIIIIIl.mc.player.getBlockPos().equals((Object)llIllIllIIIIIIl.blockPos) || llIllIllIIIIIIl.isBlocked((BlockPos)llIllIllIIIIIIl.blockPos)) {
                return;
            }
            if (!llIllIllIIIIIIl.webs.get().booleanValue() && llIllIllIIIIIIl.mc.world.getBlockState((BlockPos)llIllIllIIIIIIl.blockPos).getBlock().is(Blocks.COBWEB)) {
                return;
            }
            Direction llIllIlIllllllI = Direction.UP;
            int llIllIlIlllllIl = 0;
            int llIllIlIlllllII = 0;
            int llIllIlIllllIll = 0;
            BlockState llIllIlIllllIlI = llIllIllIIIIIIl.mc.world.getBlockState(llIllIllIIIIIIl.blockPos.down());
            if (llIllIlIllllIlI.getBlock() == Blocks.BEDROCK) {
                ++llIllIlIlllllIl;
            } else if (llIllIlIllllIlI.getBlock() == Blocks.OBSIDIAN) {
                ++llIllIlIlllllII;
            } else if (llIllIlIllllIlI.isAir()) {
                return;
            }
            BlockState llIllIlIllllIIl = llIllIllIIIIIIl.mc.world.getBlockState(llIllIllIIIIIIl.blockPos.north());
            if (llIllIlIllllIIl.getBlock() == Blocks.BEDROCK) {
                ++llIllIlIlllllIl;
            } else if (llIllIlIllllIIl.getBlock() == Blocks.OBSIDIAN) {
                ++llIllIlIlllllII;
            } else if (llIllIlIllllIIl.isAir()) {
                llIllIlIllllllI = Direction.NORTH;
                ++llIllIlIllllIll;
            }
            BlockState llIllIlIllllIII = llIllIllIIIIIIl.mc.world.getBlockState(llIllIllIIIIIIl.blockPos.south());
            if (llIllIlIllllIII.getBlock() == Blocks.BEDROCK) {
                ++llIllIlIlllllIl;
            } else if (llIllIlIllllIII.getBlock() == Blocks.OBSIDIAN) {
                ++llIllIlIlllllII;
            } else if (llIllIlIllllIII.isAir()) {
                llIllIlIllllllI = Direction.SOUTH;
                ++llIllIlIllllIll;
            }
            BlockState llIllIlIlllIlll = llIllIllIIIIIIl.mc.world.getBlockState(llIllIllIIIIIIl.blockPos.east());
            if (llIllIlIlllIlll.getBlock() == Blocks.BEDROCK) {
                ++llIllIlIlllllIl;
            } else if (llIllIlIlllIlll.getBlock() == Blocks.OBSIDIAN) {
                ++llIllIlIlllllII;
            } else if (llIllIlIlllIlll.isAir()) {
                llIllIlIllllllI = Direction.EAST;
                ++llIllIlIllllIll;
            }
            BlockState llIllIlIlllIllI = llIllIllIIIIIIl.mc.world.getBlockState(llIllIllIIIIIIl.blockPos.west());
            if (llIllIlIlllIllI.getBlock() == Blocks.BEDROCK) {
                ++llIllIlIlllllIl;
            } else if (llIllIlIlllIllI.getBlock() == Blocks.OBSIDIAN) {
                ++llIllIlIlllllII;
            } else if (llIllIlIlllIllI.isAir()) {
                llIllIlIllllllI = Direction.WEST;
                ++llIllIlIllllIll;
            }
            if (llIllIlIllllIll > 1) {
                return;
            }
            if (llIllIlIlllllII + llIllIlIlllllIl == 5) {
                if (llIllIlIlllllIl == 5) {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.singleBedrock.get(), Direction.UP));
                } else if (llIllIlIlllllII == 5) {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.singleObsidian.get(), Direction.UP));
                } else {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.singleMixed.get(), Direction.UP));
                }
            } else if (llIllIlIlllllII + llIllIlIlllllIl == 4 && llIllIlIllllIll == 1 && llIllIllIIIIIIl.doubles.get().booleanValue()) {
                int[] llIllIllIIIIIlI = llIllIllIIIIIIl.checkArround(llIllIllIIIIIIl.blockPos.offset(llIllIlIllllllI), llIllIlIllllllI.getOpposite());
                if (llIllIllIIIIIlI[0] == 4 && llIllIlIlllllIl == 4) {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.doubleBedrock.get(), llIllIlIllllllI));
                } else if (llIllIllIIIIIlI[1] == 4 && llIllIlIlllllII == 4) {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.doubleObsidian.get(), llIllIlIllllllI));
                } else if (llIllIllIIIIIlI[0] + llIllIllIIIIIlI[1] == 4) {
                    llIllIllIIIIIIl.holes.add(llIllIllIIIIIIl.holePool.get().set((BlockPos)llIllIllIIIIIIl.blockPos, llIllIllIIIIIIl.doubleMixed.get(), llIllIlIllllllI));
                }
            }
        });
    }

    private void drawBox(Hole llIllIlllIIIIII, boolean llIllIllIllllll) {
        int llIllIlllIIIlII = llIllIlllIIIIII.blockPos.getX();
        int llIllIlllIIIIll = llIllIllIllllll ? llIllIlllIIIIII.blockPos.getY() - 1 : llIllIlllIIIIII.blockPos.getY();
        int llIllIlllIIIIlI = llIllIlllIIIIII.blockPos.getZ();
        switch (llIllIlllIIIIII.direction) {
            case UP: {
                HoleESP llIllIlllIIIlll;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (BlockPos)(llIllIllIllllll ? llIllIlllIIIIII.blockPos.down() : llIllIlllIIIIII.blockPos), llIllIlllIIIIII.colorSides, llIllIlllIIIIII.colorLines, llIllIlllIIIlll.shapeMode.get(), 0);
                break;
            }
            case NORTH: {
                HoleESP llIllIlllIIIlll;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llIllIlllIIIlII, llIllIlllIIIIll, llIllIlllIIIIlI + 1, llIllIlllIIIlII + 1, llIllIlllIIIIll + 1, llIllIlllIIIIlI - 1, llIllIlllIIIIII.colorSides, llIllIlllIIIIII.colorLines, llIllIlllIIIlll.shapeMode.get(), 0);
                break;
            }
            case SOUTH: {
                HoleESP llIllIlllIIIlll;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llIllIlllIIIlII, llIllIlllIIIIll, llIllIlllIIIIlI, llIllIlllIIIlII + 1, llIllIlllIIIIll + 1, llIllIlllIIIIlI + 2, llIllIlllIIIIII.colorSides, llIllIlllIIIIII.colorLines, llIllIlllIIIlll.shapeMode.get(), 0);
                break;
            }
            case EAST: {
                HoleESP llIllIlllIIIlll;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llIllIlllIIIlII, llIllIlllIIIIll, llIllIlllIIIIlI, llIllIlllIIIlII + 2, llIllIlllIIIIll + 1, llIllIlllIIIIlI + 1, llIllIlllIIIIII.colorSides, llIllIlllIIIIII.colorLines, llIllIlllIIIlll.shapeMode.get(), 0);
                break;
            }
            case WEST: {
                HoleESP llIllIlllIIIlll;
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llIllIlllIIIlII + 1, llIllIlllIIIIll, llIllIlllIIIIlI, llIllIlllIIIlII - 1, llIllIlllIIIIll + 1, llIllIlllIIIIlI + 1, llIllIlllIIIIII.colorSides, llIllIlllIIIIII.colorLines, llIllIlllIIIlll.shapeMode.get(), 0);
            }
        }
    }

    private void drawGlowSimple(double llIllIllIIlIllI, double llIllIllIIlIlIl, double llIllIllIIlllIl, double llIllIllIIlIIll, double llIllIllIIlIIlI, Color llIllIllIIllIlI, Color llIllIllIIlIIII, boolean llIllIllIIllIII) {
        HoleESP llIllIllIIlIlll;
        if (llIllIllIIlIlll.shapeMode.get() != ShapeMode.Lines) {
            Renderer.NORMAL.gradientBoxSides(llIllIllIIlIllI, llIllIllIIlIlIl, llIllIllIIlllIl, llIllIllIIlIIll, llIllIllIIlIIlI, llIllIllIIlIlll.glowHeight.get(), llIllIllIIlIIII, llIllIllIIlIlll.transparent, llIllIllIIllIII);
        }
        if (llIllIllIIlIlll.shapeMode.get() != ShapeMode.Sides) {
            if (llIllIllIIlIlll.drawOpposite.get().booleanValue()) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIllIIlIllI, llIllIllIIllIII ? llIllIllIIlIlIl : llIllIllIIlIlIl + llIllIllIIlIlll.glowHeight.get(), llIllIllIIlllIl, llIllIllIIlIIll, llIllIllIIlIIlI, llIllIllIIllIlI, llIllIllIIlIIII, ShapeMode.Lines);
            }
            Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIllIIlIllI, llIllIllIIllIII ? llIllIllIIlIlIl + llIllIllIIlIlll.glowHeight.get() : llIllIllIIlIlIl, llIllIllIIlllIl, llIllIllIIlIIll, llIllIllIIlIIlI, llIllIllIIllIlI, llIllIllIIlIIII, ShapeMode.Lines);
            Renderer.LINES.gradientVerticalBox(llIllIllIIlIllI, llIllIllIIlIlIl, llIllIllIIlllIl, llIllIllIIlIIll, llIllIllIIlIIlI, llIllIllIIlIlll.glowHeight.get(), llIllIllIIlIIII, llIllIllIIlIlll.transparent, llIllIllIIllIII);
        }
    }

    private boolean isBlocked(BlockPos llIllIllllIlIIl) {
        HoleESP llIllIllllIlIII;
        if (((AbstractBlockAccessor)llIllIllllIlIII.mc.world.getBlockState(llIllIllllIlIIl).getBlock()).isCollidable()) {
            return true;
        }
        for (int llIllIllllIlIll = 0; llIllIllllIlIll < llIllIllllIlIII.holeHeight.get(); ++llIllIllllIlIll) {
            if (!((AbstractBlockAccessor)llIllIllllIlIII.mc.world.getBlockState(llIllIllllIlIIl.up(llIllIllllIlIll)).getBlock()).isCollidable()) continue;
            return true;
        }
        return false;
    }

    private void drawFlat(Hole llIllIlllIlIllI) {
        int llIllIlllIlIlIl = llIllIlllIlIllI.blockPos.getX();
        int llIllIlllIlIlII = llIllIlllIlIllI.blockPos.getY();
        int llIllIlllIlIIll = llIllIlllIlIllI.blockPos.getZ();
        switch (llIllIlllIlIllI.direction) {
            case UP: {
                HoleESP llIllIlllIlIlll;
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIlllIlIlIl, llIllIlllIlIlII, llIllIlllIlIIll, 1.0, llIllIlllIlIllI.colorSides, llIllIlllIlIllI.colorLines, llIllIlllIlIlll.shapeMode.get());
                break;
            }
            case NORTH: {
                HoleESP llIllIlllIlIlll;
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIlllIlIlIl, llIllIlllIlIlII, llIllIlllIlIIll + 1, llIllIlllIlIlIl + 1, llIllIlllIlIIll - 1, llIllIlllIlIllI.colorSides, llIllIlllIlIllI.colorLines, llIllIlllIlIlll.shapeMode.get());
                break;
            }
            case SOUTH: {
                HoleESP llIllIlllIlIlll;
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIlllIlIlIl, llIllIlllIlIlII, llIllIlllIlIIll, llIllIlllIlIlIl + 1, llIllIlllIlIIll + 2, llIllIlllIlIllI.colorSides, llIllIlllIlIllI.colorLines, llIllIlllIlIlll.shapeMode.get());
                break;
            }
            case EAST: {
                HoleESP llIllIlllIlIlll;
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIlllIlIlIl, llIllIlllIlIlII, llIllIlllIlIIll, llIllIlllIlIlIl + 2, llIllIlllIlIIll + 1, llIllIlllIlIllI.colorSides, llIllIlllIlIllI.colorLines, llIllIlllIlIlll.shapeMode.get());
                break;
            }
            case WEST: {
                HoleESP llIllIlllIlIlll;
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIllIlllIlIlIl + 1, llIllIlllIlIlII, llIllIlllIlIIll, llIllIlllIlIlIl - 1, llIllIlllIlIIll + 1, llIllIlllIlIllI.colorSides, llIllIlllIlIllI.colorLines, llIllIlllIlIlll.shapeMode.get());
            }
        }
    }

    private void drawBoxGlowDirection(Hole llIllIllIllIlII, boolean llIllIllIllIIll) {
        int llIllIllIllIIlI = llIllIllIllIlII.blockPos.getX();
        int llIllIllIllIIIl = llIllIllIllIlII.blockPos.getY();
        int llIllIllIllIIII = llIllIllIllIlII.blockPos.getZ();
        switch (llIllIllIllIlII.direction) {
            case UP: {
                HoleESP llIllIllIlIllll;
                llIllIllIlIllll.drawGlowSimple(llIllIllIllIIlI, llIllIllIllIIIl, llIllIllIllIIII, llIllIllIllIIlI + 1, llIllIllIllIIII + 1, llIllIllIllIlII.colorSides, llIllIllIllIlII.colorLines, llIllIllIllIIll);
                break;
            }
            case NORTH: {
                HoleESP llIllIllIlIllll;
                llIllIllIlIllll.drawGlowSimple(llIllIllIllIIlI, llIllIllIllIIIl, llIllIllIllIIII + 1, llIllIllIllIIlI + 1, llIllIllIllIIII - 1, llIllIllIllIlII.colorSides, llIllIllIllIlII.colorLines, llIllIllIllIIll);
                break;
            }
            case SOUTH: {
                HoleESP llIllIllIlIllll;
                llIllIllIlIllll.drawGlowSimple(llIllIllIllIIlI, llIllIllIllIIIl, llIllIllIllIIII, llIllIllIllIIlI + 1, llIllIllIllIIII + 2, llIllIllIllIlII.colorSides, llIllIllIllIlII.colorLines, llIllIllIllIIll);
                break;
            }
            case EAST: {
                HoleESP llIllIllIlIllll;
                llIllIllIlIllll.drawGlowSimple(llIllIllIllIIlI, llIllIllIllIIIl, llIllIllIllIIII, llIllIllIllIIlI + 2, llIllIllIllIIII + 1, llIllIllIllIlII.colorSides, llIllIllIllIlII.colorLines, llIllIllIllIIll);
                break;
            }
            case WEST: {
                HoleESP llIllIllIlIllll;
                llIllIllIlIllll.drawGlowSimple(llIllIllIllIIlI + 1, llIllIllIllIIIl, llIllIllIllIIII, llIllIllIllIIlI - 1, llIllIllIllIIII + 1, llIllIllIllIlII.colorSides, llIllIllIllIlII.colorLines, llIllIllIllIIll);
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode ReverseGlow;
        public static final /* synthetic */ /* enum */ Mode BoxBelow;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Box;
        public static final /* synthetic */ /* enum */ Mode Glow;
        public static final /* synthetic */ /* enum */ Mode Flat;

        private Mode() {
            Mode lllllIllIllllII;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String lllllIlllIIIIlI) {
            return Enum.valueOf(Mode.class, lllllIlllIIIIlI);
        }

        static {
            Flat = new Mode();
            Box = new Mode();
            BoxBelow = new Mode();
            Glow = new Mode();
            ReverseGlow = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Flat, Box, BoxBelow, Glow, ReverseGlow};
        }
    }

    private static class Hole {
        public /* synthetic */ Direction direction;
        public /* synthetic */ Color colorLines;
        public /* synthetic */ Color colorSides;
        public /* synthetic */ Mutable blockPos;

        public Hole set(BlockPos lllllIlIIllIIIl, Color lllllIlIIllIIII, Direction lllllIlIIlIllll) {
            Hole lllllIlIIlIlllI;
            lllllIlIIlIlllI.blockPos.set((Vec3i)lllllIlIIllIIIl);
            lllllIlIIlIlllI.direction = lllllIlIIlIllll;
            lllllIlIIlIlllI.colorLines.set(lllllIlIIllIIII);
            lllllIlIIlIlllI.colorSides.set(lllllIlIIllIIII);
            lllllIlIIlIlllI.colorSides.a = (int)((double)lllllIlIIlIlllI.colorSides.a * 0.5);
            lllllIlIIlIlllI.colorSides.validate();
            return lllllIlIIlIlllI;
        }

        private Hole() {
            Hole lllllIlIIlllIII;
            lllllIlIIlllIII.blockPos = new Mutable();
            lllllIlIIlllIII.colorSides = new Color();
            lllllIlIIlllIII.colorLines = new Color();
        }
    }
}

