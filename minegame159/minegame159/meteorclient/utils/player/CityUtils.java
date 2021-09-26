/*
 * Decompiled with CFR 0.151.
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
    private static final MinecraftClient mc;
    static final boolean $assertionsDisabled;
    private static final BlockPos[] surround;

    public static PlayerEntity getPlayerTarget(double d) {
        if (CityUtils.mc.player.isDead()) {
            return null;
        }
        Object object = null;
        for (PlayerEntity object2 : CityUtils.mc.world.getPlayers()) {
            if (object2 == CityUtils.mc.player || object2.isDead() || !Friends.get().attack(object2) || (double)CityUtils.mc.player.distanceTo((Entity)object2) > d) continue;
            if (object == null) {
                object = object2;
                continue;
            }
            if (!(CityUtils.mc.player.distanceTo((Entity)object2) < CityUtils.mc.player.distanceTo((Entity)object))) continue;
            object = object2;
        }
        if (object == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (fakePlayerEntity.isDead() || !Friends.get().attack((PlayerEntity)fakePlayerEntity) || (double)CityUtils.mc.player.distanceTo((Entity)fakePlayerEntity) > d) continue;
                if (object == null) {
                    object = fakePlayerEntity;
                    continue;
                }
                if (!(CityUtils.mc.player.distanceTo((Entity)fakePlayerEntity) < CityUtils.mc.player.distanceTo((Entity)object))) continue;
                object = fakePlayerEntity;
            }
        }
        return object;
    }

    public static BlockPos getTargetBlock(PlayerEntity PlayerEntity2) {
        BlockPos BlockPos2 = null;
        ArrayList<BlockPos> arrayList = CityUtils.getTargetSurround(PlayerEntity2);
        ArrayList<BlockPos> arrayList2 = CityUtils.getTargetSurround((PlayerEntity)CityUtils.mc.player);
        if (arrayList == null) {
            return null;
        }
        for (BlockPos BlockPos3 : arrayList) {
            if (arrayList2 != null && !arrayList2.isEmpty() && arrayList2.contains(BlockPos3)) continue;
            if (BlockPos2 == null) {
                BlockPos2 = BlockPos3;
                continue;
            }
            if (!(CityUtils.mc.player.squaredDistanceTo(Utils.vec3d(BlockPos3)) < CityUtils.mc.player.squaredDistanceTo(Utils.vec3d(BlockPos2)))) continue;
            BlockPos2 = BlockPos3;
        }
        return BlockPos2;
    }

    private static ArrayList<BlockPos> getTargetSurround(PlayerEntity PlayerEntity2) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        boolean bl = false;
        for (int i = 0; i < 4; ++i) {
            BlockPos BlockPos2;
            if (PlayerEntity2 == null || (BlockPos2 = CityUtils.getSurround((Entity)PlayerEntity2, surround[i])) == null) continue;
            if (!$assertionsDisabled && CityUtils.mc.world == null) {
                throw new AssertionError();
            }
            if (CityUtils.mc.world.getBlockState(BlockPos2) == null) continue;
            if (!((AbstractBlockAccessor)CityUtils.mc.world.getBlockState(BlockPos2).getBlock()).isCollidable()) {
                bl = true;
            }
            if (CityUtils.mc.world.getBlockState(BlockPos2).getBlock() != Blocks.OBSIDIAN) continue;
            arrayList.add(BlockPos2);
            if (3 <= 3) continue;
            return null;
        }
        if (bl) {
            return null;
        }
        return arrayList;
    }

    static {
        $assertionsDisabled = !CityUtils.class.desiredAssertionStatus();
        mc = MinecraftClient.getInstance();
        surround = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
    }

    public static BlockPos getSurround(Entity Entity2, BlockPos BlockPos2) {
        Vec3d Vec3d2 = Entity2.getPos();
        if (BlockPos2 == null) {
            return new BlockPos(Vec3d2.x, Vec3d2.y, Vec3d2.z);
        }
        return new BlockPos(Vec3d2.x, Vec3d2.y, Vec3d2.z).add((Vec3i)BlockPos2);
    }
}

