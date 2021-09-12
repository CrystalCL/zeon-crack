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
import net.minecraft.class_1934;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2519;
import net.minecraft.class_2520;
import net.minecraft.class_2554;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2585;
import net.minecraft.class_5251;
import net.minecraft.class_640;
import org.apache.commons.io.FileUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ActionLogger
extends Module {
    private final List<String> players;
    private final Setting<Boolean> gamemode;
    private final SettingGroup sgGeneral;
    HashMap<String, class_1934> state;
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

    private class_2554 getMode(class_1934 class_19342) {
        String string = "Survival";
        int n = 0xFFFFFF;
        if (class_19342 == class_1934.field_9220) {
            string = "Creative";
            n = 0x9966CC;
        }
        if (class_19342 == class_1934.field_9216) {
            string = "Adventure";
            n = 0x77DDE7;
        }
        if (class_19342 == class_1934.field_9219) {
            string = "Spectator";
            n = 16720896;
        }
        class_2585 class_25852 = new class_2585(string);
        class_25852.method_10862(class_25852.method_10866().method_27703(class_5251.method_27717((int)n)));
        return class_25852;
    }

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.players.isEmpty()) {
            this.toggle();
            return;
        }
        ArrayList arrayList = new ArrayList(this.mc.method_1562().method_2880());
        HashMap<String, class_1934> hashMap = new HashMap<String, class_1934>();
        for (class_640 object : arrayList) {
            if (!this.players.contains(object.method_2966().getName())) continue;
            hashMap.put(object.method_2966().getName(), object.method_2958());
        }
        for (String string : this.players) {
            if (this.joinleave.get().booleanValue()) {
                if (this.state.containsKey(string)) {
                    if (!hashMap.containsKey(string)) {
                        this.sayMode(String.valueOf(new StringBuilder().append("\u00a7a\u00a7n").append(string).append("\u00a7a \uff0d left the game")), null);
                        continue;
                    }
                } else if (hashMap.containsKey(string)) {
                    class_1934 class_19342 = null;
                    if (hashMap.get(string) != class_1934.field_9215) {
                        class_19342 = (class_1934)hashMap.get(string);
                    }
                    this.sayMode(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(string).append("\u00a7c \uff0d joined the game")), class_19342);
                    continue;
                }
            }
            if (!this.gamemode.get().booleanValue() || !this.state.containsKey(string) || !hashMap.containsKey(string) || this.state.get(string) == hashMap.get(string)) continue;
            this.sayModeChange(String.valueOf(new StringBuilder().append("\u00a7c\u00a7n").append(string).append("\u00a76 changed the game mode to ")), (class_1934)hashMap.get(string));
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

    private class_2554 getText(String string) {
        class_2585 class_25852 = new class_2585(String.valueOf(new StringBuilder().append("\u00a78\u00a7l\uff3b\u00a7bActionLogger\u00a78\u00a7l\uff3d ").append(string)));
        class_25852.method_10862(class_25852.method_10866().method_10949(new class_2568(class_2568.class_5247.field_24342, (Object)new class_2585(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(System.currentTimeMillis()))))));
        return class_25852;
    }

    @Override
    public Module fromTag(class_2487 class_24872) {
        this.players.clear();
        if (class_24872.method_10545("players")) {
            class_2499 class_24992 = class_24872.method_10554("players", 8);
            for (class_2520 class_25202 : class_24992) {
                this.players.add(class_25202.method_10714());
            }
        } else {
            this.players.add("nag1bator228");
        }
        return super.fromTag(class_24872);
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
        ArrayList arrayList = new ArrayList(this.mc.method_1562().method_2880());
        for (class_640 class_6402 : arrayList) {
            if (!this.players.contains(class_6402.method_2966().getName())) continue;
            this.state.put(class_6402.method_2966().getName(), class_6402.method_2958());
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

    private void sayMode(String string, class_1934 class_19342) {
        class_2554 class_25542 = this.getText(string);
        if (class_19342 != null) {
            class_25542.method_27693(" \u00a78\u00a7l\uff3b");
            class_25542.method_10852((class_2561)this.getMode(class_19342));
            class_25542.method_27693("\u00a78\u00a7l\uff3d");
        }
        this.send(class_25542);
    }

    private void lambda$fillTable$2(WTable wTable, GuiTheme guiTheme) {
        this.players.add("");
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    private void sayModeChange(String string, class_1934 class_19342) {
        class_2554 class_25542 = this.getText(string);
        class_25542.method_10852((class_2561)this.getMode(class_19342));
        this.send(class_25542);
    }

    private void send(class_2554 class_25542) {
        this.mc.field_1705.method_1743().method_1812((class_2561)class_25542);
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = super.toTag();
        this.players.removeIf(String::isEmpty);
        class_2499 class_24992 = new class_2499();
        for (String string : this.players) {
            class_24992.add((Object)class_2519.method_23256((String)string));
        }
        class_24872.method_10566("players", (class_2520)class_24992);
        return class_24872;
    }
}

