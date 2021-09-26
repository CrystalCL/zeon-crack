/*
 * Decompiled with CFR 0.151.
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
    }

    @EventHandler
    private void onOpenScreenEvent(OpenScreenEvent openScreenEvent) {
        if (!(openScreenEvent.screen instanceof DeathScreen)) {
            return;
        }
        this.mc.player.requestRespawn();
        openScreenEvent.cancel();
    }
}

