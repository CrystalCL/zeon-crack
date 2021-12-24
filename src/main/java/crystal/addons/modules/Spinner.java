package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;

public class Spinner extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> b = sgGeneral.add(new IntSetting.Builder().name("speed").description("The speed at which you rotate.").defaultValue(5).min(0).sliderMax(100).build());
    private final Setting<Boolean> c = sgGeneral.add(new BoolSetting.Builder().name("pause").description("Stop rotate when you use Exp and Pearl.").defaultValue(true).build());

    private short a = 0;

    public Spinner() {
        super(CrystalCL.Exc, "spinner", "Many, very many rotate...");
    }

    @EventHandler
    public void onTick(TickEvent.Post post) {
        if (!c.get() || mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && mc.player.getMainHandStack().getItem() != Items.ENDER_PEARL && mc.player.getOffHandStack().getItem() != Items.EXPERIENCE_BOTTLE && mc.player.getOffHandStack().getItem() != Items.ENDER_PEARL) {
            a = (short) (a + b.get());
            if (a > 180) {
                a = -180;
            }

            Rotations.rotate(a, 0.0D);
        }
    }
}
