/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.StringNbtReader
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

public class CompoundNbtTagArgumentType
implements ArgumentType<NbtCompound> {
    private static final /* synthetic */ Collection<String> EXAMPLES;

    public NbtCompound parse(StringReader lllIlIIllllllll) throws CommandSyntaxException {
        lllIlIIllllllll.skipWhitespace();
        if (!lllIlIIllllllll.canRead()) {
            throw StringNbtReader.EXPECTED_VALUE.createWithContext((ImmutableStringReader)lllIlIIllllllll);
        }
        StringBuilder lllIlIlIIIIIIIl = new StringBuilder();
        int lllIlIlIIIIIIII = 0;
        while (lllIlIIllllllll.canRead()) {
            if (lllIlIIllllllll.peek() == '{') {
                ++lllIlIlIIIIIIII;
            } else if (lllIlIIllllllll.peek() == '}') {
                --lllIlIlIIIIIIII;
            }
            if (lllIlIlIIIIIIII == 0) break;
            lllIlIlIIIIIIIl.append(lllIlIIllllllll.read());
        }
        lllIlIIllllllll.expect('}');
        lllIlIlIIIIIIIl.append('}');
        return StringNbtReader.parse((String)String.valueOf(lllIlIlIIIIIIIl).replace("$", "\u00a7").replace("\u00a7\u00a7", "$"));
    }

    public static CompoundNbtTagArgumentType nbtTag() {
        return new CompoundNbtTagArgumentType();
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static NbtCompound getTag(CommandContext<?> lllIlIIlllllIlI, String lllIlIIllllIlll) {
        return (NbtCompound)lllIlIIlllllIlI.getArgument(lllIlIIllllIlll, NbtCompound.class);
    }

    public CompoundNbtTagArgumentType() {
        CompoundNbtTagArgumentType lllIlIlIIIIIlll;
    }

    static {
        EXAMPLES = Arrays.asList("{foo:bar}", "{foo:[aa, bb],bar:15}");
    }
}

