/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderItemEntityEvent;
import minegame159.meteorclient.mixininterface.IItemEntity;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.Vec3f;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.json.ModelTransformation;

public class ItemPhysics
extends Module {
    private int getRenderedAmount(ItemStack ItemStack2) {
        int n = 1;
        if (ItemStack2.getCount() > 48) {
            n = 5;
        } else if (ItemStack2.getCount() > 32) {
            n = 4;
        } else if (ItemStack2.getCount() > 16) {
            n = 3;
        } else if (ItemStack2.getCount() > 1) {
            n = 2;
        }
        return n;
    }

    public ItemPhysics() {
        super(Categories.Render, "item-physics", "Applies physics to items on the ground.");
    }

    @EventHandler
    private void onRenderItemEntity(RenderItemEntityEvent renderItemEntityEvent) {
        float f;
        float f2;
        float f3;
        Item Item2;
        VoxelShape VoxelShape2;
        ItemStack ItemStack2 = renderItemEntityEvent.itemEntity.getStack();
        int n = ItemStack2.isEmpty() ? 187 : Item.getRawId((Item)ItemStack2.getItem()) + ItemStack2.getDamage();
        renderItemEntityEvent.random.setSeed(n);
        renderItemEntityEvent.matrixStack.push();
        BakedModel BakedModel2 = renderItemEntityEvent.itemRenderer.getHeldItemModel(ItemStack2, renderItemEntityEvent.itemEntity.world, null);
        boolean bl = BakedModel2.hasDepth();
        int n2 = this.getRenderedAmount(ItemStack2);
        IItemEntity iItemEntity = (IItemEntity)renderItemEntityEvent.itemEntity;
        boolean bl2 = false;
        if (renderItemEntityEvent.itemEntity.getStack().getItem() instanceof BlockItem && !(renderItemEntityEvent.itemEntity.getStack().getItem() instanceof AliasedBlockItem) && (VoxelShape2 = (Item2 = ((BlockItem)renderItemEntityEvent.itemEntity.getStack().getItem()).getBlock()).getOutlineShape(Item2.getDefaultState(), (BlockView)renderItemEntityEvent.itemEntity.world, renderItemEntityEvent.itemEntity.getBlockPos(), ShapeContext.absent())).getMax(Direction.class_2351.Y) <= 0.5) {
            bl2 = true;
        }
        if ((Item2 = renderItemEntityEvent.itemEntity.getStack().getItem()) instanceof BlockItem && !(Item2 instanceof AliasedBlockItem) && !bl2) {
            renderItemEntityEvent.matrixStack.translate(0.0, -0.06, 0.0);
        }
        if (!bl2) {
            renderItemEntityEvent.matrixStack.translate(0.0, 0.185, 0.0);
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(1.571f));
            renderItemEntityEvent.matrixStack.translate(0.0, -0.185, -0.0);
        }
        boolean bl3 = renderItemEntityEvent.itemEntity.world.getBlockState(renderItemEntityEvent.itemEntity.getBlockPos()).getFluidState().getFluid().isIn((Tag)FluidTags.WATER);
        if (!(renderItemEntityEvent.itemEntity.isOnGround() || renderItemEntityEvent.itemEntity.isSubmergedInWater() || bl3)) {
            f3 = ((float)renderItemEntityEvent.itemEntity.getItemAge() + renderItemEntityEvent.tickDelta) / 20.0f + renderItemEntityEvent.itemEntity.uniqueOffset;
            if (!bl2) {
                renderItemEntityEvent.matrixStack.translate(0.0, 0.185, 0.0);
                renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(f3));
                renderItemEntityEvent.matrixStack.translate(0.0, -0.185, 0.0);
                iItemEntity.setRotation(new Vec3d(0.0, 0.0, (double)f3));
            } else {
                renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(f3));
                iItemEntity.setRotation(new Vec3d(0.0, (double)f3, 0.0));
                renderItemEntityEvent.matrixStack.translate(0.0, -0.065, 0.0);
            }
            if (renderItemEntityEvent.itemEntity.getStack().getItem() instanceof AliasedBlockItem) {
                renderItemEntityEvent.matrixStack.translate(0.0, 0.0, 0.195);
            } else if (!(renderItemEntityEvent.itemEntity.getStack().getItem() instanceof BlockItem)) {
                renderItemEntityEvent.matrixStack.translate(0.0, 0.0, 0.195);
            }
        } else if (renderItemEntityEvent.itemEntity.getStack().getItem() instanceof AliasedBlockItem) {
            renderItemEntityEvent.matrixStack.translate(0.0, 0.185, 0.0);
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float)iItemEntity.getRotation().z));
            renderItemEntityEvent.matrixStack.translate(0.0, -0.185, 0.0);
            renderItemEntityEvent.matrixStack.translate(0.0, 0.0, 0.195);
        } else if (bl2) {
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float)iItemEntity.getRotation().y));
            renderItemEntityEvent.matrixStack.translate(0.0, -0.065, 0.0);
        } else {
            if (!(renderItemEntityEvent.itemEntity.getStack().getItem() instanceof BlockItem)) {
                renderItemEntityEvent.matrixStack.translate(0.0, 0.0, 0.195);
            }
            renderItemEntityEvent.matrixStack.translate(0.0, 0.185, 0.0);
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float)iItemEntity.getRotation().z));
            renderItemEntityEvent.matrixStack.translate(0.0, -0.185, 0.0);
        }
        if (renderItemEntityEvent.itemEntity.world.getBlockState(renderItemEntityEvent.itemEntity.getBlockPos()).getBlock().equals(Blocks.SOUL_SAND)) {
            renderItemEntityEvent.matrixStack.translate(0.0, 0.0, -0.1);
        }
        if (renderItemEntityEvent.itemEntity.getStack().getItem() instanceof BlockItem && ((BlockItem)renderItemEntityEvent.itemEntity.getStack().getItem()).getBlock() instanceof SkullBlock) {
            renderItemEntityEvent.matrixStack.translate(0.0, 0.11, 0.0);
        }
        f3 = BakedModel2.getTransformation().ground.scale.getX();
        float f4 = BakedModel2.getTransformation().ground.scale.getY();
        float f5 = BakedModel2.getTransformation().ground.scale.getZ();
        if (!bl) {
            float f6 = -0.0f * (float)n2 * 0.5f * f3;
            f2 = -0.0f * (float)n2 * 0.5f * f4;
            f = -0.09375f * (float)n2 * 0.5f * f5;
            renderItemEntityEvent.matrixStack.translate((double)f6, (double)f2, (double)f);
        }
        for (int i = 0; i < n2; ++i) {
            renderItemEntityEvent.matrixStack.push();
            if (i > 0) {
                if (bl) {
                    f2 = (renderItemEntityEvent.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    f = (renderItemEntityEvent.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f7 = (renderItemEntityEvent.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    renderItemEntityEvent.matrixStack.translate((double)f2, (double)f, (double)f7);
                } else {
                    f2 = (renderItemEntityEvent.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    f = (renderItemEntityEvent.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    renderItemEntityEvent.matrixStack.translate((double)f2, (double)f, 0.0);
                    renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(renderItemEntityEvent.random.nextFloat()));
                }
            }
            renderItemEntityEvent.itemRenderer.renderItem(ItemStack2, ModelTransformation.class_811.GROUND, false, renderItemEntityEvent.matrixStack, renderItemEntityEvent.vertexConsumerProvider, renderItemEntityEvent.light, OverlayTexture.DEFAULT_UV, BakedModel2);
            renderItemEntityEvent.matrixStack.pop();
            if (bl) continue;
            renderItemEntityEvent.matrixStack.translate((double)(0.0f * f3), (double)(0.0f * f4), (double)(0.0625f * f5));
            if (2 != 0) continue;
            return;
        }
        renderItemEntityEvent.matrixStack.pop();
        renderItemEntityEvent.setCancelled(true);
    }
}

