/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.TameableEntity
 *  net.minecraft.entity.passive.HorseBaseEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.projectile.ProjectileEntity
 *  net.minecraft.client.render.VertexFormats
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
    private final /* synthetic */ Vec3 pos;
    private static final /* synthetic */ Type RESPONSE_TYPE;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Map<UUID, String> uuidToName;
    private final /* synthetic */ Setting<Double> scale;
    private final /* synthetic */ Setting<Boolean> projectiles;
    private static final /* synthetic */ Color BACKGROUND;
    private static final /* synthetic */ Color TEXT;
    private static final /* synthetic */ MeshBuilder MB;

    @EventHandler
    private void onRender2D(Render2DEvent llllIlIIIllIlI) {
        EntityOwner llllIlIIIllIll;
        for (Entity llllIlIIIlllII : llllIlIIIllIll.mc.world.getEntities()) {
            UUID llllIlIIIlllIl;
            if (llllIlIIIlllII instanceof TameableEntity) {
                UUID llllIlIIIlllll = ((TameableEntity)llllIlIIIlllII).getOwnerUuid();
            } else if (llllIlIIIlllII instanceof HorseBaseEntity) {
                UUID llllIlIIIllllI = ((HorseBaseEntity)llllIlIIIlllII).getOwnerUuid();
            } else {
                if (!(llllIlIIIlllII instanceof ProjectileEntity) || !llllIlIIIllIll.projectiles.get().booleanValue()) continue;
                llllIlIIIlllIl = ((ProjectileEntityAccessor)llllIlIIIlllII).getOwnerUuid();
            }
            if (llllIlIIIlllIl == null) continue;
            llllIlIIIllIll.pos.set(llllIlIIIlllII, llllIlIIIllIlI.tickDelta);
            llllIlIIIllIll.pos.add(0.0, (double)llllIlIIIlllII.getEyeHeight(llllIlIIIlllII.getPose()) + 0.75, 0.0);
            if (!NametagUtils.to2D(llllIlIIIllIll.pos, llllIlIIIllIll.scale.get())) continue;
            llllIlIIIllIll.renderNametag(llllIlIIIllIll.getOwnerName(llllIlIIIlllIl));
        }
    }

    private String getOwnerName(UUID llllIIllllllIl) {
        EntityOwner llllIIlllllllI;
        PlayerEntity llllIIllllllII = llllIIlllllllI.mc.world.getPlayerByUuid(llllIIllllllIl);
        if (llllIIllllllII != null) {
            return llllIIllllllII.getGameProfile().getName();
        }
        String llllIIlllllIll = llllIIlllllllI.uuidToName.get(llllIIllllllIl);
        if (llllIIlllllIll != null) {
            return llllIIlllllIll;
        }
        MeteorExecutor.execute(() -> {
            EntityOwner llllIIllllIIII;
            if (llllIIllllIIII.isActive()) {
                List llllIIllllIIll = (List)HttpUtils.get(String.valueOf(new StringBuilder().append("https://api.mojang.com/user/profiles/").append(llllIIllllllIl.toString().replace("-", "")).append("/names")), RESPONSE_TYPE);
                if (llllIIllllIIII.isActive()) {
                    if (llllIIllllIIll == null || llllIIllllIIll.size() <= 0) {
                        llllIIllllIIII.uuidToName.put(llllIIllllllIl, "Failed to get name");
                    } else {
                        llllIIllllIIII.uuidToName.put(llllIIllllllIl, ((UuidNameHistoryResponseItem)llllIIllllIIll.get((int)(llllIIllllIIll.size() - 1))).name);
                    }
                }
            }
        });
        llllIIlllllIll = "Retrieving";
        llllIIlllllllI.uuidToName.put(llllIIllllllIl, llllIIlllllIll);
        return llllIIlllllIll;
    }

    private void renderNametag(String llllIlIIIIIlll) {
        EntityOwner llllIlIIIIlIII;
        TextRenderer llllIlIIIIllII = TextRenderer.get();
        NametagUtils.begin(llllIlIIIIlIII.pos);
        llllIlIIIIllII.beginBig();
        double llllIlIIIIlIll = llllIlIIIIllII.getWidth(llllIlIIIIIlll);
        double llllIlIIIIlIlI = -llllIlIIIIlIll / 2.0;
        double llllIlIIIIlIIl = -llllIlIIIIllII.getHeight();
        MB.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
        MB.quad(llllIlIIIIlIlI - 1.0, llllIlIIIIlIIl - 1.0, llllIlIIIIlIll + 2.0, llllIlIIIIllII.getHeight() + 2.0, BACKGROUND);
        MB.end();
        llllIlIIIIllII.render(llllIlIIIIIlll, llllIlIIIIlIlI, llllIlIIIIlIIl, TEXT);
        llllIlIIIIllII.end();
        NametagUtils.end();
    }

    public EntityOwner() {
        super(Categories.Render, "entity-owner", "Displays the name of the player who owns the entity you're looking at.");
        EntityOwner llllIlIIlIlIII;
        llllIlIIlIlIII.sgGeneral = llllIlIIlIlIII.settings.getDefaultGroup();
        llllIlIIlIlIII.scale = llllIlIIlIlIII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of the text.").defaultValue(1.0).min(0.0).build());
        llllIlIIlIlIII.projectiles = llllIlIIlIlIII.sgGeneral.add(new BoolSetting.Builder().name("projectiles").description("Display owner names of projectiles.").defaultValue(false).build());
        llllIlIIlIlIII.pos = new Vec3();
        llllIlIIlIlIII.uuidToName = new HashMap<UUID, String>();
    }

    static {
        MB = new MeshBuilder(128);
        BACKGROUND = new Color(0, 0, 0, 75);
        TEXT = new Color(255, 255, 255);
        RESPONSE_TYPE = new TypeToken<List<UuidNameHistoryResponseItem>>(){
            {
                1 lIIlllllllIlIlI;
            }
        }.getType();
    }

    @Override
    public void onDeactivate() {
        EntityOwner llllIlIIlIIllI;
        llllIlIIlIIllI.uuidToName.clear();
    }
}

