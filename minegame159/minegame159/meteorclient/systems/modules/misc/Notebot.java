/*
 * Decompiled with CFR 0.151.
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
    private int offset;
    private final List<BlockPos> possibleBlockPos;
    private final HashMap<Integer, BlockPos> blockPositions;
    private final List<Integer> uniqueNotes;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;
    private int currentNote;
    private final Setting<Integer> tickDelay;
    private final SettingGroup sgRender;
    private Stage stage;
    private final SettingGroup sgGeneral;
    private int ticks;
    private final Setting<ShapeMode> shapeMode;
    private int lastKey;
    private final Setting<Boolean> moveNotes;
    private final HashMap<Integer, Integer> song;
    private boolean isPlaying;
    private final Setting<SettingColor> lineColor;
    private final List<BlockPos> scannedNoteblocks;

    public void loadSong(File file) {
        if (!this.isActive()) {
            this.toggle();
        }
        if (!this.loadFileToMap(file)) {
            return;
        }
        if (!this.setupBlocks()) {
            return;
        }
        ChatUtils.moduleInfo(this, "Loading song \"%s\".", this.getFileLabel(file.toPath()));
    }

    private boolean loadTextFile(File file) {
        List<String> list;
        try {
            list = Files.readAllLines(file.toPath());
        }
        catch (IOException iOException) {
            ChatUtils.moduleError(this, "Error while reading \"%s\"", file.getName());
            return false;
        }
        this.resetVariables();
        for (int i = 0; i < list.size(); ++i) {
            int n;
            int n2;
            block8: {
                String[] stringArray = list.get(i).split(":");
                if (stringArray.length < 2) {
                    ChatUtils.moduleWarning(this, "Malformed line %d", i);
                    continue;
                }
                try {
                    n2 = Integer.parseInt(stringArray[0]);
                    n = Integer.parseInt(stringArray[1]);
                    if (stringArray.length <= 2) break block8;
                    int n3 = Integer.parseInt(stringArray[2]);
                    if (n3 == 1 || n3 == 2 || n3 == 3) continue;
                    if (n3 == 11) {
                    }
                    break block8;
                }
                catch (NumberFormatException numberFormatException) {
                    ChatUtils.moduleWarning(this, "Invalid character at line %d", i);
                }
                continue;
            }
            if (this.moveNotes.get().booleanValue() && this.song.containsKey(n2)) {
                this.song.put(n2 + 1, n);
                this.lastKey = n2 + 1;
                continue;
            }
            this.song.put(n2, n);
            this.lastKey = n2;
        }
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        ++this.ticks;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$misc$Notebot$Stage[this.stage.ordinal()]) {
            case 1: {
                this.onTickPreview();
                break;
            }
            case 2: {
                this.onTickSetup();
                break;
            }
            case 3: {
                this.onTickTune();
                break;
            }
            case 4: {
                this.onTickPlay();
                break;
            }
        }
    }

    private boolean tuneBlock(BlockPos BlockPos2, int n) {
        if (this.mc.world == null || this.mc.player == null) {
            return false;
        }
        BlockState BlockState2 = this.mc.world.getBlockState(BlockPos2);
        if (BlockState2.getBlock() != Blocks.NOTE_BLOCK) {
            ++this.offset;
            this.stage = Stage.SetUp;
            return true;
        }
        if (((Integer)BlockState2.get((Property)NoteBlock.NOTE)).equals(n)) {
            ++this.currentNote;
            this.stage = Stage.SetUp;
            return true;
        }
        this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(this.mc.player.getPos(), this.rayTraceCheck(BlockPos2, true), BlockPos2, true)));
        this.mc.player.swingHand(Hand.MAIN_HAND);
        return true;
    }

    public void Play() {
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.player.abilities.creativeMode && this.stage != Stage.Preview) {
            ChatUtils.moduleError(this, "You need to be in survival mode.", new Object[0]);
        } else if (this.stage == Stage.Preview || this.stage == Stage.Playing) {
            this.isPlaying = true;
            ChatUtils.moduleInfo(this, "Playing.", new Object[0]);
        } else {
            ChatUtils.moduleError(this, "No song loaded.", new Object[0]);
        }
    }

    private boolean loadFileToMap(File file) {
        if (!file.exists() || !file.isFile()) {
            ChatUtils.moduleError(this, "File not found", new Object[0]);
            return false;
        }
        String string = FilenameUtils.getExtension((String)file.getName());
        if (string.equals("txt")) {
            return this.loadTextFile(file);
        }
        if (string.equals("nbs")) {
            return this.loadNbsFile(file);
        }
        return false;
    }

    public void Stop() {
        ChatUtils.moduleInfo(this, "Stopping.", new Object[0]);
        if (this.stage == Stage.SetUp || this.stage == Stage.Tune) {
            this.resetVariables();
        } else {
            this.isPlaying = false;
            this.currentNote = 0;
        }
    }

    private void playRotate() {
        if (this.mc.interactionManager == null) {
            return;
        }
        try {
            int n = this.song.get(this.currentNote);
            BlockPos BlockPos2 = this.blockPositions.get(n);
            this.mc.interactionManager.attackBlock(BlockPos2, Direction.DOWN);
            ++this.currentNote;
        }
        catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
    }

    private boolean setupBlocks() {
        this.song.values().forEach(this::lambda$setupBlocks$7);
        this.scanForNoteblocks();
        if (this.uniqueNotes.size() > this.possibleBlockPos.size() + this.scannedNoteblocks.size()) {
            ChatUtils.moduleError(this, "Too many notes. %d is the maximum.", this.possibleBlockPos.size());
            return false;
        }
        this.currentNote = 0;
        this.offset = 0;
        this.stage = Stage.SetUp;
        return true;
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WTable wTable = guiTheme.table();
        WLabel wLabel = wTable.add(guiTheme.label(this.getStatus())).expandCellX().widget();
        WButton wButton = wTable.add(guiTheme.button(this.isPlaying ? "Pause" : "Resume")).right().widget();
        wButton.action = () -> this.lambda$getWidget$2(wButton, wLabel);
        WButton wButton2 = wTable.add(guiTheme.button("Stop")).right().widget();
        wButton2.action = () -> this.lambda$getWidget$3(wLabel);
        wTable.row();
        try {
            Files.list(MeteorClient.FOLDER.toPath().resolve("notebot")).forEach(arg_0 -> this.lambda$getWidget$6(wTable, guiTheme, wLabel, arg_0));
        }
        catch (IOException iOException) {
            wTable.add(guiTheme.label("Missing \"notebot\" folder.")).expandCellX();
        }
        return wTable;
    }

    @Override
    public void onActivate() {
        this.ticks = 0;
        this.resetVariables();
    }

    private void onTickPlay() {
        if (!this.isPlaying) {
            return;
        }
        if (this.song == null) {
            return;
        }
        if (this.currentNote >= this.lastKey) {
            this.Stop();
            return;
        }
        if (this.song.containsKey(this.currentNote)) {
            int n = this.song.get(this.currentNote);
            BlockPos BlockPos2 = this.blockPositions.get(n);
            Rotations.rotate(Rotations.getYaw(BlockPos2), Rotations.getPitch(BlockPos2), 100, this::playRotate);
        } else {
            ++this.currentNote;
        }
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!this.render.get().booleanValue()) {
            return;
        }
        if (this.stage != Stage.SetUp && this.stage != Stage.Tune && !this.isPlaying) {
            return;
        }
        this.blockPositions.values().forEach(this::lambda$onRender$1);
    }

    private void resetVariables() {
        this.currentNote = 0;
        this.offset = 0;
        this.lastKey = 0;
        this.isPlaying = false;
        this.stage = Stage.None;
        this.song.clear();
        this.blockPositions.clear();
        this.uniqueNotes.clear();
    }

    private boolean isValidFile(Path path) {
        String string = FilenameUtils.getExtension((String)path.toFile().getName());
        if (string.equals("txt")) {
            return true;
        }
        return string.equals("nbs");
    }

    private static int lambda$new$0(BlockPos BlockPos2, BlockPos BlockPos3) {
        double d = BlockPos2.getSquaredDistance(new Vec3i(0, 0, 0));
        double d2 = BlockPos3.getSquaredDistance(new Vec3i(0, 0, 0));
        return Double.compare(d, d2);
    }

    private void onTickPreview() {
        if (this.isPlaying && this.mc.player != null) {
            if (this.currentNote >= this.lastKey) {
                this.Stop();
            }
            if (this.song.containsKey(this.currentNote)) {
                this.mc.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HARP, 2.0f, (float)Math.pow(2.0, (double)(this.song.get(this.currentNote) - 12) / 12.0));
            }
            ++this.currentNote;
        }
    }

    private void lambda$getWidget$3(WLabel wLabel) {
        this.Stop();
        wLabel.set(this.getStatus());
    }

    public void printStatus() {
        ChatUtils.moduleInfo(this, this.getStatus(), new Object[0]);
    }

    private void tuneRotate() {
        BlockPos BlockPos2 = this.blockPositions.get(this.uniqueNotes.get(this.currentNote));
        if (BlockPos2 == null) {
            return;
        }
        if (!this.tuneBlock(BlockPos2, this.uniqueNotes.get(this.currentNote))) {
            this.Disable();
        }
    }

    private void lambda$setupBlocks$7(Integer n) {
        if (!this.uniqueNotes.contains(n)) {
            this.uniqueNotes.add(n);
        }
    }

    public Notebot() {
        super(Categories.Misc, "notebot", "Plays noteblock nicely");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render", false);
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("tick-delay").description("The delay when loading a song.").defaultValue(2).min(0).sliderMax(20).build());
        this.moveNotes = this.sgGeneral.add(new BoolSetting.Builder().name("move-notes").description("Move notes by one tick, if multiple notes supposed to play in one tick.").defaultValue(false).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the outline around the noteblocks.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.possibleBlockPos = new ArrayList(Collections.emptyList());
        this.stage = Stage.None;
        this.isPlaying = false;
        this.song = new HashMap();
        this.uniqueNotes = new ArrayList(Collections.emptyList());
        this.blockPositions = new HashMap();
        this.scannedNoteblocks = new ArrayList<BlockPos>();
        this.currentNote = 0;
        this.lastKey = -1;
        this.offset = 0;
        this.ticks = 0;
        for (int i = -5; i < 5; ++i) {
            for (int j = -5; j < 5; ++j) {
                BlockPos BlockPos2;
                if (i == 0 && j == 0 || !((BlockPos2 = new BlockPos(j, 0, i)).getSquaredDistance(0.0, 0.0, 0.0, true) < 17.99)) continue;
                this.possibleBlockPos.add(BlockPos2);
                if (-4 <= 0) continue;
                throw null;
            }
            if (2 != 0) continue;
            throw null;
        }
        this.possibleBlockPos.sort(Notebot::lambda$new$0);
    }

    private void onTickSetup() {
        BlockPos BlockPos2;
        if (this.ticks < this.tickDelay.get()) {
            return;
        }
        this.ticks = 0;
        if (this.currentNote >= this.uniqueNotes.size()) {
            this.stage = Stage.Playing;
            ChatUtils.moduleInfo(this, "Loading done.", new Object[0]);
            this.Play();
            return;
        }
        int n = this.currentNote + this.offset;
        if (n < this.scannedNoteblocks.size()) {
            BlockPos BlockPos3 = this.scannedNoteblocks.get(n);
            if (this.mc.world.getBlockState(BlockPos3).getBlock() != Blocks.NOTE_BLOCK) {
                ++this.offset;
            } else {
                this.blockPositions.put(this.uniqueNotes.get(this.currentNote), BlockPos3);
                this.stage = Stage.Tune;
            }
            return;
        }
        int n2 = InvUtils.findItemInHotbar(Items.NOTE_BLOCK);
        if (n2 == -1) {
            ChatUtils.moduleError(this, "Not enough noteblocks", new Object[0]);
            this.Disable();
            return;
        }
        n -= this.scannedNoteblocks.size();
        try {
            BlockPos2 = this.mc.player.getBlockPos().add((Vec3i)this.possibleBlockPos.get(n));
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            ChatUtils.moduleError(this, "Not enough valid positions.", new Object[0]);
            this.Disable();
            return;
        }
        if (this.mc.world.getBlockState(BlockPos2.down()).getBlock() == Blocks.NOTE_BLOCK) {
            ++this.offset;
            return;
        }
        if (!BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n2, true, 100, true)) {
            ++this.offset;
            return;
        }
        this.blockPositions.put(this.uniqueNotes.get(this.currentNote), BlockPos2);
        this.stage = Stage.Tune;
    }

    private void lambda$onRender$1(BlockPos BlockPos2) {
        double d = BlockPos2.getX();
        double d2 = BlockPos2.getY();
        double d3 = BlockPos2.getZ();
        double d4 = BlockPos2.getX() + 1;
        double d5 = BlockPos2.getY() + 1;
        double d6 = BlockPos2.getZ() + 1;
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d, d2, d3, d4, d5, d6, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    private void onTickTune() {
        if (this.ticks < this.tickDelay.get()) {
            return;
        }
        this.ticks = 0;
        BlockPos BlockPos2 = this.blockPositions.get(this.uniqueNotes.get(this.currentNote));
        if (BlockPos2 == null) {
            return;
        }
        Rotations.rotate(Rotations.getYaw(BlockPos2), Rotations.getPitch(BlockPos2), 100, this::tuneRotate);
    }

    private void scanForNoteblocks() {
        if (this.mc.interactionManager == null || this.mc.world == null || this.mc.player == null) {
            return;
        }
        this.scannedNoteblocks.clear();
        int n = (int)(-this.mc.interactionManager.getReachDistance()) - 1;
        int n2 = (int)this.mc.interactionManager.getReachDistance() + 1;
        for (int i = n; i < n2; ++i) {
            for (int j = n; j < n2; ++j) {
                for (int k = n; k < n2; ++k) {
                    BlockPos BlockPos2 = this.mc.player.getBlockPos().add(i, j, k);
                    if (this.mc.world.getBlockState(BlockPos2).getBlock() != Blocks.NOTE_BLOCK) continue;
                    float f = this.mc.interactionManager.getReachDistance();
                    f *= f;
                    if (BlockPos2.getSquaredDistance((Position)this.mc.player.getPos(), false) > (double)f) continue;
                    this.scannedNoteblocks.add(BlockPos2);
                }
                if (3 != 4) continue;
                return;
            }
            if (-4 < 0) continue;
            return;
        }
    }

    public void previewSong(File file) {
        if (!this.isActive()) {
            this.toggle();
        }
        if (this.loadFileToMap(file)) {
            ChatUtils.moduleInfo(this, "Song \"%s\" loaded.", this.getFileLabel(file.toPath()));
            this.stage = Stage.Preview;
            this.Play();
        }
    }

    private Direction rayTraceCheck(BlockPos BlockPos2, boolean bl) {
        Vec3d Vec3d2 = new Vec3d(this.mc.player.getX(), this.mc.player.getY() + (double)this.mc.player.getEyeHeight(this.mc.player.getPose()), this.mc.player.getZ());
        for (Direction Direction2 : Direction.values()) {
            RaycastContext RaycastContext2 = new RaycastContext(Vec3d2, new Vec3d((double)BlockPos2.getX() + 0.5 + (double)Direction2.getVector().getX() * 0.5, (double)BlockPos2.getY() + 0.5 + (double)Direction2.getVector().getY() * 0.5, (double)BlockPos2.getZ() + 0.5 + (double)Direction2.getVector().getZ() * 0.5), RaycastContext.class_3960.COLLIDER, RaycastContext.class_242.NONE, (Entity)this.mc.player);
            BlockHitResult BlockHitResult2 = this.mc.world.raycast(RaycastContext2);
            if (BlockHitResult2 == null || BlockHitResult2.getType() != HitResult.class_240.BLOCK || !BlockHitResult2.getBlockPos().equals((Object)BlockPos2)) continue;
            return Direction2;
        }
        if (bl) {
            if ((double)BlockPos2.getY() > Vec3d2.y) {
                return Direction.DOWN;
            }
            return Direction.UP;
        }
        return null;
    }

    private void lambda$getWidget$6(WTable wTable, GuiTheme guiTheme, WLabel wLabel, Path path) {
        if (this.isValidFile(path)) {
            wTable.add(guiTheme.label(this.getFileLabel(path))).expandCellX();
            WButton wButton = wTable.add(guiTheme.button("Load")).right().widget();
            wButton.action = () -> this.lambda$getWidget$4(path, wLabel);
            WButton wButton2 = wTable.add(guiTheme.button("Preview")).right().widget();
            wButton2.action = () -> this.lambda$getWidget$5(path, wLabel);
            wTable.row();
        }
    }

    private boolean loadNbsFile(File file) {
        Song song = NBSDecoder.parse(file);
        if (song == null) {
            ChatUtils.moduleError(this, "Couldn't parse the file. Only classic and opennbs v5 are supported", new Object[0]);
            return false;
        }
        ArrayList<Layer> arrayList = new ArrayList<Layer>(song.getLayerHashMap().values());
        this.resetVariables();
        for (Layer layer : arrayList) {
            for (int n : layer.getHashMap().keySet()) {
                byte by;
                Note note = layer.getNote(n);
                n = (int)((float)n * song.getDelay());
                if (note == null || (by = note.getInstrument()) == 2 || by == 3 || by == 4) continue;
                int n2 = Byte.toUnsignedInt(note.getKey());
                if ((n2 -= 33) < 0 || n2 > 24) {
                    ChatUtils.moduleWarning(this, "Note at tick %d out of range.", n);
                    continue;
                }
                if (this.moveNotes.get().booleanValue() && this.song.containsKey(n)) {
                    this.song.put(n + 1, n2);
                    this.lastKey = n + 1;
                    continue;
                }
                this.song.put(n, n2);
                this.lastKey = n;
            }
        }
        return true;
    }

    private String getStatus() {
        if (!this.isActive()) {
            return "Module disabled.";
        }
        if (this.song.isEmpty()) {
            return "No song loaded.";
        }
        if (this.isPlaying) {
            return String.format("Playing song. %d/%d", this.currentNote, this.lastKey);
        }
        if (this.stage == Stage.Playing || this.stage == Stage.Preview) {
            return "Ready to play.";
        }
        if (this.stage == Stage.SetUp || this.stage == Stage.Tune) {
            return "Setting up the noteblocks.";
        }
        return String.format("Stage: %s.", this.stage.toString());
    }

    private String getFileLabel(Path path) {
        return path.getFileName().toString().replace(".txt", "").replace(".nbs", "");
    }

    public void Pause() {
        if (!this.isActive()) {
            this.toggle();
        }
        if (this.isPlaying) {
            ChatUtils.moduleInfo(this, "Pausing.", new Object[0]);
            this.isPlaying = false;
        } else {
            ChatUtils.moduleInfo(this, "Resuming.", new Object[0]);
            this.isPlaying = true;
        }
    }

    public void Disable() {
        this.resetVariables();
        ChatUtils.moduleInfo(this, "Stopping.", new Object[0]);
        if (!this.isActive()) {
            this.toggle();
        }
    }

    private void lambda$getWidget$4(Path path, WLabel wLabel) {
        this.loadSong(path.toFile());
        wLabel.set(this.getStatus());
    }

    private void lambda$getWidget$5(Path path, WLabel wLabel) {
        this.previewSong(path.toFile());
        wLabel.set(this.getStatus());
    }

    private void lambda$getWidget$2(WButton wButton, WLabel wLabel) {
        this.Pause();
        wButton.set(this.isPlaying ? "Pause" : "Resume");
        wLabel.set(this.getStatus());
    }

    private static final class Stage
    extends Enum<Stage> {
        public static final /* enum */ Stage SetUp;
        public static final /* enum */ Stage Tune;
        public static final /* enum */ Stage Preview;
        public static final /* enum */ Stage None;
        public static final /* enum */ Stage Playing;
        private static final Stage[] $VALUES;

        public static Stage valueOf(String string) {
            return Enum.valueOf(Stage.class, string);
        }

        private static Stage[] $values() {
            return new Stage[]{None, SetUp, Tune, Playing, Preview};
        }

        static {
            None = new Stage();
            SetUp = new Stage();
            Tune = new Stage();
            Playing = new Stage();
            Preview = new Stage();
            $VALUES = Stage.$values();
        }

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }
    }
}

