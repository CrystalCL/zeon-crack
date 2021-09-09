/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.serialization.MapCodec
 *  net.minecraft.util.Hand
 *  net.minecraft.util.ActionResult
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.World
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockState
 *  net.minecraft.state.property.Property
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.block.AbstractBlock.AbstractBlockState
 *  org.spongepowered.asm.mixin.Mixin
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
    public BlockStateMixin(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> mapCodec) {
        super(block, propertyMap, mapCodec);
    }

    public ActionResult onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MeteorClient.EVENT_BUS.post(BlockActivateEvent.get((BlockState)this));
        return super.onUse(world, player, hand, hit);
    }
}

