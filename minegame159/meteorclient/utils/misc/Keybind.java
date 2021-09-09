/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.utils.misc;

import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.input.Input;
import net.minecraft.nbt.NbtCompound;

public class Keybind
implements ISerializable<Keybind> {
    private /* synthetic */ boolean isKey;
    private /* synthetic */ int value;

    public static Keybind fromButton(int lllIIllllIlIl) {
        return new Keybind(false, lllIIllllIlIl);
    }

    public boolean isPressed() {
        Keybind lllIIllIlIllI;
        return lllIIllIlIllI.isKey ? Input.isKeyPressed(lllIIllIlIllI.value) : Input.isButtonPressed(lllIIllIlIllI.value);
    }

    @Override
    public Keybind fromTag(NbtCompound lllIIllIIlIIl) {
        Keybind lllIIllIIlIII;
        lllIIllIIlIII.isKey = lllIIllIIlIIl.getBoolean("isKey");
        lllIIllIIlIII.value = lllIIllIIlIIl.getInt("value");
        return lllIIllIIlIII;
    }

    public String toString() {
        Keybind lllIIllIlIlII;
        if (lllIIllIlIlII.value == -1) {
            return "None";
        }
        return lllIIllIlIlII.isKey ? Utils.getKeyName(lllIIllIlIlII.value) : Utils.getButtonName(lllIIllIlIlII.value);
    }

    public boolean matches(boolean lllIIllIlllIl, int lllIIllIlllII) {
        Keybind lllIIllIllllI;
        if (lllIIllIllllI.isKey != lllIIllIlllIl) {
            return false;
        }
        return lllIIllIllllI.value == lllIIllIlllII;
    }

    private Keybind(boolean lllIIllllllll, int lllIIlllllIll) {
        Keybind lllIIllllllIl;
        lllIIllllllIl.set(lllIIllllllll, lllIIlllllIll);
    }

    public static Keybind fromKey(int lllIIlllllIII) {
        return new Keybind(true, lllIIlllllIII);
    }

    public boolean canBindTo(boolean lllIIlllIllII, int lllIIlllIllIl) {
        if (lllIIlllIllII) {
            return true;
        }
        return lllIIlllIllIl != 0 && lllIIlllIllIl != 1;
    }

    public boolean isSet() {
        Keybind lllIIllllIIll;
        return lllIIllllIIll.value != -1;
    }

    public void set(boolean lllIIlllIIIll, int lllIIlllIIlIl) {
        lllIIlllIIlll.isKey = lllIIlllIIIll;
        lllIIlllIIlll.value = lllIIlllIIlIl;
    }

    @Override
    public NbtCompound toTag() {
        Keybind lllIIllIlIIII;
        NbtCompound lllIIllIIllll = new NbtCompound();
        lllIIllIIllll.putBoolean("isKey", lllIIllIlIIII.isKey);
        lllIIllIIllll.putInt("value", lllIIllIlIIII.value);
        return lllIIllIIllll;
    }
}

