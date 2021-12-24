package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class MultiTask extends Module {
   public MultiTask() {
      super(CrystalCL.PvE, "Multi-Task", "Allows you to eat while mining a block.");
   }

   @EventHandler
   public void onInteractEvent(InteractEvent event) {
      event.usingItem = false;
   }
}
