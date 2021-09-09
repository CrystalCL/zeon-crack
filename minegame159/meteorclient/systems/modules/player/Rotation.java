/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class Rotation
extends Module {
    private final /* synthetic */ Setting<LockMode> yawLockMode;
    private final /* synthetic */ SettingGroup sgPitch;
    private final /* synthetic */ SettingGroup sgYaw;
    private final /* synthetic */ Setting<Double> yawAngle;
    private final /* synthetic */ Setting<Double> pitchAngle;
    private final /* synthetic */ Setting<LockMode> pitchLockMode;

    private float getSmartPitchDirection() {
        Rotation llIIllIIlIllIII;
        return (float)Math.round((llIIllIIlIllIII.mc.player.pitch + 1.0f) / 30.0f) * 30.0f;
    }

    @Override
    public void onActivate() {
        Rotation llIIllIIllIIIIl;
        llIIllIIllIIIIl.onTick(null);
    }

    @EventHandler
    private void onTick(TickEvent.Post llIIllIIlIllllI) {
        Rotation llIIllIIlIlllIl;
        switch (llIIllIIlIlllIl.yawLockMode.get()) {
            case Simple: {
                llIIllIIlIlllIl.setYawAngle(llIIllIIlIlllIl.yawAngle.get().floatValue());
                break;
            }
            case Smart: {
                llIIllIIlIlllIl.setYawAngle(llIIllIIlIlllIl.getSmartYawDirection());
            }
        }
        switch (llIIllIIlIlllIl.pitchLockMode.get()) {
            case Simple: {
                llIIllIIlIlllIl.mc.player.pitch = llIIllIIlIlllIl.pitchAngle.get().floatValue();
                break;
            }
            case Smart: {
                llIIllIIlIlllIl.mc.player.pitch = llIIllIIlIlllIl.getSmartPitchDirection();
            }
        }
    }

    private float getSmartYawDirection() {
        Rotation llIIllIIlIllIll;
        return (float)Math.round((llIIllIIlIllIll.mc.player.yaw + 1.0f) / 45.0f) * 45.0f;
    }

    private void setYawAngle(float llIIllIIlIlIIIl) {
        llIIllIIlIlIIlI.mc.player.yaw = llIIllIIlIlIIIl;
        llIIllIIlIlIIlI.mc.player.headYaw = llIIllIIlIlIIIl;
        llIIllIIlIlIIlI.mc.player.bodyYaw = llIIllIIlIlIIIl;
    }

    public Rotation() {
        super(Categories.Player, "rotation", "Changes/locks your yaw and pitch.");
        Rotation llIIllIIllIIlIl;
        llIIllIIllIIlIl.sgYaw = llIIllIIllIIlIl.settings.createGroup("Yaw");
        llIIllIIllIIlIl.sgPitch = llIIllIIllIIlIl.settings.createGroup("Pitch");
        llIIllIIllIIlIl.yawLockMode = llIIllIIllIIlIl.sgYaw.add(new EnumSetting.Builder().name("yaw-lock-mode").description("The way in which your yaw is locked.").defaultValue(LockMode.Simple).build());
        llIIllIIllIIlIl.yawAngle = llIIllIIllIIlIl.sgYaw.add(new DoubleSetting.Builder().name("yaw-angle").description("Yaw angle in degrees.").defaultValue(0.0).sliderMax(360.0).max(360.0).build());
        llIIllIIllIIlIl.pitchLockMode = llIIllIIllIIlIl.sgPitch.add(new EnumSetting.Builder().name("pitch-lock-mode").description("The way in which your pitch is locked.").defaultValue(LockMode.Simple).build());
        llIIllIIllIIlIl.pitchAngle = llIIllIIllIIlIl.sgPitch.add(new DoubleSetting.Builder().name("pitch-angle").description("Pitch angle in degrees.").defaultValue(0.0).min(-90.0).max(90.0).sliderMin(-90.0).sliderMax(90.0).build());
    }

    public static final class LockMode
    extends Enum<LockMode> {
        public static final /* synthetic */ /* enum */ LockMode Smart;
        public static final /* synthetic */ /* enum */ LockMode None;
        public static final /* synthetic */ /* enum */ LockMode Simple;
        private static final /* synthetic */ LockMode[] $VALUES;

        static {
            Smart = new LockMode();
            Simple = new LockMode();
            None = new LockMode();
            $VALUES = LockMode.$values();
        }

        public static LockMode[] values() {
            return (LockMode[])$VALUES.clone();
        }

        private static /* synthetic */ LockMode[] $values() {
            return new LockMode[]{Smart, Simple, None};
        }

        public static LockMode valueOf(String lllllllllllllllllIlIIlllIllIIlIl) {
            return Enum.valueOf(LockMode.class, lllllllllllllllllIlIIlllIllIIlIl);
        }

        private LockMode() {
            LockMode lllllllllllllllllIlIIlllIllIIIII;
        }
    }
}

