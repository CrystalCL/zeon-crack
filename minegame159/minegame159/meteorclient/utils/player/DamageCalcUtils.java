/*
 * Decompiled with CFR 0.151.
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
    public static MinecraftClient mc;
    private static final Explosion explosion;

    static {
        explosion = new Explosion(null, null, 0.0, 0.0, 0.0, 6.0f, false, Explosion.class_4179.DESTROY);
        mc = MinecraftClient.getInstance();
    }

    public static double crystalDamage(LivingEntity LivingEntity2, Vec3d Vec3d2) {
        if (LivingEntity2 instanceof PlayerEntity && ((PlayerEntity)LivingEntity2).abilities.creativeMode) {
            return 0.0;
        }
        double d = Math.sqrt(LivingEntity2.squaredDistanceTo(Vec3d2));
        if (d > 12.0) {
            return 0.0;
        }
        double d2 = Explosion.getExposure((Vec3d)Vec3d2, (Entity)LivingEntity2);
        double d3 = (1.0 - d / 12.0) * d2;
        double d4 = (d3 * d3 + d3) / 2.0 * 7.0 * 12.0 + 1.0;
        d4 = DamageCalcUtils.getDamageForDifficulty(d4);
        d4 = DamageCalcUtils.resistanceReduction(LivingEntity2, d4);
        d4 = DamageUtil.getDamageLeft((float)((float)d4), (float)LivingEntity2.getArmor(), (float)((float)LivingEntity2.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        ((IExplosion)explosion).set(Vec3d2, 6.0f, false);
        d4 = DamageCalcUtils.blastProtReduction((Entity)LivingEntity2, d4, explosion);
        return d4 < 0.0 ? 0.0 : d4;
    }

    public static double bedDamage(LivingEntity LivingEntity2, Vec3d Vec3d2) {
        if (LivingEntity2 instanceof PlayerEntity && ((PlayerEntity)LivingEntity2).abilities.creativeMode) {
            return 0.0;
        }
        double d = Math.sqrt(LivingEntity2.squaredDistanceTo(Vec3d2));
        if (d > 10.0) {
            return 0.0;
        }
        double d2 = Explosion.getExposure((Vec3d)Vec3d2, (Entity)LivingEntity2);
        double d3 = (1.0 - d / 10.0) * d2;
        double d4 = (d3 * d3 + d3) / 2.0 * 7.0 * 10.0 + 1.0;
        d4 = DamageCalcUtils.getDamageForDifficulty(d4);
        d4 = DamageCalcUtils.resistanceReduction(LivingEntity2, d4);
        d4 = DamageUtil.getDamageLeft((float)((float)d4), (float)LivingEntity2.getArmor(), (float)((float)LivingEntity2.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        ((IExplosion)explosion).set(Vec3d2, 5.0f, true);
        d4 = DamageCalcUtils.blastProtReduction((Entity)LivingEntity2, d4, explosion);
        if (d4 < 0.0) {
            d4 = 0.0;
        }
        return d4;
    }

    public static double anchorDamage(LivingEntity LivingEntity2, Vec3d Vec3d2) {
        DamageCalcUtils.mc.world.removeBlock(new BlockPos(Vec3d2), false);
        double d = DamageCalcUtils.bedDamage(LivingEntity2, Vec3d2);
        DamageCalcUtils.mc.world.setBlockState(new BlockPos(Vec3d2), Blocks.RESPAWN_ANCHOR.getDefaultState());
        return d;
    }

    public static double getSwordDamage(PlayerEntity PlayerEntity2, boolean bl) {
        int n;
        double d = 0.0;
        if (bl) {
            if (PlayerEntity2.getActiveItem().getItem() == Items.DIAMOND_SWORD) {
                d += 7.0;
            } else if (PlayerEntity2.getActiveItem().getItem() == Items.GOLDEN_SWORD) {
                d += 4.0;
            } else if (PlayerEntity2.getActiveItem().getItem() == Items.IRON_SWORD) {
                d += 6.0;
            } else if (PlayerEntity2.getActiveItem().getItem() == Items.STONE_SWORD) {
                d += 5.0;
            } else if (PlayerEntity2.getActiveItem().getItem() == Items.WOODEN_SWORD) {
                d += 4.0;
            }
            d *= 1.5;
        }
        if (PlayerEntity2.getActiveItem().getEnchantments() != null && EnchantmentHelper.get((ItemStack)PlayerEntity2.getActiveItem()).containsKey(Enchantments.SHARPNESS)) {
            n = EnchantmentHelper.getLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)PlayerEntity2.getActiveItem());
            d += 0.5 * (double)n + 0.5;
        }
        if (PlayerEntity2.getActiveStatusEffects().containsKey(StatusEffects.STRENGTH)) {
            n = Objects.requireNonNull(PlayerEntity2.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier() + 1;
            d += (double)(3 * n);
        }
        d = DamageCalcUtils.resistanceReduction((LivingEntity)PlayerEntity2, d);
        d = DamageUtil.getDamageLeft((float)((float)d), (float)PlayerEntity2.getArmor(), (float)((float)PlayerEntity2.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue()));
        return (d = DamageCalcUtils.normalProtReduction((Entity)PlayerEntity2, d)) < 0.0 ? 0.0 : d;
    }

    private static double blastProtReduction(Entity Entity2, double d, Explosion Explosion2) {
        int n = EnchantmentHelper.getProtectionAmount((Iterable)Entity2.getArmorItems(), (DamageSource)DamageSource.explosion((Explosion)Explosion2));
        if (n > 20) {
            n = 20;
        }
        return (d *= 1.0 - (double)n / 25.0) < 0.0 ? 0.0 : d;
    }

    private static double getDamageForDifficulty(double d) {
        switch (1.$SwitchMap$net$minecraft$world$Difficulty[DamageCalcUtils.mc.world.getDifficulty().ordinal()]) {
            case 1: {
                return 0.0;
            }
            case 2: {
                return Math.min(d / 2.0 + 1.0, d);
            }
            case 3: {
                return d * 3.0 / 2.0;
            }
        }
        return d;
    }

    private static double resistanceReduction(LivingEntity LivingEntity2, double d) {
        if (LivingEntity2.hasStatusEffect(StatusEffects.RESISTANCE)) {
            int n = LivingEntity2.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1;
            d *= 1.0 - (double)n * 0.2;
        }
        return d < 0.0 ? 0.0 : d;
    }

    private static double normalProtReduction(Entity Entity2, double d) {
        int n = EnchantmentHelper.getProtectionAmount((Iterable)Entity2.getArmorItems(), (DamageSource)DamageSource.GENERIC);
        if (n > 20) {
            n = 20;
        }
        return (d *= 1.0 - (double)n / 25.0) < 0.0 ? 0.0 : d;
    }
}

