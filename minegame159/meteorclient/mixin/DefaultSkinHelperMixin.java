/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.util.DefaultSkinHelper
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin;

import java.util.UUID;
import net.minecraft.client.util.DefaultSkinHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={DefaultSkinHelper.class})
public class DefaultSkinHelperMixin {
    @Inject(method={"shouldUseSlimModel"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onShouldUseSlimModel(UUID uuid, CallbackInfoReturnable<Boolean> info) {
        if (uuid == null) {
            info.setReturnValue((Object)false);
        }
    }
}

