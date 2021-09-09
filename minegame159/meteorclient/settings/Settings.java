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

public class Settings
implements ISerializable<Settings>,
Iterable<SettingGroup> {
    private /* synthetic */ SettingGroup defaultGroup;
    public final /* synthetic */ List<SettingGroup> groups;

    public int sizeGroups() {
        Settings llllllllllllllllIllIIIIlIlllIIlI;
        return llllllllllllllllIllIIIIlIlllIIlI.groups.size();
    }

    public SettingGroup createGroup(String llllllllllllllllIllIIIIlIllIlIIl, boolean llllllllllllllllIllIIIIlIllIIlII) {
        Settings llllllllllllllllIllIIIIlIllIlIlI;
        SettingGroup llllllllllllllllIllIIIIlIllIIlll = new SettingGroup(llllllllllllllllIllIIIIlIllIlIIl, llllllllllllllllIllIIIIlIllIIlII);
        llllllllllllllllIllIIIIlIllIlIlI.groups.add(llllllllllllllllIllIIIIlIllIIlll);
        return llllllllllllllllIllIIIIlIllIIlll;
    }

    public SettingGroup createGroup(String llllllllllllllllIllIIIIlIlIlllIl) {
        Settings llllllllllllllllIllIIIIlIllIIIII;
        return llllllllllllllllIllIIIIlIllIIIII.createGroup(llllllllllllllllIllIIIIlIlIlllIl, true);
    }

    @Override
    public Settings fromTag(NbtCompound llllllllllllllllIllIIIIlIIlIlIll) {
        Settings llllllllllllllllIllIIIIlIIlIlIIl;
        NbtList llllllllllllllllIllIIIIlIIlIlIlI = llllllllllllllllIllIIIIlIIlIlIll.getList("groups", 10);
        for (NbtElement llllllllllllllllIllIIIIlIIlIllIl : llllllllllllllllIllIIIIlIIlIlIlI) {
            NbtCompound llllllllllllllllIllIIIIlIIlIllll = (NbtCompound)llllllllllllllllIllIIIIlIIlIllIl;
            SettingGroup llllllllllllllllIllIIIIlIIlIlllI = llllllllllllllllIllIIIIlIIlIlIIl.getGroup(llllllllllllllllIllIIIIlIIlIllll.getString("name"));
            if (llllllllllllllllIllIIIIlIIlIlllI == null) continue;
            llllllllllllllllIllIIIIlIIlIlllI.fromTag(llllllllllllllllIllIIIIlIIlIllll);
        }
        return llllllllllllllllIllIIIIlIIlIlIIl;
    }

    public Setting<?> get(String llllllllllllllllIllIIIIllIIIIllI) {
        Settings llllllllllllllllIllIIIIllIIIIlIl;
        for (SettingGroup llllllllllllllllIllIIIIllIIIlIII : llllllllllllllllIllIIIIllIIIIlIl) {
            for (Setting<?> llllllllllllllllIllIIIIllIIIlIIl : llllllllllllllllIllIIIIllIIIlIII) {
                if (!llllllllllllllllIllIIIIllIIIIllI.equalsIgnoreCase(llllllllllllllllIllIIIIllIIIlIIl.name)) continue;
                return llllllllllllllllIllIIIIllIIIlIIl;
            }
        }
        return null;
    }

    public void onActivated() {
        Settings llllllllllllllllIllIIIIllIIlIlII;
        for (SettingGroup llllllllllllllllIllIIIIllIIlIllI : llllllllllllllllIllIIIIllIIlIlII.groups) {
            for (Setting<?> llllllllllllllllIllIIIIllIIlIlll : llllllllllllllllIllIIIIllIIlIllI) {
                llllllllllllllllIllIIIIllIIlIlll.onActivated();
            }
        }
    }

    public SettingGroup getDefaultGroup() {
        Settings llllllllllllllllIllIIIIlIlllIIII;
        if (llllllllllllllllIllIIIIlIlllIIII.defaultGroup == null) {
            llllllllllllllllIllIIIIlIlllIIII.defaultGroup = llllllllllllllllIllIIIIlIlllIIII.createGroup("General");
        }
        return llllllllllllllllIllIIIIlIlllIIII.defaultGroup;
    }

    @Override
    public Iterator<SettingGroup> iterator() {
        Settings llllllllllllllllIllIIIIlIIlllllI;
        return llllllllllllllllIllIIIIlIIlllllI.groups.iterator();
    }

    public void registerColorSettings(Module llllllllllllllllIllIIIIlIlIlIIIl) {
        Settings llllllllllllllllIllIIIIlIlIlIlII;
        for (SettingGroup llllllllllllllllIllIIIIlIlIlIlIl : llllllllllllllllIllIIIIlIlIlIlII) {
            for (Setting<SettingColor> llllllllllllllllIllIIIIlIlIlIllI : llllllllllllllllIllIIIIlIlIlIlIl) {
                llllllllllllllllIllIIIIlIlIlIllI.module = llllllllllllllllIllIIIIlIlIlIIIl;
                if (!(llllllllllllllllIllIIIIlIlIlIllI instanceof ColorSetting)) continue;
                RainbowColors.addSetting(llllllllllllllllIllIIIIlIlIlIllI);
            }
        }
    }

    public void unregisterColorSettings() {
        Settings llllllllllllllllIllIIIIlIlIIIlIl;
        for (SettingGroup llllllllllllllllIllIIIIlIlIIIllI : llllllllllllllllIllIIIIlIlIIIlIl) {
            for (Setting<SettingColor> llllllllllllllllIllIIIIlIlIIIlll : llllllllllllllllIllIIIIlIlIIIllI) {
                if (!(llllllllllllllllIllIIIIlIlIIIlll instanceof ColorSetting)) continue;
                RainbowColors.removeSetting(llllllllllllllllIllIIIIlIlIIIlll);
            }
        }
    }

    public Settings() {
        Settings llllllllllllllllIllIIIIllIIllllI;
        llllllllllllllllIllIIIIllIIllllI.groups = new ArrayList<SettingGroup>(1);
    }

    @Override
    public NbtCompound toTag() {
        Settings llllllllllllllllIllIIIIlIIlllIlI;
        NbtCompound llllllllllllllllIllIIIIlIIlllIIl = new NbtCompound();
        llllllllllllllllIllIIIIlIIlllIIl.put("groups", (NbtElement)NbtUtils.listToTag(llllllllllllllllIllIIIIlIIlllIlI.groups));
        return llllllllllllllllIllIIIIlIIlllIIl;
    }

    public SettingGroup getGroup(String llllllllllllllllIllIIIIlIllllIIl) {
        Settings llllllllllllllllIllIIIIlIllllIII;
        for (SettingGroup llllllllllllllllIllIIIIlIllllIll : llllllllllllllllIllIIIIlIllllIII) {
            if (!llllllllllllllllIllIIIIlIllllIll.name.equals(llllllllllllllllIllIIIIlIllllIIl)) continue;
            return llllllllllllllllIllIIIIlIllllIll;
        }
        return null;
    }
}

