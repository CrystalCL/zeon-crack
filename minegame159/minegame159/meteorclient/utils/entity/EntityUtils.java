/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.misc.text.TextUtils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.client.network.PlayerListEntry;

public class EntityUtils {
    private static final List<Entity> entities = new ArrayList<Entity>();
    public static MinecraftClient mc;

    public static boolean isAttackable(EntityType<?> EntityType2) {
        return EntityType2 != EntityType.AREA_EFFECT_CLOUD && EntityType2 != EntityType.ARROW && EntityType2 != EntityType.FALLING_BLOCK && EntityType2 != EntityType.FIREWORK_ROCKET && EntityType2 != EntityType.ITEM && EntityType2 != EntityType.LLAMA_SPIT && EntityType2 != EntityType.SPECTRAL_ARROW && EntityType2 != EntityType.ENDER_PEARL && EntityType2 != EntityType.EXPERIENCE_BOTTLE && EntityType2 != EntityType.POTION && EntityType2 != EntityType.TRIDENT && EntityType2 != EntityType.LIGHTNING_BOLT && EntityType2 != EntityType.FISHING_BOBBER && EntityType2 != EntityType.EXPERIENCE_ORB && EntityType2 != EntityType.EGG;
    }

    private static boolean lambda$getPlayerTarget$1(double d, boolean bl, Entity Entity2) {
        if (!(Entity2 instanceof PlayerEntity) || Entity2 == EntityUtils.mc.player) {
            return false;
        }
        if (((PlayerEntity)Entity2).isDead() || ((PlayerEntity)Entity2).getHealth() <= 0.0f) {
            return false;
        }
        if ((double)EntityUtils.mc.player.distanceTo(Entity2) > d) {
            return false;
        }
        if (!Friends.get().attack((PlayerEntity)Entity2) && !bl) {
            return false;
        }
        return EntityUtils.getGameMode((PlayerEntity)Entity2) == GameMode.SURVIVAL || Entity2 instanceof FakePlayerEntity;
    }

    public static int getPing(PlayerEntity PlayerEntity2) {
        if (mc.getNetworkHandler() == null) {
            return 0;
        }
        PlayerListEntry PlayerListEntry2 = mc.getNetworkHandler().getPlayerListEntry(PlayerEntity2.getUuid());
        if (PlayerListEntry2 == null) {
            return 0;
        }
        return PlayerListEntry2.getLatency();
    }

    public static Color getEntityColor(Entity Entity2, Color color, Color color2, Color color3, Color color4, Color color5, Color color6, boolean bl) {
        if (Entity2 instanceof PlayerEntity) {
            Color color7 = Friends.get().getFriendColor((PlayerEntity)Entity2);
            if (color7 != null) {
                return new Color(color7.r, color7.g, color7.b, color.a);
            }
            if (bl) {
                return TextUtils.getMostPopularColor(Entity2.getDisplayName());
            }
            return color;
        }
        switch (Entity2.getType().getSpawnGroup()) {
            case CREATURE: {
                return color2;
            }
            case WATER_CREATURE: {
                return color3;
            }
            case MONSTER: {
                return color4;
            }
            case AMBIENT: {
                return color5;
            }
            case MISC: {
                return color6;
            }
        }
        return Utils.WHITE;
    }

    public static float getTotalHealth(PlayerEntity PlayerEntity2) {
        return PlayerEntity2.getHealth() + PlayerEntity2.getAbsorptionAmount();
    }

    public static void getList(Predicate<Entity> predicate, SortPriority sortPriority, List<Entity> list) {
        for (Entity Entity2 : EntityUtils.mc.world.getEntities()) {
            if (!predicate.test(Entity2)) continue;
            list.add(Entity2);
        }
        for (Entity Entity2 : FakePlayerUtils.getPlayers().keySet()) {
            if (!predicate.test(Entity2)) continue;
            list.add(Entity2);
        }
        list.sort((arg_0, arg_1) -> EntityUtils.lambda$getList$0(sortPriority, arg_0, arg_1));
    }

