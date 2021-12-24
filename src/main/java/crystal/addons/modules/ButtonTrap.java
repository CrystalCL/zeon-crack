package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ButtonTrap extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
    private final Setting<Integer> delaySetting = sgGeneral.add(new IntSetting.Builder().name("place-delay").description("How many ticks between block placements.").defaultValue(1).sliderMin(0).sliderMax(10).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());

    private PlayerEntity target;
    private List<BlockPos> placePositions;
    private int delay;

    public ButtonTrap() {
        super(CrystalCL.Exc, "button-trap", "Anti Surround.");
    }

    public void onActivate() {
        target = null;
        if (!placePositions.isEmpty()) {
            placePositions.clear();
        }

        delay = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        target = TargetUtils.getPlayerTarget(range.get(), SortPriority.LowestDistance);
        if (target != null && !(mc.player.distanceTo(target) > (float) range.get())) {
            int slot = InvUtils.findInHotbar(Items.ACACIA_BUTTON, Items.STONE_BUTTON, Items.OAK_BUTTON, Items.SPRUCE_BUTTON, Items.BIRCH_BUTTON, Items.JUNGLE_BUTTON, Items.DARK_OAK_BUTTON, Items.CRIMSON_BUTTON, Items.WARPED_BUTTON).getSlot();
            if (slot != -1) {
                placePositions.clear();
                findPlacePos(target);
                if (delay >= delaySetting.get() && placePositions.size() > 0) {
                    BlockPos blockPos = placePositions.get(placePositions.size() - 1);
                    if (BlockUtils.place(blockPos, Hand.MAIN_HAND, slot, rotate.get(), 50, false, false, true)) {
                        placePositions.remove(blockPos);
                    }

                    delay = 0;
                } else {
                    delay++;
                }

            }
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (render.get() && !placePositions.isEmpty()) {

            for (BlockPos pos : placePositions) {
                event.renderer.box(pos, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
            }
        }
    }

    private void add(BlockPos blockPos) {
        if (!placePositions.contains(blockPos) && mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.ACACIA_BUTTON.getDefaultState(), blockPos, ShapeContext.absent()) && (mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())).isFullCube(mc.world, new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())) || mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ())).isFullCube(mc.world, new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ())) || mc.world.getBlockState(new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ())).isFullCube(mc.world, new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ())) || mc.world.getBlockState(new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ())).isFullCube(mc.world, new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ())) || mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1)).isFullCube(mc.world, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1)) || mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1)).isFullCube(mc.world, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1)))) {
            placePositions.add(blockPos);
        }
    }

    private void findPlacePos(PlayerEntity target) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(1, 0, 0));
        add(targetPos.add(0, 0, 1));
        add(targetPos.add(-1, 0, 0));
        add(targetPos.add(0, 0, -1));
    }
}
