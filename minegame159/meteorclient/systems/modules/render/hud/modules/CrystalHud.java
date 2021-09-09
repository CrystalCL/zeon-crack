/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.ItemConvertible
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.render.RenderUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;

public class CrystalHud
extends HudElement {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> scale;

    @Override
    public void update(HudRenderer llIlllIlIIllI) {
        CrystalHud llIlllIlIIlIl;
        llIlllIlIIlIl.box.setSize(16.0 * llIlllIlIIlIl.scale.get(), 16.0 * llIlllIlIIlIl.scale.get());
    }

    @Override
    public void render(HudRenderer llIlllIlIIIII) {
        CrystalHud llIlllIIlllIl;
        double llIlllIIlllll = llIlllIIlllIl.box.getX() / llIlllIIlllIl.scale.get();
        double llIlllIIllllI = llIlllIIlllIl.box.getY() / llIlllIIlllIl.scale.get();
        if (llIlllIIlllIl.isInEditor()) {
            RenderUtils.drawItem(Items.END_CRYSTAL.getDefaultStack(), (int)llIlllIIlllll, (int)llIlllIIllllI, llIlllIIlllIl.scale.get(), true);
        } else if (InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).count > 0) {
            RenderUtils.drawItem(new ItemStack((ItemConvertible)Items.END_CRYSTAL, InvUtils.findItemWithCount((Item)Items.END_CRYSTAL).count), (int)llIlllIIlllll, (int)llIlllIIllllI, llIlllIIlllIl.scale.get(), true);
        }
    }

    public CrystalHud(HUD llIlllIlIlIIl) {
        super(llIlllIlIlIIl, "crystals", "Displays the amount of crystals in your inventory.");
        CrystalHud llIlllIlIlIlI;
        llIlllIlIlIlI.sgGeneral = llIlllIlIlIlI.settings.getDefaultGroup();
        llIlllIlIlIlI.scale = llIlllIlIlIlI.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of crystal counter.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
    }
}

