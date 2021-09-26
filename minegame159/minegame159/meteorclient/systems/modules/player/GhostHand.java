/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.hit.BlockHitResult;

public class GhostHand
extends Module {
    private final List<BlockPos> posList = new ArrayList<BlockPos>();

    public GhostHand() {
        super(Categories.Player, "ghost-hand", "Opens containers through walls.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (!this.mc.options.keyUse.isPressed() || this.mc.player.isSneaking()) {
            return;
        }
        for (BlockEntity BlockEntity2 : this.mc.world.blockEntities) {
            if (!new BlockPos(this.mc.player.raycast((double)this.mc.interactionManager.getReachDistance(), this.mc.getTickDelta(), false).getPos()).equals((Object)BlockEntity2.getPos())) continue;
            return;
        }
        Vec3d Vec3d2 = new Vec3d(0.0, 0.0, 0.1).rotateX(-((float)Math.toRadians(this.mc.player.pitch))).rotateY(-((float)Math.toRadians(this.mc.player.yaw)));
        int n = 1;
        while ((float)n < this.mc.interactionManager.getReachDistance() * 10.0f) {
            BlockPos BlockPos2 = new BlockPos(this.mc.player.getCameraPosVec(this.mc.getTickDelta()).add(Vec3d2.multiply((double)n)));
            if (!this.posList.contains(BlockPos2)) {
                this.posList.add(BlockPos2);
                for (BlockEntity BlockEntity3 : this.mc.world.blockEntities) {
                    if (!BlockEntity3.getPos().equals((Object)BlockPos2)) continue;
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), Direction.UP, BlockPos2, true));
                    return;
                }
            }
            ++n;
            if (-1 <= 4) continue;
            return;
        }
        this.posList.clear();
    }
}

