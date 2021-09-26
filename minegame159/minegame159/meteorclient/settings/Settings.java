/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.NbtUtils;
import minegame159.meteorclient.utils.render.color.RainbowColors;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Settings
implements ISerializable<Settings>,
Iterable<SettingGroup> {
    public final List<SettingGroup> groups = new ArrayList<SettingGroup>(1);
    private SettingGroup defaultGroup;

    @Override
    public Settings fromTag(NbtCompound NbtCompound2) {
        NbtList NbtList2 = NbtCompound2.getList("groups", 10);
        for (NbtElement NbtElement2 : NbtList2) {
            NbtCompound NbtCompound3 = (NbtCompound)NbtElement2;
            SettingGroup settingGroup = this.getGroup(NbtCompound3.getString("name"));
            if (settingGroup == null) continue;
            settingGroup.fromTag(NbtCompound3);
        }
        return this;
    }

    @Override
    public Iterator<SettingGroup> iterator() {
        return this.groups.iterator();
    }

    public SettingGroup createGroup(String string, boolean bl) {
        SettingGroup settingGroup = new SettingGroup(string, bl);
        this.groups.add(settingGroup);
        return settingGroup;
    }

    public SettingGroup createGroup(String string) {
        return this.createGroup(string, true);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public SettingGroup getDefaultGroup() {
        if (this.defaultGroup == null) {
            this.defaultGroup = this.createGroup("General");
        }
        return this.defaultGroup;
    }

    public Setting<?> get(String string) {
        for (SettingGroup settingGroup : this) {
            for (Setting<?> setting : settingGroup) {
                if (!string.equalsIgnoreCase(setting.name)) continue;
                return setting;
            }
        }
        return null;
    }

    public int sizeGroups() {
        return this.groups.size();
    }

    public void unregisterColorSettings() {
        for (SettingGroup settingGroup : this) {
            for (Setting<SettingColor> setting : settingGroup) {
                if (!(setting instanceof ColorSetting)) continue;
                RainbowColors.removeSetting(setting);
            }
        }
    }

    public void registerColorSettings(Module module) {
        for (SettingGroup settingGroup : this) {
            for (Setting<SettingColor> setting : settingGroup) {
                setting.module = module;
                if (!(setting instanceof ColorSetting)) continue;
                RainbowColors.addSetting(setting);
            }
        }
    }

    public SettingGroup getGroup(String string) {
        for (SettingGroup settingGroup : this) {
            if (!settingGroup.name.equals(string)) continue;
            return settingGroup;
        }
        return null;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("groups", (NbtElement)NbtUtils.listToTag(this.groups));
        return NbtCompound2;
    }

    public void onActivated() {
        for (SettingGroup settingGroup : this.groups) {
            for (Setting<?> setting : settingGroup) {
                setting.onActivated();
            }
        }
    }
}

