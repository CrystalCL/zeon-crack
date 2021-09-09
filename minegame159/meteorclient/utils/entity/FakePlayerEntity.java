/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.data.DataTracker
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.world.ClientWorld
 *  net.minecraft.client.network.OtherClientPlayerEntity
 *  net.minecraft.client.network.ClientPlayerEntity
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
    private static final /* synthetic */ MinecraftClient mc;
    private final /* synthetic */ ClientPlayerEntity player;
    private final /* synthetic */ ClientWorld world;

    private void copyPlayerModel(Entity lllllIIIlI, Entity lllllIIIIl) {
        DataTracker lllllIIIII = lllllIIIlI.getDataTracker();
        DataTracker llllIlllll = lllllIIIIl.getDataTracker();
        Byte llllIllllI = (Byte)lllllIIIII.get(PlayerEntity.PLAYER_MODEL_PARTS);
        llllIlllll.set(PlayerEntity.PLAYER_MODEL_PARTS, (Object)llllIllllI);
    }

    private void resetCapeMovement() {
        FakePlayerEntity llllIlIIIl;
        llllIlIIIl.capeX = llllIlIIIl.getX();
        llllIlIIIl.capeY = llllIlIIIl.getY();
        llllIlIIIl.capeZ = llllIlIIIl.getZ();
    }

    private void copyAttributes() {
        FakePlayerEntity llllIlIIll;
        llllIlIIll.getAttributes().setFrom(llllIlIIll.player.getAttributes());
    }

    public FakePlayerEntity(String lllllIllII, boolean lllllIlIll, boolean lllllIllll, float lllllIlllI) {
        super(FakePlayerEntity.mc.world, new GameProfile(FakePlayerEntity.mc.player.getUuid(), lllllIllII));
        FakePlayerEntity lllllIllIl;
        lllllIllIl.player = FakePlayerEntity.mc.player;
        lllllIllIl.world = FakePlayerEntity.mc.world;
        lllllIllIl.copyPositionAndRotation((Entity)lllllIllIl.player);
        lllllIllIl.copyPlayerModel((Entity)lllllIllIl.player, (Entity)lllllIllIl);
        lllllIllIl.copyRotation();
        lllllIllIl.copyAttributes();
        lllllIllIl.resetCapeMovement();
        lllllIllIl.setHealth(lllllIlllI);
        if (lllllIlIll) {
            lllllIllIl.inventory.clone(FakePlayerEntity.mc.player.inventory);
        }
        if (lllllIllll) {
            lllllIllIl.setGlowing(true);
        }
        lllllIllIl.spawn();
    }

    public void despawn() {
        llllIIlIlI.removed = true;
    }

    static {
        mc = MinecraftClient.getInstance();
    }

    private void spawn() {
        FakePlayerEntity llllIIlllI;
        llllIIlllI.world.addEntity(llllIIlllI.getEntityId(), (Entity)llllIIlllI);
    }

    private void copyRotation() {
        FakePlayerEntity llllIlIllI;
        llllIlIllI.headYaw = llllIlIllI.player.headYaw;
        llllIlIllI.bodyYaw = llllIlIllI.player.bodyYaw;
    }
}

