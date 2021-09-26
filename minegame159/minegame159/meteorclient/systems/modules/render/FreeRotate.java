/*
 * Decompiled with CFR 0.151.
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
    public final Setting<Boolean> togglePerpective;
    public final Setting<Mode> mode;
    private Perspective prePers;
    private final SettingGroup sgGeneral;
    public final Setting<Double> sensitivity;
    public float cameraYaw;
    private final SettingGroup sgArrows;
    private final Setting<Double> arrowSpeed;
    public final Setting<Boolean> arrows;
    public float cameraPitch;

    public FreeRotate() {
        super(Categories.Render, "free-rotate", "Allows more rotation options in third person.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgArrows = this.settings.createGroup("Arrows");
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which entity to rotate.").defaultValue(Mode.Player).build());
        this.togglePerpective = this.sgGeneral.add(new BoolSetting.Builder().name("toggle-perspective").description("Changes your perspective on toggle.").defaultValue(true).build());
        this.sensitivity = this.sgGeneral.add(new DoubleSetting.Builder().name("camera-sensitivity").description("How fast the camera moves in camera mode.").defaultValue(8.0).min(0.0).sliderMax(10.0).build());
        this.arrows = this.sgArrows.add(new BoolSetting.Builder().name("arrows-control-opposite").description("Allows you to control the other entities rotation with the arrow keys.").defaultValue(true).build());
        this.arrowSpeed = this.sgArrows.add(new DoubleSetting.Builder().name("arrow-speed").description("Rotation speed with arrow keys.").defaultValue(4.0).min(0.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.arrows.get().booleanValue()) {
            int n = 0;
            while ((double)n < this.arrowSpeed.get() * 2.0) {
                switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$render$FreeRotate$Mode[this.mode.get().ordinal()]) {
                    case 1: {
                        if (Input.isKeyPressed(263)) {
                            this.cameraYaw = (float)((double)this.cameraYaw - 0.5);
                        }
                        if (Input.isKeyPressed(262)) {
                            this.cameraYaw = (float)((double)this.cameraYaw + 0.5);
                        }
                        if (Input.isKeyPressed(265)) {
                            this.cameraPitch = (float)((double)this.cameraPitch - 0.5);
                        }
                        if (!Input.isKeyPressed(264)) break;
                        this.cameraPitch = (float)((double)this.cameraPitch + 0.5);
                        break;
                    }
                    case 2: {
                        if (Input.isKeyPressed(263)) {
                            this.mc.player.yaw = (float)((double)this.mc.player.yaw - 0.5);
                        }
                        if (Input.isKeyPressed(262)) {
                            this.mc.player.yaw = (float)((double)this.mc.player.yaw + 0.5);
                        }
                        if (Input.isKeyPressed(265)) {
                            this.mc.player.pitch = (float)((double)this.mc.player.pitch - 0.5);
                        }
                        if (!Input.isKeyPressed(264)) break;
                        this.mc.player.pitch = (float)((double)this.mc.player.pitch + 0.5);
                    }
                }
                ++n;
                if (4 > -1) continue;
                return;
            }
        }
        this.mc.player.pitch = Utils.clamp(this.mc.player.pitch, -90.0f, 90.0f);
        this.cameraPitch = Utils.clamp(this.cameraPitch, -90.0f, 90.0f);
    }

    public boolean playerMode() {
        return this.isActive() && this.mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && this.mode.get() == Mode.Player;
    }

    @Override
    public void onDeactivate() {
        if (this.mc.options.getPerspective() != this.prePers && this.togglePerpective.get().booleanValue()) {
            this.mc.options.setPerspective(this.prePers);
        }
    }

    public boolean cameraMode() {
        return this.isActive() && this.mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && this.mode.get() == Mode.Camera;
    }

    @Override
    public void onActivate() {
        this.cameraYaw = this.mc.player.yaw;
        this.cameraPitch = this.mc.player.pitch;
        this.prePers = this.mc.options.getPerspective();
        if (this.prePers != Perspective.THIRD_PERSON_BACK && this.togglePerpective.get().booleanValue()) {
            this.mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Camera;
        public static final /* enum */ Mode Player;
        private static final Mode[] $VALUES;

        static {
            Player = new Mode();
            Camera = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static Mode[] $values() {
            return new Mode[]{Player, Camera};
        }
    }
}

