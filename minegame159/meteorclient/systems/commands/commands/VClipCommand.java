/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.network.ClientPlayerEntity
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class VClipCommand
extends Command {
    public VClipCommand() {
        super("vclip", "Lets you clip through blocks vertically.", new String[0]);
        VClipCommand lIlIIIIlIlllIIl;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIlIIIIlIllIllI) {
        lIlIIIIlIllIllI.then(VClipCommand.argument("blocks", DoubleArgumentType.doubleArg()).executes(lIlIIIIlIllIIIl -> {
            ClientPlayerEntity lIlIIIIlIllIIII = MinecraftClient.getInstance().player;
            assert (lIlIIIIlIllIIII != null);
            double lIlIIIIlIlIllll = (Double)lIlIIIIlIllIIIl.getArgument("blocks", Double.class);
            lIlIIIIlIllIIII.setPosition(lIlIIIIlIllIIII.getX(), lIlIIIIlIllIIII.getY() + lIlIIIIlIlIllll, lIlIIIIlIllIIII.getZ());
            return 1;
        }));
    }
}

