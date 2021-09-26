/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SettingGroup
implements ISerializable<SettingGroup>,
Iterable<Setting<?>> {
    public boolean sectionExpanded;
    final List<Setting<?>> settings = new ArrayList(1);
    public final String name;

    SettingGroup(String string, boolean bl) {
        this.name = string;
        this.sectionExpanded = bl;
    }

    @Override
    public SettingGroup fromTag(NbtCompound NbtCompound2) {
        this.sectionExpanded = NbtCompound2.getBoolean("sectionExpanded");
        NbtList NbtList2 = NbtCompound2.getList("settings", 10);
        for (NbtElement NbtElement2 : NbtList2) {
            NbtCompound NbtCompound3 = (NbtCompound)NbtElement2;
            Setting<?> setting = this.get(NbtCompound3.getString("name"));
            if (setting == null) continue;
            setting.fromTag(NbtCompound3);
        }
        return this;
    }

    public <T> Setting<T> add(Setting<T> setting) {
        this.settings.add(setting);
        return setting;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.putBoolean("sectionExpanded", this.sectionExpanded);
        NbtCompound2.put("settings", (NbtElement)NbtUtils.listToTag(this.settings));
        return NbtCompound2;
    }

    public Setting<?> get(String string) {
        for (Setting<?> setting : this) {
            if (!setting.name.equals(string)) continue;
            return setting;
        }
        return null;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Iterator<Setting<?>> iterator() {
        return this.settings.iterator();
    }
}

