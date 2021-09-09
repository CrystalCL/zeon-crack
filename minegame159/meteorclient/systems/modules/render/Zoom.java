/*
 * Decompiled with CFR 0.150.
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
    private /* synthetic */ double value;
    private /* synthetic */ double preMouseSensitivity;
    private /* synthetic */ boolean preCinematic;
    private final /* synthetic */ Setting<Double> zoom;
    private final /* synthetic */ Setting<Boolean> cinematic;
    private final /* synthetic */ Setting<Double> scrollSensitivity;
    private /* synthetic */ double lastFov;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIllIllIlIIIllI) {
        Zoom llllllllllllllllllIllIllIlIIIlIl;
        llllllllllllllllllIllIllIlIIIlIl.mc.options.smoothCameraEnabled = llllllllllllllllllIllIllIlIIIlIl.cinematic.get();
        if (!llllllllllllllllllIllIllIlIIIlIl.cinematic.get().booleanValue()) {
            llllllllllllllllllIllIllIlIIIlIl.mc.options.mouseSensitivity = llllllllllllllllllIllIllIlIIIlIl.preMouseSensitivity / Math.max(llllllllllllllllllIllIllIlIIIlIl.value * 0.5, 1.0);
        }
    }

    @EventHandler
    private void onGetFov(GetFovEvent llllllllllllllllllIllIllIIlllIll) {
        Zoom llllllllllllllllllIllIllIIllllII;
        llllllllllllllllllIllIllIIlllIll.fov /= llllllllllllllllllIllIllIIllllII.value;
        if (llllllllllllllllllIllIllIIllllII.lastFov != llllllllllllllllllIllIllIIlllIll.fov) {
            llllllllllllllllllIllIllIIllllII.mc.worldRenderer.scheduleTerrainUpdate();
        }
        llllllllllllllllllIllIllIIllllII.lastFov = llllllllllllllllllIllIllIIlllIll.fov;
    }

    @Override
    public void onActivate() {
        Zoom llllllllllllllllllIllIllIlIIllIl;
        llllllllllllllllllIllIllIlIIllIl.preCinematic = llllllllllllllllllIllIllIlIIllIl.mc.options.smoothCameraEnabled;
        llllllllllllllllllIllIllIlIIllIl.preMouseSensitivity = llllllllllllllllllIllIllIlIIllIl.mc.options.mouseSensitivity;
        llllllllllllllllllIllIllIlIIllIl.value = llllllllllllllllllIllIllIlIIllIl.zoom.get();
        llllllllllllllllllIllIllIlIIllIl.lastFov = llllllllllllllllllIllIllIlIIllIl.mc.options.fov;
    }

    public Zoom() {
        super(Categories.Render, "zoom", "Zooms your view.");
        Zoom llllllllllllllllllIllIllIlIIllll;
        llllllllllllllllllIllIllIlIIllll.sgGeneral = llllllllllllllllllIllIllIlIIllll.settings.getDefaultGroup();
        llllllllllllllllllIllIllIlIIllll.zoom = llllllllllllllllllIllIllIlIIllll.sgGeneral.add(new DoubleSetting.Builder().name("zoom").description("How much to zoom.").defaultValue(6.0).min(1.0).build());
        llllllllllllllllllIllIllIlIIllll.scrollSensitivity = llllllllllllllllllIllIllIlIIllll.sgGeneral.add(new DoubleSetting.Builder().name("scroll-sensitivity").description("Allows you to change zoom value using scroll wheel. 0 to disable.").defaultValue(1.0).min(0.0).build());
        llllllllllllllllllIllIllIlIIllll.cinematic = llllllllllllllllllIllIllIlIIllll.sgGeneral.add(new BoolSetting.Builder().name("cinematic").description("Enables cinematic camera.").defaultValue(false).build());
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent llllllllllllllllllIllIllIIllllll) {
        Zoom llllllllllllllllllIllIllIlIIIIlI;
        if (llllllllllllllllllIllIllIlIIIIlI.scrollSensitivity.get() > 0.0) {
            llllllllllllllllllIllIllIlIIIIlI.value += llllllllllllllllllIllIllIIllllll.value * 0.25 * (llllllllllllllllllIllIllIlIIIIlI.scrollSensitivity.get() * llllllllllllllllllIllIllIlIIIIlI.value);
            if (llllllllllllllllllIllIllIlIIIIlI.value < 1.0) {
                llllllllllllllllllIllIllIlIIIIlI.value = 1.0;
            }
            llllllllllllllllllIllIllIIllllll.cancel();
        }
    }

    @Override
    public void onDeactivate() {
        Zoom llllllllllllllllllIllIllIlIIlIlI;
        llllllllllllllllllIllIllIlIIlIlI.mc.options.smoothCameraEnabled = llllllllllllllllllIllIllIlIIlIlI.preCinematic;
        llllllllllllllllllIllIllIlIIlIlI.mc.options.mouseSensitivity = llllllllllllllllllIllIllIlIIlIlI.preMouseSensitivity;
        llllllllllllllllllIllIllIlIIlIlI.mc.worldRenderer.scheduleTerrainUpdate();
    }
}

