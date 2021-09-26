/*
 * Decompiled with CFR 0.151.
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
    protected double distance;
    protected final Speed settings = Modules.get().get(Speed.class);
    protected double speed;
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final SpeedModes type;
    protected int stage;

    public void onMove(PlayerMoveEvent playerMoveEvent) {
    }

    public String getHudString() {
        return this.type.name();
    }

    public void onDeactivate() {
    }

    public void onTick() {
    }

    public void onRubberband() {
        this.reset();
    }

    protected double getDefaultSpeed() {
        int n;
        double d = 0.2873;
        if (this.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            n = this.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        if (this.mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            n = this.mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
            d /= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }

    public SpeedMode(SpeedModes speedModes) {
        this.type = speedModes;
        this.reset();
    }

    protected void reset() {
        this.stage = 0;
        this.distance = 0.0;
        this.speed = 0.2873;
    }

    protected double getHop(double d) {
        StatusEffectInstance StatusEffectInstance2;
        StatusEffectInstance StatusEffectInstance3 = StatusEffectInstance2 = this.mc.player.hasStatusEffect(StatusEffects.JUMP_BOOST) ? this.mc.player.getStatusEffect(StatusEffects.JUMP_BOOST) : null;
        if (StatusEffectInstance2 != null) {
            d += (double)((float)StatusEffectInstance2.getAmplifier() + 0.1f);
        }
        return d;
    }

    public void onActivate() {
    }
}

