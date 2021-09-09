/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.systems.modules.movement.speed;

import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.speed.Speed;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedModes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.client.MinecraftClient;

public class SpeedMode {
    protected final /* synthetic */ Speed settings;
    protected /* synthetic */ int stage;
    protected /* synthetic */ double speed;
    protected final /* synthetic */ MinecraftClient mc;
    protected /* synthetic */ double distance;
    private final /* synthetic */ SpeedModes type;

    protected double getHop(double lllllIIIIIllI) {
        SpeedMode lllllIIIIIlII;
        StatusEffectInstance lllllIIIIIlIl;
        StatusEffectInstance StatusEffectInstance2 = lllllIIIIIlIl = lllllIIIIIlII.mc.player.hasStatusEffect(StatusEffects.JUMP_BOOST) ? lllllIIIIIlII.mc.player.getStatusEffect(StatusEffects.JUMP_BOOST) : null;
        if (lllllIIIIIlIl != null) {
            lllllIIIIIllI += (double)((float)lllllIIIIIlIl.getAmplifier() + 0.1f);
        }
        return lllllIIIIIllI;
    }

    public void onRubberband() {
        SpeedMode lllllIIIllIlI;
        lllllIIIllIlI.reset();
    }

    public String getHudString() {
        SpeedMode lllllIIIIIIII;
        return lllllIIIIIIII.type.name();
    }

    public SpeedMode(SpeedModes lllllIIlIIIlI) {
        SpeedMode lllllIIlIIIll;
        lllllIIlIIIll.settings = Modules.get().get(Speed.class);
        lllllIIlIIIll.mc = MinecraftClient.getInstance();
        lllllIIlIIIll.type = lllllIIlIIIlI;
        lllllIIlIIIll.reset();
    }

    public void onTick() {
    }

    public void onDeactivate() {
    }

    public void onActivate() {
    }

    protected double getDefaultSpeed() {
        SpeedMode lllllIIIlIIlI;
        double lllllIIIlIIIl = 0.2873;
        if (lllllIIIlIIlI.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            int lllllIIIlIlII = lllllIIIlIIlI.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            lllllIIIlIIIl *= 1.0 + 0.2 * (double)(lllllIIIlIlII + 1);
        }
        if (lllllIIIlIIlI.mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            int lllllIIIlIIll = lllllIIIlIIlI.mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
            lllllIIIlIIIl /= 1.0 + 0.2 * (double)(lllllIIIlIIll + 1);
        }
        return lllllIIIlIIIl;
    }

    protected void reset() {
        lllllIIIIlIll.stage = 0;
        lllllIIIIlIll.distance = 0.0;
        lllllIIIIlIll.speed = 0.2873;
    }

    public void onMove(PlayerMoveEvent lllllIIIlllIl) {
    }
}

