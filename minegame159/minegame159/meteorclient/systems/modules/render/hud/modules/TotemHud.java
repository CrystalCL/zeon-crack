/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.render.RenderUtils;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;

public class TotemHud
extends HudElement {
    private final SettingGroup sgGeneral;
    private final Setting<Double> scale;

    @Override
    public void update(HudRenderer hudRenderer) {
        this.box.setSize(16.0 * this.scale.get(), 16.0 * this.scale.get());
    }

    public TotemHud(HUD hUD) {
        super(hUD, "totems", "Displays the amount of totems in your inventory.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of totem counter.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX() / this.scale.get();
        double d2 = this.box.getY() / this.scale.get();
        if (this.isInEditor()) {
            RenderUtils.drawItem(class_1802.field_8288.method_7854(), (int)d, (int)d2, this.scale.get(), true);
        } else if (InvUtils.findItemWithCount((class_1792)class_1802.field_8288).count > 0) {
            RenderUtils.drawItem(new class_1799((class_1935)class_1802.field_8288, InvUtils.findItemWithCount((class_1792)class_1802.field_8288).count), (int)d, (int)d2, this.scale.get(), true);
        }
    }
}

