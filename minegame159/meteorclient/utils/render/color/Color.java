/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.utils.render.color;

import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.nbt.NbtCompound;

public class Color
implements ISerializable<Color> {
    public /* synthetic */ int b;
    public /* synthetic */ int a;
    public /* synthetic */ int g;
    public /* synthetic */ int r;

    public void set(Color llllllllllllllllllIIlIlllIIlllII) {
        Color llllllllllllllllllIIlIlllIIlllIl;
        llllllllllllllllllIIlIlllIIlllIl.r = llllllllllllllllllIIlIlllIIlllII.r;
        llllllllllllllllllIIlIlllIIlllIl.g = llllllllllllllllllIIlIlllIIlllII.g;
        llllllllllllllllllIIlIlllIIlllIl.b = llllllllllllllllllIIlIlllIIlllII.b;
        llllllllllllllllllIIlIlllIIlllIl.a = llllllllllllllllllIIlIlllIIlllII.a;
        llllllllllllllllllIIlIlllIIlllIl.validate();
    }

    public void set(int llllllllllllllllllIIlIlllIlIlIlI, int llllllllllllllllllIIlIlllIlIlIIl, int llllllllllllllllllIIlIlllIlIlIII, int llllllllllllllllllIIlIlllIlIIlll) {
        Color llllllllllllllllllIIlIlllIlIlIll;
        llllllllllllllllllIIlIlllIlIlIll.r = llllllllllllllllllIIlIlllIlIlIlI;
        llllllllllllllllllIIlIlllIlIlIll.g = llllllllllllllllllIIlIlllIlIlIIl;
        llllllllllllllllllIIlIlllIlIlIll.b = llllllllllllllllllIIlIlllIlIlIII;
        llllllllllllllllllIIlIlllIlIlIll.a = llllllllllllllllllIIlIlllIlIIlll;
        llllllllllllllllllIIlIlllIlIlIll.validate();
    }

    public String toString() {
        Color llllllllllllllllllIIlIlllIIIIlII;
        return String.valueOf(new StringBuilder().append(llllllllllllllllllIIlIlllIIIIlII.r).append(" ").append(llllllllllllllllllIIlIlllIIIIlII.g).append(" ").append(llllllllllllllllllIIlIlllIIIIlII.b).append(" ").append(llllllllllllllllllIIlIlllIIIIlII.a));
    }

    @Override
    public Color fromTag(NbtCompound llllllllllllllllllIIlIlllIIIlIIl) {
        Color llllllllllllllllllIIlIlllIIIlIlI;
        llllllllllllllllllIIlIlllIIIlIlI.r = llllllllllllllllllIIlIlllIIIlIIl.getInt("r");
        llllllllllllllllllIIlIlllIIIlIlI.g = llllllllllllllllllIIlIlllIIIlIIl.getInt("g");
        llllllllllllllllllIIlIlllIIIlIlI.b = llllllllllllllllllIIlIlllIIIlIIl.getInt("b");
        llllllllllllllllllIIlIlllIIIlIlI.a = llllllllllllllllllIIlIlllIIIlIIl.getInt("a");
        llllllllllllllllllIIlIlllIIIlIlI.validate();
        return llllllllllllllllllIIlIlllIIIlIlI;
    }

    public Color(java.awt.Color llllllllllllllllllIIlIllllllllll) {
        Color llllllllllllllllllIIllIIIIIIIIlI;
        llllllllllllllllllIIllIIIIIIIIlI.r = llllllllllllllllllIIlIllllllllll.getRed();
        llllllllllllllllllIIllIIIIIIIIlI.g = llllllllllllllllllIIlIllllllllll.getGreen();
        llllllllllllllllllIIllIIIIIIIIlI.b = llllllllllllllllllIIlIllllllllll.getBlue();
        llllllllllllllllllIIllIIIIIIIIlI.a = llllllllllllllllllIIlIllllllllll.getAlpha();
    }

    public int getPacked() {
        Color llllllllllllllllllIIlIlllIIlIIll;
        return Color.fromRGBA(llllllllllllllllllIIlIlllIIlIIll.r, llllllllllllllllllIIlIlllIIlIIll.g, llllllllllllllllllIIlIlllIIlIIll.b, llllllllllllllllllIIlIlllIIlIIll.a);
    }

    public Color(int llllllllllllllllllIIllIIIIllIIIl, int llllllllllllllllllIIllIIIIllIlII, int llllllllllllllllllIIllIIIIlIllll) {
        Color llllllllllllllllllIIllIIIIllIllI;
        llllllllllllllllllIIllIIIIllIllI.r = llllllllllllllllllIIllIIIIllIIIl;
        llllllllllllllllllIIllIIIIllIllI.g = llllllllllllllllllIIllIIIIllIlII;
        llllllllllllllllllIIllIIIIllIllI.b = llllllllllllllllllIIllIIIIlIllll;
        llllllllllllllllllIIllIIIIllIllI.a = 255;
        llllllllllllllllllIIllIIIIllIllI.validate();
    }

    public Vec3d getVec3d() {
        Color llllllllllllllllllIIlIlllIIlIllI;
        return new Vec3d((double)llllllllllllllllllIIlIlllIIlIllI.r / 255.0, (double)llllllllllllllllllIIlIlllIIlIllI.g / 255.0, (double)llllllllllllllllllIIlIlllIIlIllI.b / 255.0);
    }

    public static int toRGBAR(int llllllllllllllllllIIlIllllllIIII) {
        return llllllllllllllllllIIlIllllllIIII >> 16 & 0xFF;
    }

    public Color() {
        llllllllllllllllllIIllIIIIllllII(255, 255, 255, 255);
        Color llllllllllllllllllIIllIIIIllllII;
    }

    public void validate() {
        Color llllllllllllllllllIIlIlllIIllIIl;
        if (llllllllllllllllllIIlIlllIIllIIl.r < 0) {
            llllllllllllllllllIIlIlllIIllIIl.r = 0;
        } else if (llllllllllllllllllIIlIlllIIllIIl.r > 255) {
            llllllllllllllllllIIlIlllIIllIIl.r = 255;
        }
        if (llllllllllllllllllIIlIlllIIllIIl.g < 0) {
            llllllllllllllllllIIlIlllIIllIIl.g = 0;
        } else if (llllllllllllllllllIIlIlllIIllIIl.g > 255) {
            llllllllllllllllllIIlIlllIIllIIl.g = 255;
        }
        if (llllllllllllllllllIIlIlllIIllIIl.b < 0) {
            llllllllllllllllllIIlIlllIIllIIl.b = 0;
        } else if (llllllllllllllllllIIlIlllIIllIIl.b > 255) {
            llllllllllllllllllIIlIlllIIllIIl.b = 255;
        }
        if (llllllllllllllllllIIlIlllIIllIIl.a < 0) {
            llllllllllllllllllIIlIlllIIllIIl.a = 0;
        } else if (llllllllllllllllllIIlIlllIIllIIl.a > 255) {
            llllllllllllllllllIIlIlllIIllIIl.a = 255;
        }
    }

    public static int toRGBAG(int llllllllllllllllllIIlIlllllIllIl) {
        return llllllllllllllllllIIlIlllllIllIl >> 8 & 0xFF;
    }

    public Color(int llllllllllllllllllIIllIIIIIIllIl) {
        Color llllllllllllllllllIIllIIIIIIlllI;
        llllllllllllllllllIIllIIIIIIlllI.r = Color.toRGBAR(llllllllllllllllllIIllIIIIIIllIl);
        llllllllllllllllllIIllIIIIIIlllI.g = Color.toRGBAG(llllllllllllllllllIIllIIIIIIllIl);
        llllllllllllllllllIIllIIIIIIlllI.b = Color.toRGBAB(llllllllllllllllllIIllIIIIIIllIl);
        llllllllllllllllllIIllIIIIIIlllI.a = Color.toRGBAA(llllllllllllllllllIIllIIIIIIllIl);
    }

    public Color(int llllllllllllllllllIIllIIIIlIlIII, int llllllllllllllllllIIllIIIIlIIlll, int llllllllllllllllllIIllIIIIlIIllI, int llllllllllllllllllIIllIIIIlIIlIl) {
        Color llllllllllllllllllIIllIIIIlIIlII;
        llllllllllllllllllIIllIIIIlIIlII.r = llllllllllllllllllIIllIIIIlIlIII;
        llllllllllllllllllIIllIIIIlIIlII.g = llllllllllllllllllIIllIIIIlIIlll;
        llllllllllllllllllIIllIIIIlIIlII.b = llllllllllllllllllIIllIIIIlIIllI;
        llllllllllllllllllIIllIIIIlIIlII.a = llllllllllllllllllIIllIIIIlIIlIl;
        llllllllllllllllllIIllIIIIlIIlII.validate();
    }

    public static int toRGBAA(int llllllllllllllllllIIlIlllllIlIII) {
        return llllllllllllllllllIIlIlllllIlIII >> 24 & 0xFF;
    }

    public Color(float llllllllllllllllllIIllIIIIIlIlII, float llllllllllllllllllIIllIIIIIlIIll, float llllllllllllllllllIIllIIIIIlIlll, float llllllllllllllllllIIllIIIIIlIllI) {
        Color llllllllllllllllllIIllIIIIIlIlIl;
        llllllllllllllllllIIllIIIIIlIlIl.r = (int)(llllllllllllllllllIIllIIIIIlIlII * 255.0f);
        llllllllllllllllllIIllIIIIIlIlIl.g = (int)(llllllllllllllllllIIllIIIIIlIIll * 255.0f);
        llllllllllllllllllIIllIIIIIlIlIl.b = (int)(llllllllllllllllllIIllIIIIIlIlll * 255.0f);
        llllllllllllllllllIIllIIIIIlIlIl.a = (int)(llllllllllllllllllIIllIIIIIlIllI * 255.0f);
        llllllllllllllllllIIllIIIIIlIlIl.validate();
    }

    public static Color fromHsv(double llllllllllllllllllIIlIllllIIlIII, double llllllllllllllllllIIlIllllIIIlll, double llllllllllllllllllIIlIlllIllIlll) {
        double llllllllllllllllllIIlIlllIllllIl;
        double llllllllllllllllllIIlIlllIlllllI;
        double llllllllllllllllllIIlIlllIllllll;
        if (llllllllllllllllllIIlIllllIIIlll <= 0.0) {
            double llllllllllllllllllIIlIllllIllIlI = llllllllllllllllllIIlIlllIllIlll;
            double llllllllllllllllllIIlIllllIllIIl = llllllllllllllllllIIlIlllIllIlll;
            double llllllllllllllllllIIlIllllIllIII = llllllllllllllllllIIlIlllIllIlll;
            return new Color((int)(llllllllllllllllllIIlIllllIllIlI * 255.0), (int)(llllllllllllllllllIIlIllllIllIIl * 255.0), (int)(llllllllllllllllllIIlIllllIllIII * 255.0), 255);
        }
        double llllllllllllllllllIIlIllllIIIlIl = llllllllllllllllllIIlIllllIIlIII;
        if (llllllllllllllllllIIlIllllIIIlIl >= 360.0) {
            llllllllllllllllllIIlIllllIIIlIl = 0.0;
        }
        int llllllllllllllllllIIlIllllIIIIII = (int)(llllllllllllllllllIIlIllllIIIlIl /= 60.0);
        double llllllllllllllllllIIlIllllIIIIIl = llllllllllllllllllIIlIllllIIIlIl - (double)llllllllllllllllllIIlIllllIIIIII;
        double llllllllllllllllllIIlIllllIIIlII = llllllllllllllllllIIlIlllIllIlll * (1.0 - llllllllllllllllllIIlIllllIIIlll);
        double llllllllllllllllllIIlIllllIIIIll = llllllllllllllllllIIlIlllIllIlll * (1.0 - llllllllllllllllllIIlIllllIIIlll * llllllllllllllllllIIlIllllIIIIIl);
        double llllllllllllllllllIIlIllllIIIIlI = llllllllllllllllllIIlIlllIllIlll * (1.0 - llllllllllllllllllIIlIllllIIIlll * (1.0 - llllllllllllllllllIIlIllllIIIIIl));
        switch (llllllllllllllllllIIlIllllIIIIII) {
            case 0: {
                double llllllllllllllllllIIlIllllIlIlll = llllllllllllllllllIIlIlllIllIlll;
                double llllllllllllllllllIIlIllllIlIllI = llllllllllllllllllIIlIllllIIIIlI;
                double llllllllllllllllllIIlIllllIlIlIl = llllllllllllllllllIIlIllllIIIlII;
                break;
            }
            case 1: {
                double llllllllllllllllllIIlIllllIlIlII = llllllllllllllllllIIlIllllIIIIll;
                double llllllllllllllllllIIlIllllIlIIll = llllllllllllllllllIIlIlllIllIlll;
                double llllllllllllllllllIIlIllllIlIIlI = llllllllllllllllllIIlIllllIIIlII;
                break;
            }
            case 2: {
                double llllllllllllllllllIIlIllllIlIIIl = llllllllllllllllllIIlIllllIIIlII;
                double llllllllllllllllllIIlIllllIlIIII = llllllllllllllllllIIlIlllIllIlll;
                double llllllllllllllllllIIlIllllIIllll = llllllllllllllllllIIlIllllIIIIlI;
                break;
            }
            case 3: {
                double llllllllllllllllllIIlIllllIIlllI = llllllllllllllllllIIlIllllIIIlII;
                double llllllllllllllllllIIlIllllIIllIl = llllllllllllllllllIIlIllllIIIIll;
                double llllllllllllllllllIIlIllllIIllII = llllllllllllllllllIIlIlllIllIlll;
                break;
            }
            case 4: {
                double llllllllllllllllllIIlIllllIIlIll = llllllllllllllllllIIlIllllIIIIlI;
                double llllllllllllllllllIIlIllllIIlIlI = llllllllllllllllllIIlIllllIIIlII;
                double llllllllllllllllllIIlIllllIIlIIl = llllllllllllllllllIIlIlllIllIlll;
                break;
            }
            default: {
                llllllllllllllllllIIlIlllIllllll = llllllllllllllllllIIlIlllIllIlll;
                llllllllllllllllllIIlIlllIlllllI = llllllllllllllllllIIlIllllIIIlII;
                llllllllllllllllllIIlIlllIllllIl = llllllllllllllllllIIlIllllIIIIll;
            }
        }
        return new Color((int)(llllllllllllllllllIIlIlllIllllll * 255.0), (int)(llllllllllllllllllIIlIlllIlllllI * 255.0), (int)(llllllllllllllllllIIlIlllIllllIl * 255.0), 255);
    }

    public static int fromRGBA(int llllllllllllllllllIIlIllllllIllI, int llllllllllllllllllIIlIllllllIlIl, int llllllllllllllllllIIlIllllllIlII, int llllllllllllllllllIIlIllllllIIll) {
        return (llllllllllllllllllIIlIllllllIllI << 16) + (llllllllllllllllllIIlIllllllIlIl << 8) + llllllllllllllllllIIlIllllllIlII + (llllllllllllllllllIIlIllllllIIll << 24);
    }

    public static int toRGBAB(int llllllllllllllllllIIlIlllllIlIlI) {
        return llllllllllllllllllIIlIlllllIlIlI & 0xFF;
    }

    public Color(Color llllllllllllllllllIIllIIIIIIIlll) {
        Color llllllllllllllllllIIllIIIIIIlIII;
        llllllllllllllllllIIllIIIIIIlIII.r = llllllllllllllllllIIllIIIIIIIlll.r;
        llllllllllllllllllIIllIIIIIIlIII.g = llllllllllllllllllIIllIIIIIIIlll.g;
        llllllllllllllllllIIllIIIIIIlIII.b = llllllllllllllllllIIllIIIIIIIlll.b;
        llllllllllllllllllIIllIIIIIIlIII.a = llllllllllllllllllIIllIIIIIIIlll.a;
    }

    @Override
    public NbtCompound toTag() {
        Color llllllllllllllllllIIlIlllIIIlllI;
        NbtCompound llllllllllllllllllIIlIlllIIIllll = new NbtCompound();
        llllllllllllllllllIIlIlllIIIllll.putInt("r", llllllllllllllllllIIlIlllIIIlllI.r);
        llllllllllllllllllIIlIlllIIIllll.putInt("g", llllllllllllllllllIIlIlllIIIlllI.g);
        llllllllllllllllllIIlIlllIIIllll.putInt("b", llllllllllllllllllIIlIlllIIIlllI.b);
        llllllllllllllllllIIlIlllIIIllll.putInt("a", llllllllllllllllllIIlIlllIIIlllI.a);
        return llllllllllllllllllIIlIlllIIIllll;
    }
}

