/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.misc;

import java.util.Objects;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Vector2
implements ISerializable<Vector2> {
    public static final Vector2 ZERO = new Vector2(0.0, 0.0);
    public double y;
    public double x;

    public Vector2() {
        this(0.0, 0.0);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putDouble("x", this.x);
        NbtCompound2.putDouble("y", this.y);
        return NbtCompound2;
    }

    public Vector2(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public String toString() {
        return String.valueOf(new StringBuilder().append(this.x).append(", ").append(this.y));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Vector2 vector2 = (Vector2)object;
        return Double.compare(vector2.x, this.x) == 0 && Double.compare(vector2.y, this.y) == 0;
    }

    public Vector2(Vector2 vector2) {
        this(vector2.x, vector2.y);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public void set(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    @Override
    public Vector2 fromTag(NbtCompound NbtCompound2) {
        this.x = NbtCompound2.getDouble("x");
        this.y = NbtCompound2.getDouble("y");
        return this;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}

