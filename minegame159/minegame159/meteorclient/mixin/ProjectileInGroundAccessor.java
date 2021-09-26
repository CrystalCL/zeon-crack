/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={PersistentProjectileEntity.class})
public interface ProjectileInGroundAccessor {
    @Accessor(value="inGround")
    public boolean getInGround();
}

