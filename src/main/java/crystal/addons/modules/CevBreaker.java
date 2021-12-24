package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;

public class CevBreaker extends Module {
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRotate = settings.createGroup("Rotate");
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Integer> placeDelay = sgPlace.add(new IntSetting.Builder().name("place-delay").description("Ticks delay before placing.").defaultValue(4).min(0).sliderMax(20).build());
    private final Setting<Double> placeRange = sgPlace.add(new DoubleSetting.Builder().name("place-range").description("Place blocks/crystals range.").defaultValue(5.0D).min(0.0D).max(6.0D).build());
    private final Setting<Double> breakRange = sgBreak.add(new DoubleSetting.Builder().name("break-range").description("Break blocks/crystals range.").defaultValue(5.0D).min(0.0D).max(6.0D).build());
    private final Setting<BreakMode> breakMode = sgPlace.add(new EnumSetting.Builder<BreakMode>().name("Mode").description("Break mode for cev.").defaultValue(BreakMode.Normal).build());
    private final Setting<Integer> breakDelay = sgBreak.add(new IntSetting.Builder().name("break-delay").description("Ticks delay before breaking.").defaultValue(4).min(0).sliderMax(20).visible(() -> breakMode.get() == BreakMode.InstaMine).build());
    private final Setting<Boolean> swapPick = sgPlace.add(new BoolSetting.Builder().name("pick-swap").description("Automatically swaps to pickaxe slot.").defaultValue(true).build());
    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnBurrow = sgPause.add(new BoolSetting.Builder().name("pause-on-burrow").description("Pauses while target is burrowed.").defaultValue(false).build());
    private final Setting<Boolean> rotate = sgRotate.add(new BoolSetting.Builder().name("allow-rotate").description("Allow rotate for CevBreaker.").defaultValue(true).build());
    private final Setting<RotationMode> rMode = sgRotate.add(new EnumSetting.Builder<RotationMode>().name("rotation-mode").description("Rotation mode.").defaultValue(RotationMode.None).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay over the block the crystals are being placed on.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).visible(render::get).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color of the block overlay.").defaultValue(new SettingColor(255, 255, 255, 45)).visible(render::get).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color of the block overlay.").defaultValue(new SettingColor(255, 255, 255)).visible(render::get).build());

    public CevBreaker() {
        super(CrystalCL.Exc, "cev-breaker", "Auto crystal head, but hasn't instamine");
    }

    @EventHandler
    private void onTick(TickEvent.Pre e) {
        if (breakMode.get() == BreakMode.Normal) {
            PlayerEntity target = CityUtils.getPlayerTarget(placeRange.get());
            BlockPos targetHead2 = target.getBlockPos().up(2);
            BlockPos targetHead3 = target.getBlockPos().up(3);
            if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get()) && (!pauseOnBurrow.get() || !BlockUtilsWorld.isBurrowed(target))) {
                int obsidian;
                int pickaxe;
                Iterator var7;
                Entity entity;
                if (BlockUtilsWorld.getBlock(targetHead2) != Blocks.AIR) {
                    if (BlockUtilsWorld.getBlock(targetHead2) == Blocks.OBSIDIAN) {
                        obsidian = InvUtils.findInHotbar(Items.OBSIDIAN).getSlot();
                        if (obsidian == -1) {
                            error("No obsidian in hotbar! Disabling...");
                            toggle();
                        } else {
                            pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                            if (pickaxe == -1) {
                                pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                            }

                            if (pickaxe == -1) {
                                pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                            }

                            if (pickaxe == -1) {
                                error("No pickaxe in hotbar! Disabling...");
                                toggle();
                            } else {
                                if (BlockUtilsWorld.getBlock(targetHead3) == Blocks.AIR) {
                                    doPlace(target, targetHead3);
                                } else {
                                    if (BlockUtilsWorld.getBlock(targetHead3) == Blocks.AIR || BlockUtilsWorld.getBlock(targetHead3) == Blocks.BEDROCK) {
                                        return;
                                    }

                                    doBreak(targetHead3, pickaxe);
                                }

                                doBreak(targetHead2, pickaxe);
                                var7 = mc.world.getEntities().iterator();

                                while (var7.hasNext()) {
                                    entity = (Entity) var7.next();
                                    attackCrystal(entity);
                                }

                            }
                        }
                    }
                } else {
                    obsidian = InvUtils.findInHotbar(Items.OBSIDIAN).getSlot();
                    if (obsidian == -1) {
                        error("No obsidian in hotbar! Disabling...");
                        toggle();
                    } else {
                        pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                        if (pickaxe == -1) {
                            pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                        }

                        if (pickaxe == -1) {
                            pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                        }

                        if (pickaxe == -1) {
                            error("No pickaxe in hotbar! Disabling...");
                            toggle();
                        } else {
                            InvUtils.swap(obsidian, false);
                            placeObsidian(targetHead2, obsidian, rMode.get() == RotationMode.Place || rMode.get() == RotationMode.Both);
                            if (BlockUtilsWorld.getBlock(targetHead3) == Blocks.AIR) {
                                doPlace(target, targetHead3);
                            } else {
                                if (BlockUtilsWorld.getBlock(targetHead3) == Blocks.AIR || BlockUtilsWorld.getBlock(targetHead3) == Blocks.BEDROCK) {
                                    return;
                                }

                                doBreak(targetHead3, pickaxe);
                            }

                            doBreak(targetHead2, pickaxe);
                            var7 = mc.world.getEntities().iterator();

                            while (var7.hasNext()) {
                                entity = (Entity) var7.next();
                                attackCrystal(entity);
                            }

                        }
                    }
                }
            }
        }
    }

    public void attackCrystal(Entity entity) {
        if (entity instanceof EndCrystalEntity) {
            BlockPos crystalPos = entity.getBlockPos();
            if (BlockUtilsWorld.distanceBetween(mc.player.getBlockPos(), crystalPos) <= breakRange.get()) {
                if (rMode.get() == RotationMode.Break || rMode.get() == RotationMode.Both) {
                    look(entity.getBlockPos());
                    mc.interactionManager.attackEntity(mc.player, entity);
                }

                entity.remove(Entity.RemovalReason.KILLED);
            }
        }
    }

    public void placeObsidian(BlockPos pos, int slot, boolean rotate) {
        BlockUtils.place(pos, Hand.MAIN_HAND, slot, rotate, 0, false, false, false);
    }

    private void doPlace(PlayerEntity playerEntity, BlockPos blockPos) {
        BlockPos blockPos1 = new BlockPos(playerEntity.getBlockPos().getX(), playerEntity.getBlockPos().getY() + 3, playerEntity.getBlockPos().getZ());
        if (BlockUtils.canPlace(blockPos1, true)) {
            if (mc.world.getBlockState(blockPos1).isAir()) {
                int cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                if (cry == -1) {
                    error("No crystal in hotbar! Disabling...");
                    toggle();
                } else {
                    if (rMode.get() != RotationMode.Place && rMode.get() != RotationMode.Both) {
                        interactCrystal(blockPos, cry, Direction.UP);
                    } else {
                        look(blockPos);
                        interactCrystal(blockPos, cry, Direction.UP);
                    }

                }
            }
        }
    }

    public void doBreak(BlockPos blockPos, int slot) {
        if (blockPos == null) return;
        if (!(BlockUtilsWorld.distanceBetween(blockPos, mc.player.getBlockPos()) > breakRange.get())) {
            boolean a = true;
            if (breakMode.get() == BreakMode.Normal) {
                InvUtils.swap(slot, false);
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));

                InvUtils.swap(slot, false);
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
            } else if (breakMode.get() == BreakMode.InstaMine) {
                if (a) {
                    InvUtils.swap(slot, false);
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                    a = false;
                } else if (!a) {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                }
            }
        }
    }

    private void interactCrystal(BlockPos blockPos, int slot, Direction direction) {
        int preSlot = mc.player.getInventory().selectedSlot;
        InvUtils.swap(slot, false);
        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), direction, blockPos, true));
        InvUtils.swap(preSlot, false);
    }

    private void look(BlockPos a) {
        if (rotate.get()) {
            Vec3d hitPos = new Vec3d(0.0D, 0.0D, 0.0D);
            ((IVec3d) hitPos).set(a.getX() + 0.5D, a.getY() + 0.5D, a.getZ() + 0.5D);
            Rotations.rotate(Rotations.getYaw(hitPos), Rotations.getPitch(hitPos));
        }
    }

    public enum BreakMode {
        Normal,
        InstaMine
    }

    public enum RotationMode {
        None,
        Place,
        Break,
        Both
    }
}
