/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.sound.WeightedSoundSet
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.entity.EntityType
 *  net.minecraft.item.Item
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.block.Block
 *  net.minecraft.particle.ParticleEffect
 *  net.minecraft.particle.ParticleType
 *  net.minecraft.text.Text
 *  net.minecraft.text.TranslatableText
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.ChatUtil
 *  org.apache.commons.lang3.text.WordUtils
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
    private static final /* synthetic */ Map<ParticleType<?>, String> particleTypesNames;
    private static final /* synthetic */ Map<Block, String> blockNames;
    private static final /* synthetic */ Map<EntityType<?>, String> entityTypeNames;
    private static final /* synthetic */ Map<Enchantment, String> enchantmentNames;
    private static final /* synthetic */ Map<StatusEffect, String> statusEffectNames;
    private static final /* synthetic */ Map<Item, String> itemNames;
    private static final /* synthetic */ Map<Identifier, String> soundNames;

    public static String get(StatusEffect lIlllllIllIlll) {
        return statusEffectNames.computeIfAbsent(lIlllllIllIlll, lIlllllIIIlIIl -> ChatUtil.stripTextFormat((String)lIlllllIIIlIIl.getName().getString()));
    }

    @EventHandler
    private static void onResourcePacksReloaded(ResourcePacksReloadedEvent lIlllllIlllIIl) {
        statusEffectNames.clear();
        itemNames.clear();
        blockNames.clear();
        enchantmentNames.clear();
        entityTypeNames.clear();
        particleTypesNames.clear();
        soundNames.clear();
    }

    public static String get(Item lIlllllIllIlII) {
        return itemNames.computeIfAbsent(lIlllllIllIlII, lIlllllIIIllII -> ChatUtil.stripTextFormat((String)lIlllllIIIllII.getName().getString()));
    }

    public Names() {
        Names lIlllllIlllIll;
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(Names.class);
    }

    public static String get(ParticleType<?> lIlllllIlIIlll) {
        if (!(lIlllllIlIIlll instanceof ParticleEffect)) {
            return "";
        }
        return particleTypesNames.computeIfAbsent(lIlllllIlIIlll, lIlllllIIllIIl -> WordUtils.capitalize((String)((ParticleEffect)lIlllllIIllIIl).asString().substring(10).replace("_", " ")));
    }

    public static String getSoundName(Identifier lIlllllIlIIlII) {
        return soundNames.computeIfAbsent(lIlllllIlIIlII, lIlllllIIlllIl -> {
            WeightedSoundSet lIlllllIIlllll = MinecraftClient.getInstance().getSoundManager().get(lIlllllIIlllIl);
            if (lIlllllIIlllll == null) {
                return lIlllllIIlllIl.getPath();
            }
            Text lIlllllIIllllI = lIlllllIIlllll.getSubtitle();
            if (lIlllllIIllllI == null) {
                return lIlllllIIlllIl.getPath();
            }
            return ChatUtil.stripTextFormat((String)lIlllllIIllllI.getString());
        });
    }

    public static String get(EntityType<?> lIlllllIlIlIll) {
        return entityTypeNames.computeIfAbsent(lIlllllIlIlIll, lIlllllIIlIllI -> ChatUtil.stripTextFormat((String)lIlllllIIlIllI.getName().getString()));
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

    public static String get(Enchantment lIlllllIlIlllI) {
        return enchantmentNames.computeIfAbsent(lIlllllIlIlllI, lIlllllIIlIIll -> ChatUtil.stripTextFormat((String)new TranslatableText(lIlllllIIlIIll.getTranslationKey()).getString()));
    }

    public static String get(Block lIlllllIllIIII) {
        return blockNames.computeIfAbsent(lIlllllIllIIII, lIlllllIIIllll -> ChatUtil.stripTextFormat((String)lIlllllIIIllll.getName().getString()));
    }
}

