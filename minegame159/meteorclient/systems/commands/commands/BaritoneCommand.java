/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

public class BaritoneCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIIllllIIllllIl) {
        lIIllllIIllllIl.then(BaritoneCommand.argument("command", StringArgumentType.greedyString()).executes(lIIllllIIlllIlI -> {
            String lIIllllIIlllIIl = (String)lIIllllIIlllIlI.getArgument("command", String.class);
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(lIIllllIIlllIIl);
            return 1;
        }));
    }

    public BaritoneCommand() {
        super("baritone", "Executes baritone commands.", "b");
        BaritoneCommand lIIllllIlIIIIIl;
    }
}

