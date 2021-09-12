/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;

public class BowSpam
extends Module {
    private final Setting<Boolean> onlyWhenHoldingRightClick;
    private boolean wasHoldingRightClick;
    private boolean wasBow;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> charge;

    public BowSpam() {
        super(Categories.Combat, "bow-spam", "Spams arrows.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.charge = this.sgGeneral.add(new IntSetting.Builder().name("charge").description("How long to charge the bow before releasing in ticks.").defaultValue(5).min(5).max(20).sliderMin(5).sliderMax(20).build());
        this.onlyWhenHoldingRightClick = this.sgGeneral.add(new BoolSetting.Builder().name("when-holding-right-click").description("Works only when holding right click.").defaultValue(false).build());
        this.wasBow = false;
        this.wasHoldingRightClick = false;
    }

    private void setPressed(boolean bl) {
        this.mc.field_1690.field_1904.method_23481(bl);
    }

    @Override
    public void onActivate() {
        this.wasBow = false;
        this.wasHoldingRightClick = false;
    }

    @Override
    public void onDeactivate() {
        this.setPressed(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (InvUtils.findItemWithCount((class_1792)class_1802.field_8107).slot == -1) {
            return;
        }
        if (!this.onlyWhenHoldingRightClick.get().booleanValue() || this.mc.field_1690.field_1904.method_1434()) {
            boolean bl;
            boolean bl2 = bl = this.mc.field_1724.method_6047().method_7909() == class_1802.field_8102;
            if (!bl && this.wasBow) {
                this.setPressed(false);
            }
            this.wasBow = bl;
            if (!bl) {
                return;
            }
            if (this.mc.field_1724.method_6048() >= this.charge.get()) {
                this.mc.field_1724.method_6075();
                this.mc.field_1761.method_2897((class_1657)this.mc.field_1724);
            } else {
                this.setPressed(true);
            }
            this.wasHoldingRightClick = this.mc.field_1690.field_1904.method_1434();
        } else if (this.wasHoldingRightClick) {
            this.setPressed(false);
            this.wasHoldingRightClick = false;
        }
    }
}

