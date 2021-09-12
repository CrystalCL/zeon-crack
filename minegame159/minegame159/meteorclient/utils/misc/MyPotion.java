/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1842;
import net.minecraft.class_1844;
import net.minecraft.class_1847;
import net.minecraft.class_1935;

public final class MyPotion
extends Enum<MyPotion> {
    public static final /* enum */ MyPotion PoisonLong;
    public static final /* enum */ MyPotion HarmingStrong;
    public static final /* enum */ MyPotion TurtleMasterLong;
    public final class_1792[] ingredients;
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
    public final class_1799 potion;
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
        Swiftness = new MyPotion(class_1847.field_9005, class_1802.field_8790, class_1802.field_8479);
        SwiftnessLong = new MyPotion(class_1847.field_8983, class_1802.field_8790, class_1802.field_8479, class_1802.field_8725);
        SwiftnessStrong = new MyPotion(class_1847.field_8966, class_1802.field_8790, class_1802.field_8479, class_1802.field_8601);
        Slowness = new MyPotion(class_1847.field_8996, class_1802.field_8790, class_1802.field_8479, class_1802.field_8711);
        SlownessLong = new MyPotion(class_1847.field_8989, class_1802.field_8790, class_1802.field_8479, class_1802.field_8711, class_1802.field_8725);
        SlownessStrong = new MyPotion(class_1847.field_8976, class_1802.field_8790, class_1802.field_8479, class_1802.field_8711, class_1802.field_8601);
        JumpBoost = new MyPotion(class_1847.field_8979, class_1802.field_8790, class_1802.field_8073);
        JumpBoostLong = new MyPotion(class_1847.field_8971, class_1802.field_8790, class_1802.field_8073, class_1802.field_8725);
        JumpBoostStrong = new MyPotion(class_1847.field_8998, class_1802.field_8790, class_1802.field_8073, class_1802.field_8601);
        Strength = new MyPotion(class_1847.field_8978, class_1802.field_8790, class_1802.field_8183);
        StrengthLong = new MyPotion(class_1847.field_8965, class_1802.field_8790, class_1802.field_8183, class_1802.field_8725);
        StrengthStrong = new MyPotion(class_1847.field_8993, class_1802.field_8790, class_1802.field_8183, class_1802.field_8601);
        Healing = new MyPotion(class_1847.field_8963, class_1802.field_8790, class_1802.field_8597);
        HealingStrong = new MyPotion(class_1847.field_8980, class_1802.field_8790, class_1802.field_8597, class_1802.field_8601);
        Harming = new MyPotion(class_1847.field_9004, class_1802.field_8790, class_1802.field_8597, class_1802.field_8711);
        HarmingStrong = new MyPotion(class_1847.field_8973, class_1802.field_8790, class_1802.field_8597, class_1802.field_8711, class_1802.field_8601);
        Poison = new MyPotion(class_1847.field_8982, class_1802.field_8790, class_1802.field_8680);
        PoisonLong = new MyPotion(class_1847.field_9002, class_1802.field_8790, class_1802.field_8680, class_1802.field_8725);
        PoisonStrong = new MyPotion(class_1847.field_8972, class_1802.field_8790, class_1802.field_8680, class_1802.field_8601);
        Regeneration = new MyPotion(class_1847.field_8986, class_1802.field_8790, class_1802.field_8070);
        RegenerationLong = new MyPotion(class_1847.field_9003, class_1802.field_8790, class_1802.field_8070, class_1802.field_8725);
        RegenerationStrong = new MyPotion(class_1847.field_8992, class_1802.field_8790, class_1802.field_8070, class_1802.field_8601);
        FireResistance = new MyPotion(class_1847.field_8987, class_1802.field_8790, class_1802.field_8135);
        FireResistanceLong = new MyPotion(class_1847.field_8969, class_1802.field_8790, class_1802.field_8135, class_1802.field_8725);
        WaterBreathing = new MyPotion(class_1847.field_8994, class_1802.field_8790, class_1802.field_8323);
        WaterBreathingLong = new MyPotion(class_1847.field_9001, class_1802.field_8790, class_1802.field_8323, class_1802.field_8725);
        NightVision = new MyPotion(class_1847.field_8968, class_1802.field_8790, class_1802.field_8071);
        NightVisionLong = new MyPotion(class_1847.field_8981, class_1802.field_8790, class_1802.field_8071, class_1802.field_8725);
        Invisibility = new MyPotion(class_1847.field_8997, class_1802.field_8790, class_1802.field_8071, class_1802.field_8711);
        InvisibilityLong = new MyPotion(class_1847.field_9000, class_1802.field_8790, class_1802.field_8071, class_1802.field_8711, class_1802.field_8725);
        TurtleMaster = new MyPotion(class_1847.field_8990, class_1802.field_8790, class_1802.field_8090);
        TurtleMasterLong = new MyPotion(class_1847.field_8988, class_1802.field_8790, class_1802.field_8090, class_1802.field_8725);
        TurtleMasterStrong = new MyPotion(class_1847.field_8977, class_1802.field_8790, class_1802.field_8090, class_1802.field_8601);
        SlowFalling = new MyPotion(class_1847.field_8974, class_1802.field_8790, class_1802.field_8614);
        SlowFallingLong = new MyPotion(class_1847.field_8964, class_1802.field_8790, class_1802.field_8614, class_1802.field_8725);
        Weakness = new MyPotion(class_1847.field_8975, class_1802.field_8711);
        WeaknessLong = new MyPotion(class_1847.field_8970, class_1802.field_8711, class_1802.field_8725);
        $VALUES = MyPotion.$values();
    }

    private MyPotion(class_1842 class_18422, class_1792 ... class_1792Array) {
        this.potion = class_1844.method_8061((class_1799)new class_1799((class_1935)class_1802.field_8574), (class_1842)class_18422);
        this.ingredients = class_1792Array;
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

