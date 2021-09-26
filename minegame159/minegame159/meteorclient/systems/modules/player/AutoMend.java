/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoArmor;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;

public class AutoMend
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> removeFinished;
    private final Setting<Boolean> armourSlots;
    private final Setting<Boolean> swords;

    private boolean checkSlot(ItemStack ItemStack2, int n) {
        boolean bl = false;
        if (n == 5 && ((ArmorItem)ItemStack2.getItem()).getSlotType() == EquipmentSlot.HEAD) {
            bl = true;
        } else if (n == 6 && ((ArmorItem)ItemStack2.getItem()).getSlotType() == EquipmentSlot.CHEST) {
            bl = true;
        } else if (n == 7 && ((ArmorItem)ItemStack2.getItem()).getSlotType() == EquipmentSlot.LEGS) {
            bl = true;
        } else if (n == 8 && ((ArmorItem)ItemStack2.getItem()).getSlotType() == EquipmentSlot.FEET) {
            bl = true;
        }
        return bl;
    }

    private void replaceArmour(int n, boolean bl) {
        for (int i = 0; i < this.mc.player.inventory.main.size(); ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            if (!(ItemStack2.getItem() instanceof ArmorItem) || !this.checkSlot(this.mc.player.inventory.getStack(i), n) || EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)ItemStack2) == 0 || !ItemStack2.isDamaged()) continue;
            InvUtils.move().from(i).toId(n);
            break;
        }
        if (!this.mc.player.inventory.getStack(39 - (n - 5)).isDamaged() && this.removeFinished.get().booleanValue() && this.mc.player.inventory.getEmptySlot() != -1) {
            InvUtils.move().fromId(n).to(this.mc.player.inventory.getEmptySlot());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.currentScreenHandler.getStacks().size() != 46) {
            return;
        }
        if (this.mc.player.getOffHandStack().isEmpty()) {
            this.replaceItem(true);
        } else if (!this.mc.player.getOffHandStack().isDamaged()) {
            this.replaceItem(false);
        } else if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)this.mc.player.getOffHandStack()) == 0) {
            this.replaceItem(false);
        }
        if (this.armourSlots.get().booleanValue()) {
            if (Modules.get().isActive(AutoArmor.class)) {
                ChatUtils.moduleWarning(this, "Cannot use armor slots while AutoArmor is active. Please disable AutoArmor and try again. Disabling Use Armor Slots.", new Object[0]);
                this.armourSlots.set(false);
            }
            for (int i = 5; i < 9; ++i) {
                if (this.mc.player.inventory.getStack(39 - (i - 5)).isEmpty()) {
                    this.replaceArmour(i, true);
                    continue;
                }
                if (!this.mc.player.inventory.getStack(39 - (i - 5)).isDamaged()) {
                    this.replaceArmour(i, false);
                    continue;
                }
                if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)this.mc.player.inventory.getStack(39 - (i - 5))) != 0) continue;
                this.replaceArmour(i, false);
                if (true) continue;
                return;
            }
        }
    }

    public AutoMend() {
        super(Categories.Player, "auto-mend", "Automatically replaces equipped items and items in your offhand with Mending when fully repaired.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.swords = this.sgGeneral.add(new BoolSetting.Builder().name("swords").description("Moves swords.").defaultValue(true).build());
        this.armourSlots = this.sgGeneral.add(new BoolSetting.Builder().name("use-armor-slots").description("Whether or not to use armor slots to mend items quicker.").defaultValue(true).build());
        this.removeFinished = this.sgGeneral.add(new BoolSetting.Builder().name("remove-when-finished").description("The items will be moved out of active slots if there are no items to replace, but space in your inventory.").defaultValue(true).build());
    }

    private void replaceItem(boolean bl) {
        for (int i = 0; i < this.mc.player.inventory.main.size(); ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)ItemStack2) == 0 || !ItemStack2.isDamaged() || !this.swords.get().booleanValue() && ItemStack2.getItem() instanceof SwordItem) continue;
            InvUtils.move().from(i).toOffhand();
            break;
        }
        if (!this.mc.player.getOffHandStack().isDamaged() && this.removeFinished.get().booleanValue() && this.mc.player.inventory.getEmptySlot() != -1) {
            InvUtils.move().fromOffhand().to(this.mc.player.inventory.getEmptySlot());
        }
    }
}

