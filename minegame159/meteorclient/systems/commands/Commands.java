/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  net.minecraft.command.CommandSource
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.network.ClientCommandSource
 */
package minegame159.meteorclient.systems.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.commands.BaritoneCommand;
import minegame159.meteorclient.systems.commands.commands.BindsCommand;
import minegame159.meteorclient.systems.commands.commands.ClearChatCommand;
import minegame159.meteorclient.systems.commands.commands.CommandsCommand;
import minegame159.meteorclient.systems.commands.commands.DamageCommand;
import minegame159.meteorclient.systems.commands.commands.DismountCommand;
import minegame159.meteorclient.systems.commands.commands.DropCommand;
import minegame159.meteorclient.systems.commands.commands.EnchantCommand;
import minegame159.meteorclient.systems.commands.commands.FakePlayerCommand;
import minegame159.meteorclient.systems.commands.commands.FriendCommand;
import minegame159.meteorclient.systems.commands.commands.GamemodeCommand;
import minegame159.meteorclient.systems.commands.commands.HClipCommand;
import minegame159.meteorclient.systems.commands.commands.InventoryCommand;
import minegame159.meteorclient.systems.commands.commands.LocateCommand;
import minegame159.meteorclient.systems.commands.commands.ModulesCommand;
import minegame159.meteorclient.systems.commands.commands.NbtCommand;
import minegame159.meteorclient.systems.commands.commands.NotebotCommand;
import minegame159.meteorclient.systems.commands.commands.PanicCommand;
import minegame159.meteorclient.systems.commands.commands.PeekCommand;
import minegame159.meteorclient.systems.commands.commands.PluginsCommand;
import minegame159.meteorclient.systems.commands.commands.ProfileCommand;
import minegame159.meteorclient.systems.commands.commands.ReloadCommand;
import minegame159.meteorclient.systems.commands.commands.ResetCommand;
import minegame159.meteorclient.systems.commands.commands.SaveMapCommand;
import minegame159.meteorclient.systems.commands.commands.SayCommand;
import minegame159.meteorclient.systems.commands.commands.ServerCommand;
import minegame159.meteorclient.systems.commands.commands.SettingCommand;
import minegame159.meteorclient.systems.commands.commands.SwarmCommand;
import minegame159.meteorclient.systems.commands.commands.ToggleCommand;
import minegame159.meteorclient.systems.commands.commands.VClipCommand;
import minegame159.meteorclient.systems.modules.Exclusive.Count;
import net.minecraft.command.CommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;

