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
import net.minecraft.class_1657;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
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

    public boolean show(class_1657 class_16572) {
        Friend friend = this.get(class_16572);
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
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
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
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_2499 class_24992 = new class_2499();
        for (Friend friend : this.friends) {
            class_24992.add((Object)friend.toTag());
        }
        class_24872.method_10566("friends", (class_2520)class_24992);
        class_24872.method_10566("enemy-color", (class_2520)this.enemyColor.toTag());
        class_24872.method_10566("neutral-color", (class_2520)this.neutralColor.toTag());
        class_24872.method_10566("trusted-color", (class_2520)this.trustedColor.toTag());
        class_24872.method_10556("attack-neutral", this.attackNeutral);
        class_24872.method_10556("show-enemies", this.showEnemies);
        class_24872.method_10556("show-neutral", this.showNeutral);
        class_24872.method_10556("show-trusted", this.showTrusted);
        return class_24872;
    }

    public int count() {
        return this.friends.size();
    }

    @Override
    public Friends fromTag(class_2487 class_24872) {
        this.friends = NbtUtils.listFromTag(class_24872.method_10554("friends", 10), Friends::lambda$fromTag$0);
        if (class_24872.method_10545("enemy-color")) {
            this.enemyColor.fromTag(class_24872.method_10562("enemy-color"));
        }
        if (class_24872.method_10545("neutral-color")) {
            this.neutralColor.fromTag(class_24872.method_10562("neutral-color"));
        }
        if (class_24872.method_10545("trusted-color")) {
            this.trustedColor.fromTag(class_24872.method_10562("trusted-color"));
        }
        if (class_24872.method_10545("attack-neutral")) {
            this.attackNeutral = class_24872.method_10577("attack-neutral");
        }
        if (class_24872.method_10545("show-enemies")) {
            this.showEnemies = class_24872.method_10577("show-enemies");
        }
        if (class_24872.method_10545("show-neutral")) {
            this.showNeutral = class_24872.method_10577("show-neutral");
        }
        if (class_24872.method_10545("show-trusted")) {
            this.showTrusted = class_24872.method_10577("show-trusted");
        }
        return this;
    }

    public boolean notTrusted(class_1657 class_16572) {
        Friend friend = this.get(class_16572);
        return friend == null || friend.type != FriendType.Trusted;
    }

    public Friend get(String string) {
        for (Friend friend : this.friends) {
            if (!friend.name.equals(string)) continue;
            return friend;
        }
        return null;
    }

    public boolean attack(class_1657 class_16572) {
        Friend friend = this.get(class_16572);
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

    private static Friend lambda$fromTag$0(class_2520 class_25202) {
        return new Friend((class_2487)class_25202);
    }

    public static Friends get() {
        return Systems.get(Friends.class);
    }

    public Friend get(class_1657 class_16572) {
        return this.get(class_16572.method_7334().getName());
    }

    public void addOrRemove(Friend friend) {
        if (this.friends.contains(friend)) {
            this.remove(friend);
        } else {
            this.add(friend);
        }
    }

    public Color getFriendColor(class_1657 class_16572) {
        return this.getFriendColor(this.get(class_16572));
    }
}

