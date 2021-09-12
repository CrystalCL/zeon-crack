/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import java.util.List;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.BetterTab;
import net.minecraft.class_2561;
import net.minecraft.class_355;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_355.class})
public class PlayerListHudMixin {
    @ModifyVariable(method={"render"}, at=@At(value="INVOKE_ASSIGN", target="Ljava/util/List;subList(II)Ljava/util/List;"))
    private List<class_640> modifyCount(List<class_640> list) {
        return list.subList(0, Math.min(list.size(), Modules.get().get(BetterTab.class).getTabSize()));
    }

    @Inject(method={"getPlayerName"}, at={@At(value="HEAD")}, cancellable=true)
    public void getPlayerName(class_640 class_6402, CallbackInfoReturnable<class_2561> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)Modules.get().get(BetterTab.class).getPlayerName(class_6402));
    }
}

