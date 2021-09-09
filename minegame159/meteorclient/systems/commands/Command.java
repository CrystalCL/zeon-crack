/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.client.MinecraftClient
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
    protected static /* synthetic */ MinecraftClient mc;
    private final /* synthetic */ String name;
    private final /* synthetic */ List<String> aliases;
    private final /* synthetic */ String description;

    protected static <T> LiteralArgumentBuilder<CommandSource> literal(String llIlllllllIIII) {
        return LiteralArgumentBuilder.literal((String)llIlllllllIIII);
    }

    public String toString(String ... llIlllllIIIIlI) {
        Command llIlllllIIIIll;
        StringBuilder llIlllllIIIIIl = new StringBuilder(llIlllllIIIIll.toString());
        for (String llIlllllIIIlII : llIlllllIIIIlI) {
            llIlllllIIIIIl.append(' ').append(llIlllllIIIlII);
        }
        return String.valueOf(llIlllllIIIIIl);
    }

    public String getName() {
        Command llIlllllIlIlIl;
        return llIlllllIlIlIl.name;
    }

    public String getDescription() {
        Command llIlllllIlIIll;
        return llIlllllIlIIll.description;
    }

    public abstract void build(LiteralArgumentBuilder<CommandSource> var1);

    public String toString() {
        Command llIlllllIIllII;
        return String.valueOf(new StringBuilder().append(Config.get().getPrefix()).append(llIlllllIIllII.name));
    }

    public List<String> getAliases() {
        Command llIlllllIIllll;
        return llIlllllIIllll.aliases;
    }

    public final void registerTo(CommandDispatcher<CommandSource> llIllllllIIllI) {
        Command llIllllllIlIIl;
        llIllllllIlIIl.register(llIllllllIIllI, llIllllllIlIIl.name);
        for (String llIllllllIlIlI : llIllllllIlIIl.aliases) {
            llIllllllIlIIl.register(llIllllllIIllI, llIllllllIlIlI);
        }
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(String llIlllllllIlIl, ArgumentType<T> llIlllllllIlII) {
        return RequiredArgumentBuilder.argument((String)llIlllllllIlIl, llIlllllllIlII);
    }

    public void register(CommandDispatcher<CommandSource> llIlllllIllllI, String llIlllllIlllIl) {
        Command llIlllllIlllll;
        LiteralArgumentBuilder llIlllllIlllII = LiteralArgumentBuilder.literal((String)llIlllllIlllIl);
        llIlllllIlllll.build((LiteralArgumentBuilder<CommandSource>)llIlllllIlllII);
        llIlllllIllllI.register(llIlllllIlllII);
    }

    public Command(String llIllllllllllI, String llIllllllllIIl, String ... llIlllllllllII) {
        Command llIllllllllIll;
        llIllllllllIll.aliases = new ArrayList<String>();
        llIllllllllIll.name = llIllllllllllI;
        llIllllllllIll.description = llIllllllllIIl;
        Collections.addAll(llIllllllllIll.aliases, llIlllllllllII);
        mc = MinecraftClient.getInstance();
    }
}

