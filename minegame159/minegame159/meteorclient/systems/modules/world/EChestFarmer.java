/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.AutoTool;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_2680;
import net.minecraft.class_3965;

public class EChestFarmer
extends Module {
    private static final class_2680 ENDER_CHEST = class_2246.field_10443.method_9564();
    private int numLeft;
    private final Setting<Boolean> disableOnAmount;
    private final SettingGroup sgGeneral;
    private final Setting<Integer> amount;
    private final Setting<Integer> lowerAmount;
    private boolean stop;

    public EChestFarmer() {
        super(Categories.World, "EChest-farmer", "Places and mines Ender Chests where you're looking.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.amount = this.sgGeneral.add(new IntSetting.Builder().name("target-amount").description("The amount of obsidian to farm.").defaultValue(64).min(8).sliderMax(64).max(512).build());
        this.lowerAmount = this.sgGeneral.add(new IntSetting.Builder().name("lower-amount").description("The specified amount before this module toggles on again.").defaultValue(8).min(0).max(64).sliderMax(32).build());
        this.disableOnAmount = this.sgGeneral.add(new BoolSetting.Builder().name("disable-on-completion").description("Whether or not to disable when you reach your desired amount of stacks of obsidian.").defaultValue(true).build());
        this.stop = false;
        this.numLeft = Math.floorDiv(this.amount.get(), 8);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.lowerAmount.get() < InvUtils.findItemWithCount((class_1792)class_1802.field_8281).count) {
            this.stop = false;
        }
        if (this.stop && !this.disableOnAmount.get().booleanValue()) {
            this.stop = false;
            this.numLeft = Math.floorDiv(this.amount.get(), 8);
            return;
        }
        if (this.stop && this.disableOnAmount.get().booleanValue()) {
            this.toggle();
            return;
        }
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(class_1802.field_8466);
        int n = -1;
        if (findItemResult.count != 0 && findItemResult.slot < 9 && findItemResult.slot != -1) {
            for (int i = 0; i < 9; ++i) {
                if (!Modules.get().get(AutoTool.class).isEffectiveOn(this.mc.field_1724.field_7514.method_5438(i).method_7909(), ENDER_CHEST) || class_1890.method_8225((class_1887)class_1893.field_9099, (class_1799)this.mc.field_1724.field_7514.method_5438(i)) != 0) continue;
                n = i;
                if (3 >= 0) continue;
                return;
            }
            if (n != -1 && findItemResult.slot != -1 && findItemResult.slot < 9) {
                if (this.mc.field_1765.method_17783() != class_239.class_240.field_1332) {
                    return;
                }
                class_2338 class_23382 = ((class_3965)this.mc.field_1765).method_17777();
                if (this.mc.field_1687.method_8320(class_23382).method_26204() == class_2246.field_10443) {
                    if (this.mc.field_1724.field_7514.field_7545 != n) {
                        this.mc.field_1724.field_7514.field_7545 = n;
                    }
                    this.mc.field_1761.method_2902(class_23382, class_2350.field_11036);
                    --this.numLeft;
                    if (this.numLeft == 0) {
                        this.stop = true;
                    }
                } else if (this.mc.field_1687.method_8320(class_23382.method_10084()).method_26204() == class_2246.field_10124) {
                    BlockUtils.place(class_23382.method_10084(), class_1268.field_5808, findItemResult.slot, false, 0, true);
                }
            }
        }
    }
}

