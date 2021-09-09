/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.hit.BlockHitResult
 */
package minegame159.meteorclient.systems.modules.combat;

import minegame159.meteorclient.mixininterface.IClientPlayerInteractionManager;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.hit.BlockHitResult;

public class AutoCity
extends Module {
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Boolean> support;
    private final /* synthetic */ Setting<Boolean> crystal;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> chatInfo;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ SettingGroup sgGeneral;

    @Override
    public void onActivate() {
        AutoCity llllllllllllllllllIIlllIIlIllIll;
        llllllllllllllllllIIlllIIlIllIll.target = CityUtils.getPlayerTarget(llllllllllllllllllIIlllIIlIllIll.range.get());
        BlockPos llllllllllllllllllIIlllIIlIlllII = CityUtils.getTargetBlock(llllllllllllllllllIIlllIIlIllIll.target);
        if (llllllllllllllllllIIlllIIlIllIll.target == null || llllllllllllllllllIIlllIIlIlllII == null) {
            if (llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(llllllllllllllllllIIlllIIlIllIll, "No target block found... disabling.", new Object[0]);
            }
        } else {
            if (llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(llllllllllllllllllIIlllIIlIllIll, String.valueOf(new StringBuilder().append("Attempting to city ").append(llllllllllllllllllIIlllIIlIllIll.target.getGameProfile().getName())), new Object[0]);
            }
            if (MathHelper.sqrt((double)llllllllllllllllllIIlllIIlIllIll.mc.player.squaredDistanceTo((double)llllllllllllllllllIIlllIIlIlllII.getX(), (double)llllllllllllllllllIIlllIIlIlllII.getY(), (double)llllllllllllllllllIIlllIIlIlllII.getZ())) > llllllllllllllllllIIlllIIlIllIll.mc.interactionManager.getReachDistance()) {
                if (llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(llllllllllllllllllIIlllIIlIllIll, "Target block out of reach... disabling.", new Object[0]);
                }
                llllllllllllllllllIIlllIIlIllIll.toggle();
                return;
            }
            int llllllllllllllllllIIlllIIlIllllI = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
            if (llllllllllllllllllIIlllIIlIllllI == -1) {
                llllllllllllllllllIIlllIIlIllllI = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
            }
            if (llllllllllllllllllIIlllIIlIllIll.mc.player.abilities.creativeMode) {
                llllllllllllllllllIIlllIIlIllllI = llllllllllllllllllIIlllIIlIllIll.mc.player.inventory.selectedSlot;
            }
            if (llllllllllllllllllIIlllIIlIllllI == -1) {
                if (llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(llllllllllllllllllIIlllIIlIllIll, "No pick found... disabling.", new Object[0]);
                }
                llllllllllllllllllIIlllIIlIllIll.toggle();
                return;
            }
            if (llllllllllllllllllIIlllIIlIllIll.support.get().booleanValue()) {
                int llllllllllllllllllIIlllIIllIIIll = InvUtils.findItemInHotbar(Items.OBSIDIAN);
                BlockPos llllllllllllllllllIIlllIIllIIIlI = llllllllllllllllllIIlllIIlIlllII.down(1);
                if (!BlockUtils.canPlace(llllllllllllllllllIIlllIIllIIIlI) && llllllllllllllllllIIlllIIlIllIll.mc.world.getBlockState(llllllllllllllllllIIlllIIllIIIlI).getBlock() != Blocks.OBSIDIAN && llllllllllllllllllIIlllIIlIllIll.mc.world.getBlockState(llllllllllllllllllIIlllIIllIIIlI).getBlock() != Blocks.BEDROCK && llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleWarning(llllllllllllllllllIIlllIIlIllIll, "Couldn't place support block, mining anyway.", new Object[0]);
                } else if (llllllllllllllllllIIlllIIllIIIll == -1) {
                    if (llllllllllllllllllIIlllIIlIllIll.chatInfo.get().booleanValue()) {
                        ChatUtils.moduleWarning(llllllllllllllllllIIlllIIlIllIll, "No obsidian found for support, mining anyway.", new Object[0]);
                    }
                } else {
                    BlockUtils.place(llllllllllllllllllIIlllIIllIIIlI, Hand.MAIN_HAND, llllllllllllllllllIIlllIIllIIIll, llllllllllllllllllIIlllIIlIllIll.rotate.get(), 0, true);
                }
            }
            llllllllllllllllllIIlllIIlIllIll.mc.player.inventory.selectedSlot = llllllllllllllllllIIlllIIlIllllI;
            if (llllllllllllllllllIIlllIIlIllIll.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(llllllllllllllllllIIlllIIlIlllII), Rotations.getPitch(llllllllllllllllllIIlllIIlIlllII), () -> {
                    AutoCity llllllllllllllllllIIlllIIlIIlIlI;
                    llllllllllllllllllIIlllIIlIIlIlI.mine(llllllllllllllllllIIlllIIlIlllII);
                });
            } else {
                llllllllllllllllllIIlllIIlIllIll.mine(llllllllllllllllllIIlllIIlIlllII);
            }
            if (llllllllllllllllllIIlllIIlIllIll.crystal.get().booleanValue()) {
                if (!BlockUtils.canPlace(llllllllllllllllllIIlllIIlIlllII, true)) {
                    return;
                }
                Hand llllllllllllllllllIIlllIIlIlllll = InvUtils.getHand(Items.END_CRYSTAL);
                if (llllllllllllllllllIIlllIIlIlllll == Hand.MAIN_HAND) {
                    int llllllllllllllllllIIlllIIllIIIIl = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int llllllllllllllllllIIlllIIllIIIII = llllllllllllllllllIIlllIIlIllIll.mc.player.inventory.selectedSlot;
                    llllllllllllllllllIIlllIIlIllIll.mc.player.inventory.selectedSlot = llllllllllllllllllIIlllIIllIIIIl;
                    ((IClientPlayerInteractionManager)llllllllllllllllllIIlllIIlIllIll.mc.interactionManager).syncSelectedSlot2();
                    llllllllllllllllllIIlllIIlIllIll.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(llllllllllllllllllIIlllIIlIlllll, new BlockHitResult(llllllllllllllllllIIlllIIlIllIll.mc.player.getPos(), Direction.UP, llllllllllllllllllIIlllIIlIlllII, false)));
                    llllllllllllllllllIIlllIIlIllIll.mc.player.inventory.selectedSlot = llllllllllllllllllIIlllIIllIIIII;
                } else if (llllllllllllllllllIIlllIIlIlllll == Hand.OFF_HAND) {
                    llllllllllllllllllIIlllIIlIllIll.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(llllllllllllllllllIIlllIIlIlllll, new BlockHitResult(llllllllllllllllllIIlllIIlIllIll.mc.player.getPos(), Direction.UP, llllllllllllllllllIIlllIIlIlllII, false)));
                }
            }
        }
        llllllllllllllllllIIlllIIlIllIll.toggle();
    }

    private void mine(BlockPos llllllllllllllllllIIlllIIlIlIIII) {
        AutoCity llllllllllllllllllIIlllIIlIlIIll;
        llllllllllllllllllIIlllIIlIlIIll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, llllllllllllllllllIIlllIIlIlIIII, Direction.UP));
        llllllllllllllllllIIlllIIlIlIIll.mc.player.swingHand(Hand.MAIN_HAND);
        llllllllllllllllllIIlllIIlIlIIll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, llllllllllllllllllIIlllIIlIlIIII, Direction.UP));
    }

    public AutoCity() {
        super(Categories.Combat, "auto-city", "Automatically cities a target by mining the nearest obsidian next to them.");
        AutoCity llllllllllllllllllIIlllIIllIlIlI;
        llllllllllllllllllIIlllIIllIlIlI.sgGeneral = llllllllllllllllllIIlllIIllIlIlI.settings.getDefaultGroup();
        llllllllllllllllllIIlllIIllIlIlI.range = llllllllllllllllllIIlllIIllIlIlI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range a city-able block will be found.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        llllllllllllllllllIIlllIIllIlIlI.support = llllllllllllllllllIIlllIIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("support").description("If there is no block below a city block it will place one before mining.").defaultValue(true).build());
        llllllllllllllllllIIlllIIllIlIlI.chatInfo = llllllllllllllllllIIlllIIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Sends a client-side message if you city a player.").defaultValue(true).build());
        llllllllllllllllllIIlllIIllIlIlI.crystal = llllllllllllllllllIIlllIIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places a crystal above the city block.").defaultValue(false).build());
        llllllllllllllllllIIlllIIllIlIlI.rotate = llllllllllllllllllIIlllIIllIlIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates you towards the city block.").defaultValue(true).build());
    }

    @Override
    public String getInfoString() {
        AutoCity llllllllllllllllllIIlllIIlIIlllI;
        if (llllllllllllllllllIIlllIIlIIlllI.target != null) {
            return llllllllllllllllllIIlllIIlIIlllI.target.getEntityName();
        }
        return null;
    }
}

