/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.MouseScrollEvent;
import minegame159.meteorclient.events.render.GetFovEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class Zoom
extends Module {
    private double preMouseSensitivity;
    private final Setting<Double> scrollSensitivity;
    private boolean preCinematic;
    private final SettingGroup sgGeneral;
    private double value;
    private double lastFov;
    private final Setting<Boolean> cinematic;
    private final Setting<Double> zoom;

    @Override
    public void onActivate() {
        this.preCinematic = this.mc.options.smoothCameraEnabled;
        this.preMouseSensitivity = this.mc.options.mouseSensitivity;
        this.value = this.zoom.get();
        this.lastFov = this.mc.options.fov;
    }

    @Override
    public void onDeactivate() {
        this.mc.options.smoothCameraEnabled = this.preCinematic;
        this.mc.options.mouseSensitivity = this.preMouseSensitivity;
        this.mc.worldRenderer.scheduleTerrainUpdate();
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent mouseScrollEvent) {
        if (this.scrollSensitivity.get() > 0.0) {
            this.value += mouseScrollEvent.value * 0.25 * (this.scrollSensitivity.get() * this.value);
            if (this.value < 1.0) {
                this.value = 1.0;
            }
            mouseScrollEvent.cancel();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.mc.options.smoothCameraEnabled = this.cinematic.get();
        if (!this.cinematic.get().booleanValue()) {
            this.mc.options.mouseSensitivity = this.preMouseSensitivity / Math.max(this.value * 0.5, 1.0);
        }
    }

    public Zoom() {
        super(Categories.Render, "zoom", "Zooms your view.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.zoom = this.sgGeneral.add(new DoubleSetting.Builder().name("zoom").description("How much to zoom.").defaultValue(6.0).min(1.0).build());
        this.scrollSensitivity = this.sgGeneral.add(new DoubleSetting.Builder().name("scroll-sensitivity").description("Allows you to change zoom value using scroll wheel. 0 to disable.").defaultValue(1.0).min(0.0).build());
        this.cinematic = this.sgGeneral.add(new BoolSetting.Builder().name("cinematic").description("Enables cinematic camera.").defaultValue(false).build());
    }

    @EventHandler
    private void onGetFov(GetFovEvent getFovEvent) {
        getFovEvent.fov /= this.value;
        if (this.lastFov != getFovEvent.fov) {
            this.mc.worldRenderer.scheduleTerrainUpdate();
        }
        this.lastFov = getFovEvent.fov;
    }
}

