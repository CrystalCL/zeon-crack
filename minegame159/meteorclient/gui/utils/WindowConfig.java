/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.gui.utils;

import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

public class WindowConfig
implements ISerializable<WindowConfig> {
    public /* synthetic */ double x;
    public /* synthetic */ double y;
    public /* synthetic */ boolean expanded;

    @Override
    public NbtCompound toTag() {
        WindowConfig lllllllIlIllIlI;
        NbtCompound lllllllIlIllIIl = new NbtCompound();
        lllllllIlIllIIl.putBoolean("expanded", lllllllIlIllIlI.expanded);
        lllllllIlIllIIl.putDouble("x", lllllllIlIllIlI.x);
        lllllllIlIllIIl.putDouble("y", lllllllIlIllIlI.y);
        return lllllllIlIllIIl;
    }

    public WindowConfig() {
        WindowConfig lllllllIlIlllIl;
        lllllllIlIlllIl.expanded = true;
        lllllllIlIlllIl.x = -1.0;
        lllllllIlIlllIl.y = -1.0;
    }

    @Override
    public WindowConfig fromTag(NbtCompound lllllllIlIlIIIl) {
        WindowConfig lllllllIlIlIIlI;
        lllllllIlIlIIlI.expanded = lllllllIlIlIIIl.getBoolean("expanded");
        lllllllIlIlIIlI.x = lllllllIlIlIIIl.getDouble("x");
        lllllllIlIlIIlI.y = lllllllIlIlIIIl.getDouble("y");
        return lllllllIlIlIIlI;
    }
}

