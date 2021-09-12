/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.waypoints;

import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_290;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Waypoint
implements ISerializable<Waypoint> {
    public boolean visible = true;
    public SettingColor color = new SettingColor(225, 25, 25);
    public int y;
    public boolean nether;
    public String name = "Meteor on Crack!";
    public boolean overworld;
    public int maxVisibleDistance = 1000;
    public int x;
    public String icon = "Square";
    private static final MeshBuilder MB = new MeshBuilder(128);
    public int z;
    public Dimension actualDimension;
    public double scale = 1.0;
    public boolean end;

    static {
        Waypoint.MB.texture = true;
    }

    public void nextIcon() {
        this.icon = this.getIcon(this.findIconIndex() + 1);
    }

    @Override
    public Waypoint fromTag(class_2487 class_24872) {
        this.name = class_24872.method_10558("name");
        this.icon = class_24872.method_10558("icon");
        this.color.fromTag(class_24872.method_10562("color"));
        this.x = class_24872.method_10550("x");
        this.y = class_24872.method_10550("y");
        this.z = class_24872.method_10550("z");
        this.visible = class_24872.method_10577("visible");
        this.maxVisibleDistance = class_24872.method_10550("maxVisibleDistance");
        this.scale = class_24872.method_10574("scale");
        this.actualDimension = Dimension.valueOf(class_24872.method_10558("dimension"));
        this.overworld = class_24872.method_10577("overworld");
        this.nether = class_24872.method_10577("nether");
        this.end = class_24872.method_10577("end");
        if (!Waypoints.get().icons.containsKey(this.icon)) {
            this.icon = "Square";
        }
        return this;
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("name", this.name);
        class_24872.method_10582("icon", this.icon);
        class_24872.method_10566("color", (class_2520)this.color.toTag());
        class_24872.method_10569("x", this.x);
        class_24872.method_10569("y", this.y);
        class_24872.method_10569("z", this.z);
        class_24872.method_10556("visible", this.visible);
        class_24872.method_10569("maxVisibleDistance", this.maxVisibleDistance);
        class_24872.method_10549("scale", this.scale);
        class_24872.method_10582("dimension", this.actualDimension.name());
        class_24872.method_10556("overworld", this.overworld);
        class_24872.method_10556("nether", this.nether);
        class_24872.method_10556("end", this.end);
        return class_24872;
    }

    public void renderIcon(double d, double d2, double d3, double d4, double d5) {
        MB.begin(null, DrawMode.Triangles, class_290.field_1575);
        int n = this.color.a;
        this.color.a = (int)((double)this.color.a * d4);
        MB.pos(d, d2, d3).texture(0.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2, d3).texture(1.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2 + d5, d3).texture(1.0, 1.0).color(this.color).endVertex();
        MB.pos(d, d2, d3).texture(0.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2 + d5, d3).texture(1.0, 1.0).color(this.color).endVertex();
        MB.pos(d, d2 + d5, d3).texture(0.0, 1.0).color(this.color).endVertex();
        Waypoints.get().icons.get(this.icon).method_23207();
        MB.end();
        this.color.a = n;
    }

    private String getIcon(int n) {
        n = this.correctIconIndex(n);
        int n2 = 0;
        for (String string : Waypoints.get().icons.keySet()) {
            if (n2 == n) {
                return string;
            }
            ++n2;
        }
        return "Square";
    }

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    private int correctIconIndex(int n) {
        if (n < 0) {
            return Waypoints.get().icons.size() + n;
        }
        if (n >= Waypoints.get().icons.size()) {
            return n - Waypoints.get().icons.size();
        }
        return n;
    }

    private int findIconIndex() {
        int n = 0;
        for (String string : Waypoints.get().icons.keySet()) {
            if (this.icon.equals(string)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public void prevIcon() {
        this.icon = this.getIcon(this.findIconIndex() - 1);
    }
}

