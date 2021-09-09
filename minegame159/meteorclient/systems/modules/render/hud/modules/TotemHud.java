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

public class TotemHud
extends HudElement {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> scale;

    @Override
    public void update(HudRenderer llllllllllllllllIlIlllIlIIlIllII) {
        TotemHud llllllllllllllllIlIlllIlIIlIlIll;
        llllllllllllllllIlIlllIlIIlIlIll.box.setSize(16.0 * llllllllllllllllIlIlllIlIIlIlIll.scale.get(), 16.0 * llllllllllllllllIlIlllIlIIlIlIll.scale.get());
    }

    public TotemHud(HUD llllllllllllllllIlIlllIlIIlIllll) {
        super(llllllllllllllllIlIlllIlIIlIllll, "totems", "Displays the amount of totems in your inventory.");
        TotemHud llllllllllllllllIlIlllIlIIllIIlI;
        llllllllllllllllIlIlllIlIIllIIlI.sgGeneral = llllllllllllllllIlIlllIlIIllIIlI.settings.getDefaultGroup();
        llllllllllllllllIlIlllIlIIllIIlI.scale = llllllllllllllllIlIlllIlIIllIIlI.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of totem counter.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
    }

    @Override
    public void render(HudRenderer llllllllllllllllIlIlllIlIIlIIllI) {
        TotemHud llllllllllllllllIlIlllIlIIlIIlll;
        double llllllllllllllllIlIlllIlIIlIIlIl = llllllllllllllllIlIlllIlIIlIIlll.box.getX() / llllllllllllllllIlIlllIlIIlIIlll.scale.get();
        double llllllllllllllllIlIlllIlIIlIIlII = llllllllllllllllIlIlllIlIIlIIlll.box.getY() / llllllllllllllllIlIlllIlIIlIIlll.scale.get();
        if (llllllllllllllllIlIlllIlIIlIIlll.isInEditor()) {
            RenderUtils.drawItem(Items.TOTEM_OF_UNDYING.getDefaultStack(), (int)llllllllllllllllIlIlllIlIIlIIlIl, (int)llllllllllllllllIlIlllIlIIlIIlII, llllllllllllllllIlIlllIlIIlIIlll.scale.get(), true);
        } else if (InvUtils.findItemWithCount((Item)Items.TOTEM_OF_UNDYING).count > 0) {
            RenderUtils.drawItem(new ItemStack((ItemConvertible)Items.TOTEM_OF_UNDYING, InvUtils.findItemWithCount((Item)Items.TOTEM_OF_UNDYING).count), (int)llllllllllllllllIlIlllIlIIlIIlIl, (int)llllllllllllllllIlIlllIlIIlIIlII, llllllllllllllllIlIlllIlIIlIIlll.scale.get(), true);
        }
    }
}

