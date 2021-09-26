/*
 * Decompiled with CFR 0.151.
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
    private final SettingGroup sgGeneral;
    private BlockPos bestBlock;
    private final Setting<Double> placeRange;
    private int phase;
    private final Setting<Mode> mode;
    private int z;
    private final Setting<List<Block>> throwawayItems;
    private final Setting<Boolean> fillHopper;
    private final Setting<Boolean> autoMove;
    private int x;

    private List<Block> setDefaultBlocks() {
        ArrayList<Block> arrayList = new ArrayList<Block>();
        arrayList.add(Blocks.OBSIDIAN);
        arrayList.add(Blocks.COBBLESTONE);
        return arrayList;
    }

    private boolean isValidSlot(int n) {
        return n == -1 || n >= 9;
    }

    private double lambda$onTick$0(BlockPos BlockPos2) {
        return this.mc.player.squaredDistanceTo((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ());
    }

    @Override
    public void onActivate() {
        this.bestBlock = this.findValidBlocksDispenser();
    }

    private double lambda$findValidBlocksDispenser$1(BlockPos BlockPos2) {
        return this.mc.player.squaredDistanceTo((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ());
    }

    public Auto32K() {
        super(Categories.Combat, "auto-32k", "Automatically attacks other players with a 32k weapon.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The bypass mode used.").defaultValue(Mode.Dispenser).build());
        this.placeRange = this.sgGeneral.add(new DoubleSetting.Builder().name("place-range").description("The distance in a single direction the shulker is placed.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        this.fillHopper = this.sgGeneral.add(new BoolSetting.Builder().name("fill-hopper").description("Fills all slots of the hopper except one for the 32k.").defaultValue(true).build());
        this.throwawayItems = this.sgGeneral.add(new BlockListSetting.Builder().name("throwaway-blocks").description("Whitelisted blocks to use to fill the hopper.").defaultValue(this.setDefaultBlocks()).build());
        this.autoMove = this.sgGeneral.add(new BoolSetting.Builder().name("auto-move").description("Moves the 32K into your inventory automatically.").defaultValue(true).build());
        this.phase = 0;
    }

    @Override
    public void onDeactivate() {
        this.phase = 0;
    }

    private List<BlockPos> findValidBlocksHopper() {
        Iterator<BlockPos> iterator = this.getRange(this.mc.player.getBlockPos(), this.placeRange.get()).iterator();
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        BlockPos BlockPos2 = null;
        while (iterator.hasNext()) {
            if (BlockPos2 != null && !this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && this.mc.world.getBlockState(BlockPos2.up()).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.up().getX(), (double)BlockPos2.up().getY(), (double)BlockPos2.up().getZ(), (double)BlockPos2.up().getX() + 1.0, (double)BlockPos2.up().getY() + 2.0, (double)BlockPos2.up().getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.up(2)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.up(2).getX(), (double)BlockPos2.up(2).getY(), (double)BlockPos2.up(2).getZ(), (double)BlockPos2.up(2).getX() + 1.0, (double)BlockPos2.up(2).getY() + 2.0, (double)BlockPos2.up(2).getZ() + 1.0)).isEmpty()) {
                arrayList.add(BlockPos2);
            }
            BlockPos2 = iterator.next();
        }
        return arrayList;
    }

    private List<BlockPos> getRange(BlockPos BlockPos2, double d) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (double d2 = (double)BlockPos2.getX() - d; d2 < (double)BlockPos2.getX() + d; d2 += 1.0) {
            for (double d3 = (double)BlockPos2.getZ() - d; d3 < (double)BlockPos2.getZ() + d; d3 += 1.0) {
                for (int i = BlockPos2.getY() - 3; i < BlockPos2.getY() + 3; ++i) {
                    BlockPos BlockPos3 = new BlockPos(d2, (double)i, d3);
                    arrayList.add(BlockPos3);
                    if (null == null) continue;
                    return null;
                }
            }
        }
        return arrayList;
    }

    private BlockPos findValidBlocksDispenser() {
        List<BlockPos> list = this.getRange(this.mc.player.getBlockPos(), this.placeRange.get());
        list.sort(Comparator.comparingDouble(this::lambda$findValidBlocksDispenser$1));
        Iterator<BlockPos> iterator = list.iterator();
        BlockPos BlockPos2 = null;
        while (iterator.hasNext()) {
            if (BlockPos2 != null && !this.mc.world.getBlockState(BlockPos2).getMaterial().isReplaceable() && this.mc.world.getBlockState(BlockPos2.up()).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.up().getX(), (double)BlockPos2.up().getY(), (double)BlockPos2.up().getZ(), (double)BlockPos2.up().getX() + 1.0, (double)BlockPos2.up().getY() + 2.0, (double)BlockPos2.up().getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.up(2)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.up(2).getX(), (double)BlockPos2.up(2).getY(), (double)BlockPos2.up(2).getZ(), (double)BlockPos2.up(2).getX() + 1.0, (double)BlockPos2.up(2).getY() + 2.0, (double)BlockPos2.up(2).getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.up(3)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.up(3).getX(), (double)BlockPos2.up(3).getY(), (double)BlockPos2.up(3).getZ(), (double)BlockPos2.up(2).getX() + 1.0, (double)BlockPos2.up(2).getY() + 2.0, (double)BlockPos2.up(2).getZ() + 1.0)).isEmpty()) {
                if (this.mc.world.getBlockState(BlockPos2.add(-1, 1, 0)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(-1, 1, 0).getX(), (double)BlockPos2.add(-1, 1, 0).getY(), (double)BlockPos2.add(-1, 1, 0).getZ(), (double)BlockPos2.add(-1, 1, 0).getX() + 1.0, (double)BlockPos2.add(-1, 1, 0).getY() + 2.0, (double)BlockPos2.add(-1, 1, 0).getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(-1, 0, 0).getX(), (double)BlockPos2.add(-1, 0, 0).getY(), (double)BlockPos2.add(-1, 0, 0).getZ(), (double)BlockPos2.add(-1, 0, 0).getX() + 1.0, (double)BlockPos2.add(-1, 0, 0).getY() + 2.0, (double)BlockPos2.add(-1, 0, 0).getZ() + 1.0)).isEmpty()) {
                    this.x = -1;
                    this.z = 0;
                    return BlockPos2;
                }
                if (this.mc.world.getBlockState(BlockPos2.add(1, 1, 0)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(1, 1, 0).getX(), (double)BlockPos2.add(1, 1, 0).getY(), (double)BlockPos2.add(1, 1, 0).getZ(), (double)BlockPos2.add(1, 1, 0).getX() + 1.0, (double)BlockPos2.add(1, 1, 0).getY() + 2.0, (double)BlockPos2.add(1, 1, 0).getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.add(1, 0, 0)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(1, 0, 0).getX(), (double)BlockPos2.add(1, 0, 0).getY(), (double)BlockPos2.add(1, 0, 0).getZ(), (double)BlockPos2.add(1, 0, 0).getX() + 1.0, (double)BlockPos2.add(1, 0, 0).getY() + 2.0, (double)BlockPos2.add(1, 0, 0).getZ() + 1.0)).isEmpty()) {
                    this.x = 1;
                    this.z = 0;
                    return BlockPos2;
                }
                if (this.mc.world.getBlockState(BlockPos2.add(0, 1, -1)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(0, 1, -1).getX(), (double)BlockPos2.add(0, 1, -1).getY(), (double)BlockPos2.add(0, 1, -1).getZ(), (double)BlockPos2.add(0, 1, -1).getX() + 1.0, (double)BlockPos2.add(0, 1, -1).getY() + 2.0, (double)BlockPos2.add(0, 1, -1).getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.add(0, 0, -1)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(0, 0, -1).getX(), (double)BlockPos2.add(0, 0, -1).getY(), (double)BlockPos2.add(0, 0, -1).getZ(), (double)BlockPos2.add(0, 0, -1).getX() + 1.0, (double)BlockPos2.add(0, 0, -1).getY() + 2.0, (double)BlockPos2.add(0, 0, -1).getZ() + 1.0)).isEmpty()) {
                    this.x = 0;
                    this.z = -1;
                    return BlockPos2;
                }
                if (this.mc.world.getBlockState(BlockPos2.add(0, 1, 1)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(0, 1, 1).getX(), (double)BlockPos2.add(0, 1, 1).getY(), (double)BlockPos2.add(0, 1, 1).getZ(), (double)BlockPos2.add(0, 1, 1).getX() + 1.0, (double)BlockPos2.add(0, 1, 1).getY() + 2.0, (double)BlockPos2.add(0, 1, 1).getZ() + 1.0)).isEmpty() && this.mc.world.getBlockState(BlockPos2.add(0, 0, 1)).getBlock() == Blocks.AIR && this.mc.world.getOtherEntities(null, new Box((double)BlockPos2.add(0, 0, 1).getX(), (double)BlockPos2.add(0, 0, 1).getY(), (double)BlockPos2.add(0, 0, 1).getZ(), (double)BlockPos2.add(0, 0, 1).getX() + 1.0, (double)BlockPos2.add(0, 0, 1).getY() + 2.0, (double)BlockPos2.add(0, 0, 1).getZ() + 1.0)).isEmpty()) {
                    this.x = 0;
                    this.z = 1;
                    return BlockPos2;
                }
            }
            BlockPos2 = iterator.next();
        }
        return null;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.phase <= 7) {
            if (this.mode.get() == Mode.Hopper) {
                int n = InvUtils.findItemWithCount((Item)Items.SHULKER_BOX).slot;
                int n2 = InvUtils.findItemWithCount((Item)Items.HOPPER).slot;
                if (this.isValidSlot(n) || this.isValidSlot(n2)) {
                    return;
                }
                List<BlockPos> list = this.findValidBlocksHopper();
                list.sort(Comparator.comparingDouble(this::lambda$onTick$0));
                Iterator<BlockPos> iterator = list.iterator();
                BlockPos BlockPos2 = null;
                if (iterator.hasNext()) {
                    BlockPos2 = iterator.next();
                }
                if (BlockPos2 != null) {
                    this.mc.player.inventory.selectedSlot = n2;
                    while (!PlayerUtils.placeBlock(BlockPos2, Hand.MAIN_HAND) && iterator.hasNext()) {
                        BlockPos2 = iterator.next().up();
                    }
                    this.mc.player.setSneaking(true);
                    this.mc.player.inventory.selectedSlot = n;
                    if (!PlayerUtils.placeBlock(BlockPos2.up(), Hand.MAIN_HAND)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        this.toggle();
                        return;
                    }
                    this.mc.player.setSneaking(false);
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), this.mc.player.getHorizontalFacing(), BlockPos2.up(), false));
                    this.phase = 8;
                }
            } else if (this.mode.get() == Mode.Dispenser) {
                int n = InvUtils.findItemWithCount((Item)Items.SHULKER_BOX).slot;
                int n3 = InvUtils.findItemWithCount((Item)Items.HOPPER).slot;
                int n4 = InvUtils.findItemWithCount((Item)Items.DISPENSER).slot;
                int n5 = InvUtils.findItemWithCount((Item)Items.REDSTONE_BLOCK).slot;
                if (this.isValidSlot(n) && this.mode.get() == Mode.Hopper || this.isValidSlot(n3) || this.isValidSlot(n4) || this.isValidSlot(n5)) {
                    return;
                }
                if (this.phase == 0) {
                    this.bestBlock = this.findValidBlocksDispenser();
                    this.mc.player.inventory.selectedSlot = n3;
                    if (this.bestBlock == null) {
                        return;
                    }
                    if (!PlayerUtils.placeBlock(this.bestBlock.add(this.x, 0, this.z), Hand.MAIN_HAND)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        this.toggle();
                        return;
                    }
                    ++this.phase;
                } else if (this.phase == 1) {
                    this.mc.player.inventory.selectedSlot = n4;
                    if (this.x == -1) {
                        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(-90.0f, this.mc.player.pitch, this.mc.player.isOnGround()));
                    } else if (this.x == 1) {
                        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(90.0f, this.mc.player.pitch, this.mc.player.isOnGround()));
                    } else if (this.z == -1) {
                        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(1.0f, this.mc.player.pitch, this.mc.player.isOnGround()));
                    } else if (this.z == 1) {
                        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(179.0f, this.mc.player.pitch, this.mc.player.isOnGround()));
                    }
                    ++this.phase;
                } else if (this.phase == 2) {
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), Direction.UP, this.bestBlock, false));
                    ++this.phase;
                } else if (this.phase == 3) {
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), this.mc.player.getHorizontalFacing().getOpposite(), this.bestBlock.up(), false));
                    ++this.phase;
                } else if (this.phase == 4 && this.mc.currentScreen instanceof Generic3x3ContainerScreen) {
                    this.mc.player.getSpeed();
                    InvUtils.move().from(n).toId(4);
                    ++this.phase;
                } else if (this.phase == 5 && this.mc.currentScreen instanceof Generic3x3ContainerScreen) {
                    this.mc.player.closeHandledScreen();
                    ++this.phase;
                } else if (this.phase == 6) {
                    this.mc.player.inventory.selectedSlot = n5;
                    this.mc.player.setSneaking(true);
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), this.mc.player.getHorizontalFacing().getOpposite(), this.bestBlock.up(2), false));
                    this.mc.player.setSneaking(false);
                    ++this.phase;
                } else if (this.phase == 7) {
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), this.mc.player.getHorizontalFacing().getOpposite(), this.bestBlock.add(this.x, 0, this.z), false));
                    ++this.phase;
                }
            }
        } else if (this.phase == 8) {
            if (this.mc.currentScreen instanceof HopperScreen) {
                int n;
                int n6;
                int n7;
                if (this.fillHopper.get().booleanValue() && !this.throwawayItems.get().isEmpty()) {
                    n7 = -1;
                    n6 = 0;
                    Iterator<Block> iterator = this.throwawayItems.get().iterator();
                    Item Item2 = iterator.next().asItem();
                    while (iterator.hasNext()) {
                        for (int i = 5; i <= 40; ++i) {
                            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
                            if (ItemStack2.getItem() != Item2 || ItemStack2.getCount() < 4) continue;
                            n7 = i;
                            n6 = ItemStack2.getCount();
                            break;
                        }
                        if (n6 >= 4) break;
                        Item2 = iterator.next().asItem();
                    }
                    for (n = 1; n < 5; ++n) {
                        if (!(this.mc.player.currentScreenHandler.getSlot(n).getStack().getItem() instanceof AirBlockItem)) continue;
                        InvUtils.move().from(n7 - 4).toId(n);
                        if (null == null) continue;
                        return;
                    }
                }
                n7 = 1;
                n6 = -1;
                int n8 = -1;
                for (n = 32; n < 41; ++n) {
                    if (EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)this.mc.player.currentScreenHandler.getSlot(n).getStack()) > 5) {
                        n7 = 0;
                        n6 = n;
                        break;
                    }
                    if (!(this.mc.player.currentScreenHandler.getSlot(n).getStack().getItem() instanceof SwordItem) || EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)this.mc.player.currentScreenHandler.getSlot(n).getStack()) > 5) continue;
                    n8 = n;
                    if (-1 < 1) continue;
                    return;
                }
                if (n8 != -1) {
                    InvUtils.drop().slot(n8);
                }
                if (this.autoMove.get().booleanValue() && n7 != 0) {
                    n = this.mc.player.inventory.getEmptySlot();
                    if (n < 9 && n != -1 && EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)this.mc.player.currentScreenHandler.getSlot(0).getStack()) > 5) {
                        InvUtils.move().fromId(0).to(n - 4);
                    } else if (EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)this.mc.player.currentScreenHandler.getSlot(0).getStack()) <= 5 && this.mc.player.currentScreenHandler.getSlot(0).getStack().getItem() != Items.AIR) {
                        InvUtils.drop().slotId(0);
                    }
                }
                if (n6 != -1) {
                    this.mc.player.inventory.selectedSlot = n6 - 32;
                }
            } else {
                this.toggle();
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Dispenser;
        public static final /* enum */ Mode Hopper;

        private static Mode[] $values() {
            return new Mode[]{Hopper, Dispenser};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Hopper = new Mode();
            Dispenser = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

