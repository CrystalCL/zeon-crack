/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.misc.Names;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionTimersHud
extends HudElement {
    private final Color color = new Color();

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX();
        double d2 = this.box.getY();
        if (this.isInEditor()) {
            hudRenderer.text("Potion Timers 0:00", d, d2, this.color);
            return;
        }
        int n = 0;
        for (StatusEffectInstance StatusEffectInstance2 : this.mc.player.getStatusEffects()) {
            StatusEffect StatusEffect2 = StatusEffectInstance2.getEffectType();
            int n2 = StatusEffect2.getColor();
            this.color.r = Color.toRGBAR(n2);
            this.color.g = Color.toRGBAG(n2);
            this.color.b = Color.toRGBAB(n2);
            String string = this.getString(StatusEffectInstance2);
            hudRenderer.text(string, d + this.box.alignX(hudRenderer.textWidth(string)), d2, this.color);
            this.color.b = 255;
            this.color.g = 255;
            this.color.r = 255;
            d2 += hudRenderer.textHeight();
            if (n > 0) {
                d2 += 2.0;
            }
            ++n;
        }
    }

    public PotionTimersHud(HUD hUD) {
        super(hUD, "potion-timers", "Displays active potion effects with timers.");
    }

    @Override
    public void update(HudRenderer hudRenderer) {
        if (this.isInEditor()) {
            this.box.setSize(hudRenderer.textWidth("Potion Timers 0:00"), hudRenderer.textHeight());
            return;
        }
        double d = 0.0;
        double d2 = 0.0;
        int n = 0;
        for (StatusEffectInstance StatusEffectInstance2 : this.mc.player.getStatusEffects()) {
            d = Math.max(d, hudRenderer.textWidth(this.getString(StatusEffectInstance2)));
            d2 += hudRenderer.textHeight();
            if (n > 0) {
                d2 += 2.0;
            }
            ++n;
        }
        this.box.setSize(d, d2);
    }

    private String getString(StatusEffectInstance StatusEffectInstance2) {
        return String.format("%s %d (%s)", Names.get(StatusEffectInstance2.getEffectType()), StatusEffectInstance2.getAmplifier() + 1, StatusEffectUtil.durationToString((StatusEffectInstance)StatusEffectInstance2, (float)1.0f));
    }
}

