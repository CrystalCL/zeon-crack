/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.option.Perspective
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.Input;
import net.minecraft.client.option.Perspective;

public class FreeRotate
extends Module {
    public final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ SettingGroup sgArrows;
    public final /* synthetic */ Setting<Double> sensitivity;
    private final /* synthetic */ Setting<Double> arrowSpeed;
    public /* synthetic */ float cameraYaw;
    public /* synthetic */ float cameraPitch;
    public final /* synthetic */ Setting<Boolean> togglePerpective;
    private /* synthetic */ Perspective prePers;
    private final /* synthetic */ SettingGroup sgGeneral;
    public final /* synthetic */ Setting<Boolean> arrows;

    public FreeRotate() {
        super(Categories.Render, "free-rotate", "Allows more rotation options in third person.");
        FreeRotate llllllllllllllllllIllIIIIIIllIIl;
        llllllllllllllllllIllIIIIIIllIIl.sgGeneral = llllllllllllllllllIllIIIIIIllIIl.settings.getDefaultGroup();
        llllllllllllllllllIllIIIIIIllIIl.sgArrows = llllllllllllllllllIllIIIIIIllIIl.settings.createGroup("Arrows");
        llllllllllllllllllIllIIIIIIllIIl.mode = llllllllllllllllllIllIIIIIIllIIl.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which entity to rotate.").defaultValue(Mode.Player).build());
        llllllllllllllllllIllIIIIIIllIIl.togglePerpective = llllllllllllllllllIllIIIIIIllIIl.sgGeneral.add(new BoolSetting.Builder().name("toggle-perspective").description("Changes your perspective on toggle.").defaultValue(true).build());
        llllllllllllllllllIllIIIIIIllIIl.sensitivity = llllllllllllllllllIllIIIIIIllIIl.sgGeneral.add(new DoubleSetting.Builder().name("camera-sensitivity").description("How fast the camera moves in camera mode.").defaultValue(8.0).min(0.0).sliderMax(10.0).build());
        llllllllllllllllllIllIIIIIIllIIl.arrows = llllllllllllllllllIllIIIIIIllIIl.sgArrows.add(new BoolSetting.Builder().name("arrows-control-opposite").description("Allows you to control the other entities rotation with the arrow keys.").defaultValue(true).build());
        llllllllllllllllllIllIIIIIIllIIl.arrowSpeed = llllllllllllllllllIllIIIIIIllIIl.sgArrows.add(new DoubleSetting.Builder().name("arrow-speed").description("Rotation speed with arrow keys.").defaultValue(4.0).min(0.0).build());
    }

    @Override
    public void onActivate() {
        FreeRotate llllllllllllllllllIllIIIIIIlIlll;
        llllllllllllllllllIllIIIIIIlIlll.cameraYaw = llllllllllllllllllIllIIIIIIlIlll.mc.player.yaw;
        llllllllllllllllllIllIIIIIIlIlll.cameraPitch = llllllllllllllllllIllIIIIIIlIlll.mc.player.pitch;
        llllllllllllllllllIllIIIIIIlIlll.prePers = llllllllllllllllllIllIIIIIIlIlll.mc.options.getPerspective();
        if (llllllllllllllllllIllIIIIIIlIlll.prePers != Perspective.THIRD_PERSON_BACK && llllllllllllllllllIllIIIIIIlIlll.togglePerpective.get().booleanValue()) {
            llllllllllllllllllIllIIIIIIlIlll.mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIllIIIIIIIlIII) {
        FreeRotate llllllllllllllllllIllIIIIIIIIlll;
        if (llllllllllllllllllIllIIIIIIIIlll.arrows.get().booleanValue()) {
            int llllllllllllllllllIllIIIIIIIlIlI = 0;
            while ((double)llllllllllllllllllIllIIIIIIIlIlI < llllllllllllllllllIllIIIIIIIIlll.arrowSpeed.get() * 2.0) {
                switch (llllllllllllllllllIllIIIIIIIIlll.mode.get()) {
                    case Player: {
                        if (Input.isKeyPressed(263)) {
                            llllllllllllllllllIllIIIIIIIIlll.cameraYaw = (float)((double)llllllllllllllllllIllIIIIIIIIlll.cameraYaw - 0.5);
                        }
                        if (Input.isKeyPressed(262)) {
                            llllllllllllllllllIllIIIIIIIIlll.cameraYaw = (float)((double)llllllllllllllllllIllIIIIIIIIlll.cameraYaw + 0.5);
                        }
                        if (Input.isKeyPressed(265)) {
                            llllllllllllllllllIllIIIIIIIIlll.cameraPitch = (float)((double)llllllllllllllllllIllIIIIIIIIlll.cameraPitch - 0.5);
                        }
                        if (!Input.isKeyPressed(264)) break;
                        llllllllllllllllllIllIIIIIIIIlll.cameraPitch = (float)((double)llllllllllllllllllIllIIIIIIIIlll.cameraPitch + 0.5);
                        break;
                    }
                    case Camera: {
                        if (Input.isKeyPressed(263)) {
                            llllllllllllllllllIllIIIIIIIIlll.mc.player.yaw = (float)((double)llllllllllllllllllIllIIIIIIIIlll.mc.player.yaw - 0.5);
                        }
                        if (Input.isKeyPressed(262)) {
                            llllllllllllllllllIllIIIIIIIIlll.mc.player.yaw = (float)((double)llllllllllllllllllIllIIIIIIIIlll.mc.player.yaw + 0.5);
                        }
                        if (Input.isKeyPressed(265)) {
                            llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch = (float)((double)llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch - 0.5);
                        }
                        if (!Input.isKeyPressed(264)) break;
                        llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch = (float)((double)llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch + 0.5);
                    }
                }
                ++llllllllllllllllllIllIIIIIIIlIlI;
            }
        }
        llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch = Utils.clamp(llllllllllllllllllIllIIIIIIIIlll.mc.player.pitch, -90.0f, 90.0f);
        llllllllllllllllllIllIIIIIIIIlll.cameraPitch = Utils.clamp(llllllllllllllllllIllIIIIIIIIlll.cameraPitch, -90.0f, 90.0f);
    }

    public boolean cameraMode() {
        FreeRotate llllllllllllllllllIllIIIIIIIllIl;
        return llllllllllllllllllIllIIIIIIIllIl.isActive() && llllllllllllllllllIllIIIIIIIllIl.mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && llllllllllllllllllIllIIIIIIIllIl.mode.get() == Mode.Camera;
    }

    @Override
    public void onDeactivate() {
        FreeRotate llllllllllllllllllIllIIIIIIlIIll;
        if (llllllllllllllllllIllIIIIIIlIIll.mc.options.getPerspective() != llllllllllllllllllIllIIIIIIlIIll.prePers && llllllllllllllllllIllIIIIIIlIIll.togglePerpective.get().booleanValue()) {
            llllllllllllllllllIllIIIIIIlIIll.mc.options.setPerspective(llllllllllllllllllIllIIIIIIlIIll.prePers);
        }
    }

    public boolean playerMode() {
        FreeRotate llllllllllllllllllIllIIIIIIlIIIl;
        return llllllllllllllllllIllIIIIIIlIIIl.isActive() && llllllllllllllllllIllIIIIIIlIIIl.mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && llllllllllllllllllIllIIIIIIlIIIl.mode.get() == Mode.Player;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Player;
        public static final /* synthetic */ /* enum */ Mode Camera;
        private static final /* synthetic */ Mode[] $VALUES;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Player, Camera};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String lIlIlIIIlIIIlIl) {
            return Enum.valueOf(Mode.class, lIlIlIIIlIIIlIl);
        }

        private Mode() {
            Mode lIlIlIIIlIIIIII;
        }

        static {
            Player = new Mode();
            Camera = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

