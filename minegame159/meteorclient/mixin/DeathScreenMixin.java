/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.screen.DeathScreen
 *  net.minecraft.client.gui.widget.ButtonWidget
 *  net.minecraft.client.gui.screen.Screen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BypassDeathScreen;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={DeathScreen.class})
public class DeathScreenMixin
extends Screen {
    protected DeathScreenMixin(Text title) {
        super(title);
    }

    @Inject(method={"init"}, at={@At(value="HEAD")})
    protected void init(CallbackInfo ci) {
        if (Modules.get().isActive(BypassDeathScreen.class)) {
            this.addButton((ClickableWidget)new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48, 200, 20, (Text)new LiteralText("Ghost Spectate"), buttonWidgetx -> {
                Modules.get().get(BypassDeathScreen.class).shouldBypass = true;
                this.client.openScreen(null);
            }));
        }
    }
}

