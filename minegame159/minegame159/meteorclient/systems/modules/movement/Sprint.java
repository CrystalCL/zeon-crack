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

public class Sprint
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> whenStationary;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.forwardSpeed > 0.0f && !this.whenStationary.get().booleanValue()) {
            this.mc.player.setSprinting(true);
        } else if (this.whenStationary.get().booleanValue()) {
            this.mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDeactivate() {
        this.mc.player.setSprinting(false);
    }

    public Sprint() {
        super(Categories.Movement, "sprint", "Automatically sprints.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.whenStationary = this.sgGeneral.add(new BoolSetting.Builder().name("when-stationary").description("Continues sprinting even if you do not move.").defaultValue(true).build());
    }
}

