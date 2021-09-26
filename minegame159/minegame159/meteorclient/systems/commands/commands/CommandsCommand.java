/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.command.CommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;

public class CommandsCommand
extends Command {
    public CommandsCommand() {
        super("help", "List of all commands.", "commands");
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ChatUtils.info("--- All (highlight)%d(default) Commands ---", Commands.get().getCount());
        LiteralText LiteralText2 = new LiteralText("");
        for (Command command : Commands.get().getAll()) {
            LiteralText LiteralText3 = new LiteralText("");
            LiteralText3.append((Text)new LiteralText(Utils.nameToTitle(command.getName())).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD})).append("\n");
            LiteralText LiteralText4 = new LiteralText(String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(command.getName())));
            if (command.getAliases().size() > 0) {
                LiteralText4.append(", ");
                for (String string : command.getAliases()) {
                    if (string.isEmpty()) continue;
                    LiteralText4.append(String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(string)));
                    if (string.equals(command.getAliases().get(command.getAliases().size() - 1))) continue;
                    LiteralText4.append(", ");
                }
            }
            LiteralText3.append((Text)LiteralText4.formatted(Formatting.GRAY)).append("\n\n");
            LiteralText3.append((Text)new LiteralText(command.getDescription()).formatted(Formatting.WHITE));
            LiteralText LiteralText5 = new LiteralText(Utils.nameToTitle(command.getName()));
            if (command != Commands.get().getAll().get(Commands.get().getAll().size() - 1)) {
                LiteralText5.append((Text)new LiteralText(", ").formatted(Formatting.GRAY));
            }
            LiteralText5.setStyle(LiteralText5.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)LiteralText3)).withClickEvent(new ClickEvent(ClickEvent.class_2559.SUGGEST_COMMAND, String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(command.getName())))));
            LiteralText2.append((Text)LiteralText5);
        }
        ChatUtils.info((Text)LiteralText2);
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(CommandsCommand::lambda$build$0);
    }
}

