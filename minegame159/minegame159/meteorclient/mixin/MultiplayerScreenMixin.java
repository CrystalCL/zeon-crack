/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MultiplayerScreen.class})
public class MultiplayerScreenMixin
extends Screen {
    private int textColor1;
    private int textColor2;
    private String loggedInAs;
    private int loggedInAsLength;

    public MultiplayerScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        this.textColor1 = Color.fromRGBA(255, 255, 255, 255);
        this.textColor2 = Color.fromRGBA(175, 175, 175, 255);
        this.loggedInAs = "Logged in as ";
        this.loggedInAsLength = this.textRenderer.getWidth(this.loggedInAs);
        this.addButton((ClickableWidget)new ButtonWidget(this.width - 75 - 3, 3, 75, 20, (Text)new LiteralText("Accounts"), this::lambda$onInit$0));
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack MatrixStack2, int n, int n2, float f, CallbackInfo callbackInfo) {
        this.textRenderer.drawWithShadow(MatrixStack2, this.loggedInAs, 3.0f, 3.0f, this.textColor1);
        this.textRenderer.drawWithShadow(MatrixStack2, Modules.get().get(NameProtect.class).getName(this.client.getSession().getUsername()), (float)(3 + this.loggedInAsLength), 3.0f, this.textColor2);
    }

    private void lambda$onInit$0(ButtonWidget ButtonWidget2) {
        this.client.openScreen((Screen)GuiThemes.get().accountsScreen());
    }
}

