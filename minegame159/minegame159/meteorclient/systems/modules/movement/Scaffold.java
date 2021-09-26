/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.ClipAtLedgeEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;

public class Scaffold
extends Module {
    private int slot;
    private boolean lastWasSneaking;
    private final Setting<Boolean> renderSwing;
    private double lastSneakingY;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> fastTower;
    private final Setting<Boolean> safeWalk;
    private final Mutable blockPos;
    private BlockState blockState;
    private final Setting<List<Block>> blackList;
    private final Setting<Integer> radius;
    private final Setting<Boolean> selfToggle;
    private final Setting<Boolean> rotate;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.fastTower.get().booleanValue() && !this.mc.world.getBlockState(this.setPos(0, -1, 0)).getMaterial().isReplaceable() && this.mc.options.keyJump.isPressed() && this.findSlot(this.mc.world.getBlockState(this.setPos(0, -1, 0))) != -1 && this.mc.player.sidewaysSpeed == 0.0f && this.mc.player.forwardSpeed == 0.0f) {
            this.mc.player.jump();
        }
        this.blockState = this.mc.world.getBlockState(this.setPos(0, -1, 0));
        if (!this.blockState.getMaterial().isReplaceable()) {
            return;
        }
        boolean bl = this.lastWasSneaking;
        this.lastWasSneaking = this.mc.player.input.sneaking;
        if (this.mc.player.input.sneaking) {
            if (!bl) {
                this.lastSneakingY = this.mc.player.getY();
            }
            if (this.lastSneakingY - this.mc.player.getY() < 0.1) {
                return;
            }
        }
        this.slot = this.findSlot(this.blockState);
        if (this.slot == -1) {
            return;
        }
        this.place(this.mc.player.getBlockPos().down(), this.slot);
        if (this.mc.player.input.sneaking) {
            this.lastWasSneaking = false;
        }
        for (int i = 1; i < this.radius.get(); ++i) {
            int n;
            int n2 = 1 + (i - 1) * 2;
            int n3 = n2 / 2;
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(n - n3, -1, i), this.slot);
                if (3 < 4) continue;
                return;
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(n - n3, -1, -i), this.slot);
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(i, -1, n - n3), this.slot);
                if (null == null) continue;
                return;
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(-i, -1, n - n3), this.slot);
                if (-3 < 0) continue;
                return;
            }
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(-i, -1, i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(i, -1, i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(-i, -1, -i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(i, -1, -i), this.slot);
            if (3 > 0) continue;
            return;
        }
    }

    @EventHandler
    private void onClipAtLedge(ClipAtLedgeEvent clipAtLedgeEvent) {
        if (this.mc.player.input.sneaking) {
            clipAtLedgeEvent.setClip(false);
            return;
        }
        if (this.safeWalk.get().booleanValue()) {
            clipAtLedgeEvent.setClip(true);
        }
    }

    @Override
    public void onActivate() {
        this.lastWasSneaking = this.mc.player.input.sneaking;
        if (this.lastWasSneaking) {
            this.lastSneakingY = this.mc.player.getY();
        }
    }

    private BlockPos setPos(int n, int n2, int n3) {
        this.blockPos.set(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
        if (n != 0) {
            this.blockPos.setX(this.blockPos.getX() + n);
        }
        if (n2 != 0) {
            this.blockPos.setY(this.blockPos.getY() + n2);
        }
        if (n3 != 0) {
            this.blockPos.setZ(this.blockPos.getZ() + n3);
        }
        return this.blockPos;
    }

    public Scaffold() {
        super(Categories.Movement, "scaffold", "Automatically places blocks under you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.safeWalk = this.sgGeneral.add(new BoolSetting.Builder().name("safe-walk").description("Whether or not to toggle Safe Walk when using Scaffold.").defaultValue(true).build());
        this.fastTower = this.sgGeneral.add(new BoolSetting.Builder().name("fast-tower").description("Whether or not to scaffold upwards faster.").defaultValue(false).build());
        this.radius = this.sgGeneral.add(new IntSetting.Builder().name("radius").description("The radius of your scaffold.").defaultValue(1).min(1).sliderMin(1).sliderMax(7).build());
        this.blackList = this.sgGeneral.add(new BlockListSetting.Builder().name("blacklist").description("Blacklists certain blocks from being used to scaffold.").defaultValue(new ArrayList<Block>()).build());
        this.selfToggle = this.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles Scaffold when you run out of blocks.").defaultValue(true).build());
        this.renderSwing = this.sgGeneral.add(new BoolSetting.Builder().name("swing").description("Renders your client-side swing.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates towards the blocks being placed.").defaultValue(true).build());
        this.blockPos = new Mutable();
    }

    private void place(BlockPos BlockPos2, int n) {
        BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), -15, this.renderSwing.get(), true, true, true);
    }

    public boolean hasSafeWalk() {
        return this.safeWalk.get();
    }

    private int findSlot(BlockState BlockState2) {
        ItemStack ItemStack2;
        BlockState BlockState3;
        ItemStack ItemStack3;
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block Block2;
            ItemStack3 = this.mc.player.inventory.getStack(i);
            if (ItemStack3.isEmpty() || !(ItemStack3.getItem() instanceof BlockItem) || this.blackList.get().contains(Block.getBlockFromItem((Item)ItemStack3.getItem())) || !Block.isShapeFullCube((VoxelShape)(BlockState3 = (Block2 = ((BlockItem)ItemStack3.getItem()).getBlock()).getDefaultState()).getCollisionShape((BlockView)this.mc.world, this.setPos(0, -1, 0))) || Block2 instanceof FallingBlock && FallingBlock.canFallThrough((BlockState)BlockState2)) continue;
            n = i;
            break;
        }
        if ((ItemStack2 = this.mc.player.getMainHandStack()).isEmpty() || !(ItemStack2.getItem() instanceof BlockItem)) {
            return n;
        }
        if (this.blackList.get().contains(Block.getBlockFromItem((Item)ItemStack2.getItem()))) {
            return n;
        }
        ItemStack3 = ((BlockItem)ItemStack2.getItem()).getBlock();
        BlockState3 = ItemStack3.getDefaultState();
        if (!Block.isShapeFullCube((VoxelShape)BlockState3.getCollisionShape((BlockView)this.mc.world, this.setPos(0, -1, 0)))) {
            return n;
        }
        if (ItemStack3 instanceof FallingBlock && FallingBlock.canFallThrough((BlockState)BlockState2)) {
            return n;
        }
        n = this.mc.player.inventory.selectedSlot;
        return n;
    }

    private boolean findBlock() {
        if (this.mc.player.inventory.getStack(this.slot).isEmpty()) {
            this.slot = this.findSlot(this.blockState);
            if (this.slot == -1) {
                if (this.selfToggle.get().booleanValue()) {
                    this.toggle();
                }
                return false;
            }
        }
        return true;
    }
}

