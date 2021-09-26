/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixin.AbstractButtonWidgetAccessor;
import minegame159.meteorclient.mixin.ScreenMixin;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.AutoReconnect;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={DisconnectedScreen.class})
public class DisconnectedScreenMixin
extends ScreenMixin {
    @Shadow
    private int reasonHeight;
    private ButtonWidget reconnectBtn;
    private boolean timerActive = true;
    private double time;

    public DisconnectedScreenMixin() {
        this.time = Modules.get().get(AutoReconnect.class).time.get() * 20.0;
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onRenderBackground(CallbackInfo callbackInfo) {
        if (Modules.get().get(AutoReconnect.class).lastServerInfo != null) {
            int n = this.width / 2 - 100;
            int n2 = Math.min(this.height / 2 + this.reasonHeight / 2 + 32, this.height - 30);
            this.reconnectBtn = this.addButton(new ButtonWidget(n, n2, 200, 20, (Text)new LiteralText("Reconnecting in " + this.time / 20.0), this::lambda$onRenderBackground$0));
            this.timerActive = Modules.get().isActive(AutoReconnect.class);
        }
    }

    @Override
    public void tick() {
        if (this.timerActive) {
            if (this.time <= 0.0) {
                Utils.mc.openScreen((Screen)new ConnectScreen((Screen)new MultiplayerScreen((Screen)new TitleScreen()), Utils.mc, Modules.get().get(AutoReconnect.class).lastServerInfo));
            } else {
                this.time -= 1.0;
                if (this.reconnectBtn != null) {
                    ((AbstractButtonWidgetAccessor)this.reconnectBtn).setText((Text)new LiteralText(String.format("Reconnecting in %.1f", this.time / 20.0)));
                }
            }
        }
    }

    private void lambda$onRenderBackground$0(ButtonWidget ButtonWidget2) {
        this.timerActive = !this.timerActive;
    }
}

