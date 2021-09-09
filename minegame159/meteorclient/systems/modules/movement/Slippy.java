/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.Collections;
import java.util.List;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.block.Block;

public class Slippy
extends Module {
    public final /* synthetic */ Setting<List<Block>> blocks;
    public final /* synthetic */ Setting<Double> slippness;
    private final /* synthetic */ SettingGroup sgGeneral;

    public Slippy() {
        super(Categories.Movement, "slippy", "Makes blocks slippery like ice.");
        Slippy lIllllllIIIIl;
        lIllllllIIIIl.sgGeneral = lIllllllIIIIl.settings.getDefaultGroup();
        lIllllllIIIIl.slippness = lIllllllIIIIl.sgGeneral.add(new DoubleSetting.Builder().name("slippness").description("Decide how slippery blocks should be").min(0.0).max(1.1).sliderMax(1.1).defaultValue(1.02).build());
        lIllllllIIIIl.blocks = lIllllllIIIIl.sgGeneral.add(new BlockListSetting.Builder().name("ignored blocks").description("Decide which blocks not to slip on").defaultValue(Collections.emptyList()).build());
    }
}

