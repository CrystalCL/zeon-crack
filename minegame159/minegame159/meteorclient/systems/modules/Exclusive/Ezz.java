/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;

public class Ezz {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean BlockPlace(int n, int n2, int n3, int n4, boolean bl) {
        return Ezz.BlockPlace(new BlockPos(n, n2, n3), n4, bl);
    }

    public static void attackEntity(Entity Entity2) {
        Ezz.mc.interactionManager.attackEntity((PlayerEntity)Ezz.mc.player, Entity2);
    }

    public static void clickSlot(int n, int n2, SlotActionType SlotActionType2) {
        Ezz.mc.interactionManager.clickSlot(Ezz.mc.player.currentScreenHandler.syncId, n, n2, SlotActionType2, (PlayerEntity)Ezz.mc.player);
    }

    public static void interact(BlockPos BlockPos2, int n, Direction Direction2) {
        int n2 = Ezz.mc.player.inventory.selectedSlot;
        Ezz.swap(n);
        Ezz.mc.interactionManager.interactBlock(Ezz.mc.player, Ezz.mc.world, Hand.MAIN_HAND, new BlockHitResult(Ezz.mc.player.getPos(), Direction2, BlockPos2, true));
        Ezz.swap(n2);
    }

    public static boolean equalsBlockPos(BlockPos BlockPos2, BlockPos BlockPos3) {
        if (!(BlockPos2 instanceof Vec3i) || !(BlockPos2 instanceof Vec3i)) {
            return false;
        }
        if (BlockPos2 == null || BlockPos3 == null) {
            return false;
        }
        if (BlockPos2.getX() != BlockPos3.getX()) {
            return false;
        }
        if (BlockPos2.getY() != BlockPos3.getY()) {
            return false;
        }
        return BlockPos2.getZ() == BlockPos3.getZ();
    }

    public static boolean isFriend(PlayerEntity PlayerEntity2) {
        if (PlayerEntity2 == null) {
            return false;
        }
        return Ezz.isFriend(PlayerEntity2.getName().asString());
    }

    public static double DistanceTo(int n, double d, double d2) {
        double d3 = n;
        double d4 = d;
        double d5 = d2;
        d3 = d3 >= 0.0 ? (d3 += 0.5) : (d3 -= 0.5);
        d4 = d4 >= 0.0 ? (d4 += 0.5) : (d4 -= 0.5);
        d5 = d5 >= 0.0 ? (d5 += 0.5) : (d5 -= 0.5);
        double d6 = Ezz.mc.player.getX() - d3;
        double d7 = Ezz.mc.player.getY() - d4;
        double d8 = Ezz.mc.player.getZ() - d5;
        return Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
    }

    public static boolean BlockPlace(BlockPos BlockPos2, int n, boolean bl) {
        if (n == -1) {
            return false;
        }
        if (!BlockUtils.canPlace(BlockPos2, true)) {
            return false;
        }
        int n2 = Ezz.mc.player.inventory.selectedSlot;
        Ezz.swap(n);
        if (bl) {
            Vec3d Vec3d2 = new Vec3d(0.0, 0.0, 0.0);
            ((IVec3d)Vec3d2).set((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (double)BlockPos2.getZ() + 0.5);
            Rotations.rotate(Rotations.getYaw(Vec3d2), Rotations.getPitch(Vec3d2));
        }
        Ezz.mc.interactionManager.interactBlock(Ezz.mc.player, Ezz.mc.world, Hand.MAIN_HAND, new BlockHitResult(Ezz.mc.player.getPos(), Direction.DOWN, BlockPos2, true));
        Ezz.swap(n2);
        return true;
    }

    public static double distanceToBlockAnge(BlockPos BlockPos2) {
        double d = Ezz.mc.player.getX();
        double d2 = Ezz.mc.player.getY() + 1.0;
        double d3 = Ezz.mc.player.getZ();
        double d4 = BlockPos2.getX();
        double d5 = BlockPos2.getY();
        double d6 = BlockPos2.getZ();
        if (d5 == Ezz.floor(d2)) {
            d5 = d2;
        }
        if (d4 > 0.0 && d4 == Ezz.floor(d)) {
            d4 = d;
        }
        if (d4 < 0.0 && d4 + 1.0 == Ezz.floor(d)) {
            d4 = d;
        }
        if (d6 > 0.0 && d6 == Ezz.floor(d3)) {
            d6 = d3;
        }
        if (d6 < 0.0 && d6 + 1.0 == Ezz.floor(d3)) {
            d6 = d3;
        }
        if (d4 < d) {
            d4 += 1.0;
        }
        if (d5 < d2) {
            d5 += 1.0;
        }
        if (d6 < d3) {
            d6 += 1.0;
        }
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        return Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    public static BlockPos SetRelative(int n, int n2, int n3) {
        return new BlockPos(Ezz.mc.player.getX() + (double)n, Ezz.mc.player.getY() + (double)n2, Ezz.mc.player.getZ() + (double)n3);
    }

    public static boolean isFriend(String string) {
        return Friends.get().getAll().contains(new Friend(string));
    }

    public static void swap(int n) {
        if (n != Ezz.mc.player.inventory.selectedSlot && n >= 0 && n < 9) {
            Ezz.mc.player.inventory.selectedSlot = n;
        }
    }

    public static double DistanceTo(BlockPos BlockPos2) {
        return Ezz.DistanceTo(BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ());
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    public static double floor(double d) {
        return (long)d;
    }

    public static int invIndexToSlotId(int n) {
        if (n < 9 && n != -1) {
            return 44 - (8 - n);
        }
        return n;
    }
}

