/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2172;
import net.minecraft.class_2596;
import net.minecraft.class_2873;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_481;

public class Count
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.then(Count.argument("count", IntegerArgumentType.integer((int)1, (int)127)).executes(Count::lambda$build$0));
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        class_310 class_3102 = class_310.method_1551();
        if (class_3102.field_1724.method_6047().method_7909() == class_1802.field_8162) {
            return 1;
        }
        class_1799 class_17992 = class_3102.field_1724.method_6047();
        class_17992.method_7939(((Integer)commandContext.getArgument("count", Integer.class)).intValue());
        int n = Ezz.invIndexToSlotId(class_3102.field_1724.field_7514.field_7545);
        class_3102.field_1724.field_3944.method_2883((class_2596)new class_2873(n, class_17992));
        class_3102.method_1507((class_437)new class_481((class_1657)class_3102.field_1724));
        return 1;
    }

    public Count() {
        super("count", "Set item stack amount in main hand.", new String[0]);
    }
}

