/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.potion.PotionUtil
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
    private /* synthetic */ int speedSlot;
    private /* synthetic */ int prevSlot;
    private /* synthetic */ ArrowType shootingArrow;
    private /* synthetic */ boolean foundStrength;
    private final /* synthetic */ Setting<Boolean> checkEffects;
    private /* synthetic */ boolean foundSpeed;
    private /* synthetic */ boolean shouldShoot;
    private /* synthetic */ boolean shotStrength;
    private /* synthetic */ int strengthSlot;
    private final /* synthetic */ Setting<Boolean> chatInfo;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ boolean shotSpeed;
    private final /* synthetic */ Setting<Integer> charge;
    private /* synthetic */ boolean shooting;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllllIIlIIIlIlIl) {
        Quiver lllllllllllllllllllllIIlIIIlIIll;
        RotationUtils.packetRotate(lllllllllllllllllllllIIlIIIlIIll.mc.player.yaw, -90.0f);
        boolean lllllllllllllllllllllIIlIIIlIlII = false;
        if (lllllllllllllllllllllIIlIIIlIIll.shooting && lllllllllllllllllllllIIlIIIlIIll.mc.player.getItemUseTime() >= lllllllllllllllllllllIIlIIIlIIll.charge.get()) {
            if (lllllllllllllllllllllIIlIIIlIIll.shootingArrow == ArrowType.Strength) {
                lllllllllllllllllllllIIlIIIlIIll.endShooting(lllllllllllllllllllllIIlIIIlIIll.strengthSlot);
            }
            if (lllllllllllllllllllllIIlIIIlIIll.shootingArrow == ArrowType.Speed) {
                lllllllllllllllllllllIIlIIIlIIll.endShooting(lllllllllllllllllllllIIlIIIlIIll.speedSlot);
            }
            lllllllllllllllllllllIIlIIIlIlII = true;
        }
        if (lllllllllllllllllllllIIlIIIlIIll.shotStrength && lllllllllllllllllllllIIlIIIlIIll.shotSpeed && lllllllllllllllllllllIIlIIIlIlII) {
            if (lllllllllllllllllllllIIlIIIlIIll.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(lllllllllllllllllllllIIlIIIlIIll, "Quiver complete... disabling.", new Object[0]);
            }
            lllllllllllllllllllllIIlIIIlIIll.toggle();
            return;
        }
        if (lllllllllllllllllllllIIlIIIlIIll.shouldShoot) {
            if (!lllllllllllllllllllllIIlIIIlIIll.shooting && !lllllllllllllllllllllIIlIIIlIIll.shotStrength && lllllllllllllllllllllIIlIIIlIIll.foundStrength) {
                lllllllllllllllllllllIIlIIIlIIll.shoot(lllllllllllllllllllllIIlIIIlIIll.strengthSlot);
                lllllllllllllllllllllIIlIIIlIIll.shootingArrow = ArrowType.Strength;
                if (lllllllllllllllllllllIIlIIIlIIll.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleInfo(lllllllllllllllllllllIIlIIIlIIll, "Quivering a strength arrow.", new Object[0]);
                }
                lllllllllllllllllllllIIlIIIlIIll.shotStrength = true;
            }
            if (!lllllllllllllllllllllIIlIIIlIIll.shooting && !lllllllllllllllllllllIIlIIIlIIll.shotSpeed && lllllllllllllllllllllIIlIIIlIIll.foundSpeed && lllllllllllllllllllllIIlIIIlIIll.shotStrength) {
                lllllllllllllllllllllIIlIIIlIIll.shoot(lllllllllllllllllllllIIlIIIlIIll.speedSlot);
                lllllllllllllllllllllIIlIIIlIIll.shootingArrow = ArrowType.Speed;
                if (lllllllllllllllllllllIIlIIIlIIll.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleInfo(lllllllllllllllllllllIIlIIIlIIll, "Quivering a speed arrow.", new Object[0]);
                }
                lllllllllllllllllllllIIlIIIlIIll.shotSpeed = true;
            }
        }
    }

    private void shoot(int lllllllllllllllllllllIIlIIIIlllI) {
        Quiver lllllllllllllllllllllIIlIIIIllIl;
        if (lllllllllllllllllllllIIlIIIIlllI != 9) {
            lllllllllllllllllllllIIlIIIIllIl.moveItems(lllllllllllllllllllllIIlIIIIlllI, 9);
        }
        lllllllllllllllllllllIIlIIIIllIl.setPressed(true);
        lllllllllllllllllllllIIlIIIIllIl.shooting = true;
    }

    private int findBow() {
        Quiver lllllllllllllllllllllIIIllIlIIll;
        int lllllllllllllllllllllIIIllIlIIlI = -1;
        assert (lllllllllllllllllllllIIIllIlIIll.mc.player != null);
        for (int lllllllllllllllllllllIIIllIlIlII = 0; lllllllllllllllllllllIIIllIlIlII < 9; ++lllllllllllllllllllllIIIllIlIlII) {
            if (lllllllllllllllllllllIIIllIlIIll.mc.player.inventory.getStack(lllllllllllllllllllllIIIllIlIlII).getItem() != Items.BOW) continue;
            lllllllllllllllllllllIIIllIlIIlI = lllllllllllllllllllllIIIllIlIlII;
        }
        return lllllllllllllllllllllIIIllIlIIlI;
    }

    @Override
    public void onDeactivate() {
        Quiver lllllllllllllllllllllIIlIIIllIIl;
        lllllllllllllllllllllIIlIIIllIIl.mc.player.inventory.selectedSlot = lllllllllllllllllllllIIlIIIllIIl.prevSlot;
    }

    private boolean isType(String lllllllllllllllllllllIIIlllIlIIl, int lllllllllllllllllllllIIIlllIllII) {
        List lllllllllllllllllllllIIIlllIllll;
        Quiver lllllllllllllllllllllIIIlllIlllI;
        assert (lllllllllllllllllllllIIIlllIlllI.mc.player != null);
        ItemStack lllllllllllllllllllllIIIlllIlIll = lllllllllllllllllllllIIIlllIlllI.mc.player.inventory.getStack(lllllllllllllllllllllIIIlllIllII);
        if (lllllllllllllllllllllIIIlllIlIll.getItem() == Items.TIPPED_ARROW && (lllllllllllllllllllllIIIlllIllll = PotionUtil.getPotion((ItemStack)lllllllllllllllllllllIIIlllIlIll).getEffects()).size() > 0) {
            StatusEffectInstance lllllllllllllllllllllIIIllllIIII = (StatusEffectInstance)lllllllllllllllllllllIIIlllIllll.get(0);
            return lllllllllllllllllllllIIIllllIIII.getTranslationKey().equals(lllllllllllllllllllllIIIlllIlIIl);
        }
        return false;
    }

    private Map<ArrowType, Integer> getAllArrows() {
        Quiver lllllllllllllllllllllIIIllllllll;
        HashMap<ArrowType, Integer> lllllllllllllllllllllIIIlllllllI = new HashMap<ArrowType, Integer>();
        boolean lllllllllllllllllllllIIIllllllIl = lllllllllllllllllllllIIIllllllll.mc.player.getActiveStatusEffects().containsKey((Object)StatusEffects.STRENGTH);
        boolean lllllllllllllllllllllIIIllllllII = lllllllllllllllllllllIIIllllllll.mc.player.getActiveStatusEffects().containsKey((Object)StatusEffects.SPEED);
        for (int lllllllllllllllllllllIIlIIIIIIII = 35; lllllllllllllllllllllIIlIIIIIIII >= 0; --lllllllllllllllllllllIIlIIIIIIII) {
            if (lllllllllllllllllllllIIIllllllll.mc.player.inventory.getStack(lllllllllllllllllllllIIlIIIIIIII).getItem() != Items.TIPPED_ARROW || lllllllllllllllllllllIIlIIIIIIII == lllllllllllllllllllllIIIllllllll.mc.player.inventory.selectedSlot) continue;
            if (lllllllllllllllllllllIIIllllllll.checkEffects.get().booleanValue()) {
                if (lllllllllllllllllllllIIIllllllll.isType("effect.minecraft.strength", lllllllllllllllllllllIIlIIIIIIII) && !lllllllllllllllllllllIIIllllllIl) {
                    lllllllllllllllllllllIIIlllllllI.put(ArrowType.Strength, lllllllllllllllllllllIIlIIIIIIII);
                    continue;
                }
                if (!lllllllllllllllllllllIIIllllllll.isType("effect.minecraft.speed", lllllllllllllllllllllIIlIIIIIIII) || lllllllllllllllllllllIIIllllllII) continue;
                lllllllllllllllllllllIIIlllllllI.put(ArrowType.Speed, lllllllllllllllllllllIIlIIIIIIII);
                continue;
            }
            if (lllllllllllllllllllllIIIllllllll.isType("effect.minecraft.strength", lllllllllllllllllllllIIlIIIIIIII)) {
                lllllllllllllllllllllIIIlllllllI.put(ArrowType.Strength, lllllllllllllllllllllIIlIIIIIIII);
                continue;
            }
            if (!lllllllllllllllllllllIIIllllllll.isType("effect.minecraft.speed", lllllllllllllllllllllIIlIIIIIIII)) continue;
            lllllllllllllllllllllIIIlllllllI.put(ArrowType.Speed, lllllllllllllllllllllIIlIIIIIIII);
        }
        return lllllllllllllllllllllIIIlllllllI;
    }

    private void endShooting(int lllllllllllllllllllllIIlIIIIIllI) {
        Quiver lllllllllllllllllllllIIlIIIIIlll;
        lllllllllllllllllllllIIlIIIIIlll.setPressed(false);
        lllllllllllllllllllllIIlIIIIIlll.mc.player.stopUsingItem();
        lllllllllllllllllllllIIlIIIIIlll.mc.interactionManager.stopUsingItem((PlayerEntity)lllllllllllllllllllllIIlIIIIIlll.mc.player);
        if (lllllllllllllllllllllIIlIIIIIllI != 9) {
            lllllllllllllllllllllIIlIIIIIlll.moveItems(9, lllllllllllllllllllllIIlIIIIIllI);
        }
        lllllllllllllllllllllIIlIIIIIlll.shooting = false;
    }

    @Override
    public void onActivate() {
        Quiver lllllllllllllllllllllIIlIIlIIIll;
        lllllllllllllllllllllIIlIIlIIIll.shooting = false;
        int lllllllllllllllllllllIIlIIlIIIlI = 0;
        lllllllllllllllllllllIIlIIlIIIll.prevSlot = lllllllllllllllllllllIIlIIlIIIll.mc.player.inventory.selectedSlot;
        lllllllllllllllllllllIIlIIlIIIll.shotStrength = false;
        lllllllllllllllllllllIIlIIlIIIll.shotSpeed = false;
        lllllllllllllllllllllIIlIIlIIIll.foundStrength = false;
        lllllllllllllllllllllIIlIIlIIIll.foundSpeed = false;
        lllllllllllllllllllllIIlIIlIIIll.shootingArrow = null;
        lllllllllllllllllllllIIlIIlIIIll.strengthSlot = -1;
        lllllllllllllllllllllIIlIIlIIIll.speedSlot = -1;
        int lllllllllllllllllllllIIlIIlIIIIl = lllllllllllllllllllllIIlIIlIIIll.findBow();
        if (lllllllllllllllllllllIIlIIlIIIIl == -1) {
            if (lllllllllllllllllllllIIlIIlIIIll.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(lllllllllllllllllllllIIlIIlIIIll, "No bow found... disabling.", new Object[0]);
            }
            lllllllllllllllllllllIIlIIlIIIll.toggle();
            return;
        }
        lllllllllllllllllllllIIlIIlIIIll.mc.player.inventory.selectedSlot = lllllllllllllllllllllIIlIIlIIIIl;
        for (Map.Entry<ArrowType, Integer> lllllllllllllllllllllIIlIIlIIlII : lllllllllllllllllllllIIlIIlIIIll.getAllArrows().entrySet()) {
            if (lllllllllllllllllllllIIlIIlIIlII.getKey() == ArrowType.Strength && !lllllllllllllllllllllIIlIIlIIIll.foundStrength) {
                lllllllllllllllllllllIIlIIlIIIll.strengthSlot = lllllllllllllllllllllIIlIIlIIlII.getValue();
                lllllllllllllllllllllIIlIIlIIIll.foundStrength = true;
            }
            if (lllllllllllllllllllllIIlIIlIIlII.getKey() != ArrowType.Speed || lllllllllllllllllllllIIlIIlIIIll.foundSpeed) continue;
            lllllllllllllllllllllIIlIIlIIIll.speedSlot = lllllllllllllllllllllIIlIIlIIlII.getValue();
            lllllllllllllllllllllIIlIIlIIIll.foundSpeed = true;
        }
        if (lllllllllllllllllllllIIlIIlIIIll.strengthSlot != -1) {
            ++lllllllllllllllllllllIIlIIlIIIlI;
        }
        if (lllllllllllllllllllllIIlIIlIIIll.speedSlot != -1) {
            ++lllllllllllllllllllllIIlIIlIIIlI;
        }
        if (lllllllllllllllllllllIIlIIlIIIlI == 0) {
            if (lllllllllllllllllllllIIlIIlIIIll.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(lllllllllllllllllllllIIlIIlIIIll, "No appropriate arrows found... disabling.", new Object[0]);
            }
            lllllllllllllllllllllIIlIIlIIIll.toggle();
            return;
        }
        lllllllllllllllllllllIIlIIlIIIll.shouldShoot = true;
        if (!lllllllllllllllllllllIIlIIlIIIll.foundSpeed) {
            lllllllllllllllllllllIIlIIlIIIll.shotSpeed = true;
        }
        if (!lllllllllllllllllllllIIlIIlIIIll.foundStrength) {
            lllllllllllllllllllllIIlIIlIIIll.shotStrength = true;
        }
    }

    private void moveItems(int lllllllllllllllllllllIIIllIllIll, int lllllllllllllllllllllIIIllIllIlI) {
        InvUtils.move().from(lllllllllllllllllllllIIIllIllIll).to(lllllllllllllllllllllIIIllIllIlI);
    }

    public Quiver() {
        super(Categories.Combat, "quiver", "Automatically shoots positive effect arrows at you.");
        Quiver lllllllllllllllllllllIIlIIlIlIlI;
        lllllllllllllllllllllIIlIIlIlIlI.sgGeneral = lllllllllllllllllllllIIlIIlIlIlI.settings.getDefaultGroup();
        lllllllllllllllllllllIIlIIlIlIlI.charge = lllllllllllllllllllllIIlIIlIlIlI.sgGeneral.add(new IntSetting.Builder().name("charge-delay").description("The amount of delay for bow charging in ticks.").defaultValue(6).min(5).max(20).sliderMin(5).sliderMax(20).build());
        lllllllllllllllllllllIIlIIlIlIlI.checkEffects = lllllllllllllllllllllIIlIIlIlIlI.sgGeneral.add(new BoolSetting.Builder().name("check-effects").description("Won't shoot you with effects you already have active.").defaultValue(true).build());
        lllllllllllllllllllllIIlIIlIlIlI.chatInfo = lllllllllllllllllllllIIlIIlIlIlI.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Sends you information about the module when toggled.").defaultValue(true).build());
    }

    private void setPressed(boolean lllllllllllllllllllllIIIllIlllll) {
        Quiver lllllllllllllllllllllIIIlllIIIII;
        lllllllllllllllllllllIIIlllIIIII.mc.options.keyUse.setPressed(lllllllllllllllllllllIIIllIlllll);
    }

    public static final class ArrowType
    extends Enum<ArrowType> {
        private static final /* synthetic */ ArrowType[] $VALUES;
        public static final /* synthetic */ /* enum */ ArrowType Strength;
        public static final /* synthetic */ /* enum */ ArrowType Speed;

        public static ArrowType valueOf(String llllllllllllllllIlllIlIIIIlIllll) {
            return Enum.valueOf(ArrowType.class, llllllllllllllllIlllIlIIIIlIllll);
        }

        static {
            Strength = new ArrowType();
            Speed = new ArrowType();
            $VALUES = ArrowType.$values();
        }

        private static /* synthetic */ ArrowType[] $values() {
            return new ArrowType[]{Strength, Speed};
        }

        public static ArrowType[] values() {
            return (ArrowType[])$VALUES.clone();
        }

        private ArrowType() {
            ArrowType llllllllllllllllIlllIlIIIIlIlIIl;
        }
    }
}

