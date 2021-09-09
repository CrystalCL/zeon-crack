/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.screen.slot.SlotActionType
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.hit.BlockHitResult
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
    private static final /* synthetic */ MinecraftClient mc;

    public static void interact(BlockPos llllllllllllllllIllIIIIIlIIIlIIl, int llllllllllllllllIllIIIIIlIIIllII, Direction llllllllllllllllIllIIIIIlIIIIlll) {
        int llllllllllllllllIllIIIIIlIIIlIlI = Ezz.mc.player.inventory.selectedSlot;
        Ezz.swap(llllllllllllllllIllIIIIIlIIIllII);
        Ezz.mc.interactionManager.interactBlock(Ezz.mc.player, Ezz.mc.world, Hand.MAIN_HAND, new BlockHitResult(Ezz.mc.player.getPos(), llllllllllllllllIllIIIIIlIIIIlll, llllllllllllllllIllIIIIIlIIIlIIl, true));
        Ezz.swap(llllllllllllllllIllIIIIIlIIIlIlI);
    }

    public static boolean BlockPlace(BlockPos llllllllllllllllIllIIIIIlIllIlII, int llllllllllllllllIllIIIIIlIllIlll, boolean llllllllllllllllIllIIIIIlIllIIlI) {
        if (llllllllllllllllIllIIIIIlIllIlll == -1) {
            return false;
        }
        if (!BlockUtils.canPlace(llllllllllllllllIllIIIIIlIllIlII, true)) {
            return false;
        }
        int llllllllllllllllIllIIIIIlIllIlIl = Ezz.mc.player.inventory.selectedSlot;
        Ezz.swap(llllllllllllllllIllIIIIIlIllIlll);
        if (llllllllllllllllIllIIIIIlIllIIlI) {
            Vec3d llllllllllllllllIllIIIIIlIlllIIl = new Vec3d(0.0, 0.0, 0.0);
            ((IVec3d)llllllllllllllllIllIIIIIlIlllIIl).set((double)llllllllllllllllIllIIIIIlIllIlII.getX() + 0.5, (double)llllllllllllllllIllIIIIIlIllIlII.getY() + 0.5, (double)llllllllllllllllIllIIIIIlIllIlII.getZ() + 0.5);
            Rotations.rotate(Rotations.getYaw(llllllllllllllllIllIIIIIlIlllIIl), Rotations.getPitch(llllllllllllllllIllIIIIIlIlllIIl));
        }
        Ezz.mc.interactionManager.interactBlock(Ezz.mc.player, Ezz.mc.world, Hand.MAIN_HAND, new BlockHitResult(Ezz.mc.player.getPos(), Direction.DOWN, llllllllllllllllIllIIIIIlIllIlII, true));
        Ezz.swap(llllllllllllllllIllIIIIIlIllIlIl);
        return true;
    }

    public static boolean BlockPlace(int llllllllllllllllIllIIIIIllIIIIll, int llllllllllllllllIllIIIIIllIIIIlI, int llllllllllllllllIllIIIIIllIIIIIl, int llllllllllllllllIllIIIIIllIIIlIl, boolean llllllllllllllllIllIIIIIllIIIlII) {
        return Ezz.BlockPlace(new BlockPos(llllllllllllllllIllIIIIIllIIIIll, llllllllllllllllIllIIIIIllIIIIlI, llllllllllllllllIllIIIIIllIIIIIl), llllllllllllllllIllIIIIIllIIIlIl, llllllllllllllllIllIIIIIllIIIlII);
    }

    public static void clickSlot(int llllllllllllllllIllIIIIIlllIlIII, int llllllllllllllllIllIIIIIlllIIlll, SlotActionType llllllllllllllllIllIIIIIlllIIllI) {
        Ezz.mc.interactionManager.clickSlot(Ezz.mc.player.currentScreenHandler.syncId, llllllllllllllllIllIIIIIlllIlIII, llllllllllllllllIllIIIIIlllIIlll, llllllllllllllllIllIIIIIlllIIllI, (PlayerEntity)Ezz.mc.player);
    }

    public static boolean isFriend(PlayerEntity llllllllllllllllIllIIIIIIlllllIl) {
        if (llllllllllllllllIllIIIIIIlllllIl == null) {
            return false;
        }
        return Ezz.isFriend(llllllllllllllllIllIIIIIIlllllIl.getName().asString());
    }

    public static double distanceToBlockAnge(BlockPos llllllllllllllllIllIIIIIIlllIIlI) {
        double llllllllllllllllIllIIIIIIlllIIIl = Ezz.mc.player.getX();
        double llllllllllllllllIllIIIIIIlllIIII = Ezz.mc.player.getY() + 1.0;
        double llllllllllllllllIllIIIIIIllIllll = Ezz.mc.player.getZ();
        double llllllllllllllllIllIIIIIIllIlllI = llllllllllllllllIllIIIIIIlllIIlI.getX();
        double llllllllllllllllIllIIIIIIllIllIl = llllllllllllllllIllIIIIIIlllIIlI.getY();
        double llllllllllllllllIllIIIIIIllIllII = llllllllllllllllIllIIIIIIlllIIlI.getZ();
        if (llllllllllllllllIllIIIIIIllIllIl == Ezz.floor(llllllllllllllllIllIIIIIIlllIIII)) {
            llllllllllllllllIllIIIIIIllIllIl = llllllllllllllllIllIIIIIIlllIIII;
        }
        if (llllllllllllllllIllIIIIIIllIlllI > 0.0 && llllllllllllllllIllIIIIIIllIlllI == Ezz.floor(llllllllllllllllIllIIIIIIlllIIIl)) {
            llllllllllllllllIllIIIIIIllIlllI = llllllllllllllllIllIIIIIIlllIIIl;
        }
        if (llllllllllllllllIllIIIIIIllIlllI < 0.0 && llllllllllllllllIllIIIIIIllIlllI + 1.0 == Ezz.floor(llllllllllllllllIllIIIIIIlllIIIl)) {
            llllllllllllllllIllIIIIIIllIlllI = llllllllllllllllIllIIIIIIlllIIIl;
        }
        if (llllllllllllllllIllIIIIIIllIllII > 0.0 && llllllllllllllllIllIIIIIIllIllII == Ezz.floor(llllllllllllllllIllIIIIIIllIllll)) {
            llllllllllllllllIllIIIIIIllIllII = llllllllllllllllIllIIIIIIllIllll;
        }
        if (llllllllllllllllIllIIIIIIllIllII < 0.0 && llllllllllllllllIllIIIIIIllIllII + 1.0 == Ezz.floor(llllllllllllllllIllIIIIIIllIllll)) {
            llllllllllllllllIllIIIIIIllIllII = llllllllllllllllIllIIIIIIllIllll;
        }
        if (llllllllllllllllIllIIIIIIllIlllI < llllllllllllllllIllIIIIIIlllIIIl) {
            llllllllllllllllIllIIIIIIllIlllI += 1.0;
        }
        if (llllllllllllllllIllIIIIIIllIllIl < llllllllllllllllIllIIIIIIlllIIII) {
            llllllllllllllllIllIIIIIIllIllIl += 1.0;
        }
        if (llllllllllllllllIllIIIIIIllIllII < llllllllllllllllIllIIIIIIllIllll) {
            llllllllllllllllIllIIIIIIllIllII += 1.0;
        }
        double llllllllllllllllIllIIIIIIllIlIll = llllllllllllllllIllIIIIIIllIlllI - llllllllllllllllIllIIIIIIlllIIIl;
        double llllllllllllllllIllIIIIIIllIlIlI = llllllllllllllllIllIIIIIIllIllIl - llllllllllllllllIllIIIIIIlllIIII;
        double llllllllllllllllIllIIIIIIllIlIIl = llllllllllllllllIllIIIIIIllIllII - llllllllllllllllIllIIIIIIllIllll;
        return Math.sqrt(llllllllllllllllIllIIIIIIllIlIll * llllllllllllllllIllIIIIIIllIlIll + llllllllllllllllIllIIIIIIllIlIlI * llllllllllllllllIllIIIIIIllIlIlI + llllllllllllllllIllIIIIIIllIlIIl * llllllllllllllllIllIIIIIIllIlIIl);
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    public Ezz() {
        Ezz llllllllllllllllIllIIIIIlllIllII;
    }

    static {
        mc = MinecraftClient.getInstance();
    }

    public static double floor(double llllllllllllllllIllIIIIIIlIlllII) {
        return (long)llllllllllllllllIllIIIIIIlIlllII;
    }

    public static void swap(int llllllllllllllllIllIIIIIllIlllIl) {
        if (llllllllllllllllIllIIIIIllIlllIl != Ezz.mc.player.inventory.selectedSlot && llllllllllllllllIllIIIIIllIlllIl >= 0 && llllllllllllllllIllIIIIIllIlllIl < 9) {
            Ezz.mc.player.inventory.selectedSlot = llllllllllllllllIllIIIIIllIlllIl;
        }
    }

    public static double DistanceTo(BlockPos llllllllllllllllIllIIIIIlIlIllIl) {
        return Ezz.DistanceTo(llllllllllllllllIllIIIIIlIlIllIl.getX(), llllllllllllllllIllIIIIIlIlIllIl.getY(), llllllllllllllllIllIIIIIlIlIllIl.getZ());
    }

    public static BlockPos SetRelative(int llllllllllllllllIllIIIIIllIlIIII, int llllllllllllllllIllIIIIIllIlIIlI, int llllllllllllllllIllIIIIIllIIlllI) {
        return new BlockPos(Ezz.mc.player.getX() + (double)llllllllllllllllIllIIIIIllIlIIII, Ezz.mc.player.getY() + (double)llllllllllllllllIllIIIIIllIlIIlI, Ezz.mc.player.getZ() + (double)llllllllllllllllIllIIIIIllIIlllI);
    }

    public static boolean isFriend(String llllllllllllllllIllIIIIIlIIIIIIl) {
        return Friends.get().getAll().contains(new Friend(llllllllllllllllIllIIIIIlIIIIIIl));
    }

    public static int invIndexToSlotId(int llllllllllllllllIllIIIIIlllIIIII) {
        if (llllllllllllllllIllIIIIIlllIIIII < 9 && llllllllllllllllIllIIIIIlllIIIII != -1) {
            return 44 - (8 - llllllllllllllllIllIIIIIlllIIIII);
        }
        return llllllllllllllllIllIIIIIlllIIIII;
    }

    public static boolean equalsBlockPos(BlockPos llllllllllllllllIllIIIIIllIllIII, BlockPos llllllllllllllllIllIIIIIllIllIIl) {
        if (!(llllllllllllllllIllIIIIIllIllIII instanceof Vec3i) || !(llllllllllllllllIllIIIIIllIllIII instanceof Vec3i)) {
            return false;
        }
        if (llllllllllllllllIllIIIIIllIllIII == null || llllllllllllllllIllIIIIIllIllIIl == null) {
            return false;
        }
        if (llllllllllllllllIllIIIIIllIllIII.getX() != llllllllllllllllIllIIIIIllIllIIl.getX()) {
            return false;
        }
        if (llllllllllllllllIllIIIIIllIllIII.getY() != llllllllllllllllIllIIIIIllIllIIl.getY()) {
            return false;
        }
        return llllllllllllllllIllIIIIIllIllIII.getZ() == llllllllllllllllIllIIIIIllIllIIl.getZ();
    }

    public static double DistanceTo(int llllllllllllllllIllIIIIIlIIllIlI, double llllllllllllllllIllIIIIIlIIllIIl, double llllllllllllllllIllIIIIIlIIllIII) {
        double llllllllllllllllIllIIIIIlIlIIIII = llllllllllllllllIllIIIIIlIIllIlI;
        double llllllllllllllllIllIIIIIlIIlllll = llllllllllllllllIllIIIIIlIIllIIl;
        double llllllllllllllllIllIIIIIlIIllllI = llllllllllllllllIllIIIIIlIIllIII;
        llllllllllllllllIllIIIIIlIlIIIII = llllllllllllllllIllIIIIIlIlIIIII >= 0.0 ? (llllllllllllllllIllIIIIIlIlIIIII += 0.5) : (llllllllllllllllIllIIIIIlIlIIIII -= 0.5);
        llllllllllllllllIllIIIIIlIIlllll = llllllllllllllllIllIIIIIlIIlllll >= 0.0 ? (llllllllllllllllIllIIIIIlIIlllll += 0.5) : (llllllllllllllllIllIIIIIlIIlllll -= 0.5);
        llllllllllllllllIllIIIIIlIIllllI = llllllllllllllllIllIIIIIlIIllllI >= 0.0 ? (llllllllllllllllIllIIIIIlIIllllI += 0.5) : (llllllllllllllllIllIIIIIlIIllllI -= 0.5);
        double llllllllllllllllIllIIIIIlIIlllIl = Ezz.mc.player.getX() - llllllllllllllllIllIIIIIlIlIIIII;
        double llllllllllllllllIllIIIIIlIIlllII = Ezz.mc.player.getY() - llllllllllllllllIllIIIIIlIIlllll;
        double llllllllllllllllIllIIIIIlIIllIll = Ezz.mc.player.getZ() - llllllllllllllllIllIIIIIlIIllllI;
        return Math.sqrt(llllllllllllllllIllIIIIIlIIlllIl * llllllllllllllllIllIIIIIlIIlllIl + llllllllllllllllIllIIIIIlIIlllII * llllllllllllllllIllIIIIIlIIlllII + llllllllllllllllIllIIIIIlIIllIll * llllllllllllllllIllIIIIIlIIllIll);
    }

    public static void attackEntity(Entity llllllllllllllllIllIIIIIlIIIIlII) {
        Ezz.mc.interactionManager.attackEntity((PlayerEntity)Ezz.mc.player, llllllllllllllllIllIIIIIlIIIIlII);
    }
}

