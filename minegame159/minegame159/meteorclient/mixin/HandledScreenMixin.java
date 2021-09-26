/*
 * Decompiled with CFR 0.151.
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

    public HandledScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void mouseClicked(double d, double d2, int n, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (n == 2 && this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips betterTooltips = Modules.get().get(BetterTooltips.class);
            if (BetterTooltips.hasItems(this.focusedSlot.getStack()) && betterTooltips.middleClickOpen.get().booleanValue()) {
                Utils.getItemsInContainerItem(this.focusedSlot.getStack(), ITEMS);
                this.client.openScreen((Screen)new PeekCommand.PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, this.client.player.inventory, (Inventory)new SimpleInventory(ITEMS)), this.client.player.inventory, this.focusedSlot.getStack().getName()));
                callbackInfoReturnable.setReturnValue((Object)true);
            } else if (this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && betterTooltips.previewEChest()) {
                for (int i = 0; i < EChestMemory.ITEMS.size(); ++i) {
                    HandledScreenMixin.ITEMS[i] = (ItemStack)EChestMemory.ITEMS.get(i);
                }
                this.client.openScreen((Screen)new PeekCommand.PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, this.client.player.inventory, (Inventory)new SimpleInventory(ITEMS)), this.client.player.inventory, this.focusedSlot.getStack().getName()));
                callbackInfoReturnable.setReturnValue((Object)true);
            }
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void onRender(MatrixStack MatrixStack2, int n, int n2, float f, CallbackInfo callbackInfo) {
        if (this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips betterTooltips = Modules.get().get(BetterTooltips.class);
            if (BetterTooltips.hasItems(this.focusedSlot.getStack()) && betterTooltips.previewShulkers()) {
                NbtCompound NbtCompound2 = this.focusedSlot.getStack().getSubTag("BlockEntityTag");
                DefaultedList DefaultedList2 = DefaultedList.ofSize((int)27, (Object)ItemStack.EMPTY);
                Inventories.readNbt((NbtCompound)NbtCompound2, (DefaultedList)DefaultedList2);
                this.draw(MatrixStack2, (DefaultedList<ItemStack>)DefaultedList2, n, n2, betterTooltips.getShulkerColor(this.focusedSlot.getStack()));
            } else if (this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && betterTooltips.previewEChest()) {
                this.draw(MatrixStack2, EChestMemory.ITEMS, n, n2, betterTooltips.echestColor.get());
            } else if (this.focusedSlot.getStack().getItem() == Items.FILLED_MAP && betterTooltips.previewMaps()) {
                this.drawMapPreview(MatrixStack2, this.focusedSlot.getStack(), n, n2, betterTooltips.mapsScale.get());
            }
        }
    }

    @Inject(method={"drawMouseoverTooltip"}, at={@At(value="HEAD")}, cancellable=true)
    private void onDrawMouseoverTooltip(MatrixStack MatrixStack2, int n, int n2, CallbackInfo callbackInfo) {
        if (this.focusedSlot != null && !this.focusedSlot.getStack().isEmpty()) {
            BetterTooltips betterTooltips = Modules.get().get(BetterTooltips.class);
            if (this.focusedSlot.getStack().getItem() == Items.FILLED_MAP && betterTooltips.previewMaps()) {
                callbackInfo.cancel();
            } else if ((BetterTooltips.hasItems(this.focusedSlot.getStack()) && betterTooltips.previewShulkers() || this.focusedSlot.getStack().getItem() == Items.ENDER_CHEST && betterTooltips.previewEChest()) && !betterTooltips.showVanilla.get().booleanValue()) {
                callbackInfo.cancel();
            }
        }
    }

    private void draw(MatrixStack MatrixStack2, DefaultedList<ItemStack> DefaultedList2, int n, int n2, Color color) {
        RenderSystem.disableLighting();
        RenderSystem.disableDepthTest();
        GL11.glClear((int)256);
        this.drawBackground(MatrixStack2, n += 8, n2 -= 12, color);
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        DiffuseLighting.enable();
        int n3 = 0;
        int n4 = 0;
        for (ItemStack ItemStack2 : DefaultedList2) {
            RenderUtils.drawItem(ItemStack2, n + 8 + n4 * 18, n2 + 7 + n3 * 18, true);
            if (++n4 < 9) continue;
            n4 = 0;
            ++n3;
        }
        DiffuseLighting.disable();
        RenderSystem.enableDepthTest();
    }

    private void drawBackground(MatrixStack MatrixStack2, int n, int n2, Color color) {
        RenderSystem.color4f((float)((float)color.r / 255.0f), (float)((float)color.g / 255.0f), (float)((float)color.b / 255.0f), (float)((float)color.a / 255.0f));
        this.client.getTextureManager().bindTexture(TEXTURE_CONTAINER_BACKGROUND);
        DrawableHelper.drawTexture((MatrixStack)MatrixStack2, (int)n, (int)n2, (int)0, (float)0.0f, (float)0.0f, (int)176, (int)67, (int)67, (int)176);
    }

    private void drawMapPreview(MatrixStack MatrixStack2, ItemStack ItemStack2, int n, int n2, int n3) {
        GL11.glEnable((int)3042);
        RenderSystem.pushMatrix();
        RenderSystem.disableLighting();
        RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int n4 = n2 - 12;
        int n5 = n4 + n3;
        int n6 = n + 8;
        int n7 = n6 + n3;
        int n8 = 300;
        this.client.getTextureManager().bindTexture(TEXTURE_MAP_BACKGROUND);
        Tessellator Tessellator2 = Tessellator.getInstance();
        BufferBuilder BufferBuilder2 = Tessellator2.getBuffer();
        BufferBuilder2.begin(7, VertexFormats.POSITION_TEXTURE);
        BufferBuilder2.vertex((double)n6, (double)n5, (double)n8).texture(0.0f, 1.0f).next();
        BufferBuilder2.vertex((double)n7, (double)n5, (double)n8).texture(1.0f, 1.0f).next();
        BufferBuilder2.vertex((double)n7, (double)n4, (double)n8).texture(1.0f, 0.0f).next();
        BufferBuilder2.vertex((double)n6, (double)n4, (double)n8).texture(0.0f, 0.0f).next();
        Tessellator2.draw();
        MapState MapState2 = FilledMapItem.getOrCreateMapState((ItemStack)ItemStack2, (World)this.client.world);
        if (MapState2 != null) {
            MapState2.getPlayerSyncData((PlayerEntity)this.client.player);
            n8 = 310;
            double d = (double)(n3 - 16) / 128.0;
            RenderSystem.translatef((float)(n6 += 8), (float)(n4 += 8), (float)n8);
            RenderSystem.scaled((double)d, (double)d, (double)0.0);
            VertexConsumerProvider.class_4598 class_45982 = this.client.getBufferBuilders().getEntityVertexConsumers();
            this.client.gameRenderer.getMapRenderer().draw(MatrixStack2, (VertexConsumerProvider)class_45982, MapState2, false, 0xF000F0);
        }
        RenderSystem.enableLighting();
        RenderSystem.popMatrix();
    }

    @Inject(method={"drawSlot"}, at={@At(value="HEAD")})
    private void onDrawSlot(MatrixStack MatrixStack2, Slot Slot2, CallbackInfo callbackInfo) {
        int n = Modules.get().get(ItemHighlight.class).getColor(Slot2.getStack());
        if (n != -1) {
            HandledScreenMixin.fill((MatrixStack)MatrixStack2, (int)Slot2.x, (int)Slot2.y, (int)(Slot2.x + 16), (int)(Slot2.y + 16), (int)n);
        }
    }
}

