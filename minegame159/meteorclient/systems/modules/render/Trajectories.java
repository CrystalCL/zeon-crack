/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.BowItem
 *  net.minecraft.item.CrossbowItem
 *  net.minecraft.item.FishingRodItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.RangedWeaponItem
 *  net.minecraft.item.PotionItem
 *  net.minecraft.item.TridentItem
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class Trajectories
extends Module {
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private /* synthetic */ boolean hitQuadHorizontal;
    private /* synthetic */ double hitQuadY2;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ double hitQuadX1;
    private final /* synthetic */ List<Vec3> path;
    private /* synthetic */ double hitQuadZ2;
    private /* synthetic */ double hitQuadZ1;
    private /* synthetic */ double hitQuadY1;
    private final /* synthetic */ Pool<Vec3> vec3s;
    private /* synthetic */ double hitQuadX2;
    private final /* synthetic */ Vec3d vec3d;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private /* synthetic */ boolean hitQuad;

    private Vec3 addToPath(double llIllIlIllIIII, double llIllIlIlIllll, double llIllIlIlIlllI) {
        Trajectories llIllIlIllIIIl;
        Vec3 llIllIlIlIllIl = llIllIlIllIIIl.vec3s.get();
        llIllIlIlIllIl.set(llIllIlIllIIII, llIllIlIlIllll, llIllIlIlIlllI);
        llIllIlIllIIIl.path.add(llIllIlIlIllIl);
        return llIllIlIlIllIl;
    }

    private void calculatePath(float llIllIllIIlllI, Item llIllIllIIllIl) {
        RaycastContext llIllIlllIIIIl;
        Trajectories llIllIllIIllll;
        for (Vec3 llIllIlllIIllI : llIllIllIIllll.path) {
            llIllIllIIllll.vec3s.free(llIllIlllIIllI);
        }
        llIllIllIIllll.path.clear();
        double llIllIllIlllII = llIllIllIIllll.mc.player.lastRenderX + (llIllIllIIllll.mc.player.getX() - llIllIllIIllll.mc.player.lastRenderX) * (double)llIllIllIIlllI - Math.cos(Math.toRadians(llIllIllIIllll.mc.player.yaw)) * 0.16;
        double llIllIllIllIll = llIllIllIIllll.mc.player.lastRenderY + (llIllIllIIllll.mc.player.getY() - llIllIllIIllll.mc.player.lastRenderY) * (double)llIllIllIIlllI + (double)llIllIllIIllll.mc.player.getStandingEyeHeight() - 0.1;
        double llIllIllIllIlI = llIllIllIIllll.mc.player.lastRenderZ + (llIllIllIIllll.mc.player.getZ() - llIllIllIIllll.mc.player.lastRenderZ) * (double)llIllIllIIlllI - Math.sin(Math.toRadians(llIllIllIIllll.mc.player.yaw)) * 0.16;
        double llIllIllIllIIl = llIllIllIIllIl instanceof RangedWeaponItem ? 1.0 : 0.4;
        double llIllIllIllIII = Math.toRadians(llIllIllIIllll.mc.player.yaw);
        double llIllIllIlIlll = Math.toRadians(llIllIllIIllll.mc.player.pitch);
        double llIllIllIlIllI = -Math.sin(llIllIllIllIII) * Math.cos(llIllIllIlIlll) * llIllIllIllIIl;
        double llIllIllIlIlIl = -Math.sin(llIllIllIlIlll) * llIllIllIllIIl;
        double llIllIllIlIlII = Math.cos(llIllIllIllIII) * Math.cos(llIllIllIlIlll) * llIllIllIllIIl;
        double llIllIllIlIIll = Math.sqrt(llIllIllIlIllI * llIllIllIlIllI + llIllIllIlIlIl * llIllIllIlIlIl + llIllIllIlIlII * llIllIllIlIlII);
        llIllIllIlIllI /= llIllIllIlIIll;
        llIllIllIlIlIl /= llIllIllIlIIll;
        llIllIllIlIlII /= llIllIllIlIIll;
        if (llIllIllIIllIl instanceof RangedWeaponItem) {
            float llIllIlllIIlIl = (float)(72000 - llIllIllIIllll.mc.player.getItemUseTimeLeft()) / 20.0f;
            if ((llIllIlllIIlIl = (llIllIlllIIlIl * llIllIlllIIlIl + llIllIlllIIlIl * 2.0f) / 3.0f) > 1.0f || llIllIlllIIlIl <= 0.1f) {
                llIllIlllIIlIl = 1.0f;
            }
            llIllIllIlIllI *= (double)(llIllIlllIIlIl *= 3.0f);
            llIllIllIlIlIl *= (double)llIllIlllIIlIl;
            llIllIllIlIlII *= (double)llIllIlllIIlIl;
        } else {
            llIllIllIlIllI *= 1.5;
            llIllIllIlIlIl *= 1.5;
            llIllIllIlIlII *= 1.5;
        }
        double llIllIllIlIIlI = llIllIllIIllll.getProjectileGravity(llIllIllIIllIl);
        Vec3d llIllIllIlIIIl = llIllIllIIllll.mc.player.getPos().add(0.0, (double)llIllIllIIllll.mc.player.getEyeHeight(llIllIllIIllll.mc.player.getPose()), 0.0);
        BlockHitResult llIllIllIlIIII = null;
        do {
            Vec3 llIllIlllIIlII = llIllIllIIllll.addToPath(llIllIllIlllII, llIllIllIllIll, llIllIllIllIlI);
            llIllIllIlllII += llIllIllIlIllI * 0.1;
            llIllIllIllIlI += llIllIllIlIlII * 0.1;
            if ((llIllIllIllIll += llIllIllIlIlIl * 0.1) < 0.0) break;
            llIllIllIlIllI *= 0.999;
            llIllIllIlIlIl *= 0.999;
            llIllIllIlIlII *= 0.999;
            llIllIllIlIlIl -= llIllIllIlIIlI * 0.1;
            int llIllIlllIIIll = (int)(llIllIllIlllII / 16.0);
            int llIllIlllIIIlI = (int)(llIllIllIllIlI / 16.0);
            if (!llIllIllIIllll.mc.world.getChunkManager().isChunkLoaded(llIllIlllIIIll, llIllIlllIIIlI)) break;
            ((IVec3d)llIllIllIIllll.vec3d).set(llIllIlllIIlII);
        } while ((llIllIllIlIIII = llIllIllIIllll.mc.world.raycast(llIllIlllIIIIl = new RaycastContext(llIllIllIlIIIl, llIllIllIIllll.vec3d, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)llIllIllIIllll.mc.player))).getType() == Type.MISS);
        if (llIllIllIlIIII != null && llIllIllIlIIII.getType() == Type.BLOCK) {
            BlockHitResult llIllIlllIIIII = llIllIllIlIIII;
            llIllIllIIllll.hitQuad = true;
            llIllIllIIllll.hitQuadX1 = llIllIlllIIIII.getPos().x;
            llIllIllIIllll.hitQuadY1 = llIllIlllIIIII.getPos().y;
            llIllIllIIllll.hitQuadZ1 = llIllIlllIIIII.getPos().z;
            llIllIllIIllll.hitQuadX2 = llIllIlllIIIII.getPos().x;
            llIllIllIIllll.hitQuadY2 = llIllIlllIIIII.getPos().y;
            llIllIllIIllll.hitQuadZ2 = llIllIlllIIIII.getPos().z;
            if (llIllIlllIIIII.getSide() == Direction.UP || llIllIlllIIIII.getSide() == Direction.DOWN) {
                llIllIllIIllll.hitQuadHorizontal = true;
                llIllIllIIllll.hitQuadX1 -= 0.25;
                llIllIllIIllll.hitQuadZ1 -= 0.25;
                llIllIllIIllll.hitQuadX2 += 0.25;
                llIllIllIIllll.hitQuadZ2 += 0.25;
            } else if (llIllIlllIIIII.getSide() == Direction.NORTH || llIllIlllIIIII.getSide() == Direction.SOUTH) {
                llIllIllIIllll.hitQuadHorizontal = false;
                llIllIllIIllll.hitQuadX1 -= 0.25;
                llIllIllIIllll.hitQuadY1 -= 0.25;
                llIllIllIIllll.hitQuadX2 += 0.25;
                llIllIllIIllll.hitQuadY2 += 0.25;
            } else {
                llIllIllIIllll.hitQuadHorizontal = false;
                llIllIllIIllll.hitQuadZ1 -= 0.25;
                llIllIllIIllll.hitQuadY1 -= 0.25;
                llIllIllIIllll.hitQuadZ2 += 0.25;
                llIllIllIIllll.hitQuadY2 += 0.25;
            }
        } else {
            llIllIllIIllll.hitQuad = false;
        }
    }

    private double getProjectileGravity(Item llIllIlIlllIII) {
        if (llIllIlIlllIII instanceof BowItem || llIllIlIlllIII instanceof CrossbowItem) {
            return 0.05;
        }
        if (llIllIlIlllIII instanceof PotionItem) {
            return 0.4;
        }
        if (llIllIlIlllIII instanceof FishingRodItem) {
            return 0.15;
        }
        if (llIllIlIlllIII instanceof TridentItem) {
            return 0.015;
        }
        return 0.03;
    }

    @EventHandler
    private void onRender(RenderEvent llIlllIIIIIlII) {
        Trajectories llIlllIIIIIlIl;
        Item llIlllIIIIIIll = llIlllIIIIIlIl.mc.player.getMainHandStack().getItem();
        if (!Utils.isThrowable(llIlllIIIIIIll) && !Utils.isThrowable(llIlllIIIIIIll = llIlllIIIIIlIl.mc.player.getOffHandStack().getItem())) {
            return;
        }
        llIlllIIIIIlIl.calculatePath(llIlllIIIIIlII.tickDelta, llIlllIIIIIIll);
        Vec3 llIlllIIIIIIlI = null;
        for (Vec3 llIlllIIIIIllI : llIlllIIIIIlIl.path) {
            if (llIlllIIIIIIlI != null) {
                Renderer.LINES.line(llIlllIIIIIIlI.x, llIlllIIIIIIlI.y, llIlllIIIIIIlI.z, llIlllIIIIIllI.x, llIlllIIIIIllI.y, llIlllIIIIIllI.z, llIlllIIIIIlIl.lineColor.get());
            }
            llIlllIIIIIIlI = llIlllIIIIIllI;
        }
        if (llIlllIIIIIlIl.hitQuad) {
            if (llIlllIIIIIlIl.hitQuadHorizontal) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, llIlllIIIIIlIl.hitQuadX1, llIlllIIIIIlIl.hitQuadY1, llIlllIIIIIlIl.hitQuadZ1, 0.5, llIlllIIIIIlIl.sideColor.get(), llIlllIIIIIlIl.lineColor.get(), llIlllIIIIIlIl.shapeMode.get());
            } else {
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, llIlllIIIIIlIl.hitQuadX1, llIlllIIIIIlIl.hitQuadY1, llIlllIIIIIlIl.hitQuadZ1, llIlllIIIIIlIl.hitQuadX2, llIlllIIIIIlIl.hitQuadY2, llIlllIIIIIlIl.hitQuadZ2, llIlllIIIIIlIl.sideColor.get(), llIlllIIIIIlIl.lineColor.get(), llIlllIIIIIlIl.shapeMode.get());
            }
        }
    }

    public Trajectories() {
        super(Categories.Render, "trajectories", "Predicts the trajectory of throwable items.");
        Trajectories llIlllIIIIlllI;
        llIlllIIIIlllI.sgGeneral = llIlllIIIIlllI.settings.getDefaultGroup();
        llIlllIIIIlllI.shapeMode = llIlllIIIIlllI.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        llIlllIIIIlllI.sideColor = llIlllIIIIlllI.sgGeneral.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 150, 0, 35)).build());
        llIlllIIIIlllI.lineColor = llIlllIIIIlllI.sgGeneral.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 150, 0)).build());
        llIlllIIIIlllI.vec3d = new Vec3d(0.0, 0.0, 0.0);
        llIlllIIIIlllI.vec3s = new Pool<Vec3>(Vec3::new);
        llIlllIIIIlllI.path = new ArrayList<Vec3>();
    }
}

