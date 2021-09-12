/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class CustomFOV
extends Module {
    private final Setting<Integer> fov;
    private final SettingGroup sgGeneral;
    private float _fov;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if ((double)this.fov.get().intValue() != this.mc.field_1690.field_1826) {
            this.getFOV();
        }
    }

    public CustomFOV() {
        super(Categories.Render, "custom-fov", "Allows your FOV to be more customizable.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.fov = this.sgGeneral.add(new IntSetting.Builder().name("fOV").description("Your custom FOV.").defaultValue(100).sliderMin(1).sliderMax(179).build());
    }

    @Override
    public void onActivate() {
        this._fov = (float)this.mc.field_1690.field_1826;
        this.mc.field_1690.field_1826 = this.fov.get().intValue();
    }

    @Override
    public void onDeactivate() {
        this.mc.field_1690.field_1826 = this._fov;
    }

    public void getFOV() {
        this.mc.field_1690.field_1826 = this.fov.get().intValue();
    }
}

