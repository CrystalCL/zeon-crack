/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.util.math.Box
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.rendering.MeshBuilder;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Box;

public class ESP
extends Module {
    private final /* synthetic */ Setting<SettingColor> playersColor;
    public final /* synthetic */ Setting<Boolean> useNameColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ SettingGroup sgColors;
    private final /* synthetic */ Setting<SettingColor> miscColor;
    private /* synthetic */ int count;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<SettingColor> ambientColor;
    private static final /* synthetic */ MeshBuilder MB;
    private final /* synthetic */ Setting<Double> fadeDistance;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Color outlineColor;
    private final /* synthetic */ Setting<SettingColor> monstersColor;
    private final /* synthetic */ Color sideColor;
    public final /* synthetic */ Setting<Boolean> showInvis;
    private final /* synthetic */ Setting<SettingColor> waterAnimalsColor;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ Setting<SettingColor> animalsColor;

    @Override
    public String getInfoString() {
        ESP lIIIIllIlllllI;
        return Integer.toString(lIIIIllIlllllI.count);
    }

    @EventHandler
    private void onRender(RenderEvent lIIIIlllIIIlII) {
        ESP lIIIIlllIIIIll;
        lIIIIlllIIIIll.count = 0;
        for (Entity lIIIIlllIIIllI : lIIIIlllIIIIll.mc.world.getEntities()) {
            if (!Modules.get().isActive(Freecam.class) && lIIIIlllIIIllI == lIIIIlllIIIIll.mc.player || !lIIIIlllIIIIll.entities.get().getBoolean((Object)lIIIIlllIIIllI.getType()) || !EntityUtils.isInRenderDistance(lIIIIlllIIIllI)) continue;
            if (lIIIIlllIIIIll.mode.get() == Mode.Box) {
                lIIIIlllIIIIll.render(lIIIIlllIIIlII, lIIIIlllIIIllI, lIIIIlllIIIIll.getColor(lIIIIlllIIIllI));
            }
            ++lIIIIlllIIIIll.count;
        }
    }

    public boolean isOutline() {
        ESP lIIIIllIlIIllI;
        return lIIIIllIlIIllI.mode.get() == Mode.Outline;
    }

    public Color getOutlineColor(Entity lIIIIllIllIIIl) {
        ESP lIIIIllIllIIlI;
        if (!lIIIIllIllIIlI.entities.get().getBoolean((Object)lIIIIllIllIIIl.getType())) {
            return null;
        }
        Color lIIIIllIllIlIl = lIIIIllIllIIlI.getColor(lIIIIllIllIIIl);
        double lIIIIllIllIlII = lIIIIllIllIIlI.mc.cameraEntity.squaredDistanceTo(lIIIIllIllIIIl.getX() + (double)(lIIIIllIllIIIl.getWidth() / 2.0f), lIIIIllIllIIIl.getY() + (double)(lIIIIllIllIIIl.getHeight() / 2.0f), lIIIIllIllIIIl.getZ() + (double)(lIIIIllIllIIIl.getWidth() / 2.0f));
        double lIIIIllIllIIll = 1.0;
        if (lIIIIllIllIlII <= lIIIIllIllIIlI.fadeDistance.get() * lIIIIllIllIIlI.fadeDistance.get()) {
            lIIIIllIllIIll = lIIIIllIllIlII / (lIIIIllIllIIlI.fadeDistance.get() * lIIIIllIllIIlI.fadeDistance.get());
        }
        if (lIIIIllIllIIll >= 0.075) {
            lIIIIllIllIIlI.outlineColor.set(lIIIIllIllIlIl);
            lIIIIllIllIIlI.outlineColor.a = (int)((double)lIIIIllIllIIlI.outlineColor.a * lIIIIllIllIIll);
            return lIIIIllIllIIlI.outlineColor;
        }
        return null;
    }

    private void render(RenderEvent lIIIIlllIlllIl, Entity lIIIIlllIlllII, Color lIIIIlllIlIIlI) {
        ESP lIIIIlllIlIllI;
        lIIIIlllIlIllI.setSideColor(lIIIIlllIlIIlI);
        double lIIIIlllIllIlI = lIIIIlllIlIllI.mc.cameraEntity.squaredDistanceTo(lIIIIlllIlllII.getX() + (double)(lIIIIlllIlllII.getWidth() / 2.0f), lIIIIlllIlllII.getY() + (double)(lIIIIlllIlllII.getHeight() / 2.0f), lIIIIlllIlllII.getZ() + (double)(lIIIIlllIlllII.getWidth() / 2.0f));
        double lIIIIlllIllIIl = 1.0;
        if (lIIIIlllIllIlI <= lIIIIlllIlIllI.fadeDistance.get() * lIIIIlllIlIllI.fadeDistance.get()) {
            lIIIIlllIllIIl = lIIIIlllIllIlI / (lIIIIlllIlIllI.fadeDistance.get() * lIIIIlllIlIllI.fadeDistance.get());
        }
        int lIIIIlllIllIII = lIIIIlllIlIIlI.a;
        int lIIIIlllIlIlll = lIIIIlllIlIllI.sideColor.a;
        lIIIIlllIlIIlI.a = (int)((double)lIIIIlllIlIIlI.a * lIIIIlllIllIIl);
        lIIIIlllIlIllI.sideColor.a = (int)((double)lIIIIlllIlIllI.sideColor.a * lIIIIlllIllIIl);
        if (lIIIIlllIllIIl >= 0.075) {
            double lIIIIllllIIIlI = (lIIIIlllIlllII.getX() - lIIIIlllIlllII.prevX) * (double)lIIIIlllIlllIl.tickDelta;
            double lIIIIllllIIIIl = (lIIIIlllIlllII.getY() - lIIIIlllIlllII.prevY) * (double)lIIIIlllIlllIl.tickDelta;
            double lIIIIllllIIIII = (lIIIIlllIlllII.getZ() - lIIIIlllIlllII.prevZ) * (double)lIIIIlllIlllIl.tickDelta;
            Box lIIIIlllIlllll = lIIIIlllIlllII.getBoundingBox();
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lIIIIllllIIIlI + lIIIIlllIlllll.minX, lIIIIllllIIIIl + lIIIIlllIlllll.minY, lIIIIllllIIIII + lIIIIlllIlllll.minZ, lIIIIllllIIIlI + lIIIIlllIlllll.maxX, lIIIIllllIIIIl + lIIIIlllIlllll.maxY, lIIIIllllIIIII + lIIIIlllIlllll.maxZ, lIIIIlllIlIllI.sideColor, lIIIIlllIlIIlI, lIIIIlllIlIllI.shapeMode.get(), 0);
        }
        lIIIIlllIlIIlI.a = lIIIIlllIllIII;
        lIIIIlllIlIllI.sideColor.a = lIIIIlllIlIlll;
    }

    public Color getColor(Entity lIIIIllIlIlIlI) {
        ESP lIIIIllIlIlIll;
        return EntityUtils.getEntityColor(lIIIIllIlIlIlI, lIIIIllIlIlIll.playersColor.get(), lIIIIllIlIlIll.animalsColor.get(), lIIIIllIlIlIll.waterAnimalsColor.get(), lIIIIllIlIlIll.monstersColor.get(), lIIIIllIlIlIll.ambientColor.get(), lIIIIllIlIlIll.miscColor.get(), lIIIIllIlIlIll.useNameColor.get());
    }

    public ESP() {
        super(Categories.Render, "esp", "Renders entities through walls.");
        ESP lIIIIlllllIllI;
        lIIIIlllllIllI.sgGeneral = lIIIIlllllIllI.settings.getDefaultGroup();
        lIIIIlllllIllI.sgColors = lIIIIlllllIllI.settings.createGroup("Colors");
        lIIIIlllllIllI.mode = lIIIIlllllIllI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Rendering mode.").defaultValue(Mode.Outline).build());
        lIIIIlllllIllI.shapeMode = lIIIIlllllIllI.sgGeneral.add(new EnumSetting.Builder().name("box-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lIIIIlllllIllI.entities = lIIIIlllllIllI.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).build());
        lIIIIlllllIllI.showInvis = lIIIIlllllIllI.sgGeneral.add(new BoolSetting.Builder().name("show-invisible").description("Shows invisibile entities.").defaultValue(true).build());
        lIIIIlllllIllI.useNameColor = lIIIIlllllIllI.sgColors.add(new BoolSetting.Builder().name("use-name-color").description("Uses players displayname color for the ESP color (good for minigames).").defaultValue(false).build());
        lIIIIlllllIllI.playersColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("players-color").description("The other player's color.").defaultValue(new SettingColor(255, 255, 255)).build());
        lIIIIlllllIllI.animalsColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("animals-color").description("The animal's color.").defaultValue(new SettingColor(25, 255, 25, 255)).build());
        lIIIIlllllIllI.waterAnimalsColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("water-animals-color").description("The water animal's color.").defaultValue(new SettingColor(25, 25, 255, 255)).build());
        lIIIIlllllIllI.monstersColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("monsters-color").description("The monster's color.").defaultValue(new SettingColor(255, 25, 25, 255)).build());
        lIIIIlllllIllI.ambientColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("ambient-color").description("The ambient's color.").defaultValue(new SettingColor(25, 25, 25, 255)).build());
        lIIIIlllllIllI.miscColor = lIIIIlllllIllI.sgColors.add(new ColorSetting.Builder().name("misc-color").description("The misc color.").defaultValue(new SettingColor(175, 175, 175, 255)).build());
        lIIIIlllllIllI.fadeDistance = lIIIIlllllIllI.sgGeneral.add(new DoubleSetting.Builder().name("fade-distance").description("The distance where the color fades.").defaultValue(6.0).min(0.0).sliderMax(12.0).build());
        lIIIIlllllIllI.sideColor = new Color();
        lIIIIlllllIllI.outlineColor = new Color();
        ESP.MB.texture = true;
    }

    private void setSideColor(Color lIIIIllllIllll) {
        ESP lIIIIlllllIIlI;
        lIIIIlllllIIlI.sideColor.set(lIIIIllllIllll);
        lIIIIlllllIIlI.sideColor.a = 25;
    }

    static {
        MB = new MeshBuilder(128);
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Box;
        public static final /* synthetic */ /* enum */ Mode Outline;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Box, Outline};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private Mode() {
            Mode llIlllIlIIIIlIl;
        }

        static {
            Box = new Mode();
            Outline = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String llIlllIlIIIlIlI) {
            return Enum.valueOf(Mode.class, llIlllIlIIIlIlI);
        }
    }
}

