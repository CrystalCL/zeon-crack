/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class FakePlayerCommand
extends Command {
    public static /* synthetic */ FakePlayer fakePlayer;

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIIlIllIllIlIlll) {
        FakePlayerCommand lllllllllllllllllIIlIllIllIllIII;
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)lllllllllllllllllIIlIllIllIlIlll.then(FakePlayerCommand.literal("spawn").executes(lllllllllllllllllIIlIllIllIIIllI -> {
            FakePlayerCommand lllllllllllllllllIIlIllIllIIIlll;
            if (lllllllllllllllllIIlIllIllIIIlll.active()) {
                FakePlayerUtils.spawnFakePlayer();
            }
            return 1;
        }))).then(FakePlayerCommand.literal("remove").then(FakePlayerCommand.argument("id", IntegerArgumentType.integer()).executes(lllllllllllllllllIIlIllIllIIllIl -> {
            FakePlayerCommand lllllllllllllllllIIlIllIllIIlllI;
            int lllllllllllllllllIIlIllIllIIllII = (Integer)lllllllllllllllllIIlIllIllIIllIl.getArgument("id", Integer.class);
            if (lllllllllllllllllIIlIllIllIIlllI.active()) {
                FakePlayerUtils.removeFakePlayer(lllllllllllllllllIIlIllIllIIllII);
            }
            return 1;
        })))).then(FakePlayerCommand.literal("clear").executes(lllllllllllllllllIIlIllIllIlIIll -> {
            FakePlayerCommand lllllllllllllllllIIlIllIllIlIlII;
            if (lllllllllllllllllIIlIllIllIlIlII.active()) {
                FakePlayerUtils.clearFakePlayers();
            }
            return 1;
        }));
    }

    public FakePlayerCommand() {
        super("fake-player", "Manages fake players that you can use for testing.", new String[0]);
        FakePlayerCommand lllllllllllllllllIIlIllIllIlllIl;
    }

    static {
        fakePlayer = Modules.get().get(FakePlayer.class);
    }

    private boolean active() {
        if (!Modules.get().isActive(FakePlayer.class)) {
            ChatUtils.moduleError(Modules.get().get(FakePlayer.class), "The FakePlayer module must be enabled to use this command.", new Object[0]);
            return false;
        }
        return true;
    }
}

