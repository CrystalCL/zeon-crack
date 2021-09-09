/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.util.Identifier
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

public class SettingValueArgumentType
implements ArgumentType<String> {
    public static SettingValueArgumentType value() {
        return new SettingValueArgumentType();
    }

    public String parse(StringReader lIlIIIIIIllIII) throws CommandSyntaxException {
        String lIlIIIIIIllIIl = lIlIIIIIIllIII.getRemaining();
        lIlIIIIIIllIII.setCursor(lIlIIIIIIllIII.getTotalLength());
        return lIlIIIIIIllIIl;
    }

    public SettingValueArgumentType() {
        SettingValueArgumentType lIlIIIIIIlllll;
    }

    /*
     * WARNING - void declaration
     */
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> lIlIIIIIIIlIll, SuggestionsBuilder lIlIIIIIIIlIlI) {
        void lIlIIIIIIIllIl;
        try {
            Setting<?> lIlIIIIIIlIIlI = SettingArgumentType.getSetting(lIlIIIIIIIlIll);
        }
        catch (CommandSyntaxException lIlIIIIIIlIIIl) {
            return null;
        }
        Iterable<Identifier> lIlIIIIIIIllII = lIlIIIIIIIllIl.getIdentifierSuggestions();
        if (lIlIIIIIIIllII != null) {
            return CommandSource.suggestIdentifiers(lIlIIIIIIIllII, (SuggestionsBuilder)lIlIIIIIIIlIlI);
        }
        return CommandSource.suggestMatching(lIlIIIIIIIllIl.getSuggestions(), (SuggestionsBuilder)lIlIIIIIIIlIlI);
    }
}

