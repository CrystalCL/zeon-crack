/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import net.minecraft.class_3300;
import net.minecraft.class_3695;
import net.minecraft.class_4008;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_4008.class})
public class SplashTextResourceSupplierMixin {
    @Inject(method={"apply"}, at={@At(value="HEAD")})
    private void onApply(List<String> list, class_3300 class_33002, class_3695 class_36952, CallbackInfo callbackInfo) {
        list.add("Meteor on Crack!");
        list.add("Star Meteor Client on GitHub!");
        list.add("Based utility mod.");
        list.add("based");
        list.add(":EZ:");
        list.add(":kekw:");
        list.add("OK retard.");
        list.add("cat");
        list.add("snale");
        list.add("monkey");
        list.add("mingane");
        list.add("https://bigrat.monster");
        list.add("snale moment");
        list.add("inertia moment");
        list.add("squidoodly based god");
        list.add("r/squidoodly");
    }
}

