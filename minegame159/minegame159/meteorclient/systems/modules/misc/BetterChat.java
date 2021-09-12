/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_124;
import net.minecraft.class_2554;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2585;
import net.minecraft.class_303;
import net.minecraft.class_3544;
import net.minecraft.class_5481;

public class BetterChat
extends Module {
    private final Setting<Boolean> prefixRandom;
    private boolean skipMessage;
    private final SettingGroup sgAnnoy;
    private final Setting<Boolean> coordsProtectionEnabled;
    private final Setting<Boolean> suffixSmallCaps;
    private final SettingGroup sgSuffix;
    private final Setting<Boolean> fancyEnabled;
    private final SettingGroup sgPrefix;
    private final Setting<Boolean> disableAllMessages;
    private final SettingGroup sgAntiSpam;
    private final Setting<Boolean> suffixEnabled;
    private static final Char2CharMap SMALL_CAPS = new Char2CharArrayMap();
    private final SettingGroup sgChatProtect;
    private final Setting<Boolean> longerChatEnabled;
    private final Setting<Boolean> suffixRandom;
    private final Setting<Boolean> infiniteChatBox;
    private final Setting<Integer> antiSpamDepth;
    private final SettingGroup sgLongerChat;
    private final Setting<Boolean> disableButton;
    private final Setting<Integer> longerChatLines;
    private final StringBuilder sb;
    private final Setting<String> prefixText;
    private final Setting<Boolean> prefixSmallCaps;
    private final Setting<Boolean> antiSpamEnabled;
    private final SettingGroup sgFancyChat;
    private final Setting<Boolean> prefixEnabled;
    private final Setting<Boolean> antiSpamMoveToBottom;
    private final Setting<String> suffixText;
    private final Setting<Boolean> annoyEnabled;

    static {
        String[] stringArray = "abcdefghijklmnopqrstuvwxyz".split("");
        String[] stringArray2 = "\u1d00\u0299\u1d04\u1d05\u1d07\ua730\u0262\u029c\u026a\u1d0a\u1d0b\u029f\u1d0d\u0274\u1d0f\u1d29q\u0280\ua731\u1d1b\u1d1c\u1d20\u1d21xy\u1d22".split("");
        for (int i = 0; i < stringArray.length; ++i) {
            SMALL_CAPS.put(stringArray[i].charAt(0), stringArray2[i].charAt(0));
            if (2 != 0) continue;
            break;
        }
    }

    private class_2554 getSendButton(String string, String string2) {
        class_2585 class_25852 = new class_2585("[SEND ANYWAY]");
        class_2585 class_25853 = new class_2585("");
        class_2585 class_25854 = new class_2585(string2);
        class_25854.method_10862(class_25853.method_10866().method_27706(class_124.field_1080));
        class_25853.method_10852((class_2561)class_25854);
        class_25853.method_10852((class_2561)new class_2585(String.valueOf(new StringBuilder().append('\n').append(string))));
        class_25852.method_10862(class_25852.method_10866().method_27706(class_124.field_1079).method_10958(new class_2558(class_2558.class_2559.field_11750, Commands.get().get(SayCommand.class).toString(string))).method_10949(new class_2568(class_2568.class_5247.field_24342, (Object)class_25853)));
        return class_25852;
    }

    public int getChatLength() {
        return this.longerChatLines.get();
    }

    public boolean onMsg(String string, int n, int n2, List<class_303<class_2561>> list, List<class_303<class_5481>> list2) {
        if (!this.isActive() || this.skipMessage) {
            return false;
        }
        return this.antiSpamEnabled.get() != false && this.antiSpamOnMsg(string, n, n2, list, list2);
    }

    private String applyAnnoy(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = true;
        for (int n : string.codePoints().toArray()) {
            if (bl) {
                stringBuilder.appendCodePoint(Character.toUpperCase(n));
            } else {
                stringBuilder.appendCodePoint(Character.toLowerCase(n));
            }
            bl = !bl;
            if (false <= true) continue;
            return null;
        }
        string = String.valueOf(stringBuilder);
        return string;
    }

    private void sendWarningMessage(String string, String string2, String string3) {
        class_2585 class_25852 = new class_2585(string2);
        if (this.disableButton.get().booleanValue()) {
            class_2554 class_25542 = this.getSendButton(string, string3);
            class_25852.method_10852((class_2561)class_25542);
        }
        ChatUtils.info("Warning", (class_2561)class_25852);
    }

    public boolean isInfiniteChatBox() {
        return this.isActive() && this.infiniteChatBox.get() != false;
    }

    private String applyFancy(String string) {
        String string2 = string;
        this.sb.setLength(0);
        for (char c : string2.toCharArray()) {
            if (SMALL_CAPS.containsKey(c)) {
                this.sb.append(SMALL_CAPS.get(c));
                continue;
            }
            this.sb.append(c);
            if (3 < 4) continue;
            return null;
        }
        string2 = String.valueOf(this.sb);
        return string2;
    }

    private String getSuffix() {
        return this.getAffix(this.suffixEnabled, this.suffixRandom, " (%03d)", this.suffixText, this.suffixSmallCaps);
    }

    private boolean containsCoordinates(String string) {
        return string.matches(".*(?<x>-?\\d{3,}(?:\\.\\d*)?)(?:\\s+(?<y>\\d{1,3}(?:\\.\\d*)?))?\\s+(?<z>-?\\d{3,}(?:\\.\\d*)?).*");
    }

    public BetterChat() {
        super(Categories.Misc, "better-chat", "Improves your chat experience in various ways.");
        this.sgAnnoy = this.settings.createGroup("Annoy");
        this.sgAntiSpam = this.settings.createGroup("Anti Spam");
        this.sgChatProtect = this.settings.createGroup("Chat Protect");
        this.sgFancyChat = this.settings.createGroup("Fancy Chat");
        this.sgLongerChat = this.settings.createGroup("Longer Chat");
        this.sgPrefix = this.settings.createGroup("Prefix");
        this.sgSuffix = this.settings.createGroup("Suffix");
        this.annoyEnabled = this.sgAnnoy.add(new BoolSetting.Builder().name("annoy-enabled").description("Makes your messages aNnOyInG.").defaultValue(false).build());
        this.antiSpamEnabled = this.sgAntiSpam.add(new BoolSetting.Builder().name("anti-spam-enabled").description("Enables the anti-spam.").defaultValue(true).build());
        this.antiSpamDepth = this.sgAntiSpam.add(new IntSetting.Builder().name("depth").description("How many chat messages to check for duplicate messages.").defaultValue(4).min(1).sliderMin(1).build());
        this.antiSpamMoveToBottom = this.sgAntiSpam.add(new BoolSetting.Builder().name("move-to-bottom").description("Moves any duplicate messages to the bottom of the chat.").defaultValue(true).build());
        this.coordsProtectionEnabled = this.sgChatProtect.add(new BoolSetting.Builder().name("coords-protection-enabled").description("Prevents you from sending messages in chat that may contain coordinates.").defaultValue(true).build());
        this.disableAllMessages = this.sgChatProtect.add(new BoolSetting.Builder().name("disable-all-messages").description("Prevents you from essentially being able to send messages in chat.").defaultValue(false).build());
        this.disableButton = this.sgChatProtect.add(new BoolSetting.Builder().name("disable-button").description("Adds a button to the warning to send a message to the chat in any way.").defaultValue(true).build());
        this.fancyEnabled = this.sgFancyChat.add(new BoolSetting.Builder().name("fancy-chat-enabled").description("Makes your messages fancy!").defaultValue(false).build());
        this.infiniteChatBox = this.sgLongerChat.add(new BoolSetting.Builder().name("infinite-chat-box").description("Lets you type infinitely long messages.").defaultValue(true).build());
        this.longerChatEnabled = this.sgLongerChat.add(new BoolSetting.Builder().name("longer-chat-enabled").description("Extends chat length.").defaultValue(true).build());
        this.longerChatLines = this.sgLongerChat.add(new IntSetting.Builder().name("extra-lines").description("The amount of extra chat lines.").defaultValue(1000).min(100).sliderMax(1000).build());
        this.prefixEnabled = this.sgPrefix.add(new BoolSetting.Builder().name("prefix-enabled").description("Enables a prefix.").defaultValue(false).build());
        this.prefixText = this.sgPrefix.add(new StringSetting.Builder().name("text").description("The text to add as your prefix.").defaultValue("> ").build());
        this.prefixSmallCaps = this.sgPrefix.add(new BoolSetting.Builder().name("small-caps").description("Uses a small font.").defaultValue(false).build());
        this.prefixRandom = this.sgPrefix.add(new BoolSetting.Builder().name("random-number").description("Example: <msg> (538)").defaultValue(false).build());
        this.suffixEnabled = this.sgSuffix.add(new BoolSetting.Builder().name("suffix-enabled").description("Enables a suffix.").defaultValue(false).build());
        this.suffixText = this.sgSuffix.add(new StringSetting.Builder().name("text").description("The text to add as your suffix.").defaultValue(" | Meteor on Crack!").build());
        this.suffixSmallCaps = this.sgSuffix.add(new BoolSetting.Builder().name("small-caps").description("Uses a small font.").defaultValue(true).build());
        this.suffixRandom = this.sgSuffix.add(new BoolSetting.Builder().name("random").description("Example: <msg> (538)").defaultValue(false).build());
        this.sb = new StringBuilder();
    }

    private boolean antiSpamOnMsg(String string, int n, int n2, List<class_303<class_2561>> list, List<class_303<class_5481>> list2) {
        string = class_3544.method_15440((String)string);
        for (int i = 0; i < this.antiSpamDepth.get(); ++i) {
            if (!this.antiSpamCheckMsg(list2, string, n2, n, i)) continue;
            if (this.antiSpamMoveToBottom.get().booleanValue() && i != 0) {
                class_303<class_5481> class_3032 = list2.remove(i);
                list2.add(0, class_3032);
                list.add(0, class_3032);
            }
            return true;
        }
        return false;
    }

    private String getPrefix() {
        return this.getAffix(this.prefixEnabled, this.prefixRandom, "(%03d) ", this.prefixText, this.prefixSmallCaps);
    }

    private boolean antiSpamCheckMsg(List<class_303<class_5481>> list, String string, int n, int n2, int n3) {
        class_303<class_5481> class_3032;
        class_303<class_5481> class_3033 = class_3032 = list.size() > n3 ? list.get(n3) : null;
        if (class_3032 == null) {
            return false;
        }
        String string2 = ((class_5481)class_3032.method_1412()).toString();
        if (class_3544.method_15440((String)string2).equals(string)) {
            string2 = String.valueOf(new StringBuilder().append(string2).append(class_124.field_1080).append(" (2)"));
            ((ChatHudLineAccessor)class_3032).setText(new class_2585(string2));
            ((ChatHudLineAccessor)class_3032).setTimestamp(n);
            ((ChatHudLineAccessor)class_3032).setId(n2);
            return true;
        }
        Matcher matcher = Pattern.compile(".*(\\([0-9]+\\)$)").matcher(string2);
        if (matcher.matches()) {
            String string3 = matcher.group(1);
            int n4 = Integer.parseInt(string3.substring(1, string3.length() - 1));
            int n5 = string2.lastIndexOf(string3);
            if (class_3544.method_15440((String)(string2 = string2.substring(0, n5 - class_124.field_1080.toString().length() - 1))).equals(string)) {
                string2 = String.valueOf(new StringBuilder().append(string2).append(class_124.field_1080).append(" (").append(n4 + 1).append(")"));
                ((ChatHudLineAccessor)class_3032).setText(new class_2585(string2));
                ((ChatHudLineAccessor)class_3032).setTimestamp(n);
                ((ChatHudLineAccessor)class_3032).setId(n2);
                return true;
            }
        }
        return false;
    }

    private String getAffix(Setting<Boolean> setting, Setting<Boolean> setting2, String string, Setting<String> setting3, Setting<Boolean> setting4) {
        String string2;
        if (setting.get().booleanValue()) {
            if (setting2.get().booleanValue()) {
                string2 = String.format(string, Utils.random(0, 1000));
            } else {
                string2 = setting3.get();
                if (setting4.get().booleanValue()) {
                    this.sb.setLength(0);
                    for (char c : string2.toCharArray()) {
                        if (SMALL_CAPS.containsKey(c)) {
                            this.sb.append(SMALL_CAPS.get(c));
                            continue;
                        }
                        this.sb.append(c);
                        if (-1 <= 3) continue;
                        return null;
                    }
                    string2 = String.valueOf(this.sb);
                }
            }
        } else {
            string2 = "";
        }
        return string2;
    }

    @EventHandler
    private void onSendMessage(SendMessageEvent sendMessageEvent) {
        String string = sendMessageEvent.msg;
        if (this.annoyEnabled.get().booleanValue()) {
            string = this.applyAnnoy(string);
        }
        if (this.fancyEnabled.get().booleanValue()) {
            string = this.applyFancy(string);
        }
        string = String.valueOf(new StringBuilder().append(this.getPrefix()).append(string).append(this.getSuffix()));
        if (this.disableAllMessages.get().booleanValue()) {
            this.sendWarningMessage(string, "You are trying to send a message to the global chat! ", "Send your message to the global chat:");
            sendMessageEvent.cancel();
            return;
        }
        if (this.coordsProtectionEnabled.get().booleanValue() && this.containsCoordinates(string)) {
            this.sendWarningMessage(string, "It looks like there are coordinates in your message! ", "Send your message to the global chat even if there are coordinates:");
            sendMessageEvent.cancel();
            return;
        }
        sendMessageEvent.msg = string;
    }

    public boolean isLongerChat() {
        return this.longerChatEnabled.get();
    }
}

