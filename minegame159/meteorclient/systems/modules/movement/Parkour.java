/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Box
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.stream.Stream;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class Parkour
extends Module {
    public Parkour() {
        super(Categories.Movement, "parkour", "Automatically jumps at the edges of blocks.");
        Parkour llIlIlIlIIIIIIl;
    }

    @EventHandler
    private void onTick(TickEvent.Post llIlIlIIllllIlI) {
        Parkour llIlIlIIlllIllI;
        if (!llIlIlIIlllIllI.mc.player.isOnGround() || llIlIlIIlllIllI.mc.options.keyJump.isPressed()) {
            return;
        }
        if (llIlIlIIlllIllI.mc.player.isSneaking() || llIlIlIIlllIllI.mc.options.keySneak.isPressed()) {
            return;
        }
        Box llIlIlIIllllIIl = llIlIlIIlllIllI.mc.player.getBoundingBox();
        Box llIlIlIIllllIII = llIlIlIIllllIIl.offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001);
        Stream llIlIlIIlllIlll = llIlIlIIlllIllI.mc.world.getBlockCollisions((Entity)llIlIlIIlllIllI.mc.player, llIlIlIIllllIII);
        if (llIlIlIIlllIlll.findAny().isPresent()) {
            return;
        }
        llIlIlIIlllIllI.mc.player.jump();
    }
}

