/*
 * Decompiled with CFR 0.150.
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> whenStationary;

    @Override
    public void onDeactivate() {
        Sprint llIllIIlIlIIllI;
        llIllIIlIlIIllI.mc.player.setSprinting(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post llIllIIlIlIIIll) {
        Sprint llIllIIlIlIIlII;
        if (llIllIIlIlIIlII.mc.player.forwardSpeed > 0.0f && !llIllIIlIlIIlII.whenStationary.get().booleanValue()) {
            llIllIIlIlIIlII.mc.player.setSprinting(true);
        } else if (llIllIIlIlIIlII.whenStationary.get().booleanValue()) {
            llIllIIlIlIIlII.mc.player.setSprinting(true);
        }
    }

    public Sprint() {
        super(Categories.Movement, "sprint", "Automatically sprints.");
        Sprint llIllIIlIlIlIlI;
        llIllIIlIlIlIlI.sgGeneral = llIllIIlIlIlIlI.settings.getDefaultGroup();
        llIllIIlIlIlIlI.whenStationary = llIllIIlIlIlIlI.sgGeneral.add(new BoolSetting.Builder().name("when-stationary").description("Continues sprinting even if you do not move.").defaultValue(true).build());
    }
}

