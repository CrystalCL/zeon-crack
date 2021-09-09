/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
 *  net.minecraft.client.network.ClientPlayerEntity
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.misc.FakeClientPlayer;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;

public class PlayerModelHud
extends HudElement {
    private final /* synthetic */ Setting<Boolean> background;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> copyPitch;
    private final /* synthetic */ Setting<Integer> customYaw;
    private final /* synthetic */ Setting<Boolean> copyYaw;
    private final /* synthetic */ Setting<SettingColor> backgroundColor;
    private final /* synthetic */ Setting<Integer> customPitch;
    private final /* synthetic */ Setting<Double> scale;

    public PlayerModelHud(HUD lIIlIlllIIlllll) {
        super(lIIlIlllIIlllll, "player-model", "Displays a model of your player.");
        PlayerModelHud lIIlIlllIIllllI;
        lIIlIlllIIllllI.sgGeneral = lIIlIlllIIllllI.settings.getDefaultGroup();
        lIIlIlllIIllllI.scale = lIIlIlllIIllllI.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of player model.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
        lIIlIlllIIllllI.copyYaw = lIIlIlllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("copy-yaw").description("Makes the player model's yaw equal to yours.").defaultValue(true).build());
        lIIlIlllIIllllI.copyPitch = lIIlIlllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("copy-pitch").description("Makes the player model's pitch equal to yours.").defaultValue(true).build());
        lIIlIlllIIllllI.customYaw = lIIlIlllIIllllI.sgGeneral.add(new IntSetting.Builder().name("custom-yaw").description("Custom yaw for when copy yaw is off.").defaultValue(0).min(-180).max(180).sliderMin(-180).sliderMax(180).build());
        lIIlIlllIIllllI.customPitch = lIIlIlllIIllllI.sgGeneral.add(new IntSetting.Builder().name("custom-pitch").description("Custom pitch for when copy pitch is off.").defaultValue(0).min(-180).max(180).sliderMin(-180).sliderMax(180).build());
        lIIlIlllIIllllI.background = lIIlIlllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("background").description("Displays a background behind the player model.").defaultValue(true).build());
        lIIlIlllIIllllI.backgroundColor = lIIlIlllIIllllI.sgGeneral.add(new ColorSetting.Builder().name("background-color").description("Color of background.").defaultValue(new SettingColor(0, 0, 0, 64)).build());
    }

    @Override
    public void update(HudRenderer lIIlIlllIIllIlI) {
        PlayerModelHud lIIlIlllIIllIll;
        lIIlIlllIIllIll.box.setSize(50.0 * lIIlIlllIIllIll.scale.get(), 75.0 * lIIlIlllIIllIll.scale.get());
    }

    @Override
    public void render(HudRenderer lIIlIlllIIlIIIl) {
        PlayerModelHud lIIlIlllIIIlIll;
        double lIIlIlllIIlIIII = lIIlIlllIIIlIll.box.getX();
        double lIIlIlllIIIllll = lIIlIlllIIIlIll.box.getY();
        if (lIIlIlllIIIlIll.background.get().booleanValue()) {
            Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            Renderer.NORMAL.quad(lIIlIlllIIlIIII, lIIlIlllIIIllll, lIIlIlllIIIlIll.box.width, lIIlIlllIIIlIll.box.height, lIIlIlllIIIlIll.backgroundColor.get());
            Renderer.NORMAL.end();
        }
        ClientPlayerEntity lIIlIlllIIIlllI = lIIlIlllIIIlIll.mc.player;
        if (lIIlIlllIIIlIll.isInEditor()) {
            lIIlIlllIIIlllI = FakeClientPlayer.getPlayer();
        }
        float lIIlIlllIIIllIl = lIIlIlllIIIlIll.copyYaw.get() != false ? MathHelper.wrapDegrees((float)(lIIlIlllIIIlllI.prevYaw + (lIIlIlllIIIlllI.yaw - lIIlIlllIIIlllI.prevYaw) * lIIlIlllIIIlIll.mc.getTickDelta())) : (float)lIIlIlllIIIlIll.customYaw.get().intValue();
        float lIIlIlllIIIllII = lIIlIlllIIIlIll.copyPitch.get() != false ? lIIlIlllIIIlllI.pitch : (float)lIIlIlllIIIlIll.customPitch.get().intValue();
        InventoryScreen.drawEntity((int)((int)(lIIlIlllIIlIIII + 25.0 * lIIlIlllIIIlIll.scale.get())), (int)((int)(lIIlIlllIIIllll + 66.0 * lIIlIlllIIIlIll.scale.get())), (int)((int)(30.0 * lIIlIlllIIIlIll.scale.get())), (float)(-lIIlIlllIIIllIl), (float)(-lIIlIlllIIIllII), (LivingEntity)lIIlIlllIIIlllI);
    }
}

