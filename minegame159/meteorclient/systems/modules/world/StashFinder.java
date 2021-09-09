/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.pathing.goals.GoalXZ
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.block.entity.BlockEntity
 *  net.minecraft.block.entity.BlockEntityType
 *  net.minecraft.block.entity.ChestBlockEntity
 *  net.minecraft.block.entity.DispenserBlockEntity
 *  net.minecraft.block.entity.AbstractFurnaceBlockEntity
 *  net.minecraft.block.entity.EnderChestBlockEntity
 *  net.minecraft.block.entity.HopperBlockEntity
 *  net.minecraft.block.entity.ShulkerBoxBlockEntity
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.toast.Toast
 *  net.minecraft.client.toast.Toast.Visibility
 *  net.minecraft.block.entity.BarrelBlockEntity
 *  net.minecraft.client.toast.ToastManager
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.systems.modules.world;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.ChunkDataEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StorageBlockListSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.toast.Toast;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class StashFinder
extends Module {
    private final /* synthetic */ Setting<Integer> minimumStorageCount;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<Integer> minimumDistance;
    public /* synthetic */ List<Chunk> chunks;
    private final /* synthetic */ Setting<List<BlockEntityType<?>>> storageBlocks;
    private static final /* synthetic */ Gson GSON;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> sendNotifications;

    private void saveCsv() {
        try {
            StashFinder llIlIIIIlIIII;
            File llIlIIIIlIIll = llIlIIIIlIIII.getCsvFile();
            llIlIIIIlIIll.getParentFile().mkdirs();
            FileWriter llIlIIIIlIIlI = new FileWriter(llIlIIIIlIIll);
            llIlIIIIlIIlI.write("X,Z,Chests,Shulkers,EnderChests,Furnaces,DispensersDroppers,Hopper\n");
            for (Chunk llIlIIIIlIlII : llIlIIIIlIIII.chunks) {
                llIlIIIIlIlII.write(llIlIIIIlIIlI);
            }
            ((Writer)llIlIIIIlIIlI).close();
        }
        catch (IOException llIlIIIIlIIIl) {
            llIlIIIIlIIIl.printStackTrace();
        }
    }

    public StashFinder() {
        super(Categories.World, "stash-finder", "Searches loaded chunks for storage blocks. Saves to <your minecraft folder>/meteor-client");
        StashFinder llIlIIlllIIlI;
        llIlIIlllIIlI.sgGeneral = llIlIIlllIIlI.settings.getDefaultGroup();
        llIlIIlllIIlI.storageBlocks = llIlIIlllIIlI.sgGeneral.add(new StorageBlockListSetting.Builder().name("storage-blocks").description("Select the storage blocks to search for.").defaultValue(Arrays.asList(StorageBlockListSetting.STORAGE_BLOCKS)).build());
        llIlIIlllIIlI.minimumStorageCount = llIlIIlllIIlI.sgGeneral.add(new IntSetting.Builder().name("minimum-storage-cont").description("The minimum amount of storage blocks in a chunk to record the chunk.").defaultValue(4).min(1).build());
        llIlIIlllIIlI.minimumDistance = llIlIIlllIIlI.sgGeneral.add(new IntSetting.Builder().name("minimum-distance").description("The minimum distance you must be from spawn to record a certain chunk.").defaultValue(0).min(0).sliderMax(10000).build());
        llIlIIlllIIlI.sendNotifications = llIlIIlllIIlI.sgGeneral.add(new BoolSetting.Builder().name("send-notifications").description("Sends Minecraft notifications when new stashes are found.").defaultValue(true).build());
        llIlIIlllIIlI.mode = llIlIIlllIIlI.sgGeneral.add(new EnumSetting.Builder().name("notification-mode").description("The mode to use for notifications.").defaultValue(Mode.Toast).build());
        llIlIIlllIIlI.chunks = new ArrayList<Chunk>();
    }

    private File getJsonFile() {
        return new File(new File(new File(MeteorClient.FOLDER, "stashes"), Utils.getWorldName()), "stashes.json");
    }

    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }

    @EventHandler
    private void onChunkData(ChunkDataEvent llIlIIlIllllI) {
        StashFinder llIlIIllIIlII;
        double llIlIIllIIIIl;
        double llIlIIllIIIlI = Math.abs(llIlIIlIllllI.chunk.getPos().x * 16);
        if (Math.sqrt(llIlIIllIIIlI * llIlIIllIIIlI + (llIlIIllIIIIl = (double)Math.abs(llIlIIlIllllI.chunk.getPos().z * 16)) * llIlIIllIIIIl) < (double)llIlIIllIIlII.minimumDistance.get().intValue()) {
            return;
        }
        Chunk llIlIIllIIIII = new Chunk(llIlIIlIllllI.chunk.getPos());
        for (BlockEntity llIlIIllIIlll : llIlIIlIllllI.chunk.getBlockEntities().values()) {
            if (!llIlIIllIIlII.storageBlocks.get().contains((Object)llIlIIllIIlll.getType())) continue;
            if (llIlIIllIIlll instanceof ChestBlockEntity) {
                ++llIlIIllIIIII.chests;
                continue;
            }
            if (llIlIIllIIlll instanceof BarrelBlockEntity) {
                ++llIlIIllIIIII.barrels;
                continue;
            }
            if (llIlIIllIIlll instanceof ShulkerBoxBlockEntity) {
                ++llIlIIllIIIII.shulkers;
                continue;
            }
            if (llIlIIllIIlll instanceof EnderChestBlockEntity) {
                ++llIlIIllIIIII.enderChests;
                continue;
            }
            if (llIlIIllIIlll instanceof AbstractFurnaceBlockEntity) {
                ++llIlIIllIIIII.furnaces;
                continue;
            }
            if (llIlIIllIIlll instanceof DispenserBlockEntity) {
                ++llIlIIllIIIII.dispensersDroppers;
                continue;
            }
            if (!(llIlIIllIIlll instanceof HopperBlockEntity)) continue;
            ++llIlIIllIIIII.hoppers;
        }
        if (llIlIIllIIIII.getTotal() >= llIlIIllIIlII.minimumStorageCount.get()) {
            Chunk llIlIIllIIllI = null;
            int llIlIIllIIlIl = llIlIIllIIlII.chunks.indexOf(llIlIIllIIIII);
            if (llIlIIllIIlIl < 0) {
                llIlIIllIIlII.chunks.add(llIlIIllIIIII);
            } else {
                llIlIIllIIllI = llIlIIllIIlII.chunks.set(llIlIIllIIlIl, llIlIIllIIIII);
            }
            llIlIIllIIlII.saveJson();
            llIlIIllIIlII.saveCsv();
            if (!(!llIlIIllIIlII.sendNotifications.get().booleanValue() || llIlIIllIIIII.equals(llIlIIllIIllI) && llIlIIllIIIII.countsEqual(llIlIIllIIllI))) {
                if (llIlIIllIIlII.mode.get() == Mode.Toast) {
                    llIlIIllIIlII.mc.getToastManager().add(new Toast(){
                        private /* synthetic */ long timer;
                        private /* synthetic */ long lastTime;

                        public Visibility draw(MatrixStack llllllllllllllllIllIIIlIIIlIIIIl, ToastManager llllllllllllllllIllIIIlIIIlIIIII, long llllllllllllllllIllIIIlIIIIllIll) {
                            1 llllllllllllllllIllIIIlIIIIllllI;
                            if (llllllllllllllllIllIIIlIIIIllllI.lastTime == -1L) {
                                llllllllllllllllIllIIIlIIIIllllI.lastTime = llllllllllllllllIllIIIlIIIIllIll;
                            } else {
                                llllllllllllllllIllIIIlIIIIllllI.timer += llllllllllllllllIllIIIlIIIIllIll - llllllllllllllllIllIIIlIIIIllllI.lastTime;
                            }
                            llllllllllllllllIllIIIlIIIlIIIII.getGame().getTextureManager().bindTexture(new Identifier("textures/gui/toasts.png"));
                            RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)255.0f);
                            llllllllllllllllIllIIIlIIIlIIIII.drawTexture(llllllllllllllllIllIIIlIIIlIIIIl, 0, 0, 0, 32, 160, 32);
                            llllllllllllllllIllIIIlIIIlIIIII.getGame().textRenderer.draw(llllllllllllllllIllIIIlIIIlIIIIl, "StashRecorder found stash.", 12.0f, 12.0f, -11534256);
                            return llllllllllllllllIllIIIlIIIIllllI.timer >= 32000L ? Visibility.HIDE : Visibility.SHOW;
                        }
                        {
                            1 llllllllllllllllIllIIIlIIIlIlIlI;
                            llllllllllllllllIllIIIlIIIlIlIlI.lastTime = -1L;
                        }
                    });
                } else {
                    ChatUtils.moduleInfo(llIlIIllIIlII, "(highlight)Found stash.", new Object[0]);
                }
            }
        }
    }

    private void load() {
        block9: {
            File llIlIIIlIIIIl;
            StashFinder llIlIIIlIIIll;
            boolean llIlIIIlIIIlI;
            block8: {
                llIlIIIlIIIlI = false;
                llIlIIIlIIIIl = llIlIIIlIIIll.getJsonFile();
                if (llIlIIIlIIIIl.exists()) {
                    try {
                        FileReader llIlIIIlIlIlI = new FileReader(llIlIIIlIIIIl);
                        llIlIIIlIIIll.chunks = (List)GSON.fromJson((Reader)llIlIIIlIlIlI, new TypeToken<List<Chunk>>(){
                            {
                                2 llllllllllllllllIlllIIlllIIlllll;
                            }
                        }.getType());
                        llIlIIIlIlIlI.close();
                        for (Chunk llIlIIIlIlIll : llIlIIIlIIIll.chunks) {
                            llIlIIIlIlIll.calculatePos();
                        }
                        llIlIIIlIIIlI = true;
                    }
                    catch (Exception llIlIIIlIlIIl) {
                        if (llIlIIIlIIIll.chunks != null) break block8;
                        llIlIIIlIIIll.chunks = new ArrayList<Chunk>();
                    }
                }
            }
            llIlIIIlIIIIl = llIlIIIlIIIll.getCsvFile();
            if (!llIlIIIlIIIlI && llIlIIIlIIIIl.exists()) {
                try {
                    String llIlIIIlIIlIl;
                    BufferedReader llIlIIIlIIllI = new BufferedReader(new FileReader(llIlIIIlIIIIl));
                    llIlIIIlIIllI.readLine();
                    while ((llIlIIIlIIlIl = llIlIIIlIIllI.readLine()) != null) {
                        String[] llIlIIIlIlIII = llIlIIIlIIlIl.split(" ");
                        Chunk llIlIIIlIIlll = new Chunk(new ChunkPos(Integer.parseInt(llIlIIIlIlIII[0]), Integer.parseInt(llIlIIIlIlIII[1])));
                        llIlIIIlIIlll.chests = Integer.parseInt(llIlIIIlIlIII[2]);
                        llIlIIIlIIlll.shulkers = Integer.parseInt(llIlIIIlIlIII[3]);
                        llIlIIIlIIlll.enderChests = Integer.parseInt(llIlIIIlIlIII[4]);
                        llIlIIIlIIlll.furnaces = Integer.parseInt(llIlIIIlIlIII[5]);
                        llIlIIIlIIlll.dispensersDroppers = Integer.parseInt(llIlIIIlIlIII[6]);
                        llIlIIIlIIlll.hoppers = Integer.parseInt(llIlIIIlIlIII[7]);
                        llIlIIIlIIIll.chunks.add(llIlIIIlIIlll);
                    }
                    llIlIIIlIIllI.close();
                }
                catch (Exception llIlIIIlIIlII) {
                    if (llIlIIIlIIIll.chunks != null) break block9;
                    llIlIIIlIIIll.chunks = new ArrayList<Chunk>();
                }
            }
        }
    }

    private File getCsvFile() {
        return new File(new File(new File(MeteorClient.FOLDER, "stashes"), Utils.getWorldName()), "stashes.csv");
    }

    private void saveJson() {
        try {
            StashFinder llIlIIIIIIIll;
            File llIlIIIIIIlll = llIlIIIIIIIll.getJsonFile();
            llIlIIIIIIlll.getParentFile().mkdirs();
            FileWriter llIlIIIIIIllI = new FileWriter(llIlIIIIIIlll);
            GSON.toJson(llIlIIIIIIIll.chunks, (Appendable)llIlIIIIIIllI);
            ((Writer)llIlIIIIIIllI).close();
        }
        catch (IOException llIlIIIIIIlIl) {
            llIlIIIIIIlIl.printStackTrace();
        }
    }

    @Override
    public void onActivate() {
        StashFinder llIlIIlllIIII;
        llIlIIlllIIII.load();
    }

    private void fillTable(GuiTheme llIlIIIllllII, WTable llIlIIIlllIll) {
        StashFinder llIlIIIllllIl;
        for (Chunk llIlIIIlllllI : llIlIIIllllIl.chunks) {
            llIlIIIlllIll.add(llIlIIIllllII.label(String.valueOf(new StringBuilder().append("Pos: ").append(llIlIIIlllllI.x).append(", ").append(llIlIIIlllllI.z))));
            llIlIIIlllIll.add(llIlIIIllllII.label(String.valueOf(new StringBuilder().append("Total: ").append(llIlIIIlllllI.getTotal()))));
            WButton llIlIIlIIIIIl = llIlIIIlllIll.add(llIlIIIllllII.button("Open")).widget();
            llIlIIlIIIIIl.action = () -> {
                StashFinder llIIllllIllII;
                llIIllllIllII.mc.openScreen((Screen)new ChunkScreen(llIlIIIllllII, llIlIIIlllllI));
            };
            WButton llIlIIlIIIIII = llIlIIIlllIll.add(llIlIIIllllII.button("Goto")).widget();
            llIlIIlIIIIII.action = () -> BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ(llIIlllllIIII.x, llIIlllllIIII.z));
            WMinus llIlIIIllllll = llIlIIIlllIll.add(llIlIIIllllII.minus()).widget();
            llIlIIIllllll.action = () -> {
                StashFinder llIIlllllIllI;
                if (llIIlllllIllI.chunks.remove(llIlIIIlllllI)) {
                    llIlIIIlllIll.clear();
                    llIIlllllIllI.fillTable(llIlIIIllllII, llIlIIIlllIll);
                    llIIlllllIllI.saveJson();
                    llIIlllllIllI.saveCsv();
                }
            };
            llIlIIIlllIll.row();
        }
    }

    @Override
    public WWidget getWidget(GuiTheme llIlIIlIlIIlI) {
        StashFinder llIlIIlIIlllI;
        llIlIIlIIlllI.chunks.sort(Comparator.comparingInt(llIIlllIlllll -> -llIIlllIlllll.getTotal()));
        WVerticalList llIlIIlIlIIIl = llIlIIlIlIIlI.verticalList();
        WButton llIlIIlIlIIII = llIlIIlIlIIIl.add(llIlIIlIlIIlI.button("Clear")).widget();
        WTable llIlIIlIIllll = new WTable();
        if (llIlIIlIIlllI.chunks.size() > 0) {
            llIlIIlIlIIIl.add(llIlIIlIIllll);
        }
        llIlIIlIlIIII.action = () -> {
            StashFinder llIIllllIIlII;
            llIIllllIIlII.chunks.clear();
            llIlIIlIIllll.clear();
        };
        llIlIIlIIlllI.fillTable(llIlIIlIlIIlI, llIlIIlIIllll);
        return llIlIIlIlIIIl;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Toast;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Chat;

        private Mode() {
            Mode lllllllllllllllllIIlllIIIllIlIII;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Chat, Toast};
        }

        public static Mode valueOf(String lllllllllllllllllIIlllIIIllIllII) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIIlllIIIllIllII);
        }

        static {
            Chat = new Mode();
            Toast = new Mode();
            $VALUES = Mode.$values();
        }
    }

    private static class ChunkScreen
    extends WindowScreen {
        public ChunkScreen(GuiTheme lllllllllllllllllIIllIlIlIlIIlll, Chunk lllllllllllllllllIIllIlIlIlIIIlI) {
            super(lllllllllllllllllIIllIlIlIlIIlll, String.valueOf(new StringBuilder().append("Chunk at ").append(lllllllllllllllllIIllIlIlIlIIIlI.x).append(", ").append(lllllllllllllllllIIllIlIlIlIIIlI.z)));
            ChunkScreen lllllllllllllllllIIllIlIlIlIIlII;
            WTable lllllllllllllllllIIllIlIlIlIIlIl = lllllllllllllllllIIllIlIlIlIIlII.add(lllllllllllllllllIIllIlIlIlIIlll.table()).expandX().widget();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Total:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.getTotal()).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.horizontalSeparator()).expandX();
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Chests:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.chests).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Barrels:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.barrels).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Shulkers:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.shulkers).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Ender Chests:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.enderChests).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Furnaces:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.furnaces).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Dispensers and droppers:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.dispensersDroppers).append(""))));
            lllllllllllllllllIIllIlIlIlIIlIl.row();
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label("Hoppers:"));
            lllllllllllllllllIIllIlIlIlIIlIl.add(lllllllllllllllllIIllIlIlIlIIlll.label(String.valueOf(new StringBuilder().append(lllllllllllllllllIIllIlIlIlIIIlI.hoppers).append(""))));
        }
    }

    public static class Chunk {
        public /* synthetic */ ChunkPos chunkPos;
        public transient /* synthetic */ int z;
        public /* synthetic */ int shulkers;
        public /* synthetic */ int furnaces;
        public /* synthetic */ int hoppers;
        public /* synthetic */ int enderChests;
        public /* synthetic */ int chests;
        public /* synthetic */ int dispensersDroppers;
        private static final /* synthetic */ StringBuilder sb;
        public transient /* synthetic */ int x;
        public /* synthetic */ int barrels;

        public void calculatePos() {
            Chunk lllllIlIllIlIIl;
            lllllIlIllIlIIl.x = lllllIlIllIlIIl.chunkPos.x * 16 + 8;
            lllllIlIllIlIIl.z = lllllIlIllIlIIl.chunkPos.z * 16 + 8;
        }

        public Chunk(ChunkPos lllllIlIllIllII) {
            Chunk lllllIlIllIllIl;
            lllllIlIllIllIl.chunkPos = lllllIlIllIllII;
            lllllIlIllIllIl.calculatePos();
        }

        public boolean countsEqual(Chunk lllllIlIlIlllII) {
            Chunk lllllIlIlIlllIl;
            if (lllllIlIlIlllII == null) {
                return false;
            }
            return lllllIlIlIlllIl.chests != lllllIlIlIlllII.chests || lllllIlIlIlllIl.barrels != lllllIlIlIlllII.barrels || lllllIlIlIlllIl.shulkers != lllllIlIlIlllII.shulkers || lllllIlIlIlllIl.enderChests != lllllIlIlIlllII.enderChests || lllllIlIlIlllIl.furnaces != lllllIlIlIlllII.furnaces || lllllIlIlIlllIl.dispensersDroppers != lllllIlIlIlllII.dispensersDroppers || lllllIlIlIlllIl.hoppers != lllllIlIlIlllII.hoppers;
        }

        static {
            sb = new StringBuilder();
        }

        public int getTotal() {
            Chunk lllllIlIllIIllI;
            return lllllIlIllIIllI.chests + lllllIlIllIIllI.barrels + lllllIlIllIIllI.shulkers + lllllIlIllIIllI.enderChests + lllllIlIllIIllI.furnaces + lllllIlIllIIllI.dispensersDroppers + lllllIlIllIIllI.hoppers;
        }

        public boolean equals(Object lllllIlIlIlIIlI) {
            Chunk lllllIlIlIlIllI;
            if (lllllIlIlIlIllI == lllllIlIlIlIIlI) {
                return true;
            }
            if (lllllIlIlIlIIlI == null || lllllIlIlIlIllI.getClass() != lllllIlIlIlIIlI.getClass()) {
                return false;
            }
            Chunk lllllIlIlIlIlII = (Chunk)lllllIlIlIlIIlI;
            return Objects.equals((Object)lllllIlIlIlIllI.chunkPos, (Object)lllllIlIlIlIlII.chunkPos);
        }

        public void write(Writer lllllIlIllIIIlI) throws IOException {
            Chunk lllllIlIllIIIll;
            sb.setLength(0);
            sb.append(lllllIlIllIIIll.x).append(',').append(lllllIlIllIIIll.z).append(',');
            sb.append(lllllIlIllIIIll.chests).append(',').append(lllllIlIllIIIll.barrels).append(',').append(lllllIlIllIIIll.shulkers).append(',').append(lllllIlIllIIIll.enderChests).append(',').append(lllllIlIllIIIll.furnaces).append(',').append(lllllIlIllIIIll.dispensersDroppers).append(',').append(lllllIlIllIIIll.hoppers).append('\n');
            lllllIlIllIIIlI.write(String.valueOf(sb));
        }

        public int hashCode() {
            Chunk lllllIlIlIIlllI;
            return Objects.hash(new Object[]{lllllIlIlIIlllI.chunkPos});
        }
    }
}

