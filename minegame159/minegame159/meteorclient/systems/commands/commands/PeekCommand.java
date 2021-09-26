/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
    private static final SimpleCommandExceptionType NOT_HOLDING_SHULKER_BOX;
    private static final ItemStack[] ITEMS;

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2;
        if (Utils.isShulker(PeekCommand.mc.player.getMainHandStack().getItem())) {
            ItemStack2 = PeekCommand.mc.player.getMainHandStack();
        } else if (Utils.isShulker(PeekCommand.mc.player.getOffHandStack().getItem())) {
            ItemStack2 = PeekCommand.mc.player.getOffHandStack();
        } else {
            throw NOT_HOLDING_SHULKER_BOX.create();
        }
        Utils.getItemsInContainerItem(ItemStack2, ITEMS);
        MeteorClient.INSTANCE.screenToOpen = new PeekShulkerBoxScreen(new ShulkerBoxScreenHandler(0, PeekCommand.mc.player.inventory, (Inventory)new SimpleInventory(ITEMS)), PeekCommand.mc.player.inventory, ItemStack2.getName());
        return 1;
    }

    static {
        ITEMS = new ItemStack[27];
        NOT_HOLDING_SHULKER_BOX = new SimpleCommandExceptionType((Message)new LiteralText("You must be holding a shulker box."));
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(PeekCommand::lambda$build$0);
    }

    public PeekCommand() {
        super("peek", "Lets you see what's inside shulker boxes.", new String[0]);
    }

    public static class PeekShulkerBoxScreen
    extends ShulkerBoxScreen {
        public boolean mouseReleased(double d, double d2, int n) {
            return false;
        }

        public boolean mouseClicked(double d, double d2, int n) {
            return false;
        }

        public PeekShulkerBoxScreen(ShulkerBoxScreenHandler ShulkerBoxScreenHandler2, PlayerInventory PlayerInventory2, Text Text2) {
            super(ShulkerBoxScreenHandler2, PlayerInventory2, Text2);
        }
    }
}

