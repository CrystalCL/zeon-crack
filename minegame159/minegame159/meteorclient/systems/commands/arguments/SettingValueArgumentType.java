/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.systems.commands.arguments.SettingArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Identifier;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SettingValueArgumentType
implements ArgumentType<String> {
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static SettingValueArgumentType value() {
        return new SettingValueArgumentType();
    }

    public String parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.getRemaining();
        stringReader.setCursor(stringReader.getTotalLength());
        return string;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        Setting<?> setting;
        try {
            setting = SettingArgumentType.getSetting(commandContext);
        }
        catch (CommandSyntaxException commandSyntaxException) {
            return null;
        }
        Iterable<Identifier> iterable = setting.getIdentifierSuggestions();
        if (iterable != null) {
            return CommandSource.suggestIdentifiers(iterable, (SuggestionsBuilder)suggestionsBuilder);
        }
        return CommandSource.suggestMatching(setting.getSuggestions(), (SuggestionsBuilder)suggestionsBuilder);
    }
}

