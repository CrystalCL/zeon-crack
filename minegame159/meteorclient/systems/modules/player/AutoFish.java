/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.sound.SoundInstance
 *  net.minecraft.entity.projectile.FishingBobberEntity
 *  net.minecraft.item.FishingRodItem
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.PlaySoundEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;

public class AutoFish
extends Module {
    private /* synthetic */ int ticksToRightClick;
    private final /* synthetic */ Setting<Double> splashDetectionRange;
    private final /* synthetic */ Setting<Boolean> splashDetectionRangeEnabled;
    private final /* synthetic */ Setting<Integer> ticksAutoCast;
    private /* synthetic */ int ticksData;
    private /* synthetic */ int autoCastTimer;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> ticksThrow;
    private final /* synthetic */ Setting<Boolean> autoCast;
    private final /* synthetic */ Setting<Integer> ticksCatch;
    private /* synthetic */ int autoCastCheckTimer;
    private final /* synthetic */ SettingGroup sgSplashRangeDetection;
    private /* synthetic */ boolean autoCastEnabled;
    private /* synthetic */ boolean ticksEnabled;

    @Override
    public void onActivate() {
        lllllllllllllllllllIlIlIIlIllllI.ticksEnabled = false;
        lllllllllllllllllllIlIlIIlIllllI.autoCastEnabled = false;
        lllllllllllllllllllIlIlIIlIllllI.autoCastCheckTimer = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIlIlIIlIIlllI) {
        AutoFish lllllllllllllllllllIlIlIIlIIllIl;
        if (lllllllllllllllllllIlIlIIlIIllIl.autoCastCheckTimer <= 0) {
            lllllllllllllllllllIlIlIIlIIllIl.autoCastCheckTimer = 30;
            if (lllllllllllllllllllIlIlIIlIIllIl.autoCast.get().booleanValue() && !lllllllllllllllllllIlIlIIlIIllIl.ticksEnabled && !lllllllllllllllllllIlIlIIlIIllIl.autoCastEnabled && lllllllllllllllllllIlIlIIlIIllIl.mc.player.fishHook == null && lllllllllllllllllllIlIlIIlIIllIl.mc.player.getMainHandStack().getItem() instanceof FishingRodItem) {
                lllllllllllllllllllIlIlIIlIIllIl.autoCastTimer = 0;
                lllllllllllllllllllIlIlIIlIIllIl.autoCastEnabled = true;
            }
        } else {
            --lllllllllllllllllllIlIlIIlIIllIl.autoCastCheckTimer;
        }
        if (lllllllllllllllllllIlIlIIlIIllIl.autoCastEnabled) {
            ++lllllllllllllllllllIlIlIIlIIllIl.autoCastTimer;
            if (lllllllllllllllllllIlIlIIlIIllIl.autoCastTimer > lllllllllllllllllllIlIlIIlIIllIl.ticksAutoCast.get()) {
                lllllllllllllllllllIlIlIIlIIllIl.autoCastEnabled = false;
                Utils.rightClick();
            }
        }
        if (lllllllllllllllllllIlIlIIlIIllIl.ticksEnabled && lllllllllllllllllllIlIlIIlIIllIl.ticksToRightClick <= 0) {
            if (lllllllllllllllllllIlIlIIlIIllIl.ticksData == 0) {
                Utils.rightClick();
                lllllllllllllllllllIlIlIIlIIllIl.ticksToRightClick = lllllllllllllllllllIlIlIIlIIllIl.ticksThrow.get();
                lllllllllllllllllllIlIlIIlIIllIl.ticksData = 1;
            } else if (lllllllllllllllllllIlIlIIlIIllIl.ticksData == 1) {
                Utils.rightClick();
                lllllllllllllllllllIlIlIIlIIllIl.ticksEnabled = false;
            }
        }
        --lllllllllllllllllllIlIlIIlIIllIl.ticksToRightClick;
    }

    @EventHandler
    private void onKey(KeyEvent lllllllllllllllllllIlIlIIlIIlIlI) {
        AutoFish lllllllllllllllllllIlIlIIlIIlIIl;
        if (lllllllllllllllllllIlIlIIlIIlIIl.mc.options.keyUse.isPressed()) {
            lllllllllllllllllllIlIlIIlIIlIIl.ticksEnabled = false;
        }
    }

    public AutoFish() {
        super(Categories.Player, "auto-fish", "Automatically fishes for you.");
        AutoFish lllllllllllllllllllIlIlIIllIIIIl;
        lllllllllllllllllllIlIlIIllIIIIl.sgGeneral = lllllllllllllllllllIlIlIIllIIIIl.settings.getDefaultGroup();
        lllllllllllllllllllIlIlIIllIIIIl.sgSplashRangeDetection = lllllllllllllllllllIlIlIIllIIIIl.settings.createGroup("Splash Detection");
        lllllllllllllllllllIlIlIIllIIIIl.autoCast = lllllllllllllllllllIlIlIIllIIIIl.sgGeneral.add(new BoolSetting.Builder().name("auto-cast").description("Automatically casts when not fishing.").defaultValue(true).build());
        lllllllllllllllllllIlIlIIllIIIIl.ticksAutoCast = lllllllllllllllllllIlIlIIllIIIIl.sgGeneral.add(new IntSetting.Builder().name("ticks-auto-cast").description("The amount of ticks to wait before recasting automatically.").defaultValue(10).min(0).sliderMax(60).build());
        lllllllllllllllllllIlIlIIllIIIIl.ticksCatch = lllllllllllllllllllIlIlIIllIIIIl.sgGeneral.add(new IntSetting.Builder().name("catch-delay").description("The amount of ticks to wait before catching the fish.").defaultValue(6).min(0).sliderMax(60).build());
        lllllllllllllllllllIlIlIIllIIIIl.ticksThrow = lllllllllllllllllllIlIlIIllIIIIl.sgGeneral.add(new IntSetting.Builder().name("throw-delay").description("The amount of ticks to wait before throwing the bobber.").defaultValue(14).min(0).sliderMax(60).build());
        lllllllllllllllllllIlIlIIllIIIIl.splashDetectionRangeEnabled = lllllllllllllllllllIlIlIIllIIIIl.sgSplashRangeDetection.add(new BoolSetting.Builder().name("splash-detection-range-enabled").description("Allows you to use multiple accounts next to each other.").defaultValue(false).build());
        lllllllllllllllllllIlIlIIllIIIIl.splashDetectionRange = lllllllllllllllllllIlIlIIllIIIIl.sgSplashRangeDetection.add(new DoubleSetting.Builder().name("splash-detection-range").description("The detection range of a splash. Lower values will not work when the TPS is low.").defaultValue(10.0).min(0.0).build());
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent lllllllllllllllllllIlIlIIlIlIlll) {
        AutoFish lllllllllllllllllllIlIlIIlIllIII;
        SoundInstance lllllllllllllllllllIlIlIIlIlIllI = lllllllllllllllllllIlIlIIlIlIlll.sound;
        FishingBobberEntity lllllllllllllllllllIlIlIIlIlIlIl = lllllllllllllllllllIlIlIIlIllIII.mc.player.fishHook;
        if (lllllllllllllllllllIlIlIIlIlIllI.getId().getPath().equals("entity.fishing_bobber.splash") && (!lllllllllllllllllllIlIlIIlIllIII.splashDetectionRangeEnabled.get().booleanValue() || Utils.distance(lllllllllllllllllllIlIlIIlIlIlIl.getX(), lllllllllllllllllllIlIlIIlIlIlIl.getY(), lllllllllllllllllllIlIlIIlIlIlIl.getZ(), lllllllllllllllllllIlIlIIlIlIllI.getX(), lllllllllllllllllllIlIlIIlIlIllI.getY(), lllllllllllllllllllIlIlIIlIlIllI.getZ()) <= lllllllllllllllllllIlIlIIlIllIII.splashDetectionRange.get())) {
            lllllllllllllllllllIlIlIIlIllIII.ticksEnabled = true;
            lllllllllllllllllllIlIlIIlIllIII.ticksToRightClick = lllllllllllllllllllIlIlIIlIllIII.ticksCatch.get();
            lllllllllllllllllllIlIlIIlIllIII.ticksData = 0;
        }
    }
}

