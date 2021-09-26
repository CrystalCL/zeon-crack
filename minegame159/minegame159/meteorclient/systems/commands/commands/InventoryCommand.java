/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.PlayerArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class InventoryCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(InventoryCommand.argument("name", PlayerArgumentType.player()).executes(InventoryCommand::lambda$build$0));
    }

    public InventoryCommand() {
        super("inventory", "Allows you to see parts of another player's inventory.", "inv", "invsee");
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        PlayerEntity PlayerEntity2 = (PlayerEntity)commandContext.getArgument("name", PlayerEntity.class);
        MeteorClient.INSTANCE.screenToOpen = new InventoryScreen(PlayerEntity2);
        return 1;
    }
}

