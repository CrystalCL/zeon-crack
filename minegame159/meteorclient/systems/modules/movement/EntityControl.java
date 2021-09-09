/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.HorseBaseEntity
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> maxJump;

    @Override
    public void onDeactivate() {
        EntityControl llllllllllllllllIlllIllllIlllllI;
        if (!Utils.canUpdate() || llllllllllllllllIlllIllllIlllllI.mc.world.getEntities() == null) {
            return;
        }
        for (Entity llllllllllllllllIlllIlllllIIIIII : llllllllllllllllIlllIllllIlllllI.mc.world.getEntities()) {
            if (!(llllllllllllllllIlllIlllllIIIIII instanceof HorseBaseEntity)) continue;
            ((IHorseBaseEntity)llllllllllllllllIlllIlllllIIIIII).setSaddled(false);
        }
    }

    public EntityControl() {
        super(Categories.Movement, "entity-control", "Lets you control rideable entities without a saddle.");
        EntityControl llllllllllllllllIlllIlllllIIIlII;
        llllllllllllllllIlllIlllllIIIlII.sgGeneral = llllllllllllllllIlllIlllllIIIlII.settings.getDefaultGroup();
        llllllllllllllllIlllIlllllIIIlII.maxJump = llllllllllllllllIlllIlllllIIIlII.sgGeneral.add(new BoolSetting.Builder().name("max-jump").description("Sets jump power to maximum.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIlllIllllIllIllI) {
        EntityControl llllllllllllllllIlllIllllIllIlll;
        for (Entity llllllllllllllllIlllIllllIlllIII : llllllllllllllllIlllIllllIllIlll.mc.world.getEntities()) {
            if (!(llllllllllllllllIlllIllllIlllIII instanceof HorseBaseEntity)) continue;
            ((IHorseBaseEntity)llllllllllllllllIlllIllllIlllIII).setSaddled(true);
        }
        if (llllllllllllllllIlllIllllIllIlll.maxJump.get().booleanValue()) {
            ((ClientPlayerEntityAccessor)llllllllllllllllIlllIllllIllIlll.mc.player).setMountJumpStrength(1.0f);
        }
    }
}

