/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.client.render.DiffuseLighting
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.network.PlayerListEntry
 */
package minegame159.meteorclient.systems.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.Camera;
import net.minecraft.client.network.PlayerListEntry;

public class LogoutSpots
extends Module {
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private static final /* synthetic */ Color RED;
    private /* synthetic */ int timer;
    private final /* synthetic */ List<PlayerEntity> lastPlayers;
    private final /* synthetic */ Setting<SettingColor> nameColor;
    private /* synthetic */ Dimension lastDimension;
    private static final /* synthetic */ Color GREEN;
    private final /* synthetic */ List<PlayerListEntry> lastPlayerList;
    private final /* synthetic */ Setting<SettingColor> nameBackgroundColor;
    private final /* synthetic */ Setting<Boolean> fullHeight;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private static final /* synthetic */ MeshBuilder MB;
    private final /* synthetic */ List<Entry> players;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ SettingGroup sgGeneral;
    private static final /* synthetic */ Color ORANGE;
    private final /* synthetic */ Setting<Double> scale;

    @Override
    public void onDeactivate() {
        LogoutSpots lIIIIllIllIlI;
        lIIIIllIllIlI.players.clear();
        lIIIIllIllIlI.lastPlayerList.clear();
    }

    public LogoutSpots() {
        super(Categories.Render, "logout-spots", "Displays a box where another player has logged out at.");
        LogoutSpots lIIIIlllIIIII;
        lIIIIlllIIIII.sgGeneral = lIIIIlllIIIII.settings.getDefaultGroup();
        lIIIIlllIIIII.sgRender = lIIIIlllIIIII.settings.createGroup("Render");
        lIIIIlllIIIII.scale = lIIIIlllIIIII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale.").defaultValue(1.0).min(0.0).build());
        lIIIIlllIIIII.fullHeight = lIIIIlllIIIII.sgGeneral.add(new BoolSetting.Builder().name("full-height").description("Displays the height as the player's full height.").defaultValue(true).build());
        lIIIIlllIIIII.shapeMode = lIIIIlllIIIII.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lIIIIlllIIIII.sideColor = lIIIIlllIIIII.sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 0, 255, 55)).build());
        lIIIIlllIIIII.lineColor = lIIIIlllIIIII.sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 0, 255)).build());
        lIIIIlllIIIII.nameColor = lIIIIlllIIIII.sgRender.add(new ColorSetting.Builder().name("name-color").description("The name color.").defaultValue(new SettingColor(255, 255, 255)).build());
        lIIIIlllIIIII.nameBackgroundColor = lIIIIlllIIIII.sgRender.add(new ColorSetting.Builder().name("name-background-color").description("The name background color.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        lIIIIlllIIIII.players = new ArrayList<Entry>();
        lIIIIlllIIIII.lastPlayerList = new ArrayList<PlayerListEntry>();
        lIIIIlllIIIII.lastPlayers = new ArrayList<PlayerEntity>();
        lIIIIlllIIIII.lineColor.changed();
    }

    @EventHandler
    private void onRender(RenderEvent lIIIIlIlIlIII) {
        LogoutSpots lIIIIlIlIlIIl;
        for (Entry lIIIIlIlIllII : lIIIIlIlIlIIl.players) {
            lIIIIlIlIllII.render(lIIIIlIlIlIII);
        }
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        DiffuseLighting.disable();
        RenderSystem.enableBlend();
    }

    @EventHandler
    private void onTick(TickEvent.Post lIIIIlIllllIl) {
        LogoutSpots lIIIIlIlllIll;
        if (lIIIIlIlllIll.mc.getNetworkHandler().getPlayerList().size() != lIIIIlIlllIll.lastPlayerList.size()) {
            for (PlayerListEntry lIIIIlIllllll : lIIIIlIlllIll.lastPlayerList) {
                if (lIIIIlIlllIll.mc.getNetworkHandler().getPlayerList().stream().anyMatch(lIIIIlIIllIIl -> lIIIIlIIllIIl.getProfile().equals((Object)lIIIIlIllllll.getProfile()))) continue;
                for (PlayerEntity lIIIIllIIIIII : lIIIIlIlllIll.lastPlayers) {
                    if (!lIIIIllIIIIII.getUuid().equals(lIIIIlIllllll.getProfile().getId())) continue;
                    lIIIIlIlllIll.add(lIIIIlIlllIll.new Entry(lIIIIllIIIIII));
                }
            }
            lIIIIlIlllIll.lastPlayerList.clear();
            lIIIIlIlllIll.lastPlayerList.addAll(lIIIIlIlllIll.mc.getNetworkHandler().getPlayerList());
            lIIIIlIlllIll.updateLastPlayers();
        }
        if (lIIIIlIlllIll.timer <= 0) {
            lIIIIlIlllIll.updateLastPlayers();
            lIIIIlIlllIll.timer = 10;
        } else {
            --lIIIIlIlllIll.timer;
        }
        Dimension lIIIIlIllllII = Utils.getDimension();
        if (lIIIIlIllllII != lIIIIlIlllIll.lastDimension) {
            lIIIIlIlllIll.players.clear();
        }
        lIIIIlIlllIll.lastDimension = lIIIIlIllllII;
    }

    private void updateLastPlayers() {
        LogoutSpots lIIIIllIlIlII;
        lIIIIllIlIlII.lastPlayers.clear();
        for (Entity lIIIIllIlIllI : lIIIIllIlIlII.mc.world.getEntities()) {
            if (!(lIIIIllIlIllI instanceof PlayerEntity)) continue;
            lIIIIllIlIlII.lastPlayers.add((PlayerEntity)lIIIIllIlIllI);
        }
    }

    @Override
    public void onActivate() {
        LogoutSpots lIIIIllIlllIl;
        lIIIIllIlllIl.lastPlayerList.addAll(lIIIIllIlllIl.mc.getNetworkHandler().getPlayerList());
        lIIIIllIlllIl.updateLastPlayers();
        lIIIIllIlllIl.timer = 10;
        lIIIIllIlllIl.lastDimension = Utils.getDimension();
    }

    private void add(Entry lIIIIlIllIIll) {
        LogoutSpots lIIIIlIllIlII;
        lIIIIlIllIlII.players.removeIf(lIIIIlIIlllIl -> lIIIIlIIlllIl.uuid.equals(lIIIIlIlIIIII.uuid));
        lIIIIlIllIlII.players.add(lIIIIlIllIIll);
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent lIIIIllIIlIlI) {
        if (lIIIIllIIlIlI.entity instanceof PlayerEntity) {
            LogoutSpots lIIIIllIIlIll;
            int lIIIIllIIllII = -1;
            for (int lIIIIllIIllIl = 0; lIIIIllIIllIl < lIIIIllIIlIll.players.size(); ++lIIIIllIIllIl) {
                if (!lIIIIllIIlIll.players.get((int)lIIIIllIIllIl).uuid.equals(lIIIIllIIlIlI.entity.getUuid())) continue;
                lIIIIllIIllII = lIIIIllIIllIl;
                break;
            }
            if (lIIIIllIIllII != -1) {
                lIIIIllIIlIll.players.remove(lIIIIllIIllII);
            }
        }
    }

    @Override
    public String getInfoString() {
        LogoutSpots lIIIIlIlIIIll;
        return Integer.toString(lIIIIlIlIIIll.players.size());
    }

    static {
        MB = new MeshBuilder(64);
        GREEN = new Color(25, 225, 25);
        ORANGE = new Color(225, 105, 25);
        RED = new Color(225, 25, 25);
    }

    private class Entry {
        public final /* synthetic */ String healthText;
        public final /* synthetic */ double zWidth;
        public final /* synthetic */ double height;
        public final /* synthetic */ double x;
        public final /* synthetic */ UUID uuid;
        public final /* synthetic */ int health;
        public final /* synthetic */ double xWidth;
        public final /* synthetic */ String name;
        public final /* synthetic */ double y;
        public final /* synthetic */ double z;
        public final /* synthetic */ int maxHealth;

        public Entry(PlayerEntity lllllllllllllllllllIlllIIIlIIlIl) {
            Entry lllllllllllllllllllIlllIIIlIIllI;
            lllllllllllllllllllIlllIIIlIIllI.x = lllllllllllllllllllIlllIIIlIIlIl.getX();
            lllllllllllllllllllIlllIIIlIIllI.y = lllllllllllllllllllIlllIIIlIIlIl.getY();
            lllllllllllllllllllIlllIIIlIIllI.z = lllllllllllllllllllIlllIIIlIIlIl.getZ();
            lllllllllllllllllllIlllIIIlIIllI.xWidth = lllllllllllllllllllIlllIIIlIIlIl.getBoundingBox().getXLength();
            lllllllllllllllllllIlllIIIlIIllI.zWidth = lllllllllllllllllllIlllIIIlIIlIl.getBoundingBox().getZLength();
            lllllllllllllllllllIlllIIIlIIllI.height = lllllllllllllllllllIlllIIIlIIlIl.getBoundingBox().getYLength();
            lllllllllllllllllllIlllIIIlIIllI.uuid = lllllllllllllllllllIlllIIIlIIlIl.getUuid();
            lllllllllllllllllllIlllIIIlIIllI.name = lllllllllllllllllllIlllIIIlIIlIl.getGameProfile().getName();
            lllllllllllllllllllIlllIIIlIIllI.health = Math.round(lllllllllllllllllllIlllIIIlIIlIl.getHealth() + lllllllllllllllllllIlllIIIlIIlIl.getAbsorptionAmount());
            lllllllllllllllllllIlllIIIlIIllI.maxHealth = Math.round(lllllllllllllllllllIlllIIIlIIlIl.getMaxHealth() + lllllllllllllllllllIlllIIIlIIlIl.getAbsorptionAmount());
            lllllllllllllllllllIlllIIIlIIllI.healthText = String.valueOf(new StringBuilder().append(" ").append(lllllllllllllllllllIlllIIIlIIllI.health));
        }

        public void render(RenderEvent lllllllllllllllllllIlllIIIIIllII) {
            Color lllllllllllllllllllIlllIIIIlIIII;
            Entry lllllllllllllllllllIlllIIIIIllIl;
            Camera lllllllllllllllllllIlllIIIIlIlII = ((LogoutSpots)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this).mc.gameRenderer.getCamera();
            double lllllllllllllllllllIlllIIIIlIIll = 0.025;
            double lllllllllllllllllllIlllIIIIlIIlI = Utils.distanceToCamera(lllllllllllllllllllIlllIIIIIllIl.x, lllllllllllllllllllIlllIIIIIllIl.y, lllllllllllllllllllIlllIIIIIllIl.z);
            if (lllllllllllllllllllIlllIIIIlIIlI > 8.0) {
                lllllllllllllllllllIlllIIIIlIIll *= lllllllllllllllllllIlllIIIIlIIlI / 8.0 * (Double)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.scale.get();
            }
            if (lllllllllllllllllllIlllIIIIlIIlI > (double)(((LogoutSpots)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this).mc.options.viewDistance * 16)) {
                return;
            }
            double lllllllllllllllllllIlllIIIIlIIIl = (double)lllllllllllllllllllIlllIIIIIllIl.health / (double)lllllllllllllllllllIlllIIIIIllIl.maxHealth;
            if (((Boolean)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.fullHeight.get()).booleanValue()) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllllIlllIIIIIllIl.x, lllllllllllllllllllIlllIIIIIllIl.y, lllllllllllllllllllIlllIIIIIllIl.z, lllllllllllllllllllIlllIIIIIllIl.x + lllllllllllllllllllIlllIIIIIllIl.xWidth, lllllllllllllllllllIlllIIIIIllIl.y + lllllllllllllllllllIlllIIIIIllIl.height, lllllllllllllllllllIlllIIIIIllIl.z + lllllllllllllllllllIlllIIIIIllIl.zWidth, (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.sideColor.get(), (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.lineColor.get(), (ShapeMode)((Object)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.shapeMode.get()), 0);
            } else {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllllIlllIIIIIllIl.x, lllllllllllllllllllIlllIIIIIllIl.y, lllllllllllllllllllIlllIIIIIllIl.z, lllllllllllllllllllIlllIIIIIllIl.xWidth, (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.sideColor.get(), (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.lineColor.get(), (ShapeMode)((Object)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.shapeMode.get()));
            }
            if (lllllllllllllllllllIlllIIIIlIIIl <= 0.333) {
                Color lllllllllllllllllllIlllIIIIllIII = RED;
            } else if (lllllllllllllllllllIlllIIIIlIIIl <= 0.666) {
                Color lllllllllllllllllllIlllIIIIlIlll = ORANGE;
            } else {
                lllllllllllllllllllIlllIIIIlIIII = GREEN;
            }
            Matrices.push();
            Matrices.translate(lllllllllllllllllllIlllIIIIIllIl.x + lllllllllllllllllllIlllIIIIIllIl.xWidth / 2.0 - lllllllllllllllllllIlllIIIIIllII.offsetX, lllllllllllllllllllIlllIIIIIllIl.y + ((Boolean)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.fullHeight.get() != false ? lllllllllllllllllllIlllIIIIIllIl.height + 0.5 : 0.5) - lllllllllllllllllllIlllIIIIIllII.offsetY, lllllllllllllllllllIlllIIIIIllIl.z + lllllllllllllllllllIlllIIIIIllIl.zWidth / 2.0 - lllllllllllllllllllIlllIIIIIllII.offsetZ);
            Matrices.rotate(-lllllllllllllllllllIlllIIIIlIlII.getYaw(), 0.0, 1.0, 0.0);
            Matrices.rotate(lllllllllllllllllllIlllIIIIlIlII.getPitch(), 1.0, 0.0, 0.0);
            Matrices.scale(-lllllllllllllllllllIlllIIIIlIIll, -lllllllllllllllllllIlllIIIIlIIll, lllllllllllllllllllIlllIIIIlIIll);
            double lllllllllllllllllllIlllIIIIIllll = TextRenderer.get().getWidth(lllllllllllllllllllIlllIIIIIllIl.name) / 2.0 + TextRenderer.get().getWidth(lllllllllllllllllllIlllIIIIIllIl.healthText) / 2.0;
            MB.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            MB.quad(-lllllllllllllllllllIlllIIIIIllll - 1.0, -1.0, 0.0, -lllllllllllllllllllIlllIIIIIllll - 1.0, 8.0, 0.0, lllllllllllllllllllIlllIIIIIllll + 1.0, 8.0, 0.0, lllllllllllllllllllIlllIIIIIllll + 1.0, -1.0, 0.0, (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.nameBackgroundColor.get());
            MB.end();
            TextRenderer.get().begin(1.0, false, true);
            double lllllllllllllllllllIlllIIIIIlllI = TextRenderer.get().render(lllllllllllllllllllIlllIIIIIllIl.name, -lllllllllllllllllllIlllIIIIIllll, 0.0, (Color)lllllllllllllllllllIlllIIIIIllIl.LogoutSpots.this.nameColor.get());
            TextRenderer.get().render(lllllllllllllllllllIlllIIIIIllIl.healthText, lllllllllllllllllllIlllIIIIIlllI, 0.0, lllllllllllllllllllIlllIIIIlIIII);
            TextRenderer.get().end();
            Matrices.pop();
        }
    }
}

