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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.command.CommandSource;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;

public class Count
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(Count.argument("count", IntegerArgumentType.integer((int)1, (int)127)).executes(Count::lambda$build$0));
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        MinecraftClient MinecraftClient2 = MinecraftClient.getInstance();
        if (MinecraftClient2.player.getMainHandStack().getItem() == Items.AIR) {
            return 1;
        }
        ItemStack ItemStack2 = MinecraftClient2.player.getMainHandStack();
        ItemStack2.setCount(((Integer)commandContext.getArgument("count", Integer.class)).intValue());
        int n = Ezz.invIndexToSlotId(MinecraftClient2.player.inventory.selectedSlot);
        MinecraftClient2.player.networkHandler.sendPacket((Packet)new CreativeInventoryActionC2SPacket(n, ItemStack2));
        MinecraftClient2.openScreen((Screen)new CreativeInventoryScreen((PlayerEntity)MinecraftClient2.player));
        return 1;
    }

    public Count() {
        super("count", "Set item stack amount in main hand.", new String[0]);
    }
}

