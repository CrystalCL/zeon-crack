/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.profiles.Profile;
import minegame159.meteorclient.systems.profiles.Profiles;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;

public class ProfileCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)ProfileCommand.argument("profile", ProfileArgumentType.profile()).then(ProfileCommand.literal("load").executes(ProfileCommand::lambda$build$0))).then(ProfileCommand.literal("save").executes(ProfileCommand::lambda$build$1))).then(ProfileCommand.literal("delete").executes(ProfileCommand::lambda$build$2)));
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Profile profile = ProfileArgumentType.getProfile(commandContext, "profile");
        if (profile != null) {
            profile.save();
            ChatUtils.prefixInfo("Profiles", "Saved profile (highlight)%s(default).", profile.name);
        }
        return 1;
    }

    public ProfileCommand() {
        super("profile", "Loads and saves profiles.", new String[0]);
    }

    private static int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        Profile profile = ProfileArgumentType.getProfile(commandContext, "profile");
        if (profile != null) {
            Profiles.get().remove(profile);
            ChatUtils.prefixInfo("Profiles", "Deleted profile (highlight)%s(default).", profile.name);
        }
        return 1;
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        Profile profile = ProfileArgumentType.getProfile(commandContext, "profile");
        if (profile != null) {
            profile.load();
            ChatUtils.prefixInfo("Profiles", "Loaded profile (highlight)%s(default).", profile.name);
        }
        return 1;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ProfileArgumentType
    implements ArgumentType<String> {
        private static final DynamicCommandExceptionType NO_SUCH_PROFILE = new DynamicCommandExceptionType(ProfileArgumentType::lambda$static$0);

        public String parse(StringReader stringReader) throws CommandSyntaxException {
            String string = stringReader.readString();
            if (Profiles.get().get(string) == null) {
                throw NO_SUCH_PROFILE.create((Object)string);
            }
            return string;
        }

        public static Profile getProfile(CommandContext<?> commandContext, String string) {
            return Profiles.get().get((String)commandContext.getArgument(string, String.class));
        }

        public Object parse(StringReader stringReader) throws CommandSyntaxException {
            return this.parse(stringReader);
        }

        private static Message lambda$static$0(Object object) {
            return new LiteralText(String.valueOf(new StringBuilder().append("Profile with name ").append(object).append(" doesn't exist.")));
        }

        public static ProfileArgumentType profile() {
            return new ProfileArgumentType();
        }

        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
            return CommandSource.suggestMatching(this.getExamples(), (SuggestionsBuilder)suggestionsBuilder);
        }

        public Collection<String> getExamples() {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Profile profile : Profiles.get()) {
                arrayList.add(profile.name);
            }
            return arrayList;
        }
    }
}

