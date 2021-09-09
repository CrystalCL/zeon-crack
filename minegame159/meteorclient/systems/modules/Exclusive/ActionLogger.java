/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.GameMode
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.text.BaseText
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 *  net.minecraft.text.TextColor
 *  net.minecraft.client.network.PlayerListEntry
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.world.GameMode;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.client.network.PlayerListEntry;
import org.apache.commons.io.FileUtils;

public class ActionLogger
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<Boolean> gamemode;
    private final /* synthetic */ Setting<Boolean> joinleave;
    private final /* synthetic */ List<String> players;
    /* synthetic */ HashMap<String, GameMode> state;

    private void fillTable(GuiTheme lllllllllllllllllllllIIlllIIlIIl, WTable lllllllllllllllllllllIIlllIIIlII) {
        ActionLogger lllllllllllllllllllllIIlllIIlIlI;
        lllllllllllllllllllllIIlllIIIlII.add(lllllllllllllllllllllIIlllIIlIIl.horizontalSeparator("Players")).expandX();
        lllllllllllllllllllllIIlllIIIlII.row();
        for (int lllllllllllllllllllllIIlllIIlIll = 0; lllllllllllllllllllllIIlllIIlIll < lllllllllllllllllllllIIlllIIlIlI.players.size(); ++lllllllllllllllllllllIIlllIIlIll) {
            int lllllllllllllllllllllIIlllIIllll = lllllllllllllllllllllIIlllIIlIll;
            String lllllllllllllllllllllIIlllIIlllI = lllllllllllllllllllllIIlllIIlIlI.players.get(lllllllllllllllllllllIIlllIIlIll);
            WTextBox lllllllllllllllllllllIIlllIIllIl = lllllllllllllllllllllIIlllIIIlII.add(lllllllllllllllllllllIIlllIIlIIl.textBox(lllllllllllllllllllllIIlllIIlllI)).minWidth(100.0).expandX().widget();
            lllllllllllllllllllllIIlllIIllIl.action = () -> {
                ActionLogger lllllllllllllllllllllIIllIIIIlIl;
                lllllllllllllllllllllIIllIIIIlIl.players.set(lllllllllllllllllllllIIlllIIllll, lllllllllllllllllllllIIlllIIllIl.get());
            };
            WMinus lllllllllllllllllllllIIlllIIllII = lllllllllllllllllllllIIlllIIIlII.add(lllllllllllllllllllllIIlllIIlIIl.minus()).widget();
            lllllllllllllllllllllIIlllIIllII.action = () -> {
                ActionLogger lllllllllllllllllllllIIllIIIllII;
                lllllllllllllllllllllIIllIIIllII.players.remove(lllllllllllllllllllllIIlllIIllll);
                lllllllllllllllllllllIIlllIIIlII.clear();
                lllllllllllllllllllllIIllIIIllII.fillTable(lllllllllllllllllllllIIlllIIlIIl, lllllllllllllllllllllIIlllIIIlII);
            };
            lllllllllllllllllllllIIlllIIIlII.row();
        }
        WPlus lllllllllllllllllllllIIlllIIIlll = lllllllllllllllllllllIIlllIIIlII.add(lllllllllllllllllllllIIlllIIlIIl.plus()).expandCellX().right().widget();
        lllllllllllllllllllllIIlllIIIlll.action = () -> {
            ActionLogger lllllllllllllllllllllIIllIIllIlI;
            lllllllllllllllllllllIIllIIllIlI.players.add("");
            lllllllllllllllllllllIIlllIIIlII.clear();
            lllllllllllllllllllllIIllIIllIlI.fillTable(lllllllllllllllllllllIIlllIIlIIl, lllllllllllllllllllllIIlllIIIlII);
        };
    }

    @Override
    public void onActivate() {
        ActionLogger lllllllllllllllllllllIlIIIlIlllI;
        List lllllllllllllllllllllIlIIIllIlII = null;
        try {
            lllllllllllllllllllllIlIIIllIlII = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lllllllllllllllllllllIlIIIlIllII) {
            // empty catch block
        }
        lllllllllllllllllllllIlIIIllIlII.remove(0);
        lllllllllllllllllllllIlIIIllIlII.remove(0);
        String lllllllllllllllllllllIlIIIllIIll = String.join((CharSequence)"", lllllllllllllllllllllIlIIIllIlII).replace("\n", "");
        MessageDigest lllllllllllllllllllllIlIIIllIIlI = null;
        try {
            lllllllllllllllllllllIlIIIllIIlI = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lllllllllllllllllllllIlIIIlIlIlI) {
            // empty catch block
        }
        byte[] lllllllllllllllllllllIlIIIllIIIl = lllllllllllllllllllllIlIIIllIIlI.digest(lllllllllllllllllllllIlIIIllIIll.getBytes(StandardCharsets.UTF_8));
        StringBuilder lllllllllllllllllllllIlIIIllIIII = new StringBuilder();
        for (int lllllllllllllllllllllIlIIIlllIII = 0; lllllllllllllllllllllIlIIIlllIII < lllllllllllllllllllllIlIIIllIIIl.length; ++lllllllllllllllllllllIlIIIlllIII) {
            lllllllllllllllllllllIlIIIllIIII.append(Integer.toString((lllllllllllllllllllllIlIIIllIIIl[lllllllllllllllllllllIlIIIlllIII] & 0xFF) + 256, 16).substring(1));
        }
        lllllllllllllllllllllIlIIIllIIll = String.valueOf(lllllllllllllllllllllIlIIIllIIII);
        if (!s.contains(lllllllllllllllllllllIlIIIllIIll)) {
            File lllllllllllllllllllllIlIIIllIlll = new File("alert.vbs");
            lllllllllllllllllllllIlIIIllIlll.delete();
            try {
                FileUtils.writeStringToFile((File)lllllllllllllllllllllIlIIIllIlll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lllllllllllllllllllllIlIIIlIIlll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lllllllllllllllllllllIlIIIllIlll.getAbsolutePath()});
            }
            catch (IOException lllllllllllllllllllllIlIIIlIIlll) {
                // empty catch block
            }
            System.exit(0);
        }
        lllllllllllllllllllllIlIIIlIlllI.state.clear();
        if (lllllllllllllllllllllIlIIIlIlllI.players.isEmpty()) {
            lllllllllllllllllllllIlIIIlIlllI.toggle();
            return;
        }
        ArrayList lllllllllllllllllllllIlIIIlIllll = new ArrayList(lllllllllllllllllllllIlIIIlIlllI.mc.getNetworkHandler().getPlayerList());
        for (PlayerListEntry lllllllllllllllllllllIlIIIllIllI : lllllllllllllllllllllIlIIIlIllll) {
            if (!lllllllllllllllllllllIlIIIlIlllI.players.contains(lllllllllllllllllllllIlIIIllIllI.getProfile().getName())) continue;
            lllllllllllllllllllllIlIIIlIlllI.state.put(lllllllllllllllllllllIlIIIllIllI.getProfile().getName(), lllllllllllllllllllllIlIIIllIllI.getGameMode());
        }
    }

    public ActionLogger() {
        super(Categories.Exclusive, "action-logger", "Send message on player action.");
        ActionLogger lllllllllllllllllllllIlIIlIIIIll;
        lllllllllllllllllllllIlIIlIIIIll.sgGeneral = lllllllllllllllllllllIlIIlIIIIll.settings.getDefaultGroup();
        lllllllllllllllllllllIlIIlIIIIll.joinleave = lllllllllllllllllllllIlIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("join-leave").defaultValue(true).build());
        lllllllllllllllllllllIlIIlIIIIll.gamemode = lllllllllllllllllllllIlIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("game-mode-change").defaultValue(true).build());
        lllllllllllllllllllllIlIIlIIIIll.players = new ArrayList<String>();
        lllllllllllllllllllllIlIIlIIIIll.state = new HashMap();
    }

    @Override
    public Module fromTag(NbtCompound lllllllllllllllllllllIIllIlIIllI) {
        ActionLogger lllllllllllllllllllllIIllIlIlIIl;
        lllllllllllllllllllllIIllIlIlIIl.players.clear();
        if (lllllllllllllllllllllIIllIlIIllI.contains("players")) {
            NbtList lllllllllllllllllllllIIllIlIlIlI = lllllllllllllllllllllIIllIlIIllI.getList("players", 8);
            for (NbtElement lllllllllllllllllllllIIllIlIlIll : lllllllllllllllllllllIIllIlIlIlI) {
                lllllllllllllllllllllIIllIlIlIIl.players.add(lllllllllllllllllllllIIllIlIlIll.asString());
            }
        } else {
            lllllllllllllllllllllIIllIlIlIIl.players.add("nag1bator228");
        }
        return super.fromTag(lllllllllllllllllllllIIllIlIIllI);
    }

    @Override
    public NbtCompound toTag() {
        ActionLogger lllllllllllllllllllllIIllIlllIII;
        NbtCompound lllllllllllllllllllllIIllIllIlll = super.toTag();
        lllllllllllllllllllllIIllIlllIII.players.removeIf(String::isEmpty);
        NbtList lllllllllllllllllllllIIllIllIllI = new NbtList();
        for (String lllllllllllllllllllllIIllIlllIIl : lllllllllllllllllllllIIllIlllIII.players) {
            lllllllllllllllllllllIIllIllIllI.add((Object)NbtString.of((String)lllllllllllllllllllllIIllIlllIIl));
        }
        lllllllllllllllllllllIIllIllIlll.put("players", (NbtElement)lllllllllllllllllllllIIllIllIllI);
        return lllllllllllllllllllllIIllIllIlll;
    }

    private void sayModeChange(String lllllllllllllllllllllIlIIIIIIIlI, GameMode lllllllllllllllllllllIlIIIIIIlIl) {
        ActionLogger lllllllllllllllllllllIlIIIIIIIll;
        BaseText lllllllllllllllllllllIlIIIIIIlII = lllllllllllllllllllllIlIIIIIIIll.getText(lllllllllllllllllllllIlIIIIIIIlI);
        lllllllllllllllllllllIlIIIIIIlII.append((Text)lllllllllllllllllllllIlIIIIIIIll.getMode(lllllllllllllllllllllIlIIIIIIlIl));
        lllllllllllllllllllllIlIIIIIIIll.send(lllllllllllllllllllllIlIIIIIIlII);
    }

    private BaseText getMode(GameMode lllllllllllllllllllllIlIIIIlllII) {
        String lllllllllllllllllllllIlIIIIlllll = "Survival";
        int lllllllllllllllllllllIlIIIIllllI = 0xFFFFFF;
        if (lllllllllllllllllllllIlIIIIlllII == GameMode.CREATIVE) {
            lllllllllllllllllllllIlIIIIlllll = "Creative";
            lllllllllllllllllllllIlIIIIllllI = 0x9966CC;
        }
        if (lllllllllllllllllllllIlIIIIlllII == GameMode.ADVENTURE) {
            lllllllllllllllllllllIlIIIIlllll = "Adventure";
            lllllllllllllllllllllIlIIIIllllI = 0x77DDE7;
        }
        if (lllllllllllllllllllllIlIIIIlllII == GameMode.SPECTATOR) {
            lllllllllllllllllllllIlIIIIlllll = "Spectator";
            lllllllllllllllllllllIlIIIIllllI = 16720896;
        }
        LiteralText lllllllllllllllllllllIlIIIIlllIl = new LiteralText(lllllllllllllllllllllIlIIIIlllll);
        lllllllllllllllllllllIlIIIIlllIl.setStyle(lllllllllllllllllllllIlIIIIlllIl.getStyle().withColor(TextColor.fromRgb((int)lllllllllllllllllllllIlIIIIllllI)));
        return lllllllllllllllllllllIlIIIIlllIl;
    }

    private BaseText getText(String lllllllllllllllllllllIlIIIIlIIll) {
        LiteralText lllllllllllllllllllllIlIIIIlIlII = new LiteralText(String.valueOf(new StringBuilder().append("\u00a78\u00a7l\uff3b\u00a7bActionLogger\u00a78\u00a7l\uff3d ").append(lllllllllllllllllllllIlIIIIlIIll)));
        lllllllllllllllllllllIlIIIIlIlII.setStyle(lllllllllllllllllllllIlIIIIlIlII.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(System.currentTimeMillis()))))));
        return lllllllllllllllllllllIlIIIIlIlII;
    }

    private void send(BaseText lllllllllllllllllllllIlIIIIIllII) {
        ActionLogger lllllllllllllllllllllIlIIIIIllIl;
        lllllllllllllllllllllIlIIIIIllIl.mc.inGameHud.getChatHud().addMessage((Text)lllllllllllllllllllllIlIIIIIllII);
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void sayMode(String lllllllllllllllllllllIIllllllIlI, GameMode lllllllllllllllllllllIIlllllIlIl) {
        ActionLogger lllllllllllllllllllllIIllllllIll;
        BaseText lllllllllllllllllllllIIllllllIII = lllllllllllllllllllllIIllllllIll.getText(lllllllllllllllllllllIIllllllIlI);
        if (lllllllllllllllllllllIIlllllIlIl != null) {
            lllllllllllllllllllllIIllllllIII.append(" \u00a78\u00a7l\uff3b");
            lllllllllllllllllllllIIllllllIII.append((Text)lllllllllllllllllllllIIllllllIll.getMode(lllllllllllllllllllllIIlllllIlIl));
            lllllllllllllllllllllIIllllllIII.append("\u00a78\u00a7l\uff3d");
        }
        lllllllllllllllllllllIIllllllIll.send(lllllllllllllllllllllIIllllllIII);
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllllIIllllIlIIl) {
        ActionLogger lllllllllllllllllllllIIllllIIllI;
        if (lllllllllllllllllllllIIllllIIllI.players.isEmpty()) {
            lllllllllllllllllllllIIllllIIllI.toggle();
            return;
        }
        ArrayList lllllllllllllllllllllIIllllIlIII = new ArrayList(lllllllllllllllllllllIIllllIIllI.mc.getNetworkHandler().getPlayerList());
        HashMap<String, GameMode> lllllllllllllllllllllIIllllIIlll = new HashMap<String, GameMode>();
        for (PlayerListEntry lllllllllllllllllllllIIllllIllIl : lllllllllllllllllllllIIllllIlIII) {
            if (!lllllllllllllllllllllIIllllIIllI.players.contains(lllllllllllllllllllllIIllllIllIl.getProfile().getName())) continue;
            lllllllllllllllllllllIIllllIIlll.put(lllllllllllllllllllllIIllllIllIl.getProfile().getName(), lllllllllllllllllllllIIllllIllIl.getGameMode());
        }
        for (String lllllllllllllllllllllIIllllIlIll : lllllllllllllllllllllIIllllIIllI.players) {
            if (lllllllllllllllllllllIIllllIIllI.joinleave.get().booleanValue()) {
                if (lllllllllllllllllllllIIllllIIllI.state.containsKey(lllllllllllllllllllllIIllllIlIll)) {
                    if (!lllllllllllllllllllllIIllllIIlll.containsKey(lllllllllllllllllllllIIllllIlIll)) {
                        lllllllllllllllllllllIIllllIIllI.sayMode(String.valueOf(new StringBuilder().append("\u00a7a\u00a7n").append(lllllllllllllllllllllIIllllIlIll).append("\u00a7a \uff0d left the game")), null);
                        continue;
                    }
                } else if (lllllllllllllllllllllIIllllIIlll.containsKey(lllllllllllllllllllllIIllllIlIll)) {
                    GameMode lllllllllllllllllllllIIllllIllII = null;
                    if (lllllllllllllllllllllIIllllIIlll.get(lllllllllllllllllllllIIllllIlIll) != GameMode.SURVIVAL) {
                        lllllllllllllllllllllIIllllIllII = (GameMode)lllllllllllllllllllllIIllllIIlll.get(lllllllllllllllllllllIIllllIlIll);
                    }
                    lllllllllllllllllllllIIllllIIllI.sayMode(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(lllllllllllllllllllllIIllllIlIll).append("\u00a7c \uff0d joined the game")), lllllllllllllllllllllIIllllIllII);
                    continue;
                }
            }
            if (!lllllllllllllllllllllIIllllIIllI.gamemode.get().booleanValue() || !lllllllllllllllllllllIIllllIIllI.state.containsKey(lllllllllllllllllllllIIllllIlIll) || !lllllllllllllllllllllIIllllIIlll.containsKey(lllllllllllllllllllllIIllllIlIll) || lllllllllllllllllllllIIllllIIllI.state.get(lllllllllllllllllllllIIllllIlIll) == lllllllllllllllllllllIIllllIIlll.get(lllllllllllllllllllllIIllllIlIll)) continue;
            lllllllllllllllllllllIIllllIIllI.sayModeChange(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(lllllllllllllllllllllIIllllIlIll).append("\u00a76 changed the game mode to ")), (GameMode)lllllllllllllllllllllIIllllIIlll.get(lllllllllllllllllllllIIllllIlIll));
        }
        lllllllllllllllllllllIIllllIIllI.state = lllllllllllllllllllllIIllllIIlll;
    }

    @Override
    public WWidget getWidget(GuiTheme lllllllllllllllllllllIIlllIllIIl) {
        ActionLogger lllllllllllllllllllllIIlllIlllIl;
        lllllllllllllllllllllIIlllIlllIl.players.removeIf(String::isEmpty);
        WTable lllllllllllllllllllllIIlllIllIll = lllllllllllllllllllllIIlllIllIIl.table();
        lllllllllllllllllllllIIlllIlllIl.fillTable(lllllllllllllllllllllIIlllIllIIl, lllllllllllllllllllllIIlllIllIll);
        return lllllllllllllllllllllIIlllIllIll;
    }
}

