/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.misc.input;

public final class KeyAction
extends Enum<KeyAction> {
    private static final /* synthetic */ KeyAction[] $VALUES;
    public static final /* synthetic */ /* enum */ KeyAction Repeat;
    public static final /* synthetic */ /* enum */ KeyAction Press;
    public static final /* synthetic */ /* enum */ KeyAction Release;

    private KeyAction() {
        KeyAction llllllllllllllllllIlllIllIIllIIl;
    }

    public static KeyAction get(int llllllllllllllllllIlllIllIIlIlIl) {
        if (llllllllllllllllllIlllIllIIlIlIl == 1) {
            return Press;
        }
        if (llllllllllllllllllIlllIllIIlIlIl == 0) {
            return Release;
        }
        return Repeat;
    }

    static {
        Press = new KeyAction();
        Repeat = new KeyAction();
        Release = new KeyAction();
        $VALUES = KeyAction.$values();
    }

    public static KeyAction valueOf(String llllllllllllllllllIlllIllIIllllI) {
        return Enum.valueOf(KeyAction.class, llllllllllllllllllIlllIllIIllllI);
    }

    public static KeyAction[] values() {
        return (KeyAction[])$VALUES.clone();
    }

    private static /* synthetic */ KeyAction[] $values() {
        return new KeyAction[]{Press, Repeat, Release};
    }
}

