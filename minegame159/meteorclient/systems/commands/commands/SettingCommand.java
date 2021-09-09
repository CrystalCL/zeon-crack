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
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.commands.arguments.SettingArgumentType;
import minegame159.meteorclient.systems.commands.arguments.SettingValueArgumentType;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class SettingCommand
extends Command {
    public SettingCommand() {
        super("settings", "Allows you to view and change module settings.", "s");
        SettingCommand llllIllIllllIlI;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllIllIlllIllI) {
        llllIllIlllIllI.then(SettingCommand.argument("module", ModuleArgumentType.module()).then(((RequiredArgumentBuilder)SettingCommand.argument("setting", SettingArgumentType.setting()).executes(llllIllIllIlIlI -> {
            Setting<?> llllIllIllIlIIl = SettingArgumentType.getSetting(llllIllIllIlIlI);
            ChatUtils.info("Setting (highlight)%s(default) is (highlight)%s(default).", llllIllIllIlIIl.title, llllIllIllIlIIl.get());
            return 1;
        })).then(SettingCommand.argument("value", SettingValueArgumentType.value()).executes(llllIllIllIllll -> {
            String llllIllIlllIIII;
            Setting<?> llllIllIlllIIIl = SettingArgumentType.getSetting(llllIllIllIllll);
            if (llllIllIlllIIIl.parse(llllIllIlllIIII = (String)llllIllIllIllll.getArgument("value", String.class))) {
                ChatUtils.info("Setting (highlight)%s(default) changed to (highlight)%s(default).", llllIllIlllIIIl.title, llllIllIlllIIII);
            }
            return 1;
        }))));
    }
}

