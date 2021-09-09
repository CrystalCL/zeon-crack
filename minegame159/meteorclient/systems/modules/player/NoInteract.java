/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;

public class NoInteract
extends Module {
    public NoInteract() {
        super(Categories.Player, "no-interact", "Blocks interactions with certain types of inputs.");
        NoInteract llllllllllllllllllIlIIlIIlIIlIll;
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent llllllllllllllllllIlIIlIIlIIIlll) {
        if (llllllllllllllllllIlIIlIIlIIIlll.screen == null) {
            return;
        }
        if (!llllllllllllllllllIlIIlIIlIIIlll.screen.isPauseScreen() && !(llllllllllllllllllIlIIlIIlIIIlll.screen instanceof AbstractInventoryScreen) && llllllllllllllllllIlIIlIIlIIIlll.screen instanceof HandledScreen) {
            llllllllllllllllllIlIIlIIlIIIlll.setCancelled(true);
        }
    }
}

