/*
 * Decompiled with CFR 0.150.
 */
package club.minnced.discord.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordRichPresence
extends Structure {
    public /* synthetic */ String matchSecret;
    public /* synthetic */ String smallImageText;
    public /* synthetic */ String spectateSecret;
    public /* synthetic */ int partyMax;
    public /* synthetic */ int partySize;
    public /* synthetic */ String partyId;
    public /* synthetic */ long startTimestamp;
    public /* synthetic */ String largeImageText;
    public /* synthetic */ long endTimestamp;
    private static final /* synthetic */ List<String> FIELD_ORDER;
    public /* synthetic */ String joinSecret;
    public /* synthetic */ String details;
    public /* synthetic */ String smallImageKey;
    public /* synthetic */ String state;
    public /* synthetic */ String largeImageKey;
    public /* synthetic */ byte instance;

    public DiscordRichPresence(String lllllllllllllllllIIIIlIIllIlIIIl) {
        DiscordRichPresence lllllllllllllllllIIIIlIIllIlIlII;
        lllllllllllllllllIIIIlIIllIlIlII.setStringEncoding(lllllllllllllllllIIIIlIIllIlIIIl);
    }

    public DiscordRichPresence() {
        lllllllllllllllllIIIIlIIllIIllll("UTF-8");
        DiscordRichPresence lllllllllllllllllIIIIlIIllIIllll;
    }

    @Override
    public boolean equals(Object lllllllllllllllllIIIIlIIllIIIllI) {
        DiscordRichPresence lllllllllllllllllIIIIlIIllIIlIlI;
        if (lllllllllllllllllIIIIlIIllIIlIlI == lllllllllllllllllIIIIlIIllIIIllI) {
            return true;
        }
        if (!(lllllllllllllllllIIIIlIIllIIIllI instanceof DiscordRichPresence)) {
            return false;
        }
        DiscordRichPresence lllllllllllllllllIIIIlIIllIIlIII = (DiscordRichPresence)lllllllllllllllllIIIIlIIllIIIllI;
        return lllllllllllllllllIIIIlIIllIIlIlI.startTimestamp == lllllllllllllllllIIIIlIIllIIlIII.startTimestamp && lllllllllllllllllIIIIlIIllIIlIlI.endTimestamp == lllllllllllllllllIIIIlIIllIIlIII.endTimestamp && lllllllllllllllllIIIIlIIllIIlIlI.partySize == lllllllllllllllllIIIIlIIllIIlIII.partySize && lllllllllllllllllIIIIlIIllIIlIlI.partyMax == lllllllllllllllllIIIIlIIllIIlIII.partyMax && lllllllllllllllllIIIIlIIllIIlIlI.instance == lllllllllllllllllIIIIlIIllIIlIII.instance && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.state, lllllllllllllllllIIIIlIIllIIlIII.state) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.details, lllllllllllllllllIIIIlIIllIIlIII.details) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.largeImageKey, lllllllllllllllllIIIIlIIllIIlIII.largeImageKey) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.largeImageText, lllllllllllllllllIIIIlIIllIIlIII.largeImageText) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.smallImageKey, lllllllllllllllllIIIIlIIllIIlIII.smallImageKey) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.smallImageText, lllllllllllllllllIIIIlIIllIIlIII.smallImageText) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.partyId, lllllllllllllllllIIIIlIIllIIlIII.partyId) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.matchSecret, lllllllllllllllllIIIIlIIllIIlIII.matchSecret) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.joinSecret, lllllllllllllllllIIIIlIIllIIlIII.joinSecret) && Objects.equals(lllllllllllllllllIIIIlIIllIIlIlI.spectateSecret, lllllllllllllllllIIIIlIIllIIlIII.spectateSecret);
    }

    @Override
    public int hashCode() {
        DiscordRichPresence lllllllllllllllllIIIIlIIllIIIIll;
        return Objects.hash(lllllllllllllllllIIIIlIIllIIIIll.state, lllllllllllllllllIIIIlIIllIIIIll.details, lllllllllllllllllIIIIlIIllIIIIll.startTimestamp, lllllllllllllllllIIIIlIIllIIIIll.endTimestamp, lllllllllllllllllIIIIlIIllIIIIll.largeImageKey, lllllllllllllllllIIIIlIIllIIIIll.largeImageText, lllllllllllllllllIIIIlIIllIIIIll.smallImageKey, lllllllllllllllllIIIIlIIllIIIIll.smallImageText, lllllllllllllllllIIIIlIIllIIIIll.partyId, lllllllllllllllllIIIIlIIllIIIIll.partySize, lllllllllllllllllIIIIlIIllIIIIll.partyMax, lllllllllllllllllIIIIlIIllIIIIll.matchSecret, lllllllllllllllllIIIIlIIllIIIIll.joinSecret, lllllllllllllllllIIIIlIIllIIIIll.spectateSecret, lllllllllllllllllIIIIlIIllIIIIll.instance);
    }

    static {
        FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance"));
    }

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }
}

