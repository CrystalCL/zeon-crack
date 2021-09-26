/*
 * Decompiled with CFR 0.151.
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

