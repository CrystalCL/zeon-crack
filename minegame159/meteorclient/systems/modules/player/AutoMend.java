/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.item.ArmorItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.SwordItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
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
    private final /* synthetic */ Setting<Boolean> removeFinished;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> armourSlots;
    private final /* synthetic */ Setting<Boolean> swords;

    public AutoMend() {
        super(Categories.Player, "auto-mend", "Automatically replaces equipped items and items in your offhand with Mending when fully repaired.");
        AutoMend llllllllllllllllllllIlIIlIIlIIlI;
        llllllllllllllllllllIlIIlIIlIIlI.sgGeneral = llllllllllllllllllllIlIIlIIlIIlI.settings.getDefaultGroup();
        llllllllllllllllllllIlIIlIIlIIlI.swords = llllllllllllllllllllIlIIlIIlIIlI.sgGeneral.add(new BoolSetting.Builder().name("swords").description("Moves swords.").defaultValue(true).build());
        llllllllllllllllllllIlIIlIIlIIlI.armourSlots = llllllllllllllllllllIlIIlIIlIIlI.sgGeneral.add(new BoolSetting.Builder().name("use-armor-slots").description("Whether or not to use armor slots to mend items quicker.").defaultValue(true).build());
        llllllllllllllllllllIlIIlIIlIIlI.removeFinished = llllllllllllllllllllIlIIlIIlIIlI.sgGeneral.add(new BoolSetting.Builder().name("remove-when-finished").description("The items will be moved out of active slots if there are no items to replace, but space in your inventory.").defaultValue(true).build());
    }

    private void replaceItem(boolean llllllllllllllllllllIlIIlIIIlIlI) {
        AutoMend llllllllllllllllllllIlIIlIIIlIIl;
        for (int llllllllllllllllllllIlIIlIIIllII = 0; llllllllllllllllllllIlIIlIIIllII < llllllllllllllllllllIlIIlIIIlIIl.mc.player.inventory.main.size(); ++llllllllllllllllllllIlIIlIIIllII) {
            ItemStack llllllllllllllllllllIlIIlIIIllIl = llllllllllllllllllllIlIIlIIIlIIl.mc.player.inventory.getStack(llllllllllllllllllllIlIIlIIIllII);
            if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llllllllllllllllllllIlIIlIIIllIl) == 0 || !llllllllllllllllllllIlIIlIIIllIl.isDamaged() || !llllllllllllllllllllIlIIlIIIlIIl.swords.get().booleanValue() && llllllllllllllllllllIlIIlIIIllIl.getItem() instanceof SwordItem) continue;
            InvUtils.move().from(llllllllllllllllllllIlIIlIIIllII).toOffhand();
            break;
        }
        if (!llllllllllllllllllllIlIIlIIIlIIl.mc.player.getOffHandStack().isDamaged() && llllllllllllllllllllIlIIlIIIlIIl.removeFinished.get().booleanValue() && llllllllllllllllllllIlIIlIIIlIIl.mc.player.inventory.getEmptySlot() != -1) {
            InvUtils.move().fromOffhand().to(llllllllllllllllllllIlIIlIIIlIIl.mc.player.inventory.getEmptySlot());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllllIlIIIllIlIll) {
        AutoMend llllllllllllllllllllIlIIIllIllII;
        if (llllllllllllllllllllIlIIIllIllII.mc.player.currentScreenHandler.getStacks().size() != 46) {
            return;
        }
        if (llllllllllllllllllllIlIIIllIllII.mc.player.getOffHandStack().isEmpty()) {
            llllllllllllllllllllIlIIIllIllII.replaceItem(true);
        } else if (!llllllllllllllllllllIlIIIllIllII.mc.player.getOffHandStack().isDamaged()) {
            llllllllllllllllllllIlIIIllIllII.replaceItem(false);
        } else if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llllllllllllllllllllIlIIIllIllII.mc.player.getOffHandStack()) == 0) {
            llllllllllllllllllllIlIIIllIllII.replaceItem(false);
        }
        if (llllllllllllllllllllIlIIIllIllII.armourSlots.get().booleanValue()) {
            if (Modules.get().isActive(AutoArmor.class)) {
                ChatUtils.moduleWarning(llllllllllllllllllllIlIIIllIllII, "Cannot use armor slots while AutoArmor is active. Please disable AutoArmor and try again. Disabling Use Armor Slots.", new Object[0]);
                llllllllllllllllllllIlIIIllIllII.armourSlots.set(false);
            }
            for (int llllllllllllllllllllIlIIIllIllIl = 5; llllllllllllllllllllIlIIIllIllIl < 9; ++llllllllllllllllllllIlIIIllIllIl) {
                if (llllllllllllllllllllIlIIIllIllII.mc.player.inventory.getStack(39 - (llllllllllllllllllllIlIIIllIllIl - 5)).isEmpty()) {
                    llllllllllllllllllllIlIIIllIllII.replaceArmour(llllllllllllllllllllIlIIIllIllIl, true);
                    continue;
                }
                if (!llllllllllllllllllllIlIIIllIllII.mc.player.inventory.getStack(39 - (llllllllllllllllllllIlIIIllIllIl - 5)).isDamaged()) {
                    llllllllllllllllllllIlIIIllIllII.replaceArmour(llllllllllllllllllllIlIIIllIllIl, false);
                    continue;
                }
                if (EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llllllllllllllllllllIlIIIllIllII.mc.player.inventory.getStack(39 - (llllllllllllllllllllIlIIIllIllIl - 5))) != 0) continue;
                llllllllllllllllllllIlIIIllIllII.replaceArmour(llllllllllllllllllllIlIIIllIllIl, false);
            }
        }
    }

    private boolean checkSlot(ItemStack llllllllllllllllllllIlIIIlllllll, int llllllllllllllllllllIlIIIllllllI) {
        boolean llllllllllllllllllllIlIIlIIIIIII = false;
        if (llllllllllllllllllllIlIIIllllllI == 5 && ((ArmorItem)llllllllllllllllllllIlIIIlllllll.getItem()).getSlotType() == EquipmentSlot.HEAD) {
            llllllllllllllllllllIlIIlIIIIIII = true;
        } else if (llllllllllllllllllllIlIIIllllllI == 6 && ((ArmorItem)llllllllllllllllllllIlIIIlllllll.getItem()).getSlotType() == EquipmentSlot.CHEST) {
            llllllllllllllllllllIlIIlIIIIIII = true;
        } else if (llllllllllllllllllllIlIIIllllllI == 7 && ((ArmorItem)llllllllllllllllllllIlIIIlllllll.getItem()).getSlotType() == EquipmentSlot.LEGS) {
            llllllllllllllllllllIlIIlIIIIIII = true;
        } else if (llllllllllllllllllllIlIIIllllllI == 8 && ((ArmorItem)llllllllllllllllllllIlIIIlllllll.getItem()).getSlotType() == EquipmentSlot.FEET) {
            llllllllllllllllllllIlIIlIIIIIII = true;
        }
        return llllllllllllllllllllIlIIlIIIIIII;
    }

    private void replaceArmour(int llllllllllllllllllllIlIIIlllIIlI, boolean llllllllllllllllllllIlIIIlllIlII) {
        AutoMend llllllllllllllllllllIlIIIlllIllI;
        for (int llllllllllllllllllllIlIIIlllIlll = 0; llllllllllllllllllllIlIIIlllIlll < llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.main.size(); ++llllllllllllllllllllIlIIIlllIlll) {
            ItemStack llllllllllllllllllllIlIIIllllIII = llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.getStack(llllllllllllllllllllIlIIIlllIlll);
            if (!(llllllllllllllllllllIlIIIllllIII.getItem() instanceof ArmorItem) || !llllllllllllllllllllIlIIIlllIllI.checkSlot(llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.getStack(llllllllllllllllllllIlIIIlllIlll), llllllllllllllllllllIlIIIlllIIlI) || EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llllllllllllllllllllIlIIIllllIII) == 0 || !llllllllllllllllllllIlIIIllllIII.isDamaged()) continue;
            InvUtils.move().from(llllllllllllllllllllIlIIIlllIlll).toId(llllllllllllllllllllIlIIIlllIIlI);
            break;
        }
        if (!llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.getStack(39 - (llllllllllllllllllllIlIIIlllIIlI - 5)).isDamaged() && llllllllllllllllllllIlIIIlllIllI.removeFinished.get().booleanValue() && llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.getEmptySlot() != -1) {
            InvUtils.move().fromId(llllllllllllllllllllIlIIIlllIIlI).to(llllllllllllllllllllIlIIIlllIllI.mc.player.inventory.getEmptySlot());
        }
    }
}

