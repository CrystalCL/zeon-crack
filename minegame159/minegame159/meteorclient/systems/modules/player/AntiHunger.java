/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> sprint;
    private boolean lastOnGround;
    private boolean ignorePacket;
    private boolean sendOnGroundTruePacket;
    private final Setting<Boolean> onGround;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.isOnGround() && !this.lastOnGround && !this.sendOnGroundTruePacket) {
            this.sendOnGroundTruePacket = true;
        }
        if (this.mc.player.isOnGround() && this.sendOnGroundTruePacket && this.onGround.get().booleanValue()) {
            this.ignorePacket = true;
            this.mc.getNetworkHandler().sendPacket((Packet)new PlayerMoveC2SPacket(true));
            this.ignorePacket = false;
            this.sendOnGroundTruePacket = false;
        }
        this.lastOnGround = this.mc.player.isOnGround();
    }

    @Override
    public void onActivate() {
        this.lastOnGround = this.mc.player.isOnGround();
        this.sendOnGroundTruePacket = true;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        ClientCommandC2SPacket.class_2849 class_28492;
        if (this.ignorePacket) {
            return;
        }
        if (send.packet instanceof ClientCommandC2SPacket && this.sprint.get().booleanValue() && ((class_28492 = ((ClientCommandC2SPacket)send.packet).getMode()) == ClientCommandC2SPacket.class_2849.START_SPRINTING || class_28492 == ClientCommandC2SPacket.class_2849.STOP_SPRINTING)) {
            send.cancel();
        }
        if (send.packet instanceof PlayerMoveC2SPacket && this.onGround.get().booleanValue() && this.mc.player.isOnGround() && (double)this.mc.player.fallDistance <= 0.0 && !this.mc.interactionManager.isBreakingBlock()) {
            ((PlayerMoveC2SPacketAccessor)send.packet).setOnGround(false);
        }
    }

    public AntiHunger() {
        super(Categories.Player, "anti-hunger", "Reduces (does NOT remove) hunger consumption.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sprint = this.sgGeneral.add(new BoolSetting.Builder().name("sprint").description("Spoofs sprinting packets.").defaultValue(true).build());
        this.onGround = this.sgGeneral.add(new BoolSetting.Builder().name("on-ground").description("Spoofs the onGround flag.").defaultValue(true).build());
    }
}

