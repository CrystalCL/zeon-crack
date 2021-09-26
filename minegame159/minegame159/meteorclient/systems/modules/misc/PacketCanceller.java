/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.settings.PacketBoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.network.PacketUtils;
import net.minecraft.network.Packet;

public class PacketCanceller
extends Module {
    private final Setting<Object2BooleanMap<Class<? extends Packet<?>>>> c2sPackets;
    public static Object2BooleanMap<Class<? extends Packet<?>>> C2S_PACKETS;
    private final SettingGroup sgGeneral;
    private final Setting<Object2BooleanMap<Class<? extends Packet<?>>>> s2cPackets;
    public static Object2BooleanMap<Class<? extends Packet<?>>> S2C_PACKETS;

    static {
        S2C_PACKETS = new Object2BooleanArrayMap();
        C2S_PACKETS = new Object2BooleanArrayMap();
        for (Class<? extends Packet<?>> clazz : PacketUtils.getS2CPackets()) {
            S2C_PACKETS.put(clazz, false);
        }
        for (Class<? extends Packet<?>> clazz : PacketUtils.getC2SPackets()) {
            C2S_PACKETS.put(clazz, false);
        }
    }

    @EventHandler(priority=201)
    private void onReceivePacket(PacketEvent.Receive receive) {
        if (this.s2cPackets.get().getBoolean(receive.packet.getClass())) {
            receive.cancel();
        }
    }

    public PacketCanceller() {
        super(Categories.Misc, "packet-canceller", "Allows you to cancel certain packets.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.s2cPackets = this.sgGeneral.add(new PacketBoolSetting.Builder().name("S2C-packets").description("Server-to-client packets to cancel.").defaultValue(S2C_PACKETS).build());
        this.c2sPackets = this.sgGeneral.add(new PacketBoolSetting.Builder().name("C2S-packets").description("Client-to-server packets to cancel.").defaultValue(C2S_PACKETS).build());
    }

    @EventHandler(priority=201)
    private void onSendPacket(PacketEvent.Send send) {
        if (this.c2sPackets.get().getBoolean(send.packet.getClass())) {
            send.cancel();
        }
    }
}

