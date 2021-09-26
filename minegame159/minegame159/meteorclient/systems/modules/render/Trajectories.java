/*
 * Decompiled with CFR 0.151.
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
    private boolean hitQuadHorizontal;
    private double hitQuadX1;
    private final Pool<Vec3> vec3s;
    private double hitQuadY1;
    private final Setting<SettingColor> sideColor;
    private double hitQuadY2;
    private double hitQuadX2;
    private double hitQuadZ1;
    private final Setting<SettingColor> lineColor;
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgGeneral;
    private boolean hitQuad;
    private final Vec3d vec3d;
    private double hitQuadZ2;
    private final List<Vec3> path;

    private double getProjectileGravity(Item Item2) {
        if (Item2 instanceof BowItem || Item2 instanceof CrossbowItem) {
            return 0.05;
        }
        if (Item2 instanceof PotionItem) {
            return 0.4;
        }
        if (Item2 instanceof FishingRodItem) {
            return 0.15;
        }
        if (Item2 instanceof TridentItem) {
            return 0.015;
        }
        return 0.03;
    }

    private Vec3 addToPath(double d, double d2, double d3) {
        Vec3 vec3 = this.vec3s.get();
        vec3.set(d, d2, d3);
        this.path.add(vec3);
        return vec3;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        Item Item2 = this.mc.player.getMainHandStack().getItem();
        if (!Utils.isThrowable(Item2) && !Utils.isThrowable(Item2 = this.mc.player.getOffHandStack().getItem())) {
            return;
        }
        this.calculatePath(renderEvent.tickDelta, Item2);
        Vec3 vec3 = null;
        for (Vec3 vec32 : this.path) {
            if (vec3 != null) {
                Renderer.LINES.line(vec3.x, vec3.y, vec3.z, vec32.x, vec32.y, vec32.z, this.lineColor.get());
            }
            vec3 = vec32;
        }
        if (this.hitQuad) {
            if (this.hitQuadHorizontal) {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, this.hitQuadX1, this.hitQuadY1, this.hitQuadZ1, 0.5, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get());
            } else {
                Renderer.quadWithLinesVertical(Renderer.NORMAL, Renderer.LINES, this.hitQuadX1, this.hitQuadY1, this.hitQuadZ1, this.hitQuadX2, this.hitQuadY2, this.hitQuadZ2, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get());
            }
        }
    }

    private void calculatePath(float f, Item Item2) {
        Vec3 vec3;
        RaycastContext RaycastContext2;
        for (Vec3 vec32 : this.path) {
            this.vec3s.free(vec32);
        }
        this.path.clear();
        double d = this.mc.player.lastRenderX + (this.mc.player.getX() - this.mc.player.lastRenderX) * (double)f - Math.cos(Math.toRadians(this.mc.player.yaw)) * 0.16;
        double d2 = this.mc.player.lastRenderY + (this.mc.player.getY() - this.mc.player.lastRenderY) * (double)f + (double)this.mc.player.getStandingEyeHeight() - 0.1;
        double d3 = this.mc.player.lastRenderZ + (this.mc.player.getZ() - this.mc.player.lastRenderZ) * (double)f - Math.sin(Math.toRadians(this.mc.player.yaw)) * 0.16;
        double d4 = Item2 instanceof RangedWeaponItem ? 1.0 : 0.4;
        double d5 = Math.toRadians(this.mc.player.yaw);
        double d6 = Math.toRadians(this.mc.player.pitch);
        double d7 = -Math.sin(d5) * Math.cos(d6) * d4;
        double d8 = -Math.sin(d6) * d4;
        double d9 = Math.cos(d5) * Math.cos(d6) * d4;
        double d10 = Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
        d7 /= d10;
        d8 /= d10;
        d9 /= d10;
        if (Item2 instanceof RangedWeaponItem) {
            float f2 = (float)(72000 - this.mc.player.getItemUseTimeLeft()) / 20.0f;
            if ((f2 = (f2 * f2 + f2 * 2.0f) / 3.0f) > 1.0f || f2 <= 0.1f) {
                f2 = 1.0f;
            }
            d7 *= (double)(f2 *= 3.0f);
            d8 *= (double)f2;
            d9 *= (double)f2;
        } else {
            d7 *= 1.5;
            d8 *= 1.5;
            d9 *= 1.5;
        }
        double d11 = this.getProjectileGravity(Item2);
        Vec3d Vec3d2 = this.mc.player.getPos().add(0.0, (double)this.mc.player.getEyeHeight(this.mc.player.getPose()), 0.0);
        BlockHitResult BlockHitResult2 = null;
        do {
            vec3 = this.addToPath(d, d2, d3);
            d += d7 * 0.1;
            d3 += d9 * 0.1;
            if ((d2 += d8 * 0.1) < 0.0) break;
            d7 *= 0.999;
            d8 *= 0.999;
            d9 *= 0.999;
            d8 -= d11 * 0.1;
            int n = (int)(d / 16.0);
            int n2 = (int)(d3 / 16.0);
            if (!this.mc.world.getChunkManager().isChunkLoaded(n, n2)) break;
            ((IVec3d)this.vec3d).set(vec3);
        } while ((BlockHitResult2 = this.mc.world.raycast(RaycastContext2 = new RaycastContext(Vec3d2, this.vec3d, RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.mc.player))).getType() == HitResult.class_240.MISS);
        if (BlockHitResult2 != null && BlockHitResult2.getType() == HitResult.class_240.BLOCK) {
            vec3 = BlockHitResult2;
            this.hitQuad = true;
            this.hitQuadX1 = vec3.getPos().x;
            this.hitQuadY1 = vec3.getPos().y;
            this.hitQuadZ1 = vec3.getPos().z;
            this.hitQuadX2 = vec3.getPos().x;
            this.hitQuadY2 = vec3.getPos().y;
            this.hitQuadZ2 = vec3.getPos().z;
            if (vec3.getSide() == Direction.UP || vec3.getSide() == Direction.DOWN) {
                this.hitQuadHorizontal = true;
                this.hitQuadX1 -= 0.25;
                this.hitQuadZ1 -= 0.25;
                this.hitQuadX2 += 0.25;
                this.hitQuadZ2 += 0.25;
            } else if (vec3.getSide() == Direction.NORTH || vec3.getSide() == Direction.SOUTH) {
                this.hitQuadHorizontal = false;
                this.hitQuadX1 -= 0.25;
                this.hitQuadY1 -= 0.25;
                this.hitQuadX2 += 0.25;
                this.hitQuadY2 += 0.25;
            } else {
                this.hitQuadHorizontal = false;
                this.hitQuadZ1 -= 0.25;
                this.hitQuadY1 -= 0.25;
                this.hitQuadZ2 += 0.25;
                this.hitQuadY2 += 0.25;
            }
        } else {
            this.hitQuad = false;
        }
    }

    public Trajectories() {
        super(Categories.Render, "trajectories", "Predicts the trajectory of throwable items.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.shapeMode = this.sgGeneral.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgGeneral.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 150, 0, 35)).build());
        this.lineColor = this.sgGeneral.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 150, 0)).build());
        this.vec3d = new Vec3d(0.0, 0.0, 0.0);
        this.vec3s = new Pool<Vec3>(Vec3::new);
        this.path = new ArrayList<Vec3>();
    }
}

