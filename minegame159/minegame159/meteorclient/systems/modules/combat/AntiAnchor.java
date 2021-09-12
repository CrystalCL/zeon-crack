/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2482;
import net.minecraft.class_2596;
import net.minecraft.class_2848;

public class AntiAnchor
extends Module {
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;

    public AntiAnchor() {
        super(Categories.Combat, "anti-anchor", "Automatically prevents Anchor Aura by placing a slab on your head.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing a slab above you.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n;
        if (this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(0, 2, 0)).method_26204() == class_2246.field_23152 && this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(0, 1, 0)).method_26204() == class_2246.field_10124 && (n = InvUtils.findItemInHotbar(AntiAnchor::lambda$onTick$0)) != -1) {
            this.mc.field_1724.field_3913.field_3903 = true;
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12979));
            this.rotateCheck(n);
        }
    }

    private void placeBlock(int n) {
        BlockUtils.place(this.mc.field_1724.method_24515().method_10069(0, 1, 0), class_1268.field_5808, n, this.rotate.get(), 0, false);
    }

    private void lambda$rotateCheck$1(int n) {
        this.placeBlock(n);
    }

    private static boolean lambda$onTick$0(class_1799 class_17992) {
        return class_2248.method_9503((class_1792)class_17992.method_7909()) instanceof class_2482;
    }

    private void rotateCheck(int n) {
        if (this.rotate.get().booleanValue()) {
            Rotations.rotate(this.mc.field_1724.field_6031, -90.0, 15, () -> this.lambda$rotateCheck$1(n));
        } else {
            this.placeBlock(n);
        }
    }
}

