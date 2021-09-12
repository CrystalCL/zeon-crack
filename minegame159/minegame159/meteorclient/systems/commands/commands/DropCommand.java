/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2172;
import net.minecraft.class_2378;
import net.minecraft.class_2585;
import net.minecraft.class_2960;
import net.minecraft.class_746;

public class DropCommand
extends Command {
    static final boolean $assertionsDisabled = !DropCommand.class.desiredAssertionStatus();
    private static final SimpleCommandExceptionType NOT_SPECTATOR = new SimpleCommandExceptionType((Message)new class_2585("Can't drop items while in spectator."));
    private static final DynamicCommandExceptionType NO_SUCH_ITEM = new DynamicCommandExceptionType(DropCommand::lambda$static$0);

    private int drop(PlayerConsumer playerConsumer) throws CommandSyntaxException {
        class_746 class_7462 = DropCommand.mc.field_1724;
        if (!$assertionsDisabled && class_7462 == null) {
            throw new AssertionError();
        }
        if (class_7462.method_7325()) {
            throw NOT_SPECTATOR.create();
        }
        playerConsumer.accept(class_7462);
        return 1;
    }

    private static void lambda$build$5(class_746 class_7462) throws CommandSyntaxException {
        for (int i = 0; i < 9; ++i) {
            InvUtils.drop().slotHotbar(i);
            if (2 > -1) continue;
            return;
        }
    }

    private int lambda$build$10(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$9);
    }

    private int lambda$build$6(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$5);
    }

    private static void lambda$build$7(class_746 class_7462) throws CommandSyntaxException {
        for (int i = 9; i < class_7462.field_7514.field_7547.size(); ++i) {
            InvUtils.drop().slotMain(i - 9);
            if (1 > 0) continue;
            return;
        }
    }

    private static void lambda$build$11(class_746 class_7462) throws CommandSyntaxException {
        for (int i = 0; i < class_7462.field_7514.field_7548.size(); ++i) {
            InvUtils.drop().slotArmor(i);
            if (true <= true) continue;
            return;
        }
    }

    public DropCommand() {
        super("drop", "Automatically drops specified items.", new String[0]);
    }

    private int lambda$build$12(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$11);
    }

    private int lambda$build$14(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(arg_0 -> DropCommand.lambda$build$13(commandContext, arg_0));
    }

    private static void lambda$build$3(class_746 class_7462) throws CommandSyntaxException {
        InvUtils.drop().slotOffhand();
    }

    private static Message lambda$static$0(Object object) {
        return new class_2585(String.valueOf(new StringBuilder().append("No such item ").append(object).append("!")));
    }

    private int lambda$build$8(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$7);
    }

    private static void lambda$build$9(class_746 class_7462) throws CommandSyntaxException {
        for (int i = 0; i < class_7462.field_7514.method_5439(); ++i) {
            InvUtils.drop().slot(i);
            if (-5 < 0) continue;
            return;
        }
    }

    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(DropCommand.literal("hand").executes(this::lambda$build$2))).then(DropCommand.literal("offhand").executes(this::lambda$build$4))).then(DropCommand.literal("hotbar").executes(this::lambda$build$6))).then(DropCommand.literal("inventory").executes(this::lambda$build$8))).then(DropCommand.literal("all").executes(this::lambda$build$10))).then(DropCommand.literal("armor").executes(this::lambda$build$12))).then(DropCommand.argument("item", StringArgumentType.string()).executes(this::lambda$build$14));
    }

    private static void lambda$build$13(CommandContext commandContext, class_746 class_7462) throws CommandSyntaxException {
        String string = (String)commandContext.getArgument("item", String.class);
        class_1792 class_17922 = (class_1792)class_2378.field_11142.method_10223(new class_2960(string.toLowerCase()));
        if (class_17922 == class_1802.field_8162) {
            throw NO_SUCH_ITEM.create((Object)string);
        }
        for (int i = 0; i < class_7462.field_7514.field_7547.size(); ++i) {
            class_1799 class_17992 = (class_1799)class_7462.field_7514.field_7547.get(i);
            if (class_17992.method_7909() != class_17922) continue;
            InvUtils.drop().slot(i);
            if (null == null) continue;
            return;
        }
    }

    private static void lambda$build$1(class_746 class_7462) throws CommandSyntaxException {
        class_7462.method_7290(true);
    }

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$1);
    }

    private int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$3);
    }

    @FunctionalInterface
    private static interface PlayerConsumer {
        public void accept(class_746 var1) throws CommandSyntaxException;
    }
}

