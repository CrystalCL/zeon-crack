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
import net.minecraft.class_124;
import net.minecraft.class_2172;
import net.minecraft.class_2596;
import net.minecraft.class_2639;
import net.minecraft.class_2805;

public class PluginsCommand
extends Command {
    private Integer ticks = 0;
    private static final List<String> ANTICHEAT_LIST = Arrays.asList("nocheatplus", "negativity", "warden", "horizon", "illegalstack", "coreprotect", "exploitsx");

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        this.ticks = 0;
        MeteorClient.EVENT_BUS.subscribe(this);
        PluginsCommand.mc.field_1724.field_3944.method_2883((class_2596)new class_2805(0, "/"));
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
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
            if (receive.packet instanceof class_2639) {
                class_2639 class_26392 = (class_2639)receive.packet;
                ArrayList<String> arrayList = new ArrayList<String>();
                Suggestions suggestions = class_26392.method_11397();
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
            return String.format("%s%s(default)", class_124.field_1061, string);
        }
        if (string.contains("exploit") || string.contains("cheat") || string.contains("illegal")) {
            return String.format("%s%s(default)", class_124.field_1061, string);
        }
        return String.format("(highlight)%s(default)", string);
    }
}

