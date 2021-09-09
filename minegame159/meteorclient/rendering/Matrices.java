/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Quaternion
 *  net.minecraft.util.math.Matrix4f
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.rendering;

import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

public class Matrices {
    private static /* synthetic */ MatrixStack matrixStack;

    public static MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public static void translate(double lllllllllllllllllIIIIllIlIIIIlII, double lllllllllllllllllIIIIllIlIIIIIll, double lllllllllllllllllIIIIllIlIIIIlIl) {
        matrixStack.translate(lllllllllllllllllIIIIllIlIIIIlII, lllllllllllllllllIIIIllIlIIIIIll, lllllllllllllllllIIIIllIlIIIIlIl);
    }

    public static Matrix4f getTop() {
        return matrixStack.peek().getModel();
    }

    public static void pop() {
        matrixStack.pop();
    }

    public static void scale(double lllllllllllllllllIIIIllIIllIllll, double lllllllllllllllllIIIIllIIllIlllI, double lllllllllllllllllIIIIllIIlllIIII) {
        matrixStack.scale((float)lllllllllllllllllIIIIllIIllIllll, (float)lllllllllllllllllIIIIllIIllIlllI, (float)lllllllllllllllllIIIIllIIlllIIII);
    }

    public Matrices() {
        Matrices lllllllllllllllllIIIIllIlIIIllll;
    }

    public static void begin(MatrixStack lllllllllllllllllIIIIllIlIIIlIll) {
        matrixStack = lllllllllllllllllIIIIllIlIIIlIll;
    }

    public static void push() {
        matrixStack.push();
    }

    public static void rotate(double lllllllllllllllllIIIIllIIlllllIl, double lllllllllllllllllIIIIllIIllllIII, double lllllllllllllllllIIIIllIIlllIlll, double lllllllllllllllllIIIIllIIllllIlI) {
        matrixStack.multiply(new Quaternion((float)(lllllllllllllllllIIIIllIIllllIII * lllllllllllllllllIIIIllIIlllllIl), (float)(lllllllllllllllllIIIIllIIlllIlll * lllllllllllllllllIIIIllIIlllllIl), (float)(lllllllllllllllllIIIIllIIllllIlI * lllllllllllllllllIIIIllIIlllllIl), true));
    }
}

