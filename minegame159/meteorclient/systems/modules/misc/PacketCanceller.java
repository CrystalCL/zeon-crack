/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.network.Packet
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Object2BooleanMap<Class<? extends Packet<?>>>> c2sPackets;
    private final /* synthetic */ Setting<Object2BooleanMap<Class<? extends Packet<?>>>> s2cPackets;
    public static /* synthetic */ Object2BooleanMap<Class<? extends Packet<?>>> S2C_PACKETS;
    public static /* synthetic */ Object2BooleanMap<Class<? extends Packet<?>>> C2S_PACKETS;

    @EventHandler(priority=201)
    private void onSendPacket(PacketEvent.Send lllllllllllllllllIlIIIlllIIlIlll) {
        PacketCanceller lllllllllllllllllIlIIIlllIIllIII;
        if (lllllllllllllllllIlIIIlllIIllIII.c2sPackets.get().getBoolean(lllllllllllllllllIlIIIlllIIlIlll.packet.getClass())) {
            lllllllllllllllllIlIIIlllIIlIlll.cancel();
        }
    }

    public PacketCanceller() {
        super(Categories.Misc, "packet-canceller", "Allows you to cancel certain packets.");
        PacketCanceller lllllllllllllllllIlIIIlllIlIIIIl;
        lllllllllllllllllIlIIIlllIlIIIIl.sgGeneral = lllllllllllllllllIlIIIlllIlIIIIl.settings.getDefaultGroup();
        lllllllllllllllllIlIIIlllIlIIIIl.s2cPackets = lllllllllllllllllIlIIIlllIlIIIIl.sgGeneral.add(new PacketBoolSetting.Builder().name("S2C-packets").description("Server-to-client packets to cancel.").defaultValue(S2C_PACKETS).build());
        lllllllllllllllllIlIIIlllIlIIIIl.c2sPackets = lllllllllllllllllIlIIIlllIlIIIIl.sgGeneral.add(new PacketBoolSetting.Builder().name("C2S-packets").description("Client-to-server packets to cancel.").defaultValue(C2S_PACKETS).build());
    }

    static {
        S2C_PACKETS = new Object2BooleanArrayMap();
        C2S_PACKETS = new Object2BooleanArrayMap();
        for (Class<? extends Packet<?>> class_ : PacketUtils.getS2CPackets()) {
            S2C_PACKETS.put(class_, false);
        }
        for (Class<? extends Packet<?>> class_ : PacketUtils.getC2SPackets()) {
            C2S_PACKETS.put(class_, false);
        }
    }

    @EventHandler(priority=201)
    private void onReceivePacket(PacketEvent.Receive lllllllllllllllllIlIIIlllIIllIll) {
        PacketCanceller lllllllllllllllllIlIIIlllIIlllII;
        if (lllllllllllllllllIlIIIlllIIlllII.s2cPackets.get().getBoolean(lllllllllllllllllIlIIIlllIIllIll.packet.getClass())) {
            lllllllllllllllllIlIIIlllIIllIll.cancel();
        }
    }
}

