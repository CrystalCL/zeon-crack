/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.world.GameMode
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.world.GameMode;
import net.minecraft.command.CommandSource;

public class GamemodeCommand
extends Command {
    public GamemodeCommand() {
        super("gamemode", "Changes your gamemode client-side.", "gm");
        GamemodeCommand lllllllllllllllllllIllIlllIIllII;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllllIllIlllIIIIll) {
        for (GameMode lllllllllllllllllllIllIlllIIIlIl : GameMode.values()) {
            if (lllllllllllllllllllIllIlllIIIlIl == GameMode.NOT_SET) continue;
            lllllllllllllllllllIllIlllIIIIll.then(GamemodeCommand.literal(lllllllllllllllllllIllIlllIIIlIl.getName()).executes(lllllllllllllllllllIllIllIlllIll -> {
                GamemodeCommand.mc.player.setGameMode(lllllllllllllllllllIllIlllIIIlIl);
                GamemodeCommand.mc.interactionManager.setGameMode(lllllllllllllllllllIllIlllIIIlIl);
                return 1;
            }));
        }
    }
}

