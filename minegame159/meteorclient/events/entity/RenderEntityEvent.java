/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.client.render.entity.feature.FeatureRenderer
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.model.EntityModel
 *  net.minecraft.client.model.ModelPart
 */
package minegame159.meteorclient.events.entity;

import java.util.List;
import minegame159.meteorclient.events.Cancellable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.ModelPart;

public class RenderEntityEvent
extends Cancellable {
    public /* synthetic */ Entity entity;

    public RenderEntityEvent() {
        RenderEntityEvent lllllllllllllllllllIIIIIIllIlIII;
    }

    public static class Post
    extends RenderEntityEvent {
        private static final /* synthetic */ Post INSTANCE;

        public static Post get(Entity llllIllIIIIIIII) {
            Post.INSTANCE.entity = llllIllIIIIIIII;
            return INSTANCE;
        }

        static {
            INSTANCE = new Post();
        }

        public Post() {
            Post llllIllIIIIIIll;
        }
    }

    public static class LiveEntity
    extends RenderEntityEvent {
        public /* synthetic */ LivingEntity entity;
        public /* synthetic */ VertexConsumerProvider vertexConsumerProvider;
        private static final /* synthetic */ LiveEntity INSTANCE;
        public /* synthetic */ EntityModel<Entity> model;
        public /* synthetic */ float tickDelta;
        public /* synthetic */ List<FeatureRenderer<LivingEntity, EntityModel<LivingEntity>>> features;
        public /* synthetic */ int light;
        public /* synthetic */ MatrixStack matrixStack;
        public /* synthetic */ float yaw;

        public static LiveEntity get(EntityModel<Entity> lllllllllllllllllIIIlIllIIIlIIll, LivingEntity lllllllllllllllllIIIlIllIIIIlIlI, List<FeatureRenderer<LivingEntity, EntityModel<LivingEntity>>> lllllllllllllllllIIIlIllIIIlIIIl, float lllllllllllllllllIIIlIllIIIlIIII, float lllllllllllllllllIIIlIllIIIIllll, MatrixStack lllllllllllllllllIIIlIllIIIIIllI, VertexConsumerProvider lllllllllllllllllIIIlIllIIIIllIl, int lllllllllllllllllIIIlIllIIIIIlII) {
            LiveEntity.INSTANCE.model = lllllllllllllllllIIIlIllIIIlIIll;
            LiveEntity.INSTANCE.entity = lllllllllllllllllIIIlIllIIIIlIlI;
            LiveEntity.INSTANCE.features = lllllllllllllllllIIIlIllIIIlIIIl;
            LiveEntity.INSTANCE.yaw = lllllllllllllllllIIIlIllIIIlIIII;
            LiveEntity.INSTANCE.tickDelta = lllllllllllllllllIIIlIllIIIIllll;
            LiveEntity.INSTANCE.matrixStack = lllllllllllllllllIIIlIllIIIIIllI;
            LiveEntity.INSTANCE.vertexConsumerProvider = lllllllllllllllllIIIlIllIIIIllIl;
            LiveEntity.INSTANCE.light = lllllllllllllllllIIIlIllIIIIIlII;
            return INSTANCE;
        }

        static {
            INSTANCE = new LiveEntity();
        }

        public LiveEntity() {
            LiveEntity lllllllllllllllllIIIlIllIIIlllIl;
        }
    }

    public static class Pre
    extends RenderEntityEvent {
        private static final /* synthetic */ Pre INSTANCE;

        public Pre() {
            Pre lllllllllllllllllIIllllIllIlllll;
        }

        static {
            INSTANCE = new Pre();
        }

        public static Pre get(Entity lllllllllllllllllIIllllIllIllIll) {
            Pre.INSTANCE.entity = lllllllllllllllllIIllllIllIllIll;
            return INSTANCE;
        }
    }

    public static class Crystal
    extends RenderEntityEvent {
        private static final /* synthetic */ Crystal INSTANCE;
        public /* synthetic */ float yaw;
        public /* synthetic */ MatrixStack matrixStack;
        public /* synthetic */ float tickDelta;
        public /* synthetic */ ModelPart core;
        public /* synthetic */ ModelPart bottom;
        public /* synthetic */ VertexConsumerProvider vertexConsumerProvider;
        public /* synthetic */ int light;
        public /* synthetic */ ModelPart frame;
        public /* synthetic */ EndCrystalEntity endCrystalEntity;

        static {
            INSTANCE = new Crystal();
        }

        public static Crystal get(EndCrystalEntity lllllllllllllllllIlllIlIlIIIlIll, float lllllllllllllllllIlllIlIlIIIlIlI, float lllllllllllllllllIlllIlIlIIlIIlI, MatrixStack lllllllllllllllllIlllIlIlIIIlIII, VertexConsumerProvider lllllllllllllllllIlllIlIlIIIIlll, int lllllllllllllllllIlllIlIlIIIllll, ModelPart lllllllllllllllllIlllIlIlIIIIlIl, ModelPart lllllllllllllllllIlllIlIlIIIIlII, ModelPart lllllllllllllllllIlllIlIlIIIIIll) {
            Crystal.INSTANCE.endCrystalEntity = lllllllllllllllllIlllIlIlIIIlIll;
            Crystal.INSTANCE.yaw = lllllllllllllllllIlllIlIlIIIlIlI;
            Crystal.INSTANCE.tickDelta = lllllllllllllllllIlllIlIlIIlIIlI;
            Crystal.INSTANCE.matrixStack = lllllllllllllllllIlllIlIlIIIlIII;
            Crystal.INSTANCE.vertexConsumerProvider = lllllllllllllllllIlllIlIlIIIIlll;
            Crystal.INSTANCE.light = lllllllllllllllllIlllIlIlIIIllll;
            Crystal.INSTANCE.core = lllllllllllllllllIlllIlIlIIIIlIl;
            Crystal.INSTANCE.frame = lllllllllllllllllIlllIlIlIIIIlII;
            Crystal.INSTANCE.bottom = lllllllllllllllllIlllIlIlIIIIIll;
            return INSTANCE;
        }

        public Crystal() {
            Crystal lllllllllllllllllIlllIlIlIIlllll;
        }
    }
}

