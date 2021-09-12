/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_339;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_500;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_500.class})
public class MultiplayerScreenMixin
extends class_437 {
    private int textColor1;
    private int textColor2;
    private String loggedInAs;
    private int loggedInAsLength;

    public MultiplayerScreenMixin(class_2561 class_25612) {
        super(class_25612);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        this.textColor1 = Color.fromRGBA(255, 255, 255, 255);
        this.textColor2 = Color.fromRGBA(175, 175, 175, 255);
        this.loggedInAs = "Logged in as ";
        this.loggedInAsLength = this.field_22793.method_1727(this.loggedInAs);
        this.method_25411((class_339)new class_4185(this.field_22789 - 75 - 3, 3, 75, 20, (class_2561)new class_2585("Accounts"), this::lambda$onInit$0));
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(class_4587 class_45872, int n, int n2, float f, CallbackInfo callbackInfo) {
        this.field_22793.method_1720(class_45872, this.loggedInAs, 3.0f, 3.0f, this.textColor1);
        this.field_22793.method_1720(class_45872, Modules.get().get(NameProtect.class).getName(this.field_22787.method_1548().method_1676()), (float)(3 + this.loggedInAsLength), 3.0f, this.textColor2);
    }

    private void lambda$onInit$0(class_4185 class_41852) {
        this.field_22787.method_1507((class_437)GuiThemes.get().accountsScreen());
    }
}

