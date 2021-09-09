/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

public class ClearChatCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llIIllIllIlI) {
        llIIllIllIlI.executes(llIIllIllIII -> {
            ClearChatCommand.mc.inGameHud.getChatHud().clear(false);
            return 1;
        });
    }

    public ClearChatCommand() {
        super("clear-chat", "Clears your chat.", new String[0]);
        ClearChatCommand llIIllIllllI;
    }
}