    public static boolean isInRenderDistance(BlockPos BlockPos2) {
        if (BlockPos2 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(BlockPos2.getX(), BlockPos2.getZ());
    }

    public static Entity get(Predicate<Entity> predicate, SortPriority sortPriority) {
        entities.clear();
        EntityUtils.getList(predicate, sortPriority, entities);
        if (!entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }

    private static int invertSort(int n) {
        if (n == 0) {
            return 0;
        }
        return n > 0 ? -1 : 1;
    }

    private static int sort(Entity Entity2, Entity Entity3, SortPriority sortPriority) {
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$SortPriority[sortPriority.ordinal()]) {
            case 1: {
                return Double.compare(Entity2.distanceTo((Entity)EntityUtils.mc.player), Entity3.distanceTo((Entity)EntityUtils.mc.player));
            }
            case 2: {
                return EntityUtils.invertSort(Double.compare(Entity2.distanceTo((Entity)EntityUtils.mc.player), Entity3.distanceTo((Entity)EntityUtils.mc.player)));
            }
            case 3: {
                return EntityUtils.sortHealth(Entity2, Entity3);
            }
            case 4: {
                return EntityUtils.invertSort(EntityUtils.sortHealth(Entity2, Entity3));
            }
        }
        return 0;
    }

    private static int lambda$getList$0(SortPriority sortPriority, Entity Entity2, Entity Entity3) {
        return EntityUtils.sort(Entity2, Entity3, sortPriority);
    }

    public static boolean isAboveWater(Entity Entity2) {
        BlockState BlockState2;
        Mutable class_23392 = Entity2.getBlockPos().mutableCopy();
        for (int i = 0; i < 64 && !(BlockState2 = EntityUtils.mc.world.getBlockState((BlockPos)class_23392)).getMaterial().blocksMovement(); ++i) {
            Fluid Fluid2 = BlockState2.getFluidState().getFluid();
            if (Fluid2 == Fluids.WATER || Fluid2 == Fluids.FLOWING_WATER) {
                return true;
            }
            class_23392.move(0, -1, 0);
            if (-5 < 0) continue;
            return false;
        }
        return false;
    }

    public static boolean isInvalid(PlayerEntity PlayerEntity2, double d) {
        if (PlayerEntity2 == null) {
            return true;
        }
        return (double)EntityUtils.mc.player.distanceTo((Entity)PlayerEntity2) > d || !PlayerEntity2.isAlive() || PlayerEntity2.isDead() || PlayerEntity2.getHealth() <= 0.0f;
    }

    public static GameMode getGameMode(PlayerEntity PlayerEntity2) {
        PlayerListEntry PlayerListEntry2 = mc.getNetworkHandler().getPlayerListEntry(PlayerEntity2.getUuid());
        if (PlayerListEntry2 == null) {
            return null;
        }
        return PlayerListEntry2.getGameMode();
    }

    public static PlayerEntity getPlayerTarget(double d, SortPriority sortPriority, boolean bl) {
        if (!Utils.canUpdate()) {
            return null;
        }
        return (PlayerEntity)EntityUtils.get(arg_0 -> EntityUtils.lambda$getPlayerTarget$1(d, bl, arg_0), sortPriority);
    }

    public static boolean isInRenderDistance(double d, double d2) {
        double d3 = Math.abs(EntityUtils.mc.gameRenderer.getCamera().getPos().x - d);
        double d4 = Math.abs(EntityUtils.mc.gameRenderer.getCamera().getPos().z - d2);
        double d5 = (EntityUtils.mc.options.viewDistance + 1) * 16;
        return d3 < d5 && d4 < d5;
    }

    public static boolean isInRenderDistance(BlockEntity BlockEntity2) {
        if (BlockEntity2 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(BlockEntity2.getPos().getX(), BlockEntity2.getPos().getZ());
    }

    public static boolean isInRenderDistance(Entity Entity2) {
        if (Entity2 == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(Entity2.getX(), Entity2.getZ());
    }

    private static int sortHealth(Entity Entity2, Entity Entity3) {
        boolean bl = Entity2 instanceof LivingEntity;
        boolean bl2 = Entity3 instanceof LivingEntity;
        if (!bl && !bl2) {
            return 0;
        }
        if (bl && !bl2) {
            return 1;
        }
        if (!bl) {
            return -1;
        }
        return Float.compare(((LivingEntity)Entity2).getHealth(), ((LivingEntity)Entity3).getHealth());
    }
}

