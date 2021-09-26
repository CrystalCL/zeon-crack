/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class AutoTotem
extends Module {
    private final MinecraftClient mc;
    private String totemCountString;
    private final Setting<Boolean> elytraHold;
    private final SettingGroup sgGeneral;
    private boolean locked;
    private final Setting<Boolean> inventorySwitch;
    static final boolean $assertionsDisabled = !AutoTotem.class.desiredAssertionStatus();
    private final Setting<Integer> health;
    private final Setting<Boolean> smart;
    private final Setting<Boolean> fallback;

    private double getHealthReduction() {
        double d;
        if (!$assertionsDisabled && this.mc.world == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        double d2 = 0.0;
        if (this.mc.player.abilities.creativeMode) {
            return d2;
        }
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (Entity2 instanceof EndCrystalEntity && d2 < DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos())) {
                d2 = DamageCalcUtils.crystalDamage((LivingEntity)this.mc.player, Entity2.getPos());
                continue;
            }
            if (!(Entity2 instanceof PlayerEntity) || !(d2 < DamageCalcUtils.getSwordDamage((PlayerEntity)Entity2, true)) || !Friends.get().notTrusted((PlayerEntity)Entity2) || !(this.mc.player.getPos().distanceTo(Entity2.getPos()) < 5.0) || !(((PlayerEntity)Entity2).getActiveItem().getItem() instanceof SwordItem)) continue;
            d2 = DamageCalcUtils.getSwordDamage((PlayerEntity)Entity2, true);
        }
        if (!Modules.get().isActive(NoFall.class) && this.mc.player.fallDistance > 3.0f && (d = (double)this.mc.player.fallDistance * 0.5) > d2) {
            d2 = d;
        }
        if (Utils.getDimension() != Dimension.Nether) {
            for (Entity Entity2 : this.mc.world.blockEntities) {
                if (!(Entity2 instanceof BedBlockEntity)) continue;
                Vec3d Vec3d2 = new Vec3d((double)Entity2.getPos().getX(), (double)Entity2.getPos().getY(), (double)Entity2.getPos().getZ());
                if (!(d2 < DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Vec3d2))) continue;
                d2 = DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, new Vec3d((double)Entity2.getPos().getX(), (double)Entity2.getPos().getY(), (double)Entity2.getPos().getZ()));
            }
        }
        return d2;
    }

    public boolean getLocked() {
        return this.locked;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.currentScreen instanceof InventoryScreen && !this.inventorySwitch.get().booleanValue()) {
            return;
        }
        if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof InventoryScreen)) {
            return;
        }
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
        if (findItemResult.count <= 0) {
            if (!Modules.get().isActive(OffhandExtra.class) && this.fallback.get().booleanValue()) {
                Modules.get().get(OffhandExtra.class).toggle();
            }
            Modules.get().get(OffhandExtra.class).setTotems(true);
            this.locked = false;
        } else {
            Modules.get().get(OffhandExtra.class).setTotems(false);
            if (this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING && (!this.smart.get().booleanValue() || this.isLow() || this.elytraMove())) {
                this.locked = true;
                InvUtils.move().from(findItemResult.slot).toOffhand();
            } else if (this.smart.get().booleanValue() && !this.isLow() && !this.elytraMove()) {
                this.locked = false;
            }
        }
        this.totemCountString = Integer.toString(findItemResult.count);
    }

    public AutoTotem() {
        super(Categories.Combat, "auto-totem", "Automatically equips totems in your offhand.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.smart = this.sgGeneral.add(new BoolSetting.Builder().name("smart").description("Only switches to a totem when you are close to death.").defaultValue(false).build());
        this.fallback = this.sgGeneral.add(new BoolSetting.Builder().name("fallback").description("Enables Offhand Extra when you run out of totems.").defaultValue(true).build());
        this.inventorySwitch = this.sgGeneral.add(new BoolSetting.Builder().name("inventory").description("Whether or not to equip totems while in your inventory.").defaultValue(true).build());
        this.health = this.sgGeneral.add(new IntSetting.Builder().name("health").description("The health Auto Totem's smart mode activates at.").defaultValue(10).min(0).sliderMax(20).build());
        this.elytraHold = this.sgGeneral.add(new BoolSetting.Builder().name("elytra-hold").description("Whether or not to always hold a totem when flying with an elytra.").defaultValue(false).build());
        this.totemCountString = "0";
        this.mc = MinecraftClient.getInstance();
        this.locked = false;
    }

    private boolean elytraMove() {
        return this.elytraHold.get() != false && this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && this.mc.player.isFallFlying();
    }

    private boolean isLow() {
        return this.getHealth() < (double)this.health.get().intValue() || this.getHealth() - this.getHealthReduction() < (double)this.health.get().intValue();
    }

    private double getHealth() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        return this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
    }

    @Override
    public String getInfoString() {
        return this.totemCountString;
    }

    @Override
    public void onDeactivate() {
        this.locked = false;
    }
}

