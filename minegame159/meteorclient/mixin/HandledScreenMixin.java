/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  javax.annotation.Nullable
 *  net.minecraft.inventory.Inventories
 *  net.minecraft.inventory.Inventory
 *  net.minecraft.inventory.SimpleInventory
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.screen.slot.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.FilledMapItem
 *  net.minecraft.world.World
 *  net.minecraft.item.map.MapState
 *  net.minecraft.util.collection.DefaultedList
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.text.Text
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.render.DiffuseLighting
 *  net.minecraft.client.gui.DrawableHelper
 *  net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.VertexConsumerProvider$class_4598
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  org.lwjgl.opengl.GL11
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package minegame159.meteorclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import minegame159.meteorclient.systems.commands.commands.PeekCommand;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.BetterTooltips;
import minegame159.meteorclient.systems.modules.render.ItemHighlight;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.EChestMemory;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.FilledMapItem;
import net.minecraft.world.World;
import net.minecraft.item.map.MapState;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={HandledScreen.class})
public abstract class HandledScreenMixin<T extends ScreenHandler>
extends Screen
implements ScreenHandlerProvider<T> {
    @Shadow
    @Nullable
    protected Slot focusedSlot;
    @Shadow
    protected int x;
    @Shadow
    protected int y;
    private static final Identifier TEXTURE_CONTAINER_BACKGROUND = new Identifier("meteor-client", "container_3x9.png");
    private static final Identifier TEXTURE_MAP_BACKGROUND = new Identifier("textures/map/map_background.png");
    private static final ItemStack[] ITEMS = new ItemStack[27];

    public HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button == 2 && this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips toolips = Modules.get().get(BetterTooltips.class);
            if (BetterTooltips.hasItems(this.focusedSlot.getStack()) && toolips.middleClickOpen.get().booleanValue()) {
                Utils.getItemsInContainerItem(this.focusedSlot.getStack(), ITEMS);
                this.client.openScreen((Screen)new PeekCommand.PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, this.client.player.inventory, (Inventory)new SimpleInventory(ITEMS)), this.client.player.inventory, this.focusedSlot.getStack().getName()));
                cir.setReturnValue((Object)true);
            } else if (this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && toolips.previewEChest()) {
                for (int i = 0; i < EChestMemory.ITEMS.size(); ++i) {
                    HandledScreenMixin.ITEMS[i] = (ItemStack)EChestMemory.ITEMS.get(i);
                }
                this.client.openScreen((Screen)new PeekCommand.PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, this.client.player.inventory, (Inventory)new SimpleInventory(ITEMS)), this.client.player.inventory, this.focusedSlot.getStack().getName()));
                cir.setReturnValue((Object)true);
            }
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips toolips = Modules.get().get(BetterTooltips.class);
            if (BetterTooltips.hasItems(this.focusedSlot.getStack()) && toolips.previewShulkers()) {
                NbtCompound compoundTag = this.focusedSlot.getStack().getSubTag("BlockEntityTag");
                DefaultedList itemStacks = DefaultedList.ofSize((int)27, (Object)ItemStack.EMPTY);
                Inventories.readNbt((NbtCompound)compoundTag, (DefaultedList)itemStacks);
                this.draw(matrices, (DefaultedList<ItemStack>)itemStacks, mouseX, mouseY, toolips.getShulkerColor(this.focusedSlot.getStack()));
            } else if (this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && toolips.previewEChest()) {
                this.draw(matrices, EChestMemory.ITEMS, mouseX, mouseY, toolips.echestColor.get());
            } else if (this.focusedSlot.getStack().getItem() == Items.FILLED_MAP && toolips.previewMaps()) {
                this.drawMapPreview(matrices, this.focusedSlot.getStack(), mouseX, mouseY, toolips.mapsScale.get());
            }
        }
    }

    @Inject(method={"drawMouseoverTooltip"}, at={@At(value="HEAD")}, cancellable=true)
    private void onDrawMouseoverTooltip(MatrixStack matrices, int x, int y, CallbackInfo info) {
        if (this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips toolips = Modules.get().get(BetterTooltips.class);
            if (this.focusedSlot.getStack().getItem() == Items.FILLED_MAP && toolips.previewMaps()) {
                info.cancel();
            } else if ((BetterTooltips.hasItems(this.focusedSlot.getStack()) && toolips.previewShulkers() || this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && toolips.previewEChest()) && !toolips.showVanilla.get().booleanValue()) {
                info.cancel();
            }
        }
    }

    private void draw(MatrixStack matrices, DefaultedList<ItemStack> itemStacks, int mouseX, int mouseY, Color color) {
        RenderSystem.disableLighting();
        RenderSystem.disableDepthTest();
        GL11.glClear((int)256);
        this.drawBackground(matrices, mouseX += 8, mouseY -= 12, color);
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        DiffuseLighting.enable();
        int row = 0;
        int i = 0;
        for (ItemStack itemStack : itemStacks) {
            RenderUtils.drawItem(itemStack, mouseX + 8 + i * 18, mouseY + 7 + row * 18, true);
            if (++i < 9) continue;
            i = 0;
            ++row;
        }
        DiffuseLighting.disable();
        RenderSystem.enableDepthTest();
    }

    private void drawBackground(MatrixStack matrices, int x, int y, Color color) {
        RenderSystem.color4f((float)((float)color.r / 255.0f), (float)((float)color.g / 255.0f), (float)((float)color.b / 255.0f), (float)((float)color.a / 255.0f));
        this.client.getTextureManager().bindTexture(TEXTURE_CONTAINER_BACKGROUND);
        DrawableHelper.drawTexture((MatrixStack)matrices, (int)x, (int)y, (int)0, (float)0.0f, (float)0.0f, (int)176, (int)67, (int)67, (int)176);
    }

    private void drawMapPreview(MatrixStack matrices, ItemStack stack, int x, int y, int dimensions) {
        GL11.glEnable((int)3042);
        RenderSystem.pushMatrix();
        RenderSystem.disableLighting();
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int y1 = y - 12;
        int y2 = y1 + dimensions;
        int x1 = x + 8;
        int x2 = x1 + dimensions;
        int z = 300;
        this.client.getTextureManager().bindTexture(TEXTURE_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_TEXTURE);
        buffer.vertex((double)x1, (double)y2, (double)z).texture(0.0f, 1.0f).next();
        buffer.vertex((double)x2, (double)y2, (double)z).texture(1.0f, 1.0f).next();
        buffer.vertex((double)x2, (double)y1, (double)z).texture(1.0f, 0.0f).next();
        buffer.vertex((double)x1, (double)y1, (double)z).texture(0.0f, 0.0f).next();
        tessellator.draw();
        MapState mapState = FilledMapItem.getOrCreateMapState((ItemStack)stack, (World)this.client.world);
        if (mapState != null) {
            mapState.getPlayerSyncData((PlayerEntity)this.client.player);
            z = 310;
            double scale = (double)(dimensions - 16) / 128.0;
            RenderSystem.translatef((float)(x1 += 8), (float)(y1 += 8), (float)z);
            RenderSystem.scaled((double)scale, (double)scale, (double)0.0);
            VertexConsumerProvider.class_4598 consumer = this.client.getBufferBuilders().getEntityVertexConsumers();
            this.client.gameRenderer.getMapRenderer().draw(matrices, (VertexConsumerProvider)consumer, mapState, false, 0xF000F0);
        }
        RenderSystem.enableLighting();
        RenderSystem.popMatrix();
    }

    @Inject(method={"drawSlot"}, at={@At(value="HEAD")})
    private void onDrawSlot(MatrixStack matrices, Slot slot, CallbackInfo info) {
        int color = Modules.get().get(ItemHighlight.class).getColor(slot.getStack());
        if (color != -1) {
            HandledScreenMixin.fill((MatrixStack)matrices, (int)slot.x, (int)slot.y, (int)(slot.x + 16), (int)(slot.y + 16), (int)color);
        }
    }
}

