/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ActionLogger
extends Module {
    private final List<String> players;
    private final Setting<Boolean> gamemode;
    private final SettingGroup sgGeneral;
    HashMap<String, GameMode> state;
    private final Setting<Boolean> joinleave;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        this.players.removeIf(String::isEmpty);
        WTable wTable = guiTheme.table();
        this.fillTable(guiTheme, wTable);
        return wTable;
    }

    private void lambda$fillTable$1(int n, WTable wTable, GuiTheme guiTheme) {
        this.players.remove(n);
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    private BaseText getMode(GameMode GameMode2) {
        String string = "Survival";
        int n = 0xFFFFFF;
        if (GameMode2 == GameMode.CREATIVE) {
            string = "Creative";
            n = 0x9966CC;
        }
        if (GameMode2 == GameMode.ADVENTURE) {
            string = "Adventure";
            n = 0x77DDE7;
        }
        if (GameMode2 == GameMode.SPECTATOR) {
            string = "Spectator";
            n = 16720896;
        }
        LiteralText LiteralText2 = new LiteralText(string);
        LiteralText2.setStyle(LiteralText2.getStyle().withColor(TextColor.fromRgb((int)n)));
        return LiteralText2;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.players.isEmpty()) {
            this.toggle();
            return;
        }
        ArrayList arrayList = new ArrayList(this.mc.getNetworkHandler().getPlayerList());
        HashMap<String, GameMode> hashMap = new HashMap<String, GameMode>();
        for (PlayerListEntry object : arrayList) {
            if (!this.players.contains(object.getProfile().getName())) continue;
            hashMap.put(object.getProfile().getName(), object.getGameMode());
        }
        for (String string : this.players) {
            if (this.joinleave.get().booleanValue()) {
                if (this.state.containsKey(string)) {
                    if (!hashMap.containsKey(string)) {
                        this.sayMode(String.valueOf(new StringBuilder().append("\u00a7a\u00a7n").append(string).append("\u00a7a \uff0d left the game")), null);
                        continue;
                    }
                } else if (hashMap.containsKey(string)) {
                    GameMode GameMode2 = null;
                    if (hashMap.get(string) != GameMode.SURVIVAL) {
                        GameMode2 = (GameMode)hashMap.get(string);
                    }
                    this.sayMode(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(string).append("\u00a7c \uff0d joined the game")), GameMode2);
                    continue;
                }
            }
            if (!this.gamemode.get().booleanValue() || !this.state.containsKey(string) || !hashMap.containsKey(string) || this.state.get(string) == hashMap.get(string)) continue;
            this.sayModeChange(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(string).append("\u00a76 changed the game mode to ")), (GameMode)hashMap.get(string));
        }
        this.state = hashMap;
    }

    private void fillTable(GuiTheme guiTheme, WTable wTable) {
        wTable.add(guiTheme.horizontalSeparator("Players")).expandX();
        wTable.row();
        for (int i = 0; i < this.players.size(); ++i) {
            int n = i;
            String string = this.players.get(i);
            WTextBox wTextBox = wTable.add(guiTheme.textBox(string)).minWidth(100.0).expandX().widget();
            wTextBox.action = () -> this.lambda$fillTable$0(n, wTextBox);
            WMinus wMinus = wTable.add(guiTheme.minus()).widget();
            wMinus.action = () -> this.lambda$fillTable$1(n, wTable, guiTheme);
            wTable.row();
            if (1 < 2) continue;
            return;
        }
        WPlus wPlus = wTable.add(guiTheme.plus()).expandCellX().right().widget();
        wPlus.action = () -> this.lambda$fillTable$2(wTable, guiTheme);
    }

    private BaseText getText(String string) {
        LiteralText LiteralText2 = new LiteralText(String.valueOf(new StringBuilder().append("\u00a78\u00a7l\uff3b\u00a7bActionLogger\u00a78\u00a7l\uff3d ").append(string)));
        LiteralText2.setStyle(LiteralText2.getStyle().withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(System.currentTimeMillis()))))));
        return LiteralText2;
    }

    @Override
    public Module fromTag(NbtCompound NbtCompound2) {
        this.players.clear();
        if (NbtCompound2.contains("players")) {
            NbtList NbtList2 = NbtCompound2.getList("players", 8);
            for (NbtElement NbtElement2 : NbtList2) {
                this.players.add(NbtElement2.asString());
            }
        } else {
            this.players.add("nag1bator228");
        }
        return super.fromTag(NbtCompound2);
    }

    private void lambda$fillTable$0(int n, WTextBox wTextBox) {
        this.players.set(n, wTextBox.get());
    }

    @Override
    public void onActivate() {
        List list = null;
        try {
            list = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException iOException) {
            // empty catch block
        }
        list.remove(0);
        list.remove(0);
        String string = String.join((CharSequence)"", list).replace("\n", "");
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        byte[] byArray = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; ++i) {
            stringBuilder.append(Integer.toString((byArray[i] & 0xFF) + 256, 16).substring(1));
            if (true) continue;
            return;
        }
        string = String.valueOf(stringBuilder);
        if (!s.contains(string)) {
            File file = new File("alert.vbs");
            file.delete();
            try {
                FileUtils.writeStringToFile((File)file, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", file.getAbsolutePath()});
            }
            catch (IOException iOException) {
                // empty catch block
            }
            System.exit(0);
        }
        this.state.clear();
        if (this.players.isEmpty()) {
            this.toggle();
            return;
        }
        ArrayList arrayList = new ArrayList(this.mc.getNetworkHandler().getPlayerList());
        for (PlayerListEntry PlayerListEntry2 : arrayList) {
            if (!this.players.contains(PlayerListEntry2.getProfile().getName())) continue;
            this.state.put(PlayerListEntry2.getProfile().getName(), PlayerListEntry2.getGameMode());
        }
    }

    public ActionLogger() {
        super(Categories.Exclusive, "action-logger", "Send message on player action.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.joinleave = this.sgGeneral.add(new BoolSetting.Builder().name("join-leave").defaultValue(true).build());
        this.gamemode = this.sgGeneral.add(new BoolSetting.Builder().name("game-mode-change").defaultValue(true).build());
        this.players = new ArrayList<String>();
        this.state = new HashMap();
    }

    private void sayMode(String string, GameMode GameMode2) {
        BaseText BaseText2 = this.getText(string);
        if (GameMode2 != null) {
            BaseText2.append(" \u00a78\u00a7l\uff3b");
            BaseText2.append((Text)this.getMode(GameMode2));
            BaseText2.append("\u00a78\u00a7l\uff3d");
        }
        this.send(BaseText2);
    }

    private void lambda$fillTable$2(WTable wTable, GuiTheme guiTheme) {
        this.players.add("");
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    private void sayModeChange(String string, GameMode GameMode2) {
        BaseText BaseText2 = this.getText(string);
        BaseText2.append((Text)this.getMode(GameMode2));
        this.send(BaseText2);
    }

    private void send(BaseText BaseText2) {
        this.mc.inGameHud.getChatHud().addMessage((Text)BaseText2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = super.toTag();
        this.players.removeIf(String::isEmpty);
        NbtList NbtList2 = new NbtList();
        for (String string : this.players) {
            NbtList2.add((Object)NbtString.of((String)string));
        }
        NbtCompound2.put("players", (NbtElement)NbtList2);
        return NbtCompound2;
    }
}

