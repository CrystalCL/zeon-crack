/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.systems.friends;

import java.util.Objects;
import minegame159.meteorclient.utils.entity.FriendType;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class Friend
implements ISerializable<Friend> {
    public /* synthetic */ String name;
    public /* synthetic */ FriendType type;

    @Override
    public Friend fromTag(NbtCompound lIllIIIlllIIIl) {
        Friend lIllIIIlllIIII;
        lIllIIIlllIIII.name = lIllIIIlllIIIl.getString("name");
        if (lIllIIIlllIIIl.contains("type")) {
            lIllIIIlllIIII.type = FriendType.valueOf(lIllIIIlllIIIl.getString("type"));
        }
        return lIllIIIlllIIII;
    }

    public Friend(PlayerEntity lIllIIlIIIIIIl) {
        lIllIIlIIIIIlI(lIllIIlIIIIIIl.getGameProfile().getName());
        Friend lIllIIlIIIIIlI;
    }

    public Friend(String lIllIIlIIIIlll) {
        Friend lIllIIlIIIlIII;
        lIllIIlIIIlIII.type = FriendType.Neutral;
        lIllIIlIIIlIII.name = lIllIIlIIIIlll;
    }

    @Override
    public NbtCompound toTag() {
        Friend lIllIIIlllIllI;
        NbtCompound lIllIIIlllIlll = new NbtCompound();
        lIllIIIlllIlll.putString("name", lIllIIIlllIllI.name);
        lIllIIIlllIlll.putString("type", lIllIIIlllIllI.type.name());
        return lIllIIIlllIlll;
    }

    public Friend(NbtCompound lIllIIIlllllIl) {
        Friend lIllIIIllllllI;
        lIllIIIllllllI.type = FriendType.Neutral;
        lIllIIIllllllI.fromTag(lIllIIIlllllIl);
    }

    public int hashCode() {
        Friend lIllIIIllIIIll;
        return Objects.hash(lIllIIIllIIIll.name);
    }

    public boolean equals(Object lIllIIIllIIlll) {
        Friend lIllIIIllIlIII;
        if (lIllIIIllIlIII == lIllIIIllIIlll) {
            return true;
        }
        if (lIllIIIllIIlll == null || lIllIIIllIlIII.getClass() != lIllIIIllIIlll.getClass()) {
            return false;
        }
        Friend lIllIIIllIlIIl = (Friend)lIllIIIllIIlll;
        return Objects.equals(lIllIIIllIlIII.name, lIllIIIllIlIIl.name);
    }
}

