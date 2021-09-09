/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.GameMode
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.fluid.Fluid
 *  net.minecraft.fluid.Fluids
 *  net.minecraft.client.network.PlayerListEntry
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
    private static final /* synthetic */ List<Entity> entities;
    public static /* synthetic */ MinecraftClient mc;

    static {
        entities = new ArrayList<Entity>();
    }

    private static int invertSort(int lllllllllllllllllIIllIllIIIllllI) {
        if (lllllllllllllllllIIllIllIIIllllI == 0) {
            return 0;
        }
        return lllllllllllllllllIIllIllIIIllllI > 0 ? -1 : 1;
    }

    public static boolean isInRenderDistance(BlockEntity lllllllllllllllllIIllIlIlllIlIll) {
        if (lllllllllllllllllIIllIlIlllIlIll == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(lllllllllllllllllIIllIlIlllIlIll.getPos().getX(), lllllllllllllllllIIllIlIlllIlIll.getPos().getZ());
    }

    public static PlayerEntity getPlayerTarget(double lllllllllllllllllIIllIllIIIlIIIl, SortPriority lllllllllllllllllIIllIllIIIIllIl, boolean lllllllllllllllllIIllIllIIIIllll) {
        if (!Utils.canUpdate()) {
            return null;
        }
        return (PlayerEntity)EntityUtils.get(lllllllllllllllllIIllIlIllIlIIll -> {
            if (!(lllllllllllllllllIIllIlIllIlIIll instanceof PlayerEntity) || lllllllllllllllllIIllIlIllIlIIll == EntityUtils.mc.player) {
                return false;
            }
            if (((PlayerEntity)lllllllllllllllllIIllIlIllIlIIll).isDead() || ((PlayerEntity)lllllllllllllllllIIllIlIllIlIIll).getHealth() <= 0.0f) {
                return false;
            }
            if ((double)EntityUtils.mc.player.distanceTo(lllllllllllllllllIIllIlIllIlIIll) > lllllllllllllllllIIllIllIIIlIIIl) {
                return false;
            }
            if (!Friends.get().attack((PlayerEntity)lllllllllllllllllIIllIlIllIlIIll) && !lllllllllllllllllIIllIllIIIIllll) {
                return false;
            }
            return EntityUtils.getGameMode((PlayerEntity)lllllllllllllllllIIllIlIllIlIIll) == GameMode.SURVIVAL || lllllllllllllllllIIllIlIllIlIIll instanceof FakePlayerEntity;
        }, lllllllllllllllllIIllIllIIIIllIl);
    }

    public static boolean isInRenderDistance(double lllllllllllllllllIIllIlIlllIIIlI, double lllllllllllllllllIIllIlIlllIIIIl) {
        double lllllllllllllllllIIllIlIlllIIIII = Math.abs(EntityUtils.mc.gameRenderer.getCamera().getPos().x - lllllllllllllllllIIllIlIlllIIIlI);
        double lllllllllllllllllIIllIlIllIlllll = Math.abs(EntityUtils.mc.gameRenderer.getCamera().getPos().z - lllllllllllllllllIIllIlIlllIIIIl);
        double lllllllllllllllllIIllIlIllIllllI = (EntityUtils.mc.options.viewDistance + 1) * 16;
        return lllllllllllllllllIIllIlIlllIIIII < lllllllllllllllllIIllIlIllIllllI && lllllllllllllllllIIllIlIllIlllll < lllllllllllllllllIIllIlIllIllllI;
    }

    public static int getPing(PlayerEntity lllllllllllllllllIIllIllIIIIlIIl) {
        if (mc.getNetworkHandler() == null) {
            return 0;
        }
        PlayerListEntry lllllllllllllllllIIllIllIIIIlIII = mc.getNetworkHandler().getPlayerListEntry(lllllllllllllllllIIllIllIIIIlIIl.getUuid());
        if (lllllllllllllllllIIllIllIIIIlIII == null) {
            return 0;
        }
        return lllllllllllllllllIIllIllIIIIlIII.getLatency();
    }

    public EntityUtils() {
        EntityUtils lllllllllllllllllIIllIllIllIlIIl;
    }

    public static Entity get(Predicate<Entity> lllllllllllllllllIIllIllIlIIIllI, SortPriority lllllllllllllllllIIllIllIlIIIlll) {
        entities.clear();
        EntityUtils.getList(lllllllllllllllllIIllIllIlIIIllI, lllllllllllllllllIIllIllIlIIIlll, entities);
        if (!entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }

    private static int sort(Entity lllllllllllllllllIIllIllIIlIllll, Entity lllllllllllllllllIIllIllIIlIlllI, SortPriority lllllllllllllllllIIllIllIIlIllIl) {
        switch (lllllllllllllllllIIllIllIIlIllIl) {
            case LowestDistance: {
                return Double.compare(lllllllllllllllllIIllIllIIlIllll.distanceTo((Entity)EntityUtils.mc.player), lllllllllllllllllIIllIllIIlIlllI.distanceTo((Entity)EntityUtils.mc.player));
            }
            case HighestDistance: {
                return EntityUtils.invertSort(Double.compare(lllllllllllllllllIIllIllIIlIllll.distanceTo((Entity)EntityUtils.mc.player), lllllllllllllllllIIllIllIIlIlllI.distanceTo((Entity)EntityUtils.mc.player)));
            }
            case LowestHealth: {
                return EntityUtils.sortHealth(lllllllllllllllllIIllIllIIlIllll, lllllllllllllllllIIllIllIIlIlllI);
            }
            case HighestHealth: {
                return EntityUtils.invertSort(EntityUtils.sortHealth(lllllllllllllllllIIllIllIIlIllll, lllllllllllllllllIIllIllIIlIlllI));
            }
        }
        return 0;
    }

    public static boolean isInRenderDistance(BlockPos lllllllllllllllllIIllIlIlllIlIIl) {
        if (lllllllllllllllllIIllIlIlllIlIIl == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(lllllllllllllllllIIllIlIlllIlIIl.getX(), lllllllllllllllllIIllIlIlllIlIIl.getZ());
    }

    private static int sortHealth(Entity lllllllllllllllllIIllIllIIlIlIII, Entity lllllllllllllllllIIllIllIIlIIIll) {
        boolean lllllllllllllllllIIllIllIIlIIllI = lllllllllllllllllIIllIllIIlIlIII instanceof LivingEntity;
        boolean lllllllllllllllllIIllIllIIlIIlIl = lllllllllllllllllIIllIllIIlIIIll instanceof LivingEntity;
        if (!lllllllllllllllllIIllIllIIlIIllI && !lllllllllllllllllIIllIllIIlIIlIl) {
            return 0;
        }
        if (lllllllllllllllllIIllIllIIlIIllI && !lllllllllllllllllIIllIllIIlIIlIl) {
            return 1;
        }
        if (!lllllllllllllllllIIllIllIIlIIllI) {
            return -1;
        }
        return Float.compare(((LivingEntity)lllllllllllllllllIIllIllIIlIlIII).getHealth(), ((LivingEntity)lllllllllllllllllIIllIllIIlIIIll).getHealth());
    }

    public static boolean isAboveWater(Entity lllllllllllllllllIIllIlIllllIlll) {
        BlockState lllllllllllllllllIIllIlIlllllIlI;
        Mutable lllllllllllllllllIIllIlIllllIllI = lllllllllllllllllIIllIlIllllIlll.getBlockPos().mutableCopy();
        for (int lllllllllllllllllIIllIlIlllllIII = 0; lllllllllllllllllIIllIlIlllllIII < 64 && !(lllllllllllllllllIIllIlIlllllIlI = EntityUtils.mc.world.getBlockState((BlockPos)lllllllllllllllllIIllIlIllllIllI)).getMaterial().blocksMovement(); ++lllllllllllllllllIIllIlIlllllIII) {
            Fluid lllllllllllllllllIIllIlIlllllIIl = lllllllllllllllllIIllIlIlllllIlI.getFluidState().getFluid();
            if (lllllllllllllllllIIllIlIlllllIIl == Fluids.WATER || lllllllllllllllllIIllIlIlllllIIl == Fluids.FLOWING_WATER) {
                return true;
            }
            lllllllllllllllllIIllIlIllllIllI.move(0, -1, 0);
        }
        return false;
    }

    public static boolean isInRenderDistance(Entity lllllllllllllllllIIllIlIlllIllll) {
        if (lllllllllllllllllIIllIlIlllIllll == null) {
            return false;
        }
        return EntityUtils.isInRenderDistance(lllllllllllllllllIIllIlIlllIllll.getX(), lllllllllllllllllIIllIlIlllIllll.getZ());
    }

    public static boolean isAttackable(EntityType<?> lllllllllllllllllIIllIllIllIIlll) {
        return lllllllllllllllllIIllIllIllIIlll != EntityType.AREA_EFFECT_CLOUD && lllllllllllllllllIIllIllIllIIlll != EntityType.ARROW && lllllllllllllllllIIllIllIllIIlll != EntityType.FALLING_BLOCK && lllllllllllllllllIIllIllIllIIlll != EntityType.FIREWORK_ROCKET && lllllllllllllllllIIllIllIllIIlll != EntityType.ITEM && lllllllllllllllllIIllIllIllIIlll != EntityType.LLAMA_SPIT && lllllllllllllllllIIllIllIllIIlll != EntityType.SPECTRAL_ARROW && lllllllllllllllllIIllIllIllIIlll != EntityType.ENDER_PEARL && lllllllllllllllllIIllIllIllIIlll != EntityType.EXPERIENCE_BOTTLE && lllllllllllllllllIIllIllIllIIlll != EntityType.POTION && lllllllllllllllllIIllIllIllIIlll != EntityType.TRIDENT && lllllllllllllllllIIllIllIllIIlll != EntityType.LIGHTNING_BOLT && lllllllllllllllllIIllIllIllIIlll != EntityType.FISHING_BOBBER && lllllllllllllllllIIllIllIllIIlll != EntityType.EXPERIENCE_ORB && lllllllllllllllllIIllIllIllIIlll != EntityType.EGG;
    }

    public static Color getEntityColor(Entity lllllllllllllllllIIllIllIlIllIll, Color lllllllllllllllllIIllIllIlIlIIlI, Color lllllllllllllllllIIllIllIlIlIIIl, Color lllllllllllllllllIIllIllIlIllIII, Color lllllllllllllllllIIllIllIlIIllll, Color lllllllllllllllllIIllIllIlIlIllI, Color lllllllllllllllllIIllIllIlIlIlIl, boolean lllllllllllllllllIIllIllIlIlIlII) {
        if (lllllllllllllllllIIllIllIlIllIll instanceof PlayerEntity) {
            Color lllllllllllllllllIIllIllIlIlllII = Friends.get().getFriendColor((PlayerEntity)lllllllllllllllllIIllIllIlIllIll);
            if (lllllllllllllllllIIllIllIlIlllII != null) {
                return new Color(lllllllllllllllllIIllIllIlIlllII.r, lllllllllllllllllIIllIllIlIlllII.g, lllllllllllllllllIIllIllIlIlllII.b, lllllllllllllllllIIllIllIlIlIIlI.a);
            }
            if (lllllllllllllllllIIllIllIlIlIlII) {
                return TextUtils.getMostPopularColor(lllllllllllllllllIIllIllIlIllIll.getDisplayName());
            }
            return lllllllllllllllllIIllIllIlIlIIlI;
        }
        switch (lllllllllllllllllIIllIllIlIllIll.getType().getSpawnGroup()) {
            case CREATURE: {
                return lllllllllllllllllIIllIllIlIlIIIl;
            }
            case WATER_CREATURE: {
                return lllllllllllllllllIIllIllIlIllIII;
            }
            case MONSTER: {
                return lllllllllllllllllIIllIllIlIIllll;
            }
            case AMBIENT: {
                return lllllllllllllllllIIllIllIlIlIllI;
            }
            case MISC: {
                return lllllllllllllllllIIllIllIlIlIlIl;
            }
        }
        return Utils.WHITE;
    }

    public static void getList(Predicate<Entity> lllllllllllllllllIIllIllIIlllIlI, SortPriority lllllllllllllllllIIllIllIIllllII, List<Entity> lllllllllllllllllIIllIllIIlllIII) {
        for (Entity Entity2 : EntityUtils.mc.world.getEntities()) {
            if (!lllllllllllllllllIIllIllIIlllIlI.test(Entity2)) continue;
            lllllllllllllllllIIllIllIIlllIII.add(Entity2);
        }
        for (Entity Entity3 : FakePlayerUtils.getPlayers().keySet()) {
            if (!lllllllllllllllllIIllIllIIlllIlI.test(Entity3)) continue;
            lllllllllllllllllIIllIllIIlllIII.add(Entity3);
        }
        lllllllllllllllllIIllIllIIlllIII.sort((lllllllllllllllllIIllIlIllIIlIII, lllllllllllllllllIIllIlIllIIIlll) -> EntityUtils.sort(lllllllllllllllllIIllIlIllIIlIII, lllllllllllllllllIIllIlIllIIIlll, lllllllllllllllllIIllIllIIllllII));
    }

    public static float getTotalHealth(PlayerEntity lllllllllllllllllIIllIllIIIlllII) {
        return lllllllllllllllllIIllIllIIIlllII.getHealth() + lllllllllllllllllIIllIllIIIlllII.getAbsorptionAmount();
    }

    public static boolean isInvalid(PlayerEntity lllllllllllllllllIIllIllIIIllIII, double lllllllllllllllllIIllIllIIIlIlIl) {
        if (lllllllllllllllllIIllIllIIIllIII == null) {
            return true;
        }
        return (double)EntityUtils.mc.player.distanceTo((Entity)lllllllllllllllllIIllIllIIIllIII) > lllllllllllllllllIIllIllIIIlIlIl || !lllllllllllllllllIIllIllIIIllIII.isAlive() || lllllllllllllllllIIllIllIIIllIII.isDead() || lllllllllllllllllIIllIllIIIllIII.getHealth() <= 0.0f;
    }

    public static GameMode getGameMode(PlayerEntity lllllllllllllllllIIllIllIIIIIIIl) {
        PlayerListEntry lllllllllllllllllIIllIllIIIIIIlI = mc.getNetworkHandler().getPlayerListEntry(lllllllllllllllllIIllIllIIIIIIIl.getUuid());
        if (lllllllllllllllllIIllIllIIIIIIlI == null) {
            return null;
        }
        return lllllllllllllllllIIllIllIIIIIIlI.getGameMode();
    }
}

