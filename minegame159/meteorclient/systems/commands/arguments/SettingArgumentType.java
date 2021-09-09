/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Streams
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.LiteralText
 */
package minegame159.meteorclient.systems.commands.arguments;

import com.google.common.collect.Streams;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;

public class SettingArgumentType
implements ArgumentType<String> {
    private static final /* synthetic */ DynamicCommandExceptionType NO_SUCH_SETTING;

    public SettingArgumentType() {
        SettingArgumentType lllllllllllllllllllIIlllIIllIIlI;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> lllllllllllllllllllIIlllIIlIIlIl, SuggestionsBuilder lllllllllllllllllllIIlllIIlIIlII) {
        Stream<String> lllllllllllllllllllIIlllIIlIIllI = Streams.stream(((Module)lllllllllllllllllllIIlllIIlIIlIl.getArgument((String)"module", Module.class)).settings.iterator()).flatMap(lllllllllllllllllllIIlllIIIIllII -> Streams.stream(lllllllllllllllllllIIlllIIIIllII.iterator())).map(lllllllllllllllllllIIlllIIIIllll -> lllllllllllllllllllIIlllIIIIllll.name);
        return CommandSource.suggestMatching(lllllllllllllllllllIIlllIIlIIllI, (SuggestionsBuilder)lllllllllllllllllllIIlllIIlIIlII);
    }

    public static Setting<?> getSetting(CommandContext<?> lllllllllllllllllllIIlllIIIllIlI) throws CommandSyntaxException {
        Module lllllllllllllllllllIIlllIIIlllIl = (Module)lllllllllllllllllllIIlllIIIllIlI.getArgument("module", Module.class);
        String lllllllllllllllllllIIlllIIIlllII = (String)lllllllllllllllllllIIlllIIIllIlI.getArgument("setting", String.class);
        Setting<?> lllllllllllllllllllIIlllIIIllIll = lllllllllllllllllllIIlllIIIlllIl.settings.get(lllllllllllllllllllIIlllIIIlllII);
        if (lllllllllllllllllllIIlllIIIllIll == null) {
            throw NO_SUCH_SETTING.create((Object)lllllllllllllllllllIIlllIIIlllII);
        }
        return lllllllllllllllllllIIlllIIIllIll;
    }

    public String parse(StringReader lllllllllllllllllllIIlllIIlIlllI) throws CommandSyntaxException {
        return lllllllllllllllllllIIlllIIlIlllI.readString();
    }

    public static SettingArgumentType setting() {
        return new SettingArgumentType();
    }

    static {
        NO_SUCH_SETTING = new DynamicCommandExceptionType(lllllllllllllllllllIIlllIIIIlIIl -> new LiteralText(String.valueOf(new StringBuilder().append("No such setting '").append(lllllllllllllllllllIIlllIIIIlIIl).append("'."))));
    }
}

