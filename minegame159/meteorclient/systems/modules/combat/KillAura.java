/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.passive.AnimalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.AxeItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.SwordItem
 */
package minegame159.meteorclient.systems.modules.combat;

import baritone.api.BaritoneAPI;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class KillAura
extends Module {
    private /* synthetic */ boolean wasPathing;
    private final /* synthetic */ Setting<Boolean> targetMultiple;
    private final /* synthetic */ Setting<Boolean> pauseOnCombat;
    private final /* synthetic */ Setting<Double> hitChance;
    private final /* synthetic */ Setting<RotationMode> rotationMode;
    private final /* synthetic */ Setting<Integer> hitDelay;
    private final /* synthetic */ Setting<Boolean> smartDelay;
    private final /* synthetic */ Setting<Boolean> autoSwitch;
    private final /* synthetic */ Setting<Boolean> randomDelayEnabled;
    private /* synthetic */ int hitDelayTimer;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ SettingGroup sgDelay;
    private final /* synthetic */ List<Entity> entityList;
    private final /* synthetic */ Setting<SortPriority> priority;
    private /* synthetic */ Entity target;
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ Setting<Target> rotationDirection;
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> nametagged;
    private final /* synthetic */ Setting<Boolean> babies;
    private /* synthetic */ boolean canAttack;
    private final /* synthetic */ Setting<OnlyWith> onlyWith;
    private final /* synthetic */ SettingGroup sgRotations;
    private final /* synthetic */ Setting<Boolean> ignoreWalls;
    private final /* synthetic */ Setting<Integer> randomDelayMax;
    private /* synthetic */ int randomDelayTimer;

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIIlIIlllIllIl) {
        KillAura lllllllllllllllllllIIlIIlllIllII;
        lllllllllllllllllllIIlIIlllIllII.entityList.clear();
        if (lllllllllllllllllllIIlIIlllIllII.mc.player.isDead() || !lllllllllllllllllllIIlIIlllIllII.mc.player.isAlive() || !lllllllllllllllllllIIlIIlllIllII.itemInHand()) {
            return;
        }
        EntityUtils.getList(lllllllllllllllllllIIlIIlIllllII -> {
            KillAura lllllllllllllllllllIIlIIlIllllIl;
            if (lllllllllllllllllllIIlIIlIllllII == lllllllllllllllllllIIlIIlIllllIl.mc.player || lllllllllllllllllllIIlIIlIllllII == lllllllllllllllllllIIlIIlIllllIl.mc.cameraEntity) {
                return false;
            }
            if (lllllllllllllllllllIIlIIlIllllII instanceof LivingEntity && ((LivingEntity)lllllllllllllllllllIIlIIlIllllII).isDead() || !lllllllllllllllllllIIlIIlIllllII.isAlive()) {
                return false;
            }
            if ((double)lllllllllllllllllllIIlIIlIllllII.distanceTo((Entity)lllllllllllllllllllIIlIIlIllllIl.mc.player) > lllllllllllllllllllIIlIIlIllllIl.range.get()) {
                return false;
            }
            if (!lllllllllllllllllllIIlIIlIllllIl.entities.get().getBoolean((Object)lllllllllllllllllllIIlIIlIllllII.getType())) {
                return false;
            }
            if (!lllllllllllllllllllIIlIIlIllllIl.nametagged.get().booleanValue() && lllllllllllllllllllIIlIIlIllllII.hasCustomName()) {
                return false;
            }
            if (!lllllllllllllllllllIIlIIlIllllIl.ignoreWalls.get().booleanValue() && !PlayerUtils.canSeeEntity(lllllllllllllllllllIIlIIlIllllII)) {
                return false;
            }
            if (lllllllllllllllllllIIlIIlIllllII instanceof PlayerEntity) {
                if (((PlayerEntity)lllllllllllllllllllIIlIIlIllllII).isCreative()) {
                    return false;
                }
                if (!lllllllllllllllllllIIlIIlIllllIl.friends.get().booleanValue() && !Friends.get().attack((PlayerEntity)lllllllllllllllllllIIlIIlIllllII)) {
                    return false;
                }
            }
            return !(lllllllllllllllllllIIlIIlIllllII instanceof AnimalEntity) || lllllllllllllllllllIIlIIlIllllIl.babies.get() != false || !((AnimalEntity)lllllllllllllllllllIIlIIlIllllII).isBaby();
        }, lllllllllllllllllllIIlIIlllIllII.priority.get(), lllllllllllllllllllIIlIIlllIllII.entityList);
        if (!lllllllllllllllllllIIlIIlllIllII.targetMultiple.get().booleanValue() && !lllllllllllllllllllIIlIIlllIllII.entityList.isEmpty()) {
            lllllllllllllllllllIIlIIlllIllII.entityList.subList(1, lllllllllllllllllllIIlIIlllIllII.entityList.size()).clear();
        }
        if (lllllllllllllllllllIIlIIlllIllII.entityList.isEmpty()) {
            if (lllllllllllllllllllIIlIIlllIllII.wasPathing) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
                lllllllllllllllllllIIlIIlllIllII.wasPathing = false;
            }
            return;
        }
        if (lllllllllllllllllllIIlIIlllIllII.pauseOnCombat.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !lllllllllllllllllllIIlIIlllIllII.wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            lllllllllllllllllllIIlIIlllIllII.wasPathing = true;
        }
        if (lllllllllllllllllllIIlIIlllIllII.rotationMode.get() == RotationMode.Always && lllllllllllllllllllIIlIIlllIllII.target != null) {
            Rotations.rotate(Rotations.getYaw(lllllllllllllllllllIIlIIlllIllII.target), Rotations.getPitch(lllllllllllllllllllIIlIIlllIllII.target, lllllllllllllllllllIIlIIlllIllII.rotationDirection.get()));
        }
        if (lllllllllllllllllllIIlIIlllIllII.smartDelay.get().booleanValue() && lllllllllllllllllllIIlIIlllIllII.mc.player.getAttackCooldownProgress(0.5f) < 1.0f) {
            return;
        }
        if (lllllllllllllllllllIIlIIlllIllII.hitDelayTimer >= 0) {
            --lllllllllllllllllllIIlIIlllIllII.hitDelayTimer;
            return;
        }
        lllllllllllllllllllIIlIIlllIllII.hitDelayTimer = lllllllllllllllllllIIlIIlllIllII.hitDelay.get();
        if (lllllllllllllllllllIIlIIlllIllII.randomDelayEnabled.get().booleanValue()) {
            if (lllllllllllllllllllIIlIIlllIllII.randomDelayTimer > 0) {
                --lllllllllllllllllllIIlIIlllIllII.randomDelayTimer;
                return;
            }
            lllllllllllllllllllIIlIIlllIllII.randomDelayTimer = (int)Math.round(Math.random() * (double)lllllllllllllllllllIIlIIlllIllII.randomDelayMax.get().intValue());
        }
        for (Entity lllllllllllllllllllIIlIIlllIllll : lllllllllllllllllllIIlIIlllIllII.entityList) {
            if (!lllllllllllllllllllIIlIIlllIllII.attack(lllllllllllllllllllIIlIIlllIllll) || !lllllllllllllllllllIIlIIlllIllII.canAttack) continue;
            lllllllllllllllllllIIlIIlllIllII.hitEntity(lllllllllllllllllllIIlIIlllIllll);
        }
    }

    public Entity getTarget() {
        KillAura lllllllllllllllllllIIlIIllIlIIII;
        return lllllllllllllllllllIIlIIllIlIIII.target;
    }

    @Override
    public void onDeactivate() {
        KillAura lllllllllllllllllllIIlIIllllIlII;
        lllllllllllllllllllIIlIIllllIlII.hitDelayTimer = 0;
        lllllllllllllllllllIIlIIllllIlII.randomDelayTimer = 0;
        lllllllllllllllllllIIlIIllllIlII.entityList.clear();
        lllllllllllllllllllIIlIIllllIlII.target = null;
    }

    @Override
    public String getInfoString() {
        KillAura lllllllllllllllllllIIlIIllIlIlII;
        if (!lllllllllllllllllllIIlIIllIlIlII.entityList.isEmpty()) {
            Entity lllllllllllllllllllIIlIIllIlIlIl = lllllllllllllllllllIIlIIllIlIlII.entityList.get(0);
            if (lllllllllllllllllllIIlIIllIlIlIl instanceof PlayerEntity) {
                return lllllllllllllllllllIIlIIllIlIlIl.getEntityName();
            }
            return lllllllllllllllllllIIlIIllIlIlIl.getType().getName().getString();
        }
        return null;
    }

    public KillAura() {
        super(Categories.Combat, "kill-aura", "Attacks specified entities around you.");
        KillAura lllllllllllllllllllIIlIIllllIllI;
        lllllllllllllllllllIIlIIllllIllI.sgGeneral = lllllllllllllllllllIIlIIllllIllI.settings.getDefaultGroup();
        lllllllllllllllllllIIlIIllllIllI.sgRotations = lllllllllllllllllllIIlIIllllIllI.settings.createGroup("Rotations");
        lllllllllllllllllllIIlIIllllIllI.sgDelay = lllllllllllllllllllIIlIIllllIllI.settings.createGroup("Delay");
        lllllllllllllllllllIIlIIllllIllI.range = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range the entity can be to attack it.").defaultValue(4.0).min(0.0).max(6.0).sliderMax(6.0).build());
        lllllllllllllllllllIIlIIllllIllI.entities = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to attack.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).onlyAttackable().build());
        lllllllllllllllllllIIlIIllllIllI.priority = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new EnumSetting.Builder().name("priority").description("What type of entities to target.").defaultValue(SortPriority.LowestHealth).build());
        lllllllllllllllllllIIlIIllllIllI.onlyWith = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new EnumSetting.Builder().name("only-with").description("Only attacks an entity when a specified item is in your hand.").defaultValue(OnlyWith.Any).build());
        lllllllllllllllllllIIlIIllllIllI.autoSwitch = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("auto-switch").description("Switches to an axe or a sword when attacking the target.").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.ignoreWalls = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("ignore-walls").description("Whether or not to attack the entity through a wall.").defaultValue(true).build());
        lllllllllllllllllllIIlIIllllIllI.friends = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Whether or not to attack friends. Useful if you select players selected.").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.babies = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("babies").description("Whether or not to attack baby variants of the entity.").defaultValue(true).build());
        lllllllllllllllllllIIlIIllllIllI.nametagged = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("nametagged").description("Whether or not to attack mobs with a name tag.").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.hitChance = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new DoubleSetting.Builder().name("hit-chance").description("The probability of your hits landing.").defaultValue(100.0).min(0.0).max(100.0).sliderMax(100.0).build());
        lllllllllllllllllllIIlIIllllIllI.pauseOnCombat = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("pause-on-combat").description("Freezes Baritone temporarily until you are finished attacking the entity.").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.targetMultiple = lllllllllllllllllllIIlIIllllIllI.sgGeneral.add(new BoolSetting.Builder().name("target-multiple").description("Target multiple entities at once").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.rotationMode = lllllllllllllllllllIIlIIllllIllI.sgRotations.add(new EnumSetting.Builder().name("rotation-mode").description("Determines when you should rotate towards the target.").defaultValue(RotationMode.OnHit).build());
        lllllllllllllllllllIIlIIllllIllI.rotationDirection = lllllllllllllllllllIIlIIllllIllI.sgRotations.add(new EnumSetting.Builder().name("rotation-direction").description("The direction to use for rotating towards the enemy.").defaultValue(Target.Head).build());
        lllllllllllllllllllIIlIIllllIllI.smartDelay = lllllllllllllllllllIIlIIllllIllI.sgDelay.add(new BoolSetting.Builder().name("smart-delay").description("Smart delay.").defaultValue(true).build());
        lllllllllllllllllllIIlIIllllIllI.hitDelay = lllllllllllllllllllIIlIIllllIllI.sgDelay.add(new IntSetting.Builder().name("hit-delay").description("How fast you hit the entity in ticks.").defaultValue(0).min(0).sliderMax(60).build());
        lllllllllllllllllllIIlIIllllIllI.randomDelayEnabled = lllllllllllllllllllIIlIIllllIllI.sgDelay.add(new BoolSetting.Builder().name("random-delay-enabled").description("Adds a random delay between hits to attempt to bypass anti-cheats.").defaultValue(false).build());
        lllllllllllllllllllIIlIIllllIllI.randomDelayMax = lllllllllllllllllllIIlIIllllIllI.sgDelay.add(new IntSetting.Builder().name("random-delay-max").description("The maximum value for random delay.").defaultValue(4).min(0).sliderMax(20).build());
        lllllllllllllllllllIIlIIllllIllI.entityList = new ArrayList<Entity>();
    }

    private boolean attack(Entity lllllllllllllllllllIIlIIlllIIllI) {
        KillAura lllllllllllllllllllIIlIIlllIIlll;
        lllllllllllllllllllIIlIIlllIIlll.canAttack = false;
        lllllllllllllllllllIIlIIlllIIlll.target = lllllllllllllllllllIIlIIlllIIllI;
        if (Math.random() > lllllllllllllllllllIIlIIlllIIlll.hitChance.get() / 100.0) {
            return false;
        }
        if (lllllllllllllllllllIIlIIlllIIlll.rotationMode.get() == RotationMode.None || lllllllllllllllllllIIlIIlllIIlll.rotationMode.get() == RotationMode.Always) {
            lllllllllllllllllllIIlIIlllIIlll.hitEntity(lllllllllllllllllllIIlIIlllIIllI);
        } else {
            Rotations.rotate(Rotations.getYaw(lllllllllllllllllllIIlIIlllIIllI), Rotations.getPitch(lllllllllllllllllllIIlIIlllIIllI, lllllllllllllllllllIIlIIlllIIlll.rotationDirection.get()), () -> {
                KillAura lllllllllllllllllllIIlIIllIIIIIl;
                lllllllllllllllllllIIlIIllIIIIIl.hitEntity(lllllllllllllllllllIIlIIlllIIllI);
            });
        }
        lllllllllllllllllllIIlIIlllIIlll.canAttack = true;
        return true;
    }

    private void hitEntity(Entity lllllllllllllllllllIIlIIllIlllII) {
        KillAura lllllllllllllllllllIIlIIlllIIIII;
        int lllllllllllllllllllIIlIIllIllllI = InvUtils.findItemInHotbar(lllllllllllllllllllIIlIIllIIIlll -> {
            KillAura lllllllllllllllllllIIlIIllIIlIII;
            Item lllllllllllllllllllIIlIIllIIlIIl = lllllllllllllllllllIIlIIllIIIlll.getItem();
            return (lllllllllllllllllllIIlIIllIIlIIl instanceof SwordItem || lllllllllllllllllllIIlIIllIIlIIl instanceof AxeItem) && lllllllllllllllllllIIlIIllIIlIII.autoSwitch.get() != false;
        });
        if (lllllllllllllllllllIIlIIlllIIIII.autoSwitch.get().booleanValue() && lllllllllllllllllllIIlIIllIllllI != -1) {
            lllllllllllllllllllIIlIIlllIIIII.mc.player.inventory.selectedSlot = lllllllllllllllllllIIlIIllIllllI;
        }
        lllllllllllllllllllIIlIIlllIIIII.mc.interactionManager.attackEntity((PlayerEntity)lllllllllllllllllllIIlIIlllIIIII.mc.player, lllllllllllllllllllIIlIIllIlllII);
        lllllllllllllllllllIIlIIlllIIIII.mc.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean itemInHand() {
        KillAura lllllllllllllllllllIIlIIllIllIII;
        switch (lllllllllllllllllllIIlIIllIllIII.onlyWith.get()) {
            case Axe: {
                return lllllllllllllllllllIIlIIllIllIII.mc.player.getMainHandStack().getItem() instanceof AxeItem;
            }
            case Sword: {
                return lllllllllllllllllllIIlIIllIllIII.mc.player.getMainHandStack().getItem() instanceof SwordItem;
            }
            case Both: {
                return lllllllllllllllllllIIlIIllIllIII.mc.player.getMainHandStack().getItem() instanceof AxeItem || lllllllllllllllllllIIlIIllIllIII.mc.player.getMainHandStack().getItem() instanceof SwordItem;
            }
        }
        return true;
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* synthetic */ /* enum */ RotationMode OnHit;
        public static final /* synthetic */ /* enum */ RotationMode Always;
        private static final /* synthetic */ RotationMode[] $VALUES;
        public static final /* synthetic */ /* enum */ RotationMode None;

        static {
            Always = new RotationMode();
            OnHit = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }

        private RotationMode() {
            RotationMode lllllllllllllllllIllIllIIIIIlllI;
        }

        private static /* synthetic */ RotationMode[] $values() {
            return new RotationMode[]{Always, OnHit, None};
        }

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }

        public static RotationMode valueOf(String lllllllllllllllllIllIllIIIIlIIll) {
            return Enum.valueOf(RotationMode.class, lllllllllllllllllIllIllIIIIlIIll);
        }
    }

    public static final class OnlyWith
    extends Enum<OnlyWith> {
        public static final /* synthetic */ /* enum */ OnlyWith Both;
        private static final /* synthetic */ OnlyWith[] $VALUES;
        public static final /* synthetic */ /* enum */ OnlyWith Any;
        public static final /* synthetic */ /* enum */ OnlyWith Sword;
        public static final /* synthetic */ /* enum */ OnlyWith Axe;

        public static OnlyWith valueOf(String llIlIIIIlllIIII) {
            return Enum.valueOf(OnlyWith.class, llIlIIIIlllIIII);
        }

        private OnlyWith() {
            OnlyWith llIlIIIIllIlIll;
        }

        public static OnlyWith[] values() {
            return (OnlyWith[])$VALUES.clone();
        }

        static {
            Sword = new OnlyWith();
            Axe = new OnlyWith();
            Both = new OnlyWith();
            Any = new OnlyWith();
            $VALUES = OnlyWith.$values();
        }

        private static /* synthetic */ OnlyWith[] $values() {
            return new OnlyWith[]{Sword, Axe, Both, Any};
        }
    }
}

