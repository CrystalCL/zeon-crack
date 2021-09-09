/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityGroup
 *  net.minecraft.item.AxeItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.SwordItem
 *  net.minecraft.enchantment.EnchantmentHelper
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
    private final /* synthetic */ Setting<Integer> threshold;
    private final /* synthetic */ Setting<Boolean> antiBreak;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Weapon> weapon;

    @EventHandler
    private void onAttack(AttackEntityEvent lllllllllllllllllllIIlllllIIIIIl) {
        AutoWeapon lllllllllllllllllllIIlllllIIIIlI;
        lllllllllllllllllllIIlllllIIIIlI.mc.player.inventory.selectedSlot = lllllllllllllllllllIIlllllIIIIlI.getBestWeapon();
    }

    public AutoWeapon() {
        super(Categories.Combat, "auto-weapon", "Finds the best weapon to use in your hotbar.");
        AutoWeapon lllllllllllllllllllIIlllllIIIllI;
        lllllllllllllllllllIIlllllIIIllI.sgGeneral = lllllllllllllllllllIIlllllIIIllI.settings.getDefaultGroup();
        lllllllllllllllllllIIlllllIIIllI.weapon = lllllllllllllllllllIIlllllIIIllI.sgGeneral.add(new EnumSetting.Builder().name("Weapon").description("What type of weapon to use.").defaultValue(Weapon.Sword).build());
        lllllllllllllllllllIIlllllIIIllI.threshold = lllllllllllllllllllIIlllllIIIllI.sgGeneral.add(new IntSetting.Builder().name("threshold").description("If the non-preferred weapon produces this much damage this will favor it over your preferred weapon.").defaultValue(4).build());
        lllllllllllllllllllIIlllllIIIllI.antiBreak = lllllllllllllllllllIIlllllIIIllI.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents you from breaking your weapon.").defaultValue(false).build());
    }

    private int getBestWeapon() {
        AutoWeapon lllllllllllllllllllIIllllIIIllII;
        int lllllllllllllllllllIIllllIIIlIlI = lllllllllllllllllllIIllllIIIllII.mc.player.inventory.selectedSlot;
        int lllllllllllllllllllIIllllIIIlIII = lllllllllllllllllllIIllllIIIllII.mc.player.inventory.selectedSlot;
        int lllllllllllllllllllIIllllIIIIllI = lllllllllllllllllllIIllllIIIllII.mc.player.inventory.selectedSlot;
        double lllllllllllllllllllIIllllIIIIlII = 0.0;
        double lllllllllllllllllllIIllllIIIIIll = 0.0;
        for (int lllllllllllllllllllIIllllIIlIIlI = 0; lllllllllllllllllllIIllllIIlIIlI < 9; ++lllllllllllllllllllIIllllIIlIIlI) {
            double lllllllllllllllllllIIllllIIlIlII;
            if (!(lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIlIIlI).getItem() instanceof SwordItem) || lllllllllllllllllllIIllllIIIllII.antiBreak.get().booleanValue() && lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIlIIlI).getMaxDamage() - lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIlIIlI).getDamage() <= 10 || !((lllllllllllllllllllIIllllIIlIlII = (double)(((SwordItem)lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIlIIlI).getItem()).getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage((ItemStack)lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIlIIlI), (EntityGroup)EntityGroup.DEFAULT) + 2.0f)) > lllllllllllllllllllIIllllIIIIlII)) continue;
            lllllllllllllllllllIIllllIIIIlII = lllllllllllllllllllIIllllIIlIlII;
            lllllllllllllllllllIIllllIIIlIlI = lllllllllllllllllllIIllllIIlIIlI;
        }
        for (int lllllllllllllllllllIIllllIIIlllI = 0; lllllllllllllllllllIIllllIIIlllI < 9; ++lllllllllllllllllllIIllllIIIlllI) {
            double lllllllllllllllllllIIllllIIlIIII;
            if (!(lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIIlllI).getItem() instanceof AxeItem) || lllllllllllllllllllIIllllIIIllII.antiBreak.get().booleanValue() && lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIIlllI).getMaxDamage() - lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIIlllI).getDamage() <= 10 || !((lllllllllllllllllllIIllllIIlIIII = (double)(((AxeItem)lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIIlllI).getItem()).getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage((ItemStack)lllllllllllllllllllIIllllIIIllII.mc.player.inventory.getStack(lllllllllllllllllllIIllllIIIlllI), (EntityGroup)EntityGroup.DEFAULT) + 2.0f)) > lllllllllllllllllllIIllllIIIIIll)) continue;
            lllllllllllllllllllIIllllIIIIIll = lllllllllllllllllllIIllllIIlIIII;
            lllllllllllllllllllIIllllIIIlIII = lllllllllllllllllllIIllllIIIlllI;
        }
        if (lllllllllllllllllllIIllllIIIllII.weapon.get() == Weapon.Sword && (double)lllllllllllllllllllIIllllIIIllII.threshold.get().intValue() > lllllllllllllllllllIIllllIIIIIll - lllllllllllllllllllIIllllIIIIlII) {
            lllllllllllllllllllIIllllIIIIllI = lllllllllllllllllllIIllllIIIlIlI;
        } else if (lllllllllllllllllllIIllllIIIllII.weapon.get() == Weapon.Axe && (double)lllllllllllllllllllIIllllIIIllII.threshold.get().intValue() > lllllllllllllllllllIIllllIIIIlII - lllllllllllllllllllIIllllIIIIIll) {
            lllllllllllllllllllIIllllIIIIllI = lllllllllllllllllllIIllllIIIlIII;
        } else if (lllllllllllllllllllIIllllIIIllII.weapon.get() == Weapon.Sword && (double)lllllllllllllllllllIIllllIIIllII.threshold.get().intValue() < lllllllllllllllllllIIllllIIIIIll - lllllllllllllllllllIIllllIIIIlII) {
            lllllllllllllllllllIIllllIIIIllI = lllllllllllllllllllIIllllIIIlIII;
        } else if (lllllllllllllllllllIIllllIIIllII.weapon.get() == Weapon.Axe && (double)lllllllllllllllllllIIllllIIIllII.threshold.get().intValue() < lllllllllllllllllllIIllllIIIIlII - lllllllllllllllllllIIllllIIIIIll) {
            lllllllllllllllllllIIllllIIIIllI = lllllllllllllllllllIIllllIIIlIlI;
        }
        return lllllllllllllllllllIIllllIIIIllI;
    }

    public static final class Weapon
    extends Enum<Weapon> {
        private static final /* synthetic */ Weapon[] $VALUES;
        public static final /* synthetic */ /* enum */ Weapon Axe;
        public static final /* synthetic */ /* enum */ Weapon Sword;

        private static /* synthetic */ Weapon[] $values() {
            return new Weapon[]{Sword, Axe};
        }

        static {
            Sword = new Weapon();
            Axe = new Weapon();
            $VALUES = Weapon.$values();
        }

        private Weapon() {
            Weapon llIlIIllIllIIII;
        }

        public static Weapon[] values() {
            return (Weapon[])$VALUES.clone();
        }

        public static Weapon valueOf(String llIlIIllIllIlIl) {
            return Enum.valueOf(Weapon.class, llIlIIllIllIlIl);
        }
    }
}

