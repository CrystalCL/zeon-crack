/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.BoatMoveEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;

public class BoatFly
extends Module {
    private final /* synthetic */ Setting<Boolean> cancelServerPackets;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> speed;
    private final /* synthetic */ Setting<Double> verticalSpeed;
    private final /* synthetic */ Setting<Double> fallSpeed;

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive llllllllllllllllllllIIIIlIIIIIll) {
        BoatFly llllllllllllllllllllIIIIlIIIIlII;
        if (llllllllllllllllllllIIIIlIIIIIll.packet instanceof VehicleMoveS2CPacket && llllllllllllllllllllIIIIlIIIIlII.cancelServerPackets.get().booleanValue()) {
            llllllllllllllllllllIIIIlIIIIIll.cancel();
        }
    }

    public BoatFly() {
        super(Categories.Movement, "boat-fly", "Transforms your boat into a plane.");
        BoatFly llllllllllllllllllllIIIIlIIlllII;
        llllllllllllllllllllIIIIlIIlllII.sgGeneral = llllllllllllllllllllIIIIlIIlllII.settings.getDefaultGroup();
        llllllllllllllllllllIIIIlIIlllII.speed = llllllllllllllllllllIIIIlIIlllII.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Horizontal speed in blocks per second.").defaultValue(10.0).min(0.0).sliderMax(50.0).build());
        llllllllllllllllllllIIIIlIIlllII.verticalSpeed = llllllllllllllllllllIIIIlIIlllII.sgGeneral.add(new DoubleSetting.Builder().name("vertical-speed").description("Vertical speed in blocks per second.").defaultValue(6.0).min(0.0).sliderMax(20.0).build());
        llllllllllllllllllllIIIIlIIlllII.fallSpeed = llllllllllllllllllllIIIIlIIlllII.sgGeneral.add(new DoubleSetting.Builder().name("fall-speed").description("How fast you fall in blocks per second.").defaultValue(0.1).min(0.0).build());
        llllllllllllllllllllIIIIlIIlllII.cancelServerPackets = llllllllllllllllllllIIIIlIIlllII.sgGeneral.add(new BoolSetting.Builder().name("cancel-server-packets").description("Cancels incoming boat move packets.").defaultValue(false).build());
    }

    @EventHandler
    private void onBoatMove(BoatMoveEvent llllllllllllllllllllIIIIlIIIllIl) {
        BoatFly llllllllllllllllllllIIIIlIIlIlII;
        if (llllllllllllllllllllIIIIlIIIllIl.boat.getPrimaryPassenger() != llllllllllllllllllllIIIIlIIlIlII.mc.player) {
            return;
        }
        llllllllllllllllllllIIIIlIIIllIl.boat.yaw = llllllllllllllllllllIIIIlIIlIlII.mc.player.yaw;
        Vec3d llllllllllllllllllllIIIIlIIlIIlI = PlayerUtils.getHorizontalVelocity(llllllllllllllllllllIIIIlIIlIlII.speed.get());
        double llllllllllllllllllllIIIIlIIlIIIl = llllllllllllllllllllIIIIlIIlIIlI.getX();
        double llllllllllllllllllllIIIIlIIlIIII = 0.0;
        double llllllllllllllllllllIIIIlIIIllll = llllllllllllllllllllIIIIlIIlIIlI.getZ();
        if (llllllllllllllllllllIIIIlIIlIlII.mc.options.keyJump.isPressed()) {
            llllllllllllllllllllIIIIlIIlIIII += llllllllllllllllllllIIIIlIIlIlII.verticalSpeed.get() / 20.0;
        }
        llllllllllllllllllllIIIIlIIlIIII = llllllllllllllllllllIIIIlIIlIlII.mc.options.keySprint.isPressed() ? (llllllllllllllllllllIIIIlIIlIIII -= llllllllllllllllllllIIIIlIIlIlII.verticalSpeed.get() / 20.0) : (llllllllllllllllllllIIIIlIIlIIII -= llllllllllllllllllllIIIIlIIlIlII.fallSpeed.get() / 20.0);
        ((IVec3d)llllllllllllllllllllIIIIlIIIllIl.boat.getVelocity()).set(llllllllllllllllllllIIIIlIIlIIIl, llllllllllllllllllllIIIIlIIlIIII, llllllllllllllllllllIIIIlIIIllll);
    }
}

