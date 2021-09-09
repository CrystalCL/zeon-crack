/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerInventory
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen
 *  org.spongepowered.asm.mixin.Mixin
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
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super((ScreenHandler)handler, inventory, title);
    }

    protected void init() {
        super.init();
        AutoSteal autoSteal = Modules.get().get(AutoSteal.class);
        if (autoSteal.isActive() && autoSteal.getStealButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 88, this.y + 3, 40, 12, (Text)new LiteralText("Steal"), button -> this.steal((ShulkerBoxScreenHandler)this.handler)));
        }
        if (autoSteal.isActive() && autoSteal.getDumpButtonEnabled()) {
            this.addButton((ClickableWidget)new MeteorButtonWidget(this.x + this.backgroundWidth - 46, this.y + 3, 40, 12, (Text)new LiteralText("Dump"), button -> this.dump((ShulkerBoxScreenHandler)this.handler)));
        }
        if (autoSteal.isActive() && autoSteal.getAutoStealEnabled()) {
            this.steal((ShulkerBoxScreenHandler)this.handler);
        } else if (autoSteal.isActive() && autoSteal.getAutoDumpEnabled()) {
            this.dump((ShulkerBoxScreenHandler)this.handler);
        }
    }

    private void steal(ShulkerBoxScreenHandler handler) {
        Modules.get().get(AutoSteal.class).stealAsync((ScreenHandler)handler);
    }

    private void dump(ShulkerBoxScreenHandler handler) {
        Modules.get().get(AutoSteal.class).dumpAsync((ScreenHandler)handler);
    }
}

