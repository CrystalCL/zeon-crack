package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;

public class FastBreak extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder().name("Delay").description("Ticks delay between send packet.").defaultValue(0).min(0).sliderMax(20).build());
    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder().name("range").description("range.").defaultValue(0).min(0).sliderMax(7).build());
    private final Setting<Boolean> autocity = sgGeneral.add(new BoolSetting.Builder().name("auto-city-break").defaultValue(true).build());
    private final Setting<Boolean> crystalbreak = sgGeneral.add(new BoolSetting.Builder().name("crystal-aura-break").defaultValue(false).build());
    private final Setting<Boolean> smash = sgGeneral.add(new BoolSetting.Builder().name("smash").description("Destroy the block instantly.").defaultValue(true).build());
    private final Setting<Boolean> obbyonly = sgGeneral.add(new BoolSetting.Builder().name("only-obsidian").description("Break obsidian only.").defaultValue(false).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("rotate for a place crystal.").defaultValue(false).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 10)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(152, 251, 152, 255)).build());

    private PlayerEntity target;
    BlockPos pos = null;
    private int ticks;
    boolean isBreak = false;

    public FastBreak() {
        super(CrystalCL.Exc, "insta-break", "Fast block Break.");
    }

    public void onActivate() {
        pos = null;
    }

    @EventHandler
    private void onRender(Render3DEvent e) {
        if (render.get() && pos != null) {
            e.renderer.box(pos, sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
        }
    }

    @EventHandler
    private void AUTO_CITY(PacketEvent.Send e) {
        if (e.packet instanceof PlayerActionC2SPacket && autocity.get()) {
            PlayerActionC2SPacket p = (PlayerActionC2SPacket) e.packet;
            if (p.getAction().toString() == "START_DESTROY_BLOCK") {
                pos = p.getPos();
            }
        }
    }

    @EventHandler
    public void StartBreakingBlockEvent(StartBreakingBlockEvent e) {
        pos = e.blockPos;
        Block b = mc.world.getBlockState(pos).getBlock();
        if (obbyonly.get() && b != Blocks.OBSIDIAN) {
            pos = null;
        } else if (b != Blocks.BEDROCK && b != Blocks.NETHER_PORTAL && b != Blocks.END_GATEWAY && b != Blocks.END_PORTAL && b != Blocks.END_PORTAL_FRAME && b != Blocks.BARRIER) {
            Block[] block_pickaxe = new Block[]{Blocks.STONE, Blocks.COBBLESTONE, Blocks.NETHERRACK, Blocks.TERRACOTTA, Blocks.BASALT, Blocks.FURNACE, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.BONE_BLOCK};
            String[] block_axe = new String[]{"acacia_", "oak_", "crimson_", "birch_", "warped_", "jungle_", "spruce_", "crafting_table"};
            String[] block_pickaxe2 = new String[]{"stone_", "andesite", "diorite", "granite", "cobblestone_", "mossy_", "_terracotta", "basalt", "blackstone", "end_", "purpur_", "shulker_box"};
            boolean insta = false;
            if (smash.get() && mc.player.isOnGround()) {
                int x;
                if (mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE) {
                    if (Arrays.asList(block_pickaxe).contains(b)) {
                        insta = true;
                    }

                    for (x = 0; x < block_pickaxe2.length; x++) {
                        if (b.asItem().toString().contains(block_pickaxe2[x])) {
                            insta = true;
                            break;
                        }
                    }
                }

                if (mc.player.getMainHandStack().getItem() == Items.NETHERITE_AXE) {
                    for (x = 0; x < block_axe.length; x++) {
                        if (b.asItem().toString().contains(block_axe[x])) {
                            insta = true;
                            break;
                        }
                    }
                }

                if (b.asItem().toString().contains("_leaves")) {
                    insta = false;
                }

                if (b.asItem().toString().contains("_wart")) {
                    insta = false;
                }

                if (insta) {
                    mc.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        } else {
            pos = null;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        target = CityUtils.getPlayerTarget(range.get());
        int cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
        if (ticks >= delay.get()) {
            ticks = 0;
            if (pos == null) {
                return;
            }

            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));
            if (crystalbreak.get() && target != null) {
                interactCrystal(pos, cry, Direction.UP);
            }
        } else {
            ticks++;
        }
    }

    private void interactCrystal(BlockPos blockPos, int slot, Direction direction) {
        int preSlot = mc.player.getInventory().selectedSlot;
        InvUtils.swap(slot, false);
        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), direction, blockPos, true));
        InvUtils.swap(preSlot, false);
    }
}
