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
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.MinecraftClient
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;

public class PlayerArgumentType
implements ArgumentType<PlayerEntity> {
    private static /* synthetic */ Collection<String> EXAMPLES;
    private static final /* synthetic */ DynamicCommandExceptionType NO_SUCH_PLAYER;

    static {
        if (MinecraftClient.getInstance().world != null) {
            EXAMPLES = MinecraftClient.getInstance().world.getPlayers().stream().limit(3L).map(lllllllllllllllllllllIIIIIIllllI -> lllllllllllllllllllllIIIIIIllllI.getGameProfile().getName()).collect(Collectors.toList());
        }
        NO_SUCH_PLAYER = new DynamicCommandExceptionType(lllllllllllllllllllllIIIIIlIIIIl -> new LiteralText(String.valueOf(new StringBuilder().append("Player with name ").append(lllllllllllllllllllllIIIIIlIIIIl).append(" doesn't exist."))));
    }

    public PlayerArgumentType() {
        PlayerArgumentType lllllllllllllllllllllIIIIlIIIIII;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> lllllllllllllllllllllIIIIIlIlllI, SuggestionsBuilder lllllllllllllllllllllIIIIIlIllIl) {
        return CommandSource.suggestMatching(MinecraftClient.getInstance().world.getPlayers().stream().map(lllllllllllllllllllllIIIIIlIIlII -> lllllllllllllllllllllIIIIIlIIlII.getGameProfile().getName()), (SuggestionsBuilder)lllllllllllllllllllllIIIIIlIllIl);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public PlayerEntity parse(StringReader lllllllllllllllllllllIIIIIlllIII) throws CommandSyntaxException {
        String lllllllllllllllllllllIIIIIllIlll = lllllllllllllllllllllIIIIIlllIII.readString();
        PlayerEntity lllllllllllllllllllllIIIIIllIllI = null;
        for (PlayerEntity lllllllllllllllllllllIIIIIlllIlI : MinecraftClient.getInstance().world.getPlayers()) {
            if (!lllllllllllllllllllllIIIIIlllIlI.getGameProfile().getName().equalsIgnoreCase(lllllllllllllllllllllIIIIIllIlll)) continue;
            lllllllllllllllllllllIIIIIllIllI = lllllllllllllllllllllIIIIIlllIlI;
            break;
        }
        if (lllllllllllllllllllllIIIIIllIllI == null) {
            throw NO_SUCH_PLAYER.create((Object)lllllllllllllllllllllIIIIIllIlll);
        }
        return lllllllllllllllllllllIIIIIllIllI;
    }

    public static PlayerArgumentType player() {
        return new PlayerArgumentType();
    }
}

