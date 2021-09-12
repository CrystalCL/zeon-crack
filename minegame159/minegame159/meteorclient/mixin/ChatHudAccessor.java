/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.class_2561;
import net.minecraft.class_338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_338.class})
public interface ChatHudAccessor {
    @Invoker(value="addMessage")
    public void add(class_2561 var1, int var2);
}

