/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.text.LiteralText
 */
package minegame159.meteorclient.systems.commands.commands;

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
    public ProfileCommand() {
        super("profile", "Loads and saves profiles.", new String[0]);
        ProfileCommand lIlIllIIllIlll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIlIllIIllIIll) {
        lIlIllIIllIIll.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)ProfileCommand.argument("profile", ProfileArgumentType.profile()).then(ProfileCommand.literal("load").executes(lIlIllIIlIIIlI -> {
            Profile lIlIllIIlIIIll = ProfileArgumentType.getProfile(lIlIllIIlIIIlI, "profile");
            if (lIlIllIIlIIIll != null) {
                lIlIllIIlIIIll.load();
                ChatUtils.prefixInfo("Profiles", "Loaded profile (highlight)%s(default).", lIlIllIIlIIIll.name);
            }
            return 1;
        }))).then(ProfileCommand.literal("save").executes(lIlIllIIlIlIlI -> {
            Profile lIlIllIIlIlIIl = ProfileArgumentType.getProfile(lIlIllIIlIlIlI, "profile");
            if (lIlIllIIlIlIIl != null) {
                lIlIllIIlIlIIl.save();
                ChatUtils.prefixInfo("Profiles", "Saved profile (highlight)%s(default).", lIlIllIIlIlIIl.name);
            }
            return 1;
        }))).then(ProfileCommand.literal("delete").executes(lIlIllIIllIIII -> {
            Profile lIlIllIIlIllll = ProfileArgumentType.getProfile(lIlIllIIllIIII, "profile");
            if (lIlIllIIlIllll != null) {
                Profiles.get().remove(lIlIllIIlIllll);
                ChatUtils.prefixInfo("Profiles", "Deleted profile (highlight)%s(default).", lIlIllIIlIllll.name);
            }
            return 1;
        })));
    }

    public static class ProfileArgumentType
    implements ArgumentType<String> {
        private static final /* synthetic */ DynamicCommandExceptionType NO_SUCH_PROFILE;

        public String parse(StringReader lllllllllllllllllIIIIIIIlIIlllIl) throws CommandSyntaxException {
            String lllllllllllllllllIIIIIIIlIIllllI = lllllllllllllllllIIIIIIIlIIlllIl.readString();
            if (Profiles.get().get(lllllllllllllllllIIIIIIIlIIllllI) == null) {
                throw NO_SUCH_PROFILE.create((Object)lllllllllllllllllIIIIIIIlIIllllI);
            }
            return lllllllllllllllllIIIIIIIlIIllllI;
        }

        public static Profile getProfile(CommandContext<?> lllllllllllllllllIIIIIIIlIlIIlII, String lllllllllllllllllIIIIIIIlIlIIIll) {
            return Profiles.get().get((String)lllllllllllllllllIIIIIIIlIlIIlII.getArgument(lllllllllllllllllIIIIIIIlIlIIIll, String.class));
        }

        public static ProfileArgumentType profile() {
            return new ProfileArgumentType();
        }

        public ProfileArgumentType() {
            ProfileArgumentType lllllllllllllllllIIIIIIIlIlIlIlI;
        }

        static {
            NO_SUCH_PROFILE = new DynamicCommandExceptionType(lllllllllllllllllIIIIIIIlIIIIlII -> new LiteralText(String.valueOf(new StringBuilder().append("Profile with name ").append(lllllllllllllllllIIIIIIIlIIIIlII).append(" doesn't exist."))));
        }

        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> lllllllllllllllllIIIIIIIlIIllIII, SuggestionsBuilder lllllllllllllllllIIIIIIIlIIlIlIl) {
            ProfileArgumentType lllllllllllllllllIIIIIIIlIIlIllI;
            return CommandSource.suggestMatching(lllllllllllllllllIIIIIIIlIIlIllI.getExamples(), (SuggestionsBuilder)lllllllllllllllllIIIIIIIlIIlIlIl);
        }

        public Collection<String> getExamples() {
            ArrayList<String> lllllllllllllllllIIIIIIIlIIIllll = new ArrayList<String>();
            for (Profile lllllllllllllllllIIIIIIIlIIlIIIl : Profiles.get()) {
                lllllllllllllllllIIIIIIIlIIIllll.add(lllllllllllllllllIIIIIIIlIIlIIIl.name);
            }
            return lllllllllllllllllIIIIIIIlIIIllll;
        }
    }
}

