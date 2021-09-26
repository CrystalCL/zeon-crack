/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Object2IntMap<StatusEffect>> potions;
    private final SettingGroup sgGeneral;

    public PotionSpoof() {
        super(Categories.Player, "potion-spoof", "Spoofs specified potion effects for you. SOME effects DO NOT work.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.potions = this.sgGeneral.add(new StatusEffectSetting.Builder().name("potions").description("Potions to add.").defaultValue(Utils.createStatusEffectMap()).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        for (StatusEffect StatusEffect2 : this.potions.get().keySet()) {
            int n = this.potions.get().getInt((Object)StatusEffect2);
            if (n <= 0) continue;
            if (this.mc.player.hasStatusEffect(StatusEffect2)) {
                StatusEffectInstance StatusEffectInstance2 = this.mc.player.getStatusEffect(StatusEffect2);
                ((StatusEffectInstanceAccessor)StatusEffectInstance2).setAmplifier(n - 1);
                if (StatusEffectInstance2.getDuration() >= 20) continue;
                ((StatusEffectInstanceAccessor)StatusEffectInstance2).setDuration(20);
                continue;
            }
            this.mc.player.addStatusEffect(new StatusEffectInstance(StatusEffect2, 20, n - 1));
        }
    }
}

