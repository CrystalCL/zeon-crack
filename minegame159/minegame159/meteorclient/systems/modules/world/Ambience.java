/*
 * Decompiled with CFR 0.151.
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
    public final Setting<SettingColor> skyColor;
    public final Setting<Boolean> changeWaterColor;
    public final Setting<Boolean> changeGrassColor;
    private final SettingGroup sgDynamic;
    public final Setting<Boolean> changeFoliageColor;
    public final Setting<Boolean> enderCustomSkyColor;
    public final Setting<SettingColor> cloudColor;
    public final Setting<Boolean> changeSkyColor;
    public final Setting<SettingColor> lightningColor;
    public final Setting<SettingColor> foliageColor;
    public final Setting<Boolean> changeLavaColor;
    public final Setting<SettingColor> lavaColor;
    public final Setting<Boolean> changeCloudColor;
    public final Setting<Boolean> changeLightningColor;
    private final SettingGroup sgStatic;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> enderMode;
    public final Setting<SettingColor> endSkyColor;
    public final Setting<SettingColor> grassColor;
    public final Setting<SettingColor> waterColor;

    private void lambda$getWidget$0() {
        if (this.mc.worldRenderer != null) {
            this.mc.worldRenderer.reload();
        }
    }

    @Override
    public void onActivate() {
        if (this.mc.worldRenderer != null) {
            this.mc.worldRenderer.reload();
        }
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WHorizontalList wHorizontalList = guiTheme.horizontalList();
        WButton wButton = wHorizontalList.add(guiTheme.button("Reload World")).expandX().widget();
        wButton.action = this::lambda$getWidget$0;
        return wHorizontalList;
    }

    public Ambience() {
        super(Categories.World, "ambience", "Change the color of various pieces of the environment.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgDynamic = this.settings.createGroup("Dynamic");
        this.sgStatic = this.settings.createGroup("Static");
        this.enderMode = this.sgGeneral.add(new BoolSetting.Builder().name("ender-mode").description("Makes the sky like the vast void of the End.").defaultValue(false).build());
        this.enderCustomSkyColor = this.sgGeneral.add(new BoolSetting.Builder().name("ender-custom-color").description("Allows a custom sky color for Ender Mode.").defaultValue(false).build());
        this.changeSkyColor = this.sgDynamic.add(new BoolSetting.Builder().name("change-sky-color").description("Should the sky color be changed.").defaultValue(false).build());
        this.skyColor = this.sgDynamic.add(new ColorSetting.Builder().name("sky-color").description("The color to change the sky to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.endSkyColor = this.sgDynamic.add(new ColorSetting.Builder().name("end-sky-color").description("The color to change the End sky to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeCloudColor = this.sgDynamic.add(new BoolSetting.Builder().name("change-cloud-color").description("Should the color of the clouds be changed.").defaultValue(false).build());
        this.cloudColor = this.sgDynamic.add(new ColorSetting.Builder().name("clouds-color").description("The color to change the clouds to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeLightningColor = this.sgDynamic.add(new BoolSetting.Builder().name("change-lightning-color").description("Should the color of lightning be changed.").defaultValue(false).build());
        this.lightningColor = this.sgDynamic.add(new ColorSetting.Builder().name("lightning-color").description("The color to change lightning to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeWaterColor = this.sgStatic.add(new BoolSetting.Builder().name("change-water-color").description("Should the color of water be changed.").defaultValue(false).build());
        this.waterColor = this.sgStatic.add(new ColorSetting.Builder().name("water-color").description("The color to change water to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeLavaColor = this.sgStatic.add(new BoolSetting.Builder().name("change-lava-color").description("Should the color of lava be changed.").defaultValue(false).build());
        this.lavaColor = this.sgStatic.add(new ColorSetting.Builder().name("lava-color").description("The color to change lava to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeFoliageColor = this.sgStatic.add(new BoolSetting.Builder().name("change-foliage-color").description("Should the color of the foliage be changed.").defaultValue(false).build());
        this.foliageColor = this.sgStatic.add(new ColorSetting.Builder().name("foliage-color").description("The color to change the foliage to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
        this.changeGrassColor = this.sgStatic.add(new BoolSetting.Builder().name("change-grass-color").description("Should the color of grass be changed.").defaultValue(false).build());
        this.grassColor = this.sgStatic.add(new ColorSetting.Builder().name("grass-color").description("The color to change grass to.").defaultValue(new SettingColor(102, 0, 0, 255)).build());
    }

    @Override
    public void onDeactivate() {
        if (this.mc.worldRenderer != null) {
            this.mc.worldRenderer.reload();
        }
    }

    public static class Custom
    extends SkyProperties {
        public Vec3d adjustFogColor(Vec3d Vec3d2, float f) {
            return Vec3d2.multiply((double)0.15f);
        }

        public float[] getFogColorOverride(float f, float f2) {
            return null;
        }

        public boolean useThickFog(int n, int n2) {
            return false;
        }

        public Custom() {
            super(Float.NaN, true, SkyType.END, true, false);
        }
    }
}

