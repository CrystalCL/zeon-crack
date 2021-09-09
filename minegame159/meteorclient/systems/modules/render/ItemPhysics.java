/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.render.model.BakedModel
 *  net.minecraft.util.math.Vec3f
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.AliasedBlockItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.Direction.Axis
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.SkullBlock
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.tag.FluidTags
 *  net.minecraft.tag.Tag
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.client.render.model.json.ModelTransformation.Mode
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
import net.minecraft.block.Block;
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
    @EventHandler
    private void onRenderItemEntity(RenderItemEntityEvent llIlIlIIIIII) {
        Item llIlIIlllIII;
        Block llIlIlIIllIl;
        VoxelShape llIlIlIIllII;
        ItemPhysics llIlIIllIIll;
        ItemStack llIlIIllllll = llIlIlIIIIII.itemEntity.getStack();
        int llIlIIlllllI = llIlIIllllll.isEmpty() ? 187 : Item.getRawId((Item)llIlIIllllll.getItem()) + llIlIIllllll.getDamage();
        llIlIlIIIIII.random.setSeed(llIlIIlllllI);
        llIlIlIIIIII.matrixStack.push();
        BakedModel llIlIIllllIl = llIlIlIIIIII.itemRenderer.getHeldItemModel(llIlIIllllll, llIlIlIIIIII.itemEntity.world, null);
        boolean llIlIIllllII = llIlIIllllIl.hasDepth();
        int llIlIIlllIll = llIlIIllIIll.getRenderedAmount(llIlIIllllll);
        IItemEntity llIlIIlllIlI = (IItemEntity)llIlIlIIIIII.itemEntity;
        boolean llIlIIlllIIl = false;
        if (llIlIlIIIIII.itemEntity.getStack().getItem() instanceof BlockItem && !(llIlIlIIIIII.itemEntity.getStack().getItem() instanceof AliasedBlockItem) && (llIlIlIIllII = (llIlIlIIllIl = ((BlockItem)llIlIlIIIIII.itemEntity.getStack().getItem()).getBlock()).getOutlineShape(llIlIlIIllIl.getDefaultState(), (BlockView)llIlIlIIIIII.itemEntity.world, llIlIlIIIIII.itemEntity.getBlockPos(), ShapeContext.absent())).getMax(Axis.Y) <= 0.5) {
            llIlIIlllIIl = true;
        }
        if ((llIlIIlllIII = llIlIlIIIIII.itemEntity.getStack().getItem()) instanceof BlockItem && !(llIlIIlllIII instanceof AliasedBlockItem) && !llIlIIlllIIl) {
            llIlIlIIIIII.matrixStack.translate(0.0, -0.06, 0.0);
        }
        if (!llIlIIlllIIl) {
            llIlIlIIIIII.matrixStack.translate(0.0, 0.185, 0.0);
            llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(1.571f));
            llIlIlIIIIII.matrixStack.translate(0.0, -0.185, -0.0);
        }
        boolean llIlIIllIlll = llIlIlIIIIII.itemEntity.world.getBlockState(llIlIlIIIIII.itemEntity.getBlockPos()).getFluidState().getFluid().isIn((Tag)FluidTags.WATER);
        if (!(llIlIlIIIIII.itemEntity.isOnGround() || llIlIlIIIIII.itemEntity.isSubmergedInWater() || llIlIIllIlll)) {
            float llIlIlIIlIll = ((float)llIlIlIIIIII.itemEntity.getItemAge() + llIlIlIIIIII.tickDelta) / 20.0f + llIlIlIIIIII.itemEntity.uniqueOffset;
            if (!llIlIIlllIIl) {
                llIlIlIIIIII.matrixStack.translate(0.0, 0.185, 0.0);
                llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(llIlIlIIlIll));
                llIlIlIIIIII.matrixStack.translate(0.0, -0.185, 0.0);
                llIlIIlllIlI.setRotation(new Vec3d(0.0, 0.0, (double)llIlIlIIlIll));
            } else {
                llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(llIlIlIIlIll));
                llIlIIlllIlI.setRotation(new Vec3d(0.0, (double)llIlIlIIlIll, 0.0));
                llIlIlIIIIII.matrixStack.translate(0.0, -0.065, 0.0);
            }
            if (llIlIlIIIIII.itemEntity.getStack().getItem() instanceof AliasedBlockItem) {
                llIlIlIIIIII.matrixStack.translate(0.0, 0.0, 0.195);
            } else if (!(llIlIlIIIIII.itemEntity.getStack().getItem() instanceof BlockItem)) {
                llIlIlIIIIII.matrixStack.translate(0.0, 0.0, 0.195);
            }
        } else if (llIlIlIIIIII.itemEntity.getStack().getItem() instanceof AliasedBlockItem) {
            llIlIlIIIIII.matrixStack.translate(0.0, 0.185, 0.0);
            llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float)llIlIIlllIlI.getRotation().z));
            llIlIlIIIIII.matrixStack.translate(0.0, -0.185, 0.0);
            llIlIlIIIIII.matrixStack.translate(0.0, 0.0, 0.195);
        } else if (llIlIIlllIIl) {
            llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float)llIlIIlllIlI.getRotation().y));
            llIlIlIIIIII.matrixStack.translate(0.0, -0.065, 0.0);
        } else {
            if (!(llIlIlIIIIII.itemEntity.getStack().getItem() instanceof BlockItem)) {
                llIlIlIIIIII.matrixStack.translate(0.0, 0.0, 0.195);
            }
            llIlIlIIIIII.matrixStack.translate(0.0, 0.185, 0.0);
            llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion((float)llIlIIlllIlI.getRotation().z));
            llIlIlIIIIII.matrixStack.translate(0.0, -0.185, 0.0);
        }
        if (llIlIlIIIIII.itemEntity.world.getBlockState(llIlIlIIIIII.itemEntity.getBlockPos()).getBlock().equals((Object)Blocks.SOUL_SAND)) {
            llIlIlIIIIII.matrixStack.translate(0.0, 0.0, -0.1);
        }
        if (llIlIlIIIIII.itemEntity.getStack().getItem() instanceof BlockItem && ((BlockItem)llIlIlIIIIII.itemEntity.getStack().getItem()).getBlock() instanceof SkullBlock) {
            llIlIlIIIIII.matrixStack.translate(0.0, 0.11, 0.0);
        }
        float llIlIIllIllI = llIlIIllllIl.getTransformation().ground.scale.getX();
        float llIlIIllIlIl = llIlIIllllIl.getTransformation().ground.scale.getY();
        float llIlIIllIlII = llIlIIllllIl.getTransformation().ground.scale.getZ();
        if (!llIlIIllllII) {
            float llIlIlIIlIlI = -0.0f * (float)llIlIIlllIll * 0.5f * llIlIIllIllI;
            float llIlIlIIlIIl = -0.0f * (float)llIlIIlllIll * 0.5f * llIlIIllIlIl;
            float llIlIlIIlIII = -0.09375f * (float)llIlIIlllIll * 0.5f * llIlIIllIlII;
            llIlIlIIIIII.matrixStack.translate((double)llIlIlIIlIlI, (double)llIlIlIIlIIl, (double)llIlIlIIlIII);
        }
        for (int llIlIlIIIIlI = 0; llIlIlIIIIlI < llIlIIlllIll; ++llIlIlIIIIlI) {
            llIlIlIIIIII.matrixStack.push();
            if (llIlIlIIIIlI > 0) {
                if (llIlIIllllII) {
                    float llIlIlIIIllI = (llIlIlIIIIII.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float llIlIlIIIlIl = (llIlIlIIIIII.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float llIlIlIIIlll = (llIlIlIIIIII.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    llIlIlIIIIII.matrixStack.translate((double)llIlIlIIIllI, (double)llIlIlIIIlIl, (double)llIlIlIIIlll);
                } else {
                    float llIlIlIIIlII = (llIlIlIIIIII.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    float llIlIlIIIIll = (llIlIlIIIIII.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    llIlIlIIIIII.matrixStack.translate((double)llIlIlIIIlII, (double)llIlIlIIIIll, 0.0);
                    llIlIlIIIIII.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(llIlIlIIIIII.random.nextFloat()));
                }
            }
            llIlIlIIIIII.itemRenderer.renderItem(llIlIIllllll, Mode.GROUND, false, llIlIlIIIIII.matrixStack, llIlIlIIIIII.vertexConsumerProvider, llIlIlIIIIII.light, OverlayTexture.DEFAULT_UV, llIlIIllllIl);
            llIlIlIIIIII.matrixStack.pop();
            if (llIlIIllllII) continue;
            llIlIlIIIIII.matrixStack.translate((double)(0.0f * llIlIIllIllI), (double)(0.0f * llIlIIllIlIl), (double)(0.0625f * llIlIIllIlII));
        }
        llIlIlIIIIII.matrixStack.pop();
        llIlIlIIIIII.setCancelled(true);
    }

    private int getRenderedAmount(ItemStack llIlIIIllllI) {
        int llIlIIIlllIl = 1;
        if (llIlIIIllllI.getCount() > 48) {
            llIlIIIlllIl = 5;
        } else if (llIlIIIllllI.getCount() > 32) {
            llIlIIIlllIl = 4;
        } else if (llIlIIIllllI.getCount() > 16) {
            llIlIIIlllIl = 3;
        } else if (llIlIIIllllI.getCount() > 1) {
            llIlIIIlllIl = 2;
        }
        return llIlIIIlllIl;
    }

    public ItemPhysics() {
        super(Categories.Render, "item-physics", "Applies physics to items on the ground.");
        ItemPhysics llIlIllIIIII;
    }
}

