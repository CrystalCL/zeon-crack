/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class Velocity
extends Module {
    private final /* synthetic */ Setting<Double> horizontal;
    private final /* synthetic */ SettingGroup sgDefault;
    public final /* synthetic */ Setting<Boolean> liquids;
    private final /* synthetic */ Setting<Double> vertical;
    public final /* synthetic */ Setting<Boolean> explosions;
    public final /* synthetic */ Setting<Boolean> entities;

    public double getVertical() {
        Velocity lllllllllllllllllIIlIlIllllllIlI;
        return lllllllllllllllllIIlIlIllllllIlI.isActive() ? lllllllllllllllllIIlIlIllllllIlI.vertical.get() : 1.0;
    }

    public Velocity() {
        super(Categories.Movement, "velocity", "Prevents you from being moved by external forces.");
        Velocity lllllllllllllllllIIlIllIIIIIIIIl;
        lllllllllllllllllIIlIllIIIIIIIIl.sgDefault = lllllllllllllllllIIlIllIIIIIIIIl.settings.getDefaultGroup();
        lllllllllllllllllIIlIllIIIIIIIIl.entities = lllllllllllllllllIIlIllIIIIIIIIl.sgDefault.add(new BoolSetting.Builder().name("entities").description("Modifies the amount of knockback you take from entities and attacks.").defaultValue(true).build());
        lllllllllllllllllIIlIllIIIIIIIIl.explosions = lllllllllllllllllIIlIllIIIIIIIIl.sgDefault.add(new BoolSetting.Builder().name("explosions").description("Modifies your knockback from explosions.").defaultValue(true).build());
        lllllllllllllllllIIlIllIIIIIIIIl.liquids = lllllllllllllllllIIlIllIIIIIIIIl.sgDefault.add(new BoolSetting.Builder().name("liquids").description("Modifies the amount you are pushed by flowing liquids.").defaultValue(false).build());
        lllllllllllllllllIIlIllIIIIIIIIl.horizontal = lllllllllllllllllIIlIllIIIIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("horizontal-multiplier").description("How much velocity you will take horizontally.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
        lllllllllllllllllIIlIllIIIIIIIIl.vertical = lllllllllllllllllIIlIllIIIIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("vertical-multiplier").description("How much velocity you will take vertically.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
    }

    public double getHorizontal() {
        Velocity lllllllllllllllllIIlIlIlllllllIl;
        return lllllllllllllllllIIlIlIlllllllIl.isActive() ? lllllllllllllllllIIlIlIlllllllIl.horizontal.get() : 1.0;
    }
}

