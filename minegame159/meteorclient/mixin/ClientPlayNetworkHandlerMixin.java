/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket
 *  net.minecraft.network.packet.s2c.play.ExplosionS2CPacket
 *  net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket
 *  net.minecraft.network.packet.s2c.play.GameJoinS2CPacket
 *  net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket
 *  net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket
 *  net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket
 *  net.minecraft.world.chunk.WorldChunk
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.network.ClientPlayNetworkHandler
 *  net.minecraft.client.world.ClientWorld
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.EntityDestroyEvent;
import minegame159.meteorclient.events.entity.player.PickItemsEvent;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.packets.ContainerSlotUpdateEvent;
import minegame159.meteorclient.events.packets.PlaySoundPacketEvent;
import minegame159.meteorclient.events.world.ChunkDataEvent;
import minegame159.meteorclient.mixininterface.IExplosionS2CPacket;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.Velocity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow
    private MinecraftClient client;
    @Shadow
    private ClientWorld world;
    private boolean worldNotNull;

    @Inject(at={@At(value="HEAD")}, method={"onGameJoin"})
    private void onGameJoinHead(GameJoinS2CPacket packet, CallbackInfo info) {
        this.worldNotNull = this.world != null;
    }

    @Inject(at={@At(value="TAIL")}, method={"onGameJoin"})
    private void onGameJoinTail(GameJoinS2CPacket packet, CallbackInfo info) {
        if (this.worldNotNull) {
            MeteorClient.EVENT_BUS.post(GameLeftEvent.get());
        }
        MeteorClient.EVENT_BUS.post(GameJoinedEvent.get());
    }

    @Inject(at={@At(value="HEAD")}, method={"onPlaySound"})
    private void onPlaySound(PlaySoundS2CPacket packet, CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(PlaySoundPacketEvent.get(packet));
    }

    @Inject(method={"onChunkData"}, at={@At(value="TAIL")})
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
        WorldChunk chunk = this.client.world.getChunk(packet.getX(), packet.getZ());
        MeteorClient.EVENT_BUS.post(ChunkDataEvent.get(chunk));
    }

    @Inject(method={"onScreenHandlerSlotUpdate"}, at={@At(value="TAIL")})
    private void onContainerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(ContainerSlotUpdateEvent.get(packet));
    }

    @Inject(method={"onEntitiesDestroy"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/world/ClientWorld;removeEntity(I)V")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onEntityDestroy(EntitiesDestroyS2CPacket packet, CallbackInfo info, int i, int j) {
        MeteorClient.EVENT_BUS.post(EntityDestroyEvent.get(this.client.world.getEntityById(j)));
    }

    @Inject(method={"onExplosion"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift=At.Shift.AFTER)})
    private void onExplosionVelocity(ExplosionS2CPacket packet, CallbackInfo ci) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (!velocity.explosions.get().booleanValue()) {
            return;
        }
        ((IExplosionS2CPacket)packet).setVelocityX((float)((double)packet.getPlayerVelocityX() * velocity.getHorizontal()));
        ((IExplosionS2CPacket)packet).setVelocityY((float)((double)packet.getPlayerVelocityY() * velocity.getVertical()));
        ((IExplosionS2CPacket)packet).setVelocityZ((float)((double)packet.getPlayerVelocityZ() * velocity.getHorizontal()));
    }

    @Inject(method={"onItemPickupAnimation"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;", ordinal=0)})
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity itemEntity = this.client.world.getEntityById(packet.getEntityId());
        Entity entity = this.client.world.getEntityById(packet.getCollectorEntityId());
        if (itemEntity instanceof ItemEntity && entity == this.client.player) {
            MeteorClient.EVENT_BUS.post(PickItemsEvent.get(((ItemEntity)itemEntity).getStack(), packet.getStackAmount()));
        }
    }
}

