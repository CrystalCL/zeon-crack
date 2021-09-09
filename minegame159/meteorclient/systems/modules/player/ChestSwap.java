/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.item.ArmorItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
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
    private final /* synthetic */ Setting<Chestplate> chestplate;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> stayOn;

    public void swap() {
        ChestSwap lllllllllllllllllIIlIIlIIIlllIlI;
        Item lllllllllllllllllIIlIIlIIIlllIIl = lllllllllllllllllIIlIIlIIIlllIlI.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem();
        if (lllllllllllllllllIIlIIlIIIlllIIl == Items.ELYTRA) {
            lllllllllllllllllIIlIIlIIIlllIlI.equipChestplate();
        } else if (lllllllllllllllllIIlIIlIIIlllIIl instanceof ArmorItem && ((ArmorItem)lllllllllllllllllIIlIIlIIIlllIIl).getSlotType() == EquipmentSlot.CHEST) {
            lllllllllllllllllIIlIIlIIIlllIlI.equipElytra();
        } else if (!lllllllllllllllllIIlIIlIIIlllIlI.equipChestplate()) {
            lllllllllllllllllIIlIIlIIIlllIlI.equipElytra();
        }
    }

    @Override
    public void onDeactivate() {
        ChestSwap lllllllllllllllllIIlIIlIIIlllllI;
        if (lllllllllllllllllIIlIIlIIIlllllI.stayOn.get().booleanValue()) {
            lllllllllllllllllIIlIIlIIIlllllI.swap();
        }
    }

    public ChestSwap() {
        super(Categories.Player, "chest-swap", "Automatically swaps between a chestplate and an elytra.");
        ChestSwap lllllllllllllllllIIlIIlIIlIIIlII;
        lllllllllllllllllIIlIIlIIlIIIlII.sgGeneral = lllllllllllllllllIIlIIlIIlIIIlII.settings.getDefaultGroup();
        lllllllllllllllllIIlIIlIIlIIIlII.chestplate = lllllllllllllllllIIlIIlIIlIIIlII.sgGeneral.add(new EnumSetting.Builder().name("chestplate").description("Which type of chestplate to swap to.").defaultValue(Chestplate.PreferNetherite).build());
        lllllllllllllllllIIlIIlIIlIIIlII.stayOn = lllllllllllllllllIIlIIlIIlIIIlII.sgGeneral.add(new BoolSetting.Builder().name("stay-on").description("Stays on and activates when you turn it off.").defaultValue(false).build());
    }

    private void equip(int lllllllllllllllllIIlIIlIIIIllIll) {
        InvUtils.move().from(lllllllllllllllllIIlIIlIIIIllIll).toArmor(2);
    }

    private void equipElytra() {
        ChestSwap lllllllllllllllllIIlIIlIIIlIIIlI;
        for (int lllllllllllllllllIIlIIlIIIlIIIll = 0; lllllllllllllllllIIlIIlIIIlIIIll < lllllllllllllllllIIlIIlIIIlIIIlI.mc.player.inventory.main.size(); ++lllllllllllllllllIIlIIlIIIlIIIll) {
            Item lllllllllllllllllIIlIIlIIIlIIlII = ((ItemStack)lllllllllllllllllIIlIIlIIIlIIIlI.mc.player.inventory.main.get(lllllllllllllllllIIlIIlIIIlIIIll)).getItem();
            if (lllllllllllllllllIIlIIlIIIlIIlII != Items.ELYTRA) continue;
            lllllllllllllllllIIlIIlIIIlIIIlI.equip(lllllllllllllllllIIlIIlIIIlIIIll);
            break;
        }
    }

    private boolean equipChestplate() {
        ChestSwap lllllllllllllllllIIlIIlIIIlIllII;
        int lllllllllllllllllIIlIIlIIIlIlllI = -1;
        boolean lllllllllllllllllIIlIIlIIIlIllIl = false;
        for (int lllllllllllllllllIIlIIlIIIllIIII = 0; lllllllllllllllllIIlIIlIIIllIIII < lllllllllllllllllIIlIIlIIIlIllII.mc.player.inventory.main.size(); ++lllllllllllllllllIIlIIlIIIllIIII) {
            Item lllllllllllllllllIIlIIlIIIllIIIl = ((ItemStack)lllllllllllllllllIIlIIlIIIlIllII.mc.player.inventory.main.get(lllllllllllllllllIIlIIlIIIllIIII)).getItem();
            switch (lllllllllllllllllIIlIIlIIIlIllII.chestplate.get()) {
                case Diamond: {
                    if (lllllllllllllllllIIlIIlIIIllIIIl != Items.DIAMOND_CHESTPLATE) break;
                    lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                    lllllllllllllllllIIlIIlIIIlIllIl = true;
                    break;
                }
                case Netherite: {
                    if (lllllllllllllllllIIlIIlIIIllIIIl != Items.NETHERITE_CHESTPLATE) break;
                    lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                    lllllllllllllllllIIlIIlIIIlIllIl = true;
                    break;
                }
                case PreferDiamond: {
                    if (lllllllllllllllllIIlIIlIIIllIIIl == Items.DIAMOND_CHESTPLATE) {
                        lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                        lllllllllllllllllIIlIIlIIIlIllIl = true;
                        break;
                    }
                    if (lllllllllllllllllIIlIIlIIIllIIIl != Items.NETHERITE_CHESTPLATE) break;
                    lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                    break;
                }
                case PreferNetherite: {
                    if (lllllllllllllllllIIlIIlIIIllIIIl == Items.DIAMOND_CHESTPLATE) {
                        lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                        break;
                    }
                    if (lllllllllllllllllIIlIIlIIIllIIIl != Items.NETHERITE_CHESTPLATE) break;
                    lllllllllllllllllIIlIIlIIIlIlllI = lllllllllllllllllIIlIIlIIIllIIII;
                    lllllllllllllllllIIlIIlIIIlIllIl = true;
                }
            }
            if (lllllllllllllllllIIlIIlIIIlIllIl) break;
        }
        if (lllllllllllllllllIIlIIlIIIlIlllI != -1) {
            lllllllllllllllllIIlIIlIIIlIllII.equip(lllllllllllllllllIIlIIlIIIlIlllI);
        }
        return lllllllllllllllllIIlIIlIIIlIlllI != -1;
    }

    @Override
    public void sendToggledMsg() {
        ChestSwap lllllllllllllllllIIlIIlIIIIllIII;
        if (lllllllllllllllllIIlIIlIIIIllIII.stayOn.get().booleanValue()) {
            super.sendToggledMsg();
        } else if (Config.get().chatCommandsInfo) {
            ChatUtils.moduleInfo(lllllllllllllllllIIlIIlIIIIllIII, "Triggered (highlight)%s(default).", lllllllllllllllllIIlIIlIIIIllIII.title);
        }
    }

    @Override
    public void onActivate() {
        ChestSwap lllllllllllllllllIIlIIlIIlIIIIIl;
        lllllllllllllllllIIlIIlIIlIIIIIl.swap();
        if (!lllllllllllllllllIIlIIlIIlIIIIIl.stayOn.get().booleanValue()) {
            lllllllllllllllllIIlIIlIIlIIIIIl.toggle();
        }
    }

    public static final class Chestplate
    extends Enum<Chestplate> {
        private static final /* synthetic */ Chestplate[] $VALUES;
        public static final /* synthetic */ /* enum */ Chestplate PreferNetherite;
        public static final /* synthetic */ /* enum */ Chestplate PreferDiamond;
        public static final /* synthetic */ /* enum */ Chestplate Netherite;
        public static final /* synthetic */ /* enum */ Chestplate Diamond;

        private static /* synthetic */ Chestplate[] $values() {
            return new Chestplate[]{Diamond, Netherite, PreferDiamond, PreferNetherite};
        }

        private Chestplate() {
            Chestplate llllllllllllllllllIlIIlllIlIlllI;
        }

        static {
            Diamond = new Chestplate();
            Netherite = new Chestplate();
            PreferDiamond = new Chestplate();
            PreferNetherite = new Chestplate();
            $VALUES = Chestplate.$values();
        }

        public static Chestplate[] values() {
            return (Chestplate[])$VALUES.clone();
        }

        public static Chestplate valueOf(String llllllllllllllllllIlIIlllIllIIlI) {
            return Enum.valueOf(Chestplate.class, llllllllllllllllllIlIIlllIllIIlI);
        }
    }
}

