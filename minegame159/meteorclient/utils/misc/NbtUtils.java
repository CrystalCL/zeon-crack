/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
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
    public NbtUtils() {
        NbtUtils lllIlIIIllIlII;
    }

    public static <K, V extends ISerializable<?>> NbtCompound mapToTag(Map<K, V> lllIlIIIIlIIIl) {
        NbtCompound lllIlIIIIlIIII = new NbtCompound();
        for (K lllIlIIIIlIIlI : lllIlIIIIlIIIl.keySet()) {
            lllIlIIIIlIIII.put(lllIlIIIIlIIlI.toString(), (NbtElement)((ISerializable)lllIlIIIIlIIIl.get(lllIlIIIIlIIlI)).toTag());
        }
        return lllIlIIIIlIIII;
    }

    public static <T> List<T> listFromTag(NbtList lllIlIIIIlllII, ToValue<T> lllIlIIIIllIll) {
        ArrayList<T> lllIlIIIIlllIl = new ArrayList<T>(lllIlIIIIlllII.size());
        for (NbtElement lllIlIIIlIIIII : lllIlIIIIlllII) {
            T lllIlIIIlIIIIl = lllIlIIIIllIll.toValue(lllIlIIIlIIIII);
            if (lllIlIIIlIIIIl == null) continue;
            lllIlIIIIlllIl.add(lllIlIIIlIIIIl);
        }
        return lllIlIIIIlllIl;
    }

    public static <T extends ISerializable<?>> NbtList listToTag(Iterable<T> lllIlIIIlIllIl) {
        NbtList lllIlIIIlIllII = new NbtList();
        for (ISerializable lllIlIIIlIlllI : lllIlIIIlIllIl) {
            lllIlIIIlIllII.add((Object)lllIlIIIlIlllI.toTag());
        }
        return lllIlIIIlIllII;
    }

    public static <K, V> Map<K, V> mapFromTag(NbtCompound lllIlIIIIIIlII, ToKey<K> lllIIlllllllll, ToValue<V> lllIIllllllllI) {
        HashMap<K, V> lllIlIIIIIIIIl = new HashMap<K, V>(lllIlIIIIIIlII.getSize());
        for (String lllIlIIIIIIlIl : lllIlIIIIIIlII.getKeys()) {
            lllIlIIIIIIIIl.put(lllIIlllllllll.toKey(lllIlIIIIIIlIl), lllIIllllllllI.toValue(lllIlIIIIIIlII.get(lllIlIIIIIIlIl)));
        }
        return lllIlIIIIIIIIl;
    }

    public static interface ToValue<T> {
        public T toValue(NbtElement var1);
    }

    public static interface ToKey<T> {
        public T toKey(String var1);
    }
}

