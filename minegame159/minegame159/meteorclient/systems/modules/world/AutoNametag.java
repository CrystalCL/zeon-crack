/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;

public class AutoNametag
extends Module {
    private final SettingGroup sgGeneral;
    private boolean offHand;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private int preSlot;
    private Entity entity;
    private final Setting<Boolean> rotate;
    private final Setting<Double> distance;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.entity = null;
        for (Entity Entity2 : this.mc.world.getEntities()) {
            boolean bl;
            if (!this.entities.get().getBoolean((Object)Entity2.getType()) || Entity2.hasCustomName() || (double)this.mc.player.distanceTo(Entity2) > this.distance.get()) continue;
            boolean bl2 = true;
            if (this.mc.player.inventory.getMainHandStack().getItem() instanceof NameTagItem) {
                bl2 = false;
            } else if (((ItemStack)this.mc.player.inventory.offHand.get(0)).getItem() instanceof NameTagItem) {
                bl2 = false;
                this.offHand = true;
            }
            boolean bl3 = bl = !bl2;
            if (bl2) {
                for (int i = 0; i < 9; ++i) {
                    ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
                    if (!(ItemStack2.getItem() instanceof NameTagItem)) continue;
                    this.preSlot = this.mc.player.inventory.selectedSlot;
                    this.mc.player.inventory.selectedSlot = i;
                    bl = true;
                    break;
                }
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

    @Override
    public void onDeactivate() {
        this.entity = null;
    }

    private void interact() {
        this.mc.interactionManager.interactEntity((PlayerEntity)this.mc.player, this.entity, this.offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        this.mc.player.inventory.selectedSlot = this.preSlot;
    }

    public AutoNametag() {
        super(Categories.World, "auto-nametag", "Automatically uses nametags on entities without a nametag. WILL nametag ALL entities in the specified distance.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Which entities to nametag.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).build());
        this.distance = this.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance a nametagged entity can be to be nametagged.").min(0.0).defaultValue(5.0).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the mob being nametagged.").defaultValue(true).build());
    }
}

