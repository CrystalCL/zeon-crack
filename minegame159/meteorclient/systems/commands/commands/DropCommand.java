/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.command.CommandSource
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.text.LiteralText
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.network.ClientPlayerEntity
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    private static final /* synthetic */ DynamicCommandExceptionType NO_SUCH_ITEM;
    private static final /* synthetic */ SimpleCommandExceptionType NOT_SPECTATOR;

    static {
        NOT_SPECTATOR = new SimpleCommandExceptionType((Message)new LiteralText("Can't drop items while in spectator."));
        NO_SUCH_ITEM = new DynamicCommandExceptionType(lIIlllllIIIIlII -> new LiteralText(String.valueOf(new StringBuilder().append("No such item ").append(lIIlllllIIIIlII).append("!"))));
    }

    private int drop(PlayerConsumer lIIllllllIlIlII) throws CommandSyntaxException {
        ClientPlayerEntity lIIllllllIlIIll = DropCommand.mc.player;
        assert (lIIllllllIlIIll != null);
        if (lIIllllllIlIIll.isSpectator()) {
            throw NOT_SPECTATOR.create();
        }
        lIIllllllIlIlII.accept(lIIllllllIlIIll);
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIIllllllIllIlI) {
        DropCommand lIIllllllIllIll;
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)lIIllllllIllIlI.then(DropCommand.literal("hand").executes(lIIlllllIIIlIll -> {
            DropCommand lIIlllllIIIllII;
            return lIIlllllIIIllII.drop(lIIlllllIIIlIII -> lIIlllllIIIlIII.dropSelectedItem(true));
        }))).then(DropCommand.literal("offhand").executes(lIIlllllIIlIIII -> {
            DropCommand lIIlllllIIlIIIl;
            return lIIlllllIIlIIIl.drop(lIIlllllIIIlllI -> InvUtils.drop().slotOffhand());
        }))).then(DropCommand.literal("hotbar").executes(lIIlllllIIllIII -> {
            DropCommand lIIlllllIIlIlll;
            return lIIlllllIIlIlll.drop(lIIlllllIIlIlII -> {
                for (int lIIlllllIIlIlIl = 0; lIIlllllIIlIlIl < 9; ++lIIlllllIIlIlIl) {
                    InvUtils.drop().slotHotbar(lIIlllllIIlIlIl);
                }
            });
        }))).then(DropCommand.literal("inventory").executes(lIIlllllIlIIIlI -> {
            DropCommand lIIlllllIlIIIll;
            return lIIlllllIlIIIll.drop(lIIlllllIIlllII -> {
                for (int lIIlllllIIllllI = 9; lIIlllllIIllllI < lIIlllllIIlllII.inventory.main.size(); ++lIIlllllIIllllI) {
                    InvUtils.drop().slotMain(lIIlllllIIllllI - 9);
                }
            });
        }))).then(DropCommand.literal("all").executes(lIIlllllIlIllII -> {
            DropCommand lIIlllllIlIlIll;
            return lIIlllllIlIlIll.drop(lIIlllllIlIIllI -> {
                for (int lIIlllllIlIlIII = 0; lIIlllllIlIlIII < lIIlllllIlIIllI.inventory.size(); ++lIIlllllIlIlIII) {
                    InvUtils.drop().slot(lIIlllllIlIlIII);
                }
            });
        }))).then(DropCommand.literal("armor").executes(lIIlllllIllIllI -> {
            DropCommand lIIlllllIllIlll;
            return lIIlllllIllIlll.drop(lIIlllllIllIIIl -> {
                for (int lIIlllllIllIIlI = 0; lIIlllllIllIIlI < lIIlllllIllIIIl.inventory.armor.size(); ++lIIlllllIllIIlI) {
                    InvUtils.drop().slotArmor(lIIlllllIllIIlI);
                }
            });
        }))).then(DropCommand.argument("item", StringArgumentType.string()).executes(lIIllllllIIlIll -> {
            DropCommand lIIllllllIIllII;
            return lIIllllllIIllII.drop(lIIlllllIllllIl -> {
                String lIIllllllIIIIII = (String)lIIllllllIIlIll.getArgument("item", String.class);
                Item lIIlllllIllllll = (Item)Registry.ITEM.get(new Identifier(lIIllllllIIIIII.toLowerCase()));
                if (lIIlllllIllllll == Items.AIR) {
                    throw NO_SUCH_ITEM.create((Object)lIIllllllIIIIII);
                }
                for (int lIIllllllIIIIll = 0; lIIllllllIIIIll < lIIlllllIllllIl.inventory.main.size(); ++lIIllllllIIIIll) {
                    ItemStack lIIllllllIIIlII = (ItemStack)lIIlllllIllllIl.inventory.main.get(lIIllllllIIIIll);
                    if (lIIllllllIIIlII.getItem() != lIIlllllIllllll) continue;
                    InvUtils.drop().slot(lIIllllllIIIIll);
                }
            });
        }));
    }

    public DropCommand() {
        super("drop", "Automatically drops specified items.", new String[0]);
        DropCommand lIIllllllIllllI;
    }

    @FunctionalInterface
    private static interface PlayerConsumer {
        public void accept(ClientPlayerEntity var1) throws CommandSyntaxException;
    }
}

