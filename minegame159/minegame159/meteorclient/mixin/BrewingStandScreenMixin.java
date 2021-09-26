/*
 * Decompiled with CFR 0.151.
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
    public BrewingStandScreenMixin(BrewingStandScreenHandler BrewingStandScreenHandler2, PlayerInventory PlayerInventory2, Text Text2) {
        super((ScreenHandler)BrewingStandScreenHandler2, PlayerInventory2, Text2);
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

