/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ChestSwap
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> stayOn;
    private final Setting<Chestplate> chestplate;

    private boolean equipChestplate() {
        int n = -1;
        boolean bl = false;
        for (int i = 0; i < this.mc.player.inventory.main.size(); ++i) {
            Item Item2 = ((ItemStack)this.mc.player.inventory.main.get(i)).getItem();
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$ChestSwap$Chestplate[this.chestplate.get().ordinal()]) {
                case 1: {
                    if (Item2 != Items.DIAMOND_CHESTPLATE) break;
                    n = i;
                    bl = true;
                    break;
                }
                case 2: {
                    if (Item2 != Items.NETHERITE_CHESTPLATE) break;
                    n = i;
                    bl = true;
                    break;
                }
                case 3: {
                    if (Item2 == Items.DIAMOND_CHESTPLATE) {
                        n = i;
                        bl = true;
                        break;
                    }
                    if (Item2 != Items.NETHERITE_CHESTPLATE) break;
                    n = i;
                    break;
                }
                case 4: {
                    if (Item2 == Items.DIAMOND_CHESTPLATE) {
                        n = i;
                        break;
                    }
                    if (Item2 != Items.NETHERITE_CHESTPLATE) break;
                    n = i;
                    bl = true;
                }
            }
            if (bl) break;
            if (1 > 0) continue;
            return false;
        }
        if (n != -1) {
            this.equip(n);
        }
        return n != -1;
    }

    public ChestSwap() {
        super(Categories.Player, "chest-swap", "Automatically swaps between a chestplate and an elytra.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.chestplate = this.sgGeneral.add(new EnumSetting.Builder().name("chestplate").description("Which type of chestplate to swap to.").defaultValue(Chestplate.PreferNetherite).build());
        this.stayOn = this.sgGeneral.add(new BoolSetting.Builder().name("stay-on").description("Stays on and activates when you turn it off.").defaultValue(false).build());
    }

    private void equip(int n) {
        InvUtils.move().from(n).toArmor(2);
    }

    private void equipElytra() {
        for (int i = 0; i < this.mc.player.inventory.main.size(); ++i) {
            Item Item2 = ((ItemStack)this.mc.player.inventory.main.get(i)).getItem();
            if (Item2 != Items.ELYTRA) continue;
            this.equip(i);
            break;
        }
    }

    public void swap() {
        Item Item2 = this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem();
        if (Item2 == Items.ELYTRA) {
            this.equipChestplate();
        } else if (Item2 instanceof ArmorItem && ((ArmorItem)Item2).getSlotType() == EquipmentSlot.CHEST) {
            this.equipElytra();
        } else if (!this.equipChestplate()) {
            this.equipElytra();
        }
    }

    @Override
    public void onActivate() {
        this.swap();
        if (!this.stayOn.get().booleanValue()) {
            this.toggle();
        }
    }

    @Override
    public void onDeactivate() {
        if (this.stayOn.get().booleanValue()) {
            this.swap();
        }
    }

    @Override
    public void sendToggledMsg() {
        if (this.stayOn.get().booleanValue()) {
            super.sendToggledMsg();
        } else if (Config.get().chatCommandsInfo) {
            ChatUtils.moduleInfo(this, "Triggered (highlight)%s(default).", this.title);
        }
    }

    public static final class Chestplate
    extends Enum<Chestplate> {
        private static final Chestplate[] $VALUES;
        public static final /* enum */ Chestplate PreferNetherite;
        public static final /* enum */ Chestplate PreferDiamond;
        public static final /* enum */ Chestplate Diamond;
        public static final /* enum */ Chestplate Netherite;

        public static Chestplate[] values() {
            return (Chestplate[])$VALUES.clone();
        }

        static {
            Diamond = new Chestplate();
            Netherite = new Chestplate();
            PreferDiamond = new Chestplate();
            PreferNetherite = new Chestplate();
            $VALUES = Chestplate.$values();
        }

        private static Chestplate[] $values() {
            return new Chestplate[]{Diamond, Netherite, PreferDiamond, PreferNetherite};
        }

        public static Chestplate valueOf(String string) {
            return Enum.valueOf(Chestplate.class, string);
        }
    }
}

