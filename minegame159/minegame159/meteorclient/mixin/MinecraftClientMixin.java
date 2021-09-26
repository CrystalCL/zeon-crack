/*
 * Decompiled with CFR 0.151.
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
    private void onInit(CallbackInfo callbackInfo) {
        MeteorClient.INSTANCE.onInitializeClient();
    }

    @Inject(at={@At(value="HEAD")}, method={"tick"})
    private void onPreTick(CallbackInfo callbackInfo) {
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
    private void onTick(CallbackInfo callbackInfo) {
        this.getProfiler().push("meteor-client_post_update");
        MeteorClient.EVENT_BUS.post(TickEvent.Post.get());
        this.getProfiler().pop();
    }

    @Inject(method={"doItemUse"}, at={@At(value="HEAD")})
    private void onDoItemUse(CallbackInfo callbackInfo) {
        this.doItemUseCalled = true;
    }

    @Inject(method={"disconnect(Lnet/minecraft/client/gui/screen/Screen;)V"}, at={@At(value="HEAD")})
    private void onDisconnect(Screen Screen2, CallbackInfo callbackInfo) {
        if (this.world != null) {
            MeteorClient.EVENT_BUS.post(GameLeftEvent.get());
        }
    }

    @Inject(method={"openScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void onOpenScreen(Screen Screen2, CallbackInfo callbackInfo) {
        if (Screen2 instanceof WidgetScreen) {
            Screen2.mouseMoved(this.mouse.getX() * this.window.getScaleFactor(), this.mouse.getY() * this.window.getScaleFactor());
        }
        OpenScreenEvent openScreenEvent = OpenScreenEvent.get(Screen2);
        MeteorClient.EVENT_BUS.post(openScreenEvent);
        if (openScreenEvent.isCancelled()) {
            callbackInfo.cancel();
            return;
        }
        if (Screen2 == null) {
            GuiKeyEvents.resetPostKeyEvents();
        }
    }

    @Redirect(method={"doItemUse"}, at=@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", ordinal=1))
    private HitResult doItemUseMinecraftClientCrosshairTargetProxy(MinecraftClient MinecraftClient2) {
        return MeteorClient.EVENT_BUS.post(ItemUseCrosshairTargetEvent.get((HitResult)MinecraftClient2.crosshairTarget)).target;
    }

    @ModifyVariable(method={"reloadResources"}, at=@At(value="STORE"), ordinal=0)
    private CompletableFuture<Void> onReloadResourcesNewCompletableFuture(CompletableFuture<Void> completableFuture) {
        completableFuture.thenRun(MinecraftClientMixin::lambda$onReloadResourcesNewCompletableFuture$0);
        return completableFuture;
    }

    @Inject(method={"getWindowTitle"}, at={@At(value="HEAD")}, cancellable=true)
    private void getTitle(CallbackInfoReturnable<String> callbackInfoReturnable) {
        if (Config.get() != null && Config.get().windowTitle) {
            callbackInfoReturnable.setReturnValue((Object)("Meteor Client " + Config.get().version.getOriginalString()));
        }
    }

    @Override
    public void rightClick() {
        this.rightClick = true;
    }

    private static void lambda$onReloadResourcesNewCompletableFuture$0() {
        MeteorClient.EVENT_BUS.post(ResourcePacksReloadedEvent.get());
    }
}

