/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Formatting
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.text.BaseText
 *  net.minecraft.text.ClickEvent
 *  net.minecraft.text.ClickEvent$class_2559
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.MinecraftClient
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
    private static final /* synthetic */ MinecraftClient mc;

    private static void moduleMessage(Formatting lIIIIlIlll, Module lIIIIlIIlI, String lIIIIlIlIl, Object ... lIIIIlIIII) {
        ChatUtils.sendMsg(0, lIIIIlIIlI.title, PrefixType.Module, ChatUtils.formatMsg(lIIIIlIlIl, lIIIIlIlll, lIIIIlIIII), lIIIIlIlll);
    }

    public static void error(String lIIlIIIllI, Object ... lIIlIIIIll) {
        ChatUtils.message(0, Formatting.RED, lIIlIIIllI, lIIlIIIIll);
    }

    private static void sendMsg(int llIllllII, String llIlllIll, PrefixType llIllllll, Text llIlllllI) {
        if (ChatUtils.mc.world == null) {
            return;
        }
        LiteralText llIllllIl = new LiteralText("");
        llIllllIl.append((Text)ChatUtils.getPrefix(llIlllIll, llIllllll));
        llIllllIl.append(llIlllllI);
        if (!Config.get().deleteChatCommandsInfo) {
            llIllllII = 0;
        }
        ((ChatHudAccessor)ChatUtils.mc.inGameHud.getChatHud()).add((Text)llIllllIl, llIllllII);
    }

    public static void warning(String lIIlIIlIlI, Object ... lIIlIIlIll) {
        ChatUtils.message(0, Formatting.YELLOW, lIIlIIlIlI, lIIlIIlIll);
    }

    public static void moduleWarning(Module llllIllII, Text llllIlIll) {
        ChatUtils.moduleMessage(Formatting.YELLOW, llllIllII, llllIlIll);
    }

    private static BaseText getPrefix(String llIlIIlII, PrefixType llIlIIlll) {
        LiteralText llIlIIllI = new LiteralText("Crystal Client");
        LiteralText llIlIIlIl = new LiteralText("");
        llIlIIlIl.setStyle(llIlIIlIl.getStyle().withFormatting(Formatting.BLUE));
        llIlIIlIl.append("\u00a7l<");
        llIlIIlIl.append((Text)llIlIIllI);
        llIlIIlIl.append("\u00a7l> ");
        if (llIlIIlll != PrefixType.None) {
            LiteralText llIlIlIIl = new LiteralText(llIlIIlII);
            llIlIlIIl.setStyle(llIlIlIIl.getStyle().withFormatting(llIlIIlll.color));
            llIlIIlIl.append("\u00a7b[");
            llIlIIlIl.append((Text)llIlIlIIl);
            llIlIIlIl.append("\u00a7b] ");
        }
        return llIlIIlIl;
    }

    static {
        mc = MinecraftClient.getInstance();
    }

    public static void prefixError(String lIIIlIIIIl, String lIIIlIIIII, Object ... lIIIIlllII) {
        ChatUtils.prefixMessage(Formatting.RED, lIIIlIIIIl, lIIIlIIIII, lIIIIlllII);
    }

    public static void prefixWarning(String lIIIlIIlll, String lIIIlIIllI, Object ... lIIIlIIlIl) {
        ChatUtils.prefixMessage(Formatting.YELLOW, lIIIlIIlll, lIIIlIIllI, lIIIlIIlIl);
    }

    public static void info(String lIIlIlIIll, Text lIIlIlIlII) {
        ChatUtils.sendMsg(0, lIIlIlIIll, PrefixType.Other, lIIlIlIlII);
    }

    public static void prefixInfo(String lIIIllIIII, String lIIIllIIlI, Object ... lIIIlIlllI) {
        ChatUtils.prefixMessage(Formatting.GRAY, lIIIllIIII, lIIIllIIlI, lIIIlIlllI);
    }

    private static void moduleMessage(Formatting lIIIIIllIl, Module lIIIIIlIlI, Text lIIIIIlIIl) {
        ChatUtils.sendMsg(0, lIIIIIlIlI.title, PrefixType.Module, lIIIIIlIIl);
    }

    public static BaseText formatCoords(Vec3d llIllIlII) {
        String llIllIIll = String.format("(highlight)(underline)%.0f, %.0f, %.0f(default)", llIllIlII.x, llIllIlII.y, llIllIlII.z);
        llIllIIll = ChatUtils.formatMsg(llIllIIll, Formatting.GRAY, new Object[0]);
        LiteralText llIllIIlI = new LiteralText(llIllIIll);
        llIllIIlI.setStyle(llIllIIlI.getStyle().withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.class_2559.RUN_COMMAND, String.format("%sb goto %d %d %d", Config.get().getPrefix(), (int)llIllIlII.x, (int)llIllIlII.y, (int)llIllIlII.z))).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText("Set as Baritone goal"))));
        return llIllIIlI;
    }

    public static void moduleError(Module lllIlllll, Text lllIllllI) {
        ChatUtils.moduleMessage(Formatting.RED, lllIlllll, lllIllllI);
    }

    private static void prefixMessage(Formatting lIIIlllIlI, String lIIIllllIl, String lIIIlllIII, Object ... lIIIlllIll) {
        ChatUtils.sendMsg(0, lIIIllllIl, PrefixType.Other, ChatUtils.formatMsg(lIIIlllIII, lIIIlllIlI, lIIIlllIll), lIIIlllIlI);
    }

    public static void info(String lIIlIllIIl, Object ... lIIlIllIlI) {
        ChatUtils.message(0, Formatting.GRAY, lIIlIllIIl, lIIlIllIlI);
    }

    private static void sendMsg(int lllIIllIl, String lllIIllII, PrefixType lllIIlIll, String lllIlIIIl, Formatting lllIIlIIl) {
        String lllIIllll = lllIlIIIl.replaceAll("\\(default\\)", lllIIlIIl.toString()).replaceAll("\\(highlight\\)", Formatting.WHITE.toString());
        LiteralText lllIIlllI = new LiteralText(lllIIllll);
        lllIIlllI.setStyle(lllIIlllI.getStyle().withFormatting(lllIIlIIl));
        ChatUtils.sendMsg(lllIIllIl, lllIIllII, lllIIlIll, (Text)lllIIlllI);
    }

    public static void moduleError(Module llllIIlll, String llllIIllI, Object ... llllIIlIl) {
        ChatUtils.moduleMessage(Formatting.RED, llllIIlll, llllIIllI, llllIIlIl);
    }

    public static void moduleWarning(Module lllllIIll, String lllllIIlI, Object ... lllllIIIl) {
        ChatUtils.moduleMessage(Formatting.YELLOW, lllllIIll, lllllIIlI, lllllIIIl);
    }

    public static void info(int lIIllIIIII, String lIIlIlllll, Object ... lIIllIIIIl) {
        ChatUtils.message(lIIllIIIII, Formatting.GRAY, lIIlIlllll, lIIllIIIIl);
    }

    public static String formatMsg(String llIIllIll, Formatting llIIllIlI, Object ... llIIllIIl) {
        String llIIllIII = String.format(llIIllIll, llIIllIIl);
        llIIllIII = llIIllIII.replaceAll("\\(default\\)", llIIllIlI.toString());
        llIIllIII = llIIllIII.replaceAll("\\(highlight\\)", Formatting.WHITE.toString());
        llIIllIII = llIIllIII.replaceAll("\\(underline\\)", Formatting.UNDERLINE.toString());
        return llIIllIII;
    }

    public static void moduleInfo(Module lIIIIIIIlI, String lIIIIIIIIl, Object ... lIIIIIIIll) {
        ChatUtils.moduleMessage(Formatting.GRAY, lIIIIIIIlI, lIIIIIIIIl, lIIIIIIIll);
    }

    public static void info(Text lIIlIlIIII) {
        ChatUtils.sendMsg(0, null, PrefixType.None, lIIlIlIIII);
    }

    public static void moduleInfo(Module llllllIll, Text lllllllII) {
        ChatUtils.moduleMessage(Formatting.GRAY, llllllIll, lllllllII);
    }

    public ChatUtils() {
        ChatUtils lIIlllIIll;
    }

    private static void message(int lIIllIlllI, Formatting lIIllIllIl, String lIIllIllII, Object ... lIIllIIlll) {
        ChatUtils.sendMsg(lIIllIlllI, null, PrefixType.None, ChatUtils.formatMsg(lIIllIllII, lIIllIllIl, lIIllIIlll), lIIllIllIl);
    }

    private static final class PrefixType
    extends Enum<PrefixType> {
        public static final /* synthetic */ /* enum */ PrefixType Module;
        public /* synthetic */ Formatting color;
        private static final /* synthetic */ PrefixType[] $VALUES;
        public static final /* synthetic */ /* enum */ PrefixType Other;
        public static final /* synthetic */ /* enum */ PrefixType None;

        private static /* synthetic */ PrefixType[] $values() {
            return new PrefixType[]{Module, Other, None};
        }

        public static PrefixType valueOf(String llIIlIlIllIIlIl) {
            return Enum.valueOf(PrefixType.class, llIIlIlIllIIlIl);
        }

        public static PrefixType[] values() {
            return (PrefixType[])$VALUES.clone();
        }

        private PrefixType(Formatting llIIlIlIlIllIll) {
            PrefixType llIIlIlIllIIIII;
            llIIlIlIllIIIII.color = llIIlIlIlIllIll;
        }

        static {
            Module = new PrefixType(Formatting.AQUA);
            Other = new PrefixType(Formatting.LIGHT_PURPLE);
            None = new PrefixType(Formatting.RESET);
            $VALUES = PrefixType.$values();
        }
    }
}

