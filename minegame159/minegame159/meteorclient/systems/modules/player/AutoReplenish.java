/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> searchHotbar;
    private final Setting<Integer> tickDelay;
    private int tickDelayLeft;
    private final Setting<List<Item>> excludedItems;
    private boolean prevHadOpenScreen;
    private final SettingGroup sgGeneral;
    private final ItemStack[] items;
    private final Setting<Integer> threshold;
    private final Setting<Boolean> unstackable;
    private final Setting<Boolean> offhand;

    @Override
    public void onActivate() {
        this.fillItems();
        this.tickDelayLeft = this.tickDelay.get();
        this.prevHadOpenScreen = this.mc.currentScreen != null;
    }

    private void checkSlot(int n, ItemStack ItemStack2) {
        ItemStack ItemStack3 = this.getItem(n);
        if (!ItemStack2.isEmpty() && ItemStack2.isStackable() && !this.excludedItems.get().contains(ItemStack2.getItem()) && ItemStack2.getCount() <= this.threshold.get()) {
            this.addSlots(n, this.findItem(ItemStack2, n, this.threshold.get() - ItemStack2.getCount() + 1));
        }
        if (ItemStack2.isEmpty() && !ItemStack3.isEmpty() && !this.excludedItems.get().contains(ItemStack3.getItem())) {
            if (ItemStack3.isStackable()) {
                this.addSlots(n, this.findItem(ItemStack3, n, this.threshold.get() - ItemStack2.getCount() + 1));
            } else if (this.unstackable.get().booleanValue()) {
                this.addSlots(n, this.findItem(ItemStack3, n, 1));
            }
        }
        this.setItem(n, ItemStack2);
    }

    public AutoReplenish() {
        super(Categories.Player, "auto-replenish", "Automatically refills items in your hotbar, main hand, or offhand.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.threshold = this.sgGeneral.add(new IntSetting.Builder().name("threshold").description("The threshold of items left this actives at.").defaultValue(8).min(1).sliderMax(63).build());
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The tick delay to replenish your hotbar.").defaultValue(1).min(0).sliderMax(10).build());
        this.offhand = this.sgGeneral.add(new BoolSetting.Builder().name("offhand").description("Whether or not to refill your offhand with items.").defaultValue(true).build());
        this.unstackable = this.sgGeneral.add(new BoolSetting.Builder().name("unstackable").description("Replenishes unstackable items.").defaultValue(true).build());
        this.searchHotbar = this.sgGeneral.add(new BoolSetting.Builder().name("search-hotbar").description("Uses items in your hotbar to replenish if they are the only ones left.").defaultValue(true).build());
        this.excludedItems = this.sgGeneral.add(new ItemListSetting.Builder().name("excluded-items").description("Items that WILL NOT replenish.").defaultValue(new ArrayList<Item>()).build());
        this.items = new ItemStack[10];
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = new ItemStack((ItemConvertible)Items.AIR);
            if (-2 < 0) continue;
            throw null;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.currentScreen == null && this.prevHadOpenScreen) {
            this.fillItems();
        }
        boolean bl = this.prevHadOpenScreen = this.mc.currentScreen != null;
        if (this.mc.player.currentScreenHandler.getStacks().size() != 46 || this.mc.currentScreen != null) {
            return;
        }
        if (this.tickDelayLeft <= 0) {
            this.tickDelayLeft = this.tickDelay.get();
            for (int i = 0; i < 9; ++i) {
                ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
                this.checkSlot(i, ItemStack2);
                if (-1 <= 3) continue;
                return;
            }
            if (this.offhand.get().booleanValue() && !Modules.get().get(AutoTotem.class).getLocked()) {
                ItemStack ItemStack3 = this.mc.player.getOffHandStack();
                this.checkSlot(45, ItemStack3);
            }
        } else {
            --this.tickDelayLeft;
        }
    }

    private ItemStack getItem(int n) {
        if (n == 45) {
            n = 9;
        }
        return this.items[n];
    }

    private void fillItems() {
        for (int i = 0; i < 9; ++i) {
            this.setItem(i, this.mc.player.inventory.getStack(i));
            if (null == null) continue;
            return;
        }
        this.setItem(45, this.mc.player.getOffHandStack());
    }

    private void setItem(int n, ItemStack ItemStack2) {
        if (n == 45) {
            n = 9;
        }
        ItemStack ItemStack3 = this.items[n];
        ((ItemStackAccessor)ItemStack3).setItem(ItemStack2.getItem());
        ItemStack3.setCount(ItemStack2.getCount());
        ItemStack3.setTag(ItemStack2.getTag());
        ((ItemStackAccessor)ItemStack3).setEmpty(ItemStack2.isEmpty());
    }

    private int findItem(ItemStack ItemStack2, int n, int n2) {
        int n3 = -1;
        int n4 = 0;
        for (int i = this.mc.player.inventory.size() - 2; i >= (this.searchHotbar.get() != false ? 0 : 9); --i) {
            ItemStack ItemStack3 = this.mc.player.inventory.getStack(i);
            if (i == n || ItemStack3.getItem() != ItemStack2.getItem() || !ItemStack.areTagsEqual((ItemStack)ItemStack2, (ItemStack)ItemStack3) || ItemStack3.getCount() <= n4) continue;
            n3 = i;
            n4 = ItemStack3.getCount();
            if (n4 >= n2) break;
            if (!false) continue;
            return 0;
        }
        return n3;
    }

    private void addSlots(int n, int n2) {
        InvUtils.move().from(n2).to(n);
    }
}

