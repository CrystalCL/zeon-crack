/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class AntiLevitation
extends Module {
    private final /* synthetic */ Setting<Boolean> applyGravity;
    private final /* synthetic */ SettingGroup sgGeneral;

    public AntiLevitation() {
        super(Categories.Movement, "anti-levitation", "Prevents the levitation effect from working.");
        AntiLevitation lllIIIIIllIIII;
        lllIIIIIllIIII.sgGeneral = lllIIIIIllIIII.settings.getDefaultGroup();
        lllIIIIIllIIII.applyGravity = lllIIIIIllIIII.sgGeneral.add(new BoolSetting.Builder().name("gravity").description("Applies gravity.").defaultValue(false).build());
    }

    public boolean isApplyGravity() {
        AntiLevitation lllIIIIIlIllIl;
        return lllIIIIIlIllIl.applyGravity.get();
    }
}

