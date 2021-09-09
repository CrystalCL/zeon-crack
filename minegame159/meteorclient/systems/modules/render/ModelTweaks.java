/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Quaternion
 *  net.minecraft.util.math.Vec3f
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.render.entity.feature.FeatureRenderer
 *  net.minecraft.entity.EntityPose
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.client.render.entity.model.EntityModel
 *  net.minecraft.client.render.entity.EnderDragonEntityRenderer
 *  net.minecraft.client.render.entity.LivingEntityRenderer
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
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.entity.EntityPose;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;

public class ModelTweaks
extends Module {
    private final /* synthetic */ SettingGroup sgPlayers;
    public final /* synthetic */ Setting<Double> crystalsScale;
    private final /* synthetic */ Setting<SettingColor> playersColor;
    private final /* synthetic */ Setting<SettingColor> crystalsFrameColor;
    private final /* synthetic */ Identifier WHITE;
    public final /* synthetic */ Setting<Boolean> useNameColor;
    private final /* synthetic */ SettingGroup sgCrystals;
    public final /* synthetic */ Setting<Boolean> renderFrame;
    public final /* synthetic */ Setting<Boolean> renderBottom;
    public final /* synthetic */ Setting<Boolean> crystals;
    public final /* synthetic */ Setting<Boolean> players;
    private final /* synthetic */ Setting<Boolean> playerTexture;
    public final /* synthetic */ Setting<Boolean> ignoreSelf;
    private final /* synthetic */ float SINE_45_DEGREES;
    public final /* synthetic */ Setting<Double> crystalsRotationAngle;
    private final /* synthetic */ Setting<SettingColor> crystalsCoreColor;
    public final /* synthetic */ Setting<Double> crystalsBounce;
    public final /* synthetic */ Setting<Boolean> renderCore;
    private final /* synthetic */ Setting<Boolean> crystalsTexture;
    public final /* synthetic */ Setting<Double> crystalsRotationSpeed;
    public final /* synthetic */ Setting<Double> playersScale;
    private final /* synthetic */ Setting<SettingColor> crystalsBottomColor;
    private final /* synthetic */ Identifier TEXTURE;

    protected RenderLayer getRenderLayer(LivingEntity lllllllllllllllllIllIlIIIllIllII, boolean lllllllllllllllllIllIlIIIllIIIll, boolean lllllllllllllllllIllIlIIIllIlIlI, boolean lllllllllllllllllIllIlIIIllIlIIl, RenderEntityEvent.LiveEntity lllllllllllllllllIllIlIIIllIlIII) {
        Identifier lllllllllllllllllIllIlIIIllIIllI;
        ModelTweaks lllllllllllllllllIllIlIIIllIIlIl;
        IEntityRenderer lllllllllllllllllIllIlIIIllIIlll = (IEntityRenderer)lllllllllllllllllIllIlIIIllIIlIl.mc.getEntityRenderDispatcher().getRenderer((Entity)lllllllllllllllllIllIlIIIllIlIII.entity);
        Identifier Identifier2 = lllllllllllllllllIllIlIIIllIIllI = lllllllllllllllllIllIlIIIllIIlIl.playerTexture.get() != false ? lllllllllllllllllIllIlIIIllIIlll.getTextureInterface((Entity)lllllllllllllllllIllIlIIIllIllII) : lllllllllllllllllIllIlIIIllIIlIl.WHITE;
        if (lllllllllllllllllIllIlIIIllIlIlI) {
            return RenderLayer.getItemEntityTranslucentCull((Identifier)lllllllllllllllllIllIlIIIllIIllI);
        }
        if (lllllllllllllllllIllIlIIIllIIIll) {
            return lllllllllllllllllIllIlIIIllIlIII.model.getLayer(lllllllllllllllllIllIlIIIllIIllI);
        }
        return lllllllllllllllllIllIlIIIllIlIIl ? RenderLayer.getOutline((Identifier)lllllllllllllllllIllIlIIIllIIllI) : null;
    }

    private float getYOffset(EndCrystalEntity lllllllllllllllllIllIlIIIIIlllll, float lllllllllllllllllIllIlIIIIIllllI) {
        ModelTweaks lllllllllllllllllIllIlIIIIlIIIII;
        float lllllllllllllllllIllIlIIIIlIIIlI = (float)lllllllllllllllllIllIlIIIIIlllll.endCrystalAge + lllllllllllllllllIllIlIIIIIllllI;
        float lllllllllllllllllIllIlIIIIlIIIIl = MathHelper.sin((float)(lllllllllllllllllIllIlIIIIlIIIlI * 0.2f)) / 2.0f + 0.5f;
        lllllllllllllllllIllIlIIIIlIIIIl = (lllllllllllllllllIllIlIIIIlIIIIl * lllllllllllllllllIllIlIIIIlIIIIl + lllllllllllllllllIllIlIIIIlIIIIl) * 0.4f * lllllllllllllllllIllIlIIIIlIIIII.crystalsBounce.get().floatValue();
        return lllllllllllllllllIllIlIIIIlIIIIl - 1.4f;
    }

    public ModelTweaks() {
        super(Categories.Render, "model-tweaks", "Changes the way certain entity models are rendered.");
        ModelTweaks lllllllllllllllllIllIlIIlIllIIIl;
        lllllllllllllllllIllIlIIlIllIIIl.sgPlayers = lllllllllllllllllIllIlIIlIllIIIl.settings.createGroup("Players");
        lllllllllllllllllIllIlIIlIllIIIl.sgCrystals = lllllllllllllllllIllIlIIlIllIIIl.settings.createGroup("Crystals");
        lllllllllllllllllIllIlIIlIllIIIl.players = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new BoolSetting.Builder().name("enabled").description("Enables model tweaks for players.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.ignoreSelf = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new BoolSetting.Builder().name("ignore-self").description("Ignores yourself when tweaking player models.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.playersScale = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new DoubleSetting.Builder().name("scale").description("Players scale.").defaultValue(1.0).min(0.0).build());
        lllllllllllllllllIllIlIIlIllIIIl.playerTexture = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new BoolSetting.Builder().name("texture").description("Enables player model textures.").defaultValue(false).build());
        lllllllllllllllllIllIlIIlIllIIIl.playersColor = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new ColorSetting.Builder().name("color").description("The color of player models.").defaultValue(new SettingColor(255, 255, 255, 100, 0.007)).build());
        lllllllllllllllllIllIlIIlIllIIIl.useNameColor = lllllllllllllllllIllIlIIlIllIIIl.sgPlayers.add(new BoolSetting.Builder().name("use-name-color").description("Uses players name color for the color.").defaultValue(false).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystals = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new BoolSetting.Builder().name("enabled").description("Enables model tweaks for end crystals.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsScale = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new DoubleSetting.Builder().name("scale").description("Crystal scale.").defaultValue(1.0).min(0.0).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsBounce = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new DoubleSetting.Builder().name("bounce").description("Crystal bounce.").defaultValue(1.0).min(0.0).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsRotationAngle = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new DoubleSetting.Builder().name("rotation-angle").description("Crystal model part rotation angle.").defaultValue(60.0).min(0.0).sliderMax(360.0).max(360.0).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsRotationSpeed = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new DoubleSetting.Builder().name("rotation-speed").description("Crystal model part rotation speed.").defaultValue(1.0).min(0.0).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsTexture = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new BoolSetting.Builder().name("texture").description("Enables crystal model textures.").defaultValue(false).build());
        lllllllllllllllllIllIlIIlIllIIIl.renderBottom = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new BoolSetting.Builder().name("render-bottom").description("Enables rendering of the bottom part of the crystal.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsBottomColor = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new ColorSetting.Builder().name("bottom-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        lllllllllllllllllIllIlIIlIllIIIl.renderCore = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new BoolSetting.Builder().name("render-core").description("Enables rendering of the core of the crystal.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsCoreColor = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new ColorSetting.Builder().name("core-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        lllllllllllllllllIllIlIIlIllIIIl.renderFrame = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new BoolSetting.Builder().name("render-frame").description("Enables rendering of the frame of the crystal.").defaultValue(true).build());
        lllllllllllllllllIllIlIIlIllIIIl.crystalsFrameColor = lllllllllllllllllIllIlIIlIllIIIl.sgCrystals.add(new ColorSetting.Builder().name("frame-color").description("The color of end crystal models.").defaultValue(new SettingColor(255, 255, 255, 0.007)).build());
        lllllllllllllllllIllIlIIlIllIIIl.SINE_45_DEGREES = (float)Math.sin(0.7853981633974483);
        lllllllllllllllllIllIlIIlIllIIIl.WHITE = new Identifier("meteor-client", "entity-texture.png");
        lllllllllllllllllIllIlIIlIllIIIl.TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal.png");
    }

    @EventHandler
    private void onRenderLiving(RenderEntityEvent.LiveEntity lllllllllllllllllIllIlIIlIIIIllI) {
        Direction lllllllllllllllllIllIlIIlIIllIll;
        ModelTweaks lllllllllllllllllIllIlIIlIIIIlll;
        if (!(lllllllllllllllllIllIlIIlIIIIllI.entity instanceof PlayerEntity) || !lllllllllllllllllIllIlIIlIIIIlll.players.get().booleanValue() || lllllllllllllllllIllIlIIlIIIIlll.ignoreSelf.get().booleanValue() && lllllllllllllllllIllIlIIlIIIIllI.entity == lllllllllllllllllIllIlIIlIIIIlll.mc.player) {
            return;
        }
        lllllllllllllllllIllIlIIlIIIIllI.setCancelled(true);
        ILivingEntityRenderer lllllllllllllllllIllIlIIlIIlIlIl = (ILivingEntityRenderer)lllllllllllllllllIllIlIIlIIIIlll.mc.getEntityRenderDispatcher().getRenderer((Entity)lllllllllllllllllIllIlIIlIIIIllI.entity);
        Color lllllllllllllllllIllIlIIlIIlIlII = lllllllllllllllllIllIlIIlIIIIlll.useNameColor.get() != false ? TextUtils.getMostPopularColor(lllllllllllllllllIllIlIIlIIIIllI.entity.getDisplayName()) : (Color)lllllllllllllllllIllIlIIlIIIIlll.playersColor.get();
        lllllllllllllllllIllIlIIlIIIIllI.matrixStack.push();
        lllllllllllllllllIllIlIIlIIIIllI.matrixStack.scale(lllllllllllllllllIllIlIIlIIIIlll.playersScale.get().floatValue(), lllllllllllllllllIllIlIIlIIIIlll.playersScale.get().floatValue(), lllllllllllllllllIllIlIIlIIIIlll.playersScale.get().floatValue());
        lllllllllllllllllIllIlIIlIIIIllI.model.handSwingProgress = lllllllllllllllllIllIlIIlIIIIllI.entity.getHandSwingProgress(lllllllllllllllllIllIlIIlIIIIllI.tickDelta);
        lllllllllllllllllIllIlIIlIIIIllI.model.riding = lllllllllllllllllIllIlIIlIIIIllI.entity.hasVehicle();
        lllllllllllllllllIllIlIIlIIIIllI.model.child = lllllllllllllllllIllIlIIlIIIIllI.entity.isBaby();
        float lllllllllllllllllIllIlIIlIIlIIll = MathHelper.lerpAngleDegrees((float)lllllllllllllllllIllIlIIlIIIIllI.tickDelta, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.prevBodyYaw, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.bodyYaw);
        float lllllllllllllllllIllIlIIlIIlIIlI = MathHelper.lerpAngleDegrees((float)lllllllllllllllllIllIlIIlIIIIllI.tickDelta, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.prevHeadYaw, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.headYaw);
        float lllllllllllllllllIllIlIIlIIlIIIl = lllllllllllllllllIllIlIIlIIlIIlI - lllllllllllllllllIllIlIIlIIlIIll;
        if (lllllllllllllllllIllIlIIlIIIIllI.entity.hasVehicle() && lllllllllllllllllIllIlIIlIIIIllI.entity.getVehicle() instanceof LivingEntity) {
            LivingEntity lllllllllllllllllIllIlIIlIIllllI = (LivingEntity)lllllllllllllllllIllIlIIlIIIIllI.entity.getVehicle();
            lllllllllllllllllIllIlIIlIIlIIll = MathHelper.lerpAngleDegrees((float)lllllllllllllllllIllIlIIlIIIIllI.tickDelta, (float)lllllllllllllllllIllIlIIlIIllllI.prevBodyYaw, (float)lllllllllllllllllIllIlIIlIIllllI.bodyYaw);
            lllllllllllllllllIllIlIIlIIlIIIl = lllllllllllllllllIllIlIIlIIlIIlI - lllllllllllllllllIllIlIIlIIlIIll;
            float lllllllllllllllllIllIlIIlIIlllIl = MathHelper.wrapDegrees((float)lllllllllllllllllIllIlIIlIIlIIIl);
            if (lllllllllllllllllIllIlIIlIIlllIl < -85.0f) {
                lllllllllllllllllIllIlIIlIIlllIl = -85.0f;
            }
            if (lllllllllllllllllIllIlIIlIIlllIl >= 85.0f) {
                lllllllllllllllllIllIlIIlIIlllIl = 85.0f;
            }
            lllllllllllllllllIllIlIIlIIlIIll = lllllllllllllllllIllIlIIlIIlIIlI - lllllllllllllllllIllIlIIlIIlllIl;
            if (lllllllllllllllllIllIlIIlIIlllIl * lllllllllllllllllIllIlIIlIIlllIl > 2500.0f) {
                lllllllllllllllllIllIlIIlIIlIIll += lllllllllllllllllIllIlIIlIIlllIl * 0.2f;
            }
            lllllllllllllllllIllIlIIlIIlIIIl = lllllllllllllllllIllIlIIlIIlIIlI - lllllllllllllllllIllIlIIlIIlIIll;
        }
        float lllllllllllllllllIllIlIIlIIIllll = MathHelper.lerp((float)lllllllllllllllllIllIlIIlIIIIllI.tickDelta, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.prevPitch, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.pitch);
        if (lllllllllllllllllIllIlIIlIIIIllI.entity.getPose() == EntityPose.SLEEPING && (lllllllllllllllllIllIlIIlIIllIll = lllllllllllllllllIllIlIIlIIIIllI.entity.getSleepingDirection()) != null) {
            float lllllllllllllllllIllIlIIlIIlllII = lllllllllllllllllIllIlIIlIIIIllI.entity.getEyeHeight(EntityPose.STANDING) - 0.1f;
            lllllllllllllllllIllIlIIlIIIIllI.matrixStack.translate((double)((float)(-lllllllllllllllllIllIlIIlIIllIll.getOffsetX()) * lllllllllllllllllIllIlIIlIIlllII), 0.0, (double)((float)(-lllllllllllllllllIllIlIIlIIllIll.getOffsetZ()) * lllllllllllllllllIllIlIIlIIlllII));
        }
        float lllllllllllllllllIllIlIIlIIlIIII = (float)lllllllllllllllllIllIlIIlIIIIllI.entity.age + lllllllllllllllllIllIlIIlIIIIllI.tickDelta;
        lllllllllllllllllIllIlIIlIIlIlIl.setupTransformsInterface(lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIIllI.matrixStack, lllllllllllllllllIllIlIIlIIlIIII, lllllllllllllllllIllIlIIlIIlIIll, lllllllllllllllllIllIlIIlIIIIllI.tickDelta);
        lllllllllllllllllIllIlIIlIIIIllI.matrixStack.scale(-1.0f, -1.0f, 1.0f);
        lllllllllllllllllIllIlIIlIIlIlIl.scaleInterface(lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIIllI.matrixStack, lllllllllllllllllIllIlIIlIIIIllI.tickDelta);
        lllllllllllllllllIllIlIIlIIIIllI.matrixStack.translate(0.0, (double)-1.501f, 0.0);
        float lllllllllllllllllIllIlIIlIIIlllI = 0.0f;
        float lllllllllllllllllIllIlIIlIIIllIl = 0.0f;
        if (!lllllllllllllllllIllIlIIlIIIIllI.entity.hasVehicle() && lllllllllllllllllIllIlIIlIIIIllI.entity.isAlive()) {
            lllllllllllllllllIllIlIIlIIIlllI = MathHelper.lerp((float)lllllllllllllllllIllIlIIlIIIIllI.tickDelta, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.lastLimbDistance, (float)lllllllllllllllllIllIlIIlIIIIllI.entity.limbDistance);
            lllllllllllllllllIllIlIIlIIIllIl = lllllllllllllllllIllIlIIlIIIIllI.entity.limbAngle - lllllllllllllllllIllIlIIlIIIIllI.entity.limbDistance * (1.0f - lllllllllllllllllIllIlIIlIIIIllI.tickDelta);
            if (lllllllllllllllllIllIlIIlIIIIllI.entity.isBaby()) {
                lllllllllllllllllIllIlIIlIIIllIl *= 3.0f;
            }
            if (lllllllllllllllllIllIlIIlIIIlllI > 1.0f) {
                lllllllllllllllllIllIlIIlIIIlllI = 1.0f;
            }
        }
        lllllllllllllllllIllIlIIlIIIIllI.model.animateModel((Entity)lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIllIl, lllllllllllllllllIllIlIIlIIIlllI, lllllllllllllllllIllIlIIlIIIIllI.tickDelta);
        lllllllllllllllllIllIlIIlIIIIllI.model.setAngles((Entity)lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIllIl, lllllllllllllllllIllIlIIlIIIlllI, lllllllllllllllllIllIlIIlIIlIIII, lllllllllllllllllIllIlIIlIIlIIIl, lllllllllllllllllIllIlIIlIIIllll);
        MinecraftClient lllllllllllllllllIllIlIIlIIIllII = MinecraftClient.getInstance();
        boolean lllllllllllllllllIllIlIIlIIIlIll = lllllllllllllllllIllIlIIlIIlIlIl.isVisibleInterface(lllllllllllllllllIllIlIIlIIIIllI.entity);
        boolean lllllllllllllllllIllIlIIlIIIlIlI = !lllllllllllllllllIllIlIIlIIIlIll && !lllllllllllllllllIllIlIIlIIIIllI.entity.isInvisibleTo((PlayerEntity)lllllllllllllllllIllIlIIlIIIllII.player);
        boolean lllllllllllllllllIllIlIIlIIIlIIl = lllllllllllllllllIllIlIIlIIIllII.hasOutline((Entity)lllllllllllllllllIllIlIIlIIIIllI.entity);
        RenderLayer lllllllllllllllllIllIlIIlIIIlIII = lllllllllllllllllIllIlIIlIIIIlll.getRenderLayer(lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIlIll, lllllllllllllllllIllIlIIlIIIlIlI, lllllllllllllllllIllIlIIlIIIlIIl, lllllllllllllllllIllIlIIlIIIIllI);
        if (lllllllllllllllllIllIlIIlIIIlIII != null) {
            VertexConsumer lllllllllllllllllIllIlIIlIIllIlI = lllllllllllllllllIllIlIIlIIIIllI.vertexConsumerProvider.getBuffer(lllllllllllllllllIllIlIIlIIIlIII);
            int lllllllllllllllllIllIlIIlIIllIIl = LivingEntityRenderer.getOverlay((LivingEntity)lllllllllllllllllIllIlIIlIIIIllI.entity, (float)lllllllllllllllllIllIlIIlIIlIlIl.getAnimationCounterInterface(lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIIllI.tickDelta));
            lllllllllllllllllIllIlIIlIIIIllI.model.render(lllllllllllllllllIllIlIIlIIIIllI.matrixStack, lllllllllllllllllIllIlIIlIIllIlI, lllllllllllllllllIllIlIIlIIIIllI.light, lllllllllllllllllIllIlIIlIIllIIl, (float)lllllllllllllllllIllIlIIlIIlIlII.r / 255.0f, (float)lllllllllllllllllIllIlIIlIIlIlII.g / 255.0f, (float)lllllllllllllllllIllIlIIlIIlIlII.b / 255.0f, (float)lllllllllllllllllIllIlIIlIIlIlII.a / 255.0f);
        }
        if (!lllllllllllllllllIllIlIIlIIIIllI.entity.isSpectator()) {
            for (FeatureRenderer<LivingEntity, EntityModel<LivingEntity>> lllllllllllllllllIllIlIIlIIllIII : lllllllllllllllllIllIlIIlIIIIllI.features) {
                lllllllllllllllllIllIlIIlIIllIII.render(lllllllllllllllllIllIlIIlIIIIllI.matrixStack, lllllllllllllllllIllIlIIlIIIIllI.vertexConsumerProvider, lllllllllllllllllIllIlIIlIIIIllI.light, (Entity)lllllllllllllllllIllIlIIlIIIIllI.entity, lllllllllllllllllIllIlIIlIIIllIl, lllllllllllllllllIllIlIIlIIIlllI, lllllllllllllllllIllIlIIlIIIIllI.tickDelta, lllllllllllllllllIllIlIIlIIlIIII, lllllllllllllllllIllIlIIlIIlIIIl, lllllllllllllllllIllIlIIlIIIllll);
            }
        }
        lllllllllllllllllIllIlIIlIIIIllI.matrixStack.pop();
    }

    @EventHandler
    private void onRenderCrystal(RenderEntityEvent.Crystal lllllllllllllllllIllIlIIIlIIIlIl) {
        ModelTweaks lllllllllllllllllIllIlIIIIlllIll;
        if (!lllllllllllllllllIllIlIIIIlllIll.crystals.get().booleanValue()) {
            return;
        }
        lllllllllllllllllIllIlIIIlIIIlIl.setCancelled(true);
        Color lllllllllllllllllIllIlIIIlIIIlII = lllllllllllllllllIllIlIIIIlllIll.crystalsBottomColor.get();
        Color lllllllllllllllllIllIlIIIlIIIIll = lllllllllllllllllIllIlIIIIlllIll.crystalsCoreColor.get();
        Color lllllllllllllllllIllIlIIIlIIIIlI = lllllllllllllllllIllIlIIIIlllIll.crystalsFrameColor.get();
        RenderLayer lllllllllllllllllIllIlIIIlIIIIIl = lllllllllllllllllIllIlIIIIlllIll.crystalsTexture.get() != false ? RenderLayer.getEntityCutoutNoCull((Identifier)lllllllllllllllllIllIlIIIIlllIll.TEXTURE) : RenderLayer.getEntityCutoutNoCull((Identifier)lllllllllllllllllIllIlIIIIlllIll.WHITE);
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.push();
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.scale(lllllllllllllllllIllIlIIIIlllIll.crystalsScale.get().floatValue(), lllllllllllllllllIllIlIIIIlllIll.crystalsScale.get().floatValue(), lllllllllllllllllIllIlIIIIlllIll.crystalsScale.get().floatValue());
        float lllllllllllllllllIllIlIIIlIIIIII = ((float)lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.endCrystalAge + lllllllllllllllllIllIlIIIlIIIlIl.tickDelta) * (3.0f * lllllllllllllllllIllIlIIIIlllIll.crystalsRotationSpeed.get().floatValue());
        float lllllllllllllllllIllIlIIIIllllll = lllllllllllllllllIllIlIIIIlllIll.getYOffset(lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity, lllllllllllllllllIllIlIIIlIIIlIl.tickDelta);
        int lllllllllllllllllIllIlIIIIlllllI = OverlayTexture.DEFAULT_UV;
        VertexConsumer lllllllllllllllllIllIlIIIIllllIl = lllllllllllllllllIllIlIIIlIIIlIl.vertexConsumerProvider.getBuffer(lllllllllllllllllIllIlIIIlIIIIIl);
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.push();
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.scale(2.0f, 2.0f, 2.0f);
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.translate(0.0, -0.5, 0.0);
        if (lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.shouldShowBottom() && lllllllllllllllllIllIlIIIIlllIll.renderBottom.get().booleanValue()) {
            lllllllllllllllllIllIlIIIlIIIlIl.bottom.render(lllllllllllllllllIllIlIIIlIIIlIl.matrixStack, lllllllllllllllllIllIlIIIIllllIl, lllllllllllllllllIllIlIIIlIIIlIl.light, lllllllllllllllllIllIlIIIIlllllI, (float)lllllllllllllllllIllIlIIIlIIIlII.r / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIlII.g / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIlII.b / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIlII.a / 255.0f);
        }
        if (lllllllllllllllllIllIlIIIIlllIll.renderFrame.get().booleanValue()) {
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lllllllllllllllllIllIlIIIlIIIIII));
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.translate(0.0, (double)(1.5f + lllllllllllllllllIllIlIIIIllllll / 2.0f), 0.0);
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(new Quaternion(new Vec3f(lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES, 0.0f, lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES), lllllllllllllllllIllIlIIIIlllIll.crystalsRotationAngle.get().floatValue(), true));
            lllllllllllllllllIllIlIIIlIIIlIl.frame.render(lllllllllllllllllIllIlIIIlIIIlIl.matrixStack, lllllllllllllllllIllIlIIIIllllIl, lllllllllllllllllIllIlIIIlIIIlIl.light, lllllllllllllllllIllIlIIIIlllllI, (float)lllllllllllllllllIllIlIIIlIIIIlI.r / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.g / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.b / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.a / 255.0f);
        }
        if (lllllllllllllllllIllIlIIIIlllIll.renderFrame.get().booleanValue()) {
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.scale(0.875f, 0.875f, 0.875f);
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(new Quaternion(new Vec3f(lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES, 0.0f, lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES), lllllllllllllllllIllIlIIIIlllIll.crystalsRotationAngle.get().floatValue(), true));
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lllllllllllllllllIllIlIIIlIIIIII));
            lllllllllllllllllIllIlIIIlIIIlIl.frame.render(lllllllllllllllllIllIlIIIlIIIlIl.matrixStack, lllllllllllllllllIllIlIIIIllllIl, lllllllllllllllllIllIlIIIlIIIlIl.light, lllllllllllllllllIllIlIIIIlllllI, (float)lllllllllllllllllIllIlIIIlIIIIlI.r / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.g / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.b / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIlI.a / 255.0f);
        }
        if (lllllllllllllllllIllIlIIIIlllIll.renderCore.get().booleanValue()) {
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.scale(0.875f, 0.875f, 0.875f);
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(new Quaternion(new Vec3f(lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES, 0.0f, lllllllllllllllllIllIlIIIIlllIll.SINE_45_DEGREES), lllllllllllllllllIllIlIIIIlllIll.crystalsRotationAngle.get().floatValue(), true));
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(lllllllllllllllllIllIlIIIlIIIIII));
            lllllllllllllllllIllIlIIIlIIIlIl.core.render(lllllllllllllllllIllIlIIIlIIIlIl.matrixStack, lllllllllllllllllIllIlIIIIllllIl, lllllllllllllllllIllIlIIIlIIIlIl.light, lllllllllllllllllIllIlIIIIlllllI, (float)lllllllllllllllllIllIlIIIlIIIIll.r / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIll.g / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIll.b / 255.0f, (float)lllllllllllllllllIllIlIIIlIIIIll.a / 255.0f);
        }
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.pop();
        BlockPos lllllllllllllllllIllIlIIIIllllII = lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.getBeamTarget();
        if (lllllllllllllllllIllIlIIIIllllII != null) {
            float lllllllllllllllllIllIlIIIlIIllII = (float)lllllllllllllllllIllIlIIIIllllII.getX() + 0.5f;
            float lllllllllllllllllIllIlIIIlIIlIll = (float)lllllllllllllllllIllIlIIIIllllII.getY() + 0.5f;
            float lllllllllllllllllIllIlIIIlIIlIlI = (float)lllllllllllllllllIllIlIIIIllllII.getZ() + 0.5f;
            float lllllllllllllllllIllIlIIIlIIlIIl = (float)((double)lllllllllllllllllIllIlIIIlIIllII - lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.getX());
            float lllllllllllllllllIllIlIIIlIIlIII = (float)((double)lllllllllllllllllIllIlIIIlIIlIll - lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.getY());
            float lllllllllllllllllIllIlIIIlIIIlll = (float)((double)lllllllllllllllllIllIlIIIlIIlIlI - lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.getZ());
            lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.translate((double)lllllllllllllllllIllIlIIIlIIlIIl, (double)lllllllllllllllllIllIlIIIlIIlIII, (double)lllllllllllllllllIllIlIIIlIIIlll);
            EnderDragonEntityRenderer.renderCrystalBeam((float)(-lllllllllllllllllIllIlIIIlIIlIIl), (float)(-lllllllllllllllllIllIlIIIlIIlIII + lllllllllllllllllIllIlIIIIllllll), (float)(-lllllllllllllllllIllIlIIIlIIIlll), (float)lllllllllllllllllIllIlIIIlIIIlIl.tickDelta, (int)lllllllllllllllllIllIlIIIlIIIlIl.endCrystalEntity.endCrystalAge, (MatrixStack)lllllllllllllllllIllIlIIIlIIIlIl.matrixStack, (VertexConsumerProvider)lllllllllllllllllIllIlIIIlIIIlIl.vertexConsumerProvider, (int)lllllllllllllllllIllIlIIIlIIIlIl.light);
        }
        lllllllllllllllllIllIlIIIlIIIlIl.matrixStack.pop();
    }
}

