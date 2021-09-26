/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules;

import net.minecraft.item.ItemStack;

public class Category {
    public final String name;
    public final ItemStack icon;
    private final int nameHash;

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Category category = (Category)object;
        return this.nameHash == category.nameHash;
    }

    public Category(String string) {
        this(string, null);
    }

    public Category(String string, ItemStack ItemStack2) {
        this.name = string;
        this.nameHash = string.hashCode();
        this.icon = ItemStack2;
    }

    public int hashCode() {
        return this.nameHash;
    }

    public String toString() {
        return this.name;
    }
}

