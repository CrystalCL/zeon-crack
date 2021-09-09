/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class BowSpam
extends Module {
    private /* synthetic */ boolean wasHoldingRightClick;
    private final /* synthetic */ Setting<Boolean> onlyWhenHoldingRightClick;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> charge;
    private /* synthetic */ boolean wasBow;

    private void setPressed(boolean lIlIIlIIIIIIIll) {
        BowSpam lIlIIlIIIIIIlII;
        lIlIIlIIIIIIlII.mc.options.keyUse.setPressed(lIlIIlIIIIIIIll);
    }

    @EventHandler
    private void onTick(TickEvent.Post lIlIIlIIIIIlIll) {
        BowSpam lIlIIlIIIIIlIlI;
        if (InvUtils.findItemWithCount((Item)Items.ARROW).slot == -1) {
            return;
        }
        if (!lIlIIlIIIIIlIlI.onlyWhenHoldingRightClick.get().booleanValue() || lIlIIlIIIIIlIlI.mc.options.keyUse.isPressed()) {
            boolean lIlIIlIIIIIllIl;
            boolean bl = lIlIIlIIIIIllIl = lIlIIlIIIIIlIlI.mc.player.getMainHandStack().getItem() == Items.BOW;
            if (!lIlIIlIIIIIllIl && lIlIIlIIIIIlIlI.wasBow) {
                lIlIIlIIIIIlIlI.setPressed(false);
            }
            lIlIIlIIIIIlIlI.wasBow = lIlIIlIIIIIllIl;
            if (!lIlIIlIIIIIllIl) {
                return;
            }
            if (lIlIIlIIIIIlIlI.mc.player.getItemUseTime() >= lIlIIlIIIIIlIlI.charge.get()) {
                lIlIIlIIIIIlIlI.mc.player.stopUsingItem();
                lIlIIlIIIIIlIlI.mc.interactionManager.stopUsingItem((PlayerEntity)lIlIIlIIIIIlIlI.mc.player);
            } else {
                lIlIIlIIIIIlIlI.setPressed(true);
            }
            lIlIIlIIIIIlIlI.wasHoldingRightClick = lIlIIlIIIIIlIlI.mc.options.keyUse.isPressed();
        } else if (lIlIIlIIIIIlIlI.wasHoldingRightClick) {
            lIlIIlIIIIIlIlI.setPressed(false);
            lIlIIlIIIIIlIlI.wasHoldingRightClick = false;
        }
    }

    @Override
    public void onActivate() {
        lIlIIlIIIIlIlII.wasBow = false;
        lIlIIlIIIIlIlII.wasHoldingRightClick = false;
    }

    public BowSpam() {
        super(Categories.Combat, "bow-spam", "Spams arrows.");
        BowSpam lIlIIlIIIIlIllI;
        lIlIIlIIIIlIllI.sgGeneral = lIlIIlIIIIlIllI.settings.getDefaultGroup();
        lIlIIlIIIIlIllI.charge = lIlIIlIIIIlIllI.sgGeneral.add(new IntSetting.Builder().name("charge").description("How long to charge the bow before releasing in ticks.").defaultValue(5).min(5).max(20).sliderMin(5).sliderMax(20).build());
        lIlIIlIIIIlIllI.onlyWhenHoldingRightClick = lIlIIlIIIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("when-holding-right-click").description("Works only when holding right click.").defaultValue(false).build());
        lIlIIlIIIIlIllI.wasBow = false;
        lIlIIlIIIIlIllI.wasHoldingRightClick = false;
    }

    @Override
    public void onDeactivate() {
        BowSpam lIlIIlIIIIlIIIl;
        lIlIIlIIIIlIIIl.setPressed(false);
    }
}

