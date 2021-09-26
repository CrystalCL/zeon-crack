/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.input.Input;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Keybind
implements ISerializable<Keybind> {
    private int value;
    private boolean isKey;

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public void set(boolean bl, int n) {
        this.isKey = bl;
        this.value = n;
    }

    public boolean isSet() {
        return this.value != -1;
    }

    @Override
    public Keybind fromTag(NbtCompound NbtCompound2) {
        this.isKey = NbtCompound2.getBoolean("isKey");
        this.value = NbtCompound2.getInt("value");
        return this;
    }

    public boolean matches(boolean bl, int n) {
        if (this.isKey != bl) {
            return false;
        }
        return this.value == n;
    }

    public boolean canBindTo(boolean bl, int n) {
        if (bl) {
            return true;
        }
        return n != 0 && n != 1;
    }

    public static Keybind fromButton(int n) {
        return new Keybind(false, n);
    }

    public static Keybind fromKey(int n) {
        return new Keybind(true, n);
    }

    private Keybind(boolean bl, int n) {
        this.set(bl, n);
    }

    public boolean isPressed() {
        return this.isKey ? Input.isKeyPressed(this.value) : Input.isButtonPressed(this.value);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putBoolean("isKey", this.isKey);
        NbtCompound2.putInt("value", this.value);
        return NbtCompound2;
    }

    public String toString() {
        if (this.value == -1) {
            return "None";
        }
        return this.isKey ? Utils.getKeyName(this.value) : Utils.getButtonName(this.value);
    }
}

