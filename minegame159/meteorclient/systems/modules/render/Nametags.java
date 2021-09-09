/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.ItemFrameEntity
 *  net.minecraft.entity.TntEntity
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.world.GameMode
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.client.render.VertexFormats
 *  org.lwjgl.opengl.GL11
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.misc.MeteorPlayers;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.render.NametagUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.GameMode;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

public class Nametags
extends Module {
    private final /* synthetic */ Setting<SettingColor> background;
    private final /* synthetic */ Setting<SettingColor> healthStage1;
    private final /* synthetic */ SettingGroup sgPlayers;
    private final /* synthetic */ Setting<Boolean> displayItems;
    private final /* synthetic */ Setting<SettingColor> otherHealthStage3;
    private final /* synthetic */ Setting<SettingColor> otherNameColor;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ SettingGroup sgItems;
    private final /* synthetic */ Color RED;
    private final /* synthetic */ Setting<SettingColor> healthStage3;
    private final /* synthetic */ Setting<Double> scale;
    private final /* synthetic */ Setting<SettingColor> healthStage2;
    private final /* synthetic */ Setting<Double> enchantTextScale;
    private final /* synthetic */ Setting<Double> itemSpacing;
    private final /* synthetic */ Setting<Boolean> ignoreEmpty;
    private final /* synthetic */ Setting<Boolean> yourself;
    private final /* synthetic */ Setting<SettingColor> itemNameColor;
    private final /* synthetic */ Vec3 pos;
    private final /* synthetic */ Setting<SettingColor> otherHealthStage2;
    private final /* synthetic */ Setting<SettingColor> pingColor;
    private final /* synthetic */ Setting<Integer> enchantLength;
    private final /* synthetic */ Setting<Position> enchantPos;
    private final /* synthetic */ Map<Enchantment, Integer> enchantmentsToShowScale;
    private final /* synthetic */ Setting<Boolean> displayItemEnchants;
    private final /* synthetic */ Setting<Boolean> displayMeteor;
    private final /* synthetic */ Setting<Boolean> itemCount;
    private final /* synthetic */ Setting<SettingColor> meteorColor;
    private final /* synthetic */ Setting<SettingColor> otherHealthStage1;
    private final /* synthetic */ Setting<List<Enchantment>> displayedEnchantments;
    private final /* synthetic */ double[] itemWidths;
    private final /* synthetic */ Setting<Boolean> displayDistance;
    private final /* synthetic */ SettingGroup sgOther;
    private final /* synthetic */ Setting<SettingColor> itemCountColor;
    private final /* synthetic */ Setting<Boolean> displayPing;
    private final /* synthetic */ Setting<SettingColor> gmColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> distanceColor;
    private final /* synthetic */ Setting<Boolean> displayGameMode;
    private final /* synthetic */ Setting<SettingColor> enchantmentTextColor;
    private final /* synthetic */ Setting<SettingColor> normalName;

    private ItemStack getItem(PlayerEntity lllllllllllllllllIllIIlIIIIIlIlI, int lllllllllllllllllIllIIlIIIIIlIIl) {
        switch (lllllllllllllllllIllIIlIIIIIlIIl) {
            case 0: {
                return lllllllllllllllllIllIIlIIIIIlIlI.getMainHandStack();
            }
            case 1: {
                return (ItemStack)lllllllllllllllllIllIIlIIIIIlIlI.inventory.armor.get(3);
            }
            case 2: {
                return (ItemStack)lllllllllllllllllIllIIlIIIIIlIlI.inventory.armor.get(2);
            }
            case 3: {
                return (ItemStack)lllllllllllllllllIllIIlIIIIIlIlI.inventory.armor.get(1);
            }
            case 4: {
                return (ItemStack)lllllllllllllllllIllIIlIIIIIlIlI.inventory.armor.get(0);
            }
            case 5: {
                return lllllllllllllllllIllIIlIIIIIlIlI.getOffHandStack();
            }
        }
        return ItemStack.EMPTY;
    }

    private double getHeight(Entity lllllllllllllllllIllIIllIIlIlllI) {
        double lllllllllllllllllIllIIllIIlIllIl = lllllllllllllllllIllIIllIIlIlllI.getEyeHeight(lllllllllllllllllIllIIllIIlIlllI.getPose());
        lllllllllllllllllIllIIllIIlIllIl = lllllllllllllllllIllIIllIIlIlllI.getType() == EntityType.ITEM || lllllllllllllllllIllIIllIIlIlllI.getType() == EntityType.ITEM_FRAME ? (lllllllllllllllllIllIIllIIlIllIl += 0.2) : (lllllllllllllllllIllIIllIIlIllIl += 0.5);
        return lllllllllllllllllIllIIllIIlIllIl;
    }

    private void renderTntNametag(TntEntity lllllllllllllllllIllIIlIIIlIIIII) {
        Nametags lllllllllllllllllIllIIlIIIlIIIIl;
        TextRenderer lllllllllllllllllIllIIlIIIlIlIII = TextRenderer.get();
        NametagUtils.begin(lllllllllllllllllIllIIlIIIlIIIIl.pos);
        String lllllllllllllllllIllIIlIIIlIIlll = Nametags.ticksToTime(lllllllllllllllllIllIIlIIIlIIIII.getFuseTimer());
        double lllllllllllllllllIllIIlIIIlIIllI = lllllllllllllllllIllIIlIIIlIlIII.getWidth(lllllllllllllllllIllIIlIIIlIIlll);
        double lllllllllllllllllIllIIlIIIlIIlIl = lllllllllllllllllIllIIlIIIlIlIII.getHeight();
        double lllllllllllllllllIllIIlIIIlIIlII = lllllllllllllllllIllIIlIIIlIIllI / 2.0;
        lllllllllllllllllIllIIlIIIlIIIIl.drawBg(-lllllllllllllllllIllIIlIIIlIIlII, -lllllllllllllllllIllIIlIIIlIIlIl, lllllllllllllllllIllIIlIIIlIIllI, lllllllllllllllllIllIIlIIIlIIlIl);
        lllllllllllllllllIllIIlIIIlIlIII.beginBig();
        double lllllllllllllllllIllIIlIIIlIIIll = -lllllllllllllllllIllIIlIIIlIIlII;
        double lllllllllllllllllIllIIlIIIlIIIlI = -lllllllllllllllllIllIIlIIIlIIlIl;
        lllllllllllllllllIllIIlIIIlIlIII.render(lllllllllllllllllIllIIlIIIlIIlll, lllllllllllllllllIllIIlIIIlIIIll, lllllllllllllllllIllIIlIIIlIIIlI, lllllllllllllllllIllIIlIIIlIIIIl.otherNameColor.get());
        lllllllllllllllllIllIIlIIIlIlIII.end();
        NametagUtils.end();
    }

    private void renderGenericNametag(LivingEntity lllllllllllllllllIllIIlIIlIlIIlI) {
        Color lllllllllllllllllIllIIlIIlIIlIll;
        Nametags lllllllllllllllllIllIIlIIlIIIIll;
        TextRenderer lllllllllllllllllIllIIlIIlIlIIIl = TextRenderer.get();
        NametagUtils.begin(lllllllllllllllllIllIIlIIlIIIIll.pos);
        String lllllllllllllllllIllIIlIIlIlIIII = lllllllllllllllllIllIIlIIlIlIIlI.getType().getName().getString();
        lllllllllllllllllIllIIlIIlIlIIII = String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIlIIlIlIIII).append(" "));
        float lllllllllllllllllIllIIlIIlIIllll = lllllllllllllllllIllIIlIIlIlIIlI.getAbsorptionAmount();
        int lllllllllllllllllIllIIlIIlIIlllI = Math.round(lllllllllllllllllIllIIlIIlIlIIlI.getHealth() + lllllllllllllllllIllIIlIIlIIllll);
        double lllllllllllllllllIllIIlIIlIIllIl = (float)lllllllllllllllllIllIIlIIlIIlllI / (lllllllllllllllllIllIIlIIlIlIIlI.getMaxHealth() + lllllllllllllllllIllIIlIIlIIllll);
        String lllllllllllllllllIllIIlIIlIIllII = String.valueOf(lllllllllllllllllIllIIlIIlIIlllI);
        if (lllllllllllllllllIllIIlIIlIIllIl <= 0.333) {
            Color lllllllllllllllllIllIIlIIlIlIlIl = lllllllllllllllllIllIIlIIlIIIIll.otherHealthStage3.get();
        } else if (lllllllllllllllllIllIIlIIlIIllIl <= 0.666) {
            Color lllllllllllllllllIllIIlIIlIlIlII = lllllllllllllllllIllIIlIIlIIIIll.otherHealthStage2.get();
        } else {
            lllllllllllllllllIllIIlIIlIIlIll = lllllllllllllllllIllIIlIIlIIIIll.otherHealthStage1.get();
        }
        double lllllllllllllllllIllIIlIIlIIlIlI = lllllllllllllllllIllIIlIIlIlIIIl.getWidth(lllllllllllllllllIllIIlIIlIlIIII);
        double lllllllllllllllllIllIIlIIlIIlIIl = lllllllllllllllllIllIIlIIlIlIIIl.getWidth(lllllllllllllllllIllIIlIIlIIllII);
        double lllllllllllllllllIllIIlIIlIIlIII = lllllllllllllllllIllIIlIIlIlIIIl.getHeight();
        double lllllllllllllllllIllIIlIIlIIIlll = lllllllllllllllllIllIIlIIlIIlIlI + lllllllllllllllllIllIIlIIlIIlIIl;
        double lllllllllllllllllIllIIlIIlIIIllI = lllllllllllllllllIllIIlIIlIIIlll / 2.0;
        lllllllllllllllllIllIIlIIlIIIIll.drawBg(-lllllllllllllllllIllIIlIIlIIIllI, -lllllllllllllllllIllIIlIIlIIlIII, lllllllllllllllllIllIIlIIlIIIlll, lllllllllllllllllIllIIlIIlIIlIII);
        lllllllllllllllllIllIIlIIlIlIIIl.beginBig();
        double lllllllllllllllllIllIIlIIlIIIlIl = -lllllllllllllllllIllIIlIIlIIIllI;
        double lllllllllllllllllIllIIlIIlIIIlII = -lllllllllllllllllIllIIlIIlIIlIII;
        lllllllllllllllllIllIIlIIlIIIlIl = lllllllllllllllllIllIIlIIlIlIIIl.render(lllllllllllllllllIllIIlIIlIlIIII, lllllllllllllllllIllIIlIIlIIIlIl, lllllllllllllllllIllIIlIIlIIIlII, lllllllllllllllllIllIIlIIlIIIIll.otherNameColor.get());
        lllllllllllllllllIllIIlIIlIlIIIl.render(lllllllllllllllllIllIIlIIlIIllII, lllllllllllllllllIllIIlIIlIIIlIl, lllllllllllllllllIllIIlIIlIIIlII, lllllllllllllllllIllIIlIIlIIlIll);
        lllllllllllllllllIllIIlIIlIlIIIl.end();
        NametagUtils.end();
    }

    private List<Enchantment> setDefaultList() {
        ArrayList<Enchantment> lllllllllllllllllIllIIlIIIIlIIll = new ArrayList<Enchantment>();
        for (Enchantment lllllllllllllllllIllIIlIIIIlIlIl : Registry.ENCHANTMENT) {
            lllllllllllllllllIllIIlIIIIlIIll.add(lllllllllllllllllIllIIlIIIIlIlIl);
        }
        return lllllllllllllllllIllIIlIIIIlIIll;
    }

    private void renderNametagPlayer(PlayerEntity lllllllllllllllllIllIIlIllIllIII) {
        Color lllllllllllllllllIllIIlIllIIllIl;
        String lllllllllllllllllIllIIlIllIlIIll;
        Nametags lllllllllllllllllIllIIlIllIllIIl;
        TextRenderer lllllllllllllllllIllIIlIllIlIlll = TextRenderer.get();
        NametagUtils.begin(lllllllllllllllllIllIIlIllIllIIl.pos);
        String lllllllllllllllllIllIIlIllIlIllI = "";
        if (lllllllllllllllllIllIIlIllIllIIl.displayMeteor.get().booleanValue() && MeteorPlayers.get(lllllllllllllllllIllIIlIllIllIII)) {
            lllllllllllllllllIllIIlIllIlIllI = "M ";
        }
        GameMode lllllllllllllllllIllIIlIllIlIlIl = EntityUtils.getGameMode(lllllllllllllllllIllIIlIllIllIII);
        String lllllllllllllllllIllIIlIllIlIlII = "ERR";
        if (lllllllllllllllllIllIIlIllIlIlIl != null) {
            switch (lllllllllllllllllIllIIlIllIlIlIl) {
                case SPECTATOR: {
                    lllllllllllllllllIllIIlIllIlIlII = "Sp";
                    break;
                }
                case SURVIVAL: {
                    lllllllllllllllllIllIIlIllIlIlII = "S";
                    break;
                }
                case CREATIVE: {
                    lllllllllllllllllIllIIlIllIlIlII = "C";
                    break;
                }
                case ADVENTURE: {
                    lllllllllllllllllIllIIlIllIlIlII = "A";
                }
            }
        }
        lllllllllllllllllIllIIlIllIlIlII = String.valueOf(new StringBuilder().append("[").append(lllllllllllllllllIllIIlIllIlIlII).append("] "));
        Color lllllllllllllllllIllIIlIllIlIIlI = Friends.get().getFriendColor(lllllllllllllllllIllIIlIllIllIII);
        if (lllllllllllllllllIllIIlIllIllIII == lllllllllllllllllIllIIlIllIllIIl.mc.player) {
            String lllllllllllllllllIllIIlIllllIllI = Modules.get().get(NameProtect.class).getName(lllllllllllllllllIllIIlIllIllIII.getGameProfile().getName());
        } else {
            lllllllllllllllllIllIIlIllIlIIll = lllllllllllllllllIllIIlIllIllIII.getGameProfile().getName();
        }
        if (Modules.get().get(FakePlayer.class).showID(lllllllllllllllllIllIIlIllIllIII)) {
            lllllllllllllllllIllIIlIllIlIIll = String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIlIllIlIIll).append(" [").append(FakePlayerUtils.getID((FakePlayerEntity)lllllllllllllllllIllIIlIllIllIII)).append("]"));
        }
        lllllllllllllllllIllIIlIllIlIIll = String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIlIllIlIIll).append(" "));
        float lllllllllllllllllIllIIlIllIlIIIl = lllllllllllllllllIllIIlIllIllIII.getAbsorptionAmount();
        int lllllllllllllllllIllIIlIllIlIIII = Math.round(lllllllllllllllllIllIIlIllIllIII.getHealth() + lllllllllllllllllIllIIlIllIlIIIl);
        double lllllllllllllllllIllIIlIllIIllll = (float)lllllllllllllllllIllIIlIllIlIIII / (lllllllllllllllllIllIIlIllIllIII.getMaxHealth() + lllllllllllllllllIllIIlIllIlIIIl);
        String lllllllllllllllllIllIIlIllIIlllI = String.valueOf(lllllllllllllllllIllIIlIllIlIIII);
        if (lllllllllllllllllIllIIlIllIIllll <= 0.333) {
            Color lllllllllllllllllIllIIlIllllIlIl = lllllllllllllllllIllIIlIllIllIIl.healthStage3.get();
        } else if (lllllllllllllllllIllIIlIllIIllll <= 0.666) {
            Color lllllllllllllllllIllIIlIllllIlII = lllllllllllllllllIllIIlIllIllIIl.healthStage2.get();
        } else {
            lllllllllllllllllIllIIlIllIIllIl = lllllllllllllllllIllIIlIllIllIIl.healthStage1.get();
        }
        int lllllllllllllllllIllIIlIllIIllII = EntityUtils.getPing(lllllllllllllllllIllIIlIllIllIII);
        String lllllllllllllllllIllIIlIllIIlIll = String.valueOf(new StringBuilder().append(" [").append(lllllllllllllllllIllIIlIllIIllII).append("ms]"));
        double lllllllllllllllllIllIIlIllIIlIlI = (double)Math.round(Utils.distanceToCamera((Entity)lllllllllllllllllIllIIlIllIllIII) * 10.0) / 10.0;
        String lllllllllllllllllIllIIlIllIIlIIl = String.valueOf(new StringBuilder().append(" ").append(lllllllllllllllllIllIIlIllIIlIlI).append("m"));
        double lllllllllllllllllIllIIlIllIIlIII = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIlIllI);
        double lllllllllllllllllIllIIlIllIIIlll = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIlIlII);
        double lllllllllllllllllIllIIlIllIIIllI = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIlIIll);
        double lllllllllllllllllIllIIlIllIIIlIl = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIIlllI);
        double lllllllllllllllllIllIIlIllIIIlII = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIIlIll);
        double lllllllllllllllllIllIIlIllIIIIll = lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllIIlIIl);
        double lllllllllllllllllIllIIlIllIIIIlI = lllllllllllllllllIllIIlIllIIIllI + lllllllllllllllllIllIIlIllIIIlIl;
        if (lllllllllllllllllIllIIlIllIllIIl.displayMeteor.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIIIIlI += lllllllllllllllllIllIIlIllIIlIII;
        }
        if (lllllllllllllllllIllIIlIllIllIIl.displayGameMode.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIIIIlI += lllllllllllllllllIllIIlIllIIIlll;
        }
        if (lllllllllllllllllIllIIlIllIllIIl.displayPing.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIIIIlI += lllllllllllllllllIllIIlIllIIIlII;
        }
        if (lllllllllllllllllIllIIlIllIllIIl.displayDistance.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIIIIlI += lllllllllllllllllIllIIlIllIIIIll;
        }
        double lllllllllllllllllIllIIlIllIIIIIl = lllllllllllllllllIllIIlIllIIIIlI / 2.0;
        double lllllllllllllllllIllIIlIllIIIIII = lllllllllllllllllIllIIlIllIlIlll.getHeight();
        lllllllllllllllllIllIIlIllIllIIl.drawBg(-lllllllllllllllllIllIIlIllIIIIIl, -lllllllllllllllllIllIIlIllIIIIII, lllllllllllllllllIllIIlIllIIIIlI, lllllllllllllllllIllIIlIllIIIIII);
        lllllllllllllllllIllIIlIllIlIlll.beginBig();
        double lllllllllllllllllIllIIlIlIllllll = -lllllllllllllllllIllIIlIllIIIIIl;
        double lllllllllllllllllIllIIlIlIlllllI = -lllllllllllllllllIllIIlIllIIIIII;
        if (lllllllllllllllllIllIIlIllIllIIl.displayMeteor.get().booleanValue()) {
            lllllllllllllllllIllIIlIlIllllll = lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIlIllI, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIllIIl.meteorColor.get());
        }
        if (lllllllllllllllllIllIIlIllIllIIl.displayGameMode.get().booleanValue()) {
            lllllllllllllllllIllIIlIlIllllll = lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIlIlII, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIllIIl.gmColor.get());
        }
        lllllllllllllllllIllIIlIlIllllll = lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIlIIll, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIlIIlI != null ? lllllllllllllllllIllIIlIllIlIIlI : (Color)lllllllllllllllllIllIIlIllIllIIl.normalName.get());
        lllllllllllllllllIllIIlIlIllllll = lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIIlllI, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIIllIl);
        if (lllllllllllllllllIllIIlIllIllIIl.displayPing.get().booleanValue()) {
            lllllllllllllllllIllIIlIlIllllll = lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIIlIll, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIllIIl.pingColor.get());
        }
        if (lllllllllllllllllIllIIlIllIllIIl.displayDistance.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIllIIlIIl, lllllllllllllllllIllIIlIlIllllll, lllllllllllllllllIllIIlIlIlllllI, lllllllllllllllllIllIIlIllIllIIl.distanceColor.get());
        }
        lllllllllllllllllIllIIlIllIlIlll.end();
        if (lllllllllllllllllIllIIlIllIllIIl.displayItems.get().booleanValue()) {
            Arrays.fill(lllllllllllllllllIllIIlIllIllIIl.itemWidths, 0.0);
            boolean lllllllllllllllllIllIIlIlllIIIII = false;
            int lllllllllllllllllIllIIlIllIlllll = 0;
            for (int lllllllllllllllllIllIIlIlllIlllI = 0; lllllllllllllllllIllIIlIlllIlllI < 6; ++lllllllllllllllllIllIIlIlllIlllI) {
                ItemStack lllllllllllllllllIllIIlIlllIllll = lllllllllllllllllIllIIlIllIllIIl.getItem(lllllllllllllllllIllIIlIllIllIII, lllllllllllllllllIllIIlIlllIlllI);
                if (!(lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIlllI] != 0.0 || lllllllllllllllllIllIIlIllIllIIl.ignoreEmpty.get().booleanValue() && lllllllllllllllllIllIIlIlllIllll.isEmpty())) {
                    lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIlllI] = 32.0 + lllllllllllllllllIllIIlIllIllIIl.itemSpacing.get();
                }
                if (!lllllllllllllllllIllIIlIlllIllll.isEmpty()) {
                    lllllllllllllllllIllIIlIlllIIIII = true;
                }
                if (!lllllllllllllllllIllIIlIllIllIIl.displayItemEnchants.get().booleanValue()) continue;
                Map lllllllllllllllllIllIIlIllllIIII = EnchantmentHelper.get((ItemStack)lllllllllllllllllIllIIlIlllIllll);
                lllllllllllllllllIllIIlIllIllIIl.enchantmentsToShowScale.clear();
                for (Enchantment Enchantment2 : lllllllllllllllllIllIIlIllIllIIl.displayedEnchantments.get()) {
                    if (!lllllllllllllllllIllIIlIllllIIII.containsKey((Object)Enchantment2)) continue;
                    lllllllllllllllllIllIIlIllIllIIl.enchantmentsToShowScale.put(Enchantment2, (Integer)lllllllllllllllllIllIIlIllllIIII.get((Object)Enchantment2));
                }
                for (Enchantment Enchantment3 : lllllllllllllllllIllIIlIllIllIIl.enchantmentsToShowScale.keySet()) {
                    String lllllllllllllllllIllIIlIllllIIlI = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(Enchantment3, lllllllllllllllllIllIIlIllIllIIl.enchantLength.get())).append(" ").append(lllllllllllllllllIllIIlIllIllIIl.enchantmentsToShowScale.get((Object)Enchantment3)));
                    lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIlllI] = Math.max(lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIlllI], lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIllllIIlI) / 2.0);
                }
                lllllllllllllllllIllIIlIllIlllll = Math.max(lllllllllllllllllIllIIlIllIlllll, lllllllllllllllllIllIIlIllIllIIl.enchantmentsToShowScale.size());
            }
            double lllllllllllllllllIllIIlIllIllllI = lllllllllllllllllIllIIlIlllIIIII ? 32 : 0;
            double lllllllllllllllllIllIIlIllIlllIl = 0.0;
            for (double lllllllllllllllllIllIIlIlllIllIl : lllllllllllllllllIllIIlIllIllIIl.itemWidths) {
                lllllllllllllllllIllIIlIllIlllIl += lllllllllllllllllIllIIlIlllIllIl;
            }
            double d = lllllllllllllllllIllIIlIllIlllIl / 2.0;
            double lllllllllllllllllIllIIlIllIllIll = -lllllllllllllllllIllIIlIllIIIIII - 7.0 - lllllllllllllllllIllIIlIllIllllI;
            double lllllllllllllllllIllIIlIllIllIlI = -d;
            for (int lllllllllllllllllIllIIlIlllIIIIl = 0; lllllllllllllllllIllIIlIlllIIIIl < 6; ++lllllllllllllllllIllIIlIlllIIIIl) {
                ItemStack lllllllllllllllllIllIIlIlllIIIlI = lllllllllllllllllIllIIlIllIllIIl.getItem(lllllllllllllllllIllIIlIllIllIII, lllllllllllllllllIllIIlIlllIIIIl);
                GL11.glPushMatrix();
                GL11.glScaled((double)2.0, (double)2.0, (double)1.0);
                lllllllllllllllllIllIIlIllIllIIl.mc.getItemRenderer().renderGuiItemIcon(lllllllllllllllllIllIIlIlllIIIlI, (int)(lllllllllllllllllIllIIlIllIllIlI / 2.0), (int)(lllllllllllllllllIllIIlIllIllIll / 2.0));
                lllllllllllllllllIllIIlIllIllIIl.mc.getItemRenderer().renderGuiItemOverlay(lllllllllllllllllIllIIlIllIllIIl.mc.textRenderer, lllllllllllllllllIllIIlIlllIIIlI, (int)(lllllllllllllllllIllIIlIllIllIlI / 2.0), (int)(lllllllllllllllllIllIIlIllIllIll / 2.0));
                GL11.glPopMatrix();
                if (lllllllllllllllllIllIIlIllIlllll > 0 && lllllllllllllllllIllIIlIllIllIIl.displayItemEnchants.get().booleanValue()) {
                    lllllllllllllllllIllIIlIllIlIlll.begin(0.5 * lllllllllllllllllIllIIlIllIllIIl.enchantTextScale.get(), false, true);
                    Map lllllllllllllllllIllIIlIlllIlIII = EnchantmentHelper.get((ItemStack)lllllllllllllllllIllIIlIlllIIIlI);
                    HashMap<Enchantment, Integer> lllllllllllllllllIllIIlIlllIIlll = new HashMap<Enchantment, Integer>();
                    for (Enchantment lllllllllllllllllIllIIlIlllIllII : lllllllllllllllllIllIIlIllIllIIl.displayedEnchantments.get()) {
                        if (!lllllllllllllllllIllIIlIlllIlIII.containsKey((Object)lllllllllllllllllIllIIlIlllIllII)) continue;
                        lllllllllllllllllIllIIlIlllIIlll.put(lllllllllllllllllIllIIlIlllIllII, (Integer)lllllllllllllllllIllIIlIlllIlIII.get((Object)lllllllllllllllllIllIIlIlllIllII));
                    }
                    double lllllllllllllllllIllIIlIlllIIllI = lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIIIIl];
                    double lllllllllllllllllIllIIlIlllIIlIl = 0.0;
                    double lllllllllllllllllIllIIlIlllIIlII = 0.0;
                    switch (lllllllllllllllllIllIIlIllIllIIl.enchantPos.get()) {
                        case Above: {
                            lllllllllllllllllIllIIlIlllIIlII = -((double)(lllllllllllllllllIllIIlIlllIIlll.size() + 1) * lllllllllllllllllIllIIlIllIlIlll.getHeight());
                            break;
                        }
                        case OnTop: {
                            lllllllllllllllllIllIIlIlllIIlII = (lllllllllllllllllIllIIlIllIllllI - (double)lllllllllllllllllIllIIlIlllIIlll.size() * lllllllllllllllllIllIIlIllIlIlll.getHeight()) / 2.0;
                        }
                    }
                    double lllllllllllllllllIllIIlIlllIIIll = lllllllllllllllllIllIIlIllIllIlI;
                    for (Enchantment lllllllllllllllllIllIIlIlllIlIIl : lllllllllllllllllIllIIlIlllIIlll.keySet()) {
                        String lllllllllllllllllIllIIlIlllIlIll = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(lllllllllllllllllIllIIlIlllIlIIl, lllllllllllllllllIllIIlIllIllIIl.enchantLength.get())).append(" ").append(lllllllllllllllllIllIIlIlllIIlll.get((Object)lllllllllllllllllIllIIlIlllIlIIl)));
                        Color lllllllllllllllllIllIIlIlllIlIlI = lllllllllllllllllIllIIlIllIllIIl.enchantmentTextColor.get();
                        if (lllllllllllllllllIllIIlIlllIlIIl.isCursed()) {
                            lllllllllllllllllIllIIlIlllIlIlI = lllllllllllllllllIllIIlIllIllIIl.RED;
                        }
                        switch (lllllllllllllllllIllIIlIllIllIIl.enchantPos.get()) {
                            case Above: {
                                lllllllllllllllllIllIIlIlllIIIll = lllllllllllllllllIllIIlIllIllIlI + lllllllllllllllllIllIIlIlllIIllI / 2.0 - lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIlllIlIll) / 2.0;
                                break;
                            }
                            case OnTop: {
                                lllllllllllllllllIllIIlIlllIIIll = lllllllllllllllllIllIIlIllIllIlI + (lllllllllllllllllIllIIlIlllIIllI - lllllllllllllllllIllIIlIllIlIlll.getWidth(lllllllllllllllllIllIIlIlllIlIll)) / 2.0;
                            }
                        }
                        lllllllllllllllllIllIIlIllIlIlll.render(lllllllllllllllllIllIIlIlllIlIll, lllllllllllllllllIllIIlIlllIIIll, lllllllllllllllllIllIIlIllIllIll + lllllllllllllllllIllIIlIlllIIlII + lllllllllllllllllIllIIlIlllIIlIl, lllllllllllllllllIllIIlIlllIlIlI);
                        lllllllllllllllllIllIIlIlllIIlIl += lllllllllllllllllIllIIlIllIlIlll.getHeight();
                    }
                    lllllllllllllllllIllIIlIllIlIlll.end();
                }
                lllllllllllllllllIllIIlIllIllIlI += lllllllllllllllllIllIIlIllIllIIl.itemWidths[lllllllllllllllllIllIIlIlllIIIIl];
            }
        } else if (lllllllllllllllllIllIIlIllIllIIl.displayItemEnchants.get().booleanValue()) {
            lllllllllllllllllIllIIlIllIllIIl.displayItemEnchants.set(false);
        }
        NametagUtils.end();
    }

    private void renderNametagItem(ItemStack lllllllllllllllllIllIIlIIlllIIII) {
        Nametags lllllllllllllllllIllIIlIIlllIIIl;
        TextRenderer lllllllllllllllllIllIIlIIllllIll = TextRenderer.get();
        NametagUtils.begin(lllllllllllllllllIllIIlIIlllIIIl.pos);
        String lllllllllllllllllIllIIlIIllllIlI = lllllllllllllllllIllIIlIIlllIIII.getName().getString();
        String lllllllllllllllllIllIIlIIllllIIl = String.valueOf(new StringBuilder().append(" x").append(lllllllllllllllllIllIIlIIlllIIII.getCount()));
        double lllllllllllllllllIllIIlIIllllIII = lllllllllllllllllIllIIlIIllllIll.getWidth(lllllllllllllllllIllIIlIIllllIlI);
        double lllllllllllllllllIllIIlIIlllIlll = lllllllllllllllllIllIIlIIllllIll.getWidth(lllllllllllllllllIllIIlIIllllIIl);
        double lllllllllllllllllIllIIlIIlllIllI = lllllllllllllllllIllIIlIIllllIll.getHeight();
        double lllllllllllllllllIllIIlIIlllIlIl = lllllllllllllllllIllIIlIIllllIII;
        if (lllllllllllllllllIllIIlIIlllIIIl.itemCount.get().booleanValue()) {
            lllllllllllllllllIllIIlIIlllIlIl += lllllllllllllllllIllIIlIIlllIlll;
        }
        double lllllllllllllllllIllIIlIIlllIlII = lllllllllllllllllIllIIlIIlllIlIl / 2.0;
        lllllllllllllllllIllIIlIIlllIIIl.drawBg(-lllllllllllllllllIllIIlIIlllIlII, -lllllllllllllllllIllIIlIIlllIllI, lllllllllllllllllIllIIlIIlllIlIl, lllllllllllllllllIllIIlIIlllIllI);
        lllllllllllllllllIllIIlIIllllIll.beginBig();
        double lllllllllllllllllIllIIlIIlllIIll = -lllllllllllllllllIllIIlIIlllIlII;
        double lllllllllllllllllIllIIlIIlllIIlI = -lllllllllllllllllIllIIlIIlllIllI;
        lllllllllllllllllIllIIlIIlllIIll = lllllllllllllllllIllIIlIIllllIll.render(lllllllllllllllllIllIIlIIllllIlI, lllllllllllllllllIllIIlIIlllIIll, lllllllllllllllllIllIIlIIlllIIlI, lllllllllllllllllIllIIlIIlllIIIl.itemNameColor.get());
        if (lllllllllllllllllIllIIlIIlllIIIl.itemCount.get().booleanValue()) {
            lllllllllllllllllIllIIlIIllllIll.render(lllllllllllllllllIllIIlIIllllIIl, lllllllllllllllllIllIIlIIlllIIll, lllllllllllllllllIllIIlIIlllIIlI, lllllllllllllllllIllIIlIIlllIIIl.itemCountColor.get());
        }
        lllllllllllllllllIllIIlIIllllIll.end();
        NametagUtils.end();
    }

    private static String ticksToTime(int lllllllllllllllllIllIIIlllllIIIl) {
        if (lllllllllllllllllIllIIIlllllIIIl > 72000) {
            int lllllllllllllllllIllIIIlllllIllI = lllllllllllllllllIllIIIlllllIIIl / 20 / 3600;
            return String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIIlllllIllI).append(" h"));
        }
        if (lllllllllllllllllIllIIIlllllIIIl > 1200) {
            int lllllllllllllllllIllIIIlllllIlIl = lllllllllllllllllIllIIIlllllIIIl / 20 / 60;
            return String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIIlllllIlIl).append(" m"));
        }
        int lllllllllllllllllIllIIIlllllIlII = lllllllllllllllllIllIIIlllllIIIl / 20;
        int lllllllllllllllllIllIIIlllllIIll = lllllllllllllllllIllIIIlllllIIIl % 20 / 2;
        return String.valueOf(new StringBuilder().append(lllllllllllllllllIllIIIlllllIlII).append(".").append(lllllllllllllllllIllIIIlllllIIll).append(" s"));
    }

    @EventHandler
    private void onRender2D(Render2DEvent lllllllllllllllllIllIIllIIllIllI) {
        Nametags lllllllllllllllllIllIIllIIllIlll;
        boolean lllllllllllllllllIllIIllIIlllIII = !Modules.get().isActive(Freecam.class);
        for (Entity lllllllllllllllllIllIIllIIlllIll : lllllllllllllllllIllIIllIIllIlll.mc.world.getEntities()) {
            EntityType lllllllllllllllllIllIIllIIllllII;
            if (!lllllllllllllllllIllIIllIIllIlll.entities.get().containsKey((Object)lllllllllllllllllIllIIllIIlllIll.getType()) || (lllllllllllllllllIllIIllIIllllII = lllllllllllllllllIllIIllIIlllIll.getType()) == EntityType.PLAYER && (lllllllllllllllllIllIIllIIlllIII && lllllllllllllllllIllIIllIIlllIll == lllllllllllllllllIllIIllIIllIlll.mc.cameraEntity || !lllllllllllllllllIllIIllIIllIlll.yourself.get().booleanValue() && lllllllllllllllllIllIIllIIlllIll == lllllllllllllllllIllIIllIIllIlll.mc.player)) continue;
            lllllllllllllllllIllIIllIIllIlll.pos.set(lllllllllllllllllIllIIllIIlllIll, lllllllllllllllllIllIIllIIllIllI.tickDelta);
            lllllllllllllllllIllIIllIIllIlll.pos.add(0.0, lllllllllllllllllIllIIllIIllIlll.getHeight(lllllllllllllllllIllIIllIIlllIll), 0.0);
            if (!NametagUtils.to2D(lllllllllllllllllIllIIllIIllIlll.pos, lllllllllllllllllIllIIllIIllIlll.scale.get())) continue;
            if (lllllllllllllllllIllIIllIIllllII == EntityType.PLAYER) {
                lllllllllllllllllIllIIllIIllIlll.renderNametagPlayer((PlayerEntity)lllllllllllllllllIllIIllIIlllIll);
                continue;
            }
            if (lllllllllllllllllIllIIllIIllllII == EntityType.ITEM) {
                lllllllllllllllllIllIIllIIllIlll.renderNametagItem(((ItemEntity)lllllllllllllllllIllIIllIIlllIll).getStack());
                continue;
            }
            if (lllllllllllllllllIllIIllIIllllII == EntityType.ITEM_FRAME) {
                lllllllllllllllllIllIIllIIllIlll.renderNametagItem(((ItemFrameEntity)lllllllllllllllllIllIIllIIlllIll).getHeldItemStack());
                continue;
            }
            if (lllllllllllllllllIllIIllIIllllII == EntityType.TNT) {
                lllllllllllllllllIllIIllIIllIlll.renderTntNametag((TntEntity)lllllllllllllllllIllIIllIIlllIll);
                continue;
            }
            if (!(lllllllllllllllllIllIIllIIlllIll instanceof LivingEntity)) continue;
            lllllllllllllllllIllIIllIIllIlll.renderGenericNametag((LivingEntity)lllllllllllllllllIllIIllIIlllIll);
        }
    }

    public Nametags() {
        super(Categories.Render, "nametags", "Displays customizable nametags above players.");
        Nametags lllllllllllllllllIllIIllIlIIIlII;
        lllllllllllllllllIllIIllIlIIIlII.sgGeneral = lllllllllllllllllIllIIllIlIIIlII.settings.getDefaultGroup();
        lllllllllllllllllIllIIllIlIIIlII.sgPlayers = lllllllllllllllllIllIIllIlIIIlII.settings.createGroup("Players");
        lllllllllllllllllIllIIllIlIIIlII.sgItems = lllllllllllllllllIllIIllIlIIIlII.settings.createGroup("Items");
        lllllllllllllllllIllIIllIlIIIlII.sgOther = lllllllllllllllllIllIIllIlIIIlII.settings.createGroup("Other");
        lllllllllllllllllIllIIllIlIIIlII.entities = lllllllllllllllllIllIIllIlIIIlII.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to draw nametags on.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER, EntityType.ITEM})).build());
        lllllllllllllllllIllIIllIlIIIlII.scale = lllllllllllllllllIllIIllIlIIIlII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of the nametag.").defaultValue(1.5).min(0.1).build());
        lllllllllllllllllIllIIllIlIIIlII.yourself = lllllllllllllllllIllIIllIlIIIlII.sgGeneral.add(new BoolSetting.Builder().name("self-nametag").description("Displays a nametag on your player if you're in Freecam.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.background = lllllllllllllllllIllIIllIlIIIlII.sgGeneral.add(new ColorSetting.Builder().name("background").description("The color of the nametag background.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        lllllllllllllllllIllIIllIlIIIlII.displayItems = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("display-items").description("Displays armor and hand items above the name tags.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.itemSpacing = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new DoubleSetting.Builder().name("item-spacing").description("The spacing between items.").defaultValue(2.0).min(0.0).sliderMax(5.0).max(10.0).build());
        lllllllllllllllllIllIIllIlIIIlII.ignoreEmpty = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("ignore-empty").description("Doesn't add spacing where an empty item stack would be.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.displayItemEnchants = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("display-enchants").description("Displays item enchantments on the items.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.enchantPos = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new EnumSetting.Builder().name("enchantment-position").description("Where the enchantments are rendered.").defaultValue(Position.Above).build());
        lllllllllllllllllIllIIllIlIIIlII.enchantLength = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new IntSetting.Builder().name("enchant-name-length").description("The length enchantment names are trimmed to.").defaultValue(3).min(1).max(5).sliderMax(5).build());
        lllllllllllllllllIllIIllIlIIIlII.displayedEnchantments = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new EnchListSetting.Builder().name("displayed-enchantments").description("The enchantments that are shown on nametags.").defaultValue(lllllllllllllllllIllIIllIlIIIlII.setDefaultList()).build());
        lllllllllllllllllIllIIllIlIIIlII.enchantTextScale = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new DoubleSetting.Builder().name("enchant-text-scale").description("The scale of the enchantment text.").defaultValue(1.0).min(0.1).max(2.0).sliderMin(0.1).sliderMax(2.0).build());
        lllllllllllllllllIllIIllIlIIIlII.displayMeteor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("meteor").description("Shows if the player is using Meteor.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.displayGameMode = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("gamemode").description("Shows the player's GameMode.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.displayPing = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("ping").description("Shows the player's ping.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.displayDistance = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new BoolSetting.Builder().name("distance").description("Shows the distance between you and the player.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.normalName = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("normal-color").description("The color of people not in your Friends List.").defaultValue(new SettingColor(255, 255, 255)).build());
        lllllllllllllllllIllIIllIlIIIlII.meteorColor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("meteor-color").description("The color of M when the player is using Meteor.").defaultValue(new SettingColor(135, 0, 255)).build());
        lllllllllllllllllIllIIllIlIIIlII.gmColor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("gamemode-color").description("The color of the gamemode text.").defaultValue(new SettingColor(232, 185, 35)).build());
        lllllllllllllllllIllIIllIlIIIlII.pingColor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("ping-color").description("The color of the ping text.").defaultValue(new SettingColor(150, 150, 150)).build());
        lllllllllllllllllIllIIllIlIIIlII.distanceColor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("distance-color").description("The color of the distance text.").defaultValue(new SettingColor(150, 150, 150)).build());
        lllllllllllllllllIllIIllIlIIIlII.healthStage1 = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("health-stage-1").description("The color if a player is full health.").defaultValue(new SettingColor(25, 252, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.healthStage2 = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("health-stage-2").description("The color if a player is at two-thirds health.").defaultValue(new SettingColor(255, 105, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.healthStage3 = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("health-stage-3").description("The color of a player if they are at one-third health.").defaultValue(new SettingColor(255, 25, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.enchantmentTextColor = lllllllllllllllllIllIIllIlIIIlII.sgPlayers.add(new ColorSetting.Builder().name("enchantment-text-color").description("The color of the enchantment text.").defaultValue(new SettingColor(255, 255, 255)).build());
        lllllllllllllllllIllIIllIlIIIlII.itemNameColor = lllllllllllllllllIllIIllIlIIIlII.sgItems.add(new ColorSetting.Builder().name("name-color").description("The color of the name of the item.").defaultValue(new SettingColor(255, 255, 255)).build());
        lllllllllllllllllIllIIllIlIIIlII.itemCount = lllllllllllllllllIllIIllIlIIIlII.sgItems.add(new BoolSetting.Builder().name("count-on-items").description("Shows the number of items in an item entities nametag.").defaultValue(true).build());
        lllllllllllllllllIllIIllIlIIIlII.itemCountColor = lllllllllllllllllIllIIllIlIIIlII.sgItems.add(new ColorSetting.Builder().name("item-count-color").description("The color of the item count.").defaultValue(new SettingColor(232, 185, 35)).build());
        lllllllllllllllllIllIIllIlIIIlII.otherNameColor = lllllllllllllllllIllIIllIlIIIlII.sgOther.add(new ColorSetting.Builder().name("name-color").description("The color of the name of the entity.").defaultValue(new SettingColor(255, 255, 255)).build());
        lllllllllllllllllIllIIllIlIIIlII.otherHealthStage1 = lllllllllllllllllIllIIllIlIIIlII.sgOther.add(new ColorSetting.Builder().name("health-stage-1").description("The color of a mobs health if it's full health.").defaultValue(new SettingColor(25, 252, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.otherHealthStage2 = lllllllllllllllllIllIIllIlIIIlII.sgOther.add(new ColorSetting.Builder().name("health-stage-2").description("The color of a mobs health if it's at two-thirds health.").defaultValue(new SettingColor(255, 105, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.otherHealthStage3 = lllllllllllllllllIllIIllIlIIIlII.sgOther.add(new ColorSetting.Builder().name("health-stage-3").description("The color of a mobs health if they are at one-third health.").defaultValue(new SettingColor(255, 25, 25)).build());
        lllllllllllllllllIllIIllIlIIIlII.pos = new Vec3();
        lllllllllllllllllIllIIllIlIIIlII.itemWidths = new double[6];
        lllllllllllllllllIllIIllIlIIIlII.RED = new Color(255, 15, 15);
        lllllllllllllllllIllIIllIlIIIlII.enchantmentsToShowScale = new HashMap<Enchantment, Integer>();
    }

    private void drawBg(double lllllllllllllllllIllIIIlllllllIl, double lllllllllllllllllIllIIIlllllllII, double lllllllllllllllllIllIIIllllllIll, double lllllllllllllllllIllIIIlllllllll) {
        Nametags lllllllllllllllllIllIIIllllllllI;
        Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
        Renderer.NORMAL.quad(lllllllllllllllllIllIIIlllllllIl - 1.0, lllllllllllllllllIllIIIlllllllII - 1.0, lllllllllllllllllIllIIIllllllIll + 2.0, lllllllllllllllllIllIIIlllllllll + 2.0, lllllllllllllllllIllIIIllllllllI.background.get());
        Renderer.NORMAL.end();
    }

    public static final class Position
    extends Enum<Position> {
        private static final /* synthetic */ Position[] $VALUES;
        public static final /* synthetic */ /* enum */ Position OnTop;
        public static final /* synthetic */ /* enum */ Position Above;

        private Position() {
            Position llllllllllllllllllIIlIlIIIlIlIlI;
        }

        static {
            Above = new Position();
            OnTop = new Position();
            $VALUES = Position.$values();
        }

        public static Position[] values() {
            return (Position[])$VALUES.clone();
        }

        private static /* synthetic */ Position[] $values() {
            return new Position[]{Above, OnTop};
        }

        public static Position valueOf(String llllllllllllllllllIIlIlIIIllIIII) {
            return Enum.valueOf(Position.class, llllllllllllllllllIIlIlIIIllIIII);
        }
    }
}

