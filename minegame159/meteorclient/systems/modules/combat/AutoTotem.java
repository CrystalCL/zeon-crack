/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.item.SwordItem
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.entity.BedBlockEntity
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.OffhandExtra;
import minegame159.meteorclient.systems.modules.movement.NoFall;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class AutoTotem
extends Module {
    private final /* synthetic */ Setting<Boolean> inventorySwitch;
    private final /* synthetic */ Setting<Boolean> elytraHold;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ String totemCountString;
    private final /* synthetic */ Setting<Boolean> fallback;
    private /* synthetic */ boolean locked;
    private final /* synthetic */ Setting<Boolean> smart;
    private final /* synthetic */ MinecraftClient mc;
    private final /* synthetic */ Setting<Integer> health;

    @Override
    public void onDeactivate() {
        lllllIllIIIlIl.locked = false;
    }

    @Override
    public String getInfoString() {
        AutoTotem lllllIlIlllIlI;
        return lllllIlIlllIlI.totemCountString;
    }

    public boolean getLocked() {
        AutoTotem lllllIlIlIIlll;
        return lllllIlIlIIlll.locked;
    }

    private double getHealth() {
        AutoTotem lllllIlIlIlIll;
        assert (lllllIlIlIlIll.mc.player != null);
        return lllllIlIlIlIll.mc.player.getHealth() + lllllIlIlIlIll.mc.player.getAbsorptionAmount();
    }

    private boolean elytraMove() {
        AutoTotem lllllIlIlIIIIl;
        return lllllIlIlIIIIl.elytraHold.get() != false && lllllIlIlIIIIl.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && lllllIlIlIIIIl.mc.player.isFallFlying();
    }

    public AutoTotem() {
        super(Categories.Combat, "auto-totem", "Automatically equips totems in your offhand.");
        AutoTotem lllllIllIIlIII;
        lllllIllIIlIII.sgGeneral = lllllIllIIlIII.settings.getDefaultGroup();
        lllllIllIIlIII.smart = lllllIllIIlIII.sgGeneral.add(new BoolSetting.Builder().name("smart").description("Only switches to a totem when you are close to death.").defaultValue(false).build());
        lllllIllIIlIII.fallback = lllllIllIIlIII.sgGeneral.add(new BoolSetting.Builder().name("fallback").description("Enables Offhand Extra when you run out of totems.").defaultValue(true).build());
        lllllIllIIlIII.inventorySwitch = lllllIllIIlIII.sgGeneral.add(new BoolSetting.Builder().name("inventory").description("Whether or not to equip totems while in your inventory.").defaultValue(true).build());
        lllllIllIIlIII.health = lllllIllIIlIII.sgGeneral.add(new IntSetting.Builder().name("health").description("The health Auto Totem's smart mode activates at.").defaultValue(10).min(0).sliderMax(20).build());
        lllllIllIIlIII.elytraHold = lllllIllIIlIII.sgGeneral.add(new BoolSetting.Builder().name("elytra-hold").description("Whether or not to always hold a totem when flying with an elytra.").defaultValue(false).build());
        lllllIllIIlIII.totemCountString = "0";
        lllllIllIIlIII.mc = MinecraftClient.getInstance();
        lllllIllIIlIII.locked = false;
    }

    private double getHealthReduction() {
        double lllllIlIllIlII;
        AutoTotem lllllIlIllIIII;
        assert (lllllIlIllIIII.mc.world != null);
        assert (lllllIlIllIIII.mc.player != null);
        double lllllIlIllIIIl = 0.0;
        if (lllllIlIllIIII.mc.player.abilities.creativeMode) {
            return lllllIlIllIIIl;
        }
        for (Entity lllllIlIllIlIl : lllllIlIllIIII.mc.world.getEntities()) {
            if (lllllIlIllIlIl instanceof EndCrystalEntity && lllllIlIllIIIl < DamageCalcUtils.crystalDamage((LivingEntity)lllllIlIllIIII.mc.player, lllllIlIllIlIl.getPos())) {
                lllllIlIllIIIl = DamageCalcUtils.crystalDamage((LivingEntity)lllllIlIllIIII.mc.player, lllllIlIllIlIl.getPos());
                continue;
            }
            if (!(lllllIlIllIlIl instanceof PlayerEntity) || !(lllllIlIllIIIl < DamageCalcUtils.getSwordDamage((PlayerEntity)lllllIlIllIlIl, true)) || !Friends.get().notTrusted((PlayerEntity)lllllIlIllIlIl) || !(lllllIlIllIIII.mc.player.getPos().distanceTo(lllllIlIllIlIl.getPos()) < 5.0) || !(((PlayerEntity)lllllIlIllIlIl).getActiveItem().getItem() instanceof SwordItem)) continue;
            lllllIlIllIIIl = DamageCalcUtils.getSwordDamage((PlayerEntity)lllllIlIllIlIl, true);
        }
        if (!Modules.get().isActive(NoFall.class) && lllllIlIllIIII.mc.player.fallDistance > 3.0f && (lllllIlIllIlII = (double)lllllIlIllIIII.mc.player.fallDistance * 0.5) > lllllIlIllIIIl) {
            lllllIlIllIIIl = lllllIlIllIlII;
        }
        if (Utils.getDimension() != Dimension.Nether) {
            for (BlockEntity lllllIlIllIIll : lllllIlIllIIII.mc.world.blockEntities) {
                if (!(lllllIlIllIIll instanceof BedBlockEntity)) continue;
                Vec3d Vec3d2 = new Vec3d((double)lllllIlIllIIll.getPos().getX(), (double)lllllIlIllIIll.getPos().getY(), (double)lllllIlIllIIll.getPos().getZ());
                if (!(lllllIlIllIIIl < DamageCalcUtils.bedDamage((LivingEntity)lllllIlIllIIII.mc.player, Vec3d2))) continue;
                lllllIlIllIIIl = DamageCalcUtils.bedDamage((LivingEntity)lllllIlIllIIII.mc.player, new Vec3d((double)lllllIlIllIIll.getPos().getX(), (double)lllllIlIllIIll.getPos().getY(), (double)lllllIlIllIIll.getPos().getZ()));
            }
        }
        return lllllIlIllIIIl;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllIllIIIIII) {
        AutoTotem lllllIlIlllllI;
        if (lllllIlIlllllI.mc.currentScreen instanceof InventoryScreen && !lllllIlIlllllI.inventorySwitch.get().booleanValue()) {
            return;
        }
        if (lllllIlIlllllI.mc.currentScreen != null && !(lllllIlIlllllI.mc.currentScreen instanceof InventoryScreen)) {
            return;
        }
        InvUtils.FindItemResult lllllIlIllllll = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
        if (lllllIlIllllll.count <= 0) {
            if (!Modules.get().isActive(OffhandExtra.class) && lllllIlIlllllI.fallback.get().booleanValue()) {
                Modules.get().get(OffhandExtra.class).toggle();
            }
            Modules.get().get(OffhandExtra.class).setTotems(true);
            lllllIlIlllllI.locked = false;
        } else {
            Modules.get().get(OffhandExtra.class).setTotems(false);
            if (lllllIlIlllllI.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING && (!lllllIlIlllllI.smart.get().booleanValue() || lllllIlIlllllI.isLow() || lllllIlIlllllI.elytraMove())) {
                lllllIlIlllllI.locked = true;
                InvUtils.move().from(lllllIlIllllll.slot).toOffhand();
            } else if (lllllIlIlllllI.smart.get().booleanValue() && !lllllIlIlllllI.isLow() && !lllllIlIlllllI.elytraMove()) {
                lllllIlIlllllI.locked = false;
            }
        }
        lllllIlIlllllI.totemCountString = Integer.toString(lllllIlIllllll.count);
    }

    private boolean isLow() {
        AutoTotem lllllIlIlIIlII;
        return lllllIlIlIIlII.getHealth() < (double)lllllIlIlIIlII.health.get().intValue() || lllllIlIlIIlII.getHealth() - lllllIlIlIIlII.getHealthReduction() < (double)lllllIlIlIIlII.health.get().intValue();
    }
}

