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
    private final Setting<Boolean> lookDown;
    private final Setting<Boolean> autoToggle;
    private final SettingGroup sgGeneral;

    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles in your hotbar.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.lookDown = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate downwards when throwing bottles.").defaultValue(true).build());
        this.autoToggle = this.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles off when your armor is repaired.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n;
        if (this.autoToggle.get().booleanValue()) {
            n = 0;
            int n2 = 0;
            for (int i = 0; i < 4; ++i) {
                if (!((ItemStack)this.mc.player.inventory.armor.get(i)).isEmpty() && EnchantmentHelper.getLevel((Enchantment)Enchantments.MENDING, (ItemStack)this.mc.player.inventory.getArmorStack(i)) == 1) {
                    ++n2;
                }
                if (((ItemStack)this.mc.player.inventory.armor.get(i)).isDamaged()) continue;
                ++n;
                if (null == null) continue;
                return;
            }
            if (n == n2 && n2 != 0) {
                this.toggle();
                return;
            }
        }
        if ((n = InvUtils.findItemInHotbar(Items.EXPERIENCE_BOTTLE)) != -1) {
            if (this.lookDown.get().booleanValue()) {
                Rotations.rotate(this.mc.player.yaw, 90.0, () -> this.lambda$onTick$0(n));
            } else {
                this.throwExp(n);
            }
        }
    }

    private void lambda$onTick$0(int n) {
        this.throwExp(n);
    }

    private void throwExp(int n) {
        int n2 = this.mc.player.inventory.selectedSlot;
        this.mc.player.inventory.selectedSlot = n;
        this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand.MAIN_HAND);
        this.mc.player.inventory.selectedSlot = n2;
    }
}

