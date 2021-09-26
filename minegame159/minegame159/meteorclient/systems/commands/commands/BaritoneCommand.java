/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

public class BaritoneCommand
extends Command {
    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        String string = (String)commandContext.getArgument("command", String.class);
        BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(string);
        return 1;
    }

    public BaritoneCommand() {
        super("baritone", "Executes baritone commands.", "b");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(BaritoneCommand.argument("command", StringArgumentType.greedyString()).executes(BaritoneCommand::lambda$build$0));
    }
}

