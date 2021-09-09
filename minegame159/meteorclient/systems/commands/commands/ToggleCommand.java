/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.command.CommandSource;

public class ToggleCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIllIlllIIIlllIl) {
        lllllllllllllllllIllIlllIIIlllIl.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)ToggleCommand.argument("module", ModuleArgumentType.module()).executes(lllllllllllllllllIllIlllIIIIllIl -> {
            Module lllllllllllllllllIllIlllIIIIllII = (Module)lllllllllllllllllIllIlllIIIIllIl.getArgument("module", Module.class);
            lllllllllllllllllIllIlllIIIIllII.toggle();
            lllllllllllllllllIllIlllIIIIllII.sendToggledMsg();
            return 1;
        })).then(ToggleCommand.literal("on").executes(lllllllllllllllllIllIlllIIIlIIIl -> {
            Module lllllllllllllllllIllIlllIIIlIIlI = (Module)lllllllllllllllllIllIlllIIIlIIIl.getArgument("module", Module.class);
            if (!lllllllllllllllllIllIlllIIIlIIlI.isActive()) {
                lllllllllllllllllIllIlllIIIlIIlI.toggle();
            }
            lllllllllllllllllIllIlllIIIlIIlI.sendToggledMsg();
            return 1;
        }))).then(ToggleCommand.literal("off").executes(lllllllllllllllllIllIlllIIIllIIl -> {
            Module lllllllllllllllllIllIlllIIIllIII = (Module)lllllllllllllllllIllIlllIIIllIIl.getArgument("module", Module.class);
            if (lllllllllllllllllIllIlllIIIllIII.isActive()) {
                lllllllllllllllllIllIlllIIIllIII.toggle();
            }
            lllllllllllllllllIllIlllIIIllIII.sendToggledMsg();
            return 1;
        })));
    }

    public ToggleCommand() {
        super("toggle", "Toggles a module.", "t");
        ToggleCommand lllllllllllllllllIllIlllIIlIIIIl;
    }
}

