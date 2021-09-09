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
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllIllIlIIIIlllIIII) {
        llllllllllllllllIllIlIIIIlllIIII.executes(llllllllllllllllIllIlIIIIllIIlll -> {
            ChatUtils.info("--- All (highlight)%d(default) Modules ---", Modules.get().getCount());
            LiteralText llllllllllllllllIllIlIIIIllIIllI = new LiteralText("");
            for (Module llllllllllllllllIllIlIIIIllIlIII : Modules.get().getList()) {
                LiteralText llllllllllllllllIllIlIIIIllIlIlI = new LiteralText("");
                llllllllllllllllIllIlIIIIllIlIlI.append((Text)new LiteralText(llllllllllllllllIllIlIIIIllIlIII.title).formatted(new Formatting[]{Formatting.BLUE, Formatting.BOLD})).append("\n");
                llllllllllllllllIllIlIIIIllIlIlI.append((Text)new LiteralText(llllllllllllllllIllIlIIIIllIlIII.name).formatted(Formatting.GRAY)).append("\n\n");
                llllllllllllllllIllIlIIIIllIlIlI.append((Text)new LiteralText(llllllllllllllllIllIlIIIIllIlIII.description).formatted(Formatting.WHITE));
                LiteralText llllllllllllllllIllIlIIIIllIlIIl = new LiteralText(llllllllllllllllIllIlIIIIllIlIII.title);
                if (llllllllllllllllIllIlIIIIllIlIII != Modules.get().getList().get(Modules.get().getAll().size() - 1)) {
                    llllllllllllllllIllIlIIIIllIIllI.append((Text)new LiteralText(", ").formatted(Formatting.GRAY));
                }
                llllllllllllllllIllIlIIIIllIlIIl.setStyle(llllllllllllllllIllIlIIIIllIlIIl.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)llllllllllllllllIllIlIIIIllIlIlI)));
                llllllllllllllllIllIlIIIIllIIllI.append((Text)llllllllllllllllIllIlIIIIllIlIIl);
            }
            ChatUtils.info((Text)llllllllllllllllIllIlIIIIllIIllI);
            return 1;
        });
    }

    public ModulesCommand() {
        super("modules", "Displays a list of all modules.", "features");
        ModulesCommand llllllllllllllllIllIlIIIIlllIlII;
    }
}

