/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.class_2172;

public class ReloadCommand
extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, macros and accounts.", new String[0]);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        Systems.load();
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ReloadCommand::lambda$build$0);
    }
}

