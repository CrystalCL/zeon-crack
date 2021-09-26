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
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TimeChanger
extends Module {
    long oldTime;
    private final SettingGroup sgDefault;
    private final Setting<Double> time;

    @Override
    public void onDeactivate() {
        this.mc.world.setTimeOfDay(this.oldTime);
    }

    public TimeChanger() {
        super(Categories.Render, "time-changer", "Makes you able to set a custom time.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.time = this.sgDefault.add(new DoubleSetting.Builder().name("time").description("The specified time to be set.").defaultValue(0.0).sliderMin(-20000.0).sliderMax(20000.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.mc.world.setTimeOfDay(this.time.get().longValue());
    }

    @Override
    public void onActivate() {
        this.oldTime = this.mc.world.getTime();
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        if (receive.packet instanceof WorldTimeUpdateS2CPacket) {
            this.oldTime = ((WorldTimeUpdateS2CPacket)receive.packet).getTime();
            receive.setCancelled(true);
        }
    }
}

