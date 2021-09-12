/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_1297;
import net.minecraft.class_2596;
import net.minecraft.class_2848;

public class NoSlow
extends Module {
    private final Setting<Boolean> items;
    private final Setting<Boolean> airStrict;
    private final Setting<Boolean> slimeBlock;
    private class_2848 STOP;
    private final Setting<Boolean> web;
    private final Setting<Boolean> soulSand;
    private class_2848 START;
    private boolean shouldSneak;
    private final SettingGroup sgGeneral;

    public boolean slimeBlock() {
        return this.isActive() && this.slimeBlock.get() != false;
    }

    public boolean items() {
        return this.isActive() && this.items.get() != false;
    }

    public boolean web() {
        return this.isActive() && this.web.get() != false;
    }

    public NoSlow() {
        super(Categories.Movement, "no-slow", "Allows you to move normally when using objects that will slow you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.items = this.sgGeneral.add(new BoolSetting.Builder().name("items").description("Whether or not using items will slow you.").defaultValue(true).build());
        this.web = this.sgGeneral.add(new BoolSetting.Builder().name("web").description("Whether or not cobwebs will slow you.").defaultValue(true).build());
        this.soulSand = this.sgGeneral.add(new BoolSetting.Builder().name("soul-sand").description("Whether or not Soul Sand will slow you.").defaultValue(true).build());
        this.slimeBlock = this.sgGeneral.add(new BoolSetting.Builder().name("slime-block").description("Whether or not slime blocks will slow you.").defaultValue(true).build());
        this.airStrict = this.sgGeneral.add(new BoolSetting.Builder().name("air-strict").description("Will attempt to bypass anti-cheats like 2b2t's. Only works while in air.").defaultValue(false).build());
        this.shouldSneak = false;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre pre) {
        if (!this.airStrict.get().booleanValue()) {
            return;
        }
        if (this.mc.field_1724.method_6115()) {
            this.mc.field_1724.field_3944.method_2883((class_2596)this.START);
            this.shouldSneak = true;
        } else if (this.shouldSneak && !this.mc.field_1724.method_6115()) {
            this.mc.field_1724.field_3944.method_2883((class_2596)this.STOP);
            this.shouldSneak = false;
        }
    }

    public boolean soulSand() {
        return this.isActive() && this.soulSand.get() != false;
    }

    @Override
    public void onActivate() {
        this.START = new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12979);
        this.STOP = new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12984);
    }
}

