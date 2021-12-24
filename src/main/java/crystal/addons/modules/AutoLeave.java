package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.LiteralText;

public class AutoLeave extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> a = sgGeneral.add(new IntSetting.Builder().name("totem-count").description("Totem count when you kick").defaultValue(3).min(0).sliderMax(27).build());
    private final Setting<Mode> b = sgGeneral.add(new EnumSetting.Builder<Mode>().name("Mode").description("The mode.").defaultValue(Mode.MessageAndLeave).build());
    private final Setting<String> c = sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message .").defaultValue("Good Fight Bro :)").build());
    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder().name("auto-disable").description("Disable module after leave.").defaultValue(true).build());

    public AutoLeave() {
        super(CrystalCL.Exc, "auto-leave", "Kick from the server if you have small totems");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        int Count = InvUtils.find(new Item[]{Items.TOTEM_OF_UNDYING}).getCount();
        if (Count <= a.get() && b.get() == Mode.MessageAndLeave && autoDisable.get()) {
            mc.player.sendChatMessage(c.get());
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(new LiteralText("[AutoLeave] Your totem's count <= " + a)));
            toggle();
        } else if (Count <= a.get() && b.get() == Mode.NoneAndLeave && autoDisable.get()) {
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(new LiteralText("[AutoLeave] Your totem's count <= " + a)));
            toggle();
        } else if (Count <= a.get() && b.get() == Mode.MessageAndLeave) {
            mc.player.sendChatMessage(c.get());
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(new LiteralText("[AutoLeave] Your totem's count <= " + a)));
        } else if (Count <= a.get() && b.get() == Mode.NoneAndLeave) {
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(new LiteralText("[AutoLeave] Your totem's count <= " + a)));
        }
    }

    public enum Mode {
        MessageAndLeave,
        NoneAndLeave
    }
}
