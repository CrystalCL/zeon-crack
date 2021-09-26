/*
 * Decompiled with CFR 0.151.
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
    private void onGameJoinHead(GameJoinS2CPacket GameJoinS2CPacket2, CallbackInfo callbackInfo) {
        this.worldNotNull = this.world != null;
    }

    @Inject(at={@At(value="TAIL")}, method={"onGameJoin"})
    private void onGameJoinTail(GameJoinS2CPacket GameJoinS2CPacket2, CallbackInfo callbackInfo) {
        if (this.worldNotNull) {
            MeteorClient.EVENT_BUS.post(GameLeftEvent.get());
        }
        MeteorClient.EVENT_BUS.post(GameJoinedEvent.get());
    }

    @Inject(at={@At(value="HEAD")}, method={"onPlaySound"})
    private void onPlaySound(PlaySoundS2CPacket PlaySoundS2CPacket2, CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(PlaySoundPacketEvent.get(PlaySoundS2CPacket2));
    }

    @Inject(method={"onChunkData"}, at={@At(value="TAIL")})
    private void onChunkData(ChunkDataS2CPacket ChunkDataS2CPacket2, CallbackInfo callbackInfo) {
        WorldChunk WorldChunk2 = this.client.world.getChunk(ChunkDataS2CPacket2.getX(), ChunkDataS2CPacket2.getZ());
        MeteorClient.EVENT_BUS.post(ChunkDataEvent.get(WorldChunk2));
    }

    @Inject(method={"onScreenHandlerSlotUpdate"}, at={@At(value="TAIL")})
    private void onContainerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket ScreenHandlerSlotUpdateS2CPacket2, CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(ContainerSlotUpdateEvent.get(ScreenHandlerSlotUpdateS2CPacket2));
    }

    @Inject(method={"onEntitiesDestroy"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/world/ClientWorld;removeEntity(I)V")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onEntityDestroy(EntitiesDestroyS2CPacket EntitiesDestroyS2CPacket2, CallbackInfo callbackInfo, int n, int n2) {
        MeteorClient.EVENT_BUS.post(EntityDestroyEvent.get(this.client.world.getEntityById(n2)));
    }

    @Inject(method={"onExplosion"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift=At.Shift.AFTER)})
    private void onExplosionVelocity(ExplosionS2CPacket ExplosionS2CPacket2, CallbackInfo callbackInfo) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (!velocity.explosions.get().booleanValue()) {
            return;
        }
        ((IExplosionS2CPacket)ExplosionS2CPacket2).setVelocityX((float)((double)ExplosionS2CPacket2.getPlayerVelocityX() * velocity.getHorizontal()));
        ((IExplosionS2CPacket)ExplosionS2CPacket2).setVelocityY((float)((double)ExplosionS2CPacket2.getPlayerVelocityY() * velocity.getVertical()));
        ((IExplosionS2CPacket)ExplosionS2CPacket2).setVelocityZ((float)((double)ExplosionS2CPacket2.getPlayerVelocityZ() * velocity.getHorizontal()));
    }

    @Inject(method={"onItemPickupAnimation"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;", ordinal=0)})
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket ItemPickupAnimationS2CPacket2, CallbackInfo callbackInfo) {
        Entity Entity2 = this.client.world.getEntityById(ItemPickupAnimationS2CPacket2.getEntityId());
        Entity Entity3 = this.client.world.getEntityById(ItemPickupAnimationS2CPacket2.getCollectorEntityId());
        if (Entity2 instanceof ItemEntity && Entity3 == this.client.player) {
            MeteorClient.EVENT_BUS.post(PickItemsEvent.get(((ItemEntity)Entity2).getStack(), ItemPickupAnimationS2CPacket2.getStackAmount()));
        }
    }
}

