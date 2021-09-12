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
import net.minecraft.class_1304;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1829;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;

public class AutoMend
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> removeFinished;
    private final Setting<Boolean> armourSlots;
    private final Setting<Boolean> swords;

    private boolean checkSlot(class_1799 class_17992, int n) {
        boolean bl = false;
        if (n == 5 && ((class_1738)class_17992.method_7909()).method_7685() == class_1304.field_6169) {
            bl = true;
        } else if (n == 6 && ((class_1738)class_17992.method_7909()).method_7685() == class_1304.field_6174) {
            bl = true;
        } else if (n == 7 && ((class_1738)class_17992.method_7909()).method_7685() == class_1304.field_6172) {
            bl = true;
        } else if (n == 8 && ((class_1738)class_17992.method_7909()).method_7685() == class_1304.field_6166) {
            bl = true;
        }
        return bl;
    }

    private void replaceArmour(int n, boolean bl) {
        for (int i = 0; i < this.mc.field_1724.field_7514.field_7547.size(); ++i) {
            class_1799 class_17992 = this.mc.field_1724.field_7514.method_5438(i);
            if (!(class_17992.method_7909() instanceof class_1738) || !this.checkSlot(this.mc.field_1724.field_7514.method_5438(i), n) || class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)class_17992) == 0 || !class_17992.method_7986()) continue;
            InvUtils.move().from(i).toId(n);
            break;
        }
        if (!this.mc.field_1724.field_7514.method_5438(39 - (n - 5)).method_7986() && this.removeFinished.get().booleanValue() && this.mc.field_1724.field_7514.method_7376() != -1) {
            InvUtils.move().fromId(n).to(this.mc.field_1724.field_7514.method_7376());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.field_1724.field_7512.method_7602().size() != 46) {
            return;
        }
        if (this.mc.field_1724.method_6079().method_7960()) {
            this.replaceItem(true);
        } else if (!this.mc.field_1724.method_6079().method_7986()) {
            this.replaceItem(false);
        } else if (class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)this.mc.field_1724.method_6079()) == 0) {
            this.replaceItem(false);
        }
        if (this.armourSlots.get().booleanValue()) {
            if (Modules.get().isActive(AutoArmor.class)) {
                ChatUtils.moduleWarning(this, "Cannot use armor slots while AutoArmor is active. Please disable AutoArmor and try again. Disabling Use Armor Slots.", new Object[0]);
                this.armourSlots.set(false);
            }
            for (int i = 5; i < 9; ++i) {
                if (this.mc.field_1724.field_7514.method_5438(39 - (i - 5)).method_7960()) {
                    this.replaceArmour(i, true);
                    continue;
                }
                if (!this.mc.field_1724.field_7514.method_5438(39 - (i - 5)).method_7986()) {
                    this.replaceArmour(i, false);
                    continue;
                }
                if (class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)this.mc.field_1724.field_7514.method_5438(39 - (i - 5))) != 0) continue;
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
        for (int i = 0; i < this.mc.field_1724.field_7514.field_7547.size(); ++i) {
            class_1799 class_17992 = this.mc.field_1724.field_7514.method_5438(i);
            if (class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)class_17992) == 0 || !class_17992.method_7986() || !this.swords.get().booleanValue() && class_17992.method_7909() instanceof class_1829) continue;
            InvUtils.move().from(i).toOffhand();
            break;
        }
        if (!this.mc.field_1724.method_6079().method_7986() && this.removeFinished.get().booleanValue() && this.mc.field_1724.field_7514.method_7376() != -1) {
            InvUtils.move().fromOffhand().to(this.mc.field_1724.field_7514.method_7376());
        }
    }
}

