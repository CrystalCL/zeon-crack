/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.arguments;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompoundNbtTagArgumentType
implements ArgumentType<NbtCompound> {
    private static final Collection<String> EXAMPLES = Arrays.asList("{foo:bar}", "{foo:[aa, bb],bar:15}");

    public static CompoundNbtTagArgumentType nbtTag() {
        return new CompoundNbtTagArgumentType();
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public NbtCompound parse(StringReader stringReader) throws CommandSyntaxException {
        stringReader.skipWhitespace();
        if (!stringReader.canRead()) {
            throw StringNbtReader.EXPECTED_VALUE.createWithContext((ImmutableStringReader)stringReader);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (stringReader.canRead()) {
            if (stringReader.peek() == '{') {
                ++n;
                if (-2 >= 0) {
                    return null;
                }
            } else if (stringReader.peek() == '}') {
                --n;
            }
            if (n == 0) break;
            stringBuilder.append(stringReader.read());
        }
        stringReader.expect('}');
        stringBuilder.append('}');
        return StringNbtReader.parse((String)String.valueOf(stringBuilder).replace("$", "\u00a7").replace("\u00a7\u00a7", "$"));
    }

    public static NbtCompound getTag(CommandContext<?> commandContext, String string) {
        return (NbtCompound)commandContext.getArgument(string, NbtCompound.class);
    }
}

