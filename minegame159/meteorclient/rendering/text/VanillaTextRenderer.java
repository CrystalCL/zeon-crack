/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.VertexConsumerProvider$class_4598
 */
package minegame159.meteorclient.rendering.text;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;

public class VanillaTextRenderer
implements TextRenderer {
    public static final /* synthetic */ TextRenderer INSTANCE;
    private /* synthetic */ double scale;
    private final /* synthetic */ TextRenderer mr;
    private /* synthetic */ double alpha;
    private /* synthetic */ boolean building;

    @Override
    public double getWidth(String lllllllllllllllllIlIIIIIIIIllIlI, int lllllllllllllllllIlIIIIIIIIllIIl) {
        VanillaTextRenderer lllllllllllllllllIlIIIIIIIIllIll;
        String lllllllllllllllllIlIIIIIIIIllIII = lllllllllllllllllIlIIIIIIIIllIlI;
        if (lllllllllllllllllIlIIIIIIIIllIIl != lllllllllllllllllIlIIIIIIIIllIII.length()) {
            lllllllllllllllllIlIIIIIIIIllIII = lllllllllllllllllIlIIIIIIIIllIII.substring(0, lllllllllllllllllIlIIIIIIIIllIIl);
        }
        return (double)lllllllllllllllllIlIIIIIIIIllIll.mr.getWidth(lllllllllllllllllIlIIIIIIIIllIII) * lllllllllllllllllIlIIIIIIIIllIll.scale;
    }

    @Override
    public boolean isBuilding() {
        VanillaTextRenderer lllllllllllllllllIIlllllllIllIII;
        return lllllllllllllllllIIlllllllIllIII.building;
    }

    static {
        INSTANCE = new VanillaTextRenderer();
    }

    @Override
    public double getHeight() {
        VanillaTextRenderer lllllllllllllllllIlIIIIIIIIlIIlI;
        Objects.requireNonNull(lllllllllllllllllIlIIIIIIIIlIIlI.mr);
        return 9.0 * lllllllllllllllllIlIIIIIIIIlIIlI.scale;
    }

    @Override
    public double render(String lllllllllllllllllIIllllllllIlIII, double lllllllllllllllllIIlllllllllIlIl, double lllllllllllllllllIIllllllllIIllI, Color lllllllllllllllllIIlllllllllIIIl, boolean lllllllllllllllllIIllllllllIllll) {
        VanillaTextRenderer lllllllllllllllllIIllllllllIlIlI;
        Matrices.push();
        Matrices.scale(lllllllllllllllllIIllllllllIlIlI.scale, lllllllllllllllllIIllllllllIlIlI.scale, 1.0);
        int lllllllllllllllllIIllllllllIlllI = lllllllllllllllllIIlllllllllIIIl.a;
        lllllllllllllllllIIlllllllllIIIl.a = (int)((double)(lllllllllllllllllIIlllllllllIIIl.a / 255) * lllllllllllllllllIIllllllllIlIlI.alpha * 255.0);
        RenderSystem.disableDepthTest();
        VertexConsumerProvider.class_4598 lllllllllllllllllIIllllllllIllIl = VertexConsumerProvider.immediate((BufferBuilder)Tessellator.getInstance().getBuffer());
        double lllllllllllllllllIIllllllllIlIll = lllllllllllllllllIIllllllllIlIlI.mr.draw(lllllllllllllllllIIllllllllIlIII, (float)((lllllllllllllllllIIlllllllllIlIl += 0.5 * lllllllllllllllllIIllllllllIlIlI.scale) / lllllllllllllllllIIllllllllIlIlI.scale), (float)((lllllllllllllllllIIllllllllIIllI += 0.5 * lllllllllllllllllIIllllllllIlIlI.scale) / lllllllllllllllllIIllllllllIlIlI.scale), lllllllllllllllllIIlllllllllIIIl.getPacked(), lllllllllllllllllIIllllllllIllll, Matrices.getTop(), (VertexConsumerProvider)lllllllllllllllllIIllllllllIllIl, true, 0, 0xF000F0);
        lllllllllllllllllIIllllllllIllIl.draw();
        RenderSystem.enableDepthTest();
        lllllllllllllllllIIlllllllllIIIl.a = lllllllllllllllllIIllllllllIlllI;
        Matrices.pop();
        return lllllllllllllllllIIllllllllIlIll * lllllllllllllllllIIllllllllIlIlI.scale;
    }

    private VanillaTextRenderer() {
        VanillaTextRenderer lllllllllllllllllIlIIIIIIIlIIllI;
        lllllllllllllllllIlIIIIIIIlIIllI.mr = MinecraftClient.getInstance().textRenderer;
        lllllllllllllllllIlIIIIIIIlIIllI.scale = 1.74;
        lllllllllllllllllIlIIIIIIIlIIllI.alpha = 1.0;
    }

    @Override
    public void begin(double lllllllllllllllllIlIIIIIIIIIllIl, boolean lllllllllllllllllIlIIIIIIIIIllII, boolean lllllllllllllllllIlIIIIIIIIIlIll) {
        lllllllllllllllllIlIIIIIIIIIlllI.scale = lllllllllllllllllIlIIIIIIIIIllIl * 1.74;
        lllllllllllllllllIlIIIIIIIIIlllI.building = true;
    }

    @Override
    public void setAlpha(double lllllllllllllllllIlIIIIIIIlIIIII) {
        lllllllllllllllllIlIIIIIIIlIIIIl.alpha = lllllllllllllllllIlIIIIIIIlIIIII;
    }

    @Override
    public void end() {
        lllllllllllllllllIIlllllllIlIllI.scale = 1.74;
        lllllllllllllllllIIlllllllIlIllI.building = false;
    }
}

