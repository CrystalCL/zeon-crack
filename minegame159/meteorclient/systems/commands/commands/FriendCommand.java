/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend", "Manages friends.", new String[0]);
        FriendCommand lllllllllllllllllIlIIIlIIlllIIII;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIlIIIlIIllIllIl) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)lllllllllllllllllIlIIIlIIllIllIl.then(FriendCommand.literal("add").then(FriendCommand.argument("friend", FriendArgumentType.friend()).executes(lllllllllllllllllIlIIIlIIlIlllll -> {
            Friend lllllllllllllllllIlIIIlIIlIllllI = FriendArgumentType.getFriend(lllllllllllllllllIlIIIlIIlIlllll, "friend");
            if (Friends.get().add(lllllllllllllllllIlIIIlIIlIllllI)) {
                ChatUtils.prefixInfo("Friends", "Added (highlight)%s (default)to friends.", lllllllllllllllllIlIIIlIIlIllllI.name);
            } else {
                ChatUtils.prefixError("Friends", "That person is already your friend.", new Object[0]);
            }
            return 1;
        })))).then(FriendCommand.literal("remove").then(FriendCommand.argument("friend", FriendArgumentType.friend()).executes(lllllllllllllllllIlIIIlIIllIIIll -> {
            Friend lllllllllllllllllIlIIIlIIllIIlII = FriendArgumentType.getFriend(lllllllllllllllllIlIIIlIIllIIIll, "friend");
            if (Friends.get().remove(lllllllllllllllllIlIIIlIIllIIlII)) {
                ChatUtils.prefixInfo("Friends", "Removed (highlight)%s (default)from friends.", lllllllllllllllllIlIIIlIIllIIlII.name);
            } else {
                ChatUtils.prefixError("Friends", "That person is not your friend.", new Object[0]);
            }
            return 1;
        })))).then(FriendCommand.literal("list").executes(lllllllllllllllllIlIIIlIIllIlIll -> {
            ChatUtils.prefixInfo("Friends", "You have (highlight)%d (default)friends:", Friends.get().count());
            Friends.get().forEach(lllllllllllllllllIlIIIlIIllIlIII -> ChatUtils.info(" - (highlight)%s", lllllllllllllllllIlIIIlIIllIlIII.name));
            return 1;
        }));
    }

    private static class FriendArgumentType
    implements ArgumentType<Friend> {
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> lllllllllllllllllIllIIIlIIlIIIlI, SuggestionsBuilder lllllllllllllllllIllIIIlIIlIIIII) {
            return CommandSource.suggestMatching((Iterable)mc.getNetworkHandler().getPlayerList().stream().map(lllllllllllllllllIllIIIlIIIlIlll -> lllllllllllllllllIllIIIlIIIlIlll.getProfile().getName()).collect(Collectors.toList()), (SuggestionsBuilder)lllllllllllllllllIllIIIlIIlIIIII);
        }

        public Collection<String> getExamples() {
            return Arrays.asList("seasnail8169", "MineGame159");
        }

        public static FriendArgumentType friend() {
            return new FriendArgumentType();
        }

        public Friend parse(StringReader lllllllllllllllllIllIIIlIIlIllII) throws CommandSyntaxException {
            return new Friend(lllllllllllllllllIllIIIlIIlIllII.readString());
        }

        public static Friend getFriend(CommandContext<?> lllllllllllllllllIllIIIlIIlIlIII, String lllllllllllllllllIllIIIlIIlIIlIl) {
            return (Friend)lllllllllllllllllIllIIIlIIlIlIII.getArgument(lllllllllllllllllIllIIIlIIlIIlIl, Friend.class);
        }

        private FriendArgumentType() {
            FriendArgumentType lllllllllllllllllIllIIIlIIlIllll;
        }
    }
}

