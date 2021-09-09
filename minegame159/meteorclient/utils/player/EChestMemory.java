/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Inventory
 *  net.minecraft.screen.GenericContainerScreenHandler
 *  net.minecraft.item.ItemStack
 *  net.minecraft.block.EnderChestBlock
 *  net.minecraft.util.collection.DefaultedList
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.ingame.GenericContainerScreen
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
    private static final /* synthetic */ MinecraftClient MC;
    private static /* synthetic */ int echestOpenedState;
    public static final /* synthetic */ DefaultedList<ItemStack> ITEMS;

    public EChestMemory() {
        EChestMemory lIllIIlllIIIlI;
    }

    @EventHandler
    private static void onBlockActivate(BlockActivateEvent lIllIIllIlllll) {
        if (lIllIIllIlllll.blockState.getBlock() instanceof EnderChestBlock && echestOpenedState == 0) {
            echestOpenedState = 1;
        }
    }

    @EventHandler
    private static void onOpenScreenEvent(OpenScreenEvent lIllIIllIlIllI) {
        if (echestOpenedState == 1 && lIllIIllIlIllI.screen instanceof GenericContainerScreen) {
            echestOpenedState = 2;
            return;
        }
        if (echestOpenedState == 0) {
            return;
        }
        if (!(EChestMemory.MC.currentScreen instanceof GenericContainerScreen)) {
            return;
        }
        GenericContainerScreenHandler lIllIIllIllIII = (GenericContainerScreenHandler)((GenericContainerScreen)EChestMemory.MC.currentScreen).getScreenHandler();
        if (lIllIIllIllIII == null) {
            return;
        }
        Inventory lIllIIllIlIlll = lIllIIllIllIII.getInventory();
        for (int lIllIIllIllIlI = 0; lIllIIllIllIlI < 27; ++lIllIIllIllIlI) {
            ITEMS.set(lIllIIllIllIlI, (Object)lIllIIllIlIlll.getStack(lIllIIllIllIlI));
        }
        echestOpenedState = 0;
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(EChestMemory.class);
    }

    static {
        ITEMS = DefaultedList.ofSize((int)27, (Object)ItemStack.EMPTY);
        MC = MinecraftClient.getInstance();
    }
}

