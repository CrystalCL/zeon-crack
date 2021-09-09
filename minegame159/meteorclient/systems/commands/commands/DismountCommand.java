/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;

public class DismountCommand
extends Command {
    public DismountCommand() {
        super("dismount", "Dismounts you from entity you are riding.", new String[0]);
        DismountCommand llllllllllllllllIllIIllllIlllIlI;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllIllIIllllIllIlll) {
        llllllllllllllllIllIIllllIllIlll.executes(llllllllllllllllIllIIllllIllIlIl -> {
            mc.getNetworkHandler().sendPacket((Packet)new PlayerInputC2SPacket(0.0f, 0.0f, false, true));
            return 1;
        });
    }
}

