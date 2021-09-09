/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.util.Formatting
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 *  net.minecraft.text.MutableText
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    private MutableText getTooltip(Module llllllllllllllllllIIIllIIlIlllll) {
        MutableText llllllllllllllllllIIIllIIlIllllI = new LiteralText(Utils.nameToTitle(llllllllllllllllllIIIllIIlIlllll.title)).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD}).append("\n\n");
        llllllllllllllllllIIIllIIlIllllI.append((Text)new LiteralText(llllllllllllllllllIIIllIIlIlllll.description).formatted(Formatting.WHITE));
        return llllllllllllllllllIIIllIIlIllllI;
    }

    public BindsCommand() {
        super("binds", "List of all bound modules.", new String[0]);
        BindsCommand llllllllllllllllllIIIllIIllIlIIl;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllllIIIllIIllIIIll) {
        BindsCommand llllllllllllllllllIIIllIIllIIllI;
        llllllllllllllllllIIIllIIllIIIll.executes(llllllllllllllllllIIIllIIlIIllIl -> {
            List llllllllllllllllllIIIllIIlIIllII = Modules.get().getAll().stream().filter(llllllllllllllllllIIIllIIlIIIIIl -> llllllllllllllllllIIIllIIlIIIIIl.keybind.isSet()).collect(Collectors.toList());
            ChatUtils.info("--- (highlight)%d(default) bound modules ---", llllllllllllllllllIIIllIIlIIllII.size());
            for (Module llllllllllllllllllIIIllIIlIIllll : llllllllllllllllllIIIllIIlIIllII) {
                BindsCommand llllllllllllllllllIIIllIIlIIlllI;
                HoverEvent llllllllllllllllllIIIllIIlIlIIll = new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)llllllllllllllllllIIIllIIlIIlllI.getTooltip(llllllllllllllllllIIIllIIlIIllll));
                MutableText llllllllllllllllllIIIllIIlIlIIlI = new LiteralText(llllllllllllllllllIIIllIIlIIllll.title).formatted(Formatting.WHITE);
                llllllllllllllllllIIIllIIlIlIIlI.setStyle(llllllllllllllllllIIIllIIlIlIIlI.getStyle().withHoverEvent(llllllllllllllllllIIIllIIlIlIIll));
                LiteralText llllllllllllllllllIIIllIIlIlIIIl = new LiteralText(" - ");
                llllllllllllllllllIIIllIIlIlIIIl.setStyle(llllllllllllllllllIIIllIIlIlIIIl.getStyle().withHoverEvent(llllllllllllllllllIIIllIIlIlIIll));
                llllllllllllllllllIIIllIIlIlIIlI.append((Text)llllllllllllllllllIIIllIIlIlIIIl.formatted(Formatting.GRAY));
                LiteralText llllllllllllllllllIIIllIIlIlIIII = new LiteralText(llllllllllllllllllIIIllIIlIIllll.keybind.toString());
                llllllllllllllllllIIIllIIlIlIIII.setStyle(llllllllllllllllllIIIllIIlIlIIII.getStyle().withHoverEvent(llllllllllllllllllIIIllIIlIlIIll));
                llllllllllllllllllIIIllIIlIlIIlI.append((Text)llllllllllllllllllIIIllIIlIlIIII.formatted(Formatting.GRAY));
                ChatUtils.info((Text)llllllllllllllllllIIIllIIlIlIIlI);
            }
            return 1;
        });
    }
}

