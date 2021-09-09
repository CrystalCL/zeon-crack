/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 */
package minegame159.meteorclient.utils.render.color;

import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.nbt.NbtCompound;

public class SettingColor
extends Color {
    private static final /* synthetic */ float[] hsb;
    public /* synthetic */ double rainbowSpeed;

    public SettingColor(int llllllllllllllllIlIlIlIlllIIIlll, int llllllllllllllllIlIlIlIlllIIIllI, int llllllllllllllllIlIlIlIlllIIIlIl, double llllllllllllllllIlIlIlIllIllllll) {
        llllllllllllllllIlIlIlIlllIIlIII(llllllllllllllllIlIlIlIlllIIIlll, llllllllllllllllIlIlIlIlllIIIllI, llllllllllllllllIlIlIlIlllIIIlIl, 255, llllllllllllllllIlIlIlIllIllllll);
        SettingColor llllllllllllllllIlIlIlIlllIIlIII;
    }

    public SettingColor() {
        SettingColor llllllllllllllllIlIlIlIlllIllIlI;
    }

    @Override
    public NbtCompound toTag() {
        SettingColor llllllllllllllllIlIlIlIlIllllIlI;
        NbtCompound llllllllllllllllIlIlIlIlIllllIIl = super.toTag();
        llllllllllllllllIlIlIlIlIllllIIl.putDouble("rainbowSpeed", llllllllllllllllIlIlIlIlIllllIlI.rainbowSpeed);
        return llllllllllllllllIlIlIlIlIllllIIl;
    }

    public SettingColor(int llllllllllllllllIlIlIlIllIllIIll, int llllllllllllllllIlIlIlIllIllIlll, int llllllllllllllllIlIlIlIllIllIllI, int llllllllllllllllIlIlIlIllIllIlIl) {
        super(llllllllllllllllIlIlIlIllIllIIll, llllllllllllllllIlIlIlIllIllIlll, llllllllllllllllIlIlIlIllIllIllI, llllllllllllllllIlIlIlIllIllIlIl);
        SettingColor llllllllllllllllIlIlIlIllIllIlII;
    }

    @Override
    public void set(Color llllllllllllllllIlIlIlIlIlllllIl) {
        SettingColor llllllllllllllllIlIlIlIlIllllllI;
        super.set(llllllllllllllllIlIlIlIlIlllllIl);
        if (llllllllllllllllIlIlIlIlIlllllIl instanceof SettingColor) {
            llllllllllllllllIlIlIlIlIllllllI.rainbowSpeed = ((SettingColor)llllllllllllllllIlIlIlIlIlllllIl).rainbowSpeed;
        }
    }

    public SettingColor(SettingColor llllllllllllllllIlIlIlIllIIIlIll) {
        super(llllllllllllllllIlIlIlIllIIIlIll);
        SettingColor llllllllllllllllIlIlIlIllIIIllII;
        llllllllllllllllIlIlIlIllIIIllII.rainbowSpeed = llllllllllllllllIlIlIlIllIIIlIll.rainbowSpeed;
    }

    static {
        hsb = new float[3];
    }

    public SettingColor(int llllllllllllllllIlIlIlIllIIlIIll, int llllllllllllllllIlIlIlIllIIlIIlI, int llllllllllllllllIlIlIlIllIIlIlll, int llllllllllllllllIlIlIlIllIIlIIII, double llllllllllllllllIlIlIlIllIIlIlIl) {
        super(llllllllllllllllIlIlIlIllIIlIIll, llllllllllllllllIlIlIlIllIIlIIlI, llllllllllllllllIlIlIlIllIIlIlll, llllllllllllllllIlIlIlIllIIlIIII);
        SettingColor llllllllllllllllIlIlIlIllIIlIlII;
        llllllllllllllllIlIlIlIllIIlIlII.rainbowSpeed = llllllllllllllllIlIlIlIllIIlIlIl;
    }

    public SettingColor(float llllllllllllllllIlIlIlIllIlIIlII, float llllllllllllllllIlIlIlIllIlIlIII, float llllllllllllllllIlIlIlIllIlIIIlI, float llllllllllllllllIlIlIlIllIlIIllI) {
        super(llllllllllllllllIlIlIlIllIlIIlII, llllllllllllllllIlIlIlIllIlIlIII, llllllllllllllllIlIlIlIllIlIIIlI, llllllllllllllllIlIlIlIllIlIIllI);
        SettingColor llllllllllllllllIlIlIlIllIlIlIlI;
    }

    public SettingColor(int llllllllllllllllIlIlIlIlllIlIlII, int llllllllllllllllIlIlIlIlllIIllll, int llllllllllllllllIlIlIlIlllIlIIlI) {
        super(llllllllllllllllIlIlIlIlllIlIlII, llllllllllllllllIlIlIlIlllIIllll, llllllllllllllllIlIlIlIlllIlIIlI);
        SettingColor llllllllllllllllIlIlIlIlllIlIIIl;
    }

    @Override
    public SettingColor fromTag(NbtCompound llllllllllllllllIlIlIlIlIlllIIll) {
        SettingColor llllllllllllllllIlIlIlIlIlllIIlI;
        super.fromTag(llllllllllllllllIlIlIlIlIlllIIll);
        llllllllllllllllIlIlIlIlIlllIIlI.rainbowSpeed = llllllllllllllllIlIlIlIlIlllIIll.getDouble("rainbowSpeed");
        return llllllllllllllllIlIlIlIlIlllIIlI;
    }

    public void update() {
        SettingColor llllllllllllllllIlIlIlIllIIIIlIl;
        if (llllllllllllllllIlIlIlIllIIIIlIl.rainbowSpeed > 0.0) {
            java.awt.Color.RGBtoHSB(llllllllllllllllIlIlIlIllIIIIlIl.r, llllllllllllllllIlIlIlIllIIIIlIl.g, llllllllllllllllIlIlIlIllIIIIlIl.b, hsb);
            int llllllllllllllllIlIlIlIllIIIIllI = java.awt.Color.HSBtoRGB(hsb[0] + (float)llllllllllllllllIlIlIlIllIIIIlIl.rainbowSpeed, 1.0f, 1.0f);
            llllllllllllllllIlIlIlIllIIIIlIl.r = Color.toRGBAR(llllllllllllllllIlIlIlIllIIIIllI);
            llllllllllllllllIlIlIlIllIIIIlIl.g = Color.toRGBAG(llllllllllllllllIlIlIlIllIIIIllI);
            llllllllllllllllIlIlIlIllIIIIlIl.b = Color.toRGBAB(llllllllllllllllIlIlIlIllIIIIllI);
        }
    }
}

