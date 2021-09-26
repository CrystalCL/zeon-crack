/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.BlockActivateEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={BlockState.class})
public abstract class BlockStateMixin
extends AbstractBlockState {
    public BlockStateMixin(Block Block2, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
        super(Block2, immutableMap, mapCodec);
    }

    public ActionResult onUse(World World2, PlayerEntity PlayerEntity2, Hand Hand2, BlockHitResult BlockHitResult2) {
        MeteorClient.EVENT_BUS.post(BlockActivateEvent.get((BlockState)this));
        return super.onUse(World2, PlayerEntity2, Hand2, BlockHitResult2);
    }
}

