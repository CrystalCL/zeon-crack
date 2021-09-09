/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;

public class TextRadarHud
extends HudElement {
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ List<AbstractClientPlayerEntity> players;
    private final /* synthetic */ Setting<Boolean> distance;
    private final /* synthetic */ SettingGroup sgGeneral;

    public TextRadarHud(HUD llllllllllllllllIlIllIIIlIIIIlIl) {
        super(llllllllllllllllIlIllIIIlIIIIlIl, "player-info", "Displays players in your visual range.");
        TextRadarHud llllllllllllllllIlIllIIIlIIIIllI;
        llllllllllllllllIlIllIIIlIIIIllI.sgGeneral = llllllllllllllllIlIllIIIlIIIIllI.settings.getDefaultGroup();
        llllllllllllllllIlIllIIIlIIIIllI.distance = llllllllllllllllIlIllIIIlIIIIllI.sgGeneral.add(new BoolSetting.Builder().name("distance").description("Shows the distance to the player next to their name.").defaultValue(false).build());
        llllllllllllllllIlIllIIIlIIIIllI.friends = llllllllllllllllIlIllIIIlIIIIllI.sgGeneral.add(new BoolSetting.Builder().name("display-friends").description("Whether to show friends or not.").defaultValue(true).build());
        llllllllllllllllIlIllIIIlIIIIllI.players = new ArrayList<AbstractClientPlayerEntity>();
    }

    @Override
    public void update(HudRenderer llllllllllllllllIlIllIIIIlllIlII) {
        TextRadarHud llllllllllllllllIlIllIIIIllllIIl;
        double llllllllllllllllIlIllIIIIlllIlll = llllllllllllllllIlIllIIIIlllIlII.textWidth("Players:");
        double llllllllllllllllIlIllIIIIlllIllI = llllllllllllllllIlIllIIIIlllIlII.textHeight();
        if (llllllllllllllllIlIllIIIIllllIIl.mc.world == null) {
            llllllllllllllllIlIllIIIIllllIIl.box.setSize(llllllllllllllllIlIllIIIIlllIlll, llllllllllllllllIlIllIIIIlllIllI);
            return;
        }
        for (PlayerEntity PlayerEntity2 : llllllllllllllllIlIllIIIIllllIIl.getPlayers()) {
            if (PlayerEntity2.equals((Object)llllllllllllllllIlIllIIIIllllIIl.mc.player) || !llllllllllllllllIlIllIIIIllllIIl.friends.get().booleanValue() && Friends.get().contains(Friends.get().get(PlayerEntity2))) continue;
            String llllllllllllllllIlIllIIIIllllIll = PlayerEntity2.getGameProfile().getName();
            if (llllllllllllllllIlIllIIIIllllIIl.distance.get().booleanValue()) {
                llllllllllllllllIlIllIIIIllllIll = String.valueOf(new StringBuilder().append(llllllllllllllllIlIllIIIIllllIll).append(String.format("(%sm)", Math.round(llllllllllllllllIlIllIIIIllllIIl.mc.getCameraEntity().distanceTo((Entity)PlayerEntity2)))));
            }
            llllllllllllllllIlIllIIIIlllIlll = Math.max(llllllllllllllllIlIllIIIIlllIlll, llllllllllllllllIlIllIIIIlllIlII.textWidth(llllllllllllllllIlIllIIIIllllIll));
            llllllllllllllllIlIllIIIIlllIllI += llllllllllllllllIlIllIIIIlllIlII.textHeight() + 2.0;
        }
        llllllllllllllllIlIllIIIIllllIIl.box.setSize(llllllllllllllllIlIllIIIIlllIlll, llllllllllllllllIlIllIIIIlllIllI);
    }

    private List<AbstractClientPlayerEntity> getPlayers() {
        TextRadarHud llllllllllllllllIlIllIIIIlIlIllI;
        llllllllllllllllIlIllIIIIlIlIllI.players.clear();
        llllllllllllllllIlIllIIIIlIlIllI.players.addAll(llllllllllllllllIlIllIIIIlIlIllI.mc.world.getPlayers());
        llllllllllllllllIlIllIIIIlIlIllI.players.sort(Comparator.comparingDouble(llllllllllllllllIlIllIIIIlIIllll -> {
            TextRadarHud llllllllllllllllIlIllIIIIlIlIIII;
            return llllllllllllllllIlIllIIIIlIIllll.distanceTo(llllllllllllllllIlIllIIIIlIlIIII.mc.getCameraEntity());
        }));
        return llllllllllllllllIlIllIIIIlIlIllI.players;
    }

    @Override
    public void render(HudRenderer llllllllllllllllIlIllIIIIlIllllI) {
        TextRadarHud llllllllllllllllIlIllIIIIlIlllll;
        double llllllllllllllllIlIllIIIIllIIIIl = llllllllllllllllIlIllIIIIlIlllll.box.getX();
        double llllllllllllllllIlIllIIIIllIIIII = llllllllllllllllIlIllIIIIlIlllll.box.getY();
        llllllllllllllllIlIllIIIIlIllllI.text("Players:", llllllllllllllllIlIllIIIIllIIIIl, llllllllllllllllIlIllIIIIllIIIII, llllllllllllllllIlIllIIIIlIlllll.hud.secondaryColor.get());
        if (llllllllllllllllIlIllIIIIlIlllll.mc.world == null) {
            return;
        }
        for (PlayerEntity PlayerEntity2 : llllllllllllllllIlIllIIIIlIlllll.getPlayers()) {
            if (PlayerEntity2.equals((Object)llllllllllllllllIlIllIIIIlIlllll.mc.player) || !llllllllllllllllIlIllIIIIlIlllll.friends.get().booleanValue() && Friends.get().contains(Friends.get().get(PlayerEntity2))) continue;
            llllllllllllllllIlIllIIIIllIIIIl = llllllllllllllllIlIllIIIIlIlllll.box.getX();
            String llllllllllllllllIlIllIIIIllIIllI = PlayerEntity2.getGameProfile().getName();
            Color llllllllllllllllIlIllIIIIllIIlIl = Friends.get().contains(Friends.get().get(PlayerEntity2)) ? Friends.get().getFriendColor(PlayerEntity2) : (Color)llllllllllllllllIlIllIIIIlIlllll.hud.primaryColor.get();
            llllllllllllllllIlIllIIIIlIllllI.text(llllllllllllllllIlIllIIIIllIIllI, llllllllllllllllIlIllIIIIllIIIIl, llllllllllllllllIlIllIIIIllIIIII += llllllllllllllllIlIllIIIIlIllllI.textHeight() + 2.0, llllllllllllllllIlIllIIIIllIIlIl);
            if (!llllllllllllllllIlIllIIIIlIlllll.distance.get().booleanValue()) continue;
            llllllllllllllllIlIllIIIIllIIIIl += llllllllllllllllIlIllIIIIlIllllI.textWidth(String.valueOf(new StringBuilder().append(llllllllllllllllIlIllIIIIllIIllI).append(" ")));
            llllllllllllllllIlIllIIIIllIIllI = String.format("(%sm)", Math.round(llllllllllllllllIlIllIIIIlIlllll.mc.getCameraEntity().distanceTo((Entity)PlayerEntity2)));
            llllllllllllllllIlIllIIIIllIIlIl = llllllllllllllllIlIllIIIIlIlllll.hud.secondaryColor.get();
            llllllllllllllllIlIllIIIIlIllllI.text(llllllllllllllllIlIllIIIIllIIllI, llllllllllllllllIlIllIIIIllIIIIl, llllllllllllllllIlIllIIIIllIIIII, llllllllllllllllIlIllIIIIllIIlIl);
        }
    }
}

