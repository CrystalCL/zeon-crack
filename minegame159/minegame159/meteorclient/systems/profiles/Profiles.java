/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.profiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.profiles.Profile;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.NbtUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Profiles
extends System<Profiles>
implements Iterable<Profile> {
    private List<Profile> profiles = new ArrayList<Profile>();
    public static final File FOLDER = new File(MeteorClient.FOLDER, "profiles");

    @EventHandler
    private void onGameJoined(GameJoinedEvent gameJoinedEvent) {
        for (Profile profile : this) {
            if (!profile.loadOnJoinIps.contains(Utils.getWorldName())) continue;
            profile.load();
        }
    }

    public void remove(Profile profile) {
        if (this.profiles.remove(profile)) {
            profile.delete();
        }
        this.save();
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("profiles", (NbtElement)NbtUtils.listToTag(this.profiles));
        return NbtCompound2;
    }

    public List<Profile> getAll() {
        return this.profiles;
    }

    @Override
    public Profiles fromTag(NbtCompound NbtCompound2) {
        this.profiles = NbtUtils.listFromTag(NbtCompound2.getList("profiles", 10), Profiles::lambda$fromTag$0);
        return this;
    }

    public static Profiles get() {
        return Systems.get(Profiles.class);
    }

    @Override
    public Iterator<Profile> iterator() {
        return this.profiles.iterator();
    }

    @Override
    public File getFile() {
        return new File(FOLDER, "profiles.nbt");
    }

    private static Profile lambda$fromTag$0(NbtElement NbtElement2) {
        return new Profile().fromTag((NbtCompound)NbtElement2);
    }

    public Profile get(String string) {
        for (Profile profile : this) {
            if (!profile.name.equalsIgnoreCase(string)) continue;
            return profile;
        }
        return null;
    }

    public Profiles() {
        super("profiles");
    }

    public void add(Profile profile) {
        if (!this.profiles.contains(profile)) {
            this.profiles.add(profile);
        }
        profile.save();
        this.save();
    }
}

