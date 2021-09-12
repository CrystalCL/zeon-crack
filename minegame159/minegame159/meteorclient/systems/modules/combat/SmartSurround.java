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
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_3959;

public class SmartSurround
extends Module {
    private int slot;
    private int rPosX;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> onlyObsidian;
    private class_1297 crystal;
    private final Setting<Double> minDamage;
    private final SettingGroup sgGeneral;
    private int rPosZ;
    private int oldSlot;

    @EventHandler
    private void onSpawn(EntityAddedEvent entityAddedEvent) {
        this.crystal = entityAddedEvent.entity;
        if (entityAddedEvent.entity.method_5864() == class_1299.field_6110 && DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, entityAddedEvent.entity.method_19538()) > this.minDamage.get()) {
            this.slot = this.findObiInHotbar();
            if (this.slot == -1 && this.onlyObsidian.get().booleanValue()) {
                ChatUtils.moduleError(this, "No obsidian in hotbar... disabling.", new Object[0]);
                return;
            }
            for (int i = 0; i < 9; ++i) {
                class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
                if (!(class_17922 instanceof class_1747)) continue;
                this.mc.field_1724.field_7514.field_7545 = this.slot = i;
                break;
            }
            if (this.slot == -1) {
                ChatUtils.moduleError(this, "No blocks in hotbar... disabling.", new Object[0]);
                return;
            }
            this.rPosX = this.mc.field_1724.method_24515().method_10263() - entityAddedEvent.entity.method_24515().method_10263();
            this.rPosZ = this.mc.field_1724.method_24515().method_10260() - entityAddedEvent.entity.method_24515().method_10260();
        }
    }

    private int findObiInHotbar() {
        this.oldSlot = this.mc.field_1724.field_7514.field_7545;
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            if (class_17922 != class_1802.field_8281 && class_17922 != class_1802.field_22421) continue;
            this.mc.field_1724.field_7514.field_7545 = n = i;
            break;
        }
        return n;
    }

    private void placeObi(int n, int n2, class_1297 class_12972) {
        class_2338 class_23382 = class_12972.method_24515().method_10069(n, -1, n2);
        BlockUtils.place(class_23382, class_1268.field_5808, this.slot, this.rotate.get(), 100, true);
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
            if (this.mc.field_1687.method_17742(new class_3959(this.mc.field_1724.method_19538(), this.crystal.method_19538(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724)).method_17783() != class_239.class_240.field_1333) {
                this.slot = -1;
                this.mc.field_1724.field_7514.field_7545 = this.oldSlot;
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

