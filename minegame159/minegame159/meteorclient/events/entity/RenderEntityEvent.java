/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.entity;

import java.util.List;
import minegame159.meteorclient.events.Cancellable;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_583;
import net.minecraft.class_630;

public class RenderEntityEvent
extends Cancellable {
    public class_1297 entity;

    public static class LiveEntity
    extends RenderEntityEvent {
        private static final LiveEntity INSTANCE = new LiveEntity();
        public class_4587 matrixStack;
        public float yaw;
        public int light;
        public class_4597 vertexConsumerProvider;
        public List<class_3887<class_1309, class_583<class_1309>>> features;
        public float tickDelta;
        public class_583<class_1297> model;
        public class_1309 entity;

        public static LiveEntity get(class_583<class_1297> class_5832, class_1309 class_13092, List<class_3887<class_1309, class_583<class_1309>>> list, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n) {
            LiveEntity.INSTANCE.model = class_5832;
            LiveEntity.INSTANCE.entity = class_13092;
            LiveEntity.INSTANCE.features = list;
            LiveEntity.INSTANCE.yaw = f;
            LiveEntity.INSTANCE.tickDelta = f2;
            LiveEntity.INSTANCE.matrixStack = class_45872;
            LiveEntity.INSTANCE.vertexConsumerProvider = class_45972;
            LiveEntity.INSTANCE.light = n;
            return INSTANCE;
        }
    }

    public static class Pre
    extends RenderEntityEvent {
        private static final Pre INSTANCE = new Pre();

        public static Pre get(class_1297 class_12972) {
            Pre.INSTANCE.entity = class_12972;
            return INSTANCE;
        }
    }

    public static class Post
    extends RenderEntityEvent {
        private static final Post INSTANCE = new Post();

        public static Post get(class_1297 class_12972) {
            Post.INSTANCE.entity = class_12972;
            return INSTANCE;
        }
    }

    public static class Crystal
    extends RenderEntityEvent {
        private static final Crystal INSTANCE = new Crystal();
        public float tickDelta;
        public class_4587 matrixStack;
        public class_630 core;
        public class_630 bottom;
        public class_4597 vertexConsumerProvider;
        public int light;
        public class_630 frame;
        public float yaw;
        public class_1511 endCrystalEntity;

        public static Crystal get(class_1511 class_15112, float f, float f2, class_4587 class_45872, class_4597 class_45972, int n, class_630 class_6302, class_630 class_6303, class_630 class_6304) {
            Crystal.INSTANCE.endCrystalEntity = class_15112;
            Crystal.INSTANCE.yaw = f;
            Crystal.INSTANCE.tickDelta = f2;
            Crystal.INSTANCE.matrixStack = class_45872;
            Crystal.INSTANCE.vertexConsumerProvider = class_45972;
            Crystal.INSTANCE.light = n;
            Crystal.INSTANCE.core = class_6302;
            Crystal.INSTANCE.frame = class_6303;
            Crystal.INSTANCE.bottom = class_6304;
            return INSTANCE;
        }
    }
}

