/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.block.FallingBlock
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.block.BlockState
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
    private /* synthetic */ boolean lastWasSneaking;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> safeWalk;
    private /* synthetic */ int slot;
    private final /* synthetic */ Setting<Boolean> rotate;
    private /* synthetic */ BlockState blockState;
    private final /* synthetic */ Setting<Boolean> renderSwing;
    private final /* synthetic */ Setting<Boolean> fastTower;
    private final /* synthetic */ Setting<List<Block>> blackList;
    private final /* synthetic */ Setting<Boolean> selfToggle;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ double lastSneakingY;
    private final /* synthetic */ Setting<Integer> radius;

    private BlockPos setPos(int lllllllllllllllllIlllIIlIIlIIlIl, int lllllllllllllllllIlllIIlIIlIIlII, int lllllllllllllllllIlllIIlIIIlllll) {
        Scaffold lllllllllllllllllIlllIIlIIlIIllI;
        lllllllllllllllllIlllIIlIIlIIllI.blockPos.set(lllllllllllllllllIlllIIlIIlIIllI.mc.player.getX(), lllllllllllllllllIlllIIlIIlIIllI.mc.player.getY(), lllllllllllllllllIlllIIlIIlIIllI.mc.player.getZ());
        if (lllllllllllllllllIlllIIlIIlIIlIl != 0) {
            lllllllllllllllllIlllIIlIIlIIllI.blockPos.setX(lllllllllllllllllIlllIIlIIlIIllI.blockPos.getX() + lllllllllllllllllIlllIIlIIlIIlIl);
        }
        if (lllllllllllllllllIlllIIlIIlIIlII != 0) {
            lllllllllllllllllIlllIIlIIlIIllI.blockPos.setY(lllllllllllllllllIlllIIlIIlIIllI.blockPos.getY() + lllllllllllllllllIlllIIlIIlIIlII);
        }
        if (lllllllllllllllllIlllIIlIIIlllll != 0) {
            lllllllllllllllllIlllIIlIIlIIllI.blockPos.setZ(lllllllllllllllllIlllIIlIIlIIllI.blockPos.getZ() + lllllllllllllllllIlllIIlIIIlllll);
        }
        return lllllllllllllllllIlllIIlIIlIIllI.blockPos;
    }

    @EventHandler
    private void onClipAtLedge(ClipAtLedgeEvent lllllllllllllllllIlllIIlIlIIIIll) {
        Scaffold lllllllllllllllllIlllIIlIlIIIIlI;
        if (lllllllllllllllllIlllIIlIlIIIIlI.mc.player.input.sneaking) {
            lllllllllllllllllIlllIIlIlIIIIll.setClip(false);
            return;
        }
        if (lllllllllllllllllIlllIIlIlIIIIlI.safeWalk.get().booleanValue()) {
            lllllllllllllllllIlllIIlIlIIIIll.setClip(true);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlllIIlIlIIlllI) {
        Scaffold lllllllllllllllllIlllIIlIlIIllII;
        if (lllllllllllllllllIlllIIlIlIIllII.fastTower.get().booleanValue() && !lllllllllllllllllIlllIIlIlIIllII.mc.world.getBlockState(lllllllllllllllllIlllIIlIlIIllII.setPos(0, -1, 0)).getMaterial().isReplaceable() && lllllllllllllllllIlllIIlIlIIllII.mc.options.keyJump.isPressed() && lllllllllllllllllIlllIIlIlIIllII.findSlot(lllllllllllllllllIlllIIlIlIIllII.mc.world.getBlockState(lllllllllllllllllIlllIIlIlIIllII.setPos(0, -1, 0))) != -1 && lllllllllllllllllIlllIIlIlIIllII.mc.player.sidewaysSpeed == 0.0f && lllllllllllllllllIlllIIlIlIIllII.mc.player.forwardSpeed == 0.0f) {
            lllllllllllllllllIlllIIlIlIIllII.mc.player.jump();
        }
        lllllllllllllllllIlllIIlIlIIllII.blockState = lllllllllllllllllIlllIIlIlIIllII.mc.world.getBlockState(lllllllllllllllllIlllIIlIlIIllII.setPos(0, -1, 0));
        if (!lllllllllllllllllIlllIIlIlIIllII.blockState.getMaterial().isReplaceable()) {
            return;
        }
        boolean lllllllllllllllllIlllIIlIlIIllIl = lllllllllllllllllIlllIIlIlIIllII.lastWasSneaking;
        lllllllllllllllllIlllIIlIlIIllII.lastWasSneaking = lllllllllllllllllIlllIIlIlIIllII.mc.player.input.sneaking;
        if (lllllllllllllllllIlllIIlIlIIllII.mc.player.input.sneaking) {
            if (!lllllllllllllllllIlllIIlIlIIllIl) {
                lllllllllllllllllIlllIIlIlIIllII.lastSneakingY = lllllllllllllllllIlllIIlIlIIllII.mc.player.getY();
            }
            if (lllllllllllllllllIlllIIlIlIIllII.lastSneakingY - lllllllllllllllllIlllIIlIlIIllII.mc.player.getY() < 0.1) {
                return;
            }
        }
        lllllllllllllllllIlllIIlIlIIllII.slot = lllllllllllllllllIlllIIlIlIIllII.findSlot(lllllllllllllllllIlllIIlIlIIllII.blockState);
        if (lllllllllllllllllIlllIIlIlIIllII.slot == -1) {
            return;
        }
        lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.mc.player.getBlockPos().down(), lllllllllllllllllIlllIIlIlIIllII.slot);
        if (lllllllllllllllllIlllIIlIlIIllII.mc.player.input.sneaking) {
            lllllllllllllllllIlllIIlIlIIllII.lastWasSneaking = false;
        }
        for (int lllllllllllllllllIlllIIlIlIlIIII = 1; lllllllllllllllllIlllIIlIlIlIIII < lllllllllllllllllIlllIIlIlIIllII.radius.get(); ++lllllllllllllllllIlllIIlIlIlIIII) {
            int lllllllllllllllllIlllIIlIlIlIIlI = 1 + (lllllllllllllllllIlllIIlIlIlIIII - 1) * 2;
            int lllllllllllllllllIlllIIlIlIlIIIl = lllllllllllllllllIlllIIlIlIlIIlI / 2;
            for (int lllllllllllllllllIlllIIlIlIlIllI = 0; lllllllllllllllllIlllIIlIlIlIllI < lllllllllllllllllIlllIIlIlIlIIlI; ++lllllllllllllllllIlllIIlIlIlIllI) {
                if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                    return;
                }
                lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(lllllllllllllllllIlllIIlIlIlIllI - lllllllllllllllllIlllIIlIlIlIIIl, -1, lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
            }
            for (int lllllllllllllllllIlllIIlIlIlIlIl = 0; lllllllllllllllllIlllIIlIlIlIlIl < lllllllllllllllllIlllIIlIlIlIIlI; ++lllllllllllllllllIlllIIlIlIlIlIl) {
                if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                    return;
                }
                lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(lllllllllllllllllIlllIIlIlIlIlIl - lllllllllllllllllIlllIIlIlIlIIIl, -1, -lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
            }
            for (int lllllllllllllllllIlllIIlIlIlIlII = 0; lllllllllllllllllIlllIIlIlIlIlII < lllllllllllllllllIlllIIlIlIlIIlI; ++lllllllllllllllllIlllIIlIlIlIlII) {
                if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                    return;
                }
                lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(lllllllllllllllllIlllIIlIlIlIIII, -1, lllllllllllllllllIlllIIlIlIlIlII - lllllllllllllllllIlllIIlIlIlIIIl), lllllllllllllllllIlllIIlIlIIllII.slot);
            }
            for (int lllllllllllllllllIlllIIlIlIlIIll = 0; lllllllllllllllllIlllIIlIlIlIIll < lllllllllllllllllIlllIIlIlIlIIlI; ++lllllllllllllllllIlllIIlIlIlIIll) {
                if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                    return;
                }
                lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(-lllllllllllllllllIlllIIlIlIlIIII, -1, lllllllllllllllllIlllIIlIlIlIIll - lllllllllllllllllIlllIIlIlIlIIIl), lllllllllllllllllIlllIIlIlIIllII.slot);
            }
            if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                return;
            }
            lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(-lllllllllllllllllIlllIIlIlIlIIII, -1, lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
            if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                return;
            }
            lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(lllllllllllllllllIlllIIlIlIlIIII, -1, lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
            if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                return;
            }
            lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(-lllllllllllllllllIlllIIlIlIlIIII, -1, -lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
            if (!lllllllllllllllllIlllIIlIlIIllII.findBlock()) {
                return;
            }
            lllllllllllllllllIlllIIlIlIIllII.place(lllllllllllllllllIlllIIlIlIIllII.setPos(lllllllllllllllllIlllIIlIlIlIIII, -1, -lllllllllllllllllIlllIIlIlIlIIII), lllllllllllllllllIlllIIlIlIIllII.slot);
        }
    }

    private boolean findBlock() {
        Scaffold lllllllllllllllllIlllIIlIIllllll;
        if (lllllllllllllllllIlllIIlIIllllll.mc.player.inventory.getStack(lllllllllllllllllIlllIIlIIllllll.slot).isEmpty()) {
            lllllllllllllllllIlllIIlIIllllll.slot = lllllllllllllllllIlllIIlIIllllll.findSlot(lllllllllllllllllIlllIIlIIllllll.blockState);
            if (lllllllllllllllllIlllIIlIIllllll.slot == -1) {
                if (lllllllllllllllllIlllIIlIIllllll.selfToggle.get().booleanValue()) {
                    lllllllllllllllllIlllIIlIIllllll.toggle();
                }
                return false;
            }
        }
        return true;
    }

    private int findSlot(BlockState lllllllllllllllllIlllIIIlllIlIll) {
        ItemStack lllllllllllllllllIlllIIIlllIllll;
        Scaffold lllllllllllllllllIlllIIIlllIllII;
        int lllllllllllllllllIlllIIIllllIIll = -1;
        for (int lllllllllllllllllIlllIIIllllIllI = 0; lllllllllllllllllIlllIIIllllIllI < 9; ++lllllllllllllllllIlllIIIllllIllI) {
            Block lllllllllllllllllIlllIIIlllllIIl;
            BlockState lllllllllllllllllIlllIIIlllllIII;
            ItemStack lllllllllllllllllIlllIIIlllllIlI = lllllllllllllllllIlllIIIlllIllII.mc.player.inventory.getStack(lllllllllllllllllIlllIIIllllIllI);
            if (lllllllllllllllllIlllIIIlllllIlI.isEmpty() || !(lllllllllllllllllIlllIIIlllllIlI.getItem() instanceof BlockItem) || lllllllllllllllllIlllIIIlllIllII.blackList.get().contains((Object)Block.getBlockFromItem((Item)lllllllllllllllllIlllIIIlllllIlI.getItem())) || !Block.isShapeFullCube((VoxelShape)(lllllllllllllllllIlllIIIlllllIII = (lllllllllllllllllIlllIIIlllllIIl = ((BlockItem)lllllllllllllllllIlllIIIlllllIlI.getItem()).getBlock()).getDefaultState()).getCollisionShape((BlockView)lllllllllllllllllIlllIIIlllIllII.mc.world, lllllllllllllllllIlllIIIlllIllII.setPos(0, -1, 0))) || lllllllllllllllllIlllIIIlllllIIl instanceof FallingBlock && FallingBlock.canFallThrough((BlockState)lllllllllllllllllIlllIIIlllIlIll)) continue;
            lllllllllllllllllIlllIIIllllIIll = lllllllllllllllllIlllIIIllllIllI;
            break;
        }
        if ((lllllllllllllllllIlllIIIlllIllll = lllllllllllllllllIlllIIIlllIllII.mc.player.getMainHandStack()).isEmpty() || !(lllllllllllllllllIlllIIIlllIllll.getItem() instanceof BlockItem)) {
            return lllllllllllllllllIlllIIIllllIIll;
        }
        if (lllllllllllllllllIlllIIIlllIllII.blackList.get().contains((Object)Block.getBlockFromItem((Item)lllllllllllllllllIlllIIIlllIllll.getItem()))) {
            return lllllllllllllllllIlllIIIllllIIll;
        }
        Block lllllllllllllllllIlllIIIlllIllIl = ((BlockItem)lllllllllllllllllIlllIIIlllIllll.getItem()).getBlock();
        BlockState lllllllllllllllllIlllIIIllllIIIl = lllllllllllllllllIlllIIIlllIllIl.getDefaultState();
        if (!Block.isShapeFullCube((VoxelShape)lllllllllllllllllIlllIIIllllIIIl.getCollisionShape((BlockView)lllllllllllllllllIlllIIIlllIllII.mc.world, lllllllllllllllllIlllIIIlllIllII.setPos(0, -1, 0)))) {
            return lllllllllllllllllIlllIIIllllIIll;
        }
        if (lllllllllllllllllIlllIIIlllIllIl instanceof FallingBlock && FallingBlock.canFallThrough((BlockState)lllllllllllllllllIlllIIIlllIlIll)) {
            return lllllllllllllllllIlllIIIllllIIll;
        }
        lllllllllllllllllIlllIIIllllIIll = lllllllllllllllllIlllIIIlllIllII.mc.player.inventory.selectedSlot;
        return lllllllllllllllllIlllIIIllllIIll;
    }

    public Scaffold() {
        super(Categories.Movement, "scaffold", "Automatically places blocks under you.");
        Scaffold lllllllllllllllllIlllIIlIllIIIIl;
        lllllllllllllllllIlllIIlIllIIIIl.sgGeneral = lllllllllllllllllIlllIIlIllIIIIl.settings.getDefaultGroup();
        lllllllllllllllllIlllIIlIllIIIIl.safeWalk = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("safe-walk").description("Whether or not to toggle Safe Walk when using Scaffold.").defaultValue(true).build());
        lllllllllllllllllIlllIIlIllIIIIl.fastTower = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("fast-tower").description("Whether or not to scaffold upwards faster.").defaultValue(false).build());
        lllllllllllllllllIlllIIlIllIIIIl.radius = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new IntSetting.Builder().name("radius").description("The radius of your scaffold.").defaultValue(1).min(1).sliderMin(1).sliderMax(7).build());
        lllllllllllllllllIlllIIlIllIIIIl.blackList = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BlockListSetting.Builder().name("blacklist").description("Blacklists certain blocks from being used to scaffold.").defaultValue(new ArrayList<Block>()).build());
        lllllllllllllllllIlllIIlIllIIIIl.selfToggle = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles Scaffold when you run out of blocks.").defaultValue(true).build());
        lllllllllllllllllIlllIIlIllIIIIl.renderSwing = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("swing").description("Renders your client-side swing.").defaultValue(true).build());
        lllllllllllllllllIlllIIlIllIIIIl.rotate = lllllllllllllllllIlllIIlIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates towards the blocks being placed.").defaultValue(true).build());
        lllllllllllllllllIlllIIlIllIIIIl.blockPos = new Mutable();
    }

    private void place(BlockPos lllllllllllllllllIlllIIlIIlllIII, int lllllllllllllllllIlllIIlIIllIIll) {
        Scaffold lllllllllllllllllIlllIIlIIlllIIl;
        BlockUtils.place(lllllllllllllllllIlllIIlIIlllIII, Hand.MAIN_HAND, lllllllllllllllllIlllIIlIIllIIll, lllllllllllllllllIlllIIlIIlllIIl.rotate.get(), -15, lllllllllllllllllIlllIIlIIlllIIl.renderSwing.get(), true, true, true);
    }

    public boolean hasSafeWalk() {
        Scaffold lllllllllllllllllIlllIIIlllIIlII;
        return lllllllllllllllllIlllIIIlllIIlII.safeWalk.get();
    }

    @Override
    public void onActivate() {
        Scaffold lllllllllllllllllIlllIIlIlIlllIl;
        lllllllllllllllllIlllIIlIlIlllIl.lastWasSneaking = lllllllllllllllllIlllIIlIlIlllIl.mc.player.input.sneaking;
        if (lllllllllllllllllIlllIIlIlIlllIl.lastWasSneaking) {
            lllllllllllllllllIlllIIlIlIlllIl.lastSneakingY = lllllllllllllllllIlllIIlIlIlllIl.mc.player.getY();
        }
    }
}

