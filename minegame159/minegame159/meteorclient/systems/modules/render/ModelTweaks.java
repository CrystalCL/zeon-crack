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
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;

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
    private final Identifier WHITE;
    private final float SINE_45_DEGREES;
    private final Identifier TEXTURE;
    public final Setting<Double> crystalsRotationSpeed;
    private final SettingGroup sgCrystals;

    private float getYOffset(EndCrystalEntity EndCrystalEntity2, float f) {
        float f2 = (float)EndCrystalEntity2.endCrystalAge + f;
        float f3 = MathHelper.sin((float)(f2 * 0.2f)) / 2.0f + 0.5f;
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
        RenderLayer RenderLayer2 = this.crystalsTexture.get() != false ? RenderLayer.getEntityCutoutNoCull((Identifier)this.TEXTURE) : RenderLayer.getEntityCutoutNoCull((Identifier)this.WHITE);
        crystal.matrixStack.push();
        crystal.matrixStack.scale(this.crystalsScale.get().floatValue(), this.crystalsScale.get().floatValue(), this.crystalsScale.get().floatValue());
        float f = ((float)crystal.endCrystalEntity.endCrystalAge + crystal.tickDelta) * (3.0f * this.crystalsRotationSpeed.get().floatValue());
        float f2 = this.getYOffset(crystal.endCrystalEntity, crystal.tickDelta);
        int n = OverlayTexture.DEFAULT_UV;
        VertexConsumer VertexConsumer2 = crystal.vertexConsumerProvider.getBuffer(RenderLayer2);
        crystal.matrixStack.push();
        crystal.matrixStack.scale(2.0f, 2.0f, 2.0f);
        crystal.matrixStack.translate(0.0, -0.5, 0.0);
        if (crystal.endCrystalEntity.shouldShowBottom() && this.renderBottom.get().booleanValue()) {
            crystal.bottom.render(crystal.matrixStack, VertexConsumer2, crystal.light, n, (float)color3.r / 255.0f, (float)color3.g / 255.0f, (float)color3.b / 255.0f, (float)color3.a / 255.0f);
        }
        if (this.renderFrame.get().booleanValue()) {
            crystal.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
            crystal.matrixStack.translate(0.0, (double)(1.5f + f2 / 2.0f), 0.0);
            crystal.matrixStack.multiply(new Quaternion(new Vec3f(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.frame.render(crystal.matrixStack, VertexConsumer2, crystal.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (this.renderFrame.get().booleanValue()) {
            crystal.matrixStack.scale(0.875f, 0.875f, 0.875f);
            crystal.matrixStack.multiply(new Quaternion(new Vec3f(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
            crystal.frame.render(crystal.matrixStack, VertexConsumer2, crystal.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (this.renderCore.get().booleanValue()) {
            crystal.matrixStack.scale(0.875f, 0.875f, 0.875f);
            crystal.matrixStack.multiply(new Quaternion(new Vec3f(this.SINE_45_DEGREES, 0.0f, this.SINE_45_DEGREES), this.crystalsRotationAngle.get().floatValue(), true));
            crystal.matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
            crystal.core.render(crystal.matrixStack, VertexConsumer2, crystal.light, n, (float)color2.r / 255.0f, (float)color2.g / 255.0f, (float)color2.b / 255.0f, (float)color2.a / 255.0f);
        }
        crystal.matrixStack.pop();
        BlockPos BlockPos2 = crystal.endCrystalEntity.getBeamTarget();
        if (BlockPos2 != null) {
            float f3 = (float)BlockPos2.getX() + 0.5f;
            float f4 = (float)BlockPos2.getY() + 0.5f;
            float f5 = (float)BlockPos2.getZ() + 0.5f;
            float f6 = (float)((double)f3 - crystal.endCrystalEntity.getX());
            float f7 = (float)((double)f4 - crystal.endCrystalEntity.getY());
            float f8 = (float)((double)f5 - crystal.endCrystalEntity.getZ());
            crystal.matrixStack.translate((double)f6, (double)f7, (double)f8);
            EnderDragonEntityRenderer.renderCrystalBeam((float)(-f6), (float)(-f7 + f2), (float)(-f8), (float)crystal.tickDelta, (int)crystal.endCrystalEntity.endCrystalAge, (MatrixStack)crystal.matrixStack, (VertexConsumerProvider)crystal.vertexConsumerProvider, (int)crystal.light);
        }
        crystal.matrixStack.pop();
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
        this.WHITE = new Identifier("meteor-client", "entity-texture.png");
        this.TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal.png");
    }

    protected RenderLayer getRenderLayer(LivingEntity LivingEntity2, boolean bl, boolean bl2, boolean bl3, RenderEntityEvent.LiveEntity liveEntity) {
        Identifier Identifier2;
        IEntityRenderer iEntityRenderer = (IEntityRenderer)this.mc.getEntityRenderDispatcher().getRenderer((Entity)liveEntity.entity);
        Identifier Identifier3 = Identifier2 = this.playerTexture.get() != false ? iEntityRenderer.getTextureInterface((Entity)LivingEntity2) : this.WHITE;
        if (bl2) {
            return RenderLayer.getItemEntityTranslucentCull((Identifier)Identifier2);
        }
        if (bl) {
            return liveEntity.model.getLayer(Identifier2);
        }
        return bl3 ? RenderLayer.getOutline((Identifier)Identifier2) : null;
    }

    @EventHandler
    private void onRenderLiving(RenderEntityEvent.LiveEntity liveEntity) {
        float f;
        Direction Direction2;
        float f2;
        if (!(liveEntity.entity instanceof PlayerEntity) || !this.players.get().booleanValue() || this.ignoreSelf.get().booleanValue() && liveEntity.entity == this.mc.player) {
            return;
        }
        liveEntity.setCancelled(true);
        ILivingEntityRenderer iLivingEntityRenderer = (ILivingEntityRenderer)this.mc.getEntityRenderDispatcher().getRenderer((Entity)liveEntity.entity);
        Color color = this.useNameColor.get() != false ? TextUtils.getMostPopularColor(liveEntity.entity.getDisplayName()) : (Color)this.playersColor.get();
        liveEntity.matrixStack.push();
        liveEntity.matrixStack.scale(this.playersScale.get().floatValue(), this.playersScale.get().floatValue(), this.playersScale.get().floatValue());
        liveEntity.model.handSwingProgress = liveEntity.entity.getHandSwingProgress(liveEntity.tickDelta);
        liveEntity.model.riding = liveEntity.entity.hasVehicle();
        liveEntity.model.child = liveEntity.entity.isBaby();
        float f3 = MathHelper.lerpAngleDegrees((float)liveEntity.tickDelta, (float)liveEntity.entity.prevBodyYaw, (float)liveEntity.entity.bodyYaw);
        float f4 = MathHelper.lerpAngleDegrees((float)liveEntity.tickDelta, (float)liveEntity.entity.prevHeadYaw, (float)liveEntity.entity.headYaw);
        float f5 = f4 - f3;
        if (liveEntity.entity.hasVehicle() && liveEntity.entity.getVehicle() instanceof LivingEntity) {
            LivingEntity LivingEntity2 = (LivingEntity)liveEntity.entity.getVehicle();
            f3 = MathHelper.lerpAngleDegrees((float)liveEntity.tickDelta, (float)LivingEntity2.prevBodyYaw, (float)LivingEntity2.bodyYaw);
            f5 = f4 - f3;
            f2 = MathHelper.wrapDegrees((float)f5);
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
        float f6 = MathHelper.lerp((float)liveEntity.tickDelta, (float)liveEntity.entity.prevPitch, (float)liveEntity.entity.pitch);
        if (liveEntity.entity.getPose() == EntityPose.SLEEPING && (Direction2 = liveEntity.entity.getSleepingDirection()) != null) {
            f = liveEntity.entity.getEyeHeight(EntityPose.STANDING) - 0.1f;
            liveEntity.matrixStack.translate((double)((float)(-Direction2.getOffsetX()) * f), 0.0, (double)((float)(-Direction2.getOffsetZ()) * f));
        }
        f2 = (float)liveEntity.entity.age + liveEntity.tickDelta;
        iLivingEntityRenderer.setupTransformsInterface(liveEntity.entity, liveEntity.matrixStack, f2, f3, liveEntity.tickDelta);
        liveEntity.matrixStack.scale(-1.0f, -1.0f, 1.0f);
        iLivingEntityRenderer.scaleInterface(liveEntity.entity, liveEntity.matrixStack, liveEntity.tickDelta);
        liveEntity.matrixStack.translate(0.0, (double)-1.501f, 0.0);
        f = 0.0f;
        float f7 = 0.0f;
        if (!liveEntity.entity.hasVehicle() && liveEntity.entity.isAlive()) {
            f = MathHelper.lerp((float)liveEntity.tickDelta, (float)liveEntity.entity.lastLimbDistance, (float)liveEntity.entity.limbDistance);
            f7 = liveEntity.entity.limbAngle - liveEntity.entity.limbDistance * (1.0f - liveEntity.tickDelta);
            if (liveEntity.entity.isBaby()) {
                f7 *= 3.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
        }
        liveEntity.model.animateModel((Entity)liveEntity.entity, f7, f, liveEntity.tickDelta);
        liveEntity.model.setAngles((Entity)liveEntity.entity, f7, f, f2, f5, f6);
        MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
        boolean bl = iLivingEntityRenderer.isVisibleInterface(liveEntity.entity);
        boolean bl2 = !bl && !liveEntity.entity.isInvisibleTo((PlayerEntity)MinecraftClient2.player);
        boolean bl3 = MinecraftClient2.hasOutline((Entity)liveEntity.entity);
        RenderLayer RenderLayer2 = this.getRenderLayer(liveEntity.entity, bl, bl2, bl3, liveEntity);
        if (RenderLayer2 != null) {
            VertexConsumer VertexConsumer2 = liveEntity.vertexConsumerProvider.getBuffer(RenderLayer2);
            int n = LivingEntityRenderer.getOverlay((LivingEntity)liveEntity.entity, (float)iLivingEntityRenderer.getAnimationCounterInterface(liveEntity.entity, liveEntity.tickDelta));
            liveEntity.model.render(liveEntity.matrixStack, VertexConsumer2, liveEntity.light, n, (float)color.r / 255.0f, (float)color.g / 255.0f, (float)color.b / 255.0f, (float)color.a / 255.0f);
        }
        if (!liveEntity.entity.isSpectator()) {
            for (FeatureRenderer FeatureRenderer2 : liveEntity.features) {
                FeatureRenderer2.render(liveEntity.matrixStack, liveEntity.vertexConsumerProvider, liveEntity.light, (Entity)liveEntity.entity, f7, f, liveEntity.tickDelta, f2, f5, f6);
            }
        }
        liveEntity.matrixStack.pop();
    }
}

