/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.item.ItemConvertible;

public final class MyPotion
extends Enum<MyPotion> {
    public static final /* enum */ MyPotion PoisonLong;
    public static final /* enum */ MyPotion HarmingStrong;
    public static final /* enum */ MyPotion TurtleMasterLong;
    public final Item[] ingredients;
    public static final /* enum */ MyPotion JumpBoostLong;
    public static final /* enum */ MyPotion SlownessStrong;
    private static final MyPotion[] $VALUES;
    public static final /* enum */ MyPotion SwiftnessStrong;
    public static final /* enum */ MyPotion TurtleMasterStrong;
    public static final /* enum */ MyPotion Invisibility;
    public static final /* enum */ MyPotion SwiftnessLong;
    public static final /* enum */ MyPotion NightVision;
    public static final /* enum */ MyPotion Slowness;
    public static final /* enum */ MyPotion Weakness;
    public static final /* enum */ MyPotion JumpBoostStrong;
    public static final /* enum */ MyPotion TurtleMaster;
    public static final /* enum */ MyPotion SlowFallingLong;
    public static final /* enum */ MyPotion InvisibilityLong;
    public static final /* enum */ MyPotion RegenerationStrong;
    public static final /* enum */ MyPotion NightVisionLong;
    public final ItemStack potion;
    public static final /* enum */ MyPotion Poison;
    public static final /* enum */ MyPotion WaterBreathingLong;
    public static final /* enum */ MyPotion Swiftness;
    public static final /* enum */ MyPotion Harming;
    public static final /* enum */ MyPotion Strength;
    public static final /* enum */ MyPotion RegenerationLong;
    public static final /* enum */ MyPotion HealingStrong;
    public static final /* enum */ MyPotion WaterBreathing;
    public static final /* enum */ MyPotion StrengthStrong;
    public static final /* enum */ MyPotion StrengthLong;
    public static final /* enum */ MyPotion WeaknessLong;
    public static final /* enum */ MyPotion JumpBoost;
    public static final /* enum */ MyPotion FireResistanceLong;
    public static final /* enum */ MyPotion SlowFalling;
    public static final /* enum */ MyPotion PoisonStrong;
    public static final /* enum */ MyPotion Regeneration;
    public static final /* enum */ MyPotion FireResistance;
    public static final /* enum */ MyPotion SlownessLong;
    public static final /* enum */ MyPotion Healing;

    static {
        Swiftness = new MyPotion(Potions.SWIFTNESS, Items.NETHER_WART, Items.SUGAR);
        SwiftnessLong = new MyPotion(Potions.LONG_SWIFTNESS, Items.NETHER_WART, Items.SUGAR, Items.REDSTONE);
        SwiftnessStrong = new MyPotion(Potions.STRONG_SWIFTNESS, Items.NETHER_WART, Items.SUGAR, Items.GLOWSTONE_DUST);
        Slowness = new MyPotion(Potions.SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE);
        SlownessLong = new MyPotion(Potions.LONG_SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE);
        SlownessStrong = new MyPotion(Potions.STRONG_SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST);
        JumpBoost = new MyPotion(Potions.LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT);
        JumpBoostLong = new MyPotion(Potions.LONG_LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT, Items.REDSTONE);
        JumpBoostStrong = new MyPotion(Potions.STRONG_LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT, Items.GLOWSTONE_DUST);
        Strength = new MyPotion(Potions.STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER);
        StrengthLong = new MyPotion(Potions.LONG_STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER, Items.REDSTONE);
        StrengthStrong = new MyPotion(Potions.STRONG_STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER, Items.GLOWSTONE_DUST);
        Healing = new MyPotion(Potions.HEALING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE);
        HealingStrong = new MyPotion(Potions.STRONG_HEALING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.GLOWSTONE_DUST);
        Harming = new MyPotion(Potions.HARMING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.FERMENTED_SPIDER_EYE);
        HarmingStrong = new MyPotion(Potions.STRONG_HARMING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST);
        Poison = new MyPotion(Potions.POISON, Items.NETHER_WART, Items.SPIDER_EYE);
        PoisonLong = new MyPotion(Potions.LONG_POISON, Items.NETHER_WART, Items.SPIDER_EYE, Items.REDSTONE);
        PoisonStrong = new MyPotion(Potions.STRONG_POISON, Items.NETHER_WART, Items.SPIDER_EYE, Items.GLOWSTONE_DUST);
        Regeneration = new MyPotion(Potions.REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR);
        RegenerationLong = new MyPotion(Potions.LONG_REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR, Items.REDSTONE);
        RegenerationStrong = new MyPotion(Potions.STRONG_REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR, Items.GLOWSTONE_DUST);
        FireResistance = new MyPotion(Potions.FIRE_RESISTANCE, Items.NETHER_WART, Items.MAGMA_CREAM);
        FireResistanceLong = new MyPotion(Potions.LONG_FIRE_RESISTANCE, Items.NETHER_WART, Items.MAGMA_CREAM, Items.REDSTONE);
        WaterBreathing = new MyPotion(Potions.WATER_BREATHING, Items.NETHER_WART, Items.PUFFERFISH);
        WaterBreathingLong = new MyPotion(Potions.LONG_WATER_BREATHING, Items.NETHER_WART, Items.PUFFERFISH, Items.REDSTONE);
        NightVision = new MyPotion(Potions.NIGHT_VISION, Items.NETHER_WART, Items.GOLDEN_CARROT);
        NightVisionLong = new MyPotion(Potions.LONG_NIGHT_VISION, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.REDSTONE);
        Invisibility = new MyPotion(Potions.INVISIBILITY, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE);
        InvisibilityLong = new MyPotion(Potions.LONG_INVISIBILITY, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE);
        TurtleMaster = new MyPotion(Potions.TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET);
        TurtleMasterLong = new MyPotion(Potions.LONG_TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET, Items.REDSTONE);
        TurtleMasterStrong = new MyPotion(Potions.STRONG_TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET, Items.GLOWSTONE_DUST);
        SlowFalling = new MyPotion(Potions.SLOW_FALLING, Items.NETHER_WART, Items.PHANTOM_MEMBRANE);
        SlowFallingLong = new MyPotion(Potions.LONG_SLOW_FALLING, Items.NETHER_WART, Items.PHANTOM_MEMBRANE, Items.REDSTONE);
        Weakness = new MyPotion(Potions.WEAKNESS, Items.FERMENTED_SPIDER_EYE);
        WeaknessLong = new MyPotion(Potions.LONG_WEAKNESS, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE);
        $VALUES = MyPotion.$values();
    }

    private MyPotion(Potion Potion2, Item ... ItemArray) {
        this.potion = PotionUtil.setPotion((ItemStack)new ItemStack((ItemConvertible)Items.POTION), (Potion)Potion2);
        this.ingredients = ItemArray;
    }

    public static MyPotion[] values() {
        return (MyPotion[])$VALUES.clone();
    }

    public static MyPotion valueOf(String string) {
        return Enum.valueOf(MyPotion.class, string);
    }

    private static MyPotion[] $values() {
        return new MyPotion[]{Swiftness, SwiftnessLong, SwiftnessStrong, Slowness, SlownessLong, SlownessStrong, JumpBoost, JumpBoostLong, JumpBoostStrong, Strength, StrengthLong, StrengthStrong, Healing, HealingStrong, Harming, HarmingStrong, Poison, PoisonLong, PoisonStrong, Regeneration, RegenerationLong, RegenerationStrong, FireResistance, FireResistanceLong, WaterBreathing, WaterBreathingLong, NightVision, NightVisionLong, Invisibility, InvisibilityLong, TurtleMaster, TurtleMasterLong, TurtleMasterStrong, SlowFalling, SlowFallingLong, Weakness, WeaknessLong};
    }
}

