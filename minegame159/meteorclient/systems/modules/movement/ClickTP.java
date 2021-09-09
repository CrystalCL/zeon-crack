/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.util.ActionResult
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.World
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.hit.HitResult
 *  net.minecraft.util.hit.HitResult$class_240
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.EntityHitResult
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class ClickTP
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> maxDistance;

    public ClickTP() {
        super(Categories.Movement, "click-tp", "Teleports you to the block you click on.");
        ClickTP lllllllllllllllllllIIIllIIIIIIIl;
        lllllllllllllllllllIIIllIIIIIIIl.sgGeneral = lllllllllllllllllllIIIllIIIIIIIl.settings.getDefaultGroup();
        lllllllllllllllllllIIIllIIIIIIIl.maxDistance = lllllllllllllllllllIIIllIIIIIIIl.sgGeneral.add(new DoubleSetting.Builder().name("max-distance").description("The maximum distance you can teleport.").defaultValue(5.0).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllIIIlIllllIlll) {
        ClickTP lllllllllllllllllllIIIlIlllllIII;
        if (lllllllllllllllllllIIIlIlllllIII.mc.player.isUsingItem()) {
            return;
        }
        if (lllllllllllllllllllIIIlIlllllIII.mc.options.keyUse.isPressed()) {
            HitResult lllllllllllllllllllIIIlIlllllIIl = lllllllllllllllllllIIIlIlllllIII.mc.player.raycast(lllllllllllllllllllIIIlIlllllIII.maxDistance.get().doubleValue(), 0.05f, false);
            if (lllllllllllllllllllIIIlIlllllIIl.getType() == HitResult.class_240.ENTITY && lllllllllllllllllllIIIlIlllllIII.mc.player.interact(((EntityHitResult)lllllllllllllllllllIIIlIlllllIIl).getEntity(), Hand.MAIN_HAND) != ActionResult.PASS) {
                return;
            }
            if (lllllllllllllllllllIIIlIlllllIIl.getType() == HitResult.class_240.BLOCK) {
                BlockPos lllllllllllllllllllIIIlIlllllIll = ((BlockHitResult)lllllllllllllllllllIIIlIlllllIIl).getBlockPos();
                Direction lllllllllllllllllllIIIlIlllllIlI = ((BlockHitResult)lllllllllllllllllllIIIlIlllllIIl).getSide();
                if (lllllllllllllllllllIIIlIlllllIII.mc.world.getBlockState(lllllllllllllllllllIIIlIlllllIll).onUse((World)lllllllllllllllllllIIIlIlllllIII.mc.world, (PlayerEntity)lllllllllllllllllllIIIlIlllllIII.mc.player, Hand.MAIN_HAND, (BlockHitResult)lllllllllllllllllllIIIlIlllllIIl) != ActionResult.PASS) {
                    return;
                }
                lllllllllllllllllllIIIlIlllllIII.mc.player.setPosition((double)lllllllllllllllllllIIIlIlllllIll.getX() + 0.5 + (double)lllllllllllllllllllIIIlIlllllIlI.getOffsetX(), (double)(lllllllllllllllllllIIIlIlllllIll.getY() + lllllllllllllllllllIIIlIlllllIlI.getOffsetY()), (double)lllllllllllllllllllIIIlIlllllIll.getZ() + 0.5 + (double)lllllllllllllllllllIIIlIlllllIlI.getOffsetZ());
            }
        }
    }
}

