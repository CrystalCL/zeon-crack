/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.rendering;

import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

public class Matrices {
    private static MatrixStack matrixStack;

    public static void scale(double d, double d2, double d3) {
        matrixStack.scale((float)d, (float)d2, (float)d3);
    }

    public static void push() {
        matrixStack.push();
    }

    public static void translate(double d, double d2, double d3) {
        matrixStack.translate(d, d2, d3);
    }

    public static void rotate(double d, double d2, double d3, double d4) {
        matrixStack.multiply(new Quaternion((float)(d2 * d), (float)(d3 * d), (float)(d4 * d), true));
    }

    public static void pop() {
        matrixStack.pop();
    }

    public static void begin(MatrixStack MatrixStack2) {
        matrixStack = MatrixStack2;
    }

    public static MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public static Matrix4f getTop() {
        return matrixStack.peek().getModel();
    }
}

