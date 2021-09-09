/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.utils.render;

import java.util.Objects;

public class Box {
    public /* synthetic */ double height;
    public /* synthetic */ double y;
    public /* synthetic */ double width;
    public /* synthetic */ double x;

    public Box() {
        llllllllllllllllIllllIIlllIIIlII(0.0, 0.0, 0.0, 0.0);
        Box llllllllllllllllIllllIIlllIIIlII;
    }

    public Box(double llllllllllllllllIllllIIlllIIllll, double llllllllllllllllIllllIIlllIIlIIl, double llllllllllllllllIllllIIlllIIlIII, double llllllllllllllllIllllIIlllIIIlll) {
        Box llllllllllllllllIllllIIlllIlIIII;
        llllllllllllllllIllllIIlllIlIIII.x = llllllllllllllllIllllIIlllIIllll;
        llllllllllllllllIllllIIlllIlIIII.y = llllllllllllllllIllllIIlllIIlIIl;
        llllllllllllllllIllllIIlllIlIIII.width = llllllllllllllllIllllIIlllIIlIII;
        llllllllllllllllIllllIIlllIlIIII.height = llllllllllllllllIllllIIlllIIIlll;
    }

    public int hashCode() {
        Box llllllllllllllllIllllIIllIlllIII;
        return Objects.hash(llllllllllllllllIllllIIllIlllIII.x, llllllllllllllllIllllIIllIlllIII.y, llllllllllllllllIllllIIllIlllIII.width, llllllllllllllllIllllIIllIlllIII.height);
    }

    public boolean equals(Object llllllllllllllllIllllIIllIllllII) {
        Box llllllllllllllllIllllIIlllIIIIII;
        if (llllllllllllllllIllllIIlllIIIIII == llllllllllllllllIllllIIllIllllII) {
            return true;
        }
        if (llllllllllllllllIllllIIllIllllII == null || llllllllllllllllIllllIIlllIIIIII.getClass() != llllllllllllllllIllllIIllIllllII.getClass()) {
            return false;
        }
        Box llllllllllllllllIllllIIllIlllllI = (Box)llllllllllllllllIllllIIllIllllII;
        return Double.compare(llllllllllllllllIllllIIllIlllllI.x, llllllllllllllllIllllIIlllIIIIII.x) == 0 && Double.compare(llllllllllllllllIllllIIllIlllllI.y, llllllllllllllllIllllIIlllIIIIII.y) == 0 && Double.compare(llllllllllllllllIllllIIllIlllllI.width, llllllllllllllllIllllIIlllIIIIII.width) == 0 && Double.compare(llllllllllllllllIllllIIllIlllllI.height, llllllllllllllllIllllIIlllIIIIII.height) == 0;
    }
}

