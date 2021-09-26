/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
    private void sendPosistionPacket(double d, double d2, double d3, boolean bl) {
        DamageCommand.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(d, d2, d3, bl));
    }

    public DamageCommand() {
        super("damage", "Damages self", "dmg");
    }

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        int n = (Integer)commandContext.getArgument("damage", Integer.class);
        if (DamageCommand.mc.player.abilities.invulnerable) {
            ChatUtils.error("You are in invulnerable.", new Object[0]);
            return 1;
        }
        this.damagePlayer(n);
        return 1;
    }

    private void damagePlayer(int n) {
        boolean bl;
        boolean bl2 = Modules.get().isActive(NoFall.class);
        if (bl2) {
            Modules.get().get(NoFall.class).toggle();
        }
        if (bl = Modules.get().isActive(AntiHunger.class)) {
            Modules.get().get(AntiHunger.class).toggle();
        }
        Vec3d Vec3d2 = DamageCommand.mc.player.getPos();
        for (int i = 0; i < 80; ++i) {
            this.sendPosistionPacket(Vec3d2.x, Vec3d2.y + (double)n + 2.1, Vec3d2.z, false);
            this.sendPosistionPacket(Vec3d2.x, Vec3d2.y + 0.05, Vec3d2.z, false);
            if (4 > 3) continue;
            return;
        }
        this.sendPosistionPacket(Vec3d2.x, Vec3d2.y, Vec3d2.z, true);
        if (bl2) {
            Modules.get().get(NoFall.class).toggle();
        }
        if (bl) {
            Modules.get().get(AntiHunger.class).toggle();
        }
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(DamageCommand.argument("damage", IntegerArgumentType.integer((int)1, (int)7)).executes(this::lambda$build$0));
    }
}

