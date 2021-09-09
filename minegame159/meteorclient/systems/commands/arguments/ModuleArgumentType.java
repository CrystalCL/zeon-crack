/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
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

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;

public class ModuleArgumentType
implements ArgumentType<Module> {
    private static final /* synthetic */ Collection<String> EXAMPLES;
    private static final /* synthetic */ DynamicCommandExceptionType NO_SUCH_MODULE;

    static {
        EXAMPLES = Modules.get().getAll().stream().limit(3L).map(llIllIIlIlIIII -> llIllIIlIlIIII.name).collect(Collectors.toList());
        NO_SUCH_MODULE = new DynamicCommandExceptionType(llIllIIlIlIIll -> new LiteralText(String.valueOf(new StringBuilder().append("Module with name ").append(llIllIIlIlIIll).append(" doesn't exist."))));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> llIllIIllIIIII, SuggestionsBuilder llIllIIlIllllI) {
        return CommandSource.suggestMatching(Modules.get().getAll().stream().map(llIllIIlIlIlIl -> llIllIIlIlIlIl.name), (SuggestionsBuilder)llIllIIlIllllI);
    }

    public Module parse(StringReader llIllIIllIIlIl) throws CommandSyntaxException {
        String llIllIIllIIlll = llIllIIllIIlIl.readString();
        Module llIllIIllIIllI = Modules.get().get(llIllIIllIIlll);
        if (llIllIIllIIllI == null) {
            throw NO_SUCH_MODULE.create((Object)llIllIIllIIlll);
        }
        return llIllIIllIIllI;
    }

    public static ModuleArgumentType module() {
        return new ModuleArgumentType();
    }

    public ModuleArgumentType() {
        ModuleArgumentType llIllIIllIllIl;
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

