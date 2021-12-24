package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;

public class FireWorksAura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder().name("range").description("The radius players can be in to be targeted.").defaultValue(5).sliderMin(0).sliderMax(10).build());
    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnMine = sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when placing.").defaultValue(false).build());
    private PlayerEntity target = null;

    public FireWorksAura() {
        super(CrystalCL.Exc, "firework-aura", "Kill player with using firework rockets.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        target = CityUtils.getPlayerTarget(range.get());
        if (target != null && !(mc.player.distanceTo(target) > (float) range.get())) {
            int fir;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof FireworkRocketItem) {
                fir = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                fir = InvUtils.findInHotbar(Items.FIREWORK_ROCKET).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (fir != -1) {
                if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.interactionManager.isBreakingBlock() || !pauseOnMine.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get())) {
                    BlockUtils.place(target.getBlockPos(), hand, fir, rotate.get(), 50, false, false, false);
                }
            }
        }
    }
}
