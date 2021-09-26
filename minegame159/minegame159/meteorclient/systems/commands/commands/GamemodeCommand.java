/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.world.GameMode;
import net.minecraft.command.CommandSource;

public class GamemodeCommand
extends Command {
    private static int lambda$build$0(GameMode GameMode2, CommandContext commandContext) throws CommandSyntaxException {
        GamemodeCommand.mc.player.setGameMode(GameMode2);
        GamemodeCommand.mc.interactionManager.setGameMode(GameMode2);
        return 1;
    }

    public GamemodeCommand() {
        super("gamemode", "Changes your gamemode client-side.", "gm");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        for (GameMode GameMode2 : GameMode.values()) {
            if (GameMode2 == GameMode.NOT_SET) continue;
            literalArgumentBuilder.then(GamemodeCommand.literal(GameMode2.getName()).executes(arg_0 -> GamemodeCommand.lambda$build$0(GameMode2, arg_0)));
        }
    }
}

