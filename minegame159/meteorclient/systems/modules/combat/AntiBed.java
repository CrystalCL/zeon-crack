/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BannerItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.SignItem
 *  net.minecraft.block.Block
 *  net.minecraft.block.DoorBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.SlabBlock
 *  net.minecraft.block.StoneButtonBlock
 *  net.minecraft.block.TrapdoorBlock
 *  net.minecraft.block.WoodenButtonBlock
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  net.minecraft.client.gui.screen.ingame.SignEditScreen
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
    private final /* synthetic */ Setting<Boolean> onlyOnGround;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> autoToggle;
    private /* synthetic */ boolean closeScreen;
    private /* synthetic */ int place;
    private final /* synthetic */ Setting<Boolean> autoCenter;

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIllIIlllIlllIllI) {
        AntiBed llllllllllllllllIllIIlllIlllIlIl;
        if (llllllllllllllllIllIIlllIlllIlIl.closeScreen && llllllllllllllllIllIIlllIlllIlIl.mc.currentScreen instanceof SignEditScreen) {
            llllllllllllllllIllIIlllIlllIlIl.closeScreen = false;
            llllllllllllllllIllIIlllIlllIlIl.mc.player.closeScreen();
            return;
        }
        if (llllllllllllllllIllIIlllIlllIlIl.closeScreen) {
            return;
        }
        if (!llllllllllllllllIllIIlllIlllIlIl.mc.world.getBlockState(llllllllllllllllIllIIlllIlllIlIl.mc.player.getBlockPos().up()).isAir()) {
            return;
        }
        if (llllllllllllllllIllIIlllIlllIlIl.onlyOnGround.get().booleanValue() && !llllllllllllllllIllIIlllIlllIlIl.mc.player.isOnGround()) {
            return;
        }
        if (llllllllllllllllIllIIlllIlllIlIl.place == 0) {
            --llllllllllllllllIllIIlllIlllIlIl.place;
            llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIlllIlIl.mc.player.inventory.selectedSlot, true);
        } else if (llllllllllllllllIllIIlllIlllIlIl.place > 0) {
            --llllllllllllllllIllIIlllIlllIlIl.place;
        }
        for (int llllllllllllllllIllIIlllIllllIII = 0; llllllllllllllllIllIIlllIllllIII < 9; ++llllllllllllllllIllIIlllIllllIII) {
            ItemStack llllllllllllllllIllIIlllIllllIll = llllllllllllllllIllIIlllIlllIlIl.mc.player.inventory.getStack(llllllllllllllllIllIIlllIllllIII);
            Item llllllllllllllllIllIIlllIllllIlI = llllllllllllllllIllIIlllIllllIll.getItem();
            Block llllllllllllllllIllIIlllIllllIIl = Block.getBlockFromItem((Item)llllllllllllllllIllIIlllIllllIlI);
            if (llllllllllllllllIllIIlllIllllIlI == Items.STRING || llllllllllllllllIllIIlllIllllIIl instanceof TrapdoorBlock || llllllllllllllllIllIIlllIllllIlI == Items.COBWEB) {
                llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, true);
                return;
            }
            if (llllllllllllllllIllIIlllIllllIIl instanceof SlabBlock) {
                llllllllllllllllIllIIlllIlllIlIl.mc.player.inventory.selectedSlot = llllllllllllllllIllIIlllIllllIII;
                llllllllllllllllIllIIlllIlllIlIl.mc.options.keySneak.setPressed(true);
                if (llllllllllllllllIllIIlllIlllIlIl.place == -1) {
                    llllllllllllllllIllIIlllIlllIlIl.place = 2;
                }
                return;
            }
            if (llllllllllllllllIllIIlllIllllIIl instanceof DoorBlock) {
                if (llllllllllllllllIllIIlllIlllIlIl.autoCenter.get().booleanValue()) {
                    Vec3d llllllllllllllllIllIIlllIllllllI = Utils.vec3d(llllllllllllllllIllIIlllIlllIlIl.mc.player.getBlockPos());
                    if (llllllllllllllllIllIIlllIlllIlIl.mc.player.getHorizontalFacing() == Direction.SOUTH) {
                        llllllllllllllllIllIIlllIllllllI = llllllllllllllllIllIIlllIllllllI.add(0.5, 0.0, 0.7);
                    } else if (llllllllllllllllIllIIlllIlllIlIl.mc.player.getHorizontalFacing() == Direction.NORTH) {
                        llllllllllllllllIllIIlllIllllllI = llllllllllllllllIllIIlllIllllllI.add(0.5, 0.0, 0.3);
                    } else if (llllllllllllllllIllIIlllIlllIlIl.mc.player.getHorizontalFacing() == Direction.EAST) {
                        llllllllllllllllIllIIlllIllllllI = llllllllllllllllIllIIlllIllllllI.add(0.7, 0.0, 0.5);
                    } else if (llllllllllllllllIllIIlllIlllIlIl.mc.player.getHorizontalFacing() == Direction.WEST) {
                        llllllllllllllllIllIIlllIllllllI = llllllllllllllllIllIIlllIllllllI.add(0.3, 0.0, 0.5);
                    }
                    llllllllllllllllIllIIlllIlllIlIl.mc.player.setPosition(llllllllllllllllIllIIlllIllllllI.x, llllllllllllllllIllIIlllIllllllI.y, llllllllllllllllIllIIlllIllllllI.z);
                    llllllllllllllllIllIIlllIlllIlIl.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(llllllllllllllllIllIIlllIllllllI.x, llllllllllllllllIllIIlllIllllllI.y, llllllllllllllllIllIIlllIllllllI.z, llllllllllllllllIllIIlllIlllIlIl.mc.player.isOnGround()));
                }
                llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, true);
                return;
            }
            if (llllllllllllllllIllIIlllIllllIlI == Items.LADDER) {
                if (llllllllllllllllIllIIlllIlllIlIl.autoCenter.get().booleanValue()) {
                    Vec3d llllllllllllllllIllIIlllIlllllIl = Utils.vec3d(llllllllllllllllIllIIlllIlllIlIl.mc.player.getBlockPos());
                    BlockPos llllllllllllllllIllIIlllIlllllII = llllllllllllllllIllIIlllIlllIlIl.checkBlocks();
                    if (llllllllllllllllIllIIlllIlllllII == null) {
                        return;
                    }
                    if (llllllllllllllllIllIIlllIlllllIl.subtract((Vec3d)Utils.vec3d((BlockPos)llllllllllllllllIllIIlllIlllllII)).x > 0.0) {
                        llllllllllllllllIllIIlllIlllllIl = llllllllllllllllIllIIlllIlllllIl.add(0.7, 0.0, 0.5);
                    } else if (llllllllllllllllIllIIlllIlllllIl.subtract((Vec3d)Utils.vec3d((BlockPos)llllllllllllllllIllIIlllIlllllII)).x < 0.0) {
                        llllllllllllllllIllIIlllIlllllIl = llllllllllllllllIllIIlllIlllllIl.add(0.3, 0.0, 0.5);
                    } else if (llllllllllllllllIllIIlllIlllllIl.subtract((Vec3d)Utils.vec3d((BlockPos)llllllllllllllllIllIIlllIlllllII)).z > 0.0) {
                        llllllllllllllllIllIIlllIlllllIl = llllllllllllllllIllIIlllIlllllIl.add(0.5, 0.0, 0.7);
                    } else if (llllllllllllllllIllIIlllIlllllIl.subtract((Vec3d)Utils.vec3d((BlockPos)llllllllllllllllIllIIlllIlllllII)).z < 0.0) {
                        llllllllllllllllIllIIlllIlllllIl = llllllllllllllllIllIIlllIlllllIl.add(0.5, 0.0, 0.3);
                    }
                    llllllllllllllllIllIIlllIlllIlIl.mc.player.setPosition(llllllllllllllllIllIIlllIlllllIl.x, llllllllllllllllIllIIlllIlllllIl.y, llllllllllllllllIllIIlllIlllllIl.z);
                    llllllllllllllllIllIIlllIlllIlIl.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(llllllllllllllllIllIIlllIlllllIl.x, llllllllllllllllIllIIlllIlllllIl.y, llllllllllllllllIllIIlllIlllllIl.z, llllllllllllllllIllIIlllIlllIlIl.mc.player.isOnGround()));
                }
                llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, true);
                return;
            }
            if (llllllllllllllllIllIIlllIllllIlI instanceof BannerItem || llllllllllllllllIllIIlllIllllIlI == Items.LEVER || llllllllllllllllIllIIlllIllllIlI == Items.TORCH || llllllllllllllllIllIIlllIllllIlI == Items.REDSTONE_TORCH || llllllllllllllllIllIIlllIllllIlI instanceof SignItem || llllllllllllllllIllIIlllIllllIlI == Items.TRIPWIRE_HOOK || llllllllllllllllIllIIlllIllllIIl instanceof StoneButtonBlock || llllllllllllllllIllIIlllIllllIIl instanceof WoodenButtonBlock) {
                llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, true);
                if (llllllllllllllllIllIIlllIllllIlI instanceof SignItem) {
                    llllllllllllllllIllIIlllIlllIlIl.closeScreen = true;
                }
                return;
            }
            if (llllllllllllllllIllIIlllIllllIlI != Items.SCAFFOLDING || llllllllllllllllIllIIlllIllllIll.getCount() < 2) continue;
            llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, false);
            llllllllllllllllIllIIlllIlllIlIl.place(llllllllllllllllIllIIlllIllllIII, true);
            return;
        }
    }

    @Override
    public void onDeactivate() {
        llllllllllllllllIllIIllllIIIIlll.closeScreen = false;
    }

    public AntiBed() {
        super(Categories.Combat, "anti-bed", "Prevents people from placing beds where you are standing.");
        AntiBed llllllllllllllllIllIIllllIIIlIIl;
        llllllllllllllllIllIIllllIIIlIIl.sgGeneral = llllllllllllllllIllIIllllIIIlIIl.settings.getDefaultGroup();
        llllllllllllllllIllIIllllIIIlIIl.autoToggle = llllllllllllllllIllIIllllIIIlIIl.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles AntiBed off when finished.").defaultValue(false).build());
        llllllllllllllllIllIIllllIIIlIIl.autoCenter = llllllllllllllllIllIIllllIIIlIIl.sgGeneral.add(new BoolSetting.Builder().name("auto-center").description("Teleports you to the center of the blocks.").defaultValue(true).build());
        llllllllllllllllIllIIllllIIIlIIl.onlyOnGround = llllllllllllllllIllIIllllIIIlIIl.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Only toggles Anti Bed when you are standing on a block.").defaultValue(true).build());
        llllllllllllllllIllIIllllIIIlIIl.rotate = llllllllllllllllIllIIllllIIIlIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards where the blocks are placed.").defaultValue(true).build());
        llllllllllllllllIllIIllllIIIlIIl.place = -1;
        llllllllllllllllIllIIllllIIIlIIl.closeScreen = false;
    }

    private void place(int llllllllllllllllIllIIlllIlIllllI, boolean llllllllllllllllIllIIlllIlIlllIl) {
        BlockPos llllllllllllllllIllIIlllIllIIIII;
        AntiBed llllllllllllllllIllIIlllIlIlllll;
        if (llllllllllllllllIllIIlllIlIlllIl) {
            BlockPos llllllllllllllllIllIIlllIllIIlII = llllllllllllllllIllIIlllIlIlllll.mc.player.getBlockPos().up();
        } else {
            llllllllllllllllIllIIlllIllIIIII = llllllllllllllllIllIIlllIlIlllll.mc.player.getBlockPos();
        }
        if (BlockUtils.place(llllllllllllllllIllIIlllIllIIIII, Hand.MAIN_HAND, llllllllllllllllIllIIlllIlIllllI, llllllllllllllllIllIIlllIlIlllll.rotate.get(), 100, true) && llllllllllllllllIllIIlllIlIlllll.autoToggle.get().booleanValue()) {
            llllllllllllllllIllIIlllIlIlllll.toggle();
        }
    }

    private BlockPos checkBlocks() {
        AntiBed llllllllllllllllIllIIlllIllIllII;
        BlockPos llllllllllllllllIllIIlllIllIlIll = null;
        if (!llllllllllllllllIllIIlllIllIllII.mc.world.getBlockState(llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(0, 1, 1)).isAir()) {
            llllllllllllllllIllIIlllIllIlIll = llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(0, 1, 1);
        } else if (!llllllllllllllllIllIIlllIllIllII.mc.world.getBlockState(llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(0, 1, -1)).isAir()) {
            llllllllllllllllIllIIlllIllIlIll = llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(0, 1, -1);
        } else if (!llllllllllllllllIllIIlllIllIllII.mc.world.getBlockState(llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(1, 1, 0)).isAir()) {
            llllllllllllllllIllIIlllIllIlIll = llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(1, 1, 0);
        } else if (!llllllllllllllllIllIIlllIllIllII.mc.world.getBlockState(llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(-1, 1, 0)).isAir()) {
            llllllllllllllllIllIIlllIllIlIll = llllllllllllllllIllIIlllIllIllII.mc.player.getBlockPos().add(-1, 1, 0);
        }
        return llllllllllllllllIllIIlllIllIlIll;
    }
}

