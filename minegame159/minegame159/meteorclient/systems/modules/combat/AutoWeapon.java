/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.AttackEntityEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.enchantment.EnchantmentHelper;

public class AutoWeapon
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Integer> threshold;
    private final Setting<Boolean> antiBreak;
    private final Setting<Weapon> weapon;

    private int getBestWeapon() {
        int n;
        int n2 = this.mc.player.inventory.selectedSlot;
        int n3 = this.mc.player.inventory.selectedSlot;
        int n4 = this.mc.player.inventory.selectedSlot;
        double d = 0.0;
        double d2 = 0.0;
        for (n = 0; n < 9; ++n) {
            double d3;
            if (!(this.mc.player.inventory.getStack(n).getItem() instanceof SwordItem) || this.antiBreak.get().booleanValue() && this.mc.player.inventory.getStack(n).getMaxDamage() - this.mc.player.inventory.getStack(n).getDamage() <= 10 || !((d3 = (double)(((SwordItem)this.mc.player.inventory.getStack(n).getItem()).getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage((ItemStack)this.mc.player.inventory.getStack(n), (EntityGroup)EntityGroup.DEFAULT) + 2.0f)) > d)) continue;
            d = d3;
            n2 = n;
            if (4 >= -1) continue;
            return 0;
        }
        for (n = 0; n < 9; ++n) {
            double d4;
            if (!(this.mc.player.inventory.getStack(n).getItem() instanceof AxeItem) || this.antiBreak.get().booleanValue() && this.mc.player.inventory.getStack(n).getMaxDamage() - this.mc.player.inventory.getStack(n).getDamage() <= 10 || !((d4 = (double)(((AxeItem)this.mc.player.inventory.getStack(n).getItem()).getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage((ItemStack)this.mc.player.inventory.getStack(n), (EntityGroup)EntityGroup.DEFAULT) + 2.0f)) > d2)) continue;
            d2 = d4;
            n3 = n;
            if (-1 <= 4) continue;
            return 0;
        }
        if (this.weapon.get() == Weapon.Sword && (double)this.threshold.get().intValue() > d2 - d) {
            n4 = n2;
        } else if (this.weapon.get() == Weapon.Axe && (double)this.threshold.get().intValue() > d - d2) {
            n4 = n3;
        } else if (this.weapon.get() == Weapon.Sword && (double)this.threshold.get().intValue() < d2 - d) {
            n4 = n3;
        } else if (this.weapon.get() == Weapon.Axe && (double)this.threshold.get().intValue() < d - d2) {
            n4 = n2;
        }
        return n4;
    }

    @EventHandler
    private void onAttack(AttackEntityEvent attackEntityEvent) {
        this.mc.player.inventory.selectedSlot = this.getBestWeapon();
    }

    public AutoWeapon() {
        super(Categories.Combat, "auto-weapon", "Finds the best weapon to use in your hotbar.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.weapon = this.sgGeneral.add(new EnumSetting.Builder().name("Weapon").description("What type of weapon to use.").defaultValue(Weapon.Sword).build());
        this.threshold = this.sgGeneral.add(new IntSetting.Builder().name("threshold").description("If the non-preferred weapon produces this much damage this will favor it over your preferred weapon.").defaultValue(4).build());
        this.antiBreak = this.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents you from breaking your weapon.").defaultValue(false).build());
    }

    public static final class Weapon
    extends Enum<Weapon> {
        public static final /* enum */ Weapon Sword = new Weapon();
        private static final Weapon[] $VALUES;
        public static final /* enum */ Weapon Axe;

        private static Weapon[] $values() {
            return new Weapon[]{Sword, Axe};
        }

        static {
            Axe = new Weapon();
            $VALUES = Weapon.$values();
        }

        public static Weapon valueOf(String string) {
            return Enum.valueOf(Weapon.class, string);
        }

        public static Weapon[] values() {
            return (Weapon[])$VALUES.clone();
        }
    }
}

