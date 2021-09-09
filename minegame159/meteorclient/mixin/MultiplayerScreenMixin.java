/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.widget.ButtonWidget
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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

    public MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo info) {
        this.textColor1 = Color.fromRGBA(255, 255, 255, 255);
        this.textColor2 = Color.fromRGBA(175, 175, 175, 255);
        this.loggedInAs = "Logged in as ";
        this.loggedInAsLength = this.textRenderer.getWidth(this.loggedInAs);
        this.addButton((ClickableWidget)new ButtonWidget(this.width - 75 - 3, 3, 75, 20, (Text)new LiteralText("Accounts"), button -> this.client.openScreen((Screen)GuiThemes.get().accountsScreen())));
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        this.textRenderer.drawWithShadow(matrices, this.loggedInAs, 3.0f, 3.0f, this.textColor1);
        this.textRenderer.drawWithShadow(matrices, Modules.get().get(NameProtect.class).getName(this.client.getSession().getUsername()), (float)(3 + this.loggedInAsLength), 3.0f, this.textColor2);
    }
}

