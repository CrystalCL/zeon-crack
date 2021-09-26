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
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class AutoWeb
extends Module {
    private final Setting<Boolean> doubles;
    private PlayerEntity target;
    private final Setting<Boolean> rotate;
    private final Setting<Double> range;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = InvUtils.findItemInHotbar(Items.COBWEB);
        if (n == -1) {
            return;
        }
        if (this.target != null && ((double)this.mc.player.distanceTo((Entity)this.target) > this.range.get() || !this.target.isAlive())) {
            this.target = null;
        }
        for (PlayerEntity object : this.mc.world.getPlayers()) {
            if (object == this.mc.player || !Friends.get().attack(object) || !object.isAlive() || (double)this.mc.player.distanceTo((Entity)object) > this.range.get()) continue;
            if (this.target == null) {
                this.target = object;
                continue;
            }
            if (!(this.mc.player.distanceTo((Entity)this.target) > this.mc.player.distanceTo((Entity)object))) continue;
            this.target = object;
        }
        if (this.target == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (fakePlayerEntity.getHealth() <= 0.0f || !Friends.get().attack((PlayerEntity)fakePlayerEntity) || !fakePlayerEntity.isAlive()) continue;
                if (this.target == null) {
                    this.target = fakePlayerEntity;
                    continue;
                }
                if (!(this.mc.player.distanceTo((Entity)fakePlayerEntity) < this.mc.player.distanceTo((Entity)this.target))) continue;
                this.target = fakePlayerEntity;
            }
        }
        if (this.target != null) {
            Iterator<Object> iterator = this.target.getBlockPos();
            BlockUtils.place(iterator, Hand.MAIN_HAND, n, this.rotate.get(), 0, false);
            if (this.doubles.get().booleanValue()) {
                iterator = iterator.add(0, 1, 0);
                BlockUtils.place(iterator, Hand.MAIN_HAND, InvUtils.findItemInHotbar(Items.COBWEB), this.rotate.get(), 0, false);
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

