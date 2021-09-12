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
import net.minecraft.class_1297;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1787;
import net.minecraft.class_1792;
import net.minecraft.class_1811;
import net.minecraft.class_1812;
import net.minecraft.class_1835;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

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
    private final class_243 vec3d;
    private double hitQuadZ2;
    private final List<Vec3> path;

    private double getProjectileGravity(class_1792 class_17922) {
        if (class_17922 instanceof class_1753 || class_17922 instanceof class_1764) {
            return 0.05;
        }
        if (class_17922 instanceof class_1812) {
            return 0.4;
        }
        if (class_17922 instanceof class_1787) {
            return 0.15;
        }
        if (class_17922 instanceof class_1835) {
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
        class_1792 class_17922 = this.mc.field_1724.method_6047().method_7909();
        if (!Utils.isThrowable(class_17922) && !Utils.isThrowable(class_17922 = this.mc.field_1724.method_6079().method_7909())) {
            return;
        }
        this.calculatePath(renderEvent.tickDelta, class_17922);
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

    private void calculatePath(float f, class_1792 class_17922) {
        Vec3 vec3;
        class_3959 class_39592;
        for (Vec3 vec32 : this.path) {
            this.vec3s.free(vec32);
        }
        this.path.clear();
        double d = this.mc.field_1724.field_6038 + (this.mc.field_1724.method_23317() - this.mc.field_1724.field_6038) * (double)f - Math.cos(Math.toRadians(this.mc.field_1724.field_6031)) * 0.16;
        double d2 = this.mc.field_1724.field_5971 + (this.mc.field_1724.method_23318() - this.mc.field_1724.field_5971) * (double)f + (double)this.mc.field_1724.method_5751() - 0.1;
        double d3 = this.mc.field_1724.field_5989 + (this.mc.field_1724.method_23321() - this.mc.field_1724.field_5989) * (double)f - Math.sin(Math.toRadians(this.mc.field_1724.field_6031)) * 0.16;
        double d4 = class_17922 instanceof class_1811 ? 1.0 : 0.4;
        double d5 = Math.toRadians(this.mc.field_1724.field_6031);
        double d6 = Math.toRadians(this.mc.field_1724.field_5965);
        double d7 = -Math.sin(d5) * Math.cos(d6) * d4;
        double d8 = -Math.sin(d6) * d4;
        double d9 = Math.cos(d5) * Math.cos(d6) * d4;
        double d10 = Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
        d7 /= d10;
        d8 /= d10;
        d9 /= d10;
        if (class_17922 instanceof class_1811) {
            float f2 = (float)(72000 - this.mc.field_1724.method_6014()) / 20.0f;
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
        double d11 = this.getProjectileGravity(class_17922);
        class_243 class_2432 = this.mc.field_1724.method_19538().method_1031(0.0, (double)this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), 0.0);
        class_3965 class_39652 = null;
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
            if (!this.mc.field_1687.method_2935().method_12123(n, n2)) break;
            ((IVec3d)this.vec3d).set(vec3);
        } while ((class_39652 = this.mc.field_1687.method_17742(class_39592 = new class_3959(class_2432, this.vec3d, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724))).method_17783() == class_239.class_240.field_1333);
        if (class_39652 != null && class_39652.method_17783() == class_239.class_240.field_1332) {
            vec3 = class_39652;
            this.hitQuad = true;
            this.hitQuadX1 = vec3.method_17784().field_1352;
            this.hitQuadY1 = vec3.method_17784().field_1351;
            this.hitQuadZ1 = vec3.method_17784().field_1350;
            this.hitQuadX2 = vec3.method_17784().field_1352;
            this.hitQuadY2 = vec3.method_17784().field_1351;
            this.hitQuadZ2 = vec3.method_17784().field_1350;
            if (vec3.method_17780() == class_2350.field_11036 || vec3.method_17780() == class_2350.field_11033) {
                this.hitQuadHorizontal = true;
                this.hitQuadX1 -= 0.25;
                this.hitQuadZ1 -= 0.25;
                this.hitQuadX2 += 0.25;
                this.hitQuadZ2 += 0.25;
            } else if (vec3.method_17780() == class_2350.field_11043 || vec3.method_17780() == class_2350.field_11035) {
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
        this.vec3d = new class_243(0.0, 0.0, 0.0);
        this.vec3s = new Pool<Vec3>(Vec3::new);
        this.path = new ArrayList<Vec3>();
    }
}

