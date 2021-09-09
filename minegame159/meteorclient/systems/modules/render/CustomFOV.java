/*
 * Decompiled with CFR 0.150.
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
    private final /* synthetic */ Setting<Integer> fov;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ float _fov;

    public CustomFOV() {
        super(Categories.Render, "custom-fov", "Allows your FOV to be more customizable.");
        CustomFOV lllllllllllllllllIlIIIlIlIIlllII;
        lllllllllllllllllIlIIIlIlIIlllII.sgGeneral = lllllllllllllllllIlIIIlIlIIlllII.settings.getDefaultGroup();
        lllllllllllllllllIlIIIlIlIIlllII.fov = lllllllllllllllllIlIIIlIlIIlllII.sgGeneral.add(new IntSetting.Builder().name("fOV").description("Your custom FOV.").defaultValue(100).sliderMin(1).sliderMax(179).build());
    }

    @Override
    public void onActivate() {
        CustomFOV lllllllllllllllllIlIIIlIlIIllIlI;
        lllllllllllllllllIlIIIlIlIIllIlI._fov = (float)lllllllllllllllllIlIIIlIlIIllIlI.mc.options.fov;
        lllllllllllllllllIlIIIlIlIIllIlI.mc.options.fov = lllllllllllllllllIlIIIlIlIIllIlI.fov.get().intValue();
    }

    public void getFOV() {
        CustomFOV lllllllllllllllllIlIIIlIlIIlIllI;
        lllllllllllllllllIlIIIlIlIIlIllI.mc.options.fov = lllllllllllllllllIlIIIlIlIIlIllI.fov.get().intValue();
    }

    @Override
    public void onDeactivate() {
        CustomFOV lllllllllllllllllIlIIIlIlIIIllll;
        lllllllllllllllllIlIIIlIlIIIllll.mc.options.fov = lllllllllllllllllIlIIIlIlIIIllll._fov;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIIIlIlIIlIIll) {
        CustomFOV lllllllllllllllllIlIIIlIlIIlIlII;
        if ((double)lllllllllllllllllIlIIIlIlIIlIlII.fov.get().intValue() != lllllllllllllllllIlIIIlIlIIlIlII.mc.options.fov) {
            lllllllllllllllllIlIIIlIlIIlIlII.getFOV();
        }
    }
}

