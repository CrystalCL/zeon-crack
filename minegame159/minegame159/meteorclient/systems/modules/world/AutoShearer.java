/*
 * Decompiled with CFR 0.151.
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
    private boolean offHand;
    private final Setting<Double> distance;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;
    private int preSlot;
    private Entity entity;
    private final Setting<Boolean> antiBreak;

    private void interact() {
        this.mc.interactionManager.interactEntity((PlayerEntity)this.mc.player, this.entity, this.offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        this.mc.player.inventory.selectedSlot = this.preSlot;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.entity = null;
        for (Entity Entity2 : this.mc.world.getEntities()) {
            int n;
            boolean bl;
            if (!(Entity2 instanceof SheepEntity) || ((SheepEntity)Entity2).isSheared() || ((SheepEntity)Entity2).isBaby() || (double)this.mc.player.distanceTo(Entity2) > this.distance.get()) continue;
            boolean bl2 = false;
            if (this.mc.player.inventory.getMainHandStack().getItem() instanceof ShearsItem) {
                if (this.antiBreak.get().booleanValue() && this.mc.player.inventory.getMainHandStack().getDamage() >= this.mc.player.inventory.getMainHandStack().getMaxDamage() - 1) {
                    bl2 = true;
                }
            } else if (((ItemStack)this.mc.player.inventory.offHand.get(0)).getItem() instanceof ShearsItem) {
                if (this.antiBreak.get().booleanValue() && ((ItemStack)this.mc.player.inventory.offHand.get(0)).getDamage() >= ((ItemStack)this.mc.player.inventory.offHand.get(0)).getMaxDamage() - 1) {
                    bl2 = true;
                } else {
                    this.offHand = true;
                }
            } else {
                bl2 = true;
            }
            boolean bl3 = bl = !bl2;
            if (bl2 && (n = InvUtils.findItemInHotbar(Items.SHEARS, this::lambda$onTick$0)) != -1) {
                this.mc.player.inventory.selectedSlot = n;
                bl = true;
            }
            if (!bl) continue;
            this.entity = Entity2;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(Entity2), Rotations.getPitch(Entity2), -100, this::interact);
            } else {
                this.interact();
            }
            return;
        }
    }

    private boolean lambda$onTick$0(ItemStack ItemStack2) {
        return this.antiBreak.get() == false || this.antiBreak.get() != false && ItemStack2.getDamage() < ItemStack2.getMaxDamage() - 1;
    }

    public AutoShearer() {
        super(Categories.World, "auto-shearer", "Automatically shears sheep.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.distance = this.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance the sheep have to be to be sheared.").min(0.0).defaultValue(5.0).build());
        this.antiBreak = this.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents shears from being broken.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the animal being sheared.").defaultValue(true).build());
    }

    @Override
    public void onDeactivate() {
        this.entity = null;
    }
}

