/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_1132;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_642;

public class ServerCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ServerCommand::lambda$build$0);
    }

    public ServerCommand() {
        super("server", "Prints server information", new String[0]);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        if (mc.method_1496()) {
            class_1132 class_11322 = mc.method_1576();
            ChatUtils.prefixInfo("Server", "Singleplayer", new Object[0]);
            if (class_11322 != null) {
                ChatUtils.prefixInfo("Server", "Version: %s", class_11322.method_3827());
            }
            return 1;
        }
        class_642 class_6422 = mc.method_1558();
        if (class_6422 == null) {
            ChatUtils.prefixError("Server", "Couldn't obtain any server information.", new Object[0]);
            return 1;
        }
        ChatUtils.prefixInfo("Server", "IP: %s", class_6422.field_3761);
        String string = ServerCommand.mc.field_1724.method_3135();
        if (string == null) {
            string = "unknown";
        }
        ChatUtils.prefixInfo("Server", "Type: %s", string);
        class_2585 class_25852 = new class_2585("Motd: ");
        if (class_6422.field_3757 != null) {
            class_25852.method_10852(class_6422.field_3757);
        } else {
            class_25852.method_10852((class_2561)new class_2585("unknown"));
        }
        ChatUtils.info("Server", (class_2561)class_25852);
        class_2585 class_25853 = new class_2585("Version: ");
        class_25853.method_10852(class_6422.field_3760);
        ChatUtils.info("Server", (class_2561)class_25853);
        ChatUtils.prefixInfo("Server", "Protocol version: %d", class_6422.field_3756);
        return 1;
    }
}

