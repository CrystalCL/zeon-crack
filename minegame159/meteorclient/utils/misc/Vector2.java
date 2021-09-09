/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.utils.misc;

import java.util.Objects;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

public class Vector2
implements ISerializable<Vector2> {
    public /* synthetic */ double x;
    public static final /* synthetic */ Vector2 ZERO;
    public /* synthetic */ double y;

    public void set(double lllllllllllllllllllIllIIIlIlllll, double lllllllllllllllllllIllIIIlIllllI) {
        lllllllllllllllllllIllIIIllIIIll.x = lllllllllllllllllllIllIIIlIlllll;
        lllllllllllllllllllIllIIIllIIIll.y = lllllllllllllllllllIllIIIlIllllI;
    }

    public String toString() {
        Vector2 lllllllllllllllllllIllIIIlIIllll;
        return String.valueOf(new StringBuilder().append(lllllllllllllllllllIllIIIlIIllll.x).append(", ").append(lllllllllllllllllllIllIIIlIIllll.y));
    }

    public Vector2(Vector2 lllllllllllllllllllIllIIIllIlIIl) {
        lllllllllllllllllllIllIIIllIlIII(lllllllllllllllllllIllIIIllIlIIl.x, lllllllllllllllllllIllIIIllIlIIl.y);
        Vector2 lllllllllllllllllllIllIIIllIlIII;
    }

    @Override
    public Vector2 fromTag(NbtCompound lllllllllllllllllllIllIIIlIlIIlI) {
        Vector2 lllllllllllllllllllIllIIIlIlIIll;
        lllllllllllllllllllIllIIIlIlIIll.x = lllllllllllllllllllIllIIIlIlIIlI.getDouble("x");
        lllllllllllllllllllIllIIIlIlIIll.y = lllllllllllllllllllIllIIIlIlIIlI.getDouble("y");
        return lllllllllllllllllllIllIIIlIlIIll;
    }

    static {
        ZERO = new Vector2(0.0, 0.0);
    }

    public Vector2(double lllllllllllllllllllIllIIIlllIlII, double lllllllllllllllllllIllIIIlllIIII) {
        Vector2 lllllllllllllllllllIllIIIlllIlIl;
        lllllllllllllllllllIllIIIlllIlIl.x = lllllllllllllllllllIllIIIlllIlII;
        lllllllllllllllllllIllIIIlllIlIl.y = lllllllllllllllllllIllIIIlllIIII;
    }

    public int hashCode() {
        Vector2 lllllllllllllllllllIllIIIlIIIlII;
        return Objects.hash(lllllllllllllllllllIllIIIlIIIlII.x, lllllllllllllllllllIllIIIlIIIlII.y);
    }

    @Override
    public NbtCompound toTag() {
        Vector2 lllllllllllllllllllIllIIIlIllIIl;
        NbtCompound lllllllllllllllllllIllIIIlIllIlI = new NbtCompound();
        lllllllllllllllllllIllIIIlIllIlI.putDouble("x", lllllllllllllllllllIllIIIlIllIIl.x);
        lllllllllllllllllllIllIIIlIllIlI.putDouble("y", lllllllllllllllllllIllIIIlIllIIl.y);
        return lllllllllllllllllllIllIIIlIllIlI;
    }

    public Vector2() {
        lllllllllllllllllllIllIIIllIlllI(0.0, 0.0);
        Vector2 lllllllllllllllllllIllIIIllIlllI;
    }

    public boolean equals(Object lllllllllllllllllllIllIIIlIIIlll) {
        Vector2 lllllllllllllllllllIllIIIlIIlIII;
        if (lllllllllllllllllllIllIIIlIIlIII == lllllllllllllllllllIllIIIlIIIlll) {
            return true;
        }
        if (lllllllllllllllllllIllIIIlIIIlll == null || lllllllllllllllllllIllIIIlIIlIII.getClass() != lllllllllllllllllllIllIIIlIIIlll.getClass()) {
            return false;
        }
        Vector2 lllllllllllllllllllIllIIIlIIlIIl = (Vector2)lllllllllllllllllllIllIIIlIIIlll;
        return Double.compare(lllllllllllllllllllIllIIIlIIlIIl.x, lllllllllllllllllllIllIIIlIIlIII.x) == 0 && Double.compare(lllllllllllllllllllIllIIIlIIlIIl.y, lllllllllllllllllllIllIIIlIIlIII.y) == 0;
    }
}

