/*
 * Decompiled with CFR 0.151.
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
    private final SettingGroup sgGeneral;
    private final Setting<Double> maxDistance;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.player.isUsingItem()) {
            return;
        }
        if (this.mc.options.keyUse.isPressed()) {
            HitResult HitResult2 = this.mc.player.raycast(this.maxDistance.get().doubleValue(), 0.05f, false);
            if (HitResult2.getType() == HitResult.class_240.ENTITY && this.mc.player.interact(((EntityHitResult)HitResult2).getEntity(), Hand.MAIN_HAND) != ActionResult.PASS) {
                return;
            }
            if (HitResult2.getType() == HitResult.class_240.BLOCK) {
                BlockPos BlockPos2 = ((BlockHitResult)HitResult2).getBlockPos();
                Direction Direction2 = ((BlockHitResult)HitResult2).getSide();
                if (this.mc.world.getBlockState(BlockPos2).onUse((World)this.mc.world, (PlayerEntity)this.mc.player, Hand.MAIN_HAND, (BlockHitResult)HitResult2) != ActionResult.PASS) {
                    return;
                }
                this.mc.player.setPosition((double)BlockPos2.getX() + 0.5 + (double)Direction2.getOffsetX(), (double)(BlockPos2.getY() + Direction2.getOffsetY()), (double)BlockPos2.getZ() + 0.5 + (double)Direction2.getOffsetZ());
            }
        }
    }

    public ClickTP() {
        super(Categories.Movement, "click-tp", "Teleports you to the block you click on.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.maxDistance = this.sgGeneral.add(new DoubleSetting.Builder().name("max-distance").description("The maximum distance you can teleport.").defaultValue(5.0).build());
    }
}

