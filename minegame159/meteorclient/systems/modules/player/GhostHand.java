/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.util.hit.BlockHitResult
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
    private final /* synthetic */ List<BlockPos> posList;

    @EventHandler
    private void onTick(TickEvent.Pre llIlIIlIlllllI) {
        GhostHand llIlIIlIllllll;
        if (!llIlIIlIllllll.mc.options.keyUse.isPressed() || llIlIIlIllllll.mc.player.isSneaking()) {
            return;
        }
        for (BlockEntity llIlIIllIIIIll : llIlIIlIllllll.mc.world.blockEntities) {
            if (!new BlockPos(llIlIIlIllllll.mc.player.raycast((double)llIlIIlIllllll.mc.interactionManager.getReachDistance(), llIlIIlIllllll.mc.getTickDelta(), false).getPos()).equals((Object)llIlIIllIIIIll.getPos())) continue;
            return;
        }
        Vec3d llIlIIlIllllIl = new Vec3d(0.0, 0.0, 0.1).rotateX(-((float)Math.toRadians(llIlIIlIllllll.mc.player.pitch))).rotateY(-((float)Math.toRadians(llIlIIlIllllll.mc.player.yaw)));
        int llIlIIllIIIIII = 1;
        while ((float)llIlIIllIIIIII < llIlIIlIllllll.mc.interactionManager.getReachDistance() * 10.0f) {
            BlockPos llIlIIllIIIIIl = new BlockPos(llIlIIlIllllll.mc.player.getCameraPosVec(llIlIIlIllllll.mc.getTickDelta()).add(llIlIIlIllllIl.multiply((double)llIlIIllIIIIII)));
            if (!llIlIIlIllllll.posList.contains((Object)llIlIIllIIIIIl)) {
                llIlIIlIllllll.posList.add(llIlIIllIIIIIl);
                for (BlockEntity llIlIIllIIIIlI : llIlIIlIllllll.mc.world.blockEntities) {
                    if (!llIlIIllIIIIlI.getPos().equals((Object)llIlIIllIIIIIl)) continue;
                    llIlIIlIllllll.mc.interactionManager.interactBlock(llIlIIlIllllll.mc.player, llIlIIlIllllll.mc.world, Hand.MAIN_HAND, new BlockHitResult(llIlIIlIllllll.mc.player.getPos(), Direction.UP, llIlIIllIIIIIl, true));
                    return;
                }
            }
            ++llIlIIllIIIIII;
        }
        llIlIIlIllllll.posList.clear();
    }

    public GhostHand() {
        super(Categories.Player, "ghost-hand", "Opens containers through walls.");
        GhostHand llIlIIllIIlIlI;
        llIlIIllIIlIlI.posList = new ArrayList<BlockPos>();
    }
}

