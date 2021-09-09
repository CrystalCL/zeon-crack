/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.AirBlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.SwordItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Box
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookOnly
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen
 *  net.minecraft.client.gui.screen.ingame.HopperScreen
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Box;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HopperScreen;

public class Auto32K
extends Module {
    private final /* synthetic */ Setting<Boolean> fillHopper;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> placeRange;
    private /* synthetic */ int z;
    private /* synthetic */ BlockPos bestBlock;
    private final /* synthetic */ Setting<List<Block>> throwawayItems;
    private /* synthetic */ int x;
    private final /* synthetic */ Setting<Boolean> autoMove;
    private final /* synthetic */ Setting<Mode> mode;
    private /* synthetic */ int phase;

    private boolean isValidSlot(int llIIlIlIlI) {
        return llIIlIlIlI == -1 || llIIlIlIlI >= 9;
    }

    @Override
    public void onDeactivate() {
        lllIIIIlII.phase = 0;
    }

    private List<Block> setDefaultBlocks() {
        ArrayList<Block> llIIlIIllI = new ArrayList<Block>();
        llIIlIIllI.add(Blocks.OBSIDIAN);
        llIIlIIllI.add(Blocks.COBBLESTONE);
        return llIIlIIllI;
    }

    @EventHandler
    private void onTick(TickEvent.Post llIllIIIlI) {
        Auto32K llIllIIIll;
        if (llIllIIIll.phase <= 7) {
            if (llIllIIIll.mode.get() == Mode.Hopper) {
                int llIllllIII = InvUtils.findItemWithCount((Item)Items.SHULKER_BOX).slot;
                int llIlllIlll = InvUtils.findItemWithCount((Item)Items.HOPPER).slot;
                if (llIllIIIll.isValidSlot(llIllllIII) || llIllIIIll.isValidSlot(llIlllIlll)) {
                    return;
                }
                List<BlockPos> llIlllIllI = llIllIIIll.findValidBlocksHopper();
                llIlllIllI.sort(Comparator.comparingDouble(llIIIllIIl -> {
                    Auto32K llIIIllIlI;
                    return llIIIllIlI.mc.player.squaredDistanceTo((double)llIIIllIIl.getX(), (double)llIIIllIIl.getY(), (double)llIIIllIIl.getZ());
                }));
                Iterator<BlockPos> llIlllIlIl = llIlllIllI.iterator();
                BlockPos llIlllIlII = null;
                if (llIlllIlIl.hasNext()) {
                    llIlllIlII = llIlllIlIl.next();
                }
                if (llIlllIlII != null) {
                    llIllIIIll.mc.player.inventory.selectedSlot = llIlllIlll;
                    while (!PlayerUtils.placeBlock(llIlllIlII, Hand.MAIN_HAND) && llIlllIlIl.hasNext()) {
                        llIlllIlII = llIlllIlIl.next().up();
                    }
                    llIllIIIll.mc.player.setSneaking(true);
                    llIllIIIll.mc.player.inventory.selectedSlot = llIllllIII;
                    if (!PlayerUtils.placeBlock(llIlllIlII.up(), Hand.MAIN_HAND)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        llIllIIIll.toggle();
                        return;
                    }
                    llIllIIIll.mc.player.setSneaking(false);
                    llIllIIIll.mc.interactionManager.interactBlock(llIllIIIll.mc.player, llIllIIIll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIllIIIll.mc.player.getPos(), llIllIIIll.mc.player.getHorizontalFacing(), llIlllIlII.up(), false));
                    llIllIIIll.phase = 8;
                }
            } else if (llIllIIIll.mode.get() == Mode.Dispenser) {
                int llIlllIIll = InvUtils.findItemWithCount((Item)Items.SHULKER_BOX).slot;
                int llIlllIIlI = InvUtils.findItemWithCount((Item)Items.HOPPER).slot;
                int llIlllIIIl = InvUtils.findItemWithCount((Item)Items.DISPENSER).slot;
                int llIlllIIII = InvUtils.findItemWithCount((Item)Items.REDSTONE_BLOCK).slot;
                if (llIllIIIll.isValidSlot(llIlllIIll) && llIllIIIll.mode.get() == Mode.Hopper || llIllIIIll.isValidSlot(llIlllIIlI) || llIllIIIll.isValidSlot(llIlllIIIl) || llIllIIIll.isValidSlot(llIlllIIII)) {
                    return;
                }
                if (llIllIIIll.phase == 0) {
                    llIllIIIll.bestBlock = llIllIIIll.findValidBlocksDispenser();
                    llIllIIIll.mc.player.inventory.selectedSlot = llIlllIIlI;
                    if (llIllIIIll.bestBlock == null) {
                        return;
                    }
                    if (!PlayerUtils.placeBlock(llIllIIIll.bestBlock.add(llIllIIIll.x, 0, llIllIIIll.z), Hand.MAIN_HAND)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        llIllIIIll.toggle();
                        return;
                    }
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 1) {
                    llIllIIIll.mc.player.inventory.selectedSlot = llIlllIIIl;
                    if (llIllIIIll.x == -1) {
                        llIllIIIll.mc.player.networkHandler.sendPacket((Packet)new LookOnly(-90.0f, llIllIIIll.mc.player.pitch, llIllIIIll.mc.player.isOnGround()));
                    } else if (llIllIIIll.x == 1) {
                        llIllIIIll.mc.player.networkHandler.sendPacket((Packet)new LookOnly(90.0f, llIllIIIll.mc.player.pitch, llIllIIIll.mc.player.isOnGround()));
                    } else if (llIllIIIll.z == -1) {
                        llIllIIIll.mc.player.networkHandler.sendPacket((Packet)new LookOnly(1.0f, llIllIIIll.mc.player.pitch, llIllIIIll.mc.player.isOnGround()));
                    } else if (llIllIIIll.z == 1) {
                        llIllIIIll.mc.player.networkHandler.sendPacket((Packet)new LookOnly(179.0f, llIllIIIll.mc.player.pitch, llIllIIIll.mc.player.isOnGround()));
                    }
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 2) {
                    llIllIIIll.mc.interactionManager.interactBlock(llIllIIIll.mc.player, llIllIIIll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIllIIIll.mc.player.getPos(), Direction.UP, llIllIIIll.bestBlock, false));
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 3) {
                    llIllIIIll.mc.interactionManager.interactBlock(llIllIIIll.mc.player, llIllIIIll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIllIIIll.mc.player.getPos(), llIllIIIll.mc.player.getHorizontalFacing().getOpposite(), llIllIIIll.bestBlock.up(), false));
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 4 && llIllIIIll.mc.currentScreen instanceof Generic3x3ContainerScreen) {
                    llIllIIIll.mc.player.getSpeed();
                    InvUtils.move().from(llIlllIIll).toId(4);
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 5 && llIllIIIll.mc.currentScreen instanceof Generic3x3ContainerScreen) {
                    llIllIIIll.mc.player.closeHandledScreen();
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 6) {
                    llIllIIIll.mc.player.inventory.selectedSlot = llIlllIIII;
                    llIllIIIll.mc.player.setSneaking(true);
                    llIllIIIll.mc.interactionManager.interactBlock(llIllIIIll.mc.player, llIllIIIll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIllIIIll.mc.player.getPos(), llIllIIIll.mc.player.getHorizontalFacing().getOpposite(), llIllIIIll.bestBlock.up(2), false));
                    llIllIIIll.mc.player.setSneaking(false);
                    ++llIllIIIll.phase;
                } else if (llIllIIIll.phase == 7) {
                    llIllIIIll.mc.interactionManager.interactBlock(llIllIIIll.mc.player, llIllIIIll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIllIIIll.mc.player.getPos(), llIllIIIll.mc.player.getHorizontalFacing().getOpposite(), llIllIIIll.bestBlock.add(llIllIIIll.x, 0, llIllIIIll.z), false));
                    ++llIllIIIll.phase;
                }
            }
        } else if (llIllIIIll.phase == 8) {
            if (llIllIIIll.mc.currentScreen instanceof HopperScreen) {
                if (llIllIIIll.fillHopper.get().booleanValue() && !llIllIIIll.throwawayItems.get().isEmpty()) {
                    int llIllIlIll = -1;
                    int llIllIlIlI = 0;
                    Iterator<Block> llIllIlIIl = llIllIIIll.throwawayItems.get().iterator();
                    Item llIllIllIl = llIllIlIIl.next().asItem();
                    while (llIllIlIIl.hasNext()) {
                        for (int llIllIlllI = 5; llIllIlllI <= 40; ++llIllIlllI) {
                            ItemStack llIllIllll = llIllIIIll.mc.player.inventory.getStack(llIllIlllI);
                            if (llIllIllll.getItem() != llIllIllIl || llIllIllll.getCount() < 4) continue;
                            llIllIlIll = llIllIlllI;
                            llIllIlIlI = llIllIllll.getCount();
                            break;
                        }
                        if (llIllIlIlI >= 4) break;
                        llIllIllIl = llIllIlIIl.next().asItem();
                    }
                    for (int llIllIllII = 1; llIllIllII < 5; ++llIllIllII) {
                        if (!(llIllIIIll.mc.player.currentScreenHandler.getSlot(llIllIllII).getStack().getItem() instanceof AirBlockItem)) continue;
                        InvUtils.move().from(llIllIlIll - 4).toId(llIllIllII);
                    }
                }
                boolean llIllIIllI = true;
                int llIllIIlIl = -1;
                int llIllIIlII = -1;
                for (int llIllIlIII = 32; llIllIlIII < 41; ++llIllIlIII) {
                    if (EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)llIllIIIll.mc.player.currentScreenHandler.getSlot(llIllIlIII).getStack()) > 5) {
                        llIllIIllI = false;
                        llIllIIlIl = llIllIlIII;
                        break;
                    }
                    if (!(llIllIIIll.mc.player.currentScreenHandler.getSlot(llIllIlIII).getStack().getItem() instanceof SwordItem) || EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)llIllIIIll.mc.player.currentScreenHandler.getSlot(llIllIlIII).getStack()) > 5) continue;
                    llIllIIlII = llIllIlIII;
                }
                if (llIllIIlII != -1) {
                    InvUtils.drop().slot(llIllIIlII);
                }
                if (llIllIIIll.autoMove.get().booleanValue() && llIllIIllI) {
                    int llIllIIlll = llIllIIIll.mc.player.inventory.getEmptySlot();
                    if (llIllIIlll < 9 && llIllIIlll != -1 && EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)llIllIIIll.mc.player.currentScreenHandler.getSlot(0).getStack()) > 5) {
                        InvUtils.move().fromId(0).to(llIllIIlll - 4);
                    } else if (EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)llIllIIIll.mc.player.currentScreenHandler.getSlot(0).getStack()) <= 5 && llIllIIIll.mc.player.currentScreenHandler.getSlot(0).getStack().getItem() != Items.AIR) {
                        InvUtils.drop().slotId(0);
                    }
                }
                if (llIllIIlIl != -1) {
                    llIllIIIll.mc.player.inventory.selectedSlot = llIllIIlIl - 32;
                }
            } else {
                llIllIIIll.toggle();
            }
        }
    }

    private BlockPos findValidBlocksDispenser() {
        Auto32K llIlIIIllI;
        List<BlockPos> llIlIIlIII = llIlIIIllI.getRange(llIlIIIllI.mc.player.getBlockPos(), llIlIIIllI.placeRange.get());
        llIlIIlIII.sort(Comparator.comparingDouble(llIIIlllll -> {
            Auto32K llIIlIIIlI;
            return llIIlIIIlI.mc.player.squaredDistanceTo((double)llIIIlllll.getX(), (double)llIIIlllll.getY(), (double)llIIIlllll.getZ());
        }));
        Iterator<BlockPos> llIlIIIlll = llIlIIlIII.iterator();
        BlockPos llIlIIlIlI = null;
        while (llIlIIIlll.hasNext()) {
            if (llIlIIlIlI != null && !llIlIIIllI.mc.world.getBlockState(llIlIIlIlI).getMaterial().isReplaceable() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.up()).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.up().getX(), (double)llIlIIlIlI.up().getY(), (double)llIlIIlIlI.up().getZ(), (double)llIlIIlIlI.up().getX() + 1.0, (double)llIlIIlIlI.up().getY() + 2.0, (double)llIlIIlIlI.up().getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.up(2)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.up(2).getX(), (double)llIlIIlIlI.up(2).getY(), (double)llIlIIlIlI.up(2).getZ(), (double)llIlIIlIlI.up(2).getX() + 1.0, (double)llIlIIlIlI.up(2).getY() + 2.0, (double)llIlIIlIlI.up(2).getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.up(3)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.up(3).getX(), (double)llIlIIlIlI.up(3).getY(), (double)llIlIIlIlI.up(3).getZ(), (double)llIlIIlIlI.up(2).getX() + 1.0, (double)llIlIIlIlI.up(2).getY() + 2.0, (double)llIlIIlIlI.up(2).getZ() + 1.0)).isEmpty()) {
                if (llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(-1, 1, 0)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(-1, 1, 0).getX(), (double)llIlIIlIlI.add(-1, 1, 0).getY(), (double)llIlIIlIlI.add(-1, 1, 0).getZ(), (double)llIlIIlIlI.add(-1, 1, 0).getX() + 1.0, (double)llIlIIlIlI.add(-1, 1, 0).getY() + 2.0, (double)llIlIIlIlI.add(-1, 1, 0).getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(-1, 0, 0)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(-1, 0, 0).getX(), (double)llIlIIlIlI.add(-1, 0, 0).getY(), (double)llIlIIlIlI.add(-1, 0, 0).getZ(), (double)llIlIIlIlI.add(-1, 0, 0).getX() + 1.0, (double)llIlIIlIlI.add(-1, 0, 0).getY() + 2.0, (double)llIlIIlIlI.add(-1, 0, 0).getZ() + 1.0)).isEmpty()) {
                    llIlIIIllI.x = -1;
                    llIlIIIllI.z = 0;
                    return llIlIIlIlI;
                }
                if (llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(1, 1, 0)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(1, 1, 0).getX(), (double)llIlIIlIlI.add(1, 1, 0).getY(), (double)llIlIIlIlI.add(1, 1, 0).getZ(), (double)llIlIIlIlI.add(1, 1, 0).getX() + 1.0, (double)llIlIIlIlI.add(1, 1, 0).getY() + 2.0, (double)llIlIIlIlI.add(1, 1, 0).getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(1, 0, 0)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(1, 0, 0).getX(), (double)llIlIIlIlI.add(1, 0, 0).getY(), (double)llIlIIlIlI.add(1, 0, 0).getZ(), (double)llIlIIlIlI.add(1, 0, 0).getX() + 1.0, (double)llIlIIlIlI.add(1, 0, 0).getY() + 2.0, (double)llIlIIlIlI.add(1, 0, 0).getZ() + 1.0)).isEmpty()) {
                    llIlIIIllI.x = 1;
                    llIlIIIllI.z = 0;
                    return llIlIIlIlI;
                }
                if (llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(0, 1, -1)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(0, 1, -1).getX(), (double)llIlIIlIlI.add(0, 1, -1).getY(), (double)llIlIIlIlI.add(0, 1, -1).getZ(), (double)llIlIIlIlI.add(0, 1, -1).getX() + 1.0, (double)llIlIIlIlI.add(0, 1, -1).getY() + 2.0, (double)llIlIIlIlI.add(0, 1, -1).getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(0, 0, -1)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(0, 0, -1).getX(), (double)llIlIIlIlI.add(0, 0, -1).getY(), (double)llIlIIlIlI.add(0, 0, -1).getZ(), (double)llIlIIlIlI.add(0, 0, -1).getX() + 1.0, (double)llIlIIlIlI.add(0, 0, -1).getY() + 2.0, (double)llIlIIlIlI.add(0, 0, -1).getZ() + 1.0)).isEmpty()) {
                    llIlIIIllI.x = 0;
                    llIlIIIllI.z = -1;
                    return llIlIIlIlI;
                }
                if (llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(0, 1, 1)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(0, 1, 1).getX(), (double)llIlIIlIlI.add(0, 1, 1).getY(), (double)llIlIIlIlI.add(0, 1, 1).getZ(), (double)llIlIIlIlI.add(0, 1, 1).getX() + 1.0, (double)llIlIIlIlI.add(0, 1, 1).getY() + 2.0, (double)llIlIIlIlI.add(0, 1, 1).getZ() + 1.0)).isEmpty() && llIlIIIllI.mc.world.getBlockState(llIlIIlIlI.add(0, 0, 1)).getBlock() == Blocks.AIR && llIlIIIllI.mc.world.getOtherEntities(null, new Box((double)llIlIIlIlI.add(0, 0, 1).getX(), (double)llIlIIlIlI.add(0, 0, 1).getY(), (double)llIlIIlIlI.add(0, 0, 1).getZ(), (double)llIlIIlIlI.add(0, 0, 1).getX() + 1.0, (double)llIlIIlIlI.add(0, 0, 1).getY() + 2.0, (double)llIlIIlIlI.add(0, 0, 1).getZ() + 1.0)).isEmpty()) {
                    llIlIIIllI.x = 0;
                    llIlIIIllI.z = 1;
                    return llIlIIlIlI;
                }
            }
            llIlIIlIlI = llIlIIIlll.next();
        }
        return null;
    }

    private List<BlockPos> getRange(BlockPos llIIllIllI, double llIIllIIlI) {
        ArrayList<BlockPos> llIIllIlII = new ArrayList<BlockPos>();
        for (double llIIlllIII = (double)llIIllIllI.getX() - llIIllIIlI; llIIlllIII < (double)llIIllIllI.getX() + llIIllIIlI; llIIlllIII += 1.0) {
            for (double llIIlllIIl = (double)llIIllIllI.getZ() - llIIllIIlI; llIIlllIIl < (double)llIIllIllI.getZ() + llIIllIIlI; llIIlllIIl += 1.0) {
                for (int llIIlllIlI = llIIllIllI.getY() - 3; llIIlllIlI < llIIllIllI.getY() + 3; ++llIIlllIlI) {
                    BlockPos llIIlllIll = new BlockPos(llIIlllIII, (double)llIIlllIlI, llIIlllIIl);
                    llIIllIlII.add(llIIlllIll);
                }
            }
        }
        return llIIllIlII;
    }

    @Override
    public void onActivate() {
        Auto32K lllIIIIIII;
        lllIIIIIII.bestBlock = lllIIIIIII.findValidBlocksDispenser();
    }

    public Auto32K() {
        super(Categories.Combat, "auto-32k", "Automatically attacks other players with a 32k weapon.");
        Auto32K lllIIIIlll;
        lllIIIIlll.sgGeneral = lllIIIIlll.settings.getDefaultGroup();
        lllIIIIlll.mode = lllIIIIlll.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The bypass mode used.").defaultValue(Mode.Dispenser).build());
        lllIIIIlll.placeRange = lllIIIIlll.sgGeneral.add(new DoubleSetting.Builder().name("place-range").description("The distance in a single direction the shulker is placed.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        lllIIIIlll.fillHopper = lllIIIIlll.sgGeneral.add(new BoolSetting.Builder().name("fill-hopper").description("Fills all slots of the hopper except one for the 32k.").defaultValue(true).build());
        lllIIIIlll.throwawayItems = lllIIIIlll.sgGeneral.add(new BlockListSetting.Builder().name("throwaway-blocks").description("Whitelisted blocks to use to fill the hopper.").defaultValue(lllIIIIlll.setDefaultBlocks()).build());
        lllIIIIlll.autoMove = lllIIIIlll.sgGeneral.add(new BoolSetting.Builder().name("auto-move").description("Moves the 32K into your inventory automatically.").defaultValue(true).build());
        lllIIIIlll.phase = 0;
    }

    private List<BlockPos> findValidBlocksHopper() {
        Auto32K llIlIlIlIl;
        Iterator<BlockPos> llIlIlIlII = llIlIlIlIl.getRange(llIlIlIlIl.mc.player.getBlockPos(), llIlIlIlIl.placeRange.get()).iterator();
        ArrayList<BlockPos> llIlIlIIll = new ArrayList<BlockPos>();
        BlockPos llIlIlIllI = null;
        while (llIlIlIlII.hasNext()) {
            if (llIlIlIllI != null && !llIlIlIlIl.mc.world.getBlockState(llIlIlIllI).getMaterial().isReplaceable() && llIlIlIlIl.mc.world.getBlockState(llIlIlIllI.up()).getBlock() == Blocks.AIR && llIlIlIlIl.mc.world.getOtherEntities(null, new Box((double)llIlIlIllI.up().getX(), (double)llIlIlIllI.up().getY(), (double)llIlIlIllI.up().getZ(), (double)llIlIlIllI.up().getX() + 1.0, (double)llIlIlIllI.up().getY() + 2.0, (double)llIlIlIllI.up().getZ() + 1.0)).isEmpty() && llIlIlIlIl.mc.world.getBlockState(llIlIlIllI.up(2)).getBlock() == Blocks.AIR && llIlIlIlIl.mc.world.getOtherEntities(null, new Box((double)llIlIlIllI.up(2).getX(), (double)llIlIlIllI.up(2).getY(), (double)llIlIlIllI.up(2).getZ(), (double)llIlIlIllI.up(2).getX() + 1.0, (double)llIlIlIllI.up(2).getY() + 2.0, (double)llIlIlIllI.up(2).getZ() + 1.0)).isEmpty()) {
                llIlIlIIll.add(llIlIlIllI);
            }
            llIlIlIllI = llIlIlIlII.next();
        }
        return llIlIlIIll;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Hopper;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Dispenser;

        static {
            Hopper = new Mode();
            Dispenser = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llllllllllllllllIlllIIIIlIIIlIlI) {
            return Enum.valueOf(Mode.class, llllllllllllllllIlllIIIIlIIIlIlI);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Hopper, Dispenser};
        }

        private Mode() {
            Mode llllllllllllllllIlllIIIIlIIIIlII;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

