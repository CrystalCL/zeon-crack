/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.NoFall;
import minegame159.meteorclient.systems.modules.player.AntiHunger;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class DamageCommand
extends Command {
    private void sendPosistionPacket(double llIIIIllIllIlll, double llIIIIllIlIllIl, double llIIIIllIlIlIll, boolean llIIIIllIlIlIIl) {
        DamageCommand.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(llIIIIllIllIlll, llIIIIllIlIllIl, llIIIIllIlIlIll, llIIIIllIlIlIIl));
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llIIIIllllllllI) {
        DamageCommand llIIIlIIIIIIIIl;
        llIIIIllllllllI.then(DamageCommand.argument("damage", IntegerArgumentType.integer((int)1, (int)7)).executes(llIIIIllIIllIlI -> {
            DamageCommand llIIIIllIIlllII;
            int llIIIIllIIllIII = (Integer)llIIIIllIIllIlI.getArgument("damage", Integer.class);
            if (DamageCommand.mc.player.abilities.invulnerable) {
                ChatUtils.error("You are in invulnerable.", new Object[0]);
                return 1;
            }
            llIIIIllIIlllII.damagePlayer(llIIIIllIIllIII);
            return 1;
        }));
    }

    public DamageCommand() {
        super("damage", "Damages self", "dmg");
        DamageCommand llIIIlIIIIIIlIl;
    }

    private void damagePlayer(int llIIIIlllIlllIl) {
        DamageCommand llIIIIlllllIllI;
        boolean llIIIIllllIIIIl;
        boolean llIIIIllllIIlII = Modules.get().isActive(NoFall.class);
        if (llIIIIllllIIlII) {
            Modules.get().get(NoFall.class).toggle();
        }
        if (llIIIIllllIIIIl = Modules.get().isActive(AntiHunger.class)) {
            Modules.get().get(AntiHunger.class).toggle();
        }
        Vec3d llIIIIlllIlllll = DamageCommand.mc.player.getPos();
        for (int llIIIIlllllIlll = 0; llIIIIlllllIlll < 80; ++llIIIIlllllIlll) {
            llIIIIlllllIllI.sendPosistionPacket(llIIIIlllIlllll.x, llIIIIlllIlllll.y + (double)llIIIIlllIlllIl + 2.1, llIIIIlllIlllll.z, false);
            llIIIIlllllIllI.sendPosistionPacket(llIIIIlllIlllll.x, llIIIIlllIlllll.y + 0.05, llIIIIlllIlllll.z, false);
        }
        llIIIIlllllIllI.sendPosistionPacket(llIIIIlllIlllll.x, llIIIIlllIlllll.y, llIIIIlllIlllll.z, true);
        if (llIIIIllllIIlII) {
            Modules.get().get(NoFall.class).toggle();
        }
        if (llIIIIllllIIIIl) {
            Modules.get().get(AntiHunger.class).toggle();
        }
    }
}

