/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc.input;

public final class KeyAction
extends Enum<KeyAction> {
    public static final /* enum */ KeyAction Repeat;
    private static final KeyAction[] $VALUES;
    public static final /* enum */ KeyAction Release;
    public static final /* enum */ KeyAction Press;

    public static KeyAction get(int n) {
        if (n == 1) {
            return Press;
        }
        if (n == 0) {
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

    public static KeyAction[] values() {
        return (KeyAction[])$VALUES.clone();
    }

    public static KeyAction valueOf(String string) {
        return Enum.valueOf(KeyAction.class, string);
    }

    private static KeyAction[] $values() {
        return new KeyAction[]{Press, Repeat, Release};
    }
}

