/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.Collection;
import java.util.Iterator;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.RenderBossBarEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.NoRender;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BossBarHud.class})
public class BossBarHudMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void onRender(CallbackInfo callbackInfo) {
        if (Modules.get().get(NoRender.class).noBossBar()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Ljava/util/Collection;iterator()Ljava/util/Iterator;"))
    public Iterator<ClientBossBar> onRender(Collection<ClientBossBar> collection) {
        RenderBossBarEvent.BossIterator bossIterator = MeteorClient.EVENT_BUS.post(RenderBossBarEvent.BossIterator.get(collection.iterator()));
        return bossIterator.iterator;
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ClientBossBar;getName()Lnet/minecraft/text/Text;"))
    public Text onAsFormattedString(ClientBossBar ClientBossBar2) {
        RenderBossBarEvent.BossText bossText = MeteorClient.EVENT_BUS.post(RenderBossBarEvent.BossText.get(ClientBossBar2, ClientBossBar2.getName()));
        return bossText.name;
    }

    @ModifyConstant(method={"render"}, constant={@Constant(intValue=9, ordinal=1)})
    public int modifySpacingConstant(int n) {
        RenderBossBarEvent.BossSpacing bossSpacing = MeteorClient.EVENT_BUS.post(RenderBossBarEvent.BossSpacing.get(n));
        return bossSpacing.spacing;
    }
}

