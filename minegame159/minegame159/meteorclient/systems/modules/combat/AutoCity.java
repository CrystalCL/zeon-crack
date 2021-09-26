/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> support;
    private final Setting<Double> range;
    private final Setting<Boolean> crystal;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;
    private PlayerEntity target;
    private final Setting<Boolean> chatInfo;

    private void lambda$onActivate$0(BlockPos BlockPos2) {
        this.mine(BlockPos2);
    }

    private void mine(BlockPos BlockPos2) {
        this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, BlockPos2, Direction.UP));
        this.mc.player.swingHand(Hand.MAIN_HAND);
        this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, BlockPos2, Direction.UP));
    }

    @Override
    public void onActivate() {
        this.target = CityUtils.getPlayerTarget(this.range.get());
        BlockPos BlockPos2 = CityUtils.getTargetBlock(this.target);
        if (this.target == null || BlockPos2 == null) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(this, "No target block found... disabling.", new Object[0]);
            }
        } else {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append("Attempting to city ").append(this.target.getGameProfile().getName())), new Object[0]);
            }
            if (MathHelper.sqrt((double)this.mc.player.squaredDistanceTo((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ())) > this.mc.interactionManager.getReachDistance()) {
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(this, "Target block out of reach... disabling.", new Object[0]);
                }
                this.toggle();
                return;
            }
            int n = InvUtils.findItemInHotbar(Items.NETHERITE_PICKAXE);
            if (n == -1) {
                n = InvUtils.findItemInHotbar(Items.DIAMOND_PICKAXE);
            }
            if (this.mc.player.abilities.creativeMode) {
                n = this.mc.player.inventory.selectedSlot;
            }
            if (n == -1) {
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(this, "No pick found... disabling.", new Object[0]);
                }
                this.toggle();
                return;
            }
            if (this.support.get().booleanValue()) {
                int n2 = InvUtils.findItemInHotbar(Items.OBSIDIAN);
                BlockPos BlockPos3 = BlockPos2.down(1);
                if (!BlockUtils.canPlace(BlockPos3) && this.mc.world.getBlockState(BlockPos3).getBlock() != Blocks.OBSIDIAN && this.mc.world.getBlockState(BlockPos3).getBlock() != Blocks.BEDROCK && this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleWarning(this, "Couldn't place support block, mining anyway.", new Object[0]);
                } else if (n2 == -1) {
                    if (this.chatInfo.get().booleanValue()) {
                        ChatUtils.moduleWarning(this, "No obsidian found for support, mining anyway.", new Object[0]);
                    }
                } else {
                    BlockUtils.place(BlockPos3, Hand.MAIN_HAND, n2, this.rotate.get(), 0, true);
                }
            }
            this.mc.player.inventory.selectedSlot = n;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(BlockPos2), Rotations.getPitch(BlockPos2), () -> this.lambda$onActivate$0(BlockPos2));
            } else {
                this.mine(BlockPos2);
            }
            if (this.crystal.get().booleanValue()) {
                if (!BlockUtils.canPlace(BlockPos2, true)) {
                    return;
                }
                Hand Hand2 = InvUtils.getHand(Items.END_CRYSTAL);
                if (Hand2 == Hand.MAIN_HAND) {
                    int n3 = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int n4 = this.mc.player.inventory.selectedSlot;
                    this.mc.player.inventory.selectedSlot = n3;
                    ((IClientPlayerInteractionManager)this.mc.interactionManager).syncSelectedSlot2();
                    this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand2, new BlockHitResult(this.mc.player.getPos(), Direction.UP, BlockPos2, false)));
                    this.mc.player.inventory.selectedSlot = n4;
                } else if (Hand2 == Hand.OFF_HAND) {
                    this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand2, new BlockHitResult(this.mc.player.getPos(), Direction.UP, BlockPos2, false)));
                }
            }
        }
        this.toggle();
    }

    public AutoCity() {
        super(Categories.Combat, "auto-city", "Automatically cities a target by mining the nearest obsidian next to them.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range a city-able block will be found.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        this.support = this.sgGeneral.add(new BoolSetting.Builder().name("support").description("If there is no block below a city block it will place one before mining.").defaultValue(true).build());
        this.chatInfo = this.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Sends a client-side message if you city a player.").defaultValue(true).build());
        this.crystal = this.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places a crystal above the city block.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates you towards the city block.").defaultValue(true).build());
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
    }
}

