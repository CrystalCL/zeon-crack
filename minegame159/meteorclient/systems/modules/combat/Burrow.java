/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.network.Packet
 *  net.minecraft.block.BlockState
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
 *  net.minecraft.util.hit.BlockHitResult
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
    private final /* synthetic */ Setting<Double> rubberbandHeight;
    private final /* synthetic */ Setting<Double> timer;
    private final /* synthetic */ Setting<Double> triggerHeight;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ int slot;
    private /* synthetic */ int prevSlot;
    private final /* synthetic */ Setting<Boolean> center;
    private /* synthetic */ boolean shouldBurrow;
    private final /* synthetic */ Setting<Block> block;
    private final /* synthetic */ Setting<Boolean> automatic;
    private final /* synthetic */ Setting<Boolean> instant;
    private final /* synthetic */ Setting<Boolean> onlyInHole;

    public Burrow() {
        super(Categories.Combat, "burrow", "Place block inside your hole.");
        Burrow lIlIIIllIlllII;
        lIlIIIllIlllII.sgGeneral = lIlIIIllIlllII.settings.getDefaultGroup();
        lIlIIIllIlllII.block = lIlIIIllIlllII.sgGeneral.add(new EnumSetting.Builder().name("use-block").description("The block to use.").defaultValue(Block.EChest).build());
        lIlIIIllIlllII.instant = lIlIIIllIlllII.sgGeneral.add(new BoolSetting.Builder().name("jump-with-packet").description("Packet jump.").defaultValue(true).build());
        lIlIIIllIlllII.automatic = lIlIIIllIlllII.sgGeneral.add(new BoolSetting.Builder().name("automatic").description("Automatically burrows on activate rather than waiting for jump.").defaultValue(true).build());
        lIlIIIllIlllII.triggerHeight = lIlIIIllIlllII.sgGeneral.add(new DoubleSetting.Builder().name("trigger-height").description("How high you have to jump before a rubberband is triggered.").defaultValue(1.12).min(0.01).sliderMax(1.4).build());
        lIlIIIllIlllII.rubberbandHeight = lIlIIIllIlllII.sgGeneral.add(new DoubleSetting.Builder().name("rubberband-height").description("How far to attempt to cause rubberband.").defaultValue(12.0).sliderMin(-30.0).sliderMax(30.0).build());
        lIlIIIllIlllII.timer = lIlIIIllIlllII.sgGeneral.add(new DoubleSetting.Builder().name("timer").description("Timer override.").defaultValue(1.0).min(0.01).sliderMax(10.0).build());
        lIlIIIllIlllII.onlyInHole = lIlIIIllIlllII.sgGeneral.add(new BoolSetting.Builder().name("only-in-holes").description("Burrowing only in holes.").defaultValue(false).build());
        lIlIIIllIlllII.center = lIlIIIllIlllII.sgGeneral.add(new BoolSetting.Builder().name("CenterTP").description("Teleport character to the center of the block.").defaultValue(true).build());
        lIlIIIllIlllII.rotate = lIlIIIllIlllII.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Send packets for rotate.").defaultValue(true).build());
        lIlIIIllIlllII.blockPos = new Mutable();
    }

    private boolean checkHead() {
        Burrow lIlIIIlIIIlIlI;
        BlockState lIlIIIlIIlIlll = lIlIIIlIIIlIlI.mc.world.getBlockState((BlockPos)lIlIIIlIIIlIlI.blockPos.set(lIlIIIlIIIlIlI.mc.player.getX() + 0.3, lIlIIIlIIIlIlI.mc.player.getY() + 2.3, lIlIIIlIIIlIlI.mc.player.getZ() + 0.3));
        BlockState lIlIIIlIIlIlIl = lIlIIIlIIIlIlI.mc.world.getBlockState((BlockPos)lIlIIIlIIIlIlI.blockPos.set(lIlIIIlIIIlIlI.mc.player.getX() + 0.3, lIlIIIlIIIlIlI.mc.player.getY() + 2.3, lIlIIIlIIIlIlI.mc.player.getZ() - 0.3));
        BlockState lIlIIIlIIlIIll = lIlIIIlIIIlIlI.mc.world.getBlockState((BlockPos)lIlIIIlIIIlIlI.blockPos.set(lIlIIIlIIIlIlI.mc.player.getX() - 0.3, lIlIIIlIIIlIlI.mc.player.getY() + 2.3, lIlIIIlIIIlIlI.mc.player.getZ() - 0.3));
        BlockState lIlIIIlIIlIIIl = lIlIIIlIIIlIlI.mc.world.getBlockState((BlockPos)lIlIIIlIIIlIlI.blockPos.set(lIlIIIlIIIlIlI.mc.player.getX() - 0.3, lIlIIIlIIIlIlI.mc.player.getY() + 2.3, lIlIIIlIIIlIlI.mc.player.getZ() + 0.3));
        boolean lIlIIIlIIlIIII = lIlIIIlIIlIlll.getMaterial().isReplaceable();
        boolean lIlIIIlIIIllll = lIlIIIlIIlIlIl.getMaterial().isReplaceable();
        boolean lIlIIIlIIIlllI = lIlIIIlIIlIIll.getMaterial().isReplaceable();
        boolean lIlIIIlIIIllII = lIlIIIlIIlIIIl.getMaterial().isReplaceable();
        return lIlIIIlIIlIIII & lIlIIIlIIIllll & lIlIIIlIIIlllI & lIlIIIlIIIllII;
    }

    @Override
    public void onActivate() {
        Burrow lIlIIIllIlIllI;
        if (!lIlIIIllIlIllI.mc.world.getBlockState(lIlIIIllIlIllI.mc.player.getBlockPos()).getMaterial().isReplaceable()) {
            ChatUtils.moduleError(lIlIIIllIlIllI, "Already burrowed, disabling.", new Object[0]);
            lIlIIIllIlIllI.toggle();
            return;
        }
        if (!PlayerUtils.isInHole() && lIlIIIllIlIllI.onlyInHole.get().booleanValue()) {
            ChatUtils.moduleError(lIlIIIllIlIllI, "Not in a hole, disabling.", new Object[0]);
            lIlIIIllIlIllI.toggle();
            return;
        }
        if (!lIlIIIllIlIllI.checkHead()) {
            ChatUtils.moduleError(lIlIIIllIlIllI, "Not enough headroom to burrow, disabling.", new Object[0]);
            lIlIIIllIlIllI.toggle();
            return;
        }
        if (!lIlIIIllIlIllI.checkInventory()) {
            ChatUtils.moduleError(lIlIIIllIlIllI, "No burrow block found, disabling.", new Object[0]);
            lIlIIIllIlIllI.toggle();
            return;
        }
        lIlIIIllIlIllI.blockPos.set((Vec3i)lIlIIIllIlIllI.mc.player.getBlockPos());
        Modules.get().get(Timer.class).setOverride(lIlIIIllIlIllI.timer.get());
        lIlIIIllIlIllI.shouldBurrow = false;
        if (lIlIIIllIlIllI.automatic.get().booleanValue()) {
            if (lIlIIIllIlIllI.instant.get().booleanValue()) {
                lIlIIIllIlIllI.shouldBurrow = true;
            } else {
                lIlIIIllIlIllI.mc.player.jump();
            }
        } else {
            ChatUtils.moduleInfo(lIlIIIllIlIllI, "Waiting for manual jump.", new Object[0]);
        }
    }

    private boolean checkInventory() {
        Burrow lIlIIIIlllIlll;
        lIlIIIIlllIlll.prevSlot = lIlIIIIlllIlll.mc.player.inventory.selectedSlot;
        switch (lIlIIIIlllIlll.block.get()) {
            case Obsidian: {
                lIlIIIIlllIlll.slot = InvUtils.findItemInHotbar(Items.OBSIDIAN);
                break;
            }
            case EChest: {
                lIlIIIIlllIlll.slot = InvUtils.findItemInHotbar(Items.ENDER_CHEST);
            }
        }
        return lIlIIIIlllIlll.slot != -1;
    }

    @EventHandler
    private void onKey(KeyEvent lIlIIIlIlIllll) {
        Burrow lIlIIIlIllIIII;
        if (lIlIIIlIllIIII.instant.get().booleanValue() && !lIlIIIlIllIIII.shouldBurrow) {
            if (lIlIIIlIlIllll.action == KeyAction.Press && lIlIIIlIllIIII.mc.options.keyJump.matchesKey(lIlIIIlIlIllll.key, 0)) {
                lIlIIIlIllIIII.shouldBurrow = true;
            }
            lIlIIIlIllIIII.blockPos.set((Vec3i)lIlIIIlIllIIII.mc.player.getBlockPos());
        }
    }

    private void placeBlock() {
        Burrow lIlIIIlIlllIIl;
        lIlIIIlIlllIIl.mc.interactionManager.interactBlock(lIlIIIlIlllIIl.mc.player, lIlIIIlIlllIIl.mc.world, Hand.MAIN_HAND, new BlockHitResult(Utils.vec3d((BlockPos)lIlIIIlIlllIIl.blockPos), Direction.UP, (BlockPos)lIlIIIlIlllIIl.blockPos, false));
        lIlIIIlIlllIIl.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIlIIIllIIIIlI) {
        Burrow lIlIIIllIIIlII;
        if (!lIlIIIllIIIlII.instant.get().booleanValue()) {
            boolean bl = lIlIIIllIIIlII.shouldBurrow = lIlIIIllIIIlII.mc.player.getY() > (double)lIlIIIllIIIlII.blockPos.getY() + lIlIIIllIIIlII.triggerHeight.get();
        }
        if (!lIlIIIllIIIlII.shouldBurrow && lIlIIIllIIIlII.instant.get().booleanValue()) {
            lIlIIIllIIIlII.blockPos.set((Vec3i)lIlIIIllIIIlII.mc.player.getBlockPos());
        }
        if (lIlIIIllIIIlII.shouldBurrow) {
            if (!lIlIIIllIIIlII.checkInventory()) {
                return;
            }
            if (lIlIIIllIIIlII.center.get().booleanValue()) {
                PlayerUtils.centerPlayer();
            }
            if (lIlIIIllIIIlII.instant.get().booleanValue()) {
                lIlIIIllIIIlII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIlIIIllIIIlII.mc.player.getX(), lIlIIIllIIIlII.mc.player.getY() + 0.4, lIlIIIllIIIlII.mc.player.getZ(), true));
                lIlIIIllIIIlII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIlIIIllIIIlII.mc.player.getX(), lIlIIIllIIIlII.mc.player.getY() + 0.75, lIlIIIllIIIlII.mc.player.getZ(), true));
                lIlIIIllIIIlII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIlIIIllIIIlII.mc.player.getX(), lIlIIIllIIIlII.mc.player.getY() + 1.0, lIlIIIllIIIlII.mc.player.getZ(), true));
                lIlIIIllIIIlII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIlIIIllIIIlII.mc.player.getX(), lIlIIIllIIIlII.mc.player.getY() + 1.15, lIlIIIllIIIlII.mc.player.getZ(), true));
            }
            lIlIIIllIIIlII.mc.player.inventory.selectedSlot = lIlIIIllIIIlII.slot;
            if (lIlIIIllIIIlII.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw((BlockPos)lIlIIIllIIIlII.blockPos), Rotations.getPitch((BlockPos)lIlIIIllIIIlII.blockPos), lIlIIIllIIIlII::placeBlock);
            } else {
                lIlIIIllIIIlII.placeBlock();
            }
            lIlIIIllIIIlII.mc.player.inventory.selectedSlot = lIlIIIllIIIlII.prevSlot;
            if (lIlIIIllIIIlII.instant.get().booleanValue()) {
                lIlIIIllIIIlII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIlIIIllIIIlII.mc.player.getX(), lIlIIIllIIIlII.mc.player.getY() + lIlIIIllIIIlII.rubberbandHeight.get(), lIlIIIllIIIlII.mc.player.getZ(), false));
            } else {
                lIlIIIllIIIlII.mc.player.setVelocity(lIlIIIllIIIlII.mc.player.getVelocity().add(0.0, lIlIIIllIIIlII.rubberbandHeight.get().doubleValue(), 0.0));
            }
            lIlIIIllIIIlII.toggle();
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1.0);
    }

    public static final class Block
    extends Enum<Block> {
        public static final /* synthetic */ /* enum */ Block EChest;
        public static final /* synthetic */ /* enum */ Block Obsidian;
        private static final /* synthetic */ Block[] $VALUES;

        public static Block[] values() {
            return (Block[])$VALUES.clone();
        }

        static {
            EChest = new Block();
            Obsidian = new Block();
            $VALUES = Block.$values();
        }

        private Block() {
            Block lllllllllllllllllllIIllllllIlIII;
        }

        public static Block valueOf(String lllllllllllllllllllIIllllllIllIl) {
            return Enum.valueOf(Block.class, lllllllllllllllllllIIllllllIllIl);
        }

        private static /* synthetic */ Block[] $values() {
            return new Block[]{EChest, Obsidian};
        }
    }
}

