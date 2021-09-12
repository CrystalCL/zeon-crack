/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.RenderEntityEvent;
import minegame159.meteorclient.mixininterface.IEntityRenderer;
import minegame159.meteorclient.mixininterface.ILivingEntityRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.text.TextUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1158;
import net.minecraft.class_1160;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1921;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3887;
import net.minecraft.class_4050;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_895;
import net.minecraft.class_922;

public class ModelTweaks
extends Module {
    private final Setting<SettingColor> crystalsBottomColor;
    public final Setting<Boolean> renderBottom;
    public final Setting<Double> playersScale;
    public final Setting<Boolean> ignoreSelf;
    public final Setting<Boolean> useNameColor;
    private final SettingGroup sgPlayers;
    private final Setting<Boolean> playerTexture;
    public final Setting<Boolean> renderCore;
    public final Setting<Boolean> players;
    public final Setting<Boolean> crystals;
    public final Setting<Double> crystalsScale;
    private final Setting<SettingColor> crystalsCoreColor;
    public final Setting<Boolean> renderFrame;
    public final Setting<Double> crystalsRotationAngle;
    private final Setting<SettingColor> playersColor;
    public final Setting<Double> crystalsBounce;
    private final Setting<SettingColor> crystalsFrameColor;
    private final Setting<Boolean> crystalsTexture;
    private final class_2960 WHITE;
    private final float SINE_45_DEGREES;
    private final class_2960 TEXTURE;
    public final Setting<Double> crystalsRotationSpeed;
    private final SettingGroup sgCrystals;

    private float getYOffset(class_1511 class_15112, float f) {
        float f2 = (float)class_15112.field_7034 + f;
        float f3 = class_3532.method_15374((float)(f2 * 0.2f)) / 2.0f + 0.5f;
        f3 = (f3 * f3 + f3) * 0.4f * this.crystalsBounce.get().floatValue();
        return f3 - 1.4f;
    }

    @EventHandler
    private void onRenderCrystal(RenderEntityEvent.Crystal crystal) {
        Color color;
        Color color2;
        Color color3;
        if (this.crystals.get().booleanValue()) {
            crystal.setCancelled(true);
            color3 = this.crystalsBottomColor.get();
            color2 = this.crystalsCoreColor.get();
            color = this.crystalsFrameColor.get();
        }
        return;
        class_1921 class_19212 = this.crystalsTexture.get() != false ? class_1921.method_23578((class_2960)this.TEXTURE) : class_1921.method_23578((class_2960)this.WHITE);
        crystal.matrixStack.method_22903();
        crystal.matrixStack.method_22905(this.crystalsScale.get().floatValue(), this.crystalsScale.get().floatValue(), this.crystalsScale.get().floatValue());
        float f = ((float)crystal.endCrystalEntity.field_7034 + crystal.tickDelta) * (3.0f * this.crystalsRotationSpeed.get().floatValue());
        float f2 = this.getYOffset(crystal.endCrystalEntity, crystal.tickDelta);
        int n = class_4608.field_21444;
        class_4588 class_45882 = crystal.vertexConsumerProvider.getBuffer(class_19212);
        crystal.matrixStack.method_22903();
        crystal.matrixStack.method_22905(2.0f, 2.0f, 2.0f);
        crystal.matrixStack.method_22904(0.0, -0.5, 0.0);
        if (crystal.endCrystalEntity.method_6836() && this.renderBottom.get().booleanValue()) {
            crystal.bottom.method_22699(crystal.matrixStack, class_45882, crystal.light, n, (float)color3.r / 255.0f, (float)color3.g / 255.0f, (float)color3.b / 255.0f, (float)color3.a / 255.0f);
        }
        if (this.renderFrame.get().booleanValue()) {
            crystal.matrixStack.method_22907(class_1160.field_20705.method_23214(f));
            crystal.matrixStack.method_22904(0.0, (double)(1.5f + f2 / 2.0f), 0.0);
            crystal.matrixStack.method_22907(new class_1158(new class_1160(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.frame.method_22699(crystal.matrixStack, class_45882, crystal.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (this.renderFrame.get().booleanValue()) {
            crystal.matrixStack.method_22905(0.875f, 0.875f, 0.875f);
            crystal.matrixStack.method_22907(new class_1158(new class_1160(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.matrixStack.method_22907(class_1160.field_20705.method_23214(f));
            crystal.frame.method_22699(crystal.matrixStack, class_45882, crystal.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (this.renderCore.get().booleanValue()) {
            crystal.matrixStack.method_22905(0.875f, 0.875f, 0.875f);
            crystal.matrixStack.method_22907(new class_1158(new class_1160(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.matrixStack.method_22907(class_1160.field_20705.method_23214(f));
            crystal.core.method_22699(crystal.matrixStack, class_45882, crystal.light, n, (float)color2.r / 255.0f, (float)color2.g / 255.0f, (float)color2.b / 255.0f, (float)color2.a / 255.0f);
        }
        crystal.matrixStack.method_22909();
        class_2338 class_23382 = crystal.endCrystalEntity.method_6838();
        if (class_23382 != null) {
            float f3 = (float)class_23382.method_10263() + 0.5f;
            float f4 = (float)class_23382.method_10264() + 0.5f;
            float f5 = (float)class_23382.method_10260() + 0.5f;
            float f6 = (float)((double)f3 - crystal.endCrystalEntity.method_23317());
            float f7 = (float)((double)f4 - crystal.endCrystalEntity.method_23318());
            float f8 = (float)((double)f5 - crystal.endCrystalEntity.method_23321());
            crystal.matrixStack.method_22904((double)f6, (double)f7, (double)f8);
            class_895.method_3917((float)(-f6), (float)(-f7 + f2), (float)(-f8), (float)crystal.tickDelta, (int)crystal.endCrystalEntity.field_7034, (class_4587)crystal.matrixStack, (class_4597)crystal.vertexConsumerProvider, (int)crystal.light);
        }
        crystal.matrixStack.method_22909();
    }

    public ModelTweaks() {
        super(Categories.Render, "model-tweaks", "Changes the way certain entity models are rendered.");
        this.sgPlayers = this.settings.createGroup("Players");
        this.sgCrystals = this.settings.createGroup("Crystals");
        this.players = this.sgPlayers.add(new BoolSetting.Builder().name("enabled").description("Enables model tweaks for players.").defaultValue(true).build());
        this.ignoreSelf = this.sgPlayers.add(new BoolSetting.Builder().name("ignore-self").description("Ignores yourself when tweaking player models.").defaultValue(true).build());
        this.playersScale = this.sgPlayers.add(new DoubleSetting.Builder().name("scale").description("Players scale.").defaultValue(1.0).min(0.0).build());
        this.playerTexture = this.sgPlayers.add(new BoolSetting.Builder().name("texture").description("Enables player model textures.").defaultValue(false).build());
        this.playersColor = this.sgPlayers.add(new ColorSetting.Builder().name("color").description("The color of player models.").defaultValue(new SettingColor(255, 255, 255, 100, 0.007)).build());
        this.useNameColor = this.sgPlayers.add(new BoolSetting.Builder().name("use-name-color").description("Uses players name color for the color.").defaultValue(false).build());
        this.crystals = this.sgCrystals.add(new BoolSetting.Builder().name("enabled").description("Enables model tweaks for end crystals.").defaultValue(true).build());
        this.crystalsScale = this.sgCrystals.add(new DoubleSetting.Builder().name("scale").description("Crystal scale.").defaultValue(1.0).min(0.0).build());
        this.crystalsBounce = this.sgCrystals.add(new DoubleSetting.Builder().name("bounce").description("Crystal bounce.").defaultValue(1.0).min(0.0).build());
        this.crystalsRotationAngle = this.sgCrystals.add(new DoubleSetting.Builder().name("rotation-angle").description("Crystal model part rotation angle.").defaultValue(60.0).min(0.0).sliderMax(360.0).max(360.0).build());
        this.crystalsRotationSpeed = this.sgCrystals.add(new DoubleSetting.Builder().name("rotation-speed").description("Crystal model part rotation speed.").defaultValue(1.0).min(0.0).build());
        this.crystalsTexture = this.sgCrystals.add(new BoolSetting.Builder().name("texture").description("Enables crystal model textures.").defaultValue(false).build());
        this.renderBottom = this.sgCrystals.add(new BoolSetting.Builder().name("render-bottom").description("Enables rendering of the bottom part of the crystal.").defaultValue(true).build());
        this.crystalsBottomColor = this.sgCrystals.add(new ColorSetting.Builder().name("bottom-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        this.renderCore = this.sgCrystals.add(new BoolSetting.Builder().name("render-core").description("Enables rendering of the core of the crystal.").defaultValue(true).build());
        this.crystalsCoreColor = this.sgCrystals.add(new ColorSetting.Builder().name("core-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        this.renderFrame = this.sgCrystals.add(new BoolSetting.Builder().name("render-frame").description("Enables rendering of the frame of the crystal.").defaultValue(true).build());
        this.crystalsFrameColor = this.sgCrystals.add(new ColorSetting.Builder().name("frame-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        this.SINE_45_DEGREES = (float)Math.sin(0.7853981633974483);
        this.WHITE = new class_2960("meteor-client", "entity-texture.png");
        this.TEXTURE = new class_2960("textures/entity/end_crystal/end_crystal.png");
    }

    protected class_1921 getRenderLayer(class_1309 class_13092, boolean bl, boolean bl2, boolean bl3, RenderEntityEvent.LiveEntity liveEntity) {
        class_2960 class_29602;
        IEntityRenderer iEntityRenderer = (IEntityRenderer)this.mc.method_1561().method_3953((class_1297)liveEntity.entity);
        class_2960 class_29603 = class_29602 = this.playerTexture.get() != false ? iEntityRenderer.getTextureInterface((class_1297)class_13092) : this.WHITE;
        if (bl2) {
            return class_1921.method_29379((class_2960)class_29602);
        }
        if (bl) {
            return liveEntity.model.method_23500(class_29602);
        }
        return bl3 ? class_1921.method_23287((class_2960)class_29602) : null;
    }

    @EventHandler
    private void onRenderLiving(RenderEntityEvent.LiveEntity liveEntity) {
        float f;
        class_2350 class_23502;
        float f2;
        if (!(liveEntity.entity instanceof class_1657) || !this.players.get().booleanValue() || this.ignoreSelf.get().booleanValue() && liveEntity.entity == this.mc.field_1724) {
            return;
        }
        liveEntity.setCancelled(true);
        ILivingEntityRenderer iLivingEntityRenderer = (ILivingEntityRenderer)this.mc.method_1561().method_3953((class_1297)liveEntity.entity);
        Color color = this.useNameColor.get() != false ? TextUtils.getMostPopularColor(liveEntity.entity.method_5476()) : (Color)this.playersColor.get();
        liveEntity.matrixStack.method_22903();
        liveEntity.matrixStack.method_22905(this.playersScale.get().floatValue(), this.playersScale.get().floatValue(), this.playersScale.get().floatValue());
        liveEntity.model.field_3447 = liveEntity.entity.method_6055(liveEntity.tickDelta);
        liveEntity.model.field_3449 = liveEntity.entity.method_5765();
        liveEntity.model.field_3448 = liveEntity.entity.method_6109();
        float f3 = class_3532.method_17821((float)liveEntity.tickDelta, (float)liveEntity.entity.field_6220, (float)liveEntity.entity.field_6283);
        float f4 = class_3532.method_17821((float)liveEntity.tickDelta, (float)liveEntity.entity.field_6259, (float)liveEntity.entity.field_6241);
        float f5 = f4 - f3;
        if (liveEntity.entity.method_5765() && liveEntity.entity.method_5854() instanceof class_1309) {
            class_1309 class_13092 = (class_1309)liveEntity.entity.method_5854();
            f3 = class_3532.method_17821((float)liveEntity.tickDelta, (float)class_13092.field_6220, (float)class_13092.field_6283);
            f5 = f4 - f3;
            f2 = class_3532.method_15393((float)f5);
            if (f2 < -85.0f) {
                f2 = -85.0f;
            }
            if (f2 >= 85.0f) {
                f2 = 85.0f;
            }
            f3 = f4 - f2;
            if (f2 * f2 > 2500.0f) {
                f3 += f2 * 0.2f;
            }
            f5 = f4 - f3;
        }
        float f6 = class_3532.method_16439((float)liveEntity.tickDelta, (float)liveEntity.entity.field_6004, (float)liveEntity.entity.field_5965);
        if (liveEntity.entity.method_18376() == class_4050.field_18078 && (class_23502 = liveEntity.entity.method_18401()) != null) {
            f = liveEntity.entity.method_18381(class_4050.field_18076) - 0.1f;
            liveEntity.matrixStack.method_22904((double)((float)(-class_23502.method_10148()) * f), 0.0, (double)((float)(-class_23502.method_10165()) * f));
        }
        f2 = (float)liveEntity.entity.field_6012 + liveEntity.tickDelta;
        iLivingEntityRenderer.setupTransformsInterface(liveEntity.entity, liveEntity.matrixStack, f2, f3, liveEntity.tickDelta);
        liveEntity.matrixStack.method_22905(-1.0f, -1.0f, 1.0f);
        iLivingEntityRenderer.scaleInterface(liveEntity.entity, liveEntity.matrixStack, liveEntity.tickDelta);
        liveEntity.matrixStack.method_22904(0.0, (double)-1.501f, 0.0);
        f = 0.0f;
        float f7 = 0.0f;
        if (!liveEntity.entity.method_5765() && liveEntity.entity.method_5805()) {
            f = class_3532.method_16439((float)liveEntity.tickDelta, (float)liveEntity.entity.field_6211, (float)liveEntity.entity.field_6225);
            f7 = liveEntity.entity.field_6249 - liveEntity.entity.field_6225 * (1.0f - liveEntity.tickDelta);
            if (liveEntity.entity.method_6109()) {
                f7 *= 3.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
        }
        liveEntity.model.method_2816((class_1297)liveEntity.entity, f7, f, liveEntity.tickDelta);
        liveEntity.model.method_2819((class_1297)liveEntity.entity, f7, f, f2, f5, f6);
        class_310 class_3102 = class_310.method_1551();
        boolean bl = iLivingEntityRenderer.isVisibleInterface(liveEntity.entity);
        boolean bl2 = !bl && !liveEntity.entity.method_5756((class_1657)class_3102.field_1724);
        boolean bl3 = class_3102.method_27022((class_1297)liveEntity.entity);
        class_1921 class_19212 = this.getRenderLayer(liveEntity.entity, bl, bl2, bl3, liveEntity);
        if (class_19212 != null) {
            class_4588 class_45882 = liveEntity.vertexConsumerProvider.getBuffer(class_19212);
            int n = class_922.method_23622((class_1309)liveEntity.entity, (float)iLivingEntityRenderer.getAnimationCounterInterface(liveEntity.entity, liveEntity.tickDelta));
            liveEntity.model.method_2828(liveEntity.matrixStack, class_45882, liveEntity.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (!liveEntity.entity.method_7325()) {
            for (class_3887 class_38872 : liveEntity.features) {
                class_38872.method_4199(liveEntity.matrixStack, liveEntity.vertexConsumerProvider, liveEntity.light, (class_1297)liveEntity.entity, f7, f, liveEntity.tickDelta, f2, f5, f6);
            }
        }
        liveEntity.matrixStack.method_22909();
    }
}

