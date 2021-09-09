/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.gui.screen.TitleScreen
 *  net.minecraft.client.util.math.MatrixStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import com.g00fy2.versioncompare.Version;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.gui.screens.NewUpdateScreen;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.network.HttpUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TitleScreen.class})
public class TitleScreenMixin
extends Screen {
    private final int WHITE = Color.fromRGBA(255, 255, 255, 255);
    private final int GRAY = Color.fromRGBA(175, 175, 175, 255);
    private String text1;
    private int text1Length;
    private String text2;
    private int text2Length;
    private String text3;
    private int text3Length;
    private String text4;
    private int text4Length;
    private String text5;
    private int text5Length;
    private String text6;
    private int fullLength;
    private int prevWidth;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo info) {
        this.text1 = "Meteor Client by ";
        this.text2 = "MineGame159";
        this.text3 = ", ";
        this.text4 = "squidoodly";
        this.text5 = " & ";
        this.text6 = "seasnail";
        this.text1Length = this.textRenderer.getWidth(this.text1);
        this.text2Length = this.textRenderer.getWidth(this.text2);
        this.text3Length = this.textRenderer.getWidth(this.text3);
        this.text4Length = this.textRenderer.getWidth(this.text4);
        this.text5Length = this.textRenderer.getWidth(this.text5);
        int text6Length = this.textRenderer.getWidth(this.text6);
        this.fullLength = this.text1Length + this.text2Length + this.text3Length + this.text4Length + this.text5Length + text6Length;
        this.prevWidth = 0;
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal=0)})
    private void onRenderIdkDude(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Utils.firstTimeTitleScreen) {
            Utils.firstTimeTitleScreen = false;
            MeteorClient.LOG.info("Checking latest version of Meteor Client");
            MeteorExecutor.execute(() -> HttpUtils.getLines("http://meteorclient.com/api/version", s -> {
                Version latestVer = new Version((String)s);
                if (latestVer.isHigherThan(Config.get().version)) {
                    MinecraftClient.getInstance().openScreen((Screen)new NewUpdateScreen(GuiThemes.get(), latestVer));
                }
            }));
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!Config.get().titleScreenCredits) {
            return;
        }
        this.prevWidth = 0;
        this.textRenderer.drawWithShadow(matrices, this.text1, (float)(this.width - this.fullLength - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text1Length;
        this.textRenderer.drawWithShadow(matrices, this.text2, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
        this.prevWidth += this.text2Length;
        this.textRenderer.drawWithShadow(matrices, this.text3, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text3Length;
        this.textRenderer.drawWithShadow(matrices, this.text4, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
        this.prevWidth += this.text4Length;
        this.textRenderer.drawWithShadow(matrices, this.text5, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text5Length;
        this.textRenderer.drawWithShadow(matrices, this.text6, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
    }
}

