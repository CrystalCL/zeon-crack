/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.mixininterface;

import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.math.MatrixStack;

public interface ILivingEntityRenderer {
    public void scaleInterface(LivingEntity var1, MatrixStack var2, float var3);

    public boolean isVisibleInterface(LivingEntity var1);

    public float getAnimationCounterInterface(LivingEntity var1, float var2);

    public void setupTransformsInterface(LivingEntity var1, MatrixStack var2, float var3, float var4, float var5);
}

