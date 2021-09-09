/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class NoRender
extends Module {
    private final /* synthetic */ Setting<Boolean> noXpOrbs;
    private final /* synthetic */ Setting<Boolean> noSkylightUpdates;
    private final /* synthetic */ Setting<Boolean> noPortalOverlay;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> noHurtCam;
    private final /* synthetic */ Setting<Boolean> noNausea;
    private final /* synthetic */ Setting<Boolean> noTotemAnimation;
    private final /* synthetic */ Setting<Boolean> noPumpkinOverlay;
    private final /* synthetic */ Setting<Boolean> noArmor;
    private final /* synthetic */ Setting<Boolean> noGuiBackground;
    private final /* synthetic */ Setting<Boolean> noCrosshair;
    private final /* synthetic */ Setting<Boolean> noVignette;
    private final /* synthetic */ Setting<Boolean> noScoreboard;
    private final /* synthetic */ Setting<Boolean> noBossBar;
    private final /* synthetic */ Setting<Boolean> noFog;
    private final /* synthetic */ Setting<Boolean> noWeather;
    private final /* synthetic */ Setting<Boolean> noBlockBreakParticles;
    private final /* synthetic */ Setting<Boolean> noSignText;
    private final /* synthetic */ Setting<Boolean> noMinecarts;
    private final /* synthetic */ Setting<Boolean> noFallingBlocks;
    private final /* synthetic */ Setting<Boolean> noPotionIcons;
    private final /* synthetic */ Setting<Boolean> noEatParticles;
    private final /* synthetic */ Setting<Boolean> noEnchTableBook;
    private final /* synthetic */ Setting<Boolean> noItems;
    private final /* synthetic */ Setting<Boolean> noFireOverlay;
    private final /* synthetic */ Setting<Boolean> noWaterOverlay;
    private final /* synthetic */ Setting<Boolean> noArmorStands;

    public boolean noPotionIcons() {
        NoRender llllllllllllllllIlIllIlIIlIlllll;
        return llllllllllllllllIlIllIlIIlIlllll.isActive() && llllllllllllllllIlIllIlIIlIlllll.noPotionIcons.get() != false;
    }

    public boolean noWeather() {
        NoRender llllllllllllllllIlIllIlIlIIlIIlI;
        return llllllllllllllllIlIllIlIlIIlIIlI.isActive() && llllllllllllllllIlIllIlIlIIlIIlI.noWeather.get() != false;
    }

    public boolean noTotemAnimation() {
        NoRender llllllllllllllllIlIllIlIIlllIllI;
        return llllllllllllllllIlIllIlIIlllIllI.isActive() && llllllllllllllllIlIllIlIIlllIllI.noTotemAnimation.get() != false;
    }

    public boolean noSignText() {
        NoRender llllllllllllllllIlIllIlIIllIIlll;
        return llllllllllllllllIlIllIlIIllIIlll.isActive() && llllllllllllllllIlIllIlIIllIIlll.noSignText.get() != false;
    }

    public boolean noNausea() {
        NoRender llllllllllllllllIlIllIlIIlllIIII;
        return llllllllllllllllIlIllIlIIlllIIII.isActive() && llllllllllllllllIlIllIlIIlllIIII.noNausea.get() != false;
    }

    public boolean noVignette() {
        NoRender llllllllllllllllIlIllIlIlIIIIIlI;
        return llllllllllllllllIlIllIlIlIIIIIlI.isActive() && llllllllllllllllIlIllIlIlIIIIIlI.noVignette.get() != false;
    }

    public boolean noEatParticles() {
        NoRender llllllllllllllllIlIllIlIIlIIllll;
        return llllllllllllllllIlIllIlIIlIIllll.isActive() && llllllllllllllllIlIllIlIIlIIllll.noEatParticles.get() != false;
    }

    public boolean noPumpkinOverlay() {
        NoRender llllllllllllllllIlIllIlIlIIIlIll;
        return llllllllllllllllIlIllIlIlIIIlIll.isActive() && llllllllllllllllIlIllIlIlIIIlIll.noPumpkinOverlay.get() != false;
    }

    public boolean noHurtCam() {
        NoRender llllllllllllllllIlIllIlIlIIlIlII;
        return llllllllllllllllIlIllIlIlIIlIlII.isActive() && llllllllllllllllIlIllIlIlIIlIlII.noHurtCam.get() != false;
    }

    public boolean noFallingBlocks() {
        NoRender llllllllllllllllIlIllIlIIllIIIIl;
        return llllllllllllllllIlIllIlIIllIIIIl.isActive() && llllllllllllllllIlIllIlIIllIIIIl.noFallingBlocks.get() != false;
    }

    public boolean noFog() {
        NoRender llllllllllllllllIlIllIlIIllllIlI;
        return llllllllllllllllIlIllIlIIllllIlI.isActive() && llllllllllllllllIlIllIlIIllllIlI.noFog.get() != false;
    }

    public boolean noEnchTableBook() {
        NoRender llllllllllllllllIlIllIlIIllIlIlI;
        return llllllllllllllllIlIllIlIIllIlIlI.isActive() && llllllllllllllllIlIllIlIIllIlIlI.noEnchTableBook.get() != false;
    }

    public boolean noWaterOverlay() {
        NoRender llllllllllllllllIlIllIlIlIIIIllI;
        return llllllllllllllllIlIllIlIlIIIIllI.isActive() && llllllllllllllllIlIllIlIlIIIIllI.noWaterOverlay.get() != false;
    }

    public boolean noItems() {
        NoRender llllllllllllllllIlIllIlIIllIllIl;
        return llllllllllllllllIlIllIlIIllIllIl.isActive() && llllllllllllllllIlIllIlIIllIllIl.noItems.get() != false;
    }

    public boolean noArmorStands() {
        NoRender llllllllllllllllIlIllIlIIlIllIll;
        return llllllllllllllllIlIllIlIIlIllIll.isActive() && llllllllllllllllIlIllIlIIlIllIll.noArmorStands.get() != false;
    }

    public boolean noBlockBreakParticles() {
        NoRender llllllllllllllllIlIllIlIIllIIlII;
        return llllllllllllllllIlIllIlIIllIIlII.isActive() && llllllllllllllllIlIllIlIIllIIlII.noBlockBreakParticles.get() != false;
    }

    public boolean noCrosshair() {
        NoRender llllllllllllllllIlIllIlIIlIIlIlI;
        return llllllllllllllllIlIllIlIIlIIlIlI.isActive() && llllllllllllllllIlIllIlIIlIIlIlI.noCrosshair.get() != false;
    }

    public boolean noSkylightUpdates() {
        NoRender llllllllllllllllIlIllIlIIlIIllII;
        return llllllllllllllllIlIllIlIIlIIllII.isActive() && llllllllllllllllIlIllIlIIlIIllII.noSkylightUpdates.get() != false;
    }

    public boolean noMinecarts() {
        NoRender llllllllllllllllIlIllIlIIlIllIIl;
        return llllllllllllllllIlIllIlIIlIllIIl.isActive() && llllllllllllllllIlIllIlIIlIllIIl.noMinecarts.get() != false;
    }

    public boolean noXpOrbs() {
        NoRender llllllllllllllllIlIllIlIIlIlIIlI;
        return llllllllllllllllIlIllIlIIlIlIIlI.isActive() && llllllllllllllllIlIllIlIIlIlIIlI.noXpOrbs.get() != false;
    }

    public boolean noBossBar() {
        NoRender llllllllllllllllIlIllIlIIlllllll;
        return llllllllllllllllIlIllIlIIlllllll.isActive() && llllllllllllllllIlIllIlIIlllllll.noBossBar.get() != false;
    }

    public NoRender() {
        super(Categories.Render, "no-render", "Disables certain animations or overlays from rendering.");
        NoRender llllllllllllllllIlIllIlIlIIlIlll;
        llllllllllllllllIlIllIlIlIIlIlll.sgGeneral = llllllllllllllllIlIllIlIlIIlIlll.settings.getDefaultGroup();
        llllllllllllllllIlIllIlIlIIlIlll.noHurtCam = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-hurt-cam").description("Disables the hurt camera effect.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noWeather = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-weather").description("Disables the weather.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noPortalOverlay = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-portal-overlay").description("Disables all portal overlays.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noPumpkinOverlay = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-pumpkin-overlay").description("Disables the pumpkin-head overlay.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noFireOverlay = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-fire-overlay").description("Disables the fire overlay.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noWaterOverlay = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-water-overlay").description("Disables the water overlay.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noVignette = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-vignette").description("Disables the vignette effect.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noBossBar = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-boss-bar").description("Disables all boss bars from rendering.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noScoreboard = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-scoreboard").description("Disable the scoreboard from rendering.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noFog = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-fog").description("Disables all fog.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noTotemAnimation = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-totem-animation").description("Disables the totem animation on your screen when popping a totem.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noArmor = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-armor").description("Disables all armor from rendering.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noNausea = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-nausea").description("Disables the nausea effect.").defaultValue(true).build());
        llllllllllllllllIlIllIlIlIIlIlll.noItems = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-item").description("Disables all item entities.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noEnchTableBook = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-ench-table-book").description("Disables book above enchanting table.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noSignText = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-sign-text").description("Disables any text on signs. Useful for screenshots or streams.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noBlockBreakParticles = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-block-break-particles").description("Disables all block-break particles.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noFallingBlocks = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-falling-blocks").description("Disables rendering of falling blocks. Useful for lag machines.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noPotionIcons = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-potion-icons").description("Disables rendering of status effect icons.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noArmorStands = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-armor-stands").description("Disables rendering of armor stands. Useful for lag machines.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noMinecarts = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-minecarts").description("Disables rendering of minecarts.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noGuiBackground = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-gui-background").description("Disables rendering of the dark GUI background.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noXpOrbs = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-xp-orbs").description("Disables rendering of experience orb entities.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noEatParticles = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-eating-particles").description("Disables rendering of eating particles.").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noSkylightUpdates = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-skylight-updates").description("Disables rendering of skylight updates. Useful for lag machines").defaultValue(false).build());
        llllllllllllllllIlIllIlIlIIlIlll.noCrosshair = llllllllllllllllIlIllIlIlIIlIlll.sgGeneral.add(new BoolSetting.Builder().name("no-crosshair").description("Disables rendering of the crosshair.").defaultValue(false).build());
    }

    public boolean noScoreboard() {
        NoRender llllllllllllllllIlIllIlIIlllllII;
        return llllllllllllllllIlIllIlIIlllllII.isActive() && llllllllllllllllIlIllIlIIlllllII.noScoreboard.get() != false;
    }

    public boolean noGuiBackground() {
        NoRender llllllllllllllllIlIllIlIIlIlIlIl;
        return llllllllllllllllIlIllIlIIlIlIlIl.isActive() && llllllllllllllllIlIllIlIIlIlIlIl.noGuiBackground.get() != false;
    }

    public boolean noPortalOverlay() {
        NoRender llllllllllllllllIlIllIlIlIIIllll;
        return llllllllllllllllIlIllIlIlIIIllll.isActive() && llllllllllllllllIlIllIlIlIIIllll.noPortalOverlay.get() != false;
    }

    public boolean noFireOverlay() {
        NoRender llllllllllllllllIlIllIlIlIIIlIIl;
        return llllllllllllllllIlIllIlIlIIIlIIl.isActive() && llllllllllllllllIlIllIlIlIIIlIIl.noFireOverlay.get() != false;
    }

    public boolean noArmor() {
        NoRender llllllllllllllllIlIllIlIIlllIlII;
        return llllllllllllllllIlIllIlIIlllIlII.isActive() && llllllllllllllllIlIllIlIIlllIlII.noArmor.get() != false;
    }
}

