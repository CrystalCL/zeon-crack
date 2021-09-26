/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import net.minecraft.client.network.PlayerListEntry;

public class PingHud
extends DoubleTextHudElement {
    public PingHud(HUD hUD) {
        super(hUD, "ping", "Displays your ping.", "Ping: ");
    }

    @Override
    protected String getRight() {
        if (this.isInEditor()) {
            return "0";
        }
        PlayerListEntry PlayerListEntry2 = this.mc.getNetworkHandler().getPlayerListEntry(this.mc.player.getUuid());
        if (PlayerListEntry2 != null) {
            return Integer.toString(PlayerListEntry2.getLatency());
        }
        return "0";
    }
}

