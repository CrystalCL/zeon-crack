/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

public class ReloadCommand
extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, macros and accounts.", new String[0]);
        ReloadCommand lllllllllllllllllllIIIIlIllIIlll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllllIIIIlIllIIIll) {
        lllllllllllllllllllIIIIlIllIIIll.executes(lllllllllllllllllllIIIIlIllIIIIl -> {
            Systems.load();
            return 1;
        });
    }
}

