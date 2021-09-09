/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.item.ArmorItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.entity.BedBlockEntity
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class AutoArmor
extends Module {
    private /* synthetic */ int currentBlast;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> weight;
    private final /* synthetic */ Setting<Boolean> bProtLegs;
    private final /* synthetic */ Setting<Boolean> boomSwitch;
    private /* synthetic */ int currentBest;
    private final /* synthetic */ SettingGroup sgDelay;
    private final /* synthetic */ Setting<Boolean> antiBreak;
    private /* synthetic */ int currentProt;
    private final /* synthetic */ Setting<Integer> breakDurability;
    private final /* synthetic */ Setting<Integer> delay;
    private /* synthetic */ int currentProj;
    private final /* synthetic */ Setting<Integer> boomDamage;
    private final /* synthetic */ Setting<Integer> switchCooldown;
    private final /* synthetic */ Setting<Boolean> pause;
    private final /* synthetic */ Setting<Prot> mode;
    private /* synthetic */ float currentToughness;
    private /* synthetic */ int delayLeft;
    private /* synthetic */ int currentFire;
    private /* synthetic */ int currentUnbreaking;
    private final /* synthetic */ Setting<Boolean> pauseInInventory;
    private /* synthetic */ int currentArmour;
    private /* synthetic */ int currentMending;
    private final /* synthetic */ Setting<List<Enchantment>> avoidEnch;
    private final /* synthetic */ Setting<Boolean> ignoreElytra;
    private final /* synthetic */ Setting<Boolean> preferMending;
    private /* synthetic */ boolean didSkip;

    @EventHandler
    private void onTick(TickEvent.Post lllIIlIIlIIlll) {
        AutoArmor lllIIlIIlIlIII;
        if (lllIIlIIlIlIII.mc.player.abilities.creativeMode) {
            return;
        }
        if (lllIIlIIlIlIII.pauseInInventory.get().booleanValue() && lllIIlIIlIlIII.mc.currentScreen instanceof InventoryScreen) {
            return;
        }
        if (lllIIlIIlIlIII.boomSwitch.get().booleanValue() && lllIIlIIlIlIII.mode.get() != Prot.Blast_Protection && lllIIlIIlIlIII.explosionNear()) {
            lllIIlIIlIlIII.mode.set(Prot.Blast_Protection);
            lllIIlIIlIlIII.delayLeft = 0;
            lllIIlIIlIlIII.didSkip = true;
        }
        if (lllIIlIIlIlIII.delayLeft > 0) {
            --lllIIlIIlIlIII.delayLeft;
            return;
        }
        lllIIlIIlIlIII.delayLeft = lllIIlIIlIlIII.delay.get();
        Prot lllIIlIIlIIllI = lllIIlIIlIlIII.mode.get();
        if (lllIIlIIlIlIII.didSkip) {
            lllIIlIIlIlIII.delayLeft = lllIIlIIlIlIII.switchCooldown.get();
            lllIIlIIlIlIII.didSkip = false;
        }
        for (int lllIIlIIlIlIIl = 0; lllIIlIIlIlIIl < 4; ++lllIIlIIlIlIIl) {
            ItemStack lllIIlIIlIlIlI = lllIIlIIlIlIII.mc.player.inventory.getArmorStack(lllIIlIIlIlIIl);
            lllIIlIIlIlIII.currentBest = 0;
            lllIIlIIlIlIII.currentProt = 0;
            lllIIlIIlIlIII.currentBlast = 0;
            lllIIlIIlIlIII.currentFire = 0;
            lllIIlIIlIlIII.currentProj = 0;
            lllIIlIIlIlIII.currentArmour = 0;
            lllIIlIIlIlIII.currentToughness = 0.0f;
            lllIIlIIlIlIII.currentUnbreaking = 0;
            lllIIlIIlIlIII.currentMending = 0;
            if ((lllIIlIIlIlIII.ignoreElytra.get().booleanValue() || Modules.get().isActive(ChestSwap.class)) && lllIIlIIlIlIlI.getItem() == Items.ELYTRA || EnchantmentHelper.hasBindingCurse((ItemStack)lllIIlIIlIlIlI)) continue;
            if (lllIIlIIlIlIlI.getItem() instanceof ArmorItem) {
                if (lllIIlIIlIlIIl == 1 && lllIIlIIlIlIII.bProtLegs.get().booleanValue()) {
                    lllIIlIIlIlIII.mode.set(Prot.Blast_Protection);
                }
                lllIIlIIlIlIII.getCurrentScore(lllIIlIIlIlIlI);
            }
            int lllIIlIIlIllII = -1;
            int lllIIlIIlIlIll = 0;
            for (int lllIIlIIlIllIl = 0; lllIIlIIlIllIl < 36; ++lllIIlIIlIllIl) {
                int lllIIlIIlIllll;
                ItemStack lllIIlIIlIlllI = lllIIlIIlIlIII.mc.player.inventory.getStack(lllIIlIIlIllIl);
                if (!(lllIIlIIlIlllI.getItem() instanceof ArmorItem) || ((ArmorItem)lllIIlIIlIlllI.getItem()).getSlotType().getEntitySlotId() != lllIIlIIlIlIIl || lllIIlIIlIlIll >= (lllIIlIIlIllll = lllIIlIIlIlIII.getItemScore(lllIIlIIlIlllI))) continue;
                lllIIlIIlIlIll = lllIIlIIlIllll;
                lllIIlIIlIllII = lllIIlIIlIllIl;
            }
            if (lllIIlIIlIllII > -1) {
                InvUtils.move().from(lllIIlIIlIllII).toArmor(lllIIlIIlIlIIl);
                if (lllIIlIIlIlIII.pause.get().booleanValue()) break;
            }
            lllIIlIIlIlIII.mode.set(lllIIlIIlIIllI);
        }
    }

    private boolean explosionNear() {
        AutoArmor lllIIlIIIIIIIl;
        for (Entity lllIIlIIIIIlII : lllIIlIIIIIIIl.mc.world.getEntities()) {
            if (!(lllIIlIIIIIlII instanceof EndCrystalEntity) || !(DamageCalcUtils.crystalDamage((LivingEntity)lllIIlIIIIIIIl.mc.player, lllIIlIIIIIlII.getPos()) > (double)lllIIlIIIIIIIl.boomDamage.get().intValue())) continue;
            return true;
        }
        if (!lllIIlIIIIIIIl.mc.world.getDimension().isBedWorking()) {
            for (BlockEntity lllIIlIIIIIIlI : lllIIlIIIIIIIl.mc.world.blockEntities) {
                BlockPos lllIIlIIIIIIll = lllIIlIIIIIIlI.getPos();
                if (!(lllIIlIIIIIIlI instanceof BedBlockEntity)) continue;
                Vec3d Vec3d2 = new Vec3d((double)lllIIlIIIIIIll.getX(), (double)lllIIlIIIIIIll.getY(), (double)lllIIlIIIIIIll.getZ());
                if (!(DamageCalcUtils.bedDamage((LivingEntity)lllIIlIIIIIIIl.mc.player, Vec3d2) > (double)lllIIlIIIIIIIl.boomDamage.get().intValue())) continue;
                return true;
            }
        }
        return false;
    }

    private int getItemScore(ItemStack lllIIlIIIlIlIl) {
        AutoArmor lllIIlIIIlIllI;
        int lllIIlIIIlIlII = 0;
        if (lllIIlIIIlIllI.antiBreak.get().booleanValue() && lllIIlIIIlIlIl.getMaxDamage() - lllIIlIIIlIlIl.getDamage() <= lllIIlIIIlIllI.breakDurability.get()) {
            return 0;
        }
        for (Enchantment lllIIlIIIlIlll : lllIIlIIIlIllI.avoidEnch.get()) {
            if (EnchantmentHelper.getLevel((Enchantment)lllIIlIIIlIlll, (ItemStack)lllIIlIIIlIlIl) <= 0) continue;
            return -10;
        }
        lllIIlIIIlIlII += 4 * (EnchantmentHelper.getLevel((Enchantment)lllIIlIIIlIllI.mode.get().enchantment, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentBest);
        lllIIlIIIlIlII += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentProt);
        lllIIlIIIlIlII += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.BLAST_PROTECTION, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentBlast);
        lllIIlIIIlIlII += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.FIRE_PROTECTION, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentFire);
        lllIIlIIIlIlII += 2 * (EnchantmentHelper.getLevel((Enchantment)Enchantments.PROJECTILE_PROTECTION, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentProj);
        lllIIlIIIlIlII += 2 * (((ArmorItem)lllIIlIIIlIlIl.getItem()).getProtection() - lllIIlIIIlIllI.currentArmour);
        lllIIlIIIlIlII = (int)((float)lllIIlIIIlIlII + 2.0f * (((ArmorItem)lllIIlIIIlIlIl.getItem()).method_26353() - lllIIlIIIlIllI.currentToughness));
        lllIIlIIIlIlII += EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentUnbreaking;
        if (lllIIlIIIlIllI.preferMending.get().booleanValue() && EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)lllIIlIIIlIlIl) - lllIIlIIIlIllI.currentMending > 0) {
            lllIIlIIIlIlII += lllIIlIIIlIllI.weight.get().intValue();
        }
        return lllIIlIIIlIlII;
    }

    private List<Enchantment> setDefaultValue() {
        ArrayList<Enchantment> lllIIIlllllIlI = new ArrayList<Enchantment>();
        lllIIIlllllIlI.add(Enchantments.BINDING_CURSE);
        lllIIIlllllIlI.add(Enchantments.FROST_WALKER);
        return lllIIIlllllIlI;
    }

    private void getCurrentScore(ItemStack lllIIlIIIIlIll) {
        AutoArmor lllIIlIIIIllII;
        lllIIlIIIIllII.currentBest = EnchantmentHelper.getLevel((Enchantment)lllIIlIIIIllII.mode.get().enchantment, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentProt = EnchantmentHelper.getLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentBlast = EnchantmentHelper.getLevel((Enchantment)Enchantments.BLAST_PROTECTION, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentFire = EnchantmentHelper.getLevel((Enchantment)Enchantments.FIRE_PROTECTION, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentProj = EnchantmentHelper.getLevel((Enchantment)Enchantments.PROJECTILE_PROTECTION, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentArmour = ((ArmorItem)lllIIlIIIIlIll.getItem()).getProtection();
        lllIIlIIIIllII.currentToughness = ((ArmorItem)lllIIlIIIIlIll.getItem()).method_26353();
        lllIIlIIIIllII.currentUnbreaking = EnchantmentHelper.getLevel((Enchantment)Enchantments.UNBREAKING, (ItemStack)lllIIlIIIIlIll);
        lllIIlIIIIllII.currentMending = EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)lllIIlIIIIlIll);
    }

    public AutoArmor() {
        super(Categories.Combat, "auto-armor", "Automatically manages and equips your armor for you.");
        AutoArmor lllIIlIIlllIIl;
        lllIIlIIlllIIl.sgGeneral = lllIIlIIlllIIl.settings.getDefaultGroup();
        lllIIlIIlllIIl.sgDelay = lllIIlIIlllIIl.settings.createGroup("Delay");
        lllIIlIIlllIIl.mode = lllIIlIIlllIIl.sgGeneral.add(new EnumSetting.Builder().name("prioritize").description("Which type of protection to prioritize.").defaultValue(Prot.Protection).build());
        lllIIlIIlllIIl.bProtLegs = lllIIlIIlllIIl.sgGeneral.add(new BoolSetting.Builder().name("blast-protection-leggings").description("Prioritizes blast protection on your leggings. Useful for fights with End Crystals.").defaultValue(true).build());
        lllIIlIIlllIIl.preferMending = lllIIlIIlllIIl.sgGeneral.add(new BoolSetting.Builder().name("prefer-mending").description("Prefers to equip mending over non-mending armor pieces.").defaultValue(true).build());
        lllIIlIIlllIIl.weight = lllIIlIIlllIIl.sgGeneral.add(new IntSetting.Builder().name("weight").description("How much mending is preferred.").defaultValue(2).min(1).max(10).sliderMax(4).build());
        lllIIlIIlllIIl.avoidEnch = lllIIlIIlllIIl.sgGeneral.add(new EnchListSetting.Builder().name("avoided-enchantments").description("Enchantments that should be avoided unless it's a last resort.").defaultValue(lllIIlIIlllIIl.setDefaultValue()).build());
        lllIIlIIlllIIl.antiBreak = lllIIlIIlllIIl.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Attempts to stop your armor from being broken.").defaultValue(true).build());
        lllIIlIIlllIIl.breakDurability = lllIIlIIlllIIl.sgGeneral.add(new IntSetting.Builder().name("anti-break-durability").description("The durability damaged armor is swapped.").defaultValue(10).max(50).min(2).sliderMax(20).build());
        lllIIlIIlllIIl.boomSwitch = lllIIlIIlllIIl.sgGeneral.add(new BoolSetting.Builder().name("switch-for-explosion").description("Switches to Blast Protection automatically if you're going to get hit by an explosion.").defaultValue(false).build());
        lllIIlIIlllIIl.boomDamage = lllIIlIIlllIIl.sgGeneral.add(new IntSetting.Builder().name("max-explosion-damage").description("The maximum damage you intake before switching to Blast Protection.").defaultValue(5).min(1).max(18).sliderMax(10).build());
        lllIIlIIlllIIl.ignoreElytra = lllIIlIIlllIIl.sgGeneral.add(new BoolSetting.Builder().name("ignore-elytra").description("Will not replace your elytra if you have it equipped.").defaultValue(false).build());
        lllIIlIIlllIIl.delay = lllIIlIIlllIIl.sgDelay.add(new IntSetting.Builder().name("delay").description("Delay between pieces being equipped to prevent desync.").defaultValue(1).min(0).max(20).sliderMax(5).build());
        lllIIlIIlllIIl.switchCooldown = lllIIlIIlllIIl.sgDelay.add(new IntSetting.Builder().name("switch-cooldown").description("The cooldown between swapping from your current type of Protection to your preferred type of Protection.").defaultValue(20).min(0).max(60).sliderMax(40).build());
        lllIIlIIlllIIl.pauseInInventory = lllIIlIIlllIIl.sgDelay.add(new BoolSetting.Builder().name("pause-in-inventory").description("Stops managing armor when you are in your inventory.").defaultValue(false).build());
        lllIIlIIlllIIl.pause = lllIIlIIlllIIl.sgDelay.add(new BoolSetting.Builder().name("pause-between-pieces").description("Pauses between equipping each individual piece to prevent desync.").defaultValue(true).build());
        lllIIlIIlllIIl.delayLeft = lllIIlIIlllIIl.delay.get();
        lllIIlIIlllIIl.didSkip = false;
        lllIIlIIlllIIl.currentMending = 0;
        lllIIlIIlllIIl.currentToughness = 0.0f;
    }

    public static final class Prot
    extends Enum<Prot> {
        public static final /* synthetic */ /* enum */ Prot Projectile_Protection;
        public static final /* synthetic */ /* enum */ Prot Blast_Protection;
        private final /* synthetic */ Enchantment enchantment;
        private static final /* synthetic */ Prot[] $VALUES;
        public static final /* synthetic */ /* enum */ Prot Protection;
        public static final /* synthetic */ /* enum */ Prot Fire_Protection;

        private Prot(Enchantment llllllllllllllllIlIlllIIlIIIlIlI) {
            Prot llllllllllllllllIlIlllIIlIIIllll;
            llllllllllllllllIlIlllIIlIIIllll.enchantment = llllllllllllllllIlIlllIIlIIIlIlI;
        }

        private static /* synthetic */ Prot[] $values() {
            return new Prot[]{Protection, Blast_Protection, Fire_Protection, Projectile_Protection};
        }

        public static Prot valueOf(String llllllllllllllllIlIlllIIlIIlIlIl) {
            return Enum.valueOf(Prot.class, llllllllllllllllIlIlllIIlIIlIlIl);
        }

        static {
            Protection = new Prot(Enchantments.PROTECTION);
            Blast_Protection = new Prot(Enchantments.BLAST_PROTECTION);
            Fire_Protection = new Prot(Enchantments.FIRE_PROTECTION);
            Projectile_Protection = new Prot(Enchantments.PROJECTILE_PROTECTION);
            $VALUES = Prot.$values();
        }

        public static Prot[] values() {
            return (Prot[])$VALUES.clone();
        }
    }
}

