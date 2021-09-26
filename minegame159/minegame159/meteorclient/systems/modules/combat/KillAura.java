/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class KillAura
extends Module {
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final List<Entity> entityList;
    private final Setting<RotationMode> rotationMode;
    private final Setting<Boolean> randomDelayEnabled;
    private boolean canAttack;
    private final Setting<Boolean> smartDelay;
    private final Setting<Boolean> nametagged;
    private final Setting<Integer> randomDelayMax;
    private final Setting<Boolean> friends;
    private boolean wasPathing;
    private int randomDelayTimer;
    private final Setting<Boolean> targetMultiple;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> babies;
    private Entity target;
    private final Setting<Boolean> pauseOnCombat;
    private final Setting<Boolean> ignoreWalls;
    private final Setting<Double> range;
    private final Setting<Boolean> autoSwitch;
    private final Setting<OnlyWith> onlyWith;
    private final Setting<SortPriority> priority;
    private int hitDelayTimer;
    private final Setting<Double> hitChance;
    private final SettingGroup sgRotations;
    private final SettingGroup sgDelay;
    private final Setting<Target> rotationDirection;
    private final Setting<Integer> hitDelay;

    private boolean lambda$onTick$0(Entity Entity2) {
        if (Entity2 == this.mc.player || Entity2 == this.mc.cameraEntity) {
            return false;
        }
        if (Entity2 instanceof LivingEntity && ((LivingEntity)Entity2).isDead() || !Entity2.isAlive()) {
            return false;
        }
        if ((double)Entity2.distanceTo((Entity)this.mc.player) > this.range.get()) {
            return false;
        }
        if (!this.entities.get().getBoolean((Object)Entity2.getType())) {
            return false;
        }
        if (!this.nametagged.get().booleanValue() && Entity2.hasCustomName()) {
            return false;
        }
        if (!this.ignoreWalls.get().booleanValue() && !PlayerUtils.canSeeEntity(Entity2)) {
            return false;
        }
        if (Entity2 instanceof PlayerEntity) {
            if (((PlayerEntity)Entity2).isCreative()) {
                return false;
            }
            if (!this.friends.get().booleanValue() && !Friends.get().attack((PlayerEntity)Entity2)) {
                return false;
            }
        }
        return !(Entity2 instanceof AnimalEntity) || this.babies.get() != false || !((AnimalEntity)Entity2).isBaby();
    }

    private boolean lambda$hitEntity$2(ItemStack ItemStack2) {
        Item Item2 = ItemStack2.getItem();
        return (Item2 instanceof SwordItem || Item2 instanceof AxeItem) && this.autoSwitch.get() != false;
    }

    @Override
    public void onDeactivate() {
        this.hitDelayTimer = 0;
        this.randomDelayTimer = 0;
        this.entityList.clear();
        this.target = null;
    }

    private boolean itemInHand() {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$KillAura$OnlyWith[this.onlyWith.get().ordinal()]) {
            case 1: {
                return this.mc.player.getMainHandStack().getItem() instanceof AxeItem;
            }
            case 2: {
                return this.mc.player.getMainHandStack().getItem() instanceof SwordItem;
            }
            case 3: {
                return this.mc.player.getMainHandStack().getItem() instanceof AxeItem || this.mc.player.getMainHandStack().getItem() instanceof SwordItem;
            }
        }
        return true;
    }

    private boolean attack(Entity Entity2) {
        this.canAttack = false;
        this.target = Entity2;
        if (Math.random() > this.hitChance.get() / 100.0) {
            return false;
        }
        if (this.rotationMode.get() == RotationMode.None || this.rotationMode.get() == RotationMode.Always) {
            this.hitEntity(Entity2);
        } else {
            Rotations.rotate(Rotations.getYaw(Entity2), Rotations.getPitch(Entity2, this.rotationDirection.get()), () -> this.lambda$attack$1(Entity2));
        }
        this.canAttack = true;
        return true;
    }

    @Override
    public String getInfoString() {
        if (!this.entityList.isEmpty()) {
            Entity Entity2 = this.entityList.get(0);
            if (Entity2 instanceof PlayerEntity) {
                return Entity2.getEntityName();
            }
            return Entity2.getType().getName().getString();
        }
        return null;
    }

    private void lambda$attack$1(Entity Entity2) {
        this.hitEntity(Entity2);
    }

    public Entity getTarget() {
        return this.target;
    }

    public KillAura() {
        super(Categories.Combat, "kill-aura", "Attacks specified entities around you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRotations = this.settings.createGroup("Rotations");
        this.sgDelay = this.settings.createGroup("Delay");
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range the entity can be to attack it.").defaultValue(4.0).min(0.0).max(6.0).sliderMax(6.0).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to attack.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).onlyAttackable().build());
        this.priority = this.sgGeneral.add(new EnumSetting.Builder().name("priority").description("What type of entities to target.").defaultValue(SortPriority.LowestHealth).build());
        this.onlyWith = this.sgGeneral.add(new EnumSetting.Builder().name("only-with").description("Only attacks an entity when a specified item is in your hand.").defaultValue(OnlyWith.Any).build());
        this.autoSwitch = this.sgGeneral.add(new BoolSetting.Builder().name("auto-switch").description("Switches to an axe or a sword when attacking the target.").defaultValue(false).build());
        this.ignoreWalls = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-walls").description("Whether or not to attack the entity through a wall.").defaultValue(true).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Whether or not to attack friends. Useful if you select players selected.").defaultValue(false).build());
        this.babies = this.sgGeneral.add(new BoolSetting.Builder().name("babies").description("Whether or not to attack baby variants of the entity.").defaultValue(true).build());
        this.nametagged = this.sgGeneral.add(new BoolSetting.Builder().name("nametagged").description("Whether or not to attack mobs with a name tag.").defaultValue(false).build());
        this.hitChance = this.sgGeneral.add(new DoubleSetting.Builder().name("hit-chance").description("The probability of your hits landing.").defaultValue(100.0).min(0.0).max(100.0).sliderMax(100.0).build());
        this.pauseOnCombat = this.sgGeneral.add(new BoolSetting.Builder().name("pause-on-combat").description("Freezes Baritone temporarily until you are finished attacking the entity.").defaultValue(false).build());
        this.targetMultiple = this.sgGeneral.add(new BoolSetting.Builder().name("target-multiple").description("Target multiple entities at once").defaultValue(false).build());
        this.rotationMode = this.sgRotations.add(new EnumSetting.Builder().name("rotation-mode").description("Determines when you should rotate towards the target.").defaultValue(RotationMode.OnHit).build());
        this.rotationDirection = this.sgRotations.add(new EnumSetting.Builder().name("rotation-direction").description("The direction to use for rotating towards the enemy.").defaultValue(Target.Head).build());
        this.smartDelay = this.sgDelay.add(new BoolSetting.Builder().name("smart-delay").description("Smart delay.").defaultValue(true).build());
        this.hitDelay = this.sgDelay.add(new IntSetting.Builder().name("hit-delay").description("How fast you hit the entity in ticks.").defaultValue(0).min(0).sliderMax(60).build());
        this.randomDelayEnabled = this.sgDelay.add(new BoolSetting.Builder().name("random-delay-enabled").description("Adds a random delay between hits to attempt to bypass anti-cheats.").defaultValue(false).build());
        this.randomDelayMax = this.sgDelay.add(new IntSetting.Builder().name("random-delay-max").description("The maximum value for random delay.").defaultValue(4).min(0).sliderMax(20).build());
        this.entityList = new ArrayList<Entity>();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.entityList.clear();
        if (this.mc.player.isDead() || !this.mc.player.isAlive() || !this.itemInHand()) {
            return;
        }
        EntityUtils.getList(this::lambda$onTick$0, this.priority.get(), this.entityList);
        if (!this.targetMultiple.get().booleanValue() && !this.entityList.isEmpty()) {
            this.entityList.subList(1, this.entityList.size()).clear();
        }
        if (this.entityList.isEmpty()) {
            if (this.wasPathing) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
                this.wasPathing = false;
            }
            return;
        }
        if (this.pauseOnCombat.get().booleanValue() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !this.wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            this.wasPathing = true;
        }
        if (this.rotationMode.get() == RotationMode.Always && this.target != null) {
            Rotations.rotate(Rotations.getYaw(this.target), Rotations.getPitch(this.target, this.rotationDirection.get()));
        }
        if (this.smartDelay.get().booleanValue() && this.mc.player.getAttackCooldownProgress(0.5f) < 1.0f) {
            return;
        }
        if (this.hitDelayTimer >= 0) {
            --this.hitDelayTimer;
            return;
        }
        this.hitDelayTimer = this.hitDelay.get();
        if (this.randomDelayEnabled.get().booleanValue()) {
            if (this.randomDelayTimer > 0) {
                --this.randomDelayTimer;
                return;
            }
            this.randomDelayTimer = (int)Math.round(Math.random() * (double)this.randomDelayMax.get().intValue());
        }
        for (Entity Entity2 : this.entityList) {
            if (!this.attack(Entity2) || !this.canAttack) continue;
            this.hitEntity(Entity2);
        }
    }

    private void hitEntity(Entity Entity2) {
        int n = InvUtils.findItemInHotbar(this::lambda$hitEntity$2);
        if (this.autoSwitch.get().booleanValue() && n != -1) {
            this.mc.player.inventory.selectedSlot = n;
        }
        this.mc.interactionManager.attackEntity((PlayerEntity)this.mc.player, Entity2);
        this.mc.player.swingHand(Hand.MAIN_HAND);
    }

    public static final class OnlyWith
    extends Enum<OnlyWith> {
        public static final /* enum */ OnlyWith Sword = new OnlyWith();
        public static final /* enum */ OnlyWith Both;
        public static final /* enum */ OnlyWith Any;
        public static final /* enum */ OnlyWith Axe;
        private static final OnlyWith[] $VALUES;

        static {
            Axe = new OnlyWith();
            Both = new OnlyWith();
            Any = new OnlyWith();
            $VALUES = OnlyWith.$values();
        }

        private static OnlyWith[] $values() {
            return new OnlyWith[]{Sword, Axe, Both, Any};
        }

        public static OnlyWith[] values() {
            return (OnlyWith[])$VALUES.clone();
        }

        public static OnlyWith valueOf(String string) {
            return Enum.valueOf(OnlyWith.class, string);
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* enum */ RotationMode Always = new RotationMode();
        private static final RotationMode[] $VALUES;
        public static final /* enum */ RotationMode None;
        public static final /* enum */ RotationMode OnHit;

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }

        public static RotationMode valueOf(String string) {
            return Enum.valueOf(RotationMode.class, string);
        }

        static {
            OnHit = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }

        private static RotationMode[] $values() {
            return new RotationMode[]{Always, OnHit, None};
        }
    }
}

