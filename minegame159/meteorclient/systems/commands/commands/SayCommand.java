/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

public class SayCommand
extends Command {
    public SayCommand() {
        super("say", "Sends messages in chat.", new String[0]);
        SayCommand lllllllllllllllllIlllIIlllIllIll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIlllIIlllIllIII) {
        lllllllllllllllllIlllIIlllIllIII.then(SayCommand.argument("message", StringArgumentType.greedyString()).executes(lllllllllllllllllIlllIIlllIlIIIl -> {
            mc.getNetworkHandler().sendPacket((Packet)new ChatMessageC2SPacket((String)lllllllllllllllllIlllIIlllIlIIIl.getArgument("message", String.class)));
            return 1;
        }));
    }
}

