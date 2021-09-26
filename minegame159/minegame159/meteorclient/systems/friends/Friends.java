/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Friends
extends System<Friends>
implements Iterable<Friend> {
    public final SettingColor enemyColor = new SettingColor(204, 0, 0);
    public boolean showNeutral = true;
    public boolean showEnemies = true;
    public boolean showTrusted = true;
    public boolean attackNeutral = false;
    private List<Friend> friends;
    public final SettingColor trustedColor;
    public final SettingColor neutralColor = new SettingColor(0, 255, 180);

    public Color getFriendColor(Friend friend) {
        if (friend == null) {
            return null;
        }
        SettingColor settingColor = null;
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$FriendType[friend.type.ordinal()]) {
            case 1: {
                settingColor = Friends.get().enemyColor;
                break;
            }
            case 2: {
                settingColor = Friends.get().trustedColor;
                break;
            }
            case 3: {
                settingColor = Friends.get().neutralColor;
            }
        }
        return settingColor;
    }

    public List<Friend> getAll() {
        return this.friends;
    }

    public boolean add(Friend friend) {
        if (friend.name.isEmpty()) {
            return false;
        }
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
            this.save();
            return true;
        }
        return false;
    }

    public boolean remove(Friend friend) {
        if (this.friends.remove(friend)) {
            this.save();
            return true;
        }
        return false;
    }

    public boolean show(PlayerEntity PlayerEntity2) {
        Friend friend = this.get(PlayerEntity2);
        if (friend == null) {
            return false;
        }
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$FriendType[friend.type.ordinal()]) {
            case 1: {
                return this.showEnemies;
            }
            case 2: {
                return this.showTrusted;
            }
        }
        return this.showNeutral;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    @NotNull
    public Iterator<Friend> iterator() {
        return this.friends.iterator();
    }

    @Override
    public void init() {
        RainbowColors.add(this.enemyColor);
        RainbowColors.add(this.neutralColor);
        RainbowColors.add(this.trustedColor);
    }

    public boolean contains(Friend friend) {
        return this.friends.contains(friend);
    }

    public Friends() {
        super("friends");
        this.trustedColor = new SettingColor(57, 247, 47);
        this.friends = new ArrayList<Friend>();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtList NbtList2 = new NbtList();
        for (Friend friend : this.friends) {
            NbtList2.add((Object)friend.toTag());
        }
        NbtCompound2.put("friends", (NbtElement)NbtList2);
        NbtCompound2.put("enemy-color", (NbtElement)this.enemyColor.toTag());
        NbtCompound2.put("neutral-color", (NbtElement)this.neutralColor.toTag());
        NbtCompound2.put("trusted-color", (NbtElement)this.trustedColor.toTag());
        NbtCompound2.putBoolean("attack-neutral", this.attackNeutral);
        NbtCompound2.putBoolean("show-enemies", this.showEnemies);
        NbtCompound2.putBoolean("show-neutral", this.showNeutral);
        NbtCompound2.putBoolean("show-trusted", this.showTrusted);
        return NbtCompound2;
    }

    public int count() {
        return this.friends.size();
    }

    @Override
    public Friends fromTag(NbtCompound NbtCompound2) {
        this.friends = NbtUtils.listFromTag(NbtCompound2.getList("friends", 10), Friends::lambda$fromTag$0);
        if (NbtCompound2.contains("enemy-color")) {
            this.enemyColor.fromTag(NbtCompound2.getCompound("enemy-color"));
        }
        if (NbtCompound2.contains("neutral-color")) {
            this.neutralColor.fromTag(NbtCompound2.getCompound("neutral-color"));
        }
        if (NbtCompound2.contains("trusted-color")) {
            this.trustedColor.fromTag(NbtCompound2.getCompound("trusted-color"));
        }
        if (NbtCompound2.contains("attack-neutral")) {
            this.attackNeutral = NbtCompound2.getBoolean("attack-neutral");
        }
        if (NbtCompound2.contains("show-enemies")) {
            this.showEnemies = NbtCompound2.getBoolean("show-enemies");
        }
        if (NbtCompound2.contains("show-neutral")) {
            this.showNeutral = NbtCompound2.getBoolean("show-neutral");
        }
        if (NbtCompound2.contains("show-trusted")) {
            this.showTrusted = NbtCompound2.getBoolean("show-trusted");
        }
        return this;
    }

    public boolean notTrusted(PlayerEntity PlayerEntity2) {
        Friend friend = this.get(PlayerEntity2);
        return friend == null || friend.type != FriendType.Trusted;
    }

    public Friend get(String string) {
        for (Friend friend : this.friends) {
            if (!friend.name.equals(string)) continue;
            return friend;
        }
        return null;
    }

    public boolean attack(PlayerEntity PlayerEntity2) {
        Friend friend = this.get(PlayerEntity2);
        if (friend == null) {
            return true;
        }
        switch (1.$SwitchMap$minegame159$meteorclient$utils$entity$FriendType[friend.type.ordinal()]) {
            case 1: {
                return true;
            }
            case 2: {
                return false;
            }
        }
        return this.attackNeutral;
    }

    private static Friend lambda$fromTag$0(NbtElement NbtElement2) {
        return new Friend((NbtCompound)NbtElement2);
    }

    public static Friends get() {
        return Systems.get(Friends.class);
    }

    public Friend get(PlayerEntity PlayerEntity2) {
        return this.get(PlayerEntity2.getGameProfile().getName());
    }

    public void addOrRemove(Friend friend) {
        if (this.friends.contains(friend)) {
            this.remove(friend);
        } else {
            this.add(friend);
        }
    }

    public Color getFriendColor(PlayerEntity PlayerEntity2) {
        return this.getFriendColor(this.get(PlayerEntity2));
    }
}

