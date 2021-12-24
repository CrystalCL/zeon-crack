package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.BreakIndicators;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class InstaAutoCity extends Module {
    private final SettingGroup sgTarget = settings.createGroup("Target");
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgMisc = settings.createGroup("Misc");
    private final SettingGroup sgRotate = settings.createGroup("Rotations");
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Integer> delay = sgBreak.add(new IntSetting.Builder().name("delay").description("Delay per ticks").defaultValue(3).min(0).sliderMax(30).build());
    private final Setting<Mode> b = sgBreak.add(new EnumSetting.Builder<Mode>().name("Mode").description("The mode.").defaultValue(Mode.Normal).build());
    private final Setting<BMode> c = sgBreak.add(new EnumSetting.Builder<BMode>().name("break-mode").description("Break mode.").defaultValue(BMode.Always).build());
    private final Setting<Boolean> cplace = sgPlace.add(new BoolSetting.Builder().name("crystal-place").description("Place crystal inside the breaking block.").defaultValue(false).build());
    private final Setting<Boolean> swap = sgMisc.add(new BoolSetting.Builder().name("swap").description("Switches to a pickaxe automatically.").defaultValue(true).build());
    private final Setting<Boolean> backswap = sgMisc.add(new BoolSetting.Builder().name("back-swap").description("Swap to a selected automatically.").defaultValue(true).build());
    private final Setting<Boolean> chatInfo = sgMisc.add(new BoolSetting.Builder().name("chat-info").description("Info.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnEat = sgMisc.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnDrink = sgMisc.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
    private final Setting<Double> range = sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The range which player will be target.").defaultValue(5.0D).min(0.0D).sliderMax(20.0D).build());
    private final Setting<Boolean> rotate = sgRotate.add(new BoolSetting.Builder().name("rotate").description("See on the city block.").defaultValue(true).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders block break.").defaultValue(true).build());

    BlockPos pos = null;
    private int ticks;
    private PlayerEntity target;
    private List<BlockPos> placePositions = new ArrayList();
    private boolean placed;
    boolean cancel = false;

    public InstaAutoCity() {
        super(CrystalCL.Exc, "insta-auto-city", "Break player's surround.");
    }

    public void onActivate() {
        ticks = 0;
        BlockPos mineTarget;
        if (c.get() == BMode.Normal) {
            target = CityUtils.getPlayerTarget(range.get());
            mineTarget = CityUtils.getTargetBlock(target);
            if (target != null && mineTarget != null) {
                if (chatInfo.get()) {
                    info("Break city " + target.getGameProfile().getName());
                }

                if (Math.sqrt(mc.player.squaredDistanceTo(mineTarget.getX(), mineTarget.getY(), mineTarget.getZ())) > 6.0D) {
                    if (chatInfo.get()) {
                        error("Target block out of reach... disabling.");
                    }

                    toggle();
                    return;
                }

                if (rotate.get()) {
                    Rotations.rotate(Rotations.getYaw(mineTarget), Rotations.getPitch(mineTarget), () -> {
                        mine(mineTarget);
                    });
                } else {
                    mine(mineTarget);
                }
            } else if (chatInfo.get()) {
                error("Target block not found... disabling.");
            }

            if (b.get() == Mode.Normal) {
                toggle();
            }
        } else if (c.get() == BMode.Always && b.get() == Mode.Fast) {
            target = CityUtils.getPlayerTarget(range.get());
            mineTarget = CityUtils.getTargetBlock(target);
            if (target != null && mineTarget != null) {
                if (chatInfo.get()) {
                    info("Break city " + target.getGameProfile().getName());
                }

                if (Math.sqrt(mc.player.squaredDistanceTo(mineTarget.getX(), mineTarget.getY(), mineTarget.getZ())) > 6.0D) {
                    if (chatInfo.get()) {
                        error("Target block out of reach... disabling.");
                    }

                    toggle();
                    return;
                }

                if (rotate.get()) {
                    Rotations.rotate(Rotations.getYaw(mineTarget), Rotations.getPitch(mineTarget), () -> {
                        start(mineTarget);
                    });
                } else {
                    start(mineTarget);
                }
            } else if (chatInfo.get()) {
                error("Target block not found... disabling.");
            }
        }
    }

    private void mine(BlockPos blockPos) {
        int pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE, Items.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE).getSlot();
        if (pickaxe == -1) {
            error("There is no pickaxe in the quick access bar!");
            toggle();
        } else {
            int preSlot = mc.player.getInventory().selectedSlot;
            target = CityUtils.getPlayerTarget(range.get());
            BlockPos mneTarget = CityUtils.getTargetBlock(target);
            if (target != null && mneTarget != null) {
                if (swap.get()) {
                    InvUtils.swap(pickaxe, false);
                }

                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
            }

            if (swap.get()) {
                InvUtils.swap(pickaxe, false);
            }

            if (target != null && mneTarget != null) {
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                pos = blockPos;
                if (cplace.get()) {
                    placed = false;
                    placePositions.clear();
                    int cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                    if (cry == -1) {
                        return;
                    }

                    findPlacePos(target);

                    for (int x = 0; x < placePositions.size(); x++) {
                        BlockPos a = placePositions.get(placePositions.size() - 1);
                        interactCrystal(a, cry, Direction.UP);
                        boolean f = true;
                        if (f) {
                            placePositions.remove(a);
                            f = false;
                            placed = true;
                        }
                    }
                }
            }

            if (backswap.get()) {
                InvUtils.swap(preSlot, false);
            }
        }
    }

    private void start(BlockPos blockPos) {
        int pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE, Items.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE).getSlot();
        if (pickaxe == -1) {
            error("There is no pickaxe in the quick access bar!");
            toggle();
        } else {
            int preSlot = mc.player.getInventory().selectedSlot;
            target = CityUtils.getPlayerTarget(range.get());
            BlockPos mneTarget = CityUtils.getTargetBlock(target);
            if (target != null && mneTarget != null) {
                if (swap.get()) {
                    InvUtils.swap(pickaxe, false);
                }

                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                if (swap.get()) {
                    InvUtils.swap(pickaxe, false);
                }
            }
        }
    }

    @EventHandler
    private void a(PacketEvent.Send e) {
        if (cancel) {
            Packet var3 = e.packet;
            if (var3 instanceof PlayerActionC2SPacket) {
                PlayerActionC2SPacket p = (PlayerActionC2SPacket) var3;
                if (p.getAction().name().contains("DESTROY_BLOCK")) {
                    e.cancel();
                }
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get())) {
            if (pos != null && render.get()) {
                BreakIndicators bi = Modules.get().get(BreakIndicators.class);
                if (!bi.isActive()) {
                    bi.toggle();
                    bi.sendToggledMsg();
                }

                cancel = true;
                mc.interactionManager.updateBlockBreakingProgress(pos, Direction.DOWN);
                cancel = false;
            }

            if (c.get() == BMode.Always) {
                int pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE, Items.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE).getSlot();
                if (pickaxe == -1) {
                    error("There is no pickaxe in the quick access bar!");
                    toggle();
                    return;
                }

                BlockPos mneTarget;
                if (b.get() == Mode.Normal) {
                    target = CityUtils.getPlayerTarget(range.get());
                    mneTarget = CityUtils.getTargetBlock(target);
                    mine(mneTarget);
                } else if (b.get() == Mode.Fast) {
                    target = CityUtils.getPlayerTarget(range.get());
                    mneTarget = CityUtils.getTargetBlock(target);
                    int preSlot = mc.player.getInventory().selectedSlot;
                    if (target != null && mneTarget != null) {
                        if (ticks >= delay.get()) {
                            ticks = 0;
                            if (swap.get()) {
                                InvUtils.swap(pickaxe, false);
                            }

                            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mneTarget, Direction.UP));
                            pos = mneTarget;
                        } else {
                            ticks++;
                        }

                        if (cplace.get()) {
                            placed = false;
                            placePositions.clear();
                            int cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                            if (cry == -1) {
                                return;
                            }

                            findPlacePos(target);

                            for (int x = 0; x < placePositions.size(); x++) {
                                BlockPos blockPos = placePositions.get(placePositions.size() - 1);
                                interactCrystal(blockPos, cry, Direction.UP);
                                boolean f = true;
                                if (f) {
                                    placePositions.remove(blockPos);
                                    f = false;
                                    placed = true;
                                }
                            }
                        }

                        if (backswap.get()) {
                            InvUtils.swap(preSlot, false);
                        }
                    }
                }
            }
        }
    }

    private void add(BlockPos blockPos1) {
        if (!placePositions.contains(blockPos1) && mc.world.getBlockState(blockPos1).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPos1, ShapeContext.absent())) {
            placePositions.add(blockPos1);
        }
    }

    private void findPlacePos(PlayerEntity target1) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(1, 0, 0));
        add(targetPos.add(0, 0, -1));
        add(targetPos.add(-1, 0, 0));
        add(targetPos.add(0, 0, 1));
    }

    private void interactCrystal(BlockPos blockPos, int slot, Direction direction) {
        int preSlot = mc.player.getInventory().selectedSlot;
        InvUtils.swap(slot, false);
        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), direction, blockPos, true));
        InvUtils.swap(preSlot, false);
    }

    public String getInfoString() {
        return target != null ? target.getEntityName() : null;
    }

    public enum Mode {
        Normal,
        Fast
    }

    public enum BMode {
        Normal,
        Always
    }
}
