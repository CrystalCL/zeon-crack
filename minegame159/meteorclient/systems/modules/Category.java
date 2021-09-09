/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package minegame159.meteorclient.systems.modules;

import net.minecraft.item.ItemStack;

public class Category {
    public final /* synthetic */ String name;
    public final /* synthetic */ ItemStack icon;
    private final /* synthetic */ int nameHash;

    public String toString() {
        Category lllllllllllllllllIIIlllIlIllllIl;
        return lllllllllllllllllIIIlllIlIllllIl.name;
    }

    public Category(String lllllllllllllllllIIIlllIllIIlIlI, ItemStack lllllllllllllllllIIIlllIllIIlIIl) {
        Category lllllllllllllllllIIIlllIllIIlIll;
        lllllllllllllllllIIIlllIllIIlIll.name = lllllllllllllllllIIIlllIllIIlIlI;
        lllllllllllllllllIIIlllIllIIlIll.nameHash = lllllllllllllllllIIIlllIllIIlIlI.hashCode();
        lllllllllllllllllIIIlllIllIIlIll.icon = lllllllllllllllllIIIlllIllIIlIIl;
    }

    public boolean equals(Object lllllllllllllllllIIIlllIlIlllIII) {
        Category lllllllllllllllllIIIlllIlIlllIIl;
        if (lllllllllllllllllIIIlllIlIlllIIl == lllllllllllllllllIIIlllIlIlllIII) {
            return true;
        }
        if (lllllllllllllllllIIIlllIlIlllIII == null || lllllllllllllllllIIIlllIlIlllIIl.getClass() != lllllllllllllllllIIIlllIlIlllIII.getClass()) {
            return false;
        }
        Category lllllllllllllllllIIIlllIlIllIlll = (Category)lllllllllllllllllIIIlllIlIlllIII;
        return lllllllllllllllllIIIlllIlIlllIIl.nameHash == lllllllllllllllllIIIlllIlIllIlll.nameHash;
    }

    public int hashCode() {
        Category lllllllllllllllllIIIlllIlIllIIIl;
        return lllllllllllllllllIIIlllIlIllIIIl.nameHash;
    }

    public Category(String lllllllllllllllllIIIlllIllIIIIlI) {
        lllllllllllllllllIIIlllIllIIIIll(lllllllllllllllllIIIlllIllIIIIlI, null);
        Category lllllllllllllllllIIIlllIllIIIIll;
    }
}

