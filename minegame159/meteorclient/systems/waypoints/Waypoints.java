/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.NativeImage
 *  net.minecraft.client.texture.NativeImageBackedTexture
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.Camera
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

public class Waypoints
extends System<Waypoints>
implements Iterable<Waypoint> {
    public final /* synthetic */ Map<String, AbstractTexture> icons;
    private static final /* synthetic */ Color TEXT;
    private static final /* synthetic */ String[] BUILTIN_ICONS;
    private /* synthetic */ List<Waypoint> waypoints;
    private static final /* synthetic */ Color BACKGROUND;

    static {
        BUILTIN_ICONS = new String[]{"Square", "Circle", "Triangle", "Star", "Diamond"};
        BACKGROUND = new Color(0, 0, 0, 75);
        TEXT = new Color(255, 255, 255);
    }

    private boolean checkDimension(Waypoint lIlIlIllIIllIll) {
        Dimension lIlIlIllIIllIlI = Utils.getDimension();
        if (lIlIlIllIIllIll.overworld && lIlIlIllIIllIlI == Dimension.Overworld) {
            return true;
        }
        if (lIlIlIllIIllIll.nether && lIlIlIllIIllIlI == Dimension.Nether) {
            return true;
        }
        return lIlIlIllIIllIll.end && lIlIlIllIIllIlI == Dimension.End;
    }

    public Waypoints() {
        super(null);
        Waypoints lIlIlIlllIIllll;
        lIlIlIlllIIllll.icons = new HashMap<String, AbstractTexture>();
        lIlIlIlllIIllll.waypoints = new ArrayList<Waypoint>();
    }

    @Override
    public File getFile() {
        if (!Utils.canUpdate()) {
            return null;
        }
        return new File(new File(MeteorClient.FOLDER, "waypoints"), String.valueOf(new StringBuilder().append(Utils.getWorldName()).append(".nbt")));
    }

    public void add(Waypoint lIlIlIllIlIllll) {
        Waypoints lIlIlIllIlIlllI;
        lIlIlIllIlIlllI.waypoints.add(lIlIlIllIlIllll);
        lIlIlIllIlIlllI.save();
    }

    private void copyIcon(File lIlIlIlIIllIIII) {
        try {
            int lIlIlIlIIllIIll;
            InputStream lIlIlIlIIllIllI = Waypoints.class.getResourceAsStream(String.valueOf(new StringBuilder().append("/assets/meteor-client/waypoint-icons/").append(lIlIlIlIIllIIII.getName())));
            FileOutputStream lIlIlIlIIllIlIl = new FileOutputStream(lIlIlIlIIllIIII);
            byte[] lIlIlIlIIllIlII = new byte[256];
            while ((lIlIlIlIIllIIll = lIlIlIlIIllIllI.read(lIlIlIlIIllIlII)) > 0) {
                ((OutputStream)lIlIlIlIIllIlIl).write(lIlIlIlIIllIlII, 0, lIlIlIlIIllIIll);
            }
            ((OutputStream)lIlIlIlIIllIlIl).close();
            lIlIlIlIIllIllI.close();
        }
        catch (IOException lIlIlIlIIllIIlI) {
            lIlIlIlIIllIIlI.printStackTrace();
        }
    }

    @EventHandler(priority=-200)
    private void onGameDisconnected(GameLeftEvent lIlIlIllIlIIIII) {
        Waypoints lIlIlIllIIlllll;
        lIlIlIllIIlllll.waypoints.clear();
    }

    @Override
    public Waypoints fromTag(NbtCompound lIlIlIlIlIIIIIl) {
        Waypoints lIlIlIlIlIIIIlI;
        lIlIlIlIlIIIIlI.waypoints = NbtUtils.listFromTag(lIlIlIlIlIIIIIl.getList("waypoints", 10), lIlIlIlIIlIIIll -> new Waypoint().fromTag((NbtCompound)lIlIlIlIIlIIIll));
        return lIlIlIlIlIIIIlI;
    }

    @Override
    public NbtCompound toTag() {
        Waypoints lIlIlIlIlIIIllI;
        NbtCompound lIlIlIlIlIIIlll = new NbtCompound();
        lIlIlIlIlIIIlll.put("waypoints", (NbtElement)NbtUtils.listToTag(lIlIlIlIlIIIllI.waypoints));
        return lIlIlIlIlIIIlll;
    }

    public Vec3d getCoords(Waypoint lIlIlIllIIIlllI) {
        double lIlIlIllIIlIIIl = lIlIlIllIIIlllI.x;
        double lIlIlIllIIlIIII = lIlIlIllIIIlllI.y;
        double lIlIlIllIIIllll = lIlIlIllIIIlllI.z;
        if (lIlIlIllIIIlllI.actualDimension == Dimension.Overworld && Utils.getDimension() == Dimension.Nether) {
            lIlIlIllIIlIIIl = lIlIlIllIIIlllI.x / 8;
            lIlIlIllIIIllll = lIlIlIllIIIlllI.z / 8;
        } else if (lIlIlIllIIIlllI.actualDimension == Dimension.Nether && Utils.getDimension() == Dimension.Overworld) {
            lIlIlIllIIlIIIl = lIlIlIllIIIlllI.x * 8;
            lIlIlIllIIIllll = lIlIlIllIIIlllI.z * 8;
        }
        return new Vec3d(lIlIlIllIIlIIIl, lIlIlIllIIlIIII, lIlIlIllIIIllll);
    }

    public void remove(Waypoint lIlIlIllIlIIlll) {
        Waypoints lIlIlIllIlIlIII;
        if (lIlIlIllIlIlIII.waypoints.remove(lIlIlIllIlIIlll)) {
            lIlIlIllIlIlIII.save();
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent lIlIlIllIlIIlII) {
        Waypoints lIlIlIllIlIIIll;
        lIlIlIllIlIIIll.load();
    }

    @Override
    public Iterator<Waypoint> iterator() {
        Waypoints lIlIlIlIIllllII;
        return lIlIlIlIIllllII.waypoints.iterator();
    }

    @EventHandler
    private void onRender(RenderEvent lIlIlIlIlIlllll) {
        Waypoints lIlIlIlIllIIIlI;
        if (!Modules.get().isActive(WaypointsModule.class)) {
            return;
        }
        for (Waypoint lIlIlIlIllIIIll : lIlIlIlIllIIIlI) {
            if (!lIlIlIlIllIIIll.visible || !lIlIlIlIllIIIlI.checkDimension(lIlIlIlIllIIIll)) continue;
            Camera lIlIlIlIlllIIIl = MinecraftClient.getInstance().gameRenderer.getCamera();
            double lIlIlIlIlllIIII = lIlIlIlIllIIIlI.getCoords((Waypoint)lIlIlIlIllIIIll).x;
            double lIlIlIlIllIllll = lIlIlIlIllIIIlI.getCoords((Waypoint)lIlIlIlIllIIIll).y;
            double lIlIlIlIllIlllI = lIlIlIlIllIIIlI.getCoords((Waypoint)lIlIlIlIllIIIll).z;
            double lIlIlIlIllIllIl = Utils.distanceToCamera(lIlIlIlIlllIIII, lIlIlIlIllIllll, lIlIlIlIllIlllI);
            if (lIlIlIlIllIllIl > (double)lIlIlIlIllIIIll.maxVisibleDistance) continue;
            double lIlIlIlIllIllII = 0.01 * lIlIlIlIllIIIll.scale;
            if (lIlIlIlIllIllIl > 8.0) {
                lIlIlIlIllIllII *= lIlIlIlIllIllIl / 8.0;
            }
            double lIlIlIlIllIlIll = 1.0;
            if (lIlIlIlIllIllIl < 10.0 && (lIlIlIlIllIlIll = lIlIlIlIllIllIl / 10.0) < 0.1) continue;
            int lIlIlIlIllIlIlI = Waypoints.BACKGROUND.a;
            int lIlIlIlIllIlIIl = Waypoints.TEXT.a;
            Waypoints.BACKGROUND.a = (int)((double)Waypoints.BACKGROUND.a * lIlIlIlIllIlIll);
            Waypoints.TEXT.a = (int)((double)Waypoints.TEXT.a * lIlIlIlIllIlIll);
            double lIlIlIlIllIlIII = MinecraftClient.getInstance().options.viewDistance * 16;
            if (lIlIlIlIllIllIl > lIlIlIlIllIlIII) {
                double lIlIlIlIlllIlIl = lIlIlIlIlllIIII - lIlIlIlIlllIIIl.getPos().x;
                double lIlIlIlIlllIlII = lIlIlIlIllIllll - lIlIlIlIlllIIIl.getPos().y;
                double lIlIlIlIlllIIll = lIlIlIlIllIlllI - lIlIlIlIlllIIIl.getPos().z;
                double lIlIlIlIlllIIlI = Math.sqrt(lIlIlIlIlllIlIl * lIlIlIlIlllIlIl + lIlIlIlIlllIlII * lIlIlIlIlllIlII + lIlIlIlIlllIIll * lIlIlIlIlllIIll);
                lIlIlIlIlllIlIl /= lIlIlIlIlllIIlI;
                lIlIlIlIlllIlII /= lIlIlIlIlllIIlI;
                lIlIlIlIlllIIll /= lIlIlIlIlllIIlI;
                lIlIlIlIlllIIII = lIlIlIlIlllIIIl.getPos().x + (lIlIlIlIlllIlIl *= lIlIlIlIllIlIII);
                lIlIlIlIllIllll = lIlIlIlIlllIIIl.getPos().y + (lIlIlIlIlllIlII *= lIlIlIlIllIlIII);
                lIlIlIlIllIlllI = lIlIlIlIlllIIIl.getPos().z + (lIlIlIlIlllIIll *= lIlIlIlIllIlIII);
                lIlIlIlIllIllII /= lIlIlIlIllIllIl / 15.0;
                lIlIlIlIllIllII *= lIlIlIlIllIlIII / 15.0;
            }
            Matrices.push();
            Matrices.translate(lIlIlIlIlllIIII + 0.5 - lIlIlIlIlIlllll.offsetX, lIlIlIlIllIllll - lIlIlIlIlIlllll.offsetY, lIlIlIlIllIlllI + 0.5 - lIlIlIlIlIlllll.offsetZ);
            Matrices.translate(0.0, -0.5 + lIlIlIlIllIIIll.scale - 1.0, 0.0);
            Matrices.rotate(-lIlIlIlIlllIIIl.getYaw(), 0.0, 1.0, 0.0);
            Matrices.rotate(lIlIlIlIlllIIIl.getPitch(), 1.0, 0.0, 0.0);
            Matrices.translate(0.0, 0.5, 0.0);
            Matrices.scale(-lIlIlIlIllIllII, -lIlIlIlIllIllII, lIlIlIlIllIllII);
            String lIlIlIlIllIIlll = String.valueOf(new StringBuilder().append(Math.round(lIlIlIlIllIllIl)).append(" blocks"));
            TextRenderer.get().begin(1.0, false, true);
            double lIlIlIlIllIIllI = TextRenderer.get().getWidth(lIlIlIlIllIIIll.name) / 2.0;
            double lIlIlIlIllIIlIl = TextRenderer.get().getWidth(lIlIlIlIllIIlll) / 2.0;
            double lIlIlIlIllIIlII = TextRenderer.get().getHeight();
            lIlIlIlIllIIIll.renderIcon(-8.0, lIlIlIlIllIIlII, 0.0, lIlIlIlIllIlIll, 16.0);
            TextRenderer.get().render(lIlIlIlIllIIIll.name, -lIlIlIlIllIIllI, -lIlIlIlIllIIlII + 1.0, TEXT);
            TextRenderer.get().render(lIlIlIlIllIIlll, -lIlIlIlIllIIlIl, 0.0, TEXT);
            TextRenderer.get().end();
            Matrices.pop();
            Waypoints.BACKGROUND.a = lIlIlIlIllIlIlI;
            Waypoints.TEXT.a = lIlIlIlIllIlIIl;
        }
    }

    @Override
    public void init() {
        File[] lIlIlIllIllllII;
        Waypoints lIlIlIllIlllllI;
        File lIlIlIllIllllIl = new File(new File(MeteorClient.FOLDER, "waypoints"), "icons");
        lIlIlIllIllllIl.mkdirs();
        for (String lIlIlIlllIIIIll : BUILTIN_ICONS) {
            File lIlIlIlllIIIlII = new File(lIlIlIllIllllIl, String.valueOf(new StringBuilder().append(lIlIlIlllIIIIll).append(".png")));
            if (lIlIlIlllIIIlII.exists()) continue;
            lIlIlIllIlllllI.copyIcon(lIlIlIlllIIIlII);
        }
        for (File lIlIlIllIllllll : lIlIlIllIllllII = lIlIlIllIllllIl.listFiles()) {
            if (!lIlIlIllIllllll.getName().endsWith(".png")) continue;
            try {
                String lIlIlIlllIIIIlI = lIlIlIllIllllll.getName().replace(".png", "");
                NativeImageBackedTexture lIlIlIlllIIIIIl = new NativeImageBackedTexture(NativeImage.read((InputStream)new FileInputStream(lIlIlIllIllllll)));
                lIlIlIllIlllllI.icons.put(lIlIlIlllIIIIlI, (AbstractTexture)lIlIlIlllIIIIIl);
            }
            catch (IOException lIlIlIlllIIIIII) {
                lIlIlIlllIIIIII.printStackTrace();
            }
        }
    }

    public static Waypoints get() {
        return Systems.get(Waypoints.class);
    }
}

