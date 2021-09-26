/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;

public class Flamethrower
extends Module {
    private final Setting<Boolean> putOutFire;
    private Entity entity;
    private int preSlot;
    private final Setting<Boolean> rotate;
    private final Setting<Double> distance;
    private final Setting<Boolean> antiBreak;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final Setting<Boolean> targetBabies;
    private final Setting<Integer> tickInterval;
    private final SettingGroup sgGeneral;
    private int ticks;

    @Override
    public void onDeactivate() {
        this.entity = null;
    }

    private void interact() {
        Block Block2 = this.mc.world.getBlockState(this.entity.getBlockPos()).getBlock();
        Block Block3 = this.mc.world.getBlockState(this.entity.getBlockPos().down()).getBlock();
        if (Block2.is(Blocks.WATER) || Block3.is(Blocks.WATER) || Block3.is(Blocks.GRASS_PATH)) {
            return;
        }
        if (Block2.is(Blocks.GRASS)) {
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos(), Direction.DOWN);
        }
        LivingEntity LivingEntity2 = (LivingEntity)this.entity;
        if (this.putOutFire.get().booleanValue() && LivingEntity2.getHealth() < 1.0f) {
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos(), Direction.DOWN);
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos().west(), Direction.DOWN);
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos().east(), Direction.DOWN);
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos().north(), Direction.DOWN);
            this.mc.interactionManager.attackBlock(this.entity.getBlockPos().south(), Direction.DOWN);
        } else if (this.ticks >= this.tickInterval.get() && !this.entity.isOnFire()) {
            this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.entity.getPos().subtract(new Vec3d(0.0, 1.0, 0.0)), Direction.UP, this.entity.getBlockPos().down(), false));
            this.ticks = 0;
        }
        this.mc.player.inventory.selectedSlot = this.preSlot;
    }

    public Flamethrower() {
        super(Categories.World, "flamethrower", "Ignites every alive piece of food.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.distance = this.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance the animal has to be to be roasted.").min(0.0).defaultValue(5.0).build());
        this.antiBreak = this.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents flint and steel from being broken.").defaultValue(false).build());
        this.putOutFire = this.sgGeneral.add(new BoolSetting.Builder().name("put-out-fire").description("Tries to put out the fire when animal is low health, so the items don't burn.").defaultValue(true).build());
        this.targetBabies = this.sgGeneral.add(new BoolSetting.Builder().name("target-babies").description("If checked babies will also be killed.").defaultValue(false).build());
        this.tickInterval = this.sgGeneral.add(new IntSetting.Builder().name("tick-interval").defaultValue(5).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the animal roasted.").defaultValue(true).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to cook.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PIG, EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN, EntityType.RABBIT)).build());
        this.ticks = 0;
    }

    private boolean lambda$selectSlot$0(ItemStack ItemStack2) {
        return this.antiBreak.get() == false || this.antiBreak.get() != false && ItemStack2.getDamage() < ItemStack2.getMaxDamage() - 1;
    }

    private boolean selectSlot() {
        int n;
        boolean bl;
        this.preSlot = this.mc.player.inventory.selectedSlot;
        boolean bl2 = false;
        if (this.mc.player.inventory.getMainHandStack().getItem() == Items.FLINT_AND_STEEL) {
            if (this.antiBreak.get().booleanValue() && this.mc.player.inventory.getMainHandStack().getDamage() >= this.mc.player.inventory.getMainHandStack().getMaxDamage() - 1) {
                bl2 = true;
            }
        } else if (((ItemStack)this.mc.player.inventory.offHand.get(0)).getItem() == Items.FLINT_AND_STEEL) {
            if (this.antiBreak.get().booleanValue() && ((ItemStack)this.mc.player.inventory.offHand.get(0)).getDamage() >= ((ItemStack)this.mc.player.inventory.offHand.get(0)).getMaxDamage() - 1) {
                bl2 = true;
            }
        } else {
            bl2 = true;
        }
        boolean bl3 = bl = !bl2;
        if (bl2 && (n = InvUtils.findItemInHotbar(Items.FLINT_AND_STEEL, this::lambda$selectSlot$0)) != -1) {
            this.mc.player.inventory.selectedSlot = n;
            bl = true;
        }
        return bl;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.entity = null;
        ++this.ticks;
        for (Entity Entity2 : this.mc.world.getEntities()) {
            boolean bl;
            if (!this.entities.get().getBoolean((Object)Entity2.getType()) || (double)this.mc.player.distanceTo(Entity2) > this.distance.get() || Entity2.isFireImmune() || Entity2 == this.mc.player || !this.targetBabies.get().booleanValue() && Entity2 instanceof LivingEntity && ((LivingEntity)Entity2).isBaby() || !(bl = this.selectSlot())) continue;
            this.entity = Entity2;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(Entity2.getBlockPos()), Rotations.getPitch(Entity2.getBlockPos()), -100, this::interact);
            } else {
                this.interact();
            }
            return;
        }
    }
}

