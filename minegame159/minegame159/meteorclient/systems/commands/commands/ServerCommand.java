/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.network.ServerInfo;

public class ServerCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ServerCommand::lambda$build$0);
    }

    public ServerCommand() {
        super("server", "Prints server information", new String[0]);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        if (mc.isIntegratedServerRunning()) {
            IntegratedServer IntegratedServer2 = mc.getServer();
            ChatUtils.prefixInfo("Server", "Singleplayer", new Object[0]);
            if (IntegratedServer2 != null) {
                ChatUtils.prefixInfo("Server", "Version: %s", IntegratedServer2.getVersion());
            }
            return 1;
        }
        ServerInfo ServerInfo2 = mc.getCurrentServerEntry();
        if (ServerInfo2 == null) {
            ChatUtils.prefixError("Server", "Couldn't obtain any server information.", new Object[0]);
            return 1;
        }
        ChatUtils.prefixInfo("Server", "IP: %s", ServerInfo2.address);
        String string = ServerCommand.mc.player.getServerBrand();
        if (string == null) {
            string = "unknown";
        }
        ChatUtils.prefixInfo("Server", "Type: %s", string);
        LiteralText LiteralText2 = new LiteralText("Motd: ");
        if (ServerInfo2.label != null) {
            LiteralText2.append(ServerInfo2.label);
        } else {
            LiteralText2.append((Text)new LiteralText("unknown"));
        }
        ChatUtils.info("Server", (Text)LiteralText2);
        LiteralText LiteralText3 = new LiteralText("Version: ");
        LiteralText3.append(ServerInfo2.version);
        ChatUtils.info("Server", (Text)LiteralText3);
        ChatUtils.prefixInfo("Server", "Protocol version: %d", ServerInfo2.protocolVersion);
        return 1;
    }
}

