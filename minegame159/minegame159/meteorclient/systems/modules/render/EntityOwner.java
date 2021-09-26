/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.mixin.ProjectileEntityAccessor;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.network.HttpUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.network.UuidNameHistoryResponseItem;
import minegame159.meteorclient.utils.render.NametagUtils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.client.render.VertexFormats;

public class EntityOwner
extends Module {
    private final Setting<Boolean> projectiles;
    private static final Color TEXT;
    private final SettingGroup sgGeneral;
    private final Map<UUID, String> uuidToName;
    private static final MeshBuilder MB;
    private final Vec3 pos;
    private final Setting<Double> scale;
    private static final Type RESPONSE_TYPE;
    private static final Color BACKGROUND;

    static {
        MB = new MeshBuilder(128);
        BACKGROUND = new Color(0, 0, 0, 75);
        TEXT = new Color(255, 255, 255);
        RESPONSE_TYPE = new TypeToken<List<UuidNameHistoryResponseItem>>(){}.getType();
    }

    @Override
    public void onDeactivate() {
        this.uuidToName.clear();
    }

    private void renderNametag(String string) {
        TextRenderer textRenderer = TextRenderer.get();
        NametagUtils.begin(this.pos);
        textRenderer.beginBig();
        double d = textRenderer.getWidth(string);
        double d2 = -d / 2.0;
        double d3 = -textRenderer.getHeight();
        MB.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
        MB.quad(d2 - 1.0, d3 - 1.0, d + 2.0, textRenderer.getHeight() + 2.0, BACKGROUND);
        MB.end();
        textRenderer.render(string, d2, d3, TEXT);
        textRenderer.end();
        NametagUtils.end();
    }

    private String getOwnerName(UUID uUID) {
        PlayerEntity PlayerEntity2 = this.mc.world.getPlayerByUuid(uUID);
        if (PlayerEntity2 != null) {
            return PlayerEntity2.getGameProfile().getName();
        }
        String string = this.uuidToName.get(uUID);
        if (string != null) {
            return string;
        }
        MeteorExecutor.execute(() -> this.lambda$getOwnerName$0(uUID));
        string = "Retrieving";
        this.uuidToName.put(uUID, string);
        return string;
    }

    public EntityOwner() {
        super(Categories.Render, "entity-owner", "Displays the name of the player who owns the entity you're looking at.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of the text.").defaultValue(1.0).min(0.0).build());
        this.projectiles = this.sgGeneral.add(new BoolSetting.Builder().name("projectiles").description("Display owner names of projectiles.").defaultValue(false).build());
        this.pos = new Vec3();
        this.uuidToName = new HashMap<UUID, String>();
    }

    private void lambda$getOwnerName$0(UUID uUID) {
        if (this.isActive()) {
            List list = (List)HttpUtils.get(String.valueOf(new StringBuilder().append("https://api.mojang.com/user/profiles/").append(uUID.toString().replace("-", "")).append("/names")), RESPONSE_TYPE);
            if (this.isActive()) {
                if (list == null || list.size() <= 0) {
                    this.uuidToName.put(uUID, "Failed to get name");
                } else {
                    this.uuidToName.put(uUID, ((UuidNameHistoryResponseItem)list.get((int)(list.size() - 1))).name);
                }
            }
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent render2DEvent) {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            UUID uUID;
            if (Entity2 instanceof TameableEntity) {
                uUID = ((TameableEntity)Entity2).getOwnerUuid();
            } else if (Entity2 instanceof HorseBaseEntity) {
                uUID = ((HorseBaseEntity)Entity2).getOwnerUuid();
            } else {
                if (!(Entity2 instanceof ProjectileEntity) || !this.projectiles.get().booleanValue()) continue;
                uUID = ((ProjectileEntityAccessor)Entity2).getOwnerUuid();
            }
            if (uUID == null) continue;
            this.pos.set(Entity2, render2DEvent.tickDelta);
            this.pos.add(0.0, (double)Entity2.getEyeHeight(Entity2.getPose()) + 0.75, 0.0);
            if (!NametagUtils.to2D(this.pos, this.scale.get())) continue;
            this.renderNametag(this.getOwnerName(uUID));
        }
    }
}