public class Commands
extends System<Commands> {
    private final /* synthetic */ CommandSource COMMAND_SOURCE;
    private final /* synthetic */ CommandDispatcher<CommandSource> DISPATCHER;
    private final /* synthetic */ List<Command> commands;
    private final /* synthetic */ Map<Class<? extends Command>, Command> commandInstances;

    public int getCount() {
        Commands llllllllllllllllIlIlllIlIllIlllI;
        return llllllllllllllllIlIlllIlIllIlllI.commands.size();
    }

    public void add(Command llllllllllllllllIlIlllIlIlllIIll) {
        Commands llllllllllllllllIlIlllIlIlllIIlI;
        llllllllllllllllIlIlllIlIlllIIlI.commands.removeIf(llllllllllllllllIlIlllIlIlIllIll -> llllllllllllllllIlIlllIlIlIllIll.getName().equals(llllllllllllllllIlIlllIlIlllIIll.getName()));
        llllllllllllllllIlIlllIlIlllIIlI.commandInstances.values().removeIf(llllllllllllllllIlIlllIlIllIIIIl -> llllllllllllllllIlIlllIlIllIIIIl.getName().equals(llllllllllllllllIlIlllIlIlllIIll.getName()));
        llllllllllllllllIlIlllIlIlllIIll.registerTo(llllllllllllllllIlIlllIlIlllIIlI.DISPATCHER);
        llllllllllllllllIlIlllIlIlllIIlI.commands.add(llllllllllllllllIlIlllIlIlllIIll);
        llllllllllllllllIlIlllIlIlllIIlI.commandInstances.put(llllllllllllllllIlIlllIlIlllIIll.getClass(), llllllllllllllllIlIlllIlIlllIIll);
    }

    public void dispatch(String llllllllllllllllIlIlllIllIIIlIll) throws CommandSyntaxException {
        Commands llllllllllllllllIlIlllIllIIIllII;
        llllllllllllllllIlIlllIllIIIllII.dispatch(llllllllllllllllIlIlllIllIIIlIll, (CommandSource)new ChatCommandSource(MinecraftClient.getInstance()));
    }

    public CommandSource getCommandSource() {
        Commands llllllllllllllllIlIlllIlIlllIlll;
        return llllllllllllllllIlIlllIlIlllIlll.COMMAND_SOURCE;
    }

    public <T extends Command> T get(Class<T> llllllllllllllllIlIlllIlIllIIlIl) {
        Commands llllllllllllllllIlIlllIlIllIlIII;
        return (T)llllllllllllllllIlIlllIlIllIlIII.commandInstances.get(llllllllllllllllIlIlllIlIllIIlIl);
    }

    public static Commands get() {
        return Systems.get(Commands.class);
    }

    public Commands() {
        super(null);
        Commands llllllllllllllllIlIlllIllIIlIIll;
        llllllllllllllllIlIlllIllIIlIIll.DISPATCHER = new CommandDispatcher();
        llllllllllllllllIlIlllIllIIlIIll.COMMAND_SOURCE = new ChatCommandSource(MinecraftClient.getInstance());
        llllllllllllllllIlIlllIllIIlIIll.commands = new ArrayList<Command>();
        llllllllllllllllIlIlllIllIIlIIll.commandInstances = new HashMap<Class<? extends Command>, Command>();
    }

    public void dispatch(String llllllllllllllllIlIlllIlIlllllll, CommandSource llllllllllllllllIlIlllIllIIIIIlI) throws CommandSyntaxException {
        Commands llllllllllllllllIlIlllIllIIIIIII;
        ParseResults llllllllllllllllIlIlllIllIIIIIIl = llllllllllllllllIlIlllIllIIIIIII.DISPATCHER.parse(llllllllllllllllIlIlllIlIlllllll, (Object)llllllllllllllllIlIlllIllIIIIIlI);
        llllllllllllllllIlIlllIllIIIIIII.DISPATCHER.execute(llllllllllllllllIlIlllIllIIIIIIl);
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        Commands llllllllllllllllIlIlllIlIllllIlI;
        return llllllllllllllllIlIlllIlIllllIlI.DISPATCHER;
    }

    @Override
    public void init() {
        Commands llllllllllllllllIlIlllIllIIlIIII;
        llllllllllllllllIlIlllIllIIlIIII.add(new BaritoneCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new VClipCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new HClipCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ClearChatCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new Count());
        llllllllllllllllIlIlllIllIIlIIII.add(new DismountCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new DamageCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new DropCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new EnchantCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new FakePlayerCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new FriendCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new CommandsCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new InventoryCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new LocateCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new NbtCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new NotebotCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new PanicCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new PeekCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new PluginsCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ProfileCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ReloadCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ResetCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new SayCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ServerCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new SwarmCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ToggleCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new SettingCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new GamemodeCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new SaveMapCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new ModulesCommand());
        llllllllllllllllIlIlllIllIIlIIII.add(new BindsCommand());
        llllllllllllllllIlIlllIllIIlIIII.commands.sort(Comparator.comparing(Command::getName));
    }

    public List<Command> getAll() {
        Commands llllllllllllllllIlIlllIlIllIlIll;
        return llllllllllllllllIlIlllIlIllIlIll.commands;
    }

    private static final class ChatCommandSource
    extends ClientCommandSource {
        public ChatCommandSource(MinecraftClient lllllllllllllllllIlIIIlllllIllll) {
            super(null, lllllllllllllllllIlIIIlllllIllll);
            ChatCommandSource lllllllllllllllllIlIIIllllllIIlI;
        }
    }
}

