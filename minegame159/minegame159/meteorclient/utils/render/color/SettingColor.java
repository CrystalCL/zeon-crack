/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.render.color;

import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SettingColor
extends Color {
    public double rainbowSpeed;
    private static final float[] hsb = new float[3];

    @Override
    public void set(Color color) {
        super.set(color);
        if (color instanceof SettingColor) {
            this.rainbowSpeed = ((SettingColor)color).rainbowSpeed;
        }
    }

    public SettingColor(int n, int n2, int n3, int n4, double d) {
        super(n, n2, n3, n4);
        this.rainbowSpeed = d;
    }

    public SettingColor(SettingColor settingColor) {
        super(settingColor);
        this.rainbowSpeed = settingColor.rainbowSpeed;
    }

    @Override
    public SettingColor fromTag(NbtCompound NbtCompound2) {
        super.fromTag(NbtCompound2);
        this.rainbowSpeed = NbtCompound2.getDouble("rainbowSpeed");
        return this;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = super.toTag();
        NbtCompound2.putDouble("rainbowSpeed", this.rainbowSpeed);
        return NbtCompound2;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public SettingColor(int n, int n2, int n3, int n4) {
        super(n, n2, n3, n4);
    }

    public SettingColor() {
    }

    public SettingColor(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
    }

    public SettingColor(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public void update() {
        if (this.rainbowSpeed > 0.0) {
            java.awt.Color.RGBtoHSB(this.r, this.g, this.b, hsb);
            int n = java.awt.Color.HSBtoRGB(hsb[0] + (float)this.rainbowSpeed, 1.0f, 1.0f);
            this.r = Color.toRGBAR(n);
            this.g = Color.toRGBAG(n);
            this.b = Color.toRGBAB(n);
        }
    }

    @Override
    public Color fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public SettingColor(int n, int n2, int n3, double d) {
        this(n, n2, n3, 255, d);
    }
}

