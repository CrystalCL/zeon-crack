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
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_2428;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2885;
import net.minecraft.class_3417;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import org.apache.commons.io.FilenameUtils;

public class Notebot
extends Module {
    private int offset;
    private final List<class_2338> possibleBlockPos;
    private final HashMap<Integer, class_2338> blockPositions;
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
    private final List<class_2338> scannedNoteblocks;

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

    private boolean tuneBlock(class_2338 class_23382, int n) {
        if (this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return false;
        }
        class_2680 class_26802 = this.mc.field_1687.method_8320(class_23382);
        if (class_26802.method_26204() != class_2246.field_10179) {
            ++this.offset;
            this.stage = Stage.SetUp;
            return true;
        }
        if (((Integer)class_26802.method_11654((class_2769)class_2428.field_11324)).equals(n)) {
            ++this.currentNote;
            this.stage = Stage.SetUp;
            return true;
        }
        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), this.rayTraceCheck(class_23382, true), class_23382, true)));
        this.mc.field_1724.method_6104(class_1268.field_5808);
        return true;
    }

    public void Play() {
        if (this.mc.field_1724 == null) {
            return;
        }
        if (this.mc.field_1724.field_7503.field_7477 && this.stage != Stage.Preview) {
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
        if (this.mc.field_1761 == null) {
            return;
        }
        try {
            int n = this.song.get(this.currentNote);
            class_2338 class_23382 = this.blockPositions.get(n);
            this.mc.field_1761.method_2910(class_23382, class_2350.field_11033);
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
            class_2338 class_23382 = this.blockPositions.get(n);
            Rotations.rotate(Rotations.getYaw(class_23382), Rotations.getPitch(class_23382), 100, this::playRotate);
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

    private static int lambda$new$0(class_2338 class_23382, class_2338 class_23383) {
        double d = class_23382.method_10262(new class_2382(0, 0, 0));
        double d2 = class_23383.method_10262(new class_2382(0, 0, 0));
        return Double.compare(d, d2);
    }

    private void onTickPreview() {
        if (this.isPlaying && this.mc.field_1724 != null) {
            if (this.currentNote >= this.lastKey) {
                this.Stop();
            }
            if (this.song.containsKey(this.currentNote)) {
                this.mc.field_1724.method_5783(class_3417.field_15114, 2.0f, (float)Math.pow(2.0, (double)(this.song.get(this.currentNote) - 12) / 12.0));
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
        class_2338 class_23382 = this.blockPositions.get(this.uniqueNotes.get(this.currentNote));
        if (class_23382 == null) {
            return;
        }
        if (!this.tuneBlock(class_23382, this.uniqueNotes.get(this.currentNote))) {
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
        this.scannedNoteblocks = new ArrayList<class_2338>();
        this.currentNote = 0;
        this.lastKey = -1;
        this.offset = 0;
        this.ticks = 0;
        for (int i = -5; i < 5; ++i) {
            for (int j = -5; j < 5; ++j) {
                class_2338 class_23382;
                if (i == 0 && j == 0 || !((class_23382 = new class_2338(j, 0, i)).method_10268(0.0, 0.0, 0.0, true) < 17.99)) continue;
                this.possibleBlockPos.add(class_23382);
                if (-4 <= 0) continue;
                throw null;
            }
            if (2 != 0) continue;
            throw null;
        }
        this.possibleBlockPos.sort(Notebot::lambda$new$0);
    }

    private void onTickSetup() {
        class_2338 class_23382;
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
            class_2338 class_23383 = this.scannedNoteblocks.get(n);
            if (this.mc.field_1687.method_8320(class_23383).method_26204() != class_2246.field_10179) {
                ++this.offset;
            } else {
                this.blockPositions.put(this.uniqueNotes.get(this.currentNote), class_23383);
                this.stage = Stage.Tune;
            }
            return;
        }
        int n2 = InvUtils.findItemInHotbar(class_1802.field_8643);
        if (n2 == -1) {
            ChatUtils.moduleError(this, "Not enough noteblocks", new Object[0]);
            this.Disable();
            return;
        }
        n -= this.scannedNoteblocks.size();
        try {
            class_23382 = this.mc.field_1724.method_24515().method_10081((class_2382)this.possibleBlockPos.get(n));
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            ChatUtils.moduleError(this, "Not enough valid positions.", new Object[0]);
            this.Disable();
            return;
        }
        if (this.mc.field_1687.method_8320(class_23382.method_10074()).method_26204() == class_2246.field_10179) {
            ++this.offset;
            return;
        }
        if (!BlockUtils.place(class_23382, class_1268.field_5808, n2, true, 100, true)) {
            ++this.offset;
            return;
        }
        this.blockPositions.put(this.uniqueNotes.get(this.currentNote), class_23382);
        this.stage = Stage.Tune;
    }

    private void lambda$onRender$1(class_2338 class_23382) {
        double d = class_23382.method_10263();
        double d2 = class_23382.method_10264();
        double d3 = class_23382.method_10260();
        double d4 = class_23382.method_10263() + 1;
        double d5 = class_23382.method_10264() + 1;
        double d6 = class_23382.method_10260() + 1;
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d, d2, d3, d4, d5, d6, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    private void onTickTune() {
        if (this.ticks < this.tickDelay.get()) {
            return;
        }
        this.ticks = 0;
        class_2338 class_23382 = this.blockPositions.get(this.uniqueNotes.get(this.currentNote));
        if (class_23382 == null) {
            return;
        }
        Rotations.rotate(Rotations.getYaw(class_23382), Rotations.getPitch(class_23382), 100, this::tuneRotate);
    }

    private void scanForNoteblocks() {
        if (this.mc.field_1761 == null || this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return;
        }
        this.scannedNoteblocks.clear();
        int n = (int)(-this.mc.field_1761.method_2904()) - 1;
        int n2 = (int)this.mc.field_1761.method_2904() + 1;
        for (int i = n; i < n2; ++i) {
            for (int j = n; j < n2; ++j) {
                for (int k = n; k < n2; ++k) {
                    class_2338 class_23382 = this.mc.field_1724.method_24515().method_10069(i, j, k);
                    if (this.mc.field_1687.method_8320(class_23382).method_26204() != class_2246.field_10179) continue;
                    float f = this.mc.field_1761.method_2904();
                    f *= f;
                    if (class_23382.method_19770((class_2374)this.mc.field_1724.method_19538(), false) > (double)f) continue;
                    this.scannedNoteblocks.add(class_23382);
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

    private class_2350 rayTraceCheck(class_2338 class_23382, boolean bl) {
        class_243 class_2432 = new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + (double)this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321());
        for (class_2350 class_23502 : class_2350.values()) {
            class_3959 class_39592 = new class_3959(class_2432, new class_243((double)class_23382.method_10263() + 0.5 + (double)class_23502.method_10163().method_10263() * 0.5, (double)class_23382.method_10264() + 0.5 + (double)class_23502.method_10163().method_10264() * 0.5, (double)class_23382.method_10260() + 0.5 + (double)class_23502.method_10163().method_10260() * 0.5), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
            class_3965 class_39652 = this.mc.field_1687.method_17742(class_39592);
            if (class_39652 == null || class_39652.method_17783() != class_239.class_240.field_1332 || !class_39652.method_17777().equals((Object)class_23382)) continue;
            return class_23502;
        }
        if (bl) {
            if ((double)class_23382.method_10264() > class_2432.field_1351) {
                return class_2350.field_11033;
            }
            return class_2350.field_11036;
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

