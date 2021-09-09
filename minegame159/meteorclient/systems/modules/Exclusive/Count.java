/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.command.CommandSource
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    public Count() {
        super("count", "Set item stack amount in main hand.", new String[0]);
        Count lllllllllllllllllllIIlllllIIIlIl;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllllIIllllIllllII) {
        lllllllllllllllllllIIllllIllllII.then(Count.argument("count", IntegerArgumentType.integer((int)1, (int)127)).executes(lllllllllllllllllllIIllllIllIlll -> {
            MinecraftClient lllllllllllllllllllIIllllIllIllI = MinecraftClient.getInstance();
            if (lllllllllllllllllllIIllllIllIllI.player.getMainHandStack().getItem() == Items.AIR) {
                return 1;
            }
            ItemStack lllllllllllllllllllIIllllIllIlIl = lllllllllllllllllllIIllllIllIllI.player.getMainHandStack();
            lllllllllllllllllllIIllllIllIlIl.setCount(((Integer)lllllllllllllllllllIIllllIllIlll.getArgument("count", Integer.class)).intValue());
            int lllllllllllllllllllIIllllIllIlII = Ezz.invIndexToSlotId(lllllllllllllllllllIIllllIllIllI.player.inventory.selectedSlot);
            lllllllllllllllllllIIllllIllIllI.player.networkHandler.sendPacket((Packet)new CreativeInventoryActionC2SPacket(lllllllllllllllllllIIllllIllIlII, lllllllllllllllllllIIllllIllIlIl));
            lllllllllllllllllllIIllllIllIllI.openScreen((Screen)new CreativeInventoryScreen((PlayerEntity)lllllllllllllllllllIIllllIllIllI.player));
            return 1;
        }));
    }
}

