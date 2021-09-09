/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.DeathScreen
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn
extends Module {
    public AutoRespawn() {
        super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
        AutoRespawn lIlIlIIIl;
    }

    @EventHandler
    private void onOpenScreenEvent(OpenScreenEvent lIlIIlIll) {
        AutoRespawn lIlIIlllI;
        if (!(lIlIIlIll.screen instanceof DeathScreen)) {
            return;
        }
        lIlIIlllI.mc.player.requestRespawn();
        lIlIIlIll.cancel();
    }
}

