/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.misc;

public class Vec4 {
    public /* synthetic */ double x;
    public /* synthetic */ double w;
    public /* synthetic */ double y;
    public /* synthetic */ double z;

    public Vec4() {
        Vec4 lllIllIIIllllIl;
    }

    public void toScreen() {
        Vec4 lllIllIIIlIlIlI;
        double lllIllIIIlIlIIl = 1.0 / lllIllIIIlIlIlI.w * 0.5;
        lllIllIIIlIlIlI.x = lllIllIIIlIlIlI.x * lllIllIIIlIlIIl + 0.5;
        lllIllIIIlIlIlI.y = lllIllIIIlIlIlI.y * lllIllIIIlIlIIl + 0.5;
        lllIllIIIlIlIlI.z = lllIllIIIlIlIlI.z * lllIllIIIlIlIIl + 0.5;
        lllIllIIIlIlIlI.w = lllIllIIIlIlIIl;
    }

    public void set(double lllIllIIIllIlIl, double lllIllIIIlIllll, double lllIllIIIlIlllI, double lllIllIIIlIllIl) {
        lllIllIIIllIllI.x = lllIllIIIllIlIl;
        lllIllIIIllIllI.y = lllIllIIIlIllll;
        lllIllIIIllIllI.z = lllIllIIIlIlllI;
        lllIllIIIllIllI.w = lllIllIIIlIllIl;
    }
}

