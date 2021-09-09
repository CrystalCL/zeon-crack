/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.player;

import java.util.ArrayList;
import minegame159.meteorclient.mixin.AbstractBlockAccessor;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;

public class CityUtils {
    private static final /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ BlockPos[] surround;

    static {
        mc = MinecraftClient.getInstance();
        surround = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
    }

    public static BlockPos getTargetBlock(PlayerEntity llllllllllllllllIlllIIIIIlIIlIll) {
        BlockPos llllllllllllllllIlllIIIIIlIIlllI = null;
        ArrayList<BlockPos> llllllllllllllllIlllIIIIIlIIllIl = CityUtils.getTargetSurround(llllllllllllllllIlllIIIIIlIIlIll);
        ArrayList<BlockPos> llllllllllllllllIlllIIIIIlIIllII = CityUtils.getTargetSurround((PlayerEntity)CityUtils.mc.player);
        if (llllllllllllllllIlllIIIIIlIIllIl == null) {
            return null;
        }
        for (BlockPos llllllllllllllllIlllIIIIIlIlIIII : llllllllllllllllIlllIIIIIlIIllIl) {
            if (llllllllllllllllIlllIIIIIlIIllII != null && !llllllllllllllllIlllIIIIIlIIllII.isEmpty() && llllllllllllllllIlllIIIIIlIIllII.contains((Object)llllllllllllllllIlllIIIIIlIlIIII)) continue;
            if (llllllllllllllllIlllIIIIIlIIlllI == null) {
                llllllllllllllllIlllIIIIIlIIlllI = llllllllllllllllIlllIIIIIlIlIIII;
                continue;
            }
            if (!(CityUtils.mc.player.squaredDistanceTo(Utils.vec3d(llllllllllllllllIlllIIIIIlIlIIII)) < CityUtils.mc.player.squaredDistanceTo(Utils.vec3d(llllllllllllllllIlllIIIIIlIIlllI)))) continue;
            llllllllllllllllIlllIIIIIlIIlllI = llllllllllllllllIlllIIIIIlIlIIII;
        }
        return llllllllllllllllIlllIIIIIlIIlllI;
    }

    public static PlayerEntity getPlayerTarget(double llllllllllllllllIlllIIIIIlIllIlI) {
        if (CityUtils.mc.player.isDead()) {
            return null;
        }
        Object llllllllllllllllIlllIIIIIlIllIll = null;
        for (PlayerEntity llllllllllllllllIlllIIIIIlIllllI : CityUtils.mc.world.getPlayers()) {
            if (llllllllllllllllIlllIIIIIlIllllI == CityUtils.mc.player || llllllllllllllllIlllIIIIIlIllllI.isDead() || !Friends.get().attack(llllllllllllllllIlllIIIIIlIllllI) || (double)CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIllllI) > llllllllllllllllIlllIIIIIlIllIlI) continue;
            if (llllllllllllllllIlllIIIIIlIllIll == null) {
                llllllllllllllllIlllIIIIIlIllIll = llllllllllllllllIlllIIIIIlIllllI;
                continue;
            }
            if (!(CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIllllI) < CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIllIll))) continue;
            llllllllllllllllIlllIIIIIlIllIll = llllllllllllllllIlllIIIIIlIllllI;
        }
        if (llllllllllllllllIlllIIIIIlIllIll == null) {
            for (FakePlayerEntity llllllllllllllllIlllIIIIIlIlllIl : FakePlayerUtils.getPlayers().keySet()) {
                if (llllllllllllllllIlllIIIIIlIlllIl.isDead() || !Friends.get().attack((PlayerEntity)llllllllllllllllIlllIIIIIlIlllIl) || (double)CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIlllIl) > llllllllllllllllIlllIIIIIlIllIlI) continue;
                if (llllllllllllllllIlllIIIIIlIllIll == null) {
                    llllllllllllllllIlllIIIIIlIllIll = llllllllllllllllIlllIIIIIlIlllIl;
                    continue;
                }
                if (!(CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIlllIl) < CityUtils.mc.player.distanceTo((Entity)llllllllllllllllIlllIIIIIlIllIll))) continue;
                llllllllllllllllIlllIIIIIlIllIll = llllllllllllllllIlllIIIIIlIlllIl;
            }
        }
        return llllllllllllllllIlllIIIIIlIllIll;
    }

    public static BlockPos getSurround(Entity llllllllllllllllIlllIIIIIIllIIII, BlockPos llllllllllllllllIlllIIIIIIlIllll) {
        Vec3d llllllllllllllllIlllIIIIIIllIIIl = llllllllllllllllIlllIIIIIIllIIII.getPos();
        if (llllllllllllllllIlllIIIIIIlIllll == null) {
            return new BlockPos(llllllllllllllllIlllIIIIIIllIIIl.x, llllllllllllllllIlllIIIIIIllIIIl.y, llllllllllllllllIlllIIIIIIllIIIl.z);
        }
        return new BlockPos(llllllllllllllllIlllIIIIIIllIIIl.x, llllllllllllllllIlllIIIIIIllIIIl.y, llllllllllllllllIlllIIIIIIllIIIl.z).add((Vec3i)llllllllllllllllIlllIIIIIIlIllll);
    }

    public CityUtils() {
        CityUtils llllllllllllllllIlllIIIIIllIIIll;
    }

    private static ArrayList<BlockPos> getTargetSurround(PlayerEntity llllllllllllllllIlllIIIIIIlllIll) {
        ArrayList<BlockPos> llllllllllllllllIlllIIIIIIllllIl = new ArrayList<BlockPos>();
        boolean llllllllllllllllIlllIIIIIIllllII = false;
        for (int llllllllllllllllIlllIIIIIIllllll = 0; llllllllllllllllIlllIIIIIIllllll < 4; ++llllllllllllllllIlllIIIIIIllllll) {
            BlockPos llllllllllllllllIlllIIIIIlIIIIII;
            if (llllllllllllllllIlllIIIIIIlllIll == null || (llllllllllllllllIlllIIIIIlIIIIII = CityUtils.getSurround((Entity)llllllllllllllllIlllIIIIIIlllIll, surround[llllllllllllllllIlllIIIIIIllllll])) == null) continue;
            assert (CityUtils.mc.world != null);
            if (CityUtils.mc.world.getBlockState(llllllllllllllllIlllIIIIIlIIIIII) == null) continue;
            if (!((AbstractBlockAccessor)CityUtils.mc.world.getBlockState(llllllllllllllllIlllIIIIIlIIIIII).getBlock()).isCollidable()) {
                llllllllllllllllIlllIIIIIIllllII = true;
            }
            if (CityUtils.mc.world.getBlockState(llllllllllllllllIlllIIIIIlIIIIII).getBlock() != Blocks.OBSIDIAN) continue;
            llllllllllllllllIlllIIIIIIllllIl.add(llllllllllllllllIlllIIIIIlIIIIII);
        }
        if (llllllllllllllllIlllIIIIIIllllII) {
            return null;
        }
        return llllllllllllllllIlllIIIIIIllllIl;
    }
}

