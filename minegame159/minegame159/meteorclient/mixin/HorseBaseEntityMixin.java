/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IHorseBaseEntity;
import net.minecraft.class_1496;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={class_1496.class})
public abstract class HorseBaseEntityMixin
implements IHorseBaseEntity {
    @Shadow
    protected abstract void method_6769(int var1, boolean var2);

    @Override
    public void setSaddled(boolean bl) {
        this.method_6769(4, bl);
    }
}

