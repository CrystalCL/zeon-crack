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
import net.minecraft.class_1304;
import net.minecraft.class_1738;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;

public class ChestSwap
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> stayOn;
    private final Setting<Chestplate> chestplate;

    private boolean equipChestplate() {
        int n = -1;
        boolean bl = false;
        for (int i = 0; i < this.mc.field_1724.field_7514.field_7547.size(); ++i) {
            class_1792 class_17922 = ((class_1799)this.mc.field_1724.field_7514.field_7547.get(i)).method_7909();
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$ChestSwap$Chestplate[this.chestplate.get().ordinal()]) {
                case 1: {
                    if (class_17922 != class_1802.field_8058) break;
                    n = i;
                    bl = true;
                    break;
                }
                case 2: {
                    if (class_17922 != class_1802.field_22028) break;
                    n = i;
                    bl = true;
                    break;
                }
                case 3: {
                    if (class_17922 == class_1802.field_8058) {
                        n = i;
                        bl = true;
                        break;
                    }
                    if (class_17922 != class_1802.field_22028) break;
                    n = i;
                    break;
                }
                case 4: {
                    if (class_17922 == class_1802.field_8058) {
                        n = i;
                        break;
                    }
                    if (class_17922 != class_1802.field_22028) break;
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
        for (int i = 0; i < this.mc.field_1724.field_7514.field_7547.size(); ++i) {
            class_1792 class_17922 = ((class_1799)this.mc.field_1724.field_7514.field_7547.get(i)).method_7909();
            if (class_17922 != class_1802.field_8833) continue;
            this.equip(i);
            break;
        }
    }

    public void swap() {
        class_1792 class_17922 = this.mc.field_1724.method_6118(class_1304.field_6174).method_7909();
        if (class_17922 == class_1802.field_8833) {
            this.equipChestplate();
        } else if (class_17922 instanceof class_1738 && ((class_1738)class_17922).method_7685() == class_1304.field_6174) {
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

