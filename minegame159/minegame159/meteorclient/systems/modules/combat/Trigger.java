/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Trigger
extends Module {
    private final Setting<Boolean> whenHoldingLeftClick;
    private final SettingGroup sgGeneral;
    private Entity target;

    private void attack(Entity Entity2) {
        this.mc.interactionManager.attackEntity((PlayerEntity)this.mc.player, Entity2);
        this.mc.player.swingHand(Hand.MAIN_HAND);
    }

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof PlayerEntity) {
            return this.target.getEntityName();
        }
        if (this.target != null) {
            return this.target.getType().getName().getString();
        }
        return null;
    }

    public Trigger() {
        super(Categories.Combat, "trigger", "Automatically swings when you look at entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.whenHoldingLeftClick = this.sgGeneral.add(new BoolSetting.Builder().name("when-holding-left-click").description("Attacks only when you are holding left click.").defaultValue(false).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.target = null;
        if (this.mc.player.getHealth() <= 0.0f || this.mc.player.getAttackCooldownProgress(0.5f) < 1.0f) {
            return;
        }
        if (!(this.mc.targetedEntity instanceof LivingEntity)) {
            return;
        }
        if (((LivingEntity)this.mc.targetedEntity).getHealth() <= 0.0f) {
            return;
        }
        this.target = this.mc.targetedEntity;
        if (this.whenHoldingLeftClick.get().booleanValue()) {
            if (this.mc.options.keyAttack.isPressed()) {
                this.attack(this.target);
            }
        } else {
            this.attack(this.target);
        }
    }
}

