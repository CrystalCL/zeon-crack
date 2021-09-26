/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.AutoSmelter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={AbstractFurnaceScreen.class})
public abstract class AbstractFurnaceScreenMixin<T extends AbstractFurnaceScreenHandler>
extends HandledScreen<T>
implements RecipeBookProvider {
    public AbstractFurnaceScreenMixin(T t, PlayerInventory PlayerInventory2, Text Text2) {
        super(t, PlayerInventory2, Text2);
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void onTick(CallbackInfo callbackInfo) {
        if (Modules.get().isActive(AutoSmelter.class)) {
            Modules.get().get(AutoSmelter.class).tick((AbstractFurnaceScreenHandler)this.handler);
        }
    }

    public void onClose() {
        super.onClose();
        if (Modules.get().isActive(AutoSmelter.class)) {
            Modules.get().get(AutoSmelter.class).onFurnaceClose();
        }
    }
}

