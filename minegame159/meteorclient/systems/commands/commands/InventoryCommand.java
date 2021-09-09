/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.command.CommandSource
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.PlayerArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class InventoryCommand
extends Command {
    public InventoryCommand() {
        super("inventory", "Allows you to see parts of another player's inventory.", "inv", "invsee");
        InventoryCommand lIIIlIIIlIl;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIIIlIIIIIl) {
        lIIIlIIIIIl.then(InventoryCommand.argument("name", PlayerArgumentType.player()).executes(lIIIIlllllI -> {
            PlayerEntity lIIIIllllIl = (PlayerEntity)lIIIIlllllI.getArgument("name", PlayerEntity.class);
            MeteorClient.INSTANCE.screenToOpen = new InventoryScreen(lIIIIllllIl);
            return 1;
        }));
    }
}

