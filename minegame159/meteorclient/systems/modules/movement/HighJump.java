/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.JumpVelocityMultiplierEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class HighJump
extends Module {
    private final /* synthetic */ Setting<Double> multiplier;
    private final /* synthetic */ SettingGroup sgGeneral;

    public HighJump() {
        super(Categories.Movement, "high-jump", "Makes you jump higher than normal.");
        HighJump llIIIIIIIIllIlI;
        llIIIIIIIIllIlI.sgGeneral = llIIIIIIIIllIlI.settings.getDefaultGroup();
        llIIIIIIIIllIlI.multiplier = llIIIIIIIIllIlI.sgGeneral.add(new DoubleSetting.Builder().name("jump-multiplier").description("Jump height multiplier.").defaultValue(1.0).min(0.0).build());
    }

    @EventHandler
    private void onJumpVelocityMultiplier(JumpVelocityMultiplierEvent llIIIIIIIIlIlIl) {
        HighJump llIIIIIIIIlIlII;
        llIIIIIIIIlIlIl.multiplier = (float)((double)llIIIIIIIIlIlIl.multiplier * llIIIIIIIIlIlII.multiplier.get());
    }
}

