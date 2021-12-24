package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Send;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;

public class PortalGodMode extends Module {
   Packet<?> a;

   public PortalGodMode() {
      super(CrystalCL.Exc, "portal-god-mode", "Portal God Mode.");
   }

   @EventHandler
   private void POPS(PacketEvent.Send e) {
      if (e.packet instanceof TeleportConfirmC2SPacket) {
         e.cancel();
      }

      a = e.packet;
   }

   public void onDeactivate() {
      mc.player.networkHandler.sendPacket(a);
   }
}
