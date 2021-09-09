/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.player.PlayerEntity
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class Tracers
extends Module {
    private final /* synthetic */ Setting<Boolean> stem;
    private final /* synthetic */ Setting<SettingColor> playersColor;
    private final /* synthetic */ Setting<SettingColor> monstersColor;
    public final /* synthetic */ Setting<Boolean> showInvis;
    private final /* synthetic */ Setting<SettingColor> waterAnimalsColor;
    private final /* synthetic */ SettingGroup sgAppearance;
    private final /* synthetic */ Setting<SettingColor> ambientColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ int count;
    public final /* synthetic */ Setting<Boolean> distance;
    public final /* synthetic */ Setting<Boolean> useNameColor;
    private final /* synthetic */ Setting<Target> target;
    private final /* synthetic */ Setting<Integer> maxDist;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ Setting<SettingColor> miscColor;
    private final /* synthetic */ SettingGroup sgColors;
    private final /* synthetic */ Setting<SettingColor> animalsColor;
    private final /* synthetic */ Color distanceColor;

    public Tracers() {
        super(Categories.Render, "tracers", "Displays tracer lines to specified entities.");
        Tracers lllllIlIIlIIlIl;
        lllllIlIIlIIlIl.sgGeneral = lllllIlIIlIIlIl.settings.getDefaultGroup();
        lllllIlIIlIIlIl.sgAppearance = lllllIlIIlIIlIl.settings.createGroup("Appearance");
        lllllIlIIlIIlIl.sgColors = lllllIlIIlIIlIl.settings.createGroup("Colors");
        lllllIlIIlIIlIl.entities = lllllIlIIlIIlIl.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).build());
        lllllIlIIlIIlIl.target = lllllIlIIlIIlIl.sgAppearance.add(new EnumSetting.Builder().name("target").description("What part of the entity to target.").defaultValue(Target.Body).build());
        lllllIlIIlIIlIl.stem = lllllIlIIlIIlIl.sgAppearance.add(new BoolSetting.Builder().name("stem").description("Draw a line through the center of the tracer target.").defaultValue(true).build());
        lllllIlIIlIIlIl.maxDist = lllllIlIIlIIlIl.sgAppearance.add(new IntSetting.Builder().name("max-distance").description("Maximum distance for tracers to show.").defaultValue(256).min(0).sliderMax(256).build());
        lllllIlIIlIIlIl.showInvis = lllllIlIIlIIlIl.sgGeneral.add(new BoolSetting.Builder().name("show-invisible").description("Shows invisibile entities.").defaultValue(true).build());
        lllllIlIIlIIlIl.distance = lllllIlIIlIIlIl.sgColors.add(new BoolSetting.Builder().name("distance-colors").description("Changes the color of tracers depending on distance.").defaultValue(false).build());
        lllllIlIIlIIlIl.useNameColor = lllllIlIIlIIlIl.sgColors.add(new BoolSetting.Builder().name("use-name-color").description("Uses players displayname color for the tracer color (good for minigames).").defaultValue(false).build());
        lllllIlIIlIIlIl.playersColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("players-colors").description("The player's color.").defaultValue(new SettingColor(205, 205, 205, 127)).build());
        lllllIlIIlIIlIl.animalsColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("animals-color").description("The animal's color.").defaultValue(new SettingColor(145, 255, 145, 127)).build());
        lllllIlIIlIIlIl.waterAnimalsColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("water-animals-color").description("The water animal's color.").defaultValue(new SettingColor(145, 145, 255, 127)).build());
        lllllIlIIlIIlIl.monstersColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("monsters-color").description("The monster's color.").defaultValue(new SettingColor(255, 145, 145, 127)).build());
        lllllIlIIlIIlIl.ambientColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("ambient-color").description("The ambient color.").defaultValue(new SettingColor(75, 75, 75, 127)).build());
        lllllIlIIlIIlIl.miscColor = lllllIlIIlIIlIl.sgColors.add(new ColorSetting.Builder().name("misc-color").description("The misc color.").defaultValue(new SettingColor(145, 145, 145, 127)).build());
        lllllIlIIlIIlIl.distanceColor = new Color(255, 255, 255);
    }

    private Color getColorFromDistance(PlayerEntity lllllIlIIIIlIll) {
        int lllllIlIIIIlIII;
        int lllllIlIIIIIlll;
        Tracers lllllIlIIIIIllI;
        double lllllIlIIIIlIlI = lllllIlIIIIIllI.mc.player.distanceTo((Entity)lllllIlIIIIlIll);
        double lllllIlIIIIlIIl = lllllIlIIIIlIlI / 60.0;
        if (lllllIlIIIIlIIl < 0.0 || lllllIlIIIIlIIl > 1.0) {
            lllllIlIIIIIllI.distanceColor.set(0, 255, 0, 255);
            return lllllIlIIIIIllI.distanceColor;
        }
        if (lllllIlIIIIlIIl < 0.5) {
            int lllllIlIIIIlllI = 255;
            int lllllIlIIIIllIl = (int)(255.0 * lllllIlIIIIlIIl / 0.5);
        } else {
            lllllIlIIIIIlll = 255;
            lllllIlIIIIlIII = 255 - (int)(255.0 * (lllllIlIIIIlIIl - 0.5) / 0.5);
        }
        lllllIlIIIIIllI.distanceColor.set(lllllIlIIIIlIII, lllllIlIIIIIlll, 0, 255);
        return lllllIlIIIIIllI.distanceColor;
    }

    @EventHandler
    private void onRender(RenderEvent lllllIlIIIllIlI) {
        Tracers lllllIlIIIllIll;
        lllllIlIIIllIll.count = 0;
        for (Entity lllllIlIIIlllII : lllllIlIIIllIll.mc.world.getEntities()) {
            Color lllllIlIIIlllIl;
            if (lllllIlIIIllIll.mc.player.distanceTo(lllllIlIIIlllII) > (float)lllllIlIIIllIll.maxDist.get().intValue() || lllllIlIIIlllII instanceof PlayerEntity && Friends.get().get((PlayerEntity)lllllIlIIIlllII) != null && !Friends.get().show((PlayerEntity)lllllIlIIIlllII) || !Modules.get().isActive(Freecam.class) && lllllIlIIIlllII == lllllIlIIIllIll.mc.player || !lllllIlIIIllIll.entities.get().getBoolean((Object)lllllIlIIIlllII.getType()) || !lllllIlIIIllIll.showInvis.get().booleanValue() && lllllIlIIIlllII.isInvisible() || !EntityUtils.isInRenderDistance(lllllIlIIIlllII)) continue;
            if (!lllllIlIIIllIll.distance.get().booleanValue() || !(lllllIlIIIlllII instanceof PlayerEntity) || Friends.get().contains(Friends.get().get((PlayerEntity)lllllIlIIIlllII))) {
                Color lllllIlIIIllllI = EntityUtils.getEntityColor(lllllIlIIIlllII, lllllIlIIIllIll.playersColor.get(), lllllIlIIIllIll.animalsColor.get(), lllllIlIIIllIll.waterAnimalsColor.get(), lllllIlIIIllIll.monstersColor.get(), lllllIlIIIllIll.ambientColor.get(), lllllIlIIIllIll.miscColor.get(), lllllIlIIIllIll.useNameColor.get());
            } else {
                lllllIlIIIlllIl = lllllIlIIIllIll.getColorFromDistance((PlayerEntity)lllllIlIIIlllII);
            }
            RenderUtils.drawTracerToEntity(lllllIlIIIllIlI, lllllIlIIIlllII, lllllIlIIIlllIl, lllllIlIIIllIll.target.get(), lllllIlIIIllIll.stem.get());
            ++lllllIlIIIllIll.count;
        }
    }

    @Override
    public String getInfoString() {
        Tracers lllllIIllllllll;
        return Integer.toString(lllllIIllllllll.count);
    }
}

