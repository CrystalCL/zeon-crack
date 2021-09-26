/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EnchantmentArgumentType;
import net.minecraft.util.registry.Registry;
import net.minecraft.text.LiteralText;

public class EnchantCommand
extends Command {
    private static final SimpleCommandExceptionType NOT_IN_CREATIVE = new SimpleCommandExceptionType((Message)new LiteralText("You must be in creative mode to use this."));

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        if (!EnchantCommand.mc.player.isCreative()) {
            throw NOT_IN_CREATIVE.create();
        }
        ItemStack ItemStack2 = this.getItemStack();
        if (ItemStack2 != null) {
            Enchantment Enchantment2 = (Enchantment)commandContext.getArgument("enchantment", Enchantment.class);
            int n = (Integer)commandContext.getArgument("level", Integer.class);
            Utils.addEnchantment(ItemStack2, Enchantment2, n);
        }
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(EnchantCommand.literal("one").then(EnchantCommand.argument("enchantment", EnchantmentArgumentType.enchantment()).then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(this::lambda$build$0))));
        literalArgumentBuilder.then(EnchantCommand.literal("all_possible").then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(this::lambda$build$1)));
        literalArgumentBuilder.then(EnchantCommand.literal("all").then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(this::lambda$build$2)));
    }

    private int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        if (!EnchantCommand.mc.player.isCreative()) {
            throw NOT_IN_CREATIVE.create();
        }
        ItemStack ItemStack2 = this.getItemStack();
        if (ItemStack2 != null) {
            int n = (Integer)commandContext.getArgument("level", Integer.class);
            for (Enchantment Enchantment2 : Registry.ENCHANTMENT) {
                if (!Enchantment2.isAcceptableItem(ItemStack2)) continue;
                Utils.addEnchantment(ItemStack2, Enchantment2, n);
            }
        }
        return 1;
    }

    public EnchantCommand() {
        super("enchant", "Enchants the item in your hand. REQUIRES Creative mode.", new String[0]);
    }

    private ItemStack getItemStack() {
        ItemStack ItemStack2 = EnchantCommand.mc.player.getMainHandStack();
        if (ItemStack2 == null) {
            ItemStack2 = EnchantCommand.mc.player.getOffHandStack();
        }
        return ItemStack2;
    }

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        if (!EnchantCommand.mc.player.isCreative()) {
            throw NOT_IN_CREATIVE.create();
        }
        ItemStack ItemStack2 = this.getItemStack();
        if (ItemStack2 != null) {
            int n = (Integer)commandContext.getArgument("level", Integer.class);
            for (Enchantment Enchantment2 : Registry.ENCHANTMENT) {
                Utils.addEnchantment(ItemStack2, Enchantment2, n);
            }
        }
        return 1;
    }
}

