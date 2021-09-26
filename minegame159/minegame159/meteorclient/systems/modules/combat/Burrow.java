/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Timer;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.hit.BlockHitResult;

public class Burrow
extends Module {
    private final Mutable blockPos;
    private final SettingGroup sgGeneral;
    private boolean shouldBurrow;
    private final Setting<Boolean> rotate;
    private int slot;
    private final Setting<Double> triggerHeight;
    private final Setting<Block> block;
    private int prevSlot;
    private final Setting<Double> timer;
    private final Setting<Boolean> onlyInHole;
    private final Setting<Boolean> automatic;
    private final Setting<Boolean> center;
    private final Setting<Boolean> instant;
    private final Setting<Double> rubberbandHeight;

    @Override
    public void onActivate() {
        if (!this.mc.world.getBlockState(this.mc.player.getBlockPos()).getMaterial().isReplaceable()) {
            ChatUtils.moduleError(this, "Already burrowed, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!PlayerUtils.isInHole() && this.onlyInHole.get().booleanValue()) {
            ChatUtils.moduleError(this, "Not in a hole, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!this.checkHead()) {
            ChatUtils.moduleError(this, "Not enough headroom to burrow, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!this.checkInventory()) {
            ChatUtils.moduleError(this, "No burrow block found, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        this.blockPos.set((Vec3i)this.mc.player.getBlockPos());
        Modules.get().get(Timer.class).setOverride(this.timer.get());
        this.shouldBurrow = false;
        if (this.automatic.get().booleanValue()) {
            if (this.instant.get().booleanValue()) {
                this.shouldBurrow = true;
            } else {
                this.mc.player.jump();
            }
        } else {
            ChatUtils.moduleInfo(this, "Waiting for manual jump.", new Object[0]);
        }
    }

    public Burrow() {
        super(Categories.Combat, "burrow", "Place block inside your hole.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.block = this.sgGeneral.add(new EnumSetting.Builder().name("use-block").description("The block to use.").defaultValue(Block.EChest).build());
        this.instant = this.sgGeneral.add(new BoolSetting.Builder().name("jump-with-packet").description("Packet jump.").defaultValue(true).build());
        this.automatic = this.sgGeneral.add(new BoolSetting.Builder().name("automatic").description("Automatically burrows on activate rather than waiting for jump.").defaultValue(true).build());
        this.triggerHeight = this.sgGeneral.add(new DoubleSetting.Builder().name("trigger-height").description("How high you have to jump before a rubberband is triggered.").defaultValue(1.12).min(0.01).sliderMax(1.4).build());
        this.rubberbandHeight = this.sgGeneral.add(new DoubleSetting.Builder().name("rubberband-height").description("How far to attempt to cause rubberband.").defaultValue(12.0).sliderMin(-30.0).sliderMax(30.0).build());
        this.timer = this.sgGeneral.add(new DoubleSetting.Builder().name("timer").description("Timer override.").defaultValue(1.0).min(0.01).sliderMax(10.0).build());
        this.onlyInHole = this.sgGeneral.add(new BoolSetting.Builder().name("only-in-holes").description("Burrowing only in holes.").defaultValue(false).build());
        this.center = this.sgGeneral.add(new BoolSetting.Builder().name("CenterTP").description("Teleport character to the center of the block.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Send packets for rotate.").defaultValue(true).build());
        this.blockPos = new Mutable();
    }

    private boolean checkInventory() {
        this.prevSlot = this.mc.player.inventory.selectedSlot;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$Burrow$Block[this.block.get().ordinal()]) {
            case 1: {
                this.slot = InvUtils.findItemInHotbar(Items.OBSIDIAN);
                break;
            }
            case 2: {
                this.slot = InvUtils.findItemInHotbar(Items.ENDER_CHEST);
            }
        }
        return this.slot != -1;
    }

    @EventHandler
    private void onKey(KeyEvent keyEvent) {
        if (this.instant.get().booleanValue() && !this.shouldBurrow) {
            if (keyEvent.action == KeyAction.Press && this.mc.options.keyJump.matchesKey(keyEvent.key, 0)) {
                this.shouldBurrow = true;
            }
            this.blockPos.set((Vec3i)this.mc.player.getBlockPos());
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1.0);
    }

    private boolean checkHead() {
        BlockState BlockState2 = this.mc.world.getBlockState((BlockPos)this.blockPos.set(this.mc.player.getX() + 0.3, this.mc.player.getY() + 2.3, this.mc.player.getZ() + 0.3));
        BlockState BlockState3 = this.mc.world.getBlockState((BlockPos)this.blockPos.set(this.mc.player.getX() + 0.3, this.mc.player.getY() + 2.3, this.mc.player.getZ() - 0.3));
        BlockState BlockState4 = this.mc.world.getBlockState((BlockPos)this.blockPos.set(this.mc.player.getX() - 0.3, this.mc.player.getY() + 2.3, this.mc.player.getZ() - 0.3));
        BlockState BlockState5 = this.mc.world.getBlockState((BlockPos)this.blockPos.set(this.mc.player.getX() - 0.3, this.mc.player.getY() + 2.3, this.mc.player.getZ() + 0.3));
        boolean bl = BlockState2.getMaterial().isReplaceable();
        boolean bl2 = BlockState3.getMaterial().isReplaceable();
        boolean bl3 = BlockState4.getMaterial().isReplaceable();
        boolean bl4 = BlockState5.getMaterial().isReplaceable();
        return bl & bl2 & bl3 & bl4;
    }

    private void placeBlock() {
        this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(Utils.vec3d((BlockPos)this.blockPos), Direction.UP, (BlockPos)this.blockPos, false));
        this.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (!this.instant.get().booleanValue()) {
            boolean bl = this.shouldBurrow = this.mc.player.getY() > (double)this.blockPos.getY() + this.triggerHeight.get();
        }
        if (!this.shouldBurrow && this.instant.get().booleanValue()) {
            this.blockPos.set((Vec3i)this.mc.player.getBlockPos());
        }
        if (this.shouldBurrow) {
            if (!this.checkInventory()) {
                return;
            }
            if (this.center.get().booleanValue()) {
                PlayerUtils.centerPlayer();
            }
            if (this.instant.get().booleanValue()) {
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY() + 0.4, this.mc.player.getZ(), true));
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY() + 0.75, this.mc.player.getZ(), true));
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY() + 1.0, this.mc.player.getZ(), true));
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY() + 1.15, this.mc.player.getZ(), true));
            }
            this.mc.player.inventory.selectedSlot = this.slot;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw((BlockPos)this.blockPos), Rotations.getPitch((BlockPos)this.blockPos), this::placeBlock);
            } else {
                this.placeBlock();
            }
            this.mc.player.inventory.selectedSlot = this.prevSlot;
            if (this.instant.get().booleanValue()) {
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY() + this.rubberbandHeight.get(), this.mc.player.getZ(), false));
            } else {
                this.mc.player.setVelocity(this.mc.player.getVelocity().add(0.0, this.rubberbandHeight.get().doubleValue(), 0.0));
            }
            this.toggle();
        }
    }

    public static final class Block
    extends Enum<Block> {
        private static final Block[] $VALUES;
        public static final /* enum */ Block Obsidian;
        public static final /* enum */ Block EChest;

        public static Block[] values() {
            return (Block[])$VALUES.clone();
        }

        static {
            EChest = new Block();
            Obsidian = new Block();
            $VALUES = Block.$values();
        }

        private static Block[] $values() {
            return new Block[]{EChest, Obsidian};
        }

        public static Block valueOf(String string) {
            return Enum.valueOf(Block.class, string);
        }
    }
}

