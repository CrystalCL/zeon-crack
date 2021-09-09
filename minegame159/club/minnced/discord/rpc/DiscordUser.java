/*
 * Decompiled with CFR 0.150.
 */
package club.minnced.discord.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordUser
extends Structure {
    private static final /* synthetic */ List<String> FIELD_ORDER;
    public /* synthetic */ String userId;
    public /* synthetic */ String username;
    public /* synthetic */ String avatar;
    public /* synthetic */ String discriminator;

    public DiscordUser(String lllllllllllllllllIIlIlIllIllIIll) {
        DiscordUser lllllllllllllllllIIlIlIllIllIllI;
        lllllllllllllllllIIlIlIllIllIllI.setStringEncoding(lllllllllllllllllIIlIlIllIllIIll);
    }

    static {
        FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("userId", "username", "discriminator", "avatar"));
    }

    public DiscordUser() {
        lllllllllllllllllIIlIlIllIllIIII("UTF-8");
        DiscordUser lllllllllllllllllIIlIlIllIllIIII;
    }

    @Override
    public int hashCode() {
        DiscordUser lllllllllllllllllIIlIlIllIlIIlIl;
        return Objects.hash(lllllllllllllllllIIlIlIllIlIIlIl.userId, lllllllllllllllllIIlIlIllIlIIlIl.username, lllllllllllllllllIIlIlIllIlIIlIl.discriminator, lllllllllllllllllIIlIlIllIlIIlIl.avatar);
    }

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }

    @Override
    public boolean equals(Object lllllllllllllllllIIlIlIllIlIlIll) {
        DiscordUser lllllllllllllllllIIlIlIllIlIlIIl;
        if (lllllllllllllllllIIlIlIllIlIlIIl == lllllllllllllllllIIlIlIllIlIlIll) {
            return true;
        }
        if (!(lllllllllllllllllIIlIlIllIlIlIll instanceof DiscordUser)) {
            return false;
        }
        DiscordUser lllllllllllllllllIIlIlIllIlIlIlI = (DiscordUser)lllllllllllllllllIIlIlIllIlIlIll;
        return Objects.equals(lllllllllllllllllIIlIlIllIlIlIIl.userId, lllllllllllllllllIIlIlIllIlIlIlI.userId) && Objects.equals(lllllllllllllllllIIlIlIllIlIlIIl.username, lllllllllllllllllIIlIlIllIlIlIlI.username) && Objects.equals(lllllllllllllllllIIlIlIllIlIlIIl.discriminator, lllllllllllllllllIIlIlIllIlIlIlI.discriminator) && Objects.equals(lllllllllllllllllIIlIlIllIlIlIIl.avatar, lllllllllllllllllIIlIlIllIlIlIlI.avatar);
    }
}

