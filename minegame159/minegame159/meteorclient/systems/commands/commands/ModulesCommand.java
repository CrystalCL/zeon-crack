/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;

public class ModulesCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ModulesCommand::lambda$build$0);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ChatUtils.info("--- All (highlight)%d(default) Modules ---", Modules.get().getCount());
        LiteralText LiteralText2 = new LiteralText("");
        for (Module module : Modules.get().getList()) {
            LiteralText LiteralText3 = new LiteralText("");
            LiteralText3.append((Text)new LiteralText(module.title).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD})).append("\n");
            LiteralText3.append((Text)new LiteralText(module.name).formatted(Formatting.GRAY)).append("\n\n");
            LiteralText3.append((Text)new LiteralText(module.description).formatted(Formatting.WHITE));
            LiteralText LiteralText4 = new LiteralText(module.title);
            if (module != Modules.get().getList().get(Modules.get().getAll().size() - 1)) {
                LiteralText2.append((Text)new LiteralText(", ").formatted(Formatting.GRAY));
            }
            LiteralText4.setStyle(LiteralText4.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)LiteralText3)));
            LiteralText2.append((Text)LiteralText4);
        }
        ChatUtils.info((Text)LiteralText2);
        return 1;
    }

    public ModulesCommand() {
        super("modules", "Displays a list of all modules.", "features");
    }
}

