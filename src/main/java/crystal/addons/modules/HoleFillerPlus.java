package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockIterator;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class HoleFillerPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> hRadius = sgGeneral.add(new IntSetting.Builder().name("h-radius").description("Horizontal radius.").defaultValue(4).min(0).sliderMax(6).build());
    private final Setting<Integer> vRadius = sgGeneral.add(new IntSetting.Builder().name("v-radius").description("Vertical radius.").defaultValue(4).min(0).sliderMax(6).build());
    private final Setting<Integer> Delay = sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay per ticks between placement.").defaultValue(1).min(0).sliderMax(10).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the placing block.").defaultValue(true).build());

    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
    private int tickDelay;
    private final SetBlockResult RESULT = new SetBlockResult();

    public HoleFillerPlus() {
        super(CrystalCL.Exc, "hole-filler+", "Hole Filler+.");
    }

    public void onActivate() {
        tickDelay = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        int slot = findSlot();
        if (slot != -1 && tickDelay <= 0) {
            tickDelay = Delay.get();
            BlockIterator.register(hRadius.get(), vRadius.get(), (blockPos1, blockState) -> {
                if (blockState.getMaterial().isReplaceable()) {
                    blockPos.set(blockPos1);
                    Block bottom = mc.world.getBlockState(add(0, -1, 0)).getBlock();
                    if (bottom == Blocks.BEDROCK || bottom == Blocks.OBSIDIAN) {
                        Block forward = mc.world.getBlockState(add(0, 1, 1)).getBlock();
                        if (forward == Blocks.BEDROCK || forward == Blocks.OBSIDIAN) {
                            Block back = mc.world.getBlockState(add(0, 0, -2)).getBlock();
                            if (back == Blocks.BEDROCK || back == Blocks.OBSIDIAN) {
                                Block right = mc.world.getBlockState(add(1, 0, 1)).getBlock();
                                if (right == Blocks.BEDROCK || right == Blocks.OBSIDIAN) {
                                    Block left = mc.world.getBlockState(add(-2, 0, 0)).getBlock();
                                    if (left == Blocks.BEDROCK || left == Blocks.OBSIDIAN) {
                                        add(1, 0, 0);
                                        if (setBlock().POS(blockPos).SLOT(slot).ROTATE(rotate.get()).PACKET(true).S()) {
                                            BlockIterator.disableCurrent();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        tickDelay--;
    }

    private int findSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack.getItem() == Items.OBSIDIAN || itemStack.getItem() == Items.CRYING_OBSIDIAN) {
                return i;
            }
        }

        return -1;
    }

    private BlockPos.Mutable add(int x, int y, int z) {
        blockPos.setX(blockPos.getX() + x);
        blockPos.setY(blockPos.getY() + y);
        blockPos.setZ(blockPos.getZ() + z);
        return blockPos;
    }

    public SetBlockResult setBlock() {
        return RESULT;
    }

    public int invIndexToSlotId(int invIndex) {
        return invIndex < 9 && invIndex != -1 ? 44 - (8 - invIndex) : invIndex;
    }

    public void swap(int s) {
        if (s != mc.player.getInventory().selectedSlot && s >= 0 && s < 9) {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(s));
            mc.player.getInventory().selectedSlot = s;
        }
    }

    public class SetBlockResult {
        private int slot = -1;
        private BlockPos pos = null;
        private Direction direct;
        private boolean rotate;
        private boolean noback;
        private boolean packet;

        public SetBlockResult() {
            direct = Direction.DOWN;
            rotate = false;
            noback = false;
            packet = false;
        }

        public SetBlockResult POS(BlockPos s) {
            pos = s;
            return this;
        }

        public SetBlockResult DIRECTION(Direction s) {
            direct = s;
            return this;
        }

        public SetBlockResult ROTATE(boolean s) {
            rotate = s;
            return this;
        }

        public SetBlockResult XYZ(int x, int y, int z) {
            pos = new BlockPos(x, y, z);
            return this;
        }

        public SetBlockResult RELATIVE_XYZ(int x, int y, int z) {
            pos = new BlockPos(mc.player.getBlockPos().getX() + x, mc.player.getBlockPos().getY() + y, mc.player.getBlockPos().getZ() + z);
            return this;
        }

        public SetBlockResult NOBACK() {
            noback = true;
            return this;
        }

        public SetBlockResult PACKET(boolean s) {
            packet = s;
            return this;
        }

        public SetBlockResult SLOT(int s) {
            slot = s;
            return this;
        }

        public SetBlockResult INDEX_SLOT(int s) {
            slot = invIndexToSlotId(s);
            return this;
        }

        private void reset() {
            slot = -1;
            pos = null;
            direct = Direction.DOWN;
            rotate = false;
            noback = false;
            packet = false;
        }

        public boolean S() {
            if (pos != null && slot != -1 && !mc.player.getInventory().getStack(slot).isEmpty() && mc.player.getInventory().getStack(slot).getItem() instanceof BlockItem) {
                if (!BlockUtils.canPlace(pos, true)) {
                    reset();
                    return false;
                } else {
                    int PreSlot = mc.player.getInventory().selectedSlot;
                    swap(slot);
                    if (rotate) {
                        Vec3d hitPos = new Vec3d(0.0D, 0.0D, 0.0D);
                        ((IVec3d) hitPos).set(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
                        Rotations.rotate(Rotations.getYaw(hitPos), Rotations.getPitch(hitPos));
                    }

                    BlockHitResult hitresult = new BlockHitResult(mc.player.getPos(), direct, pos, true);
                    if (packet) {
                        mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitresult));
                    } else {
                        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, hitresult);
                    }

                    if (!noback) {
                        swap(PreSlot);
                    }

                    reset();
                    return true;
                }
            } else {
                reset();
                return false;
            }
        }
    }
}
