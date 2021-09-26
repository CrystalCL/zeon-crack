/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

public class SayCommand
extends Command {
    public SayCommand() {
        super("say", "Sends messages in chat.", new String[0]);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(SayCommand.argument("message", StringArgumentType.greedyString()).executes(SayCommand::lambda$build$0));
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        mc.getNetworkHandler().sendPacket((Packet)new ChatMessageC2SPacket((String)commandContext.getArgument("message", String.class)));
        return 1;
    }
}

