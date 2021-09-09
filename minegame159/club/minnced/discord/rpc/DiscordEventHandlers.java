/*
 * Decompiled with CFR 0.150.
 */
package club.minnced.discord.rpc;

import club.minnced.discord.rpc.DiscordUser;
import com.sun.jna.Callback;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordEventHandlers
extends Structure {
    private static final /* synthetic */ List<String> FIELD_ORDER;
    public /* synthetic */ OnJoinRequest joinRequest;
    public /* synthetic */ OnReady ready;
    public /* synthetic */ OnStatus errored;
    public /* synthetic */ OnGameUpdate joinGame;
    public /* synthetic */ OnGameUpdate spectateGame;
    public /* synthetic */ OnStatus disconnected;

    public DiscordEventHandlers() {
        DiscordEventHandlers lllllllllllllllllIIllllIlIlIlIll;
    }

    static {
        FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest"));
    }

    @Override
    public boolean equals(Object lllllllllllllllllIIllllIlIlIIIll) {
        DiscordEventHandlers lllllllllllllllllIIllllIlIlIIlll;
        if (lllllllllllllllllIIllllIlIlIIlll == lllllllllllllllllIIllllIlIlIIIll) {
            return true;
        }
        if (!(lllllllllllllllllIIllllIlIlIIIll instanceof DiscordEventHandlers)) {
            return false;
        }
        DiscordEventHandlers lllllllllllllllllIIllllIlIlIIlIl = (DiscordEventHandlers)lllllllllllllllllIIllllIlIlIIIll;
        return Objects.equals(lllllllllllllllllIIllllIlIlIIlll.ready, lllllllllllllllllIIllllIlIlIIlIl.ready) && Objects.equals(lllllllllllllllllIIllllIlIlIIlll.disconnected, lllllllllllllllllIIllllIlIlIIlIl.disconnected) && Objects.equals(lllllllllllllllllIIllllIlIlIIlll.errored, lllllllllllllllllIIllllIlIlIIlIl.errored) && Objects.equals(lllllllllllllllllIIllllIlIlIIlll.joinGame, lllllllllllllllllIIllllIlIlIIlIl.joinGame) && Objects.equals(lllllllllllllllllIIllllIlIlIIlll.spectateGame, lllllllllllllllllIIllllIlIlIIlIl.spectateGame) && Objects.equals(lllllllllllllllllIIllllIlIlIIlll.joinRequest, lllllllllllllllllIIllllIlIlIIlIl.joinRequest);
    }

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }

    @Override
    public int hashCode() {
        DiscordEventHandlers lllllllllllllllllIIllllIlIIlllll;
        return Objects.hash(lllllllllllllllllIIllllIlIIlllll.ready, lllllllllllllllllIIllllIlIIlllll.disconnected, lllllllllllllllllIIllllIlIIlllll.errored, lllllllllllllllllIIllllIlIIlllll.joinGame, lllllllllllllllllIIllllIlIIlllll.spectateGame, lllllllllllllllllIIllllIlIIlllll.joinRequest);
    }

    public static interface OnStatus
    extends Callback {
        public void accept(int var1, String var2);
    }

    public static interface OnReady
    extends Callback {
        public void accept(DiscordUser var1);
    }

    public static interface OnJoinRequest
    extends Callback {
        public void accept(DiscordUser var1);
    }

    public static interface OnGameUpdate
    extends Callback {
        public void accept(String var1);
    }
}

