/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.network.ClientPlayerEntity
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class HClipCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllllIlIlIllIllIIII) {
        llllllllllllllllllIlIlIllIllIIII.then(HClipCommand.argument("blocks", DoubleArgumentType.doubleArg()).executes(llllllllllllllllllIlIlIllIlIlIll -> {
            ClientPlayerEntity llllllllllllllllllIlIlIllIlIlIlI = MinecraftClient.getInstance().player;
            assert (llllllllllllllllllIlIlIllIlIlIlI != null);
            double llllllllllllllllllIlIlIllIlIlIIl = (Double)llllllllllllllllllIlIlIllIlIlIll.getArgument("blocks", Double.class);
            Vec3d llllllllllllllllllIlIlIllIlIlIII = Vec3d.fromPolar((float)0.0f, (float)llllllllllllllllllIlIlIllIlIlIlI.yaw).normalize();
            llllllllllllllllllIlIlIllIlIlIlI.setPosition(llllllllllllllllllIlIlIllIlIlIlI.getX() + llllllllllllllllllIlIlIllIlIlIII.x * llllllllllllllllllIlIlIllIlIlIIl, llllllllllllllllllIlIlIllIlIlIlI.getY(), llllllllllllllllllIlIlIllIlIlIlI.getZ() + llllllllllllllllllIlIlIllIlIlIII.z * llllllllllllllllllIlIlIllIlIlIIl);
            return 1;
        }));
    }

    public HClipCommand() {
        super("hclip", "Lets you clip through blocks horizontally.", new String[0]);
        HClipCommand llllllllllllllllllIlIlIllIllIlIl;
    }
}

