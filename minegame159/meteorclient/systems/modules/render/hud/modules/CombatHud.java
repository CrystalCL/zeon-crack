/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BedItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.SwordItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.misc.FakeClientPlayer;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class CombatHud
extends HudElement {
    private final /* synthetic */ Setting<Boolean> displayPing;
    private final /* synthetic */ Setting<Boolean> displayDistance;
    private final /* synthetic */ Setting<Double> scale;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> pingColor3;
    private final /* synthetic */ Setting<SettingColor> distColor3;
    private /* synthetic */ PlayerEntity playerEntity;
    private final /* synthetic */ Setting<SettingColor> healthColor2;
    private final /* synthetic */ Setting<SettingColor> distColor1;
    private final /* synthetic */ Setting<Boolean> ignoreFriends;
    private final /* synthetic */ Setting<Double> range;
    private static final /* synthetic */ Color RED;
    private final /* synthetic */ Setting<SettingColor> healthColor1;
    private final /* synthetic */ Setting<SettingColor> healthColor3;
    private final /* synthetic */ Setting<List<Enchantment>> displayedEnchantments;
    private static final /* synthetic */ Color GREEN;
    private final /* synthetic */ Setting<SettingColor> enchantmentTextColor;
    private final /* synthetic */ Setting<SettingColor> distColor2;
    private final /* synthetic */ Setting<SettingColor> backgroundColor;
    private static final /* synthetic */ Color BLACK;
    private final /* synthetic */ Setting<SettingColor> pingColor1;
    private final /* synthetic */ Setting<SettingColor> pingColor2;

    private ItemStack getItem(int lIIlllllIIlIIl) {
        CombatHud lIIlllllIIlIlI;
        if (lIIlllllIIlIlI.isInEditor()) {
            switch (lIIlllllIIlIIl) {
                case 0: {
                    return Items.END_CRYSTAL.getDefaultStack();
                }
                case 1: {
                    return Items.NETHERITE_BOOTS.getDefaultStack();
                }
                case 2: {
                    return Items.NETHERITE_LEGGINGS.getDefaultStack();
                }
                case 3: {
                    return Items.NETHERITE_CHESTPLATE.getDefaultStack();
                }
                case 4: {
                    return Items.NETHERITE_HELMET.getDefaultStack();
                }
                case 5: {
                    return Items.TOTEM_OF_UNDYING.getDefaultStack();
                }
            }
        }
        if (lIIlllllIIlIlI.playerEntity == null) {
            return ItemStack.EMPTY;
        }
        if (lIIlllllIIlIIl == 5) {
            return lIIlllllIIlIlI.playerEntity.getMainHandStack();
        }
        if (lIIlllllIIlIIl == 4) {
            return lIIlllllIIlIlI.playerEntity.getOffHandStack();
        }
        return lIIlllllIIlIlI.playerEntity.inventory.getArmorStack(lIIlllllIIlIIl);
    }

    @Override
    public void render(HudRenderer lIIlllllIlIIIl) {
        CombatHud lIIlllllIlIIlI;
        lIIlllllIlIIIl.addPostTask(() -> {
            Color lIIlllIlllllIl;
            Color lIIllllIIIIIII;
            CombatHud lIIlllIllIlIIl;
            double lIIllllIIIIlll = lIIlllIllIlIIl.box.getX();
            double lIIllllIIIIllI = lIIlllIllIlIIl.box.getY();
            lIIlllIllIlIIl.playerEntity = lIIlllIllIlIIl.isInEditor() ? FakeClientPlayer.getPlayer() : EntityUtils.getPlayerTarget(lIIlllIllIlIIl.range.get(), SortPriority.LowestDistance, lIIlllIllIlIIl.ignoreFriends.get());
            if (lIIlllIllIlIIl.playerEntity == null) {
                return;
            }
            Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            Renderer.NORMAL.quad(lIIllllIIIIlll, lIIllllIIIIllI, lIIlllIllIlIIl.box.width, lIIlllIllIlIIl.box.height, lIIlllIllIlIIl.backgroundColor.get());
            Renderer.NORMAL.end();
            InventoryScreen.drawEntity((int)((int)(lIIllllIIIIlll + 25.0 * lIIlllIllIlIIl.scale.get())), (int)((int)(lIIllllIIIIllI + 66.0 * lIIlllIllIlIIl.scale.get())), (int)((int)(30.0 * lIIlllIllIlIIl.scale.get())), (float)(-MathHelper.wrapDegrees((float)(lIIlllIllIlIIl.playerEntity.prevYaw + (lIIlllIllIlIIl.playerEntity.yaw - lIIlllIllIlIIl.playerEntity.prevYaw) * lIIlllIllIlIIl.mc.getTickDelta()))), (float)(-lIIlllIllIlIIl.playerEntity.pitch), (LivingEntity)lIIlllIllIlIIl.playerEntity);
            lIIllllIIIIlll += 50.0 * lIIlllIllIlIIl.scale.get();
            lIIllllIIIIllI += 5.0 * lIIlllIllIlIIl.scale.get();
            String lIIllllIIIIlIl = " | ";
            String lIIllllIIIIlII = lIIlllIllIlIIl.playerEntity.getGameProfile().getName();
            Color lIIllllIIIIIll = Friends.get().getFriendColor(lIIlllIllIlIIl.playerEntity);
            int lIIllllIIIIIlI = EntityUtils.getPing(lIIlllIllIlIIl.playerEntity);
            String lIIllllIIIIIIl = String.valueOf(new StringBuilder().append(lIIllllIIIIIlI).append("ms"));
            if (lIIllllIIIIIlI <= 75) {
                Color lIIllllIIlllIl = lIIlllIllIlIIl.pingColor1.get();
            } else if (lIIllllIIIIIlI <= 200) {
                Color lIIllllIIlllII = lIIlllIllIlIIl.pingColor2.get();
            } else {
                lIIllllIIIIIII = lIIlllIllIlIIl.pingColor3.get();
            }
            double lIIlllIlllllll = 0.0;
            if (!lIIlllIllIlIIl.isInEditor()) {
                lIIlllIlllllll = (double)Math.round((double)lIIlllIllIlIIl.mc.player.distanceTo((Entity)lIIlllIllIlIIl.playerEntity) * 100.0) / 100.0;
            }
            String lIIlllIllllllI = String.valueOf(new StringBuilder().append(lIIlllIlllllll).append("m"));
            if (lIIlllIlllllll <= 10.0) {
                Color lIIllllIIllIll = lIIlllIllIlIIl.distColor1.get();
            } else if (lIIlllIlllllll <= 50.0) {
                Color lIIllllIIllIlI = lIIlllIllIlIIl.distColor2.get();
            } else {
                lIIlllIlllllIl = lIIlllIllIlIIl.distColor3.get();
            }
            String lIIlllIlllllII = "Unknown";
            Color lIIlllIllllIll = lIIlllIllIlIIl.hud.primaryColor.get();
            if (Friends.get().get(lIIlllIllIlIIl.playerEntity) != null) {
                Friend lIIllllIIllIIl = Friends.get().get(lIIlllIllIlIIl.playerEntity);
                lIIlllIlllllII = lIIllllIIllIIl.type.name();
                lIIlllIllllIll = Friends.get().getFriendColor(lIIlllIllIlIIl.playerEntity);
            } else {
                boolean lIIllllIIlIIll = true;
                for (int lIIllllIIlIlll = 3; lIIllllIIlIlll >= 0; --lIIllllIIlIlll) {
                    ItemStack lIIllllIIllIII = lIIlllIllIlIIl.getItem(lIIllllIIlIlll);
                    if (lIIllllIIllIII.isEmpty()) continue;
                    lIIllllIIlIIll = false;
                }
                if (lIIllllIIlIIll) {
                    lIIlllIlllllII = "Naked";
                    lIIlllIllllIll = GREEN;
                } else {
                    boolean lIIllllIIlIlII = false;
                    for (int lIIllllIIlIlIl = 5; lIIllllIIlIlIl >= 0; --lIIllllIIlIlIl) {
                        ItemStack lIIllllIIlIllI = lIIlllIllIlIIl.getItem(lIIllllIIlIlIl);
                        if (lIIllllIIlIllI.getItem() != Items.END_CRYSTAL && !(lIIllllIIlIllI.getItem() instanceof SwordItem) && lIIllllIIlIllI.getItem() != Items.RESPAWN_ANCHOR && !(lIIllllIIlIllI.getItem() instanceof BedItem)) continue;
                        lIIllllIIlIlII = true;
                    }
                    if (lIIllllIIlIlII) {
                        lIIlllIlllllII = "Threat";
                        lIIlllIllllIll = RED;
                    }
                }
            }
            TextRenderer.get().begin(0.45 * lIIlllIllIlIIl.scale.get(), false, true);
            double lIIlllIllllIlI = TextRenderer.get().getWidth(lIIllllIIIIlIl);
            double lIIlllIllllIIl = TextRenderer.get().getWidth(lIIllllIIIIIIl);
            double lIIlllIllllIII = TextRenderer.get().getWidth(lIIlllIlllllII);
            TextRenderer.get().render(lIIllllIIIIlII, lIIllllIIIIlll, lIIllllIIIIllI, lIIllllIIIIIll != null ? lIIllllIIIIIll : (Color)lIIlllIllIlIIl.hud.primaryColor.get());
            TextRenderer.get().render(lIIlllIlllllII, lIIllllIIIIlll, lIIllllIIIIllI += TextRenderer.get().getHeight(), lIIlllIllllIll);
            if (lIIlllIllIlIIl.displayPing.get().booleanValue()) {
                TextRenderer.get().render(lIIllllIIIIlIl, lIIllllIIIIlll + lIIlllIllllIII, lIIllllIIIIllI, lIIlllIllIlIIl.hud.secondaryColor.get());
                TextRenderer.get().render(lIIllllIIIIIIl, lIIllllIIIIlll + lIIlllIllllIII + lIIlllIllllIlI, lIIllllIIIIllI, lIIllllIIIIIII);
                if (lIIlllIllIlIIl.displayDistance.get().booleanValue()) {
                    TextRenderer.get().render(lIIllllIIIIlIl, lIIllllIIIIlll + lIIlllIllllIII + lIIlllIllllIlI + lIIlllIllllIIl, lIIllllIIIIllI, lIIlllIllIlIIl.hud.secondaryColor.get());
                    TextRenderer.get().render(lIIlllIllllllI, lIIllllIIIIlll + lIIlllIllllIII + lIIlllIllllIlI + lIIlllIllllIIl + lIIlllIllllIlI, lIIllllIIIIllI, lIIlllIlllllIl);
                }
            } else if (lIIlllIllIlIIl.displayDistance.get().booleanValue()) {
                TextRenderer.get().render(lIIllllIIIIlIl, lIIllllIIIIlll + lIIlllIllllIII, lIIllllIIIIllI, lIIlllIllIlIIl.hud.secondaryColor.get());
                TextRenderer.get().render(lIIlllIllllllI, lIIllllIIIIlll + lIIlllIllllIII + lIIlllIllllIlI, lIIllllIIIIllI, lIIlllIlllllIl);
            }
            TextRenderer.get().end();
            lIIllllIIIIllI += 10.0 * lIIlllIllIlIIl.scale.get();
            int lIIlllIlllIlll = 5;
            RenderSystem.pushMatrix();
            RenderSystem.scaled((double)lIIlllIllIlIIl.scale.get(), (double)lIIlllIllIlIIl.scale.get(), (double)1.0);
            lIIllllIIIIlll /= lIIlllIllIlIIl.scale.get().doubleValue();
            lIIllllIIIIllI /= lIIlllIllIlIIl.scale.get().doubleValue();
            TextRenderer.get().begin(0.35, false, true);
            for (int lIIllllIIIlIIl = 0; lIIllllIIIlIIl < 6; ++lIIllllIIIlIIl) {
                double lIIllllIIIlIll = lIIllllIIIIlll + (double)(lIIllllIIIlIIl * 20);
                double lIIllllIIIlIlI = lIIllllIIIIllI;
                ItemStack lIIllllIIIlllI = lIIlllIllIlIIl.getItem(lIIlllIlllIlll);
                RenderUtils.drawItem(lIIllllIIIlllI, (int)lIIllllIIIlIll, (int)lIIllllIIIlIlI, true);
                lIIllllIIIlIlI += 18.0;
                Map lIIllllIIIllIl = EnchantmentHelper.get((ItemStack)lIIllllIIIlllI);
                HashMap<Enchantment, Integer> lIIllllIIIllII = new HashMap<Enchantment, Integer>();
                for (Enchantment lIIllllIIlIIlI : lIIlllIllIlIIl.displayedEnchantments.get()) {
                    if (!lIIllllIIIllIl.containsKey((Object)lIIllllIIlIIlI)) continue;
                    lIIllllIIIllII.put(lIIllllIIlIIlI, (Integer)lIIllllIIIllIl.get((Object)lIIllllIIlIIlI));
                }
                for (Enchantment lIIllllIIIllll : lIIllllIIIllII.keySet()) {
                    String lIIllllIIlIIIl = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(lIIllllIIIllll, 3)).append(" ").append(lIIllllIIIllII.get((Object)lIIllllIIIllll)));
                    double lIIllllIIlIIII = lIIllllIIIlIll + 8.0 - TextRenderer.get().getWidth(lIIllllIIlIIIl) / 2.0;
                    TextRenderer.get().render(lIIllllIIlIIIl, lIIllllIIlIIII, lIIllllIIIlIlI, lIIllllIIIllll.isCursed() ? RED : (Color)lIIlllIllIlIIl.enchantmentTextColor.get());
                    lIIllllIIIlIlI += TextRenderer.get().getHeight();
                }
                --lIIlllIlllIlll;
            }
            TextRenderer.get().end();
            RenderSystem.popMatrix();
            lIIllllIIIIllI = (int)(lIIlllIllIlIIl.box.getY() + 75.0 * lIIlllIllIlIIl.scale.get());
            lIIllllIIIIlll = lIIlllIllIlIIl.box.getX();
            RenderSystem.pushMatrix();
            RenderSystem.scaled((double)lIIlllIllIlIIl.scale.get(), (double)lIIlllIllIlIIl.scale.get(), (double)1.0);
            lIIllllIIIIlll /= lIIlllIllIlIIl.scale.get().doubleValue();
            lIIllllIIIIllI /= lIIlllIllIlIIl.scale.get().doubleValue();
            Renderer.LINES.begin(null, DrawMode.Lines, VertexFormats.POSITION_COLOR);
            Renderer.LINES.boxEdges(lIIllllIIIIlll += 5.0, lIIllllIIIIllI += 5.0, 165.0, 11.0, BLACK);
            Renderer.LINES.end();
            float lIIlllIlllIllI = lIIlllIllIlIIl.playerEntity.getMaxHealth();
            int lIIlllIlllIlIl = 16;
            int lIIlllIlllIlII = (int)(lIIlllIlllIllI + (float)lIIlllIlllIlIl);
            int lIIlllIlllIIll = (int)(161.0f * lIIlllIlllIllI / (float)lIIlllIlllIlII);
            int lIIlllIlllIIlI = 161 * lIIlllIlllIlIl / lIIlllIlllIlII;
            float lIIlllIlllIIIl = lIIlllIllIlIIl.playerEntity.getHealth();
            float lIIlllIlllIIII = lIIlllIllIlIIl.playerEntity.getAbsorptionAmount();
            float lIIlllIllIllll = lIIlllIlllIIIl + lIIlllIlllIIII;
            double lIIlllIllIlllI = lIIlllIlllIIIl / lIIlllIlllIllI;
            double lIIlllIllIllIl = lIIlllIlllIIII / (float)lIIlllIlllIlIl;
            int lIIlllIllIllII = (int)((double)lIIlllIlllIIll * lIIlllIllIlllI);
            int lIIlllIllIlIll = (int)((double)lIIlllIlllIIlI * lIIlllIllIllIl);
            Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            Renderer.NORMAL.gradientQuad(lIIllllIIIIlll += 2.0, lIIllllIIIIllI += 2.0, lIIlllIllIllII, 7.0, lIIlllIllIlIIl.healthColor1.get(), lIIlllIllIlIIl.healthColor2.get());
            Renderer.NORMAL.gradientQuad(lIIllllIIIIlll + (double)lIIlllIllIllII, lIIllllIIIIllI, lIIlllIllIlIll, 7.0, lIIlllIllIlIIl.healthColor2.get(), lIIlllIllIlIIl.healthColor3.get());
            Renderer.NORMAL.end();
            String lIIlllIllIlIlI = String.valueOf((double)Math.round((double)lIIlllIllIllll * 10.0) / 10.0);
            TextRenderer.get().begin(0.45);
            TextRenderer.get().render(lIIlllIllIlIlI, lIIllllIIIIlll, lIIllllIIIIllI, lIIlllIllIlIIl.hud.primaryColor.get());
            TextRenderer.get().end();
            RenderSystem.popMatrix();
        });
    }

    public CombatHud(HUD lIIlllllIllIIl) {
        super(lIIlllllIllIIl, "combat-info", "Displays information about your combat target.");
        CombatHud lIIlllllIlllII;
        lIIlllllIlllII.sgGeneral = lIIlllllIlllII.settings.getDefaultGroup();
        lIIlllllIlllII.scale = lIIlllllIlllII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of combat info.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
        lIIlllllIlllII.range = lIIlllllIlllII.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range to target players.").defaultValue(100.0).min(1.0).sliderMax(200.0).build());
        lIIlllllIlllII.ignoreFriends = lIIlllllIlllII.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends when targeting.").defaultValue(false).build());
        lIIlllllIlllII.displayPing = lIIlllllIlllII.sgGeneral.add(new BoolSetting.Builder().name("ping").description("Shows the player's ping.").defaultValue(true).build());
        lIIlllllIlllII.displayDistance = lIIlllllIlllII.sgGeneral.add(new BoolSetting.Builder().name("distance").description("Shows the distance between you and the player.").defaultValue(true).build());
        lIIlllllIlllII.displayedEnchantments = lIIlllllIlllII.sgGeneral.add(new EnchListSetting.Builder().name("displayed-enchantments").description("The enchantments that are shown on nametags.").defaultValue(CombatHud.getDefaultEnchantments()).build());
        lIIlllllIlllII.backgroundColor = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("background-color").description("Color of background.").defaultValue(new SettingColor(0, 0, 0, 64)).build());
        lIIlllllIlllII.enchantmentTextColor = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("enchantment-color").description("Color of enchantment text.").defaultValue(new SettingColor(255, 255, 255)).build());
        lIIlllllIlllII.pingColor1 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-1").description("Color of ping text when under 75.").defaultValue(new SettingColor(15, 255, 15)).build());
        lIIlllllIlllII.pingColor2 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-2").description("Color of ping text when between 75 and 200.").defaultValue(new SettingColor(255, 150, 15)).build());
        lIIlllllIlllII.pingColor3 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-3").description("Color of ping text when over 200.").defaultValue(new SettingColor(255, 15, 15)).build());
        lIIlllllIlllII.distColor1 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-1").description("The color when a player is within 10 blocks of you.").defaultValue(new SettingColor(255, 15, 15)).build());
        lIIlllllIlllII.distColor2 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-2").description("The color when a player is within 50 blocks of you.").defaultValue(new SettingColor(255, 150, 15)).build());
        lIIlllllIlllII.distColor3 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-3").description("The color when a player is greater then 50 blocks away from you.").defaultValue(new SettingColor(15, 255, 15)).build());
        lIIlllllIlllII.healthColor1 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("healh-stage-1").description("The color on the left of the health gradient.").defaultValue(new SettingColor(255, 15, 15)).build());
        lIIlllllIlllII.healthColor2 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("health-stage-2").description("The color in the middle of the health gradient.").defaultValue(new SettingColor(255, 150, 15)).build());
        lIIlllllIlllII.healthColor3 = lIIlllllIlllII.sgGeneral.add(new ColorSetting.Builder().name("health-stage-3").description("The color on the right of the health gradient.").defaultValue(new SettingColor(15, 255, 15)).build());
    }

    static {
        GREEN = new Color(15, 255, 15);
        RED = new Color(255, 15, 15);
        BLACK = new Color(0, 0, 0, 255);
    }

    @Override
    public void update(HudRenderer lIIlllllIlIllI) {
        CombatHud lIIlllllIlIlll;
        lIIlllllIlIlll.box.setSize(175.0 * lIIlllllIlIlll.scale.get(), 95.0 * lIIlllllIlIlll.scale.get());
    }

    public static List<Enchantment> getDefaultEnchantments() {
        ArrayList<Enchantment> lIIlllllIIIlII = new ArrayList<Enchantment>();
        for (Enchantment lIIlllllIIIlIl : Registry.ENCHANTMENT) {
            lIIlllllIIIlII.add(lIIlllllIIIlIl);
        }
        return lIIlllllIIIlII;
    }
}

