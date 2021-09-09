/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
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
    private final /* synthetic */ List<Class<? extends Module>> wasAura;
    private /* synthetic */ int slot;
    private final /* synthetic */ SettingGroup sgHunger;
    private final /* synthetic */ Setting<List<Item>> blacklist;
    private /* synthetic */ boolean wasBaritone;
    private final /* synthetic */ Setting<Boolean> pauseAuras;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> pauseBaritone;
    private /* synthetic */ boolean eating;
    private /* synthetic */ int prevSlot;
    private final /* synthetic */ Setting<Integer> hungerThreshold;
    private static final /* synthetic */ Class<? extends Module>[] AURAS;

    @Override
    public void onDeactivate() {
        AutoEat llllllllllllllllllIlIIIIIlIlIlII;
        if (llllllllllllllllllIlIIIIIlIlIlII.eating) {
            llllllllllllllllllIlIIIIIlIlIlII.stopEating();
        }
    }

    static {
        AURAS = new Class[]{KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class};
    }

    private static List<Item> getDefaultBlacklist() {
        ArrayList<Item> llllllllllllllllllIIllllllIIllIl = new ArrayList<Item>(9);
        llllllllllllllllllIIllllllIIllIl.add(Items.ENCHANTED_GOLDEN_APPLE);
        llllllllllllllllllIIllllllIIllIl.add(Items.GOLDEN_APPLE);
        llllllllllllllllllIIllllllIIllIl.add(Items.CHORUS_FRUIT);
        llllllllllllllllllIIllllllIIllIl.add(Items.POISONOUS_POTATO);
        llllllllllllllllllIIllllllIIllIl.add(Items.PUFFERFISH);
        llllllllllllllllllIIllllllIIllIl.add(Items.CHICKEN);
        llllllllllllllllllIIllllllIIllIl.add(Items.ROTTEN_FLESH);
        llllllllllllllllllIIllllllIIllIl.add(Items.SPIDER_EYE);
        llllllllllllllllllIIllllllIIllIl.add(Items.SUSPICIOUS_STEW);
        return llllllllllllllllllIIllllllIIllIl;
    }

    private void startEating() {
        AutoEat llllllllllllllllllIlIIIIIIllllII;
        llllllllllllllllllIlIIIIIIllllII.prevSlot = llllllllllllllllllIlIIIIIIllllII.mc.player.inventory.selectedSlot;
        llllllllllllllllllIlIIIIIIllllII.eat();
        llllllllllllllllllIlIIIIIIllllII.wasAura.clear();
        if (llllllllllllllllllIlIIIIIIllllII.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> llllllllllllllllllIlIIIIIIllllIl : AURAS) {
                Module llllllllllllllllllIlIIIIIIlllllI = Modules.get().get(llllllllllllllllllIlIIIIIIllllIl);
                if (!llllllllllllllllllIlIIIIIIlllllI.isActive()) continue;
                llllllllllllllllllIlIIIIIIllllII.wasAura.add(llllllllllllllllllIlIIIIIIllllIl);
                llllllllllllllllllIlIIIIIIlllllI.toggle();
            }
        }
        llllllllllllllllllIlIIIIIIllllII.wasBaritone = false;
        if (llllllllllllllllllIlIIIIIIllllII.pauseBaritone.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            llllllllllllllllllIlIIIIIIllllII.wasBaritone = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
        }
    }

    private void changeSlot(int llllllllllllllllllIIlllllllllIll) {
        llllllllllllllllllIlIIIIIIIIIIIl.mc.player.inventory.selectedSlot = llllllllllllllllllIIlllllllllIll;
        llllllllllllllllllIlIIIIIIIIIIIl.slot = llllllllllllllllllIIlllllllllIll;
    }

    private void stopEating() {
        AutoEat llllllllllllllllllIlIIIIIIIlllIl;
        llllllllllllllllllIlIIIIIIIlllIl.changeSlot(llllllllllllllllllIlIIIIIIIlllIl.prevSlot);
        llllllllllllllllllIlIIIIIIIlllIl.setPressed(false);
        llllllllllllllllllIlIIIIIIIlllIl.eating = false;
        if (llllllllllllllllllIlIIIIIIIlllIl.pauseAuras.get().booleanValue()) {
            for (Class<? extends Module> llllllllllllllllllIlIIIIIIIllllI : AURAS) {
                Module llllllllllllllllllIlIIIIIIIlllll = Modules.get().get(llllllllllllllllllIlIIIIIIIllllI);
                if (!llllllllllllllllllIlIIIIIIIlllIl.wasAura.contains(llllllllllllllllllIlIIIIIIIllllI) || llllllllllllllllllIlIIIIIIIlllll.isActive()) continue;
                llllllllllllllllllIlIIIIIIIlllll.toggle();
            }
        }
        if (llllllllllllllllllIlIIIIIIIlllIl.pauseBaritone.get().booleanValue() && llllllllllllllllllIlIIIIIIIlllIl.wasBaritone) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
        }
    }

    private void eat() {
        AutoEat llllllllllllllllllIlIIIIIIlIllII;
        llllllllllllllllllIlIIIIIIlIllII.changeSlot(llllllllllllllllllIlIIIIIIlIllII.slot);
        llllllllllllllllllIlIIIIIIlIllII.setPressed(true);
        if (!llllllllllllllllllIlIIIIIIlIllII.mc.player.isUsingItem()) {
            Utils.rightClick();
        }
        llllllllllllllllllIlIIIIIIlIllII.eating = true;
    }

    public AutoEat() {
        super(Categories.Player, "auto-eat", "Automatically eats food.");
        AutoEat llllllllllllllllllIlIIIIIlIllIII;
        llllllllllllllllllIlIIIIIlIllIII.sgGeneral = llllllllllllllllllIlIIIIIlIllIII.settings.getDefaultGroup();
        llllllllllllllllllIlIIIIIlIllIII.sgHunger = llllllllllllllllllIlIIIIIlIllIII.settings.createGroup("Hunger");
        llllllllllllllllllIlIIIIIlIllIII.blacklist = llllllllllllllllllIlIIIIIlIllIII.sgGeneral.add(new ItemListSetting.Builder().name("blacklist").description("Which items to not eat.").defaultValue(AutoEat.getDefaultBlacklist()).filter(Item::isFood).build());
        llllllllllllllllllIlIIIIIlIllIII.pauseAuras = llllllllllllllllllIlIIIIIlIllIII.sgGeneral.add(new BoolSetting.Builder().name("pause-auras").description("Pauses all auras when eating.").defaultValue(true).build());
        llllllllllllllllllIlIIIIIlIllIII.pauseBaritone = llllllllllllllllllIlIIIIIlIllIII.sgGeneral.add(new BoolSetting.Builder().name("pause-baritone").description("Pause baritone when eating.").defaultValue(true).build());
        llllllllllllllllllIlIIIIIlIllIII.hungerThreshold = llllllllllllllllllIlIIIIIlIllIII.sgHunger.add(new IntSetting.Builder().name("hunger-threshold").description("The level of hunger you eat at.").defaultValue(16).min(1).max(19).sliderMin(1).sliderMax(19).build());
        llllllllllllllllllIlIIIIIlIllIII.wasAura = new ArrayList<Class<? extends Module>>();
    }

    private boolean shouldEat() {
        AutoEat llllllllllllllllllIIllllllllIIIl;
        return llllllllllllllllllIIllllllllIIIl.mc.player.getHungerManager().getFoodLevel() <= llllllllllllllllllIIllllllllIIIl.hungerThreshold.get();
    }

    @EventHandler
    private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent llllllllllllllllllIlIIIIIlIIIlll) {
        AutoEat llllllllllllllllllIlIIIIIlIIlIII;
        if (llllllllllllllllllIlIIIIIlIIlIII.eating) {
            llllllllllllllllllIlIIIIIlIIIlll.target = null;
        }
    }

    private int findSlot() {
        int llllllllllllllllllIIllllllIllIIl = -1;
        int llllllllllllllllllIIllllllIlIlll = -1;
        for (int llllllllllllllllllIIllllllIlllII = 0; llllllllllllllllllIIllllllIlllII < 9; ++llllllllllllllllllIIllllllIlllII) {
            int llllllllllllllllllIIllllllIlllIl;
            AutoEat llllllllllllllllllIIllllllIlIlIl;
            Item llllllllllllllllllIIllllllIllllI = llllllllllllllllllIIllllllIlIlIl.mc.player.inventory.getStack(llllllllllllllllllIIllllllIlllII).getItem();
            if (!llllllllllllllllllIIllllllIllllI.isFood() || (llllllllllllllllllIIllllllIlllIl = llllllllllllllllllIIllllllIllllI.getFoodComponent().getHunger()) <= llllllllllllllllllIIllllllIlIlll || llllllllllllllllllIIllllllIlIlIl.blacklist.get().contains((Object)llllllllllllllllllIIllllllIllllI)) continue;
            llllllllllllllllllIIllllllIllIIl = llllllllllllllllllIIllllllIlllII;
            llllllllllllllllllIIllllllIlIlll = llllllllllllllllllIIllllllIlllIl;
        }
        return llllllllllllllllllIIllllllIllIIl;
    }

    private void setPressed(boolean llllllllllllllllllIlIIIIIIIIllIl) {
        AutoEat llllllllllllllllllIlIIIIIIIIlIll;
        llllllllllllllllllIlIIIIIIIIlIll.mc.options.keyUse.setPressed(llllllllllllllllllIlIIIIIIIIllIl);
    }

    @EventHandler(priority=-100)
    private void onTick(TickEvent.Pre llllllllllllllllllIlIIIIIlIIllll) {
        AutoEat llllllllllllllllllIlIIIIIlIIlllI;
        if (Modules.get().get(AutoGap.class).isEating()) {
            return;
        }
        if (llllllllllllllllllIlIIIIIlIIlllI.eating) {
            if (llllllllllllllllllIlIIIIIlIIlllI.shouldEat()) {
                if (!llllllllllllllllllIlIIIIIlIIlllI.mc.player.inventory.getStack(llllllllllllllllllIlIIIIIlIIlllI.slot).isFood()) {
                    int llllllllllllllllllIlIIIIIlIlIIIl = llllllllllllllllllIlIIIIIlIIlllI.findSlot();
                    if (llllllllllllllllllIlIIIIIlIlIIIl == -1) {
                        llllllllllllllllllIlIIIIIlIIlllI.stopEating();
                        return;
                    }
                    llllllllllllllllllIlIIIIIlIIlllI.changeSlot(llllllllllllllllllIlIIIIIlIlIIIl);
                }
                llllllllllllllllllIlIIIIIlIIlllI.eat();
            } else {
                llllllllllllllllllIlIIIIIlIIlllI.stopEating();
            }
        } else if (llllllllllllllllllIlIIIIIlIIlllI.shouldEat()) {
            llllllllllllllllllIlIIIIIlIIlllI.slot = llllllllllllllllllIlIIIIIlIIlllI.findSlot();
            if (llllllllllllllllllIlIIIIIlIIlllI.slot != -1) {
                llllllllllllllllllIlIIIIIlIIlllI.startEating();
            }
        }
    }
}

