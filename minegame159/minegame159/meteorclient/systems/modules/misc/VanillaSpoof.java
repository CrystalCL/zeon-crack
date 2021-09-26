/*
 * Decompiled with CFR 0.151.
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
        MeteorClient.EVENT_BUS.subscribe(new Listener(this, null));
    }

    private class Listener {
        final VanillaSpoof this$0;

        private Listener(VanillaSpoof vanillaSpoof) {
            this.this$0 = vanillaSpoof;
        }

        Listener(VanillaSpoof vanillaSpoof, 1 var2_2) {
            this(vanillaSpoof);
        }

        @EventHandler
        private void onPacketSend(PacketEvent.Send send) {
            if (!this.this$0.isActive() || !(send.packet instanceof CustomPayloadC2SPacket)) {
                return;
            }
            CustomPayloadC2SPacketAccessor customPayloadC2SPacketAccessor = (CustomPayloadC2SPacketAccessor)send.packet;
            Identifier Identifier2 = customPayloadC2SPacketAccessor.getChannel();
            if (Identifier2.equals((Object)CustomPayloadC2SPacket.BRAND)) {
                customPayloadC2SPacketAccessor.setData(new PacketByteBuf(Unpooled.buffer()).writeString("vanilla"));
            } else if (StringUtils.containsIgnoreCase((CharSequence)customPayloadC2SPacketAccessor.getData().toString(StandardCharsets.UTF_8), (CharSequence)"fabric")) {
                send.cancel();
            }
        }
    }
}

