/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.misc.FakeClientPlayer;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1748;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1829;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_2378;
import net.minecraft.class_290;
import net.minecraft.class_3532;
import net.minecraft.class_490;

public class CombatHud
extends HudElement {
    private class_1657 playerEntity;
    private static final Color BLACK;
    private final Setting<Boolean> displayPing;
    private final Setting<SettingColor> distColor3;
    private static final Color RED;
    private final Setting<SettingColor> enchantmentTextColor;
    private final Setting<SettingColor> healthColor1;
    private final Setting<SettingColor> backgroundColor;
    private final Setting<SettingColor> pingColor3;
    private final Setting<Double> range;
    private final Setting<SettingColor> healthColor3;
    private final Setting<SettingColor> pingColor1;
    private final Setting<SettingColor> distColor2;
    private final Setting<List<class_1887>> displayedEnchantments;
    private final Setting<SettingColor> distColor1;
    private final Setting<SettingColor> healthColor2;
    private final Setting<Double> scale;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> pingColor2;
    private final Setting<Boolean> displayDistance;
    private static final Color GREEN;
    private final Setting<Boolean> ignoreFriends;

    static {
        GREEN = new Color(15, 255, 15);
        RED = new Color(255, 15, 15);
        BLACK = new Color(0, 0, 0, 255);
    }

    public CombatHud(HUD hUD) {
        super(hUD, "combat-info", "Displays information about your combat target.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of combat info.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The range to target players.").defaultValue(100.0).min(1.0).sliderMax(200.0).build());
        this.ignoreFriends = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends when targeting.").defaultValue(false).build());
        this.displayPing = this.sgGeneral.add(new BoolSetting.Builder().name("ping").description("Shows the player's ping.").defaultValue(true).build());
        this.displayDistance = this.sgGeneral.add(new BoolSetting.Builder().name("distance").description("Shows the distance between you and the player.").defaultValue(true).build());
        this.displayedEnchantments = this.sgGeneral.add(new EnchListSetting.Builder().name("displayed-enchantments").description("The enchantments that are shown on nametags.").defaultValue(CombatHud.getDefaultEnchantments()).build());
        this.backgroundColor = this.sgGeneral.add(new ColorSetting.Builder().name("background-color").description("Color of background.").defaultValue(new SettingColor(0, 0, 0, 64)).build());
        this.enchantmentTextColor = this.sgGeneral.add(new ColorSetting.Builder().name("enchantment-color").description("Color of enchantment text.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.pingColor1 = this.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-1").description("Color of ping text when under 75.").defaultValue(new SettingColor(15, 255, 15)).build());
        this.pingColor2 = this.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-2").description("Color of ping text when between 75 and 200.").defaultValue(new SettingColor(255, 150, 15)).build());
        this.pingColor3 = this.sgGeneral.add(new ColorSetting.Builder().name("ping-stage-3").description("Color of ping text when over 200.").defaultValue(new SettingColor(255, 15, 15)).build());
        this.distColor1 = this.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-1").description("The color when a player is within 10 blocks of you.").defaultValue(new SettingColor(255, 15, 15)).build());
        this.distColor2 = this.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-2").description("The color when a player is within 50 blocks of you.").defaultValue(new SettingColor(255, 150, 15)).build());
        this.distColor3 = this.sgGeneral.add(new ColorSetting.Builder().name("distance-stage-3").description("The color when a player is greater then 50 blocks away from you.").defaultValue(new SettingColor(15, 255, 15)).build());
        this.healthColor1 = this.sgGeneral.add(new ColorSetting.Builder().name("healh-stage-1").description("The color on the left of the health gradient.").defaultValue(new SettingColor(255, 15, 15)).build());
        this.healthColor2 = this.sgGeneral.add(new ColorSetting.Builder().name("health-stage-2").description("The color in the middle of the health gradient.").defaultValue(new SettingColor(255, 150, 15)).build());
        this.healthColor3 = this.sgGeneral.add(new ColorSetting.Builder().name("health-stage-3").description("The color on the right of the health gradient.").defaultValue(new SettingColor(15, 255, 15)).build());
    }

    @Override
    public void update(HudRenderer hudRenderer) {
        this.box.setSize(175.0 * this.scale.get(), 95.0 * this.scale.get());
    }

    public static List<class_1887> getDefaultEnchantments() {
        ArrayList<class_1887> arrayList = new ArrayList<class_1887>();
        for (class_1887 class_18872 : class_2378.field_11160) {
            arrayList.add(class_18872);
        }
        return arrayList;
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        hudRenderer.addPostTask(this::lambda$render$0);
    }

    private void lambda$render$0() {
        double d = this.box.getX();
        double d2 = this.box.getY();
        this.playerEntity = this.isInEditor() ? FakeClientPlayer.getPlayer() : EntityUtils.getPlayerTarget(this.range.get(), SortPriority.LowestDistance, this.ignoreFriends.get());
        if (this.playerEntity == null) {
            return;
        }
        Renderer.NORMAL.begin(null, DrawMode.Triangles, class_290.field_1576);
        Renderer.NORMAL.quad(d, d2, this.box.width, this.box.height, this.backgroundColor.get());
        Renderer.NORMAL.end();
        class_490.method_2486((int)((int)(d + 25.0 * this.scale.get())), (int)((int)(d2 + 66.0 * this.scale.get())), (int)((int)(30.0 * this.scale.get())), (float)(-class_3532.method_15393((float)(this.playerEntity.field_5982 + (this.playerEntity.field_6031 - this.playerEntity.field_5982) * this.mc.method_1488()))), (float)(-this.playerEntity.field_5965), (class_1309)this.playerEntity);
        d += 50.0 * this.scale.get();
        d2 += 5.0 * this.scale.get();
        String string = " | ";
        String string2 = this.playerEntity.method_7334().getName();
        Color color = Friends.get().getFriendColor(this.playerEntity);
        int n = EntityUtils.getPing(this.playerEntity);
        String string3 = String.valueOf(new StringBuilder().append(n).append("ms"));
        Color color2 = n <= 75 ? (Color)this.pingColor1.get() : (n <= 200 ? (Color)this.pingColor2.get() : (Color)this.pingColor3.get());
        double d3 = 0.0;
        if (!this.isInEditor()) {
            d3 = (double)Math.round((double)this.mc.field_1724.method_5739((class_1297)this.playerEntity) * 100.0) / 100.0;
        }
        String string4 = String.valueOf(new StringBuilder().append(d3).append("m"));
        Color color3 = d3 <= 10.0 ? (Color)this.distColor1.get() : (d3 <= 50.0 ? (Color)this.distColor2.get() : (Color)this.distColor3.get());
        String string5 = "Unknown";
        Color color4 = this.hud.primaryColor.get();
        if (Friends.get().get(this.playerEntity) != null) {
            Friend friend = Friends.get().get(this.playerEntity);
            string5 = friend.type.name();
            color4 = Friends.get().getFriendColor(this.playerEntity);
        } else {
            int n2;
            boolean bl = true;
            for (n2 = 3; n2 >= 0; --n2) {
                class_1799 class_17992 = this.getItem(n2);
                if (class_17992.method_7960()) continue;
                bl = false;
                if (null == null) continue;
                return;
            }
            if (bl) {
                string5 = "Naked";
                color4 = GREEN;
            } else {
                n2 = 0;
                for (int i = 5; i >= 0; --i) {
                    class_1799 class_17993 = this.getItem(i);
                    if (class_17993.method_7909() != class_1802.field_8301 && !(class_17993.method_7909() instanceof class_1829) && class_17993.method_7909() != class_1802.field_23141 && !(class_17993.method_7909() instanceof class_1748)) continue;
                    n2 = 1;
                    if (null == null) continue;
                    return;
                }
                if (n2 != 0) {
                    string5 = "Threat";
                    color4 = RED;
                }
            }
        }
        TextRenderer.get().begin(0.45 * this.scale.get(), false, true);
        double d4 = TextRenderer.get().getWidth(string);
        double d5 = TextRenderer.get().getWidth(string3);
        double d6 = TextRenderer.get().getWidth(string5);
        TextRenderer.get().render(string2, d, d2, color != null ? color : (Color)this.hud.primaryColor.get());
        TextRenderer.get().render(string5, d, d2 += TextRenderer.get().getHeight(), color4);
        if (this.displayPing.get().booleanValue()) {
            TextRenderer.get().render(string, d + d6, d2, this.hud.secondaryColor.get());
            TextRenderer.get().render(string3, d + d6 + d4, d2, color2);
            if (this.displayDistance.get().booleanValue()) {
                TextRenderer.get().render(string, d + d6 + d4 + d5, d2, this.hud.secondaryColor.get());
                TextRenderer.get().render(string4, d + d6 + d4 + d5 + d4, d2, color3);
            }
        } else if (this.displayDistance.get().booleanValue()) {
            TextRenderer.get().render(string, d + d6, d2, this.hud.secondaryColor.get());
            TextRenderer.get().render(string4, d + d6 + d4, d2, color3);
        }
        TextRenderer.get().end();
        d2 += 10.0 * this.scale.get();
        int n3 = 5;
        RenderSystem.pushMatrix();
        RenderSystem.scaled((double)this.scale.get(), (double)this.scale.get(), (double)1.0);
        d /= this.scale.get().doubleValue();
        d2 /= this.scale.get().doubleValue();
        TextRenderer.get().begin(0.35, false, true);
        for (int i = 0; i < 6; ++i) {
            double d7 = d + (double)(i * 20);
            double d8 = d2;
            class_1799 class_17994 = this.getItem(n3);
            RenderUtils.drawItem(class_17994, (int)d7, (int)d8, true);
            d8 += 18.0;
            Map map = class_1890.method_8222((class_1799)class_17994);
            HashMap<class_1887, Integer> hashMap = new HashMap<class_1887, Integer>();
            for (class_1887 class_18872 : this.displayedEnchantments.get()) {
                if (!map.containsKey(class_18872)) continue;
                hashMap.put(class_18872, (Integer)map.get(class_18872));
            }
            for (class_1887 class_18872 : hashMap.keySet()) {
                String string6 = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(class_18872, 3)).append(" ").append(hashMap.get(class_18872)));
                double d9 = d7 + 8.0 - TextRenderer.get().getWidth(string6) / 2.0;
                TextRenderer.get().render(string6, d9, d8, class_18872.method_8195() ? RED : (Color)this.enchantmentTextColor.get());
                d8 += TextRenderer.get().getHeight();
            }
            --n3;
        }
        TextRenderer.get().end();
        RenderSystem.popMatrix();
        d2 = (int)(this.box.getY() + 75.0 * this.scale.get());
        d = this.box.getX();
        RenderSystem.pushMatrix();
        RenderSystem.scaled((double)this.scale.get(), (double)this.scale.get(), (double)1.0);
        d /= this.scale.get().doubleValue();
        d2 /= this.scale.get().doubleValue();
        Renderer.LINES.begin(null, DrawMode.Lines, class_290.field_1576);
        Renderer.LINES.boxEdges(d += 5.0, d2 += 5.0, 165.0, 11.0, BLACK);
        Renderer.LINES.end();
        float f = this.playerEntity.method_6063();
        int n4 = 16;
        int n5 = (int)(f + (float)n4);
        int n6 = (int)(161.0f * f / (float)n5);
        int n7 = 161 * n4 / n5;
        float f2 = this.playerEntity.method_6032();
        float f3 = this.playerEntity.method_6067();
        float f4 = f2 + f3;
        double d10 = f2 / f;
        double d11 = f3 / (float)n4;
        int n8 = (int)((double)n6 * d10);
        int n9 = (int)((double)n7 * d11);
        Renderer.NORMAL.begin(null, DrawMode.Triangles, class_290.field_1576);
        Renderer.NORMAL.gradientQuad(d += 2.0, d2 += 2.0, n8, 7.0, this.healthColor1.get(), this.healthColor2.get());
        Renderer.NORMAL.gradientQuad(d + (double)n8, d2, n9, 7.0, this.healthColor2.get(), this.healthColor3.get());
        Renderer.NORMAL.end();
        String string7 = String.valueOf((double)Math.round((double)f4 * 10.0) / 10.0);
        TextRenderer.get().begin(0.45);
        TextRenderer.get().render(string7, d, d2, this.hud.primaryColor.get());
        TextRenderer.get().end();
        RenderSystem.popMatrix();
    }

    private class_1799 getItem(int n) {
        if (this.isInEditor()) {
            switch (n) {
                case 0: {
                    return class_1802.field_8301.method_7854();
                }
                case 1: {
                    return class_1802.field_22030.method_7854();
                }
                case 2: {
                    return class_1802.field_22029.method_7854();
                }
                case 3: {
                    return class_1802.field_22028.method_7854();
                }
                case 4: {
                    return class_1802.field_22027.method_7854();
                }
                case 5: {
                    return class_1802.field_8288.method_7854();
                }
            }
        }
        if (this.playerEntity == null) {
            return class_1799.field_8037;
        }
        if (n == 5) {
            return this.playerEntity.method_6047();
        }
        if (n == 4) {
            return this.playerEntity.method_6079();
        }
        return this.playerEntity.field_7514.method_7372(n);
    }
}

