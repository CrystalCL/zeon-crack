/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
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
    private final /* synthetic */ Setting<Boolean> potionsResistance;
    private /* synthetic */ boolean eating;
    private final /* synthetic */ Setting<Integer> healthThreshold;
    private final /* synthetic */ Setting<Boolean> pauseAuras;
    private final /* synthetic */ Setting<Boolean> potionsFireResistance;
    private final /* synthetic */ Setting<Boolean> preferEGap;
    private /* synthetic */ boolean wasBaritone;
    private final /* synthetic */ Setting<Boolean> pauseBaritone;
    private /* synthetic */ int slot;
    private final /* synthetic */ Setting<Boolean> potionsRegeneration;
    private /* synthetic */ int prevSlot;
    private /* synthetic */ boolean requiresEGap;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ List<Class<? extends Module>> wasAura;
    private static final /* synthetic */ Class<? extends Module>[] AURAS;
    private final /* synthetic */ Setting<Boolean> healthEnabled;
    private final /* synthetic */ SettingGroup sgPotions;
    private final /* synthetic */ SettingGroup sgHealth;
    private final /* synthetic */ Setting<Boolean> always;

    private void changeSlot(int llIlIllIlllllll) {
        llIlIlllIIIIIII.mc.player.inventory.selectedSlot = llIlIllIlllllll;
        llIlIlllIIIIIII.slot = llIlIllIlllllll;
    }

    private boolean shouldEatHealth() {
        AutoGap llIlIllIlllIIll;
        if (!llIlIllIlllIIll.healthEnabled.get().booleanValue()) {
            return false;
        }
        int llIlIllIlllIIlI = Math.round(llIlIllIlllIIll.mc.player.getHealth() + llIlIllIlllIIll.mc.player.getAbsorptionAmount());
        return llIlIllIlllIIlI < llIlIllIlllIIll.healthThreshold.get();
    }

    private void stopEating() {
        AutoGap llIlIlllIIlIIIl;
        llIlIlllIIlIIIl.changeSlot(llIlIlllIIlIIIl.prevSlot);
        llIlIlllIIlIIIl.setPressed(false);
        llIlIlllIIlIIIl.eating = false;
        if (llIlIlllIIlIIIl.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> llIlIlllIIlIIlI : AURAS) {
                Module llIlIlllIIlIIll = Modules.get().get(llIlIlllIIlIIlI);
                if (!llIlIlllIIlIIIl.wasAura.contains(llIlIlllIIlIIlI) || llIlIlllIIlIIll.isActive()) continue;
                llIlIlllIIlIIll.toggle();
            }
        }
        if (llIlIlllIIlIIIl.pauseBaritone.get().booleanValue() && llIlIlllIIlIIIl.wasBaritone) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
        }
    }

    private void setPressed(boolean llIlIlllIIIIlll) {
        AutoGap llIlIlllIIIlIII;
        llIlIlllIIIlIII.mc.options.keyUse.setPressed(llIlIlllIIIIlll);
    }

    @EventHandler
    private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent llIlIlllIlIllII) {
        AutoGap llIlIlllIlIllll;
        if (llIlIlllIlIllll.eating) {
            llIlIlllIlIllII.target = null;
        }
    }

    public AutoGap() {
        super(Categories.Player, "auto-gap", "Automatically eats Gaps or E-Gaps.");
        AutoGap llIlIlllIllllII;
        llIlIlllIllllII.sgGeneral = llIlIlllIllllII.settings.getDefaultGroup();
        llIlIlllIllllII.sgPotions = llIlIlllIllllII.settings.createGroup("Potions");
        llIlIlllIllllII.sgHealth = llIlIlllIllllII.settings.createGroup("Health");
        llIlIlllIllllII.preferEGap = llIlIlllIllllII.sgGeneral.add(new BoolSetting.Builder().name("prefer-egap").description("Prefers to eat E-Gap over Gaps if found.").defaultValue(true).build());
        llIlIlllIllllII.always = llIlIlllIllllII.sgGeneral.add(new BoolSetting.Builder().name("always").description("If it should always eat.").defaultValue(false).build());
        llIlIlllIllllII.pauseAuras = llIlIlllIllllII.sgGeneral.add(new BoolSetting.Builder().name("pause-auras").description("Pauses all auras when eating.").defaultValue(true).build());
        llIlIlllIllllII.pauseBaritone = llIlIlllIllllII.sgGeneral.add(new BoolSetting.Builder().name("pause-baritone").description("Pause baritone when eating.").defaultValue(true).build());
        llIlIlllIllllII.potionsRegeneration = llIlIlllIllllII.sgPotions.add(new BoolSetting.Builder().name("potions-regeneration").description("If it should eat when Regeneration runs out.").defaultValue(false).build());
        llIlIlllIllllII.potionsFireResistance = llIlIlllIllllII.sgPotions.add(new BoolSetting.Builder().name("potions-fire-resistance").description("If it should eat when Fire Resistance runs out. Requires E-Gaps.").defaultValue(true).build());
        llIlIlllIllllII.potionsResistance = llIlIlllIllllII.sgPotions.add(new BoolSetting.Builder().name("potions-absorption").description("If it should eat when Resistance runs out. Requires E-Gaps.").defaultValue(false).build());
        llIlIlllIllllII.healthEnabled = llIlIlllIllllII.sgHealth.add(new BoolSetting.Builder().name("health-enabled").description("If it should eat when health drops below threshold.").defaultValue(true).build());
        llIlIlllIllllII.healthThreshold = llIlIlllIllllII.sgHealth.add(new IntSetting.Builder().name("health-threshold").description("Health threshold to eat at. Includes absorption.").defaultValue(20).min(0).sliderMax(40).build());
        llIlIlllIllllII.wasAura = new ArrayList<Class<? extends Module>>();
    }

    @Override
    public void onDeactivate() {
        AutoGap llIlIlllIlllIlI;
        if (llIlIlllIlllIlI.eating) {
            llIlIlllIlllIlI.stopEating();
        }
    }

    private int findSlot() {
        AutoGap llIlIllIllIIlIl;
        boolean llIlIllIllIIlII = llIlIllIllIIlIl.preferEGap.get();
        if (llIlIllIllIIlIl.requiresEGap) {
            llIlIllIllIIlII = true;
        }
        int llIlIllIllIIIll = -1;
        Item llIlIllIllIIIlI = null;
        for (int llIlIllIllIIllI = 0; llIlIllIllIIllI < 9; ++llIlIllIllIIllI) {
            ItemStack llIlIllIllIlIII = llIlIllIllIIlIl.mc.player.inventory.getStack(llIlIllIllIIllI);
            if (llIlIllIllIlIII.isEmpty() || llIlIllIllIIlIl.isNotGapOrEGap(llIlIllIllIlIII)) continue;
            Item llIlIllIllIIlll = llIlIllIllIlIII.getItem();
            if (llIlIllIllIIIlI == null) {
                llIlIllIllIIIll = llIlIllIllIIllI;
                llIlIllIllIIIlI = llIlIllIllIIlll;
                continue;
            }
            if (llIlIllIllIIIlI == llIlIllIllIIlll) continue;
            if (llIlIllIllIIlll == Items.ENCHANTED_GOLDEN_APPLE && llIlIllIllIIlII) {
                llIlIllIllIIIll = llIlIllIllIIllI;
                llIlIllIllIIIlI = llIlIllIllIIlll;
                break;
            }
            if (llIlIllIllIIlll != Items.GOLDEN_APPLE || llIlIllIllIIlII) continue;
            llIlIllIllIIIll = llIlIllIllIIllI;
            llIlIllIllIIIlI = llIlIllIllIIlll;
            break;
        }
        if (llIlIllIllIIlIl.requiresEGap && llIlIllIllIIIlI != Items.ENCHANTED_GOLDEN_APPLE) {
            return -1;
        }
        return llIlIllIllIIIll;
    }

    private void startEating() {
        AutoGap llIlIlllIlIIIll;
        llIlIlllIlIIIll.prevSlot = llIlIlllIlIIIll.mc.player.inventory.selectedSlot;
        llIlIlllIlIIIll.eat();
        llIlIlllIlIIIll.wasAura.clear();
        if (llIlIlllIlIIIll.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> llIlIlllIlIIlII : AURAS) {
                Module llIlIlllIlIIlIl = Modules.get().get(llIlIlllIlIIlII);
                if (!llIlIlllIlIIlIl.isActive()) continue;
                llIlIlllIlIIIll.wasAura.add(llIlIlllIlIIlII);
                llIlIlllIlIIlIl.toggle();
            }
        }
        llIlIlllIlIIIll.wasBaritone = false;
        if (llIlIlllIlIIIll.pauseBaritone.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            llIlIlllIlIIIll.wasBaritone = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
        }
    }

    private boolean isNotGapOrEGap(ItemStack llIlIllIlIlIlll) {
        Item llIlIllIlIlIllI = llIlIllIlIlIlll.getItem();
        return llIlIllIlIlIllI != Items.GOLDEN_APPLE && llIlIllIlIlIllI != Items.ENCHANTED_GOLDEN_APPLE;
    }

    private boolean shouldEat() {
        AutoGap llIlIllIlllllIl;
        llIlIllIlllllIl.requiresEGap = false;
        if (llIlIllIlllllIl.always.get().booleanValue()) {
            return true;
        }
        if (llIlIllIlllllIl.shouldEatPotions()) {
            return true;
        }
        return llIlIllIlllllIl.shouldEatHealth();
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIlIlllIllIlII) {
        AutoGap llIlIlllIllIlIl;
        if (llIlIlllIllIlIl.eating) {
            if (llIlIlllIllIlIl.shouldEat()) {
                if (llIlIlllIllIlIl.isNotGapOrEGap(llIlIlllIllIlIl.mc.player.inventory.getStack(llIlIlllIllIlIl.slot))) {
                    int llIlIlllIllIllI = llIlIlllIllIlIl.findSlot();
                    if (llIlIlllIllIllI == -1) {
                        llIlIlllIllIlIl.stopEating();
                        return;
                    }
                    llIlIlllIllIlIl.changeSlot(llIlIlllIllIllI);
                }
                llIlIlllIllIlIl.eat();
            } else {
                llIlIlllIllIlIl.stopEating();
            }
        } else if (llIlIlllIllIlIl.shouldEat()) {
            llIlIlllIllIlIl.slot = llIlIlllIllIlIl.findSlot();
            if (llIlIlllIllIlIl.slot != -1) {
                llIlIlllIllIlIl.startEating();
            }
        }
    }

    public boolean isEating() {
        AutoGap llIlIllIlIlIIIl;
        return llIlIllIlIlIIIl.isActive() && llIlIllIlIlIIIl.eating;
    }

    static {
        AURAS = new Class[]{KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class};
    }

    private boolean shouldEatPotions() {
        AutoGap llIlIllIlllIlll;
        Map llIlIllIllllIII = llIlIllIlllIlll.mc.player.getActiveStatusEffects();
        if (llIlIllIlllIlll.potionsRegeneration.get().booleanValue() && !llIlIllIllllIII.containsKey((Object)StatusEffects.REGENERATION)) {
            return true;
        }
        if (llIlIllIlllIlll.potionsFireResistance.get().booleanValue() && !llIlIllIllllIII.containsKey((Object)StatusEffects.FIRE_RESISTANCE)) {
            llIlIllIlllIlll.requiresEGap = true;
            return true;
        }
        if (llIlIllIlllIlll.potionsResistance.get().booleanValue() && !llIlIllIllllIII.containsKey((Object)StatusEffects.RESISTANCE)) {
            llIlIllIlllIlll.requiresEGap = true;
            return true;
        }
        return false;
    }

    private void eat() {
        AutoGap llIlIlllIIllIlI;
        llIlIlllIIllIlI.changeSlot(llIlIlllIIllIlI.slot);
        llIlIlllIIllIlI.setPressed(true);
        if (!llIlIlllIIllIlI.mc.player.isUsingItem()) {
            Utils.rightClick();
        }
        llIlIlllIIllIlI.eating = true;
    }
}

