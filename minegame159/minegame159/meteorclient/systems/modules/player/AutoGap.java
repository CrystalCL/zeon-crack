/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import baritone.api.BaritoneAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.ItemUseCrosshairTargetEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AnchorAura;
import minegame159.meteorclient.systems.modules.combat.BedAura;
import minegame159.meteorclient.systems.modules.combat.CrystalAura;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AutoGap
extends Module {
    private final Setting<Boolean> potionsResistance;
    private final SettingGroup sgPotions;
    private final Setting<Boolean> potionsRegeneration;
    private boolean wasBaritone;
    private final SettingGroup sgHealth;
    private boolean requiresEGap;
    private final Setting<Boolean> pauseBaritone;
    private final Setting<Integer> healthThreshold;
    private int slot;
    private int prevSlot;
    private boolean eating;
    private final Setting<Boolean> always;
    private final List<Class<? extends Module>> wasAura;
    private static final Class<? extends Module>[] AURAS = new Class[]{KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class};
    private final Setting<Boolean> preferEGap;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> potionsFireResistance;
    private final Setting<Boolean> healthEnabled;
    private final Setting<Boolean> pauseAuras;

    private void setPressed(boolean bl) {
        this.mc.options.keyUse.setPressed(bl);
    }

    private void stopEating() {
        this.changeSlot(this.prevSlot);
        this.setPressed(false);
        this.eating = false;
        if (this.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> clazz : AURAS) {
                Module module = Modules.get().get(clazz);
                if (!this.wasAura.contains(clazz) || module.isActive()) continue;
                module.toggle();
                if (2 >= 0) continue;
                return;
            }
        }
        if (this.pauseBaritone.get().booleanValue() && this.wasBaritone) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
        }
    }

    private boolean shouldEat() {
        this.requiresEGap = false;
        if (this.always.get().booleanValue()) {
            return true;
        }
        if (this.shouldEatPotions()) {
            return true;
        }
        return this.shouldEatHealth();
    }

    @Override
    public void onDeactivate() {
        if (this.eating) {
            this.stopEating();
        }
    }

    public AutoGap() {
        super(Categories.Player, "auto-gap", "Automatically eats Gaps or E-Gaps.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgPotions = this.settings.createGroup("Potions");
        this.sgHealth = this.settings.createGroup("Health");
        this.preferEGap = this.sgGeneral.add(new BoolSetting.Builder().name("prefer-egap").description("Prefers to eat E-Gap over Gaps if found.").defaultValue(true).build());
        this.always = this.sgGeneral.add(new BoolSetting.Builder().name("always").description("If it should always eat.").defaultValue(false).build());
        this.pauseAuras = this.sgGeneral.add(new BoolSetting.Builder().name("pause-auras").description("Pauses all auras when eating.").defaultValue(true).build());
        this.pauseBaritone = this.sgGeneral.add(new BoolSetting.Builder().name("pause-baritone").description("Pause baritone when eating.").defaultValue(true).build());
        this.potionsRegeneration = this.sgPotions.add(new BoolSetting.Builder().name("potions-regeneration").description("If it should eat when Regeneration runs out.").defaultValue(false).build());
        this.potionsFireResistance = this.sgPotions.add(new BoolSetting.Builder().name("potions-fire-resistance").description("If it should eat when Fire Resistance runs out. Requires E-Gaps.").defaultValue(true).build());
        this.potionsResistance = this.sgPotions.add(new BoolSetting.Builder().name("potions-absorption").description("If it should eat when Resistance runs out. Requires E-Gaps.").defaultValue(false).build());
        this.healthEnabled = this.sgHealth.add(new BoolSetting.Builder().name("health-enabled").description("If it should eat when health drops below threshold.").defaultValue(true).build());
        this.healthThreshold = this.sgHealth.add(new IntSetting.Builder().name("health-threshold").description("Health threshold to eat at. Includes absorption.").defaultValue(20).min(0).sliderMax(40).build());
        this.wasAura = new ArrayList<Class<? extends Module>>();
    }

    private int findSlot() {
        boolean bl = this.preferEGap.get();
        if (this.requiresEGap) {
            bl = true;
        }
        int n = -1;
        Item Item2 = null;
        for (int i = 0; i < 9; ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            if (ItemStack2.isEmpty() || this.isNotGapOrEGap(ItemStack2)) continue;
            Item Item3 = ItemStack2.getItem();
            if (Item2 == null) {
                n = i;
                Item2 = Item3;
                continue;
            }
            if (Item2 == Item3) continue;
            if (Item3 == Items.ENCHANTED_GOLDEN_APPLE && bl) {
                n = i;
                Item2 = Item3;
                break;
            }
            if (Item3 != Items.GOLDEN_APPLE || bl) continue;
            n = i;
            Item2 = Item3;
            break;
        }
        if (this.requiresEGap && Item2 != Items.ENCHANTED_GOLDEN_APPLE) {
            return -1;
        }
        return n;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.eating) {
            if (this.shouldEat()) {
                if (this.isNotGapOrEGap(this.mc.player.inventory.getStack(this.slot))) {
                    int n = this.findSlot();
                    if (n == -1) {
                        this.stopEating();
                        return;
                    }
                    this.changeSlot(n);
                }
                this.eat();
            } else {
                this.stopEating();
            }
        } else if (this.shouldEat()) {
            this.slot = this.findSlot();
            if (this.slot != -1) {
                this.startEating();
            }
        }
    }

    public boolean isEating() {
        return this.isActive() && this.eating;
    }

    private void startEating() {
        this.prevSlot = this.mc.player.inventory.selectedSlot;
        this.eat();
        this.wasAura.clear();
        if (this.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> clazz : AURAS) {
                Module module = Modules.get().get(clazz);
                if (!module.isActive()) continue;
                this.wasAura.add(clazz);
                module.toggle();
                if (3 != 0) continue;
                return;
            }
        }
        this.wasBaritone = false;
        if (this.pauseBaritone.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            this.wasBaritone = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
        }
    }

    @EventHandler
    private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent itemUseCrosshairTargetEvent) {
        if (this.eating) {
            itemUseCrosshairTargetEvent.target = null;
        }
    }

    private boolean shouldEatPotions() {
        Map map = this.mc.player.getActiveStatusEffects();
        if (this.potionsRegeneration.get().booleanValue() && !map.containsKey(StatusEffects.REGENERATION)) {
            return true;
        }
        if (this.potionsFireResistance.get().booleanValue() && !map.containsKey(StatusEffects.FIRE_RESISTANCE)) {
            this.requiresEGap = true;
            return true;
        }
        if (this.potionsResistance.get().booleanValue() && !map.containsKey(StatusEffects.RESISTANCE)) {
            this.requiresEGap = true;
            return true;
        }
        return false;
    }

    private boolean shouldEatHealth() {
        if (!this.healthEnabled.get().booleanValue()) {
            return false;
        }
        int n = Math.round(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount());
        return n < this.healthThreshold.get();
    }

    private void eat() {
        this.changeSlot(this.slot);
        this.setPressed(true);
        if (!this.mc.player.isUsingItem()) {
            Utils.rightClick();
        }
        this.eating = true;
    }

    private void changeSlot(int n) {
        this.mc.player.inventory.selectedSlot = n;
        this.slot = n;
    }

    private boolean isNotGapOrEGap(ItemStack ItemStack2) {
        Item Item2 = ItemStack2.getItem();
        return Item2 != Items.GOLDEN_APPLE && Item2 != Items.ENCHANTED_GOLDEN_APPLE;
    }
}

