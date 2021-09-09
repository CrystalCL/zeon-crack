/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.network.PacketByteBuf
 *  net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket
 *  net.minecraft.util.Identifier
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.systems.modules.misc;

import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixin.CustomPayloadC2SPacketAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

public class VanillaSpoof
extends Module {
    public VanillaSpoof() {
        super(Categories.Misc, "vanilla-spoof", "When connecting to a server it spoofs the client name to be 'vanilla'.");
        VanillaSpoof lllllllllllllllllllIIIllllIIIIII;
        MeteorClient.EVENT_BUS.subscribe(lllllllllllllllllllIIIllllIIIIII.new Listener());
    }

    private class Listener {
        private Listener() {
            Listener llIlllIllIIIIII;
        }

        @EventHandler
        private void onPacketSend(PacketEvent.Send llIlllIlIlllIII) {
            Listener llIlllIlIlllIIl;
            if (!llIlllIlIlllIIl.VanillaSpoof.this.isActive() || !(llIlllIlIlllIII.packet instanceof CustomPayloadC2SPacket)) {
                return;
            }
            CustomPayloadC2SPacketAccessor llIlllIlIllIlll = (CustomPayloadC2SPacketAccessor)llIlllIlIlllIII.packet;
            Identifier llIlllIlIllIllI = llIlllIlIllIlll.getChannel();
            if (llIlllIlIllIllI.equals((Object)CustomPayloadC2SPacket.BRAND)) {
                llIlllIlIllIlll.setData(new PacketByteBuf(Unpooled.buffer()).writeString("vanilla"));
            } else if (StringUtils.containsIgnoreCase((CharSequence)llIlllIlIllIlll.getData().toString(StandardCharsets.UTF_8), (CharSequence)"fabric")) {
                llIlllIlIlllIII.cancel();
            }
        }
    }
}

