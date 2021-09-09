/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.math.MatrixStack
 *  org.lwjgl.opengl.GL11
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
    private static /* synthetic */ double windowScale;
    private static final /* synthetic */ MinecraftClient mc;
    private static /* synthetic */ Matrix4f projection;
    private static /* synthetic */ double scale;
    private static final /* synthetic */ Vec4 vec4;
    private static final /* synthetic */ Vec3 camera;
    private static final /* synthetic */ Vec3 cameraNegated;
    private static final /* synthetic */ Vec4 pmMat4;
    private static /* synthetic */ Matrix4f model;
    private static final /* synthetic */ Vec4 mmMat4;

    public NametagUtils() {
        NametagUtils lllllllllllllllllIIllllIllIllIII;
    }

    public static void begin(Vec3 lllllllllllllllllIIllllIllIIIIll) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)lllllllllllllllllIIllllIllIIIIll.x, (double)lllllllllllllllllIIllllIllIIIIll.y, (double)0.0);
        GL11.glScaled((double)scale, (double)scale, (double)1.0);
    }

    public static void onRender(MatrixStack lllllllllllllllllIIllllIllIlIlIl, Matrix4f lllllllllllllllllIIllllIllIlIIlI) {
        model = lllllllllllllllllIIllllIllIlIlIl.peek().getModel().copy();
        projection = lllllllllllllllllIIllllIllIlIIlI;
        camera.set(NametagUtils.mc.gameRenderer.getCamera().getPos());
        cameraNegated.set(camera);
        cameraNegated.negate();
        windowScale = mc.getWindow().calculateScaleFactor(1, mc.forcesUnicodeFont());
    }

    public static void end() {
        GL11.glPopMatrix();
    }

    private static double getScale(Vec3 lllllllllllllllllIIllllIlIlllllI) {
        double lllllllllllllllllIIllllIlIllllll = camera.distanceTo(lllllllllllllllllIIllllIlIlllllI);
        return Utils.clamp(1.0 - lllllllllllllllllIIllllIlIllllll * 0.01, 0.5, 2.147483647E9);
    }

    static {
        mc = MinecraftClient.getInstance();
        vec4 = new Vec4();
        mmMat4 = new Vec4();
        pmMat4 = new Vec4();
        camera = new Vec3();
        cameraNegated = new Vec3();
    }

    public static boolean to2D(Vec3 lllllllllllllllllIIllllIllIIllIl, double lllllllllllllllllIIllllIllIIlIII) {
        scale = NametagUtils.getScale(lllllllllllllllllIIllllIllIIllIl) * lllllllllllllllllIIllllIllIIlIII;
        vec4.set(NametagUtils.cameraNegated.x + lllllllllllllllllIIllllIllIIllIl.x, NametagUtils.cameraNegated.y + lllllllllllllllllIIllllIllIIllIl.y, NametagUtils.cameraNegated.z + lllllllllllllllllIIllllIllIIllIl.z, 1.0);
        ((IMatrix4f)model).multiplyMatrix(vec4, mmMat4);
        ((IMatrix4f)projection).multiplyMatrix(mmMat4, pmMat4);
        if (NametagUtils.pmMat4.w <= 0.0) {
            return false;
        }
        pmMat4.toScreen();
        double lllllllllllllllllIIllllIllIIlIll = NametagUtils.pmMat4.x * (double)mc.getWindow().getFramebufferWidth();
        double lllllllllllllllllIIllllIllIIlIlI = NametagUtils.pmMat4.y * (double)mc.getWindow().getFramebufferHeight();
        if (Double.isInfinite(lllllllllllllllllIIllllIllIIlIll) || Double.isInfinite(lllllllllllllllllIIllllIllIIlIlI)) {
            return false;
        }
        lllllllllllllllllIIllllIllIIllIl.set(lllllllllllllllllIIllllIllIIlIll / windowScale, (double)mc.getWindow().getFramebufferHeight() - lllllllllllllllllIIllllIllIIlIlI / windowScale, NametagUtils.pmMat4.z);
        return true;
    }
}

