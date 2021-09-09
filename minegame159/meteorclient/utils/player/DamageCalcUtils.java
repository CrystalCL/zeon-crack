/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.DamageUtil
 *  net.minecraft.entity.damage.DamageSource
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.world.explosion.Explosion
 *  net.minecraft.world.explosion.Explosion$class_4179
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.entity.attribute.EntityAttributes
 */
package minegame159.meteorclient.utils.player;

import java.util.Objects;
import minegame159.meteorclient.mixininterface.IExplosion;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;

public class DamageCalcUtils {
    public static /* synthetic */ MinecraftClient mc;
    private static final /* synthetic */ Explosion explosion;

    static {
        explosion = new Explosion(null, null, 0.0, 0.0, 0.0, 6.0f, false, Explosion.class_4179.DESTROY);
        mc = MinecraftClient.getInstance();
    }

    public DamageCalcUtils() {
        DamageCalcUtils llllllllllllllllIllIIIlIlIlIlIII;
    }

    public static double getSwordDamage(PlayerEntity llllllllllllllllIllIIIlIIlllIIII, boolean llllllllllllllllIllIIIlIIllIllll) {
        double llllllllllllllllIllIIIlIIlllIIIl = 0.0;
        if (llllllllllllllllIllIIIlIIllIllll) {
            if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getItem() == Items.DIAMOND_SWORD) {
                llllllllllllllllIllIIIlIIlllIIIl += 7.0;
            } else if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getItem() == Items.GOLDEN_SWORD) {
                llllllllllllllllIllIIIlIIlllIIIl += 4.0;
            } else if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getItem() == Items.IRON_SWORD) {
                llllllllllllllllIllIIIlIIlllIIIl += 6.0;
            } else if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getItem() == Items.STONE_SWORD) {
                llllllllllllllllIllIIIlIIlllIIIl += 5.0;
            } else if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getItem() == Items.WOODEN_SWORD) {
                llllllllllllllllIllIIIlIIlllIIIl += 4.0;
            }
            llllllllllllllllIllIIIlIIlllIIIl *= 1.5;
        }
        if (llllllllllllllllIllIIIlIIlllIIII.getActiveItem().getEnchantments() != null && EnchantmentHelper.get((ItemStack)llllllllllllllllIllIIIlIIlllIIII.getActiveItem()).containsKey((Object)Enchantments.SHARPNESS)) {
            int llllllllllllllllIllIIIlIIlllIlIl = EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)llllllllllllllllIllIIIlIIlllIIII.getActiveItem());
            llllllllllllllllIllIIIlIIlllIIIl += 0.5 * (double)llllllllllllllllIllIIIlIIlllIlIl + 0.5;
        }
        if (llllllllllllllllIllIIIlIIlllIIII.getActiveStatusEffects().containsKey((Object)StatusEffects.STRENGTH)) {
            int llllllllllllllllIllIIIlIIlllIlII = Objects.requireNonNull(llllllllllllllllIllIIIlIIlllIIII.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier() + 1;
            llllllllllllllllIllIIIlIIlllIIIl += (double)(3 * llllllllllllllllIllIIIlIIlllIlII);
        }
        llllllllllllllllIllIIIlIIlllIIIl = DamageCalcUtils.resistanceReduction((LivingEntity)llllllllllllllllIllIIIlIIlllIIII, llllllllllllllllIllIIIlIIlllIIIl);
        llllllllllllllllIllIIIlIIlllIIIl = DamageUtil.getDamageLeft((float)((float)llllllllllllllllIllIIIlIIlllIIIl), (float)llllllllllllllllIllIIIlIIlllIIII.getArmor(), (float)((float)llllllllllllllllIllIIIlIIlllIIII.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        return (llllllllllllllllIllIIIlIIlllIIIl = DamageCalcUtils.normalProtReduction((Entity)llllllllllllllllIllIIIlIIlllIIII, llllllllllllllllIllIIIlIIlllIIIl)) < 0.0 ? 0.0 : llllllllllllllllIllIIIlIIlllIIIl;
    }

    private static double blastProtReduction(Entity llllllllllllllllIllIIIlIIlIlllII, double llllllllllllllllIllIIIlIIlIllIll, Explosion llllllllllllllllIllIIIlIIlIllIlI) {
        int llllllllllllllllIllIIIlIIlIllIIl = EnchantmentHelper.getProtectionAmount((Iterable)llllllllllllllllIllIIIlIIlIlllII.getArmorItems(), (DamageSource)DamageSource.explosion((Explosion)llllllllllllllllIllIIIlIIlIllIlI));
        if (llllllllllllllllIllIIIlIIlIllIIl > 20) {
            llllllllllllllllIllIIIlIIlIllIIl = 20;
        }
        return (llllllllllllllllIllIIIlIIlIllIll *= 1.0 - (double)llllllllllllllllIllIIIlIIlIllIIl / 25.0) < 0.0 ? 0.0 : llllllllllllllllIllIIIlIIlIllIll;
    }

    private static double normalProtReduction(Entity llllllllllllllllIllIIIlIIllIIllI, double llllllllllllllllIllIIIlIIllIIIlI) {
        int llllllllllllllllIllIIIlIIllIIlII = EnchantmentHelper.getProtectionAmount((Iterable)llllllllllllllllIllIIIlIIllIIllI.getArmorItems(), (DamageSource)DamageSource.GENERIC);
        if (llllllllllllllllIllIIIlIIllIIlII > 20) {
            llllllllllllllllIllIIIlIIllIIlII = 20;
        }
        return (llllllllllllllllIllIIIlIIllIIIlI *= 1.0 - (double)llllllllllllllllIllIIIlIIllIIlII / 25.0) < 0.0 ? 0.0 : llllllllllllllllIllIIIlIIllIIIlI;
    }

    private static double getDamageForDifficulty(double llllllllllllllllIllIIIlIIllIlIlI) {
        switch (DamageCalcUtils.mc.world.getDifficulty()) {
            case PEACEFUL: {
                return 0.0;
            }
            case EASY: {
                return Math.min(llllllllllllllllIllIIIlIIllIlIlI / 2.0 + 1.0, llllllllllllllllIllIIIlIIllIlIlI);
            }
            case HARD: {
                return llllllllllllllllIllIIIlIIllIlIlI * 3.0 / 2.0;
            }
        }
        return llllllllllllllllIllIIIlIIllIlIlI;
    }

    public static double crystalDamage(LivingEntity llllllllllllllllIllIIIlIlIlIIIII, Vec3d llllllllllllllllIllIIIlIlIIlllll) {
        if (llllllllllllllllIllIIIlIlIlIIIII instanceof PlayerEntity && ((PlayerEntity)llllllllllllllllIllIIIlIlIlIIIII).abilities.creativeMode) {
            return 0.0;
        }
        double llllllllllllllllIllIIIlIlIIllllI = Math.sqrt(llllllllllllllllIllIIIlIlIlIIIII.squaredDistanceTo(llllllllllllllllIllIIIlIlIIlllll));
        if (llllllllllllllllIllIIIlIlIIllllI > 12.0) {
            return 0.0;
        }
        double llllllllllllllllIllIIIlIlIIlllIl = Explosion.getExposure((Vec3d)llllllllllllllllIllIIIlIlIIlllll, (Entity)llllllllllllllllIllIIIlIlIlIIIII);
        double llllllllllllllllIllIIIlIlIIlllII = (1.0 - llllllllllllllllIllIIIlIlIIllllI / 12.0) * llllllllllllllllIllIIIlIlIIlllIl;
        double llllllllllllllllIllIIIlIlIIllIll = (llllllllllllllllIllIIIlIlIIlllII * llllllllllllllllIllIIIlIlIIlllII + llllllllllllllllIllIIIlIlIIlllII) / 2.0 * 7.0 * 12.0 + 1.0;
        llllllllllllllllIllIIIlIlIIllIll = DamageCalcUtils.getDamageForDifficulty(llllllllllllllllIllIIIlIlIIllIll);
        llllllllllllllllIllIIIlIlIIllIll = DamageCalcUtils.resistanceReduction(llllllllllllllllIllIIIlIlIlIIIII, llllllllllllllllIllIIIlIlIIllIll);
        llllllllllllllllIllIIIlIlIIllIll = DamageUtil.getDamageLeft((float)((float)llllllllllllllllIllIIIlIlIIllIll), (float)llllllllllllllllIllIIIlIlIlIIIII.getArmor(), (float)((float)llllllllllllllllIllIIIlIlIlIIIII.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        ((IExplosion)explosion).set(llllllllllllllllIllIIIlIlIIlllll, 6.0f, false);
        llllllllllllllllIllIIIlIlIIllIll = DamageCalcUtils.blastProtReduction((Entity)llllllllllllllllIllIIIlIlIlIIIII, llllllllllllllllIllIIIlIlIIllIll, explosion);
        return llllllllllllllllIllIIIlIlIIllIll < 0.0 ? 0.0 : llllllllllllllllIllIIIlIlIIllIll;
    }

    public static double anchorDamage(LivingEntity llllllllllllllllIllIIIlIIlllllII, Vec3d llllllllllllllllIllIIIlIIllllIll) {
        DamageCalcUtils.mc.world.removeBlock(new BlockPos(llllllllllllllllIllIIIlIIllllIll), false);
        double llllllllllllllllIllIIIlIIlllllIl = DamageCalcUtils.bedDamage(llllllllllllllllIllIIIlIIlllllII, llllllllllllllllIllIIIlIIllllIll);
        DamageCalcUtils.mc.world.setBlockState(new BlockPos(llllllllllllllllIllIIIlIIllllIll), Blocks.RESPAWN_ANCHOR.getDefaultState());
        return llllllllllllllllIllIIIlIIlllllIl;
    }

    private static double resistanceReduction(LivingEntity llllllllllllllllIllIIIlIIlIlIIII, double llllllllllllllllIllIIIlIIlIIllIl) {
        if (llllllllllllllllIllIIIlIIlIlIIII.hasStatusEffect(StatusEffects.RESISTANCE)) {
            int llllllllllllllllIllIIIlIIlIlIIIl = llllllllllllllllIllIIIlIIlIlIIII.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1;
            llllllllllllllllIllIIIlIIlIIllIl *= 1.0 - (double)llllllllllllllllIllIIIlIIlIlIIIl * 0.2;
        }
        return llllllllllllllllIllIIIlIIlIIllIl < 0.0 ? 0.0 : llllllllllllllllIllIIIlIIlIIllIl;
    }

    public static double bedDamage(LivingEntity llllllllllllllllIllIIIlIlIIIlIII, Vec3d llllllllllllllllIllIIIlIlIIIllIl) {
        if (llllllllllllllllIllIIIlIlIIIlIII instanceof PlayerEntity && ((PlayerEntity)llllllllllllllllIllIIIlIlIIIlIII).abilities.creativeMode) {
            return 0.0;
        }
        double llllllllllllllllIllIIIlIlIIIllII = Math.sqrt(llllllllllllllllIllIIIlIlIIIlIII.squaredDistanceTo(llllllllllllllllIllIIIlIlIIIllIl));
        if (llllllllllllllllIllIIIlIlIIIllII > 10.0) {
            return 0.0;
        }
        double llllllllllllllllIllIIIlIlIIIlIll = Explosion.getExposure((Vec3d)llllllllllllllllIllIIIlIlIIIllIl, (Entity)llllllllllllllllIllIIIlIlIIIlIII);
        double llllllllllllllllIllIIIlIlIIIlIlI = (1.0 - llllllllllllllllIllIIIlIlIIIllII / 10.0) * llllllllllllllllIllIIIlIlIIIlIll;
        double llllllllllllllllIllIIIlIlIIIlIIl = (llllllllllllllllIllIIIlIlIIIlIlI * llllllllllllllllIllIIIlIlIIIlIlI + llllllllllllllllIllIIIlIlIIIlIlI) / 2.0 * 7.0 * 10.0 + 1.0;
        llllllllllllllllIllIIIlIlIIIlIIl = DamageCalcUtils.getDamageForDifficulty(llllllllllllllllIllIIIlIlIIIlIIl);
        llllllllllllllllIllIIIlIlIIIlIIl = DamageCalcUtils.resistanceReduction(llllllllllllllllIllIIIlIlIIIlIII, llllllllllllllllIllIIIlIlIIIlIIl);
        llllllllllllllllIllIIIlIlIIIlIIl = DamageUtil.getDamageLeft((float)((float)llllllllllllllllIllIIIlIlIIIlIIl), (float)llllllllllllllllIllIIIlIlIIIlIII.getArmor(), (float)((float)llllllllllllllllIllIIIlIlIIIlIII.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        ((IExplosion)explosion).set(llllllllllllllllIllIIIlIlIIIllIl, 5.0f, true);
        llllllllllllllllIllIIIlIlIIIlIIl = DamageCalcUtils.blastProtReduction((Entity)llllllllllllllllIllIIIlIlIIIlIII, llllllllllllllllIllIIIlIlIIIlIIl, explosion);
        if (llllllllllllllllIllIIIlIlIIIlIIl < 0.0) {
            llllllllllllllllIllIIIlIlIIIlIIl = 0.0;
        }
        return llllllllllllllllIllIIIlIlIIIlIIl;
    }
}

