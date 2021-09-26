/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;

public class AntiBed
extends Module {
    private final Setting<Boolean> autoToggle;
    private final Setting<Boolean> onlyOnGround;
    private int place;
    private boolean closeScreen;
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> autoCenter;

    public AntiBed() {
        super(Categories.Combat, "anti-bed", "Prevents people from placing beds where you are standing.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.autoToggle = this.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles AntiBed off when finished.").defaultValue(false).build());
        this.autoCenter = this.sgGeneral.add(new BoolSetting.Builder().name("auto-center").description("Teleports you to the center of the blocks.").defaultValue(true).build());
        this.onlyOnGround = this.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Only toggles Anti Bed when you are standing on a block.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards where the blocks are placed.").defaultValue(true).build());
        this.place = -1;
        this.closeScreen = false;
    }

    private BlockPos checkBlocks() {
        BlockPos BlockPos2 = null;
        if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, 1, 1)).isAir()) {
            BlockPos2 = this.mc.player.getBlockPos().add(0, 1, 1);
        } else if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, 1, -1)).isAir()) {
            BlockPos2 = this.mc.player.getBlockPos().add(0, 1, -1);
        } else if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(1, 1, 0)).isAir()) {
            BlockPos2 = this.mc.player.getBlockPos().add(1, 1, 0);
        } else if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(-1, 1, 0)).isAir()) {
            BlockPos2 = this.mc.player.getBlockPos().add(-1, 1, 0);
        }
        return BlockPos2;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.closeScreen && this.mc.currentScreen instanceof SignEditScreen) {
            this.closeScreen = false;
            this.mc.player.closeScreen();
            return;
        }
        if (this.closeScreen) {
            return;
        }
        if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().up()).isAir()) {
            return;
        }
        if (this.onlyOnGround.get().booleanValue() && !this.mc.player.isOnGround()) {
            return;
        }
        if (this.place == 0) {
            --this.place;
            this.place(this.mc.player.inventory.selectedSlot, true);
        } else if (this.place > 0) {
            --this.place;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            Item Item2 = ItemStack2.getItem();
            Block Block2 = Block.getBlockFromItem((Item)Item2);
            if (Item2 == Items.STRING || Block2 instanceof TrapdoorBlock || Item2 == Items.COBWEB) {
                this.place(i, true);
                return;
            }
            if (Block2 instanceof SlabBlock) {
                this.mc.player.inventory.selectedSlot = i;
                this.mc.options.keySneak.setPressed(true);
                if (this.place == -1) {
                    this.place = 2;
                }
                return;
            }
            if (Block2 instanceof DoorBlock) {
                if (this.autoCenter.get().booleanValue()) {
                    Vec3d Vec3d2 = Utils.vec3d(this.mc.player.getBlockPos());
                    if (this.mc.player.getHorizontalFacing() == Direction.SOUTH) {
                        Vec3d2 = Vec3d2.add(0.5, 0.0, 0.7);
                    } else if (this.mc.player.getHorizontalFacing() == Direction.NORTH) {
                        Vec3d2 = Vec3d2.add(0.5, 0.0, 0.3);
                    } else if (this.mc.player.getHorizontalFacing() == Direction.EAST) {
                        Vec3d2 = Vec3d2.add(0.7, 0.0, 0.5);
                    } else if (this.mc.player.getHorizontalFacing() == Direction.WEST) {
                        Vec3d2 = Vec3d2.add(0.3, 0.0, 0.5);
                    }
                    this.mc.player.setPosition(Vec3d2.x, Vec3d2.y, Vec3d2.z);
                    this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(Vec3d2.x, Vec3d2.y, Vec3d2.z, this.mc.player.isOnGround()));
                }
                this.place(i, true);
                return;
            }
            if (Item2 == Items.LADDER) {
                if (this.autoCenter.get().booleanValue()) {
                    Vec3d Vec3d3 = Utils.vec3d(this.mc.player.getBlockPos());
                    BlockPos BlockPos2 = this.checkBlocks();
                    if (BlockPos2 == null) {
                        return;
                    }
                    if (Vec3d3.subtract((Vec3d)Utils.vec3d((BlockPos)BlockPos2)).x > 0.0) {
                        Vec3d3 = Vec3d3.add(0.7, 0.0, 0.5);
                    } else if (Vec3d3.subtract((Vec3d)Utils.vec3d((BlockPos)BlockPos2)).x < 0.0) {
                        Vec3d3 = Vec3d3.add(0.3, 0.0, 0.5);
                    } else if (Vec3d3.subtract((Vec3d)Utils.vec3d((BlockPos)BlockPos2)).z > 0.0) {
                        Vec3d3 = Vec3d3.add(0.5, 0.0, 0.7);
                    } else if (Vec3d3.subtract((Vec3d)Utils.vec3d((BlockPos)BlockPos2)).z < 0.0) {
                        Vec3d3 = Vec3d3.add(0.5, 0.0, 0.3);
                    }
                    this.mc.player.setPosition(Vec3d3.x, Vec3d3.y, Vec3d3.z);
                    this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(Vec3d3.x, Vec3d3.y, Vec3d3.z, this.mc.player.isOnGround()));
                }
                this.place(i, true);
                return;
            }
            if (Item2 instanceof BannerItem || Item2 == Items.LEVER || Item2 == Items.TORCH || Item2 == Items.REDSTONE_TORCH || Item2 instanceof SignItem || Item2 == Items.TRIPWIRE_HOOK || Block2 instanceof StoneButtonBlock || Block2 instanceof WoodenButtonBlock) {
                this.place(i, true);
                if (Item2 instanceof SignItem) {
                    this.closeScreen = true;
                }
                return;
            }
            if (Item2 != Items.SCAFFOLDING || ItemStack2.getCount() < 2) continue;
            this.place(i, false);
            this.place(i, true);
            return;
        }
    }

    @Override
    public void onDeactivate() {
        this.closeScreen = false;
    }

    private void place(int n, boolean bl) {
        BlockPos BlockPos2 = bl ? this.mc.player.getBlockPos().up() : this.mc.player.getBlockPos();
        if (BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 100, true) && this.autoToggle.get().booleanValue()) {
            this.toggle();
        }
    }
}

