/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.SheepEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.ShearsItem
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;

public class AutoShearer
extends Module {
    private final /* synthetic */ Setting<Double> distance;
    private final /* synthetic */ Setting<Boolean> antiBreak;
    private final /* synthetic */ Setting<Boolean> rotate;
    private /* synthetic */ Entity entity;
    private /* synthetic */ boolean offHand;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Pre llIlIIllIIIllll) {
        AutoShearer llIlIIllIIIlllI;
        llIlIIllIIIlllI.entity = null;
        for (Entity llIlIIllIIlIIIl : llIlIIllIIIlllI.mc.world.getEntities()) {
            int llIlIIllIIlIlII;
            boolean llIlIIllIIlIIlI;
            if (!(llIlIIllIIlIIIl instanceof SheepEntity) || ((SheepEntity)llIlIIllIIlIIIl).isSheared() || ((SheepEntity)llIlIIllIIlIIIl).isBaby() || (double)llIlIIllIIIlllI.mc.player.distanceTo(llIlIIllIIlIIIl) > llIlIIllIIIlllI.distance.get()) continue;
            boolean llIlIIllIIlIIll = false;
            if (llIlIIllIIIlllI.mc.player.inventory.getMainHandStack().getItem() instanceof ShearsItem) {
                if (llIlIIllIIIlllI.antiBreak.get().booleanValue() && llIlIIllIIIlllI.mc.player.inventory.getMainHandStack().getDamage() >= llIlIIllIIIlllI.mc.player.inventory.getMainHandStack().getMaxDamage() - 1) {
                    llIlIIllIIlIIll = true;
                }
            } else if (((ItemStack)llIlIIllIIIlllI.mc.player.inventory.offHand.get(0)).getItem() instanceof ShearsItem) {
                if (llIlIIllIIIlllI.antiBreak.get().booleanValue() && ((ItemStack)llIlIIllIIIlllI.mc.player.inventory.offHand.get(0)).getDamage() >= ((ItemStack)llIlIIllIIIlllI.mc.player.inventory.offHand.get(0)).getMaxDamage() - 1) {
                    llIlIIllIIlIIll = true;
                } else {
                    llIlIIllIIIlllI.offHand = true;
                }
            } else {
                llIlIIllIIlIIll = true;
            }
            boolean bl = llIlIIllIIlIIlI = !llIlIIllIIlIIll;
            if (llIlIIllIIlIIll && (llIlIIllIIlIlII = InvUtils.findItemInHotbar(Items.SHEARS, llIlIIllIIIIIII -> {
                AutoShearer llIlIIllIIIIIIl;
                return llIlIIllIIIIIIl.antiBreak.get() == false || llIlIIllIIIIIIl.antiBreak.get() != false && llIlIIllIIIIIII.getDamage() < llIlIIllIIIIIII.getMaxDamage() - 1;
            })) != -1) {
                llIlIIllIIIlllI.mc.player.inventory.selectedSlot = llIlIIllIIlIlII;
                llIlIIllIIlIIlI = true;
            }
            if (!llIlIIllIIlIIlI) continue;
            llIlIIllIIIlllI.entity = llIlIIllIIlIIIl;
            if (llIlIIllIIIlllI.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(llIlIIllIIlIIIl), Rotations.getPitch(llIlIIllIIlIIIl), -100, llIlIIllIIIlllI::interact);
            } else {
                llIlIIllIIIlllI.interact();
            }
            return;
        }
    }

    private void interact() {
        AutoShearer llIlIIllIIIIlll;
        llIlIIllIIIIlll.mc.interactionManager.interactEntity((PlayerEntity)llIlIIllIIIIlll.mc.player, llIlIIllIIIIlll.entity, llIlIIllIIIIlll.offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        llIlIIllIIIIlll.mc.player.inventory.selectedSlot = llIlIIllIIIIlll.preSlot;
    }

    @Override
    public void onDeactivate() {
        llIlIIllIIllIll.entity = null;
    }

    public AutoShearer() {
        super(Categories.World, "auto-shearer", "Automatically shears sheep.");
        AutoShearer llIlIIllIIlllll;
        llIlIIllIIlllll.sgGeneral = llIlIIllIIlllll.settings.getDefaultGroup();
        llIlIIllIIlllll.distance = llIlIIllIIlllll.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance the sheep have to be to be sheared.").min(0.0).defaultValue(5.0).build());
        llIlIIllIIlllll.antiBreak = llIlIIllIIlllll.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents shears from being broken.").defaultValue(false).build());
        llIlIIllIIlllll.rotate = llIlIIllIIlllll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the animal being sheared.").defaultValue(true).build());
    }
}

