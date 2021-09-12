/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_471;

public class SelfAnvil
extends Module {
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;

    public SelfAnvil() {
        super(Categories.Combat, "self-anvil", "Automatically places an anvil on you to prevent other players from going into your hole.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing the anvil.").defaultValue(true).build());
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        if (openScreenEvent.screen instanceof class_471) {
            openScreenEvent.cancel();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = this.findSlot();
        if (n == -1) {
            return;
        }
        class_2338 class_23382 = this.mc.field_1724.method_24515().method_10069(0, 2, 0);
        if (BlockUtils.place(class_23382, class_1268.field_5808, n, this.rotate.get(), 0, true)) {
            this.toggle();
        }
    }

    private int findSlot() {
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            if (class_17922 != class_1802.field_8782 && class_17922 != class_1802.field_8750 && class_17922 != class_1802.field_8427) continue;
            return i;
        }
        return -1;
    }
}

