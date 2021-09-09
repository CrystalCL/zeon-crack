/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.world;

import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class Timer
extends Module {
    private /* synthetic */ double override;
    private final /* synthetic */ Setting<Double> multiplier;
    public static final /* synthetic */ double OFF = 1.0;
    private final /* synthetic */ SettingGroup sgGeneral;

    public Timer() {
        super(Categories.World, "timer", "Changes the speed of everything in your game.");
        Timer llllIIlIIllIIl;
        llllIIlIIllIIl.sgGeneral = llllIIlIIllIIl.settings.getDefaultGroup();
        llllIIlIIllIIl.multiplier = llllIIlIIllIIl.sgGeneral.add(new DoubleSetting.Builder().name("multiplier").description("The timer multiplier amount.").defaultValue(1.0).min(0.1).sliderMin(0.1).sliderMax(10.0).build());
        llllIIlIIllIIl.override = 1.0;
    }

    public void setOverride(double llllIIlIIlIIIl) {
        llllIIlIIlIIlI.override = llllIIlIIlIIIl;
    }

    public double getMultiplier() {
        Timer llllIIlIIlIllI;
        return llllIIlIIlIllI.override != 1.0 ? llllIIlIIlIllI.override : (llllIIlIIlIllI.isActive() ? llllIIlIIlIllI.multiplier.get() : 1.0);
    }
}

