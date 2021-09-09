/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.AbstractNbtNumber
 */
package minegame159.meteorclient.systems.modules.render.hud;

import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.render.AlignmentX;
import minegame159.meteorclient.utils.render.AlignmentY;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.AbstractNbtNumber;

public class BoundingBox
implements ISerializable<BoundingBox> {
    public /* synthetic */ double height;
    public /* synthetic */ double yOffset;
    public /* synthetic */ AlignmentY y;
    public /* synthetic */ double xOffset;
    public /* synthetic */ AlignmentX x;
    public /* synthetic */ double width;

    public void setSize(double lllllllllllllllllIIIlIIlllIIIIll, double lllllllllllllllllIIIlIIlllIIIIlI) {
        lllllllllllllllllIIIlIIlllIIIIIl.width = lllllllllllllllllIIIlIIlllIIIIll;
        lllllllllllllllllIIIlIIlllIIIIIl.height = lllllllllllllllllIIIlIIlllIIIIlI;
    }

    public double alignX(double lllllllllllllllllIIIlIIlllllIIll) {
        BoundingBox lllllllllllllllllIIIlIIlllllIIlI;
        switch (lllllllllllllllllIIIlIIlllllIIlI.x) {
            default: {
                return 0.0;
            }
            case Center: {
                return lllllllllllllllllIIIlIIlllllIIlI.width / 2.0 - lllllllllllllllllIIIlIIlllllIIll / 2.0;
            }
            case Right: 
        }
        return lllllllllllllllllIIIlIIlllllIIlI.width - lllllllllllllllllIIIlIIlllllIIll;
    }

    @Override
    public NbtCompound toTag() {
        BoundingBox lllllllllllllllllIIIlIIllIlIIlIl;
        NbtCompound lllllllllllllllllIIIlIIllIlIIllI = new NbtCompound();
        lllllllllllllllllIIIlIIllIlIIllI.putString("x", lllllllllllllllllIIIlIIllIlIIlIl.x.name());
        lllllllllllllllllIIIlIIllIlIIllI.putString("y", lllllllllllllllllIIIlIIllIlIIlIl.y.name());
        lllllllllllllllllIIIlIIllIlIIllI.putDouble("xOffset", lllllllllllllllllIIIlIIllIlIIlIl.xOffset);
        lllllllllllllllllIIIlIIllIlIIllI.putDouble("yOffset", lllllllllllllllllIIIlIIllIlIIlIl.yOffset);
        return lllllllllllllllllIIIlIIllIlIIllI;
    }

    @Override
    public BoundingBox fromTag(NbtCompound lllllllllllllllllIIIlIIllIlIIIII) {
        BoundingBox lllllllllllllllllIIIlIIllIlIIIIl;
        lllllllllllllllllIIIlIIllIlIIIIl.x = AlignmentX.valueOf(lllllllllllllllllIIIlIIllIlIIIII.getString("x"));
        lllllllllllllllllIIIlIIllIlIIIIl.y = AlignmentY.valueOf(lllllllllllllllllIIIlIIllIlIIIII.getString("y"));
        lllllllllllllllllIIIlIIllIlIIIIl.xOffset = ((AbstractNbtNumber)lllllllllllllllllIIIlIIllIlIIIII.get("xOffset")).doubleValue();
        lllllllllllllllllIIIlIIllIlIIIIl.yOffset = ((AbstractNbtNumber)lllllllllllllllllIIIlIIllIlIIIII.get("yOffset")).doubleValue();
        return lllllllllllllllllIIIlIIllIlIIIIl;
    }

    public double getY() {
        BoundingBox lllllllllllllllllIIIlIIllIlllIlI;
        switch (lllllllllllllllllIIIlIIllIlllIlI.y) {
            default: {
                return lllllllllllllllllIIIlIIllIlllIlI.yOffset;
            }
            case Center: {
                return (double)Utils.getWindowHeight() / 2.0 - lllllllllllllllllIIIlIIllIlllIlI.height / 2.0 + lllllllllllllllllIIIlIIllIlllIlI.yOffset;
            }
            case Bottom: 
        }
        return (double)Utils.getWindowHeight() - lllllllllllllllllIIIlIIllIlllIlI.height + lllllllllllllllllIIIlIIllIlllIlI.yOffset;
    }

    public void setX(int lllllllllllllllllIIIlIIllllIlIll) {
        BoundingBox lllllllllllllllllIIIlIIllllIlllI;
        lllllllllllllllllIIIlIIllllIlllI.addPos((double)lllllllllllllllllIIIlIIllllIlIll - lllllllllllllllllIIIlIIllllIlllI.getX(), 0.0);
    }

    public boolean isOver(double lllllllllllllllllIIIlIIllIllIIlI, double lllllllllllllllllIIIlIIllIlIllII) {
        BoundingBox lllllllllllllllllIIIlIIllIllIIll;
        double lllllllllllllllllIIIlIIllIllIIII = lllllllllllllllllIIIlIIllIllIIll.getX();
        double lllllllllllllllllIIIlIIllIlIllll = lllllllllllllllllIIIlIIllIllIIll.getY();
        return lllllllllllllllllIIIlIIllIllIIlI >= lllllllllllllllllIIIlIIllIllIIII && lllllllllllllllllIIIlIIllIllIIlI <= lllllllllllllllllIIIlIIllIllIIII + lllllllllllllllllIIIlIIllIllIIll.width && lllllllllllllllllIIIlIIllIlIllII >= lllllllllllllllllIIIlIIllIlIllll && lllllllllllllllllIIIlIIllIlIllII <= lllllllllllllllllIIIlIIllIlIllll + lllllllllllllllllIIIlIIllIllIIll.height;
    }

    public double getX() {
        BoundingBox lllllllllllllllllIIIlIIllIllllIl;
        switch (lllllllllllllllllIIIlIIllIllllIl.x) {
            default: {
                return lllllllllllllllllIIIlIIllIllllIl.xOffset;
            }
            case Center: {
                return (double)Utils.getWindowWidth() / 2.0 - lllllllllllllllllIIIlIIllIllllIl.width / 2.0 + lllllllllllllllllIIIlIIllIllllIl.xOffset;
            }
            case Right: 
        }
        return (double)Utils.getWindowWidth() - lllllllllllllllllIIIlIIllIllllIl.width + lllllllllllllllllIIIlIIllIllllIl.xOffset;
    }

    public BoundingBox() {
        BoundingBox lllllllllllllllllIIIlIIllllllIII;
        lllllllllllllllllIIIlIIllllllIII.x = AlignmentX.Left;
        lllllllllllllllllIIIlIIllllllIII.y = AlignmentY.Top;
    }

    public void setY(int lllllllllllllllllIIIlIIllllIIlll) {
        BoundingBox lllllllllllllllllIIIlIIllllIlIII;
        lllllllllllllllllIIIlIIllllIlIII.addPos(0.0, (double)lllllllllllllllllIIIlIIllllIIlll - lllllllllllllllllIIIlIIllllIlIII.getY());
    }

    public void addPos(double lllllllllllllllllIIIlIIlllIIllIl, double lllllllllllllllllIIIlIIlllIIllII) {
        BoundingBox lllllllllllllllllIIIlIIlllIlIIll;
        lllllllllllllllllIIIlIIlllIlIIll.xOffset += lllllllllllllllllIIIlIIlllIIllIl;
        lllllllllllllllllIIIlIIlllIlIIll.yOffset += lllllllllllllllllIIIlIIlllIIllII;
        double lllllllllllllllllIIIlIIlllIlIIII = lllllllllllllllllIIIlIIlllIlIIll.getX();
        double lllllllllllllllllIIIlIIlllIIllll = lllllllllllllllllIIIlIIlllIlIIll.getY();
        switch (lllllllllllllllllIIIlIIlllIlIIll.x) {
            case Left: {
                double lllllllllllllllllIIIlIIlllIlllIl = (double)Utils.getWindowWidth() / 3.0;
                if (!(lllllllllllllllllIIIlIIlllIlIIII >= lllllllllllllllllIIIlIIlllIlllIl - lllllllllllllllllIIIlIIlllIlIIll.width / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.x = AlignmentX.Center;
                lllllllllllllllllIIIlIIlllIlIIll.xOffset = -lllllllllllllllllIIIlIIlllIlllIl / 2.0 + lllllllllllllllllIIIlIIlllIlIIII - lllllllllllllllllIIIlIIlllIlllIl + lllllllllllllllllIIIlIIlllIlIIll.width / 2.0;
                break;
            }
            case Center: {
                double lllllllllllllllllIIIlIIlllIlllII = (double)Utils.getWindowWidth() / 3.0;
                double lllllllllllllllllIIIlIIlllIllIll = (double)Utils.getWindowWidth() / 3.0 * 2.0;
                if (lllllllllllllllllIIIlIIlllIlIIII > lllllllllllllllllIIIlIIlllIllIll - lllllllllllllllllIIIlIIlllIlIIll.width / 2.0) {
                    lllllllllllllllllIIIlIIlllIlIIll.x = AlignmentX.Right;
                    lllllllllllllllllIIIlIIlllIlIIll.xOffset = -(lllllllllllllllllIIIlIIlllIlllII - lllllllllllllllllIIIlIIlllIlIIll.width) + (lllllllllllllllllIIIlIIlllIlllII - ((double)Utils.getWindowWidth() - lllllllllllllllllIIIlIIlllIlIIII));
                    break;
                }
                if (!(lllllllllllllllllIIIlIIlllIlIIII < lllllllllllllllllIIIlIIlllIlllII - lllllllllllllllllIIIlIIlllIlIIll.width / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.x = AlignmentX.Left;
                lllllllllllllllllIIIlIIlllIlIIll.xOffset = lllllllllllllllllIIIlIIlllIlIIII;
                break;
            }
            case Right: {
                double lllllllllllllllllIIIlIIlllIllIlI = (double)Utils.getWindowWidth() / 3.0;
                double lllllllllllllllllIIIlIIlllIllIIl = (double)Utils.getWindowWidth() / 3.0 * 2.0;
                if (!(lllllllllllllllllIIIlIIlllIlIIII <= lllllllllllllllllIIIlIIlllIllIIl - lllllllllllllllllIIIlIIlllIlIIll.width / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.x = AlignmentX.Center;
                lllllllllllllllllIIIlIIlllIlIIll.xOffset = -lllllllllllllllllIIIlIIlllIllIlI / 2.0 + lllllllllllllllllIIIlIIlllIlIIII - lllllllllllllllllIIIlIIlllIllIlI + lllllllllllllllllIIIlIIlllIlIIll.width / 2.0;
                break;
            }
        }
        if (lllllllllllllllllIIIlIIlllIlIIll.x == AlignmentX.Left && lllllllllllllllllIIIlIIlllIlIIll.xOffset < 0.0) {
            lllllllllllllllllIIIlIIlllIlIIll.xOffset = 0.0;
        } else if (lllllllllllllllllIIIlIIlllIlIIll.x == AlignmentX.Right && lllllllllllllllllIIIlIIlllIlIIll.xOffset > 0.0) {
            lllllllllllllllllIIIlIIlllIlIIll.xOffset = 0.0;
        }
        switch (lllllllllllllllllIIIlIIlllIlIIll.y) {
            case Top: {
                double lllllllllllllllllIIIlIIlllIllIII = (double)Utils.getWindowHeight() / 3.0;
                if (!(lllllllllllllllllIIIlIIlllIIllll >= lllllllllllllllllIIIlIIlllIllIII - lllllllllllllllllIIIlIIlllIlIIll.height / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.y = AlignmentY.Center;
                lllllllllllllllllIIIlIIlllIlIIll.yOffset = -lllllllllllllllllIIIlIIlllIllIII / 2.0 + lllllllllllllllllIIIlIIlllIIllll - lllllllllllllllllIIIlIIlllIllIII + lllllllllllllllllIIIlIIlllIlIIll.height / 2.0;
                break;
            }
            case Center: {
                double lllllllllllllllllIIIlIIlllIlIlll = (double)Utils.getWindowHeight() / 3.0;
                double lllllllllllllllllIIIlIIlllIlIllI = (double)Utils.getWindowHeight() / 3.0 * 2.0;
                if (lllllllllllllllllIIIlIIlllIIllll > lllllllllllllllllIIIlIIlllIlIllI - lllllllllllllllllIIIlIIlllIlIIll.height / 2.0) {
                    lllllllllllllllllIIIlIIlllIlIIll.y = AlignmentY.Bottom;
                    lllllllllllllllllIIIlIIlllIlIIll.yOffset = -(lllllllllllllllllIIIlIIlllIlIlll - lllllllllllllllllIIIlIIlllIlIIll.height) + (lllllllllllllllllIIIlIIlllIlIlll - ((double)Utils.getWindowHeight() - lllllllllllllllllIIIlIIlllIIllll));
                    break;
                }
                if (!(lllllllllllllllllIIIlIIlllIIllll < lllllllllllllllllIIIlIIlllIlIlll - lllllllllllllllllIIIlIIlllIlIIll.height / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.y = AlignmentY.Top;
                lllllllllllllllllIIIlIIlllIlIIll.yOffset = lllllllllllllllllIIIlIIlllIIllll;
                break;
            }
            case Bottom: {
                double lllllllllllllllllIIIlIIlllIlIlIl = (double)Utils.getWindowHeight() / 3.0;
                double lllllllllllllllllIIIlIIlllIlIlII = (double)Utils.getWindowHeight() / 3.0 * 2.0;
                if (!(lllllllllllllllllIIIlIIlllIIllll <= lllllllllllllllllIIIlIIlllIlIlII - lllllllllllllllllIIIlIIlllIlIIll.height / 2.0)) break;
                lllllllllllllllllIIIlIIlllIlIIll.y = AlignmentY.Center;
                lllllllllllllllllIIIlIIlllIlIIll.yOffset = -lllllllllllllllllIIIlIIlllIlIlIl / 2.0 + lllllllllllllllllIIIlIIlllIIllll - lllllllllllllllllIIIlIIlllIlIlIl + lllllllllllllllllIIIlIIlllIlIIll.height / 2.0;
                break;
            }
        }
        if (lllllllllllllllllIIIlIIlllIlIIll.y == AlignmentY.Top && lllllllllllllllllIIIlIIlllIlIIll.yOffset < 0.0) {
            lllllllllllllllllIIIlIIlllIlIIll.yOffset = 0.0;
        } else if (lllllllllllllllllIIIlIIlllIlIIll.y == AlignmentY.Bottom && lllllllllllllllllIIIlIIlllIlIIll.yOffset > 0.0) {
            lllllllllllllllllIIIlIIlllIlIIll.yOffset = 0.0;
        }
    }
}

