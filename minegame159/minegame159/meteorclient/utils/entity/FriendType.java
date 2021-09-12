/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.entity;

public final class FriendType
extends Enum<FriendType> {
    public static final /* enum */ FriendType Neutral;
    private static final FriendType[] $VALUES;
    public static final /* enum */ FriendType Enemy;
    public static final /* enum */ FriendType Trusted;

    static {
        Enemy = new FriendType();
        Neutral = new FriendType();
        Trusted = new FriendType();
        $VALUES = FriendType.$values();
    }

    public static FriendType valueOf(String string) {
        return Enum.valueOf(FriendType.class, string);
    }

    public static FriendType[] values() {
        return (FriendType[])$VALUES.clone();
    }

    private static FriendType[] $values() {
        return new FriendType[]{Enemy, Neutral, Trusted};
    }
}

