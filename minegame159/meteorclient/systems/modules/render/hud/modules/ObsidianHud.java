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

public class ObsidianHud
extends HudElement {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> scale;

    @Override
    public void render(HudRenderer lIIIIlIIlIllIll) {
        ObsidianHud lIIIIlIIlIlllII;
        double lIIIIlIIlIllIlI = lIIIIlIIlIlllII.box.getX() / lIIIIlIIlIlllII.scale.get();
        double lIIIIlIIlIllIIl = lIIIIlIIlIlllII.box.getY() / lIIIIlIIlIlllII.scale.get();
        if (lIIIIlIIlIlllII.isInEditor()) {
            RenderUtils.drawItem(Items.OBSIDIAN.getDefaultStack(), (int)lIIIIlIIlIllIlI, (int)lIIIIlIIlIllIIl, lIIIIlIIlIlllII.scale.get(), true);
        } else if (InvUtils.findItemWithCount((Item)Items.OBSIDIAN).count > 0) {
            RenderUtils.drawItem(new ItemStack((ItemConvertible)Items.OBSIDIAN, InvUtils.findItemWithCount((Item)Items.OBSIDIAN).count), (int)lIIIIlIIlIllIlI, (int)lIIIIlIIlIllIIl, lIIIIlIIlIlllII.scale.get(), true);
        }
    }

    @Override
    public void update(HudRenderer lIIIIlIIllIIIIl) {
        ObsidianHud lIIIIlIIllIIIII;
        lIIIIlIIllIIIII.box.setSize(16.0 * lIIIIlIIllIIIII.scale.get(), 16.0 * lIIIIlIIllIIIII.scale.get());
    }

    public ObsidianHud(HUD lIIIIlIIllIIllI) {
        super(lIIIIlIIllIIllI, "obsidians", "Displays the amount of obsidians in your inventory.");
        ObsidianHud lIIIIlIIllIIlll;
        lIIIIlIIllIIlll.sgGeneral = lIIIIlIIllIIlll.settings.getDefaultGroup();
        lIIIIlIIllIIlll.scale = lIIIIlIIllIIlll.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of obsidian counter.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(4.0).build());
    }
}

