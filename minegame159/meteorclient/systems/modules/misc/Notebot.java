/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Position
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.block.NoteBlock
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.block.BlockState
 *  net.minecraft.state.property.Property
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
 *  net.minecraft.sound.SoundEvents
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
 *  org.apache.commons.io.FilenameUtils
 */
package minegame159.meteorclient.systems.modules.misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.notebot.NBSDecoder;
import minegame159.meteorclient.utils.notebot.nbs.Layer;
import minegame159.meteorclient.utils.notebot.nbs.Note;
import minegame159.meteorclient.utils.notebot.nbs.Song;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.hit.HitResult;
import net.minecraft.block.NoteBlock;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.commons.io.FilenameUtils;

public class Notebot
extends Module {
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ List<Integer> uniqueNotes;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ List<BlockPos> scannedNoteblocks;
    private final /* synthetic */ HashMap<Integer, BlockPos> blockPositions;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<Boolean> moveNotes;
    private /* synthetic */ Stage stage;
    private /* synthetic */ boolean isPlaying;
    private final /* synthetic */ Setting<Integer> tickDelay;
    private /* synthetic */ int offset;
    private final /* synthetic */ HashMap<Integer, Integer> song;
    private /* synthetic */ int currentNote;
    private final /* synthetic */ List<BlockPos> possibleBlockPos;
    private /* synthetic */ int lastKey;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private /* synthetic */ int ticks;
    private final /* synthetic */ SettingGroup sgGeneral;

    private void tuneRotate() {
        Notebot lllllllllllllllllIllllIIlllIIllI;
        BlockPos lllllllllllllllllIllllIIlllIIlIl = lllllllllllllllllIllllIIlllIIllI.blockPositions.get(lllllllllllllllllIllllIIlllIIllI.uniqueNotes.get(lllllllllllllllllIllllIIlllIIllI.currentNote));
        if (lllllllllllllllllIllllIIlllIIlIl == null) {
            return;
        }
        if (!lllllllllllllllllIllllIIlllIIllI.tuneBlock(lllllllllllllllllIllllIIlllIIlIl, lllllllllllllllllIllllIIlllIIllI.uniqueNotes.get(lllllllllllllllllIllllIIlllIIllI.currentNote))) {
            lllllllllllllllllIllllIIlllIIllI.Disable();
        }
    }

    @Override
    public void onActivate() {
        Notebot lllllllllllllllllIllllIllIlIllII;
        lllllllllllllllllIllllIllIlIllII.ticks = 0;
        lllllllllllllllllIllllIllIlIllII.resetVariables();
    }

    private boolean setupBlocks() {
        Notebot lllllllllllllllllIllllIlIIIIIlII;
        lllllllllllllllllIllllIlIIIIIlII.song.values().forEach(lllllllllllllllllIllllIIlIlIIIll -> {
            Notebot lllllllllllllllllIllllIIlIlIIllI;
            if (!lllllllllllllllllIllllIIlIlIIllI.uniqueNotes.contains(lllllllllllllllllIllllIIlIlIIIll)) {
                lllllllllllllllllIllllIIlIlIIllI.uniqueNotes.add((Integer)lllllllllllllllllIllllIIlIlIIIll);
            }
        });
        lllllllllllllllllIllllIlIIIIIlII.scanForNoteblocks();
        if (lllllllllllllllllIllllIlIIIIIlII.uniqueNotes.size() > lllllllllllllllllIllllIlIIIIIlII.possibleBlockPos.size() + lllllllllllllllllIllllIlIIIIIlII.scannedNoteblocks.size()) {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIIIIIlII, "Too many notes. %d is the maximum.", lllllllllllllllllIllllIlIIIIIlII.possibleBlockPos.size());
            return false;
        }
        lllllllllllllllllIllllIlIIIIIlII.currentNote = 0;
        lllllllllllllllllIllllIlIIIIIlII.offset = 0;
        lllllllllllllllllIllllIlIIIIIlII.stage = Stage.SetUp;
        return true;
    }

    private String getStatus() {
        Notebot lllllllllllllllllIllllIllIIIlIIl;
        if (!lllllllllllllllllIllllIllIIIlIIl.isActive()) {
            return "Module disabled.";
        }
        if (lllllllllllllllllIllllIllIIIlIIl.song.isEmpty()) {
            return "No song loaded.";
        }
        if (lllllllllllllllllIllllIllIIIlIIl.isPlaying) {
            return String.format("Playing song. %d/%d", lllllllllllllllllIllllIllIIIlIIl.currentNote, lllllllllllllllllIllllIllIIIlIIl.lastKey);
        }
        if (lllllllllllllllllIllllIllIIIlIIl.stage == Stage.Playing || lllllllllllllllllIllllIllIIIlIIl.stage == Stage.Preview) {
            return "Ready to play.";
        }
        if (lllllllllllllllllIllllIllIIIlIIl.stage == Stage.SetUp || lllllllllllllllllIllllIllIIIlIIl.stage == Stage.Tune) {
            return "Setting up the noteblocks.";
        }
        return String.format("Stage: %s.", lllllllllllllllllIllllIllIIIlIIl.stage.toString());
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIllllIllIlIIIlI) {
        Notebot lllllllllllllllllIllllIllIlIIIll;
        ++lllllllllllllllllIllllIllIlIIIll.ticks;
        switch (lllllllllllllllllIllllIllIlIIIll.stage) {
            case Preview: {
                lllllllllllllllllIllllIllIlIIIll.onTickPreview();
                break;
            }
            case SetUp: {
                lllllllllllllllllIllllIllIlIIIll.onTickSetup();
                break;
            }
            case Tune: {
                lllllllllllllllllIllllIllIlIIIll.onTickTune();
                break;
            }
            case Playing: {
                lllllllllllllllllIllllIllIlIIIll.onTickPlay();
                break;
            }
        }
    }

    public void Stop() {
        Notebot lllllllllllllllllIllllIlIlllIIll;
        ChatUtils.moduleInfo(lllllllllllllllllIllllIlIlllIIll, "Stopping.", new Object[0]);
        if (lllllllllllllllllIllllIlIlllIIll.stage == Stage.SetUp || lllllllllllllllllIllllIlIlllIIll.stage == Stage.Tune) {
            lllllllllllllllllIllllIlIlllIIll.resetVariables();
        } else {
            lllllllllllllllllIllllIlIlllIIll.isPlaying = false;
            lllllllllllllllllIllllIlIlllIIll.currentNote = 0;
        }
    }

    public void loadSong(File lllllllllllllllllIllllIlIllIlIIl) {
        Notebot lllllllllllllllllIllllIlIllIlIlI;
        if (!lllllllllllllllllIllllIlIllIlIlI.isActive()) {
            lllllllllllllllllIllllIlIllIlIlI.toggle();
        }
        if (!lllllllllllllllllIllllIlIllIlIlI.loadFileToMap(lllllllllllllllllIllllIlIllIlIIl)) {
            return;
        }
        if (!lllllllllllllllllIllllIlIllIlIlI.setupBlocks()) {
            return;
        }
        ChatUtils.moduleInfo(lllllllllllllllllIllllIlIllIlIlI, "Loading song \"%s\".", lllllllllllllllllIllllIlIllIlIlI.getFileLabel(lllllllllllllllllIllllIlIllIlIIl.toPath()));
    }

    private String getFileLabel(Path lllllllllllllllllIllllIllIIIIIll) {
        return lllllllllllllllllIllllIllIIIIIll.getFileName().toString().replace(".txt", "").replace(".nbs", "");
    }

    private void onTickPlay() {
        Notebot lllllllllllllllllIllllIIllIlIIIl;
        if (!lllllllllllllllllIllllIIllIlIIIl.isPlaying) {
            return;
        }
        if (lllllllllllllllllIllllIIllIlIIIl.song == null) {
            return;
        }
        if (lllllllllllllllllIllllIIllIlIIIl.currentNote >= lllllllllllllllllIllllIIllIlIIIl.lastKey) {
            lllllllllllllllllIllllIIllIlIIIl.Stop();
            return;
        }
        if (lllllllllllllllllIllllIIllIlIIIl.song.containsKey(lllllllllllllllllIllllIIllIlIIIl.currentNote)) {
            int lllllllllllllllllIllllIIllIlIIll = lllllllllllllllllIllllIIllIlIIIl.song.get(lllllllllllllllllIllllIIllIlIIIl.currentNote);
            BlockPos lllllllllllllllllIllllIIllIlIIlI = lllllllllllllllllIllllIIllIlIIIl.blockPositions.get(lllllllllllllllllIllllIIllIlIIll);
            Rotations.rotate(Rotations.getYaw(lllllllllllllllllIllllIIllIlIIlI), Rotations.getPitch(lllllllllllllllllIllllIIllIlIIlI), 100, lllllllllllllllllIllllIIllIlIIIl::playRotate);
        } else {
            ++lllllllllllllllllIllllIIllIlIIIl.currentNote;
        }
    }

    public void Pause() {
        Notebot lllllllllllllllllIllllIlIlllIlIl;
        if (!lllllllllllllllllIllllIlIlllIlIl.isActive()) {
            lllllllllllllllllIllllIlIlllIlIl.toggle();
        }
        if (lllllllllllllllllIllllIlIlllIlIl.isPlaying) {
            ChatUtils.moduleInfo(lllllllllllllllllIllllIlIlllIlIl, "Pausing.", new Object[0]);
            lllllllllllllllllIllllIlIlllIlIl.isPlaying = false;
        } else {
            ChatUtils.moduleInfo(lllllllllllllllllIllllIlIlllIlIl, "Resuming.", new Object[0]);
            lllllllllllllllllIllllIlIlllIlIl.isPlaying = true;
        }
    }

    private void onTickPreview() {
        Notebot lllllllllllllllllIllllIlIIIIIIII;
        if (lllllllllllllllllIllllIlIIIIIIII.isPlaying && lllllllllllllllllIllllIlIIIIIIII.mc.player != null) {
            if (lllllllllllllllllIllllIlIIIIIIII.currentNote >= lllllllllllllllllIllllIlIIIIIIII.lastKey) {
                lllllllllllllllllIllllIlIIIIIIII.Stop();
            }
            if (lllllllllllllllllIllllIlIIIIIIII.song.containsKey(lllllllllllllllllIllllIlIIIIIIII.currentNote)) {
                lllllllllllllllllIllllIlIIIIIIII.mc.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HARP, 2.0f, (float)Math.pow(2.0, (double)(lllllllllllllllllIllllIlIIIIIIII.song.get(lllllllllllllllllIllllIlIIIIIIII.currentNote) - 12) / 12.0));
            }
            ++lllllllllllllllllIllllIlIIIIIIII.currentNote;
        }
    }

    public void Play() {
        Notebot lllllllllllllllllIllllIlIllllIIl;
        if (lllllllllllllllllIllllIlIllllIIl.mc.player == null) {
            return;
        }
        if (lllllllllllllllllIllllIlIllllIIl.mc.player.abilities.creativeMode && lllllllllllllllllIllllIlIllllIIl.stage != Stage.Preview) {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIllllIIl, "You need to be in survival mode.", new Object[0]);
        } else if (lllllllllllllllllIllllIlIllllIIl.stage == Stage.Preview || lllllllllllllllllIllllIlIllllIIl.stage == Stage.Playing) {
            lllllllllllllllllIllllIlIllllIIl.isPlaying = true;
            ChatUtils.moduleInfo(lllllllllllllllllIllllIlIllllIIl, "Playing.", new Object[0]);
        } else {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIllllIIl, "No song loaded.", new Object[0]);
        }
    }

    public Notebot() {
        super(Categories.Misc, "notebot", "Plays noteblock nicely");
        Notebot lllllllllllllllllIllllIllIllIIll;
        lllllllllllllllllIllllIllIllIIll.sgGeneral = lllllllllllllllllIllllIllIllIIll.settings.getDefaultGroup();
        lllllllllllllllllIllllIllIllIIll.sgRender = lllllllllllllllllIllllIllIllIIll.settings.createGroup("Render", false);
        lllllllllllllllllIllllIllIllIIll.tickDelay = lllllllllllllllllIllllIllIllIIll.sgGeneral.add(new IntSetting.Builder().name("tick-delay").description("The delay when loading a song.").defaultValue(2).min(0).sliderMax(20).build());
        lllllllllllllllllIllllIllIllIIll.moveNotes = lllllllllllllllllIllllIllIllIIll.sgGeneral.add(new BoolSetting.Builder().name("move-notes").description("Move notes by one tick, if multiple notes supposed to play in one tick.").defaultValue(false).build());
        lllllllllllllllllIllllIllIllIIll.render = lllllllllllllllllIllllIllIllIIll.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the outline around the noteblocks.").defaultValue(true).build());
        lllllllllllllllllIllllIllIllIIll.shapeMode = lllllllllllllllllIllllIllIllIIll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lllllllllllllllllIllllIllIllIIll.sideColor = lllllllllllllllllIllllIllIllIIll.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lllllllllllllllllIllllIllIllIIll.lineColor = lllllllllllllllllIllllIllIllIIll.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lllllllllllllllllIllllIllIllIIll.possibleBlockPos = new ArrayList(Collections.emptyList());
        lllllllllllllllllIllllIllIllIIll.stage = Stage.None;
        lllllllllllllllllIllllIllIllIIll.isPlaying = false;
        lllllllllllllllllIllllIllIllIIll.song = new HashMap();
        lllllllllllllllllIllllIllIllIIll.uniqueNotes = new ArrayList(Collections.emptyList());
        lllllllllllllllllIllllIllIllIIll.blockPositions = new HashMap();
        lllllllllllllllllIllllIllIllIIll.scannedNoteblocks = new ArrayList<BlockPos>();
        lllllllllllllllllIllllIllIllIIll.currentNote = 0;
        lllllllllllllllllIllllIllIllIIll.lastKey = -1;
        lllllllllllllllllIllllIllIllIIll.offset = 0;
        lllllllllllllllllIllllIllIllIIll.ticks = 0;
        for (int lllllllllllllllllIllllIllIllIlII = -5; lllllllllllllllllIllllIllIllIlII < 5; ++lllllllllllllllllIllllIllIllIlII) {
            for (int lllllllllllllllllIllllIllIllIlIl = -5; lllllllllllllllllIllllIllIllIlIl < 5; ++lllllllllllllllllIllllIllIllIlIl) {
                BlockPos lllllllllllllllllIllllIllIllIllI;
                if (lllllllllllllllllIllllIllIllIlII == 0 && lllllllllllllllllIllllIllIllIlIl == 0 || !((lllllllllllllllllIllllIllIllIllI = new BlockPos(lllllllllllllllllIllllIllIllIlIl, 0, lllllllllllllllllIllllIllIllIlII)).getSquaredDistance(0.0, 0.0, 0.0, true) < 17.99)) continue;
                lllllllllllllllllIllllIllIllIIll.possibleBlockPos.add(lllllllllllllllllIllllIllIllIllI);
            }
        }
        lllllllllllllllllIllllIllIllIIll.possibleBlockPos.sort((lllllllllllllllllIllllIIIlIlIIII, lllllllllllllllllIllllIIIlIIllll) -> {
            double lllllllllllllllllIllllIIIlIIlllI = lllllllllllllllllIllllIIIlIlIIII.getSquaredDistance(new Vec3i(0, 0, 0));
            double lllllllllllllllllIllllIIIlIIllIl = lllllllllllllllllIllllIIIlIIllll.getSquaredDistance(new Vec3i(0, 0, 0));
            return Double.compare(lllllllllllllllllIllllIIIlIIlllI, lllllllllllllllllIllllIIIlIIllIl);
        });
    }

    private Direction rayTraceCheck(BlockPos lllllllllllllllllIllllIIlIllIlIl, boolean lllllllllllllllllIllllIIlIllIIII) {
        Notebot lllllllllllllllllIllllIIlIllIIlI;
        Vec3d lllllllllllllllllIllllIIlIllIIll = new Vec3d(lllllllllllllllllIllllIIlIllIIlI.mc.player.getX(), lllllllllllllllllIllllIIlIllIIlI.mc.player.getY() + (double)lllllllllllllllllIllllIIlIllIIlI.mc.player.getEyeHeight(lllllllllllllllllIllllIIlIllIIlI.mc.player.getPose()), lllllllllllllllllIllllIIlIllIIlI.mc.player.getZ());
        for (Direction lllllllllllllllllIllllIIlIllIlll : Direction.values()) {
            RaycastContext lllllllllllllllllIllllIIlIlllIIl = new RaycastContext(lllllllllllllllllIllllIIlIllIIll, new Vec3d((double)lllllllllllllllllIllllIIlIllIlIl.getX() + 0.5 + (double)lllllllllllllllllIllllIIlIllIlll.getVector().getX() * 0.5, (double)lllllllllllllllllIllllIIlIllIlIl.getY() + 0.5 + (double)lllllllllllllllllIllllIIlIllIlll.getVector().getY() * 0.5, (double)lllllllllllllllllIllllIIlIllIlIl.getZ() + 0.5 + (double)lllllllllllllllllIllllIIlIllIlll.getVector().getZ() * 0.5), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)lllllllllllllllllIllllIIlIllIIlI.mc.player);
            BlockHitResult lllllllllllllllllIllllIIlIlllIII = lllllllllllllllllIllllIIlIllIIlI.mc.world.raycast(lllllllllllllllllIllllIIlIlllIIl);
            if (lllllllllllllllllIllllIIlIlllIII == null || lllllllllllllllllIllllIIlIlllIII.getType() != Type.BLOCK || !lllllllllllllllllIllllIIlIlllIII.getBlockPos().equals((Object)lllllllllllllllllIllllIIlIllIlIl)) continue;
            return lllllllllllllllllIllllIIlIllIlll;
        }
        if (lllllllllllllllllIllllIIlIllIIII) {
            if ((double)lllllllllllllllllIllllIIlIllIlIl.getY() > lllllllllllllllllIllllIIlIllIIll.y) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }
        return null;
    }

    private void playRotate() {
        Notebot lllllllllllllllllIllllIIllIIIlll;
        if (lllllllllllllllllIllllIIllIIIlll.mc.interactionManager == null) {
            return;
        }
        try {
            int lllllllllllllllllIllllIIllIIlIlI = lllllllllllllllllIllllIIllIIIlll.song.get(lllllllllllllllllIllllIIllIIIlll.currentNote);
            BlockPos lllllllllllllllllIllllIIllIIlIIl = lllllllllllllllllIllllIIllIIIlll.blockPositions.get(lllllllllllllllllIllllIIllIIlIlI);
            lllllllllllllllllIllllIIllIIIlll.mc.interactionManager.attackBlock(lllllllllllllllllIllllIIllIIlIIl, Direction.DOWN);
            ++lllllllllllllllllIllllIIllIIIlll.currentNote;
        }
        catch (NullPointerException lllllllllllllllllIllllIIllIIlIII) {
            lllllllllllllllllIllllIIllIIlIII.printStackTrace();
        }
    }

    /*
     * WARNING - void declaration
     */
    private void onTickSetup() {
        void lllllllllllllllllIllllIIllllIlIl;
        Notebot lllllllllllllllllIllllIIllllIIll;
        if (lllllllllllllllllIllllIIllllIIll.ticks < lllllllllllllllllIllllIIllllIIll.tickDelay.get()) {
            return;
        }
        lllllllllllllllllIllllIIllllIIll.ticks = 0;
        if (lllllllllllllllllIllllIIllllIIll.currentNote >= lllllllllllllllllIllllIIllllIIll.uniqueNotes.size()) {
            lllllllllllllllllIllllIIllllIIll.stage = Stage.Playing;
            ChatUtils.moduleInfo(lllllllllllllllllIllllIIllllIIll, "Loading done.", new Object[0]);
            lllllllllllllllllIllllIIllllIIll.Play();
            return;
        }
        int lllllllllllllllllIllllIIllllIllI = lllllllllllllllllIllllIIllllIIll.currentNote + lllllllllllllllllIllllIIllllIIll.offset;
        if (lllllllllllllllllIllllIIllllIllI < lllllllllllllllllIllllIIllllIIll.scannedNoteblocks.size()) {
            BlockPos lllllllllllllllllIllllIIlllllIlI = lllllllllllllllllIllllIIllllIIll.scannedNoteblocks.get(lllllllllllllllllIllllIIllllIllI);
            if (lllllllllllllllllIllllIIllllIIll.mc.world.getBlockState(lllllllllllllllllIllllIIlllllIlI).getBlock() != Blocks.NOTE_BLOCK) {
                ++lllllllllllllllllIllllIIllllIIll.offset;
            } else {
                lllllllllllllllllIllllIIllllIIll.blockPositions.put(lllllllllllllllllIllllIIllllIIll.uniqueNotes.get(lllllllllllllllllIllllIIllllIIll.currentNote), lllllllllllllllllIllllIIlllllIlI);
                lllllllllllllllllIllllIIllllIIll.stage = Stage.Tune;
            }
            return;
        }
        int lllllllllllllllllIllllIIllllIlII = InvUtils.findItemInHotbar(Items.NOTE_BLOCK);
        if (lllllllllllllllllIllllIIllllIlII == -1) {
            ChatUtils.moduleError(lllllllllllllllllIllllIIllllIIll, "Not enough noteblocks", new Object[0]);
            lllllllllllllllllIllllIIllllIIll.Disable();
            return;
        }
        lllllllllllllllllIllllIIllllIllI -= lllllllllllllllllIllllIIllllIIll.scannedNoteblocks.size();
        try {
            BlockPos lllllllllllllllllIllllIIlllllIIl = lllllllllllllllllIllllIIllllIIll.mc.player.getBlockPos().add((Vec3i)lllllllllllllllllIllllIIllllIIll.possibleBlockPos.get(lllllllllllllllllIllllIIllllIllI));
        }
        catch (IndexOutOfBoundsException lllllllllllllllllIllllIIlllllIII) {
            ChatUtils.moduleError(lllllllllllllllllIllllIIllllIIll, "Not enough valid positions.", new Object[0]);
            lllllllllllllllllIllllIIllllIIll.Disable();
            return;
        }
        if (lllllllllllllllllIllllIIllllIIll.mc.world.getBlockState(lllllllllllllllllIllllIIllllIlIl.down()).getBlock() == Blocks.NOTE_BLOCK) {
            ++lllllllllllllllllIllllIIllllIIll.offset;
            return;
        }
        if (!BlockUtils.place((BlockPos)lllllllllllllllllIllllIIllllIlIl, Hand.MAIN_HAND, lllllllllllllllllIllllIIllllIlII, true, 100, true)) {
            ++lllllllllllllllllIllllIIllllIIll.offset;
            return;
        }
        lllllllllllllllllIllllIIllllIIll.blockPositions.put(lllllllllllllllllIllllIIllllIIll.uniqueNotes.get(lllllllllllllllllIllllIIllllIIll.currentNote), (BlockPos)lllllllllllllllllIllllIIllllIlIl);
        lllllllllllllllllIllllIIllllIIll.stage = Stage.Tune;
    }

    public void Disable() {
        Notebot lllllllllllllllllIllllIlIllIllll;
        lllllllllllllllllIllllIlIllIllll.resetVariables();
        ChatUtils.moduleInfo(lllllllllllllllllIllllIlIllIllll, "Stopping.", new Object[0]);
        if (!lllllllllllllllllIllllIlIllIllll.isActive()) {
            lllllllllllllllllIllllIlIllIllll.toggle();
        }
    }

    private void onTickTune() {
        Notebot lllllllllllllllllIllllIIlllIllII;
        if (lllllllllllllllllIllllIIlllIllII.ticks < lllllllllllllllllIllllIIlllIllII.tickDelay.get()) {
            return;
        }
        lllllllllllllllllIllllIIlllIllII.ticks = 0;
        BlockPos lllllllllllllllllIllllIIlllIlIll = lllllllllllllllllIllllIIlllIllII.blockPositions.get(lllllllllllllllllIllllIIlllIllII.uniqueNotes.get(lllllllllllllllllIllllIIlllIllII.currentNote));
        if (lllllllllllllllllIllllIIlllIlIll == null) {
            return;
        }
        Rotations.rotate(Rotations.getYaw(lllllllllllllllllIllllIIlllIlIll), Rotations.getPitch(lllllllllllllllllIllllIIlllIlIll), 100, lllllllllllllllllIllllIIlllIllII::tuneRotate);
    }

    /*
     * WARNING - void declaration
     */
    private boolean loadTextFile(File lllllllllllllllllIllllIlIlIIIllI) {
        void lllllllllllllllllIllllIlIlIIIlIl;
        Notebot lllllllllllllllllIllllIlIlIIIlII;
        try {
            List<String> lllllllllllllllllIllllIlIlIlIIIl = Files.readAllLines(lllllllllllllllllIllllIlIlIIIllI.toPath());
        }
        catch (IOException lllllllllllllllllIllllIlIlIlIIII) {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIlIIIlII, "Error while reading \"%s\"", lllllllllllllllllIllllIlIlIIIllI.getName());
            return false;
        }
        lllllllllllllllllIllllIlIlIIIlII.resetVariables();
        for (int lllllllllllllllllIllllIlIlIIlIII = 0; lllllllllllllllllIllllIlIlIIlIII < lllllllllllllllllIllllIlIlIIIlIl.size(); ++lllllllllllllllllIllllIlIlIIlIII) {
            void lllllllllllllllllIllllIlIlIIlIIl;
            void lllllllllllllllllIllllIlIlIIlIlI;
            block8: {
                String[] lllllllllllllllllIllllIlIlIIlIll = ((String)lllllllllllllllllIllllIlIlIIIlIl.get(lllllllllllllllllIllllIlIlIIlIII)).split(":");
                if (lllllllllllllllllIllllIlIlIIlIll.length < 2) {
                    ChatUtils.moduleWarning(lllllllllllllllllIllllIlIlIIIlII, "Malformed line %d", lllllllllllllllllIllllIlIlIIlIII);
                    continue;
                }
                try {
                    int lllllllllllllllllIllllIlIlIIlllI = Integer.parseInt(lllllllllllllllllIllllIlIlIIlIll[0]);
                    int lllllllllllllllllIllllIlIlIIllIl = Integer.parseInt(lllllllllllllllllIllllIlIlIIlIll[1]);
                    if (lllllllllllllllllIllllIlIlIIlIll.length <= 2) break block8;
                    int lllllllllllllllllIllllIlIlIIllll = Integer.parseInt(lllllllllllllllllIllllIlIlIIlIll[2]);
                    if (lllllllllllllllllIllllIlIlIIllll == 1 || lllllllllllllllllIllllIlIlIIllll == 2 || lllllllllllllllllIllllIlIlIIllll == 3) continue;
                    if (lllllllllllllllllIllllIlIlIIllll == 11) {
                    }
                    break block8;
                }
                catch (NumberFormatException lllllllllllllllllIllllIlIlIIllII) {
                    ChatUtils.moduleWarning(lllllllllllllllllIllllIlIlIIIlII, "Invalid character at line %d", lllllllllllllllllIllllIlIlIIlIII);
                }
                continue;
            }
            if (lllllllllllllllllIllllIlIlIIIlII.moveNotes.get().booleanValue() && lllllllllllllllllIllllIlIlIIIlII.song.containsKey((int)lllllllllllllllllIllllIlIlIIlIlI)) {
                lllllllllllllllllIllllIlIlIIIlII.song.put((int)(lllllllllllllllllIllllIlIlIIlIlI + true), (int)lllllllllllllllllIllllIlIlIIlIIl);
                lllllllllllllllllIllllIlIlIIIlII.lastKey = lllllllllllllllllIllllIlIlIIlIlI + true;
                continue;
            }
            lllllllllllllllllIllllIlIlIIIlII.song.put((int)lllllllllllllllllIllllIlIlIIlIlI, (int)lllllllllllllllllIllllIlIlIIlIIl);
            lllllllllllllllllIllllIlIlIIIlII.lastKey = lllllllllllllllllIllllIlIlIIlIlI;
        }
        return true;
    }

    @EventHandler
    private void onRender(RenderEvent lllllllllllllllllIllllIllIlIIllI) {
        Notebot lllllllllllllllllIllllIllIlIIlll;
        if (!lllllllllllllllllIllllIllIlIIlll.render.get().booleanValue()) {
            return;
        }
        if (lllllllllllllllllIllllIllIlIIlll.stage != Stage.SetUp && lllllllllllllllllIllllIllIlIIlll.stage != Stage.Tune && !lllllllllllllllllIllllIllIlIIlll.isPlaying) {
            return;
        }
        lllllllllllllllllIllllIllIlIIlll.blockPositions.values().forEach(lllllllllllllllllIllllIIIlIllIll -> {
            Notebot lllllllllllllllllIllllIIIllIIlII;
            double lllllllllllllllllIllllIIIllIIIlI = lllllllllllllllllIllllIIIlIllIll.getX();
            double lllllllllllllllllIllllIIIllIIIIl = lllllllllllllllllIllllIIIlIllIll.getY();
            double lllllllllllllllllIllllIIIllIIIII = lllllllllllllllllIllllIIIlIllIll.getZ();
            double lllllllllllllllllIllllIIIlIlllll = lllllllllllllllllIllllIIIlIllIll.getX() + 1;
            double lllllllllllllllllIllllIIIlIllllI = lllllllllllllllllIllllIIIlIllIll.getY() + 1;
            double lllllllllllllllllIllllIIIlIlllIl = lllllllllllllllllIllllIIIlIllIll.getZ() + 1;
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, lllllllllllllllllIllllIIIllIIIlI, lllllllllllllllllIllllIIIllIIIIl, lllllllllllllllllIllllIIIllIIIII, lllllllllllllllllIllllIIIlIlllll, lllllllllllllllllIllllIIIlIllllI, lllllllllllllllllIllllIIIlIlllIl, lllllllllllllllllIllllIIIllIIlII.sideColor.get(), lllllllllllllllllIllllIIIllIIlII.lineColor.get(), lllllllllllllllllIllllIIIllIIlII.shapeMode.get(), 0);
        });
    }

    private boolean isValidFile(Path lllllllllllllllllIllllIlIllllllI) {
        String lllllllllllllllllIllllIlIlllllIl = FilenameUtils.getExtension((String)lllllllllllllllllIllllIlIllllllI.toFile().getName());
        if (lllllllllllllllllIllllIlIlllllIl.equals("txt")) {
            return true;
        }
        return lllllllllllllllllIllllIlIlllllIl.equals("nbs");
    }

    @Override
    public WWidget getWidget(GuiTheme lllllllllllllllllIllllIllIIlIlll) {
        Notebot lllllllllllllllllIllllIllIIlIIlI;
        WTable lllllllllllllllllIllllIllIIlIllI = lllllllllllllllllIllllIllIIlIlll.table();
        WLabel lllllllllllllllllIllllIllIIlIlIl = lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.label(lllllllllllllllllIllllIllIIlIIlI.getStatus())).expandCellX().widget();
        WButton lllllllllllllllllIllllIllIIlIlII = lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.button(lllllllllllllllllIllllIllIIlIIlI.isPlaying ? "Pause" : "Resume")).right().widget();
        lllllllllllllllllIllllIllIIlIlII.action = () -> {
            Notebot lllllllllllllllllIllllIIIllIllll;
            lllllllllllllllllIllllIIIllIllll.Pause();
            lllllllllllllllllIllllIllIIlIlII.set(lllllllllllllllllIllllIIIllIllll.isPlaying ? "Pause" : "Resume");
            lllllllllllllllllIllllIllIIlIlIl.set(lllllllllllllllllIllllIIIllIllll.getStatus());
        };
        WButton lllllllllllllllllIllllIllIIlIIll = lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.button("Stop")).right().widget();
        lllllllllllllllllIllllIllIIlIIll.action = () -> {
            Notebot lllllllllllllllllIllllIIIlllIlll;
            lllllllllllllllllIllllIIIlllIlll.Stop();
            lllllllllllllllllIllllIllIIlIlIl.set(lllllllllllllllllIllllIIIlllIlll.getStatus());
        };
        lllllllllllllllllIllllIllIIlIllI.row();
        try {
            Files.list(MeteorClient.FOLDER.toPath().resolve("notebot")).forEach(lllllllllllllllllIllllIIlIIlIIII -> {
                Notebot lllllllllllllllllIllllIIlIIlIlII;
                if (lllllllllllllllllIllllIIlIIlIlII.isValidFile((Path)lllllllllllllllllIllllIIlIIlIIII)) {
                    lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.label(lllllllllllllllllIllllIIlIIlIlII.getFileLabel((Path)lllllllllllllllllIllllIIlIIlIIII))).expandCellX();
                    WButton lllllllllllllllllIllllIIlIIllIll = lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.button("Load")).right().widget();
                    lllllllllllllllllIllllIIlIIllIll.action = () -> {
                        Notebot lllllllllllllllllIllllIIIllllllI;
                        lllllllllllllllllIllllIIIllllllI.loadSong(lllllllllllllllllIllllIIlIIlIIII.toFile());
                        lllllllllllllllllIllllIllIIlIlIl.set(lllllllllllllllllIllllIIIllllllI.getStatus());
                    };
                    WButton lllllllllllllllllIllllIIlIIllIlI = lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.button("Preview")).right().widget();
                    lllllllllllllllllIllllIIlIIllIlI.action = () -> {
                        Notebot lllllllllllllllllIllllIIlIIIlIlI;
                        lllllllllllllllllIllllIIlIIIlIlI.previewSong(lllllllllllllllllIllllIIlIIlIIII.toFile());
                        lllllllllllllllllIllllIllIIlIlIl.set(lllllllllllllllllIllllIIlIIIlIlI.getStatus());
                    };
                    lllllllllllllllllIllllIllIIlIllI.row();
                }
            });
        }
        catch (IOException lllllllllllllllllIllllIllIIllIIl) {
            lllllllllllllllllIllllIllIIlIllI.add(lllllllllllllllllIllllIllIIlIlll.label("Missing \"notebot\" folder.")).expandCellX();
        }
        return lllllllllllllllllIllllIllIIlIllI;
    }

    public void previewSong(File lllllllllllllllllIllllIlIllIIlIl) {
        Notebot lllllllllllllllllIllllIlIllIIllI;
        if (!lllllllllllllllllIllllIlIllIIllI.isActive()) {
            lllllllllllllllllIllllIlIllIIllI.toggle();
        }
        if (lllllllllllllllllIllllIlIllIIllI.loadFileToMap(lllllllllllllllllIllllIlIllIIlIl)) {
            ChatUtils.moduleInfo(lllllllllllllllllIllllIlIllIIllI, "Song \"%s\" loaded.", lllllllllllllllllIllllIlIllIIllI.getFileLabel(lllllllllllllllllIllllIlIllIIlIl.toPath()));
            lllllllllllllllllIllllIlIllIIllI.stage = Stage.Preview;
            lllllllllllllllllIllllIlIllIIllI.Play();
        }
    }

    public void printStatus() {
        Notebot lllllllllllllllllIllllIllIIIIlll;
        ChatUtils.moduleInfo(lllllllllllllllllIllllIllIIIIlll, lllllllllllllllllIllllIllIIIIlll.getStatus(), new Object[0]);
    }

    private boolean loadFileToMap(File lllllllllllllllllIllllIlIlIllllI) {
        Notebot lllllllllllllllllIllllIlIlIlllII;
        if (!lllllllllllllllllIllllIlIlIllllI.exists() || !lllllllllllllllllIllllIlIlIllllI.isFile()) {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIlIlllII, "File not found", new Object[0]);
            return false;
        }
        String lllllllllllllllllIllllIlIlIlllIl = FilenameUtils.getExtension((String)lllllllllllllllllIllllIlIlIllllI.getName());
        if (lllllllllllllllllIllllIlIlIlllIl.equals("txt")) {
            return lllllllllllllllllIllllIlIlIlllII.loadTextFile(lllllllllllllllllIllllIlIlIllllI);
        }
        if (lllllllllllllllllIllllIlIlIlllIl.equals("nbs")) {
            return lllllllllllllllllIllllIlIlIlllII.loadNbsFile(lllllllllllllllllIllllIlIlIllllI);
        }
        return false;
    }

    private boolean loadNbsFile(File lllllllllllllllllIllllIlIIlIIlll) {
        Notebot lllllllllllllllllIllllIlIIlIlIII;
        Song lllllllllllllllllIllllIlIIlIlIlI = NBSDecoder.parse(lllllllllllllllllIllllIlIIlIIlll);
        if (lllllllllllllllllIllllIlIIlIlIlI == null) {
            ChatUtils.moduleError(lllllllllllllllllIllllIlIIlIlIII, "Couldn't parse the file. Only classic and opennbs v5 are supported", new Object[0]);
            return false;
        }
        ArrayList<Layer> lllllllllllllllllIllllIlIIlIlIIl = new ArrayList<Layer>(lllllllllllllllllIllllIlIIlIlIlI.getLayerHashMap().values());
        lllllllllllllllllIllllIlIIlIlIII.resetVariables();
        for (Layer lllllllllllllllllIllllIlIIlIllIl : lllllllllllllllllIllllIlIIlIlIIl) {
            for (int lllllllllllllllllIllllIlIIlIlllI : lllllllllllllllllIllllIlIIlIllIl.getHashMap().keySet()) {
                byte lllllllllllllllllIllllIlIIllIIII;
                Note lllllllllllllllllIllllIlIIllIIIl = lllllllllllllllllIllllIlIIlIllIl.getNote(lllllllllllllllllIllllIlIIlIlllI);
                lllllllllllllllllIllllIlIIlIlllI = (int)((float)lllllllllllllllllIllllIlIIlIlllI * lllllllllllllllllIllllIlIIlIlIlI.getDelay());
                if (lllllllllllllllllIllllIlIIllIIIl == null || (lllllllllllllllllIllllIlIIllIIII = lllllllllllllllllIllllIlIIllIIIl.getInstrument()) == 2 || lllllllllllllllllIllllIlIIllIIII == 3 || lllllllllllllllllIllllIlIIllIIII == 4) continue;
                int lllllllllllllllllIllllIlIIlIllll = Byte.toUnsignedInt(lllllllllllllllllIllllIlIIllIIIl.getKey());
                if ((lllllllllllllllllIllllIlIIlIllll -= 33) < 0 || lllllllllllllllllIllllIlIIlIllll > 24) {
                    ChatUtils.moduleWarning(lllllllllllllllllIllllIlIIlIlIII, "Note at tick %d out of range.", lllllllllllllllllIllllIlIIlIlllI);
                    continue;
                }
                if (lllllllllllllllllIllllIlIIlIlIII.moveNotes.get().booleanValue() && lllllllllllllllllIllllIlIIlIlIII.song.containsKey(lllllllllllllllllIllllIlIIlIlllI)) {
                    lllllllllllllllllIllllIlIIlIlIII.song.put(lllllllllllllllllIllllIlIIlIlllI + 1, lllllllllllllllllIllllIlIIlIllll);
                    lllllllllllllllllIllllIlIIlIlIII.lastKey = lllllllllllllllllIllllIlIIlIlllI + 1;
                    continue;
                }
                lllllllllllllllllIllllIlIIlIlIII.song.put(lllllllllllllllllIllllIlIIlIlllI, lllllllllllllllllIllllIlIIlIllll);
                lllllllllllllllllIllllIlIIlIlIII.lastKey = lllllllllllllllllIllllIlIIlIlllI;
            }
        }
        return true;
    }

    private boolean tuneBlock(BlockPos lllllllllllllllllIllllIIllIlllIl, int lllllllllllllllllIllllIIllIllIII) {
        Notebot lllllllllllllllllIllllIIllIllllI;
        if (lllllllllllllllllIllllIIllIllllI.mc.world == null || lllllllllllllllllIllllIIllIllllI.mc.player == null) {
            return false;
        }
        BlockState lllllllllllllllllIllllIIllIllIll = lllllllllllllllllIllllIIllIllllI.mc.world.getBlockState(lllllllllllllllllIllllIIllIlllIl);
        if (lllllllllllllllllIllllIIllIllIll.getBlock() != Blocks.NOTE_BLOCK) {
            ++lllllllllllllllllIllllIIllIllllI.offset;
            lllllllllllllllllIllllIIllIllllI.stage = Stage.SetUp;
            return true;
        }
        if (((Integer)lllllllllllllllllIllllIIllIllIll.get((Property)NoteBlock.NOTE)).equals(lllllllllllllllllIllllIIllIllIII)) {
            ++lllllllllllllllllIllllIIllIllllI.currentNote;
            lllllllllllllllllIllllIIllIllllI.stage = Stage.SetUp;
            return true;
        }
        lllllllllllllllllIllllIIllIllllI.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(lllllllllllllllllIllllIIllIllllI.mc.player.getPos(), lllllllllllllllllIllllIIllIllllI.rayTraceCheck(lllllllllllllllllIllllIIllIlllIl, true), lllllllllllllllllIllllIIllIlllIl, true)));
        lllllllllllllllllIllllIIllIllllI.mc.player.swingHand(Hand.MAIN_HAND);
        return true;
    }

    private void scanForNoteblocks() {
        Notebot lllllllllllllllllIllllIlIIIIllIl;
        if (lllllllllllllllllIllllIlIIIIllIl.mc.interactionManager == null || lllllllllllllllllIllllIlIIIIllIl.mc.world == null || lllllllllllllllllIllllIlIIIIllIl.mc.player == null) {
            return;
        }
        lllllllllllllllllIllllIlIIIIllIl.scannedNoteblocks.clear();
        int lllllllllllllllllIllllIlIIIIllll = (int)(-lllllllllllllllllIllllIlIIIIllIl.mc.interactionManager.getReachDistance()) - 1;
        int lllllllllllllllllIllllIlIIIIlllI = (int)lllllllllllllllllIllllIlIIIIllIl.mc.interactionManager.getReachDistance() + 1;
        for (int lllllllllllllllllIllllIlIIIlIIIl = lllllllllllllllllIllllIlIIIIllll; lllllllllllllllllIllllIlIIIlIIIl < lllllllllllllllllIllllIlIIIIlllI; ++lllllllllllllllllIllllIlIIIlIIIl) {
            for (int lllllllllllllllllIllllIlIIIlIIlI = lllllllllllllllllIllllIlIIIIllll; lllllllllllllllllIllllIlIIIlIIlI < lllllllllllllllllIllllIlIIIIlllI; ++lllllllllllllllllIllllIlIIIlIIlI) {
                for (int lllllllllllllllllIllllIlIIIlIIll = lllllllllllllllllIllllIlIIIIllll; lllllllllllllllllIllllIlIIIlIIll < lllllllllllllllllIllllIlIIIIlllI; ++lllllllllllllllllIllllIlIIIlIIll) {
                    BlockPos lllllllllllllllllIllllIlIIIlIlIl = lllllllllllllllllIllllIlIIIIllIl.mc.player.getBlockPos().add(lllllllllllllllllIllllIlIIIlIIIl, lllllllllllllllllIllllIlIIIlIIlI, lllllllllllllllllIllllIlIIIlIIll);
                    if (lllllllllllllllllIllllIlIIIIllIl.mc.world.getBlockState(lllllllllllllllllIllllIlIIIlIlIl).getBlock() != Blocks.NOTE_BLOCK) continue;
                    float lllllllllllllllllIllllIlIIIlIlII = lllllllllllllllllIllllIlIIIIllIl.mc.interactionManager.getReachDistance();
                    lllllllllllllllllIllllIlIIIlIlII *= lllllllllllllllllIllllIlIIIlIlII;
                    if (lllllllllllllllllIllllIlIIIlIlIl.getSquaredDistance((Position)lllllllllllllllllIllllIlIIIIllIl.mc.player.getPos(), false) > (double)lllllllllllllllllIllllIlIIIlIlII) continue;
                    lllllllllllllllllIllllIlIIIIllIl.scannedNoteblocks.add(lllllllllllllllllIllllIlIIIlIlIl);
                }
            }
        }
    }

    private void resetVariables() {
        Notebot lllllllllllllllllIllllIllIlIlIIl;
        lllllllllllllllllIllllIllIlIlIIl.currentNote = 0;
        lllllllllllllllllIllllIllIlIlIIl.offset = 0;
        lllllllllllllllllIllllIllIlIlIIl.lastKey = 0;
        lllllllllllllllllIllllIllIlIlIIl.isPlaying = false;
        lllllllllllllllllIllllIllIlIlIIl.stage = Stage.None;
        lllllllllllllllllIllllIllIlIlIIl.song.clear();
        lllllllllllllllllIllllIllIlIlIIl.blockPositions.clear();
        lllllllllllllllllIllllIllIlIlIIl.uniqueNotes.clear();
    }

    private static final class Stage
    extends Enum<Stage> {
        public static final /* synthetic */ /* enum */ Stage Tune;
        public static final /* synthetic */ /* enum */ Stage Preview;
        public static final /* synthetic */ /* enum */ Stage Playing;
        public static final /* synthetic */ /* enum */ Stage SetUp;
        private static final /* synthetic */ Stage[] $VALUES;
        public static final /* synthetic */ /* enum */ Stage None;

        static {
            None = new Stage();
            SetUp = new Stage();
            Tune = new Stage();
            Playing = new Stage();
            Preview = new Stage();
            $VALUES = Stage.$values();
        }

        public static Stage valueOf(String lIIlIllIIIIl) {
            return Enum.valueOf(Stage.class, lIIlIllIIIIl);
        }

        private Stage() {
            Stage lIIlIlIlllII;
        }

        private static /* synthetic */ Stage[] $values() {
            return new Stage[]{None, SetUp, Tune, Playing, Preview};
        }

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }
    }
}

