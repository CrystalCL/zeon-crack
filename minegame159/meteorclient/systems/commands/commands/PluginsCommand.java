/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 *  joptsimple.internal.Strings
 *  net.minecraft.util.Formatting
 *  net.minecraft.command.CommandSource
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket
 *  net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import joptsimple.internal.Strings;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.command.CommandSource;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;

public class PluginsCommand
extends Command {
    private static final /* synthetic */ List<String> ANTICHEAT_LIST;
    private /* synthetic */ Integer ticks;

    @EventHandler
    public void onReadPacket(PacketEvent.Receive lllllllllllllllllIIIIllIllllIIII) {
        PluginsCommand lllllllllllllllllIIIIllIllllIIll;
        try {
            if (lllllllllllllllllIIIIllIllllIIII.packet instanceof CommandSuggestionsS2CPacket) {
                CommandSuggestionsS2CPacket lllllllllllllllllIIIIllIllllIlll = (CommandSuggestionsS2CPacket)lllllllllllllllllIIIIllIllllIIII.packet;
                ArrayList<String> lllllllllllllllllIIIIllIllllIllI = new ArrayList<String>();
                Suggestions lllllllllllllllllIIIIllIllllIlIl = lllllllllllllllllIIIIllIllllIlll.getSuggestions();
                if (lllllllllllllllllIIIIllIllllIlIl == null) {
                    ChatUtils.error("Invalid Packet.", new Object[0]);
                    return;
                }
                for (Suggestion lllllllllllllllllIIIIllIlllllIIl : lllllllllllllllllIIIIllIllllIlIl.getList()) {
                    String lllllllllllllllllIIIIllIlllllIll;
                    String[] lllllllllllllllllIIIIllIlllllIlI = lllllllllllllllllIIIIllIlllllIIl.getText().split(":");
                    if (lllllllllllllllllIIIIllIlllllIlI.length <= 1 || lllllllllllllllllIIIIllIllllIllI.contains(lllllllllllllllllIIIIllIlllllIll = lllllllllllllllllIIIIllIlllllIlI[0].replace("/", ""))) continue;
                    lllllllllllllllllIIIIllIllllIllI.add(lllllllllllllllllIIIIllIlllllIll);
                }
                Collections.sort(lllllllllllllllllIIIIllIllllIllI);
                for (int lllllllllllllllllIIIIllIlllllIII = 0; lllllllllllllllllIIIIllIlllllIII < lllllllllllllllllIIIIllIllllIllI.size(); ++lllllllllllllllllIIIIllIlllllIII) {
                    lllllllllllllllllIIIIllIllllIllI.set(lllllllllllllllllIIIIllIlllllIII, lllllllllllllllllIIIIllIllllIIll.formatName((String)lllllllllllllllllIIIIllIllllIllI.get(lllllllllllllllllIIIIllIlllllIII)));
                }
                if (!lllllllllllllllllIIIIllIllllIllI.isEmpty()) {
                    ChatUtils.info("Plugins (%d): %s ", lllllllllllllllllIIIIllIllllIllI.size(), Strings.join((String[])lllllllllllllllllIIIIllIllllIllI.toArray(new String[0]), (String)", "));
                } else {
                    ChatUtils.error("No plugins found.", new Object[0]);
                }
                lllllllllllllllllIIIIllIllllIIll.ticks = 0;
                MeteorClient.EVENT_BUS.unsubscribe(lllllllllllllllllIIIIllIllllIIll);
            }
        }
        catch (Exception lllllllllllllllllIIIIllIllllIlII) {
            ChatUtils.error("An error occurred while trying to find plugins", new Object[0]);
            lllllllllllllllllIIIIllIllllIIll.ticks = 0;
            MeteorClient.EVENT_BUS.unsubscribe(lllllllllllllllllIIIIllIllllIIll);
        }
    }

    public PluginsCommand() {
        super("plugins", "Tries to get the server plugins.", new String[0]);
        PluginsCommand lllllllllllllllllIIIIlllIIIlIIlI;
        lllllllllllllllllIIIIlllIIIlIIlI.ticks = 0;
    }

    private String formatName(String lllllllllllllllllIIIIllIlllIIllI) {
        if (ANTICHEAT_LIST.contains(lllllllllllllllllIIIIllIlllIIllI)) {
            return String.format("%s%s(default)", new Object[]{Formatting.RED, lllllllllllllllllIIIIllIlllIIllI});
        }
        if (lllllllllllllllllIIIIllIlllIIllI.contains("exploit") || lllllllllllllllllIIIIllIlllIIllI.contains("cheat") || lllllllllllllllllIIIIllIlllIIllI.contains("illegal")) {
            return String.format("%s%s(default)", new Object[]{Formatting.RED, lllllllllllllllllIIIIllIlllIIllI});
        }
        return String.format("(highlight)%s(default)", lllllllllllllllllIIIIllIlllIIllI);
    }

    @EventHandler
    public void onTick(TickEvent.Post lllllllllllllllllIIIIlllIIIIIlll) {
        PluginsCommand lllllllllllllllllIIIIlllIIIIIllI;
        Integer lllllllllllllllllIIIIlllIIIIIlIl = lllllllllllllllllIIIIlllIIIIIllI.ticks;
        lllllllllllllllllIIIIlllIIIIIllI.ticks = lllllllllllllllllIIIIlllIIIIIllI.ticks + 1;
        if (lllllllllllllllllIIIIlllIIIIIllI.ticks >= 5000) {
            ChatUtils.error("Plugins check timed out", new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(lllllllllllllllllIIIIlllIIIIIllI);
            lllllllllllllllllIIIIlllIIIIIllI.ticks = 0;
        }
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIIIIlllIIIIlIll) {
        PluginsCommand lllllllllllllllllIIIIlllIIIIlllI;
        lllllllllllllllllIIIIlllIIIIlIll.executes(lllllllllllllllllIIIIllIlllIIIlI -> {
            PluginsCommand lllllllllllllllllIIIIllIlllIIIll;
            lllllllllllllllllIIIIllIlllIIIll.ticks = 0;
            MeteorClient.EVENT_BUS.subscribe(lllllllllllllllllIIIIllIlllIIIll);
            PluginsCommand.mc.player.networkHandler.sendPacket((Packet)new RequestCommandCompletionsC2SPacket(0, "/"));
            return 1;
        });
    }

    static {
        ANTICHEAT_LIST = Arrays.asList("nocheatplus", "negativity", "warden", "horizon", "illegalstack", "coreprotect", "exploitsx");
    }
}

