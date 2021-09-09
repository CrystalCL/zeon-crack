/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
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
    private final /* synthetic */ Setting<Double> minDamage;
    private /* synthetic */ int rPosZ;
    private /* synthetic */ int rPosX;
    private /* synthetic */ int slot;
    private /* synthetic */ Entity crystal;
    private /* synthetic */ int oldSlot;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> onlyObsidian;

    @EventHandler
    private void onTick(TickEvent.Pre lIlIlIllIlIlll) {
        SmartSurround lIlIlIllIlIllI;
        if (lIlIlIllIlIllI.slot != -1) {
            if (lIlIlIllIlIllI.rPosX >= 2 && lIlIlIllIlIllI.rPosZ == 0) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX - 1, 0, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX > 1 && lIlIlIllIlIllI.rPosZ > 1) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX, lIlIlIllIlIllI.rPosZ - 1, lIlIlIllIlIllI.crystal);
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX - 1, lIlIlIllIlIllI.rPosZ, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX == 0 && lIlIlIllIlIllI.rPosZ >= 2) {
                lIlIlIllIlIllI.placeObi(0, lIlIlIllIlIllI.rPosZ - 1, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX < -1 && lIlIlIllIlIllI.rPosZ < -1) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX, lIlIlIllIlIllI.rPosZ + 1, lIlIlIllIlIllI.crystal);
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX + 1, lIlIlIllIlIllI.rPosZ, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX == 0 && lIlIlIllIlIllI.rPosZ <= -2) {
                lIlIlIllIlIllI.placeObi(0, lIlIlIllIlIllI.rPosZ + 1, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX > 1 && lIlIlIllIlIllI.rPosZ < -1) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX, lIlIlIllIlIllI.rPosZ + 1, lIlIlIllIlIllI.crystal);
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX - 1, lIlIlIllIlIllI.rPosZ, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX <= -2 && lIlIlIllIlIllI.rPosZ == 0) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX + 1, 0, lIlIlIllIlIllI.crystal);
            } else if (lIlIlIllIlIllI.rPosX < -1 && lIlIlIllIlIllI.rPosZ > 1) {
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX, lIlIlIllIlIllI.rPosZ - 1, lIlIlIllIlIllI.crystal);
                lIlIlIllIlIllI.placeObi(lIlIlIllIlIllI.rPosX + 1, lIlIlIllIlIllI.rPosZ, lIlIlIllIlIllI.crystal);
            }
            if (lIlIlIllIlIllI.mc.world.raycast(new RaycastContext(lIlIlIllIlIllI.mc.player.getPos(), lIlIlIllIlIllI.crystal.getPos(), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lIlIlIllIlIllI.mc.player)).getType() != Type.MISS) {
                lIlIlIllIlIllI.slot = -1;
                lIlIlIllIlIllI.mc.player.inventory.selectedSlot = lIlIlIllIlIllI.oldSlot;
            }
        }
    }

    private int findObiInHotbar() {
        SmartSurround lIlIlIllIIIIII;
        lIlIlIllIIIIII.oldSlot = lIlIlIllIIIIII.mc.player.inventory.selectedSlot;
        int lIlIlIlIllllll = -1;
        for (int lIlIlIllIIIIIl = 0; lIlIlIllIIIIIl < 9; ++lIlIlIllIIIIIl) {
            Item lIlIlIllIIIIlI = lIlIlIllIIIIII.mc.player.inventory.getStack(lIlIlIllIIIIIl).getItem();
            if (lIlIlIllIIIIlI != Items.OBSIDIAN && lIlIlIllIIIIlI != Items.CRYING_OBSIDIAN) continue;
            lIlIlIllIIIIII.mc.player.inventory.selectedSlot = lIlIlIlIllllll = lIlIlIllIIIIIl;
            break;
        }
        return lIlIlIlIllllll;
    }

    @EventHandler
    private void onSpawn(EntityAddedEvent lIlIlIllIlllII) {
        SmartSurround lIlIlIllIlllll;
        lIlIlIllIlllll.crystal = lIlIlIllIlllII.entity;
        if (lIlIlIllIlllII.entity.getType() == EntityType.END_CRYSTAL && DamageCalcUtils.crystalDamage((LivingEntity)lIlIlIllIlllll.mc.player, lIlIlIllIlllII.entity.getPos()) > lIlIlIllIlllll.minDamage.get()) {
            lIlIlIllIlllll.slot = lIlIlIllIlllll.findObiInHotbar();
            if (lIlIlIllIlllll.slot == -1 && lIlIlIllIlllll.onlyObsidian.get().booleanValue()) {
                ChatUtils.moduleError(lIlIlIllIlllll, "No obsidian in hotbar... disabling.", new Object[0]);
                return;
            }
            for (int lIlIlIlllIIIII = 0; lIlIlIlllIIIII < 9; ++lIlIlIlllIIIII) {
                Item lIlIlIlllIIIIl = lIlIlIllIlllll.mc.player.inventory.getStack(lIlIlIlllIIIII).getItem();
                if (!(lIlIlIlllIIIIl instanceof BlockItem)) continue;
                lIlIlIllIlllll.mc.player.inventory.selectedSlot = lIlIlIllIlllll.slot = lIlIlIlllIIIII;
                break;
            }
            if (lIlIlIllIlllll.slot == -1) {
                ChatUtils.moduleError(lIlIlIllIlllll, "No blocks in hotbar... disabling.", new Object[0]);
                return;
            }
            lIlIlIllIlllll.rPosX = lIlIlIllIlllll.mc.player.getBlockPos().getX() - lIlIlIllIlllII.entity.getBlockPos().getX();
            lIlIlIllIlllll.rPosZ = lIlIlIllIlllll.mc.player.getBlockPos().getZ() - lIlIlIllIlllII.entity.getBlockPos().getZ();
        }
    }

    private void placeObi(int lIlIlIllIIllll, int lIlIlIllIIlllI, Entity lIlIlIllIIlIII) {
        SmartSurround lIlIlIllIlIIII;
        BlockPos lIlIlIllIIllII = lIlIlIllIIlIII.getBlockPos().add(lIlIlIllIIllll, -1, lIlIlIllIIlllI);
        BlockUtils.place(lIlIlIllIIllII, Hand.MAIN_HAND, lIlIlIllIlIIII.slot, lIlIlIllIlIIII.rotate.get(), 100, true);
    }

    public SmartSurround() {
        super(Categories.Combat, "smart-surround", "Attempts to save you from crystals automatically.");
        SmartSurround lIlIlIlllIIlll;
        lIlIlIlllIIlll.sgGeneral = lIlIlIlllIIlll.settings.getDefaultGroup();
        lIlIlIlllIIlll.onlyObsidian = lIlIlIlllIIlll.sgGeneral.add(new BoolSetting.Builder().name("only-obsidian").description("Only uses Obsidian as a surround block.").defaultValue(false).build());
        lIlIlIlllIIlll.minDamage = lIlIlIlllIIlll.sgGeneral.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage before this activates.").defaultValue(5.5).build());
        lIlIlIlllIIlll.rotate = lIlIlIlllIIlll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate towards the blocks being placed.").defaultValue(true).build());
        lIlIlIlllIIlll.slot = -1;
    }
}

