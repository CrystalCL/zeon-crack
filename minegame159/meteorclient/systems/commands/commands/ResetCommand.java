/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class ResetCommand
extends Command {
    public ResetCommand() {
        super("reset", "Resets specified settings.", new String[0]);
        ResetCommand llllllllllllllllIlIlIllllIIIIIll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllIlIlIlllIllllllI) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)llllllllllllllllIlIlIlllIllllllI.then(((LiteralArgumentBuilder)ResetCommand.literal("settings").then(ResetCommand.argument("module", ModuleArgumentType.module()).executes(llllllllllllllllIlIlIlllIllIIlll -> {
            Module llllllllllllllllIlIlIlllIllIlIII = (Module)llllllllllllllllIlIlIlllIllIIlll.getArgument("module", Module.class);
            llllllllllllllllIlIlIlllIllIlIII.settings.forEach(llllllllllllllllIlIlIlllIllIIIll -> llllllllllllllllIlIlIlllIllIIIll.forEach(Setting::reset));
            return 1;
        }))).then(ResetCommand.literal("all").executes(llllllllllllllllIlIlIlllIlllIIlI -> {
            Modules.get().getAll().forEach(llllllllllllllllIlIlIlllIllIllll -> llllllllllllllllIlIlIlllIllIllll.settings.forEach(llllllllllllllllIlIlIlllIllIllII -> llllllllllllllllIlIlIlllIllIllII.forEach(Setting::reset)));
            return 1;
        })))).then(ResetCommand.literal("gui").executes(llllllllllllllllIlIlIlllIlllIIll -> {
            ChatUtils.info("The ClickGUI positioning has been reset.", new Object[0]);
            return 1;
        }))).then(((LiteralArgumentBuilder)ResetCommand.literal("bind").then(ResetCommand.argument("module", ModuleArgumentType.module()).executes(llllllllllllllllIlIlIlllIlllIlIl -> {
            Module llllllllllllllllIlIlIlllIlllIllI = (Module)llllllllllllllllIlIlIlllIlllIlIl.getArgument("module", Module.class);
            llllllllllllllllIlIlIlllIlllIllI.keybind.set(true, -1);
            ChatUtils.prefixInfo("KeyBinds", "This bind has been reset.", new Object[0]);
            return 1;
        }))).then(ResetCommand.literal("all").executes(llllllllllllllllIlIlIlllIlllllIl -> {
            Modules.get().getAll().forEach(llllllllllllllllIlIlIlllIllllIlI -> llllllllllllllllIlIlIlllIllllIlI.keybind.set(true, -1));
            return 1;
        })));
    }
}

