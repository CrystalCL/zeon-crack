/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.world.GameMode
 *  net.minecraft.text.Text
 *  net.minecraft.network.packet.s2c.play.PlayerListS2CPacket
 *  net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry
 */
package minegame159.meteorclient.utils.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.GameMode;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

public class PlayerListEntryFactory
extends PlayerListS2CPacket {
    private static final /* synthetic */ PlayerListEntryFactory INSTANCE;

    public PlayerListEntryFactory() {
        PlayerListEntryFactory lllllllllllllllllIlllllIllIlIIII;
    }

    static {
        INSTANCE = new PlayerListEntryFactory();
    }

    public static Entry create(GameProfile lllllllllllllllllIlllllIllIIIllI, int lllllllllllllllllIlllllIllIIlIIl, GameMode lllllllllllllllllIlllllIllIIIlII, Text lllllllllllllllllIlllllIllIIIlll) {
        return INSTANCE._create(lllllllllllllllllIlllllIllIIIllI, lllllllllllllllllIlllllIllIIlIIl, lllllllllllllllllIlllllIllIIIlII, lllllllllllllllllIlllllIllIIIlll);
    }

    private Entry _create(GameProfile lllllllllllllllllIlllllIlIllllII, int lllllllllllllllllIlllllIlIlllIll, GameMode lllllllllllllllllIlllllIlIlllIlI, Text lllllllllllllllllIlllllIlIllIlII) {
        PlayerListEntryFactory lllllllllllllllllIlllllIlIllllIl;
        return new Entry((PlayerListS2CPacket)lllllllllllllllllIlllllIlIllllIl, lllllllllllllllllIlllllIlIllllII, lllllllllllllllllIlllllIlIlllIll, lllllllllllllllllIlllllIlIlllIlI, lllllllllllllllllIlllllIlIllIlII);
    }
}

