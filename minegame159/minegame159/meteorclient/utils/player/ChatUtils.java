/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.mixin.ChatHudAccessor;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_124;
import net.minecraft.class_243;
import net.minecraft.class_2554;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2585;
import net.minecraft.class_310;

public class ChatUtils {
    private static final class_310 mc = class_310.method_1551();

    public static void info(class_2561 class_25612) {
        ChatUtils.sendMsg(0, null, PrefixType.None, class_25612);
    }

    public static void moduleWarning(Module module, class_2561 class_25612) {
        ChatUtils.moduleMessage(class_124.field_1054, module, class_25612);
    }

    public static void info(int n, String string, Object ... objectArray) {
        ChatUtils.message(n, class_124.field_1080, string, objectArray);
    }

    private static void prefixMessage(class_124 class_1242, String string, String string2, Object ... objectArray) {
        ChatUtils.sendMsg(0, string, PrefixType.Other, ChatUtils.formatMsg(string2, class_1242, objectArray), class_1242);
    }

    public static void moduleInfo(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(class_124.field_1080, module, string, objectArray);
    }

    private static void message(int n, class_124 class_1242, String string, Object ... objectArray) {
        ChatUtils.sendMsg(n, null, PrefixType.None, ChatUtils.formatMsg(string, class_1242, objectArray), class_1242);
    }

    public static void prefixWarning(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(class_124.field_1054, string, string2, objectArray);
    }

    public static void prefixInfo(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(class_124.field_1080, string, string2, objectArray);
    }

    public static void moduleError(Module module, class_2561 class_25612) {
        ChatUtils.moduleMessage(class_124.field_1061, module, class_25612);
    }

    public static void moduleError(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(class_124.field_1061, module, string, objectArray);
    }

    public static void error(String string, Object ... objectArray) {
        ChatUtils.message(0, class_124.field_1061, string, objectArray);
    }

    public static void moduleInfo(Module module, class_2561 class_25612) {
        ChatUtils.moduleMessage(class_124.field_1080, module, class_25612);
    }

    public static void info(String string, Object ... objectArray) {
        ChatUtils.message(0, class_124.field_1080, string, objectArray);
    }

    public static void info(String string, class_2561 class_25612) {
        ChatUtils.sendMsg(0, string, PrefixType.Other, class_25612);
    }

    public static class_2554 formatCoords(class_243 class_2432) {
        String string = String.format("(highlight)(underline)%.0f, %.0f, %.0f(default)", class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        string = ChatUtils.formatMsg(string, class_124.field_1080, new Object[0]);
        class_2585 class_25852 = new class_2585(string);
        class_25852.method_10862(class_25852.method_10866().method_27706(class_124.field_1073).method_10958(new class_2558(class_2558.class_2559.field_11750, String.format("%sb goto %d %d %d", Config.get().getPrefix(), (int)class_2432.field_1352, (int)class_2432.field_1351, (int)class_2432.field_1350))).method_10949(new class_2568(class_2568.class_5247.field_24342, (Object)new class_2585("Set as Baritone goal"))));
        return class_25852;
    }

    private static void moduleMessage(class_124 class_1242, Module module, class_2561 class_25612) {
        ChatUtils.sendMsg(0, module.title, PrefixType.Module, class_25612);
    }

    public static void moduleWarning(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(class_124.field_1054, module, string, objectArray);
    }

    private static void moduleMessage(class_124 class_1242, Module module, String string, Object ... objectArray) {
        ChatUtils.sendMsg(0, module.title, PrefixType.Module, ChatUtils.formatMsg(string, class_1242, objectArray), class_1242);
    }

    private static class_2554 getPrefix(String string, PrefixType prefixType) {
        class_2585 class_25852 = new class_2585("Crystal Client");
        class_2585 class_25853 = new class_2585("");
        class_25853.method_10862(class_25853.method_10866().method_27706(class_124.field_1078));
        class_25853.method_27693("\u00a7l<");
        class_25853.method_10852((class_2561)class_25852);
        class_25853.method_27693("\u00a7l> ");
        if (prefixType != PrefixType.None) {
            class_2585 class_25854 = new class_2585(string);
            class_25854.method_10862(class_25854.method_10866().method_27706(prefixType.color));
            class_25853.method_27693("\u00a7b[");
            class_25853.method_10852((class_2561)class_25854);
            class_25853.method_27693("\u00a7b] ");
        }
        return class_25853;
    }

    public static void prefixError(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(class_124.field_1061, string, string2, objectArray);
    }

    private static void sendMsg(int n, String string, PrefixType prefixType, class_2561 class_25612) {
        if (ChatUtils.mc.field_1687 == null) {
            return;
        }
        class_2585 class_25852 = new class_2585("");
        class_25852.method_10852((class_2561)ChatUtils.getPrefix(string, prefixType));
        class_25852.method_10852(class_25612);
        if (!Config.get().deleteChatCommandsInfo) {
            n = 0;
        }
        ((ChatHudAccessor)ChatUtils.mc.field_1705.method_1743()).add((class_2561)class_25852, n);
    }

    private static void sendMsg(int n, String string, PrefixType prefixType, String string2, class_124 class_1242) {
        String string3 = string2.replaceAll("\\(default\\)", class_1242.toString()).replaceAll("\\(highlight\\)", class_124.field_1068.toString());
        class_2585 class_25852 = new class_2585(string3);
        class_25852.method_10862(class_25852.method_10866().method_27706(class_1242));
        ChatUtils.sendMsg(n, string, prefixType, (class_2561)class_25852);
    }

    public static void warning(String string, Object ... objectArray) {
        ChatUtils.message(0, class_124.field_1054, string, objectArray);
    }

    public static String formatMsg(String string, class_124 class_1242, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        string2 = string2.replaceAll("\\(default\\)", class_1242.toString());
        string2 = string2.replaceAll("\\(highlight\\)", class_124.field_1068.toString());
        string2 = string2.replaceAll("\\(underline\\)", class_124.field_1073.toString());
        return string2;
    }

    private static final class PrefixType
    extends Enum<PrefixType> {
        public static final /* enum */ PrefixType None;
        private static final PrefixType[] $VALUES;
        public static final /* enum */ PrefixType Module;
        public class_124 color;
        public static final /* enum */ PrefixType Other;

        private static PrefixType[] $values() {
            return new PrefixType[]{Module, Other, None};
        }

        public static PrefixType[] values() {
            return (PrefixType[])$VALUES.clone();
        }

        static {
            Module = new PrefixType(class_124.field_1075);
            Other = new PrefixType(class_124.field_1076);
            None = new PrefixType(class_124.field_1070);
            $VALUES = PrefixType.$values();
        }

        public static PrefixType valueOf(String string) {
            return Enum.valueOf(PrefixType.class, string);
        }

        private PrefixType(class_124 class_1242) {
            this.color = class_1242;
        }
    }
}

