/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render;

import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class HandView
extends Module {
    public final /* synthetic */ Setting<Double> scaleY;
    public final /* synthetic */ Setting<Double> posX;
    public final /* synthetic */ Setting<Double> rotationY;
    public final /* synthetic */ Setting<Double> scaleX;
    private final /* synthetic */ SettingGroup sgDefault;
    public final /* synthetic */ Setting<Double> rotationZ;
    public final /* synthetic */ Setting<Double> posY;
    public final /* synthetic */ Setting<Double> posZ;
    public final /* synthetic */ Setting<Double> offSwing;
    public final /* synthetic */ Setting<Double> scaleZ;
    private final /* synthetic */ SettingGroup sgSwing;
    public final /* synthetic */ Setting<Double> rotationX;
    public final /* synthetic */ Setting<Double> mainSwing;

    public HandView() {
        super(Categories.Render, "hand-view", "Alters the way items are rendered in your hands.");
        HandView lllllllllllllllllIlIIIlIIlIIIIIl;
        lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault = lllllllllllllllllIlIIIlIIlIIIIIl.settings.getDefaultGroup();
        lllllllllllllllllIlIIIlIIlIIIIIl.sgSwing = lllllllllllllllllIlIIIlIIlIIIIIl.settings.createGroup("Swing");
        lllllllllllllllllIlIIIlIIlIIIIIl.rotationX = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("rotation-x").description("The X rotation of your hands.").defaultValue(0.0).sliderMin(-0.2).sliderMax(0.2).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.rotationY = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("rotation-y").description("The Y rotation of your hands.").defaultValue(0.0).sliderMin(-0.2).sliderMax(0.2).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.rotationZ = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("rotation-z").description("The Z rotation of your hands.").defaultValue(0.0).sliderMin(-0.25).sliderMax(0.25).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.scaleX = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("scale-x").description("The X scale of the items rendered in your hands.").defaultValue(0.75).sliderMin(0.0).sliderMax(1.5).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.scaleY = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("scale-y").description("The Y scale of the items rendered in your hands.").defaultValue(0.6).sliderMin(0.0).sliderMax(2.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.scaleZ = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("scale-z").description("The Z scale of the items rendered in your hands.").defaultValue(1.0).sliderMin(0.0).sliderMax(5.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.posX = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("pos-x").description("The X offset of your hands.").defaultValue(0.0).sliderMin(-3.0).sliderMax(3.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.posY = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("pos-y").description("The Y offset of your hands.").defaultValue(0.0).sliderMin(-3.0).sliderMax(3.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.posZ = lllllllllllllllllIlIIIlIIlIIIIIl.sgDefault.add(new DoubleSetting.Builder().name("pos-z").description("The Z offset of your hands.").defaultValue(-0.1).sliderMin(-3.0).sliderMax(3.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.mainSwing = lllllllllllllllllIlIIIlIIlIIIIIl.sgSwing.add(new DoubleSetting.Builder().name("main-swing-progress").description("The swing progress of your mainhand.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
        lllllllllllllllllIlIIIlIIlIIIIIl.offSwing = lllllllllllllllllIlIIIlIIlIIIIIl.sgSwing.add(new DoubleSetting.Builder().name("off-swing-progress").description("The swing progress of your offhand.").defaultValue(0.0).sliderMin(0.0).sliderMax(1.0).build());
    }
}

