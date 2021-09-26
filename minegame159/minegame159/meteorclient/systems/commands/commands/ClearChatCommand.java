/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

public class ClearChatCommand
extends Command {
    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ClearChatCommand.mc.inGameHud.getChatHud().clear(false);
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ClearChatCommand::lambda$build$0);
    }

    public ClearChatCommand() {
        super("clear-chat", "Clears your chat.", new String[0]);
    }
}

