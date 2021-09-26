/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={ItemEntity.class})
public class ItemEntityMixin
implements IItemEntity {
    private Vec3d rotation = new Vec3d(0.0, 0.0, 0.0);

    @Override
    public Vec3d getRotation() {
        return this.rotation;
    }

    @Override
    public void setRotation(Vec3d Vec3d2) {
        this.rotation = Vec3d2;
    }
}

