/*
 * Decompiled with CFR 0.151.
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
    private static PlayerEntity player;
    private static String lastId;
    private static final MinecraftClient mc;
    private static PlayerListEntry playerListEntry;
    private static ClientWorld world;
    private static boolean needsNewEntry;

    public static PlayerEntity getPlayer() {
        String string = mc.getSession().getUuid();
        if (player == null || !string.equals(lastId)) {
            player = new OtherClientPlayerEntity(world, mc.getSession().getProfile());
            lastId = string;
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

    static {
        mc = MinecraftClient.getInstance();
    }

    public static void init() {
        world = new ClientWorld(new ClientPlayNetworkHandler(mc, null, new ClientConnection(NetworkSide.CLIENTBOUND), mc.getSession().getProfile()), new ClientWorld.class_5271(Difficulty.NORMAL, false, false), World.OVERWORLD, DimensionTypeAccessor.getOverworld(), 1, () -> ((MinecraftClient)mc).getProfiler(), new WorldRenderer(mc, new BufferBuilderStorage()), false, 0L);
    }
}

