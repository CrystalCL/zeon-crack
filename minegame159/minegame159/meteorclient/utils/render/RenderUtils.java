/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.FreeRotate;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.MinecraftClient;

public class RenderUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawTracerToEntity(RenderEvent renderEvent, Entity Entity2, Color color, Target target, boolean bl) {
        double d = Entity2.prevX + (Entity2.getX() - Entity2.prevX) * (double)renderEvent.tickDelta;
        double d2 = Entity2.prevY + (Entity2.getY() - Entity2.prevY) * (double)renderEvent.tickDelta;
        double d3 = Entity2.prevZ + (Entity2.getZ() - Entity2.prevZ) * (double)renderEvent.tickDelta;
        double d4 = Entity2.getBoundingBox().maxY - Entity2.getBoundingBox().minY;
        if (target == Target.Head) {
            d2 += d4;
        } else if (target == Target.Body) {
            d2 += d4 / 2.0;
        }
        RenderUtils.drawLine(RenderUtils.getCameraVector(), d, d2, d3, color, renderEvent);
        if (bl) {
            Renderer.LINES.line(d, Entity2.getY(), d3, d, Entity2.getY() + d4, d3, color);
        }
    }

    public static void drawTracerToPos(BlockPos BlockPos2, Color color, RenderEvent renderEvent) {
        RenderUtils.drawLine(RenderUtils.getCameraVector(), (double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (float)BlockPos2.getZ() + 0.5f, color, renderEvent);
    }

    public static void drawLine(Vec3d Vec3d2, double d, double d2, double d3, Color color, RenderEvent renderEvent) {
        Renderer.LINES.line(Vec3d2.x - (RenderUtils.mc.gameRenderer.getCamera().getPos().x - renderEvent.offsetX), Vec3d2.y - (RenderUtils.mc.gameRenderer.getCamera().getPos().y - renderEvent.offsetY), Vec3d2.z - (RenderUtils.mc.gameRenderer.getCamera().getPos().z - renderEvent.offsetZ), d, d2, d3, color);
    }

    public static Vec3d getCameraVector() {
        boolean bl = Modules.get().isActive(Freecam.class) || Modules.get().get(FreeRotate.class).playerMode();
        return new Vec3d(0.0, 0.0, bl ? 1.0 : 75.0).rotateX(-((float)Math.toRadians(RenderUtils.mc.gameRenderer.getCamera().getPitch()))).rotateY(-((float)Math.toRadians(RenderUtils.mc.gameRenderer.getCamera().getYaw()))).add(RenderUtils.mc.gameRenderer.getCamera().getPos());
    }

    public static void drawItem(ItemStack ItemStack2, int n, int n2, double d, boolean bl) {
        RenderSystem.pushMatrix();
        RenderSystem.scaled((double)d, (double)d, (double)1.0);
        RenderUtils.drawItem(ItemStack2, n, n2, bl);
        RenderSystem.popMatrix();
    }

    public static void drawItem(ItemStack ItemStack2, int n, int n2, boolean bl) {
        RenderSystem.disableLighting();
        RenderSystem.disableDepthTest();
        DiffuseLighting.enable();
        mc.getItemRenderer().renderGuiItemIcon(ItemStack2, n, n2);
        if (bl) {
            mc.getItemRenderer().renderGuiItemOverlay(RenderUtils.mc.textRenderer, ItemStack2, n, n2, null);
        }
        DiffuseLighting.disable();
        DiffuseLighting.disable();
        RenderSystem.enableDepthTest();
    }

    public static void drawTracerToBlockEntity(BlockEntity BlockEntity2, Color color, RenderEvent renderEvent) {
        RenderUtils.drawTracerToPos(BlockEntity2.getPos(), color, renderEvent);
    }
}

