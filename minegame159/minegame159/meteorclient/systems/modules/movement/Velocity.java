/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Double> horizontal;
    public final Setting<Boolean> explosions;
    private final SettingGroup sgDefault;
    private final Setting<Double> vertical;
    public final Setting<Boolean> entities;
    public final Setting<Boolean> liquids;

    public double getHorizontal() {
        return this.isActive() ? this.horizontal.get() : 1.0;
    }

    public Velocity() {
        super(Categories.Movement, "velocity", "Prevents you from being moved by external forces.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.entities = this.sgDefault.add(new BoolSetting.Builder().name("entities").description("Modifies the amount of knockback you take from entities and attacks.").defaultValue(true).build());
        this.explosions = this.sgDefault.add(new BoolSetting.Builder().name("explosions").description("Modifies your knockback from explosions.").defaultValue(true).build());
        this.liquids = this.sgDefault.add(new BoolSetting.Builder().name("liquids").description("Modifies the amount you are pushed by flowing liquids.").defaultValue(false).build());
        this.horizontal = this.sgDefault.add(new DoubleSetting.Builder().name("horizontal-multiplier").description("How much velocity you will take horizontally.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
        this.vertical = this.sgDefault.add(new DoubleSetting.Builder().name("vertical-multiplier").description("How much velocity you will take vertically.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
    }

    public double getVertical() {
        return this.isActive() ? this.vertical.get() : 1.0;
    }
}

