/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CreativeInventoryScreen.class})
public interface CreativeInventoryScreenAccessor {
    @Accessor(value="selectedTab")
    public int getSelectedTab();
}

