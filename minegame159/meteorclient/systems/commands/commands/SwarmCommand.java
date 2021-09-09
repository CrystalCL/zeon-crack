/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.pathing.goals.GoalXZ
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.command.CommandSource
 *  net.minecraft.command.argument.BlockStateArgument
 *  net.minecraft.command.argument.BlockStateArgumentType
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Random;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.commands.arguments.PlayerArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.Swarm;
import minegame159.meteorclient.systems.modules.world.InfinityMiner;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;

public class SwarmCommand
extends Command {
    private void runInfinityMiner() {
        InfinityMiner lllllllllllllllllIlIIlIlIlllIIIl = Modules.get().get(InfinityMiner.class);
        if (lllllllllllllllllIlIIlIlIlllIIIl.isActive()) {
            lllllllllllllllllIlIIlIlIlllIIIl.toggle();
        }
        lllllllllllllllllIlIIlIlIlllIIIl.smartModuleToggle.set(true);
        if (!lllllllllllllllllIlIIlIlIlllIIIl.isActive()) {
            lllllllllllllllllIlIIlIlIlllIIIl.toggle();
        }
    }

    public SwarmCommand() {
        super("swarm", "Sends commands to connected swarm accouns.", new String[0]);
        SwarmCommand lllllllllllllllllIlIIlIlIllllIlI;
    }

    private void scatter(int lllllllllllllllllIlIIlIlIllIIIlI) {
        if (SwarmCommand.mc.player != null) {
            Random lllllllllllllllllIlIIlIlIllIlIIl = new Random();
            double lllllllllllllllllIlIIlIlIllIlIII = lllllllllllllllllIlIIlIlIllIlIIl.nextDouble() * 2.0 * Math.PI;
            double lllllllllllllllllIlIIlIlIllIIlll = (double)lllllllllllllllllIlIIlIlIllIIIlI * Math.sqrt(lllllllllllllllllIlIIlIlIllIlIIl.nextDouble());
            double lllllllllllllllllIlIIlIlIllIIllI = SwarmCommand.mc.player.getX() + lllllllllllllllllIlIIlIlIllIIlll * Math.cos(lllllllllllllllllIlIIlIlIllIlIII);
            double lllllllllllllllllIlIIlIlIllIIlIl = SwarmCommand.mc.player.getZ() + lllllllllllllllllIlIIlIlIllIIlll * Math.sin(lllllllllllllllllIlIIlIlIllIlIII);
            if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ((int)lllllllllllllllllIlIIlIlIllIIllI, (int)lllllllllllllllllIlIIlIlIllIIlIl));
        }
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllIlIIlIlIlllIllI) {
        SwarmCommand lllllllllllllllllIlIIlIlIlllIlIl;
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("close").executes(lllllllllllllllllIlIIlIIllIIlllI -> {
            try {
                Swarm lllllllllllllllllIlIIlIIllIIllll = Modules.get().get(Swarm.class);
                if (lllllllllllllllllIlIIlIIllIIllll.isActive()) {
                    lllllllllllllllllIlIIlIIllIIllll.closeAllServerConnections();
                    lllllllllllllllllIlIIlIIllIIllll.currentMode = Swarm.Mode.Idle;
                    if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                    }
                    if (Modules.get().isActive(Swarm.class)) {
                        Modules.get().get(Swarm.class).toggle();
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return 1;
        }));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("escape").executes(lllllllllllllllllIlIIlIIllIlIIll -> {
            Swarm lllllllllllllllllIlIIlIIllIlIIlI = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIIllIlIIlI.isActive()) {
                if (lllllllllllllllllIlIIlIIllIlIIlI.currentMode != Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIIllIlIIlI.closeAllServerConnections();
                    if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                    }
                    lllllllllllllllllIlIIlIIllIlIIlI.currentMode = Swarm.Mode.Idle;
                    Modules.get().get(Swarm.class).toggle();
                } else {
                    ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "You are the queen.", new Object[0]);
                }
            }
            return 1;
        }));
        lllllllllllllllllIlIIlIlIlllIllI.then(((LiteralArgumentBuilder)SwarmCommand.literal("follow").executes(lllllllllllllllllIlIIlIIllIllIII -> {
            Swarm lllllllllllllllllIlIIlIIllIlIlll = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIIllIlIlll.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIIllIlIlll.server != null && SwarmCommand.mc.player != null) {
                lllllllllllllllllIlIIlIIllIlIlll.server.sendMessage(String.valueOf(new StringBuilder().append(lllllllllllllllllIlIIlIIllIllIII.getInput()).append(" ").append(SwarmCommand.mc.player.getDisplayName().getString())));
            }
            return 1;
        })).then(SwarmCommand.argument("name", PlayerArgumentType.player()).executes(lllllllllllllllllIlIIlIIlllIIllI -> {
            PlayerEntity lllllllllllllllllIlIIlIIlllIIlIl = (PlayerEntity)lllllllllllllllllIlIIlIIlllIIllI.getArgument("name", PlayerEntity.class);
            Swarm lllllllllllllllllIlIIlIIlllIIlII = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIIlllIIlII.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIIlllIIlII.server != null) {
                lllllllllllllllllIlIIlIIlllIIlII.server.sendMessage(lllllllllllllllllIlIIlIIlllIIllI.getInput());
            } else if (lllllllllllllllllIlIIlIIlllIIlIl != null) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getFollowProcess().follow(lllllllllllllllllIlIIlIIllIlllIl -> lllllllllllllllllIlIIlIIllIlllIl.getDisplayName().asString().equalsIgnoreCase(lllllllllllllllllIlIIlIIlllIIlIl.getDisplayName().asString()));
            }
            return 1;
        })));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("goto").then(SwarmCommand.argument("x", IntegerArgumentType.integer()).then(SwarmCommand.argument("z", IntegerArgumentType.integer()).executes(lllllllllllllllllIlIIlIIllllIIIl -> {
            int lllllllllllllllllIlIIlIIllllIIII = (Integer)lllllllllllllllllIlIIlIIllllIIIl.getArgument("x", Integer.class);
            int lllllllllllllllllIlIIlIIlllIllll = (Integer)lllllllllllllllllIlIIlIIllllIIIl.getArgument("z", Integer.class);
            Swarm lllllllllllllllllIlIIlIIlllIlllI = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIIlllIlllI.isActive()) {
                if (lllllllllllllllllIlIIlIIlllIlllI.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIIlllIlllI.server != null) {
                    lllllllllllllllllIlIIlIIlllIlllI.server.sendMessage(lllllllllllllllllIlIIlIIllllIIIl.getInput());
                } else if (lllllllllllllllllIlIIlIIlllIlllI.currentMode != Swarm.Mode.Queen) {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ(lllllllllllllllllIlIIlIIllllIIII, lllllllllllllllllIlIIlIIlllIllll));
                }
            }
            return 1;
        }))));
        lllllllllllllllllIlIIlIlIlllIllI.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)SwarmCommand.literal("im").executes(lllllllllllllllllIlIIlIIlllllIlI -> {
            Swarm lllllllllllllllllIlIIlIIlllllIIl = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIIlllllIIl.isActive()) {
                if (lllllllllllllllllIlIIlIIlllllIIl.currentMode == Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIIlllllIIl.server.sendMessage(lllllllllllllllllIlIIlIIlllllIlI.getInput());
                } else {
                    SwarmCommand lllllllllllllllllIlIIlIIlllllIII;
                    lllllllllllllllllIlIIlIIlllllIII.runInfinityMiner();
                }
            }
            return 1;
        })).then(((RequiredArgumentBuilder)SwarmCommand.argument("target", BlockStateArgumentType.blockState()).executes(lllllllllllllllllIlIIlIlIIIIIIll -> {
            Swarm lllllllllllllllllIlIIlIlIIIIIIlI = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIIIIIlI.isActive()) {
                if (lllllllllllllllllIlIIlIlIIIIIIlI.currentMode == Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIlIIIIIIlI.server.sendMessage(lllllllllllllllllIlIIlIlIIIIIIll.getInput());
                } else {
                    SwarmCommand lllllllllllllllllIlIIlIlIIIIIlII;
                    Modules.get().get(InfinityMiner.class).targetBlock.set(((BlockStateArgument)lllllllllllllllllIlIIlIlIIIIIIll.getArgument("target", BlockStateArgument.class)).getBlockState().getBlock());
                    lllllllllllllllllIlIIlIlIIIIIlII.runInfinityMiner();
                }
            }
            return 1;
        })).then(SwarmCommand.argument("repair", BlockStateArgumentType.blockState()).executes(lllllllllllllllllIlIIlIlIIIIllII -> {
            Swarm lllllllllllllllllIlIIlIlIIIIlIll = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIIIlIll.isActive()) {
                if (lllllllllllllllllIlIIlIlIIIIlIll.currentMode == Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIlIIIIlIll.server.sendMessage(lllllllllllllllllIlIIlIlIIIIllII.getInput());
                } else {
                    SwarmCommand lllllllllllllllllIlIIlIlIIIIlIlI;
                    Modules.get().get(InfinityMiner.class).targetBlock.set(((BlockStateArgument)lllllllllllllllllIlIIlIlIIIIllII.getArgument("target", BlockStateArgument.class)).getBlockState().getBlock());
                    Modules.get().get(InfinityMiner.class).repairBlock.set(((BlockStateArgument)lllllllllllllllllIlIIlIlIIIIllII.getArgument("repair", BlockStateArgument.class)).getBlockState().getBlock());
                    lllllllllllllllllIlIIlIlIIIIlIlI.runInfinityMiner();
                }
            }
            return 1;
        })))).then(SwarmCommand.literal("logout").then(SwarmCommand.argument("autologout", BoolArgumentType.bool()).executes(lllllllllllllllllIlIIlIlIIIlIllI -> {
            Swarm lllllllllllllllllIlIIlIlIIIlIlIl = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIIlIlIl.isActive()) {
                if (lllllllllllllllllIlIIlIlIIIlIlIl.currentMode == Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIlIIIlIlIl.server.sendMessage(lllllllllllllllllIlIIlIlIIIlIllI.getInput());
                } else {
                    boolean lllllllllllllllllIlIIlIlIIIllIII = (Boolean)lllllllllllllllllIlIIlIlIIIlIllI.getArgument("autologout", Boolean.class);
                    InfinityMiner lllllllllllllllllIlIIlIlIIIlIlll = Modules.get().get(InfinityMiner.class);
                    lllllllllllllllllIlIIlIlIIIlIlll.autoLogOut.set(lllllllllllllllllIlIIlIlIIIllIII);
                }
            }
            return 1;
        })))).then(SwarmCommand.literal("walkhome").then(SwarmCommand.argument("walkhome", BoolArgumentType.bool()).executes(lllllllllllllllllIlIIlIlIIlIIIlI -> {
            Swarm lllllllllllllllllIlIIlIlIIlIIIIl = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIlIIIIl.isActive()) {
                if (lllllllllllllllllIlIIlIlIIlIIIIl.currentMode == Swarm.Mode.Queen) {
                    lllllllllllllllllIlIIlIlIIlIIIIl.server.sendMessage(lllllllllllllllllIlIIlIlIIlIIIlI.getInput());
                } else {
                    boolean lllllllllllllllllIlIIlIlIIlIIlII = (Boolean)lllllllllllllllllIlIIlIlIIlIIIlI.getArgument("walkhome", Boolean.class);
                    InfinityMiner lllllllllllllllllIlIIlIlIIlIIIll = Modules.get().get(InfinityMiner.class);
                    lllllllllllllllllIlIIlIlIIlIIIll.autoWalkHome.set(lllllllllllllllllIlIIlIlIIlIIlII);
                }
            }
            return 1;
        }))));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("mine").then(SwarmCommand.argument("block", BlockStateArgumentType.blockState()).executes(lllllllllllllllllIlIIlIlIIlIlIlI -> {
            try {
                Swarm lllllllllllllllllIlIIlIlIIlIllIl = Modules.get().get(Swarm.class);
                if (lllllllllllllllllIlIIlIlIIlIllIl.isActive()) {
                    if (lllllllllllllllllIlIIlIlIIlIllIl.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIIlIllIl.server != null) {
                        lllllllllllllllllIlIIlIlIIlIllIl.server.sendMessage(lllllllllllllllllIlIIlIlIIlIlIlI.getInput());
                    }
                    if (lllllllllllllllllIlIIlIlIIlIllIl.currentMode != Swarm.Mode.Queen) {
                        lllllllllllllllllIlIIlIlIIlIllIl.targetBlock = ((BlockStateArgument)lllllllllllllllllIlIIlIlIIlIlIlI.getArgument("block", BlockStateArgument.class)).getBlockState();
                    } else {
                        ChatUtils.moduleError(Modules.get().get(Swarm.class), "Null block", new Object[0]);
                    }
                }
            }
            catch (Exception lllllllllllllllllIlIIlIlIIlIllII) {
                ChatUtils.moduleError(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("Error in baritone command. ").append(lllllllllllllllllIlIIlIlIIlIllII.getClass().getSimpleName())), new Object[0]);
            }
            return 1;
        })));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("module").then(SwarmCommand.argument("m", ModuleArgumentType.module()).then(SwarmCommand.argument("bool", BoolArgumentType.bool()).executes(lllllllllllllllllIlIIlIlIIllIIlI -> {
            Swarm lllllllllllllllllIlIIlIlIIllIIll = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIllIIll.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIIllIIll.server != null) {
                lllllllllllllllllIlIIlIlIIllIIll.server.sendMessage(lllllllllllllllllIlIIlIlIIllIIlI.getInput());
            } else {
                Module lllllllllllllllllIlIIlIlIIllIlIl = (Module)lllllllllllllllllIlIIlIlIIllIIlI.getArgument("m", Module.class);
                if (lllllllllllllllllIlIIlIlIIllIlIl.isActive() != ((Boolean)lllllllllllllllllIlIIlIlIIllIIlI.getArgument("bool", Boolean.class)).booleanValue()) {
                    lllllllllllllllllIlIIlIlIIllIlIl.toggle();
                }
            }
            return 1;
        }))));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("queen").executes(lllllllllllllllllIlIIlIlIIlllIll -> {
            Swarm lllllllllllllllllIlIIlIlIIlllIlI = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIlllIlI.isActive() && lllllllllllllllllIlIIlIlIIlllIlI.server == null) {
                lllllllllllllllllIlIIlIlIIlllIlI.runServer();
            }
            return 1;
        }));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("release").executes(lllllllllllllllllIlIIlIlIIllllll -> {
            Swarm lllllllllllllllllIlIIlIlIIlllllI = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIIlllllI.isActive() && lllllllllllllllllIlIIlIlIIlllllI.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIIlllllI.server != null) {
                lllllllllllllllllIlIIlIlIIlllllI.server.sendMessage("s stop");
                lllllllllllllllllIlIIlIlIIlllllI.server.closeAllClients();
            }
            return 1;
        }));
        lllllllllllllllllIlIIlIlIlllIllI.then(((LiteralArgumentBuilder)SwarmCommand.literal("scatter").executes(lllllllllllllllllIlIIlIlIlIIIIlI -> {
            Swarm lllllllllllllllllIlIIlIlIlIIIlII = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIlIIIlII.isActive()) {
                if (lllllllllllllllllIlIIlIlIlIIIlII.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIlIIIlII.server != null) {
                    lllllllllllllllllIlIIlIlIlIIIlII.server.sendMessage(lllllllllllllllllIlIIlIlIlIIIIlI.getInput());
                } else {
                    SwarmCommand lllllllllllllllllIlIIlIlIlIIIllI;
                    lllllllllllllllllIlIIlIlIlIIIllI.scatter(100);
                }
            }
            return 1;
        })).then(SwarmCommand.argument("radius", IntegerArgumentType.integer()).executes(lllllllllllllllllIlIIlIlIlIIlIll -> {
            Swarm lllllllllllllllllIlIIlIlIlIIllIl = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIlIIllIl.isActive()) {
                if (lllllllllllllllllIlIIlIlIlIIllIl.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIlIIllIl.server != null) {
                    lllllllllllllllllIlIIlIlIlIIllIl.server.sendMessage(lllllllllllllllllIlIIlIlIlIIlIll.getInput());
                } else {
                    SwarmCommand lllllllllllllllllIlIIlIlIlIIllII;
                    lllllllllllllllllIlIIlIlIlIIllII.scatter((Integer)lllllllllllllllllIlIIlIlIlIIlIll.getArgument("radius", Integer.class));
                }
            }
            return 1;
        })));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("slave").executes(lllllllllllllllllIlIIlIlIlIlIlIl -> {
            Swarm lllllllllllllllllIlIIlIlIlIlIlII = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIlIlIlII.isActive() && lllllllllllllllllIlIIlIlIlIlIlII.client == null) {
                lllllllllllllllllIlIIlIlIlIlIlII.runClient();
            }
            return 1;
        }));
        lllllllllllllllllIlIIlIlIlllIllI.then(SwarmCommand.literal("stop").executes(lllllllllllllllllIlIIlIlIlIllIII -> {
            Swarm lllllllllllllllllIlIIlIlIlIllIIl = Modules.get().get(Swarm.class);
            if (lllllllllllllllllIlIIlIlIlIllIIl.isActive()) {
                if (lllllllllllllllllIlIIlIlIlIllIIl.currentMode == Swarm.Mode.Queen && lllllllllllllllllIlIIlIlIlIllIIl.server != null) {
                    lllllllllllllllllIlIIlIlIlIllIIl.server.sendMessage(lllllllllllllllllIlIIlIlIlIllIII.getInput());
                } else {
                    lllllllllllllllllIlIIlIlIlIllIIl.idle();
                }
            }
            return 1;
        }));
    }
}

