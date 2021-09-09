/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
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

