/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.ClientPlayerEntityAccessor;
import minegame159.meteorclient.mixininterface.IHorseBaseEntity;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;

public class EntityControl
extends Module {
    private final Setting<Boolean> maxJump;
    private final SettingGroup sgGeneral;

    public EntityControl() {
        super(Categories.Movement, "entity-control", "Lets you control rideable entities without a saddle.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.maxJump = this.sgGeneral.add(new BoolSetting.Builder().name("max-jump").description("Sets jump power to maximum.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof HorseBaseEntity)) continue;
            ((IHorseBaseEntity)Entity2).setSaddled(true);
        }
        if (this.maxJump.get().booleanValue()) {
            ((ClientPlayerEntityAccessor)this.mc.player).setMountJumpStrength(1.0f);
        }
    }

    @Override
    public void onDeactivate() {
        if (!Utils.canUpdate() || this.mc.world.getEntities() == null) {
            return;
        }
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof HorseBaseEntity)) continue;
            ((IHorseBaseEntity)Entity2).setSaddled(false);
        }
    }
}

