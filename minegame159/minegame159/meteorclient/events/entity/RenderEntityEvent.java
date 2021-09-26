/*
 * Decompiled with CFR 0.151.
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
    public Entity entity;

    public static class LiveEntity
    extends RenderEntityEvent {
        private static final LiveEntity INSTANCE = new LiveEntity();
        public MatrixStack matrixStack;
        public float yaw;
        public int light;
        public VertexConsumerProvider vertexConsumerProvider;
        public List<FeatureRenderer<LivingEntity, EntityModel<LivingEntity>>> features;
        public float tickDelta;
        public EntityModel<Entity> model;
        public LivingEntity entity;

        public static LiveEntity get(EntityModel<Entity> EntityModel2, LivingEntity LivingEntity2, List<FeatureRenderer<LivingEntity, EntityModel<LivingEntity>>> list, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n) {
            LiveEntity.INSTANCE.model = EntityModel2;
            LiveEntity.INSTANCE.entity = LivingEntity2;
            LiveEntity.INSTANCE.features = list;
            LiveEntity.INSTANCE.yaw = f;
            LiveEntity.INSTANCE.tickDelta = f2;
            LiveEntity.INSTANCE.matrixStack = MatrixStack2;
            LiveEntity.INSTANCE.vertexConsumerProvider = VertexConsumerProvider2;
            LiveEntity.INSTANCE.light = n;
            return INSTANCE;
        }
    }

    public static class Pre
    extends RenderEntityEvent {
        private static final Pre INSTANCE = new Pre();

        public static Pre get(Entity Entity2) {
            Pre.INSTANCE.entity = Entity2;
            return INSTANCE;
        }
    }

    public static class Post
    extends RenderEntityEvent {
        private static final Post INSTANCE = new Post();

        public static Post get(Entity Entity2) {
            Post.INSTANCE.entity = Entity2;
            return INSTANCE;
        }
    }

    public static class Crystal
    extends RenderEntityEvent {
        private static final Crystal INSTANCE = new Crystal();
        public float tickDelta;
        public MatrixStack matrixStack;
        public ModelPart core;
        public ModelPart bottom;
        public VertexConsumerProvider vertexConsumerProvider;
        public int light;
        public ModelPart frame;
        public float yaw;
        public EndCrystalEntity endCrystalEntity;

        public static Crystal get(EndCrystalEntity EndCrystalEntity2, float f, float f2, MatrixStack MatrixStack2, VertexConsumerProvider VertexConsumerProvider2, int n, ModelPart ModelPart2, ModelPart ModelPart3, ModelPart ModelPart4) {
            Crystal.INSTANCE.endCrystalEntity = EndCrystalEntity2;
            Crystal.INSTANCE.yaw = f;
            Crystal.INSTANCE.tickDelta = f2;
            Crystal.INSTANCE.matrixStack = MatrixStack2;
            Crystal.INSTANCE.vertexConsumerProvider = VertexConsumerProvider2;
            Crystal.INSTANCE.light = n;
            Crystal.INSTANCE.core = ModelPart2;
            Crystal.INSTANCE.frame = ModelPart3;
            Crystal.INSTANCE.bottom = ModelPart4;
            return INSTANCE;
        }
    }
}

