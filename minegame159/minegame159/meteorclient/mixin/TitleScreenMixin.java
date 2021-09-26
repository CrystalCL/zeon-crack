/*
 * Decompiled with CFR 0.151.
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

    public TitleScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
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
        int n = this.textRenderer.getWidth(this.text6);
        this.fullLength = this.text1Length + this.text2Length + this.text3Length + this.text4Length + this.text5Length + n;
        this.prevWidth = 0;
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal=0)})
    private void onRenderIdkDude(MatrixStack MatrixStack2, int n, int n2, float f, CallbackInfo callbackInfo) {
        if (Utils.firstTimeTitleScreen) {
            Utils.firstTimeTitleScreen = false;
            MeteorClient.LOG.info("Checking latest version of Meteor Client");
            MeteorExecutor.execute(TitleScreenMixin::lambda$onRenderIdkDude$1);
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack MatrixStack2, int n, int n2, float f, CallbackInfo callbackInfo) {
        if (!Config.get().titleScreenCredits) {
            return;
        }
        this.prevWidth = 0;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text1, (float)(this.width - this.fullLength - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text1Length;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text2, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
        this.prevWidth += this.text2Length;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text3, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text3Length;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text4, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
        this.prevWidth += this.text4Length;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text5, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.WHITE);
        this.prevWidth += this.text5Length;
        this.textRenderer.drawWithShadow(MatrixStack2, this.text6, (float)(this.width - this.fullLength + this.prevWidth - 3), 3.0f, this.GRAY);
    }

    private static void lambda$onRenderIdkDude$1() {
        HttpUtils.getLines("http://meteorclient.com/api/version", TitleScreenMixin::lambda$onRenderIdkDude$0);
    }

    private static void lambda$onRenderIdkDude$0(String string) {
        Version version = new Version(string);
        if (version.isHigherThan(Config.get().version)) {
            MinecraftClient.getInstance().openScreen((Screen)new NewUpdateScreen(GuiThemes.get(), version));
        }
    }
}

