/*
 * Decompiled with CFR 0.151.
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
    public GenericContainerScreenMixin(GenericContainerScreenHandler GenericContainerScreenHandler2, PlayerInventory PlayerInventory2, Text Text2) {
        super((ScreenHandler)GenericContainerScreenHandler2, PlayerInventory2, Text2);
    }

    protected void init() {
        super.init();
        AutoSteal autoSteal = Modules.get().get(AutoSteal.class);
        if (autoSteal.isActive() && autoSteal.getStealButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 88, this.y + 3, 40, 12, (Text)new LiteralText("Steal"), this::lambda$init$0));
        }
        if (autoSteal.isActive() && autoSteal.getDumpButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 46, this.y + 3, 40, 12, (Text)new LiteralText("Dump"), this::lambda$init$1));
        }
        if (autoSteal.isActive() && autoSteal.getAutoStealEnabled()) {
            this.steal((GenericContainerScreenHandler)this.handler);
        } else if (autoSteal.isActive() && autoSteal.getAutoDumpEnabled()) {
            this.dump((GenericContainerScreenHandler)this.handler);
        }
    }

    private void steal(GenericContainerScreenHandler GenericContainerScreenHandler2) {
        Modules.get().get(AutoSteal.class).stealAsync((ScreenHandler)GenericContainerScreenHandler2);
    }

    private void dump(GenericContainerScreenHandler GenericContainerScreenHandler2) {
        Modules.get().get(AutoSteal.class).dumpAsync((ScreenHandler)GenericContainerScreenHandler2);
    }

    private void lambda$init$1(MeteorButtonWidget meteorButtonWidget) {
        this.dump((GenericContainerScreenHandler)this.handler);
    }

    private void lambda$init$0(MeteorButtonWidget meteorButtonWidget) {
        this.steal((GenericContainerScreenHandler)this.handler);
    }
}

