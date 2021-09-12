/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_2172;
import net.minecraft.class_310;
import net.minecraft.class_640;

public class FriendCommand
extends Command {
    private static void lambda$build$2(Friend friend) {
        ChatUtils.info(" - (highlight)%s", friend.name);
    }

    public FriendCommand() {
        super("friend", "Manages friends.", new String[0]);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        Friend friend = FriendArgumentType.getFriend(commandContext, "friend");
        if (Friends.get().add(friend)) {
            ChatUtils.prefixInfo("Friends", "Added (highlight)%s (default)to friends.", friend.name);
        } else {
            ChatUtils.prefixError("Friends", "That person is already your friend.", new Object[0]);
        }
        return 1;
    }

    static class_310 access$000() {
        return mc;
    }

    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(FriendCommand.literal("add").then(FriendCommand.argument("friend", FriendArgumentType.friend()).executes(FriendCommand::lambda$build$0)))).then(FriendCommand.literal("remove").then(FriendCommand.argument("friend", FriendArgumentType.friend()).executes(FriendCommand::lambda$build$1)))).then(FriendCommand.literal("list").executes(FriendCommand::lambda$build$3));
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Friend friend = FriendArgumentType.getFriend(commandContext, "friend");
        if (Friends.get().remove(friend)) {
            ChatUtils.prefixInfo("Friends", "Removed (highlight)%s (default)from friends.", friend.name);
        } else {
            ChatUtils.prefixError("Friends", "That person is not your friend.", new Object[0]);
        }
        return 1;
    }

    private static int lambda$build$3(CommandContext commandContext) throws CommandSyntaxException {
        ChatUtils.prefixInfo("Friends", "You have (highlight)%d (default)friends:", Friends.get().count());
        Friends.get().forEach(FriendCommand::lambda$build$2);
        return 1;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class FriendArgumentType
    implements ArgumentType<Friend> {
        public static FriendArgumentType friend() {
            return new FriendArgumentType();
        }

        public Collection<String> getExamples() {
            return Arrays.asList("seasnail8169", "MineGame159");
        }

        public static Friend getFriend(CommandContext<?> commandContext, String string) {
            return (Friend)commandContext.getArgument(string, Friend.class);
        }

        public Friend parse(StringReader stringReader) throws CommandSyntaxException {
            return new Friend(stringReader.readString());
        }

        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
            return class_2172.method_9265((Iterable)FriendCommand.access$000().method_1562().method_2880().stream().map(FriendArgumentType::lambda$listSuggestions$0).collect(Collectors.toList()), (SuggestionsBuilder)suggestionsBuilder);
        }

        public Object parse(StringReader stringReader) throws CommandSyntaxException {
            return this.parse(stringReader);
        }

        private FriendArgumentType() {
        }

        private static String lambda$listSuggestions$0(class_640 class_6402) {
            return class_6402.method_2966().getName();
        }
    }
}

