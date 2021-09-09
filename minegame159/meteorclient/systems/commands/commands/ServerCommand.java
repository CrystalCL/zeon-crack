/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.server.integrated.IntegratedServer
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.network.ServerInfo
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.network.ServerInfo;

public class ServerCommand
extends Command {
    public ServerCommand() {
        super("server", "Prints server information", new String[0]);
        ServerCommand lllllllllllllllllIlIllIIIIIlllII;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIlIllIIIIIlIlll) {
        lllllllllllllllllIlIllIIIIIlIlll.executes(lllllllllllllllllIlIllIIIIIlIIIl -> {
            if (mc.isIntegratedServerRunning()) {
                IntegratedServer lllllllllllllllllIlIllIIIIIlIIlI = mc.getServer();
                ChatUtils.prefixInfo("Server", "Singleplayer", new Object[0]);
                if (lllllllllllllllllIlIllIIIIIlIIlI != null) {
                    ChatUtils.prefixInfo("Server", "Version: %s", lllllllllllllllllIlIllIIIIIlIIlI.getVersion());
                }
                return 1;
            }
            ServerInfo lllllllllllllllllIlIllIIIIIlIIII = mc.getCurrentServerEntry();
            if (lllllllllllllllllIlIllIIIIIlIIII == null) {
                ChatUtils.prefixError("Server", "Couldn't obtain any server information.", new Object[0]);
                return 1;
            }
            ChatUtils.prefixInfo("Server", "IP: %s", lllllllllllllllllIlIllIIIIIlIIII.address);
            String lllllllllllllllllIlIllIIIIIIllll = ServerCommand.mc.player.getServerBrand();
            if (lllllllllllllllllIlIllIIIIIIllll == null) {
                lllllllllllllllllIlIllIIIIIIllll = "unknown";
            }
            ChatUtils.prefixInfo("Server", "Type: %s", lllllllllllllllllIlIllIIIIIIllll);
            LiteralText lllllllllllllllllIlIllIIIIIIlllI = new LiteralText("Motd: ");
            if (lllllllllllllllllIlIllIIIIIlIIII.label != null) {
                lllllllllllllllllIlIllIIIIIIlllI.append(lllllllllllllllllIlIllIIIIIlIIII.label);
            } else {
                lllllllllllllllllIlIllIIIIIIlllI.append((Text)new LiteralText("unknown"));
            }
            ChatUtils.info("Server", (Text)lllllllllllllllllIlIllIIIIIIlllI);
            LiteralText lllllllllllllllllIlIllIIIIIIllIl = new LiteralText("Version: ");
            lllllllllllllllllIlIllIIIIIIllIl.append(lllllllllllllllllIlIllIIIIIlIIII.version);
            ChatUtils.info("Server", (Text)lllllllllllllllllIlIllIIIIIIllIl);
            ChatUtils.prefixInfo("Server", "Protocol version: %d", lllllllllllllllllIlIllIIIIIlIIII.protocolVersion);
            return 1;
        });
    }
}

