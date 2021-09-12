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
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_1937;

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
                if (!((class_1799)this.mc.field_1724.field_7514.field_7548.get(i)).method_7960() && class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)this.mc.field_1724.field_7514.method_7372(i)) == 1) {
                    ++n2;
                }
                if (((class_1799)this.mc.field_1724.field_7514.field_7548.get(i)).method_7986()) continue;
                ++n;
                if (null == null) continue;
                return;
            }
            if (n == n2 && n2 != 0) {
                this.toggle();
                return;
            }
        }
        if ((n = InvUtils.findItemInHotbar(class_1802.field_8287)) != -1) {
            if (this.lookDown.get().booleanValue()) {
                Rotations.rotate(this.mc.field_1724.field_6031, 90.0, () -> this.lambda$onTick$0(n));
            } else {
                this.throwExp(n);
            }
        }
    }

    private void lambda$onTick$0(int n) {
        this.throwExp(n);
    }

    private void throwExp(int n) {
        int n2 = this.mc.field_1724.field_7514.field_7545;
        this.mc.field_1724.field_7514.field_7545 = n;
        this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, (class_1937)this.mc.field_1687, class_1268.field_5808);
        this.mc.field_1724.field_7514.field_7545 = n2;
    }
}

