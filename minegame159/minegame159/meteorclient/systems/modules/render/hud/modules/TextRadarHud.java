/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> friends;
    private final Setting<Boolean> distance;
    private final List<AbstractClientPlayerEntity> players;
    private final SettingGroup sgGeneral;

    private List<AbstractClientPlayerEntity> getPlayers() {
        this.players.clear();
        this.players.addAll(this.mc.world.getPlayers());
        this.players.sort(Comparator.comparingDouble(this::lambda$getPlayers$0));
        return this.players;
    }

    public TextRadarHud(HUD hUD) {
        super(hUD, "player-info", "Displays players in your visual range.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.distance = this.sgGeneral.add(new BoolSetting.Builder().name("distance").description("Shows the distance to the player next to their name.").defaultValue(false).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("display-friends").description("Whether to show friends or not.").defaultValue(true).build());
        this.players = new ArrayList<AbstractClientPlayerEntity>();
    }

    private double lambda$getPlayers$0(AbstractClientPlayerEntity AbstractClientPlayerEntity2) {
        return AbstractClientPlayerEntity2.distanceTo(this.mc.getCameraEntity());
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX();
        double d2 = this.box.getY();
        hudRenderer.text("Players:", d, d2, this.hud.secondaryColor.get());
        if (this.mc.world == null) {
            return;
        }
        for (PlayerEntity PlayerEntity2 : this.getPlayers()) {
            if (PlayerEntity2.equals((Object)this.mc.player) || !this.friends.get().booleanValue() && Friends.get().contains(Friends.get().get(PlayerEntity2))) continue;
            d = this.box.getX();
            String string = PlayerEntity2.getGameProfile().getName();
            Color color = Friends.get().contains(Friends.get().get(PlayerEntity2)) ? Friends.get().getFriendColor(PlayerEntity2) : (Color)this.hud.primaryColor.get();
            hudRenderer.text(string, d, d2 += hudRenderer.textHeight() + 2.0, color);
            if (!this.distance.get().booleanValue()) continue;
            d += hudRenderer.textWidth(String.valueOf(new StringBuilder().append(string).append(" ")));
            string = String.format("(%sm)", Math.round(this.mc.getCameraEntity().distanceTo((Entity)PlayerEntity2)));
            color = this.hud.secondaryColor.get();
            hudRenderer.text(string, d, d2, color);
        }
    }

    @Override
    public void update(HudRenderer hudRenderer) {
        double d = hudRenderer.textWidth("Players:");
        double d2 = hudRenderer.textHeight();
        if (this.mc.world == null) {
            this.box.setSize(d, d2);
            return;
        }
        for (PlayerEntity PlayerEntity2 : this.getPlayers()) {
            if (PlayerEntity2.equals((Object)this.mc.player) || !this.friends.get().booleanValue() && Friends.get().contains(Friends.get().get(PlayerEntity2))) continue;
            String string = PlayerEntity2.getGameProfile().getName();
            if (this.distance.get().booleanValue()) {
                string = String.valueOf(new StringBuilder().append(string).append(String.format("(%sm)", Math.round(this.mc.getCameraEntity().distanceTo((Entity)PlayerEntity2)))));
            }
            d = Math.max(d, hudRenderer.textWidth(string));
            d2 += hudRenderer.textHeight() + 2.0;
        }
        this.box.setSize(d, d2);
    }
}

