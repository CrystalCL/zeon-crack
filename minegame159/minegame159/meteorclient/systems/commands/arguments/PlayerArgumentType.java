/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.arguments;

import com.mojang.brigadier.Message;
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
import net.minecraft.class_1657;
import net.minecraft.class_2172;
import net.minecraft.class_2585;
import net.minecraft.class_310;
import net.minecraft.class_742;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerArgumentType
implements ArgumentType<class_1657> {
    private static Collection<String> EXAMPLES;
    private static final DynamicCommandExceptionType NO_SUCH_PLAYER;

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return class_2172.method_9264(class_310.method_1551().field_1687.method_18456().stream().map(PlayerArgumentType::lambda$listSuggestions$2), (SuggestionsBuilder)suggestionsBuilder);
    }

    public class_1657 parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readString();
        class_1657 class_16572 = null;
        for (class_1657 class_16573 : class_310.method_1551().field_1687.method_18456()) {
            if (!class_16573.method_7334().getName().equalsIgnoreCase(string)) continue;
            class_16572 = class_16573;
            break;
        }
        if (class_16572 == null) {
            throw NO_SUCH_PLAYER.create((Object)string);
        }
        return class_16572;
    }

    private static String lambda$static$0(class_742 class_7422) {
        return class_7422.method_7334().getName();
    }

    private static String lambda$listSuggestions$2(class_742 class_7422) {
        return class_7422.method_7334().getName();
    }

    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    static {
        if (class_310.method_1551().field_1687 != null) {
            EXAMPLES = class_310.method_1551().field_1687.method_18456().stream().limit(3L).map(PlayerArgumentType::lambda$static$0).collect(Collectors.toList());
        }
        NO_SUCH_PLAYER = new DynamicCommandExceptionType(PlayerArgumentType::lambda$static$1);
    }

    private static Message lambda$static$1(Object object) {
        return new class_2585(String.valueOf(new StringBuilder().append("Player with name ").append(object).append(" doesn't exist.")));
    }

    public static PlayerArgumentType player() {
        return new PlayerArgumentType();
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

