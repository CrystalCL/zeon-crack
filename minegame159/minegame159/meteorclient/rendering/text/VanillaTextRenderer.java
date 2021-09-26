/*
 * Decompiled with CFR 0.151.
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
    private double scale;
    private double alpha;
    private final TextRenderer mr;
    public static final TextRenderer INSTANCE = new VanillaTextRenderer();
    private boolean building;

    @Override
    public void setAlpha(double d) {
        this.alpha = d;
    }

    @Override
    public void begin(double d, boolean bl, boolean bl2) {
        this.scale = d * 1.74;
        this.building = true;
    }

    private VanillaTextRenderer() {
        this.mr = MinecraftClient.getInstance().textRenderer;
        this.scale = 1.74;
        this.alpha = 1.0;
    }

    @Override
    public double getHeight() {
        Objects.requireNonNull(this.mr);
        return 9.0 * this.scale;
    }

    @Override
    public double render(String string, double d, double d2, Color color, boolean bl) {
        Matrices.push();
        Matrices.scale(this.scale, this.scale, 1.0);
        int n = color.a;
        color.a = (int)((double)(color.a / 255) * this.alpha * 255.0);
        RenderSystem.disableDepthTest();
        VertexConsumerProvider.class_4598 class_45982 = VertexConsumerProvider.immediate((BufferBuilder)Tessellator.getInstance().getBuffer());
        double d3 = this.mr.draw(string, (float)((d += 0.5 * this.scale) / this.scale), (float)((d2 += 0.5 * this.scale) / this.scale), color.getPacked(), bl, Matrices.getTop(), (VertexConsumerProvider)class_45982, true, 0, 0xF000F0);
        class_45982.draw();
        RenderSystem.enableDepthTest();
        color.a = n;
        Matrices.pop();
        return d3 * this.scale;
    }

    @Override
    public double getWidth(String string, int n) {
        String string2 = string;
        if (n != string2.length()) {
            string2 = string2.substring(0, n);
        }
        return (double)this.mr.getWidth(string2) * this.scale;
    }

    @Override
    public void end() {
        this.scale = 1.74;
        this.building = false;
    }

    @Override
    public boolean isBuilding() {
        return this.building;
    }
}

