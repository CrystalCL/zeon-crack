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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.command.CommandSource;
import net.minecraft.util.registry.Registry;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.client.network.ClientPlayerEntity;

public class DropCommand
extends Command {
    static final boolean $assertionsDisabled = !DropCommand.class.desiredAssertionStatus();
    private static final SimpleCommandExceptionType NOT_SPECTATOR = new SimpleCommandExceptionType((Message)new LiteralText("Can't drop items while in spectator."));
    private static final DynamicCommandExceptionType NO_SUCH_ITEM = new DynamicCommandExceptionType(DropCommand::lambda$static$0);

    private int drop(PlayerConsumer playerConsumer) throws CommandSyntaxException {
        ClientPlayerEntity ClientPlayerEntity2 = DropCommand.mc.player;
        if (!$assertionsDisabled && ClientPlayerEntity2 == null) {
            throw new AssertionError();
        }
        if (ClientPlayerEntity2.isSpectator()) {
            throw NOT_SPECTATOR.create();
        }
        playerConsumer.accept(ClientPlayerEntity2);
        return 1;
    }

    private static void lambda$build$5(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
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

    private static void lambda$build$7(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        for (int i = 9; i < ClientPlayerEntity2.inventory.main.size(); ++i) {
            InvUtils.drop().slotMain(i - 9);
            if (1 > 0) continue;
            return;
        }
    }

    private static void lambda$build$11(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        for (int i = 0; i < ClientPlayerEntity2.inventory.armor.size(); ++i) {
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

    private static void lambda$build$3(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        InvUtils.drop().slotOffhand();
    }

    private static Message lambda$static$0(Object object) {
        return new LiteralText(String.valueOf(new StringBuilder().append("No such item ").append(object).append("!")));
    }

    private int lambda$build$8(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$7);
    }

    private static void lambda$build$9(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        for (int i = 0; i < ClientPlayerEntity2.inventory.size(); ++i) {
            InvUtils.drop().slot(i);
            if (-5 < 0) continue;
            return;
        }
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(DropCommand.literal("hand").executes(this::lambda$build$2))).then(DropCommand.literal("offhand").executes(this::lambda$build$4))).then(DropCommand.literal("hotbar").executes(this::lambda$build$6))).then(DropCommand.literal("inventory").executes(this::lambda$build$8))).then(DropCommand.literal("all").executes(this::lambda$build$10))).then(DropCommand.literal("armor").executes(this::lambda$build$12))).then(DropCommand.argument("item", StringArgumentType.string()).executes(this::lambda$build$14));
    }

    private static void lambda$build$13(CommandContext commandContext, ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        String string = (String)commandContext.getArgument("item", String.class);
        Item Item2 = (Item)Registry.ITEM.get(new Identifier(string.toLowerCase()));
        if (Item2 == Items.AIR) {
            throw NO_SUCH_ITEM.create((Object)string);
        }
        for (int i = 0; i < ClientPlayerEntity2.inventory.main.size(); ++i) {
            ItemStack ItemStack2 = (ItemStack)ClientPlayerEntity2.inventory.main.get(i);
            if (ItemStack2.getItem() != Item2) continue;
            InvUtils.drop().slot(i);
            if (null == null) continue;
            return;
        }
    }

    private static void lambda$build$1(ClientPlayerEntity ClientPlayerEntity2) throws CommandSyntaxException {
        ClientPlayerEntity2.dropSelectedItem(true);
    }

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$1);
    }

    private int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        return this.drop(DropCommand::lambda$build$3);
    }

    @FunctionalInterface
    private static interface PlayerConsumer {
        public void accept(ClientPlayerEntity var1) throws CommandSyntaxException;
    }
}

