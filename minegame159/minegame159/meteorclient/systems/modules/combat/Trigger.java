/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;

public class Trigger
extends Module {
    private final Setting<Boolean> whenHoldingLeftClick;
    private final SettingGroup sgGeneral;
    private class_1297 target;

    private void attack(class_1297 class_12972) {
        this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, class_12972);
        this.mc.field_1724.method_6104(class_1268.field_5808);
    }

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof class_1657) {
            return this.target.method_5820();
        }
        if (this.target != null) {
            return this.target.method_5864().method_5897().getString();
        }
        return null;
    }

    public Trigger() {
        super(Categories.Combat, "trigger", "Automatically swings when you look at entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.whenHoldingLeftClick = this.sgGeneral.add(new BoolSetting.Builder().name("when-holding-left-click").description("Attacks only when you are holding left click.").defaultValue(false).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.target = null;
        if (this.mc.field_1724.method_6032() <= 0.0f || this.mc.field_1724.method_7261(0.5f) < 1.0f) {
            return;
        }
        if (!(this.mc.field_1692 instanceof class_1309)) {
            return;
        }
        if (((class_1309)this.mc.field_1692).method_6032() <= 0.0f) {
            return;
        }
        this.target = this.mc.field_1692;
        if (this.whenHoldingLeftClick.get().booleanValue()) {
            if (this.mc.field_1690.field_1886.method_1434()) {
                this.attack(this.target);
            }
        } else {
            this.attack(this.target);
        }
    }
}

