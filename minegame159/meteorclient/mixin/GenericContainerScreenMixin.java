/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerInventory
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.GenericContainerScreenHandler
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.GenericContainerScreen
 *  org.spongepowered.asm.mixin.Mixin
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AutoSteal;
import minegame159.meteorclient.utils.render.MeteorButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={GenericContainerScreen.class})
public abstract class GenericContainerScreenMixin
extends HandledScreen<GenericContainerScreenHandler>
implements ScreenHandlerProvider<GenericContainerScreenHandler> {
    public GenericContainerScreenMixin(GenericContainerScreenHandler container, PlayerInventory playerInventory, Text name) {
        super((ScreenHandler)container, playerInventory, name);
    }

    protected void init() {
        super.init();
        AutoSteal autoSteal = Modules.get().get(AutoSteal.class);
        if (autoSteal.isActive() && autoSteal.getStealButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 88, this.y + 3, 40, 12, (Text)new LiteralText("Steal"), button -> this.steal((GenericContainerScreenHandler)this.handler)));
        }
        if (autoSteal.isActive() && autoSteal.getDumpButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 46, this.y + 3, 40, 12, (Text)new LiteralText("Dump"), button -> this.dump((GenericContainerScreenHandler)this.handler)));
        }
        if (autoSteal.isActive() && autoSteal.getAutoStealEnabled()) {
            this.steal((GenericContainerScreenHandler)this.handler);
        } else if (autoSteal.isActive() && autoSteal.getAutoDumpEnabled()) {
            this.dump((GenericContainerScreenHandler)this.handler);
        }
    }

    private void steal(GenericContainerScreenHandler handler) {
        Modules.get().get(AutoSteal.class).stealAsync((ScreenHandler)handler);
    }

    private void dump(GenericContainerScreenHandler handler) {
        Modules.get().get(AutoSteal.class).dumpAsync((ScreenHandler)handler);
    }
}

