/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class BindsCommand
extends Command {
    public BindsCommand() {
        super("binds", "List of all bound modules.", new String[0]);
    }

    private static boolean lambda$build$0(Module module) {
        return module.keybind.isSet();
    }

    private int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        List list = Modules.get().getAll().stream().filter(BindsCommand::lambda$build$0).collect(Collectors.toList());
        ChatUtils.info("--- (highlight)%d(default) bound modules ---", list.size());
        for (Module module : list) {
            HoverEvent HoverEvent2 = new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)this.getTooltip(module));
            MutableText MutableText2 = new LiteralText(module.title).formatted(Formatting.WHITE);
            MutableText2.setStyle(MutableText2.getStyle().withHoverEvent(HoverEvent2));
            LiteralText LiteralText2 = new LiteralText(" - ");
            LiteralText2.setStyle(LiteralText2.getStyle().withHoverEvent(HoverEvent2));
            MutableText2.append((Text)LiteralText2.formatted(Formatting.GRAY));
            LiteralText LiteralText3 = new LiteralText(module.keybind.toString());
            LiteralText3.setStyle(LiteralText3.getStyle().withHoverEvent(HoverEvent2));
            MutableText2.append((Text)LiteralText3.formatted(Formatting.GRAY));
            ChatUtils.info((Text)MutableText2);
        }
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(this::lambda$build$1);
    }

    private MutableText getTooltip(Module module) {
        MutableText MutableText2 = new LiteralText(Utils.nameToTitle(module.title)).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD}).append("\n\n");
        MutableText2.append((Text)new LiteralText(module.description).formatted(Formatting.WHITE));
        return MutableText2;
    }
}

