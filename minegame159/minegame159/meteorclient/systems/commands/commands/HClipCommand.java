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
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class HClipCommand
extends Command {
    static final boolean $assertionsDisabled = !HClipCommand.class.desiredAssertionStatus();

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ClientPlayerEntity ClientPlayerEntity2 = MinecraftClient.getInstance().player;
        if (!$assertionsDisabled && ClientPlayerEntity2 == null) {
            throw new AssertionError();
        }
        double d = (Double)commandContext.getArgument("blocks", Double.class);
        Vec3d Vec3d2 = Vec3d.fromPolar((float)0.0f, (float)ClientPlayerEntity2.yaw).normalize();
        ClientPlayerEntity2.setPosition(ClientPlayerEntity2.getX() + Vec3d2.x * d, ClientPlayerEntity2.getY(), ClientPlayerEntity2.getZ() + Vec3d2.z * d);
        return 1;
    }

    public HClipCommand() {
        super("hclip", "Lets you clip through blocks horizontally.", new String[0]);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(HClipCommand.argument("blocks", DoubleArgumentType.doubleArg()).executes(HClipCommand::lambda$build$0));
    }
}

