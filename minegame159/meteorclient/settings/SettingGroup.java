/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.NbtUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;

public class SettingGroup
implements ISerializable<SettingGroup>,
Iterable<Setting<?>> {
    public /* synthetic */ boolean sectionExpanded;
    final /* synthetic */ List<Setting<?>> settings;
    public final /* synthetic */ String name;

    @Override
    public Iterator<Setting<?>> iterator() {
        SettingGroup llIllIlIIIlII;
        return llIllIlIIIlII.settings.iterator();
    }

    public Setting<?> get(String llIllIlIIllll) {
        SettingGroup llIllIlIlIIlI;
        for (Setting<?> llIllIlIlIIll : llIllIlIlIIlI) {
            if (!llIllIlIlIIll.name.equals(llIllIlIIllll)) continue;
            return llIllIlIlIIll;
        }
        return null;
    }

    SettingGroup(String llIllIlIllIIl, boolean llIllIlIllIll) {
        SettingGroup llIllIlIlllIl;
        llIllIlIlllIl.settings = new ArrayList(1);
        llIllIlIlllIl.name = llIllIlIllIIl;
        llIllIlIlllIl.sectionExpanded = llIllIlIllIll;
    }

    @Override
    public NbtCompound toTag() {
        SettingGroup llIllIIllllll;
        NbtCompound llIllIlIIIIII = new NbtCompound();
        llIllIlIIIIII.putString("name", llIllIIllllll.name);
        llIllIlIIIIII.putBoolean("sectionExpanded", llIllIIllllll.sectionExpanded);
        llIllIlIIIIII.put("settings", (NbtElement)NbtUtils.listToTag(llIllIIllllll.settings));
        return llIllIlIIIIII;
    }

    public <T> Setting<T> add(Setting<T> llIllIlIIIlll) {
        SettingGroup llIllIlIIlIlI;
        llIllIlIIlIlI.settings.add(llIllIlIIIlll);
        return llIllIlIIIlll;
    }

    @Override
    public SettingGroup fromTag(NbtCompound llIllIIlIllll) {
        SettingGroup llIllIIllIIII;
        llIllIIllIIII.sectionExpanded = llIllIIlIllll.getBoolean("sectionExpanded");
        NbtList llIllIIllIIIl = llIllIIlIllll.getList("settings", 10);
        for (NbtElement llIllIIllIlII : llIllIIllIIIl) {
            NbtCompound llIllIIllIllI = (NbtCompound)llIllIIllIlII;
            Setting<?> llIllIIllIlIl = llIllIIllIIII.get(llIllIIllIllI.getString("name"));
            if (llIllIIllIlIl == null) continue;
            llIllIIllIlIl.fromTag(llIllIIllIllI);
        }
        return llIllIIllIIII;
    }
}

