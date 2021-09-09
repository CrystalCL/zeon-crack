/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.font.TextHandler
 *  net.minecraft.client.font.TextHandler.WidthRetriever
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package minegame159.meteorclient.mixin;

import net.minecraft.client.font.TextHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={TextHandler.class})
public interface TextHandlerAccessor {
    @Accessor(value="widthRetriever")
    public WidthRetriever getWidthRetriever();
}

