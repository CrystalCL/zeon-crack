/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  net.minecraft.inventory.Inventory
 *  net.minecraft.inventory.SimpleInventory
 *  net.minecraft.entity.player.PlayerInventory
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.item.ItemStack
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;

public class PeekCommand
extends Command {
    private static final /* synthetic */ SimpleCommandExceptionType NOT_HOLDING_SHULKER_BOX;
    private static final /* synthetic */ ItemStack[] ITEMS;

    static {
        ITEMS = new ItemStack[27];
        NOT_HOLDING_SHULKER_BOX = new SimpleCommandExceptionType((Message)new LiteralText("You must be holding a shulker box."));
    }

    public PeekCommand() {
        super("peek", "Lets you see what's inside shulker boxes.", new String[0]);
        PeekCommand lllIIIlIlIIII;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllIIIlIIllII) {
        lllIIIlIIllII.executes(lllIIIlIIlIII -> {
            void lllIIIlIIIlll;
            if (Utils.isShulker(PeekCommand.mc.player.getMainHandStack().getItem())) {
                ItemStack lllIIIlIIlIlI = PeekCommand.mc.player.getMainHandStack();
            } else if (Utils.isShulker(PeekCommand.mc.player.getOffHandStack().getItem())) {
                ItemStack lllIIIlIIlIIl = PeekCommand.mc.player.getOffHandStack();
            } else {
                throw NOT_HOLDING_SHULKER_BOX.create();
            }
            Utils.getItemsInContainerItem((ItemStack)lllIIIlIIIlll, ITEMS);
            MeteorClient.INSTANCE.screenToOpen = new PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, PeekCommand.mc.player.inventory, (Inventory)new SimpleInventory(ITEMS)), PeekCommand.mc.player.inventory, lllIIIlIIIlll.getName());
            return 1;
        });
    }

    public static class PeekShulkerBoxScreen
    extends ShulkerBoxScreen {
        public PeekShulkerBoxScreen(ShulkerBoxScreenHandler lllllllllllllllllllIIIlllllIIIlI, PlayerInventory lllllllllllllllllllIIIlllllIIIIl, Text lllllllllllllllllllIIIlllllIIIII) {
            super(lllllllllllllllllllIIIlllllIIIlI, lllllllllllllllllllIIIlllllIIIIl, lllllllllllllllllllIIIlllllIIIII);
            PeekShulkerBoxScreen lllllllllllllllllllIIIlllllIIIll;
        }

        public boolean mouseReleased(double lllllllllllllllllllIIIllllIlIllI, double lllllllllllllllllllIIIllllIlIlIl, int lllllllllllllllllllIIIllllIlIlII) {
            return false;
        }

        public boolean mouseClicked(double lllllllllllllllllllIIIllllIllIlI, double lllllllllllllllllllIIIllllIllIIl, int lllllllllllllllllllIIIllllIllIII) {
            return false;
        }
    }
}

