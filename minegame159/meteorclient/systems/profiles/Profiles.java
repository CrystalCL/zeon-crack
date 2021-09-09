/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
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

public class Profiles
extends System<Profiles>
implements Iterable<Profile> {
    public static final /* synthetic */ File FOLDER;
    private /* synthetic */ List<Profile> profiles;

    public void add(Profile llllllllllllllllllIlIIIlIIlIIlll) {
        Profiles llllllllllllllllllIlIIIlIIlIlIII;
        if (!llllllllllllllllllIlIIIlIIlIlIII.profiles.contains(llllllllllllllllllIlIIIlIIlIIlll)) {
            llllllllllllllllllIlIIIlIIlIlIII.profiles.add(llllllllllllllllllIlIIIlIIlIIlll);
        }
        llllllllllllllllllIlIIIlIIlIIlll.save();
        llllllllllllllllllIlIIIlIIlIlIII.save();
    }

    @Override
    public File getFile() {
        return new File(FOLDER, "profiles.nbt");
    }

    public void remove(Profile llllllllllllllllllIlIIIlIIlIIIll) {
        Profiles llllllllllllllllllIlIIIlIIlIIIlI;
        if (llllllllllllllllllIlIIIlIIlIIIlI.profiles.remove(llllllllllllllllllIlIIIlIIlIIIll)) {
            llllllllllllllllllIlIIIlIIlIIIll.delete();
        }
        llllllllllllllllllIlIIIlIIlIIIlI.save();
    }

    public List<Profile> getAll() {
        Profiles llllllllllllllllllIlIIIlIIIlIIll;
        return llllllllllllllllllIlIIIlIIIlIIll.profiles;
    }

    @Override
    public NbtCompound toTag() {
        Profiles llllllllllllllllllIlIIIlIIIIllIl;
        NbtCompound llllllllllllllllllIlIIIlIIIIlllI = new NbtCompound();
        llllllllllllllllllIlIIIlIIIIlllI.put("profiles", (NbtElement)NbtUtils.listToTag(llllllllllllllllllIlIIIlIIIIllIl.profiles));
        return llllllllllllllllllIlIIIlIIIIlllI;
    }

    @Override
    public Iterator<Profile> iterator() {
        Profiles llllllllllllllllllIlIIIIlllllIll;
        return llllllllllllllllllIlIIIIlllllIll.profiles.iterator();
    }

    static {
        FOLDER = new File(MeteorClient.FOLDER, "profiles");
    }

    public static Profiles get() {
        return Systems.get(Profiles.class);
    }

    public Profiles() {
        super("profiles");
        Profiles llllllllllllllllllIlIIIlIIlIllIl;
        llllllllllllllllllIlIIIlIIlIllIl.profiles = new ArrayList<Profile>();
    }

    public Profile get(String llllllllllllllllllIlIIIlIIIllIlI) {
        Profiles llllllllllllllllllIlIIIlIIIllIll;
        for (Profile llllllllllllllllllIlIIIlIIIlllII : llllllllllllllllllIlIIIlIIIllIll) {
            if (!llllllllllllllllllIlIIIlIIIlllII.name.equalsIgnoreCase(llllllllllllllllllIlIIIlIIIllIlI)) continue;
            return llllllllllllllllllIlIIIlIIIlllII;
        }
        return null;
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent llllllllllllllllllIlIIIlIIIIIIII) {
        Profiles llllllllllllllllllIlIIIIllllllll;
        for (Profile llllllllllllllllllIlIIIlIIIIIIlI : llllllllllllllllllIlIIIIllllllll) {
            if (!llllllllllllllllllIlIIIlIIIIIIlI.loadOnJoinIps.contains(Utils.getWorldName())) continue;
            llllllllllllllllllIlIIIlIIIIIIlI.load();
        }
    }

    @Override
    public Profiles fromTag(NbtCompound llllllllllllllllllIlIIIlIIIIIllI) {
        Profiles llllllllllllllllllIlIIIlIIIIlIIl;
        llllllllllllllllllIlIIIlIIIIlIIl.profiles = NbtUtils.listFromTag(llllllllllllllllllIlIIIlIIIIIllI.getList("profiles", 10), llllllllllllllllllIlIIIIllllIIll -> new Profile().fromTag((NbtCompound)llllllllllllllllllIlIIIIllllIIll));
        return llllllllllllllllllIlIIIlIIIIlIIl;
    }
}

