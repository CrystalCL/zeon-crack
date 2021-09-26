/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.nbt.NbtCompound;

public interface ISerializable<T> {
    public T fromTag(NbtCompound var1);

    public NbtCompound toTag();
}

