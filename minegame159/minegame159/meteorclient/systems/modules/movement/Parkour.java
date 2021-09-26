/*
 * Decompiled with CFR 0.151.
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
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.mc.player.isOnGround() || this.mc.options.keyJump.isPressed()) {
            return;
        }
        if (this.mc.player.isSneaking() || this.mc.options.keySneak.isPressed()) {
            return;
        }
        Box Box3 = this.mc.player.getBoundingBox();
        Box Box4 = Box3.offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001);
        Stream stream = this.mc.world.getBlockCollisions((Entity)this.mc.player, Box4);
        if (stream.findAny().isPresent()) {
            return;
        }
        this.mc.player.jump();
    }
}

