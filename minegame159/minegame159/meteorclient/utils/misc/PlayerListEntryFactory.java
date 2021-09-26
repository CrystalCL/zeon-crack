/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.GameMode;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

public class PlayerListEntryFactory
extends PlayerListS2CPacket {
    private static final PlayerListEntryFactory INSTANCE = new PlayerListEntryFactory();

    public static Entry create(GameProfile gameProfile, int n, GameMode GameMode2, Text Text2) {
        return INSTANCE._create(gameProfile, n, GameMode2, Text2);
    }

    private Entry _create(GameProfile gameProfile, int n, GameMode GameMode2, Text Text2) {
        return new Entry((PlayerListS2CPacket)this, gameProfile, n, GameMode2, Text2);
    }
}

