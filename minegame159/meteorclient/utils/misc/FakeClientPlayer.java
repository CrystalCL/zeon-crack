/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Difficulty
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.GameMode
 *  net.minecraft.world.World
 *  net.minecraft.network.ClientConnection
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.NetworkSide
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.BufferBuilderStorage
 *  net.minecraft.client.network.ClientPlayNetworkHandler
 *  net.minecraft.client.world.ClientWorld
 *  net.minecraft.client.world.ClientWorld$class_5271
 *  net.minecraft.client.network.PlayerListEntry
 *  net.minecraft.client.network.OtherClientPlayerEntity
 *  net.minecraft.client.render.WorldRenderer
 */
package minegame159.meteorclient.utils.misc;

import minegame159.meteorclient.mixin.DimensionTypeAccessor;
import minegame159.meteorclient.utils.misc.PlayerListEntryFactory;
import net.minecraft.world.Difficulty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.network.NetworkSide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;

public class FakeClientPlayer {
    private static /* synthetic */ ClientWorld world;
    private static /* synthetic */ PlayerEntity player;
    private static /* synthetic */ PlayerListEntry playerListEntry;
    private static final /* synthetic */ MinecraftClient mc;
    private static /* synthetic */ boolean needsNewEntry;
    private static /* synthetic */ String lastId;

    public FakeClientPlayer() {
        FakeClientPlayer lllllllllllllllllIIIIlIlIIIlIIll;
    }

    public static PlayerEntity getPlayer() {
        String lllllllllllllllllIIIIlIlIIIlIIIl = mc.getSession().getUuid();
        if (player == null || !lllllllllllllllllIIIIlIlIIIlIIIl.equals(lastId)) {
            player = new OtherClientPlayerEntity(world, mc.getSession().getProfile());
            lastId = lllllllllllllllllIIIIlIlIIIlIIIl;
            needsNewEntry = true;
        }
        return player;
    }

    public static PlayerListEntry getPlayerListEntry() {
        if (playerListEntry == null || needsNewEntry) {
            playerListEntry = new PlayerListEntry(PlayerListEntryFactory.create(mc.getSession().getProfile(), 0, GameMode.SURVIVAL, (Text)new LiteralText(mc.getSession().getProfile().getName())));
            needsNewEntry = false;
        }
        return playerListEntry;
    }

    public static void init() {
        world = new ClientWorld(new ClientPlayNetworkHandler(mc, null, new ClientConnection(NetworkSide.CLIENTBOUND), mc.getSession().getProfile()), new ClientWorld.class_5271(Difficulty.NORMAL, false, false), World.OVERWORLD, DimensionTypeAccessor.getOverworld(), 1, ((MinecraftClient)mc)::getProfiler, new WorldRenderer(mc, new BufferBuilderStorage()), false, 0L);
    }

    static {
        mc = MinecraftClient.getInstance();
    }
}

