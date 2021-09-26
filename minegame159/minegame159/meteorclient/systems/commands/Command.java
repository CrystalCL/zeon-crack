/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import minegame159.meteorclient.systems.config.Config;
import net.minecraft.command.CommandSource;
import net.minecraft.client.MinecraftClient;

public abstract class Command {
    private final String description;
    private final String name;
    protected static MinecraftClient mc;
    private final List<String> aliases = new ArrayList<String>();

    public abstract void build(LiteralArgumentBuilder<CommandSource> var1);

    public List<String> getAliases() {
        return this.aliases;
    }

    public String getName() {
        return this.name;
    }

    protected static <T> LiteralArgumentBuilder<CommandSource> literal(String string) {
        return LiteralArgumentBuilder.literal((String)string);
    }

    public Command(String string, String string2, String ... stringArray) {
        this.name = string;
        this.description = string2;
        Collections.addAll(this.aliases, stringArray);
        mc = MinecraftClient.getInstance();
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String string, ArgumentType<T> argumentType) {
        return RequiredArgumentBuilder.argument((String)string, argumentType);
    }

    public String getDescription() {
        return this.description;
    }

    public void register(CommandDispatcher<CommandSource> commandDispatcher, String string) {
        LiteralArgumentBuilder literalArgumentBuilder = LiteralArgumentBuilder.literal((String)string);
        this.build((LiteralArgumentBuilder<CommandSource>)literalArgumentBuilder);
        commandDispatcher.register(literalArgumentBuilder);
    }

    public final void registerTo(CommandDispatcher<CommandSource> commandDispatcher) {
        this.register(commandDispatcher, this.name);
        for (String string : this.aliases) {
            this.register(commandDispatcher, string);
        }
    }

    public String toString() {
        return String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(this.name));
    }

    public String toString(String ... stringArray) {
        StringBuilder stringBuilder = new StringBuilder(this.toString());
        for (String string : stringArray) {
            stringBuilder.append(' ').append(string);
            if (null == null) continue;
            return null;
        }
        return String.valueOf(stringBuilder);
    }
}

