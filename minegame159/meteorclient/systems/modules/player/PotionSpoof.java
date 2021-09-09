/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.entity.effect.StatusEffectInstance
 */
package minegame159.meteorclient.systems.modules.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.StatusEffectInstanceAccessor;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StatusEffectSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionSpoof
extends Module {
    private final /* synthetic */ Setting<Object2IntMap<StatusEffect>> potions;
    private final /* synthetic */ SettingGroup sgGeneral;

    public PotionSpoof() {
        super(Categories.Player, "potion-spoof", "Spoofs specified potion effects for you. SOME effects DO NOT work.");
        PotionSpoof lllllllllllllllllIIIlIlIIlllIlII;
        lllllllllllllllllIIIlIlIIlllIlII.sgGeneral = lllllllllllllllllIIIlIlIIlllIlII.settings.getDefaultGroup();
        lllllllllllllllllIIIlIlIIlllIlII.potions = lllllllllllllllllIIIlIlIIlllIlII.sgGeneral.add(new StatusEffectSetting.Builder().name("potions").description("Potions to add.").defaultValue(Utils.createStatusEffectMap()).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIIIlIlIIllIlIIl) {
        PotionSpoof lllllllllllllllllIIIlIlIIllIlIII;
        for (StatusEffect lllllllllllllllllIIIlIlIIllIlIll : lllllllllllllllllIIIlIlIIllIlIII.potions.get().keySet()) {
            int lllllllllllllllllIIIlIlIIllIllII = lllllllllllllllllIIIlIlIIllIlIII.potions.get().getInt((Object)lllllllllllllllllIIIlIlIIllIlIll);
            if (lllllllllllllllllIIIlIlIIllIllII <= 0) continue;
            if (lllllllllllllllllIIIlIlIIllIlIII.mc.player.hasStatusEffect(lllllllllllllllllIIIlIlIIllIlIll)) {
                StatusEffectInstance lllllllllllllllllIIIlIlIIllIllIl = lllllllllllllllllIIIlIlIIllIlIII.mc.player.getStatusEffect(lllllllllllllllllIIIlIlIIllIlIll);
                ((StatusEffectInstanceAccessor)lllllllllllllllllIIIlIlIIllIllIl).setAmplifier(lllllllllllllllllIIIlIlIIllIllII - 1);
                if (lllllllllllllllllIIIlIlIIllIllIl.getDuration() >= 20) continue;
                ((StatusEffectInstanceAccessor)lllllllllllllllllIIIlIlIIllIllIl).setDuration(20);
                continue;
            }
            lllllllllllllllllIIIlIlIIllIlIII.mc.player.addStatusEffect(new StatusEffectInstance(lllllllllllllllllIIIlIlIIllIlIll, 20, lllllllllllllllllIIIlIlIIllIllII - 1));
        }
    }
}

