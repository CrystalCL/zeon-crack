package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.player.*;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.Iterator;

public class BedAuraPlus extends Module {
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgMisc = settings.createGroup("Misc");
    private final SettingGroup sgTarget = settings.createGroup("Target");
    private final SettingGroup sgExtra = settings.createGroup("Extra");
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Mode> breakMode = sgPlace.add(new EnumSetting.Builder<Mode>().name("mode").description("How to select the player to target.").defaultValue(Mode.PlaceBreak).build());
    private final Setting<Boolean> suicide = sgPlace.add(new BoolSetting.Builder().name("allow-suicide").description("Allows suicide mode for BedAura+.").defaultValue(true).build());
    private final Setting<Direct> placeDir = sgPlace.add(new EnumSetting.Builder<Direct>().name("place-direction").description("The direct where bed will be placed.").defaultValue(Direct.EAST).build());
    private final Setting<BreakMode> breakm = sgBreak.add(new EnumSetting.Builder<BreakMode>().name("break-mode").description("How to break beds.").defaultValue(BreakMode.immunity).build());
    private final Setting<Boolean> autoBr = sgBreak.add(new BoolSetting.Builder().name("auto-break").description("Automaticly break.").defaultValue(true).build());
    private final Setting<Boolean> autoB = sgBreak.add(new BoolSetting.Builder().name("self-trap-break").description("Automaticly break target's self-trap/surround/head.").defaultValue(true).visible(autoBr::get).build());
    private final Setting<Prioritet> autoBreak = sgBreak.add(new EnumSetting.Builder<Prioritet>().name("break-priority").description("How to select the position to break.").defaultValue(Prioritet.Face).visible(autoB::get).build());
    private final Setting<Boolean> webBreak = sgBreak.add(new BoolSetting.Builder().name("web-break").description("Automaticly break target's webs.").defaultValue(true).visible(autoBr::get).build());
    private final Setting<Boolean> burrowBreak = sgBreak.add(new BoolSetting.Builder().name("burrow-break").description("Automaticly break target's burrow.").defaultValue(true).visible(autoBr::get).build());
    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnMine = sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnBurrow = sgPause.add(new BoolSetting.Builder().name("pause-on-burrow").description("Pauses while target in burrow.").defaultValue(true).build());
    private final Setting<Boolean> autoSwitch = sgMisc.add(new BoolSetting.Builder().name("switch").description("Switches to a bed automatically.").defaultValue(true).build());
    private final Setting<Boolean> swing = sgMisc.add(new BoolSetting.Builder().name("swing").description("Swinges a hand automaticly.").defaultValue(true).build());
    private final Setting<Boolean> autoMove = sgMisc.add(new BoolSetting.Builder().name("auto-move").description("Moves beds into a selected slot.").defaultValue(true).build());
    private final Setting<Integer> autoMoveSlot = sgMisc.add(new IntSetting.Builder().name("move-slot").description("The slot Auto Move.").defaultValue(9).min(1).sliderMin(1).max(9).sliderMax(9).visible(autoMove::get).build());
    private final Setting<Boolean> antifriendpop = sgExtra.add(new BoolSetting.Builder().name("anti-friend-pop").description("Anti friend pop.").defaultValue(false).build());
    private final Setting<Double> minDamage = sgMisc.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage to inflict on your target.").defaultValue(7.0D).min(0.0D).sliderMax(20.0D).max(20.0D).build());
    private final Setting<Double> maxSelfDamage = sgMisc.add(new DoubleSetting.Builder().name("max-self-dmg").description(".").defaultValue(7.0D).min(0.0D).sliderMax(20.0D).max(20.0D).build());
    private final Setting<Double> minHealth = sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health required for Bed Aura to work.").defaultValue(4.0D).min(0.0D).sliderMax(36.0D).max(36.0D).build());
    private final Setting<Double> targetRange = sgTarget.add(new DoubleSetting.Builder().name("range").description("The maximum range for players to be targeted.").defaultValue(4.0D).min(0.0D).sliderMax(5.0D).build());
    private final Setting<SortPriority> priority = sgTarget.add(new EnumSetting.Builder<SortPriority>().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(15, 255, 211, 255)).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());

    private Direction direction;
    private PlayerEntity target;
    private BlockPos bestPos;
    private BlockPos stb;
    private int breakDelayLeft;
    private int placeDelayLeft;
    private Mode stage;

    public BedAuraPlus() {
        super(CrystalCL.Exc, "bed-aura+", "Bed Aura+");
    }

    public void onActivate() {
        if (breakMode.get() == Mode.Placing) {
            stage = Mode.Placing;
        } else if (breakMode.get() == Mode.Breaking) {
            stage = Mode.Breaking;
        } else if (breakMode.get() == Mode.PlaceBreak) {
            stage = Mode.PlaceBreak;
        }

        bestPos = null;
        if (placeDir.get() == Direct.EAST) {
            direction = Direction.EAST;
        } else if (placeDir.get() == Direct.NORTH) {
            direction = Direction.NORTH;
        } else if (placeDir.get() == Direct.SOUTH) {
            direction = Direction.SOUTH;
        } else if (placeDir.get() == Direct.WEST) {
            direction = Direction.WEST;
        }

        if (breakm.get() == BreakMode.immunity) {
            placeDelayLeft = 0;
            breakDelayLeft = 10;
        } else if (breakm.get() == BreakMode.ignor_immunity) {
            placeDelayLeft = 0;
            breakDelayLeft = 0;
        } else if (breakm.get() == BreakMode.TEST) {
            placeDelayLeft = 10;
            breakDelayLeft = 0;
        } else if (breakm.get() == BreakMode.TESTRUNPVP1) {
            placeDelayLeft = 10;
            breakDelayLeft = 0;
        }
    }

    @EventHandler
    private void Pre(TickEvent.Pre event) {
        if (mc.world.getDimension().isBedWorking()) {
            error("You are in the Overworld... disabling!");
            toggle();
        } else {
            target = TargetUtils.getPlayerTarget(targetRange.get(), priority.get());
            if (!PlayerUtils.shouldPause(pauseOnMine.get(), pauseOnEat.get(), pauseOnDrink.get())) {
                if (!pauseOnBurrow.get() || !BlockUtilsWorld.isBurrowed(target)) {
                    if (!(EntityUtils.getTotalHealth(mc.player) <= minHealth.get())) {
                        target = TargetUtils.getPlayerTarget(targetRange.get(), priority.get());
                        if (target == null) {
                            bestPos = null;
                        } else {
                            if (autoBr.get()) {
                                FindItemResult pick;
                                int pickaxe;
                                if (burrowBreak.get() && BlockUtilsWorld.isBurrowed(target)) {
                                    pick = findPick();
                                    pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                                    if (pickaxe == -1) {
                                        pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                                    }

                                    if (pickaxe == -1) {
                                        pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                                    }

                                    if (pickaxe == -1) {
                                        return;
                                    }

                                    if (pick.found()) {
                                        InvUtils.swap(pickaxe, false);
                                        info("Breaking " + target.getEntityName() + "'s burrow.");
                                        BlockUtilsWorld.Mine(target.getBlockPos(), pickaxe);
                                        return;
                                    }
                                }

                                if (isWebbed(target) && webBreak.get()) {
                                    pick = findSword();
                                    pickaxe = InvUtils.findInHotbar(Items.NETHERITE_SWORD).getSlot();
                                    if (pickaxe == -1) {
                                        pickaxe = InvUtils.findInHotbar(Items.DIAMOND_SWORD).getSlot();
                                    }

                                    if (pickaxe == -1) {
                                        pickaxe = InvUtils.findInHotbar(Items.IRON_SWORD).getSlot();
                                    }

                                    if (pickaxe == -1) {
                                        return;
                                    }

                                    if (pick.found()) {
                                        InvUtils.swap(pickaxe, false);
                                        info("Breaking " + target.getEntityName() + "'s web.");
                                        BlockUtilsWorld.mineWeb(target, pick.getSlot());
                                        return;
                                    }
                                }

                                if (autoB.get()) {
                                    if (autoBreak.get() == Prioritet.Face && BlockUtilsWorld.isTrapped(target)) {
                                        pick = findPick();
                                        pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            return;
                                        }

                                        if (pick.found()) {
                                            InvUtils.swap(pickaxe, false);
                                            info("Breaking " + target.getEntityName() + "'s face.");
                                            stb = BlockUtilsWorld.getSelf(target, false);
                                            BlockUtilsWorld.Mine(stb, pickaxe);
                                            return;
                                        }
                                    } else if (autoBreak.get() == Prioritet.Head && BlockUtilsWorld.isTrapped(target) && BlockUtilsWorld.LowestDist(target) != null) {
                                        pick = findPick();
                                        pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            return;
                                        }

                                        if (pick.found()) {
                                            InvUtils.swap(pickaxe, false);
                                            info("Breaking " + target.getEntityName() + "'s head.");
                                            stb = BlockUtilsWorld.getHead(target, false);
                                            BlockUtilsWorld.Mine(stb, pickaxe);
                                            return;
                                        }
                                    } else if (autoBreak.get() == Prioritet.Head && BlockUtilsWorld.isTrapped(target) && BlockUtilsWorld.LowestDist(target) == null) {
                                        pick = findPick();
                                        pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            return;
                                        }

                                        if (pick.found()) {
                                            InvUtils.swap(pickaxe, false);
                                            info("Breaking " + target.getEntityName() + "'s face.");
                                            stb = BlockUtilsWorld.getSelf(target, false);
                                            BlockUtilsWorld.Mine(stb, pickaxe);
                                            return;
                                        }
                                    } else if (autoBreak.get() == Prioritet.Legs && BlockUtilsWorld.isTrapped(target) && BlockUtilsWorld.isSurrounded(target)) {
                                        pick = findPick();
                                        pickaxe = InvUtils.findInHotbar(Items.NETHERITE_PICKAXE).getSlot();
                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.DIAMOND_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            pickaxe = InvUtils.findInHotbar(Items.IRON_PICKAXE).getSlot();
                                        }

                                        if (pickaxe == -1) {
                                            return;
                                        }

                                        if (pick.found()) {
                                            InvUtils.swap(pickaxe, false);
                                            info("Breaking " + target.getEntityName() + "'s surround.");
                                            stb = BlockUtilsWorld.getSurr(target, false);
                                            BlockUtilsWorld.Mine(stb, pickaxe);
                                            return;
                                        }
                                    }
                                }
                            }

                            if (InvUtils.find((itemStack) -> itemStack.getItem() instanceof BedItem).getCount() != -1) {
                                PlayerEntity p;
                                boolean breakbed;
                                Iterator var6;
                                switch (stage) {
                                    case Placing:
                                        if (breakMode.get() == Mode.Placing) {
                                            bestPos = getPlacePos(target);
                                            if (placeDelayLeft > 0) {
                                                --placeDelayLeft;
                                            } else {
                                                placeBed(bestPos);
                                                if (breakm.get() == BreakMode.immunity) {
                                                    placeDelayLeft = 0;
                                                } else if (breakm.get() == BreakMode.ignor_immunity) {
                                                    placeDelayLeft = 0;
                                                } else if (breakm.get() == BreakMode.TEST) {
                                                    placeDelayLeft = 10;
                                                }

                                                stage = Mode.Placing;
                                            }
                                        }
                                    case Breaking:
                                        if (breakMode.get() == Mode.Breaking) {
                                            bestPos = getBreakPos(target);
                                            if (breakDelayLeft > 0) {
                                                --breakDelayLeft;
                                            } else {
                                                breakbed = true;
                                                if (antifriendpop.get()) {
                                                    var6 = mc.world.getPlayers().iterator();

                                                    while (var6.hasNext()) {
                                                        p = (PlayerEntity) var6.next();
                                                        if (!Friends.get().shouldAttack(p) && !mc.player.equals(p) && distanceBetween(bestPos, p.getBlockPos()) <= 4.0D) {
                                                            breakbed = false;
                                                        }
                                                    }
                                                }

                                                if (breakbed) {
                                                    breakBed(bestPos);
                                                    if (breakm.get() == BreakMode.immunity) {
                                                        breakDelayLeft = 10;
                                                    } else if (breakm.get() == BreakMode.ignor_immunity) {
                                                        breakDelayLeft = 0;
                                                    } else if (breakm.get() == BreakMode.TEST) {
                                                        breakDelayLeft = 0;
                                                    }

                                                    stage = Mode.Breaking;
                                                }
                                            }
                                        }
                                    case PlaceBreak:
                                        if (breakMode.get() == Mode.PlaceBreak) {
                                            if (breakm.get() == BreakMode.immunity) {
                                                bestPos = getPlacePos(target);
                                                if (placeDelayLeft > 0) {
                                                    --placeDelayLeft;
                                                } else {
                                                    placeBed(bestPos);
                                                    placeDelayLeft = 0;
                                                }

                                                bestPos = getBreakPos(target);
                                                if (breakDelayLeft > 0) {
                                                    --breakDelayLeft;
                                                } else {
                                                    breakbed = true;
                                                    if (antifriendpop.get()) {
                                                        var6 = mc.world.getPlayers().iterator();

                                                        while (var6.hasNext()) {
                                                            p = (PlayerEntity) var6.next();
                                                            if (!Friends.get().shouldAttack(p) && !mc.player.equals(p) && distanceBetween(bestPos, p.getBlockPos()) <= 4.0D) {
                                                                breakbed = false;
                                                            }
                                                        }
                                                    }

                                                    if (breakbed) {
                                                        breakBed(bestPos);
                                                        breakDelayLeft = 10;
                                                    }
                                                }

                                                stage = Mode.PlaceBreak;
                                            } else if (breakm.get() == BreakMode.ignor_immunity) {
                                                bestPos = getPlacePos(target);
                                                if (placeDelayLeft > 0) {
                                                    --placeDelayLeft;
                                                } else {
                                                    placeBed(bestPos);
                                                    placeDelayLeft = 0;
                                                }

                                                bestPos = getBreakPos(target);
                                                if (breakDelayLeft > 0) {
                                                    --breakDelayLeft;
                                                } else {
                                                    breakbed = true;
                                                    if (antifriendpop.get()) {
                                                        var6 = mc.world.getPlayers().iterator();

                                                        while (var6.hasNext()) {
                                                            p = (PlayerEntity) var6.next();
                                                            if (!Friends.get().shouldAttack(p) && !mc.player.equals(p) && distanceBetween(bestPos, p.getBlockPos()) <= 4.0D) {
                                                                breakbed = false;
                                                            }
                                                        }
                                                    }

                                                    if (breakbed) {
                                                        breakBed(bestPos);
                                                        breakDelayLeft = 0;
                                                    }
                                                }

                                                stage = Mode.PlaceBreak;
                                            } else if (breakm.get() == BreakMode.TEST) {
                                                bestPos = getPlacePos(target);
                                                if (placeDelayLeft > 0) {
                                                    --placeDelayLeft;
                                                } else {
                                                    placeBed(bestPos);
                                                    placeDelayLeft = 10;
                                                }

                                                bestPos = getBreakPos(target);
                                                if (breakDelayLeft > 0) {
                                                    --breakDelayLeft;
                                                } else {
                                                    breakbed = true;
                                                    if (antifriendpop.get()) {
                                                        var6 = mc.world.getPlayers().iterator();

                                                        while (var6.hasNext()) {
                                                            p = (PlayerEntity) var6.next();
                                                            if (!Friends.get().shouldAttack(p) && !mc.player.equals(p) && distanceBetween(bestPos, p.getBlockPos()) <= 4.0D) {
                                                                breakbed = false;
                                                            }
                                                        }
                                                    }

                                                    if (breakbed) {
                                                        breakBed(bestPos);
                                                        breakDelayLeft = 0;
                                                    }
                                                }

                                                stage = Mode.PlaceBreak;
                                            } else if (breakm.get() == BreakMode.TESTIGNORA) {
                                                bestPos = getPlacePos(target);
                                                placeBed(bestPos);
                                                bestPos = getBreakPos(target);
                                                breakBed(bestPos);
                                                stage = Mode.PlaceBreak;
                                            } else if (breakm.get() == BreakMode.TESTRUNPVP) {
                                                bestPos = doPlacePos(target);
                                                placeBed(bestPos);
                                                bestPos = doBreakPos(target);
                                                breakBed(bestPos);
                                                stage = Mode.PlaceBreak;
                                            } else if (breakm.get() == BreakMode.TESTRUNPVP1) {
                                                bestPos = doPlacePos(target);
                                                if (placeDelayLeft > 0) {
                                                    --placeDelayLeft;
                                                } else {
                                                    placeBed(bestPos);
                                                    placeDelayLeft = 10;
                                                }

                                                bestPos = doBreakPos(target);
                                                if (breakDelayLeft > 0) {
                                                    --breakDelayLeft;
                                                } else {
                                                    breakbed = true;
                                                    if (antifriendpop.get()) {
                                                        var6 = mc.world.getPlayers().iterator();

                                                        while (var6.hasNext()) {
                                                            p = (PlayerEntity) var6.next();
                                                            if (!Friends.get().shouldAttack(p) && !mc.player.equals(p) && distanceBetween(bestPos, p.getBlockPos()) <= 4.0D) {
                                                                breakbed = false;
                                                            }
                                                        }
                                                    }

                                                    if (breakbed) {
                                                        breakBed(bestPos);
                                                        breakDelayLeft = 0;
                                                    }
                                                }

                                                stage = Mode.PlaceBreak;
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void placeBed(BlockPos pos) {
        if (pos != null && InvUtils.find((itemStack) -> itemStack.getItem() instanceof BedItem).getCount() != -1) {
            if (autoMove.get()) {
                doAutoMove();
            }

            FindItemResult beditem = InvUtils.findInHotbar((itemStack) -> itemStack.getItem() instanceof BedItem);
            if (beditem.found()) {
                Hand hand = beditem.getHand();
                if (autoSwitch.get() && hand != null) {
                    mc.player.getInventory().selectedSlot = beditem.getSlot();
                }

                if (hand == null) {
                    hand = Hand.MAIN_HAND;
                }

                Rotations.rotate(yawFromDir(direction), mc.player.getPitch(), () -> {
                    BlockUtils.place(pos, beditem, false, 0, swing.get(), false);
                });
            }
        }
    }

    private void breakBed(BlockPos pos) {
        if (pos != null) {
            boolean wasSneaking = mc.player.isSneaking();
            if (wasSneaking) {
                mc.player.input.sneaking = false;
            }

            mc.interactionManager.interactBlock(mc.player, mc.world, Hand.OFF_HAND, new BlockHitResult(mc.player.getPos(), Direction.UP, bestPos, false));
            if (wasSneaking) {
                mc.player.input.sneaking = true;
            }
        }
    }

    private BlockPos getPlacePos(PlayerEntity target) {
        BlockPos targetPos = target.getBlockPos();
        if (checkPlace(Direction.NORTH, target, true, false, false)) {
            return targetPos.up().north();
        } else if (checkPlace(Direction.SOUTH, target, true, false, false)) {
            return targetPos.up().south();
        } else if (checkPlace(Direction.EAST, target, true, false, false)) {
            return targetPos.up().east();
        } else if (checkPlace(Direction.WEST, target, true, false, false)) {
            return targetPos.up().west();
        } else if (checkPlace(Direction.NORTH, target, false, false, true)) {
            return targetPos.down().north();
        } else if (checkPlace(Direction.SOUTH, target, false, false, true)) {
            return targetPos.down().south();
        } else if (checkPlace(Direction.EAST, target, false, false, true)) {
            return targetPos.down().east();
        } else if (checkPlace(Direction.WEST, target, false, false, true)) {
            return targetPos.down().west();
        } else if (checkPlace(Direction.NORTH, target, false, false, false)) {
            return targetPos.north();
        } else if (checkPlace(Direction.SOUTH, target, false, false, false)) {
            return targetPos.south();
        } else if (checkPlace(Direction.EAST, target, false, false, false)) {
            return targetPos.east();
        } else if (checkPlace(Direction.WEST, target, false, false, false)) {
            return targetPos.west();
        } else if (checkPlace(Direction.NORTH, target, false, true, false)) {
            return targetPos.up(2).north();
        } else if (checkPlace(Direction.SOUTH, target, false, true, false)) {
            return targetPos.up(2).south();
        } else if (checkPlace(Direction.EAST, target, false, true, false)) {
            return targetPos.up(2).east();
        } else {
            return checkPlace(Direction.WEST, target, false, true, false) ? targetPos.up(2).west() : null;
        }
    }

    private BlockPos doPlacePos(PlayerEntity target) {
        BlockPos targetPos = target.getBlockPos();
        if (doPlace(Direction.NORTH, target, true)) {
            return targetPos.up().north();
        } else if (doPlace(Direction.SOUTH, target, true)) {
            return targetPos.up().south();
        } else if (doPlace(Direction.EAST, target, true)) {
            return targetPos.up().east();
        } else {
            return doPlace(Direction.WEST, target, true) ? targetPos.up().west() : null;
        }
    }

    private boolean checkPlace(Direction direction, PlayerEntity target, boolean up, boolean up2, boolean down) {
        BlockPos headPos = up ? target.getBlockPos().up() : target.getBlockPos();
        BlockPos headPos2 = up2 ? target.getBlockPos().up(2) : target.getBlockPos();
        BlockPos downPos = down ? target.getBlockPos().down() : target.getBlockPos();
        if (mc.world.getBlockState(headPos).getMaterial().isReplaceable() && BlockUtils.canPlace(headPos.offset(direction)) && (suicide.get() || DamageUtils.bedDamage(target, Utils.vec3d(headPos)) >= minDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos.offset(direction))) < maxSelfDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos)) < maxSelfDamage.get())) {
            direction = direction;
            return true;
        } else if (mc.world.getBlockState(headPos2).getMaterial().isReplaceable() && BlockUtils.canPlace(headPos2.offset(direction)) && (suicide.get() || DamageUtils.bedDamage(target, Utils.vec3d(headPos2)) >= minDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos2.offset(direction))) < maxSelfDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos2)) < maxSelfDamage.get())) {
            direction = direction;
            return true;
        } else if (!mc.world.getBlockState(downPos).getMaterial().isReplaceable() || !BlockUtils.canPlace(downPos.offset(direction)) || !suicide.get() && (!(DamageUtils.bedDamage(target, Utils.vec3d(downPos)) >= minDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(downPos.offset(direction))) < maxSelfDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(downPos)) < maxSelfDamage.get()))) {
            return false;
        } else {
            direction = direction;
            return true;
        }
    }

    private boolean doPlace(Direction direction, PlayerEntity target, boolean up) {
        BlockPos headPos = up ? target.getBlockPos().up() : target.getBlockPos();
        if (!mc.world.getBlockState(headPos).getMaterial().isReplaceable() || !BlockUtils.canPlace(headPos.offset(direction)) || !suicide.get() && (!(DamageUtils.bedDamage(target, Utils.vec3d(headPos)) >= 1.0D) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos.offset(direction))) < maxSelfDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos)) < maxSelfDamage.get()))) {
            return false;
        } else {
            direction = direction;
            return true;
        }
    }

    private BlockPos getBreakPos(PlayerEntity target) {
        BlockPos targetPos = target.getBlockPos();
        if (checkBreak(Direction.NORTH, target, true, false, false)) {
            return targetPos.up().north();
        } else if (checkBreak(Direction.SOUTH, target, true, false, false)) {
            return targetPos.up().south();
        } else if (checkBreak(Direction.EAST, target, true, false, false)) {
            return targetPos.up().east();
        } else if (checkBreak(Direction.WEST, target, true, false, false)) {
            return targetPos.up().west();
        } else if (checkBreak(Direction.NORTH, target, false, true, false)) {
            return targetPos.up(2).north();
        } else if (checkBreak(Direction.SOUTH, target, false, true, false)) {
            return targetPos.up(2).south();
        } else if (checkBreak(Direction.EAST, target, false, true, false)) {
            return targetPos.up(2).east();
        } else if (checkBreak(Direction.WEST, target, false, true, false)) {
            return targetPos.up(2).west();
        } else if (checkBreak(Direction.NORTH, target, false, false, false)) {
            return targetPos.north();
        } else if (checkBreak(Direction.SOUTH, target, false, false, false)) {
            return targetPos.south();
        } else if (checkBreak(Direction.EAST, target, false, false, false)) {
            return targetPos.east();
        } else if (checkBreak(Direction.WEST, target, false, false, false)) {
            return targetPos.west();
        } else if (checkBreak(Direction.NORTH, target, false, false, true)) {
            return targetPos.down().north();
        } else if (checkBreak(Direction.SOUTH, target, false, false, true)) {
            return targetPos.down().south();
        } else if (checkBreak(Direction.EAST, target, false, false, true)) {
            return targetPos.down().east();
        } else {
            return checkBreak(Direction.WEST, target, false, false, true) ? targetPos.down().west() : null;
        }
    }

    private BlockPos doBreakPos(PlayerEntity target) {
        BlockPos targetPos = target.getBlockPos();
        if (doBreak(Direction.NORTH, target, true)) {
            return targetPos.up().north();
        } else if (doBreak(Direction.SOUTH, target, true)) {
            return targetPos.up().south();
        } else if (doBreak(Direction.EAST, target, true)) {
            return targetPos.up().east();
        } else {
            return doBreak(Direction.WEST, target, true) ? targetPos.up().west() : null;
        }
    }

    private boolean checkBreak(Direction direction, PlayerEntity target, boolean up, boolean up2, boolean down) {
        BlockPos headPos = up ? target.getBlockPos().up() : target.getBlockPos();
        BlockPos headPos2 = up2 ? target.getBlockPos().up(2) : target.getBlockPos();
        BlockPos downPos = down ? target.getBlockPos().down() : target.getBlockPos();
        if (!(mc.world.getBlockState(headPos).getBlock() instanceof BedBlock) || !(mc.world.getBlockState(headPos.offset(direction)).getBlock() instanceof BedBlock) || !suicide.get() && (!(DamageUtils.bedDamage(target, Utils.vec3d(headPos)) >= minDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos.offset(direction))) < maxSelfDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos)) < maxSelfDamage.get()))) {
            if (mc.world.getBlockState(headPos2).getBlock() instanceof BedBlock && mc.world.getBlockState(headPos2.offset(direction)).getBlock() instanceof BedBlock && (suicide.get() || DamageUtils.bedDamage(target, Utils.vec3d(headPos2)) >= minDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos2.offset(direction))) < maxSelfDamage.get() && DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos2)) < maxSelfDamage.get())) {
                direction = direction;
                return true;
            } else if (!(mc.world.getBlockState(downPos).getBlock() instanceof BedBlock) || !(mc.world.getBlockState(downPos.offset(direction)).getBlock() instanceof BedBlock) || !suicide.get() && (!(DamageUtils.bedDamage(target, Utils.vec3d(downPos)) >= minDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(downPos.offset(direction))) < maxSelfDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(downPos)) < maxSelfDamage.get()))) {
                return false;
            } else {
                direction = direction;
                return true;
            }
        } else {
            direction = direction;
            return true;
        }
    }

    private boolean doBreak(Direction direction, PlayerEntity target, boolean up) {
        BlockPos headPos = up ? target.getBlockPos().up() : target.getBlockPos();
        if (!(mc.world.getBlockState(headPos).getBlock() instanceof BedBlock) || !(mc.world.getBlockState(headPos.offset(direction)).getBlock() instanceof BedBlock) || !suicide.get() && (!(DamageUtils.bedDamage(target, Utils.vec3d(headPos)) >= 1.0D) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos.offset(direction))) < maxSelfDamage.get()) || !(DamageUtils.bedDamage(mc.player, Utils.vec3d(headPos)) < maxSelfDamage.get()))) {
            return false;
        } else {
            direction = direction;
            return true;
        }
    }

    private void doAutoMove() {
        if (InvUtils.findInHotbar((itemStack) -> itemStack.getItem() instanceof BedItem).getSlot() == -1) {
            int slot = InvUtils.find((itemStack) -> itemStack.getItem() instanceof BedItem).getSlot();
            InvUtils.move().from(slot).toHotbar(autoMoveSlot.get() - 1);
        }
    }

    private float yawFromDir(Direction direction) {
        return switch (direction) {
            case EAST -> 90.0F;
            case NORTH -> 0.0F;
            case SOUTH -> 180.0F;
            case WEST -> -90.0F;
            default -> 0.0F;
        };
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (render.get() && bestPos != null) {
            int x = bestPos.getX();
            int y = bestPos.getY();
            int z = bestPos.getZ();
            switch (direction) {
                case EAST -> event.renderer.box((x - 1), y, z, (x + 1), y + 0.6D, (z + 1), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
                case NORTH -> event.renderer.box(x, y, z, (x + 1), y + 0.6D, (z + 2), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
                case SOUTH -> event.renderer.box(x, y, (z - 1), (x + 1), y + 0.6D, (z + 1), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
                case WEST -> event.renderer.box(x, y, z, (x + 2), y + 0.6D, (z + 1), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }
        }
    }

    public String getInfoString() {
        return target != null ? target.getEntityName() : null;
    }

    public static boolean isWeb(BlockPos pos) {
        return BlockUtilsWorld.getBlock(pos) == Blocks.COBWEB || BlockUtilsWorld.getBlock(pos) == Block.getBlockFromItem(Items.STRING);
    }

    public static FindItemResult findPick() {
        return InvUtils.findInHotbar((itemStack) -> itemStack.getItem() instanceof PickaxeItem);
    }

    public static FindItemResult findSword() {
        return InvUtils.findInHotbar((itemStack) -> itemStack.getItem() instanceof SwordItem);
    }

    public static boolean isWebbed(PlayerEntity p) {
        BlockPos pos = p.getBlockPos();
        return isWeb(pos) && isWeb(pos.up()) || isWeb(pos.up());
    }

    public static double distanceBetween(BlockPos pos1, BlockPos pos2) {
        double d = (pos1.getX() - pos2.getX());
        double e = (pos1.getY() - pos2.getY());
        double f = (pos1.getZ() - pos2.getZ());
        return MathHelper.sqrt((float) (d * d + e * e + f * f));
    }

    public enum Mode {
        Placing,
        Breaking,
        PlaceBreak
    }

    public enum Direct {
        EAST,
        WEST,
        SOUTH,
        NORTH
    }

    public enum BreakMode {
        immunity,
        ignor_immunity,
        TEST,
        TESTIGNORA,
        TESTRUNPVP,
        TESTRUNPVP1
    }

    public enum Prioritet {
        Head,
        Face,
        Legs
    }
}
