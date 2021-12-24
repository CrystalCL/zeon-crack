package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class TntTrap extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
    private final Setting<Boolean> Oplace = sgPause.add(new BoolSetting.Builder().name("obsidian-place").description("Place obsidian.").defaultValue(true).build());
    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnMine = sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());

    private PlayerEntity target = null;
    private List<BlockPos> placePositions = new ArrayList();
    private List<BlockPos> placePositionsO = new ArrayList();
    private boolean placed;

    public TntTrap() {
        super(CrystalCL.Exc, "TNT-trap", "Anti Surround.");
    }

    public void onActivate() {
        if (Oplace.get()) {
            target = CityUtils.getPlayerTarget(range.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositionsO.clear();
            int cry = InvUtils.findInHotbar(Items.OBSIDIAN).getSlot();
            if (cry == -1) {
                return;
            }

            findPlacePosO(target);

            for (int x = 0; x < placePositionsO.size(); x++) {
                BlockPos blockPosO = (BlockPos) placePositionsO.get(placePositionsO.size() - 1);
                if (BlockUtils.place(blockPosO, Hand.MAIN_HAND, cry, rotate.get(), 50, true, false, true)) {
                    placePositionsO.remove(blockPosO);
                    placed = true;
                }
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        target = CityUtils.getPlayerTarget(range.get());
        if (target != null && !(mc.player.distanceTo(target) > (float) range.get())) {
            placed = false;
            placePositions.clear();
            int tnt = InvUtils.findInHotbar(Items.TNT).getSlot();
            int fire = InvUtils.findInHotbar(Items.FLINT_AND_STEEL).getSlot();
            if (fire == -1) {
                fire = InvUtils.findInHotbar(Items.FIRE_CHARGE).getSlot();
            }

            if (tnt != -1 && fire != -1) {
                if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.interactionManager.isBreakingBlock() || !pauseOnMine.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get())) {
                    findPlacePos(target);

                    for (int x = 0; x < placePositions.size(); x++) {
                        BlockPos blockPos = placePositions.get(placePositions.size() - 1);
                        if (BlockUtils.place(blockPos, Hand.MAIN_HAND, tnt, rotate.get(), 50, true, false, true)) {
                            placePositions.remove(blockPos);
                            placed = true;
                        }

                        if (placed) {
                            int preSlot = mc.player.getInventory().selectedSlot;
                            mc.player.getInventory().selectedSlot = fire;
                            mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.UP, blockPos, true));
                            mc.player.getInventory().selectedSlot = preSlot;
                        }
                    }

                    target = null;
                }
            }
        }
    }

    @EventHandler
    private void onTick1(TickEvent.Post event) {
        target = CityUtils.getPlayerTarget(range.get());
        if (target != null && !(mc.player.distanceTo(target) > (float) range.get())) {
            placed = false;
            placePositions.clear();
            int tnt = InvUtils.findInHotbar(Items.TNT).getSlot();
            int fire = InvUtils.findInHotbar(Items.FLINT_AND_STEEL).getSlot();
            if (fire == -1) {
                fire = InvUtils.findInHotbar(Items.FIRE_CHARGE).getSlot();
            }

            if (tnt != -1 && fire != -1) {
                if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.interactionManager.isBreakingBlock() || !pauseOnMine.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get())) {
                    findPlacePos(target);

                    for (int x = 0; x < placePositions.size(); x++) {
                        BlockPos blockPos = placePositions.get(placePositions.size() - 1);
                        if (BlockUtils.place(blockPos, Hand.MAIN_HAND, tnt, rotate.get(), 50, true, false, true)) {
                            placePositions.remove(blockPos);
                            placed = true;
                        }

                        if (placed) {
                            int preSlot = mc.player.getInventory().selectedSlot;
                            mc.player.getInventory().selectedSlot = fire;
                            mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.UP, blockPos, true));
                            mc.player.getInventory().selectedSlot = preSlot;
                        }
                    }

                    target = null;
                }
            }
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (render.get() && !placePositions.isEmpty()) {
            for (BlockPos blockPos : placePositions) {
                event.renderer.box(blockPos, sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }
        }
    }

    private void add(BlockPos blockPos) {
        if (!placePositions.contains(blockPos) && mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.TNT.getDefaultState(), blockPos, ShapeContext.absent())) {
            placePositions.add(blockPos);
        }
    }

    private void addO(BlockPos blockPosO) {
        if (!placePositionsO.contains(blockPosO) && mc.world.getBlockState(blockPosO).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPosO, ShapeContext.absent())) {
            placePositionsO.add(blockPosO);
        }
    }

    private void findPlacePos(PlayerEntity target) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(0, 2, 0));
    }

    private void findPlacePosO(PlayerEntity target) {
        placePositionsO.clear();
        BlockPos targetPos = target.getBlockPos();
        addO(targetPos.add(1, 2, 0));
        addO(targetPos.add(-1, 2, 0));
        addO(targetPos.add(0, 2, 1));
        addO(targetPos.add(0, 2, -1));
        addO(targetPos.add(0, 3, 0));
    }
}
