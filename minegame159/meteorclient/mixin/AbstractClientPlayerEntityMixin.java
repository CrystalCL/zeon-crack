/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.network.PlayerListEntry
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private void onGetCapeTexture(CallbackInfoReturnable<Identifier> info) {
        Identifier id = Capes.get((PlayerEntity)this);
        if (id != null) {
            info.setReturnValue((Object)id);
        }
    }

    @Inject(method={"getPlayerListEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetPlayerListEntry(CallbackInfoReturnable<PlayerListEntry> info) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            info.setReturnValue((Object)FakeClientPlayer.getPlayerListEntry());
        }
    }

    @Inject(method={"isSpectator"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> info) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            info.setReturnValue((Object)false);
        }
    }

    @Inject(method={"isCreative"}, at={@At(value="HEAD")}, cancellable=true)
    private void onIsCreative(CallbackInfoReturnable<Boolean> info) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            info.setReturnValue((Object)false);
        }
    }
}

