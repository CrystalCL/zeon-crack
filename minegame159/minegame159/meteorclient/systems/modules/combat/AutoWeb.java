/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.Iterator;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1802;

public class AutoWeb
extends Module {
    private final Setting<Boolean> doubles;
    private class_1657 target;
    private final Setting<Boolean> rotate;
    private final Setting<Double> range;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = InvUtils.findItemInHotbar(class_1802.field_8786);
        if (n == -1) {
            return;
        }
        if (this.target != null && ((double)this.mc.field_1724.method_5739((class_1297)this.target) > this.range.get() || !this.target.method_5805())) {
            this.target = null;
        }
        for (class_1657 object : this.mc.field_1687.method_18456()) {
            if (object == this.mc.field_1724 || !Friends.get().attack(object) || !object.method_5805() || (double)this.mc.field_1724.method_5739((class_1297)object) > this.range.get()) continue;
            if (this.target == null) {
                this.target = object;
                continue;
            }
            if (!(this.mc.field_1724.method_5739((class_1297)this.target) > this.mc.field_1724.method_5739((class_1297)object))) continue;
            this.target = object;
        }
        if (this.target == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (fakePlayerEntity.method_6032() <= 0.0f || !Friends.get().attack((class_1657)fakePlayerEntity) || !fakePlayerEntity.method_5805()) continue;
                if (this.target == null) {
                    this.target = fakePlayerEntity;
                    continue;
                }
                if (!(this.mc.field_1724.method_5739((class_1297)fakePlayerEntity) < this.mc.field_1724.method_5739((class_1297)this.target))) continue;
                this.target = fakePlayerEntity;
            }
        }
        if (this.target != null) {
            Iterator<Object> iterator = this.target.method_24515();
            BlockUtils.place(iterator, class_1268.field_5808, n, this.rotate.get(), 0, false);
            if (this.doubles.get().booleanValue()) {
                iterator = iterator.method_10069(0, 1, 0);
                BlockUtils.place(iterator, class_1268.field_5808, InvUtils.findItemInHotbar(class_1802.field_8786), this.rotate.get(), 0, false);
            }
        }
    }

    public AutoWeb() {
        super(Categories.Combat, "auto-web", "Automatically places webs on other players.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum distance to be able to place webs.").defaultValue(4.0).min(0.0).build());
        this.doubles = this.sgGeneral.add(new BoolSetting.Builder().name("doubles").description("Places webs in the target's upper hitbox as well as the lower hitbox.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates towards the webs when placing.").defaultValue(true).build());
        this.target = null;
    }
}

