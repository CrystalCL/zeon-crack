/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.client.render.DiffuseLighting
 *  net.minecraft.client.MinecraftClient
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
    private static final /* synthetic */ MinecraftClient mc;

    public static void drawTracerToPos(BlockPos lIlIIlIIIlllllI, Color lIlIIlIIIllllIl, RenderEvent lIlIIlIIIllllII) {
        RenderUtils.drawLine(RenderUtils.getCameraVector(), (double)lIlIIlIIIlllllI.getX() + 0.5, (double)lIlIIlIIIlllllI.getY() + 0.5, (float)lIlIIlIIIlllllI.getZ() + 0.5f, lIlIIlIIIllllIl, lIlIIlIIIllllII);
    }

    public static void drawItem(ItemStack lIlIIlIIllIIlll, int lIlIIlIIllIIllI, int lIlIIlIIllIIlIl, double lIlIIlIIllIlIIl, boolean lIlIIlIIllIIIll) {
        RenderSystem.pushMatrix();
        RenderSystem.scaled((double)lIlIIlIIllIlIIl, (double)lIlIIlIIllIlIIl, (double)1.0);
        RenderUtils.drawItem(lIlIIlIIllIIlll, lIlIIlIIllIIllI, lIlIIlIIllIIlIl, lIlIIlIIllIIIll);
        RenderSystem.popMatrix();
    }

    public static void drawLine(Vec3d lIlIIlIIIlIllll, double lIlIIlIIIlIlllI, double lIlIIlIIIllIIll, double lIlIIlIIIllIIlI, Color lIlIIlIIIlIlIll, RenderEvent lIlIIlIIIllIIII) {
        Renderer.LINES.line(lIlIIlIIIlIllll.x - (RenderUtils.mc.gameRenderer.getCamera().getPos().x - lIlIIlIIIllIIII.offsetX), lIlIIlIIIlIllll.y - (RenderUtils.mc.gameRenderer.getCamera().getPos().y - lIlIIlIIIllIIII.offsetY), lIlIIlIIIlIllll.z - (RenderUtils.mc.gameRenderer.getCamera().getPos().z - lIlIIlIIIllIIII.offsetZ), lIlIIlIIIlIlllI, lIlIIlIIIllIIll, lIlIIlIIIllIIlI, lIlIIlIIIlIlIll);
    }

    public static void drawTracerToBlockEntity(BlockEntity lIlIIlIIIlIIIll, Color lIlIIlIIIlIIIlI, RenderEvent lIlIIlIIIlIIIIl) {
        RenderUtils.drawTracerToPos(lIlIIlIIIlIIIll.getPos(), lIlIIlIIIlIIIlI, lIlIIlIIIlIIIIl);
    }

    public static Vec3d getCameraVector() {
        boolean lIlIIlIIllIIIIl = Modules.get().isActive(Freecam.class) || Modules.get().get(FreeRotate.class).playerMode();
        return new Vec3d(0.0, 0.0, lIlIIlIIllIIIIl ? 1.0 : 75.0).rotateX(-((float)Math.toRadians(RenderUtils.mc.gameRenderer.getCamera().getPitch()))).rotateY(-((float)Math.toRadians(RenderUtils.mc.gameRenderer.getCamera().getYaw()))).add(RenderUtils.mc.gameRenderer.getCamera().getPos());
    }

    static {
        mc = MinecraftClient.getInstance();
    }

    public RenderUtils() {
        RenderUtils lIlIIlIIllllllI;
    }

    public static void drawItem(ItemStack lIlIIlIIlllIlIl, int lIlIIlIIllllIII, int lIlIIlIIlllIIll, boolean lIlIIlIIlllIIlI) {
        RenderSystem.disableLighting();
        RenderSystem.disableDepthTest();
        DiffuseLighting.enable();
        mc.getItemRenderer().renderGuiItemIcon(lIlIIlIIlllIlIl, lIlIIlIIllllIII, lIlIIlIIlllIIll);
        if (lIlIIlIIlllIIlI) {
            mc.getItemRenderer().renderGuiItemOverlay(RenderUtils.mc.textRenderer, lIlIIlIIlllIlIl, lIlIIlIIllllIII, lIlIIlIIlllIIll, null);
        }
        DiffuseLighting.disable();
        DiffuseLighting.disable();
        RenderSystem.enableDepthTest();
    }

    public static void drawTracerToEntity(RenderEvent lIlIIlIIlIIllIl, Entity lIlIIlIIlIlIlIl, Color lIlIIlIIlIlIlII, Target lIlIIlIIlIIlIlI, boolean lIlIIlIIlIIlIIl) {
        double lIlIIlIIlIlIIIl = lIlIIlIIlIlIlIl.prevX + (lIlIIlIIlIlIlIl.getX() - lIlIIlIIlIlIlIl.prevX) * (double)lIlIIlIIlIIllIl.tickDelta;
        double lIlIIlIIlIlIIII = lIlIIlIIlIlIlIl.prevY + (lIlIIlIIlIlIlIl.getY() - lIlIIlIIlIlIlIl.prevY) * (double)lIlIIlIIlIIllIl.tickDelta;
        double lIlIIlIIlIIllll = lIlIIlIIlIlIlIl.prevZ + (lIlIIlIIlIlIlIl.getZ() - lIlIIlIIlIlIlIl.prevZ) * (double)lIlIIlIIlIIllIl.tickDelta;
        double lIlIIlIIlIIlllI = lIlIIlIIlIlIlIl.getBoundingBox().maxY - lIlIIlIIlIlIlIl.getBoundingBox().minY;
        if (lIlIIlIIlIIlIlI == Target.Head) {
            lIlIIlIIlIlIIII += lIlIIlIIlIIlllI;
        } else if (lIlIIlIIlIIlIlI == Target.Body) {
            lIlIIlIIlIlIIII += lIlIIlIIlIIlllI / 2.0;
        }
        RenderUtils.drawLine(RenderUtils.getCameraVector(), lIlIIlIIlIlIIIl, lIlIIlIIlIlIIII, lIlIIlIIlIIllll, lIlIIlIIlIlIlII, lIlIIlIIlIIllIl);
        if (lIlIIlIIlIIlIIl) {
            Renderer.LINES.line(lIlIIlIIlIlIIIl, lIlIIlIIlIlIlIl.getY(), lIlIIlIIlIIllll, lIlIIlIIlIlIIIl, lIlIIlIIlIlIlIl.getY() + lIlIIlIIlIIlllI, lIlIIlIIlIIllll, lIlIIlIIlIlIlII);
        }
    }
}

