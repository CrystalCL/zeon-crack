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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.render.VertexFormats;

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
    public Waypoint fromTag(NbtCompound NbtCompound2) {
        this.name = NbtCompound2.getString("name");
        this.icon = NbtCompound2.getString("icon");
        this.color.fromTag(NbtCompound2.getCompound("color"));
        this.x = NbtCompound2.getInt("x");
        this.y = NbtCompound2.getInt("y");
        this.z = NbtCompound2.getInt("z");
        this.visible = NbtCompound2.getBoolean("visible");
        this.maxVisibleDistance = NbtCompound2.getInt("maxVisibleDistance");
        this.scale = NbtCompound2.getDouble("scale");
        this.actualDimension = Dimension.valueOf(NbtCompound2.getString("dimension"));
        this.overworld = NbtCompound2.getBoolean("overworld");
        this.nether = NbtCompound2.getBoolean("nether");
        this.end = NbtCompound2.getBoolean("end");
        if (!Waypoints.get().icons.containsKey(this.icon)) {
            this.icon = "Square";
        }
        return this;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.putString("icon", this.icon);
        NbtCompound2.put("color", (NbtElement)this.color.toTag());
        NbtCompound2.putInt("x", this.x);
        NbtCompound2.putInt("y", this.y);
        NbtCompound2.putInt("z", this.z);
        NbtCompound2.putBoolean("visible", this.visible);
        NbtCompound2.putInt("maxVisibleDistance", this.maxVisibleDistance);
        NbtCompound2.putDouble("scale", this.scale);
        NbtCompound2.putString("dimension", this.actualDimension.name());
        NbtCompound2.putBoolean("overworld", this.overworld);
        NbtCompound2.putBoolean("nether", this.nether);
        NbtCompound2.putBoolean("end", this.end);
        return NbtCompound2;
    }

    public void renderIcon(double d, double d2, double d3, double d4, double d5) {
        MB.begin(null, DrawMode.Triangles, VertexFormats.POSITION_TEXTURE_COLOR);
        int n = this.color.a;
        this.color.a = (int)((double)this.color.a * d4);
        MB.pos(d, d2, d3).texture(0.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2, d3).texture(1.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2 + d5, d3).texture(1.0, 1.0).color(this.color).endVertex();
        MB.pos(d, d2, d3).texture(0.0, 0.0).color(this.color).endVertex();
        MB.pos(d + d5, d2 + d5, d3).texture(1.0, 1.0).color(this.color).endVertex();
        MB.pos(d, d2 + d5, d3).texture(0.0, 1.0).color(this.color).endVertex();
        Waypoints.get().icons.get(this.icon).bindTexture();
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
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
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

