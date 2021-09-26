/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.BlockActivateEvent;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;

public class EChestMemory {
    private static final MinecraftClient MC;
    private static int echestOpenedState;
    public static final DefaultedList<ItemStack> ITEMS;

    @EventHandler
    private static void onOpenScreenEvent(OpenScreenEvent openScreenEvent) {
        if (echestOpenedState == 1 && openScreenEvent.screen instanceof GenericContainerScreen) {
            echestOpenedState = 2;
            return;
        }
        if (echestOpenedState == 0) {
            return;
        }
        if (!(EChestMemory.MC.currentScreen instanceof GenericContainerScreen)) {
            return;
        }
        GenericContainerScreenHandler GenericContainerScreenHandler2 = (GenericContainerScreenHandler)((GenericContainerScreen)EChestMemory.MC.currentScreen).getScreenHandler();
        if (GenericContainerScreenHandler2 == null) {
            return;
        }
        Inventory Inventory2 = GenericContainerScreenHandler2.getInventory();
        for (int i = 0; i < 27; ++i) {
            ITEMS.set(i, (Object)Inventory2.getStack(i));
            if (-4 <= 0) continue;
            return;
        }
        echestOpenedState = 0;
    }

    @EventHandler
    private static void onBlockActivate(BlockActivateEvent blockActivateEvent) {
        if (blockActivateEvent.blockState.getBlock() instanceof EnderChestBlock && echestOpenedState == 0) {
            echestOpenedState = 1;
        }
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(EChestMemory.class);
    }

    static {
        ITEMS = DefaultedList.ofSize((int)27, (Object)ItemStack.EMPTY);
        MC = MinecraftClient.getInstance();
    }
}

