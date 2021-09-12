/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.ModuleArgumentType;
import minegame159.meteorclient.systems.commands.arguments.PlayerArgumentType;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.Swarm;
import minegame159.meteorclient.systems.modules.world.InfinityMiner;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2172;
import net.minecraft.class_2247;
import net.minecraft.class_2257;

public class SwarmCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.then(SwarmCommand.literal("close").executes(SwarmCommand::lambda$build$0));
        literalArgumentBuilder.then(SwarmCommand.literal("escape").executes(SwarmCommand::lambda$build$1));
        literalArgumentBuilder.then(((LiteralArgumentBuilder)SwarmCommand.literal("follow").executes(SwarmCommand::lambda$build$2)).then(SwarmCommand.argument("name", PlayerArgumentType.player()).executes(SwarmCommand::lambda$build$4)));
        literalArgumentBuilder.then(SwarmCommand.literal("goto").then(SwarmCommand.argument("x", IntegerArgumentType.integer()).then(SwarmCommand.argument("z", IntegerArgumentType.integer()).executes(SwarmCommand::lambda$build$5))));
        literalArgumentBuilder.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)SwarmCommand.literal("im").executes(this::lambda$build$6)).then(((RequiredArgumentBuilder)SwarmCommand.argument("target", class_2257.method_9653()).executes(this::lambda$build$7)).then(SwarmCommand.argument("repair", class_2257.method_9653()).executes(this::lambda$build$8)))).then(SwarmCommand.literal("logout").then(SwarmCommand.argument("autologout", BoolArgumentType.bool()).executes(SwarmCommand::lambda$build$9)))).then(SwarmCommand.literal("walkhome").then(SwarmCommand.argument("walkhome", BoolArgumentType.bool()).executes(SwarmCommand::lambda$build$10))));
        literalArgumentBuilder.then(SwarmCommand.literal("mine").then(SwarmCommand.argument("block", class_2257.method_9653()).executes(SwarmCommand::lambda$build$11)));
        literalArgumentBuilder.then(SwarmCommand.literal("module").then(SwarmCommand.argument("m", ModuleArgumentType.module()).then(SwarmCommand.argument("bool", BoolArgumentType.bool()).executes(SwarmCommand::lambda$build$12))));
        literalArgumentBuilder.then(SwarmCommand.literal("queen").executes(SwarmCommand::lambda$build$13));
        literalArgumentBuilder.then(SwarmCommand.literal("release").executes(SwarmCommand::lambda$build$14));
        literalArgumentBuilder.then(((LiteralArgumentBuilder)SwarmCommand.literal("scatter").executes(this::lambda$build$15)).then(SwarmCommand.argument("radius", IntegerArgumentType.integer()).executes(this::lambda$build$16)));
        literalArgumentBuilder.then(SwarmCommand.literal("slave").executes(SwarmCommand::lambda$build$17));
        literalArgumentBuilder.then(SwarmCommand.literal("stop").executes(SwarmCommand::lambda$build$18));
    }

    private static int lambda$build$5(CommandContext commandContext) throws CommandSyntaxException {
        int n = (Integer)commandContext.getArgument("x", Integer.class);
        int n2 = (Integer)commandContext.getArgument("z", Integer.class);
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                swarm.server.sendMessage(commandContext.getInput());
            } else if (swarm.currentMode != Swarm.Mode.Queen) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ(n, n2));
            }
        }
        return 1;
    }

    private static int lambda$build$12(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
            swarm.server.sendMessage(commandContext.getInput());
        } else {
            Module module = (Module)commandContext.getArgument("m", Module.class);
            if (module.isActive() != ((Boolean)commandContext.getArgument("bool", Boolean.class)).booleanValue()) {
                module.toggle();
            }
        }
        return 1;
    }

    private void scatter(int n) {
        if (SwarmCommand.mc.field_1724 != null) {
            Random random = new Random();
            double d = random.nextDouble() * 2.0 * Math.PI;
            double d2 = (double)n * Math.sqrt(random.nextDouble());
            double d3 = SwarmCommand.mc.field_1724.method_23317() + d2 * Math.cos(d);
            double d4 = SwarmCommand.mc.field_1724.method_23321() + d2 * Math.sin(d);
            if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ((int)d3, (int)d4));
        }
    }

    private int lambda$build$7(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                Modules.get().get(InfinityMiner.class).targetBlock.set(((class_2247)commandContext.getArgument("target", class_2247.class)).method_9494().method_26204());
                this.runInfinityMiner();
            }
        }
        return 1;
    }

    private static int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null && SwarmCommand.mc.field_1724 != null) {
            swarm.server.sendMessage(String.valueOf(new StringBuilder().append(commandContext.getInput()).append(" ").append(SwarmCommand.mc.field_1724.method_5476().getString())));
        }
        return 1;
    }

    private static int lambda$build$13(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive() && swarm.server == null) {
            swarm.runServer();
        }
        return 1;
    }

    private int lambda$build$6(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                this.runInfinityMiner();
            }
        }
        return 1;
    }

    private static int lambda$build$9(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                boolean bl = (Boolean)commandContext.getArgument("autologout", Boolean.class);
                InfinityMiner infinityMiner = Modules.get().get(InfinityMiner.class);
                infinityMiner.autoLogOut.set(bl);
            }
        }
        return 1;
    }

    private static int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        class_1657 class_16572 = (class_1657)commandContext.getArgument("name", class_1657.class);
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
            swarm.server.sendMessage(commandContext.getInput());
        } else if (class_16572 != null) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getFollowProcess().follow(arg_0 -> SwarmCommand.lambda$build$3(class_16572, arg_0));
        }
        return 1;
    }

    private static int lambda$build$14(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive() && swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
            swarm.server.sendMessage("s stop");
            swarm.server.closeAllClients();
        }
        return 1;
    }

    private int lambda$build$16(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                this.scatter((Integer)commandContext.getArgument("radius", Integer.class));
            }
        }
        return 1;
    }

    private static int lambda$build$10(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                boolean bl = (Boolean)commandContext.getArgument("walkhome", Boolean.class);
                InfinityMiner infinityMiner = Modules.get().get(InfinityMiner.class);
                infinityMiner.autoWalkHome.set(bl);
            }
        }
        return 1;
    }

    private int lambda$build$8(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                Modules.get().get(InfinityMiner.class).targetBlock.set(((class_2247)commandContext.getArgument("target", class_2247.class)).method_9494().method_26204());
                Modules.get().get(InfinityMiner.class).repairBlock.set(((class_2247)commandContext.getArgument("repair", class_2247.class)).method_9494().method_26204());
                this.runInfinityMiner();
            }
        }
        return 1;
    }

    private static boolean lambda$build$3(class_1657 class_16572, class_1297 class_12972) {
        return class_12972.method_5476().method_10851().equalsIgnoreCase(class_16572.method_5476().method_10851());
    }

    private static int lambda$build$17(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive() && swarm.client == null) {
            swarm.runClient();
        }
        return 1;
    }

    private static int lambda$build$11(CommandContext commandContext) throws CommandSyntaxException {
        try {
            Swarm swarm = Modules.get().get(Swarm.class);
            if (swarm.isActive()) {
                if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                    swarm.server.sendMessage(commandContext.getInput());
                }
                if (swarm.currentMode != Swarm.Mode.Queen) {
                    swarm.targetBlock = ((class_2247)commandContext.getArgument("block", class_2247.class)).method_9494();
                } else {
                    ChatUtils.moduleError(Modules.get().get(Swarm.class), "Null block", new Object[0]);
                }
            }
        }
        catch (Exception exception) {
            ChatUtils.moduleError(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("Error in baritone command. ").append(exception.getClass().getSimpleName())), new Object[0]);
        }
        return 1;
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        try {
            Swarm swarm = Modules.get().get(Swarm.class);
            if (swarm.isActive()) {
                swarm.closeAllServerConnections();
                swarm.currentMode = Swarm.Mode.Idle;
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
    }

    private static int lambda$build$18(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                swarm.idle();
            }
        }
        return 1;
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode != Swarm.Mode.Queen) {
                swarm.closeAllServerConnections();
                if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                    BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                }
                swarm.currentMode = Swarm.Mode.Idle;
                Modules.get().get(Swarm.class).toggle();
            } else {
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "You are the queen.", new Object[0]);
            }
        }
        return 1;
    }

    private void runInfinityMiner() {
        InfinityMiner infinityMiner = Modules.get().get(InfinityMiner.class);
        if (infinityMiner.isActive()) {
            infinityMiner.toggle();
        }
        infinityMiner.smartModuleToggle.set(true);
        if (!infinityMiner.isActive()) {
            infinityMiner.toggle();
        }
    }

    public SwarmCommand() {
        super("swarm", "Sends commands to connected swarm accouns.", new String[0]);
    }

    private int lambda$build$15(CommandContext commandContext) throws CommandSyntaxException {
        Swarm swarm = Modules.get().get(Swarm.class);
        if (swarm.isActive()) {
            if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                swarm.server.sendMessage(commandContext.getInput());
            } else {
                this.scatter(100);
            }
        }
        return 1;
    }
}

