/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_2761;

public class TimeChanger
extends Module {
    long oldTime;
    private final SettingGroup sgDefault;
    private final Setting<Double> time;

    @Override
    public void onDeactivate() {
        this.mc.field_1687.method_8435(this.oldTime);
    }

    public TimeChanger() {
        super(Categories.Render, "time-changer", "Makes you able to set a custom time.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.time = this.sgDefault.add(new DoubleSetting.Builder().name("time").description("The specified time to be set.").defaultValue(0.0).sliderMin(-20000.0).sliderMax(20000.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.mc.field_1687.method_8435(this.time.get().longValue());
    }

    @Override
    public void onActivate() {
        this.oldTime = this.mc.field_1687.method_8510();
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        if (receive.packet instanceof class_2761) {
            this.oldTime = ((class_2761)receive.packet).method_11871();
            receive.setCancelled(true);
        }
    }
}

