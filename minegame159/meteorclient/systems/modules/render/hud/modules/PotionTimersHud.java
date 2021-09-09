/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.entity.effect.StatusEffectUtil
 *  net.minecraft.entity.effect.StatusEffectInstance
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
    private final /* synthetic */ Color color;

    public PotionTimersHud(HUD lllllllllllllllllIIlIlIIIIlIIIIl) {
        super(lllllllllllllllllIIlIlIIIIlIIIIl, "potion-timers", "Displays active potion effects with timers.");
        PotionTimersHud lllllllllllllllllIIlIlIIIIlIIIlI;
        lllllllllllllllllIIlIlIIIIlIIIlI.color = new Color();
    }

    @Override
    public void render(HudRenderer lllllllllllllllllIIlIIlllllllIll) {
        PotionTimersHud lllllllllllllllllIIlIIllllllllII;
        double lllllllllllllllllIIlIIlllllllIlI = lllllllllllllllllIIlIIllllllllII.box.getX();
        double lllllllllllllllllIIlIIlllllllIIl = lllllllllllllllllIIlIIllllllllII.box.getY();
        if (lllllllllllllllllIIlIIllllllllII.isInEditor()) {
            lllllllllllllllllIIlIIlllllllIll.text("Potion Timers 0:00", lllllllllllllllllIIlIIlllllllIlI, lllllllllllllllllIIlIIlllllllIIl, lllllllllllllllllIIlIIllllllllII.color);
            return;
        }
        int lllllllllllllllllIIlIIlllllllIII = 0;
        for (StatusEffectInstance lllllllllllllllllIIlIIllllllllIl : lllllllllllllllllIIlIIllllllllII.mc.player.getStatusEffects()) {
            StatusEffect lllllllllllllllllIIlIlIIIIIIIIII = lllllllllllllllllIIlIIllllllllIl.getEffectType();
            int lllllllllllllllllIIlIIllllllllll = lllllllllllllllllIIlIlIIIIIIIIII.getColor();
            lllllllllllllllllIIlIIllllllllII.color.r = Color.toRGBAR(lllllllllllllllllIIlIIllllllllll);
            lllllllllllllllllIIlIIllllllllII.color.g = Color.toRGBAG(lllllllllllllllllIIlIIllllllllll);
            lllllllllllllllllIIlIIllllllllII.color.b = Color.toRGBAB(lllllllllllllllllIIlIIllllllllll);
            String lllllllllllllllllIIlIIlllllllllI = lllllllllllllllllIIlIIllllllllII.getString(lllllllllllllllllIIlIIllllllllIl);
            lllllllllllllllllIIlIIlllllllIll.text(lllllllllllllllllIIlIIlllllllllI, lllllllllllllllllIIlIIlllllllIlI + lllllllllllllllllIIlIIllllllllII.box.alignX(lllllllllllllllllIIlIIlllllllIll.textWidth(lllllllllllllllllIIlIIlllllllllI)), lllllllllllllllllIIlIIlllllllIIl, lllllllllllllllllIIlIIllllllllII.color);
            lllllllllllllllllIIlIIllllllllII.color.b = 255;
            lllllllllllllllllIIlIIllllllllII.color.g = 255;
            lllllllllllllllllIIlIIllllllllII.color.r = 255;
            lllllllllllllllllIIlIIlllllllIIl += lllllllllllllllllIIlIIlllllllIll.textHeight();
            if (lllllllllllllllllIIlIIlllllllIII > 0) {
                lllllllllllllllllIIlIIlllllllIIl += 2.0;
            }
            ++lllllllllllllllllIIlIIlllllllIII;
        }
    }

    private String getString(StatusEffectInstance lllllllllllllllllIIlIIlllllIlIll) {
        return String.format("%s %d (%s)", Names.get(lllllllllllllllllIIlIIlllllIlIll.getEffectType()), lllllllllllllllllIIlIIlllllIlIll.getAmplifier() + 1, StatusEffectUtil.durationToString((StatusEffectInstance)lllllllllllllllllIIlIIlllllIlIll, (float)1.0f));
    }

    @Override
    public void update(HudRenderer lllllllllllllllllIIlIlIIIIIlIIII) {
        PotionTimersHud lllllllllllllllllIIlIlIIIIIlIllI;
        if (lllllllllllllllllIIlIlIIIIIlIllI.isInEditor()) {
            lllllllllllllllllIIlIlIIIIIlIllI.box.setSize(lllllllllllllllllIIlIlIIIIIlIIII.textWidth("Potion Timers 0:00"), lllllllllllllllllIIlIlIIIIIlIIII.textHeight());
            return;
        }
        double lllllllllllllllllIIlIlIIIIIlIlII = 0.0;
        double lllllllllllllllllIIlIlIIIIIlIIll = 0.0;
        int lllllllllllllllllIIlIlIIIIIlIIlI = 0;
        for (StatusEffectInstance lllllllllllllllllIIlIlIIIIIlIlll : lllllllllllllllllIIlIlIIIIIlIllI.mc.player.getStatusEffects()) {
            lllllllllllllllllIIlIlIIIIIlIlII = Math.max(lllllllllllllllllIIlIlIIIIIlIlII, lllllllllllllllllIIlIlIIIIIlIIII.textWidth(lllllllllllllllllIIlIlIIIIIlIllI.getString(lllllllllllllllllIIlIlIIIIIlIlll)));
            lllllllllllllllllIIlIlIIIIIlIIll += lllllllllllllllllIIlIlIIIIIlIIII.textHeight();
            if (lllllllllllllllllIIlIlIIIIIlIIlI > 0) {
                lllllllllllllllllIIlIlIIIIIlIIll += 2.0;
            }
            ++lllllllllllllllllIIlIlIIIIIlIIlI;
        }
        lllllllllllllllllIIlIlIIIIIlIllI.box.setSize(lllllllllllllllllIIlIlIIIIIlIlII, lllllllllllllllllIIlIlIIIIIlIIll);
    }
}

