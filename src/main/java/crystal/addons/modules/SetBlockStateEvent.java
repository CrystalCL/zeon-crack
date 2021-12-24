package crystal.addons.modules;

import meteordevelopment.meteorclient.events.Cancellable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

public class SetBlockStateEvent extends Cancellable {
   private static final SetBlockStateEvent INSTANCE = new SetBlockStateEvent();
   public BlockPos pos;
   public BlockState oldState;
   public BlockState newState;

   public static SetBlockStateEvent get(BlockPos pos, BlockState oldState, BlockState newState) {
      INSTANCE.setCancelled(false);
      INSTANCE.pos = pos;
      INSTANCE.oldState = oldState;
      INSTANCE.newState = newState;
      return INSTANCE;
   }
}
