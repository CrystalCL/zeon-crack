/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import baritone.api.BaritoneAPI;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.ItemUseCrosshairTargetEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AnchorAura;
import minegame159.meteorclient.systems.modules.combat.BedAura;
import minegame159.meteorclient.systems.modules.combat.CrystalAura;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import minegame159.meteorclient.systems.modules.player.AutoGap;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class AutoEat
extends Module {
    private int prevSlot;
    private final Setting<Boolean> pauseAuras;
    private final SettingGroup sgGeneral;
    private static final Class<? extends Module>[] AURAS = new Class[]{KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class};
    private final SettingGroup sgHunger;
    private final Setting<Boolean> pauseBaritone;
    private boolean wasBaritone;
    private final Setting<Integer> hungerThreshold;
    private boolean eating;
    private int slot;
    private final Setting<List<Item>> blacklist;
    private final List<Class<? extends Module>> wasAura;

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
                if (4 > 2) continue;
                return;
            }
        }
        this.wasBaritone = false;
        if (this.pauseBaritone.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            this.wasBaritone = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
        }
    }

    @Override
    public void onDeactivate() {
        if (this.eating) {
            this.stopEating();
        }
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
                if (2 > -1) continue;
                return;
            }
        }
        if (this.pauseBaritone.get().booleanValue() && this.wasBaritone) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
        }
    }

    private int findSlot() {
        int n = -1;
        int n2 = -1;
        for (int i = 0; i < 9; ++i) {
            int n3;
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (!Item2.isFood() || (n3 = Item2.getFoodComponent().getHunger()) <= n2 || this.blacklist.get().contains(Item2)) continue;
            n = i;
            n2 = n3;
            if (3 >= 1) continue;
            return 0;
        }
        return n;
    }

    private boolean shouldEat() {
        return this.mc.player.getHungerManager().getFoodLevel() <= this.hungerThreshold.get();
    }

    private void changeSlot(int n) {
        this.mc.player.inventory.selectedSlot = n;
        this.slot = n;
    }

    @EventHandler
    private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent itemUseCrosshairTargetEvent) {
        if (this.eating) {
            itemUseCrosshairTargetEvent.target = null;
        }
    }

    private void setPressed(boolean bl) {
        this.mc.options.keyUse.setPressed(bl);
    }

    private void eat() {
        this.changeSlot(this.slot);
        this.setPressed(true);
        if (!this.mc.player.isUsingItem()) {
            Utils.rightClick();
        }
        this.eating = true;
    }

    public AutoEat() {
        super(Categories.Player, "auto-eat", "Automatically eats food.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgHunger = this.settings.createGroup("Hunger");
        this.blacklist = this.sgGeneral.add(new ItemListSetting.Builder().name("blacklist").description("Which items to not eat.").defaultValue(AutoEat.getDefaultBlacklist()).filter(Item::isFood).build());
        this.pauseAuras = this.sgGeneral.add(new BoolSetting.Builder().name("pause-auras").description("Pauses all auras when eating.").defaultValue(true).build());
        this.pauseBaritone = this.sgGeneral.add(new BoolSetting.Builder().name("pause-baritone").description("Pause baritone when eating.").defaultValue(true).build());
        this.hungerThreshold = this.sgHunger.add(new IntSetting.Builder().name("hunger-threshold").description("The level of hunger you eat at.").defaultValue(16).min(1).max(19).sliderMin(1).sliderMax(19).build());
        this.wasAura = new ArrayList<Class<? extends Module>>();
    }

    private static List<Item> getDefaultBlacklist() {
        ArrayList<Item> arrayList = new ArrayList<Item>(9);
        arrayList.add(Items.ENCHANTED_GOLDEN_APPLE);
        arrayList.add(Items.GOLDEN_APPLE);
        arrayList.add(Items.CHORUS_FRUIT);
        arrayList.add(Items.POISONOUS_POTATO);
        arrayList.add(Items.PUFFERFISH);
        arrayList.add(Items.CHICKEN);
        arrayList.add(Items.ROTTEN_FLESH);
        arrayList.add(Items.SPIDER_EYE);
        arrayList.add(Items.SUSPICIOUS_STEW);
        return arrayList;
    }

    @EventHandler(priority=-100)
    private void onTick(TickEvent.Pre pre) {
        if (Modules.get().get(AutoGap.class).isEating()) {
            return;
        }
        if (this.eating) {
            if (this.shouldEat()) {
                if (!this.mc.player.inventory.getStack(this.slot).isFood()) {
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
}

