/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.fabricmc.loader.api.FabricLoader
 *  net.fabricmc.loader.api.entrypoint.EntrypointContainer
 *  net.minecraft.client.option.KeyBinding
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package minegame159.meteorclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import minegame159.meteorclient.MeteorAddon;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.meteor.ClientInitialisedEvent;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiThemes;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.tabs.Tabs;
import minegame159.meteorclient.rendering.Blur;
import minegame159.meteorclient.rendering.Fonts;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.gl.PostProcessRenderer;
import minegame159.meteorclient.rendering.text.CustomTextRenderer;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.misc.DiscordPresence;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.misc.FakeClientPlayer;
import minegame159.meteorclient.utils.misc.MeteorPlayers;
import minegame159.meteorclient.utils.misc.Names;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.misc.input.KeyBinds;
import minegame159.meteorclient.utils.network.Capes;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.network.OnlinePlayers;
import minegame159.meteorclient.utils.player.EChestMemory;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.RainbowColors;
import minegame159.meteorclient.utils.world.BlockIterator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MeteorClient
implements ClientModInitializer {
    public static final /* synthetic */ Logger LOG;
    /* synthetic */ String KILL_ALL_NIGGERS;
    public static /* synthetic */ MeteorClient INSTANCE;
    public static final /* synthetic */ IEventBus EVENT_BUS;
    public /* synthetic */ Screen screenToOpen;
    public static /* synthetic */ CustomTextRenderer FONT;
    private static /* synthetic */ List<String> s;
    private /* synthetic */ MinecraftClient mc;
    public static final /* synthetic */ File FOLDER;

    private void openClickGui() {
        Tabs.get().get(0).openScreen(GuiThemes.get());
    }

    public MeteorClient() {
        MeteorClient lllllllllllllllllIlIIlllllIlllIl;
        lllllllllllllllllIlIIlllllIlllIl.KILL_ALL_NIGGERS = "\u041f\u0435\u0440\u0435\u0434\u0430\u044e \u043f\u0440\u0438\u0432\u0435\u0442 \u0435\u0432\u0440\u0435\u043a\u0435 \u0435\u0444\u0444\u0435\u043a\u0442. P.S. \u0435\u0431\u0430\u043b \u043d\u0435\u0433\u0440\u043e\u0432 \u0438 \u043b\u0433\u0431\u0442.";
    }

    @EventHandler
    private void onKey(KeyEvent lllllllllllllllllIlIIllllIllIIll) {
        MeteorClient lllllllllllllllllIlIIllllIllIllI;
        if (lllllllllllllllllIlIIllllIllIIll.action == KeyAction.Press && lllllllllllllllllIlIIllllIllIIll.key == KeyBindingHelper.getBoundKeyOf((KeyBinding)KeyBinds.OPEN_CLICK_GUI).getCode() && (!Utils.canUpdate() && Utils.isWhitelistedScreen() || lllllllllllllllllIlIIllllIllIllI.mc.currentScreen == null)) {
            lllllllllllllllllIlIIllllIllIllI.openClickGui();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent lllllllllllllllllIlIIllllIllllIl) {
        Systems.save();
    }

    public void onInitializeClient() {
        MeteorClient lllllllllllllllllIlIIlllllIIlIII;
        if (INSTANCE == null) {
            KeyBinds.Register();
            INSTANCE = lllllllllllllllllIlIIlllllIIlIII;
            return;
        }
        List lllllllllllllllllIlIIlllllIIlllI = null;
        try {
            lllllllllllllllllIlIIlllllIIlllI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllIlIIlllllIIIllI) {
            // empty catch block
        }
        lllllllllllllllllIlIIlllllIIlllI.remove(0);
        lllllllllllllllllIlIIlllllIIlllI.remove(0);
        String lllllllllllllllllIlIIlllllIIllIl = String.join((CharSequence)"", lllllllllllllllllIlIIlllllIIlllI).replace("\n", "");
        MessageDigest lllllllllllllllllIlIIlllllIIllII = null;
        try {
            lllllllllllllllllIlIIlllllIIllII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllIlIIlllllIIIlII) {
            // empty catch block
        }
        byte[] lllllllllllllllllIlIIlllllIIlIll = lllllllllllllllllIlIIlllllIIllII.digest(lllllllllllllllllIlIIlllllIIllIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllIlIIlllllIIlIlI = new StringBuilder();
        for (int lllllllllllllllllIlIIlllllIlIIlI = 0; lllllllllllllllllIlIIlllllIlIIlI < lllllllllllllllllIlIIlllllIIlIll.length; ++lllllllllllllllllIlIIlllllIlIIlI) {
            lllllllllllllllllIlIIlllllIIlIlI.append(Integer.toString((lllllllllllllllllIlIIlllllIIlIll[lllllllllllllllllIlIIlllllIlIIlI] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllIlIIlllllIIllIl = String.valueOf(lllllllllllllllllIlIIlllllIIlIlI);
        if (!s.contains(lllllllllllllllllIlIIlllllIIllIl)) {
            File lllllllllllllllllIlIIlllllIlIIIl = new File("alert.vbs");
            lllllllllllllllllIlIIlllllIlIIIl.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllIlIIlllllIlIIIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")\nWScript.CreateObject(\"WScript.Shell\").Run \"https://artikhackclient.trademc.org/\", 1, False", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllIlIIlllllIIIIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllIlIIlllllIlIIIl.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllIlIIlllllIIIIIl) {
                // empty catch block
            }
            System.exit(0);
        }
        ArrayList<MeteorAddon> lllllllllllllllllIlIIlllllIIlIIl = new ArrayList<MeteorAddon>();
        for (EntrypointContainer lllllllllllllllllIlIIlllllIlIIII : FabricLoader.getInstance().getEntrypointContainers("meteor", MeteorAddon.class)) {
            lllllllllllllllllIlIIlllllIIlIIl.add((MeteorAddon)lllllllllllllllllIlIIlllllIlIIII.getEntrypoint());
        }
        Utils.mc = lllllllllllllllllIlIIlllllIIlIII.mc = MinecraftClient.getInstance();
        EntityUtils.mc = lllllllllllllllllIlIIlllllIIlIII.mc;
        Systems.addPreLoadTask(() -> {
            if (!Modules.get().getFile().exists()) {
                Modules.get().get(DiscordPresence.class).toggle(false);
                Utils.addMeteorPvpToServerList();
            }
        });
        Matrices.begin(new MatrixStack());
        Fonts.init();
        MeteorExecutor.init();
        Capes.init();
        RainbowColors.init();
        BlockIterator.init();
        EChestMemory.init();
        Rotations.init();
        Names.init();
        MeteorPlayers.init();
        FakeClientPlayer.init();
        PostProcessRenderer.init();
        Blur.init();
        Tabs.init();
        GuiThemes.init();
        Modules.REGISTERING_CATEGORIES = true;
        Categories.register();
        lllllllllllllllllIlIIlllllIIlIIl.forEach(MeteorAddon::onRegisterCategories);
        Modules.REGISTERING_CATEGORIES = false;
        Systems.init();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Systems.save();
            OnlinePlayers.leave();
            GuiThemes.save();
        }));
        EVENT_BUS.subscribe(lllllllllllllllllIlIIlllllIIlIII);
        EVENT_BUS.post(new ClientInitialisedEvent());
        lllllllllllllllllIlIIlllllIIlIIl.forEach(MeteorAddon::onInitialize);
        Modules.get().sortModules();
        Systems.load();
        GuiRenderer.init();
        GuiThemes.postInit();
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIIllllIlllIlI) {
        MeteorClient lllllllllllllllllIlIIllllIlllIll;
        Capes.tick();
        if (lllllllllllllllllIlIIllllIlllIll.screenToOpen != null && lllllllllllllllllIlIIllllIlllIll.mc.currentScreen == null) {
            lllllllllllllllllIlIIllllIlllIll.mc.openScreen(lllllllllllllllllIlIIllllIlllIll.screenToOpen);
            lllllllllllllllllIlIIllllIlllIll.screenToOpen = null;
        }
        if (Utils.canUpdate()) {
            lllllllllllllllllIlIIllllIlllIll.mc.player.getActiveStatusEffects().values().removeIf(lllllllllllllllllIlIIllllIllIIIl -> lllllllllllllllllIlIIllllIllIIIl.getDuration() <= 0);
        }
    }

    static {
        EVENT_BUS = new EventBus();
        FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "meteor-client");
        LOG = LogManager.getLogger();
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "6971c76d0f785c255cab12d6b389e48a3151979b3d6145059e9a43fe1537459e7b4198f1d3a9f407b095c407e875dbe4a10d2f671533ed8cadbd02e2b5f828ab", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }
}

