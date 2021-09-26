/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BetterTab;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerListHud.class})
public class PlayerListHudMixin {
    @ModifyVariable(method={"render"}, at=@At(value="INVOKE_ASSIGN", target="Ljava/util/List;subList(II)Ljava/util/List;"))
    private List<PlayerListEntry> modifyCount(List<PlayerListEntry> list) {
        return list.subList(0, Math.min(list.size(), Modules.get().get(BetterTab.class).getTabSize()));
    }

    @Inject(method={"getPlayerName"}, at={@At(value="HEAD")}, cancellable=true)
    public void getPlayerName(PlayerListEntry PlayerListEntry2, CallbackInfoReturnable<Text> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)Modules.get().get(BetterTab.class).getPlayerName(PlayerListEntry2));
    }
}

