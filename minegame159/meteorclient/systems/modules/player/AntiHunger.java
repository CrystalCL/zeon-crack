/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class AntiHunger
extends Module {
    private /* synthetic */ boolean ignorePacket;
    private final /* synthetic */ Setting<Boolean> onGround;
    private /* synthetic */ boolean lastOnGround;
    private /* synthetic */ boolean sendOnGroundTruePacket;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> sprint;

    public AntiHunger() {
        super(Categories.Player, "anti-hunger", "Reduces (does NOT remove) hunger consumption.");
        AntiHunger lllllllllllllllllllIIllIllIllIll;
        lllllllllllllllllllIIllIllIllIll.sgGeneral = lllllllllllllllllllIIllIllIllIll.settings.getDefaultGroup();
        lllllllllllllllllllIIllIllIllIll.sprint = lllllllllllllllllllIIllIllIllIll.sgGeneral.add(new BoolSetting.Builder().name("sprint").description("Spoofs sprinting packets.").defaultValue(true).build());
        lllllllllllllllllllIIllIllIllIll.onGround = lllllllllllllllllllIIllIllIllIll.sgGeneral.add(new BoolSetting.Builder().name("on-ground").description("Spoofs the onGround flag.").defaultValue(true).build());
    }

    @Override
    public void onActivate() {
        AntiHunger lllllllllllllllllllIIllIllIllIIl;
        lllllllllllllllllllIIllIllIllIIl.lastOnGround = lllllllllllllllllllIIllIllIllIIl.mc.player.isOnGround();
        lllllllllllllllllllIIllIllIllIIl.sendOnGroundTruePacket = true;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIIllIllIIllII) {
        AntiHunger lllllllllllllllllllIIllIllIIlIll;
        if (lllllllllllllllllllIIllIllIIlIll.mc.player.isOnGround() && !lllllllllllllllllllIIllIllIIlIll.lastOnGround && !lllllllllllllllllllIIllIllIIlIll.sendOnGroundTruePacket) {
            lllllllllllllllllllIIllIllIIlIll.sendOnGroundTruePacket = true;
        }
        if (lllllllllllllllllllIIllIllIIlIll.mc.player.isOnGround() && lllllllllllllllllllIIllIllIIlIll.sendOnGroundTruePacket && lllllllllllllllllllIIllIllIIlIll.onGround.get().booleanValue()) {
            lllllllllllllllllllIIllIllIIlIll.ignorePacket = true;
            lllllllllllllllllllIIllIllIIlIll.mc.getNetworkHandler().sendPacket((Packet)new PlayerMoveC2SPacket(true));
            lllllllllllllllllllIIllIllIIlIll.ignorePacket = false;
            lllllllllllllllllllIIllIllIIlIll.sendOnGroundTruePacket = false;
        }
        lllllllllllllllllllIIllIllIIlIll.lastOnGround = lllllllllllllllllllIIllIllIIlIll.mc.player.isOnGround();
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send lllllllllllllllllllIIllIllIlIIII) {
        ClientCommandC2SPacket.class_2849 lllllllllllllllllllIIllIllIlIlII;
        AntiHunger lllllllllllllllllllIIllIllIlIIIl;
        if (lllllllllllllllllllIIllIllIlIIIl.ignorePacket) {
            return;
        }
        if (lllllllllllllllllllIIllIllIlIIII.packet instanceof ClientCommandC2SPacket && lllllllllllllllllllIIllIllIlIIIl.sprint.get().booleanValue() && ((lllllllllllllllllllIIllIllIlIlII = ((ClientCommandC2SPacket)lllllllllllllllllllIIllIllIlIIII.packet).getMode()) == ClientCommandC2SPacket.class_2849.START_SPRINTING || lllllllllllllllllllIIllIllIlIlII == ClientCommandC2SPacket.class_2849.STOP_SPRINTING)) {
            lllllllllllllllllllIIllIllIlIIII.cancel();
        }
        if (lllllllllllllllllllIIllIllIlIIII.packet instanceof PlayerMoveC2SPacket && lllllllllllllllllllIIllIllIlIIIl.onGround.get().booleanValue() && lllllllllllllllllllIIllIllIlIIIl.mc.player.isOnGround() && (double)lllllllllllllllllllIIllIllIlIIIl.mc.player.fallDistance <= 0.0 && !lllllllllllllllllllIIllIllIlIIIl.mc.interactionManager.isBreakingBlock()) {
            ((PlayerMoveC2SPacketAccessor)lllllllllllllllllllIIllIllIlIIII.packet).setOnGround(false);
        }
    }
}

