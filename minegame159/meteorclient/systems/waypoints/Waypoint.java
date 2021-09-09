/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.render.VertexFormats
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

public class Waypoint
implements ISerializable<Waypoint> {
    public /* synthetic */ String name;
    public /* synthetic */ int x;
    public /* synthetic */ String icon;
    public /* synthetic */ int z;
    public /* synthetic */ boolean visible;
    public /* synthetic */ boolean nether;
    public /* synthetic */ int y;
    public /* synthetic */ boolean end;
    public /* synthetic */ double scale;
    public /* synthetic */ SettingColor color;
    private static final /* synthetic */ MeshBuilder MB;
    public /* synthetic */ boolean overworld;
    public /* synthetic */ Dimension actualDimension;
    public /* synthetic */ int maxVisibleDistance;

    private String getIcon(int llllllllllllllllllIIlllIllIIlIll) {
        Waypoint llllllllllllllllllIIlllIllIIllll;
        llllllllllllllllllIIlllIllIIlIll = llllllllllllllllllIIlllIllIIllll.correctIconIndex(llllllllllllllllllIIlllIllIIlIll);
        int llllllllllllllllllIIlllIllIIllIl = 0;
        for (String llllllllllllllllllIIlllIllIlIIII : Waypoints.get().icons.keySet()) {
            if (llllllllllllllllllIIlllIllIIllIl == llllllllllllllllllIIlllIllIIlIll) {
                return llllllllllllllllllIIlllIllIlIIII;
            }
            ++llllllllllllllllllIIlllIllIIllIl;
        }
        return "Square";
    }

    public void prevIcon() {
        Waypoint llllllllllllllllllIIlllIllIIIllI;
        llllllllllllllllllIIlllIllIIIllI.icon = llllllllllllllllllIIlllIllIIIllI.getIcon(llllllllllllllllllIIlllIllIIIllI.findIconIndex() - 1);
    }

    static {
        MB = new MeshBuilder(128);
        Waypoint.MB.texture = true;
    }

    public void renderIcon(double llllllllllllllllllIIlllIllllIIIl, double llllllllllllllllllIIlllIllllIIII, double llllllllllllllllllIIlllIlllIllll, double llllllllllllllllllIIlllIlllIlllI, double llllllllllllllllllIIlllIlllIllIl) {
        Waypoint llllllllllllllllllIIlllIllllIIlI;
        MB.begin(null, DrawMode.Triangles, VertexFormats.POSITION_TEXTURE_COLOR);
        int llllllllllllllllllIIlllIlllIllII = llllllllllllllllllIIlllIllllIIlI.color.a;
        llllllllllllllllllIIlllIllllIIlI.color.a = (int)((double)llllllllllllllllllIIlllIllllIIlI.color.a * llllllllllllllllllIIlllIlllIlllI);
        MB.pos(llllllllllllllllllIIlllIllllIIIl, llllllllllllllllllIIlllIllllIIII, llllllllllllllllllIIlllIlllIllll).texture(0.0, 0.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        MB.pos(llllllllllllllllllIIlllIllllIIIl + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIllllIIII, llllllllllllllllllIIlllIlllIllll).texture(1.0, 0.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        MB.pos(llllllllllllllllllIIlllIllllIIIl + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIllllIIII + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIlllIllll).texture(1.0, 1.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        MB.pos(llllllllllllllllllIIlllIllllIIIl, llllllllllllllllllIIlllIllllIIII, llllllllllllllllllIIlllIlllIllll).texture(0.0, 0.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        MB.pos(llllllllllllllllllIIlllIllllIIIl + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIllllIIII + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIlllIllll).texture(1.0, 1.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        MB.pos(llllllllllllllllllIIlllIllllIIIl, llllllllllllllllllIIlllIllllIIII + llllllllllllllllllIIlllIlllIllIl, llllllllllllllllllIIlllIlllIllll).texture(0.0, 1.0).color(llllllllllllllllllIIlllIllllIIlI.color).endVertex();
        Waypoints.get().icons.get(llllllllllllllllllIIlllIllllIIlI.icon).bindTexture();
        MB.end();
        llllllllllllllllllIIlllIllllIIlI.color.a = llllllllllllllllllIIlllIlllIllII;
    }

    @Override
    public NbtCompound toTag() {
        Waypoint llllllllllllllllllIIlllIlIllllll;
        NbtCompound llllllllllllllllllIIlllIlIlllllI = new NbtCompound();
        llllllllllllllllllIIlllIlIlllllI.putString("name", llllllllllllllllllIIlllIlIllllll.name);
        llllllllllllllllllIIlllIlIlllllI.putString("icon", llllllllllllllllllIIlllIlIllllll.icon);
        llllllllllllllllllIIlllIlIlllllI.put("color", (NbtElement)llllllllllllllllllIIlllIlIllllll.color.toTag());
        llllllllllllllllllIIlllIlIlllllI.putInt("x", llllllllllllllllllIIlllIlIllllll.x);
        llllllllllllllllllIIlllIlIlllllI.putInt("y", llllllllllllllllllIIlllIlIllllll.y);
        llllllllllllllllllIIlllIlIlllllI.putInt("z", llllllllllllllllllIIlllIlIllllll.z);
        llllllllllllllllllIIlllIlIlllllI.putBoolean("visible", llllllllllllllllllIIlllIlIllllll.visible);
        llllllllllllllllllIIlllIlIlllllI.putInt("maxVisibleDistance", llllllllllllllllllIIlllIlIllllll.maxVisibleDistance);
        llllllllllllllllllIIlllIlIlllllI.putDouble("scale", llllllllllllllllllIIlllIlIllllll.scale);
        llllllllllllllllllIIlllIlIlllllI.putString("dimension", llllllllllllllllllIIlllIlIllllll.actualDimension.name());
        llllllllllllllllllIIlllIlIlllllI.putBoolean("overworld", llllllllllllllllllIIlllIlIllllll.overworld);
        llllllllllllllllllIIlllIlIlllllI.putBoolean("nether", llllllllllllllllllIIlllIlIllllll.nether);
        llllllllllllllllllIIlllIlIlllllI.putBoolean("end", llllllllllllllllllIIlllIlIllllll.end);
        return llllllllllllllllllIIlllIlIlllllI;
    }

    @Override
    public Waypoint fromTag(NbtCompound llllllllllllllllllIIlllIlIlllIII) {
        Waypoint llllllllllllllllllIIlllIlIllIlll;
        llllllllllllllllllIIlllIlIllIlll.name = llllllllllllllllllIIlllIlIlllIII.getString("name");
        llllllllllllllllllIIlllIlIllIlll.icon = llllllllllllllllllIIlllIlIlllIII.getString("icon");
        llllllllllllllllllIIlllIlIllIlll.color.fromTag(llllllllllllllllllIIlllIlIlllIII.getCompound("color"));
        llllllllllllllllllIIlllIlIllIlll.x = llllllllllllllllllIIlllIlIlllIII.getInt("x");
        llllllllllllllllllIIlllIlIllIlll.y = llllllllllllllllllIIlllIlIlllIII.getInt("y");
        llllllllllllllllllIIlllIlIllIlll.z = llllllllllllllllllIIlllIlIlllIII.getInt("z");
        llllllllllllllllllIIlllIlIllIlll.visible = llllllllllllllllllIIlllIlIlllIII.getBoolean("visible");
        llllllllllllllllllIIlllIlIllIlll.maxVisibleDistance = llllllllllllllllllIIlllIlIlllIII.getInt("maxVisibleDistance");
        llllllllllllllllllIIlllIlIllIlll.scale = llllllllllllllllllIIlllIlIlllIII.getDouble("scale");
        llllllllllllllllllIIlllIlIllIlll.actualDimension = Dimension.valueOf(llllllllllllllllllIIlllIlIlllIII.getString("dimension"));
        llllllllllllllllllIIlllIlIllIlll.overworld = llllllllllllllllllIIlllIlIlllIII.getBoolean("overworld");
        llllllllllllllllllIIlllIlIllIlll.nether = llllllllllllllllllIIlllIlIlllIII.getBoolean("nether");
        llllllllllllllllllIIlllIlIllIlll.end = llllllllllllllllllIIlllIlIlllIII.getBoolean("end");
        if (!Waypoints.get().icons.containsKey(llllllllllllllllllIIlllIlIllIlll.icon)) {
            llllllllllllllllllIIlllIlIllIlll.icon = "Square";
        }
        return llllllllllllllllllIIlllIlIllIlll;
    }

    private int findIconIndex() {
        int llllllllllllllllllIIlllIllIllllI = 0;
        for (String llllllllllllllllllIIlllIlllIIIII : Waypoints.get().icons.keySet()) {
            Waypoint llllllllllllllllllIIlllIllIlllll;
            if (llllllllllllllllllIIlllIllIlllll.icon.equals(llllllllllllllllllIIlllIlllIIIII)) {
                return llllllllllllllllllIIlllIllIllllI;
            }
            ++llllllllllllllllllIIlllIllIllllI;
        }
        return -1;
    }

    public Waypoint() {
        Waypoint llllllllllllllllllIIlllIlllllIll;
        llllllllllllllllllIIlllIlllllIll.name = "Meteor on Crack!";
        llllllllllllllllllIIlllIlllllIll.icon = "Square";
        llllllllllllllllllIIlllIlllllIll.color = new SettingColor(225, 25, 25);
        llllllllllllllllllIIlllIlllllIll.visible = true;
        llllllllllllllllllIIlllIlllllIll.maxVisibleDistance = 1000;
        llllllllllllllllllIIlllIlllllIll.scale = 1.0;
    }

    public void nextIcon() {
        Waypoint llllllllllllllllllIIlllIllIIIIlI;
        llllllllllllllllllIIlllIllIIIIlI.icon = llllllllllllllllllIIlllIllIIIIlI.getIcon(llllllllllllllllllIIlllIllIIIIlI.findIconIndex() + 1);
    }

    private int correctIconIndex(int llllllllllllllllllIIlllIllIlIllI) {
        if (llllllllllllllllllIIlllIllIlIllI < 0) {
            return Waypoints.get().icons.size() + llllllllllllllllllIIlllIllIlIllI;
        }
        if (llllllllllllllllllIIlllIllIlIllI >= Waypoints.get().icons.size()) {
            return llllllllllllllllllIIlllIllIlIllI - Waypoints.get().icons.size();
        }
        return llllllllllllllllllIIlllIllIlIllI;
    }
}

