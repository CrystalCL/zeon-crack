/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.utils.misc.FakeClientPlayer;
import minegame159.meteorclient.utils.network.Capes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayerEntity.class})
public class AbstractClientPlayerEntityMixin {
    @Inject(method={"getCapeTexture"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCapeTexture(CallbackInfoReturnable<Identifier> callbackInfoReturnable) {
        Identifier Identifier2 = Capes.get((PlayerEntity)this);
        if (Identifier2 != null) {
            callbackInfoReturnable.setReturnValue((Object)Identifier2);
        }
    }

    @Inject(method={"getPlayerListEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetPlayerListEntry(CallbackInfoReturnable<PlayerListEntry> callbackInfoReturnable) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            callbackInfoReturnable.setReturnValue((Object)FakeClientPlayer.getPlayerListEntry());
        }
    }

    @Inject(method={"isSpectator"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }

    @Inject(method={"isCreative"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsCreative(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
}

