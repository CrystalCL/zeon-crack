/*
 * Decompiled with CFR 0.151.
 */
package club.minnced.discord.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordRichPresence
extends Structure {
    public int partyMax;
    public String largeImageKey;
    public String matchSecret;
    public int partySize;
    public byte instance;
    public String smallImageKey;
    public String spectateSecret;
    public long endTimestamp;
    public String partyId;
    public String details;
    public String joinSecret;
    private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance"));
    public String largeImageText;
    public long startTimestamp;
    public String smallImageText;
    public String state;

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }

    public DiscordRichPresence(String string) {
        this.setStringEncoding(string);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DiscordRichPresence)) {
            return false;
        }
        DiscordRichPresence discordRichPresence = (DiscordRichPresence)object;
        return this.startTimestamp == discordRichPresence.startTimestamp && this.endTimestamp == discordRichPresence.endTimestamp && this.partySize == discordRichPresence.partySize && this.partyMax == discordRichPresence.partyMax && this.instance == discordRichPresence.instance && Objects.equals(this.state, discordRichPresence.state) && Objects.equals(this.details, discordRichPresence.details) && Objects.equals(this.largeImageKey, discordRichPresence.largeImageKey) && Objects.equals(this.largeImageText, discordRichPresence.largeImageText) && Objects.equals(this.smallImageKey, discordRichPresence.smallImageKey) && Objects.equals(this.smallImageText, discordRichPresence.smallImageText) && Objects.equals(this.partyId, discordRichPresence.partyId) && Objects.equals(this.matchSecret, discordRichPresence.matchSecret) && Objects.equals(this.joinSecret, discordRichPresence.joinSecret) && Objects.equals(this.spectateSecret, discordRichPresence.spectateSecret);
    }

    public DiscordRichPresence() {
        this("UTF-8");
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
    }
}

