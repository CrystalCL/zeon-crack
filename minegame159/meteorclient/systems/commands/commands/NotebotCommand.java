/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 *  net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket
 *  net.minecraft.sound.SoundEvents
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    /* synthetic */ int ticks;
    /* synthetic */ List<List<Integer>> song;

    public NotebotCommand() {
        super("notebot", "Allows you load notebot files", new String[0]);
        NotebotCommand llllllllllllllllIlllllllIIllllIl;
        llllllllllllllllIlllllllIIllllIl.ticks = -1;
        llllllllllllllllIlllllllIIllllIl.song = new ArrayList<List<Integer>>();
    }

    @EventHandler
    public void onTick(TickEvent.Post llllllllllllllllIlllllllIIllIlII) {
        NotebotCommand llllllllllllllllIlllllllIIllIIll;
        if (llllllllllllllllIlllllllIIllIIll.ticks == -1) {
            return;
        }
        ++llllllllllllllllIlllllllIIllIIll.ticks;
    }

    private int getNote(float llllllllllllllllIlllllllIIIlIlII) {
        for (int llllllllllllllllIlllllllIIIlIllI = 0; llllllllllllllllIlllllllIIIlIllI < 25; ++llllllllllllllllIlllllllIIIlIllI) {
            if (!((double)((float)Math.pow(2.0, (double)(llllllllllllllllIlllllllIIIlIllI - 12) / 12.0)) - 0.01 < (double)llllllllllllllllIlllllllIIIlIlII) || !((double)((float)Math.pow(2.0, (double)(llllllllllllllllIlllllllIIIlIllI - 12) / 12.0)) + 0.01 > (double)llllllllllllllllIlllllllIIIlIlII)) continue;
            return llllllllllllllllIlllllllIIIlIllI;
        }
        return 0;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllIlllllllIIllIlll) {
        NotebotCommand llllllllllllllllIlllllllIIlllIII;
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("status").executes(llllllllllllllllIllllllIllIllIII -> {
            Notebot llllllllllllllllIllllllIllIlIlll = Modules.get().get(Notebot.class);
            llllllllllllllllIllllllIllIlIlll.printStatus();
            return 1;
        }));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("pause").executes(llllllllllllllllIllllllIllIlllII -> {
            Notebot llllllllllllllllIllllllIllIllIll = Modules.get().get(Notebot.class);
            llllllllllllllllIllllllIllIllIll.Pause();
            return 1;
        }));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("resume").executes(llllllllllllllllIllllllIlllIIIII -> {
            Notebot llllllllllllllllIllllllIllIlllll = Modules.get().get(Notebot.class);
            llllllllllllllllIllllllIllIlllll.Pause();
            return 1;
        }));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("stop").executes(llllllllllllllllIllllllIlllIIlII -> {
            Notebot llllllllllllllllIllllllIlllIIIll = Modules.get().get(Notebot.class);
            llllllllllllllllIllllllIlllIIIll.Stop();
            return 1;
        }));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("play").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(llllllllllllllllIllllllIlllIllIl -> {
            Path llllllllllllllllIllllllIlllIlIlI;
            Notebot llllllllllllllllIllllllIlllIllII = Modules.get().get(Notebot.class);
            String llllllllllllllllIllllllIlllIlIll = (String)llllllllllllllllIllllllIlllIllIl.getArgument("name", String.class);
            if (llllllllllllllllIllllllIlllIlIll == null || llllllllllllllllIllllllIlllIlIll.equals("")) {
                ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
            }
            if (!(llllllllllllllllIllllllIlllIlIlI = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", llllllllllllllllIllllllIlllIlIll))).toFile().exists()) {
                llllllllllllllllIllllllIlllIlIlI = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.nbs", llllllllllllllllIllllllIlllIlIll));
            }
            llllllllllllllllIllllllIlllIllII.loadSong(llllllllllllllllIllllllIlllIlIlI.toFile());
            return 1;
        })));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("preview").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(llllllllllllllllIllllllIlllllIIl -> {
            Path llllllllllllllllIllllllIllllIllI;
            Notebot llllllllllllllllIllllllIlllllIII = Modules.get().get(Notebot.class);
            String llllllllllllllllIllllllIllllIlll = (String)llllllllllllllllIllllllIlllllIIl.getArgument("name", String.class);
            if (llllllllllllllllIllllllIllllIlll == null || llllllllllllllllIllllllIllllIlll == "") {
                ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
            }
            if (!(llllllllllllllllIllllllIllllIllI = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", llllllllllllllllIllllllIllllIlll))).toFile().exists()) {
                llllllllllllllllIllllllIllllIllI = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.nbs", llllllllllllllllIllllllIllllIlll));
            }
            llllllllllllllllIllllllIlllllIII.previewSong(llllllllllllllllIllllllIllllIllI.toFile());
            return 1;
        })));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("record").then(NotebotCommand.literal("start").executes(llllllllllllllllIllllllIllllllll -> {
            NotebotCommand llllllllllllllllIllllllIlllllllI;
            llllllllllllllllIllllllIlllllllI.ticks = -1;
            llllllllllllllllIllllllIlllllllI.song.clear();
            MeteorClient.EVENT_BUS.subscribe(llllllllllllllllIllllllIlllllllI);
            ChatUtils.prefixInfo("Notebot", "Recording started", new Object[0]);
            return 1;
        })));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("record").then(NotebotCommand.literal("cancel").executes(llllllllllllllllIlllllllIIIIIIll -> {
            NotebotCommand llllllllllllllllIlllllllIIIIIIlI;
            MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllIlllllllIIIIIIlI);
            ChatUtils.prefixInfo("Notebot", "Recording cancelled", new Object[0]);
            return 1;
        })));
        llllllllllllllllIlllllllIIllIlll.then(NotebotCommand.literal("record").then(NotebotCommand.literal("save").then(NotebotCommand.argument("name", StringArgumentType.greedyString()).executes(llllllllllllllllIlllllllIIIIllII -> {
            NotebotCommand llllllllllllllllIlllllllIIIIlIIl;
            String llllllllllllllllIlllllllIIIIlIll = (String)llllllllllllllllIlllllllIIIIllII.getArgument("name", String.class);
            if (llllllllllllllllIlllllllIIIIlIll == null || llllllllllllllllIlllllllIIIIlIll == "") {
                ChatUtils.prefixError("Notebot", "Invalid name", new Object[0]);
            }
            Path llllllllllllllllIlllllllIIIIlIlI = MeteorClient.FOLDER.toPath().resolve(String.format("notebot/%s.txt", llllllllllllllllIlllllllIIIIlIll));
            llllllllllllllllIlllllllIIIIlIIl.saveRecording(llllllllllllllllIlllllllIIIIlIlI);
            return 1;
        }))));
    }

    private void saveRecording(Path llllllllllllllllIlllllllIIIllllI) {
        NotebotCommand llllllllllllllllIlllllllIIIlllll;
        if (llllllllllllllllIlllllllIIIlllll.song.size() < 1) {
            MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllIlllllllIIIlllll);
            return;
        }
        try {
            FileWriter llllllllllllllllIlllllllIIlIIIlI = new FileWriter(llllllllllllllllIlllllllIIIllllI.toFile());
            for (int llllllllllllllllIlllllllIIlIIIll = 0; llllllllllllllllIlllllllIIlIIIll < llllllllllllllllIlllllllIIIlllll.song.size() - 1; ++llllllllllllllllIlllllllIIlIIIll) {
                List<Integer> llllllllllllllllIlllllllIIlIIlII = llllllllllllllllIlllllllIIIlllll.song.get(llllllllllllllllIlllllllIIlIIIll);
                llllllllllllllllIlllllllIIlIIIlI.write(String.format("%d:%d\n", llllllllllllllllIlllllllIIlIIlII.get(0), llllllllllllllllIlllllllIIlIIlII.get(1)));
            }
            List<Integer> llllllllllllllllIlllllllIIlIIIIl = llllllllllllllllIlllllllIIIlllll.song.get(llllllllllllllllIlllllllIIIlllll.song.size() - 1);
            llllllllllllllllIlllllllIIlIIIlI.write(String.format("%d:%d", llllllllllllllllIlllllllIIlIIIIl.get(0), llllllllllllllllIlllllllIIlIIIIl.get(1)));
            llllllllllllllllIlllllllIIlIIIlI.close();
            ChatUtils.prefixInfo("Notebot", String.format("Song saved. Length: (highlight)%d(default).", llllllllllllllllIlllllllIIlIIIIl.get(0)), new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllIlllllllIIIlllll);
        }
        catch (IOException llllllllllllllllIlllllllIIlIIIII) {
            ChatUtils.prefixError("Notebot", "Couldn't create the file.", new Object[0]);
            MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllIlllllllIIIlllll);
        }
    }

    @EventHandler
    public void onReadPacket(PacketEvent.Receive llllllllllllllllIlllllllIIlIlIll) {
        PlaySoundS2CPacket llllllllllllllllIlllllllIIlIllll;
        if (llllllllllllllllIlllllllIIlIlIll.packet instanceof PlaySoundS2CPacket && (llllllllllllllllIlllllllIIlIllll = (PlaySoundS2CPacket)llllllllllllllllIlllllllIIlIlIll.packet).getSound() == SoundEvents.BLOCK_NOTE_BLOCK_HARP) {
            NotebotCommand llllllllllllllllIlllllllIIlIllII;
            if (llllllllllllllllIlllllllIIlIllII.ticks == -1) {
                llllllllllllllllIlllllllIIlIllII.ticks = 0;
            }
            llllllllllllllllIlllllllIIlIllII.song.add(Arrays.asList(llllllllllllllllIlllllllIIlIllII.ticks, llllllllllllllllIlllllllIIlIllII.getNote(llllllllllllllllIlllllllIIlIllll.getPitch())));
        }
    }
}

