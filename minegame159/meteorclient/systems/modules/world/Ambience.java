/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.render.SkyProperties
 *  net.minecraft.client.render.SkyProperties.SkyType
 */
package minegame159.meteorclient.systems.modules.world;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.SkyProperties;

public class Ambience
extends Module {
    public final /* synthetic */ Setting<SettingColor> foliageColor;
    public final /* synthetic */ Setting<SettingColor> cloudColor;
    public final /* synthetic */ Setting<SettingColor> grassColor;
    public final /* synthetic */ Setting<Boolean> changeLavaColor;
    public final /* synthetic */ Setting<SettingColor> skyColor;
    public final /* synthetic */ Setting<SettingColor> waterColor;
    public final /* synthetic */ Setting<Boolean> changeLightningColor;
    public final /* synthetic */ Setting<SettingColor> lightningColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    public final /* synthetic */ Setting<Boolean> changeGrassColor;
    public final /* synthetic */ Setting<SettingColor> endSkyColor;
    public final /* synthetic */ Setting<SettingColor> lavaColor;
    public final /* synthetic */ Setting<Boolean> enderCustomSkyColor;
    private final /* synthetic */ SettingGroup sgDynamic;
    public final /* synthetic */ Setting<Boolean> changeWaterColor;
    public final /* synthetic */ Setting<Boolean> changeCloudColor;
    public final /* synthetic */ Setting<Boolean> changeSkyColor;
    private final /* synthetic */ SettingGroup sgStatic;
    public final /* synthetic */ Setting<Boolean> enderMode;
    public final /* synthetic */ Setting<Boolean> changeFoliageColor;

    @Override
    public void onActivate() {
        Ambience lllllllllllllllllIlIlIlIllIlIlll;
        if (lllllllllllllllllIlIlIlIllIlIlll.mc.worldRenderer != null) {
            lllllllllllllllllIlIlIlIllIlIlll.mc.worldRenderer.reload();
        }
    }

    @Override
    public WWidget getWidget(GuiTheme lllllllllllllllllIlIlIlIllIIlIlI) {
        Ambience lllllllllllllllllIlIlIlIllIIllll;
        WHorizontalList lllllllllllllllllIlIlIlIllIIllIl = lllllllllllllllllIlIlIlIllIIlIlI.horizontalList();
        WButton lllllllllllllllllIlIlIlIllIIllII = lllllllllllllllllIlIlIlIllIIllIl.add(lllllllllllllllllIlIlIlIllIIlIlI.button("Reload World")).expandX().widget();
        lllllllllllllllllIlIlIlIllIIllII.action = () -> {
            Ambience lllllllllllllllllIlIlIlIllIIIlIl;
            if (lllllllllllllllllIlIlIlIllIIIlIl.mc.worldRenderer != null) {
                lllllllllllllllllIlIlIlIllIIIlIl.mc.worldRenderer.reload();
            }
        };
        return lllllllllllllllllIlIlIlIllIIllIl;
    }

    @Override
    public void onDeactivate() {
        Ambience lllllllllllllllllIlIlIlIllIlIlIl;
        if (lllllllllllllllllIlIlIlIllIlIlIl.mc.worldRenderer != null) {
            lllllllllllllllllIlIlIlIllIlIlIl.mc.worldRenderer.reload();
        }
    }

    public Ambience() {
        super(Categories.World, "ambience", "Change the color of various pieces of the environment.");
        Ambience lllllllllllllllllIlIlIlIllIllIlI;
        lllllllllllllllllIlIlIlIllIllIlI.sgGeneral = lllllllllllllllllIlIlIlIllIllIlI.settings.getDefaultGroup();
        lllllllllllllllllIlIlIlIllIllIlI.sgDynamic = lllllllllllllllllIlIlIlIllIllIlI.settings.createGroup("Dynamic");
        lllllllllllllllllIlIlIlIllIllIlI.sgStatic = lllllllllllllllllIlIlIlIllIllIlI.settings.createGroup("Static");
        lllllllllllllllllIlIlIlIllIllIlI.enderMode = lllllllllllllllllIlIlIlIllIllIlI.sgGeneral.add(new BoolSetting.Builder().name("ender-mode").description("Makes the sky like the vast void of the End.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.enderCustomSkyColor = lllllllllllllllllIlIlIlIllIllIlI.sgGeneral.add(new BoolSetting.Builder().name("ender-custom-color").description("Allows a custom sky color for Ender Mode.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeSkyColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new BoolSetting.Builder().name("change-sky-color").description("Should the sky color be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.skyColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new ColorSetting.Builder().name("sky-color").description("The color to change the sky to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.endSkyColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new ColorSetting.Builder().name("end-sky-color").description("The color to change the End sky to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeCloudColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new BoolSetting.Builder().name("change-cloud-color").description("Should the color of the clouds be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.cloudColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new ColorSetting.Builder().name("clouds-color").description("The color to change the clouds to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeLightningColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new BoolSetting.Builder().name("change-lightning-color").description("Should the color of lightning be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.lightningColor = lllllllllllllllllIlIlIlIllIllIlI.sgDynamic.add(new ColorSetting.Builder().name("lightning-color").description("The color to change lightning to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeWaterColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new BoolSetting.Builder().name("change-water-color").description("Should the color of water be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.waterColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new ColorSetting.Builder().name("water-color").description("The color to change water to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeLavaColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new BoolSetting.Builder().name("change-lava-color").description("Should the color of lava be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.lavaColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new ColorSetting.Builder().name("lava-color").description("The color to change lava to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeFoliageColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new BoolSetting.Builder().name("change-foliage-color").description("Should the color of the foliage be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.foliageColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new ColorSetting.Builder().name("foliage-color").description("The color to change the foliage to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        lllllllllllllllllIlIlIlIllIllIlI.changeGrassColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new BoolSetting.Builder().name("change-grass-color").description("Should the color of grass be changed.").defaultValue(false).build());
        lllllllllllllllllIlIlIlIllIllIlI.grassColor = lllllllllllllllllIlIlIlIllIllIlI.sgStatic.add(new ColorSetting.Builder().name("grass-color").description("The color to change grass to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
    }

    public static class Custom
    extends SkyProperties {
        public boolean useThickFog(int lIIIIlIIIlIlllI, int lIIIIlIIIlIllIl) {
            return false;
        }

        public Vec3d adjustFogColor(Vec3d lIIIIlIIIllIIlI, float lIIIIlIIIllIIIl) {
            return lIIIIlIIIllIIlI.multiply((double)0.15f);
        }

        public float[] getFogColorOverride(float lIIIIlIIIlIlIll, float lIIIIlIIIlIlIlI) {
            return null;
        }

        public Custom() {
            super(Float.NaN, true, SkyType.END, true, false);
            Custom lIIIIlIIIllIllI;
        }
    }
}

