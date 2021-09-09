/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.world.World
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.World;

public class EXPThrower
extends Module {
    private final /* synthetic */ Setting<Boolean> autoToggle;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> lookDown;

    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles in your hotbar.");
        EXPThrower llIIIlIIllIII;
        llIIIlIIllIII.sgGeneral = llIIIlIIllIII.settings.getDefaultGroup();
        llIIIlIIllIII.lookDown = llIIIlIIllIII.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate downwards when throwing bottles.").defaultValue(true).build());
        llIIIlIIllIII.autoToggle = llIIIlIIllIII.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles off when your armor is repaired.").defaultValue(true).build());
    }

    private void throwExp(int llIIIlIIIIlIl) {
        EXPThrower llIIIlIIIIIll;
        int llIIIlIIIIlII = llIIIlIIIIIll.mc.player.inventory.selectedSlot;
        llIIIlIIIIIll.mc.player.inventory.selectedSlot = llIIIlIIIIlIl;
        llIIIlIIIIIll.mc.interactionManager.interactItem((PlayerEntity)llIIIlIIIIIll.mc.player, (World)llIIIlIIIIIll.mc.world, Hand.MAIN_HAND);
        llIIIlIIIIIll.mc.player.inventory.selectedSlot = llIIIlIIIIlII;
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIIIlIIIllll) {
        int llIIIlIIIlllI;
        EXPThrower llIIIlIIlIIII;
        if (llIIIlIIlIIII.autoToggle.get().booleanValue()) {
            int llIIIlIIlIIlI = 0;
            int llIIIlIIlIIIl = 0;
            for (int llIIIlIIlIIll = 0; llIIIlIIlIIll < 4; ++llIIIlIIlIIll) {
                if (!((ItemStack)llIIIlIIlIIII.mc.player.inventory.armor.get(llIIIlIIlIIll)).isEmpty() && EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)llIIIlIIlIIII.mc.player.inventory.getArmorStack(llIIIlIIlIIll)) == 1) {
                    ++llIIIlIIlIIIl;
                }
                if (((ItemStack)llIIIlIIlIIII.mc.player.inventory.armor.get(llIIIlIIlIIll)).isDamaged()) continue;
                ++llIIIlIIlIIlI;
            }
            if (llIIIlIIlIIlI == llIIIlIIlIIIl && llIIIlIIlIIIl != 0) {
                llIIIlIIlIIII.toggle();
                return;
            }
        }
        if ((llIIIlIIIlllI = InvUtils.findItemInHotbar(Items.EXPERIENCE_BOTTLE)) != -1) {
            if (llIIIlIIlIIII.lookDown.get().booleanValue()) {
                Rotations.rotate(llIIIlIIlIIII.mc.player.yaw, 90.0, () -> {
                    EXPThrower llIIIIlllllII;
                    llIIIIlllllII.throwExp(llIIIlIIIlllI);
                });
            } else {
                llIIIlIIlIIII.throwExp(llIIIlIIIlllI);
            }
        }
    }
}

