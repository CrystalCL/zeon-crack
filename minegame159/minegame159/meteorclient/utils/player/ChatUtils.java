/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.mixin.ChatHudAccessor;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.BaseText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;

public class ChatUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void info(Text Text2) {
        ChatUtils.sendMsg(0, null, PrefixType.None, Text2);
    }

    public static void moduleWarning(Module module, Text Text2) {
        ChatUtils.moduleMessage(Formatting.YELLOW, module, Text2);
    }

    public static void info(int n, String string, Object ... objectArray) {
        ChatUtils.message(n, Formatting.GRAY, string, objectArray);
    }

    private static void prefixMessage(Formatting Schema1483, String string, String string2, Object ... objectArray) {
        ChatUtils.sendMsg(0, string, PrefixType.Other, ChatUtils.formatMsg(string2, Schema1483, objectArray), Schema1483);
    }

    public static void moduleInfo(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(Formatting.GRAY, module, string, objectArray);
    }

    private static void message(int n, Formatting Schema1483, String string, Object ... objectArray) {
        ChatUtils.sendMsg(n, null, PrefixType.None, ChatUtils.formatMsg(string, Schema1483, objectArray), Schema1483);
    }

    public static void prefixWarning(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(Formatting.YELLOW, string, string2, objectArray);
    }

    public static void prefixInfo(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(Formatting.GRAY, string, string2, objectArray);
    }

    public static void moduleError(Module module, Text Text2) {
        ChatUtils.moduleMessage(Formatting.RED, module, Text2);
    }

    public static void moduleError(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(Formatting.RED, module, string, objectArray);
    }

    public static void error(String string, Object ... objectArray) {
        ChatUtils.message(0, Formatting.RED, string, objectArray);
    }

    public static void moduleInfo(Module module, Text Text2) {
        ChatUtils.moduleMessage(Formatting.GRAY, module, Text2);
    }

    public static void info(String string, Object ... objectArray) {
        ChatUtils.message(0, Formatting.GRAY, string, objectArray);
    }

    public static void info(String string, Text Text2) {
        ChatUtils.sendMsg(0, string, PrefixType.Other, Text2);
    }

    public static BaseText formatCoords(Vec3d Vec3d2) {
        String string = String.format("(highlight)(underline)%.0f, %.0f, %.0f(default)", Vec3d2.x, Vec3d2.y, Vec3d2.z);
        string = ChatUtils.formatMsg(string, Formatting.GRAY, new Object[0]);
        LiteralText LiteralText2 = new LiteralText(string);
        LiteralText2.setStyle(LiteralText2.getStyle().withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.class_2559.RUN_COMMAND, String.format("%sb goto %d %d %d", Config.get().getPrefix(), (int)Vec3d2.x, (int)Vec3d2.y, (int)Vec3d2.z))).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText("Set as Baritone goal"))));
        return LiteralText2;
    }

    private static void moduleMessage(Formatting Schema1483, Module module, Text Text2) {
        ChatUtils.sendMsg(0, module.title, PrefixType.Module, Text2);
    }

    public static void moduleWarning(Module module, String string, Object ... objectArray) {
        ChatUtils.moduleMessage(Formatting.YELLOW, module, string, objectArray);
    }

    private static void moduleMessage(Formatting Schema1483, Module module, String string, Object ... objectArray) {
        ChatUtils.sendMsg(0, module.title, PrefixType.Module, ChatUtils.formatMsg(string, Schema1483, objectArray), Schema1483);
    }

    private static BaseText getPrefix(String string, PrefixType prefixType) {
        LiteralText LiteralText2 = new LiteralText("Crystal Client");
        LiteralText LiteralText3 = new LiteralText("");
        LiteralText3.setStyle(LiteralText3.getStyle().withFormatting(Formatting.BLUE));
        LiteralText3.append("\u00a7l<");
        LiteralText3.append((Text)LiteralText2);
        LiteralText3.append("\u00a7l> ");
        if (prefixType != PrefixType.None) {
            LiteralText LiteralText4 = new LiteralText(string);
            LiteralText4.setStyle(LiteralText4.getStyle().withFormatting(prefixType.color));
            LiteralText3.append("\u00a7b[");
            LiteralText3.append((Text)LiteralText4);
            LiteralText3.append("\u00a7b] ");
        }
        return LiteralText3;
    }

    public static void prefixError(String string, String string2, Object ... objectArray) {
        ChatUtils.prefixMessage(Formatting.RED, string, string2, objectArray);
    }

    private static void sendMsg(int n, String string, PrefixType prefixType, Text Text2) {
        if (ChatUtils.mc.world == null) {
            return;
        }
        LiteralText LiteralText2 = new LiteralText("");
        LiteralText2.append((Text)ChatUtils.getPrefix(string, prefixType));
        LiteralText2.append(Text2);
        if (!Config.get().deleteChatCommandsInfo) {
            n = 0;
        }
        ((ChatHudAccessor)ChatUtils.mc.inGameHud.getChatHud()).add((Text)LiteralText2, n);
    }

    private static void sendMsg(int n, String string, PrefixType prefixType, String string2, Formatting Schema1483) {
        String string3 = string2.replaceAll("\\(default\\)", Schema1483.toString()).replaceAll("\\(highlight\\)", Formatting.WHITE.toString());
        LiteralText LiteralText2 = new LiteralText(string3);
        LiteralText2.setStyle(LiteralText2.getStyle().withFormatting(Schema1483));
        ChatUtils.sendMsg(n, string, prefixType, (Text)LiteralText2);
    }

    public static void warning(String string, Object ... objectArray) {
        ChatUtils.message(0, Formatting.YELLOW, string, objectArray);
    }

    public static String formatMsg(String string, Formatting Schema1483, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        string2 = string2.replaceAll("\\(default\\)", Schema1483.toString());
        string2 = string2.replaceAll("\\(highlight\\)", Formatting.WHITE.toString());
        string2 = string2.replaceAll("\\(underline\\)", Formatting.UNDERLINE.toString());
        return string2;
    }

    private static final class PrefixType
    extends Enum<PrefixType> {
        public static final /* enum */ PrefixType None;
        private static final PrefixType[] $VALUES;
        public static final /* enum */ PrefixType Module;
        public Formatting color;
        public static final /* enum */ PrefixType Other;

        private static PrefixType[] $values() {
            return new PrefixType[]{Module, Other, None};
        }

        public static PrefixType[] values() {
            return (PrefixType[])$VALUES.clone();
        }

        static {
            Module = new PrefixType(Formatting.AQUA);
            Other = new PrefixType(Formatting.LIGHT_PURPLE);
            None = new PrefixType(Formatting.RESET);
            $VALUES = PrefixType.$values();
        }

        public static PrefixType valueOf(String string) {
            return Enum.valueOf(PrefixType.class, string);
        }

        private PrefixType(Formatting Schema1483) {
            this.color = Schema1483;
        }
    }
}

