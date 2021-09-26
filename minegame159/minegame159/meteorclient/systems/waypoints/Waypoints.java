/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.waypoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.WaypointsModule;
import minegame159.meteorclient.systems.waypoints.Waypoint;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.NbtUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.math.Vec3d;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Waypoints
extends System<Waypoints>
implements Iterable<Waypoint> {
    private static final Color TEXT;
    private static final String[] BUILTIN_ICONS;
    private static final Color BACKGROUND;
    private List<Waypoint> waypoints;
    public final Map<String, AbstractTexture> icons = new HashMap<String, AbstractTexture>();

    public void add(Waypoint waypoint) {
        this.waypoints.add(waypoint);
        this.save();
    }

    @Override
    public File getFile() {
        if (!Utils.canUpdate()) {
            return null;
        }
        return new File(new File(MeteorClient.FOLDER, "waypoints"), String.valueOf(new StringBuilder().append(Utils.getWorldName()).append(".nbt")));
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent gameJoinedEvent) {
        this.load();
    }

    private static Waypoint lambda$fromTag$0(NbtElement NbtElement2) {
        return new Waypoint().fromTag((NbtCompound)NbtElement2);
    }

    private boolean checkDimension(Waypoint waypoint) {
        Dimension dimension = Utils.getDimension();
        if (waypoint.overworld && dimension == Dimension.Overworld) {
            return true;
        }
        if (waypoint.nether && dimension == Dimension.Nether) {
            return true;
        }
        return waypoint.end && dimension == Dimension.End;
    }

    public static Waypoints get() {
        return Systems.get(Waypoints.class);
    }

    static {
        BUILTIN_ICONS = new String[]{"Square", "Circle", "Triangle", "Star", "Diamond"};
        BACKGROUND = new Color(0, 0, 0, 75);
        TEXT = new Color(255, 255, 255);
    }

    public Waypoints() {
        super(null);
        this.waypoints = new ArrayList<Waypoint>();
    }

    public void remove(Waypoint waypoint) {
        if (this.waypoints.remove(waypoint)) {
            this.save();
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!Modules.get().isActive(WaypointsModule.class)) {
            return;
        }
        for (Waypoint waypoint : this) {
            if (!waypoint.visible || !this.checkDimension(waypoint)) continue;
            Camera Camera2 = MinecraftClient.getInstance().gameRenderer.getCamera();
            double d = this.getCoords((Waypoint)waypoint).x;
            double d2 = this.getCoords((Waypoint)waypoint).y;
            double d3 = this.getCoords((Waypoint)waypoint).z;
            double d4 = Utils.distanceToCamera(d, d2, d3);
            if (d4 > (double)waypoint.maxVisibleDistance) continue;
            double d5 = 0.01 * waypoint.scale;
            if (d4 > 8.0) {
                d5 *= d4 / 8.0;
            }
            double d6 = 1.0;
            if (d4 < 10.0 && (d6 = d4 / 10.0) < 0.1) continue;
            int n = Waypoints.BACKGROUND.a;
            int n2 = Waypoints.TEXT.a;
            Waypoints.BACKGROUND.a = (int)((double)Waypoints.BACKGROUND.a * d6);
            Waypoints.TEXT.a = (int)((double)Waypoints.TEXT.a * d6);
            double d7 = MinecraftClient.getInstance().options.viewDistance * 16;
            if (d4 > d7) {
                double d8 = d - Camera2.getPos().x;
                double d9 = d2 - Camera2.getPos().y;
                double d10 = d3 - Camera2.getPos().z;
                double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
                d8 /= d11;
                d9 /= d11;
                d10 /= d11;
                d = Camera2.getPos().x + (d8 *= d7);
                d2 = Camera2.getPos().y + (d9 *= d7);
                d3 = Camera2.getPos().z + (d10 *= d7);
                d5 /= d4 / 15.0;
                d5 *= d7 / 15.0;
            }
            Matrices.push();
            Matrices.translate(d + 0.5 - renderEvent.offsetX, d2 - renderEvent.offsetY, d3 + 0.5 - renderEvent.offsetZ);
            Matrices.translate(0.0, -0.5 + waypoint.scale - 1.0, 0.0);
            Matrices.rotate(-Camera2.getYaw(), 0.0, 1.0, 0.0);
            Matrices.rotate(Camera2.getPitch(), 1.0, 0.0, 0.0);
            Matrices.translate(0.0, 0.5, 0.0);
            Matrices.scale(-d5, -d5, d5);
            String string = String.valueOf(new StringBuilder().append(Math.round(d4)).append(" blocks"));
            TextRenderer.get().begin(1.0, false, true);
            double d12 = TextRenderer.get().getWidth(waypoint.name) / 2.0;
            double d13 = TextRenderer.get().getWidth(string) / 2.0;
            double d14 = TextRenderer.get().getHeight();
            waypoint.renderIcon(-8.0, d14, 0.0, d6, 16.0);
            TextRenderer.get().render(waypoint.name, -d12, -d14 + 1.0, TEXT);
            TextRenderer.get().render(string, -d13, 0.0, TEXT);
            TextRenderer.get().end();
            Matrices.pop();
            Waypoints.BACKGROUND.a = n;
            Waypoints.TEXT.a = n2;
        }
    }

    public Vec3d getCoords(Waypoint waypoint) {
        double d = waypoint.x;
        double d2 = waypoint.y;
        double d3 = waypoint.z;
        if (waypoint.actualDimension == Dimension.Overworld && Utils.getDimension() == Dimension.Nether) {
            d = waypoint.x / 8;
            d3 = waypoint.z / 8;
        } else if (waypoint.actualDimension == Dimension.Nether && Utils.getDimension() == Dimension.Overworld) {
            d = waypoint.x * 8;
            d3 = waypoint.z * 8;
        }
        return new Vec3d(d, d2, d3);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Iterator<Waypoint> iterator() {
        return this.waypoints.iterator();
    }

    @Override
    public Waypoints fromTag(NbtCompound NbtCompound2) {
        this.waypoints = NbtUtils.listFromTag(NbtCompound2.getList("waypoints", 10), Waypoints::lambda$fromTag$0);
        return this;
    }

    private void copyIcon(File file) {
        try {
            int n;
            InputStream inputStream = Waypoints.class.getResourceAsStream(String.valueOf(new StringBuilder().append("/assets/meteor-client/waypoint-icons/").append(file.getName())));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] byArray = new byte[256];
            while ((n = inputStream.read(byArray)) > 0) {
                ((OutputStream)fileOutputStream).write(byArray, 0, n);
            }
            ((OutputStream)fileOutputStream).close();
            inputStream.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    @EventHandler(priority=-200)
    private void onGameDisconnected(GameLeftEvent gameLeftEvent) {
        this.waypoints.clear();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("waypoints", (NbtElement)NbtUtils.listToTag(this.waypoints));
        return NbtCompound2;
    }

    @Override
    public void init() {
        File file = new File(new File(MeteorClient.FOLDER, "waypoints"), "icons");
        file.mkdirs();
        Object[] objectArray = BUILTIN_ICONS;
        int n = objectArray.length;
        for (int i = 0; i < n; ++i) {
            String string = objectArray[i];
            Object object = new File(file, String.valueOf(new StringBuilder().append(string).append(".png")));
            if (((File)object).exists()) continue;
            this.copyIcon((File)object);
        }
        for (Object object : objectArray = file.listFiles()) {
            if (!((File)object).getName().endsWith(".png")) continue;
            try {
                String string = ((File)object).getName().replace(".png", "");
                NativeImageBackedTexture NativeImageBackedTexture2 = new NativeImageBackedTexture(NativeImage.read((InputStream)new FileInputStream((File)object)));
                this.icons.put(string, (AbstractTexture)NativeImageBackedTexture2);
                continue;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            if (3 > 0) continue;
            return;
        }
    }
}

