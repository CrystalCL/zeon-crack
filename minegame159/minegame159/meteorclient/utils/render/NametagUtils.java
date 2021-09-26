/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import minegame159.meteorclient.mixininterface.IMatrix4f;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.misc.Vec4;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class NametagUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Vec3 cameraNegated;
    private static double windowScale;
    private static Matrix4f model;
    private static final Vec4 mmMat4;
    private static final Vec3 camera;
    private static final Vec4 vec4;
    private static double scale;
    private static final Vec4 pmMat4;
    private static Matrix4f projection;

    private static double getScale(Vec3 vec3) {
        double d = camera.distanceTo(vec3);
        return Utils.clamp(1.0 - d * 0.01, 0.5, 2.147483647E9);
    }

    public static void end() {
        GL11.glPopMatrix();
    }

    public static void onRender(MatrixStack MatrixStack2, Matrix4f Matrix4f2) {
        model = MatrixStack2.peek().getModel().copy();
        projection = Matrix4f2;
        camera.set(NametagUtils.mc.gameRenderer.getCamera().getPos());
        cameraNegated.set(camera);
        cameraNegated.negate();
        windowScale = mc.getWindow().calculateScaleFactor(1, mc.forcesUnicodeFont());
    }

    static {
        vec4 = new Vec4();
        mmMat4 = new Vec4();
        pmMat4 = new Vec4();
        camera = new Vec3();
        cameraNegated = new Vec3();
    }

    public static void begin(Vec3 vec3) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)vec3.x, (double)vec3.y, (double)0.0);
        GL11.glScaled((double)scale, (double)scale, (double)1.0);
    }

    public static boolean to2D(Vec3 vec3, double d) {
        scale = NametagUtils.getScale(vec3) * d;
        vec4.set(NametagUtils.cameraNegated.x + vec3.x, NametagUtils.cameraNegated.y + vec3.y, NametagUtils.cameraNegated.z + vec3.z, 1.0);
        ((IMatrix4f)model).multiplyMatrix(vec4, mmMat4);
        ((IMatrix4f)projection).multiplyMatrix(mmMat4, pmMat4);
        if (NametagUtils.pmMat4.w <= 0.0) {
            return false;
        }
        pmMat4.toScreen();
        double d2 = NametagUtils.pmMat4.x * (double)mc.getWindow().getFramebufferWidth();
        double d3 = NametagUtils.pmMat4.y * (double)mc.getWindow().getFramebufferHeight();
        if (Double.isInfinite(d2) || Double.isInfinite(d3)) {
            return false;
        }
        vec3.set(d2 / windowScale, (double)mc.getWindow().getFramebufferHeight() - d3 / windowScale, NametagUtils.pmMat4.z);
        return true;
    }
}

