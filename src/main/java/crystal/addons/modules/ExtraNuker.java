package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.world.TickRate;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExtraNuker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup gsize = settings.createGroup("Size");
    private final Setting<Boolean> onlyOnGround = sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(true).build());
    private final Setting<eType> itemsaver = sgGeneral.add(new EnumSetting.Builder<eType>().name("item-saver").description("Prevent destruction of tools.").defaultValue(eType.Replace).build());
    private final Setting<Boolean> sword = sgGeneral.add(new BoolSetting.Builder().name("stop-on-sword").description("Pause nuker if sword in main hand.").defaultValue(true).build());
    private final Setting<Boolean> checkchunk = sgGeneral.add(new BoolSetting.Builder().name("chunk-border").description("Break blocks in only current chunk.").defaultValue(false).build());
    private final Setting<Boolean> ignoreChests = sgGeneral.add(new BoolSetting.Builder().name("ignore-chests").description("Ignore chests and shulker box.").defaultValue(true).build());
    private final Setting<SortMode> sortMode = sgGeneral.add(new EnumSetting.Builder<SortMode>().name("sort-mode").description("The blocks you want to mine first.").defaultValue(SortMode.Closest).build());
    private final Setting<Integer> spamlimit = sgGeneral.add(new IntSetting.Builder().name("speed").description("Block break speed.").defaultValue(29).min(1).sliderMin(1).sliderMax(100).build());
    private final Setting<Double> lagg = sgGeneral.add(new DoubleSetting.Builder().name("stop-on-lags").description("Pause on server lagging. (Time since last tick)").defaultValue(0.8D).min(0.1D).max(5.0D).sliderMin(0.1D).sliderMax(5.0D).build());
    private final Setting<Double> Distance = sgGeneral.add(new DoubleSetting.Builder().name("distance").description("Maximum distance.").min(1.0D).defaultValue(6.6D).build());
    private final Setting<Boolean> onlySelected = sgGeneral.add(new BoolSetting.Builder().name("only-selected").description("Only mines your selected blocks.").defaultValue(false).build());
    private final Setting<List<Block>> selectedBlocks = sgGeneral.add(new BlockListSetting.Builder().name("selected-blocks").description("The certain type of blocks you want to mine.").defaultValue(new ArrayList(0)).build());
    private final Setting<Integer> xmin = gsize.add(new IntSetting.Builder().name("x-min").defaultValue(1).min(0).max(6).sliderMin(0).sliderMax(6).build());
    private final Setting<Integer> xmax = gsize.add(new IntSetting.Builder().name("x-max").defaultValue(1).min(0).max(6).sliderMin(0).sliderMax(6).build());
    private final Setting<Integer> zmin = gsize.add(new IntSetting.Builder().name("z-min").defaultValue(1).min(0).max(6).sliderMin(0).sliderMax(6).build());
    private final Setting<Integer> zmax = gsize.add(new IntSetting.Builder().name("z-max").defaultValue(1).min(0).max(6).sliderMin(0).sliderMax(6).build());
    private final Setting<Integer> ymin = gsize.add(new IntSetting.Builder().name("up").defaultValue(1).min(1).max(6).sliderMin(1).sliderMax(6).build());
    private final Setting<Integer> ymax = gsize.add(new IntSetting.Builder().name("down").defaultValue(0).min(0).max(7).sliderMin(0).sliderMax(7).build());

    int limit = 0;
    byte pause = 0;
    private final List<BlockPos> blocks = new ArrayList();

    public ExtraNuker() {
        super(CrystalCL.PvE, "Nuker+", "Breaks a large amount of specified blocks around you.");
    }

    public void onActivate() {
        limit = 0;
        pause = 0;
    }

    @EventHandler(priority = Integer.MIN_VALUE)
    private void ADD_LIMIT(PacketEvent.Send e) {
        if (!e.isCancelled()) {
            ++limit;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        try {
            blocks.clear();
            if (pause > 0) {
                --pause;
                return;
            }

            if (onlyOnGround.get() && !mc.player.isOnGround()) {
                return;
            }

            if (TickRate.INSTANCE.getTimeSinceLastTick() >= lagg.get()) {
                return;
            }

            if (sword.get() && mc.player.getMainHandStack().getItem() instanceof SwordItem) {
                return;
            }

            limit = 0;
            int px = mc.player.getBlockPos().getX();
            int py = mc.player.getBlockPos().getY();
            int pz = mc.player.getBlockPos().getZ();

            for (int x = px - xmin.get(); x <= px + xmax.get(); x++) {
                for (int z = pz - zmin.get(); z <= pz + zmax.get(); z++) {
                    for (int y = py - ymax.get(); y <= py + ymin.get() - 1; y++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        Block b = mc.world.getBlockState(pos).getBlock();
                        if ((!checkchunk.get() || mc.world.getChunk(pos).getPos() == mc.world.getChunk(mc.player.getBlockPos()).getPos()) && mc.world.getBlockState(pos).getOutlineShape(mc.world, pos) != VoxelShapes.empty() && b != Blocks.BEDROCK && !(distance(pos.getX(), pos.getY(), pos.getZ()) >= Distance.get()) && (!onlySelected.get() || (selectedBlocks.get()).contains(b)) && (!ignoreChests.get() || b != Blocks.CHEST && b != Blocks.TRAPPED_CHEST && b != Blocks.ENDER_CHEST && b != Blocks.SHULKER_BOX && !b.toString().contains("_shulker_box"))) {
                            blocks.add(pos);
                        }
                    }
                }
            }

            double pX = mc.player.getX() - 0.5D;
            double pY = mc.player.getY();
            double pZ = mc.player.getZ() - 0.5D;
            if (sortMode.get() != SortMode.None) {
                blocks.sort(Comparator.comparingDouble((value) -> Utils.squaredDistance(pX, pY, pZ, value.getX(), value.getY(), value.getZ()) * (sortMode.get() == SortMode.Closest ? 1 : -1)));
            }

            int q;
            switch (itemsaver.get()) {
                case Save:
                    if (isbreak()) {
                        warning("save mode...!");
                        toggle();
                        return;
                    }
                case Replace:
                    if (isbreak()) {
                        if (swap_item()) {
                            pause = 5;
                            return;
                        }

                        warning("replace mode...!");
                        toggle();
                        return;
                    }
                case None:
                default:
                    q = 0;
            }

            while (q < blocks.size()) {
                if (limit > spamlimit.get()) {
                    return;
                }

                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, (BlockPos) blocks.get(q), Direction.UP));
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, (BlockPos) blocks.get(q), Direction.UP));
                ++q;
            }
        } catch (Exception ignored) {
            ignored.fillInStackTrace();
        }
    }

    private boolean isbreak() {
        return mc.player.getMainHandStack().getDamage() != 0 && mc.player.getMainHandStack().getMaxDamage() - mc.player.getMainHandStack().getDamage() < 31;
    }

    private boolean swap_item() {
        Item item = mc.player.getMainHandStack().getItem();

        for (int x = 0; x < mc.player.getInventory().size(); x++) {
            if (mc.player.getInventory().getStack(x).getItem() == item && mc.player.getInventory().getStack(x).getMaxDamage() - mc.player.getInventory().getStack(x).getDamage() >= 31) {
                Ezz.clickSlot(Ezz.invIndexToSlotId(x), mc.player.getInventory().selectedSlot, SlotActionType.SWAP);
                return true;
            }
        }

        return false;
    }

    private double distance(double x, double y, double z) {
        if (x > 0.0D) {
            x += 0.5D;
        } else {
            x -= 0.5D;
        }

        if (y > 0.0D) {
            y += 0.5D;
        } else {
            y -= 0.5D;
        }

        if (z > 0.0D) {
            z += 0.5D;
        } else {
            z -= 0.5D;
        }

        double d = mc.player.getPos().getX() - x;
        if (d < 0.0D) {
            --d;
        }

        double e = mc.player.getPos().getY() + 1.0D - y;
        double f = mc.player.getPos().getZ() - z;
        if (f < 0.0D) {
            --f;
        }

        return Math.sqrt(d * d + e * e + f * f);
    }

    public enum eType {
        None,
        Save,
        Replace
    }

    public enum SortMode {
        None,
        Closest,
        Furthest
    }
}
