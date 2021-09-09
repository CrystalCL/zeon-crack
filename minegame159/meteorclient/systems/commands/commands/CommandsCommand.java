/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.util.Formatting
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.ClickEvent
 *  net.minecraft.text.ClickEvent$class_2559
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
        CommandsCommand lIlIIlllIllIlll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIlIIlllIllIlII) {
        lIlIIlllIllIlII.executes(lIlIIlllIlIIllI -> {
            ChatUtils.info("--- All (highlight)%d(default) Commands ---", Commands.get().getCount());
            LiteralText lIlIIlllIlIIlIl = new LiteralText("");
            for (Command lIlIIlllIlIIlll : Commands.get().getAll()) {
                LiteralText lIlIIlllIlIlIlI = new LiteralText("");
                lIlIIlllIlIlIlI.append((Text)new LiteralText(Utils.nameToTitle(lIlIIlllIlIIlll.getName())).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD})).append("\n");
                LiteralText lIlIIlllIlIlIIl = new LiteralText(String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(lIlIIlllIlIIlll.getName())));
                if (lIlIIlllIlIIlll.getAliases().size() > 0) {
                    lIlIIlllIlIlIIl.append(", ");
                    for (String lIlIIlllIlIlIll : lIlIIlllIlIIlll.getAliases()) {
                        if (lIlIIlllIlIlIll.isEmpty()) continue;
                        lIlIIlllIlIlIIl.append(String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(lIlIIlllIlIlIll)));
                        if (lIlIIlllIlIlIll.equals(lIlIIlllIlIIlll.getAliases().get(lIlIIlllIlIIlll.getAliases().size() - 1))) continue;
                        lIlIIlllIlIlIIl.append(", ");
                    }
                }
                lIlIIlllIlIlIlI.append((Text)lIlIIlllIlIlIIl.formatted(Formatting.GRAY)).append("\n\n");
                lIlIIlllIlIlIlI.append((Text)new LiteralText(lIlIIlllIlIIlll.getDescription()).formatted(Formatting.WHITE));
                LiteralText lIlIIlllIlIlIII = new LiteralText(Utils.nameToTitle(lIlIIlllIlIIlll.getName()));
                if (lIlIIlllIlIIlll != Commands.get().getAll().get(Commands.get().getAll().size() - 1)) {
                    lIlIIlllIlIlIII.append((Text)new LiteralText(", ").formatted(Formatting.GRAY));
                }
                lIlIIlllIlIlIII.setStyle(lIlIIlllIlIlIII.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)lIlIIlllIlIlIlI)).withClickEvent(new ClickEvent(ClickEvent.class_2559.SUGGEST_COMMAND, String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(lIlIIlllIlIIlll.getName())))));
                lIlIIlllIlIIlIl.append((Text)lIlIIlllIlIlIII);
            }
            ChatUtils.info((Text)lIlIIlllIlIIlIl);
            return 1;
        });
    }
}

