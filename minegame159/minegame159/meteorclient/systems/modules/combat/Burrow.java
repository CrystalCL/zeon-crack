/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.Timer;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_2828;
import net.minecraft.class_2879;
import net.minecraft.class_3965;

public class Burrow
extends Module {
    private final class_2338.class_2339 blockPos;
    private final SettingGroup sgGeneral;
    private boolean shouldBurrow;
    private final Setting<Boolean> rotate;
    private int slot;
    private final Setting<Double> triggerHeight;
    private final Setting<Block> block;
    private int prevSlot;
    private final Setting<Double> timer;
    private final Setting<Boolean> onlyInHole;
    private final Setting<Boolean> automatic;
    private final Setting<Boolean> center;
    private final Setting<Boolean> instant;
    private final Setting<Double> rubberbandHeight;

    @Override
    public void onActivate() {
        if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515()).method_26207().method_15800()) {
            ChatUtils.moduleError(this, "Already burrowed, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!PlayerUtils.isInHole() && this.onlyInHole.get().booleanValue()) {
            ChatUtils.moduleError(this, "Not in a hole, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!this.checkHead()) {
            ChatUtils.moduleError(this, "Not enough headroom to burrow, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (!this.checkInventory()) {
            ChatUtils.moduleError(this, "No burrow block found, disabling.", new Object[0]);
            this.toggle();
            return;
        }
        this.blockPos.method_10101((class_2382)this.mc.field_1724.method_24515());
        Modules.get().get(Timer.class).setOverride(this.timer.get());
        this.shouldBurrow = false;
        if (this.automatic.get().booleanValue()) {
            if (this.instant.get().booleanValue()) {
                this.shouldBurrow = true;
            } else {
                this.mc.field_1724.method_6043();
            }
        } else {
            ChatUtils.moduleInfo(this, "Waiting for manual jump.", new Object[0]);
        }
    }

    public Burrow() {
        super(Categories.Combat, "burrow", "Place block inside your hole.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.block = this.sgGeneral.add(new EnumSetting.Builder().name("use-block").description("The block to use.").defaultValue(Block.EChest).build());
        this.instant = this.sgGeneral.add(new BoolSetting.Builder().name("jump-with-packet").description("Packet jump.").defaultValue(true).build());
        this.automatic = this.sgGeneral.add(new BoolSetting.Builder().name("automatic").description("Automatically burrows on activate rather than waiting for jump.").defaultValue(true).build());
        this.triggerHeight = this.sgGeneral.add(new DoubleSetting.Builder().name("trigger-height").description("How high you have to jump before a rubberband is triggered.").defaultValue(1.12).min(0.01).sliderMax(1.4).build());
        this.rubberbandHeight = this.sgGeneral.add(new DoubleSetting.Builder().name("rubberband-height").description("How far to attempt to cause rubberband.").defaultValue(12.0).sliderMin(-30.0).sliderMax(30.0).build());
        this.timer = this.sgGeneral.add(new DoubleSetting.Builder().name("timer").description("Timer override.").defaultValue(1.0).min(0.01).sliderMax(10.0).build());
        this.onlyInHole = this.sgGeneral.add(new BoolSetting.Builder().name("only-in-holes").description("Burrowing only in holes.").defaultValue(false).build());
        this.center = this.sgGeneral.add(new BoolSetting.Builder().name("CenterTP").description("Teleport character to the center of the block.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Send packets for rotate.").defaultValue(true).build());
        this.blockPos = new class_2338.class_2339();
    }

    private boolean checkInventory() {
        this.prevSlot = this.mc.field_1724.field_7514.field_7545;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$Burrow$Block[this.block.get().ordinal()]) {
            case 1: {
                this.slot = InvUtils.findItemInHotbar(class_1802.field_8281);
                break;
            }
            case 2: {
                this.slot = InvUtils.findItemInHotbar(class_1802.field_8466);
            }
        }
        return this.slot != -1;
    }

    @EventHandler
    private void onKey(KeyEvent keyEvent) {
        if (this.instant.get().booleanValue() && !this.shouldBurrow) {
            if (keyEvent.action == KeyAction.Press && this.mc.field_1690.field_1903.method_1417(keyEvent.key, 0)) {
                this.shouldBurrow = true;
            }
            this.blockPos.method_10101((class_2382)this.mc.field_1724.method_24515());
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1.0);
    }

    private boolean checkHead() {
        class_2680 class_26802 = this.mc.field_1687.method_8320((class_2338)this.blockPos.method_10102(this.mc.field_1724.method_23317() + 0.3, this.mc.field_1724.method_23318() + 2.3, this.mc.field_1724.method_23321() + 0.3));
        class_2680 class_26803 = this.mc.field_1687.method_8320((class_2338)this.blockPos.method_10102(this.mc.field_1724.method_23317() + 0.3, this.mc.field_1724.method_23318() + 2.3, this.mc.field_1724.method_23321() - 0.3));
        class_2680 class_26804 = this.mc.field_1687.method_8320((class_2338)this.blockPos.method_10102(this.mc.field_1724.method_23317() - 0.3, this.mc.field_1724.method_23318() + 2.3, this.mc.field_1724.method_23321() - 0.3));
        class_2680 class_26805 = this.mc.field_1687.method_8320((class_2338)this.blockPos.method_10102(this.mc.field_1724.method_23317() - 0.3, this.mc.field_1724.method_23318() + 2.3, this.mc.field_1724.method_23321() + 0.3));
        boolean bl = class_26802.method_26207().method_15800();
        boolean bl2 = class_26803.method_26207().method_15800();
        boolean bl3 = class_26804.method_26207().method_15800();
        boolean bl4 = class_26805.method_26207().method_15800();
        return bl & bl2 & bl3 & bl4;
    }

    private void placeBlock() {
        this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(Utils.vec3d((class_2338)this.blockPos), class_2350.field_11036, (class_2338)this.blockPos, false));
        this.mc.method_1562().method_2883((class_2596)new class_2879(class_1268.field_5808));
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (!this.instant.get().booleanValue()) {
            boolean bl = this.shouldBurrow = this.mc.field_1724.method_23318() > (double)this.blockPos.method_10264() + this.triggerHeight.get();
        }
        if (!this.shouldBurrow && this.instant.get().booleanValue()) {
            this.blockPos.method_10101((class_2382)this.mc.field_1724.method_24515());
        }
        if (this.shouldBurrow) {
            if (!this.checkInventory()) {
                return;
            }
            if (this.center.get().booleanValue()) {
                PlayerUtils.centerPlayer();
            }
            if (this.instant.get().booleanValue()) {
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + 0.4, this.mc.field_1724.method_23321(), true));
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + 0.75, this.mc.field_1724.method_23321(), true));
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + 1.0, this.mc.field_1724.method_23321(), true));
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + 1.15, this.mc.field_1724.method_23321(), true));
            }
            this.mc.field_1724.field_7514.field_7545 = this.slot;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw((class_2338)this.blockPos), Rotations.getPitch((class_2338)this.blockPos), this::placeBlock);
            } else {
                this.placeBlock();
            }
            this.mc.field_1724.field_7514.field_7545 = this.prevSlot;
            if (this.instant.get().booleanValue()) {
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + this.rubberbandHeight.get(), this.mc.field_1724.method_23321(), false));
            } else {
                this.mc.field_1724.method_18799(this.mc.field_1724.method_18798().method_1031(0.0, this.rubberbandHeight.get().doubleValue(), 0.0));
            }
            this.toggle();
        }
    }

    public static final class Block
    extends Enum<Block> {
        private static final Block[] $VALUES;
        public static final /* enum */ Block Obsidian;
        public static final /* enum */ Block EChest;

        public static Block[] values() {
            return (Block[])$VALUES.clone();
        }

        static {
            EChest = new Block();
            Obsidian = new Block();
            $VALUES = Block.$values();
        }

        private static Block[] $values() {
            return new Block[]{EChest, Obsidian};
        }

        public static Block valueOf(String string) {
            return Enum.valueOf(Block.class, string);
        }
    }
}

