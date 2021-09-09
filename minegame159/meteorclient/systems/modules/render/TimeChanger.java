/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket
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
    /* synthetic */ long oldTime;
    private final /* synthetic */ Setting<Double> time;
    private final /* synthetic */ SettingGroup sgDefault;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllIIIIlllIlllII) {
        TimeChanger llllllllllllllllIllIIIIlllIlllIl;
        llllllllllllllllIllIIIIlllIlllIl.mc.world.setTimeOfDay(llllllllllllllllIllIIIIlllIlllIl.time.get().longValue());
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive llllllllllllllllIllIIIIllllIIIIl) {
        if (llllllllllllllllIllIIIIllllIIIIl.packet instanceof WorldTimeUpdateS2CPacket) {
            llllllllllllllllIllIIIIllllIIIII.oldTime = ((WorldTimeUpdateS2CPacket)llllllllllllllllIllIIIIllllIIIIl.packet).getTime();
            llllllllllllllllIllIIIIllllIIIIl.setCancelled(true);
        }
    }

    public TimeChanger() {
        super(Categories.Render, "time-changer", "Makes you able to set a custom time.");
        TimeChanger llllllllllllllllIllIIIIllllIlIll;
        llllllllllllllllIllIIIIllllIlIll.sgDefault = llllllllllllllllIllIIIIllllIlIll.settings.getDefaultGroup();
        llllllllllllllllIllIIIIllllIlIll.time = llllllllllllllllIllIIIIllllIlIll.sgDefault.add(new DoubleSetting.Builder().name("time").description("The specified time to be set.").defaultValue(0.0).sliderMin(-20000.0).sliderMax(20000.0).build());
    }

    @Override
    public void onActivate() {
        TimeChanger llllllllllllllllIllIIIIllllIlIIl;
        llllllllllllllllIllIIIIllllIlIIl.oldTime = llllllllllllllllIllIIIIllllIlIIl.mc.world.getTime();
    }

    @Override
    public void onDeactivate() {
        TimeChanger llllllllllllllllIllIIIIllllIIllI;
        llllllllllllllllIllIIIIllllIIllI.mc.world.setTimeOfDay(llllllllllllllllIllIIIIllllIIllI.oldTime);
    }
}

