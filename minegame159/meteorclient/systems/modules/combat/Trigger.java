/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ Entity target;
    private final /* synthetic */ Setting<Boolean> whenHoldingLeftClick;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIlIlIlIlIIllIl) {
        Trigger llllllllllllllllllIlIlIlIlIIlllI;
        llllllllllllllllllIlIlIlIlIIlllI.target = null;
        if (llllllllllllllllllIlIlIlIlIIlllI.mc.player.getHealth() <= 0.0f || llllllllllllllllllIlIlIlIlIIlllI.mc.player.getAttackCooldownProgress(0.5f) < 1.0f) {
            return;
        }
        if (!(llllllllllllllllllIlIlIlIlIIlllI.mc.targetedEntity instanceof LivingEntity)) {
            return;
        }
        if (((LivingEntity)llllllllllllllllllIlIlIlIlIIlllI.mc.targetedEntity).getHealth() <= 0.0f) {
            return;
        }
        llllllllllllllllllIlIlIlIlIIlllI.target = llllllllllllllllllIlIlIlIlIIlllI.mc.targetedEntity;
        if (llllllllllllllllllIlIlIlIlIIlllI.whenHoldingLeftClick.get().booleanValue()) {
            if (llllllllllllllllllIlIlIlIlIIlllI.mc.options.keyAttack.isPressed()) {
                llllllllllllllllllIlIlIlIlIIlllI.attack(llllllllllllllllllIlIlIlIlIIlllI.target);
            }
        } else {
            llllllllllllllllllIlIlIlIlIIlllI.attack(llllllllllllllllllIlIlIlIlIIlllI.target);
        }
    }

    public Trigger() {
        super(Categories.Combat, "trigger", "Automatically swings when you look at entities.");
        Trigger llllllllllllllllllIlIlIlIlIlIIIl;
        llllllllllllllllllIlIlIlIlIlIIIl.sgGeneral = llllllllllllllllllIlIlIlIlIlIIIl.settings.getDefaultGroup();
        llllllllllllllllllIlIlIlIlIlIIIl.whenHoldingLeftClick = llllllllllllllllllIlIlIlIlIlIIIl.sgGeneral.add(new BoolSetting.Builder().name("when-holding-left-click").description("Attacks only when you are holding left click.").defaultValue(false).build());
    }

    @Override
    public String getInfoString() {
        Trigger llllllllllllllllllIlIlIlIlIIIlII;
        if (llllllllllllllllllIlIlIlIlIIIlII.target != null && llllllllllllllllllIlIlIlIlIIIlII.target instanceof PlayerEntity) {
            return llllllllllllllllllIlIlIlIlIIIlII.target.getEntityName();
        }
        if (llllllllllllllllllIlIlIlIlIIIlII.target != null) {
            return llllllllllllllllllIlIlIlIlIIIlII.target.getType().getName().getString();
        }
        return null;
    }

    private void attack(Entity llllllllllllllllllIlIlIlIlIIlIII) {
        Trigger llllllllllllllllllIlIlIlIlIIlIIl;
        llllllllllllllllllIlIlIlIlIIlIIl.mc.interactionManager.attackEntity((PlayerEntity)llllllllllllllllllIlIlIlIlIIlIIl.mc.player, llllllllllllllllllIlIlIlIlIIlIII);
        llllllllllllllllllIlIlIlIlIIlIIl.mc.player.swingHand(Hand.MAIN_HAND);
    }
}

