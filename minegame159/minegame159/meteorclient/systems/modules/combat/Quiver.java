/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.RotationUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;

public class Quiver
extends Module {
    private boolean shotStrength;
    private boolean shouldShoot;
    private boolean foundSpeed;
    private final SettingGroup sgGeneral;
    private ArrowType shootingArrow;
    private boolean shotSpeed;
    private int speedSlot;
    private boolean foundStrength;
    private int prevSlot;
    private final Setting<Boolean> checkEffects;
    private int strengthSlot;
    static final boolean $assertionsDisabled = !Quiver.class.desiredAssertionStatus();
    private final Setting<Integer> charge;
    private final Setting<Boolean> chatInfo;
    private boolean shooting;

    private void moveItems(int n, int n2) {
        InvUtils.move().from(n).to(n2);
    }

    @Override
    public void onActivate() {
        this.shooting = false;
        int n = 0;
        this.prevSlot = this.mc.player.inventory.selectedSlot;
        this.shotStrength = false;
        this.shotSpeed = false;
        this.foundStrength = false;
        this.foundSpeed = false;
        this.shootingArrow = null;
        this.strengthSlot = -1;
        this.speedSlot = -1;
        int n2 = this.findBow();
        if (n2 == -1) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(this, "No bow found... disabling.", new Object[0]);
            }
            this.toggle();
            return;
        }
        this.mc.player.inventory.selectedSlot = n2;
        for (Map.Entry<ArrowType, Integer> entry : this.getAllArrows().entrySet()) {
            if (entry.getKey() == ArrowType.Strength && !this.foundStrength) {
                this.strengthSlot = entry.getValue();
                this.foundStrength = true;
            }
            if (entry.getKey() != ArrowType.Speed || this.foundSpeed) continue;
            this.speedSlot = entry.getValue();
            this.foundSpeed = true;
        }
        if (this.strengthSlot != -1) {
            ++n;
        }
        if (this.speedSlot != -1) {
            ++n;
        }
        if (n == 0) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(this, "No appropriate arrows found... disabling.", new Object[0]);
            }
            this.toggle();
            return;
        }
        this.shouldShoot = true;
        if (!this.foundSpeed) {
            this.shotSpeed = true;
        }
        if (!this.foundStrength) {
            this.shotStrength = true;
        }
    }

    public Quiver() {
        super(Categories.Combat, "quiver", "Automatically shoots positive effect arrows at you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.charge = this.sgGeneral.add(new IntSetting.Builder().name("charge-delay").description("The amount of delay for bow charging in ticks.").defaultValue(6).min(5).max(20).sliderMin(5).sliderMax(20).build());
        this.checkEffects = this.sgGeneral.add(new BoolSetting.Builder().name("check-effects").description("Won't shoot you with effects you already have active.").defaultValue(true).build());
        this.chatInfo = this.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Sends you information about the module when toggled.").defaultValue(true).build());
    }

    private void shoot(int n) {
        if (n != 9) {
            this.moveItems(n, 9);
        }
        this.setPressed(true);
        this.shooting = true;
    }

    private void endShooting(int n) {
        this.setPressed(false);
        this.mc.player.stopUsingItem();
        this.mc.interactionManager.stopUsingItem((PlayerEntity)this.mc.player);
        if (n != 9) {
            this.moveItems(9, n);
        }
        this.shooting = false;
    }

    @Override
    public void onDeactivate() {
        this.mc.player.inventory.selectedSlot = this.prevSlot;
    }

    private boolean isType(String string, int n) {
        List list;
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        ItemStack ItemStack2 = this.mc.player.inventory.getStack(n);
        if (ItemStack2.getItem() == Items.TIPPED_ARROW && (list = PotionUtil.getPotion((ItemStack)ItemStack2).getEffects()).size() > 0) {
            StatusEffectInstance StatusEffectInstance2 = (StatusEffectInstance)list.get(0);
            return StatusEffectInstance2.getTranslationKey().equals(string);
        }
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        RotationUtils.packetRotate(this.mc.player.yaw, -90.0f);
        boolean bl = false;
        if (this.shooting && this.mc.player.getItemUseTime() >= this.charge.get()) {
            if (this.shootingArrow == ArrowType.Strength) {
                this.endShooting(this.strengthSlot);
            }
            if (this.shootingArrow == ArrowType.Speed) {
                this.endShooting(this.speedSlot);
            }
            bl = true;
        }
        if (this.shotStrength && this.shotSpeed && bl) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(this, "Quiver complete... disabling.", new Object[0]);
            }
            this.toggle();
            return;
        }
        if (this.shouldShoot) {
            if (!this.shooting && !this.shotStrength && this.foundStrength) {
                this.shoot(this.strengthSlot);
                this.shootingArrow = ArrowType.Strength;
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleInfo(this, "Quivering a strength arrow.", new Object[0]);
                }
                this.shotStrength = true;
            }
            if (!this.shooting && !this.shotSpeed && this.foundSpeed && this.shotStrength) {
                this.shoot(this.speedSlot);
                this.shootingArrow = ArrowType.Speed;
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleInfo(this, "Quivering a speed arrow.", new Object[0]);
                }
                this.shotSpeed = true;
            }
        }
    }

    private void setPressed(boolean bl) {
        this.mc.options.keyUse.setPressed(bl);
    }

    private int findBow() {
        int n = -1;
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        for (int i = 0; i < 9; ++i) {
            if (this.mc.player.inventory.getStack(i).getItem() != Items.BOW) continue;
            n = i;
            if (4 > 0) continue;
            return 0;
        }
        return n;
    }

    private Map<ArrowType, Integer> getAllArrows() {
        HashMap<ArrowType, Integer> hashMap = new HashMap<ArrowType, Integer>();
        boolean bl = this.mc.player.getActiveStatusEffects().containsKey(StatusEffects.STRENGTH);
        boolean bl2 = this.mc.player.getActiveStatusEffects().containsKey(StatusEffects.SPEED);
        for (int i = 35; i >= 0; --i) {
            if (this.mc.player.inventory.getStack(i).getItem() != Items.TIPPED_ARROW || i == this.mc.player.inventory.selectedSlot) continue;
            if (this.checkEffects.get().booleanValue()) {
                if (this.isType("effect.minecraft.strength", i) && !bl) {
                    hashMap.put(ArrowType.Strength, i);
                    continue;
                }
                if (!this.isType("effect.minecraft.speed", i) || bl2) continue;
                hashMap.put(ArrowType.Speed, i);
                continue;
            }
            if (this.isType("effect.minecraft.strength", i)) {
                hashMap.put(ArrowType.Strength, i);
                continue;
            }
            if (!this.isType("effect.minecraft.speed", i)) continue;
            hashMap.put(ArrowType.Speed, i);
        }
        return hashMap;
    }

    public static final class ArrowType
    extends Enum<ArrowType> {
        private static final ArrowType[] $VALUES;
        public static final /* enum */ ArrowType Strength;
        public static final /* enum */ ArrowType Speed;

        public static ArrowType[] values() {
            return (ArrowType[])$VALUES.clone();
        }

        private static ArrowType[] $values() {
            return new ArrowType[]{Strength, Speed};
        }

        public static ArrowType valueOf(String string) {
            return Enum.valueOf(ArrowType.class, string);
        }

        static {
            Strength = new ArrowType();
            Speed = new ArrowType();
            $VALUES = ArrowType.$values();
        }
    }
}

