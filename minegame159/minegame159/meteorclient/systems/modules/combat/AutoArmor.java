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
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2587;
import net.minecraft.class_490;

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
    private final Setting<List<class_1887>> avoidEnch;
    private final Setting<Integer> switchCooldown;

    private List<class_1887> setDefaultValue() {
        ArrayList<class_1887> arrayList = new ArrayList<class_1887>();
        arrayList.add(class_1893.field_9113);
        arrayList.add(class_1893.field_9122);
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
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (!(class_12972 instanceof class_1511) || !(DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, class_12972.method_19538()) > (double)this.boomDamage.get().intValue())) continue;
            return true;
        }
        if (!this.mc.field_1687.method_8597().method_29956()) {
            for (class_1297 class_12972 : this.mc.field_1687.field_9231) {
                class_2338 class_23382 = class_12972.method_11016();
                if (!(class_12972 instanceof class_2587)) continue;
                class_243 class_2432 = new class_243((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
                if (!(DamageCalcUtils.bedDamage((class_1309)this.mc.field_1724, class_2432) > (double)this.boomDamage.get().intValue())) continue;
                return true;
            }
        }
        return false;
    }

    private void getCurrentScore(class_1799 class_17992) {
        this.currentBest = class_1890.method_8225((class_1887)Prot.access$000(this.mode.get()), (class_1799)class_17992);
        this.currentProt = class_1890.method_8225((class_1887)class_1893.field_9111, (class_1799)class_17992);
        this.currentBlast = class_1890.method_8225((class_1887)class_1893.field_9107, (class_1799)class_17992);
        this.currentFire = class_1890.method_8225((class_1887)class_1893.field_9095, (class_1799)class_17992);
        this.currentProj = class_1890.method_8225((class_1887)class_1893.field_9096, (class_1799)class_17992);
        this.currentArmour = ((class_1738)class_17992.method_7909()).method_7687();
        this.currentToughness = ((class_1738)class_17992.method_7909()).method_26353();
        this.currentUnbreaking = class_1890.method_8225((class_1887)class_1893.field_9119, (class_1799)class_17992);
        this.currentMending = class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)class_17992);
    }

    private int getItemScore(class_1799 class_17992) {
        int n = 0;
        if (this.antiBreak.get().booleanValue() && class_17992.method_7936() - class_17992.method_7919() <= this.breakDurability.get()) {
            return 0;
        }
        for (class_1887 class_18872 : this.avoidEnch.get()) {
            if (class_1890.method_8225((class_1887)class_18872, (class_1799)class_17992) <= 0) continue;
            return -10;
        }
        n += 4 * (class_1890.method_8225((class_1887)Prot.access$000(this.mode.get()), (class_1799)class_17992) - this.currentBest);
        n += 2 * (class_1890.method_8225((class_1887)class_1893.field_9111, (class_1799)class_17992) - this.currentProt);
        n += 2 * (class_1890.method_8225((class_1887)class_1893.field_9107, (class_1799)class_17992) - this.currentBlast);
        n += 2 * (class_1890.method_8225((class_1887)class_1893.field_9095, (class_1799)class_17992) - this.currentFire);
        n += 2 * (class_1890.method_8225((class_1887)class_1893.field_9096, (class_1799)class_17992) - this.currentProj);
        n += 2 * (((class_1738)class_17992.method_7909()).method_7687() - this.currentArmour);
        n = (int)((float)n + 2.0f * (((class_1738)class_17992.method_7909()).method_26353() - this.currentToughness));
        n += class_1890.method_8225((class_1887)class_1893.field_9119, (class_1799)class_17992) - this.currentUnbreaking;
        if (this.preferMending.get().booleanValue() && class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)class_17992) - this.currentMending > 0) {
            n += this.weight.get().intValue();
        }
        return n;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.field_1724.field_7503.field_7477) {
            return;
        }
        if (this.pauseInInventory.get().booleanValue() && this.mc.field_1755 instanceof class_490) {
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
            class_1799 class_17992 = this.mc.field_1724.field_7514.method_7372(i);
            this.currentBest = 0;
            this.currentProt = 0;
            this.currentBlast = 0;
            this.currentFire = 0;
            this.currentProj = 0;
            this.currentArmour = 0;
            this.currentToughness = 0.0f;
            this.currentUnbreaking = 0;
            this.currentMending = 0;
            if ((this.ignoreElytra.get().booleanValue() || Modules.get().isActive(ChestSwap.class)) && class_17992.method_7909() == class_1802.field_8833 || class_1890.method_8224((class_1799)class_17992)) continue;
            if (class_17992.method_7909() instanceof class_1738) {
                if (i == 1 && this.bProtLegs.get().booleanValue()) {
                    this.mode.set(Prot.Blast_Protection);
                }
                this.getCurrentScore(class_17992);
            }
            int n = -1;
            int n2 = 0;
            for (int j = 0; j < 36; ++j) {
                int n3;
                class_1799 class_17993 = this.mc.field_1724.field_7514.method_5438(j);
                if (!(class_17993.method_7909() instanceof class_1738) || ((class_1738)class_17993.method_7909()).method_7685().method_5927() != i || n2 >= (n3 = this.getItemScore(class_17993))) continue;
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
        public static final /* enum */ Prot Protection = new Prot(class_1893.field_9111);
        private final class_1887 enchantment;
        public static final /* enum */ Prot Blast_Protection = new Prot(class_1893.field_9107);
        public static final /* enum */ Prot Fire_Protection = new Prot(class_1893.field_9095);
        public static final /* enum */ Prot Projectile_Protection = new Prot(class_1893.field_9096);
        private static final Prot[] $VALUES = Prot.$values();

        private static Prot[] $values() {
            return new Prot[]{Protection, Blast_Protection, Fire_Protection, Projectile_Protection};
        }

        public static Prot[] values() {
            return (Prot[])$VALUES.clone();
        }

        static class_1887 access$000(Prot prot) {
            return prot.enchantment;
        }

        private Prot(class_1887 class_18872) {
            this.enchantment = class_18872;
        }

        public static Prot valueOf(String string) {
            return Enum.valueOf(Prot.class, string);
        }
    }
}

