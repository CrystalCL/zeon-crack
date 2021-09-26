/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.command.CommandSource;

public class ToggleCommand
extends Command {
    private static int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        Module module = (Module)commandContext.getArgument("module", Module.class);
        if (module.isActive()) {
            module.toggle();
        }
        module.sendToggledMsg();
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)ToggleCommand.argument("module", ModuleArgumentType.module()).executes(ToggleCommand::lambda$build$0)).then(ToggleCommand.literal("on").executes(ToggleCommand::lambda$build$1))).then(ToggleCommand.literal("off").executes(ToggleCommand::lambda$build$2)));
    }

    public ToggleCommand() {
        super("toggle", "Toggles a module.", "t");
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        Module module = (Module)commandContext.getArgument("module", Module.class);
        module.toggle();
        module.sendToggledMsg();
        return 1;
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Module module = (Module)commandContext.getArgument("module", Module.class);
        if (!module.isActive()) {
            module.toggle();
        }
        module.sendToggledMsg();
        return 1;
    }
}

