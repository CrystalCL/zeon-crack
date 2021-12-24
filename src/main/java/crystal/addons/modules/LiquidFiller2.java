package crystal.addons.modules;

import com.google.common.collect.Lists;
import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.world.Nuker.SortMode;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ToolItem;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LiquidFiller2 extends Module {
    private final SettingGroup sg = settings.getDefaultGroup();
    private final SettingGroup sr = settings.createGroup("Render");
    private final Setting<PlaceIn> placeInLiquids = sg.add(new EnumSetting.Builder<PlaceIn>().name("place-in").description("What type of liquids to place in.").defaultValue(PlaceIn.Lava).build());
    private final Setting<Boolean> slab = sg.add(new BoolSetting.Builder().name("slab-block").defaultValue(false).build());
    private final Setting<Integer> delay = sg.add(new IntSetting.Builder().name("delay").defaultValue(50).min(1).sliderMin(20).sliderMax(50).build());
    private final Setting<Double> radius = sg.add(new DoubleSetting.Builder().name("radius").description("The max block place radius.").defaultValue(4.8D).min(0.0D).sliderMax(4.8D).build());
    private final Setting<SortMode> SortBlocksMode = sg.add(new EnumSetting.Builder<SortMode>().name("sort-mode").description("The blocks you want to mine first.").defaultValue(SortMode.Closest).build());
    private final Setting<List<Block>> whitelist = sg.add(new BlockListSetting.Builder().name("block-whitelist").description("The allowed blocks that it will use to fill up the liquid.").defaultValue(getDefaultWhitelist()).build());
    private final Setting<Boolean> rotate = sg.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the space targeted for filling.").defaultValue(false).build());
    private final Setting<SettingColor> RenderColor = sr.add(new ColorSetting.Builder().name("block-render-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(127, 255, 212, 30)).build());
    private final Setting<Boolean> fluidEsp = sg.add(new BoolSetting.Builder().name("fluid-esp").defaultValue(true).build());
    private final Setting<SettingColor> fluidEspColor = sr.add(new ColorSetting.Builder().name("fluid-esp-color").visible(fluidEsp::get).defaultValue(new SettingColor(120, 219, 226, 30)).build());
    private final Setting<ShapeMode> fluidEspShapeMode = (new EnumSetting.Builder<ShapeMode>().name("fluid-esp-shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).visible(fluidEsp::get).build());
    private final Setting<Integer> fluidEspRadius = sr.add(new IntSetting.Builder().name("fluid-lava-radius").visible(fluidEsp::get).defaultValue(25).min(1).sliderMax(70).build());
    private final Setting<Integer> fluidEspLimit = sr.add(new IntSetting.Builder().name("fluid-esp-limit").visible(fluidEsp::get).defaultValue(500).min(1).sliderMin(1).sliderMax(50000).build());
    private final Setting<Integer> fluidScanDelay = sr.add(new IntSetting.Builder().name("fluid-esp-scan-delay").visible(fluidEsp::get).defaultValue(50).min(1).sliderMin(1).sliderMax(500).build());

    private static final List<BlockPos> fluids = Collections.synchronizedList(new ArrayList());
    private static BlockPos rpos = null;
    private static boolean isStarted = false;
    private static int wait = 0;
    private static Thread execute;

    public LiquidFiller2() {
        super(CrystalCL.PvE, "liquid-filler+", "Places blocks inside of liquid source blocks within range of you.");
    }

    public void onActivate() {
        if (!isStarted) {
            execute.start();
        }

        wait = 0;
    }

    @EventHandler
    private void render_event(Render3DEvent e) {
        if (rpos != null) {
            e.renderer.box(rpos, RenderColor.get(), null, ShapeMode.Sides, 0);
        }

        if (fluidEsp.get()) {
            int limit = 0;
            synchronized (fluids) {

                for (BlockPos pos : fluids) {
                    ++limit;
                    if (limit == fluidEspLimit.get()) {
                        return;
                    }

                    e.renderer.box(pos, fluidEspColor.get(), fluidEspColor.get(), fluidEspShapeMode.get(), 0);
                }
            }
        }
    }

    @EventHandler
    private void packet_event_receive(PacketEvent.Receive e) {
        synchronized (fluids) {
            Packet var4 = e.packet;
            if (var4 instanceof BlockUpdateS2CPacket) {
                BlockUpdateS2CPacket p = (BlockUpdateS2CPacket) var4;
                if (p.getState().getFluidState().isEmpty()) {
                    fluids.remove(p.getPos());
                }
            }
        }
    }

    @EventHandler
    private void tick_event(TickEvent.Post e) {
        if (!fluidEsp.get()) {
            synchronized (fluids) {
                fluids.clear();
            }
        } else if (wait > 0) {
            --wait;
        } else {
            int px = mc.player.getBlockPos().getX();
            int py = mc.player.getBlockPos().getY();
            int pz = mc.player.getBlockPos().getZ();
            int r = fluidEspRadius.get();
            synchronized (fluids) {
                fluids.clear();

                for (int x = px - r; x <= px + r; x++) {
                    for (int z = pz - r; z <= pz + r; z++) {
                        for (int y = py - r; y <= py + r; y++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            if (mc.world.getBlockState(pos).getFluidState().isStill() && mc.world.getBlockState(pos).getFluidState().getLevel() == 8) {
                                fluids.add(pos);
                            }
                        }
                    }
                }

                fluids.sort(Comparator.comparingDouble((a) -> BlockUtilsWorld.distanceTo(a)));
            }

            wait = fluidScanDelay.get();
        }
    }

    private List<Block> getDefaultWhitelist() {
        return Lists.newArrayList(Blocks.DIRT, Blocks.STONE, Blocks.NETHERRACK, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_BASALT, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_DIORITE, Blocks.SAND, Blocks.GRAVEL, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.NETHERRACK);
    }

    {
        execute = new Thread(() -> {
            isStarted = true;

            while (true) {
                try {
                    if (mc.world != null && mc.player != null && isActive()) {
                        Thread.sleep(delay.get());
                        rpos = null;
                        List<BlockPos> blocks = new ArrayList();
                        FindItemResult item;
                        if (slab.get()) {
                            item = InvUtils.findInHotbar((a) -> a.getItem() instanceof BlockItem && Block.getBlockFromItem(a.getItem()) instanceof SlabBlock);
                            if (!item.found()) {
                                item = InvUtils.find((a) -> a.getItem() instanceof BlockItem && Block.getBlockFromItem(a.getItem()) instanceof SlabBlock);
                            }
                        } else {
                            item = InvUtils.findInHotbar((itemStack) -> itemStack.getItem() instanceof BlockItem && (whitelist.get()).contains(Block.getBlockFromItem(itemStack.getItem())));
                            if (!item.found()) {
                                item = InvUtils.find((itemStack) -> itemStack.getItem() instanceof BlockItem && (whitelist.get()).contains(Block.getBlockFromItem(itemStack.getItem())));
                            }
                        }

                        if (item.found()) {
                            int px = mc.player.getBlockPos().getX();
                            int py = mc.player.getBlockPos().getY();
                            int pz = mc.player.getBlockPos().getZ();

                            int slot;
                            for (int x = px - 4; x <= px + 4; x++) {
                                for (int z = pz - 4; z <= pz + 4; z++) {
                                    for (slot = py - 4; slot <= py + 5; ++slot) {
                                        BlockPos posx = new BlockPos(x, slot, z);
                                        BlockState state = mc.world.getBlockState(posx);
                                        if (state.getFluidState().getLevel() == 8 && state.getFluidState().isStill() && !(BlockUtilsWorld.distanceTo(posx) > radius.get()) && (placeInLiquids.get() == PlaceIn.Both || placeInLiquids.get() == PlaceIn.Lava && state.getBlock() == Blocks.LAVA || placeInLiquids.get() == PlaceIn.Water && state.getBlock() == Blocks.WATER)) {
                                            blocks.add(posx);
                                        }
                                    }
                                }
                            }

                            if (SortBlocksMode.get() != SortMode.None) {
                                blocks.sort(Comparator.comparingDouble((a) -> BlockUtilsWorld.distanceTo(a) * (SortBlocksMode.get() == SortMode.Closest ? 1 : -1)));
                            }

                            for (BlockPos pos : blocks) {
                                if (item.getSlot() > 8) {
                                    slot = -1;
                                    int[] hotbar = new int[]{4, 5, 3, 6, 2, 7, 1, 8, 0};

                                    for (byte q = 0; q < 9; ++q) {
                                        if (!(mc.player.getInventory().getStack(hotbar[q]).getItem() instanceof ToolItem)) {
                                            slot = hotbar[q];
                                            break;
                                        }
                                    }

                                    if (slot == -1) {
                                        slot = Utils.random(0, 8);
                                    }

                                    BlockUtilsWorld.clickSlot(item.getSlot(), slot, SlotActionType.SWAP);
                                    Thread.sleep(100L);
                                    break;
                                }

                                if (BlockUtilsWorld.distanceTo(pos) <= radius.get() && BlockUtilsWorld.setBlock().POS(pos).SLOT(item.getSlot()).ROTATE(rotate.get()).PACKET(true).S()) {
                                    rpos = pos;
                                    Thread.sleep(delay.get());
                                    if (mc.player.getInventory().getStack(item.getSlot()).isEmpty()) {
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        Thread.sleep(500L);
                    }
                } catch (Exception var11) {
                }
            }
        });
    }

    public enum PlaceIn {
        Lava,
        Water,
        Both
    }
}
