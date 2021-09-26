/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.friends;

import java.util.Objects;
import minegame159.meteorclient.utils.entity.FriendType;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Friend
implements ISerializable<Friend> {
    public String name;
    public FriendType type = FriendType.Neutral;

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Friend friend = (Friend)object;
        return Objects.equals(this.name, friend.name);
    }

    public Friend(PlayerEntity PlayerEntity2) {
        this(PlayerEntity2.getGameProfile().getName());
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.putString("type", this.type.name());
        return NbtCompound2;
    }

    public Friend(NbtCompound NbtCompound2) {
        this.fromTag(NbtCompound2);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Friend fromTag(NbtCompound NbtCompound2) {
        this.name = NbtCompound2.getString("name");
        if (NbtCompound2.contains("type")) {
            this.type = FriendType.valueOf(NbtCompound2.getString("type"));
        }
        return this;
    }

    public Friend(String string) {
        this.name = string;
    }
}

