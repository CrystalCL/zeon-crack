/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.Notebot;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;

public class NotebotCommand
extends Command {
    int ticks = -1;
    List<List<Integer>> song = new ArrayList<List<Integer>>();

    private void saveRecording(Path path) {
        if (this.song.size() < 1) {
            MeteorClient.EVENT_BUS.unsubscribe(this);
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(path.toFile());
            for (int i = 0; i < this.song.size() - 1; ++i) {
                List<Integer> list = this.song.get(i);
                fileWriter.write(String.format("%d:%d\n", list.get(0), list.get(1)));
            }
            List<Integer> list = this.song.get(this.song.size() - 1);
            fileWriter.write(String.format("%d:%d", list.get(0), list.get(1)));
            fileWriter.close();
            ChatUtils.prefixInfo("Notebot", String.format("Song saved. Length: (highlight)%d(default).", list.get(0)), new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(this);
        }
        catch (IOException iOException) {
            ChatUtils.prefixError("Notebot", "Couldn't create the file.", new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(this);
        }
    }

    private int lambda$build$7(CommandContext commandContext) throws CommandSyntaxException {
        MeteorClient.EVENT_BUS.unsubscribe(this);
        ChatUtils.prefixInfo("Notebot", "Recording cancelled", new Object[0]);
        return 1;
    }

    public NotebotCommand() {
        super("notebot", "Allows you load notebot files", new String[0]);
    }

    private int lambda$build$6(CommandContext commandContext) throws CommandSyntaxException {
        this.ticks = -1;
        this.song.clear();
        MeteorClient.EVENT_BUS.subscribe(this);
        ChatUtils.prefixInfo("Notebot", "Recording started", new Object[0]);
        return 1;
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        Notebot notebot = Modules.get().get(Notebot.class);
        notebot.Pause();
        return 1;
    }

    private static int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        Notebot notebot = Modules.get().get(Notebot.class);
        notebot.Pause();
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(NotebotCommand.literal("status").executes(NotebotCommand::lambda$build$0));
        literalArgumentBuilder.then(NotebotCommand.literal("pause").executes(NotebotCommand::lambda$build$1));
        literalArgumentBuilder.then(NotebotCommand.literal("resume").executes(NotebotCommand::lambda$build$2));
        literalArgumentBuilder.then(NotebotCommand.literal("stop").executes(NotebotCommand::lambda$build$3));
        literalArgumentBuilder.then(NotebotCommand.literal("play").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(NotebotCommand::lambda$build$4)));
        literalArgumentBuilder.then(NotebotCommand.literal("preview").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(NotebotCommand::lambda$build$5)));
        literalArgumentBuilder.then(NotebotCommand.literal("record").then(NotebotCommand.literal("start").executes(this::lambda$build$6)));
        literalArgumentBuilder.then(NotebotCommand.literal("record").then(NotebotCommand.literal("cancel").executes(this::lambda$build$7)));
        literalArgumentBuilder.then(NotebotCommand.literal("record").then(NotebotCommand.literal("save").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(this::lambda$build$8))));
    }

    private static int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        Path path;
        Notebot notebot = Modules.get().get(Notebot.class);
        String string = (String)commandContext.getArgument("name", String.class);
        if (string == null || string.equals("")) {
            ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
        }
        if (!(path = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", string))).toFile().exists()) {
            path = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.nbs", string));
        }
        notebot.loadSong(path.toFile());
        return 1;
    }

    @EventHandler
    public void onTick(TickEvent.Post post) {
        if (this.ticks == -1) {
            return;
        }
        ++this.ticks;
    }

    private static int lambda$build$5(CommandContext commandContext) throws CommandSyntaxException {
        Path path;
        Notebot notebot = Modules.get().get(Notebot.class);
        String string = (String)commandContext.getArgument("name", String.class);
        if (string == null || string == "") {
            ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
        }
        if (!(path = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", string))).toFile().exists()) {
            path = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.nbs", string));
        }
        notebot.previewSong(path.toFile());
        return 1;
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        Notebot notebot = Modules.get().get(Notebot.class);
        notebot.printStatus();
        return 1;
    }

    private static int lambda$build$3(CommandContext commandContext) throws CommandSyntaxException {
        Notebot notebot = Modules.get().get(Notebot.class);
        notebot.Stop();
        return 1;
    }

    private int lambda$build$8(CommandContext commandContext) throws CommandSyntaxException {
        String string = (String)commandContext.getArgument("name", String.class);
        if (string == null || string == "") {
            ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
        }
        Path path = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", string));
        this.saveRecording(path);
        return 1;
    }

    @EventHandler
    public void onReadPacket(PacketEvent.Receive receive) {
        PlaySoundS2CPacket PlaySoundS2CPacket2;
        if (receive.packet instanceof PlaySoundS2CPacket && (PlaySoundS2CPacket2 = (PlaySoundS2CPacket)receive.packet).getSound() == SoundEvents.BLOCK_NOTE_BLOCK_HARP) {
            if (this.ticks == -1) {
                this.ticks = 0;
            }
            this.song.add(Arrays.asList(this.ticks, this.getNote(PlaySoundS2CPacket2.getPitch())));
        }
    }

    private int getNote(float f) {
        for (int i = 0; i < 25; ++i) {
            if (!((double)((float)Math.pow(2.0, (double)(i - 12) / 12.0)) - 0.01 < (double)f) || !((double)((float)Math.pow(2.0, (double)(i - 12) / 12.0)) + 0.01 > (double)f)) continue;
            return i;
        }
        return 0;
    }
}

