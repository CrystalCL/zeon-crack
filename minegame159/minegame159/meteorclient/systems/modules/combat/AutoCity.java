/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import minegame159.meteorclient.mixininterface.IClientPlayerInteractionManager;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.CityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2846;
import net.minecraft.class_2885;
import net.minecraft.class_3532;
import net.minecraft.class_3965;

public class AutoCity
extends Module {
    private final Setting<Boolean> support;
    private final Setting<Double> range;
    private final Setting<Boolean> crystal;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;
    private class_1657 target;
    private final Setting<Boolean> chatInfo;

    private void lambda$onActivate$0(class_2338 class_23382) {
        this.mine(class_23382);
    }

    private void mine(class_2338 class_23382) {
        this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12968, class_23382, class_2350.field_11036));
        this.mc.field_1724.method_6104(class_1268.field_5808);
        this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12973, class_23382, class_2350.field_11036));
    }

    @Override
    public void onActivate() {
        this.target = CityUtils.getPlayerTarget(this.range.get());
        class_2338 class_23382 = CityUtils.getTargetBlock(this.target);
        if (this.target == null || class_23382 == null) {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleError(this, "No target block found... disabling.", new Object[0]);
            }
        } else {
            if (this.chatInfo.get().booleanValue()) {
                ChatUtils.moduleInfo(this, String.valueOf(new StringBuilder().append("Attempting to city ").append(this.target.method_7334().getName())), new Object[0]);
            }
            if (class_3532.method_15368((double)this.mc.field_1724.method_5649((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260())) > this.mc.field_1761.method_2904()) {
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(this, "Target block out of reach... disabling.", new Object[0]);
                }
                this.toggle();
                return;
            }
            int n = InvUtils.findItemInHotbar(class_1802.field_22024);
            if (n == -1) {
                n = InvUtils.findItemInHotbar(class_1802.field_8377);
            }
            if (this.mc.field_1724.field_7503.field_7477) {
                n = this.mc.field_1724.field_7514.field_7545;
            }
            if (n == -1) {
                if (this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleError(this, "No pick found... disabling.", new Object[0]);
                }
                this.toggle();
                return;
            }
            if (this.support.get().booleanValue()) {
                int n2 = InvUtils.findItemInHotbar(class_1802.field_8281);
                class_2338 class_23383 = class_23382.method_10087(1);
                if (!BlockUtils.canPlace(class_23383) && this.mc.field_1687.method_8320(class_23383).method_26204() != class_2246.field_10540 && this.mc.field_1687.method_8320(class_23383).method_26204() != class_2246.field_9987 && this.chatInfo.get().booleanValue()) {
                    ChatUtils.moduleWarning(this, "Couldn't place support block, mining anyway.", new Object[0]);
                } else if (n2 == -1) {
                    if (this.chatInfo.get().booleanValue()) {
                        ChatUtils.moduleWarning(this, "No obsidian found for support, mining anyway.", new Object[0]);
                    }
                } else {
                    BlockUtils.place(class_23383, class_1268.field_5808, n2, this.rotate.get(), 0, true);
                }
            }
            this.mc.field_1724.field_7514.field_7545 = n;
            if (this.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(class_23382), Rotations.getPitch(class_23382), () -> this.lambda$onActivate$0(class_23382));
            } else {
                this.mine(class_23382);
            }
            if (this.crystal.get().booleanValue()) {
                if (!BlockUtils.canPlace(class_23382, true)) {
                    return;
                }
                class_1268 class_12682 = InvUtils.getHand(class_1802.field_8301);
                if (class_12682 == class_1268.field_5808) {
                    int n3 = InvUtils.findItemInHotbar(class_1802.field_8301);
                    int n4 = this.mc.field_1724.field_7514.field_7545;
                    this.mc.field_1724.field_7514.field_7545 = n3;
                    ((IClientPlayerInteractionManager)this.mc.field_1761).syncSelectedSlot2();
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(class_12682, new class_3965(this.mc.field_1724.method_19538(), class_2350.field_11036, class_23382, false)));
                    this.mc.field_1724.field_7514.field_7545 = n4;
                } else if (class_12682 == class_1268.field_5810) {
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(class_12682, new class_3965(this.mc.field_1724.method_19538(), class_2350.field_11036, class_23382, false)));
                }
            }
        }
        this.toggle();
    }

    public AutoCity() {
        super(Categories.Combat, "auto-city", "Automatically cities a target by mining the nearest obsidian next to them.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum range a city-able block will be found.").defaultValue(5.0).min(0.0).sliderMax(20.0).build());
        this.support = this.sgGeneral.add(new BoolSetting.Builder().name("support").description("If there is no block below a city block it will place one before mining.").defaultValue(true).build());
        this.chatInfo = this.sgGeneral.add(new BoolSetting.Builder().name("chat-info").description("Sends a client-side message if you city a player.").defaultValue(true).build());
        this.crystal = this.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places a crystal above the city block.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates you towards the city block.").defaultValue(true).build());
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.method_5820();
        }
        return null;
    }
}

