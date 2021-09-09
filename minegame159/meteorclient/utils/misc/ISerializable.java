/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.utils.misc;

import net.minecraft.nbt.NbtCompound;

public interface ISerializable<T> {
    public NbtCompound toTag();

    public T fromTag(NbtCompound var1);
}

