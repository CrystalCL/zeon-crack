/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

public class SmartSurround
extends Module {
    private int slot;
    private int rPosX;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> onlyObsidian;
    private Entity crystal;
    private final Setting<Double> minDamage;
    private final SettingGroup sgGeneral;
    private int rPosZ;
    private int oldSlot;

    @EventHandler
    private void onSpawn(EntityAddedEvent entityAddedEvent) {
        this.crystal = entityAddedEvent.entity;
        if (entityAddedEvent.entity.getType() == EntityType.END_CRYSTAL && DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, entityAddedEvent.entity.getPos()) > this.minDamage.get()) {
            this.slot = this.findObiInHotbar();
            if (this.slot == -1 && this.onlyObsidian.get().booleanValue()) {
                ChatUtils.moduleError(this, "No obsidian in hotbar... disabling.", new Object[0]);
                return;
            }
            for (int i = 0; i < 9; ++i) {
                Item Item2 = this.mc.player.inventory.getStack(i).getItem();
                if (!(Item2 instanceof BlockItem)) continue;
                this.mc.player.inventory.selectedSlot = this.slot = i;
                break;
            }
            if (this.slot == -1) {
                ChatUtils.moduleError(this, "No blocks in hotbar... disabling.", new Object[0]);
                return;
            }
            this.rPosX = this.mc.player.getBlockPos().getX() - entityAddedEvent.entity.getBlockPos().getX();
            this.rPosZ = this.mc.player.getBlockPos().getZ() - entityAddedEvent.entity.getBlockPos().getZ();
        }
    }

    private int findObiInHotbar() {
        this.oldSlot = this.mc.player.inventory.selectedSlot;
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (Item2 != Items.OBSIDIAN && Item2 != Items.CRYING_OBSIDIAN) continue;
            this.mc.player.inventory.selectedSlot = n = i;
            break;
        }
        return n;
    }

    private void placeObi(int n, int n2, Entity Entity2) {
        BlockPos BlockPos2 = Entity2.getBlockPos().add(n, -1, n2);
        BlockUtils.place(BlockPos2, Hand.MAIN_HAND, this.slot, this.rotate.get(), 100, true);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.slot != -1) {
            if (this.rPosX >= 2 && this.rPosZ == 0) {
                this.placeObi(this.rPosX - 1, 0, this.crystal);
            } else if (this.rPosX > 1 && this.rPosZ > 1) {
                this.placeObi(this.rPosX, this.rPosZ - 1, this.crystal);
                this.placeObi(this.rPosX - 1, this.rPosZ, this.crystal);
            } else if (this.rPosX == 0 && this.rPosZ >= 2) {
                this.placeObi(0, this.rPosZ - 1, this.crystal);
            } else if (this.rPosX < -1 && this.rPosZ < -1) {
                this.placeObi(this.rPosX, this.rPosZ + 1, this.crystal);
                this.placeObi(this.rPosX + 1, this.rPosZ, this.crystal);
            } else if (this.rPosX == 0 && this.rPosZ <= -2) {
                this.placeObi(0, this.rPosZ + 1, this.crystal);
            } else if (this.rPosX > 1 && this.rPosZ < -1) {
                this.placeObi(this.rPosX, this.rPosZ + 1, this.crystal);
                this.placeObi(this.rPosX - 1, this.rPosZ, this.crystal);
            } else if (this.rPosX <= -2 && this.rPosZ == 0) {
                this.placeObi(this.rPosX + 1, 0, this.crystal);
            } else if (this.rPosX < -1 && this.rPosZ > 1) {
                this.placeObi(this.rPosX, this.rPosZ - 1, this.crystal);
                this.placeObi(this.rPosX + 1, this.rPosZ, this.crystal);
            }
            if (this.mc.world.raycast(new RaycastContext(this.mc.player.getPos(), this.crystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.mc.player)).getType() != HitResult.class_240.MISS) {
                this.slot = -1;
                this.mc.player.inventory.selectedSlot = this.oldSlot;
            }
        }
    }

    public SmartSurround() {
        super(Categories.Combat, "smart-surround", "Attempts to save you from crystals automatically.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.onlyObsidian = this.sgGeneral.add(new BoolSetting.Builder().name("only-obsidian").description("Only uses Obsidian as a surround block.").defaultValue(false).build());
        this.minDamage = this.sgGeneral.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage before this activates.").defaultValue(5.5).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate towards the blocks being placed.").defaultValue(true).build());
        this.slot = -1;
    }
}

