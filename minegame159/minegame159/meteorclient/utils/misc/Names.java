/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.ResourcePacksReloadedEvent;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ChatUtil;
import org.apache.commons.lang3.text.WordUtils;

public class Names {
    private static final Map<ParticleType<?>, String> particleTypesNames;
    private static final Map<Enchantment, String> enchantmentNames;
    private static final Map<Item, String> itemNames;
    private static final Map<StatusEffect, String> statusEffectNames;
    private static final Map<Block, String> blockNames;
    private static final Map<EntityType<?>, String> entityTypeNames;
    private static final Map<Identifier, String> soundNames;

    public static String get(StatusEffect StatusEffect2) {
        return statusEffectNames.computeIfAbsent(StatusEffect2, Names::lambda$get$0);
    }

    static {
        statusEffectNames = new HashMap<StatusEffect, String>(16);
        itemNames = new HashMap<Item, String>(128);
        blockNames = new HashMap<Block, String>(128);
        enchantmentNames = new HashMap<Enchantment, String>(16);
        entityTypeNames = new HashMap(64);
        particleTypesNames = new HashMap(64);
        soundNames = new HashMap<Identifier, String>(64);
    }

    public static String getSoundName(Identifier Identifier2) {
        return soundNames.computeIfAbsent(Identifier2, Names::lambda$getSoundName$6);
    }

    public static String get(ParticleType<?> ParticleType2) {
        if (!(ParticleType2 instanceof ParticleEffect)) {
            return "";
        }
        return particleTypesNames.computeIfAbsent(ParticleType2, Names::lambda$get$5);
    }

    private static String lambda$getSoundName$6(Identifier Identifier2) {
        WeightedSoundSet WeightedSoundSet2 = MinecraftClient.getInstance().getSoundManager().get(Identifier2);
        if (WeightedSoundSet2 == null) {
            return Identifier2.getPath();
        }
        Text Text2 = WeightedSoundSet2.getSubtitle();
        if (Text2 == null) {
            return Identifier2.getPath();
        }
        return ChatUtil.stripTextFormat((String)Text2.getString());
    }

    private static String lambda$get$5(ParticleType ParticleType2) {
        return WordUtils.capitalize((String)((ParticleEffect)ParticleType2).asString().substring(10).replace("_", " "));
    }

    private static String lambda$get$0(StatusEffect StatusEffect2) {
        return ChatUtil.stripTextFormat((String)StatusEffect2.getName().getString());
    }

    public static String get(Block Block2) {
        return blockNames.computeIfAbsent(Block2, Names::lambda$get$2);
    }

    private static String lambda$get$1(Item Item2) {
        return ChatUtil.stripTextFormat((String)Item2.getName().getString());
    }

    private static String lambda$get$2(Block Block2) {
        return ChatUtil.stripTextFormat((String)Block2.getName().getString());
    }

    public static String get(Enchantment Enchantment2) {
        return enchantmentNames.computeIfAbsent(Enchantment2, Names::lambda$get$3);
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(Names.class);
    }

    private static String lambda$get$4(EntityType EntityType2) {
        return ChatUtil.stripTextFormat((String)EntityType2.getName().getString());
    }

    public static String get(EntityType<?> EntityType2) {
        return entityTypeNames.computeIfAbsent(EntityType2, Names::lambda$get$4);
    }

    @EventHandler
    private static void onResourcePacksReloaded(ResourcePacksReloadedEvent resourcePacksReloadedEvent) {
        statusEffectNames.clear();
        itemNames.clear();
        blockNames.clear();
        enchantmentNames.clear();
        entityTypeNames.clear();
        particleTypesNames.clear();
        soundNames.clear();
    }

    public static String get(Item Item2) {
        return itemNames.computeIfAbsent(Item2, Names::lambda$get$1);
    }

    private static String lambda$get$3(Enchantment Enchantment2) {
        return ChatUtil.stripTextFormat((String)new TranslatableText(Enchantment2.getTranslationKey()).getString());
    }
}

