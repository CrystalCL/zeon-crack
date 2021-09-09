/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.command.CommandSource
 *  net.minecraft.command.argument.EnchantmentArgumentType
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.text.LiteralText
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    private static final /* synthetic */ SimpleCommandExceptionType NOT_IN_CREATIVE;

    public EnchantCommand() {
        super("enchant", "Enchants the item in your hand. REQUIRES Creative mode.", new String[0]);
        EnchantCommand lllllllllllllllllIlIlIlIIllIlIII;
    }

    static {
        NOT_IN_CREATIVE = new SimpleCommandExceptionType((Message)new LiteralText("You must be in creative mode to use this."));
    }

    private ItemStack getItemStack() {
        ItemStack lllllllllllllllllIlIlIlIIlIlllll = EnchantCommand.mc.player.getMainHandStack();
        if (lllllllllllllllllIlIlIlIIlIlllll == null) {
            lllllllllllllllllIlIlIlIIlIlllll = EnchantCommand.mc.player.getOffHandStack();
        }
        return lllllllllllllllllIlIlIlIIlIlllll;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIlIlIlIIllIIIlI) {
        EnchantCommand lllllllllllllllllIlIlIlIIllIIIll;
        lllllllllllllllllIlIlIlIIllIIIlI.then(EnchantCommand.literal("one").then(EnchantCommand.argument("enchantment", EnchantmentArgumentType.enchantment()).then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(lllllllllllllllllIlIlIlIIIllIIII -> {
            EnchantCommand lllllllllllllllllIlIlIlIIIllIlII;
            if (!EnchantCommand.mc.player.isCreative()) {
                throw NOT_IN_CREATIVE.create();
            }
            ItemStack lllllllllllllllllIlIlIlIIIllIIlI = lllllllllllllllllIlIlIlIIIllIlII.getItemStack();
            if (lllllllllllllllllIlIlIlIIIllIIlI != null) {
                Enchantment lllllllllllllllllIlIlIlIIIllIllI = (Enchantment)lllllllllllllllllIlIlIlIIIllIIII.getArgument("enchantment", Enchantment.class);
                int lllllllllllllllllIlIlIlIIIllIlIl = (Integer)lllllllllllllllllIlIlIlIIIllIIII.getArgument("level", Integer.class);
                Utils.addEnchantment(lllllllllllllllllIlIlIlIIIllIIlI, lllllllllllllllllIlIlIlIIIllIllI, lllllllllllllllllIlIlIlIIIllIlIl);
            }
            return 1;
        }))));
        lllllllllllllllllIlIlIlIIllIIIlI.then(EnchantCommand.literal("all_possible").then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(lllllllllllllllllIlIlIlIIlIIIIII -> {
            EnchantCommand lllllllllllllllllIlIlIlIIlIIIlII;
            if (!EnchantCommand.mc.player.isCreative()) {
                throw NOT_IN_CREATIVE.create();
            }
            ItemStack lllllllllllllllllIlIlIlIIlIIIIlI = lllllllllllllllllIlIlIlIIlIIIlII.getItemStack();
            if (lllllllllllllllllIlIlIlIIlIIIIlI != null) {
                int lllllllllllllllllIlIlIlIIlIIIlIl = (Integer)lllllllllllllllllIlIlIlIIlIIIIII.getArgument("level", Integer.class);
                for (Enchantment lllllllllllllllllIlIlIlIIlIIIllI : Registry.ENCHANTMENT) {
                    if (!lllllllllllllllllIlIlIlIIlIIIllI.isAcceptableItem(lllllllllllllllllIlIlIlIIlIIIIlI)) continue;
                    Utils.addEnchantment(lllllllllllllllllIlIlIlIIlIIIIlI, lllllllllllllllllIlIlIlIIlIIIllI, lllllllllllllllllIlIlIlIIlIIIlIl);
                }
            }
            return 1;
        })));
        lllllllllllllllllIlIlIlIIllIIIlI.then(EnchantCommand.literal("all").then(EnchantCommand.argument("level", IntegerArgumentType.integer()).executes(lllllllllllllllllIlIlIlIIlIlIIIl -> {
            EnchantCommand lllllllllllllllllIlIlIlIIlIlIIlI;
            if (!EnchantCommand.mc.player.isCreative()) {
                throw NOT_IN_CREATIVE.create();
            }
            ItemStack lllllllllllllllllIlIlIlIIlIlIIll = lllllllllllllllllIlIlIlIIlIlIIlI.getItemStack();
            if (lllllllllllllllllIlIlIlIIlIlIIll != null) {
                int lllllllllllllllllIlIlIlIIlIlIllI = (Integer)lllllllllllllllllIlIlIlIIlIlIIIl.getArgument("level", Integer.class);
                for (Enchantment lllllllllllllllllIlIlIlIIlIlIlll : Registry.ENCHANTMENT) {
                    Utils.addEnchantment(lllllllllllllllllIlIlIlIIlIlIIll, lllllllllllllllllIlIlIlIIlIlIlll, lllllllllllllllllIlIlIlIIlIlIllI);
                }
            }
            return 1;
        })));
    }
}

