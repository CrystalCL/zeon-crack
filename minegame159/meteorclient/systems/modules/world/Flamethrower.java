/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.hit.BlockHitResult
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
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> targetBabies;
    private final /* synthetic */ Setting<Boolean> putOutFire;
    private /* synthetic */ int ticks;
    private final /* synthetic */ Setting<Integer> tickInterval;
    private /* synthetic */ Entity entity;
    private final /* synthetic */ Setting<Boolean> antiBreak;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> distance;

    public Flamethrower() {
        super(Categories.World, "flamethrower", "Ignites every alive piece of food.");
        Flamethrower lllllllllllllllllIIIlIIlIlIlIlIl;
        lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral = lllllllllllllllllIIIlIIlIlIlIlIl.settings.getDefaultGroup();
        lllllllllllllllllIIIlIIlIlIlIlIl.distance = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance the animal has to be to be roasted.").min(0.0).defaultValue(5.0).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.antiBreak = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("anti-break").description("Prevents flint and steel from being broken.").defaultValue(false).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.putOutFire = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("put-out-fire").description("Tries to put out the fire when animal is low health, so the items don't burn.").defaultValue(true).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.targetBabies = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("target-babies").description("If checked babies will also be killed.").defaultValue(false).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.tickInterval = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new IntSetting.Builder().name("tick-interval").defaultValue(5).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.rotate = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the animal roasted.").defaultValue(true).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.entities = lllllllllllllllllIIIlIIlIlIlIlIl.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to cook.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PIG, EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN, EntityType.RABBIT})).build());
        lllllllllllllllllIIIlIIlIlIlIlIl.ticks = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIIIlIIlIlIIlIIl) {
        Flamethrower lllllllllllllllllIIIlIIlIlIIlIII;
        lllllllllllllllllIIIlIIlIlIIlIII.entity = null;
        ++lllllllllllllllllIIIlIIlIlIIlIII.ticks;
        for (Entity lllllllllllllllllIIIlIIlIlIIlIll : lllllllllllllllllIIIlIIlIlIIlIII.mc.world.getEntities()) {
            boolean lllllllllllllllllIIIlIIlIlIIllII;
            if (!lllllllllllllllllIIIlIIlIlIIlIII.entities.get().getBoolean((Object)lllllllllllllllllIIIlIIlIlIIlIll.getType()) || (double)lllllllllllllllllIIIlIIlIlIIlIII.mc.player.distanceTo(lllllllllllllllllIIIlIIlIlIIlIll) > lllllllllllllllllIIIlIIlIlIIlIII.distance.get() || lllllllllllllllllIIIlIIlIlIIlIll.isFireImmune() || lllllllllllllllllIIIlIIlIlIIlIll == lllllllllllllllllIIIlIIlIlIIlIII.mc.player || !lllllllllllllllllIIIlIIlIlIIlIII.targetBabies.get().booleanValue() && lllllllllllllllllIIIlIIlIlIIlIll instanceof LivingEntity && ((LivingEntity)lllllllllllllllllIIIlIIlIlIIlIll).isBaby() || !(lllllllllllllllllIIIlIIlIlIIllII = lllllllllllllllllIIIlIIlIlIIlIII.selectSlot())) continue;
            lllllllllllllllllIIIlIIlIlIIlIII.entity = lllllllllllllllllIIIlIIlIlIIlIll;
            if (lllllllllllllllllIIIlIIlIlIIlIII.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(lllllllllllllllllIIIlIIlIlIIlIll.getBlockPos()), Rotations.getPitch(lllllllllllllllllIIIlIIlIlIIlIll.getBlockPos()), -100, lllllllllllllllllIIIlIIlIlIIlIII::interact);
            } else {
                lllllllllllllllllIIIlIIlIlIIlIII.interact();
            }
            return;
        }
    }

    private boolean selectSlot() {
        int lllllllllllllllllIIIlIIlIIllIlII;
        boolean lllllllllllllllllIIIlIIlIIllIIIl;
        Flamethrower lllllllllllllllllIIIlIIlIIllIIII;
        lllllllllllllllllIIIlIIlIIllIIII.preSlot = lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.selectedSlot;
        boolean lllllllllllllllllIIIlIIlIIllIIlI = false;
        if (lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.getMainHandStack().getItem() == Items.FLINT_AND_STEEL) {
            if (lllllllllllllllllIIIlIIlIIllIIII.antiBreak.get().booleanValue() && lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.getMainHandStack().getDamage() >= lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.getMainHandStack().getMaxDamage() - 1) {
                lllllllllllllllllIIIlIIlIIllIIlI = true;
            }
        } else if (((ItemStack)lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.offHand.get(0)).getItem() == Items.FLINT_AND_STEEL) {
            if (lllllllllllllllllIIIlIIlIIllIIII.antiBreak.get().booleanValue() && ((ItemStack)lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.offHand.get(0)).getDamage() >= ((ItemStack)lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.offHand.get(0)).getMaxDamage() - 1) {
                lllllllllllllllllIIIlIIlIIllIIlI = true;
            }
        } else {
            lllllllllllllllllIIIlIIlIIllIIlI = true;
        }
        boolean bl = lllllllllllllllllIIIlIIlIIllIIIl = !lllllllllllllllllIIIlIIlIIllIIlI;
        if (lllllllllllllllllIIIlIIlIIllIIlI && (lllllllllllllllllIIIlIIlIIllIlII = InvUtils.findItemInHotbar(Items.FLINT_AND_STEEL, lllllllllllllllllIIIlIIlIIlIIlll -> {
            Flamethrower lllllllllllllllllIIIlIIlIIlIlIII;
            return lllllllllllllllllIIIlIIlIIlIlIII.antiBreak.get() == false || lllllllllllllllllIIIlIIlIIlIlIII.antiBreak.get() != false && lllllllllllllllllIIIlIIlIIlIIlll.getDamage() < lllllllllllllllllIIIlIIlIIlIIlll.getMaxDamage() - 1;
        })) != -1) {
            lllllllllllllllllIIIlIIlIIllIIII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIlIIlIIllIlII;
            lllllllllllllllllIIIlIIlIIllIIIl = true;
        }
        return lllllllllllllllllIIIlIIlIIllIIIl;
    }

    @Override
    public void onDeactivate() {
        lllllllllllllllllIIIlIIlIlIlIIlI.entity = null;
    }

    private void interact() {
        Flamethrower lllllllllllllllllIIIlIIlIIllllII;
        Block lllllllllllllllllIIIlIIlIIllllll = lllllllllllllllllIIIlIIlIIllllII.mc.world.getBlockState(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos()).getBlock();
        Block lllllllllllllllllIIIlIIlIIlllllI = lllllllllllllllllIIIlIIlIIllllII.mc.world.getBlockState(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().down()).getBlock();
        if (lllllllllllllllllIIIlIIlIIllllll.is(Blocks.WATER) || lllllllllllllllllIIIlIIlIIlllllI.is(Blocks.WATER) || lllllllllllllllllIIIlIIlIIlllllI.is(Blocks.GRASS_PATH)) {
            return;
        }
        if (lllllllllllllllllIIIlIIlIIllllll.is(Blocks.GRASS)) {
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos(), Direction.DOWN);
        }
        LivingEntity lllllllllllllllllIIIlIIlIIllllIl = (LivingEntity)lllllllllllllllllIIIlIIlIIllllII.entity;
        if (lllllllllllllllllIIIlIIlIIllllII.putOutFire.get().booleanValue() && lllllllllllllllllIIIlIIlIIllllIl.getHealth() < 1.0f) {
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos(), Direction.DOWN);
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().west(), Direction.DOWN);
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().east(), Direction.DOWN);
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().north(), Direction.DOWN);
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.attackBlock(lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().south(), Direction.DOWN);
        } else if (lllllllllllllllllIIIlIIlIIllllII.ticks >= lllllllllllllllllIIIlIIlIIllllII.tickInterval.get() && !lllllllllllllllllIIIlIIlIIllllII.entity.isOnFire()) {
            lllllllllllllllllIIIlIIlIIllllII.mc.interactionManager.interactBlock(lllllllllllllllllIIIlIIlIIllllII.mc.player, lllllllllllllllllIIIlIIlIIllllII.mc.world, Hand.MAIN_HAND, new BlockHitResult(lllllllllllllllllIIIlIIlIIllllII.entity.getPos().subtract(new Vec3d(0.0, 1.0, 0.0)), Direction.UP, lllllllllllllllllIIIlIIlIIllllII.entity.getBlockPos().down(), false));
            lllllllllllllllllIIIlIIlIIllllII.ticks = 0;
        }
        lllllllllllllllllIIIlIIlIIllllII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIlIIlIIllllII.preSlot;
    }
}

