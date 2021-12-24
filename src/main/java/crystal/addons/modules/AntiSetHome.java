package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Send;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.block.RespawnAnchorBlock;

public class AntiSetHome extends Module {
   public AntiSetHome() {
      super(CrystalCL.PvE, "anti-respawn-lose", "Prevent the player from losing the respawn point.");
   }

   @EventHandler
   private void onSendPacket(PacketEvent.Send event) {
      if (mc.world != null) {
         if (event.packet instanceof PlayerInteractBlockC2SPacket) {
            BlockPos blockPos = ((PlayerInteractBlockC2SPacket)event.packet).getBlockHitResult().getBlockPos();
            boolean IsOverWorld = mc.world.getDimension().isBedWorking();
            boolean IsNetherWorld = mc.world.getDimension().isRespawnAnchorWorking();
            boolean BlockIsBed = mc.world.getBlockState(blockPos).getBlock() instanceof BedBlock;
            boolean BlockIsAnchor = mc.world.getBlockState(blockPos).getBlock() instanceof RespawnAnchorBlock;
            if (BlockIsBed && IsOverWorld || BlockIsAnchor && IsNetherWorld) {
               event.cancel();
            }
         }
      }
   }
}
