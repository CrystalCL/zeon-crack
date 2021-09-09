/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.ItemConvertible
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.ItemStackAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoTotem;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;

public class AutoReplenish
extends Module {
    private final /* synthetic */ Setting<Boolean> offhand;
    private final /* synthetic */ ItemStack[] items;
    private final /* synthetic */ Setting<Integer> tickDelay;
    private final /* synthetic */ Setting<Boolean> searchHotbar;
    private /* synthetic */ boolean prevHadOpenScreen;
    private final /* synthetic */ Setting<Integer> threshold;
    private final /* synthetic */ Setting<Boolean> unstackable;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<List<Item>> excludedItems;
    private /* synthetic */ int tickDelayLeft;

    public AutoReplenish() {
        super(Categories.Player, "auto-replenish", "Automatically refills items in your hotbar, main hand, or offhand.");
        AutoReplenish lllllllllllllllllllIIIlllIlIlllI;
        lllllllllllllllllllIIIlllIlIlllI.sgGeneral = lllllllllllllllllllIIIlllIlIlllI.settings.getDefaultGroup();
        lllllllllllllllllllIIIlllIlIlllI.threshold = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new IntSetting.Builder().name("threshold").description("The threshold of items left this actives at.").defaultValue(8).min(1).sliderMax(63).build());
        lllllllllllllllllllIIIlllIlIlllI.tickDelay = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new IntSetting.Builder().name("delay").description("The tick delay to replenish your hotbar.").defaultValue(1).min(0).sliderMax(10).build());
        lllllllllllllllllllIIIlllIlIlllI.offhand = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new BoolSetting.Builder().name("offhand").description("Whether or not to refill your offhand with items.").defaultValue(true).build());
        lllllllllllllllllllIIIlllIlIlllI.unstackable = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new BoolSetting.Builder().name("unstackable").description("Replenishes unstackable items.").defaultValue(true).build());
        lllllllllllllllllllIIIlllIlIlllI.searchHotbar = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new BoolSetting.Builder().name("search-hotbar").description("Uses items in your hotbar to replenish if they are the only ones left.").defaultValue(true).build());
        lllllllllllllllllllIIIlllIlIlllI.excludedItems = lllllllllllllllllllIIIlllIlIlllI.sgGeneral.add(new ItemListSetting.Builder().name("excluded-items").description("Items that WILL NOT replenish.").defaultValue(new ArrayList<Item>()).build());
        lllllllllllllllllllIIIlllIlIlllI.items = new ItemStack[10];
        for (int lllllllllllllllllllIIIlllIlIllll = 0; lllllllllllllllllllIIIlllIlIllll < lllllllllllllllllllIIIlllIlIlllI.items.length; ++lllllllllllllllllllIIIlllIlIllll) {
            lllllllllllllllllllIIIlllIlIlllI.items[lllllllllllllllllllIIIlllIlIllll] = new ItemStack((ItemConvertible)Items.AIR);
        }
    }

    private void checkSlot(int lllllllllllllllllllIIIlllIIllIII, ItemStack lllllllllllllllllllIIIlllIIlIIll) {
        AutoReplenish lllllllllllllllllllIIIlllIIlIlIl;
        ItemStack lllllllllllllllllllIIIlllIIlIllI = lllllllllllllllllllIIIlllIIlIlIl.getItem(lllllllllllllllllllIIIlllIIllIII);
        if (!lllllllllllllllllllIIIlllIIlIIll.isEmpty() && lllllllllllllllllllIIIlllIIlIIll.isStackable() && !lllllllllllllllllllIIIlllIIlIlIl.excludedItems.get().contains((Object)lllllllllllllllllllIIIlllIIlIIll.getItem()) && lllllllllllllllllllIIIlllIIlIIll.getCount() <= lllllllllllllllllllIIIlllIIlIlIl.threshold.get()) {
            lllllllllllllllllllIIIlllIIlIlIl.addSlots(lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIlIl.findItem(lllllllllllllllllllIIIlllIIlIIll, lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIlIl.threshold.get() - lllllllllllllllllllIIIlllIIlIIll.getCount() + 1));
        }
        if (lllllllllllllllllllIIIlllIIlIIll.isEmpty() && !lllllllllllllllllllIIIlllIIlIllI.isEmpty() && !lllllllllllllllllllIIIlllIIlIlIl.excludedItems.get().contains((Object)lllllllllllllllllllIIIlllIIlIllI.getItem())) {
            if (lllllllllllllllllllIIIlllIIlIllI.isStackable()) {
                lllllllllllllllllllIIIlllIIlIlIl.addSlots(lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIlIl.findItem(lllllllllllllllllllIIIlllIIlIllI, lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIlIl.threshold.get() - lllllllllllllllllllIIIlllIIlIIll.getCount() + 1));
            } else if (lllllllllllllllllllIIIlllIIlIlIl.unstackable.get().booleanValue()) {
                lllllllllllllllllllIIIlllIIlIlIl.addSlots(lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIlIl.findItem(lllllllllllllllllllIIIlllIIlIllI, lllllllllllllllllllIIIlllIIllIII, 1));
            }
        }
        lllllllllllllllllllIIIlllIIlIlIl.setItem(lllllllllllllllllllIIIlllIIllIII, lllllllllllllllllllIIIlllIIlIIll);
    }

    private ItemStack getItem(int lllllllllllllllllllIIIllIllIIlll) {
        AutoReplenish lllllllllllllllllllIIIllIllIlIII;
        if (lllllllllllllllllllIIIllIllIIlll == 45) {
            lllllllllllllllllllIIIllIllIIlll = 9;
        }
        return lllllllllllllllllllIIIllIllIlIII.items[lllllllllllllllllllIIIllIllIIlll];
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIIIlllIlIIIIl) {
        AutoReplenish lllllllllllllllllllIIIlllIlIIIII;
        if (lllllllllllllllllllIIIlllIlIIIII.mc.currentScreen == null && lllllllllllllllllllIIIlllIlIIIII.prevHadOpenScreen) {
            lllllllllllllllllllIIIlllIlIIIII.fillItems();
        }
        boolean bl = lllllllllllllllllllIIIlllIlIIIII.prevHadOpenScreen = lllllllllllllllllllIIIlllIlIIIII.mc.currentScreen != null;
        if (lllllllllllllllllllIIIlllIlIIIII.mc.player.currentScreenHandler.getStacks().size() != 46 || lllllllllllllllllllIIIlllIlIIIII.mc.currentScreen != null) {
            return;
        }
        if (lllllllllllllllllllIIIlllIlIIIII.tickDelayLeft <= 0) {
            lllllllllllllllllllIIIlllIlIIIII.tickDelayLeft = lllllllllllllllllllIIIlllIlIIIII.tickDelay.get();
            for (int lllllllllllllllllllIIIlllIlIIlII = 0; lllllllllllllllllllIIIlllIlIIlII < 9; ++lllllllllllllllllllIIIlllIlIIlII) {
                ItemStack lllllllllllllllllllIIIlllIlIIlIl = lllllllllllllllllllIIIlllIlIIIII.mc.player.inventory.getStack(lllllllllllllllllllIIIlllIlIIlII);
                lllllllllllllllllllIIIlllIlIIIII.checkSlot(lllllllllllllllllllIIIlllIlIIlII, lllllllllllllllllllIIIlllIlIIlIl);
            }
            if (lllllllllllllllllllIIIlllIlIIIII.offhand.get().booleanValue() && !Modules.get().get(AutoTotem.class).getLocked()) {
                ItemStack lllllllllllllllllllIIIlllIlIIIll = lllllllllllllllllllIIIlllIlIIIII.mc.player.getOffHandStack();
                lllllllllllllllllllIIIlllIlIIIII.checkSlot(45, lllllllllllllllllllIIIlllIlIIIll);
            }
        } else {
            --lllllllllllllllllllIIIlllIlIIIII.tickDelayLeft;
        }
    }

    private void fillItems() {
        AutoReplenish lllllllllllllllllllIIIllIllIlllI;
        for (int lllllllllllllllllllIIIllIlllIIII = 0; lllllllllllllllllllIIIllIlllIIII < 9; ++lllllllllllllllllllIIIllIlllIIII) {
            lllllllllllllllllllIIIllIllIlllI.setItem(lllllllllllllllllllIIIllIlllIIII, lllllllllllllllllllIIIllIllIlllI.mc.player.inventory.getStack(lllllllllllllllllllIIIllIlllIIII));
        }
        lllllllllllllllllllIIIllIllIlllI.setItem(45, lllllllllllllllllllIIIllIllIlllI.mc.player.getOffHandStack());
    }

    private int findItem(ItemStack lllllllllllllllllllIIIlllIIIIllI, int lllllllllllllllllllIIIllIlllllll, int lllllllllllllllllllIIIllIllllllI) {
        AutoReplenish lllllllllllllllllllIIIlllIIIIlll;
        int lllllllllllllllllllIIIlllIIIIIll = -1;
        int lllllllllllllllllllIIIlllIIIIIlI = 0;
        for (int lllllllllllllllllllIIIlllIIIlIII = lllllllllllllllllllIIIlllIIIIlll.mc.player.inventory.size() - 2; lllllllllllllllllllIIIlllIIIlIII >= (lllllllllllllllllllIIIlllIIIIlll.searchHotbar.get() != false ? 0 : 9); --lllllllllllllllllllIIIlllIIIlIII) {
            ItemStack lllllllllllllllllllIIIlllIIIlIIl = lllllllllllllllllllIIIlllIIIIlll.mc.player.inventory.getStack(lllllllllllllllllllIIIlllIIIlIII);
            if (lllllllllllllllllllIIIlllIIIlIII == lllllllllllllllllllIIIllIlllllll || lllllllllllllllllllIIIlllIIIlIIl.getItem() != lllllllllllllllllllIIIlllIIIIllI.getItem() || !ItemStack.areTagsEqual((ItemStack)lllllllllllllllllllIIIlllIIIIllI, (ItemStack)lllllllllllllllllllIIIlllIIIlIIl) || lllllllllllllllllllIIIlllIIIlIIl.getCount() <= lllllllllllllllllllIIIlllIIIIIlI) continue;
            lllllllllllllllllllIIIlllIIIIIll = lllllllllllllllllllIIIlllIIIlIII;
            lllllllllllllllllllIIIlllIIIIIlI = lllllllllllllllllllIIIlllIIIlIIl.getCount();
            if (lllllllllllllllllllIIIlllIIIIIlI >= lllllllllllllllllllIIIllIllllllI) break;
        }
        return lllllllllllllllllllIIIlllIIIIIll;
    }

    private void addSlots(int lllllllllllllllllllIIIllIlllIlII, int lllllllllllllllllllIIIllIlllIlIl) {
        InvUtils.move().from(lllllllllllllllllllIIIllIlllIlIl).to(lllllllllllllllllllIIIllIlllIlII);
    }

    @Override
    public void onActivate() {
        AutoReplenish lllllllllllllllllllIIIlllIlIlIlI;
        lllllllllllllllllllIIIlllIlIlIlI.fillItems();
        lllllllllllllllllllIIIlllIlIlIlI.tickDelayLeft = lllllllllllllllllllIIIlllIlIlIlI.tickDelay.get();
        lllllllllllllllllllIIIlllIlIlIlI.prevHadOpenScreen = lllllllllllllllllllIIIlllIlIlIlI.mc.currentScreen != null;
    }

    private void setItem(int lllllllllllllllllllIIIllIlIlllIl, ItemStack lllllllllllllllllllIIIllIlIlllII) {
        AutoReplenish lllllllllllllllllllIIIllIlIllllI;
        if (lllllllllllllllllllIIIllIlIlllIl == 45) {
            lllllllllllllllllllIIIllIlIlllIl = 9;
        }
        ItemStack lllllllllllllllllllIIIllIlIlllll = lllllllllllllllllllIIIllIlIllllI.items[lllllllllllllllllllIIIllIlIlllIl];
        ((ItemStackAccessor)lllllllllllllllllllIIIllIlIlllll).setItem(lllllllllllllllllllIIIllIlIlllII.getItem());
        lllllllllllllllllllIIIllIlIlllll.setCount(lllllllllllllllllllIIIllIlIlllII.getCount());
        lllllllllllllllllllIIIllIlIlllll.setTag(lllllllllllllllllllIIIllIlIlllII.getTag());
        ((ItemStackAccessor)lllllllllllllllllllIIIllIlIlllll).setEmpty(lllllllllllllllllllIIIllIlIlllII.isEmpty());
    }
}

