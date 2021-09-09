/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.hud.ChatHudLine
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.client.gui.hud.ChatHudLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ChatHudLine.class})
public interface ChatHudLineAccessor<T> {
    @Accessor(value="creationTick")
    public void setTimestamp(int var1);

    @Accessor(value="text")
    public void setText(T var1);

    @Accessor(value="id")
    public void setId(int var1);
}

