/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.ChestSwap;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class AutoArmor
extends Module {
    private int currentFire;
    private final Setting<Boolean> bProtLegs;
    private final Setting<Integer> breakDurability;
    private final Setting<Boolean> ignoreElytra;
    private int currentUnbreaking;
    private int currentBlast;
    private final Setting<Boolean> pause;
    private final Setting<Integer> delay;
    private final SettingGroup sgDelay;
    private int currentProt;
    private int currentProj;
    private float currentToughness;
    private boolean didSkip;
    private int currentArmour;
    private final Setting<Boolean> pauseInInventory;
    private final Setting<Integer> weight;
    private int currentBest;
    private final Setting<Prot> mode;
    private int delayLeft;
    private final Setting<Integer> boomDamage;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> preferMending;
    private final Setting<Boolean> antiBreak;
    private final Setting<Boolean> boomSwitch;
    private int currentMending;
    private final Setting<List<Enchantment>> avoidEnch;
    private final Setting<Integer> switchCooldown;

    private List<Enchantment> setDefaultValue() {
        ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>();
        arrayList.add(Enchantments.BINDING_CURSE);
        arrayList.add(Enchantments.FROST_WALKER);
        return arrayList;
    }

    public AutoArmor() {
        super(Categories.Combat, "auto-armor", "Automatically manages and equips your armor for you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgDelay = this.settings.createGroup("Delay");
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("prioritize").description("Which type of protection to prioritize.").defaultValue(Prot.Protection).build());
        this.bProtLegs = this.sgGeneral.add(new BoolSetting.Builder().name("blast-protection-leggings").description("Prioritizes blast protection on your leggings. Useful for fights with End Crystals.").defaultValue(true).build());
        this.preferMending = this.sgGeneral.add(new BoolSetting.Builder().name("prefer-mending").description("Prefers to equip mending over non-mending armor pieces.").defaultValue(true).build());
        this.weight = this.sgGeneral.add(new IntSetting.Builder().name("weight").description("How much mending is preferred.").defaultValue(2).min(1).max(10).sliderMax(4).build());
        this.avoidEnch = this.sgGeneral.add(new EnchListSetting.Builder().name("avoided-enchantments").description("Enchantments that should be avoided unless it's a last resort.").defaultValue(this.setDefaultValue()).build());
        this.antiBreak = this.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Attempts to stop your armor from being broken.").defaultValue(true).build());
        this.breakDurability = this.sgGeneral.add(new IntSetting.Builder().name("anti-break-durability").description("The durability damaged armor is swapped.").defaultValue(10).max(50).min(2).sliderMax(20).build());
        this.boomSwitch = this.sgGeneral.add(new BoolSetting.Builder().name("switch-for-explosion").description("Switches to Blast Protection automatically if you're going to get hit by an explosion.").defaultValue(false).build());
        this.boomDamage = this.sgGeneral.add(new IntSetting.Builder().name("max-explosion-damage").description("The maximum damage you intake before switching to Blast Protection.").defaultValue(5).min(1).max(18).sliderMax(10).build());
        this.ignoreElytra = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-elytra").description("Will not replace your elytra if you have it equipped.").defaultValue(false).build());
        this.delay = this.sgDelay.add(new IntSetting.Builder().name("delay").description("Delay between pieces being equipped to prevent desync.").defaultValue(1).min(0).max(20).sliderMax(5).build());
        this.switchCooldown = this.sgDelay.add(new IntSetting.Builder().name("switch-cooldown").description("The cooldown between swapping from your current type of Protection to your preferred type of Protection.").defaultValue(20).min(0).max(60).sliderMax(40).build());
        this.pauseInInventory = this.sgDelay.add(new BoolSetting.Builder().name("pause-in-inventory").description("Stops managing armor when you are in your inventory.").defaultValue(false).build());
        this.pause = this.sgDelay.add(new BoolSetting.Builder().name("pause-between-pieces").description("Pauses between equipping each individual piece to prevent desync.").defaultValue(true).build());
        this.delayLeft = this.delay.get();
        this.didSkip = false;
        this.currentMending = 0;
        this.currentToughness = 0.0f;
    }

    private boolean explosionNear() {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof EndCrystalEntity) || !(DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos()) > (double)this.boomDamage.get().intValue())) continue;
            return true;
        }
        if (!this.mc.world.getDimension().isBedWorking()) {
            for (Entity Entity2 : this.mc.world.blockEntities) {
                BlockPos BlockPos2 = Entity2.getPos();
                if (!(Entity2 instanceof BedBlockEntity)) continue;
                Vec3d Vec3d2 = new Vec3d((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ());
                if (!(DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Vec3d2) > (double)this.boomDamage.get().intValue())) continue;
                return true;
            }
        }
        return false;
    }

    private void getCurrentScore(ItemStack ItemStack2) {
        this.currentBest = EnchantmentHelper.getLevel((Enchantment)Prot.access$000(this.mode.get()), (ItemStack)ItemStack2);
        this.currentProt = EnchantmentHelper.getLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)ItemStack2);
        this.currentBlast = EnchantmentHelper.getLevel((Enchantment)Enchantments.BLAST_PROTECTION, (ItemStack)ItemStack2);
        this.currentFire = EnchantmentHelper.getLevel((Enchantment)Enchantments.FIRE_PROTECTION, (ItemStack)ItemStack2);
        this.currentProj = EnchantmentHelper.getLevel((Enchantment)Enchantments.PROJECTILE_PROTECTION, (ItemStack)ItemStack2);
        this.currentArmour = ((ArmorItem)ItemStack2.getItem()).getProtection();
        this.currentToughness = ((ArmorItem)ItemStack2.getItem()).method_26353();
        this.currentUnbreaking = EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)ItemStack2);
        this.currentMending = EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)ItemStack2);
    }

    private int getItemScore(ItemStack ItemStack2) {
        int n = 0;
        if (this.antiBreak.get().booleanValue() && ItemStack2.getMaxDamage() - ItemStack2.getDamage() <= this.breakDurability.get()) {
            return 0;
        }
        for (Enchantment Enchantment2 : this.avoidEnch.get()) {
            if (EnchantmentHelper.getLevel((Enchantment)Enchantment2, (ItemStack)ItemStack2) <= 0) continue;
            return -10;
        }
        n += 4 * (EnchantmentHelper.getLevel((Enchantment)Prot.access$000(this.mode.get()), (ItemStack)ItemStack2) - this.currentBest);
        n += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)ItemStack2) - this.currentProt);
        n += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.BLAST_PROTECTION, (ItemStack)ItemStack2) - this.currentBlast);
        n += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.FIRE_PROTECTION, (ItemStack)ItemStack2) - this.currentFire);
        n += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.PROJECTILE_PROTECTION, (ItemStack)ItemStack2) - this.currentProj);
        n += 2 * (((ArmorItem)ItemStack2.getItem()).getProtection() - this.currentArmour);
        n = (int)((float)n + 2.0f * (((ArmorItem)ItemStack2.getItem()).method_26353() - this.currentToughness));
        n += EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)ItemStack2) - this.currentUnbreaking;
        if (this.preferMending.get().booleanValue() && EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)ItemStack2) - this.currentMending > 0) {
            n += this.weight.get().intValue();
        }
        return n;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.abilities.creativeMode) {
            return;
        }
        if (this.pauseInInventory.get().booleanValue() && this.mc.currentScreen instanceof InventoryScreen) {
            return;
        }
        if (this.boomSwitch.get().booleanValue() && this.mode.get() != Prot.Blast_Protection && this.explosionNear()) {
            this.mode.set(Prot.Blast_Protection);
            this.delayLeft = 0;
            this.didSkip = true;
        }
        if (this.delayLeft > 0) {
            --this.delayLeft;
            return;
        }
        this.delayLeft = this.delay.get();
        Prot prot = this.mode.get();
        if (this.didSkip) {
            this.delayLeft = this.switchCooldown.get();
            this.didSkip = false;
        }
        for (int i = 0; i < 4; ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getArmorStack(i);
            this.currentBest = 0;
            this.currentProt = 0;
            this.currentBlast = 0;
            this.currentFire = 0;
            this.currentProj = 0;
            this.currentArmour = 0;
            this.currentToughness = 0.0f;
            this.currentUnbreaking = 0;
            this.currentMending = 0;
            if ((this.ignoreElytra.get().booleanValue() || Modules.get().isActive(ChestSwap.class)) && ItemStack2.getItem() == Items.ELYTRA || EnchantmentHelper.hasBindingCurse((ItemStack)ItemStack2)) continue;
            if (ItemStack2.getItem() instanceof ArmorItem) {
                if (i == 1 && this.bProtLegs.get().booleanValue()) {
                    this.mode.set(Prot.Blast_Protection);
                }
                this.getCurrentScore(ItemStack2);
            }
            int n = -1;
            int n2 = 0;
            for (int j = 0; j < 36; ++j) {
                int n3;
                ItemStack ItemStack3 = this.mc.player.inventory.getStack(j);
                if (!(ItemStack3.getItem() instanceof ArmorItem) || ((ArmorItem)ItemStack3.getItem()).getSlotType().getEntitySlotId() != i || n2 >= (n3 = this.getItemScore(ItemStack3))) continue;
                n2 = n3;
                n = j;
                if (null == null) continue;
                return;
            }
            if (n > -1) {
                InvUtils.move().from(n).toArmor(i);
                if (this.pause.get().booleanValue()) break;
            }
            this.mode.set(prot);
            if (-3 <= 0) continue;
            return;
        }
    }

    public static final class Prot
    extends Enum<Prot> {
        public static final /* enum */ Prot Protection = new Prot(Enchantments.PROTECTION);
        private final Enchantment enchantment;
        public static final /* enum */ Prot Blast_Protection = new Prot(Enchantments.BLAST_PROTECTION);
        public static final /* enum */ Prot Fire_Protection = new Prot(Enchantments.FIRE_PROTECTION);
        public static final /* enum */ Prot Projectile_Protection = new Prot(Enchantments.PROJECTILE_PROTECTION);
        private static final Prot[] $VALUES = Prot.$values();

        private static Prot[] $values() {
            return new Prot[]{Protection, Blast_Protection, Fire_Protection, Projectile_Protection};
        }

        public static Prot[] values() {
            return (Prot[])$VALUES.clone();
        }

        static Enchantment access$000(Prot prot) {
            return prot.enchantment;
        }

        private Prot(Enchantment Enchantment2) {
            this.enchantment = Enchantment2;
        }

        public static Prot valueOf(String string) {
            return Enum.valueOf(Prot.class, string);
        }
    }
}

