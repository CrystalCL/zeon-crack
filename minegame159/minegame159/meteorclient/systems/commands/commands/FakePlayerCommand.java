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
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class FakePlayerCommand
extends Command {
    public static FakePlayer fakePlayer = Modules.get().get(FakePlayer.class);

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        if (this.active()) {
            FakePlayerUtils.clearFakePlayers();
        }
        return 1;
    }

    private int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        int n = (Integer)commandContext.getArgument("id", Integer.class);
        if (this.active()) {
            FakePlayerUtils.removeFakePlayer(n);
        }
        return 1;
    }

    public FakePlayerCommand() {
        super("fake-player", "Manages fake players that you can use for testing.", new String[0]);
    }

    private boolean active() {
        if (!Modules.get().isActive(FakePlayer.class)) {
            ChatUtils.moduleError(Modules.get().get(FakePlayer.class), "The FakePlayer module must be enabled to use this command.", new Object[0]);
            return false;
        }
        return true;
    }

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        if (this.active()) {
            FakePlayerUtils.spawnFakePlayer();
        }
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(FakePlayerCommand.literal("spawn").executes(this::lambda$build$0))).then(FakePlayerCommand.literal("remove").then(FakePlayerCommand.argument("id", IntegerArgumentType.integer()).executes(this::lambda$build$1)))).then(FakePlayerCommand.literal("clear").executes(this::lambda$build$2));
    }
}

