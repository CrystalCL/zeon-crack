/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.profiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.accounts.Accounts;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.macros.Macros;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.profiles.Profiles;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import org.apache.commons.io.FileUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Profile
implements ISerializable<Profile> {
    public boolean friends = false;
    public boolean macros = true;
    public final List<String> loadOnJoinIps = new ArrayList<String>();
    public boolean modules = true;
    public boolean onLaunch = false;
    public boolean waypoints = false;
    public boolean accounts = false;
    public boolean config = true;
    public String name;

    public void load() {
        File file = new File(Profiles.FOLDER, this.name);
        if (this.accounts) {
            Accounts.get().load(file);
        }
        if (this.config) {
            Config.get().load(file);
        }
        if (this.friends) {
            Friends.get().load(file);
        }
        if (this.macros) {
            Macros.get().load(file);
        }
        if (this.modules) {
            Modules.get().load(file);
        }
        if (this.waypoints) {
            Waypoints.get().load(file);
        }
    }

    @Override
    public Profile fromTag(NbtCompound NbtCompound2) {
        this.name = NbtCompound2.getString("name");
        this.onLaunch = NbtCompound2.contains("onLaunch") && NbtCompound2.getBoolean("onLaunch");
        this.accounts = NbtCompound2.contains("accounts") && NbtCompound2.getBoolean("accounts");
        this.config = NbtCompound2.contains("config") && NbtCompound2.getBoolean("config");
        this.friends = NbtCompound2.contains("friends") && NbtCompound2.getBoolean("friends");
        this.macros = NbtCompound2.contains("macros") && NbtCompound2.getBoolean("macros");
        this.modules = NbtCompound2.contains("modules") && NbtCompound2.getBoolean("modules");
        this.waypoints = NbtCompound2.contains("waypoints") && NbtCompound2.getBoolean("waypoints");
        this.loadOnJoinIps.clear();
        if (NbtCompound2.contains("loadOnJoinIps")) {
            NbtList NbtList2 = NbtCompound2.getList("loadOnJoinIps", 8);
            for (NbtElement NbtElement2 : NbtList2) {
                this.loadOnJoinIps.add(NbtElement2.asString());
            }
        }
        return this;
    }

    public void save(System<?> system) {
        File file = new File(Profiles.FOLDER, this.name);
        system.save(file);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public void load(System<?> system) {
        File file = new File(Profiles.FOLDER, this.name);
        system.load(file);
    }

    public void save() {
        File file = new File(Profiles.FOLDER, this.name);
        if (this.accounts) {
            Accounts.get().save(file);
        }
        if (this.config) {
            Config.get().save(file);
        }
        if (this.friends) {
            Friends.get().save(file);
        }
        if (this.macros) {
            Macros.get().save(file);
        }
        if (this.modules) {
            Modules.get().save(file);
        }
        if (this.waypoints) {
            Waypoints.get().save(file);
        }
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.putBoolean("onLaunch", this.onLaunch);
        NbtCompound2.putBoolean("accounts", this.accounts);
        NbtCompound2.putBoolean("config", this.config);
        NbtCompound2.putBoolean("friends", this.friends);
        NbtCompound2.putBoolean("macros", this.macros);
        NbtCompound2.putBoolean("modules", this.modules);
        NbtCompound2.putBoolean("waypoints", this.waypoints);
        this.loadOnJoinIps.removeIf(String::isEmpty);
        NbtList NbtList2 = new NbtList();
        for (String string : this.loadOnJoinIps) {
            NbtList2.add((Object)NbtString.of((String)string));
        }
        NbtCompound2.put("loadOnJoinIps", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    public void delete() {
        try {
            FileUtils.deleteDirectory((File)new File(Profiles.FOLDER, this.name));
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void delete(System<?> system) {
        File file = new File(new File(Profiles.FOLDER, this.name), system.getFile().getName());
        file.delete();
    }
}

