/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AutoSteal;
import minegame159.meteorclient.utils.render.MeteorButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={ShulkerBoxScreen.class})
public abstract class ShulkerBoxScreenMixin
extends HandledScreen<ShulkerBoxScreenHandler> {
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler ShulkerBoxScreenHandler2, PlayerInventory PlayerInventory2, Text Text2) {
        super((ScreenHandler)ShulkerBoxScreenHandler2, PlayerInventory2, Text2);
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
            this.steal((ShulkerBoxScreenHandler)this.handler);
        } else if (autoSteal.isActive() && autoSteal.getAutoDumpEnabled()) {
            this.dump((ShulkerBoxScreenHandler)this.handler);
        }
    }

    private void steal(ShulkerBoxScreenHandler ShulkerBoxScreenHandler2) {
        Modules.get().get(AutoSteal.class).stealAsync((ScreenHandler)ShulkerBoxScreenHandler2);
    }

    private void dump(ShulkerBoxScreenHandler ShulkerBoxScreenHandler2) {
        Modules.get().get(AutoSteal.class).dumpAsync((ScreenHandler)ShulkerBoxScreenHandler2);
    }

    private void lambda$init$1(MeteorButtonWidget meteorButtonWidget) {
        this.dump((ShulkerBoxScreenHandler)this.handler);
    }

    private void lambda$init$0(MeteorButtonWidget meteorButtonWidget) {
        this.steal((ShulkerBoxScreenHandler)this.handler);
    }
}

