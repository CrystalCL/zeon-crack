/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.resource.ResourceManager
 *  net.minecraft.util.profiler.Profiler
 *  net.minecraft.client.resource.SplashTextResourceSupplier
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SplashTextResourceSupplier.class})
public class SplashTextResourceSupplierMixin {
    @Inject(method={"apply"}, at={@At(value="HEAD")})
    private void onApply(List<String> list, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
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

