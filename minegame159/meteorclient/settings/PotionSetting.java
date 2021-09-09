/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.MyPotion;

public class PotionSetting
extends EnumSetting<MyPotion> {
    public PotionSetting(String llIIIlllIIIlIlI, String llIIIlllIIIllll, MyPotion llIIIlllIIIlIII, Consumer<MyPotion> llIIIlllIIIIlll, Consumer<Setting<MyPotion>> llIIIlllIIIIllI) {
        super(llIIIlllIIIlIlI, llIIIlllIIIllll, llIIIlllIIIlIII, llIIIlllIIIIlll, llIIIlllIIIIllI);
        PotionSetting llIIIlllIIIlIll;
    }

    public static class Builder
    extends EnumSetting.Builder<MyPotion> {
        @Override
        public EnumSetting<MyPotion> build() {
            Builder llIIlIlIIIIllII;
            return new PotionSetting(llIIlIlIIIIllII.name, llIIlIlIIIIllII.description, (MyPotion)llIIlIlIIIIllII.defaultValue, llIIlIlIIIIllII.onChanged, llIIlIlIIIIllII.onModuleActivated);
        }

        public Builder() {
            Builder llIIlIlIIIlIIII;
        }
    }
}

