package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class Strafe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder().name("speed").description(".").defaultValue(5.0D).min(0.0D).sliderMax(20.0D).build());

    public Strafe() {
        super(CrystalCL.PvE, "strafe", "speed");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        double speedstrafe = speed.get() / 3.0D;
        double forward = mc.player.forwardSpeed;
        double strafe = mc.player.sidewaysSpeed;
        float yaw = mc.player.getYaw();
        if (!mc.player.isFallFlying()) {
            if (forward == 0.0D && strafe == 0.0D) {
                mc.player.setVelocity(0.0D, mc.player.getVelocity().y, 0.0D);
            } else {
                if (forward != 0.0D) {
                    if (strafe > 0.0D) {
                        yaw += (float) (forward > 0.0D ? -45 : 45);
                    } else if (strafe < 0.0D) {
                        yaw += (float) (forward > 0.0D ? 45 : -45);
                    }

                    strafe = 0.0D;
                    if (forward > 0.0D) {
                        forward = 1.0D;
                    } else if (forward < 0.0D) {
                        forward = -1.0D;
                    }
                }

                mc.player.setVelocity(forward * speedstrafe * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speedstrafe * Math.sin(Math.toRadians((yaw + 90.0F))), mc.player.getVelocity().y, forward * speedstrafe * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speedstrafe * Math.cos(Math.toRadians((yaw + 90.0F))));
            }
        }
    }
}
