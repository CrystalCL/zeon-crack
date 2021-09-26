/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class VClipCommand
extends Command {
    static final boolean $assertionsDisabled = !VClipCommand.class.desiredAssertionStatus();

    public VClipCommand() {
        super("vclip", "Lets you clip through blocks vertically.", new String[0]);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(VClipCommand.argument("blocks", DoubleArgumentType.doubleArg()).executes(VClipCommand::lambda$build$0));
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ClientPlayerEntity ClientPlayerEntity2 = MinecraftClient.getInstance().player;
        if (!$assertionsDisabled && ClientPlayerEntity2 == null) {
            throw new AssertionError();
        }
        double d = (Double)commandContext.getArgument("blocks", Double.class);
        ClientPlayerEntity2.setPosition(ClientPlayerEntity2.getX(), ClientPlayerEntity2.getY() + d, ClientPlayerEntity2.getZ());
        return 1;
    }
}

