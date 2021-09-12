/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class NoRender
extends Module {
    private final Setting<Boolean> noFog;
    private final Setting<Boolean> noArmorStands;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> noGuiBackground;
    private final Setting<Boolean> noBlockBreakParticles;
    private final Setting<Boolean> noPortalOverlay;
    private final Setting<Boolean> noEnchTableBook;
    private final Setting<Boolean> noNausea;
    private final Setting<Boolean> noVignette;
    private final Setting<Boolean> noFallingBlocks;
    private final Setting<Boolean> noItems;
    private final Setting<Boolean> noSignText;
    private final Setting<Boolean> noPotionIcons;
    private final Setting<Boolean> noEatParticles;
    private final Setting<Boolean> noHurtCam;
    private final Setting<Boolean> noBossBar;
    private final Setting<Boolean> noCrosshair;
    private final Setting<Boolean> noSkylightUpdates;
    private final Setting<Boolean> noWeather;
    private final Setting<Boolean> noMinecarts;
    private final Setting<Boolean> noTotemAnimation;
    private final Setting<Boolean> noArmor;
    private final Setting<Boolean> noPumpkinOverlay;
    private final Setting<Boolean> noScoreboard;
    private final Setting<Boolean> noWaterOverlay;
    private final Setting<Boolean> noXpOrbs;
    private final Setting<Boolean> noFireOverlay;

    public boolean noBossBar() {
        return this.isActive() && this.noBossBar.get() != false;
    }

    public boolean noHurtCam() {
        return this.isActive() && this.noHurtCam.get() != false;
    }

    public boolean noItems() {
        return this.isActive() && this.noItems.get() != false;
    }

    public boolean noFallingBlocks() {
        return this.isActive() && this.noFallingBlocks.get() != false;
    }

    public boolean noArmor() {
        return this.isActive() && this.noArmor.get() != false;
    }

    public boolean noPortalOverlay() {
        return this.isActive() && this.noPortalOverlay.get() != false;
    }

    public boolean noFog() {
        return this.isActive() && this.noFog.get() != false;
    }

    public boolean noWeather() {
        return this.isActive() && this.noWeather.get() != false;
    }

    public boolean noFireOverlay() {
        return this.isActive() && this.noFireOverlay.get() != false;
    }

    public boolean noPumpkinOverlay() {
        return this.isActive() && this.noPumpkinOverlay.get() != false;
    }

    public boolean noCrosshair() {
        return this.isActive() && this.noCrosshair.get() != false;
    }

    public boolean noEatParticles() {
        return this.isActive() && this.noEatParticles.get() != false;
    }

    public boolean noWaterOverlay() {
        return this.isActive() && this.noWaterOverlay.get() != false;
    }

    public boolean noNausea() {
        return this.isActive() && this.noNausea.get() != false;
    }

    public boolean noTotemAnimation() {
        return this.isActive() && this.noTotemAnimation.get() != false;
    }

    public NoRender() {
        super(Categories.Render, "no-render", "Disables certain animations or overlays from rendering.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.noHurtCam = this.sgGeneral.add(new BoolSetting.Builder().name("no-hurt-cam").description("Disables the hurt camera effect.").defaultValue(true).build());
        this.noWeather = this.sgGeneral.add(new BoolSetting.Builder().name("no-weather").description("Disables the weather.").defaultValue(true).build());
        this.noPortalOverlay = this.sgGeneral.add(new BoolSetting.Builder().name("no-portal-overlay").description("Disables all portal overlays.").defaultValue(true).build());
        this.noPumpkinOverlay = this.sgGeneral.add(new BoolSetting.Builder().name("no-pumpkin-overlay").description("Disables the pumpkin-head overlay.").defaultValue(true).build());
        this.noFireOverlay = this.sgGeneral.add(new BoolSetting.Builder().name("no-fire-overlay").description("Disables the fire overlay.").defaultValue(true).build());
        this.noWaterOverlay = this.sgGeneral.add(new BoolSetting.Builder().name("no-water-overlay").description("Disables the water overlay.").defaultValue(true).build());
        this.noVignette = this.sgGeneral.add(new BoolSetting.Builder().name("no-vignette").description("Disables the vignette effect.").defaultValue(true).build());
        this.noBossBar = this.sgGeneral.add(new BoolSetting.Builder().name("no-boss-bar").description("Disables all boss bars from rendering.").defaultValue(true).build());
        this.noScoreboard = this.sgGeneral.add(new BoolSetting.Builder().name("no-scoreboard").description("Disable the scoreboard from rendering.").defaultValue(true).build());
        this.noFog = this.sgGeneral.add(new BoolSetting.Builder().name("no-fog").description("Disables all fog.").defaultValue(true).build());
        this.noTotemAnimation = this.sgGeneral.add(new BoolSetting.Builder().name("no-totem-animation").description("Disables the totem animation on your screen when popping a totem.").defaultValue(true).build());
        this.noArmor = this.sgGeneral.add(new BoolSetting.Builder().name("no-armor").description("Disables all armor from rendering.").defaultValue(false).build());
        this.noNausea = this.sgGeneral.add(new BoolSetting.Builder().name("no-nausea").description("Disables the nausea effect.").defaultValue(true).build());
        this.noItems = this.sgGeneral.add(new BoolSetting.Builder().name("no-item").description("Disables all item entities.").defaultValue(false).build());
        this.noEnchTableBook = this.sgGeneral.add(new BoolSetting.Builder().name("no-ench-table-book").description("Disables book above enchanting table.").defaultValue(false).build());
        this.noSignText = this.sgGeneral.add(new BoolSetting.Builder().name("no-sign-text").description("Disables any text on signs. Useful for screenshots or streams.").defaultValue(false).build());
        this.noBlockBreakParticles = this.sgGeneral.add(new BoolSetting.Builder().name("no-block-break-particles").description("Disables all block-break particles.").defaultValue(false).build());
        this.noFallingBlocks = this.sgGeneral.add(new BoolSetting.Builder().name("no-falling-blocks").description("Disables rendering of falling blocks. Useful for lag machines.").defaultValue(false).build());
        this.noPotionIcons = this.sgGeneral.add(new BoolSetting.Builder().name("no-potion-icons").description("Disables rendering of status effect icons.").defaultValue(false).build());
        this.noArmorStands = this.sgGeneral.add(new BoolSetting.Builder().name("no-armor-stands").description("Disables rendering of armor stands. Useful for lag machines.").defaultValue(false).build());
        this.noMinecarts = this.sgGeneral.add(new BoolSetting.Builder().name("no-minecarts").description("Disables rendering of minecarts.").defaultValue(false).build());
        this.noGuiBackground = this.sgGeneral.add(new BoolSetting.Builder().name("no-gui-background").description("Disables rendering of the dark GUI background.").defaultValue(false).build());
        this.noXpOrbs = this.sgGeneral.add(new BoolSetting.Builder().name("no-xp-orbs").description("Disables rendering of experience orb entities.").defaultValue(false).build());
        this.noEatParticles = this.sgGeneral.add(new BoolSetting.Builder().name("no-eating-particles").description("Disables rendering of eating particles.").defaultValue(false).build());
        this.noSkylightUpdates = this.sgGeneral.add(new BoolSetting.Builder().name("no-skylight-updates").description("Disables rendering of skylight updates. Useful for lag machines").defaultValue(false).build());
        this.noCrosshair = this.sgGeneral.add(new BoolSetting.Builder().name("no-crosshair").description("Disables rendering of the crosshair.").defaultValue(false).build());
    }

    public boolean noSignText() {
        return this.isActive() && this.noSignText.get() != false;
    }

    public boolean noPotionIcons() {
        return this.isActive() && this.noPotionIcons.get() != false;
    }

    public boolean noGuiBackground() {
        return this.isActive() && this.noGuiBackground.get() != false;
    }

    public boolean noScoreboard() {
        return this.isActive() && this.noScoreboard.get() != false;
    }

    public boolean noEnchTableBook() {
        return this.isActive() && this.noEnchTableBook.get() != false;
    }

    public boolean noBlockBreakParticles() {
        return this.isActive() && this.noBlockBreakParticles.get() != false;
    }

    public boolean noSkylightUpdates() {
        return this.isActive() && this.noSkylightUpdates.get() != false;
    }

    public boolean noXpOrbs() {
        return this.isActive() && this.noXpOrbs.get() != false;
    }

    public boolean noArmorStands() {
        return this.isActive() && this.noArmorStands.get() != false;
    }

    public boolean noVignette() {
        return this.isActive() && this.noVignette.get() != false;
    }

    public boolean noMinecarts() {
        return this.isActive() && this.noMinecarts.get() != false;
    }
}

