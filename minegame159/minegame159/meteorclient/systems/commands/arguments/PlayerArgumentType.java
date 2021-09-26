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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerArgumentType
implements ArgumentType<PlayerEntity> {
    private static Collection<String> EXAMPLES;
    private static final DynamicCommandExceptionType NO_SUCH_PLAYER;

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return CommandSource.suggestMatching(MinecraftClient.getInstance().world.getPlayers().stream().map(PlayerArgumentType::lambda$listSuggestions$2), (SuggestionsBuilder)suggestionsBuilder);
    }

    public PlayerEntity parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readString();
        PlayerEntity PlayerEntity2 = null;
        for (PlayerEntity PlayerEntity3 : MinecraftClient.getInstance().world.getPlayers()) {
            if (!PlayerEntity3.getGameProfile().getName().equalsIgnoreCase(string)) continue;
            PlayerEntity2 = PlayerEntity3;
            break;
        }
        if (PlayerEntity2 == null) {
            throw NO_SUCH_PLAYER.create((Object)string);
        }
        return PlayerEntity2;
    }

    private static String lambda$static$0(AbstractClientPlayerEntity AbstractClientPlayerEntity2) {
        return AbstractClientPlayerEntity2.getGameProfile().getName();
    }

    private static String lambda$listSuggestions$2(AbstractClientPlayerEntity AbstractClientPlayerEntity2) {
        return AbstractClientPlayerEntity2.getGameProfile().getName();
    }

    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    static {
        if (MinecraftClient.getInstance().world != null) {
            EXAMPLES = MinecraftClient.getInstance().world.getPlayers().stream().limit(3L).map(PlayerArgumentType::lambda$static$0).collect(Collectors.toList());
        }
        NO_SUCH_PLAYER = new DynamicCommandExceptionType(PlayerArgumentType::lambda$static$1);
    }

    private static Message lambda$static$1(Object object) {
        return new LiteralText(String.valueOf(new StringBuilder().append("Player with name ").append(object).append(" doesn't exist.")));
    }

    public static PlayerArgumentType player() {
        return new PlayerArgumentType();
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

