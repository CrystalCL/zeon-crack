/*
 * Decompiled with CFR 0.151.
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
    protected DeathScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"init"}, at={@At(value="HEAD")})
    protected void init(CallbackInfo callbackInfo) {
        if (Modules.get().isActive(BypassDeathScreen.class)) {
            this.addButton((ClickableWidget)new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48, 200, 20, (Text)new LiteralText("Ghost Spectate"), this::lambda$init$0));
        }
    }

    private void lambda$init$0(ButtonWidget ButtonWidget2) {
        Modules.get().get(BypassDeathScreen.class).shouldBypass = true;
        this.client.openScreen(null);
    }
}

