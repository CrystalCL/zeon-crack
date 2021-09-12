/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.ClipAtLedgeEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_265;
import net.minecraft.class_2680;

public class Scaffold
extends Module {
    private int slot;
    private boolean lastWasSneaking;
    private final Setting<Boolean> renderSwing;
    private double lastSneakingY;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> fastTower;
    private final Setting<Boolean> safeWalk;
    private final class_2338.class_2339 blockPos;
    private class_2680 blockState;
    private final Setting<List<class_2248>> blackList;
    private final Setting<Integer> radius;
    private final Setting<Boolean> selfToggle;
    private final Setting<Boolean> rotate;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.fastTower.get().booleanValue() && !this.mc.field_1687.method_8320(this.setPos(0, -1, 0)).method_26207().method_15800() && this.mc.field_1690.field_1903.method_1434() && this.findSlot(this.mc.field_1687.method_8320(this.setPos(0, -1, 0))) != -1 && this.mc.field_1724.field_6212 == 0.0f && this.mc.field_1724.field_6250 == 0.0f) {
            this.mc.field_1724.method_6043();
        }
        this.blockState = this.mc.field_1687.method_8320(this.setPos(0, -1, 0));
        if (!this.blockState.method_26207().method_15800()) {
            return;
        }
        boolean bl = this.lastWasSneaking;
        this.lastWasSneaking = this.mc.field_1724.field_3913.field_3903;
        if (this.mc.field_1724.field_3913.field_3903) {
            if (!bl) {
                this.lastSneakingY = this.mc.field_1724.method_23318();
            }
            if (this.lastSneakingY - this.mc.field_1724.method_23318() < 0.1) {
                return;
            }
        }
        this.slot = this.findSlot(this.blockState);
        if (this.slot == -1) {
            return;
        }
        this.place(this.mc.field_1724.method_24515().method_10074(), this.slot);
        if (this.mc.field_1724.field_3913.field_3903) {
            this.lastWasSneaking = false;
        }
        for (int i = 1; i < this.radius.get(); ++i) {
            int n;
            int n2 = 1 + (i - 1) * 2;
            int n3 = n2 / 2;
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(n - n3, -1, i), this.slot);
                if (3 < 4) continue;
                return;
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(n - n3, -1, -i), this.slot);
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(i, -1, n - n3), this.slot);
                if (null == null) continue;
                return;
            }
            for (n = 0; n < n2; ++n) {
                if (!this.findBlock()) {
                    return;
                }
                this.place(this.setPos(-i, -1, n - n3), this.slot);
                if (-3 < 0) continue;
                return;
            }
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(-i, -1, i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(i, -1, i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(-i, -1, -i), this.slot);
            if (!this.findBlock()) {
                return;
            }
            this.place(this.setPos(i, -1, -i), this.slot);
            if (3 > 0) continue;
            return;
        }
    }

    @EventHandler
    private void onClipAtLedge(ClipAtLedgeEvent clipAtLedgeEvent) {
        if (this.mc.field_1724.field_3913.field_3903) {
            clipAtLedgeEvent.setClip(false);
            return;
        }
        if (this.safeWalk.get().booleanValue()) {
            clipAtLedgeEvent.setClip(true);
        }
    }

    @Override
    public void onActivate() {
        this.lastWasSneaking = this.mc.field_1724.field_3913.field_3903;
        if (this.lastWasSneaking) {
            this.lastSneakingY = this.mc.field_1724.method_23318();
        }
    }

    private class_2338 setPos(int n, int n2, int n3) {
        this.blockPos.method_10102(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321());
        if (n != 0) {
            this.blockPos.method_20787(this.blockPos.method_10263() + n);
        }
        if (n2 != 0) {
            this.blockPos.method_10099(this.blockPos.method_10264() + n2);
        }
        if (n3 != 0) {
            this.blockPos.method_20788(this.blockPos.method_10260() + n3);
        }
        return this.blockPos;
    }

    public Scaffold() {
        super(Categories.Movement, "scaffold", "Automatically places blocks under you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.safeWalk = this.sgGeneral.add(new BoolSetting.Builder().name("safe-walk").description("Whether or not to toggle Safe Walk when using Scaffold.").defaultValue(true).build());
        this.fastTower = this.sgGeneral.add(new BoolSetting.Builder().name("fast-tower").description("Whether or not to scaffold upwards faster.").defaultValue(false).build());
        this.radius = this.sgGeneral.add(new IntSetting.Builder().name("radius").description("The radius of your scaffold.").defaultValue(1).min(1).sliderMin(1).sliderMax(7).build());
        this.blackList = this.sgGeneral.add(new BlockListSetting.Builder().name("blacklist").description("Blacklists certain blocks from being used to scaffold.").defaultValue(new ArrayList<class_2248>()).build());
        this.selfToggle = this.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles Scaffold when you run out of blocks.").defaultValue(true).build());
        this.renderSwing = this.sgGeneral.add(new BoolSetting.Builder().name("swing").description("Renders your client-side swing.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates towards the blocks being placed.").defaultValue(true).build());
        this.blockPos = new class_2338.class_2339();
    }

    private void place(class_2338 class_23382, int n) {
        BlockUtils.place(class_23382, class_1268.field_5808, n, this.rotate.get(), -15, this.renderSwing.get(), true, true, true);
    }

    public boolean hasSafeWalk() {
        return this.safeWalk.get();
    }

    private int findSlot(class_2680 class_26802) {
        class_1799 class_17992;
        class_2680 class_26803;
        class_1799 class_17993;
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            class_2248 class_22482;
            class_17993 = this.mc.field_1724.field_7514.method_5438(i);
            if (class_17993.method_7960() || !(class_17993.method_7909() instanceof class_1747) || this.blackList.get().contains(class_2248.method_9503((class_1792)class_17993.method_7909())) || !class_2248.method_9614((class_265)(class_26803 = (class_22482 = ((class_1747)class_17993.method_7909()).method_7711()).method_9564()).method_26220((class_1922)this.mc.field_1687, this.setPos(0, -1, 0))) || class_22482 instanceof class_2346 && class_2346.method_10128((class_2680)class_26802)) continue;
            n = i;
            break;
        }
        if ((class_17992 = this.mc.field_1724.method_6047()).method_7960() || !(class_17992.method_7909() instanceof class_1747)) {
            return n;
        }
        if (this.blackList.get().contains(class_2248.method_9503((class_1792)class_17992.method_7909()))) {
            return n;
        }
        class_17993 = ((class_1747)class_17992.method_7909()).method_7711();
        class_26803 = class_17993.method_9564();
        if (!class_2248.method_9614((class_265)class_26803.method_26220((class_1922)this.mc.field_1687, this.setPos(0, -1, 0)))) {
            return n;
        }
        if (class_17993 instanceof class_2346 && class_2346.method_10128((class_2680)class_26802)) {
            return n;
        }
        n = this.mc.field_1724.field_7514.field_7545;
        return n;
    }

    private boolean findBlock() {
        if (this.mc.field_1724.field_7514.method_5438(this.slot).method_7960()) {
            this.slot = this.findSlot(this.blockState);
            if (this.slot == -1) {
                if (this.selfToggle.get().booleanValue()) {
                    this.toggle();
                }
                return false;
            }
        }
        return true;
    }
}

