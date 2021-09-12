/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import java.io.IOException;
import minegame159.meteorclient.mixin.WorldRendererAccessor;
import net.minecraft.class_276;
import net.minecraft.class_279;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4618;
import net.minecraft.class_761;

public class Outlines {
    public static class_4618 vertexConsumerProvider;
    public static boolean loadingOutlineShader;
    public static class_276 outlinesFbo;
    public static boolean renderingOutlines;
    private static class_279 outlinesShader;

    public static void beginRender() {
        outlinesFbo.method_1230(class_310.field_1703);
        class_310.method_1551().method_1522().method_1235(false);
    }

    public static void onResized(int n, int n2) {
        if (outlinesShader != null) {
            outlinesShader.method_1259(n, n2);
        }
    }

    public static void endRender(float f) {
        class_761 class_7612 = class_310.method_1551().field_1769;
        WorldRendererAccessor worldRendererAccessor = (WorldRendererAccessor)class_7612;
        class_276 class_2763 = class_7612.method_22990();
        worldRendererAccessor.setEntityOutlinesFramebuffer(outlinesFbo);
        vertexConsumerProvider.method_23285();
        worldRendererAccessor.setEntityOutlinesFramebuffer(class_2763);
        outlinesShader.method_1258(f);
        class_310.method_1551().method_1522().method_1235(false);
    }

    public static void renderFbo() {
        class_310 class_3102 = class_310.method_1551();
        outlinesFbo.method_22594(class_3102.method_22683().method_4489(), class_3102.method_22683().method_4506(), false);
    }

    public static void load() {
        try {
            class_310 class_3102 = class_310.method_1551();
            if (outlinesShader != null) {
                outlinesShader.close();
            }
            loadingOutlineShader = true;
            outlinesShader = new class_279(class_3102.method_1531(), class_3102.method_1478(), class_3102.method_1522(), new class_2960("meteor-client", "shaders/post/my_entity_outline.json"));
            outlinesShader.method_1259(class_3102.method_22683().method_4489(), class_3102.method_22683().method_4506());
            outlinesFbo = outlinesShader.method_1264("final");
            vertexConsumerProvider = new class_4618(class_3102.method_22940().method_23000());
            loadingOutlineShader = false;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

