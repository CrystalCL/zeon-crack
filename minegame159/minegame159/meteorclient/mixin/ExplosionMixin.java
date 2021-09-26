/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.mixininterface.IExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Explosion.class})
public class ExplosionMixin
implements IExplosion {
    @Shadow
    @Final
    @Mutable
    private World world;
    @Shadow
    @Final
    @Mutable
    @Nullable
    private Entity entity;
    @Shadow
    @Final
    @Mutable
    private double x;
    @Shadow
    @Final
    @Mutable
    private double y;
    @Shadow
    @Final
    @Mutable
    private double z;
    @Shadow
    @Final
    @Mutable
    private float power;
    @Shadow
    @Final
    @Mutable
    private boolean createFire;
    @Shadow
    @Final
    @Mutable
    private Explosion.class_4179 destructionType;

    @Override
    public void set(Vec3d Vec3d2, float f, boolean bl) {
        this.world = MinecraftClient.getInstance().world;
        this.entity = null;
        this.x = Vec3d2.x;
        this.y = Vec3d2.y;
        this.z = Vec3d2.z;
        this.power = f;
        this.createFire = bl;
        this.destructionType = Explosion.class_4179.DESTROY;
    }
}

