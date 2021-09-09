/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.entity;

public final class FriendType
extends Enum<FriendType> {
    public static final /* synthetic */ /* enum */ FriendType Neutral;
    public static final /* synthetic */ /* enum */ FriendType Trusted;
    private static final /* synthetic */ FriendType[] $VALUES;
    public static final /* synthetic */ /* enum */ FriendType Enemy;

    public static FriendType[] values() {
        return (FriendType[])$VALUES.clone();
    }

    private FriendType() {
        FriendType llllllllllllllllIlIlllIlllIIlllI;
    }

    public static FriendType valueOf(String llllllllllllllllIlIlllIlllIlIIlI) {
        return Enum.valueOf(FriendType.class, llllllllllllllllIlIlllIlllIlIIlI);
    }

    private static /* synthetic */ FriendType[] $values() {
        return new FriendType[]{Enemy, Neutral, Trusted};
    }

    static {
        Enemy = new FriendType();
        Neutral = new FriendType();
        Trusted = new FriendType();
        $VALUES = FriendType.$values();
    }
}

