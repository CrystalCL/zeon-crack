/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
 *  org.jetbrains.annotations.NotNull
 */
package minegame159.meteorclient.systems.friends;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.utils.entity.FriendType;
import minegame159.meteorclient.utils.misc.NbtUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.RainbowColors;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.NotNull;

public class Friends
extends System<Friends>
implements Iterable<Friend> {
    public final /* synthetic */ SettingColor neutralColor;
    public /* synthetic */ boolean showNeutral;
    public final /* synthetic */ SettingColor trustedColor;
    public /* synthetic */ boolean attackNeutral;
    public /* synthetic */ boolean showEnemies;
    public /* synthetic */ boolean showTrusted;
    private /* synthetic */ List<Friend> friends;
    public final /* synthetic */ SettingColor enemyColor;

    public int count() {
        Friends lllllllllllllllllIIllllllIIIlIIl;
        return lllllllllllllllllIIllllllIIIlIIl.friends.size();
    }

    public boolean show(PlayerEntity lllllllllllllllllIIllllllIllIIlI) {
        Friends lllllllllllllllllIIllllllIllIIII;
        Friend lllllllllllllllllIIllllllIllIIIl = lllllllllllllllllIIllllllIllIIII.get(lllllllllllllllllIIllllllIllIIlI);
        if (lllllllllllllllllIIllllllIllIIIl == null) {
            return false;
        }
        switch (lllllllllllllllllIIllllllIllIIIl.type) {
            case Enemy: {
                return lllllllllllllllllIIllllllIllIIII.showEnemies;
            }
            case Trusted: {
                return lllllllllllllllllIIllllllIllIIII.showTrusted;
            }
        }
        return lllllllllllllllllIIllllllIllIIII.showNeutral;
    }

    @Override
    public NbtCompound toTag() {
        Friends lllllllllllllllllIIlllllIlllllll;
        NbtCompound lllllllllllllllllIIlllllIllllllI = new NbtCompound();
        NbtList lllllllllllllllllIIlllllIlllllIl = new NbtList();
        for (Friend lllllllllllllllllIIllllllIIIIIII : lllllllllllllllllIIlllllIlllllll.friends) {
            lllllllllllllllllIIlllllIlllllIl.add((Object)lllllllllllllllllIIllllllIIIIIII.toTag());
        }
        lllllllllllllllllIIlllllIllllllI.put("friends", (NbtElement)lllllllllllllllllIIlllllIlllllIl);
        lllllllllllllllllIIlllllIllllllI.put("enemy-color", (NbtElement)lllllllllllllllllIIlllllIlllllll.enemyColor.toTag());
        lllllllllllllllllIIlllllIllllllI.put("neutral-color", (NbtElement)lllllllllllllllllIIlllllIlllllll.neutralColor.toTag());
        lllllllllllllllllIIlllllIllllllI.put("trusted-color", (NbtElement)lllllllllllllllllIIlllllIlllllll.trustedColor.toTag());
        lllllllllllllllllIIlllllIllllllI.putBoolean("attack-neutral", lllllllllllllllllIIlllllIlllllll.attackNeutral);
        lllllllllllllllllIIlllllIllllllI.putBoolean("show-enemies", lllllllllllllllllIIlllllIlllllll.showEnemies);
        lllllllllllllllllIIlllllIllllllI.putBoolean("show-neutral", lllllllllllllllllIIlllllIlllllll.showNeutral);
        lllllllllllllllllIIlllllIllllllI.putBoolean("show-trusted", lllllllllllllllllIIlllllIlllllll.showTrusted);
        return lllllllllllllllllIIlllllIllllllI;
    }

    @Override
    public Friends fromTag(NbtCompound lllllllllllllllllIIlllllIlllIlII) {
        Friends lllllllllllllllllIIlllllIlllIIll;
        lllllllllllllllllIIlllllIlllIIll.friends = NbtUtils.listFromTag(lllllllllllllllllIIlllllIlllIlII.getList("friends", 10), lllllllllllllllllIIlllllIllIlIll -> new Friend((NbtCompound)lllllllllllllllllIIlllllIllIlIll));
        if (lllllllllllllllllIIlllllIlllIlII.contains("enemy-color")) {
            lllllllllllllllllIIlllllIlllIIll.enemyColor.fromTag(lllllllllllllllllIIlllllIlllIlII.getCompound("enemy-color"));
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("neutral-color")) {
            lllllllllllllllllIIlllllIlllIIll.neutralColor.fromTag(lllllllllllllllllIIlllllIlllIlII.getCompound("neutral-color"));
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("trusted-color")) {
            lllllllllllllllllIIlllllIlllIIll.trustedColor.fromTag(lllllllllllllllllIIlllllIlllIlII.getCompound("trusted-color"));
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("attack-neutral")) {
            lllllllllllllllllIIlllllIlllIIll.attackNeutral = lllllllllllllllllIIlllllIlllIlII.getBoolean("attack-neutral");
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("show-enemies")) {
            lllllllllllllllllIIlllllIlllIIll.showEnemies = lllllllllllllllllIIlllllIlllIlII.getBoolean("show-enemies");
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("show-neutral")) {
            lllllllllllllllllIIlllllIlllIIll.showNeutral = lllllllllllllllllIIlllllIlllIlII.getBoolean("show-neutral");
        }
        if (lllllllllllllllllIIlllllIlllIlII.contains("show-trusted")) {
            lllllllllllllllllIIlllllIlllIIll.showTrusted = lllllllllllllllllIIlllllIlllIlII.getBoolean("show-trusted");
        }
        return lllllllllllllllllIIlllllIlllIIll;
    }

    public boolean contains(Friend lllllllllllllllllIIlllllllIllIll) {
        Friends lllllllllllllllllIIlllllllIlllll;
        return lllllllllllllllllIIlllllllIlllll.friends.contains(lllllllllllllllllIIlllllllIllIll);
    }

    public Color getFriendColor(PlayerEntity lllllllllllllllllIIllllllIIllIlI) {
        Friends lllllllllllllllllIIllllllIIllIIl;
        return lllllllllllllllllIIllllllIIllIIl.getFriendColor(lllllllllllllllllIIllllllIIllIIl.get(lllllllllllllllllIIllllllIIllIlI));
    }

    public List<Friend> getAll() {
        Friends lllllllllllllllllIIllllllllIlIlI;
        return lllllllllllllllllIIllllllllIlIlI.friends;
    }

    public static Friends get() {
        return Systems.get(Friends.class);
    }

    @Override
    @NotNull
    public Iterator<Friend> iterator() {
        Friends lllllllllllllllllIIllllllIIIIllI;
        return lllllllllllllllllIIllllllIIIIllI.friends.iterator();
    }

    public void addOrRemove(Friend lllllllllllllllllIIllllllIIlIIlI) {
        Friends lllllllllllllllllIIllllllIIlIlIl;
        if (lllllllllllllllllIIllllllIIlIlIl.friends.contains(lllllllllllllllllIIllllllIIlIIlI)) {
            lllllllllllllllllIIllllllIIlIlIl.remove(lllllllllllllllllIIllllllIIlIIlI);
        } else {
            lllllllllllllllllIIllllllIIlIlIl.add(lllllllllllllllllIIllllllIIlIIlI);
        }
    }

    public Color getFriendColor(Friend lllllllllllllllllIIllllllIlIIIIl) {
        if (lllllllllllllllllIIllllllIlIIIIl == null) {
            return null;
        }
        SettingColor lllllllllllllllllIIllllllIlIIIII = null;
        switch (lllllllllllllllllIIllllllIlIIIIl.type) {
            case Enemy: {
                lllllllllllllllllIIllllllIlIIIII = Friends.get().enemyColor;
                break;
            }
            case Trusted: {
                lllllllllllllllllIIllllllIlIIIII = Friends.get().trustedColor;
                break;
            }
            case Neutral: {
                lllllllllllllllllIIllllllIlIIIII = Friends.get().neutralColor;
            }
        }
        return lllllllllllllllllIIllllllIlIIIII;
    }

    public boolean attack(PlayerEntity lllllllllllllllllIIllllllIlIIllI) {
        Friends lllllllllllllllllIIllllllIlIIlll;
        Friend lllllllllllllllllIIllllllIlIlIII = lllllllllllllllllIIllllllIlIIlll.get(lllllllllllllllllIIllllllIlIIllI);
        if (lllllllllllllllllIIllllllIlIlIII == null) {
            return true;
        }
        switch (lllllllllllllllllIIllllllIlIlIII.type) {
            case Enemy: {
                return true;
            }
            case Trusted: {
                return false;
            }
        }
        return lllllllllllllllllIIllllllIlIIlll.attackNeutral;
    }

    public Friends() {
        super("friends");
        Friends lllllllllllllllllIlIIIIIIIIIIlll;
        lllllllllllllllllIlIIIIIIIIIIlll.enemyColor = new SettingColor(204, 0, 0);
        lllllllllllllllllIlIIIIIIIIIIlll.neutralColor = new SettingColor(0, 255, 180);
        lllllllllllllllllIlIIIIIIIIIIlll.trustedColor = new SettingColor(57, 247, 47);
        lllllllllllllllllIlIIIIIIIIIIlll.attackNeutral = false;
        lllllllllllllllllIlIIIIIIIIIIlll.showEnemies = true;
        lllllllllllllllllIlIIIIIIIIIIlll.showNeutral = true;
        lllllllllllllllllIlIIIIIIIIIIlll.showTrusted = true;
        lllllllllllllllllIlIIIIIIIIIIlll.friends = new ArrayList<Friend>();
    }

    @Override
    public void init() {
        Friends lllllllllllllllllIlIIIIIIIIIIIll;
        RainbowColors.add(lllllllllllllllllIlIIIIIIIIIIIll.enemyColor);
        RainbowColors.add(lllllllllllllllllIlIIIIIIIIIIIll.neutralColor);
        RainbowColors.add(lllllllllllllllllIlIIIIIIIIIIIll.trustedColor);
    }

    public Friend get(PlayerEntity lllllllllllllllllIIlllllllIIIIlI) {
        Friends lllllllllllllllllIIlllllllIIIIIl;
        return lllllllllllllllllIIlllllllIIIIIl.get(lllllllllllllllllIIlllllllIIIIlI.getGameProfile().getName());
    }

    public boolean notTrusted(PlayerEntity lllllllllllllllllIIllllllIlllIII) {
        Friends lllllllllllllllllIIllllllIllllII;
        Friend lllllllllllllllllIIllllllIlllIlI = lllllllllllllllllIIllllllIllllII.get(lllllllllllllllllIIllllllIlllIII);
        return lllllllllllllllllIIllllllIlllIlI == null || lllllllllllllllllIIllllllIlllIlI.type != FriendType.Trusted;
    }

    public boolean add(Friend lllllllllllllllllIIlllllllllIIII) {
        Friends lllllllllllllllllIIlllllllllIIlI;
        if (lllllllllllllllllIIlllllllllIIII.name.isEmpty()) {
            return false;
        }
        if (!lllllllllllllllllIIlllllllllIIlI.friends.contains(lllllllllllllllllIIlllllllllIIII)) {
            lllllllllllllllllIIlllllllllIIlI.friends.add(lllllllllllllllllIIlllllllllIIII);
            lllllllllllllllllIIlllllllllIIlI.save();
            return true;
        }
        return false;
    }

    public boolean remove(Friend lllllllllllllllllIIllllllIIIlllI) {
        Friends lllllllllllllllllIIllllllIIIllll;
        if (lllllllllllllllllIIllllllIIIllll.friends.remove(lllllllllllllllllIIllllllIIIlllI)) {
            lllllllllllllllllIIllllllIIIllll.save();
            return true;
        }
        return false;
    }

    public Friend get(String lllllllllllllllllIIlllllllIIllII) {
        Friends lllllllllllllllllIIlllllllIIllIl;
        for (Friend lllllllllllllllllIIlllllllIlIIII : lllllllllllllllllIIlllllllIIllIl.friends) {
            if (!lllllllllllllllllIIlllllllIlIIII.name.equals(lllllllllllllllllIIlllllllIIllII)) continue;
            return lllllllllllllllllIIlllllllIlIIII;
        }
        return null;
    }
}

