/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;

public class NbtUtils {
    public static <K, V extends ISerializable<?>> NbtCompound mapToTag(Map<K, V> map) {
        NbtCompound NbtCompound2 = new NbtCompound();
        for (K k : map.keySet()) {
            NbtCompound2.put(k.toString(), (NbtElement)((ISerializable)map.get(k)).toTag());
        }
        return NbtCompound2;
    }

    public static <T extends ISerializable<?>> NbtList listToTag(Iterable<T> iterable) {
        NbtList NbtList2 = new NbtList();
        for (ISerializable iSerializable : iterable) {
            NbtList2.add((Object)iSerializable.toTag());
        }
        return NbtList2;
    }

    public static <T> List<T> listFromTag(NbtList NbtList2, ToValue<T> toValue) {
        ArrayList<T> arrayList = new ArrayList<T>(NbtList2.size());
        for (NbtElement NbtElement2 : NbtList2) {
            T t = toValue.toValue(NbtElement2);
            if (t == null) continue;
            arrayList.add(t);
        }
        return arrayList;
    }

    public static <K, V> Map<K, V> mapFromTag(NbtCompound NbtCompound2, ToKey<K> toKey, ToValue<V> toValue) {
        HashMap<K, V> hashMap = new HashMap<K, V>(NbtCompound2.getSize());
        for (String string : NbtCompound2.getKeys()) {
            hashMap.put(toKey.toKey(string), toValue.toValue(NbtCompound2.get(string)));
        }
        return hashMap;
    }

    public static interface ToKey<T> {
        public T toKey(String var1);
    }

    public static interface ToValue<T> {
        public T toValue(NbtElement var1);
    }
}

