/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.utils;

import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class WindowConfig
implements ISerializable<WindowConfig> {
    public boolean expanded = true;
    public double y = -1.0;
    public double x = -1.0;

    @Override
    public WindowConfig fromTag(NbtCompound NbtCompound2) {
        this.expanded = NbtCompound2.getBoolean("expanded");
        this.x = NbtCompound2.getDouble("x");
        this.y = NbtCompound2.getDouble("y");
        return this;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putBoolean("expanded", this.expanded);
        NbtCompound2.putDouble("x", this.x);
        NbtCompound2.putDouble("y", this.y);
        return NbtCompound2;
    }
}

