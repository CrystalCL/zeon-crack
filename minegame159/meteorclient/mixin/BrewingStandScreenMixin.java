/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerInventory
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.BrewingStandScreenHandler
 *  net.minecraft.text.Text
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.BrewingStandScreen
 *  org.spongepowered.asm.mixin.Mixin
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AutoBrewer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={BrewingStandScreen.class})
public abstract class BrewingStandScreenMixin
extends HandledScreen<BrewingStandScreenHandler> {
    public BrewingStandScreenMixin(BrewingStandScreenHandler container, PlayerInventory playerInventory, Text name) {
        super((ScreenHandler)container, playerInventory, name);
    }

    public void tick() {
        super.tick();
        if (Modules.get().isActive(AutoBrewer.class)) {
            Modules.get().get(AutoBrewer.class).tick((BrewingStandScreenHandler)this.handler);
        }
    }

    public void onClose() {
        if (Modules.get().isActive(AutoBrewer.class)) {
            Modules.get().get(AutoBrewer.class).onBrewingStandClose();
        }
        super.onClose();
    }
}

