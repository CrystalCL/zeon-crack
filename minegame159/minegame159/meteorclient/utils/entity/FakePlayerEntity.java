/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;

public class FakePlayerEntity
extends OtherClientPlayerEntity {
    private final ClientWorld world;
    private final ClientPlayerEntity player;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public void despawn() {
        this.removed = true;
    }

    private void spawn() {
        this.world.addEntity(this.getEntityId(), (Entity)this);
    }

    private void copyPlayerModel(Entity Entity2, Entity Entity3) {
        DataTracker DataTracker2 = Entity2.getDataTracker();
        DataTracker DataTracker3 = Entity3.getDataTracker();
        Byte by = (Byte)DataTracker2.get(PlayerEntity.PLAYER_MODEL_PARTS);
        DataTracker3.set(PlayerEntity.PLAYER_MODEL_PARTS, (Object)by);
    }

    private void copyRotation() {
        this.headYaw = this.player.headYaw;
        this.bodyYaw = this.player.bodyYaw;
    }

    private void copyAttributes() {
        this.getAttributes().setFrom(this.player.getAttributes());
    }

    public FakePlayerEntity(String string, boolean bl, boolean bl2, float f) {
        super(FakePlayerEntity.mc.world, new GameProfile(FakePlayerEntity.mc.player.getUuid(), string));
        this.player = FakePlayerEntity.mc.player;
        this.world = FakePlayerEntity.mc.world;
        this.copyPositionAndRotation((Entity)this.player);
        this.copyPlayerModel((Entity)this.player, (Entity)this);
        this.copyRotation();
        this.copyAttributes();
        this.resetCapeMovement();
        this.setHealth(f);
        if (bl) {
            this.inventory.clone(FakePlayerEntity.mc.player.inventory);
        }
        if (bl2) {
            this.setGlowing(true);
        }
        this.spawn();
    }

    private void resetCapeMovement() {
        this.capeX = this.getX();
        this.capeY = this.getY();
        this.capeZ = this.getZ();
    }
}

