/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.util.Window
 *  net.minecraft.util.hit.HitResult
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.Mouse
 *  net.minecraft.util.profiler.Profiler
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.world.ClientWorld
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.ItemUseCrosshairTargetEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.game.ResourcePacksReloadedEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.mixininterface.IMinecraftClient;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.network.OnlinePlayers;
import net.minecraft.client.util.Window;
import net.minecraft.util.hit.HitResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MinecraftClient.class}, priority=1001)
public abstract class MinecraftClientMixin
implements IMinecraftClient {
    @Shadow
    public ClientWorld world;
    @Shadow
    @Final
    public Mouse mouse;
    @Shadow
    @Final
    private Window window;
    @Shadow
    @Nullable
    public Screen currentScreen;
    @Unique
    private boolean doItemUseCalled;
    @Unique
    private boolean rightClick;

    @Shadow
    protected abstract void doItemUse();

    @Shadow
    public abstract Profiler getProfiler();

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo info) {
        MeteorClient.INSTANCE.onInitializeClient();
    }

    @Inject(at={@At(value="HEAD")}, method={"tick"})
    private void onPreTick(CallbackInfo info) {
        OnlinePlayers.update();
        this.doItemUseCalled = false;
        this.getProfiler().push("meteor-client_pre_update");
        MeteorClient.EVENT_BUS.post(TickEvent.Pre.get());
        this.getProfiler().pop();
        if (this.rightClick && !this.doItemUseCalled) {
            this.doItemUse();
        }
        this.rightClick = false;
    }

    @Inject(at={@At(value="TAIL")}, method={"tick"})
    private void onTick(CallbackInfo info) {
        this.getProfiler().push("meteor-client_post_update");
        MeteorClient.EVENT_BUS.post(TickEvent.Post.get());
        this.getProfiler().pop();
    }

    @Inject(method={"doItemUse"}, at={@At(value="HEAD")})
    private void onDoItemUse(CallbackInfo info) {
        this.doItemUseCalled = true;
    }

    @Inject(method={"disconnect(Lnet/minecraft/client/gui/screen/Screen;)V"}, at={@At(value="HEAD")})
    private void onDisconnect(Screen screen, CallbackInfo info) {
        if (this.world != null) {
            MeteorClient.EVENT_BUS.post(GameLeftEvent.get());
        }
    }

    @Inject(method={"openScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void onOpenScreen(Screen screen, CallbackInfo info) {
        if (screen instanceof WidgetScreen) {
            screen.mouseMoved(this.mouse.getX() * this.window.getScaleFactor(), this.mouse.getY() * this.window.getScaleFactor());
        }
        OpenScreenEvent event = OpenScreenEvent.get(screen);
        MeteorClient.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
            return;
        }
        if (screen == null) {
            GuiKeyEvents.resetPostKeyEvents();
        }
    }

    @Redirect(method={"doItemUse"}, at=@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", ordinal=1))
    private HitResult doItemUseMinecraftClientCrosshairTargetProxy(MinecraftClient client) {
        return MeteorClient.EVENT_BUS.post(ItemUseCrosshairTargetEvent.get((HitResult)client.crosshairTarget)).target;
    }

    @ModifyVariable(method={"reloadResources"}, at=@At(value="STORE"), ordinal=0)
    private CompletableFuture<Void> onReloadResourcesNewCompletableFuture(CompletableFuture<Void> completableFuture) {
        completableFuture.thenRun(() -> MeteorClient.EVENT_BUS.post(ResourcePacksReloadedEvent.get()));
        return completableFuture;
    }

    @Inject(method={"getWindowTitle"}, at={@At(value="HEAD")}, cancellable=true)
    private void getTitle(CallbackInfoReturnable<String> cir) {
        if (Config.get() != null && Config.get().windowTitle) {
            cir.setReturnValue((Object)("Meteor Client " + Config.get().version.getOriginalString()));
        }
    }

    @Override
    public void rightClick() {
        this.rightClick = true;
    }
}

