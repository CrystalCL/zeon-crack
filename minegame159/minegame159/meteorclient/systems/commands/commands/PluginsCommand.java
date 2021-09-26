/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
    private Integer ticks = 0;
    private static final List<String> ANTICHEAT_LIST = Arrays.asList("nocheatplus", "negativity", "warden", "horizon", "illegalstack", "coreprotect", "exploitsx");

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        this.ticks = 0;
        MeteorClient.EVENT_BUS.subscribe(this);
        PluginsCommand.mc.player.networkHandler.sendPacket((Packet)new RequestCommandCompletionsC2SPacket(0, "/"));
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(this::lambda$build$0);
    }

    public PluginsCommand() {
        super("plugins", "Tries to get the server plugins.", new String[0]);
    }

    @EventHandler
    public void onTick(TickEvent.Post post) {
        Integer n = this.ticks;
        this.ticks = this.ticks + 1;
        if (this.ticks >= 5000) {
            ChatUtils.error("Plugins check timed out", new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(this);
            this.ticks = 0;
        }
    }

    @EventHandler
    public void onReadPacket(PacketEvent.Receive receive) {
        try {
            if (receive.packet instanceof CommandSuggestionsS2CPacket) {
                CommandSuggestionsS2CPacket CommandSuggestionsS2CPacket2 = (CommandSuggestionsS2CPacket)receive.packet;
                ArrayList<String> arrayList = new ArrayList<String>();
                Suggestions suggestions = CommandSuggestionsS2CPacket2.getSuggestions();
                if (suggestions == null) {
                    ChatUtils.error("Invalid Packet.", new Object[0]);
                    return;
                }
                for (Suggestion suggestion : suggestions.getList()) {
                    String string;
                    String[] stringArray = suggestion.getText().split(":");
                    if (stringArray.length <= 1 || arrayList.contains(string = stringArray[0].replace("/", ""))) continue;
                    arrayList.add(string);
                }
                Collections.sort(arrayList);
                for (int i = 0; i < arrayList.size(); ++i) {
                    arrayList.set(i, this.formatName((String)arrayList.get(i)));
                    if (2 >= 2) continue;
                    return;
                }
                if (!arrayList.isEmpty()) {
                    ChatUtils.info("Plugins (%d): %s ", arrayList.size(), Strings.join((String[])arrayList.toArray(new String[0]), (String)", "));
                } else {
                    ChatUtils.error("No plugins found.", new Object[0]);
                }
                this.ticks = 0;
                MeteorClient.EVENT_BUS.unsubscribe(this);
            }
        }
        catch (Exception exception) {
            ChatUtils.error("An error occurred while trying to find plugins", new Object[0]);
            this.ticks = 0;
            MeteorClient.EVENT_BUS.unsubscribe(this);
        }
    }

    private String formatName(String string) {
        if (ANTICHEAT_LIST.contains(string)) {
            return String.format("%s%s(default)", Formatting.RED, string);
        }
        if (string.contains("exploit") || string.contains("cheat") || string.contains("illegal")) {
            return String.format("%s%s(default)", Formatting.RED, string);
        }
        return String.format("(highlight)%s(default)", string);
    }
}

