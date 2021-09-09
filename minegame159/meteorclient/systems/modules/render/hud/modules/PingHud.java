/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.PlayerListEntry
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import net.minecraft.client.network.PlayerListEntry;

public class PingHud
extends DoubleTextHudElement {
    @Override
    protected String getRight() {
        PingHud lIlIlllIllIlll;
        if (lIlIlllIllIlll.isInEditor()) {
            return "0";
        }
        PlayerListEntry lIlIlllIllIllI = lIlIlllIllIlll.mc.getNetworkHandler().getPlayerListEntry(lIlIlllIllIlll.mc.player.getUuid());
        if (lIlIlllIllIllI != null) {
            return Integer.toString(lIlIlllIllIllI.getLatency());
        }
        return "0";
    }

    public PingHud(HUD lIlIlllIllllII) {
        super(lIlIlllIllllII, "ping", "Displays your ping.", "Ping: ");
        PingHud lIlIlllIlllIll;
    }
}

