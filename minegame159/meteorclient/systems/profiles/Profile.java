/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  org.apache.commons.io.FileUtils
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

public class Profile
implements ISerializable<Profile> {
    public /* synthetic */ boolean modules;
    public final /* synthetic */ List<String> loadOnJoinIps;
    public /* synthetic */ boolean config;
    public /* synthetic */ boolean accounts;
    public /* synthetic */ String name;
    public /* synthetic */ boolean friends;
    public /* synthetic */ boolean onLaunch;
    public /* synthetic */ boolean macros;
    public /* synthetic */ boolean waypoints;

    public void save(System<?> lllllllllllllllllIlllIIlIIIlIlII) {
        Profile lllllllllllllllllIlllIIlIIIlIlIl;
        File lllllllllllllllllIlllIIlIIIlIIll = new File(Profiles.FOLDER, lllllllllllllllllIlllIIlIIIlIlIl.name);
        lllllllllllllllllIlllIIlIIIlIlII.save(lllllllllllllllllIlllIIlIIIlIIll);
    }

    public void delete(System<?> lllllllllllllllllIlllIIIllllllll) {
        Profile lllllllllllllllllIlllIIlIIIIIllI;
        File lllllllllllllllllIlllIIlIIIIIIll = new File(new File(Profiles.FOLDER, lllllllllllllllllIlllIIlIIIIIllI.name), lllllllllllllllllIlllIIIllllllll.getFile().getName());
        lllllllllllllllllIlllIIlIIIIIIll.delete();
    }

    public void delete() {
        try {
            Profile lllllllllllllllllIlllIIIllllIIlI;
            FileUtils.deleteDirectory((File)new File(Profiles.FOLDER, lllllllllllllllllIlllIIIllllIIlI.name));
        }
        catch (IOException lllllllllllllllllIlllIIIllllIlII) {
            lllllllllllllllllIlllIIIllllIlII.printStackTrace();
        }
    }

    public void load() {
        Profile lllllllllllllllllIlllIIlIIIlllII;
        File lllllllllllllllllIlllIIlIIIllIll = new File(Profiles.FOLDER, lllllllllllllllllIlllIIlIIIlllII.name);
        if (lllllllllllllllllIlllIIlIIIlllII.accounts) {
            Accounts.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
        if (lllllllllllllllllIlllIIlIIIlllII.config) {
            Config.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
        if (lllllllllllllllllIlllIIlIIIlllII.friends) {
            Friends.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
        if (lllllllllllllllllIlllIIlIIIlllII.macros) {
            Macros.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
        if (lllllllllllllllllIlllIIlIIIlllII.modules) {
            Modules.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
        if (lllllllllllllllllIlllIIlIIIlllII.waypoints) {
            Waypoints.get().load(lllllllllllllllllIlllIIlIIIllIll);
        }
    }

    @Override
    public Profile fromTag(NbtCompound lllllllllllllllllIlllIIIlIlIllIl) {
        Profile lllllllllllllllllIlllIIIlIlIlIll;
        lllllllllllllllllIlllIIIlIlIlIll.name = lllllllllllllllllIlllIIIlIlIllIl.getString("name");
        lllllllllllllllllIlllIIIlIlIlIll.onLaunch = lllllllllllllllllIlllIIIlIlIllIl.contains("onLaunch") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("onLaunch");
        lllllllllllllllllIlllIIIlIlIlIll.accounts = lllllllllllllllllIlllIIIlIlIllIl.contains("accounts") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("accounts");
        lllllllllllllllllIlllIIIlIlIlIll.config = lllllllllllllllllIlllIIIlIlIllIl.contains("config") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("config");
        lllllllllllllllllIlllIIIlIlIlIll.friends = lllllllllllllllllIlllIIIlIlIllIl.contains("friends") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("friends");
        lllllllllllllllllIlllIIIlIlIlIll.macros = lllllllllllllllllIlllIIIlIlIllIl.contains("macros") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("macros");
        lllllllllllllllllIlllIIIlIlIlIll.modules = lllllllllllllllllIlllIIIlIlIllIl.contains("modules") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("modules");
        lllllllllllllllllIlllIIIlIlIlIll.waypoints = lllllllllllllllllIlllIIIlIlIllIl.contains("waypoints") && lllllllllllllllllIlllIIIlIlIllIl.getBoolean("waypoints");
        lllllllllllllllllIlllIIIlIlIlIll.loadOnJoinIps.clear();
        if (lllllllllllllllllIlllIIIlIlIllIl.contains("loadOnJoinIps")) {
            NbtList lllllllllllllllllIlllIIIlIllIIIl = lllllllllllllllllIlllIIIlIlIllIl.getList("loadOnJoinIps", 8);
            for (NbtElement lllllllllllllllllIlllIIIlIllIIll : lllllllllllllllllIlllIIIlIllIIIl) {
                lllllllllllllllllIlllIIIlIlIlIll.loadOnJoinIps.add(lllllllllllllllllIlllIIIlIllIIll.asString());
            }
        }
        return lllllllllllllllllIlllIIIlIlIlIll;
    }

    public Profile() {
        Profile lllllllllllllllllIlllIIlIIllIlIl;
        lllllllllllllllllIlllIIlIIllIlIl.onLaunch = false;
        lllllllllllllllllIlllIIlIIllIlIl.loadOnJoinIps = new ArrayList<String>();
        lllllllllllllllllIlllIIlIIllIlIl.accounts = false;
        lllllllllllllllllIlllIIlIIllIlIl.config = true;
        lllllllllllllllllIlllIIlIIllIlIl.friends = false;
        lllllllllllllllllIlllIIlIIllIlIl.macros = true;
        lllllllllllllllllIlllIIlIIllIlIl.modules = true;
        lllllllllllllllllIlllIIlIIllIlIl.waypoints = false;
    }

    public void save() {
        Profile lllllllllllllllllIlllIIlIIIIllIl;
        File lllllllllllllllllIlllIIlIIIIllII = new File(Profiles.FOLDER, lllllllllllllllllIlllIIlIIIIllIl.name);
        if (lllllllllllllllllIlllIIlIIIIllIl.accounts) {
            Accounts.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
        if (lllllllllllllllllIlllIIlIIIIllIl.config) {
            Config.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
        if (lllllllllllllllllIlllIIlIIIIllIl.friends) {
            Friends.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
        if (lllllllllllllllllIlllIIlIIIIllIl.macros) {
            Macros.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
        if (lllllllllllllllllIlllIIlIIIIllIl.modules) {
            Modules.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
        if (lllllllllllllllllIlllIIlIIIIllIl.waypoints) {
            Waypoints.get().save(lllllllllllllllllIlllIIlIIIIllII);
        }
    }

    @Override
    public NbtCompound toTag() {
        Profile lllllllllllllllllIlllIIIllIlllII;
        NbtCompound lllllllllllllllllIlllIIIllIllIll = new NbtCompound();
        lllllllllllllllllIlllIIIllIllIll.putString("name", lllllllllllllllllIlllIIIllIlllII.name);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("onLaunch", lllllllllllllllllIlllIIIllIlllII.onLaunch);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("accounts", lllllllllllllllllIlllIIIllIlllII.accounts);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("config", lllllllllllllllllIlllIIIllIlllII.config);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("friends", lllllllllllllllllIlllIIIllIlllII.friends);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("macros", lllllllllllllllllIlllIIIllIlllII.macros);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("modules", lllllllllllllllllIlllIIIllIlllII.modules);
        lllllllllllllllllIlllIIIllIllIll.putBoolean("waypoints", lllllllllllllllllIlllIIIllIlllII.waypoints);
        lllllllllllllllllIlllIIIllIlllII.loadOnJoinIps.removeIf(String::isEmpty);
        NbtList lllllllllllllllllIlllIIIllIllIlI = new NbtList();
        for (String lllllllllllllllllIlllIIIllIlllIl : lllllllllllllllllIlllIIIllIlllII.loadOnJoinIps) {
            lllllllllllllllllIlllIIIllIllIlI.add((Object)NbtString.of((String)lllllllllllllllllIlllIIIllIlllIl));
        }
        lllllllllllllllllIlllIIIllIllIll.put("loadOnJoinIps", (NbtElement)lllllllllllllllllIlllIIIllIllIlI);
        return lllllllllllllllllIlllIIIllIllIll;
    }

    public void load(System<?> lllllllllllllllllIlllIIlIIlIlllI) {
        Profile lllllllllllllllllIlllIIlIIlIllll;
        File lllllllllllllllllIlllIIlIIlIllIl = new File(Profiles.FOLDER, lllllllllllllllllIlllIIlIIlIllll.name);
        lllllllllllllllllIlllIIlIIlIlllI.load(lllllllllllllllllIlllIIlIIlIllIl);
    }
}

