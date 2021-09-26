/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class ResetCommand
extends Command {
    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Module module = (Module)commandContext.getArgument("module", Module.class);
        module.settings.forEach(ResetCommand::lambda$build$0);
        return 1;
    }

    private static void lambda$build$0(SettingGroup settingGroup) {
        settingGroup.forEach(Setting::reset);
    }

    private static void lambda$build$3(Module module) {
        module.settings.forEach(ResetCommand::lambda$build$2);
    }

    private static void lambda$build$7(Module module) {
        module.keybind.set(true, -1);
    }

    private static int lambda$build$8(CommandContext commandContext) throws CommandSyntaxException {
        Modules.get().getAll().forEach(ResetCommand::lambda$build$7);
        return 1;
    }

    private static void lambda$build$2(SettingGroup settingGroup) {
        settingGroup.forEach(Setting::reset);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(((LiteralArgumentBuilder)ResetCommand.literal("settings").then(ResetCommand.argument("module", ModuleArgumentType.module()).executes(ResetCommand::lambda$build$1))).then(ResetCommand.literal("all").executes(ResetCommand::lambda$build$4)))).then(ResetCommand.literal("gui").executes(ResetCommand::lambda$build$5))).then(((LiteralArgumentBuilder)ResetCommand.literal("bind").then(ResetCommand.argument("module", ModuleArgumentType.module()).executes(ResetCommand::lambda$build$6))).then(ResetCommand.literal("all").executes(ResetCommand::lambda$build$8)));
    }

    private static int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        Modules.get().getAll().forEach(ResetCommand::lambda$build$3);
        return 1;
    }

    private static int lambda$build$6(CommandContext commandContext) throws CommandSyntaxException {
        Module module = (Module)commandContext.getArgument("module", Module.class);
        module.keybind.set(true, -1);
        ChatUtils.prefixInfo("KeyBinds", "This bind has been reset.", new Object[0]);
        return 1;
    }

    private static int lambda$build$5(CommandContext commandContext) throws CommandSyntaxException {
        ChatUtils.info("The ClickGUI positioning has been reset.", new Object[0]);
        return 1;
    }

    public ResetCommand() {
        super("reset", "Resets specified settings.", new String[0]);
    }
}

