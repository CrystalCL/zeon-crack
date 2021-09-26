/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.network.PlayerListEntry;

public class LogoutSpots
extends Module {
    private int timer;
    private final Setting<SettingColor> nameBackgroundColor;
    private static final Color ORANGE;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> nameColor;
    private final List<PlayerListEntry> lastPlayerList;
    private static final MeshBuilder MB;
    private static final Color RED;
    private final SettingGroup sgRender;
    private final List<Entry> players;
    private final List<PlayerEntity> lastPlayers;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> fullHeight;
    private final Setting<SettingColor> lineColor;
    private static final Color GREEN;
    private final Setting<Double> scale;
    private Dimension lastDimension;
    private final Setting<SettingColor> sideColor;

    @Override
    public void onDeactivate() {
        this.players.clear();
        this.lastPlayerList.clear();
    }

    private static boolean lambda$onTick$0(PlayerListEntry PlayerListEntry2, PlayerListEntry PlayerListEntry3) {
        return PlayerListEntry3.getProfile().equals((Object)PlayerListEntry2.getProfile());
    }

    static MeshBuilder access$1000() {
        return MB;
    }

    static MinecraftClient access$200(LogoutSpots logoutSpots) {
        return logoutSpots.mc;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.getNetworkHandler().getPlayerList().size() != this.lastPlayerList.size()) {
            for (PlayerListEntry PlayerListEntry2 : this.lastPlayerList) {
                if (this.mc.getNetworkHandler().getPlayerList().stream().anyMatch(arg_0 -> LogoutSpots.lambda$onTick$0(PlayerListEntry2, arg_0))) continue;
                for (PlayerEntity PlayerEntity2 : this.lastPlayers) {
                    if (!PlayerEntity2.getUuid().equals(PlayerListEntry2.getProfile().getId())) continue;
                    this.add(new Entry(this, PlayerEntity2));
                }
            }
            this.lastPlayerList.clear();
            this.lastPlayerList.addAll(this.mc.getNetworkHandler().getPlayerList());
            this.updateLastPlayers();
        }
        if (this.timer <= 0) {
            this.updateLastPlayers();
            this.timer = 10;
        } else {
            --this.timer;
        }
        Object object = Utils.getDimension();
        if (object != this.lastDimension) {
            this.players.clear();
        }
        this.lastDimension = object;
    }

    static Setting access$300(LogoutSpots logoutSpots) {
        return logoutSpots.fullHeight;
    }

    public LogoutSpots() {
        super(Categories.Render, "logout-spots", "Displays a box where another player has logged out at.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale.").defaultValue(1.0).min(0.0).build());
        this.fullHeight = this.sgGeneral.add(new BoolSetting.Builder().name("full-height").description("Displays the height as the player's full height.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 0, 255, 55)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 0, 255)).build());
        this.nameColor = this.sgRender.add(new ColorSetting.Builder().name("name-color").description("The name color.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.nameBackgroundColor = this.sgRender.add(new ColorSetting.Builder().name("name-background-color").description("The name background color.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        this.players = new ArrayList<Entry>();
        this.lastPlayerList = new ArrayList<PlayerListEntry>();
        this.lastPlayers = new ArrayList<PlayerEntity>();
        this.lineColor.changed();
    }

    @Override
    public void onActivate() {
        this.lastPlayerList.addAll(this.mc.getNetworkHandler().getPlayerList());
        this.updateLastPlayers();
        this.timer = 10;
        this.lastDimension = Utils.getDimension();
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        for (Entry entry : this.players) {
            entry.render(renderEvent);
        }
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        DiffuseLighting.disable();
        RenderSystem.enableBlend();
    }

    static Setting access$400(LogoutSpots logoutSpots) {
        return logoutSpots.sideColor;
    }

    static Setting access$100(LogoutSpots logoutSpots) {
        return logoutSpots.scale;
    }

    static Color access$800() {
        return ORANGE;
    }

    private void updateLastPlayers() {
        this.lastPlayers.clear();
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof PlayerEntity)) continue;
            this.lastPlayers.add((PlayerEntity)Entity2);
        }
    }

    static Color access$900() {
        return GREEN;
    }

    private void add(Entry entry) {
        this.players.removeIf(arg_0 -> LogoutSpots.lambda$add$1(entry, arg_0));
        this.players.add(entry);
    }

    static Setting access$500(LogoutSpots logoutSpots) {
        return logoutSpots.lineColor;
    }

    static Setting access$1100(LogoutSpots logoutSpots) {
        return logoutSpots.nameBackgroundColor;
    }

    static Setting access$600(LogoutSpots logoutSpots) {
        return logoutSpots.shapeMode;
    }

    static MinecraftClient access$000(LogoutSpots logoutSpots) {
        return logoutSpots.mc;
    }

    private static boolean lambda$add$1(Entry entry, Entry entry2) {
        return entry2.uuid.equals(entry.uuid);
    }

    static {
        MB = new MeshBuilder(64);
        GREEN = new Color(25, 225, 25);
        ORANGE = new Color(225, 105, 25);
        RED = new Color(225, 25, 25);
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent entityAddedEvent) {
        if (entityAddedEvent.entity instanceof PlayerEntity) {
            int n = -1;
            for (int i = 0; i < this.players.size(); ++i) {
                if (!this.players.get((int)i).uuid.equals(entityAddedEvent.entity.getUuid())) continue;
                n = i;
                break;
            }
            if (n != -1) {
                this.players.remove(n);
            }
        }
    }

    @Override
    public String getInfoString() {
        return Integer.toString(this.players.size());
    }

    static Setting access$1200(LogoutSpots logoutSpots) {
        return logoutSpots.nameColor;
    }

    static Color access$700() {
        return RED;
    }

    private class Entry {
        public final String name;
        public final double height;
        public final int maxHealth;
        public final double zWidth;
        public final double z;
        public final String healthText;
        public final double xWidth;
        final LogoutSpots this$0;
        public final int health;
        public final double x;
        public final UUID uuid;
        public final double y;

        public Entry(LogoutSpots logoutSpots, PlayerEntity PlayerEntity2) {
            this.this$0 = logoutSpots;
            this.x = PlayerEntity2.getX();
            this.y = PlayerEntity2.getY();
            this.z = PlayerEntity2.getZ();
            this.xWidth = PlayerEntity2.getBoundingBox().getXLength();
            this.zWidth = PlayerEntity2.getBoundingBox().getZLength();
            this.height = PlayerEntity2.getBoundingBox().getYLength();
            this.uuid = PlayerEntity2.getUuid();
            this.name = PlayerEntity2.getGameProfile().getName();
            this.health = Math.round(PlayerEntity2.getHealth() + PlayerEntity2.getAbsorptionAmount());
            this.maxHealth = Math.round(PlayerEntity2.getMaxHealth() + PlayerEntity2.getAbsorptionAmount());
            this.healthText = String.valueOf(new StringBuilder().append(" ").append(this.health));
        }

        public void render(RenderEvent renderEvent) {
            Camera Camera2 = LogoutSpots.access$000((LogoutSpots)this.this$0).gameRenderer.getCamera();
            double d = 0.025;
            double d2 = Utils.distanceToCamera(this.x, this.y, this.z);
            if (d2 > 8.0) {
                d *= d2 / 8.0 * (Double)LogoutSpots.access$100(this.this$0).get();
            }
            if (d2 > (double)(LogoutSpots.access$200((LogoutSpots)this.this$0).options.viewDistance * 16)) {
                return;
            }
            double d3 = (double)this.health / (double)this.maxHealth;
            if (((Boolean)LogoutSpots.access$300(this.this$0).get()).booleanValue()) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, this.x + this.xWidth, this.y + this.height, this.z + this.zWidth, (Color)LogoutSpots.access$400(this.this$0).get(), (Color)LogoutSpots.access$500(this.this$0).get(), (ShapeMode)((Object)LogoutSpots.access$600(this.this$0).get()), 0);
            } else {
                Renderer.quadWithLinesHorizontal(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, this.xWidth, (Color)LogoutSpots.access$400(this.this$0).get(), (Color)LogoutSpots.access$500(this.this$0).get(), (ShapeMode)((Object)LogoutSpots.access$600(this.this$0).get()));
            }
            Color color = d3 <= 0.333 ? LogoutSpots.access$700() : (d3 <= 0.666 ? LogoutSpots.access$800() : LogoutSpots.access$900());
            Matrices.push();
            Matrices.translate(this.x + this.xWidth / 2.0 - renderEvent.offsetX, this.y + ((Boolean)LogoutSpots.access$300(this.this$0).get() != false ? this.height + 0.5 : 0.5) - renderEvent.offsetY, this.z + this.zWidth / 2.0 - renderEvent.offsetZ);
            Matrices.rotate(-Camera2.getYaw(), 0.0, 1.0, 0.0);
            Matrices.rotate(Camera2.getPitch(), 1.0, 0.0, 0.0);
            Matrices.scale(-d, -d, d);
            double d4 = TextRenderer.get().getWidth(this.name) / 2.0 + TextRenderer.get().getWidth(this.healthText) / 2.0;
            LogoutSpots.access$1000().begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            LogoutSpots.access$1000().quad(-d4 - 1.0, -1.0, 0.0, -d4 - 1.0, 8.0, 0.0, d4 + 1.0, 8.0, 0.0, d4 + 1.0, -1.0, 0.0, (Color)LogoutSpots.access$1100(this.this$0).get());
            LogoutSpots.access$1000().end();
            TextRenderer.get().begin(1.0, false, true);
            double d5 = TextRenderer.get().render(this.name, -d4, 0.0, (Color)LogoutSpots.access$1200(this.this$0).get());
            TextRenderer.get().render(this.healthText, d5, 0.0, color);
            TextRenderer.get().end();
            Matrices.pop();
        }
    }
}

