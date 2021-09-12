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
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1429;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1829;

public class KillAura
extends Module {
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private final List<class_1297> entityList;
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
    private class_1297 target;
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

    private boolean lambda$onTick$0(class_1297 class_12972) {
        if (class_12972 == this.mc.field_1724 || class_12972 == this.mc.field_1719) {
            return false;
        }
        if (class_12972 instanceof class_1309 && ((class_1309)class_12972).method_29504() || !class_12972.method_5805()) {
            return false;
        }
        if ((double)class_12972.method_5739((class_1297)this.mc.field_1724) > this.range.get()) {
            return false;
        }
        if (!this.entities.get().getBoolean((Object)class_12972.method_5864())) {
            return false;
        }
        if (!this.nametagged.get().booleanValue() && class_12972.method_16914()) {
            return false;
        }
        if (!this.ignoreWalls.get().booleanValue() && !PlayerUtils.canSeeEntity(class_12972)) {
            return false;
        }
        if (class_12972 instanceof class_1657) {
            if (((class_1657)class_12972).method_7337()) {
                return false;
            }
            if (!this.friends.get().booleanValue() && !Friends.get().attack((class_1657)class_12972)) {
                return false;
            }
        }
        return !(class_12972 instanceof class_1429) || this.babies.get() != false || !((class_1429)class_12972).method_6109();
    }

    private boolean lambda$hitEntity$2(class_1799 class_17992) {
        class_1792 class_17922 = class_17992.method_7909();
        return (class_17922 instanceof class_1829 || class_17922 instanceof class_1743) && this.autoSwitch.get() != false;
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
                return this.mc.field_1724.method_6047().method_7909() instanceof class_1743;
            }
            case 2: {
                return this.mc.field_1724.method_6047().method_7909() instanceof class_1829;
            }
            case 3: {
                return this.mc.field_1724.method_6047().method_7909() instanceof class_1743 || this.mc.field_1724.method_6047().method_7909() instanceof class_1829;
            }
        }
        return true;
    }

    private boolean attack(class_1297 class_12972) {
        this.canAttack = false;
        this.target = class_12972;
        if (Math.random() > this.hitChance.get() / 100.0) {
            return false;
        }
        if (this.rotationMode.get() == RotationMode.None || this.rotationMode.get() == RotationMode.Always) {
            this.hitEntity(class_12972);
        } else {
            Rotations.rotate(Rotations.getYaw(class_12972), Rotations.getPitch(class_12972, this.rotationDirection.get()), () -> this.lambda$attack$1(class_12972));
        }
        this.canAttack = true;
        return true;
    }

    @Override
    public String getInfoString() {
        if (!this.entityList.isEmpty()) {
            class_1297 class_12972 = this.entityList.get(0);
            if (class_12972 instanceof class_1657) {
                return class_12972.method_5820();
            }
            return class_12972.method_5864().method_5897().getString();
        }
        return null;
    }

    private void lambda$attack$1(class_1297 class_12972) {
        this.hitEntity(class_12972);
    }

    public class_1297 getTarget() {
        return this.target;
    }

    public KillAura() {
        super(Categories.Combat, "kill-aura", "Attacks specified entities around you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRotations = this.settings.createGroup("Rotations");
        this.sgDelay = this.settings.createGroup("Delay");
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range the entity can be to attack it.").defaultValue(4.0).min(0.0).max(6.0).sliderMax(6.0).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to attack.").defaultValue((Object2BooleanMap<class_1299<?>>)new Object2BooleanOpenHashMap(0)).onlyAttackable().build());
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
        this.entityList = new ArrayList<class_1297>();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.entityList.clear();
        if (this.mc.field_1724.method_29504() || !this.mc.field_1724.method_5805() || !this.itemInHand()) {
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
        if (this.smartDelay.get().booleanValue() && this.mc.field_1724.method_7261(0.5f) < 1.0f) {
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
        for (class_1297 class_12972 : this.entityList) {
            if (!this.attack(class_12972) || !this.canAttack) continue;
            this.hitEntity(class_12972);
        }
    }

    private void hitEntity(class_1297 class_12972) {
        int n = InvUtils.findItemInHotbar(this::lambda$hitEntity$2);
        if (this.autoSwitch.get().booleanValue() && n != -1) {
            this.mc.field_1724.field_7514.field_7545 = n;
        }
        this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, class_12972);
        this.mc.field_1724.method_6104(class_1268.field_5808);
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

