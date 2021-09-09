/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.chars.Char2CharArrayMap
 *  it.unimi.dsi.fastutil.chars.Char2CharMap
 *  net.minecraft.util.Formatting
 *  net.minecraft.text.BaseText
 *  net.minecraft.text.ClickEvent
 *  net.minecraft.text.ClickEvent$class_2559
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.gui.hud.ChatHudLine
 *  net.minecraft.util.ChatUtil
 *  net.minecraft.text.OrderedText
 */
package minegame159.meteorclient.systems.modules.misc;

import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.SendMessageEvent;
import minegame159.meteorclient.mixin.ChatHudLineAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.commands.commands.SayCommand;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.text.BaseText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.util.ChatUtil;
import net.minecraft.text.OrderedText;

public class BetterChat
extends Module {
    private final /* synthetic */ Setting<Boolean> suffixRandom;
    private final /* synthetic */ SettingGroup sgAnnoy;
    private final /* synthetic */ Setting<Boolean> fancyEnabled;
    private final /* synthetic */ StringBuilder sb;
    private final /* synthetic */ SettingGroup sgAntiSpam;
    private final /* synthetic */ SettingGroup sgFancyChat;
    private final /* synthetic */ Setting<Integer> longerChatLines;
    private final /* synthetic */ SettingGroup sgChatProtect;
    private final /* synthetic */ Setting<Boolean> prefixRandom;
    private final /* synthetic */ Setting<Boolean> suffixEnabled;
    private final /* synthetic */ Setting<Boolean> disableButton;
    private final /* synthetic */ Setting<Boolean> prefixSmallCaps;
    private final /* synthetic */ Setting<Boolean> antiSpamMoveToBottom;
    private final /* synthetic */ Setting<Boolean> longerChatEnabled;
    private static final /* synthetic */ Char2CharMap SMALL_CAPS;
    private final /* synthetic */ Setting<Boolean> suffixSmallCaps;
    private final /* synthetic */ Setting<Boolean> annoyEnabled;
    private final /* synthetic */ Setting<Boolean> infiniteChatBox;
    private final /* synthetic */ SettingGroup sgPrefix;
    private final /* synthetic */ Setting<Boolean> coordsProtectionEnabled;
    private final /* synthetic */ Setting<Boolean> prefixEnabled;
    private final /* synthetic */ SettingGroup sgLongerChat;
    private final /* synthetic */ Setting<Boolean> antiSpamEnabled;
    private final /* synthetic */ SettingGroup sgSuffix;
    private final /* synthetic */ Setting<String> prefixText;
    private final /* synthetic */ Setting<Integer> antiSpamDepth;
    private final /* synthetic */ Setting<String> suffixText;
    private final /* synthetic */ Setting<Boolean> disableAllMessages;
    private /* synthetic */ boolean skipMessage;

    public int getChatLength() {
        BetterChat llllllllllllllllIlllIlIllllIlllI;
        return llllllllllllllllIlllIlIllllIlllI.longerChatLines.get();
    }

    private void sendWarningMessage(String llllllllllllllllIlllIlIllIIIIIII, String llllllllllllllllIlllIlIlIlllllll, String llllllllllllllllIlllIlIllIIIIIll) {
        BetterChat llllllllllllllllIlllIlIllIIIIIIl;
        LiteralText llllllllllllllllIlllIlIllIIIIIlI = new LiteralText(llllllllllllllllIlllIlIlIlllllll);
        if (llllllllllllllllIlllIlIllIIIIIIl.disableButton.get().booleanValue()) {
            BaseText llllllllllllllllIlllIlIllIIIIlll = llllllllllllllllIlllIlIllIIIIIIl.getSendButton(llllllllllllllllIlllIlIllIIIIIII, llllllllllllllllIlllIlIllIIIIIll);
            llllllllllllllllIlllIlIllIIIIIlI.append((Text)llllllllllllllllIlllIlIllIIIIlll);
        }
        ChatUtils.info("Warning", (Text)llllllllllllllllIlllIlIllIIIIIlI);
    }

    private BaseText getSendButton(String llllllllllllllllIlllIlIllIIlIIlI, String llllllllllllllllIlllIlIllIIlIIIl) {
        LiteralText llllllllllllllllIlllIlIllIIlIlIl = new LiteralText("[SEND ANYWAY]");
        LiteralText llllllllllllllllIlllIlIllIIlIlII = new LiteralText("");
        LiteralText llllllllllllllllIlllIlIllIIlIIll = new LiteralText(llllllllllllllllIlllIlIllIIlIIIl);
        llllllllllllllllIlllIlIllIIlIIll.setStyle(llllllllllllllllIlllIlIllIIlIlII.getStyle().withFormatting(Formatting.GRAY));
        llllllllllllllllIlllIlIllIIlIlII.append((Text)llllllllllllllllIlllIlIllIIlIIll);
        llllllllllllllllIlllIlIllIIlIlII.append((Text)new LiteralText(String.valueOf(new StringBuilder().append('\n').append(llllllllllllllllIlllIlIllIIlIIlI))));
        llllllllllllllllIlllIlIllIIlIlIl.setStyle(llllllllllllllllIlllIlIllIIlIlIl.getStyle().withFormatting(Formatting.DARK_RED).withClickEvent(new ClickEvent(ClickEvent.class_2559.RUN_COMMAND, Commands.get().get(SayCommand.class).toString(llllllllllllllllIlllIlIllIIlIIlI))).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)llllllllllllllllIlllIlIllIIlIlII)));
        return llllllllllllllllIlllIlIllIIlIlIl;
    }

    private boolean containsCoordinates(String llllllllllllllllIlllIlIllIIlllll) {
        return llllllllllllllllIlllIlIllIIlllll.matches(".*(?<x>-?\\d{3,}(?:\\.\\d*)?)(?:\\s+(?<y>\\d{1,3}(?:\\.\\d*)?))?\\s+(?<z>-?\\d{3,}(?:\\.\\d*)?).*");
    }

    public BetterChat() {
        super(Categories.Misc, "better-chat", "Improves your chat experience in various ways.");
        BetterChat llllllllllllllllIlllIllIIlIIlIll;
        llllllllllllllllIlllIllIIlIIlIll.sgAnnoy = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Annoy");
        llllllllllllllllIlllIllIIlIIlIll.sgAntiSpam = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Anti Spam");
        llllllllllllllllIlllIllIIlIIlIll.sgChatProtect = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Chat Protect");
        llllllllllllllllIlllIllIIlIIlIll.sgFancyChat = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Fancy Chat");
        llllllllllllllllIlllIllIIlIIlIll.sgLongerChat = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Longer Chat");
        llllllllllllllllIlllIllIIlIIlIll.sgPrefix = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Prefix");
        llllllllllllllllIlllIllIIlIIlIll.sgSuffix = llllllllllllllllIlllIllIIlIIlIll.settings.createGroup("Suffix");
        llllllllllllllllIlllIllIIlIIlIll.annoyEnabled = llllllllllllllllIlllIllIIlIIlIll.sgAnnoy.add(new BoolSetting.Builder().name("annoy-enabled").description("Makes your messages aNnOyInG.").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.antiSpamEnabled = llllllllllllllllIlllIllIIlIIlIll.sgAntiSpam.add(new BoolSetting.Builder().name("anti-spam-enabled").description("Enables the anti-spam.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.antiSpamDepth = llllllllllllllllIlllIllIIlIIlIll.sgAntiSpam.add(new IntSetting.Builder().name("depth").description("How many chat messages to check for duplicate messages.").defaultValue(4).min(1).sliderMin(1).build());
        llllllllllllllllIlllIllIIlIIlIll.antiSpamMoveToBottom = llllllllllllllllIlllIllIIlIIlIll.sgAntiSpam.add(new BoolSetting.Builder().name("move-to-bottom").description("Moves any duplicate messages to the bottom of the chat.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.coordsProtectionEnabled = llllllllllllllllIlllIllIIlIIlIll.sgChatProtect.add(new BoolSetting.Builder().name("coords-protection-enabled").description("Prevents you from sending messages in chat that may contain coordinates.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.disableAllMessages = llllllllllllllllIlllIllIIlIIlIll.sgChatProtect.add(new BoolSetting.Builder().name("disable-all-messages").description("Prevents you from essentially being able to send messages in chat.").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.disableButton = llllllllllllllllIlllIllIIlIIlIll.sgChatProtect.add(new BoolSetting.Builder().name("disable-button").description("Adds a button to the warning to send a message to the chat in any way.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.fancyEnabled = llllllllllllllllIlllIllIIlIIlIll.sgFancyChat.add(new BoolSetting.Builder().name("fancy-chat-enabled").description("Makes your messages fancy!").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.infiniteChatBox = llllllllllllllllIlllIllIIlIIlIll.sgLongerChat.add(new BoolSetting.Builder().name("infinite-chat-box").description("Lets you type infinitely long messages.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.longerChatEnabled = llllllllllllllllIlllIllIIlIIlIll.sgLongerChat.add(new BoolSetting.Builder().name("longer-chat-enabled").description("Extends chat length.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.longerChatLines = llllllllllllllllIlllIllIIlIIlIll.sgLongerChat.add(new IntSetting.Builder().name("extra-lines").description("The amount of extra chat lines.").defaultValue(1000).min(100).sliderMax(1000).build());
        llllllllllllllllIlllIllIIlIIlIll.prefixEnabled = llllllllllllllllIlllIllIIlIIlIll.sgPrefix.add(new BoolSetting.Builder().name("prefix-enabled").description("Enables a prefix.").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.prefixText = llllllllllllllllIlllIllIIlIIlIll.sgPrefix.add(new StringSetting.Builder().name("text").description("The text to add as your prefix.").defaultValue("> ").build());
        llllllllllllllllIlllIllIIlIIlIll.prefixSmallCaps = llllllllllllllllIlllIllIIlIIlIll.sgPrefix.add(new BoolSetting.Builder().name("small-caps").description("Uses a small font.").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.prefixRandom = llllllllllllllllIlllIllIIlIIlIll.sgPrefix.add(new BoolSetting.Builder().name("random-number").description("Example: <msg> (538)").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.suffixEnabled = llllllllllllllllIlllIllIIlIIlIll.sgSuffix.add(new BoolSetting.Builder().name("suffix-enabled").description("Enables a suffix.").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.suffixText = llllllllllllllllIlllIllIIlIIlIll.sgSuffix.add(new StringSetting.Builder().name("text").description("The text to add as your suffix.").defaultValue(" | Meteor on Crack!").build());
        llllllllllllllllIlllIllIIlIIlIll.suffixSmallCaps = llllllllllllllllIlllIllIIlIIlIll.sgSuffix.add(new BoolSetting.Builder().name("small-caps").description("Uses a small font.").defaultValue(true).build());
        llllllllllllllllIlllIllIIlIIlIll.suffixRandom = llllllllllllllllIlllIllIIlIIlIll.sgSuffix.add(new BoolSetting.Builder().name("random").description("Example: <msg> (538)").defaultValue(false).build());
        llllllllllllllllIlllIllIIlIIlIll.sb = new StringBuilder();
    }

    public boolean isInfiniteChatBox() {
        BetterChat llllllllllllllllIlllIlIlllllIIll;
        return llllllllllllllllIlllIlIlllllIIll.isActive() && llllllllllllllllIlllIlIlllllIIll.infiniteChatBox.get() != false;
    }

    @EventHandler
    private void onSendMessage(SendMessageEvent llllllllllllllllIlllIllIIIllIlII) {
        BetterChat llllllllllllllllIlllIllIIIllIlIl;
        String llllllllllllllllIlllIllIIIllIIll = llllllllllllllllIlllIllIIIllIlII.msg;
        if (llllllllllllllllIlllIllIIIllIlIl.annoyEnabled.get().booleanValue()) {
            llllllllllllllllIlllIllIIIllIIll = llllllllllllllllIlllIllIIIllIlIl.applyAnnoy(llllllllllllllllIlllIllIIIllIIll);
        }
        if (llllllllllllllllIlllIllIIIllIlIl.fancyEnabled.get().booleanValue()) {
            llllllllllllllllIlllIllIIIllIIll = llllllllllllllllIlllIllIIIllIlIl.applyFancy(llllllllllllllllIlllIllIIIllIIll);
        }
        llllllllllllllllIlllIllIIIllIIll = String.valueOf(new StringBuilder().append(llllllllllllllllIlllIllIIIllIlIl.getPrefix()).append(llllllllllllllllIlllIllIIIllIIll).append(llllllllllllllllIlllIllIIIllIlIl.getSuffix()));
        if (llllllllllllllllIlllIllIIIllIlIl.disableAllMessages.get().booleanValue()) {
            llllllllllllllllIlllIllIIIllIlIl.sendWarningMessage(llllllllllllllllIlllIllIIIllIIll, "You are trying to send a message to the global chat! ", "Send your message to the global chat:");
            llllllllllllllllIlllIllIIIllIlII.cancel();
            return;
        }
        if (llllllllllllllllIlllIllIIIllIlIl.coordsProtectionEnabled.get().booleanValue() && llllllllllllllllIlllIllIIIllIlIl.containsCoordinates(llllllllllllllllIlllIllIIIllIIll)) {
            llllllllllllllllIlllIllIIIllIlIl.sendWarningMessage(llllllllllllllllIlllIllIIIllIIll, "It looks like there are coordinates in your message! ", "Send your message to the global chat even if there are coordinates:");
            llllllllllllllllIlllIllIIIllIlII.cancel();
            return;
        }
        llllllllllllllllIlllIllIIIllIlII.msg = llllllllllllllllIlllIllIIIllIIll;
    }

    private String applyAnnoy(String llllllllllllllllIlllIlIllllIIIll) {
        StringBuilder llllllllllllllllIlllIlIllllIIIlI = new StringBuilder(llllllllllllllllIlllIlIllllIIIll.length());
        boolean llllllllllllllllIlllIlIllllIIIIl = true;
        for (int llllllllllllllllIlllIlIllllIIlIl : llllllllllllllllIlllIlIllllIIIll.codePoints().toArray()) {
            if (llllllllllllllllIlllIlIllllIIIIl) {
                llllllllllllllllIlllIlIllllIIIlI.appendCodePoint(Character.toUpperCase(llllllllllllllllIlllIlIllllIIlIl));
            } else {
                llllllllllllllllIlllIlIllllIIIlI.appendCodePoint(Character.toLowerCase(llllllllllllllllIlllIlIllllIIlIl));
            }
            llllllllllllllllIlllIlIllllIIIIl = !llllllllllllllllIlllIlIllllIIIIl;
        }
        llllllllllllllllIlllIlIllllIIIll = String.valueOf(llllllllllllllllIlllIlIllllIIIlI);
        return llllllllllllllllIlllIlIllllIIIll;
    }

    private String getPrefix() {
        BetterChat llllllllllllllllIlllIlIllIlIIlIl;
        return llllllllllllllllIlllIlIllIlIIlIl.getAffix(llllllllllllllllIlllIlIllIlIIlIl.prefixEnabled, llllllllllllllllIlllIlIllIlIIlIl.prefixRandom, "(%03d) ", llllllllllllllllIlllIlIllIlIIlIl.prefixText, llllllllllllllllIlllIlIllIlIIlIl.prefixSmallCaps);
    }

    public boolean onMsg(String llllllllllllllllIlllIllIIIllllIl, int llllllllllllllllIlllIllIIIllllII, int llllllllllllllllIlllIllIIIlllIll, List<ChatHudLine<Text>> llllllllllllllllIlllIllIIIlllIlI, List<ChatHudLine<OrderedText>> llllllllllllllllIlllIllIIIlllIIl) {
        BetterChat llllllllllllllllIlllIllIIlIIIlII;
        if (!llllllllllllllllIlllIllIIlIIIlII.isActive() || llllllllllllllllIlllIllIIlIIIlII.skipMessage) {
            return false;
        }
        return llllllllllllllllIlllIllIIlIIIlII.antiSpamEnabled.get() != false && llllllllllllllllIlllIllIIlIIIlII.antiSpamOnMsg(llllllllllllllllIlllIllIIIllllIl, llllllllllllllllIlllIllIIIllllII, llllllllllllllllIlllIllIIIlllIll, llllllllllllllllIlllIllIIIlllIlI, llllllllllllllllIlllIllIIIlllIIl);
    }

    private String getSuffix() {
        BetterChat llllllllllllllllIlllIlIllIlIIIlI;
        return llllllllllllllllIlllIlIllIlIIIlI.getAffix(llllllllllllllllIlllIlIllIlIIIlI.suffixEnabled, llllllllllllllllIlllIlIllIlIIIlI.suffixRandom, " (%03d)", llllllllllllllllIlllIlIllIlIIIlI.suffixText, llllllllllllllllIlllIlIllIlIIIlI.suffixSmallCaps);
    }

    static {
        SMALL_CAPS = new Char2CharArrayMap();
        String[] llllllllllllllllIlllIlIlIlllIlll = "abcdefghijklmnopqrstuvwxyz".split("");
        String[] llllllllllllllllIlllIlIlIlllIllI = "\u1d00\u0299\u1d04\u1d05\u1d07\ua730\u0262\u029c\u026a\u1d0a\u1d0b\u029f\u1d0d\u0274\u1d0f\u1d29q\u0280\ua731\u1d1b\u1d1c\u1d20\u1d21xy\u1d22".split("");
        for (int llllllllllllllllIlllIlIlIllllIII = 0; llllllllllllllllIlllIlIlIllllIII < llllllllllllllllIlllIlIlIlllIlll.length; ++llllllllllllllllIlllIlIlIllllIII) {
            SMALL_CAPS.put(llllllllllllllllIlllIlIlIlllIlll[llllllllllllllllIlllIlIlIllllIII].charAt(0), llllllllllllllllIlllIlIlIlllIllI[llllllllllllllllIlllIlIlIllllIII].charAt(0));
        }
    }

    private boolean antiSpamCheckMsg(List<ChatHudLine<OrderedText>> llllllllllllllllIlllIllIIIIIIlll, String llllllllllllllllIlllIllIIIIIIllI, int llllllllllllllllIlllIllIIIIIIlIl, int llllllllllllllllIlllIllIIIIIIlII, int llllllllllllllllIlllIllIIIIIIIll) {
        ChatHudLine<OrderedText> llllllllllllllllIlllIllIIIIIIIlI;
        ChatHudLine<OrderedText> ChatHudLine2 = llllllllllllllllIlllIllIIIIIIIlI = llllllllllllllllIlllIllIIIIIIlll.size() > llllllllllllllllIlllIllIIIIIIIll ? llllllllllllllllIlllIllIIIIIIlll.get(llllllllllllllllIlllIllIIIIIIIll) : null;
        if (llllllllllllllllIlllIllIIIIIIIlI == null) {
            return false;
        }
        String llllllllllllllllIlllIllIIIIIIIIl = ((OrderedText)llllllllllllllllIlllIllIIIIIIIlI.getText()).toString();
        if (ChatUtil.stripTextFormat((String)llllllllllllllllIlllIllIIIIIIIIl).equals(llllllllllllllllIlllIllIIIIIIllI)) {
            llllllllllllllllIlllIllIIIIIIIIl = String.valueOf(new StringBuilder().append(llllllllllllllllIlllIllIIIIIIIIl).append((Object)Formatting.GRAY).append(" (2)"));
            ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setText(new LiteralText(llllllllllllllllIlllIllIIIIIIIIl));
            ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setTimestamp(llllllllllllllllIlllIllIIIIIIlIl);
            ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setId(llllllllllllllllIlllIllIIIIIIlII);
            return true;
        }
        Matcher llllllllllllllllIlllIllIIIIIlIIl = Pattern.compile(".*(\\([0-9]+\\)$)").matcher(llllllllllllllllIlllIllIIIIIIIIl);
        if (llllllllllllllllIlllIllIIIIIlIIl.matches()) {
            String llllllllllllllllIlllIllIIIIIllII = llllllllllllllllIlllIllIIIIIlIIl.group(1);
            int llllllllllllllllIlllIllIIIIIlIll = Integer.parseInt(llllllllllllllllIlllIllIIIIIllII.substring(1, llllllllllllllllIlllIllIIIIIllII.length() - 1));
            int llllllllllllllllIlllIllIIIIIlIlI = llllllllllllllllIlllIllIIIIIIIIl.lastIndexOf(llllllllllllllllIlllIllIIIIIllII);
            if (ChatUtil.stripTextFormat((String)(llllllllllllllllIlllIllIIIIIIIIl = llllllllllllllllIlllIllIIIIIIIIl.substring(0, llllllllllllllllIlllIllIIIIIlIlI - Formatting.GRAY.toString().length() - 1))).equals(llllllllllllllllIlllIllIIIIIIllI)) {
                llllllllllllllllIlllIllIIIIIIIIl = String.valueOf(new StringBuilder().append(llllllllllllllllIlllIllIIIIIIIIl).append((Object)Formatting.GRAY).append(" (").append(llllllllllllllllIlllIllIIIIIlIll + 1).append(")"));
                ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setText(new LiteralText(llllllllllllllllIlllIllIIIIIIIIl));
                ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setTimestamp(llllllllllllllllIlllIllIIIIIIlIl);
                ((ChatHudLineAccessor)llllllllllllllllIlllIllIIIIIIIlI).setId(llllllllllllllllIlllIllIIIIIIlII);
                return true;
            }
        }
        return false;
    }

    public boolean isLongerChat() {
        BetterChat llllllllllllllllIlllIlIlllllIIIl;
        return llllllllllllllllIlllIlIlllllIIIl.longerChatEnabled.get();
    }

    private String applyFancy(String llllllllllllllllIlllIlIlllIlIIII) {
        BetterChat llllllllllllllllIlllIlIlllIIlllI;
        String llllllllllllllllIlllIlIlllIIllll = llllllllllllllllIlllIlIlllIlIIII;
        llllllllllllllllIlllIlIlllIIlllI.sb.setLength(0);
        for (char llllllllllllllllIlllIlIlllIlIIlI : llllllllllllllllIlllIlIlllIIllll.toCharArray()) {
            if (SMALL_CAPS.containsKey(llllllllllllllllIlllIlIlllIlIIlI)) {
                llllllllllllllllIlllIlIlllIIlllI.sb.append(SMALL_CAPS.get(llllllllllllllllIlllIlIlllIlIIlI));
                continue;
            }
            llllllllllllllllIlllIlIlllIIlllI.sb.append(llllllllllllllllIlllIlIlllIlIIlI);
        }
        llllllllllllllllIlllIlIlllIIllll = String.valueOf(llllllllllllllllIlllIlIlllIIlllI.sb);
        return llllllllllllllllIlllIlIlllIIllll;
    }

    private String getAffix(Setting<Boolean> llllllllllllllllIlllIlIllIllIIIl, Setting<Boolean> llllllllllllllllIlllIlIllIllIlll, String llllllllllllllllIlllIlIllIllIllI, Setting<String> llllllllllllllllIlllIlIllIllIlIl, Setting<Boolean> llllllllllllllllIlllIlIllIllIlII) {
        String llllllllllllllllIlllIlIllIllIIll;
        if (llllllllllllllllIlllIlIllIllIIIl.get().booleanValue()) {
            if (llllllllllllllllIlllIlIllIllIlll.get().booleanValue()) {
                String llllllllllllllllIlllIlIllIllllII = String.format(llllllllllllllllIlllIlIllIllIllI, Utils.random(0, 1000));
            } else {
                String llllllllllllllllIlllIlIllIlllIlI = llllllllllllllllIlllIlIllIllIlIl.get();
                if (llllllllllllllllIlllIlIllIllIlII.get().booleanValue()) {
                    BetterChat llllllllllllllllIlllIlIllIlllIIl;
                    llllllllllllllllIlllIlIllIlllIIl.sb.setLength(0);
                    for (char llllllllllllllllIlllIlIllIlllIll : llllllllllllllllIlllIlIllIlllIlI.toCharArray()) {
                        if (SMALL_CAPS.containsKey(llllllllllllllllIlllIlIllIlllIll)) {
                            llllllllllllllllIlllIlIllIlllIIl.sb.append(SMALL_CAPS.get(llllllllllllllllIlllIlIllIlllIll));
                            continue;
                        }
                        llllllllllllllllIlllIlIllIlllIIl.sb.append(llllllllllllllllIlllIlIllIlllIll);
                    }
                    llllllllllllllllIlllIlIllIlllIlI = String.valueOf(llllllllllllllllIlllIlIllIlllIIl.sb);
                }
            }
        } else {
            llllllllllllllllIlllIlIllIllIIll = "";
        }
        return llllllllllllllllIlllIlIllIllIIll;
    }

    private boolean antiSpamOnMsg(String llllllllllllllllIlllIllIIIlIIlII, int llllllllllllllllIlllIllIIIIlllIl, int llllllllllllllllIlllIllIIIIlllII, List<ChatHudLine<Text>> llllllllllllllllIlllIllIIIlIIIIl, List<ChatHudLine<OrderedText>> llllllllllllllllIlllIllIIIlIIIII) {
        BetterChat llllllllllllllllIlllIllIIIlIIlIl;
        llllllllllllllllIlllIllIIIlIIlII = ChatUtil.stripTextFormat((String)llllllllllllllllIlllIllIIIlIIlII);
        for (int llllllllllllllllIlllIllIIIlIIllI = 0; llllllllllllllllIlllIllIIIlIIllI < llllllllllllllllIlllIllIIIlIIlIl.antiSpamDepth.get(); ++llllllllllllllllIlllIllIIIlIIllI) {
            if (!llllllllllllllllIlllIllIIIlIIlIl.antiSpamCheckMsg(llllllllllllllllIlllIllIIIlIIIII, llllllllllllllllIlllIllIIIlIIlII, llllllllllllllllIlllIllIIIIlllII, llllllllllllllllIlllIllIIIIlllIl, llllllllllllllllIlllIllIIIlIIllI)) continue;
            if (llllllllllllllllIlllIllIIIlIIlIl.antiSpamMoveToBottom.get().booleanValue() && llllllllllllllllIlllIllIIIlIIllI != 0) {
                ChatHudLine<OrderedText> llllllllllllllllIlllIllIIIlIIlll = llllllllllllllllIlllIllIIIlIIIII.remove(llllllllllllllllIlllIllIIIlIIllI);
                llllllllllllllllIlllIllIIIlIIIII.add(0, llllllllllllllllIlllIllIIIlIIlll);
                llllllllllllllllIlllIllIIIlIIIIl.add(0, llllllllllllllllIlllIllIIIlIIlll);
            }
            return true;
        }
        return false;
    }
}

